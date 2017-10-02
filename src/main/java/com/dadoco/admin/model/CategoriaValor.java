/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "categoria_valor")
@NamedQueries({
    @NamedQuery(name = "CategoriaValor.findAll", query = "SELECT c FROM CategoriaValor c")
    , @NamedQuery(name = "CategoriaValor.findByAnio", query = "SELECT c FROM CategoriaValor c WHERE c.categoriaValorPK.anio = :anio")
    , @NamedQuery(name = "CategoriaValor.findByIdCategoria", query = "SELECT c FROM CategoriaValor c WHERE c.categoriaValorPK.idCategoria = :idCategoria")
    , @NamedQuery(name = "CategoriaValor.findByDesde", query = "SELECT c FROM CategoriaValor c WHERE c.desde = :desde")
    , @NamedQuery(name = "CategoriaValor.findByHasta", query = "SELECT c FROM CategoriaValor c WHERE c.hasta = :hasta")})
public class CategoriaValor implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategoriaValorPK categoriaValorPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    private short desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    private short hasta;
    @JoinColumn(name = "id_categoria", referencedColumnName = "valor", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CategoriaAcabado categoriaAcabado;

    public CategoriaValor() {
    }

    public CategoriaValor(CategoriaValorPK categoriaValorPK) {
        this.categoriaValorPK = categoriaValorPK;
    }

    public CategoriaValor(CategoriaValorPK categoriaValorPK, short desde, short hasta) {
        this.categoriaValorPK = categoriaValorPK;
        this.desde = desde;
        this.hasta = hasta;
    }

    public CategoriaValor(short anio, String idCategoria) {
        this.categoriaValorPK = new CategoriaValorPK(anio, idCategoria);
    }

    public CategoriaValorPK getCategoriaValorPK() {
        return categoriaValorPK;
    }

    public void setCategoriaValorPK(CategoriaValorPK categoriaValorPK) {
        this.categoriaValorPK = categoriaValorPK;
    }

    public short getDesde() {
        return desde;
    }

    public void setDesde(short desde) {
        this.desde = desde;
    }

    public short getHasta() {
        return hasta;
    }

    public void setHasta(short hasta) {
        this.hasta = hasta;
    }

    public CategoriaAcabado getCategoriaAcabado() {
        return categoriaAcabado;
    }

    public void setCategoriaAcabado(CategoriaAcabado categoriaAcabado) {
        this.categoriaAcabado = categoriaAcabado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriaValorPK != null ? categoriaValorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaValor)) {
            return false;
        }
        CategoriaValor other = (CategoriaValor) object;
        if ((this.categoriaValorPK == null && other.categoriaValorPK != null) || (this.categoriaValorPK != null && !this.categoriaValorPK.equals(other.categoriaValorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.CategoriaValor[ categoriaValorPK=" + categoriaValorPK + " ]";
    }

}
