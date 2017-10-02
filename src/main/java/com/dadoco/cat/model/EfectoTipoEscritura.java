/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_efecto_tipo_escritura")
@NamedQueries({
    @NamedQuery(name = "EfectoTipoEscritura.findAll", query = "SELECT e FROM EfectoTipoEscritura e"),
    @NamedQuery(name = "EfectoTipoEscritura.findById", query = "SELECT e FROM EfectoTipoEscritura e WHERE e.id = :id"),
    @NamedQuery(name = "EfectoTipoEscritura.findByEfecto", query = "SELECT e FROM EfectoTipoEscritura e WHERE e.efecto = :efecto")})
public class EfectoTipoEscritura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "efecto")
    private String efecto;

    public EfectoTipoEscritura() {
    }

    public EfectoTipoEscritura(Integer id) {
        this.id = id;
    }

    public EfectoTipoEscritura(Integer id, String efecto) {
        this.id = id;
        this.efecto = efecto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EfectoTipoEscritura)) {
            return false;
        }
        EfectoTipoEscritura other = (EfectoTipoEscritura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.auth.model.EfectoTipoEscritura[ id=" + id + " ]";
    }
    
}
