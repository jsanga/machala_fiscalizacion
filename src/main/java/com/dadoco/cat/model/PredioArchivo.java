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

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_predio_image")
@NamedQueries({
    @NamedQuery(name = "PredioArchivo.findAll", query = "SELECT f FROM PredioArchivo f"),
    @NamedQuery(name = "PredioArchivo.findById", query = "SELECT f FROM PredioArchivo f WHERE f.id = :id"),
    @NamedQuery(name = "PredioArchivo.findByRuta", query = "SELECT f FROM PredioArchivo f WHERE f.ruta = :ruta"),
    @NamedQuery(name = "PredioArchivo.findByAutor", query = "SELECT f FROM PredioArchivo f WHERE f.autor = :autor"),
    @NamedQuery(name = "PredioArchivo.findByFechaCreacion", query = "SELECT f FROM PredioArchivo f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "PredioArchivo.findByDescripcion", query = "SELECT f FROM PredioArchivo f WHERE f.descripcion = :descripcion")})
public class PredioArchivo implements Serializable {

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

    @JoinColumn(name = "id_predio", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Predio predio;

    public PredioArchivo() {
    }

    public PredioArchivo(Integer id) {
        this.id = id;
    }

    public PredioArchivo(Integer id, String ruta, String autor, Date fechaCreacion, Date fechaModificacion) {
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
        if (!(object instanceof PredioArchivo)) {
            return false;
        }
        PredioArchivo other = (PredioArchivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
