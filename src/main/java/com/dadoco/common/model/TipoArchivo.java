/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "adj_tipo_archivo")
@NamedQueries({
    @NamedQuery(name = "TipoArchivo.findAll", query = "SELECT t FROM TipoArchivo t"),
    @NamedQuery(name = "TipoArchivo.findById", query = "SELECT t FROM TipoArchivo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoArchivo.findByMime", query = "SELECT t FROM TipoArchivo t WHERE t.mime = :mime"),
    @NamedQuery(name = "TipoArchivo.findByExtension", query = "SELECT t FROM TipoArchivo t WHERE t.extension = :extension"),
    @NamedQuery(name = "TipoArchivo.findByDescripcion", query = "SELECT t FROM TipoArchivo t WHERE t.descripcion = :descripcion")})
public class TipoArchivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mime", nullable = false, length = 100)
    private String mime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "extension", nullable = false, length = 20)
    private String extension;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;
  
   
   
    
   

    public TipoArchivo() {
    }

    public TipoArchivo(Integer id) {
        this.id = id;
    }

    public TipoArchivo(Integer id, String mime, String extension, String descripcion) {
        this.id = id;
        this.mime = mime;
        this.extension = extension;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoArchivo)) {
            return false;
        }
        TipoArchivo other = (TipoArchivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.TipoArchivo[ id=" + id + " ]";
    }

   
}
