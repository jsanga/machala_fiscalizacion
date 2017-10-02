/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.models.cat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Ing. Carlos Loor
 */
public class AvaluoConstruccionModel implements Serializable {

    private Integer edadConstruccion;
    private Integer vidaUtil;
    private Integer clase;
    private Integer estructura;
    private Integer niveles;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal avaluoConstruccion;

    public AvaluoConstruccionModel() {
    }

    public AvaluoConstruccionModel(Integer edadConstruccion, Integer vidaUtil, Integer clase, Integer estructura, Integer niveles, BigDecimal coefClase, BigDecimal y) {
        this.edadConstruccion = edadConstruccion;
        this.vidaUtil = vidaUtil;
        this.clase = clase;
        this.estructura = estructura;
        this.niveles = niveles;
        this.x = BigDecimal.valueOf((edadConstruccion.doubleValue() / vidaUtil.doubleValue())).multiply(new BigDecimal(100)).setScale(2, RoundingMode.CEILING);
        this.y = y;
    }

    public Integer getEdadConstruccion() {
        return edadConstruccion;
    }

    public void setEdadConstruccion(Integer edadConstruccion) {
        this.edadConstruccion = edadConstruccion;
    }

    public Integer getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(Integer vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public Integer getClase() {
        return clase;
    }

    public void setClase(Integer clase) {
        this.clase = clase;
    }

    public Integer getEstructura() {
        return estructura;
    }

    public void setEstructura(Integer estructura) {
        this.estructura = estructura;
    }

    public Integer getNiveles() {
        return niveles;
    }

    public void setNiveles(Integer niveles) {
        this.niveles = niveles;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getAvaluoConstruccion() {
        return avaluoConstruccion;
    }

    public void setAvaluoConstruccion(BigDecimal avaluoConstruccion) {
        this.avaluoConstruccion = avaluoConstruccion;
    }

}
