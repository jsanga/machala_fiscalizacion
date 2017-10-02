/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.ren.model;

import com.dadoco.cat.model.ValorDiscreto;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "estructura_vida_util_residuo")
@NamedQueries({
    @NamedQuery(name = "EstructuraVidaUtilResiduo.findAll", query = "SELECT e FROM EstructuraVidaUtilResiduo e")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByIdVariableEstructura", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.estructuraVidaUtilResiduoPK.idVariableEstructura = :idVariableEstructura")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByValorEstructura", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.estructuraVidaUtilResiduoPK.valorEstructura = :valorEstructura")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByAnio", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.estructuraVidaUtilResiduoPK.anio = :anio")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByTipoConstruccion", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.estructuraVidaUtilResiduoPK.tipoConstruccion = :tipoConstruccion")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByVidaUtil", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.vidaUtil = :vidaUtil")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByResiduo", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.residuo = :residuo")
    , @NamedQuery(name = "EstructuraVidaUtilResiduo.findByTexto", query = "SELECT e FROM EstructuraVidaUtilResiduo e WHERE e.texto = :texto")})
public class EstructuraVidaUtilResiduo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstructuraVidaUtilResiduoPK estructuraVidaUtilResiduoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vida_util")
    private double vidaUtil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "residuo")
    private double residuo;
    @Size(max = 100)
    @Column(name = "texto")
    private String texto;
    @JoinColumns({
        @JoinColumn(name = "id_variable_estructura", referencedColumnName = "id_variable", insertable = false, updatable = false)
        , @JoinColumn(name = "valor_estructura", referencedColumnName = "valor", insertable = false, updatable = false)
        , @JoinColumn(name = "anio", referencedColumnName = "anio", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ValorDiscreto valorDiscreto;

    public EstructuraVidaUtilResiduo() {
    }

    public EstructuraVidaUtilResiduo(EstructuraVidaUtilResiduoPK estructuraVidaUtilResiduoPK) {
        this.estructuraVidaUtilResiduoPK = estructuraVidaUtilResiduoPK;
    }

    public EstructuraVidaUtilResiduo(EstructuraVidaUtilResiduoPK estructuraVidaUtilResiduoPK, double vidaUtil, double residuo) {
        this.estructuraVidaUtilResiduoPK = estructuraVidaUtilResiduoPK;
        this.vidaUtil = vidaUtil;
        this.residuo = residuo;
    }

    public EstructuraVidaUtilResiduo(int idVariableEstructura, short valorEstructura, short anio, short tipoConstruccion) {
        this.estructuraVidaUtilResiduoPK = new EstructuraVidaUtilResiduoPK(idVariableEstructura, valorEstructura, anio, tipoConstruccion);
    }

    public EstructuraVidaUtilResiduoPK getEstructuraVidaUtilResiduoPK() {
        return estructuraVidaUtilResiduoPK;
    }

    public void setEstructuraVidaUtilResiduoPK(EstructuraVidaUtilResiduoPK estructuraVidaUtilResiduoPK) {
        this.estructuraVidaUtilResiduoPK = estructuraVidaUtilResiduoPK;
    }

    public double getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(double vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public double getResiduo() {
        return residuo;
    }

    public void setResiduo(double residuo) {
        this.residuo = residuo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
        hash += (estructuraVidaUtilResiduoPK != null ? estructuraVidaUtilResiduoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstructuraVidaUtilResiduo)) {
            return false;
        }
        EstructuraVidaUtilResiduo other = (EstructuraVidaUtilResiduo) object;
        if ((this.estructuraVidaUtilResiduoPK == null && other.estructuraVidaUtilResiduoPK != null) || (this.estructuraVidaUtilResiduoPK != null && !this.estructuraVidaUtilResiduoPK.equals(other.estructuraVidaUtilResiduoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + estructuraVidaUtilResiduoPK;
    }
    
}
