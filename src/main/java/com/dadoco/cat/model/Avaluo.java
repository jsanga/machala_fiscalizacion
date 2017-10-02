/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author Joao Sanga
 */
@Entity
@Table(name = "cat_avaluo") 
public class Avaluo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "avaluo_terreno")
    private Double avaluoTerreno;
    @Column(name = "avaluo_construccion")
    private Double avaluoConstruccion;
    @Column(name = "avaluo_total")
    private Double avaluoTotal;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "usuario_ingreso")
    private String usuarioIngreso;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "factor_terreno_superficie")
    private Double factorTerrenoSuperficie;
    @Column(name = "factor_terreno_zona_homogenea")
    private Double factorTerrenoZonaHomogenea;
    @Column(name = "factor_terreno_total")
    private Double factorTerrenoTotal;
    @Column(name = "anio")
    private Short anio;
    
    @JsonBackReference(value = "predioAvaluos")
    @JsonIgnore
    @JoinColumn(name = "predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Avaluo other = (Avaluo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Avaluo{" + "id=" + id + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(Double avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public Double getAvaluoConstruccion() {
        return avaluoConstruccion;
    }

    public void setAvaluoConstruccion(Double avaluoConstruccion) {
        this.avaluoConstruccion = avaluoConstruccion;
    }

    public Double getAvaluoTotal() {
        return avaluoTotal;
    }

    public void setAvaluoTotal(Double avaluoTotal) {
        this.avaluoTotal = avaluoTotal;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Double getFactorTerrenoSuperficie() {
        return factorTerrenoSuperficie;
    }

    public void setFactorTerrenoSuperficie(Double factorTerrenoSuperficie) {
        this.factorTerrenoSuperficie = factorTerrenoSuperficie;
    }

    public Double getFactorTerrenoZonaHomogenea() {
        return factorTerrenoZonaHomogenea;
    }

    public void setFactorTerrenoZonaHomogenea(Double factorTerrenoZonaHomogenea) {
        this.factorTerrenoZonaHomogenea = factorTerrenoZonaHomogenea;
    }

    public Double getFactorTerrenoTotal() {
        return factorTerrenoTotal;
    }

    public void setFactorTerrenoTotal(Double factorTerrenoTotal) {
        this.factorTerrenoTotal = factorTerrenoTotal;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }
    
}
