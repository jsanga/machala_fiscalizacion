/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.ren.model;

import com.dadoco.cat.model.ValorDiscreto;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "valores_discreto_compuesto")
@XmlRootElement
@NamedQueries({
      @NamedQuery(name = "ValorDiscretoCompuesto.findAll", query = "SELECT c FROM ValorDiscretoCompuesto c")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findAllByFuerteAndDebilAndAnio", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.idVariableFuerte =?1 AND c.ValorDiscretoCompuestoPK.idVariableDebil =?2 AND c.ValorDiscretoCompuestoPK.anioFuerte =?3 AND c.ValorDiscretoCompuestoPK.anioDebil =?4 ORDER BY c.ValorDiscretoCompuestoPK.valorFuerte, c.ValorDiscretoCompuestoPK.valorDebil ")  
    , @NamedQuery(name = "ValorDiscretoCompuesto.findAllByAnio", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.anioFuerte =?1 AND c.ValorDiscretoCompuestoPK.anioDebil = ?2")    
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByFuerte", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.idVariableFuerte = ?1 AND c.ValorDiscretoCompuestoPK.anioFuerte = ?2 ORDER BY c.ValorDiscretoCompuestoPK.idVariableFuerte, c.ValorDiscretoCompuestoPK.valorFuerte")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByIdVariableFuerteAndValueFuerte", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.idVariableFuerte = ?1 AND c.ValorDiscretoCompuestoPK.valorFuerte = ?2 AND c.ValorDiscretoCompuestoPK.idVariableDebil =?3 AND c.ValorDiscretoCompuestoPK.anioFuerte = ?4 ORDER BY c.ValorDiscretoCompuestoPK.valorFuerte, c.ValorDiscretoCompuestoPK.valorDebil")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByValorFuerte", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.valorFuerte = :valorFuerte")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByAnioFuerte", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.anioFuerte = :anioFuerte")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByIdVariableDebil", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.idVariableDebil = :idVariableDebil")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByValorDebil", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.valorDebil = :valorDebil")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByAnioDebil", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.ValorDiscretoCompuestoPK.anioDebil = :anioDebil")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByTexto", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.texto = :texto")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByCoeficiente", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.coeficiente = :coeficiente")
    , @NamedQuery(name = "ValorDiscretoCompuesto.findByHabilitado", query = "SELECT c FROM ValorDiscretoCompuesto c WHERE c.habilitado = :habilitado")})
public class ValorDiscretoCompuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ValorDiscretoCompuestoPK ValorDiscretoCompuestoPK;
    @Size(max = 100)
    @Column(name = "texto")
    private String texto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "coeficiente")
    private float coeficiente;
    @Column(name = "habilitado")
    private Boolean habilitado;
    @JoinColumns({
        @JoinColumn(name = "id_variable_debil", referencedColumnName = "id_variable", insertable = false, updatable = false)
        , @JoinColumn(name = "valor_debil", referencedColumnName = "valor", insertable = false, updatable = false)
        , @JoinColumn(name = "anio_debil", referencedColumnName = "anio", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ValorDiscreto valorDebil;
    @JoinColumns({
        @JoinColumn(name = "id_variable_fuerte", referencedColumnName = "id_variable", insertable = false, updatable = false)
        , @JoinColumn(name = "valor_fuerte", referencedColumnName = "valor", insertable = false, updatable = false)
        , @JoinColumn(name = "anio_fuerte", referencedColumnName = "anio", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ValorDiscreto valorFuerte;
    
    @Transient
    private List<ValorDiscretoCompuesto> dependientes;
    
    @Transient
    private String nextOnEnter;

    public ValorDiscretoCompuesto() {
    }

    public ValorDiscretoCompuesto(ValorDiscretoCompuestoPK ValorDiscretoCompuestoPK) {
        this.ValorDiscretoCompuestoPK = ValorDiscretoCompuestoPK;
    }

    public ValorDiscretoCompuesto(ValorDiscretoCompuestoPK ValorDiscretoCompuestoPK, float coeficiente) {
        this.ValorDiscretoCompuestoPK = ValorDiscretoCompuestoPK;
        this.coeficiente = coeficiente;
    }

    public ValorDiscretoCompuesto(int idVariableFuerte, short valorFuerte, short anioFuerte, int idVariableDebil, short valorDebil, short anioDebil) {
        this.ValorDiscretoCompuestoPK = new ValorDiscretoCompuestoPK(idVariableFuerte, valorFuerte, anioFuerte, idVariableDebil, valorDebil, anioDebil);
    }

    public ValorDiscretoCompuestoPK getValorDiscretoCompuestoPK() {
        return ValorDiscretoCompuestoPK;
    }

    public void setValorDiscretoCompuestoPK(ValorDiscretoCompuestoPK ValorDiscretoCompuestoPK) {
        this.ValorDiscretoCompuestoPK = ValorDiscretoCompuestoPK;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public float getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(float coeficiente) {
        this.coeficiente = coeficiente;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public ValorDiscreto getValorDebil() {
        return valorDebil;
    }

    public void setValorDebil(ValorDiscreto valorDebil) {
        this.valorDebil = valorDebil;
    }

    public ValorDiscreto getValorFuerte() {
        return valorFuerte;
    }

    public void setValorFuerte(ValorDiscreto valorFuerte) {
        this.valorFuerte = valorFuerte;
    }

    public List<ValorDiscretoCompuesto> getDependientes() {
        return dependientes;
    }

    public void setDependientes(List<ValorDiscretoCompuesto> dependientes) {
        this.dependientes = dependientes;
    }

    public String getNextOnEnter() {
        return nextOnEnter;
    }

    public void setNextOnEnter(String nextOnEnter) {
        this.nextOnEnter = nextOnEnter;
    }

    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ValorDiscretoCompuestoPK != null ? ValorDiscretoCompuestoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorDiscretoCompuesto)) {
            return false;
        }
        ValorDiscretoCompuesto other = (ValorDiscretoCompuesto) object;
        return !((this.ValorDiscretoCompuestoPK == null && other.ValorDiscretoCompuestoPK != null) || (this.ValorDiscretoCompuestoPK != null && !this.ValorDiscretoCompuestoPK.equals(other.ValorDiscretoCompuestoPK)));
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.ValorDiscretoCompuesto[ ValorDiscretoCompuestoPK=" + ValorDiscretoCompuestoPK + " ]";
    }
    
}
