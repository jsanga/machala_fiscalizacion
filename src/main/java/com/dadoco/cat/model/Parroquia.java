/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
    @NamedQuery(name = "Parroquia.findAll", query = "SELECT p FROM Parroquia p")
    ,
    @NamedQuery(name = "Parroquia.findByCodProvincia", query = "SELECT p FROM Parroquia p WHERE p.parroquiaPK.codProvincia = :codProvincia")
    ,
    @NamedQuery(name = "Parroquia.findByCodCanton", query = "SELECT p FROM Parroquia p WHERE p.parroquiaPK.codCanton = :codCanton")
    ,
    @NamedQuery(name = "Parroquia.findByCodParroquia", query = "SELECT p FROM Parroquia p WHERE p.parroquiaPK.codParroquia = :codParroquia")
    ,
    @NamedQuery(name = "Parroquia.findByNombre", query = "SELECT p FROM Parroquia p WHERE p.nombre = :nombre")
    ,
    @NamedQuery(name = "Parroquia.find", query = "SELECT p FROM Parroquia p WHERE UPPER(p.nombre) = :nombre OR p.parroquiaPK =:parroquiaPK")
    ,
    @NamedQuery(name = "Parroquia.findByTipo", query = "SELECT p FROM Parroquia p WHERE p.tipo = :tipo")
})
public class Parroquia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParroquiaPK parroquiaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String nombre;

    private Character tipo;
    //@JsonManagedReference
    @JoinColumns({
        @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia", nullable = false, insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_canton", referencedColumnName = "cod_canton", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Canton canton;
    @JsonIgnore
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parroquia")
    private List<Zona> zonaCollection;

    @Basic(optional = false)
    @NotNull
    @Column(name = "banda_impositiva", nullable = false)
    private float bandaImpositiva;

    public Parroquia() {

    }

    public Parroquia(ParroquiaPK parroquiaPK) {
        this.parroquiaPK = parroquiaPK;
    }

    public Parroquia(ParroquiaPK parroquiaPK, String nombre, Character tipo) {
        this.parroquiaPK = parroquiaPK;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Parroquia(String codProvincia, String codCanton, String codParroquia) {
        this.parroquiaPK = new ParroquiaPK(codProvincia, codCanton, codParroquia);
    }

    public ParroquiaPK getParroquiaPK() {
        return parroquiaPK;
    }

    public void setParroquiaPK(ParroquiaPK parroquiaPK) {
        this.parroquiaPK = parroquiaPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public Canton getCanton() {
        return canton;
    }

    public void setCanton(Canton canton) {
        this.canton = canton;
    }

    public List<Zona> getZonaCollection() {
        return zonaCollection;
    }

    public void setZonaCollection(List<Zona> zonaCollection) {
        this.zonaCollection = zonaCollection;
    }

    public float getBandaImpositiva() {
        return bandaImpositiva;
    }

    public void setBandaImpositiva(float bandaImpositiva) {
        this.bandaImpositiva = bandaImpositiva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parroquiaPK != null ? parroquiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parroquia)) {
            return false;
        }
        Parroquia other = (Parroquia) object;
        if ((this.parroquiaPK == null && other.parroquiaPK != null) || (this.parroquiaPK != null && !this.parroquiaPK.equals(other.parroquiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Parroquia[ parroquiaPK=" + parroquiaPK + " ]";
    }

}
