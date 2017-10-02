/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.TipoEscritura;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedDocument;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.util.Base64;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@Named(value = "fraccionarPredioView")
@ViewScoped
public class FraccionarPredioController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FraccionarPredioController.class);

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
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private TipoEscrituraService tipoEscrituraService;
    @EJB
    private PredioService predioService;

    @EJB
    private DeudaService deudaService;
    
    @Inject
    private CrearPredioGenerico crearPredioView;

    private Predio predioOnClic, predioSeleccionado;

    private OpcionesBusquedaPredio opcionesBusqueda;

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

    private short anioActual;

    private Manzana manzana;

    private Predio predio;
    private Terreno terreno;
    private List<Predio> nuevosPredios, prediosFinales;
    private List<String> images;
    private List<Terreno> nuevosTerreno;
    private short secuenciaPredio;

    private List<Contribuyente> propietarios;
    private Contribuyente propietario;
    private Contribuyente propietarioSeleccionado;
    private List<Contribuyente> propietariosEliminar;
    private Contribuyente propietarioEliminarSeleccionado;

    private List<Contribuyente> contribuyentes;
    private Contribuyente contribuyenteSeleccionado;
    private Contribuyente nuevoContribuyente;

    private String s_tipoIdentificacion;
    private String s_numeroIdentificacion;
    private String s_nombreContribuyente;

    private boolean terrenoExistente;

    private Escritura escritura;
    private List<SelectItem> tiposEscrituras;
    private Long idTipoEscritura;

    private String tipoPredio;
    private Integer contPredios;
    private Boolean bloque;
    private short codeUsoDefault;
    private OtrosTipoConstruccion obraSeleccionada;

    private List<Predio> prediosEnManzana;

    private List<Predio> prediosSeleccionados;

    private List<UploadedFile> fotos;
    private UploadedImage[] datosFotos;
    private List<ArchivoAux> fotosPreview;

    private double coord_X;
    private double coord_Y;
    String claveGeoreferenciada;
    String claveOnclic;

    private List<UploadedFile> documentos;
    private UploadedDocument[] datosDocumento;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloTerreno;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        provinciaCod = "0";
        cantonCod = "0";
        parroquiaCod = "0";
        zonaCod = "0";
        sectorCod = "0";
        manzanaCod = "0";
        solarCod = "0";
        contPredios = 0;
        
        predioSeleccionado = null;
        predio = new Predio();
        predioOnClic = new Predio();

        opcionesBusqueda = new OpcionesBusquedaPredio();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        terrenoExistente = false;
        nuevosPredios = new ArrayList<>();
        nuevosTerreno = new ArrayList<>();
        propietarios = new ArrayList<>();
        contribuyentes = new ArrayList<>();
        prediosFinales = new ArrayList();
        tipoPredio = "M";

        escritura = new Escritura();
        tiposEscrituras = new ArrayList<>();

        prediosEnManzana = new ArrayList<>();
        prediosSeleccionados = new ArrayList<>();
        propietarioSeleccionado = new Contribuyente();
        propietariosEliminar = new ArrayList<>();
        nuevoContribuyente = new Contribuyente();

        documentos = new ArrayList<>();
        datosDocumento = new UploadedDocument[30];
        fotos = new ArrayList<>();
        datosFotos = new UploadedImage[30];
        fotosPreview = new ArrayList<>();
        codeUsoDefault = 12;
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
    
    public void fotoPredio(int idPredio) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Predio p = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);

        String foto;
        String pathToPhoto = "";
        images = new ArrayList<>();
        System.out.println("Cantidad de imagenes del predio: " + p.getImages().size());

        if (!p.getImages().isEmpty()) {

            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
            
            for (PredioImage img : p.getImages()) {
                foto = img.getRuta();
                System.out.println("Ruta de la imagen: " + uploadDir + "/" + foto);
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
                        if (!images.contains(pathToPhoto)) {
                            images.add(pathToPhoto);
                        }
                    } else {
                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
                        if (!images.contains(pathToPhoto)) {
                            images.add(pathToPhoto);
                        }
                    }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

        }
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

