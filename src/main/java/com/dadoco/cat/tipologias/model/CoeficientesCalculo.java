/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.model;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Carlos Loor
 */
@Entity
@Table(name = "coeficientes_calculo", schema = "tipologias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CoeficientesCalculo.findAll", query = "SELECT c FROM CoeficientesCalculo c")
    , @NamedQuery(name = "CoeficientesCalculo.findById", query = "SELECT c FROM CoeficientesCalculo c WHERE c.id = :id")
    , @NamedQuery(name = "CoeficientesCalculo.findByTipo", query = "SELECT c FROM CoeficientesCalculo c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "CoeficientesCalculo.findByArgumento", query = "SELECT c FROM CoeficientesCalculo c WHERE c.argumento = :argumento")
    , @NamedQuery(name = "CoeficientesCalculo.findByValores", query = "SELECT c FROM CoeficientesCalculo c WHERE c.valores = :valores")
    , @NamedQuery(name = "CoeficientesCalculo.findByPeriodo", query = "SELECT c FROM CoeficientesCalculo c WHERE c.periodo = :periodo")
    , @NamedQuery(name = "CoeficientesCalculo.findByFecCre", query = "SELECT c FROM CoeficientesCalculo c WHERE c.fecCre = :fecCre")
    , @NamedQuery(name = "CoeficientesCalculo.findByEstado", query = "SELECT c FROM CoeficientesCalculo c WHERE c.estado = :estado")})
public class CoeficientesCalculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "argumento")
    private Integer argumento;
    @Size(max = 2147483647)
    @Column(name = "valores", length = 2147483647)
    private String valores;
    @Column(name = "periodo")
    private Integer periodo;
    @Column(name = "fec_cre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecCre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "coef")
    private Boolean coef;

    public CoeficientesCalculo() {
    }

    public CoeficientesCalculo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getArgumento() {
        return argumento;
    }

    public void setArgumento(Integer argumento) {
        this.argumento = argumento;
    }

    public String getValores() {
        return valores;
    }

    public void setValores(String valores) {
        this.valores = valores;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Date getFecCre() {
        return fecCre;
    }

    public void setFecCre(Date fecCre) {
        this.fecCre = fecCre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getCoef() {
        return coef;
    }

    public void setCoef(Boolean coef) {
        this.coef = coef;
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
        if (!(object instanceof CoeficientesCalculo)) {
            return false;
        }
        CoeficientesCalculo other = (CoeficientesCalculo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.CoeficientesCalculo[ id=" + id + " ]";
    }

}
