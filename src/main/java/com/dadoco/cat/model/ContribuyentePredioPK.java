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
 * @author dairon
 */
@Embeddable
public class ContribuyentePredioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_contribuyente")
    private int idContribuyente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_predio")
    private int idPredio;

    public ContribuyentePredioPK() {
    }

    public ContribuyentePredioPK(int idContribuyente, int idPredio) {
        this.idContribuyente = idContribuyente;
        this.idPredio = idPredio;
    }

    public int getIdContribuyente() {
        return idContribuyente;
    }

    public void setIdContribuyente(int idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public int getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(int idPredio) {
        this.idPredio = idPredio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idContribuyente;
        hash += (int) idPredio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContribuyentePredioPK)) {
            return false;
        }
        ContribuyentePredioPK other = (ContribuyentePredioPK) object;
        if (this.idContribuyente != other.idContribuyente) {
            return false;
        }
        if (this.idPredio != other.idPredio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.ContribuyentePredioPK[ idContribuyente=" + idContribuyente + ", idPredio=" + idPredio + " ]";
    }
    
}
