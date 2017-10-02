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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Carlos Loor
 */
@Entity
@Table(name = "valor_m2", schema = "tipologias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ValorM2.findAll", query = "SELECT v FROM ValorM2 v")
    , @NamedQuery(name = "ValorM2.findById", query = "SELECT v FROM ValorM2 v WHERE v.id = :id")
    , @NamedQuery(name = "ValorM2.findByEstructura", query = "SELECT v FROM ValorM2 v WHERE v.estructura = :estructura")
    , @NamedQuery(name = "ValorM2.findByTipologia", query = "SELECT v FROM ValorM2 v WHERE v.tipologia = :tipologia")
    , @NamedQuery(name = "ValorM2.findByValor", query = "SELECT v FROM ValorM2 v WHERE v.valor = :valor")
    , @NamedQuery(name = "ValorM2.findByEstado", query = "SELECT v FROM ValorM2 v WHERE v.estado = :estado")
    , @NamedQuery(name = "ValorM2.findByPiso", query = "SELECT v FROM ValorM2 v WHERE v.piso = :piso")})
public class ValorM2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "estructura")
    private Short estructura;
    @Column(name = "tipologia")
    private Integer tipologia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "piso")
    private Short piso;
    @Column(name = "etapa_construccion")
    private Integer etapaConstruccion;
    @Column(name = "cubierta")
    private Integer cubierta;

    public ValorM2() {
    }

    public ValorM2(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getEstructura() {
        return estructura;
    }

    public void setEstructura(Short estructura) {
        this.estructura = estructura;
    }

    public Integer getTipologia() {
        return tipologia;
    }

    public void setTipologia(Integer tipologia) {
        this.tipologia = tipologia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Short getPiso() {
        return piso;
    }

    public void setPiso(Short piso) {
        this.piso = piso;
    }

    public Integer getEtapaConstruccion() {
        return etapaConstruccion;
    }

    public void setEtapaConstruccion(Integer etapaConstruccion) {
        this.etapaConstruccion = etapaConstruccion;
    }

    public Integer getCubierta() {
        return cubierta;
    }

    public void setCubierta(Integer cubierta) {
        this.cubierta = cubierta;
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
        if (!(object instanceof ValorM2)) {
            return false;
        }
        ValorM2 other = (ValorM2) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.ValorM2[ id=" + id + " ]";
    }

}
