/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.report;

import com.dadoco.cat.controller.OpcionesBusquedaPredio;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.service.BloqueService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.EmisionService;
import com.dadoco.cat.service.ExencionService;
import com.dadoco.cat.service.ImpuestoService;
import com.dadoco.cat.service.RazonRecargoService;
import com.dadoco.cat.service.RecargoService;
import com.dadoco.cat.service.ZonaHomogeneaService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.pago.service.DeudasOriginalesService;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@ManagedBean
@ViewScoped
@Named(value = "generarFichaView")
public class GenerarFichaController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger("");

    @EJB
    private ConfigReader config;

    @EJB
    private BloqueService bloqueService;
    @EJB
    private PredioService predioService;
    @EJB
    private ManzanaService manzanaService;
    @EJB
    private VariableService variablesService;
    @EJB
    private ContribuyenteService contribuyenteService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private AvaluosService avaluos;
    @EJB
    private RecargoService recargoService;
    @EJB
    private RazonRecargoService razonRecargoService;
    @EJB
    private ExencionService exencionService;
    @EJB
    private ZonaHomogeneaService homogeneaService;
    @EJB
    private EmisionService emisionService;
    @EJB
    private DeudaService deudaService;
    @EJB
    private DeudasOriginalesService deudasOriginalesService;
    @EJB
    private ImpuestoService impuestoService;
    @EJB
    AvaluosService avaluosService;

    private List<Object> impuestosEmitidos;
    private List<Object> impuestosPreemitidos;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;
    private String solarCod;
    private String phCod;

    private String nombreFicha;

    private int cantidadPredios = 0;
    private int prediosProcesados = 0;
    private short anioEmision = 0;


    private Manzana manzana;

    private Predio predio;

    private List<Contribuyente> propietarios;

    private boolean terrenoExistente;

    private boolean preemisionCompleta;

    private long emision_total_predios, emision_preemitidos, emision_emitidos, emision_sin_emitir;
    private float emision_recaudacion_total, emision_recaudacion_emitida, emision_recaudacion_sin_emitir;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        predio = new Predio();

        opcionesBusqueda = new OpcionesBusquedaPredio();

        impuestosPreemitidos = new ArrayList<>();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        terrenoExistente = false;

        propietarios = new ArrayList<>();

        cantidadPredios = predioService.count();

        prediosProcesados = 0;

        nombreFicha = "";
    }

    public long getEmision_total_predios() {
        return emision_total_predios;
    }

    public void setEmision_total_predios(long emision_total_predios) {
        this.emision_total_predios = emision_total_predios;
    }

    public long getEmision_preemitidos() {
        return emision_preemitidos;
    }

    public void setEmision_preemitidos(long emision_preemitidos) {
        this.emision_preemitidos = emision_preemitidos;
    }

    public long getEmision_emitidos() {
        return emision_emitidos;
    }

    public void setEmision_emitidos(long emision_emitidos) {
        this.emision_emitidos = emision_emitidos;
    }

    public long getEmision_sin_emitir() {
        return emision_sin_emitir;
    }

    public void setEmision_sin_emitir(long emision_sin_emitir) {
        this.emision_sin_emitir = emision_sin_emitir;
    }

    public float getEmision_recaudacion_total() {
        return emision_recaudacion_total;
    }

    public void setEmision_recaudacion_total(float emision_recaudacion_total) {
        this.emision_recaudacion_total = emision_recaudacion_total;
    }

    public float getEmision_recaudacion_emitida() {
        return emision_recaudacion_emitida;
    }

    public void setEmision_recaudacion_emitida(float emision_recaudacion_emitida) {
        this.emision_recaudacion_emitida = emision_recaudacion_emitida;
    }

    public float getEmision_recaudacion_sin_emitir() {
        return emision_recaudacion_sin_emitir;
    }

    public void setEmision_recaudacion_sin_emitir(float emision_recaudacion_sin_emitir) {
        this.emision_recaudacion_sin_emitir = emision_recaudacion_sin_emitir;
    }

    public boolean isPreemisionCompleta() {
        preemisionCompleta = impuestoService.preemisionCompleta(anioEmision);

        return preemisionCompleta;
    }

    public void setPreemisionCompleta(boolean preemisionCompleta) {
        this.preemisionCompleta = preemisionCompleta;
    }

    public EmisionService getEmisionService() {
        return emisionService;
    }

    public void setEmisionService(EmisionService emisionService) {
        this.emisionService = emisionService;
    }

    public DeudaService getDeudaService() {
        return deudaService;
    }

    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    public DeudasOriginalesService getDeudasOriginalesService() {
        return deudasOriginalesService;
    }

    public void setDeudasOriginalesService(DeudasOriginalesService deudasOriginalesService) {
        this.deudasOriginalesService = deudasOriginalesService;
    }

    public String getNombreFicha() {
        return nombreFicha;
    }

    public void setNombreFicha(String nombreFicha) {
        this.nombreFicha = nombreFicha;
    }



