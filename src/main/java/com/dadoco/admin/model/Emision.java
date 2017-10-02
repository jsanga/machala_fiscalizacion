/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.ren.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_emisiones")
@NamedQueries({
    @NamedQuery(name = "Emision.findAll", query = "SELECT e FROM Emision e")
    , @NamedQuery(name = "Emision.findByAnio", query = "SELECT e FROM Emision e WHERE e.anio = :anio")
    , @NamedQuery(name = "Emision.findByTotalPredios", query = "SELECT e FROM Emision e WHERE e.totalPredios = :totalPredios")
    , @NamedQuery(name = "Emision.findByPreemitidos", query = "SELECT e FROM Emision e WHERE e.preemitidos = :preemitidos")
    , @NamedQuery(name = "Emision.findByEmitidos", query = "SELECT e FROM Emision e WHERE e.emitidos = :emitidos")
    , @NamedQuery(name = "Emision.findBySinEmitir", query = "SELECT e FROM Emision e WHERE e.sinEmitir = :sinEmitir")
    , @NamedQuery(name = "Emision.findByRecaudacionTotal", query = "SELECT e FROM Emision e WHERE e.recaudacionTotal = :recaudacionTotal")
    , @NamedQuery(name = "Emision.findByRecaudacionEmitida", query = "SELECT e FROM Emision e WHERE e.recaudacionEmitida = :recaudacionEmitida")
    , @NamedQuery(name = "Emision.findByRecaudacionSinEmitir", query = "SELECT e FROM Emision e WHERE e.recaudacionSinEmitir = :recaudacionSinEmitir")
    , @NamedQuery(name = "Emision.findByAvaluoTerreno", query = "SELECT e FROM Emision e WHERE e.avaluoTerreno = :avaluoTerreno")
    , @NamedQuery(name = "Emision.findByAvaluoConstruccion", query = "SELECT e FROM Emision e WHERE e.avaluoConstruccion = :avaluoConstruccion")
    , @NamedQuery(name = "Emision.findByAvaluoComplementarias", query = "SELECT e FROM Emision e WHERE e.avaluoComplementarias = :avaluoComplementarias")
    , @NamedQuery(name = "Emision.findByAvaluoComercial", query = "SELECT e FROM Emision e WHERE e.avaluoComercial = :avaluoComercial")
    , @NamedQuery(name = "Emision.findByImpuestoPredial", query = "SELECT e FROM Emision e WHERE e.impuestoPredial = :impuestoPredial")
    , @NamedQuery(name = "Emision.findByRecoleccionBasura", query = "SELECT e FROM Emision e WHERE e.recoleccionBasura = :recoleccionBasura")
    , @NamedQuery(name = "Emision.findByRpj", query = "SELECT e FROM Emision e WHERE e.rpj = :rpj")
    , @NamedQuery(name = "Emision.findByCem", query = "SELECT e FROM Emision e WHERE e.cem = :cem")
    , @NamedQuery(name = "Emision.findByRecargoNoedif", query = "SELECT e FROM Emision e WHERE e.recargoNoedif = :recargoNoedif")})
