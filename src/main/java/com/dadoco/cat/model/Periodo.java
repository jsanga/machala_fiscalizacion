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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p"),
    @NamedQuery(name = "Periodo.findById", query = "SELECT p FROM Periodo p WHERE p.id = :id"),
    @NamedQuery(name = "Periodo.findByAnioInicial", query = "SELECT p FROM Periodo p WHERE p.anioInicial = ?1"),
    @NamedQuery(name = "Periodo.findByAnioFinal", query = "SELECT p FROM Periodo p WHERE p.anioFinal = :anioFinal")})
public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio_inicial")
    private int anioInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio_final")
    private int anioFinal;
    @Basic(optional = false)
    @JsonIgnore    
    @JsonBackReference
    @OneToMany(mappedBy = "periodo")
    private List<ZonaHomogenea> zonasHomogeneasList;

    public Periodo() {
    }

    public Periodo(Integer id) {
        this.id = id;
    }

    public Periodo(Integer id, int anioInicial, int anioFinal) {
        this.id = id;
        this.anioInicial = anioInicial;
        this.anioFinal = anioFinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnioInicial() {
        return anioInicial;
    }

    public void setAnioInicial(int anioInicial) {
        this.anioInicial = anioInicial;
    }

    public int getAnioFinal() {
        return anioFinal;
    }

    public void setAnioFinal(int anioFinal) {
        this.anioFinal = anioFinal;
    }

    @XmlTransient
    public List<ZonaHomogenea> getZonasHomogeneasList() {
        return zonasHomogeneasList;
    }

    public void setZonasHomogeneasList(List<ZonaHomogenea> zonasHomogeneasList) {
        this.zonasHomogeneasList = zonasHomogeneasList;
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
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebas.Periodo[ id=" + id + " ]";
    }

}