//<editor-fold defaultstate="collapsed" desc="Getter and Setters">
    public AvaluosService getAvaluos() {
        return avaluos;
    }

    public void setAvaluos(AvaluosService avaluos) {
        this.avaluos = avaluos;
    }

    public ZonaHomogeneaService getHomogeneaService() {
        return homogeneaService;
    }

    public void setHomogeneaService(ZonaHomogeneaService homogeneaService) {
        this.homogeneaService = homogeneaService;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
    }

    public ContribuyenteService getContribuyenteService() {
        return contribuyenteService;
    }

    public void setContribuyenteService(ContribuyenteService contribuyenteService) {
        this.contribuyenteService = contribuyenteService;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }

    public ZonaService getZonaService() {
        return zonaService;
    }

    public void setZonaService(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    public SectorService getSectorService() {
        return sectorService;
    }

    public void setSectorService(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    public List<Object> getImpuestosEmitidos() {
        return impuestosEmitidos;
    }

    public void setImpuestosEmitidos(List<Object> impuestosEmitidos) {
        this.impuestosEmitidos = impuestosEmitidos;
    }

    public List<Object> getImpuestosPreemitidos() {
        return impuestosPreemitidos;
    }

    public void setImpuestosPreemitidos(List<Object> impuestosPreemitidos) {
        this.impuestosPreemitidos = impuestosPreemitidos;
    }

    public String getProvinciaCod() {
        return provinciaCod;
    }

    public void setProvinciaCod(String provinciaCod) {
        this.provinciaCod = provinciaCod;
    }

    public String getCantonCod() {
        return cantonCod;
    }

    public void setCantonCod(String cantonCod) {
        this.cantonCod = cantonCod;
    }

    public String getParroquiaCod() {
        return parroquiaCod;
    }

    public void setParroquiaCod(String parroquiaCod) {
        this.parroquiaCod = parroquiaCod;
    }

    public String getZonaCod() {
        return zonaCod;
    }

    public void setZonaCod(String zonaCod) {
        this.zonaCod = zonaCod;
    }

    public String getSectorCod() {
        return sectorCod;
    }

    public void setSectorCod(String sectorCod) {
        this.sectorCod = sectorCod;
    }

    public String getManzanaCod() {
        return manzanaCod;
    }

    public void setManzanaCod(String manzanaCod) {
        this.manzanaCod = manzanaCod;
    }

    public String getSolarCod() {
        return solarCod;
    }

    public void setSolarCod(String solarCod) {
        this.solarCod = solarCod;
    }

    public String getPhCod() {
        return phCod;
    }

    public void setPhCod(String phCod) {
        this.phCod = phCod;
    }

    public int getCantidadPredios() {
        return cantidadPredios;
    }

    public void setCantidadPredios(int cantidadPredios) {
        this.cantidadPredios = cantidadPredios;
    }

    public int getPrediosProcesados() {
        return prediosProcesados;
    }

    public void setPrediosProcesados(int prediosProcesados) {
        this.prediosProcesados = prediosProcesados;
    }

    public short getAnioEmision() {
        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }


    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public boolean isTerrenoExistente() {
        return terrenoExistente;
    }

    public void setTerrenoExistente(boolean terrenoExistente) {
        this.terrenoExistente = terrenoExistente;
    }
    
//</editor-fold>
    public boolean buscarPredio() {

        provinciaCod = opcionesBusqueda.getProvinciaCod();
        cantonCod = opcionesBusqueda.getCantonCod();
        parroquiaCod = opcionesBusqueda.getParroquiaCod();
        zonaCod = opcionesBusqueda.getZonaCod();
        sectorCod = opcionesBusqueda.getSectorCod();
        manzanaCod = opcionesBusqueda.getManzanaCod();
        solarCod = opcionesBusqueda.getSolarCod();
       // phCod = opcionesBusqueda.getPhCod();

        log.error(" Prov: " + opcionesBusqueda.getProvinciaCod()
                + " Cant: " + opcionesBusqueda.getCantonCod()
                + " Parr: " + opcionesBusqueda.getParroquiaCod()
                + " Zona: " + opcionesBusqueda.getZonaCod()
                + " Sect: " + opcionesBusqueda.getSectorCod()
                + " Manz: " + opcionesBusqueda.getManzanaCod()
                + " sola: " + opcionesBusqueda.getSolarCod()
         //       + " PH :" + opcionesBusqueda.getPhCod()
        );

        try {
           // predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %2d-%2d-%3d-%2d-%2d no encontrado",
                        zonaCod, sectorCod, manzanaCod, solarCod, phCod));
                init();
                return false;
            } else {

                /* if(comprobarDeudas(predio))
                 {
                 JsfUtil.addInformationMessage("InformaciÃƒÂ³n", String.format("Predio con clave catastral: %2d-%2d-%3d-%2d-%2d, tiene deuda pendientes con el municipio",
                 zonaCod, sectorCod, manzanaCod, solarCod, phCod));

                 init();
                 return;
                 }*/
                opcionesBusqueda.setBuscando(true);

                return true;
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
            return false;
        }
    }

    private boolean comprobarDeudas(Predio p) {
        return true;
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public void nuevoPredio() {
        opcionesBusqueda.setBuscando(true);
    }

    public String getCodigoCatastral() {

        zonaCod = opcionesBusqueda.getZonaCod();
        sectorCod = opcionesBusqueda.getSectorCod();
        manzanaCod = opcionesBusqueda.getManzanaCod();
        solarCod = opcionesBusqueda.getSolarCod();
       // phCod = opcionesBusqueda.getPhCod();

        String clave = String.format("%02d-%02d-%02d-%02d-%02d", zonaCod, sectorCod, manzanaCod, solarCod, phCod);
        return clave;
    }

    public void generaFichaExcelPredioActivo() {

        if (buscarPredio()) {
//            generaFichaExcel(predio);
        }

    }
//
//    public void generaFichaExcel(Predio predio) {
//
//        if (predio == null) {
//            return;
//        }
//
//        File template = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/cat/reportes/plantillas/plantilla-ficha-predial.xlsx");
//        FileInputStream fis;
//        XSSFWorkbook wbw;
//
//        int contCochera = 0, contBodega = 0, contCasillero = 0;
//        float acum, areaCochera = 0, areaBodega = 0, areaCasillero = 0;
//
//        try {
//
//            Predio fichaMadre;
//
//            if (phCod != 0) {
//                fichaMadre = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, (short) 0);
//            } else {
//                fichaMadre = predio;
//            }
//
//            List<Predio> subpredios = predioService.getPrediosPorPH(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);
//
////            log.error("subpredios: " + subpredios.size());
//            if (subpredios.size() > 0) {
//                for (Predio p : subpredios) {
//                    if (p.getCochera() != null) {
//                        contCochera++;
//                        areaCochera += p.getBloqueList().get(0).getAreaTotal().floatValue();
//                    }
//                    if (p.getCasillero() != null) {
//                        contCasillero++;
//                        areaCasillero += p.getBloqueList().get(0).getAreaTotal().floatValue();
//                    }
//                    if (p.getBodega() != null) {
//                        contBodega++;
//                        areaBodega += p.getBloqueList().get(0).getAreaTotal().floatValue();
//                    }
//                }
//            }
//
//            fis = new FileInputStream(template); //Read the spreadsheet that needs to be updated
//            wbw = new XSSFWorkbook(fis);
//            XSSFSheet ws = wbw.getSheet("FICHA");
//
//            //Codigo parroquia
//            // OK
//            short codparr = predio.getTerreno().getTerrenoPK().getCodParroquia();
//            valorCelda(ws, 1, 5, (int) (codparr / 10));
//            valorCelda(ws, 1, 6, (int) (codparr % 10));
//
//            //Codigo catastral IGM
//            // OK
//            valorCelda(ws, 3, 0, predio.getTerreno().getTerrenoPK().getCodZona());
//            valorCelda(ws, 3, 1, predio.getTerreno().getTerrenoPK().getCodSector());
//            valorCelda(ws, 3, 2, predio.getTerreno().getTerrenoPK().getCodManzana());
//            valorCelda(ws, 3, 3, predio.getTerreno().getTerrenoPK().getCodSolar());
//            valorCelda(ws, 3, 4, predio.getPropiedadHorizontal());
//
//            //Clave escritura
//            // OK
//            valorCelda(ws, 7, 0, predio.getSector());
//            valorCelda(ws, 7, 3, predio.getManzana());
//            valorCelda(ws, 7, 5, predio.getSolar());
//
//            //Estado Solar
//            // OK
//            switch (predio.getTerreno().getTipoLote()) {
//                case 0:
//                    valorCelda(ws, 1, 16, "X");
//                    break;
//                case 1:
//                    valorCelda(ws, 2, 16, "X");
//                    break;
//                case 2:
//                    valorCelda(ws, 3, 16, "X");
//                    break;
//                default:
//                    valorCelda(ws, 4, 16, "X");
//            }
//
//            //Cod. Zona
//            // OK
//            valorCelda(ws, 1, 19, predio.getTerreno().getTerrenoPK().getCodZona());
//
//            //Cod. Sector
//            // OK
//            valorCelda(ws, 1, 21, predio.getTerreno().getTerrenoPK().getCodSector());
//
//            //Cod. Manzana
//            // OK
//            valorCelda(ws, 1, 23, predio.getTerreno().getTerrenoPK().getCodManzana());
//
//            //Cod. Solar
//            // OK
//            valorCelda(ws, 1, 25, predio.getTerreno().getTerrenoPK().getCodSolar());
//
//            //Cod. Piso
//            // OK
//            valorCelda(ws, 1, 27, predio.getPiso());
//
//            //Cod. Dpto
//            // OK
//            valorCelda(ws, 1, 29, predio.getDepartamento());
//
//            //Cod. Cochera
//            // OK
//            valorCelda(ws, 1, 31, contCochera > 0 ? contCochera : 0);
//
//            //Cod. Bodega
//            // OK
//            valorCelda(ws, 1, 33, contBodega > 0 ? contBodega : 0);
//
//            //Cod. Casillero
//            // OK
//            valorCelda(ws, 1, 35, contCasillero > 0 ? contCasillero : 0);
//
//            // Codigo anterior
//            // OK
//            //Cod. Zona anterior
//            // OK
//            valorCelda(ws, 4, 19, "");
//
//            //Cod. Sector anterior
//            // OK
//            valorCelda(ws, 4, 21, predio.getSector());
//
//            //Cod. Manzana anterior
//            // OK
//            valorCelda(ws, 4, 23, predio.getManzana());
//
//            //Cod. Solar anterior
//            // OK
//            valorCelda(ws, 4, 25, predio.getSolar());
//
//            //Cod. PH anterior
//            // OK
//            valorCelda(ws, 4, 27, "");
//
//            //Barrio
//            // OK
//            valorCelda(ws, 5, 22, fichaMadre.getBarrio());
//
//            // Parroquia
//            // OK
//            valorCelda(ws, 5, 32, fichaMadre.getTerreno().getManzana().getSector().getZona().getParroquia().getNombre());
//
//            //Calle
//            // OK
//            valorCelda(ws, 6, 14, fichaMadre.getCalle());
//
//            // Acera
//            // OK
//            valorCelda(ws, 6, 33, fichaMadre.getTerreno().getInfAceraBordillos() == 1 ? "Tiene" : "No tiene");
//
//            //Nombre edificio
//            // OK
//            valorCelda(ws, 7, 16, fichaMadre.getEdificio());
//
//            //Numero de pisos
//            // OK
//            valorCelda(ws, 7, 29, fichaMadre.getBloqueList().size() > 0 ? fichaMadre.getBloqueList().get(0).TotalPisos() : "");
//
//            //Numero de bloques
//            // OK
//            valorCelda(ws, 8, 15, predio.getBloqueList().size());
//
//            //Piso
//            // OK
//            valorCelda(ws, 7, 33, predio.getPiso());
//
//            //Dpto
//            // OK
//            valorCelda(ws, 7, 36, predio.getDepartamento());
//
//            //Zona Homogenea
//            // OK
//            valorCelda(ws, 8, 36, predio.getTerreno().getManzana().getZonaHomogenea().getCodigoZona());
//
//            int cont;
//
//            //Propietarios anteriores
////            cont = 0;
////            for (Contribuyente c : predio.getPropietarios()) {
////                valorCelda(ws, 11 + cont, 4, c.getNombre());
////                valorCelda(ws, 11 + cont, 10, c.getApellidoPaterno() + " " + c.getApellidoMaterno());
////                valorCelda(ws, 11 + cont, 14, c.getTelefono());
////                valorCelda(ws, 11 + cont, 17, c.getIdentificacion());
////                cont++;
////            }
//            //
//            //Propietarios actuales
//            // OK
//            cont = 0;
//
//            List<Contribuyente> propietariosPredio = predio.getPropietarios();
//
//            for (Contribuyente c : propietariosPredio) {
//                if (cont < 3) {
//                    valorCelda(ws, 15 + cont, 4, c.getNombre());
//                    valorCelda(ws, 15 + cont, 10, c.getApellidoPaterno() + " " + c.getApellidoMaterno());
//                    valorCelda(ws, 15 + cont, 14, c.getTelefono());
//                    valorCelda(ws, 15 + cont, 17, c.getIdentificacion());
//                }
//                cont++;
//            }
//
//            //Escrituras
//            // OK
//            cont = 0;
//            List<Escritura> escrituras = predio.getEscrituras();
//            for (Escritura e : escrituras) {
//                if (cont < 3) {
//                    valorCelda(ws, 15 + cont, 22, e.getTipoEscritura());
//                    valorCelda(ws, 15 + cont, 24, e.getPlazo());
//                    valorCelda(ws, 15 + cont, 26, e.getNotaria());
//                    valorCelda(ws, 15 + cont, 28, e.getFechaNotaria());
//                    valorCelda(ws, 15 + cont, 29, e.getFolioNotaria());
//                    valorCelda(ws, 15 + cont, 30, e.getCuantia());
//                    valorCelda(ws, 15 + cont, 31, e.getNumeroRegistroPropiedad());
//                    valorCelda(ws, 15 + cont, 33, e.getRepertorioRegistrador());
//                    valorCelda(ws, 15 + cont, 35, e.getFechaInscripcion());
//                    valorCelda(ws, 15 + cont, 36, e.getFechaCatastro());
//                }
//                cont++;
//            }
//
//            float avaluoTerreno = avaluos.avaluoTerreno(predio);
//            float avaluoConstruccion = avaluos.avaluoConstruccion(predio);
//            float avaluoComplementarias = avaluos.avaluoComplementarias(predio);
//
//            // Aspectos tecnico-legales
//            valorCelda(ws, 19, 7, predio.getTerreno().getLindero().getLinNorteLongitudSis());
//            valorCelda(ws, 20, 7, predio.getTerreno().getLindero().getLinEsteLongitudSis());
//            valorCelda(ws, 21, 7, predio.getTerreno().getAreaEscritura());
//            valorCelda(ws, 22, 7, predio.getTerreno().getAreaLevantamiento());
//            valorCelda(ws, 23, 7, predio.getTerreno().getAreaLevantamiento().doubleValue() - predio.getTerreno().getAreaEscritura().doubleValue());
//            valorCelda(ws, 24, 7, predio.getAlicuotaPh());
//            valorCelda(ws, 25, 7, avaluoTerreno / predio.getTerreno().getAreaLevantamiento().floatValue()); // Valor u/m2 Terreno
//            if (!predio.getBloqueList().isEmpty()) {
//                acum = 0;
//                for (Bloque b : predio.getBloqueList()) {
//                    acum += b.getAreaTotal().floatValue();
//                }
//                valorCelda(ws, 26, 7, avaluoConstruccion / acum); // Valor u/m2 Construccion
//            } else {
//                valorCelda(ws, 26, 7, 0); // no tiene construccion definida
//            }
//
//            valorCelda(ws, 29, 7, areaBodega);
//            valorCelda(ws, 30, 7, areaCochera);
//
//            // Bloques
//            cont = 0;
//            float areaTotal = 0, valor, porcientoConstruccion = 0, porcientoConservacion = 0, v1, valorMetro = 0, valorTotal = 0;
//
//            for (Bloque b : predio.getBloqueList()) {
//
////                log.error("bloque: " + b.getId());
//                if (b.getTipoConstruccion() != 999) {
//                    valor = avaluos.avaluoBloque(b);
//                    valorTotal += valor;
//                    v1 = b.getPorcientoConstruccion().floatValue();
//                    valorMetro += valor / b.getAreaTotal().floatValue();
//
//                    if (cont < 6) {
//                        valorCelda(ws, 20, 14 + cont, variablesService.obtenerValor("cat_bloque", "tipo_construccion", b.getTipoConstruccion()));
//                        valorCelda(ws, 21, 14 + cont, b.getAreaTotal());
//                        valorCelda(ws, 22, 14 + cont, valor);
//                        valorCelda(ws, 23, 14 + cont, v1);
//                        valorCelda(ws, 24, 14 + cont, b.getPorcientoConservacion().floatValue());
//                        valorCelda(ws, 25, 14 + cont, valor / b.getAreaTotal().floatValue());
//                    }
//
//                    areaTotal += b.getAreaTotal().floatValue();
//
//                    porcientoConstruccion += v1;
//                    porcientoConservacion += b.getPorcientoConservacion().floatValue();
//
//                    cont += 2;
//                }
//            }
//            if (cont > 0) {
//                log.error(porcientoConstruccion + " " + porcientoConservacion);
//                porcientoConstruccion = porcientoConstruccion / (cont / 2);
//                porcientoConservacion = porcientoConservacion / (cont / 2);
//            }
//            valorCelda(ws, 21, 20, areaTotal);
//            valorCelda(ws, 25, 20, valorMetro);
//            valorCelda(ws, 26, 20, valorTotal);
//
//            float lindero = predio.getTerreno().getLindero().getLinderoTotal();
//
//            //  Aqui van las celdas del resumen de avaluos. Error en la ficha. Aqui meti el cerramiento.
//            float metroCerramiento = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 12);
////            float bandaImpositiva = variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", (short) 2016);
//            float bandaImpositiva = predio.getTerreno().getManzana().getSector().getZona().getParroquia().getBandaImpositiva();
//            valorCelda(ws, 27, 14, lindero);
//            valorCelda(ws, 28, 14, lindero * metroCerramiento);
//            valorCelda(ws, 31, 14, metroCerramiento);
//            valorCelda(ws, 32, 20, lindero * metroCerramiento);
//            valorCelda(ws, 33, 20, bandaImpositiva);
//            //
//            // Avaluo del solar
//            valorCelda(ws, 35, 23, avaluoTerreno);
//            valorCelda(ws, 36, 23, valorTotal);
//            valorCelda(ws, 37, 23, lindero * metroCerramiento);
//            valorCelda(ws, 38, 23, avaluoTerreno + valorTotal + lindero * metroCerramiento);
//
//            float baseImponible = (avaluoTerreno + valorTotal + lindero * metroCerramiento) * bandaImpositiva / 1000;
//            valorCelda(ws, 40, 23, baseImponible);
//
//            //Cerramiento
//            valorCelda(ws, 39, 7, lindero * metroCerramiento);
//            valorCelda(ws, 40, 7, lindero * metroCerramiento);
//
//            //Recargos y Exenciones
//            short anio = getAnioEmision();
//
//            valorCelda(ws, 44, 27, baseImponible);
//
//            List<Recargo> recargos = recargoService.obtenerRecargos(predio.getId(), anio);
//
//            acum = 0;
//
////            log.error("Recargos: " + recargos.size());
//            for (Recargo r : recargos) {
////                log.error(r.getRazonRecargo().getId() + " activo en " + anio + " " + r.estaActivoEn(anio) + " razon activa " + r.getRazonRecargo().isActivo());
//                if (r.estaActivoEn(anio)) {
//                    switch (r.getRazonRecargo().getId()) {
//                        case 1: {    /// CEM
//                            valorCelda(ws, 47, 0, r.getAnioRegistro());
//                            valorCelda(ws, 47, 12, r.getVigencia());
//                            valorCelda(ws, 47, 15, r.getValor());
//                            valorCelda(ws, 47, 27, r.getValor() / r.getVigencia());
//
//                            acum += r.getValor() / r.getVigencia();
//                            break;
//                        }
//                        case 2: {
//                            valorCelda(ws, 48, 0, r.getAnioRegistro());
//                            valorCelda(ws, 48, 12, r.getVigencia());
//                            valorCelda(ws, 48, 15, r.getValor());
//                            valorCelda(ws, 48, 27, r.getValor());
//
//                            acum += r.getValor();
//                            break;
//                        }
//                        case 3:
//                        case 4: {
//                            int urban = predio.getTerreno().getInfAguaPotable()
//                                    + predio.getTerreno().getInfAlcantarilladoPluvial()
//                                    + predio.getTerreno().getInfAlcantarilladoSanitario()
//                                    + predio.getTerreno().getInfEnergiaElectrica();
//
//                            if (urban == 4) {
//                                valorCelda(ws, 45, 0, r.getAnioRegistro());
//                                valorCelda(ws, 45, 12, r.getVigencia());
//                                valorCelda(ws, 45, 15, r.getValor());
//                                valorCelda(ws, 45, 27, (avaluoTerreno + valorTotal) * (r.getValor() / 1000) * (anio - r.getVigencia()));
//
//                                acum += (avaluoTerreno + valorTotal) * (r.getValor() / 1000) * (anio - r.getAnioRegistro());
//                            }
//                            break;
//                        }
//                    }
//                }
//            }
//
//            List<Exencion> exenciones = exencionService.obtenerExenciones(predio.getId(), anio);
//
//            float valorOtros = 0, porciento3raEdad = 0;
//
//            for (Exencion e : exenciones) {
//
////                log.error(e.getRazonExencion().getId() + " activo en " + anio + " " + e.estaActivaEn(anio));
//                if (e.estaActivaEn(anio)) {
//                    switch (e.getRazonExencion().getId()) {
//                        case 1:
//                        case 2:
//                        case 3:
//                        case 5:
//                        case 6:
//                        case 7:
//                        case 9:
//                        case 10:
//                        case 15:
//                        case 17:
//                        case 18: {
//                            valorOtros += e.getPorcentaje() * baseImponible / 100;
//                            break;
//                        }
//                        case 8: {
//                            valorCelda(ws, 51, 0, e.getFechaIngreso());
//                            valorCelda(ws, 51, 12, e.getHasta());
//                            valorCelda(ws, 51, 19, e.getPorcentaje());
//                            valorCelda(ws, 51, 27, e.getPorcentaje() * baseImponible / 100);
//
//                            acum -= e.getPorcentaje() * baseImponible / 100;
//                            break;
//                        }
//                        case 11: {
//                            if (porciento3raEdad < e.getPorcentaje()) {
//                                valorCelda(ws, 49, 0, e.getFechaIngreso());
//                                valorCelda(ws, 49, 12, e.getHasta());
//                                valorCelda(ws, 49, 19, e.getPorcentaje());
//                                valorCelda(ws, 49, 27, e.getPorcentaje() * baseImponible / 100);
//                                porciento3raEdad = e.getPorcentaje();
//                            }
////                            acum -= e.getPorcentaje() * baseImponible / 100;
//                            break;
//                        }
//                        case 12: {
//                            valorOtros += e.getValor();
//                            break;
//                        }
//                        case 13: {
//                            valorCelda(ws, 50, 0, e.getFechaIngreso());
//                            valorCelda(ws, 50, 12, e.getHasta());
//                            valorCelda(ws, 50, 19, e.getValor());
//                            valorCelda(ws, 50, 27, e.getValor() * bandaImpositiva / 1000);
//
//                            acum -= e.getValor() * bandaImpositiva / 1000;
//                            break;
//                        }
//                        case 14: {
//                            valorOtros += baseImponible;
//                            break;
//                        }
//                        case 16: {
//                            valorOtros += avaluoTerreno;
//                            break;
//                        }
//                    }
//                }
//            }
//            valorCelda(ws, 52, 15, valorOtros);
//            valorCelda(ws, 52, 27, valorOtros);
//
//            float impuestoPredial = baseImponible + acum - (porciento3raEdad * baseImponible / 100) - valorOtros;
//            valorCelda(ws, 53, 27, impuestoPredial);
//
//            /*
//             Segunda pagina de la ficha
//             */
//            // Foto
//            File inputFile;
//            BufferedImage imagenOriginal;
//            BufferedImage resizedImage;
//            ByteArrayOutputStream os;
//            InputStream buffer;
//            CreationHelper helper;
//            Drawing drawing;
//            ClientAnchor anchor;
//            Picture pict;
//
//            String foto;
//            if (!predio.getArchivos().isEmpty()) {
//
//                String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
//                foto = uploadDir + "/" + predio.getArchivos().get(0).getRuta();
//
////                log.error(foto);
//                try {
//                    inputFile = new File(foto);
//                    imagenOriginal = ImageIO.read(inputFile);
//
//                    resizedImage = resizeImage(imagenOriginal, BufferedImage.TYPE_INT_RGB, 240, 180);
//
//                    os = new ByteArrayOutputStream();
//                    ImageIO.write(resizedImage, "jpg", os);
//                    buffer = new ByteArrayInputStream(os.toByteArray());
//
//                    helper = wbw.getCreationHelper();
//                    drawing = ws.createDrawingPatriarch();
//
//                    anchor = helper.createClientAnchor();
//                    anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);
//
//                    int pictureIndex = wbw.addPicture(buffer, Workbook.PICTURE_TYPE_JPEG);
//
//                    anchor.setRow1(57); // same row is okay
//                    anchor.setCol1(0);
//                    anchor.setRow2(68);
//                    anchor.setCol2(8);
//
//                    pict = drawing.createPicture(anchor, pictureIndex);
//                    pict.resize();
//                } catch (Exception e) {
//                    log.error(e.getMessage());
//                }
//            }
//
//            // Manzanero
//            if (!predio.getArchivos().isEmpty()) {
//
//                String uploadDirManzanero = config.getDbConfiguration().getString("directorio_manzaneros");
//                Archivo manzanero = predio.getTerreno().getManzana().getManzanero();
//                if (manzanero != null) {
//                    String mm = manzanero.getRuta();
//
//                    String ruta = uploadDirManzanero + "/" + manzanero.getRuta().substring(0, mm.length() - 4) + ".jpg";
//
//                    log.error("plano: " + ruta);
//
//                    if (existeArchivo(ruta)) {
//
//                        log.error("existe el plano jpg");
//
//                        try {
//                            inputFile = new File(ruta);
//                            imagenOriginal = ImageIO.read(inputFile);
//
//                            resizedImage = resizeImage(imagenOriginal, BufferedImage.TYPE_INT_RGB, 440, 360);
//                            os = new ByteArrayOutputStream();
////                            ImageIO.write(imagenOriginal, "jpg", os);
//                            ImageIO.write(resizedImage, "jpg", os);
//                            buffer = new ByteArrayInputStream(os.toByteArray());
//
//                            helper = wbw.getCreationHelper();
//                            drawing = ws.createDrawingPatriarch();
//
//                            anchor = helper.createClientAnchor();
//                            anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE);
//
//                            int pictureIndex = wbw.addPicture(buffer, Workbook.PICTURE_TYPE_JPEG);
//
//                            anchor.setRow1(72); // same row is okay
//                            anchor.setCol1(0);
//                            anchor.setRow2(93);
//                            anchor.setCol2(11);
//
//                            pict = drawing.createPicture(anchor, pictureIndex);
//                            pict.resize();
//                        } catch (Exception e) {
//                            log.error(e.getMessage());
//                        }
//                    }
//                }
//            }
//
//            cont = 0;
//            for (Bloque b : predio.getBloqueList()) {
//                if (b.getTipoConstruccion() != 999 && cont < 7) {
//                    valorCelda(ws, 57, 17 + cont, b.TotalPisos());
//                    valorCelda(ws, 58, 17 + cont, b.getCimientos());
//                    valorCelda(ws, 59, 17 + cont, b.getCadenas());
//                    valorCelda(ws, 60, 17 + cont, b.getColumnas());
//                    valorCelda(ws, 61, 17 + cont, b.getVigas());
//                    valorCelda(ws, 62, 17 + cont, b.getPisos());
//                    valorCelda(ws, 63, 17 + cont, b.getSobrepisos());
//                    valorCelda(ws, 64, 17 + cont, b.getParedes());
//                    valorCelda(ws, 65, 17 + cont, b.getEnlucidos());
//                    valorCelda(ws, 66, 17 + cont, b.getEntrepisos());
//                    valorCelda(ws, 67, 17 + cont, b.getCubiertas());
//                    valorCelda(ws, 68, 17 + cont, b.getTumbados());
//                    valorCelda(ws, 69, 17 + cont, b.getInstalacionElectrica());
//                    valorCelda(ws, 70, 17 + cont, b.getInstalacionSanitaria());
//                    valorCelda(ws, 71, 17 + cont, b.getInstalacionesEspeciales());
//                    valorCelda(ws, 72, 17 + cont, b.getSistemaContraIncendio());
//                    valorCelda(ws, 73, 17 + cont, b.getPuertasExteriores());
//                    valorCelda(ws, 74, 17 + cont, b.getPuertasInteriores());
//                    valorCelda(ws, 75, 17 + cont, b.getVentanas());
//                    valorCelda(ws, 76, 17 + cont, b.getProtectoresVentanas());
//                    valorCelda(ws, 77, 17 + cont, b.getVidrios());
//                    valorCelda(ws, 78, 17 + cont, b.getFachada());
//                    valorCelda(ws, 79, 17 + cont, b.getEscalera());
//                    valorCelda(ws, 80, 17 + cont, b.getPintura());
//                    valorCelda(ws, 81, 17 + cont, b.getCocina());
//                    valorCelda(ws, 82, 17 + cont, b.getCloset());
//                    valorCelda(ws, 83, 17 + cont, b.getBannos());
//
//                    valorCelda(ws, 91, 17 + cont, b.getNoDepartamentos());
//                    valorCelda(ws, 92, 17 + cont, b.getNoLocales());
//                    valorCelda(ws, 93, 17 + cont, b.getNoOficinas());
//                    valorCelda(ws, 94, 17 + cont, b.getNoGalpones());
//
//                    valorCelda(ws, 97 + cont, 0, cont + 1);
//                    valorCelda(ws, 97 + cont, 1, b.getUsResidenciaTemporal());
//                    valorCelda(ws, 97 + cont, 2, b.getUsResidenciaPermanente());
//                    valorCelda(ws, 97 + cont, 3, b.getUsComercial());
//                    valorCelda(ws, 97 + cont, 4, b.getUsArtesanal());
//                    valorCelda(ws, 97 + cont, 5, b.getUsRecreacion());
//                    valorCelda(ws, 97 + cont, 6, b.getUsEducacional());
//                    valorCelda(ws, 97 + cont, 7, b.getUsInstitucional());
//                    valorCelda(ws, 97 + cont, 8, b.getUsSalubridad());
//                    valorCelda(ws, 97 + cont, 9, b.getUsIndustrial());
//                    valorCelda(ws, 97 + cont, 10, b.getUsMixtoResidencial());
//                    valorCelda(ws, 97 + cont, 11, b.getUsMixtoComercial());
//                    valorCelda(ws, 97 + cont, 12, b.getUsCultural());
//
//                    cont++;
//                }
//            }
//
//            valorCelda(ws, 97, 17, predio.getTerreno().getLocalizacion());
//            valorCelda(ws, 99, 17, predio.getTerreno().getTopografia());
//
//            valorCelda(ws, 104, 9, "N: " + predio.getTerreno().getLindero().getLinNorteRefSis());
//            valorCelda(ws, 104, 13, predio.getTerreno().getLindero().getLinNorteLongitudSis() != null ? predio.getTerreno().getLindero().getLinNorteLongitudSis() : "");
//
//            valorCelda(ws, 105, 9, "S: " + predio.getTerreno().getLindero().getLinSurRefSis());
//            valorCelda(ws, 105, 13, predio.getTerreno().getLindero().getLinSurLongitudSis() != null ? predio.getTerreno().getLindero().getLinSurLongitudSis() : "");
//
//            valorCelda(ws, 106, 9, "E: " + predio.getTerreno().getLindero().getLinEsteRefSis());
//            valorCelda(ws, 106, 13, predio.getTerreno().getLindero().getLinEsteLongitudSis() != null ? predio.getTerreno().getLindero().getLinEsteLongitudSis() : "");
//
//            valorCelda(ws, 107, 9, "O: " + predio.getTerreno().getLindero().getLinOesteRefSis());
//            valorCelda(ws, 107, 13, predio.getTerreno().getLindero().getLinOesteLongitudSis() != null ? predio.getTerreno().getLindero().getLinOesteLongitudSis() : "");
//
//            valorCelda(ws, 108, 13, predio.getTerreno().getAreaLevantamiento());
//
//            valorCelda(ws, 102, 24, variablesService.obtenerValor("cat_terreno", "inf_agua_potable", predio.getTerreno().getInfAguaPotable()));
//            valorCelda(ws, 103, 24, variablesService.obtenerValor("cat_terreno", "inf_energia_electrica", predio.getTerreno().getInfEnergiaElectrica()));
//            valorCelda(ws, 104, 24, variablesService.obtenerValor("cat_terreno", "inf_alcantarillado_sanitario", predio.getTerreno().getInfAlcantarilladoSanitario()));
//            valorCelda(ws, 105, 24, variablesService.obtenerValor("cat_terreno", "inf_alcantarillado_pluvial", predio.getTerreno().getInfAlcantarilladoPluvial()));
//            valorCelda(ws, 106, 24, variablesService.obtenerValor("cat_terreno", "inf_transporte_publico", predio.getTerreno().getInfTransportePublico()));
//            valorCelda(ws, 107, 24, variablesService.obtenerValor("cat_terreno", "inf_red_telefonica", predio.getTerreno().getInfRedTelefonica()));
//            valorCelda(ws, 108, 24, variablesService.obtenerValor("cat_terreno", "inf_recoleccion_basura", predio.getTerreno().getInfRecoleccionBasura()));
//            valorCelda(ws, 109, 24, variablesService.obtenerValor("cat_terreno", "inf_aseo_calles", predio.getTerreno().getInfAseoCalles()));
//            valorCelda(ws, 110, 24, variablesService.obtenerValor("cat_terreno", "inf_acera_bordillos", predio.getTerreno().getInfAceraBordillos()));
//
//            valorCelda(ws, 104, 35, predio.getTerreno().getInfVias() == 4 ? "X" : "");
//            valorCelda(ws, 105, 35, predio.getTerreno().getInfVias() == 3 ? "X" : "");
//            valorCelda(ws, 106, 35, predio.getTerreno().getInfVias() == 2 ? "X" : "");
//            valorCelda(ws, 107, 35, predio.getTerreno().getInfVias() == 1 ? "X" : "");
//            valorCelda(ws, 108, 35, predio.getTerreno().getInfVias() == 0 ? "X" : "");
//
//            log.error("Listo.");
//            // Escribir archivo Excel con datos del predio
//
//            String fichasDir = config.getDbConfiguration().getString("directorio_fichas");
//            nombreFicha = fichasDir + "/fp-" + predio.getClaveCatastral() + "-" + anio + ".xlsx";
//
//            FileOutputStream output = new FileOutputStream(new File(nombreFicha));
//            wbw.write(output);
//
//            descargaFicha();
//        } catch (FileNotFoundException ex) {
//            JsfUtil.addErrorMessage(ex.getMessage());
//        } catch (IOException ex) {
//            JsfUtil.addErrorMessage(ex.getMessage());
//        }
//
//    }
//
//    public void descargaFicha() throws IOException {
//
//        log.error("Comienzo de descarga");
//
//        if (existeFicha()) {
//
//            log.error("archivo encontrado: " + nombreFicha);
//            FacesContext faces = FacesContext.getCurrentInstance();
//            ExternalContext externalContext = faces.getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
//
//            response.setContentType("application/vnd.ms-excel");
//            //aqui seleccionas el nombre con el cual el usuario lo recibira
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + "fp-" + predio.getClaveCatastral() + "-" + getAnioEmision() + ".xlsx" + "\"");
//            //leemos el archivo para mandarselo al cliente
//
//            InputStream in;
//            try {
//                in = new FileInputStream(nombreFicha);
//                ServletOutputStream outs = response.getOutputStream();
//
//                int bit = 256;
//                int i = 0;
//                try {
//                    while ((bit) >= 0) {
//                        bit = in.read();
//                        outs.write(bit);
//                    }
//                    //System.out.println("" +bit);
//                } catch (IOException ioe) {
//                    ioe.printStackTrace(System.out);
//                }
//                outs.flush();
//                outs.close();
//                in.close();
//
//                faces.responseComplete();
//
//                log.error("envio completo");
//
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(GenerarFichaController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    public boolean existeFicha() {
//
//        if (predio != null) {
//
//            return existeArchivo(nombreFicha);
//        }
//
//        return false;
//    }
//
//    public boolean existeArchivo(String nombreArchivo) {
//        if (!nombreArchivo.equals("")) {
//
//            File archivo = new File(nombreArchivo);
//
//            if (archivo.exists()) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public void cancelar() {
//        log.error("Cancelar");
//    }
//
//    private BufferedImage resizeImage(BufferedImage originalImage, int type, int w, int h) {
////        int w = 240, h = 180;
//
//        BufferedImage resizedImage = new BufferedImage(w, h, type);
//        Graphics2D g = resizedImage.createGraphics();
//        g.drawImage(originalImage, 0, 0, w, h, null);
//        g.dispose();
//
//        return resizedImage;
//    }
//
//    public void valorCelda(XSSFSheet ws, int y, int x, Object value) {
//        Cell cell = ws.getRow(y).getCell(x);
//        if (value != null) {
//            if (value instanceof Float) {
//                cell.setCellValue(String.format("%.2f", value));
//            } else {
//                cell.setCellValue(value.toString());
//            }
////            log.error(y + "," + x + " -> " + value.toString());
//        } else {
//            cell.setCellValue("");
////            log.error(y + "," + x + " -> ");
//        }
//    }
//
//    @PersistenceContext(unitName = "dusatecorp_catastro")
//    EntityManager em;
//
//    public void calculoDeAvaluo() {
//
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//
//        String clave = request.getParameter("buscarForm:clave-input");
//
//        try {
//
//            predio = predioService.findByNamedQuery("Predio.findClaveCatastral", clave).get(0);
//
//            if (predio == null) {
//
//                log.error("Predio no encontrado!!!");
//                init();
//
//            } else {
//
//                Object[] params = new Object[2];
//                params[0] = predio.getId();
//                params[1] = (short) 2016;
//
//                List<ImpuestoPredial> ips = impuestoService.findByNamedQuery("ImpuestoPredial.findByPredioAnio", params);
//                if (!ips.isEmpty()) {
//                    impuestoService.remove(ips.get(0));
//                }
//
//                log.error(" ");
//                log.error("Avaluos del predio << " + clave + " >>");
//                log.error(" ");
//                avaluos.calcularImpuestoPredial(predio, (short) 2016);
//                log.error(" ");
//                log.error("Fin del Cálculo");
//            }
//
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, e.getMessage());
//        }
//
//    }
//
//    public boolean porEmitir(short anio) {
//
//        Emision e = emisionService.findByNamedQuery("Emision.findByAnio", (int) anio).get(0);
//        return e.getEmitido();
//    }
//
//    public void setCantidadPredios(int cantidad) {
//        cantidadPredios = cantidad;
//    }
//
//    public int getCantidadPredios() {
//        return cantidadPredios;
//    }
//
//    public void generaEmision() {
//
//        log.error("Inicio de emision..." + (new Date()));
//
//        short anio = getAnioEmision();
//
//        List<Integer> ids = impuestoService.getPrediosSinPreemitir(anio);
////        List<Integer> ids = predioService.getIDs(1);
//        Predio p;
//
//        log.error("Ids capturados...");
//
//        int cant = ids.size(), cont = 0;
//
//        // predio de uno en uno
//        for (Integer pid : ids) {
//
//            p = predioService.findByNamedQuery("Predio.findById", pid).get(0);
//
//            if (!predioService.getEsFichaMadre(p)) {
//                cont++;
//                log.error("Predio: " + pid + " (" + cont + "/" + cant + ")");
//
//                avaluos.calcularImpuestoPredial(p, anio);
//                prediosProcesados = cont;
//
////                RequestContext.getCurrentInstance().update("textoProgreso");
////                RequestContext.getCurrentInstance().update("pbAjax");
//            }
//        }
//
////        Emision e = emisionService.findByNamedQuery("Emision.findByAnio", anio).get(0);
////
////        e.setEmitido(true);
////        emisionService.edit(e);
//        log.error("Fin de emision..." + (new Date()));
//    }
//
//    public void generaEmisionAux() {
//
//        log.error("Inicio de emision...");
//
//        List<Integer> ids = predioService.getIDs(1);
//        Predio p;
//
//        log.error("Ids capturados...");
//
//        short anio = getAnioEmision();
//
//        int cant = ids.size(), cont = 0;
//
//        // predio de uno en uno
//        for (Integer pid : ids) {
//
//            if (pid > 9641) {
//                p = predioService.findByNamedQuery("Predio.findById", pid).get(0);
//
//                if (!predioService.getEsFichaMadre(p)) {
//                    cont++;
//                    log.error("Predio: " + pid + " (" + cont + "/" + cant + ")");
//
//                    avaluos.calcularImpuestoPredial(p, anio);
//                    prediosProcesados = cont;
//
//                }
//            } else {
//                cont++;
//            }
//        }
//    }
//
//    public void confirmaEmision() {
//
//        log.error("Confirmacion de emision para el " + anioEmision);
//
//        List<ImpuestoPredial> impuestos = impuestoService.findByNamedQuery("ImpuestoPredial.findAllToRelease", anioEmision);
//
//        log.error("Impuestos a emitir: " + impuestos.size());
//
//        List<Contribuyente> propietarios;
//        String contribuyentes, identificaciones;
//        Deuda deuda;
//
//        for (ImpuestoPredial i : impuestos) {
//
//            log.error("Documento: " + i.getDocumento());
//
//            propietarios = predioService.findByNamedQuery("Predio.findByClaveCatastral", i.getClaveCatastral()).get(0).getPropietarios();
//
//            contribuyentes = "";
//            identificaciones = "";
//
//            for (Contribuyente c : propietarios) {
//                contribuyentes += "," + c.getApellidoPaterno() + " " + c.getApellidoMaterno() + " " + c.getNombre();
//                identificaciones += "," + c.getIdentificacion();
//            }
//            contribuyentes = !contribuyentes.isEmpty() ? contribuyentes.substring(1) : contribuyentes;
//            identificaciones = !identificaciones.isEmpty() ? identificaciones.substring(1) : identificaciones;
//
//            deuda = new Deuda();
//
//            deuda.setDocumento(i.getDocumento());
//            deuda.setDocAnio(anioEmision);
//            deuda.setClaveCatastral(i.getClaveCatastral());
//            deuda.setConcepto("IMPUESTO PREDIAL URBANO");
//            deuda.setContribuyente(contribuyentes);
//            deuda.setIdentificacion(identificaciones);
//            deuda.setEstado("NO PAGADO");
//            deuda.setFechaIngreso(new Date());
//            deuda.setSubtotal(i.getImpuestoPredial());
//            deuda.setVtc(variablesService.obtenerCoeficiente("datos_configuracion", "valor_titulo_credito", anioEmision));
//            deuda.setSaldo(i.getImpuestoPredial() + variablesService.obtenerCoeficiente("datos_configuracion", "valor_titulo_credito", anioEmision));
//
//            deudaService.create(deuda);
//        }
//
//        impuestoService.confirmaEmision((short) anioEmision);
//    }
//
//    public BloqueService getBloqueService() {
//        return bloqueService;
//    }
//
//    public void setBloqueService(BloqueService bloqueService) {
//        this.bloqueService = bloqueService;
//    }
//
//    public RecargoService getRecargoService() {
//        return recargoService;
//    }
//
//    public void setRecargoService(RecargoService recargoService) {
//        this.recargoService = recargoService;
//    }
//
//    public RazonRecargoService getRazonRecargoService() {
//        return razonRecargoService;
//    }
//
//    public void setRazonRecargoService(RazonRecargoService razonRecargoService) {
//        this.razonRecargoService = razonRecargoService;
//    }
//
//    public ExencionService getExencionService() {
//        return exencionService;
//    }
//
//    public void setExencionService(ExencionService exencionService) {
//        this.exencionService = exencionService;
//    }
//
//    public short getAnioEmision() {
//
//        if (anioEmision == 0) {
//            anioEmision = (short) variablesService.obtenerCoeficiente("datos_configuracion", "anio_emision", (short) 0);
//        }
//
//        return anioEmision;
//    }
//
//    public void setAnioEmision(short anioEmision) {
//        this.anioEmision = anioEmision;
//    }
//
//    public List<Object> getImpuestosEmitidos() {
//        impuestosEmitidos = impuestoService.getImpuestosEmitidos(getAnioEmision());
//        return impuestosEmitidos;
//    }
//
//    public void setImpuestosEmitidos(List<Object> impuestos) {
//        this.impuestosEmitidos = impuestos;
//    }
//
//    public List<Object> getImpuestosPreemitidos() {
//        impuestosPreemitidos = impuestoService.getImpuestosPreemitidos(getAnioEmision());
//        return impuestosPreemitidos;
//    }
//
//    public void setImpuestosNoEmitidos(List<Object> impuestos) {
//        this.impuestosPreemitidos = impuestos;
//    }
//
//    public ImpuestoService getImpuestoService() {
//        return impuestoService;
//    }
//
//    public void setImpuestoService(ImpuestoService impuestoService) {
//        this.impuestoService = impuestoService;
//    }
//
//    public int getPrediosProcesados() {
//
//        float porciento = prediosProcesados * 100 / cantidadPredios;
//
//        return (int) porciento;
//    }
//
//    public String fotoPredio(int idPredio) {
//
//        Predio p = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);
//
//        String foto;
//
//        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//
//        if (!p.getArchivos().isEmpty()) {
//
//            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
//
//            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
//
//            foto = p.getArchivos().get(0).getRuta();
//
//            try {
//                if (existeArchivo(uploadDir + "/" + foto)) {
//                    FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
//                    FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);
//
//                    try {
//                        IOUtils.copy(inFile, outFile);
//                        inFile.close();
//                        outFile.close();
//                    } catch (IOException ex) {
//                        Logger.getLogger(GenerarFichaController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//                if (existeArchivo(tempDir + "/" + foto)) {
//                    return urlBase + "/fotos/" + foto;
//                } else {
//                    return urlBase + "/fotos/nofoto.jpg";
//                }
//
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(GenerarFichaController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return urlBase + "/fotos/nofoto.jpg";
//    }
//
//    public List<Contribuyente> propietariosPredio(int idPredio) {
//
//        Predio p = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);
//
//        return p.getPropietarios();
//    }
//
//    public String direccionPredio(int idPredio) {
//
//        Predio p = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);
//
//        String dir = p.getDireccion();
//
//        if (!p.getEdificio().isEmpty()) {
//            dir += "  Edif: " + p.getEdificio();
//        }
//        if (p.getDepartamento() != null && !"".equals(p.getDepartamento())) {
//            dir += "  Dpto: " + p.getDepartamento();
//        }
//
//        if (p.getBarrio() != null && !"".equals(p.getBarrio())) {
//            dir += "  Barrio: " + p.getBarrio();
//        }
//        if (p.getSector() != null && !"".equals(p.getSector())) {
//            dir += "  Sector: " + p.getSector();
//        }
//        if (p.getManzana() != null && !"".equals(p.getManzana())) {
//            dir += "  Manzana: " + p.getManzana();
//        }
//        if (p.getSolar() != null && !"".equals(p.getSolar())) {
//            dir += "  Solar: " + p.getSolar();
//        }
//
//        return dir;
//    }
//
//    public long totalPreEmitidos() {
//
//        short anio = getAnioEmision();
//
//        return impuestoService.totalPreEmitidosPorAnnio(anio);
//    }
//
//    public long totalEmitidos() {
//        short anio = getAnioEmision();
//
//        return impuestoService.totalEmitidosPorAnnio(anio);
//    }
//
//    public float recaudacionTotal(short anio) {
//
//        return (float) impuestoService.recaudacionTotal(anio);
//    }
//
//    public float recaudacionEmitida(short anio) {
//
//        return (float) impuestoService.recaudacionEmitida(anio);
//    }
//
//    public float recaudacionSinEmitir(short anio) {
//
//        return (float) impuestoService.recaudacionSinEmitir(anio);
//    }
//
//    public void migrarImpuestos2015(String claveRef) {
//
//        log.error("Migrando impuestos 2015...");
//
//        List<DeudasOriginales> deudasOriginales;
//
//        if (!"".equals(claveRef)) {
//            claveRef = "%" + claveRef;
//            deudasOriginales = deudasOriginalesService.findByNamedQuery("DeudasOriginales.findByClaveCatastralParecida", claveRef);
//        } else {
//            deudasOriginales = deudasOriginalesService.findAll();
//        }
//
//        List<Predio> predios;
//        Predio predio;
//        List<ImpuestoPredial> impuestos;
//        ImpuestoPredial impuesto;
//
//        String[] claveOrg;
//        String clave;
//
//        Object[] params = new Object[2];
//
//        long cont = 0;
//
//        float coef_cem;
//        float coef_rpj = variablesService.obtenerCoeficiente("datos_configuracion", "recargo_personas_juridicas", (short) 2016);
//        float coef_basura = variablesService.obtenerCoeficiente("datos_configuracion", "recoleccion_basura", (short) 2016);
//
//        float subtotal, cem, rpj, basura;
//
//        for (DeudasOriginales dorg : deudasOriginales) {
//
//            claveOrg = dorg.getClaveCatastral().trim().split("-");
//
//            if (claveOrg.length == 6) {
//                clave = claveOrg[1] + "-" + claveOrg[2] + "-" + claveOrg[3] + "-" + claveOrg[4] + "-" + claveOrg[5];
//            } else {
//                clave = dorg.getClaveCatastral();
//            }
//
//            predios = predioService.findByNamedQuery("Predio.findClaveCatastral", clave);
//
//            if (!predios.isEmpty()) {
//
//                log.error("Cont: " + cont + " - ClaveOrg: " + dorg.getClaveCatastral() + " - Clave nuestra: " + clave);
//
//                predio = predios.get(0);
//
//                coef_cem = predio.getTerreno().getManzana().getSector().getCem();
//
//                params[0] = predio.getId();
//                params[1] = (short) 2015;
//
//                impuestos = impuestoService.findByNamedQuery("ImpuestoPredial.findByPredioAnio", params);
//
//                if (!impuestos.isEmpty()) {
//                    impuesto = impuestos.get(0);
//
//                    subtotal = Float.valueOf(dorg.getSubtotal());
//                    cem = subtotal * coef_cem / 1000;
//                    rpj = (subtotal * coef_rpj / 1000);
//                    basura = subtotal * coef_basura / 1000;
//                    impuesto.setImpuestoPredial(subtotal);
//                    impuesto.setCem(cem);
//                    impuesto.setRecargoPersonasJuridicas(cem);
//                    impuesto.setRecoleccionBasura(basura);
//                    impuesto.setRecargoPersonasJuridicas(rpj);
//                    impuestoService.edit(impuesto);
//                }
//            }
//
//            cont++;
//        }
//
//        log.error("Migrado.");
//
//    }
//
//    public void exportarPDF() throws JRException, IOException {
//
//        Subject subject = SecurityUtils.getSubject();
//        String user = subject.getPrincipal().toString();
//
//        Map<String, Object> parameter = new HashMap<String, Object>();
//        parameter.put("periodo", "" + getAnioEmision());
//
//        Calendar now = Calendar.getInstance();
//        int year = now.get(Calendar.YEAR);
//        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
//        int day = now.get(Calendar.DAY_OF_MONTH);
//        int hour = now.get(Calendar.HOUR_OF_DAY);
//        int minute = now.get(Calendar.MINUTE);
//        int second = now.get(Calendar.SECOND);
//        String fecha = "" + day + "/" + month + "/" + year;
//        String impresion = "" + fecha + " | " + hour + ":" + minute + ":" + second + " | " + user;
//        parameter.put("impresion", impresion);
//
//        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/emisionFinal.jasper"));
//
//        log.error("nombre del reporte: " + jasper.getName());
//
//        Object[] params = {true, getAnioEmision()};
//        List<ImpuestoPredial> listado = impuestoService.findByNamedQuery("ImpuestoPredial.EmitidosPorAnnio", params);
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameter, new JRBeanCollectionDataSource(listado));
//
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//
//        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
//        //response.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
//        response.addHeader("Content-Disposition", "attachment; filename=\"" + fecha + " emision-definitiva" + ".pdf" + "\"");
//        try (ServletOutputStream stream = response.getOutputStream()) {
//            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
//
//            stream.flush();
//        }
//        FacesContext.getCurrentInstance().responseComplete();
//    }
//
//    public byte[] generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2) throws JRException {
//        //throw the JasperPrint Objects in a list
//        List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
//        jasperPrintList.add(jasperPrint1);
//        jasperPrintList.add(jasperPrint2);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        JRPdfExporter exporter = new JRPdfExporter();
//        //Add the list as a Parameter
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
//        //this will make a bookmark in the exported PDF for each of the reports
//        exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
//        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//        exporter.exportReport();
//        return baos.toByteArray();
//    }
//
//    public List<InfoBloque> creaListaBloques(Predio predio) {
//
//        List<InfoBloque> lista = new ArrayList<>();
//
//        if (predio.getBloqueList().isEmpty()) {
//            return lista;
//        }
//
//        InfoBloque datosBloque;
//        List<Bloque> bloques = predio.getBloqueList();
//        log.error("Cantidad de bloques: " + bloques.size());
//        for (Bloque b : bloques) {
//            log.error("Nro Bloque: " + b.getNumeroBloque());
//            datosBloque = new InfoBloque(
//                    b.getNumeroBloque(),
//                    b.getListaPisos().size(),
//                    b.getAreaTotal().floatValue(),
//                    variablesService.obtenerValor("cat_bloque", "tipo_construccion", b.getTipoConstruccion()),
//                    variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", b.getTipoConstruccion()),
//                    b.getPorcientoConstruccion().floatValue(),
//                    b.getPorcientoConservacion().floatValue(),
//                    avaluos.avaluoBloque(b)
//            );
//
//            lista.add(datosBloque);
//        }
//
//        return lista;
//    }
//
//    public List<InfoInfraestructura> creaListaInfraestructura(Predio predio) {
//
//        List<InfoInfraestructura> lista = new ArrayList<>();
//
//        InfoInfraestructura datosInfra;
//        ValorDiscreto valorDiscreto;
//        float acum = 0;
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_vias", predio.getTerreno().getInfVias());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Vías", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_agua_potable", predio.getTerreno().getInfAguaPotable());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Agua potable", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_alcantarillado_sanitario", predio.getTerreno().getInfAlcantarilladoSanitario());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Alcantarillado sanitario", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_alcantarillado_pluvial", predio.getTerreno().getInfAlcantarilladoPluvial());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Alcantarillado pluvial", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_energia_electrica", predio.getTerreno().getInfEnergiaElectrica());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Energía eléctrica", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_red_telefonica", predio.getTerreno().getInfRedTelefonica());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Red telefónica", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_transporte_publico", predio.getTerreno().getInfTransportePublico());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Transporte público", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_acera_bordillos", predio.getTerreno().getInfAceraBordillos());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Aceras y bordillos", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "localizacion", predio.getTerreno().getLocalizacion());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Localización", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "topografia", predio.getTerreno().getTopografia());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Topografía", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_recoleccion_basura", predio.getTerreno().getInfRecoleccionBasura());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Recolección de basura", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variablesService.obtenerValores("cat_terreno", "inf_aseo_calles", predio.getTerreno().getInfAseoCalles());
//        acum += valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Aseo de calles", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        datosInfra = new InfoInfraestructura("Promedio de ajuste", " ", acum / 12);
//        lista.add(datosInfra);
//
//        datosInfra = new InfoInfraestructura("Valor de Zona Homogénea", "USD", predio.getTerreno().getManzana().getZonaHomogenea().getValor());
//        lista.add(datosInfra);
//
//        return lista;
//    }
//
//    public List<String> fotosPredio(Predio predio) {
//
//        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//
//        String foto;
//        String pathToPhoto;
//        List<String> images = new ArrayList<>();
//
//        if (!predio.getArchivos().isEmpty()) {
//
//            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
//
//            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
//
//            for (Archivo img : predio.getArchivos()) {
//
//                foto = img.getRuta();
//
//                try {
//                    if (existeArchivo(uploadDir + "/" + foto)) {
//                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
//                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);
//
//                        try {
//                            IOUtils.copy(inFile, outFile);
//                            inFile.close();
//                            outFile.close();
//                        } catch (IOException ex) {
//                            log.error(ex.getMessage());
//                        }
//                    }
//
//                    if (existeArchivo(tempDir + "/" + foto)) {
//                        pathToPhoto = urlBase + "/fotos/" + foto;
//                        if (!images.contains(pathToPhoto)) {
//                            images.add(pathToPhoto);
//                        }
//                    } else {
//                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
//                        if (!images.contains(pathToPhoto)) {
//                            images.add(pathToPhoto);
//                        }
//                    }
//
//                } catch (FileNotFoundException ex) {
//
//                }
//            }
//
//        }
//
//        return images;
//    }
//
//    public void recalcularExenciones() {
//
//        log.error("Inicio de operaciones.");
//
//        List<Integer> ids = exencionService.getIDsPredios();
//
//        log.error("Predios: " + ids.size());
//
//        float avaluo, result;
//
//        Predio p;
//
//        for (Integer pid : ids) {
//
//            p = predioService.findByNamedQuery("Predio.findById", pid).get(0);
//
//            avaluo = avaluosService.avaluoPredio(p, 2016);
//
//            result = 0;
//            for (Exencion e : p.getExencionList()) {
//                switch (e.getRazonExencion().getTipoExencion().getId()) {
//                    case 1:
//                    case 2:
//                    case 3:
//                    case 5:
//                    case 6:
//                    case 7:
//                    case 8:
//                    case 9:
//                    case 10:
//                    case 11:
//                    case 15:
//                    case 17:
//                    case 18: {
//                        result = e.getPorcentaje() * avaluo / 100;
//                        break;
//                    }
//                    case 12: {
//                        result = e.getValor();
//                        break;
//                    }
//                    case 13:
//                    case 14: {
//                        result = 0;
//                        break;
//                    }
//                    case 16: {
//                        result = avaluosService.avaluoTerreno(p);
//                        break;
//                    }
//                }
//                log.error("Pid: " + pid + "ID: " + e.getId() + "  Razon: " + e.getRazonExencion().getTipoExencion().getId()
//                        + "  Porciento: " + e.getPorcentaje() + "  Avalúo: " + avaluo + "  Valor: " + result + "  Valor org: " + e.getValor());
//
//                e.setValor(result);
//
//                exencionService.edit(e);
//            }
//        }
//    }

}
