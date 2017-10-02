/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.admin.service.IntegranteService;
import com.dadoco.audit.service.AuditService;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.CatPredioRuralTerreno;
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
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.SectorPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.BloqueService;
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
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import javax.transaction.Transactional;
import org.apache.commons.io.IOUtils;
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
@Named(value = "regPredioRuralView")
@ViewScoped
public class RegistroPredioRuralController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegistroPredioRuralController.class);

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
    private ManagerService aclServices;
    @EJB
    private PredioService predioService;
    @EJB
    private BloqueService bloqueService;
    @EJB
    private AuditService auditServices;

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
    private boolean registroPH, tieneInfo;
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

    private List<Contribuyente> contribuyentes, eliminados;
    private Contribuyente contribuyenteSeleccionado;

    private LazyDataModelAdvance<Contribuyente> lazyData;
    private Contribuyente nuevoPosesionario;

    private List<Bloque> bloques;
    private Bloque bloqueSeleccionado;
    private int bloqueSeleccionadoIndex;

    private boolean creandoBloque;
    private boolean editandoBloque, isEditing;

    private List<Piso> pisos;
    private List<CatPredioRuralTerreno> terrenoList;
    private CatPredioRuralTerreno terrenoClaseSel;
    
    private Piso pisoSeleccionado;
    private int pisoSeleccionadoIndex;

    private boolean creandoPiso;
    private boolean editandoPiso;
    private int parroquiaSel;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloTerreno;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;
    private List<ValorDiscreto> valorDiscretos, valoresUsoPredio, valoresUsoPredioSelected;
    private short codeUsoDefault;
    private short code;

    private boolean creandoPredio;
    private boolean tipoSeleccionado;

    private boolean dibujarPredio;

    private boolean terrenoExistente;

    private boolean isPH;
    private Short tieneTituloPropiedad;
    private Short estadoEscritura;
    private Integer tipoJuridico;

    private List<SelectItem> valoresTipoLote;
    private List<SelectItem> tiposPropietarios;

    private Escritura escritura;
    private String tipoPredio;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private boolean desarrollo;

    private boolean bloque;

    private List<UploadFile> fotosPredio;

    private List<UploadFile> fotosBloque;
    
    private List<ArchivoAux> fotosPreview;
    private List<ArchivoAux> fotosBloquePreview;

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
    private String digitador, headerTextDialog;
    private Map<Integer, List> map;

    private String parametroBusqueda;

    private int secuenciaBloque;

    private int secuenciaPiso, tipoDialog;

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
    private Subject subject;
    private String pathToDoc;
    private boolean fichamadre;
    private boolean PH;
    private short anioActual, claseTipoTerreno;
    private CatPredioRuralTerreno claseTerreno;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {
        try{
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
            tieneTituloPropiedad = Short.parseShort("1");

            creandoPiso = false;
            editandoPiso = false;
            tipoPredio = "M";
            escritura = new Escritura();

            fotosPredio = new ArrayList<>();

            fotosBloque = new ArrayList<>();

            documentos = new ArrayList<>();
            claseTerreno = new CatPredioRuralTerreno();
            
            secuenciaBloque = 1;
            secuenciaPiso = 1;

            obrasComplementarias = new ArrayList<>();
            obraSeleccionada = new OtrosTipoConstruccion();
            idObra = catastroService.numeroProximaObra();

            usoSeleccionado = new UsoSuelo();
            usosSuelo = new ArrayList<>();
            usosSueloTerreno = new ArrayList<>();
            tieneInfo = true;

            valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
            usoSeleccionado.setValorDiscreto(valorDiscreto);
            valorDiscretos = getValorDiscretos();
            valoresUsoPredio = variablesService.obtenerValor("cat_terreno", "red_uso_del_predio_rural", anioActual);

            tipoConstruccion = 1;
            tipoObraSeleccionada = 1;
            materialLabel = "Tipo material:";
            columna = "muros_r";
            onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
            valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
            muro = (short) 102;
            especial = (short) 101;

            setLazyData(new LazyDataModelAdvance<>(contribuyenteService));
            lazyData.setClasePK(Integer.class);

            codeUsoDefault = 12;
            estadoEscritura = null;
            map = new HashMap();
            
            //cargarPropietarioPorDefecto();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void verificarEstadoTerreno(){
        if(this.terreno.getEstado() == 2)
            this.terreno.setTipoViviendaRural((short)0);
    }
    
//</editor-fold>
    
    public void changeNumIdent(){
        nuevoPosesionario.setIdentificacion("");
    }
    
    @Transactional
    public void eliminarClaseTerreno(int tipo){
        try{
            if(terrenoClaseSel == null){
                JsfUti.messageInfo(null, "Info", "Debe seleccionar un ítem antes de eliminarlo");
                return;
            }
            ((List)(this.map.get(tipo))).remove(terrenoClaseSel);
            
            if(((terrenoClaseSel)).getId() != null)
                aclServices.getEm().remove(aclServices.getEm().merge(terrenoClaseSel));
            
            int i = 1;
            
            // Reajusta el id temporal
            for(Object temp : ((List)(this.map.get(tipo)))){
                ((CatPredioRuralTerreno)temp).setIdTemporal(Short.parseShort(""+i));
                i++;
            }
            switch(tipo){
                case 1:
                    JsfUti.update("dtClaseTierra");
                    break;
                case 2:
                    JsfUti.update("dtCultAnuales");
                    break;
                case 3:
                    JsfUti.update("dtCultPerennes");
                    break;
                case 4:
                    JsfUti.update("dtForestales");
                    break;
                case 5:
                    JsfUti.update("dtSemovientes");
                    break;
                case 6:
                    JsfUti.update("dtAvesCorral");
                    break;
            }
            
        }catch(Exception e){e.printStackTrace();}        
    }
    
    public void verificarAreaTerreno(){
        if(terreno != null && terreno.getAreaLevantamiento() < 10000){
            terreno.setUsoSuelo((short)24);
        }
    }
    
    public void cancelar() {

        //String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        //System.out.println(viewId + "?faces-redirect=true");
        JsfUti.redirectFaces("/catastro/actualizar/predio/rural/completo");
        //return viewId + "?faces-redirect=true";
    }
    
    public void sumarLinderos(int i){
        try{
            String s[] = {"0"};
            Double cant = 0.0;

            switch(i){
                case 1:
                    s = this.terreno.getLinNorteLongitud().split("\\+");  
                    for(String n : s){
                        cant += Double.parseDouble(n);
                    }
                    this.terreno.setLinNorteLongitud(""+cant);
                    break;
                case 2:
                    s = this.terreno.getLinSurLongitud().split("\\+");
                    for(String n : s){
                        cant += Double.parseDouble(n);
                    }
                    this.terreno.setLinSurLongitud(""+cant);
                    break;
                case 3:
                    s = this.terreno.getLinEsteLongitud().split("\\+");
                    for(String n : s){
                        cant += Double.parseDouble(n);
                    }
                    this.terreno.setLinEsteLongitud(""+cant);
                    break;
                case 4:
                    s = this.terreno.getLinOesteLongitud().split("\\+");
                    for(String n : s){
                        cant += Double.parseDouble(n);
                    }
                    this.terreno.setLinOesteLongitud(""+cant);
                    break;
            }
            sumarLinderos();
        }catch(NumberFormatException  e){
            JsfUtil.addErrorMessage("Error al ingresar datos del lindero");
            e.printStackTrace();
        }        
    }
    
    public void sumarLinderos(){
        this.terreno.setPerimetroLevantamiento(Double.parseDouble(this.terreno.getLinNorteLongitud()) + Double.parseDouble(this.terreno.getLinSurLongitud()) + Double.parseDouble(this.terreno.getLinEsteLongitud()) + Double.parseDouble(this.terreno.getLinOesteLongitud()));
    }
    
    public void onRowSelectClaseTierra(SelectEvent event){
        terrenoClaseSel = (CatPredioRuralTerreno)event.getObject();
    }
    
    public void initEditTerrenoClases(int tipo){
        claseTerreno = terrenoClaseSel;
        claseTipoTerreno = claseTerreno.getTipoSuperficie();
        
        switch(tipo){
            case 1:                
                headerTextDialog="Clase de Tierra";
                columna = "clase_de_tierra";
                JsfUti.update("datos-terreno-tipo1");
                break;

            case 2:
                headerTextDialog="Cultivos Anuales y Semiperennes";
                columna = "cultivos_anuales_y_semiperennes";
                JsfUti.update("datos-terreno-tipo1");
                break;

            case 3:
                headerTextDialog="Plantaciones (Cultivos Perennes)";
                columna = "cultivos_perennes";
                JsfUti.update("datos-terreno-tipo1");
                break;

            case 4:
                headerTextDialog="Plantaciones Forestales";
                columna = "plantaciones_forestales";
                JsfUti.update("datos-terreno-tipo1");
                break;

            case 5:
                headerTextDialog="Semovientes";
                columna = "semovientes";
                JsfUti.update("datos-terreno-form2");
                break;

            case 6:
                headerTextDialog="Aves de Corral";
                columna = "aves_de_corral";
                JsfUti.update("datos-terreno-form2");
                break;
        }
    }
    
    public void ajustarOpcionesBusqueda(){
        opcionesBusqueda.setProvinciaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getProvinciaCod()), 2));
        opcionesBusqueda.setCantonCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getCantonCod()), 2));
        opcionesBusqueda.setParroquiaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getParroquiaCod()), 2));
        opcionesBusqueda.setZonaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getZonaCod()), 2));
        opcionesBusqueda.setSectorCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSectorCod()), 2));
        opcionesBusqueda.setManzanaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getManzanaCod()), 3));
        opcionesBusqueda.setSolarCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSolarCod()), 3));
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
    
    public void iniciarTerrenoClases(int tipo){
        try{
            tipoDialog = tipo;
            terrenoList = map.get(tipo);
            claseTerreno = new CatPredioRuralTerreno();
            switch(tipo){
                case 1:                
                    headerTextDialog="Clase de Tierra";
                    columna = "clase_de_tierra";
                    claseTipoTerreno = 5;
                    JsfUti.update("datos-terreno-tipo1");
                    break;

                case 2:
                    headerTextDialog="Cultivos Anuales y Semiperennes";
                    columna = "cultivos_anuales_y_semiperennes";
                    claseTipoTerreno = 1;
                    JsfUti.update("datos-terreno-tipo1");
                    break;

                case 3:
                    headerTextDialog="Plantaciones (Cultivos Perennes)";
                    columna = "cultivos_perennes";
                    claseTipoTerreno = 1;
                    JsfUti.update("datos-terreno-tipo1");
                    break;

                case 4:
                    headerTextDialog="Plantaciones Forestales";
                    columna = "plantaciones_forestales";
                    claseTipoTerreno = 1;
                    JsfUti.update("datos-terreno-tipo1");
                    break;

                case 5:
                    headerTextDialog="Semovientes";
                    columna = "semovientes";
                    claseTipoTerreno = 1;
                    JsfUti.update("datos-terreno-form2");
                    break;

                case 6:
                    headerTextDialog="Aves de Corral";
                    columna = "aves_de_corral";
                    claseTipoTerreno = 1;
                    JsfUti.update("datos-terreno-form2");
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void agregarClaseTerreno(){
        try{
            switch(tipoDialog){
                case 1:
                    claseTerreno.setClaseTierra(claseTipoTerreno);
                    break;
                case 2:
                    claseTerreno.setCultivosAnuales(claseTipoTerreno);
                    break;
                case 3:
                    claseTerreno.setCultivosPerennes(claseTipoTerreno);
                    break;
                case 4:
                    claseTerreno.setPlantacionesForestales(claseTipoTerreno);
                    break;
                case 5:
                    claseTerreno.setSemovientes(claseTipoTerreno);
                    break;
                case 6:
                    claseTerreno.setAvesDeCorral(claseTipoTerreno);
                    break;
            }
            claseTerreno.setIdTemporal(Short.valueOf(""+(((List)(this.map.get(tipoDialog))).size()+1)));
            ((List)(this.map.get(tipoDialog))).add(claseTerreno);
            JsfUti.update("idPanelListas");
            claseTerreno = new CatPredioRuralTerreno();
        }catch(Exception e){
            e.printStackTrace();
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
    public void buscarTerreno(Boolean isEditing) {

        try {
            this.isEditing = isEditing;
            this.ajustarOpcionesBusqueda();
            provinciaCod = opcionesBusqueda.getProvinciaCod();
            cantonCod = opcionesBusqueda.getCantonCod();
            parroquiaCod = opcionesBusqueda.getParroquiaCod();
            zonaCod = opcionesBusqueda.getZonaCod();
            sectorCod = opcionesBusqueda.getSectorCod();
            manzanaCod = opcionesBusqueda.getManzanaCod();
            solarCod = opcionesBusqueda.getSolarCod();
            
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
                            
                            iniciarListas();
                            if (terreno != null) {
                                nuevoTerreno = false;
                                predio = catastroService.obtenerPredioRustico(parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod);
                                if (predio != null && !isEditing) {
                                    JsfUtil.addInformationMessage(null, "Lote " + predio.getClaveCatastral() + " ya se encuentra registrado.");
                                    predio = null;
                                    terreno = null;
                                    return;
                                } else {
                                    if(predio == null && isEditing){
                                        JsfUtil.addInformationMessage(null, "Lote no encontrado.");
                                        predio = null;
                                        terreno = null;
                                        return;
                                    }
                                    if(predio != null && isEditing){
                                        map.put(1, predioService.getListaClaseTerrenoRural(1, predio));
                                        map.put(2, predioService.getListaClaseTerrenoRural(2, predio));
                                        map.put(3, predioService.getListaClaseTerrenoRural(3, predio));
                                        map.put(4, predioService.getListaClaseTerrenoRural(4, predio));
                                        map.put(5, predioService.getListaClaseTerrenoRural(5, predio));
                                        map.put(6, predioService.getListaClaseTerrenoRural(6, predio));
                                        this.escritura = predio.getEscrituras() == null || predio.getEscrituras().isEmpty() ? new Escritura() : predio.getEscrituras().get(0);
                                        
                                        if(this.escritura.getId() != null)
                                            this.escritura.setId(null);
                                        
                                        bloques = predio.getBloques();
                                        bloqueSeleccionado = bloques == null || bloques.isEmpty() ? null : bloques.get(0);
                                        obrasComplementarias = predio.getOtrosConstruccion();
                                        fotoPredio(predio.getId());
                                        relevamientoSeleccionado = predio.getRelevamiento();
                                        supervisorSeleccionado = predio.getSupervisor();
                                        validadorSeleccionado = predio.getValidador();
                                        digitadorSeleccionado = predio.getDigitador();
                                        supervisorDigtadorSeleccionado = predio.getSupervisorDigitacion();
                                        if(this.terreno.getLinEsteLongitud() != null && this.terreno.getLinEsteLongitud() == "")
                                            this.terreno.setLinEsteLongitud("0");
                                        if(this.terreno.getLinOesteLongitud() == null && this.terreno.getLinOesteLongitud()== "")
                                            this.terreno.setLinEsteLongitud("0");
                                        if(this.terreno.getLinNorteLongitud()== null && this.terreno.getLinNorteLongitud()== "")
                                            this.terreno.setLinNorteLongitud("0");
                                        if(this.terreno.getLinSurLongitud()== null && this.terreno.getLinSurLongitud()== "")
                                            this.terreno.setLinSurLongitud("0");
                                    }
                                    if(predio == null && !isEditing){
                                        predio = new Predio();
                                        predio.setTipoPredio(Boolean.FALSE);
                                        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod ;
                                        predio.setClaveCatastral(clave);
                                        predio.setBloques(new ArrayList<Bloque>());
                                        opcionesBusqueda.setEjecutandoAccion(true);
                                    }
                                    opcionesBusqueda.setEjecutandoAccion(true);
                                }
                            } else {
                                nuevoTerreno = true;
                                terreno = new Terreno();
                                terreno.setTerrenoPK(terrenoPK);
                                terreno.setManzana(manzana);
                                terreno.setPredios(new ArrayList<Predio>());
                                this.terreno.setLinEsteLongitud("0");
                                this.terreno.setLinOesteLongitud("0");
                                this.terreno.setLinNorteLongitud("0");
                                this.terreno.setLinSurLongitud("0");
                                predio = new Predio();
                                String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod;
                                predio.setClaveCatastral(clave);
                                predio.setBloques(new ArrayList<Bloque>());
                                opcionesBusqueda.setEjecutandoAccion(true);
                                
                            }
                        } else {
                            JsfUtil.addErrorMessage("Poligono con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + " no se encuentra registrada en el sistema.");
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
    
    public void iniciarListas(){
        map.put(1, new ArrayList());
        //((List)map.get(1)).add(new CatPredioRuralTerreno(Short.parseShort("2")));
        map.put(2, new ArrayList());
        //((List)map.get(2)).add(new CatPredioRuralTerreno(Short.parseShort("1")));
        map.put(3, new ArrayList());
        //((List)map.get(3)).add(new CatPredioRuralTerreno(Short.parseShort("1")));
        map.put(4, new ArrayList());
        //((List)map.get(4)).add(new CatPredioRuralTerreno(Short.parseShort("1")));
        map.put(5, new ArrayList());
        map.put(6, new ArrayList());
    }
    
    public List<CatPredioRuralTerreno> getListClases(Integer i){
        return (List<CatPredioRuralTerreno>)map.get(i);
    }

//<editor-fold defaultstate="collapsed" desc="buscarTerrenoDesarrollo">
    public void buscarTerrenoPH() {
        try {
            fichamadre = true;
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
    
    public String getTextoValorDiscreto(Integer idVariable, Short valor){
        return variablesService.getTextoVariableDiscretaByIdVariableAndValor(idVariable, valor, Util.ANIO_ACTUAL.shortValue());
    }
    
    public String actualizar(){
        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        
        try{
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(user);
                        
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest htservlet = (HttpServletRequest) fc.getExternalContext().getRequest();
            InetAddress add = InetAddress.getByName(htservlet.getRemoteAddr());
                       
            catastroService.updatePredioRuralComplete(predio, eliminados, terreno, escritura, documentos, fotosPredio, fotosPreview, map);
           
            JsfUtil.addInformationMessage(null, "Predio rural " + predio.getClaveCatastral() + " actualizado correctamente.");
            //JsfUti.executeJS("PF('wiz').loadStep('step-datos-clave',false)");
            this.init();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    
    public String crearPredio() {
        String claveCatastral = "";
        predio.setPropietarios(propietarios);
        predio.setOtrosConstruccion(obrasComplementarias);
        predio.setUsosSuelo(usosSueloTerreno);
        predio.setDigitador(digitadorSeleccionado);
        predio.setSupervisor(supervisorSeleccionado);
        predio.setSupervisorDigitacion(supervisorDigtadorSeleccionado);
        predio.setValidador(validadorSeleccionado);
        predio.setCartografo(cartografoSeleccionado);
        predio.setRelevamiento(relevamientoSeleccionado);
        predio.setTipoPredio(Boolean.FALSE);
        predio.setCodBloque("000");
        predio.setCodPhv("000");
        predio.setCodPhh("000");
        predio.setEstado(Short.parseShort("1"));
        if(escritura != null)
            escritura.setEstadoEscritura(Short.parseShort(estadoEscritura == null ? "0" : estadoEscritura+""));
        
        try {
            /*
            claveCatastral = terreno.getTerrenoPK().getCodParroquia() + "-" + terreno.getTerrenoPK().getCodZona() + "-" + terreno.getTerrenoPK().getCodSector() + "-" + terreno.getTerrenoPK().getCodManzana() + "-" + terreno.getTerrenoPK().getCodSolar() + "-" + predio.getCodBloque() + "-" + predio.getCodPhv() + "-" + predio.getCodPhh();
            predio.setClaveCatastral(claveCatastral);
            
            ManzanaPK manzanaPK = new ManzanaPK();
            manzanaPK.setCodProvincia(provinciaCod);
            manzanaPK.setCodCanton(cantonCod);
            manzanaPK.setCodParroquia(parroquiaCod);
            manzanaPK.setCodZona(zonaCod);
            manzanaPK.setCodSector(sectorCod);
            manzanaPK.setCodManzana(manzanaCod);
            
            TerrenoPK terrenoPK = new TerrenoPK(manzanaPK, solarCod);
            terreno = catastroService.obtenerTerreno(terrenoPK);
            
            if (terreno != null)
                nuevoTerreno = false;
            else
                nuevoTerreno = true;*/
            
            catastroService.crearPredioRustico(nuevoTerreno, terreno, predio, fotosPredio, escritura, documentos, map);
            /*
            Manzana manzanaEdit = manzanaService.getManzana(terreno.getTerrenoPK());
            String cod_solar = terreno.getTerrenoPK().getCodSolar();
            manzanaEdit.setSecuencia(Integer.parseInt("" + cod_solar));

            manzanaService.edit(manzanaEdit);*/


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
            return "pretty:ingresar-predio-rural";
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
        fotosBloquePreview = new ArrayList();
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionBloque">
    public void inicializarActualizacionBloque() {
        editandoBloque = true;
        creandoBloque = false;
        fotoBloque(bloqueSeleccionado.getId());
        //fotosBloque = bloqueSeleccionado.getFotosBloque();
        pisos = bloqueSeleccionado.getPisos();
        pisoSeleccionado = pisos != null && !pisos.isEmpty() ? pisos.get(0) : new Piso();
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="crearBloque">
    @Transactional
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
                if(bloqueSeleccionado.getId() != null)
                    bloqueSeleccionado.setImagesPreview(fotosBloquePreview);
                /*for (int i = 0; i < usosSuelo.size(); i++) {
                    usosSuelo.get(i).setBloque(bloqueSeleccionado);
                }
                bloqueSeleccionado.setUsosSuelo(usosSuelo);*/
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
    
    public void mostrarMsj(){
        System.out.println("Entra");
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
    @Transactional
    public void eliminarBloque() {
        if (bloqueSeleccionado != null) {
            for (int i = 0; i < bloques.size(); i++) {
                if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {                    
                    Bloque b = bloques.remove(i);
                    if(b.getId() != null){
                        this.aclServices.getEm().remove(this.aclServices.getEm().find(Bloque.class, b.getId()));
                        auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "ELIMINACIÓN DE BLOQUE DE PREDIO RURAL - PREDIO: "+this.predio.getClaveCatastral() + " - BLOQUE: " +bloqueSeleccionado.getNumeroBloque());
                    }
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
        
        this.aclServices.getEm().flush();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarBloque">
    public void cambiarBloque() { 
        bloqueSeleccionado = bloques.get(bloqueSeleccionadoIndex);
        pisos = bloqueSeleccionado.getPisos();
        pisoSeleccionadoIndex = -1;
        this.fotoBloque(bloqueSeleccionado.getId());
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
    @Transactional
    public void eliminarPiso() {
        if (pisoSeleccionado != null) {
            for (int i = 0; i < pisos.size(); i++) {
                if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {
                    Piso p = pisos.remove(i);
                    if(p.getId() != null)
                        //p.setEstadoEliminacion(Boolean.TRUE);
                        this.aclServices.getEm().remove(this.aclServices.getEm().merge(p));
                        auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "ELIMINACIÓN DE PISO DE PREDIO RURAL - PREDIO: "+this.predio.getClaveCatastral() + " - PISO: " +pisoSeleccionado.getNumeroPiso());
                        //this.aclServices.getEm().flush();
                        //bloqueSeleccionado = (Bloque)aclServices.find("Select r FROM Bloque r WHERE r.id = :id", new String[]{"id"}, new Object[]{bloqueSeleccionado.getId()});
                        //bloqueSeleccionado.setPisos(pisos);
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
        bloqueSeleccionado.setPisos(pisos);
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
    
    private void fotoBloque(int idBloque) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Bloque b = bloqueService.findByNamedQuery("Bloque.findByID", idBloque).get(0);

        String foto;
        String pathToPhoto = "";
        //List<>
        fotosBloquePreview = new ArrayList<>();

        if (!b.getImages().isEmpty()) {

            String uploadDir = Util.directorio_imagenes;

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";

            for (BloqueImage img : b.getImages()) {

                foto = img.getRuta();
                ArchivoAux aux = new ArchivoAux();
                aux.setIdArchivo(img.getId());

                try {
                    if (existeArchivo(uploadDir + "/" + foto)) {
                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);

                        try {
                            IOUtils.copy(inFile, outFile);
                            inFile.close();
                            outFile.close();
                        } catch (IOException ex) {

                        }
                    }

                    if (existeArchivo(tempDir + "/" + foto)) {
                        pathToPhoto = urlBase + "/fotos/" + foto;
                        aux.setRuta(pathToPhoto);
                        if (!fotosBloquePreview.contains(aux)) {
                            fotosBloquePreview.add(aux);
                        }
                    } else {
                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
                        aux.setRuta(pathToPhoto);
                        if (!fotosBloquePreview.contains(aux)) {
                            fotosBloquePreview.add(aux);
                        }
                    }

                } catch (FileNotFoundException ex) {

                }
            }

        }

    }
    
    private void fotoPredio(int idPredio) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Predio p = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);

        String foto;
        String pathToPhoto = "";
        fotosPreview = new ArrayList<>();

        if (!p.getImages().isEmpty()) {

            String uploadDir = Util.directorio_imagenes;

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
            for (PredioImage img : p.getImages()) {

                foto = img.getRuta();
                ArchivoAux aux = new ArchivoAux();
                aux.setIdArchivo(img.getId());

                try {
                    if (existeArchivo(uploadDir + "/" + foto)) {
                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);

                        try {
                            IOUtils.copy(inFile, outFile);
                            inFile.close();
                            outFile.close();
                        } catch (IOException ex) {

                        }
                    }

                    if (existeArchivo(tempDir + "/" + foto)) {
                        pathToPhoto = urlBase + "/fotos/" + foto;
                        aux.setRuta(pathToPhoto);
                        if (!fotosPreview.contains(aux)) {
                            fotosPreview.add(aux);
                        }
                    } else {
                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
                        aux.setRuta(pathToPhoto);
                        if (!fotosPreview.contains(aux)) {
                            fotosPreview.add(aux);
                        }
                    }

                } catch (FileNotFoundException ex) {

                }
            }

        }

    }
    
    public void eliminarFotoActual(Integer id) {

        for (ArchivoAux au : fotosPreview) {
            if (Objects.equals(au.getIdArchivo(), id)) {
                fotosPreview.remove(au);
            }
        }

    }

    private boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
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
    
    public void eliminarFotoActualBloque(Integer id) {

        for (ArchivoAux au : fotosBloquePreview) {
            if (Objects.equals(au.getIdArchivo(), id)) {
                fotosBloquePreview.remove(au);
            }
        }
    }

    public String getCodigoCatastral() {

        String clave = parroquiaCod +"-"+ zonaCod +"-"+ sectorCod +"-"+ manzanaCod +"-"+ solarCod;
        return clave;
    }

    public void eliminarPropietario() {

        if (propietarios.contains(propietarioSeleccionado)) {
            if(eliminados == null)
                eliminados = new ArrayList();
            eliminados.add(propietarioSeleccionado);
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
        columna = "muros_r";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
    }

    public void initEditObra() {
        if (obraSeleccionada.getVariable() == 165) {
            this.tipoConstruccion = (short) 3;
        }
        if (obraSeleccionada.getVariable() == 166) {
            this.tipoConstruccion = (short) 4;
        }
        if (obraSeleccionada.getVariable() == 167) {
            this.tipoConstruccion = (short) 1;
        }
        if (obraSeleccionada.getVariable() == 168) {
            this.tipoConstruccion = (short) 2;
        }
        if (obraSeleccionada.getVariable() == 164) {
            this.tipoConstruccion = (short) 5;
        }

        switch (tipoConstruccion) {
            case 1:
            case 2:
                materialLabel = "Tipo material:";
                if (tipoConstruccion == 1) {
                    columna = "muros_r";
                } else {
                    columna = "cerramientos_r";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias_e_instalaciones";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 4:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales_r";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Otras Mejoras de Obra:";
                columna = "otras_mejoras_obra";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
        }

    }

    public void cancelarCrearObra() {
        if (!obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        }

    }

    @Transactional
    public void eliminarObra() {
        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                OtrosTipoConstruccion t = obrasComplementarias.remove(i);
                if(t.getId() != null){
                    this.aclServices.getEm().remove(this.aclServices.getEm().merge(t));                
                    auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "ELIMINACIÓN DE OBRA COMPLEMENTARIA DE PREDIO RURAL - PREDIO: "+this.predio.getClaveCatastral() + " - OBRA COMPLEMENTARIA: " +t.getNombre());
                }
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
        obraSeleccionada.setNombre(columna);
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
                    columna = "muros_r";
                } else {
                    columna = "cerramientos_r";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias_e_instalaciones";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 4:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales_r";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Otras mejoras de obra:";
                columna = "otras_mejoras_obra";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
        }
        onkeypressevent = "material:coef_avaluos-"+columna+"-input";
        obraSeleccionada.setTipoConstruccion((short) 1);
        tipoObraSeleccionada = 1;
    }

    public void changeTipoObra() {
        
        switch (tipoConstruccion) {
            case 1:
            case 2:
                materialLabel = "Tipo material:";
                if (tipoConstruccion == 1) {
                    columna = "muros_r";
                } else {
                    columna = "cerramientos_r";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias_e_instalaciones";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 4:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales_r";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Otras mejoras de obra:";
                columna = "otras_mejoras_obra";
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
        Object oldValue = event.getOldValue();
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
            nuevoPosesionario.setIdentificacion(nuevoPosesionario.getIdentificacion() == null || nuevoPosesionario.getIdentificacion() == "" ? "0000000000" : nuevoPosesionario.getIdentificacion());
            nuevoPosesionario.setNombre(nuevoPosesionario.getNombre() == null ? "" : nuevoPosesionario.getNombre().toUpperCase());
            nuevoPosesionario.setApellidoPaterno(nuevoPosesionario.getApellidoPaterno() == null ? "" : nuevoPosesionario.getApellidoPaterno().toUpperCase());
            nuevoPosesionario.setApellidoMaterno(nuevoPosesionario.getApellidoMaterno() == null ? "": nuevoPosesionario.getApellidoMaterno().toUpperCase());
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

    public List<ValorDiscreto> getValoresUsoPredio() {
        return valoresUsoPredio;
    }

    public void setValoresUsoPredio(List<ValorDiscreto> valoresUsoPredio) {
        this.valoresUsoPredio = valoresUsoPredio;
    }

    public List<ValorDiscreto> getValoresUsoPredioSelected() {
        return valoresUsoPredioSelected;
    }

    public void setValoresUsoPredioSelected(List<ValorDiscreto> valoresUsoPredioSelected) {
        this.valoresUsoPredioSelected = valoresUsoPredioSelected;
    }

    public Short getTieneTituloPropiedad() {
        return tieneTituloPropiedad;
    }

    public void setTieneTituloPropiedad(Short tieneTituloPropiedad) {
        this.tieneTituloPropiedad = tieneTituloPropiedad;
    }

    public Short getEstadoEscritura() {
        return estadoEscritura;
    }

    public void setEstadoEscritura(Short estadoEscritura) {
        this.estadoEscritura = estadoEscritura;
    }

    public Integer getTipoJuridico() {
        return tipoJuridico;
    }

    public void setTipoJuridico(Integer tipoJuridico) {
        this.tipoJuridico = tipoJuridico;
    }

    public int getParroquiaSel() {
        return parroquiaSel;
    }

    public void setParroquiaSel(int parroquiaSel) {
        this.parroquiaSel = parroquiaSel;
    }

    public String getHeaderTextDialog() {
        return headerTextDialog;
    }

    public void setHeaderTextDialog(String headerTextDialog) {
        this.headerTextDialog = headerTextDialog;
    }

    public Map<Integer, List> getMap() {
        return map;
    }

    public void setMap(Map<Integer, List> map) {
        this.map = map;
    }

    public CatPredioRuralTerreno getClaseTerreno() {
        return claseTerreno;
    }

    public void setClaseTerreno(CatPredioRuralTerreno claseTerreno) {
        this.claseTerreno = claseTerreno;
    }

    public short getClaseTipoTerreno() {
        return claseTipoTerreno;
    }

    public void setClaseTipoTerreno(short claseTipoTerreno) {
        this.claseTipoTerreno = claseTipoTerreno;
    }

    public CatPredioRuralTerreno getTerrenoClaseSel() {
        return terrenoClaseSel;
    }

    public void setTerrenoClaseSel(CatPredioRuralTerreno terrenoClaseSel) {
        this.terrenoClaseSel = terrenoClaseSel;
    }

    public boolean isTieneInfo() {
        return tieneInfo;
    }

    public void setTieneInfo(boolean tieneInfo) {
        this.tieneInfo = tieneInfo;
    }

    public boolean isIsEditing() {
        return isEditing;
    }

    public void setIsEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    public List<ArchivoAux> getFotosPreview() {
        return fotosPreview;
    }

    public void setFotosPreview(List<ArchivoAux> fotosPreview) {
        this.fotosPreview = fotosPreview;
    }

    public List<ArchivoAux> getFotosBloquePreview() {
        return fotosBloquePreview;
    }

    public void setFotosBloquePreview(List<ArchivoAux> fotosBloquePreview) {
        this.fotosBloquePreview = fotosBloquePreview;
    }
    
}
