/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.flow.model.HtTramite;
import com.dadoco.flow.model.HtTramiteDet;
import com.dadoco.flow.model.TrAprobacionPlanos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.annotations.Where;

/**
 *
 * @author dfcalderio
 */
@Entity
//@Table(indexes = {
//    @Index(name = "IDX_MYIDX1", columnList = "id"),
//    @Index(name = "IDX_MYIDX2", columnList = "nombre")})
@NamedQueries({
    @NamedQuery(name = "Contribuyente.findByMatch", query = "SELECT c FROM Contribuyente c WHERE (UPPER(c.identificacion) LIKE :dato OR UPPER(c.apellidoMaterno) like :dato OR UPPER(c.apellidoPaterno) LIKE :dato OR UPPER(c.nombre) like :dato) AND c.estado = 1 ORDER BY c.id DESC")
    ,
    @NamedQuery(name = "Contribuyente.countByMatch", query = "SELECT Count(c) FROM Contribuyente c WHERE (UPPER(c.identificacion) LIKE :dato OR UPPER(c.apellidoMaterno) like :dato OR UPPER(c.apellidoPaterno) LIKE :dato OR UPPER(c.nombre) like :dato) AND c.estado = 1")
    ,
    @NamedQuery(name = "Contribuyente.findAll", query = "SELECT c FROM Contribuyente c   WHERE  c.estado = ?1 ORDER BY c.id")
    ,
    @NamedQuery(name = "Contribuyente.findLike", query = "SELECT c FROM Contribuyente c WHERE c.nombre LIKE :code OR c.apellidoPaterno LIKE :code OR c.apellidoMaterno LIKE :code OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code")
    ,
    @NamedQuery(name = "Contribuyente.findLike2", query = "SELECT c FROM Contribuyente c WHERE "
            + "(c.nombre LIKE :code1 OR c.apellidoPaterno LIKE :code1 OR c.apellidoMaterno LIKE :code1 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code1) AND"
            + "(c.nombre LIKE :code2 OR c.apellidoPaterno LIKE :code2 OR c.apellidoMaterno LIKE :code2 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code2)")
    ,
    @NamedQuery(name = "Contribuyente.findLike3", query = "SELECT c FROM Contribuyente c WHERE "
            + "(c.nombre LIKE :code1 OR c.apellidoPaterno LIKE :code1 OR c.apellidoMaterno LIKE :code1 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code1) AND"
            + "(c.nombre LIKE :code2 OR c.apellidoPaterno LIKE :code2 OR c.apellidoMaterno LIKE :code2 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code2) AND"
            + "(c.nombre LIKE :code3 OR c.apellidoPaterno LIKE :code3 OR c.apellidoMaterno LIKE :code3 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code3)")
    ,
    @NamedQuery(name = "Contribuyente.findLike4", query = "SELECT c FROM Contribuyente c WHERE "
            + "(c.nombre LIKE :code1 OR c.apellidoPaterno LIKE :code1 OR c.apellidoMaterno LIKE :code1 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code1) AND"
            + "(c.nombre LIKE :code2 OR c.apellidoPaterno LIKE :code2 OR c.apellidoMaterno LIKE :code2 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code2) AND"
            + "(c.nombre LIKE :code3 OR c.apellidoPaterno LIKE :code3 OR c.apellidoMaterno LIKE :code3 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code3) AND"
            + "(c.nombre LIKE :code4 OR c.apellidoPaterno LIKE :code4 OR c.apellidoMaterno LIKE :code4 OR CONCAT(c.nombre,' ',c.apellidoPaterno,' ',c.apellidoMaterno) LIKE :code4)")
    ,
    @NamedQuery(name = "Contribuyente.findIdentificacion", query = "SELECT c FROM Contribuyente c WHERE c.identificacion LIKE :code")
    ,
    @NamedQuery(name = "Contribuyente.findByIdentificacion", query = "SELECT c FROM Contribuyente c WHERE c.identificacion=?1")
    ,
    @NamedQuery(name = "Contribuyente.findByNombre", query = "SELECT c FROM Contribuyente c WHERE c.nombre=?1")
    ,
    @NamedQuery(name = "Contribuyente.findByLikeFull", query = "SELECT c FROM Contribuyente c WHERE c.nombre=?1")
    ,
    @NamedQuery(name = "Contribuyente.findByTipoIdentificacion", query = "SELECT c FROM Contribuyente c WHERE c.tipoIdentificacion = ?1 AND c.identificacion = ?2 AND c.estado = ?3")})

