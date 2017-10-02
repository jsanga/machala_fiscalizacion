/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.ValorDiscretoPK;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.ValorDiscretoService;
import com.dadoco.common.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "coefValorDiscretoView")
@ViewScoped
public class ValorDiscretoViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ValorDiscretoViewController.class);

    @EJB
    private ValorDiscretoService valorDiscretoService;
    private List<ValorDiscreto> coeficientes;
    private ValorDiscreto coeficienteSeleccionado;
    private int idVariable;
    private short anioEmision;
    List<SelectItem> variables;

    @PostConstruct
    public void init() {
        variables = new ArrayList<>();
        anioEmision = (short) (Util.ANIO_ACTUAL.shortValue() + (short) 1);
        idVariable = 7;
        Object[] params = {idVariable, anioEmision};
        coeficientes = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
    }

    public void inicializarEdicion(ValorDiscretoPK id) {
        coeficienteSeleccionado = valorDiscretoService.find(id);
    }

    public List<ValorDiscreto> getCoeficientes() {
        Object[] params = {idVariable, anioEmision};
        coeficientes = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        return coeficientes;
    }

    public void setCoeficientes(List<ValorDiscreto> coeficientes) {
        this.coeficientes = coeficientes;
    }

    public ValorDiscreto getCoeficienteSeleccionado() {
        return coeficienteSeleccionado;
    }

    public void setCoeficienteSeleccionado(ValorDiscreto coeficienteSeleccionado) {
        this.coeficienteSeleccionado = coeficienteSeleccionado;
    }

    public ValorDiscretoService getValorDiscretoService() {
        return valorDiscretoService;
    }

    public void setValorDiscretoService(ValorDiscretoService valorDiscretoService) {
        this.valorDiscretoService = valorDiscretoService;
    }

    public void editar() {
        try {

            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();

            RequestContext context = RequestContext.getCurrentInstance();
            valorDiscretoService.edit(coeficienteSeleccionado);

            context.execute("PF('addDialog').hide();");
            context.update("messages");
            JsfUtil.addInformationMessage("Actualizado", "Coeficiente actualizado con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void cancelar() {
        //init();
    }
    
    public void cambiarCoeficiente(){
        
    }

    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("form-form");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiP = (UIInput) components.findComponent("parroquia-select");
        String p = uiP.getLocalValue() == null ? ""
                : uiP.getLocalValue().toString();

        UIInput uiZ = (UIInput) components.findComponent("zona-select");
        String z = uiZ.getLocalValue() == null ? ""
                : uiZ.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiP.setValid(false);
        }
        if (z.equals("")) {
            errores++;
            uiZ.setValid(false);
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

    public int getIdVariable() {
        return idVariable;
    }

    public void setIdVariable(int idVariable) {
        this.idVariable = idVariable;
    }

    public short getAnioEmision() {
        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

    public List<SelectItem> getVariables() {
        variables = new ArrayList<>();
        variables.add(new SelectItem(7, "UBICACIÓN"));
        variables.add(new SelectItem(8, "TOPOGRAFÍA"));
        variables.add(new SelectItem(9, "GEOMETRÍA"));
        variables.add(new SelectItem(10, "CARACTERÍSTICAS SUELO"));
        variables.add(new SelectItem(109, "INFRAESTRUCTURA BÁSICA"));
        variables.add(new SelectItem(110, "IFRAESTRUCTURA COMPLEMENT."));
        variables.add(new SelectItem(16, "TIPO CAPA RODADURA"));
        variables.add(new SelectItem(17, "ESTADO CONSERVACIÓN VÍA"));
        variables.add(new SelectItem(24, "ESTRUCTURA EDIFICACIÓN"));
        variables.add(new SelectItem(83, "NRO NIVELES"));
        return variables;
    }

    public void setVariables(List<SelectItem> variables) {
        this.variables = variables;
    }
    
    
}
