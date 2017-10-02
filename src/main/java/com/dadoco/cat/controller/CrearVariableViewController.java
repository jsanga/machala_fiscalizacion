/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Variable;
import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.TerrenoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author dfcalderio
 */
@Named(value = "crearVariableView")
@ViewScoped
public class CrearVariableViewController implements Serializable {

    
    @EJB
    private VariableService variableService;
   

   private Variable variableSeleccionada;
    
    private List<ValorDiscreto> valores;
    private ValorDiscreto valorSeleccionado;
    

    @PostConstruct
    public void init() {        
       
        valores = new ArrayList<ValorDiscreto>();
        variableSeleccionada = null;
        
    }

    public List<Variable> getVariables() {
        return variableService.findAll();
    }

  

    public Variable getVariableSeleccionada() {
        return variableSeleccionada;
    }

    public void setVariableSeleccionada(Variable variableSeleccionada) {
        this.variableSeleccionada = variableSeleccionada;
    }

    public List<ValorDiscreto> getValores() {
        return valores;
    }

    public void setValores(List<ValorDiscreto> valores) {
        this.valores = valores;
    }

    public ValorDiscreto getValorSeleccionado() {
        return valorSeleccionado;
    }

    public void setValorSeleccionado(ValorDiscreto valorSeleccionado) {
        this.valorSeleccionado = valorSeleccionado;
    }
    
    
    
    public void inicializarVariable(){
        variableSeleccionada = new Variable();
    }
    
    public void inicializarValor(){
        valorSeleccionado = new ValorDiscreto();
    }
    
    public void eliminarVariable(){
        if(variableSeleccionada!=null){
            try{
            
                variableService.remove(variableSeleccionada);
                JsfUtil.addSuccessMessage("Valor adicionado");
            }catch(Exception e)
            {
                JsfUtil.addErrorMessage(e, null);
            }
        }
    }
    

    public void crearVariable(){
        if(variableSeleccionada!=null){
            try{
            
                variableService.create(variableSeleccionada);
                JsfUtil.addSuccessMessage("Variable adicionada");
            }catch(Exception e)
            {
                JsfUtil.addErrorMessage(e, null);
            }
        }
    }

    

}
