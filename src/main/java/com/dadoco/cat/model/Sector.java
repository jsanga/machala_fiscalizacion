/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Sector.findAll", query = "SELECT s FROM Sector s")
    ,
    @NamedQuery(name = "Sector.findPK", query = "SELECT s FROM Sector s WHERE s.sectorPK=:sectorPK")
    ,
    @NamedQuery(name = "Sector.findByCodes", query = "SELECT s FROM Sector s WHERE s.sectorPK.codProvincia = ?1 AND s.sectorPK.codCanton = ?2 AND s.sectorPK.codParroquia = ?3 AND s.sectorPK.codZona= ?4 AND s.sectorPK.codSector= ?5")
    ,
    @NamedQuery(name = "Sector.findByCodProvincia", query = "SELECT s FROM Sector s WHERE s.sectorPK.codProvincia = :codProvincia")
    ,
    @NamedQuery(name = "Sector.findByCodCanton", query = "SELECT s FROM Sector s WHERE s.sectorPK.codCanton = :codCanton")
    ,
    @NamedQuery(name = "Sector.findByCodParroquia", query = "SELECT s FROM Sector s WHERE s.sectorPK.codParroquia = :codParroquia")
    ,
    @NamedQuery(name = "Sector.findByCodZona", query = "SELECT s FROM Sector s WHERE s.sectorPK.codZona = :codZona")
    ,
    @NamedQuery(name = "Sector.findByCodSector", query = "SELECT s FROM Sector s WHERE s.sectorPK.codSector = :codSector")
    ,
    @NamedQuery(name = "Sector.findByNombre", query = "SELECT s FROM Sector s WHERE s.nombre = :nombre")})
public class Sector implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SectorPK sectorPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    //@JsonManagedReference
    @JoinColumns({
        @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_canton", referencedColumnName = "cod_canton", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_parroquia", referencedColumnName = "cod_parroquia", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_zona", referencedColumnName = "cod_zona", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Zona zona;
    @JsonIgnore
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sector")
    private Collection<Manzana> manzanaCollection;
    @NotNull
    @Column(name = "cem")
    private float cem;
    @Column(name = "factor_fondo")
    private Double factorFondo;
    @Column(name = "factor_frente")
    private Double factorFrente;
    @Column(name = "factor_superficie")
    private Double factorSuperficie;

    public Sector() {
    }

    public Sector(SectorPK sectorPK) {
        this.sectorPK = sectorPK;
    }

    public Sector(SectorPK sectorPK, String nombre) {
        this.sectorPK = sectorPK;
        this.nombre = nombre;
    }

    public SectorPK getSectorPK() {
        return sectorPK;
    }

    public void setSectorPK(SectorPK sectorPK) {
        this.sectorPK = sectorPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Collection<Manzana> getManzanaCollection() {
        return manzanaCollection;
    }

    public void setManzanaCollection(Collection<Manzana> manzanaCollection) {
        this.manzanaCollection = manzanaCollection;
    }

    public float getCem() {
        return cem;
    }

    public void setCem(float cem) {
        this.cem = cem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sectorPK != null ? sectorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sector)) {
            return false;
        }
        Sector other = (Sector) object;
        if ((this.sectorPK == null && other.sectorPK != null) || (this.sectorPK != null && !this.sectorPK.equals(other.sectorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Sector[ sectorPK=" + sectorPK + " ]";
    }

    public Double getFactorFondo() {
        return factorFondo;
    }

    public void setFactorFondo(Double factorFondo) {
        this.factorFondo = factorFondo;
    }

    public Double getFactorFrente() {
        return factorFrente;
    }

    public void setFactorFrente(Double factorFrente) {
        this.factorFrente = factorFrente;
    }

    public Double getFactorSuperficie() {
        return factorSuperficie;
    }

    public void setFactorSuperficie(Double factorSuperficie) {
        this.factorSuperficie = factorSuperficie;
    }

}
