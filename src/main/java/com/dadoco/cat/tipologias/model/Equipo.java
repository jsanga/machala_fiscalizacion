/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Joao Sanga
 */
@Entity
@Table(name = "equipo", schema = "tipologias")
public class Equipo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "unidad")
    private Short unidad;   //variable_discreta
    @Column(name = "costo_hora")
    private BigDecimal costoHora;
    @Column(name = "rendimiento_m3_hora")
    private BigDecimal rendimientoM3Hora;
    @Column(name = "combustibles")
    private BigDecimal combustibles;
    @Column(name = "repuestos")
    private BigDecimal repuestos;
    @Column(name = "m_o_rep")
    private BigDecimal MORep;
    @Column(name = "costos_del_equipo")
    private BigDecimal costosDelEquipo;
    @Column(name = "varios")
    private BigDecimal varios;
    @Column(name = "costo_horario")
    private BigDecimal costoHorario;
    @Column(name = "galon_hora")
    private BigDecimal galonHora;
    @Column(name = "costo_m_o")
    private BigDecimal costoMO;
    @Column(name = "estado")
    private Boolean estado;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getUnidad() {
        return unidad;
    }

    public void setUnidad(Short unidad) {
        this.unidad = unidad;
    }

    public BigDecimal getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(BigDecimal costoHora) {
        this.costoHora = costoHora;
    }

    public BigDecimal getRendimientoM3Hora() {
        return rendimientoM3Hora;
    }

    public void setRendimientoM3Hora(BigDecimal rendimientoM3Hora) {
        this.rendimientoM3Hora = rendimientoM3Hora;
    }

    public BigDecimal getCombustibles() {
        return combustibles;
    }

    public void setCombustibles(BigDecimal combustibles) {
        this.combustibles = combustibles;
    }

    public BigDecimal getRepuestos() {
        return repuestos;
    }

    public void setRepuestos(BigDecimal repuestos) {
        this.repuestos = repuestos;
    }

    public BigDecimal getMORep() {
        return MORep;
    }

    public void setMORep(BigDecimal MORep) {
        this.MORep = MORep;
    }

    public BigDecimal getCostosDelEquipo() {
        return costosDelEquipo;
    }

    public void setCostosDelEquipo(BigDecimal costosDelEquipo) {
        this.costosDelEquipo = costosDelEquipo;
    }

    public BigDecimal getVarios() {
        return varios;
    }

    public void setVarios(BigDecimal varios) {
        this.varios = varios;
    }

    public BigDecimal getCostoHorario() {
        return costoHorario;
    }

    public void setCostoHorario(BigDecimal costoHorario) {
        this.costoHorario = costoHorario;
    }

    public BigDecimal getGalonHora() {
        return galonHora;
    }

    public void setGalonHora(BigDecimal galonHora) {
        this.galonHora = galonHora;
    }

    public BigDecimal getCostoMO() {
        return costoMO;
    }

    public void setCostoMO(BigDecimal costoMO) {
        this.costoMO = costoMO;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Equipo other = (Equipo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Equipo{" + "id=" + id + '}';
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
}
