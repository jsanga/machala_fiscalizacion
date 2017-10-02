/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cat_incidencias")
@XmlRootElement

public class Incidencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_incidencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIncidencia;
    @Size(max = 2147483647)
    @Column(name = "observacion_incidencia")
    private String observacionIncidencia;

    ///Relacion entre incidencia a Predio by Fsan
    @JsonBackReference(value = "predioIncidencia")
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;
    //
    ///Relacion entre incidencia a integrante by Fsan
    @JsonBackReference(value = "predioIntegrantes")
    @JoinColumn(name = "id_integrante", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Integrante integrante;
    //

    //Fsan
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incidencia")
    private List<IncidenciaImagen> incidenciaImagen;
    //

    public Incidencia() {
    }

    public Incidencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaIncidencia() {
        return fechaIncidencia;
    }

    public void setFechaIncidencia(Date fechaIncidencia) {
        this.fechaIncidencia = fechaIncidencia;
    }

    public String getObservacionIncidencia() {
        return observacionIncidencia;
    }

    public void setObservacionIncidencia(String observacionIncidencia) {
        this.observacionIncidencia = observacionIncidencia;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Integrante getIntegrante() {
        return integrante;
    }

    public void setIntegrante(Integrante integrante) {
        this.integrante = integrante;
    }

    public List<IncidenciaImagen> getIncidenciaImagen() {
        return incidenciaImagen;
    }

    public void setIncidenciaImagen(List<IncidenciaImagen> incidenciaImagen) {
        this.incidenciaImagen = incidenciaImagen;
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
        if (!(object instanceof Incidencia)) {
            return false;
        }
        Incidencia other = (Incidencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.Incidencia[ id=" + id + " ]";
    }

}
