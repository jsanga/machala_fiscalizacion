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
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_archivo_escritura")
@NamedQueries({
    @NamedQuery(name = "ArchivoEscritura.findAll", query = "SELECT f FROM ArchivoEscritura f")
    ,
    @NamedQuery(name = "ArchivoEscritura.findById", query = "SELECT f FROM ArchivoEscritura f WHERE f.id = :id")
    ,
    @NamedQuery(name = "ArchivoEscritura.findByRuta", query = "SELECT f FROM ArchivoEscritura f WHERE f.ruta = :ruta")
    ,
    @NamedQuery(name = "ArchivoEscritura.findByAutor", query = "SELECT f FROM ArchivoEscritura f WHERE f.autor = :autor")
    ,
    @NamedQuery(name = "ArchivoEscritura.findByFechaCreacion", query = "SELECT f FROM ArchivoEscritura f WHERE f.fechaCreacion = :fechaCreacion")
    ,
    @NamedQuery(name = "ArchivoEscritura.findByUsuarioModifica", query = "SELECT f FROM ArchivoEscritura f WHERE f.usuarioModifica = :usuarioModifica")
    ,
    @NamedQuery(name = "ArchivoEscritura.findByDescripcion", query = "SELECT f FROM ArchivoEscritura f WHERE f.descripcion = :descripcion")})
public class ArchivoEscritura implements Serializable {

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
    @Size(min = 1, max = 250)
    @Column(name = "usuario_modifica", nullable = false, length = 250)
    private String usuarioModifica;
    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    @JsonIgnore
    @JsonBackReference(value = "escrituraArchivos")
    @JoinColumn(name = "id_escritura", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Escritura escritura;

    public ArchivoEscritura() {
    }

    public ArchivoEscritura(Integer id) {
        this.id = id;
    }

    public ArchivoEscritura(Integer id, String ruta, String autor, Date fechaCreacion, Date fechaModificacion, String usuarioModifica) {
        this.id = id;
        this.ruta = ruta;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
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

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
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
        if (!(object instanceof ArchivoEscritura)) {
            return false;
        }
        ArchivoEscritura other = (ArchivoEscritura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
