/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.RazonExencion;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.cat.model.TipoExencion;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.RazonExencionService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.cat.service.TipoExencionService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@Named(value = "exencionView")
@ViewScoped
public class ExencionRebajaController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExencionRebajaController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private CatastroService catastroService;
    @EJB
    private TipoDocumentoService tipoDocumentoService;
    @EJB
    private TipoExencionService tipoExencionService;
    @EJB
    private RazonExencionService razonExencionService;
    @EJB
    private AvaluosService avaluosService;
    @EJB
    private VariableService variablesService;

    private DatosAutorizacion datosAutorizacion;
    private List<SelectItem> tiposDocumentos;
    private Long idTipoDocumento;

    private Exencion exencion;
    private List<SelectItem> tiposExencion;
    private Integer idTipoExencion;
    private List<SelectItem> razonesExencion;
    private Integer idRazonExencion;

    private RazonExencion razonExencionSeleccionada;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private Predio predio;

    private List<Contribuyente> propietarios;
    private Contribuyente propietarioSeleccionado;
    private Contribuyente nuevoPropietario;
    private List<Contribuyente> busquedaContribuyentes;

    private String avaluoTerreno;
    private String avaluoConstruccion;
    private String avaluoComplementarias;
    private String avaluoPredial;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        opcionesBusqueda = new OpcionesBusquedaPredio();

        predio = new Predio();
        exencion = new Exencion();
        exencion.setUsuarioIngreso(user);
        exencion.setFechaIngreso(new Date());
        propietarios = new ArrayList<Contribuyente>();
        datosAutorizacion = new DatosAutorizacion();
        datosAutorizacion.setResponsable(user);

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        busquedaContribuyentes = new ArrayList<Contribuyente>();
        propietarios = new ArrayList<Contribuyente>();
        propietarioSeleccionado = new Contribuyente();
        razonExencionSeleccionada = new RazonExencion();
    }

    public List<Contribuyente> getBusquedaContribuyentes() {
        return busquedaContribuyentes;
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setters">

    public VariableService getVariablesService() {
        return variablesService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
    }
    
    
    public TipoExencionService getTipoExencionService() {
        return tipoExencionService;
    }

    public void setTipoExencionService(TipoExencionService tipoExencionService) {
        this.tipoExencionService = tipoExencionService;
    }

    public RazonExencionService getRazonExencionService() {
        return razonExencionService;
    }

    public void setRazonExencionService(RazonExencionService razonExencionService) {
        this.razonExencionService = razonExencionService;
    }

    public Exencion getExencion() {
        return exencion;
    }

    public void setExencion(Exencion exencion) {
        this.exencion = exencion;
    }

    public List<SelectItem> getTiposExencion() {

        List<TipoExencion> tipoExecionList = tipoExencionService.findAll();
        tiposExencion = new ArrayList<SelectItem>();

        if (!tipoExecionList.isEmpty()) {
            idTipoExencion = tipoExecionList.get(0).getId();
            for (TipoExencion te : tipoExecionList) {
                tiposExencion.add(new SelectItem(te.getId(), te.getTipoExencion()));
            }
        }
        return tiposExencion;
    }

    public void setTiposExencion(List<SelectItem> tiposExencion) {
        this.tiposExencion = tiposExencion;
    }

    public Integer getIdTipoExencion() {
        return idTipoExencion;
    }

    public RazonExencion getRazonExencionSeleccionada() {
        return razonExencionSeleccionada;
    }

    public void setRazonExencionSeleccionada(RazonExencion razonExencionSeleccionada) {
        this.razonExencionSeleccionada = razonExencionSeleccionada;
    }

    public void setIdTipoExencion(Integer idTipoExencion) {
        this.idTipoExencion = idTipoExencion;
    }

    public List<SelectItem> getRazonesExencion() {

        Integer val = idTipoExencion;
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findByIdTipoExencion", val);
        razonesExencion = new ArrayList<SelectItem>();
        if (!list.isEmpty()) {
            razonExencionSeleccionada = list.get(0);
            idRazonExencion = razonExencionSeleccionada.getId();
            for (RazonExencion re : list) {
                razonesExencion.add(new SelectItem(re.getId(), re.getRazonExencion()));
            }
        }
        return razonesExencion;
    }

    public void setRazonesExencion(List<SelectItem> razonesExencion) {
        this.razonesExencion = razonesExencion;
    }

    public Integer getIdRazonExencion() {

        return idRazonExencion;
    }

    public void setIdRazonExencion(Integer idRazonExencion) {
        this.idRazonExencion = idRazonExencion;
    }

    public TipoDocumentoService getTipoDocumentoService() {
        return tipoDocumentoService;
    }

    public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public DatosAutorizacion getDatosAutorizacion() {
        return datosAutorizacion;
    }

    public void setDatosAutorizacion(DatosAutorizacion datosAutorizacion) {
        this.datosAutorizacion = datosAutorizacion;
    }

    public List<SelectItem> getTiposDocumentos() {
        List<TipoDocumento> docs = tipoDocumentoService.findAll();
        tiposDocumentos = new ArrayList<SelectItem>();
        for (TipoDocumento td : docs) {
            tiposDocumentos.add(new SelectItem(td.getId(), td.getNombre()));
        }
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public void setBusquedaContribuyentes(List<Contribuyente> busquedaContribuyentes) {
        this.busquedaContribuyentes = busquedaContribuyentes;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public Contribuyente getNuevoPropietario() {
        return nuevoPropietario;
    }

    public void setNuevoPropietario(Contribuyente nuevoPropietario) {
        this.nuevoPropietario = nuevoPropietario;
    }

    public AvaluosService getAvaluosService() {
        return avaluosService;
    }

    public void setAvaluosService(AvaluosService avaluosService) {
        this.avaluosService = avaluosService;
    }

    public String getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(String avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public String getAvaluoConstruccion() {
        return avaluoConstruccion;
    }

    public void setAvaluoConstruccion(String avaluoConstruccion) {
        this.avaluoConstruccion = avaluoConstruccion;
    }

    public String getAvaluoComplementarias() {
        return avaluoComplementarias;
    }

    public void setAvaluoComplementarias(String avaluoComplementarias) {
        this.avaluoComplementarias = avaluoComplementarias;
    }

    public String getAvaluoPredial() {
        return avaluoPredial;
    }

    public void setAvaluoPredial(String avaluoPredial) {
        this.avaluoPredial = avaluoPredial;
    }

//</editor-fold>
    public void buscarPredio() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
        String bloqueCod = opcionesBusqueda.getBloqueCod();
        String phvCod = opcionesBusqueda.getPhvCod();
        String phhCod = opcionesBusqueda.getPhhCod();

        try {predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado",
                        parroquiaCod,zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {

                /* if(comprobarDeudas(predio))
                 {
                 JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral: %2d-%2d-%3d-%2d-%2d, tiene deuda pendientes con el municipio",
                 zonaCod, sectorCod, manzanaCod, solarCod, phCod));

                 init();
                 return;
                 }*/
                opcionesBusqueda.setEjecutandoAccion(true);
                propietarios = predio.getPropietarios();

                if (propietarios.isEmpty()) {
                    propietarios = new ArrayList<Contribuyente>();
                } else {
                    propietarioSeleccionado = propietarios.get(0);
                }
                double aT = avaluosService.avaluoTerreno(predio);
                avaluoTerreno = String.format("%.2f", aT);
                double aC = avaluosService.avaluoConstruccion(predio);
                avaluoConstruccion = String.format("%.2f", aC);
                double aComp = avaluosService.avaluoComplementarias(predio);
                avaluoComplementarias = String.format("%.2f", aComp);
                avaluoPredial = String.format("%.2f", aT + aC + aComp);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public String cancelar() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String RegistrarExencion() {

        try {

            onRazonExencionChange();
            log.error("Razon De la razon antes de guardar: " + razonExencionSeleccionada.getRazonExencion());
            log.error("Requisito De la razon antes de guardar: " + razonExencionSeleccionada.getRequisito());
            catastroService.RegistrarExencion(predio, idTipoDocumento, datosAutorizacion, exencion, razonExencionSeleccionada);
            JsfUtil.addInformationMessage("Exención", "Al predio " + predio.getClaveCatastral() + " se realizó el registro la exención " + razonExencionSeleccionada.getRazonExencion() + ".");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
        }

        return "pretty:cat-exencion-rebaja";
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public void nuevoPredio() {
        opcionesBusqueda.setEjecutandoAccion(true);
    }

    public void onRowSelect(SelectEvent event) {

        propietarioSeleccionado = (Contribuyente) event.getObject();

    }

    public void onRowUnselect(UnselectEvent event) {

        Predio p = (Predio) event.getObject();

        // deleteContribuyenteListadoPropietarios(p);
        // JsfUtil.addInformationMessage("Predio Unselec", "Predio creado:" + ((Predio) event.getObject()).getId());
    }

    public void onRowSelectDelete(SelectEvent event) {

    }

    public void onRowUnselectDelete(UnselectEvent event) {

    }

    public void onTipoExencionChange() {

        Integer val = idTipoExencion;
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findByIdTipoExencion", val);

        razonesExencion = new ArrayList<SelectItem>();
        if (!list.isEmpty()) {
            razonExencionSeleccionada = list.get(0);

            idRazonExencion = razonExencionSeleccionada.getId();
            for (RazonExencion re : list) {
                razonesExencion.add(new SelectItem(re.getId(), re.getRazonExencion()));
            }
        }
    }

    public void onRazonExencionChange() {
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findById", idRazonExencion);
        if (list.isEmpty()) {
            razonExencionSeleccionada = new RazonExencion();
        } else {
            razonExencionSeleccionada = list.get(0);
            idRazonExencion = razonExencionSeleccionada.getId();
        }

        log.error("Razon De la razon: " + razonExencionSeleccionada.getRazonExencion());
        log.error("Requisito De la razon: " + razonExencionSeleccionada.getRequisito());
    }
}
