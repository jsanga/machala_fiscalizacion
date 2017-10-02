/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.ValorDiscretoCompuestoService;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.ValorDiscretoPK;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.ValorDiscretoService;
import com.dadoco.common.util.Util;
import com.dadoco.ren.model.ValorDiscretoCompuesto;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "coefEstrucNivelView")
@ViewScoped
public class EstructuraNivelViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EstructuraNivelViewController.class);

    @EJB
    private ValorDiscretoCompuestoService valorDiscretoCompuestoService;
    @EJB
    private ValorDiscretoService valorDiscretoService;
    private List<ValorDiscretoCompuesto> coeficientes;
    private List<ValorDiscretoCompuesto> coeficientesSeleccionados;
    private List<ValorDiscretoCompuesto> valores;
    private List<ValorDiscreto> discretos;
    private ValorDiscreto fuerte;
    private short anioEmision;
    private List<String> columnsName;

    @PostConstruct
    public void init() {

        anioEmision = (short) (Util.ANIO_ACTUAL + (short) 1);
        Object[] params = {24, 83, anioEmision, anioEmision};
        valores = valorDiscretoCompuestoService.findByNamedQuery("ValorDiscretoCompuesto.findAllByFuerteAndDebilAndAnio", params);
        Object[] params1 = {83, anioEmision};
        discretos = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params1);

        coeficientes = new ArrayList<>();
        int cantidadDebil = discretos.size();

        for (int i = 0; i < valores.size(); i += cantidadDebil) {

            Object[] params2 = {24, valores.get(i).getValorFuerte().getValorDiscretoPK().getValor(),83, anioEmision};
            List<ValorDiscretoCompuesto> deps = valorDiscretoCompuestoService.findByNamedQuery("ValorDiscretoCompuesto.findByIdVariableFuerteAndValueFuerte", params2);

            ValorDiscretoCompuesto aux = valores.get(i);
            aux.setDependientes(deps);
            coeficientes.add(aux);

        }

        columnsName = getColumnsName();
        coeficientesSeleccionados = new ArrayList<>();
        fuerte = new ValorDiscreto();
    }

    public void inicializarEdicion(ValorDiscretoPK id) {

        fuerte = valorDiscretoService.find(id);

        Object[] params2 = {24, id.getValor(), 83, anioEmision};
        coeficientesSeleccionados = valorDiscretoCompuestoService.findByNamedQuery("ValorDiscretoCompuesto.findByIdVariableFuerteAndValueFuerte", params2);

        for (int i = 0; i < coeficientesSeleccionados.size(); i++) {
            if (i == coeficientesSeleccionados.size() - 1) {
                coeficientesSeleccionados.get(i).setNextOnEnter("actualizar-btn");
            } else {
                coeficientesSeleccionados.get(i).setNextOnEnter("coeficiente"+(i+1));
            }
        }

    }

    public List<ValorDiscreto> getDiscretos() {
        return discretos;
    }

    public void setDiscretos(List<ValorDiscreto> discretos) {
        this.discretos = discretos;
    }

    public ValorDiscretoService getValorDiscretoService() {
        return valorDiscretoService;
    }

    public void setValorDiscretoService(ValorDiscretoService valorDiscretoService) {
        this.valorDiscretoService = valorDiscretoService;
    }

    public List<ValorDiscretoCompuesto> getValores() {
        return valores;
    }

    public void setValores(List<ValorDiscretoCompuesto> valores) {
        this.valores = valores;
    }

    public ValorDiscreto getFuerte() {
        return fuerte;
    }

    public void setFuerte(ValorDiscreto fuerte) {
        this.fuerte = fuerte;
    }

    public short getAnioEmision() {
        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

    public List<String> getColumnsName() {
        Object[] params = {83, anioEmision};
        List<ValorDiscreto> vs = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params);
        log.info("Canridad valores discretos:" + vs.size());
        columnsName = new ArrayList<>();
        for (ValorDiscreto vd : vs) {
            columnsName.add(vd.getTexto());
        }

        return columnsName;
    }

    public void setColumnsName(List<String> columnsName) {
        this.columnsName = columnsName;
    }

    public ValorDiscretoCompuestoService getValorDiscretoCompuestoService() {
        return valorDiscretoCompuestoService;
    }

    public void setValorDiscretoCompuestoService(ValorDiscretoCompuestoService valorDiscretoCompuestoService) {
        this.valorDiscretoCompuestoService = valorDiscretoCompuestoService;
    }

    public List<ValorDiscretoCompuesto> getCoeficientes() {
        return coeficientes;
    }

    public void setCoeficientes(List<ValorDiscretoCompuesto> coeficientes) {
        this.coeficientes = coeficientes;
    }

    public void editar() {
        try {

            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();

            RequestContext context = RequestContext.getCurrentInstance();

            valorDiscretoService.edit(fuerte);
            for (int i = 0; i < coeficientesSeleccionados.size(); i++) {
                valorDiscretoCompuestoService.edit(coeficientesSeleccionados.get(i));
            }
            // coeficienteService.edit(coeficienteSeleccionado);

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

    public List<ValorDiscretoCompuesto> getCoeficientesSeleccionados() {
        return coeficientesSeleccionados;
    }

    public void setCoeficientesSeleccionados(List<ValorDiscretoCompuesto> coeficientesSeleccionados) {
        this.coeficientesSeleccionados = coeficientesSeleccionados;
    }

}