//<editor-fold defaultstate="collapsed" desc="Getter and Setters">
    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public DeudaService getDeudaService() {
        return deudaService;
    }

    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    public List<UploadedFile> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<UploadedFile> documentos) {
        this.documentos = documentos;
    }

    public UploadedDocument[] getDatosDocumento() {
        return datosDocumento;
    }

    public void setDatosDocumento(UploadedDocument[] datosDocumento) {
        this.datosDocumento = datosDocumento;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public Predio getPredioOnClic() {
        return predioOnClic;
    }

    public void setPredioOnClic(Predio predioOnClic) {
        this.predioOnClic = predioOnClic;
    }

    public String getClaveOnclic() {
        return claveOnclic;
    }

    public void setClaveOnclic(String claveOnclic) {
        this.claveOnclic = claveOnclic;
    }

    public double getCoord_X() {
        return coord_X;
    }

    public void setCoord_X(double coord_X) {
        this.coord_X = coord_X;
    }

    public double getCoord_Y() {
        return coord_Y;
    }

    public void setCoord_Y(double coord_Y) {
        this.coord_Y = coord_Y;
    }

    public String getClaveGeoreferenciada() {
        return claveGeoreferenciada;
    }

    public void setClaveGeoreferenciada(String claveGeoreferenciada) {
        this.claveGeoreferenciada = claveGeoreferenciada;
    }

    public List<Terreno> getNuevosTerreno() {
        return nuevosTerreno;
    }

    public void setNuevosTerreno(List<Terreno> nuevosTerreno) {
        this.nuevosTerreno = nuevosTerreno;
    }

    public List<Predio> getNuevosPredios() {
        return nuevosPredios;
    }

    public void setNuevosPredios(List<Predio> nuevosPredios) {
        this.nuevosPredios = nuevosPredios;
    }

    public short getSecuenciaPredio() {
        return secuenciaPredio;
    }

    public void setSecuenciaPredio(short secuenciaPredio) {
        this.secuenciaPredio = secuenciaPredio;
    }

    public List<SelectItem> getTiposEscrituras() {
        List<TipoEscritura> escr = tipoEscrituraService.findAll();
        for (TipoEscritura te : escr) {
            tiposEscrituras.add(new SelectItem(te.getId(), te.getTipo()));
        }
        return tiposEscrituras;
    }

    public void setTiposEscrituras(List<SelectItem> tiposEscrituras) {
        this.tiposEscrituras = tiposEscrituras;
    }

    public Long getIdTipoEscritura() {
        return idTipoEscritura;
    }

    public void setIdTipoEscritura(Long idTipoEscritura) {
        this.idTipoEscritura = idTipoEscritura;
    }

    public TipoEscrituraService getTipoEscrituraService() {
        return tipoEscrituraService;
    }

    public void setTipoEscrituraService(TipoEscrituraService tipoEscrituraService) {
        this.tipoEscrituraService = tipoEscrituraService;
    }

    public List<Contribuyente> getPropietariosEliminar() {
        return propietariosEliminar;
    }

    public void setPropietariosEliminar(List<Contribuyente> propietariosEliminar) {
        this.propietariosEliminar = propietariosEliminar;
    }

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public Contribuyente getNuevoContribuyente() {
        return nuevoContribuyente;
    }

    public void setNuevoContribuyente(Contribuyente nuevoContribuyente) {
        this.nuevoContribuyente = nuevoContribuyente;
    }

    public Contribuyente getPropietarioEliminarSeleccionado() {
        return propietarioEliminarSeleccionado;
    }

    public void setPropietarioEliminarSeleccionado(Contribuyente propietarioEliminarSeleccionado) {
        this.propietarioEliminarSeleccionado = propietarioEliminarSeleccionado;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public Contribuyente getContribuyenteSeleccionado() {
        return contribuyenteSeleccionado;
    }

    public void setContribuyenteSeleccionado(Contribuyente contribuyenteSeleccionado) {
        this.contribuyenteSeleccionado = contribuyenteSeleccionado;
    }

    public List<Contribuyente> getContribuyentes() {
        return contribuyentes;
    }

    public void setContribuyentes(List<Contribuyente> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }

    public String getS_tipoIdentificacion() {
        return s_tipoIdentificacion;
    }

    public void setS_tipoIdentificacion(String s_tipoIdentificacion) {
        this.s_tipoIdentificacion = s_tipoIdentificacion;
    }

    public String getS_numeroIdentificacion() {
        return s_numeroIdentificacion;
    }

    public void setS_numeroIdentificacion(String s_numeroIdentificacion) {
        this.s_numeroIdentificacion = s_numeroIdentificacion;
    }

    public String getS_nombreContribuyente() {
        return s_nombreContribuyente;
    }

    public void setS_nombreContribuyente(String s_nombreContribuyente) {
        this.s_nombreContribuyente = s_nombreContribuyente;
    }

    public List<Predio> getPrediosSeleccionados() {
        return prediosSeleccionados;
    }

    public void setPrediosSeleccionados(List<Predio> prediosSeleccionados) {
        this.prediosSeleccionados = prediosSeleccionados;
    }

    public List<Predio> getPrediosEnManzana() {
        return prediosEnManzana;
    }

    public void setPrediosEnManzana(List<Predio> prediosEnManzana) {
        this.prediosEnManzana = prediosEnManzana;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
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

    public short getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public List<UploadedFile> getFotos() {
        return fotos;
    }

    public void setFotos(List<UploadedFile> fotos) {
        this.fotos = fotos;
    }

    public UploadedImage[] getDatosFotos() {
        return datosFotos;
    }

    public void setDatosFotos(UploadedImage[] datosFotos) {
        this.datosFotos = datosFotos;
    }

    public List<ArchivoAux> getFotosPreview() {
        return fotosPreview;
    }

    public void setFotosPreview(List<ArchivoAux> fotosPreview) {
        this.fotosPreview = fotosPreview;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Contribuyente getPropietario() {
        return propietario;
    }

    public void setPropietario(Contribuyente propietario) {
        this.propietario = propietario;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public boolean isTerrenoExistente() {
        return terrenoExistente;
    }

    public void setTerrenoExistente(boolean terrenoExistente) {
        this.terrenoExistente = terrenoExistente;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public String getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(String tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

//</editor-fold>
    public void buscarPredio() {
        try {

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

            //predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);
            predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {
                if (!predio.getEstado().equals(new Short("1"))) {
                    JsfUtil.addWarningMessage("Advertencia", "El predio que desea buscar se encuentra inactivo");
                    return;
                }
                if (predio.getTipoPropiedad() == 1) {
                    JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no se puede fraccionar porque es Municipal.",
                            parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
                    init();
                }
                terreno = predio.getTerreno();

//                if (comprobarDeudas(predio)) {
//                    JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral: %2d-%2d-%3d-%2d-%2d, tiene deuda pendientes con el municipio",
//                            zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//
//                    return;
//                }
                secuenciaPredio = catastroService.NumeroProximoTerreno(predio.getTerreno().getTerrenoPK());
                if (secuenciaPredio != 1) {
                    secuenciaPredio++;
                }
                opcionesBusqueda.setEjecutandoAccion(true);
                opcionesBusqueda.setBuscando(false);
                propietarios = predio.getPropietarios();
                // prediosEnManzana = catastroService.obtenerPrediosDeUnaManzana(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);

                if (prediosEnManzana.contains(predio)) {
                    prediosEnManzana.remove(predio);
                }

                Object[] coord = catastroService.coordenadas(predio.getClaveCatastral());
                if (coord != null) {
                    coord_X = Double.parseDouble(coord[0].toString());
                    coord_Y = Double.parseDouble(coord[1].toString());
                    claveGeoreferenciada = coord[2].toString();

                }

                fotoPredio(predio.getId());

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    private boolean comprobarDeudas(Predio p) {

        Object[] params = new Object[2];
        params[0] = p.getClaveCatastral();
        params[1] = "NO PAGADO";
        List<Deuda> deudas = deudaService.findByNamedQuery("Deuda.findByClaveCatastralCheck", params);

        return !deudas.isEmpty();
    }

    public String cancelarOperacionPredio() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public String onFlowProcess(FlowEvent event) {

        if (event.getOldStep().equals("step-unificar-unificacion") || event.getNewStep().equals("step-unificar-generales")) {
            addContribuyenteListadoPropietarios();
        }
        
        if(event.getOldStep().equals("step-fracionar-escritura") && !nuevosPredios.isEmpty()){
            JsfUti.messageInfo(null, "Info", "Debe ingresarle los datos a todos los predios antes de continuar");
            return event.getOldStep();
        }

        return event.getNewStep();
    }
    
    public String onFlowProcessPredio(FlowEvent event){
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

        Predio p = new Predio();
        Escritura c = new Escritura();

        Terreno t = new Terreno(predio.getTerreno());
        String s = generadorCeroALaIzquierda(Long.parseLong(""+secuenciaPredio), 3);
        TerrenoPK pk = new TerrenoPK(predio.getTerreno().getManzana().getManzanaPK(), "" + s);
        t.setTerrenoPK(pk);
        t.setEstado((short)2);
        p.setTerreno(t);
        
        p.setTipoPredio(Boolean.TRUE);
        p.setEstado((short)1);
        p.setCodBloque("000");
        p.setCodPhh("000");
        p.setCodPhv("000");
        p.setClaveAnterior("00000000000");
        
        String codigo = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + s + "-000-000-000";
        p.setClaveCatastral(codigo);
        p.setPropietarios(predio.getPropietarios());
        List<Predio> ps = new ArrayList<>();
        List<Escritura> escs = new ArrayList<>();
        ps.add(p);
        escs.add(c);
        t.setPredios(ps);
        p.setEscrituras(escs);
        nuevosTerreno.add(t);
        nuevosPredios.add(p);
        secuenciaPredio++;
        contPredios++;
    }

    public void eliminarPredio(String codSolar) {
        System.out.println("Entra!");
        if(Short.parseShort(codSolar) != (secuenciaPredio-1)){
            JsfUti.messageInfo(null, "Info", "Solo se puede eliminar el útimo predio ingresado");
            return;
        }

        for (int i = 0; i < nuevosTerreno.size(); i++) {
            if (nuevosTerreno.get(i).getTerrenoPK().getCodSolar().equals(codSolar)) {
                nuevosTerreno.remove(i);
                break;
            }
        }

        for (int i = 0; i < nuevosPredios.size(); i++) {
            if (nuevosPredios.get(i).getTerreno().getTerrenoPK().getCodSolar().equals(codSolar)) {
                nuevosPredios.remove(i);
                break;
            }
        }

        secuenciaPredio--;

    }
    
    public void finalizarPredio(){
        Predio p = this.crearPredioView.getPredio();
        p.setPhotos(this.crearPredioView.getFotosPredio());
         for (int i = 0; i < nuevosPredios.size(); i++) {
            if (nuevosPredios.get(i).getTerreno().getTerrenoPK().getCodSolar().equals(p.getTerreno().getTerrenoPK().getCodSolar())) {
                nuevosPredios.remove(i);
                prediosFinales.add(p);
                crearPredioView.setPredio(null);
                break;
            }
        }
    }
    
    public void setearPredio(Predio sp){
        crearPredioView.setPredio(sp);
        this.predioSeleccionado = sp;
    }

    public String fraccionarPredios() {

        try {

            List<UploadedDocument> files = guardarDocumentos();
            List<UploadedImage> photos = guardarFotos();
            catastroService.fraccionarPredio(this.predio, prediosFinales, files, photos, fotosPreview);
            JsfUtil.addInformationMessage("Predio fraccionado", " predio " + predio.getClaveCatastral() + " fraccionado exitosamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        return "pretty:cat-fraccionar-predio";
    }

    public String getCodigoCatastral() {

        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
        return clave;
    }

    public void onRowSelect(SelectEvent event) {

        //addContribuyenteListadoPropietarios();
        /*
         if (comprobarDeudas(select)) {
         JsfUtil.addInformationMessage("Información", "Predio con clave catastral: " + select.getClaveCatastral() + ", tiene deuda pendientes con el municipio");
         prediosSeleccionados = new ArrayList<Predio>();
         }
         */
    }

    public void onRowUnselect(UnselectEvent event) {

        Predio p = (Predio) event.getObject();

        // deleteContribuyenteListadoPropietarios(p);
        // JsfUtil.addInformationMessage("Predio Unselec", "Predio creado:" + ((Predio) event.getObject()).getId());
    }

    public void onRowSelectDelete(SelectEvent event) {

    }

    public void onRowUnselectDelete(UnselectEvent event) {

    }

    public void inicializarBusquedaContribuyente() {
        contribuyenteSeleccionado = new Contribuyente();
        contribuyentes = new ArrayList<Contribuyente>();
        s_tipoIdentificacion = "";
        s_nombreContribuyente = "";
        s_numeroIdentificacion = "";

    }

    public void inicializarContribuyente() {
        nuevoContribuyente = new Contribuyente();

    }

    public void adicionarPropietario() {
        if (contribuyenteSeleccionado != null) {
            boolean found = false;
            for (Contribuyente cont : propietarios) {
                if (contribuyenteSeleccionado.equals(cont)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                propietarios.add(contribuyenteSeleccionado);
            }
        }
    }

    public void buscarContribuyente() {

        try {
            contribuyentes = new ArrayList<Contribuyente>();

            Object[] params = new Object[2];
            params[0] = s_tipoIdentificacion;
            params[1] = s_numeroIdentificacion;

            contribuyentes = contribuyenteService.findByNamedQuery("Contribuyente.findByTipoIdentificacion", params);

            if (contribuyentes == null || contribuyentes.isEmpty()) {
                JsfUtil.addWarningMessage(null, "Contribuyente no encontrado");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    private void addContribuyenteListadoPropietarios() {

        List<Contribuyente> props;
        propietarios = new ArrayList<Contribuyente>();

        for (Contribuyente p : predio.getPropietarios()) {
            propietarios.add(p);
        }

        for (Predio p : prediosSeleccionados) {

            props = p.getPropietarios();

            for (Contribuyente c : props) {
                if (!propietarios.contains(c)) {
                    propietarios.add(c);
                }
            }
        }
    }

    public void eliminarPropietario() {

        for (Contribuyente c : propietariosEliminar) {
            if (propietarios.contains(c)) {
                propietarios.remove(c);
            }
        }
    }

    public void RegistrarNuevoContribuyente() {

        log.error("Llamada al metodo registrar contribuyente");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("alert('alert inside bean');");

        try {

            contribuyenteSeleccionado = contribuyenteService.create(nuevoContribuyente);
            log.error("Datos: " + contribuyenteSeleccionado.getId());
            JsfUtil.addInformationMessage("Contribuyente creado", "con exito.");

        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
        }
    }

    public void doSomething() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        claveOnclic = ec.getRequestParameterMap().get("fraccionar-predios:input");
        claveOnclic += "-0";

        List<Predio> lista = predioService.findByNamedQuery("Predio.findByClaveCatastral", claveOnclic);

        if (!lista.isEmpty()) {
            predioOnClic = lista.get(0);
        } else {
            predioOnClic = new Predio();
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('UsersWidget').show();");
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        documentos.add(uploadedFile);
        UploadedDocument ui = new UploadedDocument(documentos.size(), FilenameUtils.getName(uploadedFile.getFileName()), "");

        datosDocumento[documentos.size() - 1] = ui;
    }

    public void crearNuevoUso(boolean bloque) {

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        boolean existe = false;

        if (bloque) {
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                // usoSeleccionado.setBloque(bloqueSeleccionado);
                usosSuelo.add(usoSeleccionado);
//                bloqueSeleccionado.setUsosSuelo(usosSuelo);
                initNuevoUso();
            }

            if (existe) {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo se encuentra registrado al bloque.");
            } else {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito");
            }
        } else {

            for (int i = 0; i < getUsosSueloTerreno().size(); i++) {
                if (Objects.equals(getUsosSueloTerreno().get(i).getCod(), usoSeleccionado.getCod())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                usoSeleccionado.setPredio(predio);
                usosSueloTerreno.add(usoSeleccionado);
                initNuevoUso();
            }

            if (existe) {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo se ya encuentra registrado al terreno.");
            } else {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito.");
            }
        }

    }

    public void eliminarDocumento(int index) {
        if (index >= 0 && index < documentos.size()) {
            for (int i = index; i < documentos.size() - 1; i++) {
                datosDocumento[i] = datosDocumento[i + 1];
            }
            datosDocumento[documentos.size() - 1] = null;
            documentos.remove(index);
        }
    }

    private List<UploadedDocument> guardarDocumentos() {

        List<UploadedDocument> copiados = new ArrayList<UploadedDocument>();
        for (int i = 0; i < documentos.size(); i++) {
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);
            int millis = now.get(Calendar.MILLISECOND);
            UploadedFile uploadedFile = documentos.get(i);
            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_documentos");
                String nuevoNombre = String.format("%04d-%02d-%02d-%02d-%02d-%02d-%03d", year, month, day, hour, minute, second, millis);
                nuevoNombre += "-" + FilenameUtils.getName(uploadedFile.getFileName());

                File destFile = new File(new File(uploadDir), nuevoNombre);

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedDocument doc = datosDocumento[i];
                doc.setSavedFile(destFile);

                copiados.add(doc);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return copiados;
    }

    private List<UploadedImage> guardarFotos() {
        List<UploadedImage> copiados = new ArrayList<UploadedImage>();
        for (int i = 0; i < fotos.size(); i++) {
            UploadedFile uploadedFile = fotos.get(i);
            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");

                String fileName = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod + "_";
                fileName += UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
                File destFile = new File(new File(uploadDir), fileName);

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedImage image = datosFotos[i];
                image.setSavedFile(destFile);

                copiados.add(image);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return copiados;
    }

    public StreamedContent getImage() {
        DefaultStreamedContent content = new DefaultStreamedContent();

        FacesContext context = FacesContext.getCurrentInstance();

        // if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
        try {
            content = new DefaultStreamedContent(fotos.get(0).getInputstream(), "image/png");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        //}
        return content;
    }

    public String getImageName() {
        try {
            InputStream is = fotos.get(0).getInputstream();
            byte[] data = IOUtils.toByteArray(is);
            return "data:image/png;base64," + Base64.encodeToString(data, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    private String getBase64Data(UploadedFile file) {
        try {

            byte[] data = IOUtils.toByteArray(file.getInputstream());

            return "data:image/" + FilenameUtils.getExtension(file.getFileName()) + ";base64," + Base64.encodeToString(data, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    public void handleFileUploadPhoto(FileUploadEvent event) throws FileNotFoundException, IOException {

        //get uploaded file from the event
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        fotos.add(uploadedFile);
        UploadedImage ui = new UploadedImage(fotos.size(), FilenameUtils.getName(uploadedFile.getFileName()),
                getBase64Data(uploadedFile), "");

        datosFotos[fotos.size() - 1] = ui;
    }

    public void eliminarFoto(int index) {
        if (index >= 0 && index < fotos.size()) {
            for (int i = index; i < fotos.size() - 1; i++) {
                datosFotos[i] = datosFotos[i + 1];
            }
            datosFotos[fotos.size() - 1] = null;
            fotos.remove(index);

        }
    }

    public void eliminarFotoActual(Integer id) {
        for (ArchivoAux au : fotosPreview) {
            if (Objects.equals(au.getIdArchivo(), id)) {
                fotosPreview.remove(au);
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

    private boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
    }

    public void selectFotoEliminar(Integer id) {

    }

    /**
     * @return the usosSuelo
     */
    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    /**
     * @param usosSuelo the usosSuelo to set
     */
    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    /**
     * @return the usosSueloTerreno
     */
    public List<UsoSuelo> getUsosSueloTerreno() {
        if (usosSueloTerreno == null) {
            usosSueloTerreno = new LinkedList<>();
        }
        return usosSueloTerreno;
    }

    /**
     * @param usosSueloTerreno the usosSueloTerreno to set
     */
    public void setUsosSueloTerreno(List<UsoSuelo> usosSueloTerreno) {
        this.usosSueloTerreno = usosSueloTerreno;
    }

    /**
     * @return the usoSeleccionado
     */
    public UsoSuelo getUsoSeleccionado() {
        if (usoSeleccionado == null) {
            usoSeleccionado = new UsoSuelo();
        }
        return usoSeleccionado;
    }

    /**
     * @param usoSeleccionado the usoSeleccionado to set
     */
    public void setUsoSeleccionado(UsoSuelo usoSeleccionado) {
        this.usoSeleccionado = usoSeleccionado;
    }

    /**
     * @return the valorDiscreto
     */
    public ValorDiscreto getValorDiscreto() {
        return valorDiscreto;
    }

    /**
     * @param valorDiscreto the valorDiscreto to set
     */
    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
    }

    public void initNuevoUso() {
        usoSeleccionado = new UsoSuelo();
        valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
        usoSeleccionado.setValorDiscreto(getValorDiscreto());
        usoSeleccionado.setCod((short) 12);

    }

    public void cancelarCrearUso() {
        if (usosSuelo != null) {
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

    public void onRowSelectUso(SelectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();

    }

    public void onRowUnselectUso(UnselectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();
    }

    public List<SelectItem> valoresVariablesuUsos() {

        List<SelectItem> lista = new ArrayList<>();
        List<ValorDiscreto> valores = new ArrayList<>();
        for (int i = 33; i <= 52; i++) {
            valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
//            for (int j = 0; j < valores.size(); j++) {
//                for (UsoSuelo u : usosSuelo) {
//                    if (u.getCod() == valores.get(j).getValorDiscretoPK().getValor()) {
//                        valores.remove(j);
//                    }
//                }
//            }
            for (ValorDiscreto v : valores) {
                SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                lista.add(item);
            }
        }

        return lista;

    }

    public List<SelectItem> valoresVariablesuUsosPH() {

        List<SelectItem> lista = new ArrayList<>();
        List<ValorDiscreto> valores;
        for (int i = 33; i <= 52; i++) {
            valores = variablesService.obtenerValoresPorCodVariablePH(i, anioActual);

            for (ValorDiscreto v : valores) {
                if (v.isParaPH()) {
                    SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                    lista.add(item);
                }
            }
        }

        return lista;

    }

    public String codigosVariablesuUsos() {

        String codigos = "";

        for (int i = 33; i <= 52; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariable(i, anioActual);
        }
        return codigos;

    }

    public String codigosVariablesuUsosPH() {

        String codigos = "";

        for (int i = 33; i <= 52; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariablePH(i, anioActual);
        }
        return codigos;

    }

    public void changeUso(short cod) {
        valorDiscreto = variablesService.obtenerValor(33, 52, cod, anioActual);
        //     usoSeleccionado.setCod((int) cod);
    }

    public Integer getContPredios() {
        return contPredios;
    }

    public void setContPredios(Integer contPredios) {
        this.contPredios = contPredios;
    }

    public Boolean getBloque() {
        return bloque;
    }

    public void setBloque(Boolean bloque) {
        this.bloque = bloque;
    }

    public short getCodeUsoDefault() {
        return codeUsoDefault;
    }

    public void setCodeUsoDefault(short codeUsoDefault) {
        this.codeUsoDefault = codeUsoDefault;
    }

    public OtrosTipoConstruccion getObraSeleccionada() {
        return obraSeleccionada;
    }

    public void setObraSeleccionada(OtrosTipoConstruccion obraSeleccionada) {
        this.obraSeleccionada = obraSeleccionada;
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public CrearPredioGenerico getCrearPredioView() {
        return crearPredioView;
    }

    public void setCrearPredioView(CrearPredioGenerico crearPredioView) {
        this.crearPredioView = crearPredioView;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Predio> getPrediosFinales() {
        return prediosFinales;
    }

    public void setPrediosFinales(List<Predio> prediosFinales) {
        this.prediosFinales = prediosFinales;
    }

}
