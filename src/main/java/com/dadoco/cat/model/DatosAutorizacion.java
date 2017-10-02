/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dairon
 */
@Entity
@Table(name = "cat_dato_autorizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosAutorizacion.findAll", query = "SELECT d FROM DatosAutorizacion d"),
    @NamedQuery(name = "DatosAutorizacion.findById", query = "SELECT d FROM DatosAutorizacion d WHERE d.id = :id"),
    @NamedQuery(name = "DatosAutorizacion.findByFecha", query = "SELECT d FROM DatosAutorizacion d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "DatosAutorizacion.findByNumero", query = "SELECT d FROM DatosAutorizacion d WHERE d.numero = :numero"),
    @NamedQuery(name = "DatosAutorizacion.findByResponsable", query = "SELECT d FROM DatosAutorizacion d WHERE d.responsable = :responsable"),
    @NamedQuery(name = "DatosAutorizacion.findByObservaciones", query = "SELECT d FROM DatosAutorizacion d WHERE d.observaciones = :observaciones")})
public class DatosAutorizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "numero")
    private Integer numero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "responsable")
    private String responsable;
    @NotNull
    @Size(max = 150)
    @Column(name = "referencia")
    private String referencia;
    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;
    @JsonIgnore
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio idPredio;
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDocumento tipoDocumento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosAutorizacion")
    private List<ArchivoDatoAutorizacion> adjuntos;

    public DatosAutorizacion() {

    }

    public DatosAutorizacion(Integer id) {
        this.id = id;
    }

    public DatosAutorizacion(Integer id, Date fecha, String responsable) {
        this.id = id;
        this.fecha = fecha;
        this.responsable = responsable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @JsonIgnore
    public Predio getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Predio idPredio) {
        this.idPredio = idPredio;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setIdTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<ArchivoDatoAutorizacion> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<ArchivoDatoAutorizacion> adjuntos) {
        this.adjuntos = adjuntos;
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
        if (!(object instanceof DatosAutorizacion)) {
            return false;
        }
        DatosAutorizacion other = (DatosAutorizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


}
