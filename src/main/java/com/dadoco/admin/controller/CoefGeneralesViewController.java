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
import com.dadoco.common.service.VariableService;
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
@Named(value = "coefGeneralView")
@ViewScoped
public class CoefGeneralesViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CoefGeneralesViewController.class);

    @EJB
    private ValorDiscretoService valorDiscretoService;
    @EJB
    private VariableService variableService;
    private List<ValorDiscreto> coeficientes;
    private ValorDiscreto coeficienteSeleccionado;
    private short anioEmision;

    @PostConstruct
    public void init() {
        anioEmision = Util.ANIO_ACTUAL.shortValue(); //(short) variableService.obtenerCoeficiente("datos_configuracion", "anio_emision", (short) 0);
        coeficientes = new ArrayList<>();
//        List<ValorDiscreto> lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", 71);
//        if (!lista.isEmpty()) {
//            coeficientes.add(lista.get(0));
//        }
        short anio = (short) (Util.ANIO_ACTUAL.shortValue() + (short) 1);
        Object[] params = new Object[2];
        params[0] = 69;
        params[1] = anio;
        List<ValorDiscreto> lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 72;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 75;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 76;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 77;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 81;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
    }

    public void inicializarEdicion(ValorDiscretoPK id) {
        coeficienteSeleccionado = valorDiscretoService.find(id);
    }

    public List<ValorDiscreto> getCoeficientes() {
        coeficientes.clear();
        short anio = (short) (Util.ANIO_ACTUAL.shortValue() + 1);
        Object[] params = new Object[2];
        params[0] = 69;
        params[1] = anio;

        List<ValorDiscreto> lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 72;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 75;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 76;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 77;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
        params[0] = 81;
        lista = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        if (!lista.isEmpty()) {
            coeficientes.add(lista.get(0));
        }
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
        init();
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
}
