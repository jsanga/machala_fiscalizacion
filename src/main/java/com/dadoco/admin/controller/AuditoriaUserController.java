/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.audit.lazymodel.IncidenciasLazy;
import com.dadoco.audit.model.TipoIncidencia;
import com.dadoco.common.service.ManagerService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author Joao Sanga
 */
@Named(value = "auditoriaUserController")
@ViewScoped
public class AuditoriaUserController implements Serializable {
    
    @EJB
    private ManagerService aclServices;
    
    private IncidenciasLazy incidencias;
    private List<TipoIncidencia> tiposIncidencias;
    private TipoIncidencia tipo;
    private String tipoString;
    
    @PostConstruct
    public void init(){
        try{
            incidencias = new IncidenciasLazy();
            tiposIncidencias = aclServices.findAllByOrder(TipoIncidencia.class, "descripcion", Boolean.FALSE);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public IncidenciasLazy getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(IncidenciasLazy incidencias) {
        this.incidencias = incidencias;
    }    

    public List<TipoIncidencia> getTiposIncidencias() {
        return tiposIncidencias;
    }

    public void setTiposIncidencias(List<TipoIncidencia> tiposIncidencias) {
        this.tiposIncidencias = tiposIncidencias;
    }

    public TipoIncidencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoIncidencia tipo) {
        this.tipo = tipo;
    }

    public String getTipoString() {
        return tipoString;
    }

    public void setTipoString(String tipoString) {
        this.tipoString = tipoString;
    }
    
}
