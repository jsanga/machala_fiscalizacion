/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.admin.service.IntegranteService;
import com.dadoco.cat.model.ArchivoEscritura;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.CatPredioRuralTerreno;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Integrante;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.service.BloqueService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.customFilters.LazyDataModelAdvance;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
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
 * @author JoaoIsrael
 */
@Named(value = "updatePredioRFullView")
@ViewScoped
public class ActualizarPredioRuralCompletoController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActualizarPredioRuralCompletoController.class);
    
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private PredioService predioService;
    @EJB
    private SearchService searchService;
    @EJB
    private BloqueService bloqueService;
    @EJB
    private IntegranteService integranteService;
    @EJB
    private ContribuyenteService contribuyenteService;
    @EJB
    private ParroquiaService parroquiaService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private ManzanaService manzanaService;

    private LazyDataModelAdvance<Contribuyente> lazyData;

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
    private String headerTextDialog;
    private Short estadoEscritura, claseTipoTerreno;
    private int tipoDialog;

    private Terreno terreno;

    private Predio predioSeleccionado;
    private Predio predio;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private List<UploadFile> fotos;
    private List<ArchivoAux> fotosPreview;

    private List<Contribuyente> propietarios;
    private List<Contribuyente> propietarioNuevos;

    private Contribuyente propietarioSeleccionado;
    private Contribuyente nuevoPropietario;
    private Contribuyente nuevoPosesionario;
    private Parroquia parroquia;
    private Zona zona;
    private Sector sector;
    private Manzana manzana;

    private List<SelectItem> tiposPropietarios;
    private String parametroBusqueda;

    private Escritura escritura;
    private List<UploadFile> documentos;

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

    private int secuenciaBloque;
    private int secuenciaPiso;

    private List<Bloque> bloquesEliminar;
    private List<Piso> pisosEliminar;
    private List<UploadFile> fotosBloque;
    private List<ArchivoAux> fotosBloquePreview;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloTerreno;
    private List<UsoSuelo> usosSueloEliminar;
    private List<UsoSuelo> usosSueloEliminarTerreno;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;
    private List<ValorDiscreto> valorDiscretos;

    private List<OtrosTipoConstruccion> obrasComplementarias;
    private List<OtrosTipoConstruccion> obrasComplementariasEliminar;
    private OtrosTipoConstruccion obraSeleccionada;
    private int idObra;

    private short tipoObraSeleccionada;
    private short tipoConstruccion;
    private String columna;
    private String materialLabel;
    private String unidadLabel;
    private String onkeypressevent;
    private ValorDiscreto valorDiscretoObra;

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
    private short anioActual;
    private Map<Integer, List> map;
    private List<CatPredioRuralTerreno> terrenoList;
    private CatPredioRuralTerreno claseTerreno, terrenoClaseSel;

    private boolean PH;

    private short codeUsoDefault;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {
        map = new HashMap();
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);

        nuevoPosesionario = new Contribuyente();
        setLazyData(new LazyDataModelAdvance<>(contribuyenteService));
        lazyData.setClasePK(Integer.class);

        predioSeleccionado = new Predio();
        terreno = new Terreno();
        fotos = new ArrayList<>();
        fotosPreview = new ArrayList<>();
        fotosBloque = new ArrayList<>();
        fotosBloquePreview = new ArrayList<>();
        propietarios = new ArrayList<>();
        escritura = new Escritura();
        documentos = new ArrayList<>();
        bloques = new ArrayList<>();
        pisos = new ArrayList<>();
        bloquesEliminar = new ArrayList<>();
        pisosEliminar = new ArrayList<>();
        creandoBloque = false;
        editandoBloque = false;

        usoSeleccionado = new UsoSuelo();
        usosSuelo = new ArrayList<>();
        usosSueloTerreno = new ArrayList<>();
        valorDiscretos = getValorDiscretos();
        valorDiscreto = new ValorDiscreto();
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usosSueloEliminar = new ArrayList<>();
        usosSueloEliminarTerreno = new ArrayList<>();
        usosSueloTerreno = new ArrayList<>();
        usosSueloEliminarTerreno = new ArrayList<>();

        usoSeleccionado = new UsoSuelo();
        usosSuelo = new ArrayList<>();
        codeUsoDefault = (short) 12;
        valorDiscreto = variablesService.obtenerValor(33, 53, codeUsoDefault, anioActual);
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usosSueloEliminar = new ArrayList<>();

        obrasComplementarias = new ArrayList<>();
        obrasComplementariasEliminar = new ArrayList<>();
        obraSeleccionada = new OtrosTipoConstruccion();
        idObra = catastroService.numeroProximaObra();
        tipoConstruccion = 1;
        tipoObraSeleccionada = 1;
        materialLabel = "Tipo material:";
        columna = "muros";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
        
        iniciarListas();
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
//</editor-fold>
    public void eliminarClaseTerreno(int tipo){
        try{
            if(terrenoClaseSel == null){
                JsfUti.messageInfo(null, "Info", "Debe seleccionar un ítem antes de eliminarlo");
                return;
            }
            ((List)(this.map.get(tipo))).remove(terrenoClaseSel);
            int i = 1;
            
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

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public List<ValorDiscreto> getValorDiscretos() {

        valorDiscretos = new ArrayList<>();

        for (int i = 33; i < 54; i++) {
            List<ValorDiscreto> valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
            if (valores != null) {
                valorDiscretos.addAll(valores);
            }
        }

        return valorDiscretos;
//        
//        valorDiscretos = new ArrayList<>();
//        List<ValorDiscreto> valores = new ArrayList<>();
//        switch (terreno.getUsoSuelo()) {
//
//            case 0: {
//                break;
//            }
//            case 19: {
//                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//            case 20: {
//                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(36, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//            case 21: {
//                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(36, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//            default: {
//                valores = variablesService.obtenerValoresPorCodVariable(valorById(terreno.getUsoSuelo()), anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//        }
//
//        return valorDiscretos;
    }

    public void setValorDiscretos(List<ValorDiscreto> valorDiscretos) {
        this.valorDiscretos = valorDiscretos;
    }

    public short getCodeUsoDefault() {
        return codeUsoDefault;
    }

    public void setCodeUsoDefault(short codeUsoDefault) {
        this.codeUsoDefault = codeUsoDefault;
    }

    public List<UsoSuelo> getUsosSueloTerreno() {
        return usosSueloTerreno;
    }

    public void setUsosSueloTerreno(List<UsoSuelo> usosSueloTerreno) {
        this.usosSueloTerreno = usosSueloTerreno;
    }

    public List<UsoSuelo> getUsosSueloEliminarTerreno() {
        return usosSueloEliminarTerreno;
    }

    public void setUsosSueloEliminarTerreno(List<UsoSuelo> usosSueloEliminarTerreno) {
        this.usosSueloEliminarTerreno = usosSueloEliminarTerreno;
    }

    public List<UploadFile> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<UploadFile> documentos) {
        this.documentos = documentos;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public boolean isPH() {
        return PH;
    }

    public void setPH(boolean PH) {
        this.PH = PH;
    }

    public List<Contribuyente> getPropietarioNuevos() {
        return propietarioNuevos;
    }

    public void setPropietarioNuevos(List<Contribuyente> propietarioNuevos) {
        this.propietarioNuevos = propietarioNuevos;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public Contribuyente getNuevoPropietario() {
        return nuevoPropietario;
    }

    public void setNuevoPropietario(Contribuyente nuevoPropietario) {
        this.nuevoPropietario = nuevoPropietario;
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

    public List<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public Bloque getBloqueSeleccionado() {
        return bloqueSeleccionado;
    }

    public void setBloqueSeleccionado(Bloque bloqueSeleccionado) {
        this.bloqueSeleccionado = bloqueSeleccionado;
    }

    public int getBloqueSeleccionadoIndex() {
        return bloqueSeleccionadoIndex;
    }

    public void setBloqueSeleccionadoIndex(int bloqueSeleccionadoIndex) {
        this.bloqueSeleccionadoIndex = bloqueSeleccionadoIndex;
    }

    public boolean isCreandoBloque() {
        return creandoBloque;
    }

    public void setCreandoBloque(boolean creandoBloque) {
        this.creandoBloque = creandoBloque;
    }

    public boolean isEditandoBloque() {
        return editandoBloque;
    }

    public void setEditandoBloque(boolean editandoBloque) {
        this.editandoBloque = editandoBloque;
    }

    public List<Piso> getPisos() {
        return pisos;
    }

    public void setPisos(List<Piso> pisos) {
        this.pisos = pisos;
    }

    public Piso getPisoSeleccionado() {
        return pisoSeleccionado;
    }

    public void setPisoSeleccionado(Piso pisoSeleccionado) {
        this.pisoSeleccionado = pisoSeleccionado;
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

    public List<Bloque> getBloquesEliminar() {
        return bloquesEliminar;
    }

    public void setBloquesEliminar(List<Bloque> bloquesEliminar) {
        this.bloquesEliminar = bloquesEliminar;
    }

    public List<Piso> getPisosEliminar() {
        return pisosEliminar;
    }

    public void setPisosEliminar(List<Piso> pisosEliminar) {
        this.pisosEliminar = pisosEliminar;
    }

    public List<UploadFile> getFotosBloque() {
        return fotosBloque;
    }

    public void setFotosBloque(List<UploadFile> fotosBloque) {
        this.fotosBloque = fotosBloque;
    }

    public List<ArchivoAux> getFotosBloquePreview() {
        return fotosBloquePreview;
    }

    public void setFotosBloquePreview(List<ArchivoAux> fotosBloquePreview) {
        this.fotosBloquePreview = fotosBloquePreview;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public List<UsoSuelo> getUsosSueloEliminar() {
        return usosSueloEliminar;
    }

    public void setUsosSueloEliminar(List<UsoSuelo> usosSueloEliminar) {
        this.usosSueloEliminar = usosSueloEliminar;
    }

    public UsoSuelo getUsoSeleccionado() {
        return usoSeleccionado;
    }

    public void setUsoSeleccionado(UsoSuelo usoSeleccionado) {
        this.usoSeleccionado = usoSeleccionado;
    }

    public ValorDiscreto getValorDiscreto() {
        return valorDiscreto;
    }

    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
    }

    public List<OtrosTipoConstruccion> getObrasComplementarias() {
        return obrasComplementarias;
    }

    public void setObrasComplementarias(List<OtrosTipoConstruccion> obrasComplementarias) {
        this.obrasComplementarias = obrasComplementarias;
    }

    public List<OtrosTipoConstruccion> getObrasComplementariasEliminar() {
        return obrasComplementariasEliminar;
    }

    public void setObrasComplementariasEliminar(List<OtrosTipoConstruccion> obrasComplementariasEliminar) {
        this.obrasComplementariasEliminar = obrasComplementariasEliminar;
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

    public short getTipoObraSeleccionada() {
        return tipoObraSeleccionada;
    }

    public void setTipoObraSeleccionada(short tipoObraSeleccionada) {
        this.tipoObraSeleccionada = tipoObraSeleccionada;
    }

    public short getTipoConstruccion() {
        return tipoConstruccion;
    }

    public void setTipoConstruccion(short tipoConstruccion) {
        this.tipoConstruccion = tipoConstruccion;
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

    public String getUnidadLabel() {
        return unidadLabel;
    }

    public void setUnidadLabel(String unidadLabel) {
        this.unidadLabel = unidadLabel;
    }

    public String getOnkeypressevent() {
        return onkeypressevent;
    }

    public void setOnkeypressevent(String onkeypressevent) {
        this.onkeypressevent = onkeypressevent;
    }

    public ValorDiscreto getValorDiscretoObra() {
        return valorDiscretoObra;
    }

    public void setValorDiscretoObra(ValorDiscreto valorDiscretoObra) {
        this.valorDiscretoObra = valorDiscretoObra;
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

    public Integrante getCartografoSeleccionado() {
        return cartografoSeleccionado;
    }

    public void setCartografoSeleccionado(Integrante cartografoSeleccionado) {
        this.cartografoSeleccionado = cartografoSeleccionado;
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Contribuyente> propietarios) {
        this.propietarios = propietarios;
    }

    public List<UploadFile> getFotos() {
        return fotos;
    }

    public void setFotos(List<UploadFile> fotos) {
        this.fotos = fotos;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
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

    public Parroquia getParroquia() {
        return parroquia;
    }

    public void setParroquia(Parroquia parroquia) {
        this.parroquia = parroquia;
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

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public List<ArchivoAux> getFotosPreview() {
        return fotosPreview;
    }

    public void setFotosPreview(List<ArchivoAux> fotosPreview) {
        this.fotosPreview = fotosPreview;
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

//</editor-fold>
    public void buscar() {

        try {
            provinciaCod = opcionesBusqueda.getProvinciaCod();
            cantonCod = opcionesBusqueda.getCantonCod();
            parroquiaCod = opcionesBusqueda.getParroquiaCod();
            zonaCod = opcionesBusqueda.getZonaCod();
            sectorCod = opcionesBusqueda.getSectorCod();
            manzanaCod = opcionesBusqueda.getManzanaCod();
            solarCod = opcionesBusqueda.getSolarCod();

            predioSeleccionado = catastroService.obtenerPredioRustico(parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod);

            if (predioSeleccionado == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod));
            } else {
                terreno = predioSeleccionado.getTerreno();
                fotoPredio(predioSeleccionado.getId());

                propietarios = predioSeleccionado.getPropietarios();
                if (!predioSeleccionado.getPropietarios().isEmpty()) {
                    for (int i = 0; i < predioSeleccionado.getPropietarios().size(); i++) {
                        predioSeleccionado.getPropietarios().get(i).obtenerEstatus(predioSeleccionado.getId());
                    }
                }
                if (predioSeleccionado.getEscrituras().isEmpty()) {
                    predioSeleccionado.setEscrituras(new ArrayList<Escritura>());
                    escritura = new Escritura();
                    escritura.setAdjuntos(new ArrayList<ArchivoEscritura>());
                } else {
                    escritura = predioSeleccionado.getEscrituras().get(predioSeleccionado.getEscrituras().size() - 1);
                }

                obrasComplementarias = predioSeleccionado.getOtrosConstruccion();
                if (!obrasComplementarias.isEmpty()) {
                    obraSeleccionada = obrasComplementarias.get(0);
                }
                secuenciaBloque = 1;

                bloques = predioSeleccionado.getBloques();
                if (!bloques.isEmpty()) {
                    bloqueSeleccionadoIndex = 0;
                    bloqueSeleccionado = bloques.get(0);
                    fotoBloque(bloqueSeleccionado.getId());
                    usosSuelo = bloqueSeleccionado.getUsosSuelo();
                    if (!usosSuelo.isEmpty()) {
                        usoSeleccionado = usosSuelo.get(0);
                    } else {
                        usosSuelo = new ArrayList<>();
                        usoSeleccionado = new UsoSuelo();
                    }
                    secuenciaBloque = catastroService.nextBloque(predioSeleccionado.getId());

                    pisos = bloqueSeleccionado.getPisos();
                    if (!pisos.isEmpty()) {
                        pisoSeleccionadoIndex = 0;
                        pisoSeleccionado = pisos.get(0);
                        secuenciaPiso = catastroService.nextPiso(bloqueSeleccionado.getId());
                    }
                }

                digitadorSeleccionado = predioSeleccionado.getDigitador();
                relevamientoSeleccionado = predioSeleccionado.getRelevamiento();
                supervisorDigtadorSeleccionado = predioSeleccionado.getSupervisorDigitacion();
                supervisorSeleccionado = predioSeleccionado.getSupervisor();
                validadorSeleccionado = predioSeleccionado.getValidador();
                cartografoSeleccionado = predioSeleccionado.getCartografo();

                usosSueloTerreno = predioSeleccionado.getUsosSuelo();

                if (!usosSueloTerreno.isEmpty()) {
                    usoSeleccionado = usosSueloTerreno.get(0);
                } else {
                    usosSueloTerreno = new ArrayList<>();
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    public String actualizar() {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        try {
            predioSeleccionado.getEscrituras().add(escritura);

            predioSeleccionado.setFechaModificacion(new Date());
            predioSeleccionado.setUsuarioModifica(user);

            predioSeleccionado.setRelevamiento(relevamientoSeleccionado);
            predioSeleccionado.setSupervisor(supervisorSeleccionado);
            predioSeleccionado.setSupervisorDigitacion(supervisorDigtadorSeleccionado);
            predioSeleccionado.setCartografo(cartografoSeleccionado);
            predioSeleccionado.setValidador(validadorSeleccionado);
            predioSeleccionado.setDigitador(digitadorSeleccionado);

            predioSeleccionado.setOtrosConstruccion(obrasComplementarias);
            predioSeleccionado.setUsosSuelo(usosSueloTerreno);            

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest htservlet = (HttpServletRequest) fc.getExternalContext().getRequest();
            InetAddress add = InetAddress.getByName(htservlet.getRemoteAddr());
            
            Predio predioSelect = catastroService.obtenerPredio(predioSeleccionado.getTerreno().getTerrenoPK().getCodProvincia(),
                    predioSeleccionado.getTerreno().getTerrenoPK().getCodCanton(),
                    predioSeleccionado.getTerreno().getTerrenoPK().getCodParroquia(),
                    predioSeleccionado.getTerreno().getTerrenoPK().getCodZona(),
                    predioSeleccionado.getTerreno().getTerrenoPK().getCodSector(),
                    predioSeleccionado.getTerreno().getTerrenoPK().getCodManzana(),
                    predioSeleccionado.getTerreno().getTerrenoPK().getCodSolar(),
                    predioSeleccionado.getCodBloque(),
                    predioSeleccionado.getCodPhv(),
                    predioSeleccionado.getCodPhh());

            //registroHistorico(predioSelect, predioSelect.getTerreno(), auditoria, false);

            catastroService.updatePredioComplete(predioSeleccionado, terreno, escritura, documentos, bloquesEliminar, pisosEliminar, usosSueloEliminar, obrasComplementariasEliminar, fotos, fotosPreview, usosSueloEliminarTerreno);

            //registroHistorico(predioSeleccionado, terreno, auditoria, true);
            JsfUtil.addInformationMessage(null, "Predio " + predioSeleccionado.getClaveCatastral() + " actualizado correctamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

        opcionesBusqueda.setEjecutandoAccion(false);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";

    }

    public String cancelar() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String onFlowProcess(FlowEvent event) {

        obraSeleccionada = new OtrosTipoConstruccion();
        usoSeleccionado = new UsoSuelo();
//        if (terreno.getUsoSuelo() != 0) {
//
//            int id = valorById(terreno.getUsoSuelo());
//            codeUsoDefault = valorDefaultById(id);

        valorDiscreto = variablesService.obtenerValorbyVariableValor(33, codeUsoDefault, anioActual);
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
//        }

        return event.getNewStep();

    }

    public void handleFilePhotoPredioUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predioSeleccionado.getClaveCatastral() + "_FP_";
        Util.copyFile(uploadedFile, fileName, fotos, "fotos", (short) 1);
    }

    public void eliminarFoto(int index) {
        Util.deleteFile(index, fotos);
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

    public void inicializarBusquedaContribuyente() {
        nuevoPropietario = null;
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

    public void eliminarPropietario() {

        if (propietarios.contains(propietarioSeleccionado)) {
            propietarios.remove(propietarioSeleccionado);
        }
        if (!propietarios.isEmpty()) {
            propietarioSeleccionado = propietarios.get(0);
        }
    }

    public void onRowSelectPropietario(SelectEvent event) {
        propietarioSeleccionado = ((Contribuyente) event.getObject());

    }

    public void onRowUnselectPropietario(UnselectEvent event) {
        propietarioSeleccionado = ((Contribuyente) event.getObject());
    }

    public void onCellEditPropietario(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }

    public void handleFileDocumentoUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predioSeleccionado.getClaveCatastral() + "_EP_";
        Util.copyFile(uploadedFile, fileName, documentos, "documentos", (short) 2);
    }

    public void eliminarDocumento(int index) {
        Util.deleteFile(index, documentos);
    }

    public void initNuevoUso() {
        usoSeleccionado = new UsoSuelo();
//        int id = valorById(terreno.getUsoSuelo());
//        codeUsoDefault = valorDefaultById(id);

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
            if (usoSeleccionado.getId() != null) {
                if (!usosSueloEliminar.contains(usoSeleccionado)) {
                    usosSueloEliminar.add(usoSeleccionado);
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
            if (usoSeleccionado.getId() != null) {
                if (!usosSueloEliminarTerreno.contains(usoSeleccionado)) {
                    usosSueloEliminarTerreno.add(usoSeleccionado);
                }
            }
            if (!usosSueloTerreno.isEmpty()) {
                usoSeleccionado = usosSueloTerreno.get(0);
            }
        }

    }

    public void crearNuevoUso(boolean bloque) {

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());

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
            }

            if (!usosSuelo.isEmpty()) {
                usoSeleccionado = usosSuelo.get(0);
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

    public BloqueService getBloqueService() {
        return bloqueService;
    }

    public void setBloqueService(BloqueService bloqueService) {
        this.bloqueService = bloqueService;
    }

    public short getAnioActual() {
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
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

        //for (int i = 33; i <= 52; i++) {
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

    public void changeUso() {

//        ValorDiscreto aux = variablesService.obtenerValorbyVariableValor(valorDiscreto.getValorDiscretoPK().getIdVariable(), usoSeleccionado.getCod(), anioActual);
//        if (aux != null) {
//            valorDiscreto = aux;
//        } else {
//            
//            int id = valorById(terreno.getUsoSuelo());
//           
//            short value = valorDefaultById(id);
//            valorDiscreto = variablesService.obtenerValorbyVariableValor(id, value, anioActual);
//           codeUsoDefault = value;
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
        short valorActualTipo = tipoConstruccion;
        short valorActualTipoConstruccion = obraSeleccionada.getTipoConstruccion();
        changeTipoConstruccion();
        obraSeleccionada.setTipoConstruccion(valorActualTipoConstruccion);
        tipoConstruccion = valorActualTipo;

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

        if (obraSeleccionada.getId() != null) {
            if (!obrasComplementariasEliminar.contains(obraSeleccionada)) {
                obrasComplementariasEliminar.add(obraSeleccionada);
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
            obraSeleccionada.setPredio(predioSeleccionado);
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
        bloqueSeleccionado.setNumeroBloque(numeroBloque());
        pisos = new ArrayList<>();
        fotosBloquePreview = bloqueSeleccionado.getImagesPreview();

        fotosBloque = new ArrayList<>();
        usosSuelo.clear();
        usoSeleccionado = new UsoSuelo();
        creandoBloque = true;
        editandoBloque = false;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionBloque">
    public void inicializarActualizacionBloque() {
        editandoBloque = true;
        creandoBloque = true;
        fotosBloque.clear();
//        if (bloqueSeleccionado.isEditado()) {
//            fotosBloquePreview = bloqueSeleccionado.getImagesPreview();
//        }
        if (bloqueSeleccionado.getId() != null) {
//            if (bloqueSeleccionado.isEditado()) {
//                fotosBloquePreview = bloqueSeleccionado.getImagesPreview();
//            } else {
            fotoBloque(bloqueSeleccionado.getId());
//            }
        }
        pisos = bloqueSeleccionado.getPisos();
        if (pisos!=null && !pisos.isEmpty()) {
            pisoSeleccionadoIndex = 0;
            pisoSeleccionado = pisos.get(pisoSeleccionadoIndex);
        }
        usosSuelo = bloqueSeleccionado.getUsosSuelo();
        if (usosSuelo!=null && !usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        } else {
            usoSeleccionado = new UsoSuelo();
        }
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

                bloqueSeleccionado.setEditado(true);
                bloqueSeleccionado.setPredio(predioSeleccionado);
                bloqueSeleccionado.setPredio(predio);
                bloqueSeleccionado.setFotosBloque(fotosBloque);
                //bloqueSeleccionado.setInfoFotosBloque(datosFotos);
                bloqueSeleccionado.setImagesPreview(fotosBloquePreview);
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

                predioSeleccionado.setBloques(bloques);
                predio.setBloques(bloques);

                JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                creandoBloque = false;
                editandoBloque = false;
                editandoPiso = false;
                creandoPiso = false;
                secuenciaPiso = 1;
            }

        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancelarCreacionBloque">
    public void cancelarCreacionBloque() {
        creandoBloque = false;
        editandoBloque = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actualizarBloque">
    public void actualizarBloque() {
        if (bloqueSeleccionado != null) {

            JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + " actualizado");
            creandoBloque = false;
            editandoBloque = false;
        }
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
            if (bloqueSeleccionado.getId() != null) {
                bloquesEliminar.add(bloqueSeleccionado);
            }

            if (!bloques.isEmpty()) {
                bloqueSeleccionado = bloques.get(0);
                bloqueSeleccionadoIndex = 0;
                pisos = bloqueSeleccionado.getPisos();
                if (!pisos.isEmpty()) {
                    pisoSeleccionado = pisos.get(0);
                    pisoSeleccionadoIndex = 0;
                }
            }

            for (Bloque b : bloquesEliminar) {
                for (Piso p : b.getPisos()) {
                    if (p.getId() != null) {
                        if (!pisosEliminar.contains(p)) {
                            pisosEliminar.add(p);
                        }
                    }
                }
                for (UsoSuelo uso : b.getUsosSuelo()) {
                    if (uso.getId() != null) {
                        if (!usosSueloEliminar.contains(uso)) {
                            usosSueloEliminar.add(uso);
                        }
                    }
                }
            }
        }

        creandoBloque = false;
        editandoBloque = false;

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
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionPiso">
    public void inicializarActualizacionPiso() {
        editandoPiso = true;
        creandoPiso = false;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actualizarPiso">
    public void actualizarPiso() {
        editandoPiso = false;
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

                    if (pisoSeleccionado.getId() != null) {
                        if (!pisosEliminar.contains(pisoSeleccionado)) {
                            pisosEliminar.add(pisoSeleccionado);
                        }
                    }
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
    }
//</editor-fold>

    public void onRowPisoSelect(SelectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
        // FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowPisoUnselect(UnselectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
        // FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFilePhotoBloqueUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predioSeleccionado.getClaveCatastral() + "_FB_";
        Util.copyFile(uploadedFile, fileName, fotosBloque, "fotos", (short) 1);
    }

    public void eliminarFotoBloque(int index) {
        Util.deleteFile(index, fotosBloque);
    }
    
    public List<CatPredioRuralTerreno> getListClases(Integer i){
        return (List<CatPredioRuralTerreno>)map.get(i);
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public void selectFotoEliminar(Integer id) {

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

    public void eliminarFotoActualBloque(Integer id) {

        for (ArchivoAux au : fotosBloquePreview) {
            if (Objects.equals(au.getIdArchivo(), id)) {
                fotosBloquePreview.remove(au);
            }
        }

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

    public void onRowSelect(SelectEvent event) {
        nuevoPropietario = ((Contribuyente) event.getObject());

    }

    public void onRowUnselect(UnselectEvent event) {
        nuevoPropietario = ((Contribuyente) event.getObject());
    }

    public Short getEstadoEscritura() {
        return estadoEscritura;
    }

    public void setEstadoEscritura(Short estadoEscritura) {
        this.estadoEscritura = estadoEscritura;
    }

    public Map<Integer, List> getMap() {
        return map;
    }

    public void setMap(Map<Integer, List> map) {
        this.map = map;
    }

    public CatPredioRuralTerreno getTerrenoClaseSel() {
        return terrenoClaseSel;
    }

    public void setTerrenoClaseSel(CatPredioRuralTerreno terrenoClaseSel) {
        this.terrenoClaseSel = terrenoClaseSel;
    }

    public String getHeaderTextDialog() {
        return headerTextDialog;
    }

    public void setHeaderTextDialog(String headerTextDialog) {
        this.headerTextDialog = headerTextDialog;
    }

    public Short getClaseTipoTerreno() {
        return claseTipoTerreno;
    }

    public void setClaseTipoTerreno(Short claseTipoTerreno) {
        this.claseTipoTerreno = claseTipoTerreno;
    }

    public int getTipoDialog() {
        return tipoDialog;
    }

    public void setTipoDialog(int tipoDialog) {
        this.tipoDialog = tipoDialog;
    }

    public List<CatPredioRuralTerreno> getTerrenoList() {
        return terrenoList;
    }

    public void setTerrenoList(List<CatPredioRuralTerreno> terrenoList) {
        this.terrenoList = terrenoList;
    }

    public CatPredioRuralTerreno getClaseTerreno() {
        return claseTerreno;
    }

    public void setClaseTerreno(CatPredioRuralTerreno claseTerreno) {
        this.claseTerreno = claseTerreno;
    }
    
}
