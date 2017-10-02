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
    @NamedQuery(name = "Canton.findAll", query = "SELECT c FROM Canton c")
    ,
    @NamedQuery(name = "Canton.findByCodProvincia", query = "SELECT c FROM Canton c WHERE c.cantonPK.codProvincia = :codProvincia")
    ,
    @NamedQuery(name = "Canton.findByCodCanton", query = "SELECT c FROM Canton c WHERE c.cantonPK.codCanton = :codCanton")
    ,
    @NamedQuery(name = "Canton.findByNombre", query = "SELECT c FROM Canton c WHERE c.nombre = :nombre")})
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CantonPK cantonPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String nombre;
    //@JsonManagedReference
    @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Provincia provincia;
    @JsonIgnore
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "canton")
    private Collection<Parroquia> parroquiaCollection;

    public Canton() {
    }

    public Canton(CantonPK cantonPK) {
        this.cantonPK = cantonPK;
    }

    public Canton(CantonPK cantonPK, String nombre) {
        this.cantonPK = cantonPK;
        this.nombre = nombre;
    }

    public CantonPK getCantonPK() {
        return cantonPK;
    }

    public void setCantonPK(CantonPK cantonPK) {
        this.cantonPK = cantonPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Collection<Parroquia> getParroquiaCollection() {
        return parroquiaCollection;
    }

    public void setParroquiaCollection(Collection<Parroquia> parroquiaCollection) {
        this.parroquiaCollection = parroquiaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cantonPK != null ? cantonPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.cantonPK == null && other.cantonPK != null) || (this.cantonPK != null && !this.cantonPK.equals(other.cantonPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Canton[ cantonPK=" + cantonPK + " ]";
    }

}
