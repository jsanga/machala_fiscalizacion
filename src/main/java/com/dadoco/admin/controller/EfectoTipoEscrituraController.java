/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.EfectoTipoEscrituraService;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.EfectoTipoEscritura;
import com.dadoco.admin.service.ContribuyenteService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "efectoTipoEscritura")
@ViewScoped
public class EfectoTipoEscrituraController implements Serializable {
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EfectoTipoEscrituraController.class);

    @EJB
    private EfectoTipoEscrituraService efectoService;
    
    private List<EfectoTipoEscritura> items;
    
    private EfectoTipoEscritura seleccion;
    
    @PostConstruct
    public void init(){
        items = efectoService.findAll();
        seleccion = new EfectoTipoEscritura();
    }
    
    
    

    public EfectoTipoEscrituraService getEfectoService() {
        return efectoService;
    }

    public void setEfectoService(EfectoTipoEscrituraService efectoService) {
        this.efectoService = efectoService;
    }

    public List<EfectoTipoEscritura> getItems() {
        return items;
    }

    public void setItems(List<EfectoTipoEscritura> items) {
        this.items = items;
    }

    public EfectoTipoEscritura getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(EfectoTipoEscritura seleccion) {
        this.seleccion = seleccion;
    }

    
        
    
    
}
