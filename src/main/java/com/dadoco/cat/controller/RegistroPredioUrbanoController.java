/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.admin.service.IntegranteService;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Integrante;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.ParroquiaPK;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.SectorPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.ZonaHomogeneaService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.customFilters.LazyDataModelAdvance;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "regPredioUrbanoView")
@ViewScoped
public class RegistroPredioUrbanoController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegistroPredioUrbanoController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private ManzanaService manzanaService;
    @EJB
    private VariableService variablesService;
    @EJB
    private ContribuyenteService contribuyenteService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private ParroquiaService parroquiaService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private ZonaHomogeneaService zonaHomogeneaService;
    @EJB
    private SearchService searchService;
    @EJB
    private IntegranteService integranteService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private PredioService predioService;

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;
    private String solarCod;
    private String bloqueCod;
    private String phvCod;
    private String phhCod;

    private Terreno terreno;
    private boolean registroPH;
    private Manzana manzana;
    private Predio predio;
    private Parroquia parroquia;
    private Zona zona;
    private Sector sector;

    private List<Contribuyente> propietarios;
    private List<Contribuyente> propietarioNuevos;
    private List<Contribuyente> propietarioNuevosFiltrados;

    private Contribuyente propietarioSeleccionado;
    private Contribuyente nuevoPropietario;
    private List<Contribuyente> busquedaContribuyentes;

    private List<Contribuyente> contribuyentes;
    private Contribuyente contribuyenteSeleccionado;

    private LazyDataModelAdvance<Contribuyente> lazyData;
    private Contribuyente nuevoPosesionario;

    private List<Bloque> bloques;
    private Bloque bloqueSeleccionado;
    private int bloqueSeleccionadoIndex;

    private boolean creandoBloque;
    private boolean editandoBloque;

    private List<Piso> pisos;
    private Piso pisoSeleccionado;
    private int pisoSeleccionadoIndex;

    private boolean creandoPiso;
    private boolean editandoPiso;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloTerreno;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;
    private List<ValorDiscreto> valorDiscretos;
    private short codeUsoDefault;
    private short code;

    private boolean creandoPredio;
    private boolean tipoSeleccionado;

    private boolean dibujarPredio;

    private boolean terrenoExistente;

    private boolean isPH;

    private List<SelectItem> valoresTipoLote;
    private List<SelectItem> tiposPropietarios;

    private Escritura escritura;
    private String tipoPredio;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private boolean desarrollo;

    private boolean bloque;

    private List<UploadFile> fotosPredio;

    private List<UploadFile> fotosBloque;

//    private List<UploadFile> fotosBloques;
    private List<UploadFile> documentos;

    private List<Integrante> relevamientos;
    private Integrante relevamientoSeleccionado;
    private List<Integrante> validadores;
    private Integrante validadorSeleccionado;
    private List<Integrante> supervisores;
    private Integrante supervisorSeleccionado;
    private List<Integrante> supervisoresDigitadores;
    private Integrante supervisorDigtadorSeleccionado;
    private List<Integrante> cartografos;
    private Integrante cartografoSeleccionado;
    private List<Integrante> digitadores;
    private Integrante digitadorSeleccionado;
    private String digitador;

    private String parametroBusqueda;

    private int secuenciaBloque;

    private int secuenciaPiso;

    private boolean nuevoTerreno;

    private List<OtrosTipoConstruccion> obrasComplementarias;
    private OtrosTipoConstruccion obraSeleccionada;
    private int idObra;

    private short tipoObraSeleccionada;
    private short tipoConstruccion;
    private String columna;
    private String materialLabel;
    private String unidadLabel;
    private String onkeypressevent;
    private ValorDiscreto valorDiscretoObra;
    private short muro;
    private short especial;

    private String pathToDoc;
    private boolean fichamadre;
    private boolean PH;
    private short anioActual;
    private BigDecimal areaConstPH;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        Subject user = SecurityUtils.getSubject();
        Usuario usuario = getUsuarioService().serchUser(user.getPrincipal().toString());
        digitador = usuario.getNombre() + " " + usuario.getApellidos();
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        nuevoTerreno = false;
        propietarioNuevos = new ArrayList<>();
        nuevoPropietario = new Contribuyente();
        nuevoPosesionario = new Contribuyente();
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);
        
        areaConstPH = BigDecimal.ZERO;
        fichamadre = false;

        predio = new Predio();

        terreno = new Terreno();

        propietarios = new ArrayList<>();
        contribuyentes = new ArrayList<>();
        bloques = new ArrayList<>();
        pisos = new ArrayList<>();
        valoresTipoLote = new ArrayList<>();
        tiposPropietarios = new ArrayList<>();
        creandoBloque = false;
        editandoBloque = false;
        dibujarPredio = false;
        tipoSeleccionado = false;

        creandoPredio = false;
        terrenoExistente = false;

        creandoPiso = false;
        editandoPiso = false;
        tipoPredio = "M";
        escritura = new Escritura();

        fotosPredio = new ArrayList<>();

        fotosBloque = new ArrayList<>();

        documentos = new ArrayList<>();

        secuenciaBloque = 1;
        secuenciaPiso = 1;

        obrasComplementarias = new ArrayList<>();
        obraSeleccionada = new OtrosTipoConstruccion();
        idObra = catastroService.numeroProximaObra();

        usoSeleccionado = new UsoSuelo();
        usosSuelo = new ArrayList<>();
        usosSueloTerreno = new ArrayList<>();

        valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        valorDiscretos = getValorDiscretos();

        tipoConstruccion = 1;
        tipoObraSeleccionada = 1;
        materialLabel = "Tipo material:";
        columna = "muros";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
        muro = (short) 102;
        especial = (short) 101;

        setLazyData(new LazyDataModelAdvance<>(contribuyenteService));
        lazyData.setClasePK(Integer.class);

        codeUsoDefault = 12;

        //cargarPropietarioPorDefecto();
    }
