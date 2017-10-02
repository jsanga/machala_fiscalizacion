/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dfcalderio
 */
@Embeddable
public class ImpuestoPredialPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_predio")
    private int idPredio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;

    public ImpuestoPredialPK() {
    }

    public ImpuestoPredialPK(int idPredio, short anio) {
        this.idPredio = idPredio;
        this.anio = anio;
    }

    public int getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(int idPredio) {
        this.idPredio = idPredio;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
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
        hash += (int) idPredio;
        hash += (int) anio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImpuestoPredialPK)) {
            return false;
        }
        ImpuestoPredialPK other = (ImpuestoPredialPK) object;
        if (this.idPredio != other.idPredio) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.ImpuestoPredialPK[ idPredio=" + idPredio + ", anio=" + anio + " ]";
    }

}
