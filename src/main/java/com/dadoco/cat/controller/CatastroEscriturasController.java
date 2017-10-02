/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.admin.service.IntegranteService;
import com.dadoco.cat.model.ArchivoEscritura;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedDocument;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@Named(value = "catastroEscrituraView")
@ViewScoped
public class CatastroEscriturasController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CatastroEscriturasController.class);

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

    private Predio predio;

    private List<Contribuyente> propietarios;
    private List<Contribuyente> propietarioNuevos;

    private Contribuyente propietarioSeleccionado;
    private Contribuyente nuevoPropietario;

    private Escritura escritura;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private List<UploadedFile> documentos;
    private UploadedDocument[] infoDocumentos;

    private String parametroBusqueda;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        propietarioNuevos = new ArrayList<>();

        nuevoPropietario = new Contribuyente();

        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);

        predio = new Predio();

        propietarios = new ArrayList<>();

        escritura = new Escritura();
        escritura.setAdjuntos(new ArrayList<ArchivoEscritura>());

        infoDocumentos = new UploadedDocument[1];
        documentos = new ArrayList<>();

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public TipoEscrituraService getTipoEscrituraService() {
        return tipoEscrituraService;
    }

    public void setTipoEscrituraService(TipoEscrituraService tipoEscrituraService) {
        this.tipoEscrituraService = tipoEscrituraService;
    }

    public List<UploadedFile> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<UploadedFile> documentos) {
        this.documentos = documentos;
    }

    public UploadedDocument[] getInfoDocumentos() {
        return infoDocumentos;
    }

    public void setInfoDocumentos(UploadedDocument[] infoDocumentos) {
        this.infoDocumentos = infoDocumentos;
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

//    public List<SelectItem> valoresVariables(String tabla, String columna) {
//
//        List<SelectItem> lista = new LinkedList<SelectItem>();
//        List<ValorDiscreto> valores = variablesService.obtenerValores(tabla, columna);
//        for (ValorDiscreto v : valores) {
//            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
//            lista.add(i);
//        }
//        return lista;
//
//    }
//</editor-fold>
    
    public void ajustarOpcionesBusqueda() {
        opcionesBusqueda.setProvinciaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getProvinciaCod()), 2));
        opcionesBusqueda.setCantonCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getCantonCod()), 2));
        opcionesBusqueda.setParroquiaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getParroquiaCod()), 2));
        opcionesBusqueda.setZonaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getZonaCod()), 2));
        opcionesBusqueda.setSectorCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSectorCod()), 2));
        opcionesBusqueda.setManzanaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getManzanaCod()), 3));
        opcionesBusqueda.setSolarCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSolarCod()), 3));
        opcionesBusqueda.setBloqueCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getBloqueCod()), 3));
        opcionesBusqueda.setPhhCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getPhhCod()), 3));
        opcionesBusqueda.setPhvCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getPhvCod()), 3));
    }

    public String generadorCeroALaIzquierda(Long n, Integer max) {
        int cont = 0;
        Long num = n;
        String salida = "";
        while (num > 0) {
            num = num / 10;
            cont++;
        }
        for (int i = 0; i < max - cont; i++) {
            salida = salida + "0";
        }
        if(n > 0)
            salida = salida + n;
        return salida;
    }
    
    public void buscar() {
        
        ajustarOpcionesBusqueda();
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
                if (!propietarios.isEmpty()) {
                    propietarioSeleccionado = propietarios.get(0);
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

    public String catastrarEscritura() {

        List<UploadedDocument> filesEscritura = guardarDocumentos();
        try {
            predio.setFechaModificacion(new Date());
            predio.setPropietarios(propietarios);

            catastroService.catastrarEscritura(predio, escritura, filesEscritura);

            JsfUtil.addInformationMessage("Predio Actualizado", "Predio " + predio.getClaveCatastral() + " se ha catastrado la escritura correctamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
            for (UploadedDocument f : filesEscritura) {
                f.getSavedFile().delete();
            }
        }

        opcionesBusqueda.setEjecutandoAccion(false);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public String onFlowProcess(FlowEvent event) {

        log.error("New Step: " + event.getNewStep());
        log.error("Old Step: " + event.getOldStep());
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

        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
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
        documentos.add(uploadedFile);
        UploadedDocument ui = new UploadedDocument(documentos.size(), FilenameUtils.getName(uploadedFile.getFileName()), "");

        infoDocumentos[documentos.size() - 1] = ui;
    }

    public void eliminarDocumento(int index) {
        if (index >= 0 && index < documentos.size()) {
            for (int i = index; i < documentos.size() - 1; i++) {
                infoDocumentos[i] = infoDocumentos[i + 1];
            }
            infoDocumentos[documentos.size() - 1] = null;
            documentos.remove(index);
        }
    }

    private List<UploadedDocument> guardarDocumentos() {

        String uploadDir = config.getDbConfiguration().getString("directorio_documentos");
        List<UploadedDocument> copiados = new ArrayList<>();
        for (int i = 0; i < documentos.size(); i++) {

            UploadedFile uploadedFile = documentos.get(i);
            String fileName = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod + "_";
            fileName += UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName());

            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                File destFile = new File(new File(uploadDir), fileName);

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedDocument doc = infoDocumentos[i];
                doc.setSavedFile(destFile);

                copiados.add(doc);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return copiados;
    }

    public void onRowSelect(SelectEvent event) {
        propietarioSeleccionado = ((Contribuyente) event.getObject());

    }

    public void onRowUnselect(UnselectEvent event) {
        propietarioSeleccionado = ((Contribuyente) event.getObject());
    }
}
