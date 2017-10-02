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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_escritura")
@NamedQueries({
    @NamedQuery(name = "Escritura.findAll", query = "SELECT e FROM Escritura e")
    ,
    @NamedQuery(name = "Escritura.findById", query = "SELECT e FROM Escritura e WHERE e.id = ?1")
    ,
    @NamedQuery(name = "Escritura.findByPredio", query = "SELECT e FROM Escritura e WHERE e.predio.id = ?1 ORDER BY e.fechaInscripcion DESC")})
public class Escritura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "notaria")
    private String notaria;
    @Size(max = 100)
    @Column(name = "canton")
    private String canton;
    @Column(name = "provincia")
    private String provincia;
    @Column(name = "nombre_notario")
    private String nombreNotario;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_escritura")
    @Temporal(TemporalType.DATE)
    private Date fechaEscritura;
    @Size(max = 50)
    @Column(name = "folio")
    private String folio;
    @Size(max = 50)
    @Column(name = "nro_inscripcion")
    private String nroInscripcion;
    @Size(max = 50)
    @Column(name = "repertorio")
    private String repertorio;
    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "alicuota")
    private Double alicuota;
    @Column(name = "phv")
    private Short phv;
    @Column(name = "phh")
    private Short phh;
    @Column(name = "area")
    private Double area;
    @Column(name = "perimetro")
    private Double perimetro;
    @Column(name = "area_terreno")
    private Double areaTerreno;

    @Column(name = "norte_longitud")
    private String norteLongitud;

    @Column(name = "sur_longitud")
    private String surLongitud;

    @Column(name = "este_longitud")
    private String esteLongitud;

    @Column(name = "oeste_longitud")
    private String oesteLongitud;

    @Column(name = "norte_ref")
    private String norteRef;

    @Column(name = "sur_ref")
    private String surRef;

    @Column(name = "este_ref")
    private String esteRef;

    @Column(name = "oeste_ref")
    private String oesteRef;

    @Column(name = "area_construccion")
    private Double areaConstruccion;

    private Double superficie;
    @JsonIgnore
    @Column(name = "nro_lamina")
    private String nroLamina;

    @Column(name = "nro_resolucion")
    private String nroResolucion;

    @Column(name = "nro_catastro")
    private String nroCatastro;

    @Column(name = "fecha_catastro")
    @Temporal(TemporalType.DATE)
    private Date fechaCatastro;

    @Column(name = "presentaron_escritura")
    private short presentaronEscritura;
    @Column(name = "estado_escritura")
    private Short estadoEscritura;

    @Column(name = "ficha_registral")
    private String fichaRegistral;

    @Column(name = "tipos_ph")
    private short tipoPH;
    @Column(name = "fecha_resolucion_municipal")
    @Temporal(TemporalType.DATE)
    private Date fechaResolucionMunicipal;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "resolucion_municipal")
    private String resolucionMunicipal;
    @Column(name = "cant_bloques")
    private short cantidadBloques;
    @Column(name = "cant_alicuotas")
    private short cantidadAlicuotas;

    @Column(name = "tomo")
    private String tomo;
    @JsonBackReference(value = "predioEscritura")
    @JsonIgnore
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;
    @JoinTable(name = "cat_contribuyente_escritura", joinColumns = {
        @JoinColumn(name = "id_escritura", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_contribuyente", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Contribuyente> contribuyentes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escritura")
    private List<ArchivoEscritura> adjuntos;

    public Escritura() {
    }

    public Escritura(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotaria() {
        return notaria;
    }

    public void setNotaria(String notaria) {
        this.notaria = notaria;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public Date getFechaEscritura() {
        return fechaEscritura;
    }

    public void setFechaEscritura(Date fechaEscritura) {
        this.fechaEscritura = fechaEscritura;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNroInscripcion() {
        return nroInscripcion;
    }

    public void setNroInscripcion(String nroInscripcion) {
        this.nroInscripcion = nroInscripcion;
    }

    public String getRepertorio() {
        return repertorio;
    }

    public void setRepertorio(String repertorio) {
        this.repertorio = repertorio;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Double getAlicuota() {
        return alicuota;
    }

    public void setAlicuota(Double alicuota) {
        this.alicuota = alicuota;
    }

    public Short getPhv() {
        return phv;
    }

    public void setPhv(Short phv) {
        this.phv = phv;
    }

    public Short getPhh() {
        return phh;
    }

    public void setPhh(Short phh) {
        this.phh = phh;
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

    public String getNorteLongitud() {
        return norteLongitud;
    }

    public void setNorteLongitud(String norteLongitud) {
        this.norteLongitud = norteLongitud;
    }

    public String getSurLongitud() {
        return surLongitud;
    }

    public void setSurLongitud(String surLongitud) {
        this.surLongitud = surLongitud;
    }

    public String getEsteLongitud() {
        return esteLongitud;
    }

    public void setEsteLongitud(String esteLongitud) {
        this.esteLongitud = esteLongitud;
    }

    public String getOesteLongitud() {
        return oesteLongitud;
    }

    public void setOesteLongitud(String oesteLongitud) {
        this.oesteLongitud = oesteLongitud;
    }

    public String getNorteRef() {
        return norteRef;
    }

    public void setNorteRef(String norteRef) {
        this.norteRef = norteRef;
    }

    public String getSurRef() {
        return surRef;
    }

    public void setSurRef(String surRef) {
        this.surRef = surRef;
    }

    public String getEsteRef() {
        return esteRef;
    }

    public void setEsteRef(String esteRef) {
        this.esteRef = esteRef;
    }

    public String getOesteRef() {
        return oesteRef;
    }

    public void setOesteRef(String oesteRef) {
        this.oesteRef = oesteRef;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public List<Contribuyente> getContribuyentes() {
        return contribuyentes;
    }

    public void setContribuyentes(List<Contribuyente> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }

    public List<ArchivoEscritura> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<ArchivoEscritura> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public Double getAreaConstruccion() {
        return areaConstruccion;
    }

    public void setAreaConstruccion(Double areaConstruccion) {
        this.areaConstruccion = areaConstruccion;
    }

    public Double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public String getNroLamina() {
        return nroLamina;
    }

    public void setNroLamina(String nroLamina) {
        this.nroLamina = nroLamina;
    }

    public String getNroResolucion() {
        return nroResolucion;
    }

    public void setNroResolucion(String nroResolucion) {
        this.nroResolucion = nroResolucion;
    }

    public String getNroCatastro() {
        return nroCatastro;
    }

    public void setNroCatastro(String nroCatastro) {
        this.nroCatastro = nroCatastro;
    }

    public Date getFechaCatastro() {
        return fechaCatastro;
    }

    public void setFechaCatastro(Date fechaCatastro) {
        this.fechaCatastro = fechaCatastro;
    }

    public short getPresentaronEscritura() {
        return presentaronEscritura;
    }

    public void setPresentaronEscritura(short presentaronEscritura) {
        this.presentaronEscritura = presentaronEscritura;
    }

    public String getFichaRegistral() {
        return fichaRegistral;
    }

    public void setFichaRegistral(String fichaRegistral) {
        this.fichaRegistral = fichaRegistral;
    }

    public short getTipoPH() {
        return tipoPH;
    }

    public void setTipoPH(short tipoPH) {
        this.tipoPH = tipoPH;
    }

    public Date getFechaResolucionMunicipal() {
        return fechaResolucionMunicipal;
    }

    public void setFechaResolucionMunicipal(Date fechaResolucionMunicipal) {
        this.fechaResolucionMunicipal = fechaResolucionMunicipal;
    }

    public String getResolucionMunicipal() {
        return resolucionMunicipal;
    }

    public void setResolucionMunicipal(String resolucionMunicipal) {
        this.resolucionMunicipal = resolucionMunicipal;
    }

    public short getCantidadBloques() {
        return cantidadBloques;
    }

    public void setCantidadBloques(short cantidadBloques) {
        this.cantidadBloques = cantidadBloques;
    }

    public short getCantidadAlicuotas() {
        return cantidadAlicuotas;
    }

    public void setCantidadAlicuotas(short cantidadAlicuotas) {
        this.cantidadAlicuotas = cantidadAlicuotas;
    }

    public String getTomo() {
        return tomo;
    }

    public void setTomo(String tomo) {
        this.tomo = tomo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNombreNotario() {
        return nombreNotario;
    }

    public void setNombreNotario(String nombreNotario) {
        this.nombreNotario = nombreNotario;
    }

    public Double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(Double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Short getEstadoEscritura() {
        return estadoEscritura;
    }

    public void setEstadoEscritura(Short estadoEscritura) {
        this.estadoEscritura = estadoEscritura;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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
        if (!(object instanceof Escritura)) {
            return false;
        }
        Escritura other = (Escritura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Escritura[ id=" + id + " ]";
    }

}
