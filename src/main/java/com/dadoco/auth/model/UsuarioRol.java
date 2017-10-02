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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "auth_usuario_rol", catalog = "catastro", schema = "public")
@NamedQueries({
    @NamedQuery(name = "UsuarioRol.findAll", query = "SELECT u FROM UsuarioRol u"),
    @NamedQuery(name = "UsuarioRol.findByIdUsuario", query = "SELECT u FROM UsuarioRol u WHERE u.usuarioRolPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "UsuarioRol.findByIdRol", query = "SELECT u FROM UsuarioRol u WHERE u.usuarioRolPK.idRol = :idRol")})
public class UsuarioRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioRolPK usuarioRolPK;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "id_rol", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;

    public UsuarioRol() {
    }

    public UsuarioRol(UsuarioRolPK usuarioRolPK) {
        this.usuarioRolPK = usuarioRolPK;
    }

    public UsuarioRol(int idUsuario, int idRol) {
        this.usuarioRolPK = new UsuarioRolPK(idUsuario, idRol);
    }

    public UsuarioRolPK getUsuarioRolPK() {
        return usuarioRolPK;
    }

    public void setUsuarioRolPK(UsuarioRolPK usuarioRolPK) {
        this.usuarioRolPK = usuarioRolPK;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioRolPK != null ? usuarioRolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRol)) {
            return false;
        }
        UsuarioRol other = (UsuarioRol) object;
        if ((this.usuarioRolPK == null && other.usuarioRolPK != null) || (this.usuarioRolPK != null && !this.usuarioRolPK.equals(other.usuarioRolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.UsuarioRol[ usuarioRolPK=" + usuarioRolPK + " ]";
    }
    
}
