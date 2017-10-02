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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dairon
 */
@Entity
@Table(name = "cat_contrato_arrendamiento")
@NamedQueries({
    @NamedQuery(name = "ContratoArrendamiento.findAll", query = "SELECT c FROM ContratoArrendamiento c"),
    @NamedQuery(name = "ContratoArrendamiento.findById", query = "SELECT c FROM ContratoArrendamiento c WHERE c.id = :id"),
    @NamedQuery(name = "ContratoArrendamiento.findByNumero", query = "SELECT c FROM ContratoArrendamiento c WHERE c.numero = :numero"),
    @NamedQuery(name = "ContratoArrendamiento.findByFechaInicio", query = "SELECT c FROM ContratoArrendamiento c WHERE c.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "ContratoArrendamiento.findByFechaVencimiento", query = "SELECT c FROM ContratoArrendamiento c WHERE c.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "ContratoArrendamiento.findByFechaDocumento", query = "SELECT c FROM ContratoArrendamiento c WHERE c.fechaDocumento = :fechaDocumento"),
    @NamedQuery(name = "ContratoArrendamiento.findByNumeroResolucion", query = "SELECT c FROM ContratoArrendamiento c WHERE c.numeroResolucion = :numeroResolucion"),
    @NamedQuery(name = "ContratoArrendamiento.findByObservaciones", query = "SELECT c FROM ContratoArrendamiento c WHERE c.observaciones = :observaciones")})
public class ContratoArrendamiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "fecha_documento")
    @Temporal(TemporalType.DATE)
    private Date fechaDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "numero_resolucion")
    private String numeroResolucion;
    @Size(max = 4000)
    @Column(name = "observaciones")
    private String observaciones;
    
     @Column(name = "habilitado")
    private short habilitado;
    

    public ContratoArrendamiento() {
        this.habilitado = 0;
    }

    public ContratoArrendamiento(Integer id) {
        this.id = id;
    }

    public ContratoArrendamiento(Integer id, String numero, Date fechaInicio, Date fechaVencimiento, String numeroResolucion) {
        this.id = id;
        this.numero = numero;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.numeroResolucion = numeroResolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public short getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(short habilitado) {
        this.habilitado = habilitado;
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
        if (!(object instanceof ContratoArrendamiento)) {
            return false;
        }
        ContratoArrendamiento other = (ContratoArrendamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.ContratoArrendamiento[ id=" + id + " ]";
    }
    
}
