package com.dadoco.cat.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "valor_discreto")
@NamedQueries({
    @NamedQuery(name = "ValorDiscreto.findAll", query = "SELECT v FROM ValorDiscreto v ORDER BY v.valorDiscretoPK.valor")
    ,
    @NamedQuery(name = "ValorDiscreto.findAllByAnio", query = "SELECT v FROM ValorDiscreto v WHERE v.valorDiscretoPK.anio = ?1 ORDER BY v.valorDiscretoPK.valor")
    ,
    @NamedQuery(name = "ValorDiscreto.findByVariable", query = "SELECT v FROM ValorDiscreto v WHERE v.valorDiscretoPK.idVariable = ?1 AND v.valorDiscretoPK.anio = ?2 ORDER BY v.valorDiscretoPK.valor")
    ,
    @NamedQuery(name = "ValorDiscreto.findByVariableValor", query = "SELECT v FROM ValorDiscreto v WHERE v.valorDiscretoPK.idVariable = ?1 AND v.valorDiscretoPK.valor = ?2 AND v.valorDiscretoPK.anio = ?3 ORDER BY v.valorDiscretoPK.valor")})
public class ValorDiscreto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ValorDiscretoPK valorDiscretoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private float coeficiente;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private float operacion;
    @Column(name = "para_ph")
    private boolean paraPH;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short estado;
    //@JsonManagedReference
    @JoinColumn(name = "id_variable", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Variable variable;
    @JsonBackReference(value = "usoSueloCollectionValorDiscreto")
    @OneToMany(mappedBy = "valorDiscreto")
    private List<UsoSuelo> usoSueloCollection;
    @Column(name = "peso")
    private Integer peso;

    public ValorDiscreto() {
    }

    public ValorDiscreto(ValorDiscretoPK valorDiscretoPK) {
        this.valorDiscretoPK = valorDiscretoPK;
    }

    public ValorDiscreto(ValorDiscretoPK valorDiscretoPK, String texto) {
        this.valorDiscretoPK = valorDiscretoPK;
        this.texto = texto;
    }

    public ValorDiscreto(int idVariable, short valor, short anio) {
        this.valorDiscretoPK = new ValorDiscretoPK(idVariable, valor, anio);
    }

    public ValorDiscretoPK getValorDiscretoPK() {
        return valorDiscretoPK;
    }

    public void setValorDiscretoPK(ValorDiscretoPK valorDiscretoPK) {
        this.valorDiscretoPK = valorDiscretoPK;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public float getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(float coeficiente) {
        this.coeficiente = coeficiente;
    }

    public void setOperacion(float operacion) {
        this.operacion = operacion;
    }

    public float getOperacion() {
        return operacion;
    }

    public boolean isParaPH() {
        return paraPH;
    }

    public void setParaPH(boolean paraPH) {
        this.paraPH = paraPH;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public List<UsoSuelo> getUsoSueloCollection() {
        return usoSueloCollection;
    }

    public void setUsoSueloCollection(List<UsoSuelo> usoSueloCollection) {
        this.usoSueloCollection = usoSueloCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valorDiscretoPK != null ? valorDiscretoPK.hashCode() : 0);
        return hash;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ValorDiscreto)) {
            return false;
        }
        ValorDiscreto other = (ValorDiscreto) object;
        if ((this.valorDiscretoPK == null && other.valorDiscretoPK != null) || (this.valorDiscretoPK != null && !this.valorDiscretoPK.equals(other.valorDiscretoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.texto;
    }

}
