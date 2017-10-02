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
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedDocument;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
 * @author dairon
 */
@Named(value = "crearPHView")
@ViewScoped
public class CrearPHController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CrearPHController.class);

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

    @EJB
    private SearchService searchService;

    private Predio predioOnClic;

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

    private Manzana manzana;

    private Predio predio;
    private List<Predio> nuevosPredios;
    private short secuenciaPHV;
    private short secuenciaPHH;
    private List<Bloque> bloques;
    private Bloque bloqueSeleccionado;
    
    private List<Contribuyente> propietarios;
    private Contribuyente propietario;
    private Contribuyente propietarioSeleccionado;

    private List<Contribuyente> propietarioNuevos;
    private Contribuyente nuevoPropietario;
    private String parametroBusqueda;

    private boolean terrenoExistente;

    private Escritura escritura;
    private List<SelectItem> tiposEscrituras;
    private Long idTipoEscritura;

    private String tipoPredio;

    private double coord_X;
    private double coord_Y;
    String claveGeoreferenciada;
    String claveOnclic;

    private List<UploadedFile> documentos;
    private UploadedDocument[] datosDocumento;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        predio = new Predio();
        predioOnClic = new Predio();

        opcionesBusqueda = new OpcionesBusquedaPredio();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        terrenoExistente = false;
        nuevosPredios = new ArrayList<>();
        propietarios = new ArrayList<>();
        tipoPredio = "M";

        escritura = new Escritura();
        tiposEscrituras = new ArrayList<>();

        propietarioSeleccionado = new Contribuyente();

        coord_Y = -2.23725443384787;
        coord_X = -80.9373380726774;
        claveGeoreferenciada = "2-11-39-1";

        documentos = new ArrayList<>();
        datosDocumento = new UploadedDocument[30];

        propietarioNuevos = new ArrayList<>();

        nuevoPropietario = new Contribuyente();
        
        
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setters">

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
    
    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public short getSecuenciaPHV() {
        return secuenciaPHV;
    }

    public void setSecuenciaPHV(short secuenciaPHV) {
        this.secuenciaPHV = secuenciaPHV;
    }

    public short getSecuenciaPHH() {
        return secuenciaPHH;
    }

    public void setSecuenciaPHH(short secuenciaPHH) {
        this.secuenciaPHH = secuenciaPHH;
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

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
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

    public List<Predio> getNuevosPredios() {
        return nuevosPredios;
    }

    public void setNuevosPredios(List<Predio> nuevosPredios) {
        this.nuevosPredios = nuevosPredios;
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

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
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

            provinciaCod = opcionesBusqueda.getProvinciaCod();
            cantonCod = opcionesBusqueda.getCantonCod();
            parroquiaCod = opcionesBusqueda.getParroquiaCod();
            zonaCod = opcionesBusqueda.getZonaCod();
            sectorCod = opcionesBusqueda.getSectorCod();
            manzanaCod = opcionesBusqueda.getManzanaCod();
            solarCod = opcionesBusqueda.getSolarCod();
            bloqueCod = "0";
            phvCod = "0";
            phhCod = "0";

            predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {

                if (predio.getTipoPropiedad() == 1) {
                    JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no se puede crer porque no es una Propiedad Horizontal.",
                            parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
                    init();
                }

//                if (comprobarDeudas(predio)) {
//                    JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral: %2d-%2d-%3d-%2d-%2d, tiene deuda pendientes con el municipio",
//                            zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//
//                    return;
//                }
//                secuenciaPredio = 0;//catastroService.NumeroProximoPH(predio.getTerreno().getTerrenoPK());
//                if (secuenciaPredio != 1) {
//                    secuenciaPredio++;
//                }
                opcionesBusqueda.setEjecutandoAccion(true);
                opcionesBusqueda.setBuscando(false);
                propietarios = predio.getPropietarios();

                Object[] params = {parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod};
                nuevosPredios = predioService.findByNamedQuery("Predio.findAllPHByTerreno", params);
                
                bloques = predio.getBloques();
                bloqueSeleccionado = bloques.get(0);

                Object[] coord = catastroService.coordenadas(predio.getClaveCatastral());
                if (coord != null) {
                    coord_X = Double.parseDouble(coord[0].toString());
                    coord_Y = Double.parseDouble(coord[1].toString());
                    claveGeoreferenciada = coord[2].toString();

                }

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    private boolean comprobarDeudas(Predio p) {

        List<Deuda> deudas = deudaService.findByNamedQuery("Deuda.findByClaveCatastral", p.getClaveCatastral());

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

        return event.getNewStep();
    }

    public void nuevoPredio() {

        Predio p = new Predio();

        p.setCodBloque(""+bloqueSeleccionado.getNumeroBloque());
        p.setTerreno(predio.getTerreno());
        p.setCodPhv(""+secuenciaPHV++);
        p.setCodPhh(""+secuenciaPHH++);
        predio.getTerreno().getPredios().add(p);
        nuevosPredios.add(p);
    }

    public String crearPropiedadesHorizontales() {

        try {

            List<UploadedDocument> files = guardarDocumentos();
            catastroService.AdicionarPHs(predio, nuevosPredios, escritura, idTipoEscritura, files);
            JsfUtil.addInformationMessage("Nuevas PH", "Al predio " + predio.getClaveCatastral() + " se le registraron " + nuevosPredios.size() + " propiedades horizontales exitosamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        return "pretty:cat-crear-ph";
    }

    public String getCodigoCatastral() {

        String clave = String.format("%d-%d-%d-%d-%d-%d-%d-%d", parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
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

        String uploadDir = config.getDbConfiguration().getString("directorio_documentos");
        List<UploadedDocument> copiados = new ArrayList<>();
        for (int i = 0; i < documentos.size(); i++) {

            UploadedFile uploadedFile = documentos.get(i);
            String fileName = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod + "_";
            fileName += UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName());

            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                File destFile = new File(new File(uploadDir), fileName);

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

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        log.error("Old value: " + oldValue != null ? oldValue.toString() : "Null");
        log.error("New value: " + newValue != null ? newValue.toString() : "Null");
    }

    public void inicializarBusquedaContribuyente() {
        nuevoPropietario = null;
        propietarioNuevos = new ArrayList<Contribuyente>();

    }

    public void buscarContribuyente() {
        try {
            propietarioNuevos = new ArrayList<Contribuyente>();

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
        propietarioSeleccionado = new Contribuyente();
    }
}
