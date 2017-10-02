/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Manzana.findAll", query = "SELECT m FROM Manzana m")
    ,
     @NamedQuery(name = "Manzana.existeManzanaPk", query = "SELECT m FROM Manzana m WHERE m.manzanaPK=:manzanaPK")
    ,
    @NamedQuery(name = "Manzana.findByCodes", query = "SELECT m FROM Manzana m WHERE m.manzanaPK.codProvincia = ?1 AND m.manzanaPK.codCanton=?2 AND m.manzanaPK.codParroquia=?3 AND m.manzanaPK.codZona=?4 AND m.manzanaPK.codSector=?5 AND m.manzanaPK.codManzana=?6")
    ,
    @NamedQuery(name = "Manzana.findByObservacion", query = "SELECT m FROM Manzana m WHERE m.observacion = :observacion")})
public class Manzana implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
//    @Comment(name="Manzana",)
    protected ManzanaPK manzanaPK;
    @Size(max = 255)
    @Column(name = "calle_norte", length = 255)
    private String calleNorte;
    @Size(max = 255)
    @Column(name = "calle_sur", length = 255)
    private String calleSur;
    @Size(max = 255)
    @Column(name = "calle_este", length = 255)
    private String calleEste;
    @Size(max = 255)
    @Column(name = "calle_oeste", length = 255)
    private String calleOeste;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal area;
    @Size(max = 2000)
    @Column(length = 2000)
    private String observacion;

    @Column(name = "secuencia_predio")
    private Integer secuencia;
    //@JsonManagedReference
    @JoinColumn(name = "zona_homogenea", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ZonaHomogenea zonaHomogenea;
    //@JsonManagedReference
    @JoinColumn(name = "id_zona_aumento_reduccion", referencedColumnName = "id")
    @ManyToOne
    private ZonaAumentoReduccion zonaAumentoReduccion;
    //@JsonManagedReference
    @JoinColumns({
        @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_canton", referencedColumnName = "cod_canton", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_parroquia", referencedColumnName = "cod_parroquia", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_zona", referencedColumnName = "cod_zona", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_sector", referencedColumnName = "cod_sector", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Sector sector;
    @JsonIgnore
    @JsonBackReference(value = "terrenoCollection")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manzana")
    private List<Terreno> terrenoCollection;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_manzanero", referencedColumnName = "id")
    private ManzanaArchivo manzanero;

    public Manzana() {
    }

    public Manzana(ManzanaPK manzanaPK) {
        this.manzanaPK = manzanaPK;
    }

    public Manzana(ManzanaPK manzanaPK, BigDecimal area) {
        this.manzanaPK = manzanaPK;
        this.area = area;
    }

    public Manzana(String codProvincia, String codCanton, String codParroquia, String codZona, String codSector, String codManzana) {
        this.manzanaPK = new ManzanaPK(codProvincia, codCanton, codParroquia, codZona, codSector, codManzana);
    }

    public ManzanaPK getManzanaPK() {
        return manzanaPK;
    }

    public void setManzanaPK(ManzanaPK manzanaPK) {
        this.manzanaPK = manzanaPK;
    }

    public ZonaHomogenea getZonaHomogenea() {
        return zonaHomogenea;
    }

    public void setZonaHomogenea(ZonaHomogenea zonaHomogenea) {
        this.zonaHomogenea = zonaHomogenea;
    }

    public String getCalleNorte() {
        return calleNorte;
    }

    public void setCalleNorte(String calleNorte) {
        this.calleNorte = calleNorte;
    }

    public String getCalleSur() {
        return calleSur;
    }

    public void setCalleSur(String calleSur) {
        this.calleSur = calleSur;
    }

    public String getCalleEste() {
        return calleEste;
    }

    public void setCalleEste(String calleEste) {
        this.calleEste = calleEste;
    }

    public String getCalleOeste() {
        return calleOeste;
    }

    public void setCalleOeste(String calleOeste) {
        this.calleOeste = calleOeste;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public List<Terreno> getTerrenoCollection() {
        return terrenoCollection;
    }

    public void setTerrenoCollection(List<Terreno> terrenoCollection) {
        this.terrenoCollection = terrenoCollection;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public ManzanaArchivo getManzanero() {
        return manzanero;
    }

    public void setManzanero(ManzanaArchivo manzanero) {
        this.manzanero = manzanero;
    }

    public ZonaAumentoReduccion getZonaAumentoReduccion() {
        return zonaAumentoReduccion;
    }

    public void setZonaAumentoReduccion(ZonaAumentoReduccion zonaAumentoReduccion) {
        this.zonaAumentoReduccion = zonaAumentoReduccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manzanaPK != null ? manzanaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manzana)) {
            return false;
        }
        Manzana other = (Manzana) object;
        if ((this.manzanaPK == null && other.manzanaPK != null) || (this.manzanaPK != null && !this.manzanaPK.equals(other.manzanaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Manzana[ manzanaPK=" + manzanaPK + " ]";
    }

}
