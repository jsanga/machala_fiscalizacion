/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.pago.model.TasaInteres;
import com.dadoco.pago.model.TasaInteresPK;
import com.dadoco.pago.service.TasaInteresService;
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
@Named(value = "tasaInteresAdminView")
@ViewScoped
public class TasaInteresViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TasaInteresViewController.class);

    @EJB
    private TasaInteresService interesService;

    private List<TasaInteres> tasas;
    private List<TasaInteres> tasasFilter;
    private TasaInteres tasaSeleccionada;

    private short anio;
    private short mes;

    private boolean creando;
    private boolean editando;

    @PostConstruct
    public void init() {
        tasas = interesService.findByNamedQuery("TasaInteres.findAll");
        anio = Util.ANIO_ACTUAL.shortValue();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public short getMes() {
        return mes;
    }

    public void setMes(short mes) {
        this.mes = mes;
    }

    public TasaInteresService getInteresService() {
        return interesService;
    }

    public void setInteresService(TasaInteresService interesService) {
        this.interesService = interesService;
    }

    public List<TasaInteres> getTasas() {
        tasas = interesService.findByNamedQuery("TasaInteres.findAll");
        return tasas;
    }

    public void setTasas(List<TasaInteres> tasas) {
        this.tasas = tasas;
    }

    public List<TasaInteres> getTasasFilter() {
        return tasasFilter;
    }

    public void setTasasFilter(List<TasaInteres> tasasFilter) {
        this.tasasFilter = tasasFilter;
    }

    public TasaInteres getTasaSeleccionada() {
        return tasaSeleccionada;
    }

    public void setTasaSeleccionada(TasaInteres tasaSeleccionada) {
        this.tasaSeleccionada = tasaSeleccionada;
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
        tasaSeleccionada = new TasaInteres();
        mes = interesService.proximaTasa(anio);
        TasaInteresPK pk = new TasaInteresPK(anio, mes);
        tasaSeleccionada.setTasaInteresPK(pk);
        creando = true;
        editando = false;
    }

    public void cancelarCreacion() {

        if (creando) {
            tasaSeleccionada = null;
        }
        editando = false;
        creando = false;
    }

    public void inicializarEdicion(TasaInteresPK id) {

        tasaSeleccionada = interesService.find(id);
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

        String[] nodes = {"official_name", "official_lastname",
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

            RequestContext context = RequestContext.getCurrentInstance();
            TasaInteres aux = interesService.find(tasaSeleccionada.getTasaInteresPK());

            if (aux != null) {
                interesService.create(tasaSeleccionada);
                JsfUtil.addInformationMessage("Tasa de Interes creada", "Tasa del año " + tasaSeleccionada.getTasaInteresPK().getAnio() + ", mes  " + tasaSeleccionada.getTasaInteresPK().getMes() + " creada con éxito.");
            }
            else{
                 JsfUtil.addWarningMessage("Existe", "Tasa del año " + tasaSeleccionada.getTasaInteresPK().getAnio() + ", mes  " + tasaSeleccionada.getTasaInteresPK().getMes() + " se encuentra registrada.");
            }
            //integranteSeleccionado.setFechaIngreso(new Date());

//            context.execute("PF('officialDialog').hide();");
//            context.update("official-list,messages");
        } catch (Exception e) {

        }
    }

    public void edit() {
        try {

            interesService.edit(tasaSeleccionada);

            RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('officialDialog').hide();");
//            context.update("official-list,messages");
            JsfUtil.addInformationMessage("Tasa de Interes editada", "Tasa del año " + tasaSeleccionada.getTasaInteresPK().getAnio() + ", mes  " + tasaSeleccionada.getTasaInteresPK().getMes() + " editada con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void delete(TasaInteresPK id) {
        try {
            TasaInteres tasa = interesService.find(id);

            if (tasa != null) {
                interesService.remove(tasa);
                tasa = new TasaInteres();
                JsfUtil.addInformationMessage("Tasa eliminada", "Tasa del año " + tasaSeleccionada.getTasaInteresPK().getAnio() + ", mes  " + tasaSeleccionada.getTasaInteresPK().getMes() + " eliminada con éxito.");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("official-list-form");
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
