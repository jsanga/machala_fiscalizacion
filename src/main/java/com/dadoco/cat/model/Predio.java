/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.common.util.UploadFile;
import com.dadoco.flow.model.HtTramite;
import com.dadoco.flow.model.HtTramiteDet;
import com.dadoco.flow.model.TrAprobacionPlanos;
import com.dadoco.rentas.model.Exoneracion;
import com.dadoco.rentas.model.Liquidacion;
import com.dadoco.rentas.model.LocalComercial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Arrays;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * b
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_predio")
@NamedQueries({
    @NamedQuery(name = "Predio.findAll", query = "SELECT p FROM Predio p ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar, p.codPhv, p.codPhh")
    ,
    @NamedQuery(name = "Predio.findById", query = "SELECT p FROM Predio p where p.id = ?1 ")
    ,
    @NamedQuery(
            name = "Predio.findByMatch",
            query = "SELECT p FROM Predio p INNER JOIN p.propietarios d where p.claveCatastral LIKE :dato OR p.claveAnterior LIKE :dato OR (d IN (SELECT c FROM Contribuyente c WHERE c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato or c.identificacion like :dato)) OR p.nombreEdificio like :dato OR p.manzana LIKE :dato ORDER BY p.id ASC"
    )
    ,
    @NamedQuery(
            name = "Predio.findByMatchCount",
            query = "SELECT Count(p) FROM Predio p INNER JOIN p.propietarios d where p.claveCatastral LIKE :dato OR p.claveAnterior like :dato OR (d IN (SELECT c FROM Contribuyente c WHERE c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato OR c.identificacion like :dato)) OR p.nombreEdificio like :dato OR p.manzana LIKE :dato"
    )
    ,
    @NamedQuery(name = "Predio.findByCodes", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 AND t.terrenoPK.codSolar=?7 AND p.codBloque = ?8 AND p.codPhv = ?9 AND p.codPhh = ?10 AND p.tipoPredio = true AND p.estado = 1")
    ,
    @NamedQuery(name = "Predio.findByShortCodes", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codZona=?1 AND t.terrenoPK.codSector=?2 AND t.terrenoPK.codManzana=?3 AND t.terrenoPK.codSolar=?4 AND p.codPhv = ?5 AND p.codPhh = ?6 AND p.estado = 1")
    ,
    @NamedQuery(name = "Predio.findAllByManzana", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar, p.codPhv, p.codPhh")
    ,
    @NamedQuery(name = "Predio.findAllByManzanaTerreno", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 AND p.codBloque =?7 AND p.codPhv = ?8 AND p.codPhh = ?9 ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar")
    ,
    @NamedQuery(name = "Predio.findAllByManzanaTerrenoEstado", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 AND p.codBloque =?7 AND p.codPhv = ?8 AND p.codPhh = ?9 AND p.estado = 1 ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar")
    ,
    @NamedQuery(name = "Predio.findAllByManzanaPH", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 AND t.terrenoPK.codSolar =?7 ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar, p.codBloque, p.codPhv, p.codPhh")
    ,
    @NamedQuery(name = "Predio.findByClaveCatastral", query = "SELECT p FROM Predio p WHERE p.claveCatastral = ?1")
    ,
    @NamedQuery(name = "Predio.findByClaveCatastralSF", query = "SELECT p FROM Predio p WHERE replace(p.claveCatastral,'-','') = ?1")
    ,
    @NamedQuery(name = "Predio.findByClaveCatastralIn", query = "SELECT p FROM Predio p WHERE p.claveCatastral IN (?1)")
    ,
    @NamedQuery(name = "Predio.findClaveCatastral", query = "SELECT p FROM Predio p WHERE p.claveCatastral LIKE ?1")
    ,
    @NamedQuery(name = "Predio.findByClaveAnterior", query = "SELECT p FROM Predio p WHERE p.claveAnterior = ?1")
    ,
    @NamedQuery(name = "Predio.findAllByPH", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 AND t.terrenoPK.codSolar=?7 AND p.codPhv =?8 AND p.codPhh = ?9 ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar, p.codPhv, p.codPhh")
    ,
    @NamedQuery(name = "Predio.findAllPHByTerreno", query = "SELECT p FROM Predio p JOIN p.terreno t WHERE t.terrenoPK.codParroquia=?1 AND t.terrenoPK.codZona=?2 AND t.terrenoPK.codSector=?3 AND t.terrenoPK.codManzana=?4 AND t.terrenoPK.codSolar=?5 ORDER BY p.terreno.terrenoPK.codParroquia, p.terreno.terrenoPK.codZona, p.terreno.terrenoPK.codSector, p.terreno.terrenoPK.codManzana, p.terreno.terrenoPK.codSolar")
    ,
    @NamedQuery(name = "Predio.findAllIds", query = "SELECT p.id FROM Predio p WHERE id>=?1 ORDER BY p.id")
    ,
    @NamedQuery(name = "Predio.findAllIdsPH", query = "SELECT p.id FROM Predio p WHERE p.codPhv > 0 OR p.codPhh > 0 ORDER BY p.id")})
@JsonRootName(value = "predio")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Predio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    //@JsonProperty(value = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_bloque", length = 3)
    private String codBloque;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_phv", length = 3)
    private String codPhv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_phh", length = 3)
    private String codPhh;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "clave_catastral")
    private String claveCatastral;
    @Column(name = "numero_ficha")
    private String numeroFicha;
    @Size(min = 11, max = 14)
    @Column(name = "clave_anterior")
    private String claveAnterior;
    @Size(max = 100)
    @Column(name = "manzana")
    private String manzana;
    @Size(max = 25)
    @Column(name = "nro_predio")
    private String nroPredio;
    @Size(max = 10)
    @Column(name = "nro_departamento")
    private String nroDepartamento;
    @Size(max = 10)
    @Column(name = "nro_piso")
    private String nroPiso;
    @Column(name = "nro_bloque")
    private Short nroBloque;
    @Size(max = 150)
    @Column(name = "nombre_edificio")
    private String nombreEdificio;
    @Column(name = "permitieron_ingreso")
    private Short permitieronIngreso;
    @Column(name = "hubo_atencion")
    private Short huboAtencion;
    @Column(name = "nro_personas_habitan")
    private Short nroPersonasHabitan;
    @Column(name = "dominio")
    private short dominio;
    @Column(name = "origen")
    private short origen;
    @Column(name = "tipo_propiedad")
    private short tipoPropiedad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "alicuota_ph")
    private float alicuotaPh;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "usuario_modifica")
    private String usuarioModifica;

    @Column(name = "fecha_relevamiento")
    @Temporal(TemporalType.DATE)
    private Date fechaRelevamiento;

    @Column(name = "fecha_validacion")
    @Temporal(TemporalType.DATE)
    private Date fechaValidacion;

    @Column(name = "fecha_supervicion")
    @Temporal(TemporalType.DATE)
    private Date fechaSupervicion;

    @Column(name = "fecha_digitacion")
    @Temporal(TemporalType.DATE)
    private Date fechaDigitacion;

    @Column(name = "fecha_supervicion_digitacion")
    @Temporal(TemporalType.DATE)
    private Date fechaSupervicionDigitacion;

    @Column(name = "fecha_cartografia")
    @Temporal(TemporalType.DATE)
    private Date fechaCartografia;

    @Column(name = "id_predial_rural")
    private String identificacionPredialR;

    private String observacion;

    @Column(name = "estado")
    private Short estado;

    @JoinColumn(name = "id_contrato_arrendamiento", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private ContratoArrendamiento contratoArrendamiento;

    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    @OrderBy("id DESC")
    private List<Escritura> escrituras;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<DatoInspeccion> datoInspecciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    @OrderBy("id DESC")
    private List<Novedad> novedades;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    @OrderBy("fechaIngreso DESC")
    private List<Avaluo> avaluos;
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    @OrderBy("numeroBloque ASC")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Bloque> bloques;
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<PredioImage> images;
    @JsonIgnore
    //@JsonManagedReference
    @OneToMany(mappedBy = "solicitante", fetch = FetchType.LAZY)
    private Collection<TrAprobacionPlanos> trAprobacionPlanosCollection;
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<CatPredioRuralTerreno> predioRuralTerrenoCollection;
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    @OrderBy("id ASC")
    @NotFound(action = NotFoundAction.IGNORE)
    private List<OtrosTipoConstruccion> otrosConstruccion;

    //@LazyCollection(LazyCollectionOption.FALSE)
    //@JsonManagedReference
    @JoinTable(name = "cat_contribuyente_predio", joinColumns = {
        @JoinColumn(name = "id_predio")}, inverseJoinColumns = {
        @JoinColumn(name = "id_contribuyente")})
    @ManyToMany//(fetch = FetchType.EAGER)
    private List<Contribuyente> propietarios;
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<ContribuyentePredio> contribuyentePredioList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<HtTramiteDet> htTramiteDetList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<Exencion> exenciones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPredio")
    private List<DatosAutorizacion> datosAutorizacion;
    //@JsonManagedReference
    @JoinColumns({
        @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia")
        ,
        @JoinColumn(name = "cod_canton", referencedColumnName = "cod_canton")
        ,
        @JoinColumn(name = "cod_parroquia", referencedColumnName = "cod_parroquia")
        ,
        @JoinColumn(name = "cod_zona", referencedColumnName = "cod_zona")
        ,
        @JoinColumn(name = "cod_sector", referencedColumnName = "cod_sector")
        ,
        @JoinColumn(name = "cod_manzana", referencedColumnName = "cod_manzana")
        ,
        @JoinColumn(name = "cod_solar", referencedColumnName = "cod_solar")})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Terreno terreno;
    //@JsonManagedReference
    @JoinColumn(name = "id_relevamiento", referencedColumnName = "id")
    @OneToOne(optional = true)
    private Integrante relevamiento;
    @JsonIgnore
    @OneToMany(mappedBy = "predio", fetch = FetchType.LAZY)
    private Collection<HtTramite> tramiteCollection;
    @OneToMany(mappedBy = "predio", fetch = FetchType.LAZY)
    private Collection<Liquidacion> liquidacionCollection;
    //@JsonManagedReference
    @JoinColumn(name = "id_validador", referencedColumnName = "id")
    @OneToOne(optional = true)
    private Integrante validador;
    //@JsonManagedReference
    @JoinColumn(name = "id_supervisor", referencedColumnName = "id")
    @OneToOne(optional = true)
    private Integrante supervisor;
    //@JsonManagedReference
    @JoinColumn(name = "id_digitador", referencedColumnName = "id")
    @OneToOne(optional = true)
    private Integrante digitador;
    //@JsonManagedReference
    @JoinColumn(name = "id_supervisor_digitador", referencedColumnName = "id")
    @OneToOne(optional = true)
    private Integrante supervisorDigitacion;
    //@JsonManagedReference
    @JoinColumn(name = "id_cartografo", referencedColumnName = "id")
    @OneToOne(optional = true)
    private Integrante cartografo;
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<UsoSuelo> usosSuelo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<Exoneracion> exoneracionList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<LocalComercial> localesComercialesList;
    //Fsan
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predio")
    private List<Incidencia> incidencia;
    //

    @Column(name = "administrador_promotor")
    private String administrador;

    @Column(name = "administrador_promotor_cedula")
    private String administradorCedula;
    @Column(name = "administrador_promotor_celular")
    private String administradorCelular;
    @Column(name = "administrador_promotor_telefono")
    private String administradorTelefono;

    @Size(max = 100)
    @Column(name = "text_dominio")
    private String textDominio;
    @Size(max = 100)
    @Column(name = "text_tipo_propiedad")
    private String textTipoPropiedad;
    @Size(max = 100)
    @Column(name = "text_origen")
    private String textOrigen;

    @Column(name = "estado_legal_predio")
    private short estadoLegalPredio;

    @Column(name = "tipo_predio")
    private Boolean tipoPredio;
    @Column(name = "nombre_predio")
    private String nombrePredio;
    @Column(name = "registro_catastral_rural")
    private String registroCatastral;

    @Column(name = "posee_titulo")
    private short poseeTitulo;
    @Column(name = "en_venta")
    private short enVenta;

    @Column(name = "venta_valor_terreno")
    private Float ventaValorTerreno;
    @Column(name = "venta_valor_construccion")
    private Float ventaValorConstruccion;
    @Column(name = "venta_telefono")
    @Size(max = 50)
    private String ventaTelefono;
    @Size(max = 150)
    @Column(name = "venta_nombre_informante")
    private String ventaNombreInformante;

    @Column(name = "en_alquiler")
    private short enAlquiler;

    @Column(name = "valor_alquiler")
    private Float valorAlquiler;

    @Column(name = "fecha_transaccion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTransaccion;
    @Column(name = "estudio_observaciones")
    private String estudioObservaciones;

    @Transient
    private List<UploadFile> photos;
    
    @Transient
    private Boolean mostrarPredio;
    
    @Transient
    private double areaBloquePH;

    @Transient
    public String duenos;

    @Transient
    private String suelos;

    @Transient
    private String niveles;

    @Transient
    private String otrasConstrucciones;

    @Transient
    private Double areaTotalEd;

    @Transient
    private String imagenBloque;
    
    @Transient
    private String nombresContribuyente;

    public Predio() {
        this.permitieronIngreso = 2;
        this.huboAtencion = 1;
        this.tipoPropiedad = 3;
        this.origen = 0;
        this.dominio = 0;
        this.fechaDigitacion = new Date();
        this.estadoLegalPredio = 0;
        this.areaTotalEd = 0.0;
    }

    public Predio(Integer id) {
        this.id = id;
        this.areaTotalEd = 0.0;
    }

    public Predio(Integer id, String codBloque, String codPhv, String codPhh, String claveCatastral, Date fechaCreacion, Date fechaModificacion, String usuarioModifica) {
        this.id = id;
        this.codBloque = codBloque;
        this.codPhv = codPhv;
        this.codPhh = codPhh;
        this.claveCatastral = claveCatastral;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModifica = usuarioModifica;
        this.areaTotalEd = 0.0;
    }

    public Predio(Predio p) {
        this.codBloque = p.getCodBloque();
        this.codPhv = p.getCodPhv();
        this.codPhh = p.getCodPhh();
        this.claveCatastral = p.getClaveCatastral();
        this.fechaCreacion = p.getFechaCreacion();
        this.fechaModificacion = p.getFechaModificacion();
        this.usuarioModifica = p.getUsuarioModifica();
        this.administrador = p.getAdministrador();
        this.administradorCedula = p.getAdministradorCedula();
        this.administradorCelular = p.getAdministradorCelular();
        this.administradorTelefono = p.getAdministradorTelefono();
        this.alicuotaPh = p.getAlicuotaPh();
        this.areaBloquePH = p.getAreaBloquePH();
        this.cartografo = p.getCartografo();
        this.claveAnterior = p.getClaveAnterior();
        this.contratoArrendamiento = p.getContratoArrendamiento();
        this.digitador = p.getDigitador();
        this.dominio = p.getDominio();
        this.estadoLegalPredio = p.getEstadoLegalPredio();
        this.fechaCartografia = p.getFechaCartografia();
        this.fechaDigitacion = p.getFechaDigitacion();
        this.fechaRelevamiento = p.getFechaRelevamiento();
        this.fechaSupervicion = p.getFechaSupervicion();
        this.fechaSupervicionDigitacion = p.getFechaSupervicionDigitacion();
        this.fechaValidacion = p.getFechaValidacion();
        this.manzana = p.getManzana();
        this.nombreEdificio = p.getNombreEdificio();
        this.nroBloque = p.getNroBloque();
        this.nroPersonasHabitan = p.getNroPersonasHabitan();
        this.nroPiso = p.getNroPiso();
        this.nroPredio = p.getNroPredio();
        this.numeroFicha = p.getNumeroFicha();
        this.observacion = p.getObservacion();
        this.origen = p.getOrigen();
        this.permitieronIngreso = p.getPermitieronIngreso();
        this.relevamiento = p.getRelevamiento();
        this.supervisor = p.getSupervisor();
        this.supervisorDigitacion = p.getSupervisorDigitacion();
        this.tipoPropiedad = p.getTipoPropiedad();
        this.validador = p.getValidador();
        this.areaTotalEd = p.getAreaTotalEd();
    }

    public List<String> propietariosAsCombo() {

        String[] valores = {" "};

        if (!this.getPropietarios().isEmpty()) {
            valores = new String[this.getPropietarios().size()];

            for (int i = 0; i < this.getPropietarios().size(); i++) {
                valores[i] = this.getPropietarios().get(i).getIdentificacion() + " " + this.getPropietarios().get(i).getNombreCompleto();
            }
        } else {
            valores[0] = "SIN PROPIETARIOS";
        }

        return Arrays.asList(valores);
    }

    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getEstudioObservaciones() {
        return estudioObservaciones;
    }

    public void setEstudioObservaciones(String estudioObservaciones) {
        this.estudioObservaciones = estudioObservaciones;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public String getCodBloque() {
        return codBloque;
    }

    public void setCodBloque(String codBloque) {
        this.codBloque = codBloque;
    }

    public String getCodPhv() {
        return codPhv;
    }

    public void setCodPhv(String codPhv) {
        this.codPhv = codPhv;
    }

    public String getCodPhh() {
        return codPhh;
    }

    public void setCodPhh(String codPhh) {
        this.codPhh = codPhh;
    }

    public String getClaveCatastral() {
        return claveCatastral;
    }

    public void setClaveCatastral(String claveCatastral) {
        this.claveCatastral = claveCatastral;
    }

    public String getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(String numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public String getClaveAnterior() {
        return claveAnterior;
    }

    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana.toUpperCase();
    }

    public String getNroPredio() {
        return nroPredio;
    }

    public void setNroPredio(String nroPredio) {
        this.nroPredio = nroPredio.toUpperCase();
    }

    public String getNroDepartamento() {
        return nroDepartamento;
    }

    public void setNroDepartamento(String nroDepartamento) {
        this.nroDepartamento = nroDepartamento.toUpperCase();
    }

    public String getNroPiso() {
        return nroPiso;
    }

    public void setNroPiso(String nroPiso) {
        this.nroPiso = nroPiso.toUpperCase();
    }

    public Short getNroBloque() {
        return nroBloque;
    }

    public void setNroBloque(Short nroBloque) {
        this.nroBloque = nroBloque;
    }

    public String getNombreEdificio() {
        return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio) {
        this.nombreEdificio = nombreEdificio.toUpperCase();
    }

    public Short getPermitieronIngreso() {
        return permitieronIngreso;
    }

    public void setPermitieronIngreso(Short permitieronIngreso) {
        this.permitieronIngreso = permitieronIngreso;
    }

    public Short getNroPersonasHabitan() {
        return nroPersonasHabitan;
    }

    public void setNroPersonasHabitan(Short nroPersonasHabitan) {
        this.nroPersonasHabitan = nroPersonasHabitan;
    }

    public short getDominio() {
        return dominio;
    }

    public void setDominio(short dominio) {
        this.dominio = dominio;
    }

    public short getOrigen() {
        return origen;
    }

    public void setOrigen(short origen) {
        this.origen = origen;
    }

    public short getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(short tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }

    public float getAlicuotaPh() {
        return alicuotaPh;
    }

    public void setAlicuotaPh(float alicuotaPh) {
        this.alicuotaPh = alicuotaPh;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica.toUpperCase();
    }

    public ContratoArrendamiento getContratoArrendamiento() {
        return contratoArrendamiento;
    }

    public void setContratoArrendamiento(ContratoArrendamiento contratoArrendamiento) {
        this.contratoArrendamiento = contratoArrendamiento;
    }

    public List<Escritura> getEscrituras() {
        return escrituras;
    }

    public void setEscrituras(List<Escritura> escrituras) {
        this.escrituras = escrituras;
    }

    public List<DatoInspeccion> getDatoInspecciones() {
        return datoInspecciones;
    }

    public void setDatoInspecciones(List<DatoInspeccion> datoInspecciones) {
        this.datoInspecciones = datoInspecciones;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public List<PredioImage> getImages() {
        return images;
    }

    public void setImages(List<PredioImage> images) {
        this.images = images;
    }

    @XmlTransient
    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Contribuyente> propietarios) {
        this.propietarios = propietarios;
    }

    public List<Exencion> getExenciones() {
        return exenciones;
    }

    public void setExenciones(List<Exencion> exenciones) {
        this.exenciones = exenciones;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public Date getFechaRelevamiento() {
        return fechaRelevamiento;
    }

    public void setFechaRelevamiento(Date fechaRelevamiento) {
        this.fechaRelevamiento = fechaRelevamiento;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public Date getFechaSupervicion() {
        return fechaSupervicion;
    }

    public void setFechaSupervicion(Date fechaSupervicion) {
        this.fechaSupervicion = fechaSupervicion;
    }

    public Date getFechaDigitacion() {
        return fechaDigitacion;
    }

    public void setFechaDigitacion(Date fechaDigitacion) {
        this.fechaDigitacion = fechaDigitacion;
    }

    public Date getFechaSupervicionDigitacion() {
        return fechaSupervicionDigitacion;
    }

    public void setFechaSupervicionDigitacion(Date fechaSupervicionDigitacion) {
        this.fechaSupervicionDigitacion = fechaSupervicionDigitacion;
    }

    public Date getFechaCartografia() {
        return fechaCartografia;
    }

    public void setFechaCartografia(Date fechaCartografia) {
        this.fechaCartografia = fechaCartografia;
    }

    public Integrante getDigitador() {
        return digitador;
    }

    public void setDigitador(Integrante digitador) {
        this.digitador = digitador;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion.toUpperCase();
    }

    public double getAreaBloquePH() {
        return areaBloquePH;
    }

    public void setAreaBloquePH(double areaBloquePH) {
        this.areaBloquePH = areaBloquePH;
    }

    public List<DatosAutorizacion> getDatosAutorizacion() {
        return datosAutorizacion;
    }

    public void setDatosAutorizacion(List<DatosAutorizacion> datosAutorizacion) {
        this.datosAutorizacion = datosAutorizacion;
    }

    public List<OtrosTipoConstruccion> getOtrosConstruccion() {
        return otrosConstruccion;
    }

    public void setOtrosConstruccion(List<OtrosTipoConstruccion> otrosConstruccion) {
        this.otrosConstruccion = otrosConstruccion;
    }

    public List<Incidencia> getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(List<Incidencia> incidencia) {
        this.incidencia = incidencia;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getAdministradorCedula() {
        return administradorCedula;
    }

    public void setAdministradorCedula(String administradorCedula) {
        this.administradorCedula = administradorCedula;
    }

    public String getAdministradorCelular() {
        return administradorCelular;
    }

    public void setAdministradorCelular(String administradorCelular) {
        this.administradorCelular = administradorCelular;
    }

    public String getAdministradorTelefono() {
        return administradorTelefono;
    }

    public void setAdministradorTelefono(String administradorTelefono) {
        this.administradorTelefono = administradorTelefono;
    }

    public short getEstadoLegalPredio() {
        return estadoLegalPredio;
    }

    public void setEstadoLegalPredio(short estadoLegalPredio) {
        this.estadoLegalPredio = estadoLegalPredio;
    }

    public Boolean getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(Boolean tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    public String getNombrePredio() {
        return nombrePredio;
    }

    public void setNombrePredio(String nombrePredio) {
        this.nombrePredio = nombrePredio;
    }

    public String getRegistroCatastral() {
        return registroCatastral;
    }

    public void setRegistroCatastral(String registroCatastral) {
        this.registroCatastral = registroCatastral;
    }

    public short getPoseeTitulo() {
        return poseeTitulo;
    }

    public void setPoseeTitulo(short poseeTitulo) {
        this.poseeTitulo = poseeTitulo;
    }

    public short getEnVenta() {
        return enVenta;
    }

    public void setEnVenta(short enVenta) {
        this.enVenta = enVenta;
    }

    public Float getVentaValorTerreno() {
        return ventaValorTerreno;
    }

    public void setVentaValorTerreno(Float ventaValorTerreno) {
        this.ventaValorTerreno = ventaValorTerreno;
    }

    public Float getVentaValorConstruccion() {
        return ventaValorConstruccion;
    }

    public void setVentaValorConstruccion(Float ventaValorConstruccion) {
        this.ventaValorConstruccion = ventaValorConstruccion;
    }

    public String getVentaTelefono() {
        return ventaTelefono;
    }

    public void setVentaTelefono(String ventaTelefono) {
        this.ventaTelefono = ventaTelefono;
    }

    public String getVentaNombreInformante() {
        return ventaNombreInformante;
    }

    public void setVentaNombreInformante(String ventaNombreInformante) {
        this.ventaNombreInformante = ventaNombreInformante;
    }

    public short getEnAlquiler() {
        return enAlquiler;
    }

    public void setEnAlquiler(short enAlquiler) {
        this.enAlquiler = enAlquiler;
    }

    public Float getValorAlquiler() {
        return valorAlquiler;
    }

    public void setValorAlquiler(Float valorAlquiler) {
        this.valorAlquiler = valorAlquiler;
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
        if (!(object instanceof Predio)) {
            return false;
        }
        Predio other = (Predio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @PrePersist
//    protected void prePersist() {
//
//        claveCatastral = terreno.getTerrenoPK().getCodParroquia() + "-" + terreno.getTerrenoPK().getCodZona() + "-" + terreno.getTerrenoPK().getCodSector() + "-" + terreno.getTerrenoPK().getCodManzana() + "-" + terreno.getTerrenoPK().getCodSolar() + "-" + codPhv + "-" + codPhh;
//    }
    public String PropietariosAsString() {
        String result = "";
        if (!propietarios.isEmpty()) {
            for (Contribuyente c : propietarios) {
                result += c.getNombre() + " " + c.getApellidoPaterno() + " " + c.getApellidoMaterno() + "," + c.getIdentificacion() + " ";
            }
        }

        return result;
    }

    @JsonIgnore
    @JsonBackReference
    public String getNombresPropietariosJS() {
        String result = "";
        try {
            if (propietarios != null && !propietarios.isEmpty()) {
                for (Contribuyente c : propietarios) {
                    result += c.getNombreCompleto() + ", ";
                }
            }
            if(result != "")
                result = result.substring(0, result.length() - 2);
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }
        return result;
    }

    public String getDuenos() {

        duenos = "";
        if (!propietarios.isEmpty()) {
            for (Contribuyente c : propietarios) {
                if (!"".equals(duenos)) {
                    duenos += "; \n";
                }
                duenos += c.getApellidoPaterno() + " " + c.getApellidoMaterno() + " " + c.getNombre();
            }
        }

        return duenos;
    }
//     public String PropietariosAsString() {
//        String result = "";
//        if (!contribuyentePredioList.isEmpty()) {
//            for (ContribuyentePredio c : getContribuyentePredioList()) {
//                result += c.getContribuyente().getNombre() + " " + c.getContribuyente().getApellidoPaterno() + " " + c.getContribuyente().getApellidoMaterno() + "," + c.getContribuyente().getIdentificacion() + " ";
//            }
//        }
//
//        return result;
//    }
//
//    public String getDuenos() {
//
//        duenos = "";
//        if (!contribuyentePredioList.isEmpty()) {
//            for (ContribuyentePredio c : getContribuyentePredioList()) {
//                if (!"".equals(duenos)) {
//                    duenos += "; \n";
//                }
//                duenos += c.getContribuyente().getApellidoPaterno() + " " + c.getContribuyente().getApellidoMaterno() + " " + c.getContribuyente().getNombre();
//            }
//        }
//
//        return duenos;
//    }

    public void setDuenos(String duenos) {
        this.duenos = duenos.toUpperCase();
    }

    public boolean contieneContribuyenteJuridico() {

        for (Contribuyente c : propietarios) {
            if (c.getTipo().equalsIgnoreCase("J")) {
                return true;
            }
        }
        return false;
    }

    public Integrante getRelevamiento() {
        return relevamiento;
    }

    public void setRelevamiento(Integrante relevamiento) {
        this.relevamiento = relevamiento;
    }

    public Integrante getValidador() {
        return validador;
    }

    public void setValidador(Integrante validador) {
        this.validador = validador;
    }

    public Integrante getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Integrante supervisor) {
        this.supervisor = supervisor;
    }

    public Integrante getSupervisorDigitacion() {
        return supervisorDigitacion;
    }

    public void setSupervisorDigitacion(Integrante supervisorDigitacion) {
        this.supervisorDigitacion = supervisorDigitacion;
    }

    public Integrante getCartografo() {
        return cartografo;
    }

    public void setCartografo(Integrante cartografo) {
        this.cartografo = cartografo;
    }

    public String getTextDominio() {
        return textDominio;
    }

    public void setTextDominio(String textDominio) {
        this.textDominio = textDominio;
    }

    public String getTextTipoPropiedad() {
        return textTipoPropiedad;
    }

    public void setTextTipoPropiedad(String textTipoPropiedad) {
        this.textTipoPropiedad = textTipoPropiedad;
    }

    public String getTextOrigen() {
        return textOrigen;
    }

    public void setTextOrigen(String textOrigen) {
        this.textOrigen = textOrigen;
    }

    public double areaTotalEdificacion() {
        double area = 0;
        if(bloques != null){
            for (Bloque b : bloques) {
                for (Piso p : b.getPisos()) {
                    area += p.getArea();
                }
            }
        }
        return area;
    }

    public int totalPisos() {
        int cantidad = 0;
        for (Bloque b : bloques) {
            cantidad += b.getPisos().size();
        }

        return cantidad;
    }

    public Bloque bloquePorNumero(int numero) {
        for (Bloque b : bloques) {
            if (b.getNumeroBloque() == numero) {
                return b;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Predio[ id=" + id + " ]";
    }

    /**
     * @return the contribuyentePredioList
     */
    public List<ContribuyentePredio> getContribuyentePredioList() {
        return contribuyentePredioList;
    }

    /**
     * @param contribuyentePredioList the contribuyentePredioList to set
     */
    public void setContribuyentePredioList(List<ContribuyentePredio> contribuyentePredioList) {
        this.contribuyentePredioList = contribuyentePredioList;
    }

    /**
     * @return the suelos
     */
    public String getSuelos() {
        if (this.getBloques() != null) {
            for (Bloque b : this.getBloques()) {
                if (b.getUsosSuelo() != null) {
                    for (UsoSuelo usoS : b.getUsosSuelo()) {
                        suelos += usoS.getValorDiscreto().getVariable().getNombre() + " " + usoS.getValorDiscreto().getTexto() + "\n";
                    }
                }
            }
        }
        if (suelos == null) {
            if (this.getUsosSuelo() != null) {
                for (UsoSuelo usoS : this.getUsosSuelo()) {
                    suelos += usoS.getValorDiscreto().getVariable().getNombre() + " " + usoS.getValorDiscreto().getTexto() + "\n";
                }
            }
        }
        return suelos;
    }

    /**
     * @return the niveles
     */
    public String getNiveles() {
        if (this.getBloques() != null) {
            int niv = 0;
            for (Bloque b : this.getBloques()) {
                niv += b.getNumeroNiveles();
            }
            niveles = niv + "";
        }
        return niveles;
    }

    /**
     * @return the otrasConstrucciones
     */
    public String getOtrasConstrucciones() {
        try {
            if (this.getOtrosConstruccion() != null) {
                StringBuilder sb = new StringBuilder();
                for (OtrosTipoConstruccion o : this.getOtrosConstruccion()) {
                    if (o.getVaria() != null) {
                        sb.append(o.getVaria().getNombre());
                        sb.append("\n");
                    }
                }
                otrasConstrucciones = sb.toString();
            }
        } catch (Exception e) {
        }
        return otrasConstrucciones;
    }

    /**
     * @return the areaTotalEd
     */
    public Double getAreaTotalEd() {
        double d = 0d;
        for (Bloque b : bloques) {
            for (Piso p : b.getPisos()) {
                d += p.getArea();
            }
        }
        areaTotalEd = d;
        return areaTotalEd;
    }

    /**
     * @return the imagenBloque
     */
    public String getImagenBloque() {
        imagenBloque = "NO TIENE";
        if (this.getBloques() != null) {
            for (Bloque b : this.getBloques()) {
                if (b.getImages() != null) {
                    if (!b.getImages().isEmpty()) {
                        imagenBloque = "TIENE";
                        return imagenBloque;
                    }
                }
            }
        }
        return imagenBloque;
    }

//    public String valorTipoPropiedad(){
//        switch(this.tipoPropiedad){
//            tipo
//        }
//    }
    /**
     * @return the contribuyentePredioList
     */
//    public List<ContribuyentePredio> getContribuyentePredioList() {
//        return contribuyentePredioList;
//    }
//
//    /**
//     * @param contribuyentePredioList the contribuyentePredioList to set
//     */
//    public void setContribuyentePredioList(List<ContribuyentePredio> contribuyentePredioList) {
//        this.contribuyentePredioList = contribuyentePredioList;
//    }
    public String personeria() {
        String personeria = "NATURAL";

        for (Contribuyente c : propietarios) {
            if (c.getTipo().equals("J")) {
                return "JURIDICO";
            }
        }

        return personeria;
    }

    @JsonIgnore
    public Collection<HtTramite> getTramiteCollection() {
        return tramiteCollection;
    }

    public void setTramiteCollection(Collection<HtTramite> tramiteCollection) {
        this.tramiteCollection = tramiteCollection;
    }

    @JsonIgnore
    public List<HtTramiteDet> getHtTramiteDetList() {
        return htTramiteDetList;
    }

    public void setHtTramiteDetList(List<HtTramiteDet> htTramiteDetList) {
        this.htTramiteDetList = htTramiteDetList;
    }

    @JsonIgnore
    public Collection<TrAprobacionPlanos> getTrAprobacionPlanosCollection() {
        return trAprobacionPlanosCollection;
    }

    public void setTrAprobacionPlanosCollection(Collection<TrAprobacionPlanos> trAprobacionPlanosCollection) {
        this.trAprobacionPlanosCollection = trAprobacionPlanosCollection;
    }

    public List<CatPredioRuralTerreno> getPredioRuralTerrenoCollection() {
        return predioRuralTerrenoCollection;
    }

    public void setPredioRuralTerrenoCollection(List<CatPredioRuralTerreno> predioRuralTerrenoCollection) {
        this.predioRuralTerrenoCollection = predioRuralTerrenoCollection;
    }

    public String getIdentificacionPredialR() {
        return identificacionPredialR;
    }

    public void setIdentificacionPredialR(String identificacionPredialR) {
        this.identificacionPredialR = identificacionPredialR;
    }

    public Short getHuboAtencion() {
        return huboAtencion;
    }

    public void setHuboAtencion(Short huboAtencion) {
        this.huboAtencion = huboAtencion;
    }

    public List<Novedad> getNovedades() {
        return novedades;
    }

    public void setNovedades(List<Novedad> novedades) {
        this.novedades = novedades;
    }

    public Boolean getMostrarPredio() {
        return mostrarPredio;
    }

    public void setMostrarPredio(Boolean mostrarPredio) {
        this.mostrarPredio = mostrarPredio;
    }

    public List<UploadFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<UploadFile> photos) {
        this.photos = photos;
    }

    public List<Avaluo> getAvaluos() {
        return avaluos;
    }

    public void setAvaluos(List<Avaluo> avaluos) {
        this.avaluos = avaluos;
    }

    public List<Exoneracion> getExoneracionList() {
        return exoneracionList;
    }

    public void setExoneracionList(List<Exoneracion> exoneracionList) {
        this.exoneracionList = exoneracionList;
    }

    public List<LocalComercial> getLocalesComercialesList() {
        return localesComercialesList;
    }

    public void setLocalesComercialesList(List<LocalComercial> localesComercialesList) {
        this.localesComercialesList = localesComercialesList;
    }

    public String getNombresContribuyente() {
        return nombresContribuyente;
    }

    public void setNombresContribuyente(String nombresContribuyente) {
        this.nombresContribuyente = nombresContribuyente;
    }

    public Collection<Liquidacion> getLiquidacionCollection() {
        return liquidacionCollection;
    }

    public void setLiquidacionCollection(Collection<Liquidacion> liquidacionCollection) {
        this.liquidacionCollection = liquidacionCollection;
    }

}
