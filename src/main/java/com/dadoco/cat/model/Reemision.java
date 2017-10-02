/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import javax.annotation.PostConstruct;

public class Reemision {

    private long id;
    private String documento;
    private short docAnio;
    private String concepto;
    private float subtotal;
    private float saldo;
    private boolean bloqueado;
    private String nuevoDocumento;
    private String estado;
    private float nuevoSubtotal;

    public Reemision() {
    }

    public Reemision(long id, String documento, short docAnio, String concepto, float subtotal, float saldo, String estado, boolean bloqueado, String nuevoDocumento, float nuevoSubtotal) {
        this.id = id;
        this.documento = documento;
        this.docAnio = docAnio;
        this.concepto = concepto;
        this.subtotal = subtotal;
        this.saldo = saldo;
        this.estado = estado;
        this.bloqueado = bloqueado;
        this.nuevoDocumento = nuevoDocumento;
        this.nuevoSubtotal = nuevoSubtotal;
    }

    @PostConstruct
    public void init() {

    }

    public float getNuevoSubtotal() {
        return nuevoSubtotal;
    }

    public void setNuevoSubtotal(float nuevoSubtotal) {
        this.nuevoSubtotal = nuevoSubtotal;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public short getDocAnio() {
        return docAnio;
    }

    public void setDocAnio(short docAnio) {
        this.docAnio = docAnio;
    }

    public String getNuevoDocumento() {
        return nuevoDocumento;
    }

    public void setNuevoDocumento(String nuevoDocumento) {
        this.nuevoDocumento = nuevoDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
