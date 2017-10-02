/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_factor_zona_aumento_reduccion")
@NamedQueries({
    @NamedQuery(name = "FactorZonaAumentoReduccion.findAll", query = "SELECT f FROM FactorZonaAumentoReduccion f WHERE f.factorZonaAumentoReduccionPK.anio = ?1")
    , @NamedQuery(name = "FactorZonaAumentoReduccion.findById", query = "SELECT f FROM FactorZonaAumentoReduccion f WHERE f.factorZonaAumentoReduccionPK.id = :id")
    , @NamedQuery(name = "FactorZonaAumentoReduccion.findByAnio", query = "SELECT f FROM FactorZonaAumentoReduccion f WHERE f.factorZonaAumentoReduccionPK.anio = :anio")
    , @NamedQuery(name = "FactorZonaAumentoReduccion.findByFactor", query = "SELECT f FROM FactorZonaAumentoReduccion f WHERE f.factor = :factor")})
public class FactorZonaAumentoReduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FactorZonaAumentoReduccionPK factorZonaAumentoReduccionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "factor")
    private float factor;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ZonaAumentoReduccion zonaAumentoReduccion;

    public FactorZonaAumentoReduccion() {
    }

    public FactorZonaAumentoReduccion(FactorZonaAumentoReduccionPK factorZonaAumentoReduccionPK) {
        this.factorZonaAumentoReduccionPK = factorZonaAumentoReduccionPK;
    }

    public FactorZonaAumentoReduccion(FactorZonaAumentoReduccionPK factorZonaAumentoReduccionPK, float factor) {
        this.factorZonaAumentoReduccionPK = factorZonaAumentoReduccionPK;
        this.factor = factor;
    }

    public FactorZonaAumentoReduccion(int id, short anio) {
        this.factorZonaAumentoReduccionPK = new FactorZonaAumentoReduccionPK(id, anio);
    }

    public FactorZonaAumentoReduccionPK getFactorZonaAumentoReduccionPK() {
        return factorZonaAumentoReduccionPK;
    }

    public void setFactorZonaAumentoReduccionPK(FactorZonaAumentoReduccionPK factorZonaAumentoReduccionPK) {
        this.factorZonaAumentoReduccionPK = factorZonaAumentoReduccionPK;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public ZonaAumentoReduccion getZonaAumentoReduccion() {
        return zonaAumentoReduccion;
    }

    public void setZonaAumentoReduccion(ZonaAumentoReduccion zonaAumentoReduccion) {
        this.zonaAumentoReduccion = zonaAumentoReduccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factorZonaAumentoReduccionPK != null ? factorZonaAumentoReduccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FactorZonaAumentoReduccion)) {
            return false;
        }
        FactorZonaAumentoReduccion other = (FactorZonaAumentoReduccion) object;
        if ((this.factorZonaAumentoReduccionPK == null && other.factorZonaAumentoReduccionPK != null) || (this.factorZonaAumentoReduccionPK != null && !this.factorZonaAumentoReduccionPK.equals(other.factorZonaAumentoReduccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return factorZonaAumentoReduccionPK.toString();
    }
    
}
