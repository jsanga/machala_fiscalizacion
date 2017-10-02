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
public class InfoBloque {

    private int numeroBloque;
    private int numeroPisos;
    private float areaTotal;
    private String estructura;
    private float valorBase;
    private float coefEstNivel;
    private float avaluoEstructura;
    private String sobrepiso;
    private float coefSobrepiso;
    private String revestimiento;
    private float coefRevestimiento;
    private String cubierta;
    private float coefCubierta;
    private String ventanas;
    private float coefVentanas;
    private float avaluoAcabados;
    private String edad;
    private String estado;
    private float coefEdadEstado;
    private float avaluo;
    private float coefAcabados;
    
    public InfoBloque(int numeroBloque, int numeroPisos, float areaTotal, String estructura, float valorBase, float coefEstNivel, float avaluoEstructura, String sobrepiso, float coefSobrepiso, String revestimiento, float coefRevestimiento, String cubierta, float coefCubierta, String ventanas, float coefVentanas, float avaluoAcabados, String edad, String estado, float coefEdadEstado, float avaluo, float coefAcabados) {
        this.numeroBloque = numeroBloque;
        this.numeroPisos = numeroPisos;
        this.areaTotal = areaTotal;
        this.estructura = estructura;
        this.valorBase = valorBase;
        this.coefEstNivel = coefEstNivel;
        this.avaluoEstructura = avaluoEstructura;
        this.sobrepiso = sobrepiso;
        this.coefSobrepiso = coefSobrepiso;
        this.revestimiento = revestimiento;
        this.coefRevestimiento = coefRevestimiento;
        this.cubierta = cubierta;
        this.coefCubierta = coefCubierta;
        this.ventanas = ventanas;
        this.coefVentanas = coefVentanas;
        this.avaluoAcabados = avaluoAcabados;
        this.edad = edad;
        this.estado = estado;
        this.coefEdadEstado = coefEdadEstado;
        this.avaluo = avaluo;
        this.coefAcabados = coefAcabados;
    }
    
    public InfoBloque() {
        this.numeroBloque = 0;
        this.numeroPisos = 0;
        this.areaTotal = 0;
        this.estructura = "0";
        this.valorBase = 0;
        this.coefEstNivel = 0;
        this.avaluoEstructura = 0;
        this.sobrepiso = "0";
        this.coefSobrepiso = 0;
        this.revestimiento = "0";
        this.coefRevestimiento = 0;
        this.cubierta = "0";
        this.coefCubierta = 0;
        this.ventanas = "0";
        this.coefVentanas = 0;
        this.avaluoAcabados = 0;
        this.edad = "0";
        this.estado = "0";
        this.coefEdadEstado = 0;
        this.avaluo = 0;
        this.coefAcabados = 0;
    }

    public int getNumeroBloque() {
        return numeroBloque;
    }

    public void setNumeroBloque(int numeroBloque) {
        this.numeroBloque = numeroBloque;
    }

    public int getNumeroPisos() {
        return numeroPisos;
    }

    public void setNumeroPisos(int numeroPisos) {
        this.numeroPisos = numeroPisos;
    }

    public float getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(float areaTotal) {
        this.areaTotal = areaTotal;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public float getValorBase() {
        return valorBase;
    }

    public void setValorBase(float valorBase) {
        this.valorBase = valorBase;
    }

    public float getCoefEstNivel() {
        return coefEstNivel;
    }

    public void setCoefEstNivel(float coefEstNivel) {
        this.coefEstNivel = coefEstNivel;
    }

    public float getAvaluoEstructura() {
        return avaluoEstructura;
    }

    public void setAvaluoEstructura(float avaluoEstructura) {
        this.avaluoEstructura = avaluoEstructura;
    }

    public String getSobrepiso() {
        return sobrepiso;
    }

    public void setSobrepiso(String sobrepiso) {
        this.sobrepiso = sobrepiso;
    }

    public float getCoefSobrepiso() {
        return coefSobrepiso;
    }

    public void setCoefSobrepiso(float coefSobrepiso) {
        this.coefSobrepiso = coefSobrepiso;
    }

    public String getRevestimiento() {
        return revestimiento;
    }

    public void setRevestimiento(String revestimiento) {
        this.revestimiento = revestimiento;
    }

    public float getCoefRevestimiento() {
        return coefRevestimiento;
    }

    public void setCoefRevestimiento(float coefRevestimiento) {
        this.coefRevestimiento = coefRevestimiento;
    }

    public String getCubierta() {
        return cubierta;
    }

    public void setCubierta(String cubierta) {
        this.cubierta = cubierta;
    }

    public float getCoefCubierta() {
        return coefCubierta;
    }

    public void setCoefCubierta(float coefCubierta) {
        this.coefCubierta = coefCubierta;
    }

    public String getVentanas() {
        return ventanas;
    }

    public void setVentanas(String ventanas) {
        this.ventanas = ventanas;
    }

    public float getCoefVentanas() {
        return coefVentanas;
    }

    public void setCoefVentanas(float coefVentanas) {
        this.coefVentanas = coefVentanas;
    }

    public float getAvaluoAcabados() {
        return avaluoAcabados;
    }

    public void setAvaluoAcabados(float avaluoAcabados) {
        this.avaluoAcabados = avaluoAcabados;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getCoefEdadEstado() {
        return coefEdadEstado;
    }

    public void setCoefEdadEstado(float coefEdadEstado) {
        this.coefEdadEstado = coefEdadEstado;
    }

    public float getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(float avaluo) {
        this.avaluo = avaluo;
    }

    public float getCoefAcabados() {
        return coefAcabados;
    }

    public void setCoefAcabados(float coefAcabados) {
        this.coefAcabados = coefAcabados;
    }

    
}