public class Emision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private Short anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_predios")
    private int totalPredios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "preemitidos")
    private int preemitidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "emitidos")
    private int emitidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sin_emitir")
    private int sinEmitir;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "recaudacion_total")
    private BigDecimal recaudacionTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recaudacion_emitida")
    private BigDecimal recaudacionEmitida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recaudacion_sin_emitir")
    private BigDecimal recaudacionSinEmitir;
    @Column(name = "avaluo_terreno")
    private BigDecimal avaluoTerreno;
    @Column(name = "avaluo_construccion")
    private BigDecimal avaluoConstruccion;
    @Column(name = "avaluo_complementarias")
    private BigDecimal avaluoComplementarias;
    @Column(name = "avaluo_comercial")
    private BigDecimal avaluoComercial;
    @Column(name = "impuesto_predial")
    private BigDecimal impuestoPredial;
    @Column(name = "recoleccion_basura")
    private BigDecimal recoleccionBasura;
    @Column(name = "rpj")
    private BigDecimal rpj;
    @Column(name = "cem")
    private BigDecimal cem;
    @Column(name = "recargo_noedif")
    private BigDecimal recargoNoedif;
    @Column(name = "servicio_administrativo")
    private BigDecimal servicioAdministrativo;
    @Column(name = "bombero")
    private BigDecimal bombero;
    @Column(name = "salud_seguridad")
    private BigDecimal saludSeguridad;
    @Column(name = "vetustez")
    private BigDecimal vetustez;
    @Column(name = "no_edif_zona_promo")
    private BigDecimal noEdifZonaPromo;

    public Emision() {
        this.avaluoTerreno = BigDecimal.ZERO;
        this.avaluoConstruccion = BigDecimal.ZERO;
        this.avaluoComplementarias = BigDecimal.ZERO;
        this.avaluoComercial = BigDecimal.ZERO;
        this.impuestoPredial = BigDecimal.ZERO;
        this.cem = BigDecimal.ZERO;
        this.recargoNoedif = BigDecimal.ZERO;
        this.recoleccionBasura = BigDecimal.ZERO;
        this.rpj = BigDecimal.ZERO;
        this.recaudacionEmitida = BigDecimal.ZERO;
        this.recaudacionSinEmitir = BigDecimal.ZERO;
        this.recaudacionTotal = BigDecimal.ZERO;
        this.servicioAdministrativo = BigDecimal.ZERO;
        this.bombero = BigDecimal.ZERO;
        this.saludSeguridad = BigDecimal.ZERO;
        this.vetustez = BigDecimal.ZERO;
        this.noEdifZonaPromo = BigDecimal.ZERO;
    }

    public Emision(Short anio) {
        this.anio = anio;
    }

    public Emision(Short anio, int totalPredios, int preemitidos, int emitidos, int sinEmitir, BigDecimal recaudacionTotal, BigDecimal recaudacionEmitida, BigDecimal recaudacionSinEmitir) {
        this.anio = anio;
        this.totalPredios = totalPredios;
        this.preemitidos = preemitidos;
        this.emitidos = emitidos;
        this.sinEmitir = sinEmitir;
        this.recaudacionTotal = recaudacionTotal;
        this.recaudacionEmitida = recaudacionEmitida;
        this.recaudacionSinEmitir = recaudacionSinEmitir;
    }

    public Short getAnio() {
        return anio;
    }

    public void setAnio(Short anio) {
        this.anio = anio;
    }

    public int getTotalPredios() {
        return totalPredios;
    }

    public void setTotalPredios(int totalPredios) {
        this.totalPredios = totalPredios;
    }

    public int getPreemitidos() {
        return preemitidos;
    }

    public void setPreemitidos(int preemitidos) {
        this.preemitidos = preemitidos;
    }

    public int getEmitidos() {
        return emitidos;
    }

    public void setEmitidos(int emitidos) {
        this.emitidos = emitidos;
    }

    public int getSinEmitir() {
        return sinEmitir;
    }

    public void setSinEmitir(int sinEmitir) {
        this.sinEmitir = sinEmitir;
    }

    public BigDecimal getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public void setRecaudacionTotal(BigDecimal recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }

    public BigDecimal getRecaudacionEmitida() {
        return recaudacionEmitida;
    }

    public void setRecaudacionEmitida(BigDecimal recaudacionEmitida) {
        this.recaudacionEmitida = recaudacionEmitida;
    }

    public BigDecimal getRecaudacionSinEmitir() {
        return recaudacionSinEmitir;
    }

    public void setRecaudacionSinEmitir(BigDecimal recaudacionSinEmitir) {
        this.recaudacionSinEmitir = recaudacionSinEmitir;
    }

    public BigDecimal getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(BigDecimal avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public BigDecimal getAvaluoConstruccion() {
        return avaluoConstruccion;
    }

    public void setAvaluoConstruccion(BigDecimal avaluoConstruccion) {
        this.avaluoConstruccion = avaluoConstruccion;
    }

    public BigDecimal getAvaluoComplementarias() {
        return avaluoComplementarias;
    }

    public void setAvaluoComplementarias(BigDecimal avaluoComplementarias) {
        this.avaluoComplementarias = avaluoComplementarias;
    }

    public BigDecimal getAvaluoComercial() {
        return avaluoComercial;
    }

    public void setAvaluoComercial(BigDecimal avaluoComercial) {
        this.avaluoComercial = avaluoComercial;
    }

    public BigDecimal getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(BigDecimal impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }

    public BigDecimal getRecoleccionBasura() {
        return recoleccionBasura;
    }

    public void setRecoleccionBasura(BigDecimal recoleccionBasura) {
        this.recoleccionBasura = recoleccionBasura;
    }

    public BigDecimal getRpj() {
        return rpj;
    }

    public void setRpj(BigDecimal rpj) {
        this.rpj = rpj;
    }

    public BigDecimal getCem() {
        return cem;
    }

    public void setCem(BigDecimal cem) {
        this.cem = cem;
    }

    public BigDecimal getRecargoNoedif() {
        return recargoNoedif;
    }

    public void setRecargoNoedif(BigDecimal recargoNoedif) {
        this.recargoNoedif = recargoNoedif;
    }

    public BigDecimal getServicioAdministrativo() {
        return servicioAdministrativo;
    }

    public void setServicioAdministrativo(BigDecimal servicioAdministrativo) {
        this.servicioAdministrativo = servicioAdministrativo;
    }

    public BigDecimal getBombero() {
        return bombero;
    }

    public void setBombero(BigDecimal bombero) {
        this.bombero = bombero;
    }

    public BigDecimal getSaludSeguridad() {
        return saludSeguridad;
    }

    public void setSaludSeguridad(BigDecimal saludSeguridad) {
        this.saludSeguridad = saludSeguridad;
    }

    public BigDecimal getVetustez() {
        return vetustez;
    }

    public void setVetustez(BigDecimal vetustez) {
        this.vetustez = vetustez;
    }

    public BigDecimal getNoEdifZonaPromo() {
        return noEdifZonaPromo;
    }

    public void setNoEdifZonaPromo(BigDecimal noEdifZonaPromo) {
        this.noEdifZonaPromo = noEdifZonaPromo;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (anio != null ? anio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emision)) {
            return false;
        }
        Emision other = (Emision) object;
        return !((this.anio == null && other.anio != null) || (this.anio != null && !this.anio.equals(other.anio)));
    }

    @Override
    public String toString() {
        return "com.dadoco.flujo.model.Emision[ anio=" + anio + " ]";
    }
    
}
