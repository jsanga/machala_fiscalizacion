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
public class ManzanaPK implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_zona", nullable = false, length = 2)
    private String codZona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_sector", nullable = false, length = 2)
    private String codSector;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_manzana", nullable = false, length = 2)
    private String codManzana;

    public ManzanaPK() {
    }
    
    public ManzanaPK(String codProvincia, String codCanton, String codParroquia, String codZona, String codSector, String codManzana) {
        this.codProvincia = codProvincia;
        this.codCanton = codCanton;
        this.codParroquia = codParroquia;
        this.codZona = codZona;
        this.codSector = codSector;
        this.codManzana = codManzana;
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

    public String getCodSector() {
        return codSector;
    }

    public void setCodSector(String codSector) {
        this.codSector = codSector;
    }

    public String getCodManzana() {
        return codManzana;
    }

    public void setCodManzana(String codManzana) {
        this.codManzana = codManzana;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.codProvincia);
        hash = 37 * hash + Objects.hashCode(this.codCanton);
        hash = 37 * hash + Objects.hashCode(this.codParroquia);
        hash = 37 * hash + Objects.hashCode(this.codZona);
        hash = 37 * hash + Objects.hashCode(this.codSector);
        hash = 37 * hash + Objects.hashCode(this.codManzana);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManzanaPK)) {
            return false;
        }
        ManzanaPK other = (ManzanaPK) object;
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
        if (this.codSector != other.codSector) {
            return false;
        }
        if (this.codManzana != other.codManzana) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + codProvincia + "-" + codCanton + "-" + codParroquia + "-" + codZona + "-" + codSector + "-" + codManzana;
    }
    
}
