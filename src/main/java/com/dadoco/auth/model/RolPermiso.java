/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "auth_rol_permiso", catalog = "catastro", schema = "public")
@NamedQueries({
    @NamedQuery(name = "RolPermiso.findAll", query = "SELECT r FROM RolPermiso r"),
    @NamedQuery(name = "RolPermiso.findByIdRol", query = "SELECT r FROM RolPermiso r WHERE r.rolPermisoPK.idRol = :idRol"),
    @NamedQuery(name = "RolPermiso.findByIdPermiso", query = "SELECT r FROM RolPermiso r WHERE r.rolPermisoPK.idPermiso = :idPermiso")})
public class RolPermiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolPermisoPK rolPermisoPK;
    
    @JoinColumn(name = "id_rol", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;

    @JoinColumn(name = "id_permiso", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Permiso permiso;

    public RolPermiso() {
    }

    public RolPermiso(RolPermisoPK rolPermisoPK) {
        this.rolPermisoPK = rolPermisoPK;
    }

    public RolPermiso(int idRol, int idPermiso) {
        this.rolPermisoPK = new RolPermisoPK(idRol, idPermiso);
    }

    public RolPermisoPK getRolPermisoPK() {
        return rolPermisoPK;
    }

    public void setRolPermisoPK(RolPermisoPK rolPermisoPK) {
        this.rolPermisoPK = rolPermisoPK;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolPermisoPK != null ? rolPermisoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermiso)) {
            return false;
        }
        RolPermiso other = (RolPermiso) object;
        if ((this.rolPermisoPK == null && other.rolPermisoPK != null) || (this.rolPermisoPK != null && !this.rolPermisoPK.equals(other.rolPermisoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.RolPermiso[ rolPermisoPK=" + rolPermisoPK + " ]";
    }
    
}
