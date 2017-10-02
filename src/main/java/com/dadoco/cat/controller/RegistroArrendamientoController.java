/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.TipoEscritura;
import com.dadoco.cat.model.ContratoArrendamiento;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.service.EscrituraService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@Named(value = "registroArrendamientoView")
@ViewScoped
public class RegistroArrendamientoController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegistroArrendamientoController.class);

    @EJB
    private ConfigReader config;
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
    private EscrituraService escrituraService;

    @EJB
    private TipoEscrituraService tipoEscrituraService;
    @EJB
    private SearchService searchService;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private Terreno terreno;

    private Manzana manzana;

    private Predio predio;

    private Zona zona;

    private Sector sector;

    private List<Contribuyente> propietarios;
    private Contribuyente propietarioSeleccionado;
    private Contribuyente propietario;
    private Contribuyente nuevoPropietario;
    private List<Contribuyente> busquedaContribuyentes;
    private List<Contribuyente> propietarioNuevos;

    private ContratoArrendamiento contratoArrendamiento;

    private String s_tipoIdentificacion;
    private String s_numeroIdentificacion;
    private String s_nombreContribuyente;

    private boolean terrenoExistente;

    private List<SelectItem> tiposEscrituras;
    private Long idTipoEscritura;
    private Escritura escritura;
    private String tipoPredio;
    
     private String parametroBusqueda;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        opcionesBusqueda = new OpcionesBusquedaPredio();

        predio = new Predio();

        terreno = new Terreno();

        terrenoExistente = false;

        propietarios = new ArrayList<Contribuyente>();
        propietario = new Contribuyente();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        tipoPredio = "M";

        escritura = new Escritura();
//        escritura.setFechaCatastro(new Date());
        contratoArrendamiento = new ContratoArrendamiento();

        busquedaContribuyentes = new ArrayList<Contribuyente>();
        Long numCatastro = escrituraService.obtenerNuevoNumeroCatastro();
