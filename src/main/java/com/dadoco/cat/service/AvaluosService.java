package com.dadoco.cat.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dadoco.bpm.utils.QuerysFlow;
import com.dadoco.cat.model.Avaluo;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.tipologias.model.CoeficientesCalculo;
import com.dadoco.cat.tipologias.model.Excepcion;
import com.dadoco.cat.tipologias.model.ValorM2;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.models.cat.AvaluoTerrenoModel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import org.apache.commons.math3.stat.StatUtils;

/**
 *
 * @author jsanga, carlosLoor
 */
@Stateless
public class AvaluosService {

    @EJB
    private VariableService variablesService;
    @EJB
    private ImpuestoPredialService impuestoPredialService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private PredioService predioService;
    @EJB
    private ImpuestoService impuestoService;
    @EJB
    private ExencionService exencionesService;
    @EJB
    private ManagerService aclServices;
    private static Logger log = Logger.getLogger(AvaluosService.class.getName());

    @Transactional
    public Boolean recalcularFactoresDeSectores() {
        try {
            List<Sector> sectores = aclServices.findAll(Sector.class);
            Double median1, median2, median3;
            double[] superficie, frente, fondo;
            int i, tam;

            List<Object> listTemp;

            for (Sector s : sectores) {
                listTemp = (List<Object>) (aclServices.getEm().createNativeQuery("SELECT '0701' || t.cod_parroquia || t.cod_zona || t.cod_sector || "
                        + " t.cod_manzana || t.cod_solar as clave_sector, t.area_qgis, t.frente_propiedad FROM cat_terreno t WHERE t.cod_parroquia = '" + s.getSectorPK().getCodParroquia() + "' AND "
                        + "t.cod_zona = '" + s.getSectorPK().getCodZona() + "' AND t.cod_sector = '" + s.getSectorPK().getCodSector() + "' ORDER BY clave_sector ASC").getResultList());

                i = 0;
                tam = listTemp.size();
                superficie = new double[tam];
                frente = new double[tam];
                fondo = new double[tam];

                Iterator itr = listTemp.iterator();

                while (itr.hasNext()) {

                    Object[] t = (Object[]) itr.next();

                    superficie[i] = t[1] == null ? 0.0 : Double.parseDouble(t[1] + "");
                    frente[i] = t[2] == null ? 0.0 : Double.parseDouble(t[2] + "");
                    fondo[i] = frente[i] == 0 ? 0.0 : superficie[i] / frente[i];

                    i++;
                }

                median1 = StatUtils.percentile(superficie, 50);
                median2 = StatUtils.percentile(frente, 50);
                median3 = StatUtils.percentile(fondo, 50);

                s.setFactorSuperficie(median1);

                aclServices.getEm().merge(s);
                /*System.out.println("Clave=" + "0701"+s.getSectorPK().getCodParroquia()+s.getSectorPK().getCodZona()+s.getSectorPK().getCodSector());
                System.out.println("Superficie: "+median1+" - Frente: "+median2+" - Fondo: "+median3);*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public AvaluoTerrenoModel avaluoTerrenoNew(Predio p) {
        AvaluoTerrenoModel factores;
        Double valorBase;
        Double factorFondo = 0.0, factorFrente = 0.0, factorSuperficie = 0.0, factorVariosFrentes = 0.0, fCaractSuelo = 0.0, fTopografia = 0.0, fAccServs = 0.0, fInfComplServs = 0.0, fCapaRodadura = 0.0, avaluoTerreno = 0.0, factorTotal;
        Double fondoTipo = 0.0, fondoATasar = 0.0, frenteTipo = 0.0;
        Sector s = p.getTerreno().getManzana().getSector();
        if (s.getFactorFondo() == null || s.getFactorFrente() == null || s.getFactorSuperficie() == null || s.getFactorFondo() == 0 || s.getFactorFrente() == 0 || s.getFactorSuperficie() == 0) {
            this.crearExcepciones(p.getClaveCatastral(), "TERRENO - FACTORES(FRENTE, FONDO, SUPERFICIE)", "FACTORES FRENTE, FONDO, SUPERFICIE = 0");
            return null;
        }
        if (p.getTerreno().getFondoPropiedad() != null || p.getTerreno().getFondoPropiedad() == 0) {
            factorFondo = Util.round(Math.pow((p.getTerreno().getFondoPropiedad() / s.getFactorFondo()), 0.5), 2);
        } else {
            factorFondo = 1.0;
        }
        if (p.getTerreno().getFrentePropiedad() != null || p.getTerreno().getFrentePropiedad() == 0) {
            factorFrente = Util.round(Math.pow((p.getTerreno().getFrentePropiedad() / s.getFactorFrente()), 0.25), 2);
        } else {
            factorFrente = 1.0;
        }
        /*if (factorFondo == 0.0 || factorFrente == 0.0) {
            this.crearExcepciones(p.getClaveCatastral(), "TERRENO - FACTORES(FRENTE - FONDO)", "FACTORES FRENTE, FONDO = 0");
            return null;
        }*/
        factorSuperficie = p.getTerreno().getAreaQgis() / s.getFactorSuperficie();
        factorVariosFrentes = Double.valueOf(((ValorDiscreto) (variablesService.obtenerValores("cat_terreno", "ubicacion", p.getTerreno().getUbicacion(), Util.ANIO_ACTUAL.shortValue()))).getCoeficiente() + "");
        fCaractSuelo = Double.valueOf(((ValorDiscreto) (variablesService.obtenerValores("cat_terreno", "caracteristicas_suelo", p.getTerreno().getCaracteristicasSuelo(), Util.ANIO_ACTUAL.shortValue()))).getCoeficiente() + "");
        fTopografia = Double.valueOf(((ValorDiscreto) (variablesService.obtenerValores("cat_terreno", "topografia", p.getTerreno().getTopografia(), Util.ANIO_ACTUAL.shortValue()))).getCoeficiente() + "");
        fAccServs = variablesService.obtenerCoefInfraestructuraBasica(p.getTerreno());
        fInfComplServs = variablesService.obtenerCoefInfraestructuraComplementaria(p.getTerreno());
        fCapaRodadura = Double.valueOf(((ValorDiscreto) (variablesService.obtenerValores("cat_terreno", "tipo_capa_rodadura", p.getTerreno().getTipoCapaRodadura(), Util.ANIO_ACTUAL.shortValue()))).getCoeficiente() + "");
        factorTotal = factorSuperficie * factorFondo * factorFrente * factorVariosFrentes * fCaractSuelo * fTopografia * fAccServs * fInfComplServs * fCapaRodadura;
        //avaluoTerreno = factorTotal*p.getTerreno().getAreaQgis()*p.getTerreno().getManzana().getZonaHomogenea().getValor();
        factores = new AvaluoTerrenoModel(p.getId(), p.getTerreno().getAreaQgis(), p.getTerreno().getFrentePropiedad() != null ? p.getTerreno().getFrentePropiedad() : 0.0, p.getTerreno().getFondoPropiedad() != null ? p.getTerreno().getFondoPropiedad() : 0.0, factorSuperficie, factorFrente, factorFondo, factorVariosFrentes, fCaractSuelo, fTopografia, fAccServs, fInfComplServs, fCapaRodadura, factorTotal, Double.parseDouble(p.getTerreno().getManzana().getZonaHomogenea().getValor() + ""));
        this.getAvaluoConstruccion(p, factores);
        this.crearAvaluo(factores);
        return factores;
    }

    public BigDecimal getAvaluoConstruccion(Predio p, AvaluoTerrenoModel t) {
        try {
            if (p == null) {
                return null;
            }
            for (Bloque b : p.getBloques()) {
                if (b.getEdadEdificacion() <= 0) {
                    this.crearExcepciones(p.getClaveCatastral(), "BLOQUE - EDAD EDIFICACION", "MENOR O IGUAL QUE 0");
                    return null;
                }
                if (b.getNumeroNiveles() <= 0) {
                    this.crearExcepciones(p.getClaveCatastral(), "BLOQUE - NUMERO NIVELES", "MENOR O IGUAL QUE 0");
                    return null;
                }
                if (b.getEstadoConservacion() == 0) {
                    this.crearExcepciones(p.getClaveCatastral(), "BLOQUE - ESTADO CONSERVACION", "IGUAL QUE 0");
                    return null;
                }
                if (b.getEstructura() == 0) {
                    this.crearExcepciones(p.getClaveCatastral(), "BLOQUE - ESTRUCTURA", "IGUAL QUE 0");
                    return null;
                }
                if (b.getAreaTotal() <= 0) {
                    this.crearExcepciones(p.getClaveCatastral(), "BLOQUE - AREA TOTAL", "IGUAL O MENOR QUE 0");
                    return null;
                }
                CoeficientesCalculo clase = this.getValorCoefiente(1, Integer.parseInt(b.getEstadoConservacion() + ""), Boolean.FALSE);
                if (clase != null) {
                    Integer vidaUtil = Integer.parseInt(this.getValorCoefiente(Integer.parseInt(b.getEstructura() + ""), b.getNumeroNiveles(), Boolean.TRUE).getValores());
                    BigDecimal x = new BigDecimal(b.getAnioConstruccion() / vidaUtil).setScale(2, RoundingMode.CEILING);
                    if (x != null) {
                        BigDecimal y = new BigDecimal(Util.getValueFromExpretion(clase.getValores().replace("${x}", x.toString())).toString()).setScale(2, RoundingMode.CEILING);
                        //PARA CALCULAR LOS PESOS
                        Integer sobrepiso = 0, revestimiento = 0, tumbado = 0, cubierta = 0, ventana = 0, vidrios = 0, pesos = 0;
                        if (b.getSobrepiso() > 0) {
                            sobrepiso = variablesService.obtenerValores("cat_bloque", "sobrepiso", b.getSobrepiso(), Util.ANIO_ACTUAL.shortValue()).getPeso();
                        }
                        if (b.getRevestimiento() > 0) {
                            revestimiento = variablesService.obtenerValores("cat_bloque", "revestimiento", b.getRevestimiento(), Util.ANIO_ACTUAL.shortValue()).getPeso();
                        }
                        if (b.getTumbado() > 0) {
                            tumbado = variablesService.obtenerValores("cat_bloque", "tumbado", b.getTumbado(), Util.ANIO_ACTUAL.shortValue()).getPeso();
                        }
                        if (b.getCubierta() > 0) {
                            cubierta = variablesService.obtenerValores("cat_bloque", "cubierta", b.getCubierta(), Util.ANIO_ACTUAL.shortValue()).getPeso();
                        }
                        if (b.getVentana() > 0) {
                            ventana = variablesService.obtenerValores("cat_bloque", "ventana", b.getVentana(), Util.ANIO_ACTUAL.shortValue()).getPeso();
                        }
                        if (b.getVidrio() > 0) {
                            vidrios = variablesService.obtenerValores("cat_bloque", "vidrios", b.getVidrio(), Util.ANIO_ACTUAL.shortValue()).getPeso();
                        }
                        pesos = sobrepiso + revestimiento + tumbado + cubierta + ventana + vidrios;
                        System.out.println("vidaUtil " + vidaUtil + " + x " + x + ", y " + y + " pesos " + pesos);
                    }
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
        }
        return null;
    }

    public CoeficientesCalculo getValorCoefiente(Integer tipo, Integer arg, Boolean coef) {
        try {
            return (CoeficientesCalculo) aclServices.find(QuerysFlow.getCoeficientesCalculo, new String[]{"tipo", "argumento", "periodo", "coef"}, new Object[]{tipo, arg, Util.ANIO_ACTUAL, coef});
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
        }
        return null;
    }

//    private static final org.slf4j.Logger log = LoggerFactory.getLogger("Avaluos Calculating Class");
    public float avaluoTerreno(Predio predio) {
        if (predio == null) {
            return 0;
        }
        if (predio.getTipoPropiedad() == 1) {
            return 0;
        }
        if (Integer.parseInt(predio.getCodPhv()) > 0 || Integer.parseInt(predio.getCodPhh()) > 0) {
            String provincia = predio.getTerreno().getTerrenoPK().getCodProvincia();
            String canton = predio.getTerreno().getTerrenoPK().getCodCanton();
            String parroquia = predio.getTerreno().getTerrenoPK().getCodParroquia();
            String zona = predio.getTerreno().getTerrenoPK().getCodZona();
            String sector = predio.getTerreno().getTerrenoPK().getCodSector();
            String manzana = predio.getTerreno().getTerrenoPK().getCodManzana();
            String solar = predio.getTerreno().getTerrenoPK().getCodSolar();
            String codphv = predio.getCodPhv();
            // Predio p = catastroService.obtenerPredio(provincia, canton, parroquia, zona, sector, manzana, solar, (short) 0);
            float valor = (float) (Math.round(this.avaluoTerreno(null) * 100.0) / 100.0);
            float alicuota = predio.getAlicuotaPh();
            float result = valor * alicuota / 100;
            return (float) (Math.round(result * 100.0) / 100.0);
        }

        // Aqui mis calculos del avaluo del terreno
        // float acum = 1;
        float valorZona = predio.getTerreno().getManzana().getZonaHomogenea().getValorMedianero();

        double areaPredio = predio.getTerreno().getAreaLevantamiento();
        double valorTerreno = valorZona * areaPredio;

        return (float) (Math.round(valorTerreno * 100.0) / 100.0);
    }

    public float avaluoConstruccion(Predio predio) {

        if (predio == null) {
            return 0;
        }
        float acum = 0;
        float val_bloque;

        List<Bloque> bloques = predio.getBloques();

        for (Bloque b : bloques) {
            val_bloque = avaluoBloque(b);

            acum += val_bloque;
        }

        return acum;
    }

    public float avaluoComplementarias(Predio predio) {
        if (predio == null) {
            return 0;
        }

        return 0;
//        float lindero, valor = 0, coef;
//
//        if (predio.getPropiedadHorizontal() > 0) {
//            short provincia = predio.getTerreno().getTerrenoPK().getCodProvincia();
//            short canton = predio.getTerreno().getTerrenoPK().getCodCanton();
//            short parroquia = predio.getTerreno().getTerrenoPK().getCodParroquia();
//            short zona = predio.getTerreno().getTerrenoPK().getCodZona();
//            short sector = predio.getTerreno().getTerrenoPK().getCodSector();
//            short manzana = predio.getTerreno().getTerrenoPK().getCodManzana();
//            short solar = predio.getTerreno().getTerrenoPK().getCodSolar();
//            short ph = predio.getPropiedadHorizontal();
//
//            Predio p = catastroService.obtenerPredio(provincia, canton, parroquia, zona, sector, manzana, solar, (short) 0);
//
//            valor = this.avaluoComplementarias(p);
//
//            float alicuota = this.calculaAlicuotaTotal(provincia, canton, parroquia, zona, sector, manzana, solar, ph);
//
//            float result = valor * alicuota / 100;
//            return (float) (Math.round(result * 100.0) / 100.0);
//        }
//
//        if (predio.getTerreno().getCerramiento() > 0 && predio.getTerreno().getLindero() != null) {
//            lindero = predio.getTerreno().getLindero().getLinderoTotal();
//            coef = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 12);
//            valor = lindero * coef;
//        }
//
//        return (float) (Math.round(valor * 100.0) / 100.0);
    }

    public float avaluoBloque(Bloque bloque) {
        if (bloque == null) {
            return 0;
        }

        return 0;
    }

    public float valorUnitarioConstruccionBloque(Bloque bloque) {
        if (bloque == null) {
            return 0;
        }

        return 0;

    }

    public float avaluoPredio(Predio predio, int anio) {

        float aterreno = avaluoTerreno(predio);
        float aconstruccion = avaluoConstruccion(predio);
        float acomplementarias = avaluoComplementarias(predio);

        float result = aterreno + aconstruccion + acomplementarias;

        return (float) (Math.round(result * 100.0) / 100.0);
    }

    public float calculaAlicuotaTotal(short provincia, short canton, short parroquia, short zona, short sector, short manzana, short solar, short ph) {

        List<Predio> predios = predioService.getPrediosPorPH(provincia, canton, parroquia, zona, sector, manzana, solar, ph);

        float acum = 0;

        for (Predio p : predios) {
            acum += p.getAlicuotaPh();
        }

        return acum;
    }

    public ImpuestoPredial calcularImpuestoPredial(Predio predio, short anio) {
//        float bandaImpositiva = variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", anio);
        float bandaImpositiva = predio.getTerreno().getManzana().getSector().getZona().getParroquia().getBandaImpositiva();
//        log.error("banda: " + bandaImpositiva);
        float aterreno = avaluoTerreno(predio);
        aterreno = (float) (Math.round(aterreno * 100.0) / 100.0);
//        log.error("ater: " + aterreno);
        float aconstruccion = avaluoConstruccion(predio);
        aconstruccion = (float) (Math.round(aconstruccion * 100.0) / 100.0);
//        log.error("acon: " + aconstruccion);
        float acomplementarias = avaluoComplementarias(predio);
        acomplementarias = (float) (Math.round(acomplementarias * 100.0) / 100.0);
//        log.error("acom: " + acomplementarias);
        float apredial = aterreno + aconstruccion + acomplementarias;
        apredial = (float) (Math.round(apredial * 100.0) / 100.0);
//        log.error("apr: " + apredial);

        try {
            return null;// impuestoPredialService.crearImpuestoPredial(predio, anio, aterreno, aconstruccion, acomplementarias, apredial, bandaImpositiva);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }

    }

    public ImpuestoPredial reCalcularImpuestoPredial(Predio predio, short anio) {
//        float bandaImpositiva = variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", anio);
        float bandaImpositiva = predio.getTerreno().getManzana().getSector().getZona().getParroquia().getBandaImpositiva();
//        log.error("banda: " + bandaImpositiva);
        float aterreno = avaluoTerreno(predio);
        aterreno = (float) (Math.round(aterreno * 100.0) / 100.0);
//        log.error("ater: " + aterreno);
        float aconstruccion = avaluoConstruccion(predio);
        aconstruccion = (float) (Math.round(aconstruccion * 100.0) / 100.0);
//        log.error("acon: " + aconstruccion);
        float acomplementarias = avaluoComplementarias(predio);
        acomplementarias = (float) (Math.round(acomplementarias * 100.0) / 100.0);
//        log.error("acom: " + acomplementarias);
        float apredial = aterreno + aconstruccion + acomplementarias;
        apredial = (float) (Math.round(apredial * 100.0) / 100.0);
//        log.error("apr: " + apredial);

        try {
            return null;//impuestoPredialService.recalcularImpuestoPredial(predio, anio, aterreno, aconstruccion, acomplementarias, apredial, bandaImpositiva);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }

    }

    public void actualizarImpuestoPredial(Predio predio, short anio) {

        Object[] params = {predio.getId(), anio, (short) 0};
        List<ImpuestoPredial> result = impuestoService.findByNamedQuery("ImpuestoPredial.findByPredioAnio", params);

        if (!result.isEmpty()) {
            ImpuestoPredial ip = result.get(0);
            impuestoService.remove(ip);
        }

//        float bandaImpositiva = variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", anio);
        float bandaImpositiva = predio.getTerreno().getManzana().getSector().getZona().getParroquia().getBandaImpositiva();

        float aterreno = avaluoTerreno(predio);
        aterreno = (float) (Math.round(aterreno * 100.0) / 100.0);

        float aconstruccion = avaluoConstruccion(predio);
        aconstruccion = (float) (Math.round(aconstruccion * 100.0) / 100.0);

        float acomplementarias = avaluoComplementarias(predio);
        acomplementarias = (float) (Math.round(acomplementarias * 100.0) / 100.0);

        float apredial = aterreno + aconstruccion + acomplementarias;
        apredial = (float) (Math.round(apredial * 100.0) / 100.0);

        try {
            //  impuestoPredialService.crearImpuestoPredial(predio, anio, aterreno, aconstruccion, acomplementarias, apredial, bandaImpositiva);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    public ImpuestoPredial actualizarImpuestoPredialReemision(Predio predio, short anio) {

        Object[] params = {predio.getId(), anio, (short) 0};
        // List<ImpuestoPredial>
        ImpuestoPredial ip = impuestoService.findByNamedQuery("ImpuestoPredial.findByPredioAnio", params).get(0);
        //log.err
        impuestoService.remove(ip);

//        float bandaImpositiva = variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", anio);
        float bandaImpositiva = predio.getTerreno().getManzana().getSector().getZona().getParroquia().getBandaImpositiva();

        float aterreno = avaluoTerreno(predio);
        aterreno = (float) (Math.round(aterreno * 100.0) / 100.0);

        float aconstruccion = avaluoConstruccion(predio);
        aconstruccion = (float) (Math.round(aconstruccion * 100.0) / 100.0);

        float acomplementarias = avaluoComplementarias(predio);
        acomplementarias = (float) (Math.round(acomplementarias * 100.0) / 100.0);

        float apredial = aterreno + aconstruccion + acomplementarias;
        apredial = (float) (Math.round(apredial * 100.0) / 100.0);

        try {
            return null;// impuestoPredialService.recalcularImpuestoPredial(predio, anio, aterreno, aconstruccion, acomplementarias, apredial, bandaImpositiva);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
            return null;
        }

    }

    public void actualizarImpuestoPredial(Predio predio, short anio, short estado) {

        Object[] params = {predio.getId(), anio, estado};
        ImpuestoPredial ip = impuestoService.findByNamedQuery("ImpuestoPredial.findByPredioAnio", params).get(0);
        impuestoService.remove(ip);
//        float bandaImpositiva = variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", anio);
        float bandaImpositiva = predio.getTerreno().getManzana().getSector().getZona().getParroquia().getBandaImpositiva();

        float aterreno = avaluoTerreno(predio);
        aterreno = (float) (Math.round(aterreno * 100.0) / 100.0);

        float aconstruccion = avaluoConstruccion(predio);
        aconstruccion = (float) (Math.round(aconstruccion * 100.0) / 100.0);

        float acomplementarias = avaluoComplementarias(predio);
        acomplementarias = (float) (Math.round(acomplementarias * 100.0) / 100.0);

        float apredial = aterreno + aconstruccion + acomplementarias;
        apredial = (float) (Math.round(apredial * 100.0) / 100.0);

        try {

            //impuestoPredialService.crearImpuestoPredial(predio, anio, aterreno, aconstruccion, acomplementarias, apredial, bandaImpositiva);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

    }

    public float calcularExenciones(Predio predio, short anio, float aterreno, float aconstruccion, float acomplementarias) {

        List<Exencion> exenciones = exencionesService.obtenerExenciones(predio.getId(), anio);

        float avaluo = aterreno + aconstruccion + acomplementarias;

        float acum = 0;

        boolean eximido = false;

        for (Exencion e : exenciones) {
            if (e.estaActivaEn(anio) && e.getRazonExencion().getActivo() == true) {
                switch (e.getRazonExencion().getTipoExencion().getId()) {
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 15:
                    case 17:
                    case 18: {
                        acum += e.getPorcentaje() * avaluo / 100;
                        break;
                    }
                    case 12: {
                        acum += e.getValor();
                        break;
                    }
                    case 13:
                    case 14: {
                        acum = 0;
                        eximido = true;
                        break;
                    }
                    case 16: {
                        acum += aterreno;
                        break;
                    }
                }
            }
            if (eximido == true) {
                break;
            }
        }

        return (acum > avaluo) ? avaluo : acum;
    }

    public Avaluo crearAvaluo(AvaluoTerrenoModel t) {
        try {
            if (t != null) {
                Avaluo av = new Avaluo();
                av.setAnio(Util.ANIO_ACTUAL.shortValue());
                av.setAvaluoTerreno(t.getAvaluoTerreno());
                av.setEstado(Short.valueOf("1"));
                av.setFactorTerrenoSuperficie(t.getFactorSuperficie());
                av.setFactorTerrenoTotal(t.getFactorTotal());
                av.setFechaIngreso(new Date());
                av.setPredio(aclServices.getEm().find(Predio.class, t.getIdPredio()));
                return (Avaluo) aclServices.getEm().merge(av);
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return null;
    }

    public void crearExcepciones(String clave, String atributto, String observaciones) {
        aclServices.getEm().persist(new Excepcion(clave, atributto, observaciones));
    }

    public ValorM2 getValorM2(Short estructura, Integer tipologia, Short piso, Integer etapaConst, Integer cubierta) {
        if (tipologia != null) {
            return (ValorM2) aclServices.find(QuerysFlow.getValorM2Tipologia, new String[]{"estructura", "tipologia", "piso", "etapa"}, new Object[]{estructura, tipologia, piso, etapaConst});
        } else {
            return (ValorM2) aclServices.find(QuerysFlow.getValorM2Cubierta, new String[]{"estructura", "cubierta", "piso", "etapa"}, new Object[]{estructura, cubierta, piso, etapaConst});
        }
    }
}
