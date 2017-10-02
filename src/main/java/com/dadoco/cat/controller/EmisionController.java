/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.audit.service.AuditService;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.EmisionService;
import com.dadoco.cat.service.ImpuestoService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@ManagedBean
@ViewScoped
@Named(value = "emisionView")
public class EmisionController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger("");

    @EJB
    private ConfigReader config;
    @EJB
    private EmisionService emisionService;
    @EJB
    private ImpuestoService impuestoService;
    @EJB
    private PredioService predioService;
    @EJB
    private AvaluosService avaluosService;
    @EJB
    private VariableService variableService;
    @EJB
    private DeudaService deudaService;
    @EJB
    private AuditService auditServices;
    
    private Subject subject;
    private List<Object> impuestosEmitidos;
    private List<Object> impuestosPreemitidos;

    private short anioEmision = 0;

    private Object[] resumenEmision;
    private Object[] resumenAnterior;

    private boolean preemisionCompleta;

    private long emisionTotalPredios, emisionPreemitidos, emisionEmitidos, emisionSinEmitir;
    private BigDecimal emisionRecaudacionTotal, emisionRecaudacionEmitida, emisionRecaudacionSinEmitir;
    private long emisionAnteriorTotalPredios, emisionAnteriorPreemitidos, emisionAnteriorEmitidos, emisionAnteriorSinEmitir;
    private BigDecimal emisionAnteriorRecaudacionTotal, emisionAnteriorRecaudacionEmitida, emisionAnteriorRecaudacionSinEmitir;

    private BigDecimal avaluoTerreno, avaluoContruccion, avaluoComplementarias, avaluoComercial, impuestoPredial, recoleccionBasura, recargoPersJurid, cem, recargoNoEdif;

    private int prediosProcesados;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;

    @PostConstruct
    public void init() {
        subject = SecurityUtils.getSubject();
        impuestosPreemitidos = new ArrayList<>();

        anioEmision = (short)(Util.ANIO_ACTUAL.shortValue());

        resumenEmision = impuestoService.getResumenEmision(anioEmision);

        emisionTotalPredios = 0;
        emisionPreemitidos = 0;
        emisionEmitidos = 0;
        emisionSinEmitir = 0;

        emisionRecaudacionTotal = BigDecimal.ZERO;
        emisionRecaudacionEmitida = BigDecimal.ZERO;
        emisionRecaudacionSinEmitir = BigDecimal.ZERO;

        if (resumenEmision != null) {
            emisionTotalPredios = (int) resumenEmision[1];
            emisionPreemitidos = (int) resumenEmision[2];
            emisionEmitidos = (int) resumenEmision[3];
            emisionSinEmitir = (int) resumenEmision[4];

            emisionRecaudacionTotal = (BigDecimal) resumenEmision[5];
            emisionRecaudacionEmitida = (BigDecimal) resumenEmision[6];
            emisionRecaudacionSinEmitir = (BigDecimal) resumenEmision[7];

            avaluoTerreno = (BigDecimal) resumenEmision[8];
            avaluoContruccion = (BigDecimal) resumenEmision[9];
            avaluoComplementarias = (BigDecimal) resumenEmision[10];
            avaluoComercial = (BigDecimal) resumenEmision[11];
            impuestoPredial = (BigDecimal) resumenEmision[12];
            recoleccionBasura = (BigDecimal) resumenEmision[13];
            recargoPersJurid = (BigDecimal) resumenEmision[14];
            cem = (BigDecimal) resumenEmision[15];
            recargoNoEdif = (BigDecimal) resumenEmision[16];
        }

        resumenAnterior = impuestoService.getResumenEmision((short) (anioEmision - 1));

        emisionAnteriorTotalPredios = 0;
        emisionAnteriorPreemitidos = 0;
        emisionAnteriorEmitidos = 0;
        emisionAnteriorSinEmitir = 0;

        emisionAnteriorRecaudacionTotal = BigDecimal.ZERO;
        emisionAnteriorRecaudacionEmitida = BigDecimal.ZERO;
        emisionAnteriorRecaudacionSinEmitir = BigDecimal.ZERO;

        if (resumenAnterior != null) {
            emisionAnteriorTotalPredios = (int) resumenAnterior[1];
            emisionAnteriorPreemitidos = (int) resumenAnterior[2];
            emisionAnteriorEmitidos = (int) resumenAnterior[3];
            emisionAnteriorSinEmitir = (int) resumenAnterior[4];

            emisionAnteriorRecaudacionTotal = (BigDecimal) resumenAnterior[5];
            emisionAnteriorRecaudacionEmitida = (BigDecimal) resumenAnterior[6];
            emisionAnteriorRecaudacionSinEmitir = (BigDecimal) resumenAnterior[7];
        }

        opcionesBusqueda = new OpcionesBusquedaPredio();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);
    }

