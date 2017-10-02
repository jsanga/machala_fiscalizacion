/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_zona_homo_valor")
@NamedQueries({
    @NamedQuery(name = "ZonaHomoValor.findAll", query = "SELECT z FROM ZonaHomoValor z WHERE z.zonaHomoValorPK.anio = ?1 ORDER BY z.zonaHomoValorPK.idZonaHomo")
    , @NamedQuery(name = "ZonaHomoValor.findByAnioAndZona", query = "SELECT z FROM ZonaHomoValor z WHERE z.zonaHomoValorPK.anio = ?1 AND z.zonaHomoValorPK.idZonaHomo = ?2")})
public class ZonaHomoValor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZonaHomoValorPK zonaHomoValorPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private float valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "banda_impositiva")
    private float bandaImpositiva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contribucion_minima")
    private float contribucionMinima;
    @Basic(optional = false)
    @NotNull
    @Column(name = "salud_seguridad")
    private float saludSeguridad;
    //@JsonManagedReference
    @JoinColumn(name = "id_zona_homo", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ZonaHomogenea zonaHomogenea;

    public ZonaHomoValor() {
    }

    public ZonaHomoValor(ZonaHomoValorPK zonaHomoValorPK) {
        this.zonaHomoValorPK = zonaHomoValorPK;
    }

    public ZonaHomoValor(ZonaHomoValorPK zonaHomoValorPK, float valor, float bandaImpositiva, float contribucionMinima) {
        this.zonaHomoValorPK = zonaHomoValorPK;
        this.valor = valor;
        this.bandaImpositiva = bandaImpositiva;
        this.contribucionMinima = contribucionMinima;
    }

    public ZonaHomoValor(short anio, int idZonaHomo) {
        this.zonaHomoValorPK = new ZonaHomoValorPK(anio, idZonaHomo);
    }

    public ZonaHomoValorPK getZonaHomoValorPK() {
        return zonaHomoValorPK;
    }

    public void setZonaHomoValorPK(ZonaHomoValorPK zonaHomoValorPK) {
        this.zonaHomoValorPK = zonaHomoValorPK;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getBandaImpositiva() {
        return bandaImpositiva;
    }

    public void setBandaImpositiva(float bandaImpositiva) {
        this.bandaImpositiva = bandaImpositiva;
    }

    public float getContribucionMinima() {
        return contribucionMinima;
    }

    public void setContribucionMinima(float contribucionMinima) {
        this.contribucionMinima = contribucionMinima;
    }

    public float getSaludSeguridad() {
        return saludSeguridad;
    }

    public void setSaludSeguridad(float saludSeguridad) {
        this.saludSeguridad = saludSeguridad;
    }

    public ZonaHomogenea getZonaHomogenea() {
        return zonaHomogenea;
    }

    public void setZonaHomogenea(ZonaHomogenea zonaHomogenea) {
        this.zonaHomogenea = zonaHomogenea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zonaHomoValorPK != null ? zonaHomoValorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZonaHomoValor)) {
            return false;
        }
        ZonaHomoValor other = (ZonaHomoValor) object;
        if ((this.zonaHomoValorPK == null && other.zonaHomoValorPK != null) || (this.zonaHomoValorPK != null && !this.zonaHomoValorPK.equals(other.zonaHomoValorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return zonaHomoValorPK.toString();
    }

}
