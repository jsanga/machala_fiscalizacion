/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;


import com.dadoco.auth.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_dato_inspeccion")
@NamedQueries({
    @NamedQuery(name = "DatoInspeccion.findAll", query = "SELECT d FROM DatoInspeccion d"),
    @NamedQuery(name = "DatoInspeccion.findById", query = "SELECT d FROM DatoInspeccion d WHERE d.id = :id"),
    @NamedQuery(name = "DatoInspeccion.findByFecha", query = "SELECT d FROM DatoInspeccion d WHERE d.fecha = :fecha"),
    @NamedQuery(name = "DatoInspeccion.findBySecuencial", query = "SELECT d FROM DatoInspeccion d WHERE d.secuencial = :secuencial"),
    @NamedQuery(name = "DatoInspeccion.findByTipoInspeccion", query = "SELECT d FROM DatoInspeccion d WHERE d.tipoInspeccion = :tipoInspeccion"),
    @NamedQuery(name = "DatoInspeccion.findByHabitada", query = "SELECT d FROM DatoInspeccion d WHERE d.habitada = :habitada"),
    @NamedQuery(name = "DatoInspeccion.findByObservaciones", query = "SELECT d FROM DatoInspeccion d WHERE d.observaciones = :observaciones"),
    @NamedQuery(name = "DatoInspeccion.findByNumeroRecibo", query = "SELECT d FROM DatoInspeccion d WHERE d.numeroRecibo = :numeroRecibo"),
    @NamedQuery(name = "DatoInspeccion.findByMotivo", query = "SELECT d FROM DatoInspeccion d WHERE d.motivo = :motivo"),
    @NamedQuery(name = "DatoInspeccion.findBySolicitanteNombre", query = "SELECT d FROM DatoInspeccion d WHERE d.solicitanteNombre = :solicitanteNombre"),
    @NamedQuery(name = "DatoInspeccion.findBySolicitanteApellidos", query = "SELECT d FROM DatoInspeccion d WHERE d.solicitanteApellidos = :solicitanteApellidos"),
    @NamedQuery(name = "DatoInspeccion.findBySolicitanteIdentificacion", query = "SELECT d FROM DatoInspeccion d WHERE d.solicitanteIdentificacion = :solicitanteIdentificacion"),
    @NamedQuery(name = "DatoInspeccion.findByDetallesInspeccion", query = "SELECT d FROM DatoInspeccion d WHERE d.detallesInspeccion = :detallesInspeccion"),
    @NamedQuery(name = "DatoInspeccion.findByLinNorteLongitud", query = "SELECT d FROM DatoInspeccion d WHERE d.linNorteLongitud = :linNorteLongitud"),
    @NamedQuery(name = "DatoInspeccion.findByLinSurLongitud", query = "SELECT d FROM DatoInspeccion d WHERE d.linSurLongitud = :linSurLongitud"),
    @NamedQuery(name = "DatoInspeccion.findByLinEsteLongitud", query = "SELECT d FROM DatoInspeccion d WHERE d.linEsteLongitud = :linEsteLongitud"),
    @NamedQuery(name = "DatoInspeccion.findByLinOesteLongitud", query = "SELECT d FROM DatoInspeccion d WHERE d.linOesteLongitud = :linOesteLongitud"),
    @NamedQuery(name = "DatoInspeccion.findByLinNorteRef", query = "SELECT d FROM DatoInspeccion d WHERE d.linNorteRef = :linNorteRef"),
    @NamedQuery(name = "DatoInspeccion.findByLinSurRef", query = "SELECT d FROM DatoInspeccion d WHERE d.linSurRef = :linSurRef"),
    @NamedQuery(name = "DatoInspeccion.findByLinEsteRef", query = "SELECT d FROM DatoInspeccion d WHERE d.linEsteRef = :linEsteRef"),
    @NamedQuery(name = "DatoInspeccion.findByLinOesteRef", query = "SELECT d FROM DatoInspeccion d WHERE d.linOesteRef = :linOesteRef"),
    @NamedQuery(name = "DatoInspeccion.findByArea", query = "SELECT d FROM DatoInspeccion d WHERE d.area = :area"),
    @NamedQuery(name = "DatoInspeccion.findByPerimetro", query = "SELECT d FROM DatoInspeccion d WHERE d.perimetro = :perimetro")})
