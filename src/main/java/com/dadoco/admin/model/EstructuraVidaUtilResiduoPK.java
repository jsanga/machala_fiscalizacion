/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.ren.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dfcalderio
 */
@Embeddable
public class EstructuraVidaUtilResiduoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_variable_estructura")
    private int idVariableEstructura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_estructura")
    private short valorEstructura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_construccion")
    private short tipoConstruccion;

    public EstructuraVidaUtilResiduoPK() {
    }

    public EstructuraVidaUtilResiduoPK(int idVariableEstructura, short valorEstructura, short anio, short tipoConstruccion) {
        this.idVariableEstructura = idVariableEstructura;
        this.valorEstructura = valorEstructura;
        this.anio = anio;
        this.tipoConstruccion = tipoConstruccion;
    }

    public int getIdVariableEstructura() {
        return idVariableEstructura;
    }

    public void setIdVariableEstructura(int idVariableEstructura) {
        this.idVariableEstructura = idVariableEstructura;
    }

    public short getValorEstructura() {
        return valorEstructura;
    }

    public void setValorEstructura(short valorEstructura) {
        this.valorEstructura = valorEstructura;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public short getTipoConstruccion() {
        return tipoConstruccion;
    }

    public void setTipoConstruccion(short tipoConstruccion) {
        this.tipoConstruccion = tipoConstruccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idVariableEstructura;
        hash += (int) valorEstructura;
        hash += (int) anio;
        hash += (int) tipoConstruccion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstructuraVidaUtilResiduoPK)) {
            return false;
        }
        EstructuraVidaUtilResiduoPK other = (EstructuraVidaUtilResiduoPK) object;
        if (this.idVariableEstructura != other.idVariableEstructura) {
            return false;
        }
        if (this.valorEstructura != other.valorEstructura) {
            return false;
        }
        if (this.anio != other.anio) {
            return false;
        }
        if (this.tipoConstruccion != other.tipoConstruccion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + idVariableEstructura + "-" + valorEstructura + "-" + anio + "-" + tipoConstruccion;
    }
    
}
