/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Carlos Loor
 */
@Entity
@Table(name = "apu", schema = "tipologias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apu.findAll", query = "SELECT a FROM Apu a")
    , @NamedQuery(name = "Apu.findById", query = "SELECT a FROM Apu a WHERE a.id = :id")
    , @NamedQuery(name = "Apu.findByAnio", query = "SELECT a FROM Apu a WHERE a.anio = :anio")
    , @NamedQuery(name = "Apu.findByValor", query = "SELECT a FROM Apu a WHERE a.valor = :valor")
    , @NamedQuery(name = "Apu.findByCodigo", query = "SELECT a FROM Apu a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "Apu.findByEstado", query = "SELECT a FROM Apu a WHERE a.estado = :estado")})
public class Apu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "anio")
    private Short anio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    @Size(max = 2147483647)
    @Column(name = "codigo", length = 2147483647)
    private String codigo;
    @Column(name = "estado")
    private Boolean estado;

    public Apu() {
    }

    public Apu(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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
        if (!(object instanceof Apu)) {
            return false;
        }
        Apu other = (Apu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.Apu[ id=" + id + " ]";
    }
    
}