public class DatoInspeccion implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "secuencial")
    private int secuencial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_inspeccion")
    private boolean tipoInspeccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "habitada")
    private boolean habitada;
    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 150)
    @Column(name = "numero_recibo")
    private String numeroRecibo;
    @Size(max = 1000)
    @Column(name = "motivo")
    private String motivo;
    @Size(max = 200)
    @Column(name = "solicitante_nombre")
    private String solicitanteNombre;
    @Size(max = 200)
    @Column(name = "solicitante_apellidos")
    private String solicitanteApellidos;
    @Size(max = 20)
    @Column(name = "solicitante_identificacion")
    private String solicitanteIdentificacion;
    @Size(max = 4000)
    @Column(name = "detalles_inspeccion")
    private String detallesInspeccion;
    @Size(max = 255)
    @Column(name = "lin_norte_longitud")
    private String linNorteLongitud;
    @Size(max = 255)
    @Column(name = "lin_sur_longitud")
    private String linSurLongitud;
    @Size(max = 255)
    @Column(name = "lin_este_longitud")
    private String linEsteLongitud;
    @Size(max = 255)
    @Column(name = "lin_oeste_longitud")
    private String linOesteLongitud;
    @Size(max = 255)
    @Column(name = "lin_norte_ref")
    private String linNorteRef;
    @Size(max = 255)
    @Column(name = "lin_sur_ref")
    private String linSurRef;
    @Size(max = 255)
    @Column(name = "lin_este_ref")
    private String linEsteRef;
    @Size(max = 255)
    @Column(name = "lin_oeste_ref")
    private String linOesteRef;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "area")
    private Double area;
    @Column(name = "perimetro")
    private Double perimetro;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JsonIgnore
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;

    public DatoInspeccion() {
    }

    public DatoInspeccion(Integer id) {
        this.id = id;
    }

    public DatoInspeccion(Integer id, Date fecha, int secuencial, boolean tipoInspeccion, boolean habitada) {
        this.id = id;
        this.fecha = fecha;
        this.secuencial = secuencial;
        this.tipoInspeccion = tipoInspeccion;
        this.habitada = habitada;
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

    public int getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(int secuencial) {
        this.secuencial = secuencial;
    }

    public boolean getTipoInspeccion() {
        return tipoInspeccion;
    }

    public void setTipoInspeccion(boolean tipoInspeccion) {
        this.tipoInspeccion = tipoInspeccion;
    }

    public boolean getHabitada() {
        return habitada;
    }

    public void setHabitada(boolean habitada) {
        this.habitada = habitada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones.toUpperCase();
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo.toUpperCase();
    }

    public String getSolicitanteNombre() {
        return solicitanteNombre;
    }

    public void setSolicitanteNombre(String solicitanteNombre) {
        this.solicitanteNombre = solicitanteNombre.toUpperCase();
    }

    public String getSolicitanteApellidos() {
        return solicitanteApellidos;
    }

    public void setSolicitanteApellidos(String solicitanteApellidos) {
        this.solicitanteApellidos = solicitanteApellidos.toUpperCase();
    }

    public String getSolicitanteIdentificacion() {
        return solicitanteIdentificacion;
    }

    public void setSolicitanteIdentificacion(String solicitanteIdentificacion) {
        this.solicitanteIdentificacion = solicitanteIdentificacion;
    }

    public String getDetallesInspeccion() {
        return detallesInspeccion;
    }

    public void setDetallesInspeccion(String detallesInspeccion) {
        this.detallesInspeccion = detallesInspeccion.toUpperCase();
    }

    public String getLinNorteLongitud() {
        return linNorteLongitud;
    }

    public void setLinNorteLongitud(String linNorteLongitud) {
        this.linNorteLongitud = linNorteLongitud;
    }

    public String getLinSurLongitud() {
        return linSurLongitud;
    }

    public void setLinSurLongitud(String linSurLongitud) {
        this.linSurLongitud = linSurLongitud;
    }

    public String getLinEsteLongitud() {
        return linEsteLongitud;
    }

    public void setLinEsteLongitud(String linEsteLongitud) {
        this.linEsteLongitud = linEsteLongitud;
    }

    public String getLinOesteLongitud() {
        return linOesteLongitud;
    }

    public void setLinOesteLongitud(String linOesteLongitud) {
        this.linOesteLongitud = linOesteLongitud;
    }

    public String getLinNorteRef() {
        return linNorteRef;
    }

    public void setLinNorteRef(String linNorteRef) {
        this.linNorteRef = linNorteRef.toUpperCase();
    }

    public String getLinSurRef() {
        return linSurRef;
    }

    public void setLinSurRef(String linSurRef) {
        this.linSurRef = linSurRef.toUpperCase();
    }

    public String getLinEsteRef() {
        return linEsteRef;
    }

    public void setLinEsteRef(String linEsteRef) {
        this.linEsteRef = linEsteRef.toUpperCase();
    }

    public String getLinOesteRef() {
        return linOesteRef;
    }

    public void setLinOesteRef(String linOesteRef) {
        this.linOesteRef = linOesteRef.toUpperCase();
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(Double perimetro) {
        this.perimetro = perimetro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
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
        if (!(object instanceof DatoInspeccion)) {
            return false;
        }
        DatoInspeccion other = (DatoInspeccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.DatoInspeccion[ id=" + id + " ]";
    }
    
}
