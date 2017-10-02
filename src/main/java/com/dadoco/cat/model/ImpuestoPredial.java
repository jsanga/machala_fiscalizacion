/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_impuesto_predial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImpuestoPredial.findAll", query = "SELECT c FROM ImpuestoPredial c"),
    @NamedQuery(name = "ImpuestoPredial.findAllIds", query = "SELECT c.impuestoPredialPK FROM ImpuestoPredial c WHERE c.impuestoPredialPK.anio = :anio"),
    @NamedQuery(name = "ImpuestoPredial.findByPrueba", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredialPK.anio = ?1 AND c.predio.terreno.terrenoPK.codManzana = ?2"),
    @NamedQuery(name = "ImpuestoPredial.findAllOrdered", query = "SELECT c FROM ImpuestoPredial c ORDER BY c.claveCatastral"),
    @NamedQuery(name = "ImpuestoPredial.findAllUnreleased", query = "SELECT c FROM ImpuestoPredial c WHERE c.emitido=FALSE AND c.impuestoPredialPK.anio = ?1 ORDER BY c.claveCatastral"),
    @NamedQuery(name = "ImpuestoPredial.findAllReleased", query = "SELECT c FROM ImpuestoPredial c WHERE c.emitido=TRUE AND c.impuestoPredialPK.anio=?1 ORDER BY c.claveCatastral"),
    @NamedQuery(name = "ImpuestoPredial.findAllToRelease", query = "SELECT c FROM ImpuestoPredial c WHERE c.emitido=FALSE AND c.excluido=FALSE AND c.impuestoPredialPK.anio=?1 ORDER BY c.claveCatastral"),
    @NamedQuery(name = "ImpuestoPredial.findAllTwoYears", query = "SELECT c FROM ImpuestoPredial c WHERE c.emitido=FALSE AND c.excluido=FALSE AND c.impuestoPredialPK.anio=?1 ORDER BY c.claveCatastral"),
    @NamedQuery(name = "ImpuestoPredial.findByPredio", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredialPK.idPredio = :idPredio"),
    @NamedQuery(name = "ImpuestoPredial.findByAnio", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredialPK.anio = :anio"),
    @NamedQuery(name = "ImpuestoPredial.findByPredioAnio", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredialPK.idPredio = ?1 AND c.impuestoPredialPK.anio = ?2 AND c.impuestoPredialPK.estado = ?3"),
    @NamedQuery(name = "ImpuestoPredial.findByClaveAnio", query = "SELECT c FROM ImpuestoPredial c WHERE c.claveCatastral = ?1 AND c.impuestoPredialPK.anio = ?2 AND c.impuestoPredialPK.estado = ?3"),
    @NamedQuery(name = "ImpuestoPredial.findByDocumento", query = "SELECT c FROM ImpuestoPredial c WHERE c.documento = ?1"),
    @NamedQuery(name = "ImpuestoPredial.findByAvaluoTerreno", query = "SELECT c FROM ImpuestoPredial c WHERE c.avaluoTerreno = :avaluoTerreno"),
    @NamedQuery(name = "ImpuestoPredial.findByAvaluoPredial", query = "SELECT c FROM ImpuestoPredial c WHERE c.avaluoPredial = :avaluoPredial"),
    @NamedQuery(name = "ImpuestoPredial.findByExenciones", query = "SELECT c FROM ImpuestoPredial c WHERE c.exenciones = :exenciones"),
    @NamedQuery(name = "ImpuestoPredial.findByRecargos", query = "SELECT c FROM ImpuestoPredial c WHERE c.recargos = :recargos"),
    @NamedQuery(name = "ImpuestoPredial.findByBaseImponible", query = "SELECT c FROM ImpuestoPredial c WHERE c.baseImponible = :baseImponible"),
    @NamedQuery(name = "ImpuestoPredial.findByBandaImpositiva", query = "SELECT c FROM ImpuestoPredial c WHERE c.bandaImpositiva = :bandaImpositiva"),
    @NamedQuery(name = "ImpuestoPredial.findByImpuestoPredialPK", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredialPK = ?1"),
    @NamedQuery(name = "ImpuestoPredial.findByImpuestoPredial", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredial = :impuestoPredial"),
    @NamedQuery(name = "ImpuestoPredial.NoEmitidosPorAnnio", query = "SELECT c FROM ImpuestoPredial c WHERE c.excluido = ?1 AND c.impuestoPredialPK.anio = ?2"),
    @NamedQuery(name = "ImpuestoPredial.EmitidosPorAnnio", query = "SELECT c FROM ImpuestoPredial c WHERE c.emitido = ?1 AND c.impuestoPredialPK.anio = ?2"),
    @NamedQuery(name = "ImpuestoPredial.PreEmitidosPorAnnio", query = "SELECT c FROM ImpuestoPredial c WHERE c.impuestoPredialPK.anio = ?1")
}) //ImpuestoPredial.NoEmitidosPorAnnio
public class ImpuestoPredial implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImpuestoPredialPK impuestoPredialPK;
    @Column(name = "documento")
    private String documento;
    @Column(name = "area_terreno")
    private double areaTerreno;
    @Column(name = "area_construccion")
    private double areaConstruccion;
    @Column(name = "avaluo_terreno")
    private double avaluoTerreno;
    @Column(name = "avaluo_edificacion")
    private double avaluoEdificacion;
    @Column(name = "avaluo_complementarias")
    private double avaluoComplementarias;
    @Column(name = "avaluo_predial")
    private double avaluoPredial;
    @Column(name = "exenciones")
    private double exenciones;
    @Column(name = "recargos")
    private double recargos;
    @Column(name = "base_imponible")
    private double baseImponible;
    @Column(name = "banda_impositiva")
    private double bandaImpositiva;
    @Column(name = "impuesto_predial")
    private double impuestoPredial;
    @Column(name = "clave_catastral")
    private String claveCatastral;
    @Column(name = "recoleccion_basura")
    private double recoleccionBasura;
    @Column(name = "recargo_personas_juridicas")
    private double recargoPersonasJuridicas;
    @Column(name = "cem")
    private double cem;
    @Column(name = "solar_no_edificado")
    private double solarNoEdificado;
    @Column(name = "emitido")
    private boolean emitido;
    @Column(name = "bloqueado")
    private boolean bloqueado;
    @Column(name = "excluido")
    private boolean excluido;

    @Column(name = "valor_tasa")
    private double tasa;

    @Column(name = "total")
    private double total;
    
    @Column(name = "bombero")
    private double bombero;
    
    @Column(name = "salud_seguridad")
    private double saludSeguridad;
    
    @Column(name = "turismo")
    private double turismo;
    
    @Column(name = "vetustez")
    private double vetustez;
    
    @Column(name = "no_edif_zona_promo")
    private double noEdificadoZonaPromo;

    @Column(name = "descripcion")
    private String descripcion;

    @JoinColumn(name = "id_predio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Predio predio;
    
    @JoinColumns({
        @JoinColumn(name = "id_predio", referencedColumnName = "id_predio", insertable = false, updatable = false),
        @JoinColumn(name = "anio_anterior", referencedColumnName = "anio", insertable = false, updatable = false),
        @JoinColumn(name = "estado", referencedColumnName = "estado",insertable = false, updatable = false)})
	@OneToOne
     private ImpuestoPredial anterior;
    


    public ImpuestoPredial() {
       
    }

    public ImpuestoPredial(ImpuestoPredialPK impuestoPredialPK) {
        this.impuestoPredialPK = impuestoPredialPK;
    }

    public ImpuestoPredial(ImpuestoPredialPK impuestoPredialPK, double avaluoTerreno) {
        this.impuestoPredialPK = impuestoPredialPK;
        this.avaluoTerreno = avaluoTerreno;
    }

    public ImpuestoPredial(int idPredio, short anio) {
        this.impuestoPredialPK = new ImpuestoPredialPK(idPredio, anio);
    }

    public ImpuestoPredialPK getImpuestoPredialPK() {
        return impuestoPredialPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setImpuestoPredialPK(ImpuestoPredialPK impuestoPredialPK) {
        this.impuestoPredialPK = impuestoPredialPK;
    }

    public double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public double getAreaConstruccion() {
        return areaConstruccion;
    }

    public void setAreaConstruccion(double areaConstruccion) {
        this.areaConstruccion = areaConstruccion;
    }

    public double getSolarNoEdificado() {
        return solarNoEdificado;
    }

    public void setSolarNoEdificado(double solarNoEdificado) {
        this.solarNoEdificado = solarNoEdificado;
    }

    public double getRecoleccionBasura() {
        return recoleccionBasura;
    }

    public void setRecoleccionBasura(double recoleccionBasura) {
        this.recoleccionBasura = recoleccionBasura;
    }

    public double getRecargoPersonasJuridicas() {
        return recargoPersonasJuridicas;
    }

    public void setRecargoPersonasJuridicas(double recargoPersonasJuridicas) {
        this.recargoPersonasJuridicas = recargoPersonasJuridicas;
    }

    public double getCem() {
        return cem;
    }

    public void setCem(double cem) {
        this.cem = cem;
    }

    public double getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(double avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public double getAvaluoEdificacion() {
        return avaluoEdificacion;
    }

    public void setAvaluoEdificacion(double avaluoEdificacion) {
        this.avaluoEdificacion = avaluoEdificacion;
    }

    public double getAvaluoComplementarias() {
        return avaluoComplementarias;
    }

    public void setAvaluoComplementarias(double avaluoComplementarias) {
        this.avaluoComplementarias = avaluoComplementarias;
    }

    public double getAvaluoPredial() {
        return avaluoPredial;
    }

    public void setAvaluoPredial(double avaluoPredial) {
        this.avaluoPredial = avaluoPredial;
    }

    public double getExenciones() {
        return exenciones;
    }

    public void setExenciones(double exenciones) {
        this.exenciones = exenciones;
    }

    public double getRecargos() {
        return recargos;
    }

    public void setRecargos(double recargos) {
        this.recargos = recargos;
    }

    public double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public double getBandaImpositiva() {
        return bandaImpositiva;
    }

    public void setBandaImpositiva(double bandaImpositiva) {
        this.bandaImpositiva = bandaImpositiva;
    }

    public double getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(double impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }

    public String getClaveCatastral() {
        return claveCatastral;
    }

    public void setClaveCatastral(String claveCatastral) {
        this.claveCatastral = claveCatastral;
    }

    public boolean getEmitido() {
        return emitido;
    }

    public void setEmitido(boolean emitido) {
        this.emitido = emitido;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public double getBombero() {
        return bombero;
    }

    public void setBombero(double bombero) {
        this.bombero = bombero;
    }

    public double getSaludSeguridad() {
        return saludSeguridad;
    }

    public void setSaludSeguridad(double saludSeguridad) {
        this.saludSeguridad = saludSeguridad;
    }

    public double getTurismo() {
        return turismo;
    }

    public void setTurismo(double turismo) {
        this.turismo = turismo;
    }

    public double getVetustez() {
        return vetustez;
    }

    public void setVetustez(double vetustez) {
        this.vetustez = vetustez;
    }

    public double getNoEdificadoZonaPromo() {
        return noEdificadoZonaPromo;
    }

    public void setNoEdificadoZonaPromo(double noEdificadoZonaPromo) {
        this.noEdificadoZonaPromo = noEdificadoZonaPromo;
    }
    
    

    public float valorMetro(){
        return areaTerreno == 0 ? 0 : (float)(avaluoTerreno/areaTerreno);
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (impuestoPredialPK != null ? impuestoPredialPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImpuestoPredial)) {
            return false;
        }
        ImpuestoPredial other = (ImpuestoPredial) object;
        return !((this.impuestoPredialPK == null && other.impuestoPredialPK != null) || (this.impuestoPredialPK != null && !this.impuestoPredialPK.equals(other.impuestoPredialPK)));
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.ImpuestoPredial[ impuestoPredialPK=" + impuestoPredialPK + " ]";
    }

    public ImpuestoPredial getAnterior() {
        return anterior;
    }

    public void setAnterior(ImpuestoPredial anterior) {
        this.anterior = anterior;
    }

    
}