public class Contribuyente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)

    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_identificacion", nullable = false)
    private String tipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(max = 2000)
    @Column(nullable = false, length = 2000)
    private String identificacion;

    @Basic(optional = false)
    @Column(name = "tipo", nullable = false)
    private String tipo;
    @Basic(optional = false)
    @Size(max = 2000)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @Size(max = 255)
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @Size(max = 255)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    private String direccion;
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 30)
    @Column(length = 30)
    private String telefono;
    @Size(max = 30)
    @Column(length = 30)
    private String celular;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(length = 50)
    private String email;
    @Basic(optional = false)
    @Column(name = "contribuyente_especial")
    private String contribuyenteEspecial;
    @Basic(optional = false)
    @Column(name = "lleva_contabilidad")
    private String llevaContabilidad;
    @Basic(optional = false)
    @Column(name = "sin_fines_lucro")
    private String sinFinesLucro;
    @Basic(optional = false)
    @Size(min = 1, max = 1)
    @Column(nullable = false)
    private String artesano;
    @Size(max = 20)
    @Column(name = "numero_registro_artesano", length = 20)
    private String numeroRegistroArtesano;
    @Basic(optional = false)
    @Column(nullable = false)
    private String discapacitado;
    @Size(max = 20)
    @Column(name = "numero_registro_discapacitado", length = 20)
    private String numeroRegistroDiscapacitado;
    @Column(name = "porcentaje_discapacitado")
    private BigInteger porcentajeDiscapacitado;
    @Size(max = 60)
    @Column(name = "representante_legal", length = 60)
    private String representanteLegal;
    @Size(max = 60)
    @Column(name = "representante_legal_cedula", length = 60)
    private String representanteLegalCedula;
    @Basic(optional = false)
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = true)
    @Column(name = "estado", nullable = true)
    private short estado;
    @Basic(optional = false)
    @Column(name = "estado_civil", nullable = false)
    private short estadoCivil;

    @Basic(optional = false)
    @Column(name = "fuente", nullable = false)
    private short fuente;

    @Column(name = "inscripcion_propietario_juridico")
    private short inscripcionPropietarioJuridico;
    @Column(name = "nombres_conyuge")
    private String nombresConyuge;
    @Column(name = "direccion_razon_social")
    private String direccionRazonSocial;
    @JsonIgnore
    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private Collection<HtTramite> tramiteCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private Collection<HtTramiteDet> htTramiteDetCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private Collection<TrAprobacionPlanos> trAprobacionPlanosCollection;
