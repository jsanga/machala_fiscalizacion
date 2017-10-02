/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Where;

/**
 *
 * @author dairon
 */
@Entity
@Table(name = "cat_contribuyente_predio")
@NamedQueries({
    @NamedQuery(name = "ContribuyentePredio.findAll", query = "SELECT c FROM ContribuyentePredio c")
    ,
    @NamedQuery(name = "ContribuyentePredio.findByIdContribuyente", query = "SELECT c FROM ContribuyentePredio c WHERE c.contribuyentePredioPK.idContribuyente = :idContribuyente")
    ,
    @NamedQuery(name = "ContribuyentePredio.findByIdPredio", query = "SELECT c FROM ContribuyentePredio c WHERE c.contribuyentePredioPK.idPredio = :idPredio")
    ,
    @NamedQuery(name = "ContribuyentePredio.findByStatus", query = "SELECT c FROM ContribuyentePredio c WHERE c.status = :status")
    ,
    @NamedQuery(name = "ContribuyentePredio.findByContribuyenteIdent", query = "SELECT c FROM ContribuyentePredio c WHERE c.contribuyente.identificacion = ?1")
    ,@NamedQuery(name = "ContribuyentePredio.findByContribuyenteNombCompleto", query = "SELECT c FROM ContribuyentePredio c WHERE c.contribuyente.nombreCompleto LIKE ?1 and estadoNew = 1")
//@NamedQuery(name = "ContribuyentePredio.findPropietarios", query = "SELECT cp FROM ContribuyentePredio cp JOIN Contribuyente c ON cp.contribuyentePredioPK.idContribuyente = c.id JOIN Predio p ON cp.contribuyentePredioPK.idPredio = p.id  WHERE cp.contribuyentePredioPK.idContribuyente = ?1 AND cp.status = ?2")
})
public class ContribuyentePredio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContribuyentePredioPK contribuyentePredioPK;
    @Column(name = "status")
    private Short status;

    @Column(name = "porcentaje_participacion")
    private float porcentajeParticipacion;
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "id_predio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Predio predio;

    @JsonIgnore
    @JoinColumn(name = "id_contribuyente", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contribuyente contribuyente;
    
    @Column(name = "estado_new")
    private Short estadoNew;

    public ContribuyentePredio() {
        this.status = 0;
    }

    public ContribuyentePredio(ContribuyentePredioPK contribuyentePredioPK) {
        this.status = 0;
        this.contribuyentePredioPK = contribuyentePredioPK;
    }

    public ContribuyentePredio(int idContribuyente, int idPredio) {
        this.status = 0;
        this.contribuyentePredioPK = new ContribuyentePredioPK(idContribuyente, idPredio);
    }

    public ContribuyentePredioPK getContribuyentePredioPK() {
        return contribuyentePredioPK;
    }

    public void setContribuyentePredioPK(ContribuyentePredioPK contribuyentePredioPK) {
        this.contribuyentePredioPK = contribuyentePredioPK;
    }

    /**
     * 0 - PROPIETARIO 1 - POSESIONARIO 2 - ARRENDATARIO 3 - PROPIETARIO
     * ELIMINADO 4 - POSESIONARIO ELIMINADO 5 - ARRENDATARIO ELIMINADO 3 - 4 -5
     * HISTORICO DE PROPIETARIOS ANTERIORES
     *
     * @return
     */
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public float getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(float porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contribuyentePredioPK != null ? contribuyentePredioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContribuyentePredio)) {
            return false;
        }
        ContribuyentePredio other = (ContribuyentePredio) object;
        if ((this.contribuyentePredioPK == null && other.contribuyentePredioPK != null) || (this.contribuyentePredioPK != null && !this.contribuyentePredioPK.equals(other.contribuyentePredioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.ContribuyentePredio[ contribuyentePredioPK=" + contribuyentePredioPK + " ]";
    }

    public Short getEstadoNew() {
        return estadoNew;
    }

    public void setEstadoNew(Short estadoNew) {
        this.estadoNew = estadoNew;
    }

}
