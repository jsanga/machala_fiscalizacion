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
@Table(name = "cat_predio_image")
@NamedQueries({
    @NamedQuery(name = "PredioImage.findAll", query = "SELECT f FROM PredioImage f")
    ,
    @NamedQuery(name = "PredioImage.findById", query = "SELECT f FROM PredioImage f WHERE f.id = :id")
    ,
    @NamedQuery(name = "PredioImage.findByRuta", query = "SELECT f FROM PredioImage f WHERE f.ruta = :ruta")
    ,
    @NamedQuery(name = "PredioImage.findByAutor", query = "SELECT f FROM PredioImage f WHERE f.autor = :autor")
    ,
    @NamedQuery(name = "PredioImage.findByFechaCreacion", query = "SELECT f FROM PredioImage f WHERE f.fechaCreacion = :fechaCreacion")
    ,
    @NamedQuery(name = "PredioImage.findByPredio", query = "SELECT f FROM PredioImage f WHERE f.predio.id = :predio")
    ,
    @NamedQuery(name = "PredioImage.findByDescripcion", query = "SELECT f FROM PredioImage f WHERE f.descripcion = :descripcion")})
public class PredioImage implements Serializable {

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

    @Size(max = 255)
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @JsonIgnore
    @JsonBackReference(value = "predioImage")
    @JoinColumn(name = "id_predio", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Predio predio;

    public PredioImage() {
    }

    public PredioImage(PredioImage pi) {

        this.ruta = pi.getRuta();
        this.autor = pi.getAutor();
        this.fechaCreacion = pi.getFechaCreacion();
        this.descripcion = pi.getDescripcion();
    }

    public PredioImage(Integer id) {
        this.id = id;
    }

    public PredioImage(Integer id, String ruta, String autor, Date fechaCreacion, Date fechaModificacion) {
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

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
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
        if (!(object instanceof PredioImage)) {
            return false;
        }
        PredioImage other = (PredioImage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
