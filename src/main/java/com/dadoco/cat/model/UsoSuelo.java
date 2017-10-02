/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author dcalderio
 */
@Entity
@Table(name = "cat_uso_suelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoSuelo.findAll", query = "SELECT u FROM UsoSuelo u")
    ,
    @NamedQuery(name = "UsoSuelo.findById", query = "SELECT u FROM UsoSuelo u WHERE u.id = :id")})
public class UsoSuelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "cod")
    private short cod;
    @JsonBackReference(value = "bloqueUsoSuelo")
    @JoinColumn(name = "id_bloque", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Bloque bloque;
    @JsonBackReference(value = "predioUsoSuelo")
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Predio predio;
    @JoinColumns({
        @JoinColumn(name = "id_variable", referencedColumnName = "id_variable")
        ,
        @JoinColumn(name = "valor", referencedColumnName = "valor")
        ,
    @JoinColumn(name = "anio", referencedColumnName = "anio")})
    @OneToOne(fetch = FetchType.LAZY)
    private ValorDiscreto valorDiscreto;

    public UsoSuelo() {
        this.cod = 12;
    }

    public UsoSuelo(Integer cod) {
        this.id = cod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getCod() {
        return cod;
    }

    public void setCod(short cod) {
        this.cod = cod;
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public ValorDiscreto getValorDiscreto() {
        return valorDiscreto;
    }

    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
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
        if (!(object instanceof UsoSuelo)) {
            return false;
        }
        UsoSuelo other = (UsoSuelo) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.model.UsoSuelo[ id=" + id + " ]";
    }

}
