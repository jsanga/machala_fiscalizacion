/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
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
    @NamedQuery(name = "Zona.findAll", query = "SELECT z FROM Zona z")
    ,
    @NamedQuery(name = "Zona.findPK", query = "SELECT z FROM Zona z WHERE z.zonaPK=:zonaPK")
    ,
    @NamedQuery(name = "Zona.findByCodes", query = "SELECT z FROM Zona z WHERE z.zonaPK.codProvincia = ?1 AND z.zonaPK.codCanton = ?2 AND z.zonaPK.codParroquia = ?3 AND z.zonaPK.codZona= ?4")
    ,
    @NamedQuery(name = "Zona.findByCodProvincia", query = "SELECT z FROM Zona z WHERE z.zonaPK.codProvincia = :codProvincia")
    ,
    @NamedQuery(name = "Zona.findByCodCanton", query = "SELECT z FROM Zona z WHERE z.zonaPK.codCanton = :codCanton")
    ,
    @NamedQuery(name = "Zona.findByCodParroquia", query = "SELECT z FROM Zona z WHERE z.zonaPK.codParroquia = :codParroquia")
    ,
    @NamedQuery(name = "Zona.findByCodZona", query = "SELECT z FROM Zona z WHERE z.zonaPK.codZona = :codZona")
    ,
    @NamedQuery(name = "Zona.findByNombre", query = "SELECT z FROM Zona z WHERE z.nombre = :nombre")
    ,
    @NamedQuery(name = "Zona.findByAvaluoMinimo", query = "SELECT z FROM Zona z WHERE z.avaluoMinimo = :avaluoMinimo")})
public class Zona implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZonaPK zonaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation    
    @Basic(optional = false)
    @NotNull
    @Column(name = "avaluo_minimo", nullable = false, precision = 15, scale = 2)
    private BigDecimal avaluoMinimo;
    @JsonIgnore
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zona")
    private List<Sector> sectorCollection;
    //@JsonManagedReference
    @JoinColumns({
        @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_canton", referencedColumnName = "cod_canton", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_parroquia", referencedColumnName = "cod_parroquia", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Parroquia parroquia;

    public Zona() {
        avaluoMinimo = BigDecimal.ZERO;
    }

    public Zona(ZonaPK zonaPK) {
        this.zonaPK = zonaPK;
    }

    public Zona(ZonaPK zonaPK, String nombre, BigDecimal avaluoMinimo) {
        this.zonaPK = zonaPK;
        this.nombre = nombre;
        this.avaluoMinimo = avaluoMinimo;
    }

    public ZonaPK getZonaPK() {
        return zonaPK;
    }

    public void setZonaPK(ZonaPK zonaPK) {
        this.zonaPK = zonaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getAvaluoMinimo() {
        return avaluoMinimo;
    }

    public void setAvaluoMinimo(BigDecimal avaluoMinimo) {
        this.avaluoMinimo = avaluoMinimo;
    }

    public List<Sector> getSectorCollection() {
        return sectorCollection;
    }

    public void setSectorCollection(List<Sector> sectorCollection) {
        this.sectorCollection = sectorCollection;
    }

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zonaPK != null ? zonaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zona)) {
            return false;
        }
        Zona other = (Zona) object;
        if ((this.zonaPK == null && other.zonaPK != null) || (this.zonaPK != null && !this.zonaPK.equals(other.zonaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Zona[ zonaPK=" + zonaPK + " ]";
    }

}
