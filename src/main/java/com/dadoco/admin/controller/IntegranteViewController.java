/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;


import com.dadoco.admin.service.IntegranteService;
import com.dadoco.cat.model.Integrante;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "integranteAdminView")
@ViewScoped
public class IntegranteViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IntegranteViewController.class);

    @EJB
    private IntegranteService integranteService;

    private List<Integrante> integrantes;
    private List<Integrante> integrantesFilter;
    private Integrante integranteSeleccionado;

    private boolean creando;
    private boolean editando;
    
    @PostConstruct
    public void init() {
        integrantes = integranteService.findAll();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public List<Integrante> getIntegrantesFilter() {
        return integrantesFilter;
    }

    public void setIntegrantesFilter(List<Integrante> integrantesFilter) {
        this.integrantesFilter = integrantesFilter;
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    public List<Integrante> getIntegrantes() {
        integrantes = integranteService.findAll();
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    public Integrante getIntegranteSeleccionado() {
        return integranteSeleccionado;
    }

    public void setIntegranteSeleccionado(Integrante integranteSeleccionado) {
        this.integranteSeleccionado = integranteSeleccionado;
    }

    public boolean isCreando() {
        return creando;
    }

    public void setCreando(boolean creando) {
        this.creando = creando;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }


//</editor-fold>
    public void inicializarNuevo() {
        integranteSeleccionado = new Integrante();
        creando = true;
        editando = false;
    }

    public void cancelarCreacion() {

        if (creando) {
            integranteSeleccionado = null;
        }
        editando = false;
        creando = false;
    }

    public void inicializarEdicion(Integer id) {

        integranteSeleccionado = integranteService.find(id);
        editando = true;
        creando = false;
    }

    @SuppressWarnings("empty-statement")
    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("new-official-form");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        String[] nodes = {"type-select","official_name", "official_lastname", 
            "official_id"};

        UIInput uiNode;

        for (String node : nodes) {

            uiNode = (UIInput) components.findComponent(node);
            if (uiNode.getLocalValue() == null) {
                uiNode.setValid(false);
            }

        }

    }

    public void crear() {
        try {
      
            integranteSeleccionado.setFechaIngreso(new Date());

            integranteService.create(integranteSeleccionado);

            RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('officialDialog').hide();");
//            context.update("official-list,messages");
            JsfUtil.addInformationMessage("Integrante creado", "Integrante " + integranteSeleccionado.getNombre() + " " + integranteSeleccionado.getApellidos() + " creado con éxito.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void edit() {
        try {

            integranteService.edit(integranteSeleccionado);

            RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('officialDialog').hide();");
//            context.update("official-list,messages");
            JsfUtil.addInformationMessage("Integrante editado", "Integrante " + integranteSeleccionado.getNombre() + " " + integranteSeleccionado.getApellidos() + " editado con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void delete(Integer id) {
        try {
            Integrante integrante = integranteService.find(id);

            if (integrante != null) {
                integranteService.remove(integrante);
                integranteSeleccionado = new Integrante();
                JsfUtil.addInformationMessage("Integrante eliminado", "Integrante " + integrante.getNombre() + " eliminado con éxito.");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("official-list-form");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public List<Integrante> getIntegranteList() {
        return integranteService.findByNamedQuery("Integrante.findAll");
    }

    public String cancelarModificacion() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public void CancelarAction() {

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('officialDialog').hide();");
    }
}
