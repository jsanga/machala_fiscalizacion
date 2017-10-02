/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_fotos_inspeccion")
@NamedQueries({
    @NamedQuery(name = "FotosInspeccion.findAll", query = "SELECT f FROM FotosInspeccion f"),
    @NamedQuery(name = "FotosInspeccion.findById", query = "SELECT f FROM FotosInspeccion f WHERE f.id = :id"),
    @NamedQuery(name = "FotosInspeccion.findByRuta", query = "SELECT f FROM FotosInspeccion f WHERE f.ruta = :ruta"),
    @NamedQuery(name = "FotosInspeccion.findByAutor", query = "SELECT f FROM FotosInspeccion f WHERE f.autor = :autor"),
    @NamedQuery(name = "FotosInspeccion.findByFechaCreacion", query = "SELECT f FROM FotosInspeccion f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "FotosInspeccion.findByFechaModificacion", query = "SELECT f FROM FotosInspeccion f WHERE f.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "FotosInspeccion.findByUsuarioModifica", query = "SELECT f FROM FotosInspeccion f WHERE f.usuarioModifica = :usuarioModifica"),
    @NamedQuery(name = "FotosInspeccion.findByDescripcion", query = "SELECT f FROM FotosInspeccion f WHERE f.descripcion = :descripcion")})
public class FotosInspeccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ruta", nullable = false, length = 250)
    private String ruta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "autor", nullable = false, length = 250)
    private String autor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_modificacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "usuario_modifica", nullable = false, length = 250)
    private String usuarioModifica;
    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @JsonIgnore
    @JoinColumn(name = "id_inspeccion", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private DatoInspeccion inspeccion;

    public FotosInspeccion() {
    }

    public FotosInspeccion(Integer id) {
        this.id = id;
    }

    public FotosInspeccion(Integer id, String ruta, String autor, Date fechaCreacion, Date fechaModificacion, String usuarioModifica) {
        this.id = id;
        this.ruta = ruta;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModifica = usuarioModifica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DatoInspeccion getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(DatoInspeccion inspeccion) {
        this.inspeccion = inspeccion;
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
        if (!(object instanceof FotosInspeccion)) {
            return false;
        }
        FotosInspeccion other = (FotosInspeccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FotosInspeccion[ id=" + id + " ]";
    }
    
}