//    @JsonIgnore
//    @JoinTable(name = "cat_contribuyente_predio", joinColumns = {
//        @JoinColumn(name = "id_contribuyente")}, inverseJoinColumns = {
//        @JoinColumn(name = "id_predio")})
//    @ManyToMany
//    private List<Predio> predioList;
    @JsonIgnore
    @JsonBackReference(value = "propietariosList")
    @ManyToMany(mappedBy = "propietarios")
    private List<Predio> predioList;

    @JsonIgnore
    @JsonBackReference(value = "contribuyentePredioList")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contribuyente")
    private List<ContribuyentePredio> contribuyentePredioList;
    @JsonIgnore
    @Transient
    short tipoPropietario;
    @JsonIgnore
    @Transient
    float porcientoParticipacion;

    public Contribuyente() {
        this.estadoCivil = 1;
        this.tipo = "N";
        this.tipoIdentificacion = "C";

        this.contribuyenteEspecial = "N";
        this.llevaContabilidad = "N";
        this.sinFinesLucro = "S";
        this.artesano = "N";
        this.discapacitado = "N";
        this.estado = 1;
        this.inscripcionPropietarioJuridico = 1;
    }

    public Contribuyente(String tipoIdentificacion, String identificacion, String tipo,
            String nombre, String apellidoMaterno, String apellidoPaterno, Date fechaNacimiento,
            String direccion, String sexo, String telefono, String celular, String email,
            String contribuyenteEspecial, String llevaContabilidad, String sinFinesLucro,
            String artesano, String numeroRegistroArtesano,
            String discapacitado, String numeroRegistroDiscapacitado, BigInteger porcentajeDiscapacitado,
            String representanteLegal, String representanteLegalCedula, Date fechaIngreso, short estado, short estadoCivil) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.tipo = tipo;
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.sexo = sexo;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.contribuyenteEspecial = contribuyenteEspecial;
        this.llevaContabilidad = llevaContabilidad;
        this.sinFinesLucro = sinFinesLucro;
        this.artesano = artesano;
        this.numeroRegistroArtesano = numeroRegistroArtesano;
        this.discapacitado = discapacitado;
        this.numeroRegistroDiscapacitado = numeroRegistroDiscapacitado;
        this.porcentajeDiscapacitado = porcentajeDiscapacitado;
        this.representanteLegal = representanteLegal;
        this.representanteLegalCedula = representanteLegalCedula;
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
        this.estadoCivil = estadoCivil;
        this.inscripcionPropietarioJuridico = 1;
    }

    /**
     * 0 - PROPIETARIO 1 - POSESIONARIO 2 - ARRENDATARIO
     *
     * @return
     */
    public short getTipoPropietario() {
        return tipoPropietario;
    }

    public void setTipoPropietario(short tipoPropietario) {
        this.tipoPropietario = tipoPropietario;
    }

    public float getPorcientoParticipacion() {
        return porcientoParticipacion;
    }

    public void setPorcientoParticipacion(float porcientoParticipacion) {
        this.porcientoParticipacion = porcientoParticipacion;
    }

    public void obtenerPorcentajeParticipacion(int idPredio) {
        for (ContribuyentePredio cp : contribuyentePredioList) {
            if (this.id == cp.getContribuyentePredioPK().getIdContribuyente() && cp.getContribuyentePredioPK().getIdPredio() == idPredio) {
                this.porcientoParticipacion = cp.getPorcentajeParticipacion();
            }
        }
    }

    public void obtenerEstatus(int idPredio) {
        for (ContribuyentePredio cp : contribuyentePredioList) {
            if (this.id == cp.getContribuyentePredioPK().getIdContribuyente() && cp.getContribuyentePredioPK().getIdPredio() == idPredio) {
                this.tipoPropietario = cp.getStatus();
            }
        }
    }

    public String valorTipoPropietario() {
        if (this.tipoPropietario == 1) {
            return "POSESIONARIO";
        }
        if (this.tipoPropietario == 2) {
            return "ARRENDATARIO";
        }
        return "PROPIETARIO";
    }

    @JsonIgnore
    public List<ContribuyentePredio> getContribuyentePredioList() {
        return contribuyentePredioList;
    }

    public void setContribuyentePredioList(List<ContribuyentePredio> contribuyentePredioList) {
        this.contribuyentePredioList = contribuyentePredioList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @JsonIgnore
    public Collection<HtTramite> getTramiteCollection() {
        return tramiteCollection;
    }

    public void setTramiteCollection(Collection<HtTramite> tramiteCollection) {
        this.tramiteCollection = tramiteCollection;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public short getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(short estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno.toUpperCase();
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno.toUpperCase();
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public String getLlevaContabilidad() {
        return llevaContabilidad;
    }

    public void setLlevaContabilidad(String llevaContabilidad) {
        this.llevaContabilidad = llevaContabilidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getSinFinesLucro() {
        return sinFinesLucro;
    }

    public void setSinFinesLucro(String sinFinesLucro) {
        this.sinFinesLucro = sinFinesLucro;
    }

    public String getArtesano() {
        return artesano;
    }

    public void setArtesano(String artesano) {
        this.artesano = artesano;
    }

    public String getNumeroRegistroArtesano() {
        return numeroRegistroArtesano;
    }

    public void setNumeroRegistroArtesano(String numeroRegistroArtesano) {
        this.numeroRegistroArtesano = numeroRegistroArtesano;
    }

    public String getDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(String discapacitado) {
        this.discapacitado = discapacitado;
    }

    public String getNumeroRegistroDiscapacitado() {
        return numeroRegistroDiscapacitado;
    }

    public void setNumeroRegistroDiscapacitado(String numeroRegistroDiscapacitado) {
        this.numeroRegistroDiscapacitado = numeroRegistroDiscapacitado;
    }

    public BigInteger getPorcentajeDiscapacitado() {
        return porcentajeDiscapacitado;
    }

    public void setPorcentajeDiscapacitado(BigInteger porcentajeDiscapacitado) {
        this.porcentajeDiscapacitado = porcentajeDiscapacitado;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getRepresentanteLegalCedula() {
        return representanteLegalCedula;
    }

    public void setRepresentanteLegalCedula(String representanteLegalCedula) {
        this.representanteLegalCedula = representanteLegalCedula;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    @JsonIgnore
    public List<Predio> getPredioList() {
        return predioList;
    }

    public void setPredioList(List<Predio> predioList) {
        this.predioList = predioList;
    }

    public short getFuente() {
        return fuente;
    }

    public void setFuente(short fuente) {
        this.fuente = fuente;
    }

    @JsonIgnore
    public Collection<HtTramiteDet> getHtTramiteDetCollection() {
        return htTramiteDetCollection;
    }

    public void setHtTramiteDetCollection(Collection<HtTramiteDet> htTramiteDetCollection) {
        this.htTramiteDetCollection = htTramiteDetCollection;
    }

    public Collection<TrAprobacionPlanos> getTrAprobacionPlanosCollection() {
        return trAprobacionPlanosCollection;
    }

    public void setTrAprobacionPlanosCollection(Collection<TrAprobacionPlanos> trAprobacionPlanosCollection) {
        this.trAprobacionPlanosCollection = trAprobacionPlanosCollection;
    }

    public short getInscripcionPropietarioJuridico() {
        return inscripcionPropietarioJuridico;
    }

    public void setInscripcionPropietarioJuridico(short inscripcionPropietarioJuridico) {
        this.inscripcionPropietarioJuridico = inscripcionPropietarioJuridico;
    }

    public String getNombresConyuge() {
        return nombresConyuge;
    }

    public void setNombresConyuge(String nombresConyuge) {
        this.nombresConyuge = nombresConyuge;
    }

    public String getDireccionRazonSocial() {
        return direccionRazonSocial;
    }

    public void setDireccionRazonSocial(String direccionRazonSocial) {
        this.direccionRazonSocial = direccionRazonSocial;
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
        if (!(object instanceof Contribuyente)) {
            return false;
        }
        Contribuyente other = (Contribuyente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Contribuyente[ id=" + id + " ]";
    }

    public String valorIdentificacion() {

        if (this.tipoIdentificacion.equals("P")) {
            return "Pasaporte";
        }

        if (this.tipoIdentificacion.equals("R")) {
            return "RUC";
        }
        if (this.tipoIdentificacion.equals("C")) {
            return "Cédula";
        }

        return "";
    }

    public String valorPersoneria() {

        if (this.tipo.equals("N")) {
            return "NATURAL";
        }
        if (this.tipo.equals("J")) {
            return "JURIDICO";
        }

        return "";
    }
}