//        escritura.setNumeroCatastro(numCatastro);

    }

    public List<Contribuyente> getBusquedaContribuyentes() {
        return busquedaContribuyentes;
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setters">

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public List<Contribuyente> getPropietarioNuevos() {
        return propietarioNuevos;
    }

    public void setPropietarioNuevos(List<Contribuyente> propietarioNuevos) {
        this.propietarioNuevos = propietarioNuevos;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public Contribuyente getPropietario() {
        return propietario;
    }

    public void setPropietario(Contribuyente propietario) {
        this.propietario = propietario;
    }

    public ContratoArrendamiento getContratoArrendamiento() {
        return contratoArrendamiento;
    }

    public void setContratoArrendamiento(ContratoArrendamiento contratoArrendamiento) {
        this.contratoArrendamiento = contratoArrendamiento;
    }

    public void setBusquedaContribuyentes(List<Contribuyente> busquedaContribuyentes) {
        this.busquedaContribuyentes = busquedaContribuyentes;
    }

    public EscrituraService getEscrituraService() {
        return escrituraService;
    }

    public void setEscrituraService(EscrituraService escrituraService) {
        this.escrituraService = escrituraService;
    }

    public List<SelectItem> getTiposEscrituras() {
        tiposEscrituras = new ArrayList<SelectItem>();
        List<TipoEscritura> escr = tipoEscrituraService.findAll();
        for (TipoEscritura te : escr) {
            tiposEscrituras.add(new SelectItem(te.getId(), te.getTipo()));
        }
        return tiposEscrituras;
    }

    public void setTiposEscrituras(List<SelectItem> tiposEscrituras) {
        this.tiposEscrituras = tiposEscrituras;
    }

    public Long getIdTipoEscritura() {
        return idTipoEscritura;
    }

    public void setIdTipoEscritura(Long idTipoEscritura) {
        this.idTipoEscritura = idTipoEscritura;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public TipoEscrituraService getTipoEscrituraService() {
        return tipoEscrituraService;
    }

    public void setTipoEscrituraService(TipoEscrituraService tipoEscrituraService) {
        this.tipoEscrituraService = tipoEscrituraService;
    }

    public List<SelectItem> getTiposEscritura() {
        List<TipoEscritura> tiposEscritura = tipoEscrituraService.findAll();

        List<SelectItem> items = new ArrayList<SelectItem>();
        for (TipoEscritura e : tiposEscritura) {
            items.add(new SelectItem(e.getId(), e.getTipo()));
        }
        log.error("Tipos de escritura: " + items.size());
        return items;
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

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public boolean isTerrenoExistente() {
        return terrenoExistente;
    }

    public void setTerrenoExistente(boolean terrenoExistente) {
        this.terrenoExistente = terrenoExistente;
    }

    public String getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(String tipoPredio) {
        this.tipoPredio = tipoPredio;
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

    public String getS_tipoIdentificacion() {
        return s_tipoIdentificacion;
    }

    public void setS_tipoIdentificacion(String s_tipoIdentificacion) {
        this.s_tipoIdentificacion = s_tipoIdentificacion;
    }

    public String getS_numeroIdentificacion() {
        return s_numeroIdentificacion;
    }

    public void setS_numeroIdentificacion(String s_numeroIdentificacion) {
        this.s_numeroIdentificacion = s_numeroIdentificacion;
    }

    public String getS_nombreContribuyente() {
        return s_nombreContribuyente;
    }

    public void setS_nombreContribuyente(String s_nombreContribuyente) {
        this.s_nombreContribuyente = s_nombreContribuyente;
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

        try {
           predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado",
                        parroquiaCod,zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
                init();
            } else {

                if (predio.getDominio() != 2) {
                    JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no puede ser arrendado porque no es Municipal.",
                            parroquiaCod,zonaCod, sectorCod, manzanaCod, solarCod,bloqueCod, phvCod, phhCod));
                    return;
                }

                if (predio.getContratoArrendamiento() != null) {
                    if (predio.getContratoArrendamiento().getHabilitado() == 1) {
                        JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d ya se encuentra en arriendo.",
                                parroquiaCod,zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
                        return;
                    }
                }

//                Object[] params = new Object[3];
//                params[0] = "R";
//                params[1] = config.getDbConfiguration().getString("ruc_municipio");
//                params[2] = (short) 1;
//
//                List<Contribuyente> list = contribuyenteService.findByNamedQuery("Contribuyente.findByTipoIdentificacion", params);

                //propietario = list.isEmpty() ? new Contribuyente() : list.get(0);

                opcionesBusqueda.setEjecutandoAccion(true);
                propietarios = predio.getPropietarios();

                if (propietarios.isEmpty()) {
                    propietarios = new ArrayList<Contribuyente>();
                } else {
                    if (propietarios.contains(propietario)) {
                        propietarios.remove(propietario);
                    }
//
                    propietarioSeleccionado = propietarios.get(0);
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public String cancelarCatastroEscritura() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String contratarArriendo() {

        try {
            //propietarios.add(propietario);
            predio.setPropietarios(propietarios);
            catastroService.registrarContratoArrendamiento(predio, propietarios, contratoArrendamiento);
            JsfUtil.addInformationMessage("Arrendamiento", "El predio " + predio.getClaveCatastral() + " ha sido arrendado con éxito.");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
        }

        return "pretty:cat-registro-arrendamiento";
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

public void inicializarBusquedaContribuyente() {
        nuevoPropietario = null;
        propietarioNuevos = new ArrayList<>();

    }

    public void buscarContribuyente() {
        try {
            propietarioNuevos = new ArrayList<>();

            propietarioNuevos = searchService.buscarContribuyentes(parametroBusqueda);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    public void adicionarPropietario() {
        if (nuevoPropietario != null) {

            if (!propietarioNuevos.contains(nuevoPropietario)) {
                propietarioNuevos.add(nuevoPropietario);
            }

            if (!propietarios.contains(nuevoPropietario)) {
                propietarios.add(nuevoPropietario);

            }

            propietarioSeleccionado = nuevoPropietario;
            nuevoPropietario = null;
        }
        inicializarBusquedaContribuyente();
    }
    
    public void eliminarPropietario() {

        if (propietarios.contains(propietarioSeleccionado)) {
            propietarios.remove(propietarioSeleccionado);
        }
        
        if (!propietarios.isEmpty()) {
            propietarioSeleccionado = propietarios.get(0);
        }
    }

//    public void buscarContribuyente() {
//
//        try {
//            busquedaContribuyentes = new ArrayList<Contribuyente>();
//
//            Object[] params = new Object[3];
//            params[0] = s_tipoIdentificacion;
//            params[1] = s_numeroIdentificacion;
//            params[2] = (short) 1;
//
//            busquedaContribuyentes = contribuyenteService.findByNamedQuery("Contribuyente.findByTipoIdentificacion", params);
//
//            if (busquedaContribuyentes == null || busquedaContribuyentes.isEmpty()) {
//                JsfUtil.addWarningMessage(null, "Contribuyente no encontrado");
//            } else {
//                Contribuyente c = busquedaContribuyentes.get(0);
//                if (!propietarios.contains(c));
//                propietarios.add(c);
//            }
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, e.getMessage());
//        }
//    }
}