//</editor-fold>

    public void buscarClaveAnterior() {
        if (predioService.existePredioClaveAnterior(predio.getClaveAnterior())) {
            JsfUti.messageError(null, "Info", "La clave ya existe en el sistema");
        } else {
            JsfUti.messageInfo(null, "Info", "Clave no encontrada");
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public short getAnioActual() {
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public boolean isFichamadre() {
        return fichamadre;
    }

    public void setFichamadre(boolean fichamadre) {
        this.fichamadre = fichamadre;
    }

    public boolean isPH() {
        return PH;
    }

    public void setPH(boolean PH) {
        this.PH = PH;
    }

    public String getPathToDoc() {
        return pathToDoc;
    }

    public void setPathToDoc(String pathToDoc) {
        this.pathToDoc = pathToDoc;
    }

    public boolean isBloque() {
        return bloque;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    public List<UsoSuelo> getUsosSueloTerreno() {
        return usosSueloTerreno;
    }

    public void setUsosSueloTerreno(List<UsoSuelo> usosSueloTerreno) {
        this.usosSueloTerreno = usosSueloTerreno;
    }

    public List<UploadFile> getFotosPredio() {
        return fotosPredio;
    }

    public void setFotosPredio(List<UploadFile> fotosPredio) {
        this.fotosPredio = fotosPredio;
    }

    public short getMuro() {
        return muro;
    }

    public void setMuro(short muro) {
        this.muro = muro;
    }

    public short getEspecial() {
        return especial;
    }

    public void setEspecial(short especial) {
        this.especial = especial;
    }

    public List<Contribuyente> getPropietarioNuevosFiltrados() {
        return propietarioNuevosFiltrados;
    }

    public void setPropietarioNuevosFiltrados(List<Contribuyente> propietarioNuevosFiltrados) {
        this.propietarioNuevosFiltrados = propietarioNuevosFiltrados;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public ValorDiscreto getValorDiscretoObra() {
        return valorDiscretoObra;
    }

    public void setValorDiscretoObra(ValorDiscreto valorDiscretoObra) {
        this.valorDiscretoObra = valorDiscretoObra;
    }

    public String getUnidadLabel() {
        return unidadLabel;
    }

    public void setUnidadLabel(String unidadLabel) {
        this.unidadLabel = unidadLabel;
    }

    public short getTipoConstruccion() {
        return tipoConstruccion;
    }

    public void setTipoConstruccion(short tipoConstruccion) {
        this.tipoConstruccion = tipoConstruccion;
    }

    public short getTipoObraSeleccionada() {
        return tipoObraSeleccionada;
    }

    public void setTipoObraSeleccionada(short tipoObraSeleccionada) {
        this.tipoObraSeleccionada = tipoObraSeleccionada;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getMaterialLabel() {
        return materialLabel;
    }

    public void setMaterialLabel(String materialLabel) {
        this.materialLabel = materialLabel;
    }

    public String getOnkeypressevent() {
        return onkeypressevent;
    }

    public void setOnkeypressevent(String onkeypressevent) {
        this.onkeypressevent = onkeypressevent;
    }

    public ValorDiscreto getValorDiscreto() {

        return valorDiscreto;
    }

    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public UsoSuelo getUsoSeleccionado() {
        return usoSeleccionado;
    }

    public void setUsoSeleccionado(UsoSuelo usoSeleccionado) {
        this.usoSeleccionado = usoSeleccionado;
    }

    public List<OtrosTipoConstruccion> getObrasComplementarias() {
        return obrasComplementarias;
    }

    public void setObrasComplementarias(List<OtrosTipoConstruccion> obrasComplementarias) {
        this.obrasComplementarias = obrasComplementarias;
    }

    public OtrosTipoConstruccion getObraSeleccionada() {
        return obraSeleccionada;
    }

    public void setObraSeleccionada(OtrosTipoConstruccion obraSeleccionada) {
        this.obraSeleccionada = obraSeleccionada;
    }

    public int getIdObra() {
        return idObra;
    }

    public void setIdObra(int idObra) {
        this.idObra = idObra;
    }

    public List<UploadFile> getFotosBloque() {
        return fotosBloque;
    }

    public void setFotosBloque(List<UploadFile> fotosBloque) {
        this.fotosBloque = fotosBloque;
    }

    public boolean isNuevoTerreno() {
        return nuevoTerreno;
    }

    public void setNuevoTerreno(boolean nuevoTerreno) {
        this.nuevoTerreno = nuevoTerreno;
    }

    public String getDigitador() {
        return digitador;
    }

    public void setDigitador(String digitador) {
        this.digitador = digitador;
    }

    public boolean isRegistroPH() {
        return registroPH;
    }

    public void setRegistroPH(boolean registroPH) {
        this.registroPH = registroPH;
    }

    public int getSecuenciaBloque() {
        return secuenciaBloque;
    }

    public void setSecuenciaBloque(int secuenciaBloque) {
        this.secuenciaBloque = secuenciaBloque;
    }

    public int getSecuenciaPiso() {
        return secuenciaPiso;
    }

    public void setSecuenciaPiso(int secuenciaPiso) {
        this.secuenciaPiso = secuenciaPiso;
    }

    public List<UploadFile> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<UploadFile> documentos) {
        this.documentos = documentos;
    }

    public ParroquiaService getParroquiaService() {
        return parroquiaService;
    }

    public void setParroquiaService(ParroquiaService parroquiaService) {
        this.parroquiaService = parroquiaService;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public ZonaHomogeneaService getZonaHomogeneaService() {
        return zonaHomogeneaService;
    }

    public void setZonaHomogeneaService(ZonaHomogeneaService zonaHomogeneaService) {
        this.zonaHomogeneaService = zonaHomogeneaService;
    }

    public List<Contribuyente> getPropietarioNuevos() {
        return propietarioNuevos;
    }

    public void setPropietarioNuevos(List<Contribuyente> propietarioNuevos) {
        this.propietarioNuevos = propietarioNuevos;
    }

    public Contribuyente getNuevoPropietario() {
        return nuevoPropietario;
    }

    public void setNuevoPropietario(Contribuyente nuevoPropietario) {
        this.nuevoPropietario = nuevoPropietario;
    }

    public List<Contribuyente> getBusquedaContribuyentes() {
        return busquedaContribuyentes;
    }

    public void setBusquedaContribuyentes(List<Contribuyente> busquedaContribuyentes) {
        this.busquedaContribuyentes = busquedaContribuyentes;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public boolean isDesarrollo() {
        return desarrollo;
    }

    public void setDesarrollo(boolean desarrollo) {
        this.desarrollo = desarrollo;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public ContribuyenteService getContribuyenteService() {
        return contribuyenteService;
    }

    public void setContribuyenteService(ContribuyenteService contribuyenteService) {
        this.contribuyenteService = contribuyenteService;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }

    public ZonaService getZonaService() {
        return zonaService;
    }

    public void setZonaService(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    public SectorService getSectorService() {
        return sectorService;
    }

    public void setSectorService(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public Piso getPisoSeleccionado() {
        return pisoSeleccionado;
    }

    public void setPisoSeleccionado(Piso pisoSeleccionado) {
        this.pisoSeleccionado = pisoSeleccionado;
    }

    public boolean isTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(boolean tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public Predio getPredio() {
        return predio;
    }

    public List<Piso> getPisos() {
        return pisos;
    }

    public void setPisos(List<Piso> pisos) {
        this.pisos = pisos;
    }

    public int getPisoSeleccionadoIndex() {
        return pisoSeleccionadoIndex;
    }

    public void setPisoSeleccionadoIndex(int pisoSeleccionadoIndex) {
        this.pisoSeleccionadoIndex = pisoSeleccionadoIndex;
    }

    public boolean isCreandoPiso() {
        return creandoPiso;
    }

    public void setCreandoPiso(boolean creandoPiso) {
        this.creandoPiso = creandoPiso;
    }

    public boolean isEditandoPiso() {
        return editandoPiso;
    }

    public void setEditandoPiso(boolean editandoPiso) {
        this.editandoPiso = editandoPiso;
    }

    public Contribuyente getPropietarioSelecccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSelecccionado(Contribuyente propietarioSelecccionado) {
        this.propietarioSeleccionado = propietarioSelecccionado;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public Bloque getBloqueSeleccionado() {
        return bloqueSeleccionado;
    }

    public void setBloqueSeleccionado(Bloque bloqueSeleccionado) {
        this.bloqueSeleccionado = bloqueSeleccionado;
    }

    public boolean isTerrenoExistente() {
        return terrenoExistente;
    }

    public void setTerrenoExistente(boolean terrenoExistente) {
        this.terrenoExistente = terrenoExistente;
    }

    public int getBloqueSeleccionadoIndex() {
        return bloqueSeleccionadoIndex;
    }

    public void setBloqueSeleccionadoIndex(int bloqueSeleccionadoIndex) {
        this.bloqueSeleccionadoIndex = bloqueSeleccionadoIndex;
    }

    public boolean isCreandoPredio() {
        return creandoPredio;
    }

    public void setCreandoPredio(boolean creandoPredio) {
        this.creandoPredio = creandoPredio;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public boolean isDibujarPredio() {
        return dibujarPredio;
    }

    public void setDibujarPredio(boolean dibujarPredio) {
        this.dibujarPredio = dibujarPredio;
    }

    public boolean isEditandoBloque() {
        return editandoBloque;
    }

    public void setEditandoBloque(boolean editandoBloque) {
        this.editandoBloque = editandoBloque;
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String getProvinciaCod() {
        return provinciaCod;
    }

    public void setProvinciaCod(String provinciaCod) {
        this.provinciaCod = provinciaCod;
    }

    public String getCantonCod() {
        return cantonCod;
    }

    public void setCantonCod(String cantonCod) {
        this.cantonCod = cantonCod;
    }

    public String getParroquiaCod() {
        return parroquiaCod;
    }

    public void setParroquiaCod(String parroquiaCod) {
        this.parroquiaCod = parroquiaCod;
    }

    public String getZonaCod() {
        return zonaCod;
    }

    public void setZonaCod(String zonaCod) {
        this.zonaCod = zonaCod;
    }

    public String getSectorCod() {
        return sectorCod;
    }

    public void setSectorCod(String sectorCod) {
        this.sectorCod = sectorCod;
    }

    public String getManzanaCod() {
        return manzanaCod;
    }

    public void setManzanaCod(String manzanaCod) {
        this.manzanaCod = manzanaCod;
    }

    public String getSolarCod() {
        return solarCod;
    }

    public void setSolarCod(String solarCod) {
        this.solarCod = solarCod;
    }

    public String getBloqueCod() {
        return bloqueCod;
    }

    public void setBloqueCod(String bloqueCod) {
        this.bloqueCod = bloqueCod;
    }

    public String getPhvCod() {
        return phvCod;
    }

    public void setPhvCod(String phvCod) {
        this.phvCod = phvCod;
    }

    public String getPhhCod() {
        return phhCod;
    }

    public void setPhhCod(String phhCod) {
        this.phhCod = phhCod;
    }

    public List<Integrante> getDigitadores() {
        digitadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 3);
        return digitadores;
    }

    public void setDigitadores(List<Integrante> digitadores) {
        this.digitadores = digitadores;
    }

    public Integrante getDigitadorSeleccionado() {
        return digitadorSeleccionado;
    }

    public void setDigitadorSeleccionado(Integrante digitadorSeleccionado) {
        this.digitadorSeleccionado = digitadorSeleccionado;
    }

    public List<Integrante> getRelevamientos() {
        relevamientos = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 0);
        return relevamientos;
    }

    public void setRelevamientos(List<Integrante> relevamientos) {
        this.relevamientos = relevamientos;
    }

    public Integrante getRelevamientoSeleccionado() {
        return relevamientoSeleccionado;
    }

    public void setRelevamientoSeleccionado(Integrante relevamientoSeleccionado) {
        this.relevamientoSeleccionado = relevamientoSeleccionado;
    }

    public List<Integrante> getValidadores() {
        validadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 2);
        return validadores;
    }

    public void setValidadores(List<Integrante> validadores) {
        this.validadores = validadores;
    }

    public Integrante getValidadorSeleccionado() {
        return validadorSeleccionado;
    }

    public void setValidadorSeleccionado(Integrante validadorSeleccionado) {
        this.validadorSeleccionado = validadorSeleccionado;
    }

    public List<Integrante> getSupervisores() {
        supervisores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 1);
        return supervisores;
    }

    public void setSupervisores(List<Integrante> supervisores) {
        this.supervisores = supervisores;
    }

    public Integrante getSupervisorSeleccionado() {
        return supervisorSeleccionado;
    }

    public void setSupervisorSeleccionado(Integrante supervisorSeleccionado) {
        this.supervisorSeleccionado = supervisorSeleccionado;
    }

    public List<Integrante> getSupervisoresDigitadores() {
        supervisoresDigitadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 4);
        return supervisoresDigitadores;
    }

    public void setSupervisoresDigitadores(List<Integrante> supervisoresDigitadores) {
        this.supervisoresDigitadores = supervisoresDigitadores;
    }

    public Integrante getSupervisorDigtadorSeleccionado() {
        return supervisorDigtadorSeleccionado;
    }

    public void setSupervisorDigtadorSeleccionado(Integrante supervisorDigtadorSeleccionado) {
        this.supervisorDigtadorSeleccionado = supervisorDigtadorSeleccionado;
    }

    public List<Integrante> getCartografos() {
        cartografos = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 5);
        return cartografos;
    }

    public void setCartografos(List<Integrante> cartografos) {
        this.cartografos = cartografos;
    }

    public Integrante getDatoNulo() {
        return null;
    }

    public Integrante getCartografoSeleccionado() {
        return cartografoSeleccionado;
    }

    public void setCartografoSeleccionado(Integrante cartografoSeleccionado) {
        this.cartografoSeleccionado = cartografoSeleccionado;
    }

    public short getCodeUsoDefault() {
        return codeUsoDefault;
    }

    public void setCodeUsoDefault(short codeUsoDefault) {
        this.codeUsoDefault = codeUsoDefault;
    }

    public boolean isCreandoBloque() {
        return creandoBloque;
    }

    public void setCreandoBloque(boolean creandoBloque) {
        this.creandoBloque = creandoBloque;
    }

    public String getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(String tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public List<Contribuyente> getContribuyentes() {
        return contribuyentes;
    }

    public void setContribuyentes(List<Contribuyente> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }

    public Contribuyente getContribuyenteSeleccionado() {
        return contribuyenteSeleccionado;
    }

    public void setContribuyenteSeleccionado(Contribuyente contribuyenteSeleccionado) {
        this.contribuyenteSeleccionado = contribuyenteSeleccionado;
    }

    public boolean isIsPH() {

        return isPH;
    }

    public void setIsPH(boolean isPH) {
        this.isPH = isPH;
    }

    public List<SelectItem> getTiposPropietarios() {
        tiposPropietarios = new ArrayList<>();
        tiposPropietarios.add(new SelectItem(0, "PROPIETARIO"));
        tiposPropietarios.add(new SelectItem(1, "POSESIONARIO"));
        tiposPropietarios.add(new SelectItem(2, "ARRENDATARIO"));
        return tiposPropietarios;
    }

    public void setTiposPropietarios(List<SelectItem> tiposPropietarios) {
        this.tiposPropietarios = tiposPropietarios;
    }

    public List<SelectItem> getValoresTipoLote() {
        this.valoresTipoLote = valoresVariables("cat_terreno", "tipo_lote");
        return this.valoresTipoLote;
    }

    public void setValoresTipoLote(List<SelectItem> valoresTipoLote) {
        this.valoresTipoLote = valoresTipoLote;
    }

    public List<SelectItem> valoresVariables(String tabla, String columna) {

        List<SelectItem> lista = new LinkedList<>();
        List<ValorDiscreto> valores = variablesService.obtenerValores(tabla, columna, anioActual);
        for (ValorDiscreto v : valores) {
            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
            lista.add(i);
        }
        return lista;

    }

    public List<ValorDiscreto> getValorDiscretos() {
        valorDiscretos = new ArrayList<>();

        for (int i = 33; i < 54; i++) {
            List<ValorDiscreto> valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
            if (valores != null) {
                valorDiscretos.addAll(valores);
            }
        }

        return valorDiscretos;
    }

    public void setValorDiscretos(List<ValorDiscreto> valorDiscretos) {
        this.valorDiscretos = valorDiscretos;
    }

//</editor-fold>
    public void buscarTerreno() {

        PH = false;
        try {
            fichamadre = false;
            ajustarOpcionesBusqueda();
            provinciaCod = opcionesBusqueda.getProvinciaCod();
            cantonCod = opcionesBusqueda.getCantonCod();
            parroquiaCod = opcionesBusqueda.getParroquiaCod();
            zonaCod = opcionesBusqueda.getZonaCod();
            sectorCod = opcionesBusqueda.getSectorCod();
            manzanaCod = opcionesBusqueda.getManzanaCod();
            solarCod = opcionesBusqueda.getSolarCod();
            bloqueCod = opcionesBusqueda.getBloqueCod();
            phvCod = opcionesBusqueda.getPhvCod();
            phhCod = opcionesBusqueda.getPhhCod();

            ParroquiaPK parroquiaPK = new ParroquiaPK();
            parroquiaPK.setCodProvincia(provinciaCod);
            parroquiaPK.setCodCanton(cantonCod);
            parroquiaPK.setCodParroquia(parroquiaCod);

            parroquia = parroquiaService.find(parroquiaPK);

            if (parroquia != null) {
                ZonaPK zonaPK = new ZonaPK();
                zonaPK.setCodProvincia(provinciaCod);
                zonaPK.setCodCanton(cantonCod);
                zonaPK.setCodParroquia(parroquia.getParroquiaPK().getCodParroquia());
                zonaPK.setCodZona(zonaCod);

                zona = zonaService.find(zonaPK);

                if (zona != null) {
                    SectorPK sectorPK = new SectorPK();
                    sectorPK.setCodProvincia(provinciaCod);
                    sectorPK.setCodCanton(cantonCod);
                    sectorPK.setCodParroquia(parroquiaCod);
                    sectorPK.setCodZona(zona.getZonaPK().getCodZona());
                    sectorPK.setCodSector(sectorCod);

                    sector = sectorService.find(sectorPK);

                    if (sector != null) {
                        ManzanaPK manzanaPK = new ManzanaPK();
                        manzanaPK.setCodProvincia(provinciaCod);
                        manzanaPK.setCodCanton(cantonCod);
                        manzanaPK.setCodParroquia(parroquiaCod);
                        manzanaPK.setCodZona(zonaCod);
                        manzanaPK.setCodSector(sectorCod);
                        manzanaPK.setCodManzana(manzanaCod);

                        manzana = manzanaService.find(manzanaPK);

                        if (manzana != null) {

                            if (solarCod.equals("0")) {

                                JsfUtil.addErrorMessage("El codigo del solar no puede ser 0");
                                opcionesBusqueda.setEjecutandoAccion(false);
                                return;
                            }

                            TerrenoPK terrenoPK = new TerrenoPK(manzanaPK, solarCod);
                            terreno = catastroService.obtenerTerreno(terrenoPK);
                            Predio fichaResumen;
                            if (terreno != null) {
                                fichaResumen = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, "000", "000", "000");
                                if (fichaResumen != null) {
                                    if (Integer.parseInt(bloqueCod) + Integer.parseInt(phhCod) + Integer.parseInt(phvCod) == 0) {
                                        JsfUtil.addInformationMessage(null, "Predio " + fichaResumen.getClaveCatastral() + " ya se encuentra registrado.");

                                    } else if (fichaResumen.getTipoPropiedad() != 2) {
                                        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-0-0-0";
                                        JsfUtil.addInformationMessage(null, "Predio con clave: " + clave + " no esta registrado como propiedad horizontal.");
                                        opcionesBusqueda.setEjecutandoAccion(false);

                                    } else if (Integer.parseInt(phvCod) == 0 || Integer.parseInt(phhCod) == 0 || Integer.parseInt(bloqueCod) == 0) {
                                        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                        JsfUtil.addInformationMessage(null, "Predio " + clave + " ninguno de los codigos BLOQUE, PHV y PHH no pueden tener valor valor 0.");
                                    } else {
                                        predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
                                        if (predio != null) {
                                            JsfUtil.addInformationMessage(null, "Predio " + predio.getClaveCatastral() + " ya se encuentra registrado.");

                                        } else if (Integer.parseInt(bloqueCod) != 0 && Integer.parseInt(phvCod) != 0 && Integer.parseInt(phhCod) != 0) {
                                            boolean estaNumeroBloque = false;
                                            for (Bloque b : fichaResumen.getBloques()) {
                                                if (b.getNumeroBloque() == Integer.parseInt(bloqueCod)) {
                                                    estaNumeroBloque = true;
                                                    break;
                                                }
                                            }

                                            if (estaNumeroBloque) {
                                                predio = new Predio();
                                                String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                                predio.setClaveCatastral(clave);
                                                predio.setBloques(new ArrayList<Bloque>());
                                                predio.setCodBloque(bloqueCod);
                                                predio.setCodPhv(phvCod);
                                                predio.setCodPhh(phhCod);
                                                opcionesBusqueda.setEjecutandoAccion(true);
                                                PH = true;
                                                bloqueSeleccionado = fichaResumen.getBloques() != null ? fichaResumen.getBloques().get(0) : null;
                                                if(bloqueSeleccionado != null){
                                                    bloqueSeleccionado.setId(null);
                                                    bloqueSeleccionado.setPredio(null);
                                                    (bloques = new ArrayList()).add(bloqueSeleccionado);
                                                }else{
                                                    JsfUtil.addErrorMessage(null, "Error. No se encontro ninguna construcción en el predio matriz");
                                                    this.init();
                                                    return;
                                                }
                                                    
                                                this.areaConstPH = BigDecimal.valueOf(bloqueSeleccionado.getAreaTotal());
                                            } else {
                                                //String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                                JsfUtil.addInformationMessage(null, "El número del bloque " + bloqueCod + " no corresponde con el número de bloque registrado en la ficha madre.");
                                            }
                                        } else {
                                            String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                            JsfUtil.addInformationMessage(null, "Predio " + clave + " debe tener los codigos Bloque, PHV y PHH con valor distinto de 0 para ingresar una PH.");
                                        }
                                    }
                                }
                            } else if (Integer.parseInt(bloqueCod) != 0 || Integer.parseInt(phhCod) != 0 || Integer.parseInt(phvCod) != 0) {
                                JsfUtil.addErrorMessage("Debe registrar la ficha madre con clave: " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-0-0-0 para poder registrar las PH.");
                                opcionesBusqueda.setEjecutandoAccion(false);

                            } else {

                                nuevoTerreno = true;
                                predio = new Predio();
                                predio.setCodBloque("000");
                                predio.setCodPhh("000");
                                predio.setCodPhv("000");
                                String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                predio.setClaveCatastral(clave);
                                predio.setBloques(new ArrayList<Bloque>());
                                terreno = new Terreno();
                                terreno.setTerrenoPK(terrenoPK);
                                terreno.setManzana(manzana);
                                terreno.setPredios(new ArrayList<Predio>());
                                opcionesBusqueda.setEjecutandoAccion(true);
                            }

                        } else {
                            JsfUtil.addErrorMessage("Manzana con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + " no se encuentra registrada en el sistema.");
                            opcionesBusqueda.setEjecutandoAccion(false);
                        }
                    } else {
                        JsfUtil.addErrorMessage("Sector con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + " no se encuentra registrado en el sistema.");
                        opcionesBusqueda.setEjecutandoAccion(false);
                    }

                } else {
                    JsfUtil.addErrorMessage("Zona con clave " + parroquiaCod + "-" + zonaCod + " no se encuentra registrada en el sistema.");
                    opcionesBusqueda.setEjecutandoAccion(false);
                }

            } else {
                JsfUtil.addErrorMessage("Parroquia con clave " + parroquiaCod + " no se encuentra registrada en el sistema.");
                opcionesBusqueda.setEjecutandoAccion(false);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

//<editor-fold defaultstate="collapsed" desc="buscarTerrenoDesarrollo">
    public void buscarTerrenoPH() {
        try {
            fichamadre = true;
            ajustarOpcionesBusqueda();
            provinciaCod = opcionesBusqueda.getProvinciaCod();
            cantonCod = opcionesBusqueda.getCantonCod();
            parroquiaCod = opcionesBusqueda.getParroquiaCod();
            zonaCod = opcionesBusqueda.getZonaCod();
            sectorCod = opcionesBusqueda.getSectorCod();
            manzanaCod = opcionesBusqueda.getManzanaCod();
            solarCod = opcionesBusqueda.getSolarCod();
            bloqueCod = opcionesBusqueda.getBloqueCod();
            phvCod = opcionesBusqueda.getPhvCod();
            phhCod = opcionesBusqueda.getPhhCod();

            ParroquiaPK parroquiaPK = new ParroquiaPK();
            parroquiaPK.setCodProvincia(provinciaCod);
            parroquiaPK.setCodCanton(cantonCod);
            parroquiaPK.setCodParroquia(parroquiaCod);

            parroquia = parroquiaService.find(parroquiaPK);

            if (parroquia != null) {
                ZonaPK zonaPK = new ZonaPK();
                zonaPK.setCodProvincia(provinciaCod);
                zonaPK.setCodCanton(cantonCod);
                zonaPK.setCodParroquia(parroquia.getParroquiaPK().getCodParroquia());
                zonaPK.setCodZona(zonaCod);

                zona = zonaService.find(zonaPK);

                if (zona != null) {
                    SectorPK sectorPK = new SectorPK();
                    sectorPK.setCodProvincia(provinciaCod);
                    sectorPK.setCodCanton(cantonCod);
                    sectorPK.setCodParroquia(parroquiaCod);
                    sectorPK.setCodZona(zona.getZonaPK().getCodZona());
                    sectorPK.setCodSector(sectorCod);

                    sector = sectorService.find(sectorPK);

                    if (sector != null) {
                        ManzanaPK manzanaPK = new ManzanaPK();
                        manzanaPK.setCodProvincia(provinciaCod);
                        manzanaPK.setCodCanton(cantonCod);
                        manzanaPK.setCodParroquia(parroquiaCod);
                        manzanaPK.setCodZona(zonaCod);
                        manzanaPK.setCodSector(sectorCod);
                        manzanaPK.setCodManzana(manzanaCod);

                        manzana = manzanaService.find(manzanaPK);

                        if (manzana != null) {

                            TerrenoPK terrenoPK = new TerrenoPK(manzanaPK, solarCod);
                            terreno = catastroService.obtenerTerreno(terrenoPK);
                            if (terreno != null) {
                                String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-0-0-0";
                                JsfUtil.addInformationMessage(null, "Predio " + clave + " ya se encuentra registrado.");
                            } else {

                                nuevoTerreno = true;
                                predio = new Predio();
                                predio.setTipoPropiedad((short) 2);
                                String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                predio.setClaveCatastral(clave);
                                predio.setBloques(new ArrayList<Bloque>());
                                terreno = new Terreno();
                                terreno.setTerrenoPK(terrenoPK);
                                terreno.setManzana(manzana);
                                terreno.setPredios(new ArrayList<Predio>());
                                terreno.setEstado((short) 3);
                                opcionesBusqueda.setEjecutandoAccion(true);
                            }

                        } else {
                            JsfUtil.addErrorMessage("Manzana con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + " no se encuentra registrada en el sistema.");
                            opcionesBusqueda.setEjecutandoAccion(false);
                        }
                    } else {
                        JsfUtil.addErrorMessage("Sector con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + " no se encuentra registrado en el sistema.");
                        opcionesBusqueda.setEjecutandoAccion(false);
                    }

                } else {
                    JsfUtil.addErrorMessage("Zona con clave " + parroquiaCod + "-" + zonaCod + " no se encuentra registrada en el sistema.");
                    opcionesBusqueda.setEjecutandoAccion(false);
                }

            } else {
                JsfUtil.addErrorMessage("Parroquia con clave " + parroquiaCod + " no se encuentra registrada en el sistema.");
                opcionesBusqueda.setEjecutandoAccion(false);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }
//</editor-fold>

    public String cancelarCreacionPredio() {
        /* RequestContext context = RequestContext.getCurrentInstance();
         context.reset("crearPredioForm");*/

        // FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("myBean");
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String crearPredio() {
        String claveCatastral = "";
        predio.setEstado(new Short("1"));
        predio.setPropietarios(propietarios);
        predio.setOtrosConstruccion(obrasComplementarias);
        predio.setUsosSuelo(usosSueloTerreno);
        predio.setDigitador(digitadorSeleccionado);
        predio.setSupervisor(supervisorSeleccionado);
        predio.setSupervisorDigitacion(supervisorDigtadorSeleccionado);
        predio.setValidador(validadorSeleccionado);
        predio.setCartografo(cartografoSeleccionado);
        predio.setRelevamiento(relevamientoSeleccionado);
        predio.setTipoPredio(Boolean.TRUE);
        if(fichamadre){
            predio.setCodBloque("000");
            predio.setCodPhh("000");
            predio.setCodPhv("000");
        }
        
        //if(predio.getEstadoLegalPredio() == 1)
        //    predio.setEstado((short)-1);
        
        if(this.predioService.existePredio(parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, predio.getCodBloque(), predio.getCodPhv(), predio.getCodPhh())){
            JsfUti.messageInfo(null, "Info", "Predio ya fue guardado.");
            return "pretty:ingresar-predio";
        }
        
        if(PH){
            bloqueSeleccionado.setAreaTotal(areaConstPH.doubleValue());
            bloqueSeleccionado.setPredio(predio);
            predio.setBloques(bloques);
        }
        
        try {
            claveCatastral = terreno.getTerrenoPK().getCodParroquia() + "-" + terreno.getTerrenoPK().getCodZona() + "-" + terreno.getTerrenoPK().getCodSector() + "-" + terreno.getTerrenoPK().getCodManzana() + "-" + terreno.getTerrenoPK().getCodSolar() + "-" + predio.getCodBloque() + "-" + predio.getCodPhv() + "-" + predio.getCodPhh();
            predio.setClaveCatastral(claveCatastral);
            catastroService.crearPredio(nuevoTerreno, terreno, predio, fotosPredio, escritura, documentos);
            Manzana manzanaEdit = manzanaService.getManzana(terreno.getTerrenoPK());
            String cod_solar = terreno.getTerrenoPK().getCodSolar();
            manzanaEdit.setSecuencia(Integer.parseInt("" + cod_solar));

            manzanaService.edit(manzanaEdit);

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest htservlet = (HttpServletRequest) fc.getExternalContext().getRequest();
            InetAddress add = InetAddress.getByName(htservlet.getRemoteAddr());            

            JsfUtil.addInformationMessage("Predio ", predio.getClaveCatastral() + " regsitrado con exito.");
        } catch (Exception e) {

        }
        creandoPredio = false;

        //JsfUtil.addSuccessMessage("Predio " + predio.getClaveCatastral() + " regsitrado con exito.");
        if (fichamadre) {
            return "pretty:ingresar-ficha-resumen";
        } else {
            return "pretty:ingresar-predio";
        }
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public String onFlowProcess(FlowEvent event) {

        obraSeleccionada = new OtrosTipoConstruccion();
        usoSeleccionado = new UsoSuelo();
//        if (terreno.getUsoSuelo() != 0) {
//
//            int id = valorById(terreno.getUsoSuelo());
//            codeUsoDefault = valorDefaultById(id);
//
        valorDiscreto = variablesService.obtenerValorbyVariableValor(33, codeUsoDefault, anioActual);
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
//        }

        if (event.getNewStep().equals("step-datos-terreno") && event.getOldStep().equals("step-datos-dominio")) {
            bloque = false;
        } else if (event.getNewStep().equals("step-datos-construccion") && event.getOldStep().equals("step-datos-terreno")) {
            bloque = true;
        } else if (event.getOldStep().equals("step-datos-construccion") && event.getNewStep().equals("step-datos-terreno")) {
            bloque = false;
        } else {
            bloque = true;
        }
        return event.getNewStep();

    }

    private void validarEscrituras() {

    }

    public void nuevoPredio() {
        creandoPredio = true;
    }

    private int numeroBloque() {

        if (bloques.isEmpty()) {
            secuenciaBloque = 1;
        } else {
            secuenciaBloque = bloques.get(bloques.size() - 1).getNumeroBloque();
            secuenciaBloque++;
        }

        return secuenciaBloque;
    }

    //<editor-fold defaultstate="collapsed" desc="inicializarNuevoBloque">
    public void inicializarNuevoBloque() {
        bloqueSeleccionado = new Bloque();
        bloqueSeleccionado.setPisos(new ArrayList<Piso>());

        bloqueSeleccionado.setNumeroBloque(numeroBloque());
        secuenciaPiso = 1;
        pisoSeleccionado = new Piso();
        pisos = new ArrayList<>();
        pisoSeleccionadoIndex = 0;
        creandoBloque = true;
        creandoPiso = false;
        editandoBloque = false;
        editandoBloque = false;
        fotosBloque.clear();
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionBloque">
    public void inicializarActualizacionBloque() {
        editandoBloque = true;
        creandoBloque = false;
        fotosBloque = bloqueSeleccionado.getFotosBloque();
        pisos = bloqueSeleccionado.getPisos();
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="crearBloque">
    public void crearBloque() {

        if (pisos.isEmpty()) {
            secuenciaBloque--;
            creandoBloque = false;
            creandoPiso = false;
            editandoBloque = false;
            editandoBloque = false;
            JsfUtil.addErrorMessage("Debe registrarle pisos al bloque.");
        } else if (bloqueSeleccionado != null) {

            if (bloqueSeleccionado.getNumeroNiveles() != pisos.size()) {
                JsfUtil.addErrorMessage("Faltan " + (bloqueSeleccionado.getNumeroNiveles() - pisos.size()) + " pisos por registrar al bloque " + bloqueSeleccionado.getNumeroBloque() + " para que coincida con el nro de niveles.");
            } else {
                for (int i = 0; i < pisos.size(); i++) {
                    pisos.get(i).setNumeroPiso(i + 1);
                }

                bloqueSeleccionado.setPisos(pisos);
                bloqueSeleccionado.setPredio(predio);
                bloqueSeleccionado.setFotosBloque(fotosBloque);
                for (int i = 0; i < usosSuelo.size(); i++) {
                    usosSuelo.get(i).setBloque(bloqueSeleccionado);
                }
                bloqueSeleccionado.setUsosSuelo(usosSuelo);
                boolean estaBloque = false;
                String creado = " creado ";
                for (int i = 0; i < bloques.size(); i++) {
                    if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {
                        bloques.remove(i);
                        bloques.add(i, bloqueSeleccionado);
                        estaBloque = true;
                        creado = " editado ";
                        bloqueSeleccionadoIndex = i;
                        break;
                    }
                }

                if (!estaBloque) {
                    bloques.add(bloqueSeleccionado);
                    bloqueSeleccionadoIndex = bloques.size() - 1;
                }

                predio.setBloques(bloques);

                JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                creandoBloque = false;
                editandoBloque = false;
                editandoPiso = false;
                creandoPiso = false;
                secuenciaPiso = 1;
            }

        }
        obraSeleccionada = new OtrosTipoConstruccion();
        usosSuelo = new ArrayList<UsoSuelo>();
        usoSeleccionado = new UsoSuelo();
        fotosBloque = new ArrayList<UploadFile>();
    }
//</editor-fold>

    public void cancelarCreacionBloque() {
        creandoBloque = false;
        creandoPiso = false;
        editandoBloque = false;
        editandoPiso = false;
        secuenciaBloque--;
        secuenciaPiso = 1;
        fotosBloque.clear();
        obraSeleccionada = new OtrosTipoConstruccion();
        usosSuelo = new ArrayList<>();
        usoSeleccionado = new UsoSuelo();
    }

    //<editor-fold defaultstate="collapsed" desc="actualizarBloque">
    public void actualizarBloque() {
        if (bloqueSeleccionado != null) {
            //bloqueSeleccionado.setPisos(pisos);
            JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + " actualizado con éxito.");
            creandoBloque = false;
            editandoBloque = false;
            creandoPiso = false;
            editandoPiso = false;
        }
        obraSeleccionada = new OtrosTipoConstruccion();
        usoSeleccionado = new UsoSuelo();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarBloque">
    public void eliminarBloque() {
        if (bloqueSeleccionado != null) {
            for (int i = 0; i < bloques.size(); i++) {
                if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {
                    bloques.remove(i);
                    break;
                }
            }

            if (!bloques.isEmpty()) {
                bloqueSeleccionado = bloques.get(0);
                bloqueSeleccionadoIndex = 0;
            }
        }
        creandoBloque = false;
        editandoBloque = false;
        creandoPiso = false;
        editandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarBloque">
    public void cambiarBloque() {
        bloqueSeleccionado = bloques.get(bloqueSeleccionadoIndex);
        pisos = bloqueSeleccionado.getPisos();
        pisoSeleccionadoIndex = -1;
        if (!pisos.isEmpty()) {
            pisoSeleccionado = pisos.get(0);
            pisoSeleccionadoIndex = 0;
        }
        editandoBloque = false;
        creandoBloque = false;
        creandoPiso = false;
        editandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();

        if (!bloqueSeleccionado.getUsosSuelo().isEmpty()) {
            usosSuelo = bloqueSeleccionado.getUsosSuelo();
            usoSeleccionado = usosSuelo.get(0);
        } else {
            usosSuelo = new ArrayList<>();
            usoSeleccionado = new UsoSuelo();
        }
    }
//</editor-fold>

    private int numeroPiso() {

        if (pisos.isEmpty()) {
            secuenciaPiso = 1;
        } else {
            secuenciaPiso = pisos.get(pisos.size() - 1).getNumeroPiso();
            secuenciaPiso++;
        }

        return secuenciaPiso;
    }

    //<editor-fold defaultstate="collapsed" desc="inicializarNuevoPiso">
    public void inicializarNuevoPiso() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (pisos.size() == bloqueSeleccionado.getNumeroNiveles()) {
            JsfUtil.addErrorMessage("La cantidad de pisos registra coincide con la cantidad de niveles del bloque.");
        } else {

            context.execute("PF('datosPisoDialog').show();");

            pisoSeleccionado = new Piso();
            if (pisos.isEmpty()) {
                pisoSeleccionado.setNumeroPiso(pisos.size() + 1);
            } else {
                pisoSeleccionado.setNumeroPiso(pisos.get(pisos.size() - 1).getNumeroPiso() + 1);
            }
            creandoPiso = true;
            editandoPiso = false;
        }
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="guardarPiso">
    public void guardarPiso() {
        try {
            if (pisoSeleccionado != null) {
                if (pisoSeleccionado.getArea() != 0) {
                    pisoSeleccionado.setBloque(bloqueSeleccionado);
                    boolean estaPiso = false;
                    String creado = " creado ";
                    for (int i = 0; i < pisos.size(); i++) {
                        if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {
                            pisos.remove(i);
                            pisos.add(i, pisoSeleccionado);
                            estaPiso = true;
                            creado = " editado ";
                            pisoSeleccionadoIndex = i;
                            break;
                        }
                    }
                    if (!estaPiso) {
                        pisos.add(pisoSeleccionado);
                        pisoSeleccionadoIndex = pisos.size() - 1;
                    }

                    creandoPiso = false;
                    editandoPiso = false;
                    bloqueSeleccionado.setPisos(pisos);
                    double area_total = 0;
                    for (Piso p : pisos) {
                        area_total += p.getArea();
                    }
                    bloqueSeleccionado.setAreaTotal(area_total);
                    JsfUtil.addSuccessMessage("Piso " + pisoSeleccionado.getNumeroPiso() + " del bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                } else {

                    JsfUtil.addErrorMessage("Área del piso no puede ser 0.");
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionPiso">
    public void inicializarActualizacionPiso() {
        editandoPiso = true;
        creandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="actualizarPiso">
    public void actualizarPiso() {
        editandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancelarCreacionPiso">
    public void cancelarCreacionPiso() {
        if (creandoPiso) {
            pisoSeleccionado = null;
        }
        editandoPiso = false;
        creandoPiso = false;
        secuenciaPiso--;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarPiso">
    public void cambiarPiso() {
        pisoSeleccionado = pisos.get(pisoSeleccionadoIndex);
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarPiso">
    public void eliminarPiso() {
        if (pisoSeleccionado != null) {
            for (int i = 0; i < pisos.size(); i++) {
                if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {
                    pisos.remove(i);
                    break;
                }
            }

            JsfUtil.addSuccessMessage("Piso " + pisoSeleccionado.getNumeroPiso() + " del bloque " + bloqueSeleccionado.getNumeroBloque() + " eliminado con èxito.");
            if (!pisos.isEmpty()) {
                pisoSeleccionado = pisos.get(0);
                pisoSeleccionadoIndex = 0;
            }

            double area_total = 0;
            for (Piso p : pisos) {
                area_total += p.getArea();
            }
            bloqueSeleccionado.setAreaTotal(area_total);
        }
        editandoPiso = false;
        creandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    public void inicializarBusquedaContribuyente() {
        nuevoPropietario = null;
        busquedaContribuyentes = new ArrayList<>();
        propietarioNuevos = new ArrayList<>();
    }

    public void buscarContribuyente() {
        try {
            propietarioNuevos = new ArrayList<>();

            propietarioNuevos = searchService.buscarContribuyentes(parametroBusqueda);

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    public void adicionarPropietario() {
        if (nuevoPropietario != null) {

            if (!propietarioNuevos.contains(nuevoPropietario)) {
                propietarioNuevos.add(nuevoPropietario);
            }

            if (!propietarios.contains(nuevoPropietario)) {
                propietarios.add(nuevoPropietario);

            }

            propietarioSeleccionado = nuevoPropietario;
            nuevoPropietario = null;
        }
        inicializarBusquedaContribuyente();
    }

    @Asynchronous
    public void handlePhotoUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_FP_";
        Util.copyFile(uploadedFile, fileName, fotosPredio, "fotos", (short) 1);
    }

    public void eliminarFotoPredio(int index) {
        Util.deleteFile(index, fotosPredio);
    }

    @Asynchronous
    public void handlePhotoBloqueUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_FB" + bloqueSeleccionado.getNumeroBloque() + "_";
        Util.copyFile(uploadedFile, fileName, fotosBloque, "fotos", (short) 1);
        bloqueSeleccionado.setFotosBloque(fotosBloque);
    }

    public void eliminarFotoBloque(int index) {
        Util.deleteFile(index, fotosBloque);
        bloqueSeleccionado.setFotosBloque(fotosBloque);
    }

    public String getCodigoCatastral() {

        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
        return clave;
    }

    public void eliminarPropietario() {

        if (propietarios.contains(propietarioSeleccionado)) {
            propietarios.remove(propietarioSeleccionado);
        }
        propietarioSeleccionado = null;
    }

    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("crearPredioForm");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiP = (UIInput) components.findComponent("pisoSelect");
        String p = uiP.getLocalValue() == null ? ""
                : uiP.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiP.setValid(false);
        }

        errores += visitor.getInvalidFields();

        if (errores != 0) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("Existen errores en el formulario");
            fc.addMessage(null, msg);
        }

        fc.renderResponse();

    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_PE_";
        Util.copyFile(uploadedFile, fileName, documentos, "documentos", (short) 2);

        pathToDoc = documentos.get(0).getPathToFile();

    }

    public void eliminarDocumento(int index) {
        Util.deleteFile(index, documentos);
    }

    public void onRowSelect(SelectEvent event) {

        nuevoPropietario = (Contribuyente) event.getObject();

    }

    public void onRowUnselect(UnselectEvent event) {

        propietarioSeleccionado = null;

    }

    public void initNuevaObra() {
        obraSeleccionada = new OtrosTipoConstruccion();
        obraSeleccionada.setNumero(idObra++);
        tipoConstruccion = (short) 1;
        obraSeleccionada.setTipoConstruccion((short) 1);
        materialLabel = "Tipo material:";
        columna = "muros";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
    }

    public void initEditObra() {
        if (obraSeleccionada.getVariable() == 100) {
            this.tipoConstruccion = (short) 3;
        }
        if (obraSeleccionada.getVariable() == 101) {
            this.tipoConstruccion = (short) 4;
        }
        if (obraSeleccionada.getVariable() == 102) {
            this.tipoConstruccion = (short) 1;
        }
        if (obraSeleccionada.getVariable() == 103) {
            this.tipoConstruccion = (short) 2;
        }

        switch (tipoConstruccion) {
            case 1:
            case 2:
                materialLabel = "Tipo material:";
                if (tipoConstruccion == 1) {
                    columna = "muros";
                } else {
                    columna = "cerramiento";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
        }

    }

    public void cancelarCrearObra() {
        if (!obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        }

    }

    public void eliminarObra() {
        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                obrasComplementarias.remove(i);
                break;
            }
        }

        if (!obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        } else {
            initNuevaObra();
        }
    }

    public void crearNuevaObra() {

        RequestContext context = RequestContext.getCurrentInstance();

        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, obraSeleccionada.getTipoConstruccion(), anioActual);
        obraSeleccionada.setVariable(valorDiscretoObra.getVariable().getId());
        obraSeleccionada.setUnidad(obraSeleccionada.getArea().shortValue());
        boolean existe = false;
        String accion = "editado";
        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                obrasComplementarias.remove(i);
                obrasComplementarias.add(i, obraSeleccionada);
                existe = true;
                break;
            }
        }
        if (!existe) {
            obraSeleccionada.setPredio(predio);
            obrasComplementarias.add(obraSeleccionada);
            accion = "creado";
        }
        initNuevaObra();
        JsfUtil.addInformationMessage(null, "Otro tipo de construcción " + accion + " con exito.");

        if (existe) {

            context.execute("PF('obrasDialog').hide();");

        }
    }

    public void initNuevoUso() {

        //usoSeleccionado = new UsoSuelo();
        //valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12);
        //usoSeleccionado.setValorDiscreto(getValorDiscreto());
        usoSeleccionado = new UsoSuelo();

        valorDiscreto = variablesService.obtenerValorbyVariableValor(33, codeUsoDefault, anioActual);

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());

    }

    public void initEditUso() {

    }

    public void cancelarCrearUso() {
        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        }

    }

    public void eliminarUso(boolean bloque) {

        if (bloque) {
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    usosSuelo.remove(i);
                    break;
                }
            }
            if (!usosSuelo.isEmpty()) {
                usoSeleccionado = usosSuelo.get(0);
            }
        } else {
            for (int i = 0; i < usosSueloTerreno.size(); i++) {
                if (Objects.equals(usosSueloTerreno.get(i).getCod(), usoSeleccionado.getCod())) {
                    usosSueloTerreno.remove(i);
                    break;
                }
            }
            if (!usosSueloTerreno.isEmpty()) {
                usoSeleccionado = usosSueloTerreno.get(0);
            }
        }

    }

    public void crearNuevoUso(boolean bloque) {

        if (valorDiscreto != null) {
            usoSeleccionado.setValorDiscreto(valorDiscreto);
            usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        } else {
//            int id = valorById(terreno.getUsoSuelo());
//            codeUsoDefault = valorDefaultById(id);
            valorDiscreto = variablesService.obtenerValorbyVariableValor(33, codeUsoDefault, anioActual);
            usoSeleccionado.setValorDiscreto(valorDiscreto);
            usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        }

        boolean existe = false;

        if (bloque) {
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                usoSeleccionado.setBloque(bloqueSeleccionado);
                usosSuelo.add(usoSeleccionado);
                bloqueSeleccionado.setUsosSuelo(usosSuelo);
                //initNuevoUso();
            }

            if (existe) {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo se encuentra registrado al bloque.");
            } else {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito");
            }
        }
        initNuevoUso();
    }

    public void onRowSelectUso(SelectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();

    }

    public void onRowUnselectUso(UnselectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();
    }

    public List<SelectItem> valoresVariablesuUsos() {

        List<SelectItem> lista = new ArrayList<>();
        List<ValorDiscreto> valores = new ArrayList<>();

        switch (terreno.getUsoSuelo()) {

            case 19: {
                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
            case 20: {
                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(57, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
            case 21: {
                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(57, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
            default: {
                valores = variablesService.obtenerValoresPorCodVariable(valorById(terreno.getUsoSuelo()), anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
        }

//        for (int i = 33; i <= 52; i++) {
//            valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
////            for (int j = 0; j < valores.size(); j++) {
////                for (UsoSuelo u : usosSuelo) {
////                    if (u.getCod() == valores.get(j).getValorDiscretoPK().getValor()) {
////                        valores.remove(j);
////                    }
////                }
////            }
//            for (ValorDiscreto v : valores) {
//                SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
//                lista.add(item);
//            }
//        }
//
        return lista;

    }

    public void ajustarOpcionesBusqueda() {
        opcionesBusqueda.setProvinciaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getProvinciaCod()), 2));
        opcionesBusqueda.setCantonCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getCantonCod()), 2));
        opcionesBusqueda.setParroquiaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getParroquiaCod()), 2));
        opcionesBusqueda.setZonaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getZonaCod()), 2));
        opcionesBusqueda.setSectorCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSectorCod()), 2));
        opcionesBusqueda.setManzanaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getManzanaCod()), 3));
        opcionesBusqueda.setSolarCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSolarCod()), 3));
        opcionesBusqueda.setBloqueCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getBloqueCod()), 3));
        opcionesBusqueda.setPhhCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getPhhCod()), 3));
        opcionesBusqueda.setPhvCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getPhvCod()), 3));
    }

    public String generadorCeroALaIzquierda(Long n, Integer max) {
        int cont = 0;
        Long num = n;
        String salida = "";
        while (num > 0) {
            num = num / 10;
            cont++;
        }
        for (int i = 0; i < max - cont; i++) {
            salida = salida + "0";
        }
        if(n > 0)
            salida = salida + n;
        return salida;
    }

    public List<SelectItem> valoresVariablesuUsosPH() {
        List<SelectItem> lista = new ArrayList<>();
        List<ValorDiscreto> valores = new ArrayList<>();

        switch (terreno.getUsoSuelo()) {

            case 19: {
                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
            case 20: {
                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(57, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
            case 21: {
                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                valores = variablesService.obtenerValoresPorCodVariable(57, anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
            default: {
                valores = variablesService.obtenerValoresPorCodVariable(valorById(terreno.getUsoSuelo()), anioActual);
                for (ValorDiscreto v : valores) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
                break;
            }
        }

//        for (int i = 33; i <= 52; i++) {
//            valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
////            for (int j = 0; j < valores.size(); j++) {
////                for (UsoSuelo u : usosSuelo) {
////                    if (u.getCod() == valores.get(j).getValorDiscretoPK().getValor()) {
////                        valores.remove(j);
////                    }
////                }
////            }
//            for (ValorDiscreto v : valores) {
//                SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
//                lista.add(item);
//            }
//        }
//
        return lista;

    }

    public String codigosVariablesuUsos() {

        String codigos = "";

        for (int i = 33; i < 54; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariable(i, anioActual);
            codigos += "-";
        }

        log.error("Code: " + codigos);

//        switch (terreno.getUsoSuelo()) {
//
//            case 1: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                break;
//            }buscarTerreno
//            case 2: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(34, anioActual);
//                break;
//            }
//            case 3: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(35, anioActual);
//                break;
//            }
//            case 4: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 5: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(38, anioActual);
//                break;
//            }
//            case 6: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                break;
//            }
//            case 7: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(40, anioActual);
//                break;
//            }
//            case 8: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(41, anioActual);
//                break;
//            }
//            case 9: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(42, anioActual);
//                break;
//            }
//            case 10: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(45, anioActual);
//                break;
//            }
//            case 11: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(46, anioActual);
//                break;
//            }
//            case 12: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(47, anioActual);
//                break;
//            }
//            case 13: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(48, anioActual);
//                break;
//            }
//            case 14: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(49, anioActual);
//                break;
//            }
//            case 15: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                break;
//            }
//            case 16: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(52, anioActual);
//                break;
//            }
//            case 17: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(36, anioActual);
//                break;
//            }
//            case 18: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(53, anioActual);
//                break;
//            }
//            case 19: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 20: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            case 21: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            default: {
//                break;
//            }
//        }
        return codigos;

    }

    public String codigosVariablesuUsosPH() {

        String codigos = "";

        for (int i = 33; i < 54; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariable(i, anioActual);
            codigos += "-";
        }
//        switch (terreno.getUsoSuelo()) {
//
//            case 1: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                break;
//            }
//            case 2: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(34, anioActual);
//                break;
//            }
//            case 3: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(35, anioActual);
//                break;
//            }
//            case 4: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 5: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(38, anioActual);
//                break;
//            }
//            case 6: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                break;
//            }
//            case 7: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(40, anioActual);
//                break;
//            }
//            case 8: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(41, anioActual);
//                break;
//            }
//            case 9: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(42, anioActual);
//                break;
//            }
//            case 10: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(45, anioActual);
//                break;
//            }
//            case 11: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(46, anioActual);
//                break;
//            }
//            case 12: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(47, anioActual);
//                break;
//            }
//            case 13: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(48, anioActual);
//                break;
//            }
//            case 14: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(49, anioActual);
//                break;
//            }
//            case 15: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                break;
//            }
//            case 16: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(52, anioActual);
//                break;
//            }
//            case 17: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(36, anioActual);
//                break;
//            }
//            case 18: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(53, anioActual);
//                break;
//            }
//            case 19: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 20: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            case 21: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-" + variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            default: {
//                // codigos += variablesService.obtenerCodigoPorIdVariable(terreno.getUsoSuelo(), anioActual);
//                break;
//            }
//        }

//        for (int i = 33; i <= 52; i++) {
//            codigos += variablesService.obtenerCodigoPorIdVariablePH(i,anioActual);
//        }
        return codigos;

    }

    private int valorById(int id) {
        switch (id) {
            default: {
                return 33;
            }
            case 2: {
                return 34;
            }
            case 3: {
                return 35;
            }
            case 4: {
                return 37;
            }
            case 5: {
                return 38;
            }
            case 6: {
                return 39;
            }
            case 7: {
                return 40;
            }
            case 8: {
                return 41;
            }
            case 9: {
                return 42;
            }
            case 10: {
                return 45;
            }
            case 11: {
                return 46;
            }
            case 12: {
                return 47;
            }
            case 13: {
                return 48;
            }
            case 14: {
                return 49;
            }
            case 15: {
                return 51;
            }
            case 16: {
                return 52;
            }
            case 17: {
                return 36;
            }
            case 18: {
                return 53;
            }

        }
    }

    private short valorDefaultById(int id) {
        switch (id) {
            default: {
                return 12;
            }
            case 34: {
                return 10;
            }
            case 35: {
                return 47;
            }
            case 37: {
                return 95;
            }
            case 38: {
                return 6;
            }
            case 39: {
                return 34;
            }
            case 40: {
                return 4;
            }
            case 41: {
                return 25;
            }
            case 42: {
                return 27;
            }
            case 45: {
                return 97;
            }
            case 46: {
                return 29;
            }
            case 47: {
                return 30;
            }
            case 48: {
                return 21;
            }
            case 49: {
                return 1;
            }
            case 51: {
                return 49;
            }
            case 52: {
                return 11;
            }
            case 36: {
                return 101;
            }
            case 53: {
                return 106;
            }

        }
    }

    public void changeUso() {

//        log.error("Variable: ");
//        ValorDiscreto aux = null;
//        for (int i = 33; i < 54; i++) {
//            aux = variablesService.obtenerValorbyVariableValor(i, usoSeleccionado.getCod(), anioActual);
//            if (aux != null) {
//                valorDiscreto = aux;
//                break;
//            }
//        }
//
//        //ValorDiscreto aux = variablesService.obtenerValorbyVariableValor(valorDiscreto.getValorDiscretoPK().getIdVariable(), usoSeleccionado.getCod(), anioActual);
//        if (aux == null) {
//           valorDiscreto = variablesService.obtenerValorbyVariableValor(33, (short)12, anioActual);
//        } 
        try {
            valorDiscreto = variablesService.obtenerValor(33, 53, usoSeleccionado.getCod(), anioActual);

        } catch (Exception e) {
            valorDiscreto = variablesService.obtenerValorbyVariableValor(33, (short) 12, anioActual);
        }

        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        usoSeleccionado.setValorDiscreto(valorDiscreto);
    }

    public void changeUsoSelect() {

        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        usoSeleccionado.setValorDiscreto(valorDiscreto);

    }

    public void onRowSelectObra(SelectEvent event) {

        obraSeleccionada = (OtrosTipoConstruccion) event.getObject();

    }

    public void onRowUnselectObra(UnselectEvent event) {
        obraSeleccionada = (OtrosTipoConstruccion) event.getObject();
    }

    public void changeTipoConstruccion() {
        switch (tipoConstruccion) {
            case 1:
            case 2:
                materialLabel = "Tipo material:";
                if (tipoConstruccion == 1) {
                    columna = "muros";
                } else {
                    columna = "cerramiento";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
        }

        obraSeleccionada.setTipoConstruccion((short) 1);
        tipoObraSeleccionada = 1;
    }

    public void changeTipoObra() {

        switch (tipoConstruccion) {
            case 1:
            case 2:
                materialLabel = "Tipo material:";
                if (tipoConstruccion == 1) {
                    columna = "muros";
                } else {
                    columna = "cerramiento";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
        }

        if (tipoConstruccion == 3 && obraSeleccionada.getTipoConstruccion() == 8) {
            onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')(event, 'area_estructura:cat_bloque-estructura-input')";
        } else {
            onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        }
    }

    public String nombreVariable(int id) {

        return variablesService.find(id).getNombre();
    }

    public String nombreValorDiscreto(int id, short tipo, short estructura, short cubierta) {
        String valor = variablesService.obtenerValor(id, tipo, anioActual);
        if (id == 100 && tipo == 8) {
            valor += " (ESTRUCTURA: " + variablesService.obtenerValor("cat_bloque", "estructura", estructura, anioActual)
                    + " CUBIERTA: " + variablesService.obtenerValor("cat_bloque", "cubierta", cubierta, anioActual) + ")";

        }
        return valor;
    }
    
    public void onCellEditPropietario(CellEditEvent event) {
        Object newValue = event.getNewValue();
        
        
        
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }

    public List<UploadFile> getFotosBloques() {
        List<UploadFile> fBloques = new ArrayList<>();
        for (Bloque bloq : bloques) {
            fBloques.addAll(bloq.getFotosBloque());
        }
        return fBloques;
    }
     
    public void updateEstadoTerreno() {
        if (terreno.getEstado() != 1) {
            terreno.setEstadoEdificacion((short) 0);
            terreno.setPisosTerminados((short) 0);
        } else {
            if (terreno.getEstadoEdificacion() == 0) {
                terreno.setPisosTerminados((short) 1);
            } else {
                terreno.setPisosTerminados((short) 0);
            }
        }

    }

    /**
     * @return the lazyData
     */
    public LazyDataModelAdvance<Contribuyente> getLazyData() {
        if (lazyData == null) {
            lazyData = new LazyDataModelAdvance<>(contribuyenteService);
        }
        return lazyData;
    }

    /**
     * @param lazyData the lazyData to set
     */
    public void setLazyData(LazyDataModelAdvance<Contribuyente> lazyData) {
        this.lazyData = lazyData;
    }

    public Contribuyente getNuevoPosesionario() {
        return nuevoPosesionario;
    }

    public void setNuevoPosesionario(Contribuyente nuevoPosesionario) {
        this.nuevoPosesionario = nuevoPosesionario;
    }

    public void inicializarNuevo() {
        nuevoPosesionario = new Contribuyente();

    }

    public void crearContribuyente() {

        try {

            nuevoPosesionario.setFechaIngreso(new Date());
            nuevoPosesionario.setNombre(nuevoPosesionario.getNombre().toUpperCase());
            nuevoPosesionario.setApellidoPaterno(nuevoPosesionario.getApellidoPaterno().toUpperCase());
            nuevoPosesionario.setApellidoMaterno(nuevoPosesionario.getApellidoMaterno().toUpperCase());
            String nombreCompleto = nuevoPosesionario.getApellidoPaterno() + " " + nuevoPosesionario.getApellidoMaterno() + " " + nuevoPosesionario.getNombre();
            nuevoPosesionario.setNombreCompleto(nombreCompleto);
            nuevoPosesionario = contribuyenteService.guardar(nuevoPosesionario);
            propietarios.add(nuevoPosesionario);

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('CreateContribuyenteDialog').hide();");

        } catch (EJBException e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void validarFormularioPosesionario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("adicionarContribuyenteForm");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiNumero = (UIInput) components.findComponent("numero_identificacion");
        String p = uiNumero.getLocalValue() == null ? ""
                : uiNumero.getLocalValue().toString();

        UIInput uiNombre = (UIInput) components.findComponent("nombre_contribuyente");
        String z = uiNombre.getLocalValue() == null ? ""
                : uiNombre.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiNumero.setValid(false);
        }
        if (z.equals("")) {
            errores++;
            uiNombre.setValid(false);
        }

        errores += visitor.getInvalidFields();

        if (errores != 0) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("Existen errores en el formulario");
            fc.addMessage(null, msg);
        }

        fc.renderResponse();

    }

    public BigDecimal getAreaConstPH() {
        return areaConstPH;
    }

    public void setAreaConstPH(BigDecimal areaConstPH) {
        this.areaConstPH = areaConstPH;
    }

}
