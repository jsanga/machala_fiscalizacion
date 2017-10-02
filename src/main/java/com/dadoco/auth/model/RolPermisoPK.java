/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author dfcalderio
 */
@Embeddable
public class RolPermisoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_rol", nullable = false)
    private int idRol;
    @Basic(optional = false)
    @Column(name = "id_permiso", nullable = false)
    private int idPermiso;

    public RolPermisoPK() {
    }

    public RolPermisoPK(int idRol, int idPermiso) {
        this.idRol = idRol;
        this.idPermiso = idPermiso;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRol;
        hash += (int) idPermiso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermisoPK)) {
            return false;
        }
        RolPermisoPK other = (RolPermisoPK) object;
        if (this.idRol != other.idRol) {
            return false;
        }
        if (this.idPermiso != other.idPermiso) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.RolPermisoPK[ idRol=" + idRol + ", idPermiso=" + idPermiso + " ]";
    }
    
}
