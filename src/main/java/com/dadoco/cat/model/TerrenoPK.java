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
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Embeddable
public class TerrenoPK implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(TerrenoPK.class);

    private static final long serialVersionUID = 1L;

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
    @Column(name = "cod_manzana", nullable = false, length = 3)
    private String codManzana;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_solar", nullable = false, length = 3)
    private String codSolar;

    public TerrenoPK() {
    }

    public TerrenoPK(ManzanaPK manzanaPK, String codSolar) {
        this.codProvincia = manzanaPK.getCodProvincia();
        this.codCanton = manzanaPK.getCodCanton();
        this.codParroquia = manzanaPK.getCodParroquia();
        this.codZona = manzanaPK.getCodZona();
        this.codSector = manzanaPK.getCodSector();
        this.codManzana = manzanaPK.getCodManzana();
        this.codSolar = codSolar;
    }

    public TerrenoPK(TerrenoPK pk, String parroquia) {
        this.codProvincia = pk.getCodProvincia();
        this.codCanton = pk.getCodCanton();
        this.codParroquia = parroquia;
        this.codZona = pk.getCodZona();
        this.codSector = pk.getCodSector();
        this.codManzana = pk.getCodManzana();
        this.codSolar = pk.getCodSolar();
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

    public String getCodSolar() {
        return codSolar;
    }

    public void setCodSolar(String codSolar) {
        this.codSolar = codSolar;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.codProvincia);
        hash = 83 * hash + Objects.hashCode(this.codCanton);
        hash = 83 * hash + Objects.hashCode(this.codParroquia);
        hash = 83 * hash + Objects.hashCode(this.codZona);
        hash = 83 * hash + Objects.hashCode(this.codSector);
        hash = 83 * hash + Objects.hashCode(this.codManzana);
        hash = 83 * hash + Objects.hashCode(this.codSolar);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        log.error("Comparing object Terreno " + this.getClass().getName() + " - " + object.getClass().getName());
        if (!(object instanceof TerrenoPK)) {
            return false;
        }
        TerrenoPK other = (TerrenoPK) object;
        if (!Objects.equals(this.codProvincia, other.codProvincia)) {
            return false;
        }
        if (!Objects.equals(this.codCanton, other.codCanton)) {
            return false;
        }
        if (!Objects.equals(this.codParroquia, other.codParroquia)) {
            return false;
        }
        if (!Objects.equals(this.codZona, other.codZona)) {
            return false;
        }
        if (!Objects.equals(this.codSector, other.codSector)) {
            return false;
        }
        if (!Objects.equals(this.codManzana, other.codManzana)) {
            return false;
        }
        return Objects.equals(this.codSolar, other.codSolar);
    }

    @Override
    public String toString() {
        return codParroquia + "-" + codZona + "-" + codSector + "-" + codManzana + "-" + codSolar + "-0-0-0";
    }

    public String getClave() {

        return String.format(codParroquia + "-" + codZona + "-" + codSector + "-" + codManzana + "-" + codSolar + "-000-000-000");
    }

}
