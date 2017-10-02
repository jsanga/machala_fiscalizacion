/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_razon_exencion")
@NamedQueries({
    @NamedQuery(name = "RazonExencion.findAll", query = "SELECT c FROM RazonExencion c"),
    @NamedQuery(name = "RazonExencion.findById", query = "SELECT c FROM RazonExencion c WHERE c.id = ?1"),
    @NamedQuery(name = "RazonExencion.findByIdTipoExencion", query = "SELECT r FROM RazonExencion r WHERE r.tipoExencion.id = ?1")})
public class RazonExencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "razon_exencion")
    private String razonExencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "requisito")
    private String requisito;
    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_maximo")
    private float valorMaximo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "permanente")
    private boolean permanente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aplica_empresa")
    private boolean aplicaEmpresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_referencia")
    private float valorReferencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_minimo")
    private float valorMinimo;

    @Basic(optional = true)
    @Column(name = "usa_porciento")
    private boolean usaPorciento;

    @JsonIgnore
    @JoinColumn(name = "id_tipo_exencion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoExencion tipoExencion;

    public RazonExencion() {
    }

    public RazonExencion(Integer id) {
        this.id = id;
    }

    public RazonExencion(Integer id, String razonExencion, String requisito, float valorMaximo, boolean permanente, boolean activo, boolean aplicaEmpresa, float valorReferencia, float valorMinimo) {
        this.id = id;
        this.razonExencion = razonExencion;
        this.requisito = requisito;
        this.valorMaximo = valorMaximo;
        this.permanente = permanente;
        this.activo = activo;
        this.aplicaEmpresa = aplicaEmpresa;
        this.valorReferencia = valorReferencia;
        this.valorMinimo = valorMinimo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonExencion() {
        return razonExencion;
    }

    public void setRazonExencion(String razonExencion) {
        this.razonExencion = razonExencion;
    }

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public float getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(float valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public boolean getPermanente() {
        return permanente;
    }

    public void setPermanente(boolean permanente) {
        this.permanente = permanente;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getAplicaEmpresa() {
        return aplicaEmpresa;
    }

    public void setAplicaEmpresa(boolean aplicaEmpresa) {
        this.aplicaEmpresa = aplicaEmpresa;
    }

    public float getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(float valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public float getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public TipoExencion getTipoExencion() {
        return tipoExencion;
    }

    public void setTipoExencion(TipoExencion tipoExencion) {
        this.tipoExencion = tipoExencion;
    }

    public boolean isUsaPorciento() {
        return usaPorciento;
    }

    public void setUsaPorciento(boolean usaPorciento) {
        this.usaPorciento = usaPorciento;
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
        if (!(object instanceof RazonExencion)) {
            return false;
        }
        RazonExencion other = (RazonExencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.RazonExencion[ id=" + id + " ]";
    }

    public String valorPermanente() {
        if (permanente) {
            return "Si";
        } else {
            return "No";
        }
    }

    public String valorActivo() {
        if (activo) {
            return "Si";
        } else {
            return "No";
        }
    }

}
