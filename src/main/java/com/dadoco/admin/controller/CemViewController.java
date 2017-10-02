/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.ContribucionEspecialService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.admin.model.ContribucionEspecial;
import java.io.Serializable;
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
@Named(value = "cemAdminView")
@ViewScoped
public class CemViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CemViewController.class);

    @EJB
    private ContribucionEspecialService contribucionEspecialService;

    private List<ContribucionEspecial> contribuciones;
    private List<ContribucionEspecial> contribucionesFiltradas;
    private ContribucionEspecial contribucionSeleccionada;

    private boolean creando;
    private boolean editando;

    @PostConstruct
    public void init() {
        contribucionSeleccionada = new ContribucionEspecial();
        contribuciones = contribucionEspecialService.findByNamedQuery("ContribucionEspecial.findAll");
    }

    public List<ContribucionEspecial> getContribucionesFiltradas() {
        return contribucionesFiltradas;
    }

    public void setContribucionesFiltradas(List<ContribucionEspecial> contribucionesFiltradas) {
        this.contribucionesFiltradas = contribucionesFiltradas;
    }
    
    

    public ContribucionEspecialService getContribucionEspecialService() {
        return contribucionEspecialService;
    }

    public void setContribucionEspecialService(ContribucionEspecialService contribucionEspecialService) {
        this.contribucionEspecialService = contribucionEspecialService;
    }

    public List<ContribucionEspecial> getContribuciones() {
        contribuciones = contribucionEspecialService.findByNamedQuery("ContribucionEspecial.findAll");
        return contribuciones;
    }

    public void setContribuciones(List<ContribucionEspecial> contribuciones) {
        this.contribuciones = contribuciones;
    }

    public ContribucionEspecial getContribucionSeleccionada() {
        return contribucionSeleccionada;
    }

    public void setContribucionSeleccionada(ContribucionEspecial contribucionSeleccionada) {
        this.contribucionSeleccionada = contribucionSeleccionada;
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

    public void inicializarNuevo() {
        contribucionSeleccionada = new ContribucionEspecial();
        creando = true;
        editando = false;
    }

    public void cancelarCreacion() {

        if (creando) {
            contribucionSeleccionada = null;
        }
        editando = false;
        creando = false;
    }

    public void inicializarEdicion(Integer id) {

        contribucionSeleccionada = contribucionEspecialService.find(id);
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

        String[] nodes = {"nombre", "presupuesto",
            "fecha_inicio","fecha_final","fecha_inicio_c","fecha_final_c"};

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
            contribucionEspecialService.create(contribucionSeleccionada);

            JsfUtil.addInformationMessage("CEM ", contribucionSeleccionada.getNombre() + " creada con éxito.");

        } catch (Exception e) {

        }
    }

    public void edit() {
        try {
            contribucionEspecialService.edit(contribucionSeleccionada);

            JsfUtil.addInformationMessage("CEM ", contribucionSeleccionada.getNombre() + " editada con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void delete(Integer id) {
        try {

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
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
