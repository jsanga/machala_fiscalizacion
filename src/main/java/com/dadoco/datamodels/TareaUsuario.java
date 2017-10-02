/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.datamodels;

import com.dadoco.flow.model.HtTramite;
import java.io.Serializable;
import org.activiti.engine.task.Task;

/**
 *
 * @author dadoco
 */
public class TareaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Task tarea;
    private HtTramite tramite;
    private Boolean seleccionada;
    private String descripcionTarea;
    
    public TareaUsuario(){
        seleccionada = false;
    }

    public Task getTarea() {
        return tarea;
    }

    public void setTarea(Task tarea) {
        this.tarea = tarea;
    }

    public HtTramite getTramite() {
        return tramite;
    }

    public void setTramite(HtTramite tramite) {
        this.tramite = tramite;
    }

    public Boolean getSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(Boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }
    
}
