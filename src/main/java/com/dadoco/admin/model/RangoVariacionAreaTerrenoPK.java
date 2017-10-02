/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.ren.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dcalderio
 */
@Embeddable
public class RangoVariacionAreaTerrenoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private short valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;

    public RangoVariacionAreaTerrenoPK() {
    }

    public RangoVariacionAreaTerrenoPK(short valor, short anio) {
        this.valor = valor;
        this.anio = anio;
    }

    public short getValor() {
        return valor;
    }

    public void setValor(short valor) {
        this.valor = valor;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) valor;
        hash += (int) anio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RangoVariacionAreaTerrenoPK)) {
            return false;
        }
        RangoVariacionAreaTerrenoPK other = (RangoVariacionAreaTerrenoPK) object;
        if (this.valor != other.valor) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.RangoVariacionAreaTerrenoPK[ valor=" + valor + ", anio=" + anio + " ]";
    }
    
}
