/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author dfcalderio
 */
@Entity

@Table(name = "variable", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tabla", "columna"})})
@NamedQueries({
    @NamedQuery(name = "Variable.findAll", query = "SELECT v FROM Variable v")
    ,
    @NamedQuery(name = "Variable.findById", query = "SELECT v FROM Variable v WHERE v.id = :id")
    ,
    @NamedQuery(name = "Variable.findByTabla", query = "SELECT v FROM Variable v WHERE v.tabla = :tabla")
    ,
    @NamedQuery(name = "Variable.findByColumna", query = "SELECT v FROM Variable v WHERE v.columna = :columna")
    ,
    @NamedQuery(name = "Variable.findByTipo", query = "SELECT v FROM Variable v WHERE v.tipo = :tipo")})
public class Variable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tabla")
    private String tabla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "columna")
    private String columna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private Character tipo;
    @JsonIgnore
    //@LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variable")
    private List<ValorDiscreto> valores;

    @Column(name = "nombre")
    private String nombre;

    public Variable() {
    }

    public Variable(Integer id) {
        this.id = id;
    }

    public Variable(Integer id, String tabla, String columna, Character tipo) {
        this.id = id;
        this.tabla = tabla;
        this.columna = columna;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    public List<ValorDiscreto> getValores() {
        return valores;
    }

    public void setValores(List<ValorDiscreto> valores) {
        this.valores = valores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof Variable)) {
            return false;
        }
        Variable other = (Variable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Variable[ id=" + id + " ]";
    }

}
