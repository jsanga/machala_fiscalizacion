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
public class UsuarioRolPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;
    @Basic(optional = false)
    @Column(name = "id_rol", nullable = false)
    private int idRol;

    public UsuarioRolPK() {
    }

    public UsuarioRolPK(int idUsuario, int idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUsuario;
        hash += (int) idRol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRolPK)) {
            return false;
        }
        UsuarioRolPK other = (UsuarioRolPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idRol != other.idRol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.UsuarioRolPK[ idUsuario=" + idUsuario + ", idRol=" + idRol + " ]";
    }
    
}
