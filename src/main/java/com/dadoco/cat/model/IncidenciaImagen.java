/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

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
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cat_incidencia_image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncidenciaImagen.findAll", query = "SELECT c FROM IncidenciaImagen c")
    ,
    @NamedQuery(name = "IncidenciaImagen.findById", query = "SELECT c FROM IncidenciaImagen c WHERE c.id = :id")
    ,
    @NamedQuery(name = "IncidenciaImagen.findByRuta", query = "SELECT c FROM IncidenciaImagen c WHERE c.ruta = :ruta")
    ,
    @NamedQuery(name = "IncidenciaImagen.findByAutor", query = "SELECT c FROM IncidenciaImagen c WHERE c.autor = :autor")
    ,
    @NamedQuery(name = "IncidenciaImagen.findByFechaCreacion", query = "SELECT c FROM IncidenciaImagen c WHERE c.fechaCreacion = :fechaCreacion")
    ,
    @NamedQuery(name = "IncidenciaImagen.findByDescripcion", query = "SELECT c FROM IncidenciaImagen c WHERE c.descripcion = :descripcion")})
public class IncidenciaImagen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ruta")
    private String ruta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "autor")
    private String autor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    ///Relacion entre incidenciaImagen a incidencia by Fsan
    @JsonBackReference(value = "incidenciaParent")
    @JoinColumn(name = "id_incidencia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Incidencia incidencia;
    //

    public IncidenciaImagen() {
    }

    public IncidenciaImagen(Integer id) {
        this.id = id;
    }

    public IncidenciaImagen(Integer id, String ruta, String autor, Date fechaCreacion) {
        this.id = id;
        this.ruta = ruta;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
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
        if (!(object instanceof IncidenciaImagen)) {
            return false;
        }
        IncidenciaImagen other = (IncidenciaImagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.geoportal.model.CatIncidenciaImage[ id=" + id + " ]";
    }

}
