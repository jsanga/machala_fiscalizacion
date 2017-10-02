/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.admin.service.IntegranteService;
import com.dadoco.audit.service.AuditService;
import com.dadoco.cat.model.ArchivoEscritura;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.common.customFilters.LazyDataModelAdvance;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import com.icl.generics.Versioner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "updateDominioView")
@ViewScoped
public class ActualizarPredioUrbanoDominioController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActualizarPredioUrbanoDominioController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private ContribuyenteService contribuyenteService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private TipoEscrituraService tipoEscrituraService;
    @EJB
    private SearchService searchService;
    @EJB
    private IntegranteService integranteService;
    @EJB
    private AuditService audit;

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;
    private String solarCod;
    private String bloqueCod;
    private String phvCod;
    private String phhCod;

    private Predio predio, predioAnt;

    private List<Contribuyente> propietarios;
    private List<Contribuyente> propietarioNuevos;

    private Contribuyente propietarioSeleccionado;
    private Contribuyente nuevoPropietario;

    private List<SelectItem> tiposPropietarios;

    private Escritura escritura;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private List<UploadFile> documentos;

    private String parametroBusqueda;
    private short anioActual;
    private String pant;
    private LazyDataModelAdvance<Contribuyente> lazyData;

    private Contribuyente nuevoPosesionario;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        propietarioNuevos = new ArrayList<>();
        nuevoPosesionario = new Contribuyente();

        nuevoPropietario = new Contribuyente();
        tiposPropietarios = new ArrayList<>();

        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);

        predio = new Predio();

        propietarios = new ArrayList<>();

        escritura = new Escritura();
        documentos = new ArrayList<>();
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());

        setLazyData(new LazyDataModelAdvance<>(contribuyenteService));
        lazyData.setClasePK(Integer.class);

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    /**
     * @return the lazyData
     */
    public LazyDataModelAdvance<Contribuyente> getLazyData() {
        if (lazyData == null) {
            lazyData = new LazyDataModelAdvance<>(contribuyenteService);
        }
        return lazyData;
    }

    /**
     * @param lazyData the lazyData to set
     */
    public void setLazyData(LazyDataModelAdvance<Contribuyente> lazyData) {
        this.lazyData = lazyData;
    }

    public List<SelectItem> getTiposPropietarios() {
        tiposPropietarios = new ArrayList<>();
        tiposPropietarios.add(new SelectItem(0, "PROPIETARIO"));
        tiposPropietarios.add(new SelectItem(1, "POSESIONARIO"));
        tiposPropietarios.add(new SelectItem(2, "ARRENDATARIO"));
        return tiposPropietarios;
    }

    public void setTiposPropietarios(List<SelectItem> tiposPropietarios) {
        this.tiposPropietarios = tiposPropietarios;
    }

    public short getAnioActual() {
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public List<UploadFile> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<UploadFile> documentos) {
        this.documentos = documentos;
    }

    public TipoEscrituraService getTipoEscrituraService() {
        return tipoEscrituraService;
    }

    public void setTipoEscrituraService(TipoEscrituraService tipoEscrituraService) {
        this.tipoEscrituraService = tipoEscrituraService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public List<Contribuyente> getPropietarioNuevos() {
        return propietarioNuevos;
    }

    public void setPropietarioNuevos(List<Contribuyente> propietarioNuevos) {
        this.propietarioNuevos = propietarioNuevos;
    }

    public Contribuyente getNuevoPropietario() {
        return nuevoPropietario;
    }

    public void setNuevoPropietario(Contribuyente nuevoPropietario) {
        this.nuevoPropietario = nuevoPropietario;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
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

    public Predio getPredio() {
        return predio;
    }

    public Contribuyente getPropietarioSelecccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSelecccionado(Contribuyente propietarioSelecccionado) {
        this.propietarioSeleccionado = propietarioSelecccionado;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
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

    public String getBloqueCod() {
        return bloqueCod;
    }

    public void setBloqueCod(String bloqueCod) {
        this.bloqueCod = bloqueCod;
    }

    public String getPhvCod() {
        return phvCod;
    }

    public void setPhvCod(String phvCod) {
        this.phvCod = phvCod;
    }

    public String getPhhCod() {
        return phhCod;
    }

    public void setPhhCod(String phhCod) {
        this.phhCod = phhCod;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public List<SelectItem> valoresVariables(String tabla, String columna) {

        List<SelectItem> lista = new LinkedList<>();
        List<ValorDiscreto> valores = variablesService.obtenerValores(tabla, columna, anioActual);
        for (ValorDiscreto v : valores) {
            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
            lista.add(i);
        }
        return lista;

    }

//</editor-fold>
    public void buscar() {

        provinciaCod = opcionesBusqueda.getProvinciaCod();
        cantonCod = opcionesBusqueda.getCantonCod();
        parroquiaCod = opcionesBusqueda.getParroquiaCod();
        zonaCod = opcionesBusqueda.getZonaCod();
        sectorCod = opcionesBusqueda.getSectorCod();
        manzanaCod = opcionesBusqueda.getManzanaCod();
        solarCod = opcionesBusqueda.getSolarCod();
        bloqueCod = opcionesBusqueda.getBloqueCod();
        phvCod = opcionesBusqueda.getPhvCod();
        phhCod = opcionesBusqueda.getPhhCod();

        try {
            predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {
                opcionesBusqueda.setEjecutandoAccion(true);
                propietarios = predio.getPropietarios();
                predioAnt = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
                pant = new Versioner().getJson(predioAnt);
                if (!propietarios.isEmpty()) {
                    for (int i = 0; i < propietarios.size(); i++) {
                        propietarios.get(i).obtenerEstatus(predio.getId());
                    }
                    propietarioSeleccionado = propietarios.get(0);
                }

                if (predio.getEscrituras().isEmpty()) {
                    predio.setEscrituras(new ArrayList<Escritura>());
                    escritura = new Escritura();
                    escritura.setAdjuntos(new ArrayList<ArchivoEscritura>());
                } else {
                    escritura = predio.getEscrituras().get(predio.getEscrituras().size() - 1);
                }

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    public String cancelar() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String actualizar() {

        try {
            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();
            predio.setFechaModificacion(new Date());
            predio.setPropietarios(propietarios);
            predio.getEscrituras().add(escritura);
            catastroService.updateDatosLegales(predio, escritura, documentos);
            audit.save(predioAnt.getClaveCatastral(), "Actualizacion Clave Titulo de dominio", pant, new Versioner().getJson(predio), user);
            JsfUtil.addInformationMessage(null, "Predio " + predio.getClaveCatastral() + " actualizado correctamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        opcionesBusqueda.setEjecutandoAccion(false);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();

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

    public String getCodigoCatastral() {

        String clave = String.format("%d-%d-%d-%d-%d-%d-%d-%d", parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
        return clave;
    }

    public void eliminarPropietario() {

        if (propietarios.contains(propietarioSeleccionado)) {
            propietarios.remove(propietarioSeleccionado);
        }
        if (!propietarios.isEmpty()) {
            propietarioSeleccionado = propietarios.get(0);
        }
    }

    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("crearPredioForm");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiP = (UIInput) components.findComponent("pisoSelect");
        String p = uiP.getLocalValue() == null ? ""
                : uiP.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiP.setValid(false);
        }

        errores += visitor.getInvalidFields();

        if (errores != 0) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("Existen errores en el formulario");
            fc.addMessage(null, msg);
        }

        fc.renderResponse();

    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_EP_";
        Util.copyFile(uploadedFile, fileName, documentos, "documentos", (short) 2);
    }

    public void eliminarDocumento(int index) {
        Util.deleteFile(index, documentos);
    }

    public void onRowSelect(SelectEvent event) {
        nuevoPropietario = ((Contribuyente) event.getObject());

    }

    public void onRowUnselect(UnselectEvent event) {
        nuevoPropietario = ((Contribuyente) event.getObject());
    }

    public void onCellEditPropietario(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }

    public Contribuyente getNuevoPosesionario() {
        return nuevoPosesionario;
    }

    public void setNuevoPosesionario(Contribuyente nuevoPosesionario) {
        this.nuevoPosesionario = nuevoPosesionario;
    }

    public void inicializarNuevo() {
        nuevoPosesionario = new Contribuyente();

    }

    public void crearContribuyente() {

        try {

            nuevoPosesionario.setFechaIngreso(new Date());
            nuevoPosesionario.setNombre(nuevoPosesionario.getNombre().toUpperCase());
            nuevoPosesionario.setApellidoPaterno(nuevoPosesionario.getApellidoPaterno().toUpperCase());
            nuevoPosesionario.setApellidoMaterno(nuevoPosesionario.getApellidoMaterno().toUpperCase());
            String nombreCompleto = nuevoPosesionario.getApellidoPaterno() + " " + nuevoPosesionario.getApellidoMaterno() + " " + nuevoPosesionario.getNombre();
            nuevoPosesionario.setNombreCompleto(nombreCompleto);
            nuevoPosesionario = contribuyenteService.guardar(nuevoPosesionario);
            propietarios.add(nuevoPosesionario);

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('CreateContribuyenteDialog').hide();");
        } catch (EJBException e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void validarFormularioPosesionario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("adicionarContribuyenteForm");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiNumero = (UIInput) components.findComponent("numero_identificacion");
        String p = uiNumero.getLocalValue() == null ? ""
                : uiNumero.getLocalValue().toString();

        UIInput uiNombre = (UIInput) components.findComponent("nombre_contribuyente");
        String z = uiNombre.getLocalValue() == null ? ""
                : uiNombre.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiNumero.setValid(false);
        }
        if (z.equals("")) {
            errores++;
            uiNombre.setValid(false);
        }

        errores += visitor.getInvalidFields();

        if (errores != 0) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("Existen errores en el formulario");
            fc.addMessage(null, msg);
        }

        fc.renderResponse();

    }
}
