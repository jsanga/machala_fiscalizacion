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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Dairon
 */
@Entity
@Table(name = "cat_tipo_escritura")
@NamedQueries({
    @NamedQuery(name = "TipoEscritura.findAll", query = "SELECT t FROM TipoEscritura t"),
    @NamedQuery(name = "TipoEscritura.findById", query = "SELECT t FROM TipoEscritura t WHERE t.id = ?1"),
    @NamedQuery(name = "TipoEscritura.findByTipo", query = "SELECT t FROM TipoEscritura t WHERE t.tipo = ?1"),
    @NamedQuery(name = "TipoEscritura.findByDescripcion", query = "SELECT t FROM TipoEscritura t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TipoEscritura.findByEmisor", query = "SELECT t FROM TipoEscritura t WHERE t.emisor = :emisor"),
    @NamedQuery(name = "TipoEscritura.findByReceptor", query = "SELECT t FROM TipoEscritura t WHERE t.receptor = :receptor"),
    @NamedQuery(name = "TipoEscritura.findByAplicaSolar", query = "SELECT t FROM TipoEscritura t WHERE t.aplicaSolar = :aplicaSolar"),
    @NamedQuery(name = "TipoEscritura.findByAplicaBloque", query = "SELECT t FROM TipoEscritura t WHERE t.aplicaBloque = :aplicaBloque"),
    @NamedQuery(name = "TipoEscritura.findByAplicaPropiedadHorizontal", query = "SELECT t FROM TipoEscritura t WHERE t.aplicaPropiedadHorizontal = :aplicaPropiedadHorizontal"),
    @NamedQuery(name = "TipoEscritura.findByCambiaPropietario", query = "SELECT t FROM TipoEscritura t WHERE t.cambiaPropietario = :cambiaPropietario")})
public class TipoEscritura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "emisor")
    private String emisor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "receptor")
    private String receptor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aplica_solar")
    private boolean aplicaSolar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aplica_bloque")
    private boolean aplicaBloque;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aplica_propiedad_horizontal")
    private boolean aplicaPropiedadHorizontal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cambia_propietario")
    private boolean cambiaPropietario;

    @JoinColumn(name = "id_efecto", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private EfectoTipoEscritura efecto;

    public TipoEscritura() {
    }

    public TipoEscritura(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public boolean getAplicaSolar() {
        return aplicaSolar;
    }

    public void setAplicaSolar(boolean aplicaSolar) {
        this.aplicaSolar = aplicaSolar;
    }

    public boolean getAplicaBloque() {
        return aplicaBloque;
    }

    public void setAplicaBloque(boolean aplicaBloque) {
        this.aplicaBloque = aplicaBloque;
    }

    public boolean getAplicaPropiedadHorizontal() {
        return aplicaPropiedadHorizontal;
    }

    public void setAplicaPropiedadHorizontal(boolean aplicaPropiedadHorizontal) {
        this.aplicaPropiedadHorizontal = aplicaPropiedadHorizontal;
    }

    public boolean getCambiaPropietario() {
        return cambiaPropietario;
    }

    public void setCambiaPropietario(boolean cambiaPropietario) {
        this.cambiaPropietario = cambiaPropietario;
    }

    public EfectoTipoEscritura getEfecto() {
        return efecto;
    }

    public void setEfecto(EfectoTipoEscritura efecto) {
        this.efecto = efecto;
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
        if (!(object instanceof TipoEscritura)) {
            return false;
        }
        TipoEscritura other = (TipoEscritura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.tipo;
    }

    /*
     * valor = 0 => Emisor
     * valor = 1 => Receptor
     */
    public String ValorByEmisorReceptor(int valor) {

        String result = "Contribuyente";
        switch (valor) {
            case 0: {
                if (this.getEmisor().equalsIgnoreCase("M")) {
                    result = "Municipio";
                }
                break;
            }
            case 1: {
                if (this.getReceptor().equalsIgnoreCase("M")) {
                    result = "Municipio";
                }
                break;
            }
        }

        return result;
    }

    /*
     * tipo = 0 => Aplica Solar
     * tipo = 1 => Aplica Bloque
     * tipo = 2 => Aplica a PH
     * tipo = 3 => Cambia propietario
     */
    public String ValorByAplica(int tipo) {

        String result = "No";
        switch (tipo) {
            case 0: {
                if (this.getAplicaSolar()) {
                    result = "Si";
                }
                break;
            }
            case 1: {
                if (this.getAplicaBloque()) {
                    result = "Si";
                }
                break;
            }
            case 2: {
                if (this.getAplicaPropiedadHorizontal()) {
                    result = "Si";
                }
                break;
            }
            case 3: {
                if (this.getCambiaPropietario()) {
                    result = "Si";
                }
                break;
            }
        }

        return result;
    }

}
