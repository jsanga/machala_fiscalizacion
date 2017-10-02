/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dfcalderio
 */
@Embeddable
public class ParroquiaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_provincia", nullable = false, length = 2)
    private String codProvincia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_canton", nullable = false, length = 2)
    private String codCanton;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_parroquia", nullable = false, length = 2)
    private String codParroquia;

    public ParroquiaPK() {
    }
    
    public ParroquiaPK(String codProvincia, String codCanton, String codParroquia) {
        this.codProvincia = codProvincia;
        this.codCanton = codCanton;
        this.codParroquia = codParroquia;
    }

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    public String getCodCanton() {
        return codCanton;
    }

    public void setCodCanton(String codCanton) {
        this.codCanton = codCanton;
    }

    public String getCodParroquia() {
        return codParroquia;
    }

    public void setCodParroquia(String codParroquia) {
        this.codParroquia = codParroquia;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.codProvincia);
        hash = 61 * hash + Objects.hashCode(this.codCanton);
        hash = 61 * hash + Objects.hashCode(this.codParroquia);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParroquiaPK)) {
            return false;
        }
        ParroquiaPK other = (ParroquiaPK) object;
        if (this.codProvincia != other.codProvincia) {
            return false;
        }
        if (this.codCanton != other.codCanton) {
            return false;
        }
        if (this.codParroquia != other.codParroquia) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.ParroquiaPK[ codProvincia=" + codProvincia + ", codCanton=" + codCanton + ", codParroquia=" + codParroquia + " ]";
    }
    
}
