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
public class ValorDiscretoCompuestoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_variable_fuerte")
    private int idVariableFuerte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_fuerte")
    private short valorFuerte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio_fuerte")
    private short anioFuerte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_variable_debil")
    private int idVariableDebil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_debil")
    private short valorDebil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio_debil")
    private short anioDebil;

    public ValorDiscretoCompuestoPK() {
    }

    public ValorDiscretoCompuestoPK(int idVariableFuerte, short valorFuerte, short anioFuerte, int idVariableDebil, short valorDebil, short anioDebil) {
        this.idVariableFuerte = idVariableFuerte;
        this.valorFuerte = valorFuerte;
        this.anioFuerte = anioFuerte;
        this.idVariableDebil = idVariableDebil;
        this.valorDebil = valorDebil;
        this.anioDebil = anioDebil;
    }

    public int getIdVariableFuerte() {
        return idVariableFuerte;
    }

    public void setIdVariableFuerte(int idVariableFuerte) {
        this.idVariableFuerte = idVariableFuerte;
    }

    public short getValorFuerte() {
        return valorFuerte;
    }

    public void setValorFuerte(short valorFuerte) {
        this.valorFuerte = valorFuerte;
    }

    public short getAnioFuerte() {
        return anioFuerte;
    }

    public void setAnioFuerte(short anioFuerte) {
        this.anioFuerte = anioFuerte;
    }

    public int getIdVariableDebil() {
        return idVariableDebil;
    }

    public void setIdVariableDebil(int idVariableDebil) {
        this.idVariableDebil = idVariableDebil;
    }

    public short getValorDebil() {
        return valorDebil;
    }

    public void setValorDebil(short valorDebil) {
        this.valorDebil = valorDebil;
    }

    public short getAnioDebil() {
        return anioDebil;
    }

    public void setAnioDebil(short anioDebil) {
        this.anioDebil = anioDebil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idVariableFuerte;
        hash += (int) valorFuerte;
        hash += (int) anioFuerte;
        hash += (int) idVariableDebil;
        hash += (int) valorDebil;
        hash += (int) anioDebil;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorDiscretoCompuestoPK)) {
            return false;
        }
        ValorDiscretoCompuestoPK other = (ValorDiscretoCompuestoPK) object;
        if (this.idVariableFuerte != other.idVariableFuerte) {
            return false;
        }
        if (this.valorFuerte != other.valorFuerte) {
            return false;
        }
        if (this.anioFuerte != other.anioFuerte) {
            return false;
        }
        if (this.idVariableDebil != other.idVariableDebil) {
            return false;
        }
        if (this.valorDebil != other.valorDebil) {
            return false;
        }
        if (this.anioDebil != other.anioDebil) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.ValorDiscretoCompuestoPK[ idVariableFuerte=" + idVariableFuerte + ", valorFuerte=" + valorFuerte + ", anioFuerte=" + anioFuerte + ", idVariableDebil=" + idVariableDebil + ", valorDebil=" + valorDebil + ", anioDebil=" + anioDebil + " ]";
    }
    
}
