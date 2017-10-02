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
public class CategoriaEstructuraNivelPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_variable_estructura")
    private int idVariableEstructura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_estructura")
    private short valorEstructura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "id_categoria")
    private String idCategoria;

    public CategoriaEstructuraNivelPK() {
    }

    public CategoriaEstructuraNivelPK(int id, int idVariableEstructura, short valorEstructura, short anio, String idCategoria) {
        this.id = id;
        this.idVariableEstructura = idVariableEstructura;
        this.valorEstructura = valorEstructura;
        this.anio = anio;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVariableEstructura() {
        return idVariableEstructura;
    }

    public void setIdVariableEstructura(int idVariableEstructura) {
        this.idVariableEstructura = idVariableEstructura;
    }

    public short getValorEstructura() {
        return valorEstructura;
    }

    public void setValorEstructura(short valorEstructura) {
        this.valorEstructura = valorEstructura;
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
        hash += (int) id;
        hash += (int) idVariableEstructura;
        hash += (int) valorEstructura;
        hash += (int) anio;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaEstructuraNivelPK)) {
            return false;
        }
        CategoriaEstructuraNivelPK other = (CategoriaEstructuraNivelPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.idVariableEstructura != other.idVariableEstructura) {
            return false;
        }
        if (this.valorEstructura != other.valorEstructura) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        return !((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria)));
    }

    @Override
    public String toString() {
        return "id=" + id + ", idVariableEstructura=" + idVariableEstructura + ", valorEstructura=" + valorEstructura + ", anio=" + anio + ", idCategoria=" + idCategoria;
    }

}
