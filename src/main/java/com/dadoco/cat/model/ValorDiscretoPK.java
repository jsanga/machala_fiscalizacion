/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ing. Carlos Loor Vargas
 */
public class ValorDiscretoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_variable", nullable = false)
    private int idVariable;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short valor;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short anio;

    public ValorDiscretoPK() {
    }

    public ValorDiscretoPK(int idVariable, short valor, short anio) {
        this.idVariable = idVariable;
        this.valor = valor;
        this.anio = anio;
    }

    public int getIdVariable() {
        return idVariable;
    }

    public void setIdVariable(int idVariable) {
        this.idVariable = idVariable;
    }

    public short getValor() {
        return valor;
    }

    public void setValor(short valor) {
        this.valor = valor;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idVariable;
        hash += (int) valor;
        hash += (int) anio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorDiscretoPK)) {
            return false;
        }
        ValorDiscretoPK other = (ValorDiscretoPK) object;
        if (this.idVariable != other.idVariable) {
            return false;
        }
        if (this.valor != other.valor) {
            return false;
        }
        return this.anio == other.anio;
    }

    @Override
    public String toString() {
        return idVariable + "-" + valor + "-" + anio;
    }

}
