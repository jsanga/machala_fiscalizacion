/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_razon_recargo")
@NamedQueries({
    @NamedQuery(name = "RazonRecargo.findAll", query = "SELECT c FROM RazonRecargo c"),
    @NamedQuery(name = "RazonRecargo.findById", query = "SELECT c FROM RazonRecargo c WHERE c.id = ?1"),
    @NamedQuery(name = "RazonRecargo.findByRazonRecargo", query = "SELECT c FROM RazonRecargo c WHERE c.razonRecargo = ?1")})
public class RazonRecargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "razon_recargo")
    private String razonRecargo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_referencia")
    private float valorReferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vigencia_referencia")
    private int vigenciaReferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "razonRecargo")
    private List<Recargo> recargoList;

    public RazonRecargo() {
    }

    public RazonRecargo(Integer id) {
        this.id = id;
    }

    public RazonRecargo(Integer id, String razonRecargo, float valorReferencia, int vigenciaReferencia) {
        this.id = id;
        this.razonRecargo = razonRecargo;
        this.valorReferencia = valorReferencia;
        this.vigenciaReferencia = vigenciaReferencia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonRecargo() {
        return razonRecargo;
    }

    public void setRazonRecargo(String razonRecargo) {
        this.razonRecargo = razonRecargo;
    }

    public float getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(float valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public int getVigenciaReferencia() {
        return vigenciaReferencia;
    }

    public void setVigenciaReferencia(int vigenciaReferencia) {
        this.vigenciaReferencia = vigenciaReferencia;
    }

    public List<Recargo> getRecargoList() {
        return recargoList;
    }

    public void setRecargoList(List<Recargo> recargoList) {
        this.recargoList = recargoList;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
        if (!(object instanceof RazonRecargo)) {
            return false;
        }
        RazonRecargo other = (RazonRecargo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.RazonRecargo[ id=" + id + " ]";
    }

}
