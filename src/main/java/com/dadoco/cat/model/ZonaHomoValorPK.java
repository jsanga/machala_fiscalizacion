/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

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
public class ZonaHomoValorPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "anio")
    private short anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_zona_homo")
    private int idZonaHomo;

    public ZonaHomoValorPK() {
    }

    public ZonaHomoValorPK(short anio, int idZonaHomo) {
        this.anio = anio;
        this.idZonaHomo = idZonaHomo;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public int getIdZonaHomo() {
        return idZonaHomo;
    }

    public void setIdZonaHomo(int idZonaHomo) {
        this.idZonaHomo = idZonaHomo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) anio;
        hash += (int) idZonaHomo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZonaHomoValorPK)) {
            return false;
        }
        ZonaHomoValorPK other = (ZonaHomoValorPK) object;
        if (this.anio != other.anio) {
            return false;
        }
        if (this.idZonaHomo != other.idZonaHomo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  anio + "-" + idZonaHomo;
    }
    
}
