/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Carlos Loor
 */
@Entity
@Table(name = "apu_descripcion", schema = "tipologias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApuDescripcion.findAll", query = "SELECT a FROM ApuDescripcion a")
    , @NamedQuery(name = "ApuDescripcion.findByApu", query = "SELECT a FROM ApuDescripcion a WHERE a.apu = :apu")
    , @NamedQuery(name = "ApuDescripcion.findByEquipoMaterialPersonal", query = "SELECT a FROM ApuDescripcion a WHERE a.equipoMaterialPersonal = :equipoMaterialPersonal")
    , @NamedQuery(name = "ApuDescripcion.findByCantidad", query = "SELECT a FROM ApuDescripcion a WHERE a.cantidad = :cantidad")
    , @NamedQuery(name = "ApuDescripcion.findByTipo", query = "SELECT a FROM ApuDescripcion a WHERE a.tipo = :tipo")
    , @NamedQuery(name = "ApuDescripcion.findByEstado", query = "SELECT a FROM ApuDescripcion a WHERE a.estado = :estado")
    , @NamedQuery(name = "ApuDescripcion.findById", query = "SELECT a FROM ApuDescripcion a WHERE a.id = :id")})
public class ApuDescripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "apu")
    private Integer apu;
    @Column(name = "equipo_material_personal")
    private Integer equipoMaterialPersonal;
    @Column(name = "cantidad")
    private Short cantidad;
    @Column(name = "tipo")
    private Short tipo;
    @Column(name = "estado")
    private Boolean estado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    public ApuDescripcion() {
    }

    public ApuDescripcion(Long id) {
        this.id = id;
    }

    public Integer getApu() {
        return apu;
    }

    public void setApu(Integer apu) {
        this.apu = apu;
    }

    public Integer getEquipoMaterialPersonal() {
        return equipoMaterialPersonal;
    }

    public void setEquipoMaterialPersonal(Integer equipoMaterialPersonal) {
        this.equipoMaterialPersonal = equipoMaterialPersonal;
    }

    public Short getCantidad() {
        return cantidad;
    }

    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    public Short getTipo() {
        return tipo;
    }

    public void setTipo(Short tipo) {
        this.tipo = tipo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ApuDescripcion)) {
            return false;
        }
        ApuDescripcion other = (ApuDescripcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.ApuDescripcion[ id=" + id + " ]";
    }
    
}
