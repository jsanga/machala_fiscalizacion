/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "categoria_acabado")
@NamedQueries({
    @NamedQuery(name = "CategoriaAcabado.findAll", query = "SELECT c FROM CategoriaAcabado c")
    , @NamedQuery(name = "CategoriaAcabado.findByValor", query = "SELECT c FROM CategoriaAcabado c WHERE c.valor = :valor")
    , @NamedQuery(name = "CategoriaAcabado.findByTexto", query = "SELECT c FROM CategoriaAcabado c WHERE c.texto = :texto")})
public class CategoriaAcabado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "valor")
    private String valor;
    @Size(max = 200)
    @Column(name = "texto")
    private String texto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaAcabado")
    private List<CategoriaValor> valores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaAcabado")
    private List<CategoriaEstructuraNivel> categoriaEstructuraNivelList;

    public CategoriaAcabado() {
    }

    public CategoriaAcabado(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public List<CategoriaValor> getValores() {
        return valores;
    }

    public void setValores(List<CategoriaValor> valores) {
        this.valores = valores;
    }

    public List<CategoriaEstructuraNivel> getCategoriaEstructuraNivelList() {
        return categoriaEstructuraNivelList;
    }

    public void setCategoriaEstructuraNivelList(List<CategoriaEstructuraNivel> categoriaEstructuraNivelList) {
        this.categoriaEstructuraNivelList = categoriaEstructuraNivelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valor != null ? valor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaAcabado)) {
            return false;
        }
        CategoriaAcabado other = (CategoriaAcabado) object;
        return !((this.valor == null && other.valor != null) || (this.valor != null && !this.valor.equals(other.valor)));
    }

    @Override
    public String toString() {
        return "com.dadoco.ren.model.CategoriaAcabado[ valor=" + valor + " ]";
    }

}
