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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_zona_aumento_reduccion")
@NamedQueries({
    @NamedQuery(name = "ZonaAumentoReduccion.findAll", query = "SELECT z FROM ZonaAumentoReduccion z")
    , @NamedQuery(name = "ZonaAumentoReduccion.findById", query = "SELECT z FROM ZonaAumentoReduccion z WHERE z.id = :id")
    , @NamedQuery(name = "ZonaAumentoReduccion.findByNombre", query = "SELECT z FROM ZonaAumentoReduccion z WHERE z.nombre = :nombre")
    , @NamedQuery(name = "ZonaAumentoReduccion.findByCodigo", query = "SELECT z FROM ZonaAumentoReduccion z WHERE z.codigo = :codigo")})
public class ZonaAumentoReduccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;
    @JsonIgnore
    @JsonBackReference(value = "manzanaCollection")
    @OneToMany(mappedBy = "zonaAumentoReduccion")
    private List<Manzana> manzanas;
    @JsonIgnore
    @JsonBackReference(value = "factorZonaAumentoReduccionCollection")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaAumentoReduccion")
    private List<FactorZonaAumentoReduccion> factores;

    public ZonaAumentoReduccion() {
    }

    public ZonaAumentoReduccion(Integer id) {
        this.id = id;
    }

    public ZonaAumentoReduccion(Integer id, String nombre, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Manzana> getManzanas() {
        return manzanas;
    }

    public void setManzanas(List<Manzana> manzanas) {
        this.manzanas = manzanas;
    }

    public List<FactorZonaAumentoReduccion> getFactores() {
        return factores;
    }

    public void setFactores(List<FactorZonaAumentoReduccion> factores) {
        this.factores = factores;
    }

    public FactorZonaAumentoReduccion valorFactor(short anio) {
        for (FactorZonaAumentoReduccion f : factores) {
            if (f.getFactorZonaAumentoReduccionPK().getAnio() == anio) {
                return f;
            }
        }
        return null;
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
        if (!(object instanceof ZonaAumentoReduccion)) {
            return false;
        }
        ZonaAumentoReduccion other = (ZonaAumentoReduccion) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return id + "";
    }

}
