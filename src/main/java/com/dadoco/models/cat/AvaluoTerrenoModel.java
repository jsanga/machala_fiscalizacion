/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.models.cat;

import java.io.Serializable;

/**
 *
 * @author Joao Sanga
 */
public class AvaluoTerrenoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double superficie;
    private Double frente;
    private Double fondo;
    private Double factorSuperficie;
    private Double factorFrente;
    private Double factorFondo;
    private Double factorVariosFrentes;
    private Double factorCaracteristicasSuelo;
    private Double factorTopografia;
    private Double factorServicios;
    private Double factorServiciosComplementarios;
    private Double factorCapaRodadura;
    private Double factorTotal;
    private Double valorZonaHomogenea;
    private Integer idPredio;

    private Double avaluoTerreno;

    public AvaluoTerrenoModel(Integer idPredio, Double superficie, Double frente, Double fondo, Double factorSuperficie, Double factorFrente, Double factorFondo,
            Double factorVariosFrentes, Double factorCaracteristicasSuelo, Double factorTopografia, Double factorServicios, Double factorServiciosComplementarios,
            Double factorCapaRodadura, Double factorTotal, Double valorZonaHomogenea) {

        this.idPredio = idPredio;
        this.superficie = (double) Math.round(superficie * 100) / 100;
        this.frente = (double) Math.round(frente * 100) / 100;
        this.fondo = (double) Math.round(fondo * 100) / 100;
        this.factorSuperficie = (double) Math.round(factorSuperficie * 100) / 100;
        this.factorFrente = (double) Math.round(factorFrente * 100) / 100;
        this.factorFondo = (double) Math.round(factorFondo * 100) / 100;
        this.factorVariosFrentes = (double) Math.round(factorVariosFrentes * 100) / 100;
        this.factorCaracteristicasSuelo = (double) Math.round(factorCaracteristicasSuelo * 100) / 100;
        this.factorTopografia = (double) Math.round(factorTopografia * 100) / 100;
        this.factorServicios = (double) Math.round(factorServicios * 100) / 100;
        this.factorServiciosComplementarios = (double) Math.round(factorServiciosComplementarios * 100) / 100;
        this.factorCapaRodadura = (double) Math.round(factorCapaRodadura * 100) / 100;
        this.factorTotal = (double) Math.round(factorTotal * 100) / 100;
        this.valorZonaHomogenea = (double) Math.round(valorZonaHomogenea * 100) / 100;
        this.avaluoTerreno = (double) Math.round((this.superficie * this.valorZonaHomogenea * this.factorTotal) * 100) / 100;

    }

    public Double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public Double getFrente() {
        return frente;
    }

    public void setFrente(Double frente) {
        this.frente = frente;
    }

    public Double getFondo() {
        return fondo;
    }

    public void setFondo(Double fondo) {
        this.fondo = fondo;
    }

    public Double getFactorSuperficie() {
        return factorSuperficie;
    }

    public void setFactorSuperficie(Double factorSuperficie) {
        this.factorSuperficie = factorSuperficie;
    }

    public Double getFactorFrente() {
        return factorFrente;
    }

    public void setFactorFrente(Double factorFrente) {
        this.factorFrente = factorFrente;
    }

    public Double getFactorFondo() {
        return factorFondo;
    }

    public void setFactorFondo(Double factorFondo) {
        this.factorFondo = factorFondo;
    }

    public Double getFactorVariosFrentes() {
        return factorVariosFrentes;
    }

    public void setFactorVariosFrentes(Double factorVariosFrentes) {
        this.factorVariosFrentes = factorVariosFrentes;
    }

    public Double getFactorCaracteristicasSuelo() {
        return factorCaracteristicasSuelo;
    }

    public void setFactorCaracteristicasSuelo(Double factorCaracteristicasSuelo) {
        this.factorCaracteristicasSuelo = factorCaracteristicasSuelo;
    }

    public Double getFactorTopografia() {
        return factorTopografia;
    }

    public void setFactorTopografia(Double factorTopografia) {
        this.factorTopografia = factorTopografia;
    }

    public Double getFactorServicios() {
        return factorServicios;
    }

    public void setFactorServicios(Double factorServicios) {
        this.factorServicios = factorServicios;
    }

    public Double getFactorServiciosComplementarios() {
        return factorServiciosComplementarios;
    }

    public void setFactorServiciosComplementarios(Double factorServiciosComplementarios) {
        this.factorServiciosComplementarios = factorServiciosComplementarios;
    }

    public Double getFactorCapaRodadura() {
        return factorCapaRodadura;
    }

    public void setFactorCapaRodadura(Double factorCapaRodadura) {
        this.factorCapaRodadura = factorCapaRodadura;
    }

    public Double getFactorTotal() {
        return factorTotal;
    }

    public void setFactorTotal(Double factorTotal) {
        this.factorTotal = factorTotal;
    }

    public Double getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(Double avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public Double getValorZonaHomogenea() {
        return valorZonaHomogenea;
    }

    public void setValorZonaHomogenea(Double valorZonaHomogenea) {
        this.valorZonaHomogenea = valorZonaHomogenea;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

}
