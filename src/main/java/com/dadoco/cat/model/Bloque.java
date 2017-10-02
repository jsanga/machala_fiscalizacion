/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.common.annotations.GreaterZero;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.util.UploadFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_bloque")
@NamedQueries({
    @NamedQuery(name = "Bloque.findAll", query = "SELECT b FROM Bloque b")
    ,
    @NamedQuery(name = "Bloque.findByID", query = "SELECT b FROM Bloque b WHERE b.id = ?1")
    ,
    @NamedQuery(name = "Bloque.findByPredio", query = "SELECT b FROM Bloque b WHERE b.predio.id = ?1")})
public class Bloque implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_bloque")
    private int numeroBloque;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    private List<UsoSuelo> usosSuelo;

    @Basic(optional = false)
    @NotNull
    @GreaterZero
    @Column(name = "numero_niveles")
    private Integer numeroNiveles;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "area_permiso")
    private Double areaPermiso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "area_total")
    private double areaTotal;
    @Column(name = "etapa_construccion")
    private short etapaConstruccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edad_edificacion")
    private short edadEdificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_conservacion")
    private short estadoConservacion;
    @Column(name = "estructura")
    private Short estructura;
    @Column(name = "paredes")
    private Short paredes;
    @Column(name = "uso_construccion_rural")
    private Short usoConstruccionRural;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pisos")
    private short piso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sobrepiso")
    private short sobrepiso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "revestimiento")
    private short revestimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tumbado")
    private short tumbado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cubierta")
    private short cubierta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ventana")
    private short ventana;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vidrio")
    private short vidrio;

    @Basic(optional = false)
    @NotNull
    @Column(name = "columna")
    private short columna;

    @Basic(optional = false)
    @NotNull
    @Column(name = "vigas")
    private short vigas;

    @Basic(optional = false)
    @NotNull
    @Column(name = "entrepiso")
    private short entrepiso;

    @Basic(optional = false)
    @NotNull
    @Column(name = "revestimiento_cubierta")
    private short revestimientoCubierta;

    @Basic(optional = false)
    @NotNull
    @Column(name = "revestimiento_pared")
    private short revestimientoPared;

    @Basic(optional = false)
    @NotNull
    @Column(name = "puerta")
    private short puerta;
    @JsonBackReference(value = "predioBloques")
    @JsonIgnore
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Piso> pisos;
    @JsonIgnore
    @JsonBackReference(value = "predioBloqueImages")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloque")
    private List<BloqueImage> images;

    @JsonIgnore
    @Transient
    private List<UploadFile> fotosBloque;

    @JsonIgnore
    @Transient
    private List<ArchivoAux> imagesPreview;

    @JsonIgnore
    @Transient
    private boolean editado;
    
    @Column(name = "anio_construccion")
    private short anioConstruccion;

    public Bloque() {
        this.numeroNiveles = 1;
        this.etapaConstruccion = 3;
        this.estructura = 3;
        this.edadEdificacion = 3;
        this.estadoConservacion = 2;
        this.estructura = 1;
        this.paredes = 2;
        this.piso = 1;
        this.sobrepiso = 5;
        this.revestimiento = 6;
        this.tumbado = 6;
        this.cubierta = 7;
        this.ventana = 6;
        this.puerta = 1;
        this.vidrio = 5;
        this.revestimientoCubierta = 0;
        this.revestimientoPared = 2;
        this.usoConstruccionRural = 0;
        fotosBloque = new ArrayList<>();
        imagesPreview = new ArrayList<>();
    }

    public Bloque(Bloque b) {
        this.numeroNiveles = b.getNumeroNiveles();
        this.etapaConstruccion = b.getEtapaConstruccion();
        this.estructura = b.getEstructura();
        this.edadEdificacion = b.getEdadEdificacion();
        this.estadoConservacion = b.getEstadoConservacion();
        this.paredes = b.getParedes();
        this.piso = b.getPiso();
        this.sobrepiso = b.getSobrepiso();
        this.revestimiento = b.getRevestimiento();
        this.tumbado = b.getTumbado();
        this.cubierta = b.getCubierta();
        this.ventana = b.getVentana();
        this.vidrio = b.getVidrio();
        this.revestimientoCubierta = b.getRevestimientoCubierta();
        this.revestimientoPared = b.getRevestimientoPared();
        this.usoConstruccionRural = 0;
        fotosBloque = new ArrayList<>();
        imagesPreview = new ArrayList<>();
    }

    public Bloque(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroBloque() {
        return numeroBloque;
    }

    public void setNumeroBloque(int numeroBloque) {
        this.numeroBloque = numeroBloque;
    }

    public Integer getNumeroNiveles() {
        return numeroNiveles;
    }

    public void setNumeroNiveles(Integer numeroNiveles) {
        this.numeroNiveles = numeroNiveles;
    }

    public Double getAreaPermiso() {
        return areaPermiso;
    }

    public void setAreaPermiso(Double areaPermiso) {
        this.areaPermiso = areaPermiso;
    }

    public double getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(double areaTotal) {
        this.areaTotal = areaTotal;
    }

    public short getEdadEdificacion() {
        return edadEdificacion;
    }

    public void setEdadEdificacion(short edadEdificacion) {
        this.edadEdificacion = edadEdificacion;
    }

    public short getEstadoConservacion() {
        return estadoConservacion;
    }

    public void setEstadoConservacion(short estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public Short getEstructura() {
        return estructura;
    }

    public void setEstructura(Short estructura) {
        this.estructura = estructura;
    }

    public Short getParedes() {
        return paredes;
    }

    public void setParedes(Short paredes) {
        this.paredes = paredes;
    }

    public short getPiso() {
        return piso;
    }

    public void setPiso(short piso) {
        this.piso = piso;
    }

    public short getSobrepiso() {
        return sobrepiso;
    }

    public void setSobrepiso(short sobrepiso) {
        this.sobrepiso = sobrepiso;
    }

    public short getRevestimiento() {
        return revestimiento;
    }

    public void setRevestimiento(short revestimiento) {
        this.revestimiento = revestimiento;
    }

    public short getTumbado() {
        return tumbado;
    }

    public void setTumbado(short tumbado) {
        this.tumbado = tumbado;
    }

    public short getCubierta() {
        return cubierta;
    }

    public void setCubierta(short cubierta) {
        this.cubierta = cubierta;
    }

    public short getVentana() {
        return ventana;
    }

    public void setVentana(short ventana) {
        this.ventana = ventana;
    }

    public short getVidrio() {
        return vidrio;
    }

    public void setVidrio(short vidrio) {
        this.vidrio = vidrio;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public List<Piso> getPisos() {
        return pisos;
    }

    public void setPisos(List<Piso> pisos) {
        this.pisos = pisos;
    }

    public short getEtapaConstruccion() {
        return etapaConstruccion;
    }

    public void setEtapaConstruccion(short etapaConstruccion) {
        this.etapaConstruccion = etapaConstruccion;
    }

    @JsonIgnore
    public List<BloqueImage> getImages() {
        return images;
    }

    public void setImages(List<BloqueImage> images) {
        this.images = images;
    }

    @JsonIgnore
    public List<UploadFile> getFotosBloque() {
        return fotosBloque;
    }

    public void setFotosBloque(List<UploadFile> fotosBloque) {
        this.fotosBloque = fotosBloque;
    }

    @JsonIgnore
    public List<ArchivoAux> getImagesPreview() {
        return imagesPreview;
    }

    public void setImagesPreview(List<ArchivoAux> imagesPreview) {
        this.imagesPreview = imagesPreview;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public boolean isEditado() {
        return editado;
    }

    public void setEditado(boolean editado) {
        this.editado = editado;
    }

    public short getColumna() {
        return columna;
    }

    public void setColumna(short columna) {
        this.columna = columna;
    }

    public short getVigas() {
        return vigas;
    }

    public void setVigas(short vigas) {
        this.vigas = vigas;
    }

    public short getEntrepiso() {
        return entrepiso;
    }

    public void setEntrepiso(short entrepiso) {
        this.entrepiso = entrepiso;
    }

    public short getRevestimientoCubierta() {
        return revestimientoCubierta;
    }

    public void setRevestimientoCubierta(short revestimientoCubierta) {
        this.revestimientoCubierta = revestimientoCubierta;
    }

    public short getRevestimientoPared() {
        return revestimientoPared;
    }

    public void setRevestimientoPared(short revestimientoPared) {
        this.revestimientoPared = revestimientoPared;
    }

    public short getPuerta() {
        return puerta;
    }

    public void setPuerta(short puerta) {
        this.puerta = puerta;
    }

    public Short getUsoConstruccionRural() {
        return usoConstruccionRural;
    }

    public void setUsoConstruccionRural(Short usoConstruccionRural) {
        this.usoConstruccionRural = usoConstruccionRural;
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
        if (!(object instanceof Bloque)) {
            return false;
        }
        Bloque other = (Bloque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + numeroBloque;
    }

    public short getAnioConstruccion() {
        return anioConstruccion;
    }

    public void setAnioConstruccion(short anioConstruccion) {
        this.anioConstruccion = anioConstruccion;
    }

}
