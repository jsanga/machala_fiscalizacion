/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Embeddable
public class CategoriaValorPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "id_categoria")
    private String idCategoria;

    public CategoriaValorPK() {
    }

    public CategoriaValorPK(short anio, String idCategoria) {
        this.anio = anio;
        this.idCategoria = idCategoria;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) anio;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaValorPK)) {
            return false;
        }
        CategoriaValorPK other = (CategoriaValorPK) object;
        if (this.anio != other.anio) {
            return false;
        }
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.CategoriaValorPK[ anio=" + anio + ", idCategoria=" + idCategoria + " ]";
    }

}
