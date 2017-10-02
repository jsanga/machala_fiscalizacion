/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.ejb.EJB;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_piso")
@NamedQueries({
    @NamedQuery(name = "Piso.findAll", query = "SELECT p FROM Piso p"),
    @NamedQuery(name = "Piso.findAllByIdBloque", query = "SELECT p FROM Piso p WHERE p.bloque.id = ?1")})
public class Piso implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_piso")
    private int numeroPiso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel_piso")
    private Short nivelPiso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "area")
    private double area;
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "id_bloque", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Bloque bloque;
    
    public Piso() {
        this.nivelPiso = (short)1;
    }

    public Piso(Integer id) {
        this.id = id;
        this.nivelPiso = (short)1;
    }

    public Piso(Integer id, int numeroPiso, double area) {
        this.id = id;
        this.numeroPiso = numeroPiso;
        this.area = area;
        this.nivelPiso = (short)1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroPiso() {
        return numeroPiso;
    }

    public void setNumeroPiso(int numeroPiso) {
        this.numeroPiso = numeroPiso;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @JsonIgnore
    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public Short getNivelPiso() {
        return nivelPiso;
    }

    public void setNivelPiso(Short nivelPiso) {
        this.nivelPiso = nivelPiso;
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
        if (!(object instanceof Piso)) {
            return false;
        }
        Piso other = (Piso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Piso[ pisoPK=" + id + " ]";
    }
}
