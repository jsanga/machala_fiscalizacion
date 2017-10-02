/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.common.annotations.GreaterZero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_terreno")
@NamedQueries({
    @NamedQuery(name = "Terreno.findByCodes", query = "SELECT t FROM Terreno t WHERE t.terrenoPK.codProvincia = ?1 AND t.terrenoPK.codCanton=?2 AND t.terrenoPK.codParroquia=?3 AND t.terrenoPK.codZona=?4 AND t.terrenoPK.codSector=?5 AND t.terrenoPK.codManzana=?6 AND t.terrenoPK.codSolar=?7")})
public class Terreno implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TerrenoPK terrenoPK;
    @Size(max = 255)
    @Column(name = "dir_calle1")
    private String dirCalle1;
    @Size(max = 255)
    @Column(name = "dir_calle2")
    private String dirCalle2;
    @Size(max = 255)
    @Column(name = "dir_calle3")
    private String dirCalle3;
    @Size(max = 255)
    @Column(name = "dir_calle4")
    private String dirCalle4;
    @Size(max = 255)
    @Column(name = "dir_nomenclatura1")
    private String dirNomenclatura1;
    @Size(max = 255)
    @Column(name = "dir_nomenclatura2")
    private String dirNomenclatura2;
    @Size(max = 255)
    @Column(name = "dir_nomenclatura3")
    private String dirNomenclatura3;
    @Size(max = 255)
    @Column(name = "dir_nomenclatura4")
    private String dirNomenclatura4;
    @Basic(optional = false)
    @GreaterZero
    @NotNull
    @Column(name = "area_levantamiento")
    private double areaLevantamiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "perimetro_levantamiento")
    private double perimetroLevantamiento;

    @Column(name = "lin_norte_longitud")
    private String linNorteLongitud;

    @Column(name = "lin_sur_longitud")
    private String linSurLongitud;

    @Column(name = "lin_este_longitud")
    private String linEsteLongitud;

    @Column(name = "lin_oeste_longitud")
    private String linOesteLongitud;

    @Column(name = "lin_norte_ref")
    private String linNorteRef;

    @Column(name = "lin_sur_ref")
    private String linSurRef;

    @Column(name = "lin_este_ref")
    private String linEsteRef;

    @Column(name = "lin_oeste_ref")
    private String linOesteRef;
    @Size(max = 250)
    @Column(name = "cord_x")
    private String cordX;
    @Size(max = 250)
    @Column(name = "cord_y")
    private String cordY;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @Column(name = "estado_edificacion")
    private short estadoEdificacion;
    //@GreaterZero
    @Column(name = "pisos_terminados")
    private short pisosTerminados;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ubicacion")
    private short ubicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "topografia")
    private short topografia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "geometria")
    private short geometria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_frentes")
    private short nroFrentes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "caracteristicas_suelo")
    private short caracteristicasSuelo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_energia_electrica")
    private short redEnergiaElectrica;
    @Column(name = "medidor_luz")
    private short medidorLuz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_agua_potable")
    private short redAguaPotable;
    @Column(name = "medidor_agua")
    private short medidorAgua;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_alcantarillado")
    private short redAlcantarillado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "red_telefonica")
    private short redTelefonica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "alumbrado")
    private short alumbrado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recoleccion_basura")
    private short recoleccionBasura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aseo_calles")
    private short aseoCalles;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transporte_publico")
    private short transportePublico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_vias")
    private short tipoVias;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_capa_rodadura")
    private short tipoCapaRodadura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aceras")
    private short aceras;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bordillos")
    private short bordillos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_conservacion")
    private short estadoConservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "via_topografia")
    private short viaTopografia;

    @Column(name = "area_qgis")
    private Double areaQgis;
    @Column(name = "area_siscat")
    private Double areaSiscat;
    @Column(name = "perimetro_qgis")
    private Double perimetroQgis;
    @Column(name = "perimetro_siscat")
    private Double perimetroSiscat;
    @Column(name = "frente_propiedad")
    private Double frentePropiedad;
    @Column(name = "fondo_propiedad")
    private Double fondoPropiedad;
    @Column(name = "barrio")
    private String barrio;

    @Column(name = "cable_satelital")
    private short cableSatelital;
    @Column(name = "internet")
    private short internet;
    @Column(name = "otros_servicio")
    private String otrosServicio;
    private String observacion;
    @Column(name = "uso_suelo")
    private short usoSuelo;
    @Column(name = "tipo_vivienda_rural")
    private short tipoViviendaRural;
    @Column(name = "uso_construccion_rural")
    private short usoConstruccionRural;
    @JsonIgnore
    @JsonBackReference(value = "predioTerreno")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "terreno")
    private List<Predio> predios;
    //@JsonManagedReference
    @JoinColumns({
        @JoinColumn(name = "cod_provincia", referencedColumnName = "cod_provincia", insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_canton", referencedColumnName = "cod_canton", insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_parroquia", referencedColumnName = "cod_parroquia", insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_zona", referencedColumnName = "cod_zona", insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_sector", referencedColumnName = "cod_sector", insertable = false, updatable = false)
        ,
        @JoinColumn(name = "cod_manzana", referencedColumnName = "cod_manzana", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Manzana manzana;

    @Column(name = "ancho_via_publica")
    private Double anchoViaPuplica;

    @Column(name = "ancho_acera")
    private Double anchoAcera;

    @Size(max = 100)
    @Column(name = "text_estado_edificacion")
    private String textEstadoEdificacion;
    @Size(max = 100)
    @Column(name = "text_ubicacion")
    private String textUbicacion;
    @Size(max = 100)
    @Column(name = "text_topografia")
    private String textTopografia;
    @Size(max = 100)
    @Column(name = "text_geometria")
    private String textGeometria;
    @Size(max = 100)
    @Column(name = "text_caracteristicas_suelo")
    private String textCaracteristicasSuelo;
    @Size(max = 100)
    @Column(name = "text_energia_electrica")
    private String textEnergiaElectrica;
    @Size(max = 100)
    @Column(name = "text_agua_potable")
    private String textAguaPotable;
    @Size(max = 100)
    @Column(name = "text_alcantarillado")
    private String textAlcantarillado;
    @Size(max = 100)
    @Column(name = "text_telefono")
    private String textTelefono;
    @Size(max = 100)
    @Column(name = "text_recoleccion_basura")
    private String textRecoleccionBasura;
    @Size(max = 100)
    @Column(name = "text_estado")
    private String textEstado;
    @Size(max = 100)
    @Column(name = "text_aseo_calle")
    private String textAseoCalle;
    @Size(max = 100)
    @Column(name = "text_transporte")
    private String textTransporte;
    @Size(max = 100)
    @Column(name = "text_cable_satelital")
    private String textCableSatelital;
    @Size(max = 100)
    @Column(name = "text_internet")
    private String textInternet;
    @Size(max = 100)
    @Column(name = "text_tipo_via")
    private String textTipoVia;
    @Size(max = 100)
    @Column(name = "text_capa_rodadura")
    private String textCapaRodadura;
    @Size(max = 100)
    @Column(name = "text_aceras")
    private String textAceras;
    @Size(max = 100)
    @Column(name = "text_bordillos")
    private String textBordillos;
    @Size(max = 100)
    @Column(name = "text_estado_conservacion")
    private String textEstadoConservacion;
    @Size(max = 100)
    @Column(name = "text_uso_suelo")
    private String textUsoSuelo;
    @Size(max = 100)
    @Column(name = "text_topografia_via")
    private String textTopografiaVia;

    @Size(max = 250)
    @Column(name = "eje_principal")
    private String ejePrincipal;
    @Size(max = 250)
    @Column(name = "eje_secundario")
    private String ejeSecundario;
    @Size(max = 250)
    @Column(name = "placa_predial")
    private String placaPredial;
    @Size(max = 250)
    @Column(name = "poblacion_cercana1")
    private String poblacionCercana1;
    @Size(max = 250)
    @Column(name = "poblacion_cercana2")
    private String poblacionCercana2;
    @Column(name = "distancia_poblacion1")
    private Float distanciaPoblacion1;
    @Column(name = "distancia_poblacion2")
    private Float distanciaPoblacion2;
    @Size(max = 250)
    @Column(name = "via_principal_acceso1")
    private String viaPrincipalAcceso1;
    @Size(max = 250)
    @Column(name = "via_principal_acceso2")
    private String viaPrincipalAcceso2;
    @Column(name = "distancia_via_principal1")
    private Float distanciaViaPrincipal1;
    @Column(name = "distancia_via_principal2")
    private Float distanciaViaPrincipal2;

    @Column(name = "relacion_rasante")
    private short relacionRasante;
    @Column(name = "clasificacion_suelo")
    private short clasificacionsuelo;

    @Column(name = "ecosistema_relevante")
    private short ecosistemaRelevante;
    @Column(name = "cobertura_predominante")
    private short coberturaPredominante;
    @Column(name = "rodadura")
    private short rodadura;
    @Column(name = "otras_vias_acceso")
    private short otrasViasAcceso;
    @Column(name = "uso_via")
    private short usoVia;

    @Column(name = "parterre")
    private short parterre;

    @Column(name = "n_medidores_luz")
    private short numMedidoresLuz;
    @Column(name = "n_medidores_agua")
    private short numMedidoresAgua;
    @Column(name = "disponibilidad_riego")
    private short disponibilidadRiego;
    @Column(name = "eliminacion_excretas")
    private short eliminacionExcretas;
    @Column(name = "metodo_riego")
    private short metodoRiego;
    @Column(name = "transporte_privado")
    private short transportePrivado;
    @Column(name = "requiere_perfeccionamiento")
    private short requierePerfeccionamiento;
    @Column(name = "anios_sin_perfeccionamiento")
    private short aniosSinPerfeccionamiento;
    @Column(name = "anios_en_posesion")
    private short aniosEnPosesion;
    @Column(name = "nombre_etnia")
    private String nombreEtnia;

    public Terreno() {
        this.estado = 1;
        this.ubicacion = 2;
        this.topografia = 1;
        this.geometria = 1;
        this.nroFrentes = 1;
        this.caracteristicasSuelo = 1;
        this.redEnergiaElectrica = 3;
        this.redAguaPotable = 1;
        this.redAlcantarillado = 1;
        this.redTelefonica = 3;
        this.alumbrado = 2;
        this.recoleccionBasura = 2;
        this.aseoCalles = 2;
        this.transportePublico = 2;
        this.cableSatelital = 2;
        this.internet = 2;
        this.tipoVias = 4;
        this.tipoCapaRodadura = 5;
        this.aceras = 2;
        this.medidorLuz = 2;
        this.medidorAgua = 2;
        this.bordillos = 2;
        this.estadoConservacion = 1;
        this.viaTopografia = 1;
        this.pisosTerminados = 1;
        this.usoSuelo = 18;
        this.areaLevantamiento = 0;
        this.tipoViviendaRural = 0;
    }

    public Terreno(TerrenoPK terrenoPK) {
        this.terrenoPK = terrenoPK;
    }

    public Terreno(Terreno terreno) {

        this.estado = terreno.getEstado();
        this.ubicacion = terreno.getUbicacion();
        this.topografia = terreno.getTopografia();
        this.geometria = terreno.getGeometria();
        this.nroFrentes = terreno.getNroFrentes();
        this.caracteristicasSuelo = terreno.getCaracteristicasSuelo();

        this.redEnergiaElectrica = terreno.getRedEnergiaElectrica();
        this.redAguaPotable = terreno.getRedAguaPotable();
        this.redAlcantarillado = terreno.getRedAlcantarillado();
        this.redTelefonica = terreno.getRedTelefonica();
        this.alumbrado = terreno.getAlumbrado();
        this.recoleccionBasura = terreno.getRecoleccionBasura();
        this.aseoCalles = terreno.getAseoCalles();
        this.transportePublico = terreno.getTransportePublico();
        this.cableSatelital = terreno.getCableSatelital();
        this.internet = terreno.getInternet();
        this.otrosServicio = terreno.getOtrosServicio();

        this.tipoVias = terreno.getTipoVias();
        this.tipoCapaRodadura = terreno.getTipoCapaRodadura();
        this.aceras = terreno.getAceras();
        this.anchoAcera = terreno.getAnchoAcera();
        this.bordillos = terreno.getBordillos();
        this.estadoConservacion = terreno.getEstadoConservacion();
        this.viaTopografia = terreno.getViaTopografia();

        this.linNorteRef = terreno.getLinNorteRef();
        this.linNorteLongitud = terreno.getLinNorteLongitud();
        this.linSurRef = terreno.getLinSurRef();
        this.linSurLongitud = terreno.getLinSurLongitud();
        this.linEsteRef = terreno.getLinEsteRef();
        this.linEsteLongitud = terreno.getLinEsteLongitud();
        this.linOesteRef = terreno.getLinOesteRef();
        this.linOesteLongitud = terreno.getLinOesteLongitud();
        this.perimetroLevantamiento = terreno.getPerimetroLevantamiento();
        this.areaLevantamiento = terreno.getAreaLevantamiento();
        this.anchoViaPuplica = terreno.getAnchoViaPuplica();

        this.observacion = terreno.getObservacion();
        this.tipoViviendaRural = terreno.getTipoViviendaRural();
        this.manzana = terreno.getManzana();
        this.barrio = terreno.getBarrio();
        this.dirCalle1 = terreno.getDirCalle1();
        this.dirCalle2 = terreno.getDirCalle2();
        this.dirCalle3 = terreno.getDirCalle3();
        this.dirCalle4 = terreno.getDirCalle4();
    }

    public Terreno(TerrenoPK terrenoPK, double areaLevantamiento, double perimetroLevantamiento, short estado, short ubicacion, short topografia, short geometria, short nroFrentes, short caracteristicasSuelo, short redEnergiaElectrica, short redAguaPotable, short redAlcantarillado, short redTelefonica, short alumbrado, short recoleccionBasura, short aseoCalles, short transportePublico, short tipoVias, short tipoCapaRodadura, short aceras, short bordillos, short estadoConservacion, short viaTopografia, short muro, short cerramiento) {
        this.terrenoPK = terrenoPK;
        this.areaLevantamiento = areaLevantamiento;
        this.perimetroLevantamiento = perimetroLevantamiento;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.topografia = topografia;
        this.geometria = geometria;
        this.nroFrentes = nroFrentes;
        this.caracteristicasSuelo = caracteristicasSuelo;
        this.redEnergiaElectrica = redEnergiaElectrica;
        this.redAguaPotable = redAguaPotable;
        this.redAlcantarillado = redAlcantarillado;
        this.redTelefonica = redTelefonica;
        this.alumbrado = alumbrado;
        this.recoleccionBasura = recoleccionBasura;
        this.aseoCalles = aseoCalles;
        this.transportePublico = transportePublico;
        this.tipoVias = tipoVias;
        this.tipoCapaRodadura = tipoCapaRodadura;
        this.aceras = aceras;
        this.bordillos = bordillos;
        this.estadoConservacion = estadoConservacion;
        this.viaTopografia = viaTopografia;
        this.usoSuelo = 18;

    }

    public short getParterre() {
        return parterre;
    }

    public void setParterre(short parterre) {
        this.parterre = parterre;
    }

    public TerrenoPK getTerrenoPK() {
        return terrenoPK;
    }

    public void setTerrenoPK(TerrenoPK terrenoPK) {
        this.terrenoPK = terrenoPK;
    }

    public String getDirCalle1() {
        return dirCalle1;
    }

    public void setDirCalle1(String dirCalle1) {
        this.dirCalle1 = dirCalle1.toUpperCase();
    }

    public String getDirCalle2() {
        return dirCalle2;
    }

    public void setDirCalle2(String dirCalle2) {
        this.dirCalle2 = dirCalle2.toUpperCase();
    }

    public String getDirCalle3() {
        return dirCalle3;
    }

    public void setDirCalle3(String dirCalle3) {
        this.dirCalle3 = dirCalle3.toUpperCase();
    }

    public String getDirCalle4() {
        return dirCalle4;
    }

    public void setDirCalle4(String dirCalle4) {
        this.dirCalle4 = dirCalle4.toUpperCase();
    }

    public String getDirNomenclatura1() {
        return dirNomenclatura1;
    }

    public void setDirNomenclatura1(String dirNomenclatura1) {
        this.dirNomenclatura1 = dirNomenclatura1.toUpperCase();
    }

    public String getDirNomenclatura2() {
        return dirNomenclatura2;
    }

    public void setDirNomenclatura2(String dirNomenclatura2) {
        this.dirNomenclatura2 = dirNomenclatura2.toUpperCase();
    }

    public String getDirNomenclatura3() {
        return dirNomenclatura3;
    }

    public void setDirNomenclatura3(String dirNomenclatura3) {
        this.dirNomenclatura3 = dirNomenclatura3.toUpperCase();
    }

    public String getDirNomenclatura4() {
        return dirNomenclatura4;
    }

    public void setDirNomenclatura4(String dirNomenclatura4) {
        this.dirNomenclatura4 = dirNomenclatura4.toUpperCase();
    }

    public double getAreaLevantamiento() {
        return areaLevantamiento;
    }

    public void setAreaLevantamiento(double areaLevantamiento) {
        this.areaLevantamiento = areaLevantamiento;
    }

    public double getPerimetroLevantamiento() {
        return perimetroLevantamiento;
    }

    public void setPerimetroLevantamiento(double perimetroLevantamiento) {
        this.perimetroLevantamiento = perimetroLevantamiento;
    }

    public String getLinNorteLongitud() {
        return linNorteLongitud;
    }

    public void setLinNorteLongitud(String linNorteLongitud) {
        this.linNorteLongitud = linNorteLongitud;
    }

    public String getLinNorteRef() {
        return linNorteRef;
    }

    public void setLinNorteRef(String linNorteRef) {
        this.linNorteRef = linNorteRef.toUpperCase();
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

    public String getCordX() {
        return cordX;
    }

    public void setCordX(String cordX) {
        this.cordX = cordX;
    }

    public String getCordY() {
        return cordY;
    }

    public void setCordY(String cordY) {
        this.cordY = cordY;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public short getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(short ubicacion) {
        this.ubicacion = ubicacion;
    }

    public short getTopografia() {
        return topografia;
    }

    public void setTopografia(short topografia) {
        this.topografia = topografia;
    }

    public short getGeometria() {
        return geometria;
    }

    public void setGeometria(short geometria) {
        this.geometria = geometria;
    }

    public short getNroFrentes() {
        return nroFrentes;
    }

    public void setNroFrentes(short nroFrentes) {
        this.nroFrentes = nroFrentes;
    }

    public short getCaracteristicasSuelo() {
        return caracteristicasSuelo;
    }

    public void setCaracteristicasSuelo(short caracteristicasSuelo) {
        this.caracteristicasSuelo = caracteristicasSuelo;
    }

    public short getRedEnergiaElectrica() {
        return redEnergiaElectrica;
    }

    public void setRedEnergiaElectrica(short redEnergiaElectrica) {
        this.redEnergiaElectrica = redEnergiaElectrica;
    }

    public short getRedAguaPotable() {
        return redAguaPotable;
    }

    public void setRedAguaPotable(short redAguaPotable) {
        this.redAguaPotable = redAguaPotable;
    }

    public short getRedAlcantarillado() {
        return redAlcantarillado;
    }

    public void setRedAlcantarillado(short redAlcantarillado) {
        this.redAlcantarillado = redAlcantarillado;
    }

    public short getRedTelefonica() {
        return redTelefonica;
    }

    public void setRedTelefonica(short redTelefonica) {
        this.redTelefonica = redTelefonica;
    }

    public short getAlumbrado() {
        return alumbrado;
    }

    public void setAlumbrado(short alumbrado) {
        this.alumbrado = alumbrado;
    }

    public short getRecoleccionBasura() {
        return recoleccionBasura;
    }

    public void setRecoleccionBasura(short recoleccionBasura) {
        this.recoleccionBasura = recoleccionBasura;
    }

    public short getAseoCalles() {
        return aseoCalles;
    }

    public void setAseoCalles(short aseoCalles) {
        this.aseoCalles = aseoCalles;
    }

    public short getTransportePublico() {
        return transportePublico;
    }

    public void setTransportePublico(short transportePublico) {
        this.transportePublico = transportePublico;
    }

    public short getTipoVias() {
        return tipoVias;
    }

    public void setTipoVias(short tipoVias) {
        this.tipoVias = tipoVias;
    }

    public short getTipoCapaRodadura() {
        return tipoCapaRodadura;
    }

    public void setTipoCapaRodadura(short tipoCapaRodadura) {
        this.tipoCapaRodadura = tipoCapaRodadura;
    }

    public short getAceras() {
        return aceras;
    }

    public void setAceras(short aceras) {
        this.aceras = aceras;
    }

    public short getBordillos() {
        return bordillos;
    }

    public void setBordillos(short bordillos) {
        this.bordillos = bordillos;
    }

    public short getEstadoConservacion() {
        return estadoConservacion;
    }

    public void setEstadoConservacion(short estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public short getViaTopografia() {
        return viaTopografia;
    }

    public void setViaTopografia(short viaTopografia) {
        this.viaTopografia = viaTopografia;
    }

    public Double getAreaQgis() {
        return areaQgis;
    }

    public void setAreaQgis(Double areaQgis) {
        this.areaQgis = areaQgis;
    }

    public Double getAreaSiscat() {
        return areaSiscat;
    }

    public void setAreaSiscat(Double areaSiscat) {
        this.areaSiscat = areaSiscat;
    }

    public Double getPerimetroQgis() {
        return perimetroQgis;
    }

    public void setPerimetroQgis(Double perimetroQgis) {
        this.perimetroQgis = perimetroQgis;
    }

    public Double getPerimetroSiscat() {
        return perimetroSiscat;
    }

    public void setPerimetroSiscat(Double perimetroSiscat) {
        this.perimetroSiscat = perimetroSiscat;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = (barrio != null ? barrio.toUpperCase() : "");
    }

    @JsonIgnore
    public List<Predio> getPredios() {
        return predios;
    }

    public void setPredios(List<Predio> predios) {
        this.predios = predios;
    }

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public short getCableSatelital() {
        return cableSatelital;
    }

    public void setCableSatelital(short cableSatelital) {
        this.cableSatelital = cableSatelital;
    }

    public short getInternet() {
        return internet;
    }

    public void setInternet(short internet) {
        this.internet = internet;
    }

    public String getOtrosServicio() {
        return otrosServicio;
    }

    public void setOtrosServicio(String otrosServicio) {
        this.otrosServicio = otrosServicio.toUpperCase();
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion.toUpperCase();
    }

    public Double getAnchoViaPuplica() {
        return anchoViaPuplica;
    }

    public void setAnchoViaPuplica(Double anchoViaPuplica) {
        this.anchoViaPuplica = anchoViaPuplica;
    }

    public Double getAnchoAcera() {
        return anchoAcera;
    }

    public void setAnchoAcera(Double anchoAcera) {
        this.anchoAcera = anchoAcera;
    }

    public short getPisosTerminados() {
        return pisosTerminados;
    }

    public void setPisosTerminados(short pisosTerminados) {
        this.pisosTerminados = pisosTerminados;
    }

    public short getMedidorLuz() {
        return medidorLuz;
    }

    public void setMedidorLuz(short medidorLuz) {
        this.medidorLuz = medidorLuz;
    }

    public short getMedidorAgua() {
        return medidorAgua;
    }

    public void setMedidorAgua(short medidorAgua) {
        this.medidorAgua = medidorAgua;
    }

    public short getUsoSuelo() {
        return usoSuelo;
    }

    public void setUsoSuelo(short usoSuelo) {
        this.usoSuelo = usoSuelo;
    }

    public short getEstadoEdificacion() {
        return estadoEdificacion;
    }

    public void setEstadoEdificacion(short estadoEdificacion) {
        this.estadoEdificacion = estadoEdificacion;
    }

    public String getTextEstadoEdificacion() {
        return textEstadoEdificacion;
    }

    public void setTextEstadoEdificacion(String textEstadoEdificacion) {
        this.textEstadoEdificacion = textEstadoEdificacion;
    }

    public String getTextUbicacion() {
        return textUbicacion;
    }

    public void setTextUbicacion(String textUbicacion) {
        this.textUbicacion = textUbicacion;
    }

    public String getTextTopografia() {
        return textTopografia;
    }

    public void setTextTopografia(String textTopografia) {
        this.textTopografia = textTopografia;
    }

    public String getTextGeometria() {
        return textGeometria;
    }

    public void setTextGeometria(String textGeometria) {
        this.textGeometria = textGeometria;
    }

    public String getTextCaracteristicasSuelo() {
        return textCaracteristicasSuelo;
    }

    public void setTextCaracteristicasSuelo(String textCaracteristicasSuelo) {
        this.textCaracteristicasSuelo = textCaracteristicasSuelo;
    }

    public String getTextEnergiaElectrica() {
        return textEnergiaElectrica;
    }

    public void setTextEnergiaElectrica(String textEnergiaElectrica) {
        this.textEnergiaElectrica = textEnergiaElectrica;
    }

    public String getTextAguaPotable() {
        return textAguaPotable;
    }

    public void setTextAguaPotable(String textAguaPotable) {
        this.textAguaPotable = textAguaPotable;
    }

    public String getTextAlcantarillado() {
        return textAlcantarillado;
    }

    public void setTextAlcantarillado(String textAlcantarillado) {
        this.textAlcantarillado = textAlcantarillado;
    }

    public String getTextTelefono() {
        return textTelefono;
    }

    public void setTextTelefono(String textTelefono) {
        this.textTelefono = textTelefono;
    }

    public String getTextRecoleccionBasura() {
        return textRecoleccionBasura;
    }

    public void setTextRecoleccionBasura(String textRecoleccionBasura) {
        this.textRecoleccionBasura = textRecoleccionBasura;
    }

    public String getTextEstado() {
        return textEstado;
    }

    public void setTextEstado(String textEstado) {
        this.textEstado = textEstado;
    }

    public String getTextAseoCalle() {
        return textAseoCalle;
    }

    public void setTextAseoCalle(String textAseoCalle) {
        this.textAseoCalle = textAseoCalle;
    }

    public String getTextTransporte() {
        return textTransporte;
    }

    public void setTextTransporte(String textTransporte) {
        this.textTransporte = textTransporte;
    }

    public String getTextCableSatelital() {
        return textCableSatelital;
    }

    public void setTextCableSatelital(String textCableSatelital) {
        this.textCableSatelital = textCableSatelital;
    }

    public String getTextInternet() {
        return textInternet;
    }

    public void setTextInternet(String textInternet) {
        this.textInternet = textInternet;
    }

    public String getTextTipoVia() {
        return textTipoVia;
    }

    public void setTextTipoVia(String textTipoVia) {
        this.textTipoVia = textTipoVia;
    }

    public String getTextCapaRodadura() {
        return textCapaRodadura;
    }

    public void setTextCapaRodadura(String textCapaRodadura) {
        this.textCapaRodadura = textCapaRodadura;
    }

    public String getTextAceras() {
        return textAceras;
    }

    public void setTextAceras(String textAceras) {
        this.textAceras = textAceras;
    }

    public String getTextBordillos() {
        return textBordillos;
    }

    public void setTextBordillos(String textBordillos) {
        this.textBordillos = textBordillos;
    }

    public String getTextEstadoConservacion() {
        return textEstadoConservacion;
    }

    public void setTextEstadoConservacion(String textEstadoConservacion) {
        this.textEstadoConservacion = textEstadoConservacion;
    }

    public String getTextUsoSuelo() {
        return textUsoSuelo;
    }

    public void setTextUsoSuelo(String textUsoSuelo) {
        this.textUsoSuelo = textUsoSuelo;
    }

    public String getTextTopografiaVia() {
        return textTopografiaVia;
    }

    public void setTextTopografiaVia(String textTopografiaVia) {
        this.textTopografiaVia = textTopografiaVia;
    }

    public String getEjePrincipal() {
        return ejePrincipal;
    }

    public void setEjePrincipal(String ejePrincipal) {
        this.ejePrincipal = ejePrincipal;
    }

    public String getEjeSecundario() {
        return ejeSecundario;
    }

    public void setEjeSecundario(String ejeSecundario) {
        this.ejeSecundario = ejeSecundario;
    }

    public String getPlacaPredial() {
        return placaPredial;
    }

    public void setPlacaPredial(String placaPredial) {
        this.placaPredial = placaPredial;
    }

    public String getPoblacionCercana1() {
        return poblacionCercana1;
    }

    public void setPoblacionCercana1(String poblacionCercana1) {
        this.poblacionCercana1 = poblacionCercana1;
    }

    public String getPoblacionCercana2() {
        return poblacionCercana2;
    }

    public void setPoblacionCercana2(String poblacionCercana2) {
        this.poblacionCercana2 = poblacionCercana2;
    }

    public String getViaPrincipalAcceso1() {
        return viaPrincipalAcceso1;
    }

    public void setViaPrincipalAcceso1(String viaPrincipalAcceso1) {
        this.viaPrincipalAcceso1 = viaPrincipalAcceso1;
    }

    public String getViaPrincipalAcceso2() {
        return viaPrincipalAcceso2;
    }

    public void setViaPrincipalAcceso2(String viaPrincipalAcceso2) {
        this.viaPrincipalAcceso2 = viaPrincipalAcceso2;
    }

    public short getRelacionRasante() {
        return relacionRasante;
    }

    public void setRelacionRasante(short relacionRasante) {
        this.relacionRasante = relacionRasante;
    }

    public short getClasificacionsuelo() {
        return clasificacionsuelo;
    }

    public void setClasificacionsuelo(short clasificacionsuelo) {
        this.clasificacionsuelo = clasificacionsuelo;
    }

    public short getEcosistemaRelevante() {
        return ecosistemaRelevante;
    }

    public void setEcosistemaRelevante(short ecosistemaRelevante) {
        this.ecosistemaRelevante = ecosistemaRelevante;
    }

    public short getCoberturaPredominante() {
        return coberturaPredominante;
    }

    public void setCoberturaPredominante(short coberturaPredominante) {
        this.coberturaPredominante = coberturaPredominante;
    }

    public short getRodadura() {
        return rodadura;
    }

    public void setRodadura(short rodadura) {
        this.rodadura = rodadura;
    }

    public short getOtrasViasAcceso() {
        return otrasViasAcceso;
    }

    public void setOtrasViasAcceso(short otrasViasAcceso) {
        this.otrasViasAcceso = otrasViasAcceso;
    }

    public short getUsoVia() {
        return usoVia;
    }

    public void setUsoVia(short usoVia) {
        this.usoVia = usoVia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (terrenoPK != null ? terrenoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Terreno)) {
            return false;
        }
        Terreno other = (Terreno) object;
        if ((this.terrenoPK == null && other.terrenoPK != null) || (this.terrenoPK != null && !this.terrenoPK.equals(other.terrenoPK))) {
            return false;
        }
        return true;

    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Terreno[ terrenoPK=" + terrenoPK + " ]";
    }

    public Float getDistanciaPoblacion1() {
        return distanciaPoblacion1;
    }

    public void setDistanciaPoblacion1(Float distanciaPoblacion1) {
        this.distanciaPoblacion1 = distanciaPoblacion1;
    }

    public Float getDistanciaPoblacion2() {
        return distanciaPoblacion2;
    }

    public void setDistanciaPoblacion2(Float distanciaPoblacion2) {
        this.distanciaPoblacion2 = distanciaPoblacion2;
    }

    public Float getDistanciaViaPrincipal1() {
        return distanciaViaPrincipal1;
    }

    public void setDistanciaViaPrincipal1(Float distanciaViaPrincipal1) {
        this.distanciaViaPrincipal1 = distanciaViaPrincipal1;
    }

    public Float getDistanciaViaPrincipal2() {
        return distanciaViaPrincipal2;
    }

    public void setDistanciaViaPrincipal2(Float distanciaViaPrincipal2) {
        this.distanciaViaPrincipal2 = distanciaViaPrincipal2;
    }

    public short getNumMedidoresLuz() {
        return numMedidoresLuz;
    }

    public void setNumMedidoresLuz(short numMedidoresLuz) {
        this.numMedidoresLuz = numMedidoresLuz;
    }

    public short getNumMedidoresAgua() {
        return numMedidoresAgua;
    }

    public void setNumMedidoresAgua(short numMedidoresAgua) {
        this.numMedidoresAgua = numMedidoresAgua;
    }

    public short getDisponibilidadRiego() {
        return disponibilidadRiego;
    }

    public void setDisponibilidadRiego(short disponibilidadRiego) {
        this.disponibilidadRiego = disponibilidadRiego;
    }

    public short getEliminacionExcretas() {
        return eliminacionExcretas;
    }

    public void setEliminacionExcretas(short eliminacionExcretas) {
        this.eliminacionExcretas = eliminacionExcretas;
    }

    public short getMetodoRiego() {
        return metodoRiego;
    }

    public void setMetodoRiego(short metodoRiego) {
        this.metodoRiego = metodoRiego;
    }

    public short getTransportePrivado() {
        return transportePrivado;
    }

    public void setTransportePrivado(short transportePrivado) {
        this.transportePrivado = transportePrivado;
    }

    public short getTipoViviendaRural() {
        return tipoViviendaRural;
    }

    public void setTipoViviendaRural(short tipoViviendaRural) {
        this.tipoViviendaRural = tipoViviendaRural;
    }

    public short getUsoConstruccionRural() {
        return usoConstruccionRural;
    }

    public void setUsoConstruccionRural(short usoConstruccionRural) {
        this.usoConstruccionRural = usoConstruccionRural;
    }

    public short getRequierePerfeccionamiento() {
        return requierePerfeccionamiento;
    }

    public void setRequierePerfeccionamiento(short requierePerfeccionamiento) {
        this.requierePerfeccionamiento = requierePerfeccionamiento;
    }

    public short getAniosSinPerfeccionamiento() {
        return aniosSinPerfeccionamiento;
    }

    public void setAniosSinPerfeccionamiento(short aniosSinPerfeccionamiento) {
        this.aniosSinPerfeccionamiento = aniosSinPerfeccionamiento;
    }

    public short getAniosEnPosesion() {
        return aniosEnPosesion;
    }

    public void setAniosEnPosesion(short aniosEnPosesion) {
        this.aniosEnPosesion = aniosEnPosesion;
    }

    public String getNombreEtnia() {
        return nombreEtnia;
    }

    public void setNombreEtnia(String nombreEtnia) {
        this.nombreEtnia = nombreEtnia;
    }

    public Double getFrentePropiedad() {
        return frentePropiedad;
    }

    public void setFrentePropiedad(Double frentePropiedad) {
        this.frentePropiedad = frentePropiedad;
    }

    public Double getFondoPropiedad() {
        return fondoPropiedad;
    }

    public void setFondoPropiedad(Double fondoPropiedad) {
        this.fondoPropiedad = fondoPropiedad;
    }

}
