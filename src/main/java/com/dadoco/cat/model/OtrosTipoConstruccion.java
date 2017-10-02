/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.common.annotations.GreaterZero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_otros_tipos_construccion")
@NamedQueries({
    @NamedQuery(name = "OtrosTipoConstruccion.findAll", query = "SELECT f FROM OtrosTipoConstruccion f")
    ,
    @NamedQuery(name = "OtrosTipoConstruccion.findById", query = "SELECT f FROM OtrosTipoConstruccion f WHERE f.id = :id")
})
public class OtrosTipoConstruccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cubierta")
    private short cubierta;
    @Column(name = "estructura")
    private short estructura;
    @GreaterZero
    @Column(name = "area")
    private Double area;

    @Column(name = "numero")
    private int numero;

    @Column(name = "tipo_construccion")
    private short tipoConstruccion;

    @Column(name = "numero_variable")
    private int variable;

    @JoinColumn(name = "numero_variable", referencedColumnName = "id", nullable = true, updatable = false, insertable = false)
    @ManyToOne(optional = true)
    private Variable varia;

    @Column(name = "unidad")
    private short unidad;

    @Column(name = "paredes")
    private short paredes;

    @Column(name = "piso")
    private short piso;

    @Column(name = "sobrepiso")
    private short sobrepiso;

    @Column(name = "revestimiento")
    private short revestimiento;

    @Column(name = "tumbado")
    private short tumbado;

    @Column(name = "ventana")
    private short ventana;

    @Column(name = "vidrios")
    private short vidrios;

    @Column(name = "nro_nivel")
    private short numeroNivel;

    @Column(name = "nro_bloque")
    private short numeroBloque;

    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;

    @JsonIgnore
    @JsonBackReference(value = "predioOtrosTipoConstruccion")
    @JoinColumn(name = "id_predio", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Predio predio;

    public OtrosTipoConstruccion() {
        this.cubierta = 7;
        this.estructura = 1;
        this.tipoConstruccion = 1;
        this.paredes = 0;
        this.piso = 3;
        this.sobrepiso = 5;
        this.revestimiento = 6;
        this.tumbado = 6;
        this.ventana = 6;
        this.vidrios = 0;
        this.numeroNivel = 1;
        this.numeroBloque = 1;
    }

    public OtrosTipoConstruccion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getCubierta() {
        return cubierta;
    }

    public void setCubierta(short cubierta) {
        this.cubierta = cubierta;
    }

    public short getEstructura() {
        return estructura;
    }

    public void setEstructura(short estructura) {
        this.estructura = estructura;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public short getTipoConstruccion() {
        return tipoConstruccion;
    }

    public void setTipoConstruccion(short tipoConstruccion) {
        this.tipoConstruccion = tipoConstruccion;
    }

    public short getUnidad() {
        return unidad;
    }

    public void setUnidad(short unidad) {
        this.unidad = unidad;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public short getParedes() {
        return paredes;
    }

    public void setParedes(short paredes) {
        this.paredes = paredes;
    }

    public short getPiso() {
        return piso;
    }

    public void setPiso(short piso) {
        this.piso = piso;
    }

    public short getSobrepiso() {
        return sobrepiso;
    }

    public void setSobrepiso(short sobrepiso) {
        this.sobrepiso = sobrepiso;
    }

    public short getRevestimiento() {
        return revestimiento;
    }

    public void setRevestimiento(short revestimiento) {
        this.revestimiento = revestimiento;
    }

    public short getTumbado() {
        return tumbado;
    }

    public void setTumbado(short tumbado) {
        this.tumbado = tumbado;
    }

    public short getVentana() {
        return ventana;
    }

    public void setVentana(short ventana) {
        this.ventana = ventana;
    }

    public short getVidrios() {
        return vidrios;
    }

    public void setVidrios(short vidrios) {
        this.vidrios = vidrios;
    }

    public short getNumeroNivel() {
        return numeroNivel;
    }

    public void setNumeroNivel(short numeroNivel) {
        this.numeroNivel = numeroNivel;
    }

    public short getNumeroBloque() {
        return numeroBloque;
    }

    public void setNumeroBloque(short numeroBloque) {
        this.numeroBloque = numeroBloque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String medida() {
        if (this.variable == 101) {
            return this.unidad + " U";
        }
        if (this.variable == 102) {
            return this.area + " m&sup3;";
        }

        return this.area + " m&sup2;";
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
        if (!(object instanceof OtrosTipoConstruccion)) {
            return false;
        }
        OtrosTipoConstruccion other = (OtrosTipoConstruccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * @return the varia
     */
    public Variable getVaria() {
        return varia;
    }

    /**
     * @param varia the varia to set
     */
    public void setVaria(Variable varia) {
        this.varia = varia;
    }

}
