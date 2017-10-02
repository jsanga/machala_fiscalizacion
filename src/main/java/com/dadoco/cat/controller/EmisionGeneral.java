/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

/**
 *
 * @author dairon
 */
public class EmisionGeneral {

    private int idPredio;
    private int anio;
    private int anioAnterior;
    private double areaTerreno;
    private double areaTerrenoAnterior;
    private double valorMetroTerreno;
    private double valorMetroTerrenoAnterior;
    private double avaluoTerreno;
    private double avaluoTerrenoAnterior;
    private double areaEdificacion;
    private double areaEdificacionAnterior;
    private double avaluoEdificacion;
    private double avaluoEdificacionAnterior;
    private double avaluoComplementarias;
    private double avaluoComplementariasAnterior;
    private double avaluoPredial;
    private double avaluoPredialAnterior;
    private double exencion;
    private double exencionAnterior;
    private double bandaImpositiva;
    private double bandaImpositivaAnterior;
    private double impuestoPredial;
    private double impuestoPredialAnterior;
    private double basura;
    private double basuraAnterior;
    private double rpj;
    private double rpjAnterior;
    private double cem;
    private double cemAnterior;
    private double noEdificado;
    private double noEdificadoAnterior;
    private double total;
    private double totalAnterior;

    public EmisionGeneral() {
    }

    public int getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(int idPredio) {
        this.idPredio = idPredio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getAnioAnterior() {
        return anioAnterior;
    }

    public void setAnioAnterior(int anioAnterior) {
        this.anioAnterior = anioAnterior;
    }

    public double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public double getAreaTerrenoAnterior() {
        return areaTerrenoAnterior;
    }

    public void setAreaTerrenoAnterior(double areaTerrenoAnterior) {
        this.areaTerrenoAnterior = areaTerrenoAnterior;
    }

    public double getValorMetroTerreno() {
        return valorMetroTerreno;
    }

    public void setValorMetroTerreno(double valorMetroTerreno) {
        this.valorMetroTerreno = valorMetroTerreno;
    }

    public double getValorMetroTerrenoAnterior() {
        return valorMetroTerrenoAnterior;
    }

    public void setValorMetroTerrenoAnterior(double valorMetroTerrenoAnterior) {
        this.valorMetroTerrenoAnterior = valorMetroTerrenoAnterior;
    }

    public double getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(double avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public double getAvaluoTerrenoAnterior() {
        return avaluoTerrenoAnterior;
    }

    public void setAvaluoTerrenoAnterior(double avaluoTerrenoAnterior) {
        this.avaluoTerrenoAnterior = avaluoTerrenoAnterior;
    }

    public double getAreaEdificacion() {
        return areaEdificacion;
    }

    public void setAreaEdificacion(double areaEdificacion) {
        this.areaEdificacion = areaEdificacion;
    }

    public double getAreaEdificacionAnterior() {
        return areaEdificacionAnterior;
    }

    public void setAreaEdificacionAnterior(double areaEdificacionAnterior) {
        this.areaEdificacionAnterior = areaEdificacionAnterior;
    }

    public double getAvaluoEdificacion() {
        return avaluoEdificacion;
    }

    public void setAvaluoEdificacion(double avaluoEdificacion) {
        this.avaluoEdificacion = avaluoEdificacion;
    }

    public double getAvaluoEdificacionAnterior() {
        return avaluoEdificacionAnterior;
    }

    public void setAvaluoEdificacionAnterior(double avaluoEdificacionAnterior) {
        this.avaluoEdificacionAnterior = avaluoEdificacionAnterior;
    }

    public double getAvaluoComplementarias() {
        return avaluoComplementarias;
    }

    public void setAvaluoComplementarias(double avaluoComplementarias) {
        this.avaluoComplementarias = avaluoComplementarias;
    }

    public double getAvaluoComplementariasAnterior() {
        return avaluoComplementariasAnterior;
    }

    public void setAvaluoComplementariasAnterior(double avaluoComplementariasAnterior) {
        this.avaluoComplementariasAnterior = avaluoComplementariasAnterior;
    }

    public double getAvaluoPredial() {
        return avaluoPredial;
    }

    public void setAvaluoPredial(double avaluoPredial) {
        this.avaluoPredial = avaluoPredial;
    }

    public double getAvaluoPredialAnterior() {
        return avaluoPredialAnterior;
    }

    public void setAvaluoPredialAnterior(double avaluoPredialAnterior) {
        this.avaluoPredialAnterior = avaluoPredialAnterior;
    }

    public double getExencion() {
        return exencion;
    }

    public void setExencion(double exencion) {
        this.exencion = exencion;
    }

    public double getExencionAnterior() {
        return exencionAnterior;
    }

    public void setExencionAnterior(double exencionAnterior) {
        this.exencionAnterior = exencionAnterior;
    }

    public double getBandaImpositiva() {
        return bandaImpositiva;
    }

    public void setBandaImpositiva(double bandaImpositiva) {
        this.bandaImpositiva = bandaImpositiva;
    }

    public double getBandaImpositivaAnterior() {
        return bandaImpositivaAnterior;
    }

    public void setBandaImpositivaAnterior(double bandaImpositivaAnterior) {
        this.bandaImpositivaAnterior = bandaImpositivaAnterior;
    }

    public double getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(double impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }

    public double getImpuestoPredialAnterior() {
        return impuestoPredialAnterior;
    }

    public void setImpuestoPredialAnterior(double impuestoPredialAnterior) {
        this.impuestoPredialAnterior = impuestoPredialAnterior;
    }

    public double getBasura() {
        return basura;
    }

    public void setBasura(double basura) {
        this.basura = basura;
    }

    public double getBasuraAnterior() {
        return basuraAnterior;
    }

    public void setBasuraAnterior(double basuraAnterior) {
        this.basuraAnterior = basuraAnterior;
    }

    public double getRpj() {
        return rpj;
    }

    public void setRpj(double rpj) {
        this.rpj = rpj;
    }

    public double getRpjAnterior() {
        return rpjAnterior;
    }

    public void setRpjAnterior(double rpjAnterior) {
        this.rpjAnterior = rpjAnterior;
    }

    public double getCem() {
        return cem;
    }

    public void setCem(double cem) {
        this.cem = cem;
    }

    public double getCemAnterior() {
        return cemAnterior;
    }

    public void setCemAnterior(double cemAnterior) {
        this.cemAnterior = cemAnterior;
    }

    public double getNoEdificado() {
        return noEdificado;
    }

    public void setNoEdificado(double noEdificado) {
        this.noEdificado = noEdificado;
    }

    public double getNoEdificadoAnterior() {
        return noEdificadoAnterior;
    }

    public void setNoEdificadoAnterior(double noEdificadoAnterior) {
        this.noEdificadoAnterior = noEdificadoAnterior;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalAnterior() {
        return totalAnterior;
    }

    public void setTotalAnterior(double totalAnterior) {
        this.totalAnterior = totalAnterior;
    }

}
