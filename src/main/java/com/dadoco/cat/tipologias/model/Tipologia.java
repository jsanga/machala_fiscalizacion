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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Carlos Loor
 */
@Entity
@Table(name = "tipologia", schema = "tipologias", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"apu", "anio"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipologia.findAll", query = "SELECT t FROM Tipologia t")
    , @NamedQuery(name = "Tipologia.findById", query = "SELECT t FROM Tipologia t WHERE t.id = :id")
    , @NamedQuery(name = "Tipologia.findByApu", query = "SELECT t FROM Tipologia t WHERE t.apu = :apu")
    , @NamedQuery(name = "Tipologia.findByDesde", query = "SELECT t FROM Tipologia t WHERE t.desde = :desde")
    , @NamedQuery(name = "Tipologia.findByHasta", query = "SELECT t FROM Tipologia t WHERE t.hasta = :hasta")
    , @NamedQuery(name = "Tipologia.findByEstado", query = "SELECT t FROM Tipologia t WHERE t.estado = :estado")
    , @NamedQuery(name = "Tipologia.findByDescripcion", query = "SELECT t FROM Tipologia t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tipologia.findByAnio", query = "SELECT t FROM Tipologia t WHERE t.anio = :anio")})
public class Tipologia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "apu")
    private Integer apu;
    @Column(name = "desde")
    private Short desde;
    @Column(name = "hasta")
    private Short hasta;
    @Column(name = "estado")
    private Boolean estado;
    @Size(max = 2147483647)
    @Column(name = "descripcion", length = 2147483647)
    private String descripcion;
    @Column(name = "anio")
    private Short anio;
    @Column(name = "categoria")
    private Integer categoria;

    public Tipologia() {
    }

    public Tipologia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApu() {
        return apu;
    }

    public void setApu(Integer apu) {
        this.apu = apu;
    }

    public Short getDesde() {
        return desde;
    }

    public void setDesde(Short desde) {
        this.desde = desde;
    }

    public Short getHasta() {
        return hasta;
    }

    public void setHasta(Short hasta) {
        this.hasta = hasta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
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
        if (!(object instanceof Tipologia)) {
            return false;
        }
        Tipologia other = (Tipologia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.Tipologia[ id=" + id + " ]";
    }

}
