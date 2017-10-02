/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.RangoVariacionAreaTerrenoService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.ren.model.RangoVariacionAreaTerreno;
import com.dadoco.ren.model.RangoVariacionAreaTerrenoPK;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
@Named(value = "coefVariacionAreaView")
@ViewScoped
public class RangoVariacionAreaTerrenoViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RangoVariacionAreaTerrenoViewController.class);

    @EJB
    private RangoVariacionAreaTerrenoService variacionAreaService;
    private List<RangoVariacionAreaTerreno> coeficientes;
    private RangoVariacionAreaTerreno coeficienteSeleccionado;
    private short anio;

    @PostConstruct
    public void init() {
        anio = (short)(Util.ANIO_ACTUAL +(short)1);
        coeficientes = variacionAreaService.findByNamedQuery("RangoVariacionAreaTerreno.findByAnio", anio);
    }

    public void inicializarEdicion(RangoVariacionAreaTerrenoPK id) {
        coeficienteSeleccionado = variacionAreaService.find(id);
    }

    public RangoVariacionAreaTerrenoService getVariacionAreaService() {
        return variacionAreaService;
    }

    public void setVariacionAreaService(RangoVariacionAreaTerrenoService variacionAreaService) {
        this.variacionAreaService = variacionAreaService;
    }

    public List<RangoVariacionAreaTerreno> getCoeficientes() {
        coeficientes = variacionAreaService.findByNamedQuery("RangoVariacionAreaTerreno.findByAnio", anio);
        return coeficientes;
    }

    public void setCoeficientes(List<RangoVariacionAreaTerreno> coeficientes) {
        this.coeficientes = coeficientes;
    }

    public RangoVariacionAreaTerreno getCoeficienteSeleccionado() {
        return coeficienteSeleccionado;
    }

    public void setCoeficienteSeleccionado(RangoVariacionAreaTerreno coeficienteSeleccionado) {
        this.coeficienteSeleccionado = coeficienteSeleccionado;
    }

    public short getAnio() {
        anio = (short)(Util.ANIO_ACTUAL +(short)1);
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    


    public void editar() {
        try {

            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();

            RequestContext context = RequestContext.getCurrentInstance();
            variacionAreaService.edit(coeficienteSeleccionado);

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
}
