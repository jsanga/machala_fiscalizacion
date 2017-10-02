/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ing. Carlos Loor
 */
@Entity
@Table(name = "excepcion", schema = "tipologias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Excepcion.findAll", query = "SELECT e FROM Excepcion e")
    , @NamedQuery(name = "Excepcion.findById", query = "SELECT e FROM Excepcion e WHERE e.id = :id")
    , @NamedQuery(name = "Excepcion.findByClaveCatastral", query = "SELECT e FROM Excepcion e WHERE e.claveCatastral = :claveCatastral")
    , @NamedQuery(name = "Excepcion.findByAtributo", query = "SELECT e FROM Excepcion e WHERE e.atributo = :atributo")
    , @NamedQuery(name = "Excepcion.findByObservacion", query = "SELECT e FROM Excepcion e WHERE e.observacion = :observacion")
    , @NamedQuery(name = "Excepcion.findByFecCre", query = "SELECT e FROM Excepcion e WHERE e.fecCre = :fecCre")
    , @NamedQuery(name = "Excepcion.findByUsrCre", query = "SELECT e FROM Excepcion e WHERE e.usrCre = :usrCre")
    , @NamedQuery(name = "Excepcion.findByEstado", query = "SELECT e FROM Excepcion e WHERE e.estado = :estado")})
public class Excepcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 24)
    @Column(name = "clave_catastral", nullable = false, length = 24)
    private String claveCatastral;
    @Size(max = 2147483647)
    @Column(name = "atributo", length = 2147483647)
    private String atributo;
    @Size(max = 2147483647)
    @Column(name = "observacion", length = 2147483647)
    private String observacion;
    @Column(name = "fec_cre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecCre;
    @Size(max = 50)
    @Column(name = "usr_cre", length = 50)
    private String usrCre;
    @Column(name = "estado")
    private Boolean estado;

    public Excepcion() {
    }

    public Excepcion(Long id) {
        this.id = id;
    }

    public Excepcion(Long id, String claveCatastral) {
        this.id = id;
        this.claveCatastral = claveCatastral;
    }

    public Excepcion(String claveCatastral, String atributo, String observacion) {
        this.claveCatastral = claveCatastral;
        this.atributo = atributo;
        this.observacion = observacion;
        this.estado = Boolean.TRUE;
        this.fecCre = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveCatastral() {
        return claveCatastral;
    }

    public void setClaveCatastral(String claveCatastral) {
        this.claveCatastral = claveCatastral;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecCre() {
        return fecCre;
    }

    public void setFecCre(Date fecCre) {
        this.fecCre = fecCre;
    }

    public String getUsrCre() {
        return usrCre;
    }

    public void setUsrCre(String usrCre) {
        this.usrCre = usrCre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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
        if (!(object instanceof Excepcion)) {
            return false;
        }
        Excepcion other = (Excepcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.cat.tipologias.model.Excepcion[ id=" + id + " ]";
    }

}
