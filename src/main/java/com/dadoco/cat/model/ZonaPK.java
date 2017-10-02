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
public class ZonaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_provincia", nullable = false)
    private String codProvincia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_canton", nullable = false)
    private String codCanton;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_parroquia", nullable = false)
    private String codParroquia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_zona", nullable = false)
    private String codZona;

    public ZonaPK() {
    }
    
    public ZonaPK(String codProvincia, String codCanton, String codParroquia, String codZona) {
        this.codProvincia = codProvincia;
        this.codCanton = codCanton;
        this.codParroquia = codParroquia;
        this.codZona = codZona;
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

    public String getCodZona() {
        return codZona;
    }

    public void setCodZona(String codZona) {
        this.codZona = codZona;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.codProvincia);
        hash = 97 * hash + Objects.hashCode(this.codCanton);
        hash = 97 * hash + Objects.hashCode(this.codParroquia);
        hash = 97 * hash + Objects.hashCode(this.codZona);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZonaPK)) {
            return false;
        }
        ZonaPK other = (ZonaPK) object;
        if (this.codProvincia != other.codProvincia) {
            return false;
        }
        if (this.codCanton != other.codCanton) {
            return false;
        }
        if (this.codParroquia != other.codParroquia) {
            return false;
        }
        if (this.codZona != other.codZona) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + codProvincia + "-" + codCanton + "-" + codParroquia + "-" + codZona;
    }
    
}
