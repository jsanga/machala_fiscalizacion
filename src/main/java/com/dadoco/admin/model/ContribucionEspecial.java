/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.model;

import com.dadoco.common.annotations.GreaterZero;
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
 * @author dfcalderio
 */
@Entity
@Table(name = "contribucion_especial")
@NamedQueries({
    @NamedQuery(name = "ContribucionEspecial.findAll", query = "SELECT c FROM ContribucionEspecial c WHERE c.estado = 0 ORDER BY c.id")
    , @NamedQuery(name = "ContribucionEspecial.findById", query = "SELECT c FROM ContribucionEspecial c WHERE c.id = :id")
    , @NamedQuery(name = "ContribucionEspecial.findByNombre", query = "SELECT c FROM ContribucionEspecial c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "ContribucionEspecial.findByEstado", query = "SELECT c FROM ContribucionEspecial c WHERE c.estado = :estado")
    , @NamedQuery(name = "ContribucionEspecial.findByPresupuesto", query = "SELECT c FROM ContribucionEspecial c WHERE c.presupuesto = :presupuesto")
    , @NamedQuery(name = "ContribucionEspecial.findByCosto", query = "SELECT c FROM ContribucionEspecial c WHERE c.costo = :costo")
    , @NamedQuery(name = "ContribucionEspecial.findByFechaCreacion", query = "SELECT c FROM ContribucionEspecial c WHERE c.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "ContribucionEspecial.findByFechaTerminacion", query = "SELECT c FROM ContribucionEspecial c WHERE c.fechaTerminacion = :fechaTerminacion")
    , @NamedQuery(name = "ContribucionEspecial.findByFechaInicioCobro", query = "SELECT c FROM ContribucionEspecial c WHERE c.fechaInicioCobro = :fechaInicioCobro")
    , @NamedQuery(name = "ContribucionEspecial.findByFechaFinalCobro", query = "SELECT c FROM ContribucionEspecial c WHERE c.fechaFinalCobro = :fechaFinalCobro")
    , @NamedQuery(name = "ContribucionEspecial.findByDescripcion", query = "SELECT c FROM ContribucionEspecial c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "ContribucionEspecial.findByObservaciones", query = "SELECT c FROM ContribucionEspecial c WHERE c.observaciones = :observaciones")})
public class ContribucionEspecial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @Basic(optional = false)
    @NotNull
    @GreaterZero
    @Column(name = "presupuesto")
    private Double presupuesto;
    @Column(name = "costo")
    private double costo;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_terminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTerminacion;
    @Column(name = "fecha_inicio_cobro")
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @NotNull
    private Date fechaInicioCobro;
    @Column(name = "fecha_final_cobro")
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @NotNull
    private Date fechaFinalCobro;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;

    public ContribucionEspecial() {
    }

    public ContribucionEspecial(Integer id) {
        this.id = id;
    }

    public ContribucionEspecial(Integer id, String nombre, short estado) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(Date fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Date getFechaInicioCobro() {
        return fechaInicioCobro;
    }

    public void setFechaInicioCobro(Date fechaInicioCobro) {
        this.fechaInicioCobro = fechaInicioCobro;
    }

    public Date getFechaFinalCobro() {
        return fechaFinalCobro;
    }

    public void setFechaFinalCobro(Date fechaFinalCobro) {
        this.fechaFinalCobro = fechaFinalCobro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof ContribucionEspecial)) {
            return false;
        }
        ContribucionEspecial other = (ContribucionEspecial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.ContribucionEspecial[ id=" + id + " ]";
    }

}
