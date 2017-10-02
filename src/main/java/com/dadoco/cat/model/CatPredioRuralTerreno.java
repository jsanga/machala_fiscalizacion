/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author Joao Sanga
 */
@Entity
@Table(name = "cat_predio_rural_terreno")
public class CatPredioRuralTerreno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "cantidad")
    private Short cantidad;
    @Column(name = "superficie")
    private BigDecimal superficie;
    @Column(name = "tipo_superficie")
    private Short tipoSuperficie;
    @Column(name = "clase_tierra")
    private Short claseTierra;
    @Column(name = "cultivos_anuales_semiperennes")
    private Short cultivosAnuales;
    @Column(name = "cultivos_perennes")
    private Short cultivosPerennes;
    @Column(name = "plantaciones_forestales")
    private Short plantacionesForestales;
    @Column(name = "semovientes")
    private Short semovientes;
    @Column(name = "aves_de_corral")
    private Short avesDeCorral;
    @Column(name = "tipo_terreno")
    private Short tipoTerreno;
    @Column(name = "clase")
    private Short clase;

    @Transient
    private Short idTemporal;

    @JsonIgnore
    @JsonBackReference(value = "predioRutalTereno")
    @JoinColumn(name = "predio", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Predio predio;

    public CatPredioRuralTerreno() {
        superficie = BigDecimal.ZERO;
        tipoSuperficie = 1;
        clase = 1;
    }

    public CatPredioRuralTerreno(Short clase) {
        claseTierra = clase;
        superficie = BigDecimal.ZERO;
        tipoSuperficie = 1;
        clase = 1;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CatPredioRuralTerreno other = (CatPredioRuralTerreno) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idTemporal, other.idTemporal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CatPredioRuralTerreno{" + "id=" + id + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getCantidad() {
        return cantidad;
    }

    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    public Short getClaseTierra() {
        return claseTierra;
    }

    public void setClaseTierra(Short claseTierra) {
        this.claseTierra = claseTierra;
    }

    public Short getCultivosAnuales() {
        return cultivosAnuales;
    }

    public void setCultivosAnuales(Short cultivosAnuales) {
        this.cultivosAnuales = cultivosAnuales;
    }

    public Short getCultivosPerennes() {
        return cultivosPerennes;
    }

    public void setCultivosPerennes(Short cultivosPerennes) {
        this.cultivosPerennes = cultivosPerennes;
    }

    public Short getPlantacionesForestales() {
        return plantacionesForestales;
    }

    public void setPlantacionesForestales(Short plantacionesForestales) {
        this.plantacionesForestales = plantacionesForestales;
    }

    public Short getSemovientes() {
        return semovientes;
    }

    public void setSemovientes(Short semovientes) {
        this.semovientes = semovientes;
    }

    public Short getAvesDeCorral() {
        return avesDeCorral;
    }

    public void setAvesDeCorral(Short avesDeCorral) {
        this.avesDeCorral = avesDeCorral;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Short getTipoTerreno() {
        return tipoTerreno;
    }

    public void setTipoTerreno(Short tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    public Short getTipoSuperficie() {
        return tipoSuperficie;
    }

    public void setTipoSuperficie(Short tipoSuperficie) {
        this.tipoSuperficie = tipoSuperficie;
    }

    public BigDecimal getSuperficie() {
        return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {
        this.superficie = superficie;
    }

    public Short getClase() {
        return clase;
    }

    public void setClase(Short clase) {
        this.clase = clase;
    }

    public Short getIdTemporal() {
        return idTemporal;
    }

    public void setIdTemporal(Short idTemporal) {
        this.idTemporal = idTemporal;
    }

}
