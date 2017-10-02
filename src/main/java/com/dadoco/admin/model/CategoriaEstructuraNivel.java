/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.model;

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

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "categoria_estructura_nivel")
@NamedQueries({
    @NamedQuery(name = "CategoriaEstructuraNivel.findAll", query = "SELECT c FROM CategoriaEstructuraNivel c")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findById", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.categoriaEstructuraNivelPK.id = ?1 AND c.categoriaEstructuraNivelPK.anio = ?2 ORDER BY c.categoriaEstructuraNivelPK.valorEstructura, c.categoriaEstructuraNivelPK.idCategoria")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByGrupo", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.categoriaEstructuraNivelPK.id = ?1 AND c.categoriaEstructuraNivelPK.anio = ?2 AND c.categoriaEstructuraNivelPK.valorEstructura = ?3 ORDER BY c.categoriaEstructuraNivelPK.valorEstructura, c.categoriaEstructuraNivelPK.idCategoria")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByIdAndValorEstructura", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.categoriaEstructuraNivelPK.valorEstructura = ?1 AND c.categoriaEstructuraNivelPK.id = ?2 AND c.categoriaEstructuraNivelPK.anio = ?3 ORDER BY c.categoriaEstructuraNivelPK.valorEstructura, c.categoriaEstructuraNivelPK.idCategoria")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByValorEstructura", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.categoriaEstructuraNivelPK.valorEstructura = :valorEstructura")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByAnio", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.categoriaEstructuraNivelPK.anio = :anio")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByIdCategoria", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.categoriaEstructuraNivelPK.idCategoria = :idCategoria")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByValor", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.valor = :valor")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByTexto", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.texto = :texto")
    , @NamedQuery(name = "CategoriaEstructuraNivel.findByHabilitado", query = "SELECT c FROM CategoriaEstructuraNivel c WHERE c.habilitado = :habilitado")})
public class CategoriaEstructuraNivel implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategoriaEstructuraNivelPK categoriaEstructuraNivelPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    @Size(max = 100)
    @Column(name = "texto")
    private String texto;
    @Column(name = "habilitado")
    private Boolean habilitado;
    @JoinColumn(name = "id_categoria", referencedColumnName = "valor", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CategoriaAcabado categoriaAcabado;
    @JoinColumns({
        @JoinColumn(name = "id_variable_estructura", referencedColumnName = "id_variable", insertable = false, updatable = false)
        , @JoinColumn(name = "valor_estructura", referencedColumnName = "valor", insertable = false, updatable = false)
        , @JoinColumn(name = "anio", referencedColumnName = "anio", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ValorDiscreto valorDiscreto;

    @Transient
    private List<CategoriaEstructuraNivel> dependientes;

    @Transient
    private String nextOnEnter;

    public CategoriaEstructuraNivel() {
    }

    public CategoriaEstructuraNivel(CategoriaEstructuraNivelPK categoriaEstructuraNivelPK) {
        this.categoriaEstructuraNivelPK = categoriaEstructuraNivelPK;
    }

    public CategoriaEstructuraNivel(CategoriaEstructuraNivelPK categoriaEstructuraNivelPK, double valor) {
        this.categoriaEstructuraNivelPK = categoriaEstructuraNivelPK;
        this.valor = valor;
    }

    public CategoriaEstructuraNivel(int id, int idVariableEstructura, short valorEstructura, short anio, String idCategoria) {
        this.categoriaEstructuraNivelPK = new CategoriaEstructuraNivelPK(id, idVariableEstructura, valorEstructura, anio, idCategoria);
    }

    public CategoriaEstructuraNivelPK getCategoriaEstructuraNivelPK() {
        return categoriaEstructuraNivelPK;
    }

    public void setCategoriaEstructuraNivelPK(CategoriaEstructuraNivelPK categoriaEstructuraNivelPK) {
        this.categoriaEstructuraNivelPK = categoriaEstructuraNivelPK;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public CategoriaAcabado getCategoriaAcabado() {
        return categoriaAcabado;
    }

    public void setCategoriaAcabado(CategoriaAcabado categoriaAcabado) {
        this.categoriaAcabado = categoriaAcabado;
    }

    public ValorDiscreto getValorDiscreto() {
        return valorDiscreto;
    }

    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
    }

    public List<CategoriaEstructuraNivel> getDependientes() {
        return dependientes;
    }

    public void setDependientes(List<CategoriaEstructuraNivel> dependientes) {
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
        hash += (categoriaEstructuraNivelPK != null ? categoriaEstructuraNivelPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaEstructuraNivel)) {
            return false;
        }
        CategoriaEstructuraNivel other = (CategoriaEstructuraNivel) object;
        if ((this.categoriaEstructuraNivelPK == null && other.categoriaEstructuraNivelPK != null) || (this.categoriaEstructuraNivelPK != null && !this.categoriaEstructuraNivelPK.equals(other.categoriaEstructuraNivelPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + categoriaEstructuraNivelPK;
    }

}
