/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

/**
 *
 * @author dfcalderio
 */
public class InfoInfraestructura {

    private String descripcion;
    private String existencia;
    private float coeficiente;
    private boolean bolt;

    public InfoInfraestructura(String descripcion, String existencia, float coeficiente, boolean bolt) {
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.coeficiente = coeficiente;
        this.bolt = bolt;
    }
    public InfoInfraestructura(String descripcion, String existencia, float coeficiente) {
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.coeficiente = coeficiente;
        this.bolt = false;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public float getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(float coeficiente) {
        this.coeficiente = coeficiente;
    }

    public boolean isBolt() {
        return bolt;
    }

    public void setBolt(boolean bolt) {
        this.bolt = bolt;
    }

    
}
