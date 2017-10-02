/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.ren.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dcalderio
 */
@Entity
@Table(name = "coef_variacion_areas")
@NamedQueries({
    @NamedQuery(name = "RangoVariacionAreaTerreno.findAll", query = "SELECT r FROM RangoVariacionAreaTerreno r")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByValor", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.rangoVariacionAreaTerrenoPK.valor = :valor")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByAnio", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.rangoVariacionAreaTerrenoPK.anio = ?1 AND r.estado = 0 ORDER BY r.rangoVariacionAreaTerrenoPK.valor")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByAreaMin", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.areaMin = :areaMin")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByAreaMax", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.areaMax = :areaMax")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByTexto", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.texto = :texto")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByCoef", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.coef = :coef")
    , @NamedQuery(name = "RangoVariacionAreaTerreno.findByEstado", query = "SELECT r FROM RangoVariacionAreaTerreno r WHERE r.estado = :estado")})
public class RangoVariacionAreaTerreno implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RangoVariacionAreaTerrenoPK rangoVariacionAreaTerrenoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "area_min")
    private float areaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "area_max")
    private float areaMax;
    @Size(max = 200)
    @Column(name = "texto")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "coef")
    private float coef;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;

    public RangoVariacionAreaTerreno() {
    }

    public RangoVariacionAreaTerreno(RangoVariacionAreaTerrenoPK rangoVariacionAreaTerrenoPK) {
        this.rangoVariacionAreaTerrenoPK = rangoVariacionAreaTerrenoPK;
    }

    public RangoVariacionAreaTerreno(RangoVariacionAreaTerrenoPK rangoVariacionAreaTerrenoPK, float areaMin, float areaMax, float coef, short estado) {
        this.rangoVariacionAreaTerrenoPK = rangoVariacionAreaTerrenoPK;
        this.areaMin = areaMin;
        this.areaMax = areaMax;
        this.coef = coef;
        this.estado = estado;
    }

    public RangoVariacionAreaTerreno(short valor, short anio) {
        this.rangoVariacionAreaTerrenoPK = new RangoVariacionAreaTerrenoPK(valor, anio);
    }

    public RangoVariacionAreaTerrenoPK getRangoVariacionAreaTerrenoPK() {
        return rangoVariacionAreaTerrenoPK;
    }

    public void setRangoVariacionAreaTerrenoPK(RangoVariacionAreaTerrenoPK rangoVariacionAreaTerrenoPK) {
        this.rangoVariacionAreaTerrenoPK = rangoVariacionAreaTerrenoPK;
    }

    public float getAreaMin() {
        return areaMin;
    }

    public void setAreaMin(float areaMin) {
        this.areaMin = areaMin;
    }

    public float getAreaMax() {
        return areaMax;
    }

    public void setAreaMax(float areaMax) {
        this.areaMax = areaMax;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public float getCoef() {
        return coef;
    }

    public void setCoef(float coef) {
        this.coef = coef;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rangoVariacionAreaTerrenoPK != null ? rangoVariacionAreaTerrenoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RangoVariacionAreaTerreno)) {
            return false;
        }
        RangoVariacionAreaTerreno other = (RangoVariacionAreaTerreno) object;
        return !((this.rangoVariacionAreaTerrenoPK == null && other.rangoVariacionAreaTerrenoPK != null) || (this.rangoVariacionAreaTerrenoPK != null && !this.rangoVariacionAreaTerrenoPK.equals(other.rangoVariacionAreaTerrenoPK)));
    }

    @Override
    public String toString() {
        if (this.areaMin == 5000) {
            return "< a " + this.areaMin;
        }
        return "< a " + this.areaMin + " y <= " + this.areaMax;
    }

}
