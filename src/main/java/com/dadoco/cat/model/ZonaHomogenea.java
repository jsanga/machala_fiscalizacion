/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.common.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_zonas_homogeneas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ZonaHomogenea.findAll", query = "SELECT z FROM ZonaHomogenea z ORDER BY z.id")
    , @NamedQuery(name = "ZonaHomogenea.findByCodigoZona", query = "SELECT z FROM ZonaHomogenea z WHERE z.codigoZona = :codigoZona")
    , @NamedQuery(name = "ZonaHomogenea.findByValor", query = "SELECT z FROM ZonaHomogenea z WHERE z.valor = :valor")
    , @NamedQuery(name = "ZonaHomogenea.findByNombreZona", query = "SELECT z FROM ZonaHomogenea z WHERE z.nombreZona = :nombreZona")
    , @NamedQuery(name = "ZonaHomogenea.findByValorEsquinero", query = "SELECT z FROM ZonaHomogenea z WHERE z.valorEsquinero = :valorEsquinero")
    , @NamedQuery(name = "ZonaHomogenea.findByValorMedianero", query = "SELECT z FROM ZonaHomogenea z WHERE z.valorMedianero = :valorMedianero")})
public class ZonaHomogenea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "codigo_zona")
    private String codigoZona;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Float valor;
    @Size(max = 100)
    @Column(name = "nombre_zona")
    private String nombreZona;
    @Column(name = "valor_esquinero")
    private Float valorEsquinero;
    @Column(name = "valor_medianero")
    private Float valorMedianero;
    @Basic(optional = false)
    @Column(name = "emitida")
    private boolean emitida;
    //@JsonManagedReference
    @JoinColumn(name = "id_periodo", referencedColumnName = "id")
    @ManyToOne
    private Periodo periodo;
    @JsonIgnore
    @JsonBackReference(value = "manzanaList")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaHomogenea")
    private List<Manzana> manzanas;
    @JsonIgnore
    @JsonBackReference(value = "zonaHomoValorList")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaHomogenea")
    private List<ZonaHomoValor> valores;
    @JsonIgnore
    @JsonBackReference(value = "zonaHomoValor")
    @Transient
    private ZonaHomoValor zonaHomoValor;

    public ZonaHomogenea() {
        emitida = false;
        zonaHomoValor = new ZonaHomoValor();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(String codigoZona) {
        this.codigoZona = codigoZona;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public Float getValorEsquinero() {
        return valorEsquinero;
    }

    public void setValorEsquinero(Float valorEsquinero) {
        this.valorEsquinero = valorEsquinero;
    }

    public Float getValorMedianero() {
        return valorMedianero;
    }

    public void setValorMedianero(Float valorMedianero) {
        this.valorMedianero = valorMedianero;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<Manzana> getManzanas() {
        return manzanas;
    }

    public void setManzanas(List<Manzana> manzanas) {
        this.manzanas = manzanas;
    }

    public List<ZonaHomoValor> getValores() {
        return valores;
    }

    public void setValores(List<ZonaHomoValor> valores) {
        this.valores = valores;
    }

    public ZonaHomoValor getZonaHomoValor() {
        zonaHomoValor = valorActual(Util.ANIO_PROXIMO);
        return zonaHomoValor;
    }

    public void setZonaHomoValor(ZonaHomoValor zonaHomoValor) {
        this.zonaHomoValor = zonaHomoValor;
    }

    public boolean isEmitida() {
        return emitida;
    }

    public void setEmitida(boolean emitida) {
        this.emitida = emitida;
    }

    public ZonaHomoValor valorActual(short anio) {
        for (ZonaHomoValor v : getValores()) {
            if (v.getZonaHomoValorPK().getAnio() == anio) {
                return v;
            }
        }

        return new ZonaHomoValor();
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
        if (!(object instanceof ZonaHomogenea)) {
            return false;
        }
        ZonaHomogenea other = (ZonaHomogenea) object;

        return this.id.equals(other.getId());
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