//<editor-fold defaultstate="collapsed" desc="getter-setter">
    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public BigDecimal getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(BigDecimal avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public BigDecimal getAvaluoContruccion() {
        return avaluoContruccion;
    }

    public void setAvaluoContruccion(BigDecimal avaluoContruccion) {
        this.avaluoContruccion = avaluoContruccion;
    }

    public BigDecimal getAvaluoComplementarias() {
        return avaluoComplementarias;
    }

    public void setAvaluoComplementarias(BigDecimal avaluoComplementarias) {
        this.avaluoComplementarias = avaluoComplementarias;
    }

    public BigDecimal getAvaluoComercial() {
        return avaluoComercial;
    }

    public void setAvaluoComercial(BigDecimal avaluoComercial) {
        this.avaluoComercial = avaluoComercial;
    }

    public BigDecimal getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(BigDecimal impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }

    public BigDecimal getRecoleccionBasura() {
        return recoleccionBasura;
    }

    public void setRecoleccionBasura(BigDecimal recoleccionBasura) {
        this.recoleccionBasura = recoleccionBasura;
    }

    public BigDecimal getRecargoPersJurid() {
        return recargoPersJurid;
    }

    public void setRecargoPersJurid(BigDecimal recargoPersJurid) {
        this.recargoPersJurid = recargoPersJurid;
    }

    public BigDecimal getCem() {
        return cem;
    }

    public void setCem(BigDecimal cem) {
        this.cem = cem;
    }

    public BigDecimal getRecargoNoEdif() {
        return recargoNoEdif;
    }

    public void setRecargoNoEdif(BigDecimal recargoNoEdif) {
        this.recargoNoEdif = recargoNoEdif;
    }

    public int getPrediosProcesados() {
        return prediosProcesados;
    }

    public void setPrediosProcesados(int prediosProcesados) {
        this.prediosProcesados = prediosProcesados;
    }

    public long getEmisionAnteriorTotalPredios() {
        return emisionAnteriorTotalPredios;
    }

    public void setEmisionAnteriorTotalPredios(long emisionAnteriorTotalPredios) {
        this.emisionAnteriorTotalPredios = emisionAnteriorTotalPredios;
    }

    public long getEmisionAnteriorPreemitidos() {
        return emisionAnteriorPreemitidos;
    }

    public void setEmisionAnteriorPreemitidos(long emisionAnteriorPreemitidos) {
        this.emisionAnteriorPreemitidos = emisionAnteriorPreemitidos;
    }

    public long getEmisionAnteriorEmitidos() {
        return emisionAnteriorEmitidos;
    }

    public void setEmisionAnteriorEmitidos(long emisionAnteriorEmitidos) {
        this.emisionAnteriorEmitidos = emisionAnteriorEmitidos;
    }

    public long getEmisionAnteriorSinEmitir() {
        return emisionAnteriorSinEmitir;
    }

    public void setEmisionAnteriorSinEmitir(long emisionAnteriorSinEmitir) {
        this.emisionAnteriorSinEmitir = emisionAnteriorSinEmitir;
    }

    public BigDecimal getEmisionAnteriorRecaudacionTotal() {
        return emisionAnteriorRecaudacionTotal;
    }

    public void setEmisionAnteriorRecaudacionTotal(BigDecimal emisionAnteriorRecaudacionTotal) {
        this.emisionAnteriorRecaudacionTotal = emisionAnteriorRecaudacionTotal;
    }

    public BigDecimal getEmisionAnteriorRecaudacionEmitida() {
        return emisionAnteriorRecaudacionEmitida;
    }

    public void setEmisionAnteriorRecaudacionEmitida(BigDecimal emisionAnteriorRecaudacionEmitida) {
        this.emisionAnteriorRecaudacionEmitida = emisionAnteriorRecaudacionEmitida;
    }

    public BigDecimal getEmisionAnteriorRecaudacionSinEmitir() {
        return emisionAnteriorRecaudacionSinEmitir;
    }

    public void setEmisionAnteriorRecaudacionSinEmitir(BigDecimal emisionAnteriorRecaudacionSinEmitir) {
        this.emisionAnteriorRecaudacionSinEmitir = emisionAnteriorRecaudacionSinEmitir;
    }

    public ImpuestoService getImpuestoService() {
        return impuestoService;
    }

    public void setImpuestoService(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public AvaluosService getAvaluosService() {
        return avaluosService;
    }

    public void setAvaluosService(AvaluosService avaluosService) {
        this.avaluosService = avaluosService;
    }

    public VariableService getVariableService() {
        return variableService;
    }

    public void setVariableService(VariableService variableService) {
        this.variableService = variableService;
    }

    public DeudaService getDeudaService() {
        return deudaService;
    }

    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    public Object[] getResumenEmision() {
        return resumenEmision;
    }

    public void setResumenEmision(Object[] resumenEmision) {
        this.resumenEmision = resumenEmision;
    }

    public Object[] getResumenAnterior() {
        return resumenAnterior;
    }

    public void setResumenAnterior(Object[] resumenAnterior) {
        this.resumenAnterior = resumenAnterior;
    }

    public EmisionService getEmisionService() {
        return emisionService;
    }

    public void setEmisionService(EmisionService emisionService) {
        this.emisionService = emisionService;
    }

    public long getEmisionTotalPredios() {
        return emisionTotalPredios;
    }

    public void setEmisionTotalPredios(long emisionTotalPredios) {
        this.emisionTotalPredios = emisionTotalPredios;
    }

    public long getEmisionPreemitidos() {
        return emisionPreemitidos;
    }

    public void setEmisionPreemitidos(long emisionPreemitidos) {
        this.emisionPreemitidos = emisionPreemitidos;
    }

    public long getEmisionEmitidos() {
        return emisionEmitidos;
    }

    public void setEmisionEmitidos(long emisionEmitidos) {
        this.emisionEmitidos = emisionEmitidos;
    }

    public long getEmisionSinEmitir() {
        return emisionSinEmitir;
    }

    public void setEmisionSinEmitir(long emisionSinEmitir) {
        this.emisionSinEmitir = emisionSinEmitir;
    }

    public BigDecimal getEmisionRecaudacionTotal() {
        return emisionRecaudacionTotal;
    }

    public void setEmisionRecaudacionTotal(BigDecimal emisionRecaudacionTotal) {
        this.emisionRecaudacionTotal = emisionRecaudacionTotal;
    }

    public BigDecimal getEmisionRecaudacionEmitida() {
        return emisionRecaudacionEmitida;
    }

    public void setEmisionRecaudacionEmitida(BigDecimal emisionRecaudacionEmitida) {
        this.emisionRecaudacionEmitida = emisionRecaudacionEmitida;
    }

    public BigDecimal getEmisionRecaudacionSinEmitir() {
        return emisionRecaudacionSinEmitir;
    }

    public void setEmisionRecaudacionSinEmitir(BigDecimal emisionRecaudacionSinEmitir) {
        this.emisionRecaudacionSinEmitir = emisionRecaudacionSinEmitir;
    }

    public boolean isPreemisionCompleta() {
        preemisionCompleta = impuestoService.preemisionCompleta(anioEmision);

        return preemisionCompleta;
    }

    public void setPreemisionCompleta(boolean preemisionCompleta) {
        this.preemisionCompleta = preemisionCompleta;
    }

    public short getAnioEmision() {

        anioEmision = Util.ANIO_ACTUAL.shortValue();

        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

    public List<Object> getImpuestosEmitidos() {
        impuestosEmitidos = impuestoService.getImpuestosEmitidos(getAnioEmision());
        return impuestosEmitidos;
    }

    public void setImpuestosEmitidos(List<Object> impuestos) {
        this.impuestosEmitidos = impuestos;
    }

    public List<Object> getImpuestosPreemitidos() {
        impuestosPreemitidos = impuestoService.getImpuestosPreemitidos(getAnioEmision());
        return impuestosPreemitidos;
    }

    public void setImpuestosNoEmitidos(List<Object> impuestos) {
        this.impuestosPreemitidos = impuestos;
    }

    public void setExcluido(short anio, int idPredio) {
        impuestoService.setExcluido(anio, idPredio);
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
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

//</editor-fold>
    public void buscarPredio() {

        provinciaCod = opcionesBusqueda.getProvinciaCod();
        cantonCod = opcionesBusqueda.getCantonCod();
        parroquiaCod = opcionesBusqueda.getParroquiaCod();
        zonaCod = opcionesBusqueda.getZonaCod();
        sectorCod = opcionesBusqueda.getSectorCod();
        manzanaCod = opcionesBusqueda.getManzanaCod();
        //        try {
        //            predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);
        //
        //            if (predio == null) {
        //                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %2d-%2d-%3d-%2d-%2d no encontrado",
        //                        zonaCod, sectorCod, manzanaCod, solarCod, phCod));
        //                return;
        //            } else {
        //
        //                if (comprobarDeudas(predio)) {
        //                    JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral: %2d-%2d-%3d-%2d-%2d, tiene deuda pendientes con el municipio",
        //                            zonaCod, sectorCod, manzanaCod, solarCod, phCod));
        //
        //                    return;
        //                }
        //                opcionesBusqueda.setEjecutandoAccion(true);
        //                propietarios = predio.getPropietarios();
        //                prediosEnManzana = catastroService.obtenerPrediosDeUnaManzana(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);
        //
        //                if (prediosEnManzana.contains(predio)) {
        //                    prediosEnManzana.remove(predio);
        //                }
        //
        //                Object[] coord = catastroService.coordenadas(predio.getClaveCatastral());
        //                if (coord != null) {
        //                    coord_X = Double.parseDouble(coord[0].toString());
        //                    coord_Y = Double.parseDouble(coord[1].toString());
        //                    claveGeoreferenciada = coord[2].toString();
        //                }
        //
        //            }
        //
        //        } catch (Exception e) {
        //            JsfUtil.addErrorMessage(e, e.getMessage());
        //        }
    }

    public void generaEmision() {

        log.error("Inicio de emision..." + (new Date()));

        if (emisionEmitidos == 0) {
            impuestoService.eliminarEmision(anioEmision);
        }

        List<Integer> ids = impuestoService.getPrediosSinPreemitir(anioEmision);

        ImpuestoPredial ip;


        Predio p;

        log.error("Ids capturados...");

        int cant = ids.size(), cont = 0;

        prediosProcesados = 0;

        RequestContext context = RequestContext.getCurrentInstance();

        // predio de uno en uno
        for (Integer pid : ids) {

            p = predioService.findByNamedQuery("Predio.findById", pid).get(0);
            log.error("Predio: " + p.getClaveCatastral());

            if (!predioService.getEsFichaMadre(p)) {
                cont++;
                ip = avaluosService.calcularImpuestoPredial(p, anioEmision);


            } else {
                log.error("Es ficha madre: " + p.getClaveCatastral());
            }

            prediosProcesados = Math.round(cont * 100 / cant);

            context.update("pbAjax");
        }

        actualizarResumenEmision(anioEmision);

        log.error("Fin de emision..." + (new Date()));
    }

    public void confirmaEmision() {

        log.error("Confirmacion de emision para el " + anioEmision);

        List<Integer> ids = impuestoService.getIdsParaEmitir(anioEmision);

        log.error("Impuestos a emitir: " + ids.size());

        int cant = ids.size(), cont = 0;

        prediosProcesados = 0;

        RequestContext context = RequestContext.getCurrentInstance();

        List<Contribuyente> propietarios;
        String contribuyentes, identificaciones;
        Deuda deuda;

        ImpuestoPredial i;
        Object[] params = new Object[3];

        for (int id : ids) {
            cont++;

            params[0] = id;
            params[1] = anioEmision;
            params[2] = (short) 0;

            i = impuestoService.findByNamedQuery("ImpuestoPredial.findByPredioAnio", params).get(0);

            propietarios = predioService.findByNamedQuery("Predio.findByClaveCatastral", i.getClaveCatastral()).get(0).getPropietarios();

            contribuyentes = "";
            identificaciones = "";

            for (Contribuyente c : propietarios) {
                contribuyentes += "," + c.getApellidoPaterno() + " " + c.getApellidoMaterno() + " " + c.getNombre();
                identificaciones += "," + c.getIdentificacion();
            }
            contribuyentes = !contribuyentes.isEmpty() ? contribuyentes.substring(1) : contribuyentes;
            identificaciones = !identificaciones.isEmpty() ? identificaciones.substring(1) : identificaciones;

            deuda = new Deuda();

            deuda.setDocumento(i.getDocumento());
            deuda.setDocAnio(anioEmision);
            deuda.setClaveCatastral(i.getClaveCatastral());
            deuda.setConcepto("IMPUESTO PREDIAL URBANO");
            deuda.setContribuyente(contribuyentes);
            deuda.setIdentificacion(identificaciones);
            deuda.setEstado("NO PAGADO");
            deuda.setFechaIngreso(new Date());
            //deuda.setSubtotal(i.getTotal());
            deuda.setVtc(variableService.obtenerCoeficiente("datos_configuracion", "valor_titulo_credito", anioEmision, anioEmision));
            //deuda.setSaldo(i.getTotal() + variableService.obtenerCoeficiente("datos_configuracion", "valor_titulo_credito", anioEmision));

            deuda.setBloqueado(deudaRequiereBloqueo(i.getImpuestoPredialPK().getIdPredio()));

            deudaService.create(deuda);

            prediosProcesados = Math.round(cont * 100 / cant);

            log.error("(" + cont + "/" + cant + ") " + i.getClaveCatastral());

            context.update("pbAjax2");
        }

        impuestoService.confirmaEmision((short) anioEmision);

        actualizarResumenEmision(anioEmision);

        prediosProcesados = 100;

        context.update("pbAjax2");
    }

    public boolean deudaRequiereBloqueo(int idPredio) {

        Predio predio = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);

//        List<Exencion> exenciones = predio.getExencionList();
//
//        for (Exencion e : exenciones) {
//            if (e.getRazonExencion().getId() == 11) {
//                return true;
//            }
//        }

        return false;
    }

    public void actualizarResumenEmision(short anio) {

        int totalPredios = impuestoService.totalPredios();
        int totalPreemitidos = impuestoService.totalPreEmitidosPorAnnio(anio);
        int totalEmitidos = impuestoService.totalEmitidosPorAnnio(anio);
        double recaudacionTotal = impuestoService.recaudacionTotal(anio);
        double recaudacionEmitida = impuestoService.recaudacionEmitida(anio);

        impuestoService.actualizarDatosEmision(anio, totalPredios, totalPreemitidos, totalEmitidos, recaudacionTotal, recaudacionEmitida);
    }

    public void exportarPDF(boolean emitido) throws JRException, IOException {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("periodo", "" + getAnioEmision());

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        String fecha = "" + day + "/" + month + "/" + year;
        String impresion = "" + fecha + " | " + hour + ":" + minute + ":" + second + " | " + user;
        parameter.put("impresion", impresion);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/emisionFinal.jasper"));

        String zona = opcionesBusqueda.getZonaCod();
        String sector = opcionesBusqueda.getSectorCod();
        String manzana = opcionesBusqueda.getManzanaCod();

        Object[] params = {true, getAnioEmision()};
        //List<ImpuestoPredial> listado = impuestoService.findByFilter(zona, sector, manzana, emitido);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameter, new JREmptyDataSource());

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        //response.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fecha + " emision-definitiva" + ".pdf" + "\"");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    @Transactional
    public void generaReEmision(String clave) {

        Predio p = predioService.findByNamedQuery("Predio.findByClaveCatastral", clave).get(0);

        avaluosService.actualizarImpuestoPredial(p, anioEmision);

        int totalPredios = impuestoService.totalPredios();
        int totalPreemitidos = impuestoService.totalPreEmitidosPorAnnio(anioEmision);
        int totalEmitidos = impuestoService.totalEmitidosPorAnnio(anioEmision);
        double recaudacionTotal = Math.round(impuestoService.recaudacionTotal(anioEmision) * 100) / 100;
        double recaudacionEmitida = Math.round(impuestoService.recaudacionEmitida(anioEmision) * 100) / 100;

        impuestoService.actualizarDatosEmision(anioEmision, totalPredios, totalPreemitidos, totalEmitidos, recaudacionTotal, recaudacionEmitida);
        auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "GENERACIÓN DE EMISIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
    }

    public JasperPrint generarPreListado(int offset) throws JRException {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        List<ImpuestoPredial> listado = impuestoService.obtenerImpuesto(anioEmision, offset);

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("periodo", "" + getAnioEmision());

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        String fecha = "" + day + "/" + month + "/" + year;
        String impresion = "" + fecha + " | " + hour + ":" + minute + ":" + second + " | " + user;
        parameter.put("impresion", impresion);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/emisionFinal.jasper"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameter, new JRBeanCollectionDataSource(listado));

        return jasperPrint;
    }

    public void imprimirMultiplesPaginas() throws IOException, JRException {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        List<ImpuestoPredial> listado = impuestoService.obtenerImpuesto(anioEmision, 0);

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("periodo", "" + getAnioEmision());

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        String fecha = "" + day + "/" + month + "/" + year;
        String impresion = "" + fecha + " | " + hour + ":" + minute + ":" + second + " | " + user;

        Object[] resumen = impuestoService.obtenerResumenEmision(anioEmision);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        DecimalFormat anotherDFormat = (DecimalFormat) numberFormat;
        anotherDFormat.applyPattern("#.00");
        anotherDFormat.setGroupingUsed(true);
        anotherDFormat.setGroupingSize(3);

        parameter.put("avaluoTerreno", "" + anotherDFormat.format(new Double(resumen[0].toString())));
        parameter.put("avaluoConst", "" + anotherDFormat.format(new Double(resumen[1].toString())));
        parameter.put("avaluoCompl", "" + anotherDFormat.format(new Double(resumen[2].toString())));
        parameter.put("avaluoComercial", "" + anotherDFormat.format(new Double(resumen[3].toString())));
        parameter.put("impuestoPredial", "" + anotherDFormat.format(new Double(resumen[4].toString())));
        parameter.put("basura", "" + anotherDFormat.format(new Double(resumen[5].toString())));
        parameter.put("rpj", "" + anotherDFormat.format(new Double(resumen[6].toString())));
        parameter.put("cem", "" + anotherDFormat.format(new Double(resumen[7].toString())));
        parameter.put("noEdificado", "" + anotherDFormat.format(new Double(resumen[8].toString())));
        parameter.put("total", "" + anotherDFormat.format(new Double(resumen[9].toString())));
        JasperPrint jasperPrint = null;
        JasperPrint report;

        int total = impuestoService.totalEmitidosPorAnnio(anioEmision);

        for (int i = 0; i < total; i += 10000) {
            report = generarPreListado(i);

            if (jasperPrint == null) {
                jasperPrint = report;
            } else {
                for (JRPrintPage page : report.getPages()) {
                    jasperPrint.addPage(page);
                }
            }
            log.error("Entro al paquete: " + i);
        }
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/emisionFinalResumen.jasper"));

        JasperPrint jasperPrintResumen = JasperFillManager.fillReport(jasper.getPath(), parameter, new JREmptyDataSource());

        for (JRPrintPage page : jasperPrintResumen.getPages()) {
            jasperPrint.addPage(page);
        }

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fecha + " emision-definitiva" + ".pdf" + "\"");

        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
        }

        FacesContext.getCurrentInstance().responseComplete();
    }

    public void excelExport() throws IOException {

        String[] titles = {"Parroquia", "Zona", "Sector", "Manzana", "Solar", "PH", "Contribuyentes", "Título de credito", "Código anterior", "Av. Terreno", "Av. Construcción", "Av. Complementarias",
            "Av. Comercial", "Impuesto predial", "Basura", "RPJ", "CEM", "No edificado", "Total"};

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "-preemision" + "" + ".xlsx" + "\"");
        XSSFWorkbook wbw = new XSSFWorkbook();
        XSSFSheet sheet = wbw.createSheet("Impuestos");

        DataFormat format = wbw.createDataFormat();

        sheet.autoSizeColumn((short) 2);

        Map<String, CellStyle> styles = createStyles(wbw);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < 19; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        String parroquia = opcionesBusqueda.getParroquiaCod();
        String zona = opcionesBusqueda.getZonaCod();
        String sector = opcionesBusqueda.getSectorCod();
        String manzana = opcionesBusqueda.getManzanaCod();

        //Object[] params = {true, getAnioEmision()};
        List<ImpuestoPredial> listado = impuestoService.findByFilter(parroquia, zona, sector, manzana, false, anioEmision);

        sheet.setColumnWidth(6, 17000);
        sheet.setColumnWidth(7, 6000);
        sheet.setColumnWidth(8, 10000);
        sheet.setColumnWidth(9, 4000);
        sheet.setColumnWidth(10, 4000);
        sheet.setColumnWidth(11, 4000);
        sheet.setColumnWidth(12, 4000);
        sheet.setColumnWidth(13, 4000);
        sheet.setColumnWidth(14, 4000);
        sheet.setColumnWidth(15, 4000);
        sheet.setColumnWidth(16, 4000);
        sheet.setColumnWidth(17, 4000);
        sheet.setColumnWidth(18, 4000);
        sheet.setColumnWidth(19, 4000);

        int rownum = 1;
        for (ImpuestoPredial impuesto : listado) {

            Row row = sheet.createRow(rownum++);

            //increase row height to accomodate two lines of text
            int cant = impuesto.getPredio().getPropietarios().size();
            if (cant == 0) {
                cant++;
            }
            row.setHeightInPoints((cant * sheet.getDefaultRowHeightInPoints()));

            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getTerreno().getTerrenoPK().getCodParroquia());

            cell = row.createCell(1);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getTerreno().getTerrenoPK().getCodZona());

            cell = row.createCell(2);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getTerreno().getTerrenoPK().getCodSector());

            cell = row.createCell(3);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getTerreno().getTerrenoPK().getCodManzana());

            cell = row.createCell(4);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getTerreno().getTerrenoPK().getCodSolar());

            cell = row.createCell(5);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + impuesto.getPredio().getPropiedadHorizontal());

            cell = row.createCell(6);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getDuenos());

            cell = row.createCell(7);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getDocumento());

            cell = row.createCell(8);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + impuesto.getPredio().getClaveAnterior());

            cell = row.createCell(9);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getAvaluoTerreno());

            cell = row.createCell(10);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getAvaluoEdificacion());

            cell = row.createCell(11);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getAvaluoComplementarias());

            cell = row.createCell(12);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getAvaluoPredial());

            cell = row.createCell(13);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getImpuestoPredial());

            cell = row.createCell(14);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getRecoleccionBasura());

            cell = row.createCell(15);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getRecargoPersonasJuridicas());

            cell = row.createCell(16);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getCem());

            cell = row.createCell(17);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getSolarNoEdificado());

            cell = row.createCell(18);
            cell.setCellStyle(styles.get("cell-number"));
            cell.setCellValue(impuesto.getTotal());

        }

        wbw.write(externalContext.getResponseOutputStream());
        context.responseComplete();
    }

    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(XSSFWorkbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short) 11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        styles.put("cell", style);

        DataFormat format = wb.createDataFormat();

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        style.setDataFormat(format.getFormat("#,##0.00"));
        styles.put("cell-number", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        styles.put("cell-left", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setWrapText(true);
        styles.put("cell-right", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }
}
