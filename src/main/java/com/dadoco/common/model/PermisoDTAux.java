/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.model;

/**
 *
 * @author JoaoIsrael
 */
public class PermisoDTAux {
    
    private String descripcion;
    private String profesional;
    private String entidadCompetente;
    
    public PermisoDTAux(){
        
    }
    
    public PermisoDTAux(String s1, String s2, String s3){
        descripcion = s1;
        profesional = s2;
        entidadCompetente = s3;
    }

    public String getProfesional() {
        return profesional;
    }

    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

    public String getEntidadCompetente() {
        return entidadCompetente;
    }

    public void setEntidadCompetente(String entidadCompetente) {
        this.entidadCompetente = entidadCompetente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
