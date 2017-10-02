/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.model.CategoriaEstructuraNivel;
import com.dadoco.admin.service.CategoriaEstructuraNivelService;
import com.dadoco.admin.service.ValorDiscretoCompuestoService;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.ValorDiscretoPK;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.ValorDiscretoService;
import com.dadoco.common.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "categoriaEstructuraView")
@ViewScoped
public class CategoriaEstructuraNivelViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EstructuraNivelViewController.class);

    @EJB
    private CategoriaEstructuraNivelService estructuraNivelService;
    @EJB
    private ValorDiscretoCompuestoService valorDiscretoCompuestoService;
    @EJB
    private ValorDiscretoService valorDiscretoService;
    private List<CategoriaEstructuraNivel> coeficientes;
    private List<CategoriaEstructuraNivel> coeficientesSeleccionados;
    private List<CategoriaEstructuraNivel> valores;
    private List<ValorDiscreto> discretos;
    private ValorDiscreto fuerte;
    private short anioEmision;
    private List<String> columnsName;
    private int idVariable;
    List<SelectItem> variables;

    @PostConstruct
    public void init() {

        variables = new ArrayList<>();
        idVariable = 1;
        anioEmision = (short) Util.ANIO_PROXIMO;
        Object[] params = {idVariable, anioEmision};
        valores = estructuraNivelService.findByNamedQuery("CategoriaEstructuraNivel.findById", params);
        Object[] params1 = {24, anioEmision};
        discretos = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params1);

        coeficientes = new ArrayList<>();
        int cantidad = discretos.size();

        for (int i = 0; i < valores.size(); i += cantidad) {

            Object[] params2 = {valores.get(i).getValorDiscreto().getValorDiscretoPK().getValor(), idVariable, anioEmision};
            List<CategoriaEstructuraNivel> deps = estructuraNivelService.findByNamedQuery("CategoriaEstructuraNivel.findByIdAndValorEstructura", params2);

            CategoriaEstructuraNivel aux = valores.get(i);
            aux.setDependientes(deps);
            coeficientes.add(aux);

        }

        columnsName = getColumnsName();
        coeficientesSeleccionados = new ArrayList<>();
        fuerte = new ValorDiscreto();
    }

    public void inicializarEdicion(ValorDiscretoPK id) {

        fuerte = valorDiscretoService.find(id);

        Object[] params = {idVariable, id.getAnio(), id.getValor()};
        coeficientesSeleccionados = estructuraNivelService.findByNamedQuery("CategoriaEstructuraNivel.findByGrupo", params);
        for (int i = 0; i < coeficientesSeleccionados.size(); i++) {
            if (i == coeficientesSeleccionados.size() - 1) {
                coeficientesSeleccionados.get(i).setNextOnEnter("actualizar-btn");
            } else {
                coeficientesSeleccionados.get(i).setNextOnEnter("coeficiente" + (i + 1));
            }
        }

    }

    public CategoriaEstructuraNivelService getEstructuraNivelService() {
        return estructuraNivelService;
    }

    public void setEstructuraNivelService(CategoriaEstructuraNivelService estructuraNivelService) {
        this.estructuraNivelService = estructuraNivelService;
    }

    public List<CategoriaEstructuraNivel> getCoeficientes() {
        return coeficientes;
    }

    public void setCoeficientes(List<CategoriaEstructuraNivel> coeficientes) {
        this.coeficientes = coeficientes;
    }

    public List<CategoriaEstructuraNivel> getCoeficientesSeleccionados() {
        return coeficientesSeleccionados;
    }

    public void setCoeficientesSeleccionados(List<CategoriaEstructuraNivel> coeficientesSeleccionados) {
        this.coeficientesSeleccionados = coeficientesSeleccionados;
    }

    public List<CategoriaEstructuraNivel> getValores() {
        return valores;
    }

    public void setValores(List<CategoriaEstructuraNivel> valores) {
        this.valores = valores;
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
        columnsName = new ArrayList<>();
        columnsName.add("A");
        columnsName.add("B");
        columnsName.add("C");
        columnsName.add("D");
        columnsName.add("E");
        columnsName.add("F");

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

    public int getIdVariable() {
        return idVariable;
    }

    public void setIdVariable(int idVariable) {
        this.idVariable = idVariable;
    }

    public List<SelectItem> getVariables() {
        variables = new ArrayList<>();
        variables.add(new SelectItem(1, "1 a 3 Pisos"));
        variables.add(new SelectItem(2, "4 a 5 Pisos"));
        variables.add(new SelectItem(3, "6 a 9 Pisos"));
        variables.add(new SelectItem(4, "Mas de 9 Pisos"));
        return variables;
    }

    public void setVariables(List<SelectItem> variables) {
        this.variables = variables;
    }

    public void editar() {
        try {

            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();

            RequestContext context = RequestContext.getCurrentInstance();

            valorDiscretoService.edit(fuerte);
            for (int i = 0; i < coeficientesSeleccionados.size(); i++) {
                estructuraNivelService.edit(coeficientesSeleccionados.get(i));
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
        cambiarAcabado();
    }

    public void cambiarAcabado() {

        Object[] params = {idVariable, anioEmision};
        valores = estructuraNivelService.findByNamedQuery("CategoriaEstructuraNivel.findById", params);
        Object[] params1 = {24, anioEmision};
        discretos = valorDiscretoService.findByNamedQuery("ValorDiscreto.findByVariable", params1);

        coeficientes = new ArrayList<>();
        int cantidad = discretos.size();

        for (int i = 0; i < valores.size(); i += cantidad) {

            Object[] params2 = {valores.get(i).getValorDiscreto().getValorDiscretoPK().getValor(), idVariable, anioEmision};
            List<CategoriaEstructuraNivel> deps = estructuraNivelService.findByNamedQuery("CategoriaEstructuraNivel.findByIdAndValorEstructura", params2);

            CategoriaEstructuraNivel aux = valores.get(i);
            aux.setDependientes(deps);
            coeficientes.add(aux);

        }
        columnsName = getColumnsName();
        coeficientesSeleccionados = new ArrayList<>();
        fuerte = new ValorDiscreto();

    }
}
