/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.ImpuestoPredialPK;
import com.dadoco.cat.model.RazonExencion;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TipoExencion;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.ContribuyentePredioService;
import com.dadoco.cat.service.EscrituraService;
import com.dadoco.cat.service.ImpuestoService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.RazonExencionService;
import com.dadoco.cat.service.TipoExencionService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.lazyModel.LazyDataModelPredio;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.search.SearchService;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FlowEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "inquilinatoView")
@ViewScoped
public class CertificadoInquilinatoController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CertificadoInquilinatoController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;

    @EJB
    private PredioService predioService;

    @EJB
    private TipoExencionService tipoExencionService;
    @EJB
    private RazonExencionService razonExencionService;
    @EJB
    private AvaluosService avaluosService;

    @EJB
    private ContribuyenteService contribuyenteService;

    @EJB
    private ContribuyentePredioService contribuyentePredioService;

    @EJB
    private EscrituraService escrituraService;

    @EJB
    private ImpuestoService impuestoService;

    @EJB
    private SearchService searchService;

    private Exencion exencion;
    private List<SelectItem> tiposExencion;
    private Integer idTipoExencion;
    private List<SelectItem> razonesExencion;
    private Integer idRazonExencion;

    private RazonExencion razonExencionSeleccionada;

    private List<Bloque> bloquesEliminar;
    private List<Piso> pisosEliminar;

    private OpcionesBusquedaPredio opcionesBusqueda;
    private Predio predioSeleccionado;
    private List<Predio> predios;
    private List<Predio> prediosFiltrados;
    private Terreno terreno;
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

    private boolean editandoDatos;

    private boolean visualizarListado;
    private boolean visualizarDatos;

    private String avaluoTerreno;
    private String avaluoConstruccion;
    private String avaluoComplementarias;
    private String avaluoPredial;

    private String areaTerreno;
    private String areaConstruccion;
    private String valorTerreno;

    private List<String> images;

    private String datoBusqueda;
    private String opcion;
    private String textoOpcion;
    private String piso;
    private String dpto;

    private boolean porEdificio;

    private List<Contribuyente> listaContribuyente;

    private List<Contribuyente> propietariosPredioSeleccionado;
    private List<Contribuyente> arrendatariosPredioSeleccionado;

    private LazyDataModelPredio listaPredios;

    @PostConstruct
    public void init() {

        this.setListaPredios(new LazyDataModelPredio(predioService));

        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        predioSeleccionado = new Predio();
        predios = new ArrayList<Predio>();
        bloqueSeleccionado = new Bloque();

        bloqueSeleccionadoIndex = 0;
        pisoSeleccionadoIndex = 0;
        visualizarDatos = false;
        visualizarListado = true;
        exencion = new Exencion();
        images = new ArrayList<String>();
        listaContribuyente = new ArrayList<Contribuyente>();
        opcion = "CC";
        textoOpcion = "Clave catastral";

        propietariosPredioSeleccionado = new ArrayList<Contribuyente>();
        arrendatariosPredioSeleccionado = new ArrayList<Contribuyente>();

    }

    public ImpuestoService getImpuestoService() {
        return impuestoService;
    }

    public void setImpuestoService(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public void setPorEdificio(boolean porEdificio) {
        this.porEdificio = porEdificio;
    }

    public List<String> getEstatus() {
        String[] valores = {"Municipal", "Propio"};
        return Arrays.asList(valores);
    }

    public void buscarPredio() {

        String palabraClave = opcionesBusqueda.getPalabraClave();
        this.getListaPredios().setBusqueda(palabraClave);
        try {

            if (palabraClave.equals("")) {
                predios = predioService.findByFilter();
            } else {

                predios = searchService.buscarPredio(palabraClave);
            }

            prediosFiltrados.clear();
            prediosFiltrados = predios;
            visualizarDatos = false;

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public void adicionarConstruccion() {

    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public List<Contribuyente> getPropietariosPredioSeleccionado() {
        return propietariosPredioSeleccionado;
    }

    public void setPropietariosPredioSeleccionado(List<Contribuyente> propietariosPredioSeleccionado) {
        this.propietariosPredioSeleccionado = propietariosPredioSeleccionado;
    }

    public List<Contribuyente> getArrendatariosPredioSeleccionado() {
        return arrendatariosPredioSeleccionado;
    }

    public void setArrendatariosPredioSeleccionado(List<Contribuyente> arrendatariosPredioSeleccionado) {
        this.arrendatariosPredioSeleccionado = arrendatariosPredioSeleccionado;
    }

    public ContribuyentePredioService getContribuyentePredioService() {
        return contribuyentePredioService;
    }

    public void setContribuyentePredioService(ContribuyentePredioService contribuyentePredioService) {
        this.contribuyentePredioService = contribuyentePredioService;
    }

    public ContribuyenteService getContribuyenteService() {
        return contribuyenteService;
    }

    public void setContribuyenteService(ContribuyenteService contribuyenteService) {
        this.contribuyenteService = contribuyenteService;
    }

    public String getDatoBusqueda() {
        return datoBusqueda;
    }

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getTextoOpcion() {
        return textoOpcion;
    }

    public void setTextoOpcion(String textoOpcion) {
        this.textoOpcion = textoOpcion;
    }

    public List<Contribuyente> getListaContribuyente() {
        return listaContribuyente;
    }

    public void setListaContribuyente(List<Contribuyente> listaContribuyente) {
        this.listaContribuyente = listaContribuyente;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(String areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public String getAreaConstruccion() {
        return areaConstruccion;
    }

    public void setAreaConstruccion(String areaConstruccion) {
        this.areaConstruccion = areaConstruccion;
    }

    public String getValorTerreno() {
        return valorTerreno;
    }

    public void setValorTerreno(String valorTerreno) {
        this.valorTerreno = valorTerreno;
    }

    public AvaluosService getAvaluosService() {
        return avaluosService;
    }

    public void setAvaluosService(AvaluosService avaluosService) {
        this.avaluosService = avaluosService;
    }

    public String getAvaluoTerreno() {
        return avaluoTerreno;
    }

    public void setAvaluoTerreno(String avaluoTerreno) {
        this.avaluoTerreno = avaluoTerreno;
    }

    public String getAvaluoConstruccion() {
        return avaluoConstruccion;
    }

    public void setAvaluoConstruccion(String avaluoConstruccion) {
        this.avaluoConstruccion = avaluoConstruccion;
    }

    public String getAvaluoComplementarias() {
        return avaluoComplementarias;
    }

    public void setAvaluoComplementarias(String avaluoComplementarias) {
        this.avaluoComplementarias = avaluoComplementarias;
    }

    public String getAvaluoPredial() {
        return avaluoPredial;
    }

    public void setAvaluoPredial(String avaluoPredial) {
        this.avaluoPredial = avaluoPredial;
    }

    public TipoExencionService getTipoExencionService() {
        return tipoExencionService;
    }

    public void setTipoExencionService(TipoExencionService tipoExencionService) {
        this.tipoExencionService = tipoExencionService;
    }

    public RazonExencionService getRazonExencionService() {
        return razonExencionService;
    }

    public void setRazonExencionService(RazonExencionService razonExencionService) {
        this.razonExencionService = razonExencionService;
    }

    public Exencion getExencion() {
        return exencion;
    }

    public void setExencion(Exencion exencion) {
        this.exencion = exencion;
    }

    public List<SelectItem> getTiposExencion() {

        List<TipoExencion> tipoExecionList = tipoExencionService.findAll();
        tiposExencion = new ArrayList<SelectItem>();

        if (!tipoExecionList.isEmpty()) {
            idTipoExencion = tipoExecionList.get(0).getId();
            for (TipoExencion te : tipoExecionList) {
                tiposExencion.add(new SelectItem(te.getId(), te.getTipoExencion()));
            }
        }
        return tiposExencion;
    }

    public void setTiposExencion(List<SelectItem> tiposExencion) {
        this.tiposExencion = tiposExencion;
    }

    public Integer getIdTipoExencion() {
        return idTipoExencion;
    }

    public RazonExencion getRazonExencionSeleccionada() {
        return razonExencionSeleccionada;
    }

    public void setRazonExencionSeleccionada(RazonExencion razonExencionSeleccionada) {
        this.razonExencionSeleccionada = razonExencionSeleccionada;
    }

    public void setIdTipoExencion(Integer idTipoExencion) {
        this.idTipoExencion = idTipoExencion;
    }

    public List<SelectItem> getRazonesExencion() {

        Integer val = idTipoExencion;
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findByIdTipoExencion", val);
        razonesExencion = new ArrayList<SelectItem>();
        if (!list.isEmpty()) {
            razonExencionSeleccionada = list.get(0);
            idRazonExencion = razonExencionSeleccionada.getId();
            for (RazonExencion re : list) {
                razonesExencion.add(new SelectItem(re.getId(), re.getRazonExencion()));
            }
        }
        return razonesExencion;
    }

    public void setRazonesExencion(List<SelectItem> razonesExencion) {
        this.razonesExencion = razonesExencion;
    }

    public Integer getIdRazonExencion() {

        return idRazonExencion;
    }

    public void setIdRazonExencion(Integer idRazonExencion) {
        this.idRazonExencion = idRazonExencion;
    }

    public List<Predio> getPrediosFiltrados() {
        return prediosFiltrados;
    }

    public void setPrediosFiltrados(List<Predio> prediosFiltrados) {
        this.prediosFiltrados = prediosFiltrados;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public List<Predio> getPredios() {
        return predios;
    }

    public void setPredios(List<Predio> predios) {
        this.predios = predios;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
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

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
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

    public List<Bloque> getBloques() {
        return bloques;
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

    public boolean isEditandoDatos() {
        return editandoDatos;
    }

    public void setEditandoDatos(boolean editandoDatos) {
        this.editandoDatos = editandoDatos;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public boolean isEditandoBloque() {
        return editandoBloque;
    }

    public void setEditandoBloque(boolean editandoBloque) {
        this.editandoBloque = editandoBloque;
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

    public boolean isCreandoBloque() {
        return creandoBloque;
    }

    public void setCreandoBloque(boolean creandoBloque) {
        this.creandoBloque = creandoBloque;
    }

    public boolean isVisualizarListado() {
        return visualizarListado;
    }

    public void setVisualizarListado(boolean visualizarListado) {
        this.visualizarListado = visualizarListado;
    }

    public boolean isVisualizarDatos() {
        return visualizarDatos;
    }

    public void setVisualizarDatos(boolean visualizarDatos) {
        this.visualizarDatos = visualizarDatos;
    }

    public EscrituraService getEscrituraService() {
        return escrituraService;
    }

    public void setEscrituraService(EscrituraService escrituraService) {
        this.escrituraService = escrituraService;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="inicializarNuevoBloque">
    public void inicializarNuevoBloque() {
        bloqueSeleccionado = new Bloque();
        pisos = new ArrayList<Piso>();
        pisoSeleccionadoIndex = 0;
        creandoBloque = true;
        creandoPiso = true;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionBloque">
    public void inicializarActualizacionBloque() {
        editandoBloque = true;
        creandoBloque = false;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="crearBloque">
    public void crearBloque() {
        if (bloqueSeleccionado != null) {
//            bloqueSeleccionado.setListaPisos(pisos);
            bloqueSeleccionado.setNumeroBloque(bloques.size() + 1);
            bloques.add(bloqueSeleccionado);
            JsfUtil.addSuccessMessage("Bloque creado " + bloqueSeleccionado.getAreaTotal());
            creandoBloque = false;
            creandoPiso = false;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actualizarBloque">
    public void actualizarBloque() {
        if (bloqueSeleccionado != null) {
//            bloqueSeleccionado.setListaPisos(pisos);
            JsfUtil.addSuccessMessage("Bloque actualizado");
            creandoBloque = false;
            editandoBloque = false;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarBloque">
    public void eliminarBloque() {

        bloques.remove(bloqueSeleccionado);
        bloquesEliminar.add(bloqueSeleccionado);
        pisos = new ArrayList<Piso>();

//        if (!bloques.isEmpty()) {
//            bloqueSeleccionado = bloques.get(0);
//            pisos = bloqueSeleccionado.getListaPisos();
//            if (!bloqueSeleccionado.getListaPisos().isEmpty()) {
//                pisoSeleccionado = bloqueSeleccionado.getListaPisos().get(0);
//            }
//        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarBloque">
    public void cambiarBloque() {
        bloqueSeleccionado = bloques.get(bloqueSeleccionadoIndex);
//        pisos = bloqueSeleccionado.getListaPisos();
        pisoSeleccionadoIndex = -1;
        if (pisos.size() > 1) {
            pisoSeleccionado = pisos.get(0);
        }
        editandoBloque = true;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarNuevoPiso">
    public void inicializarNuevoPiso() {
        pisoSeleccionado = new Piso();
        creandoPiso = true;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="guardarPiso">
    public void guardarPiso() {
        try {
            if (pisoSeleccionado != null) {
                pisoSeleccionado.setBloque(bloqueSeleccionado);
                pisoSeleccionado.setNumeroPiso(pisos.size() + 1);
                pisos.add(pisoSeleccionado);

                pisoSeleccionadoIndex = pisos.size() - 1;
                creandoPiso = false;
                editandoPiso = false;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionPiso">
    public void inicializarActualizacionPiso() {
        editandoPiso = true;
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
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarPiso">
    public void cambiarPiso() {
        pisoSeleccionado = pisos.get(pisoSeleccionadoIndex);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarPiso">
    public void eliminarPiso() {

//        bloques.get(bloqueSeleccionadoIndex).getListaPisos().remove(pisoSeleccionadoIndex);
//        bloqueSeleccionado = bloques.get(bloqueSeleccionadoIndex);
//        pisos = bloqueSeleccionado.getListaPisos();
    }
//</editor-fold>

    public String cancelarModificacion() {
        visualizarDatos = false;
        visualizarListado = true;
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public void ImprimirCertificado(int id) throws JRException, IOException {

        // Predio p = predioService.find(id);
        Predio p = predioService.findByNamedQuery("Predio.findById", id).get(0);
        ImpuestoPredial impuesto = new ImpuestoPredial();
        short anioEmision = (short) (Util.ANIO_ACTUAL.shortValue());
        ImpuestoPredialPK ipk = new ImpuestoPredialPK(id, anioEmision);

        impuesto = impuestoService.find(ipk);

        if (impuesto == null) {
            impuesto = new ImpuestoPredial();
        }

        Map<String, Object> parameter = new HashMap<String, Object>();

        parameter.put("clave", p.getClaveCatastral());
        String parroquia = p.getTerreno().getTerrenoPK().getCodParroquia();
        parameter.put("parroquia", !parroquia.equals("99") ? parroquia : "");
        parameter.put("zona", "" + p.getTerreno().getTerrenoPK().getCodZona());
        parameter.put("sector", "" + p.getTerreno().getTerrenoPK().getCodSector());
        parameter.put("manzana", "" + p.getTerreno().getTerrenoPK().getCodManzana());
        parameter.put("solar", "" + p.getTerreno().getTerrenoPK().getCodSolar());
//        parameter.put("ph", "" + p.getPropiedadHorizontal());

        String prop1 = "";

        List<Contribuyente> props = p.getPropietarios();

        if (!props.isEmpty()) {
            for (int i = 0; i < props.size(); i++) {
                if (i == 0) {
                    prop1 = props.get(i).getApellidoPaterno() + " " + props.get(i).getApellidoMaterno() + " " + props.get(i).getNombre();
                    break;
                }

            }
        }

        parameter.put("propietario", prop1);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("#.00");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

//        double areaTerren = Math.round(p.getTerreno().getAreaLevantamiento().doubleValue() * 100.0) / 100.0;
//        parameter.put("areaTerreno", "" + decimalFormat.format(areaTerren));
        double avaluoTer = (Math.round(impuesto.getAvaluoTerreno() * 100.0) / 100.0);
        double avaluoCont = (Math.round(impuesto.getAvaluoEdificacion() * 100.0) / 100.0);
        double avaluoComercial = (Math.round((avaluoCont + avaluoTer) * 100.0) / 100.0);

        parameter.put("avalTerreno", "" + decimalFormat.format(avaluoTer));
        parameter.put("avalConstruccion", "" + decimalFormat.format(avaluoCont));
        //   parameter.put("avaluoComplementarias", "" + (Math.round(impuesto.getAvaluoComplementarias() * 100.0) / 100.0));
        parameter.put("avaluoComercial", "" + decimalFormat.format(avaluoTer + avaluoCont));
        // parameter.put("valorMetroTerreno", "" + (Math.round((impuesto.getAvaluoTerreno() / impuesto.getAreaTerreno()) * 100.0) / 100.0));
        //parameter.put("valorMetroConstruccion", "" + (Math.round((impuesto.getAvaluoEdificacion() / impuesto.getAreaConstruccion()) * 100.0) / 100.0));

        String canon = "La propiedad queda debidamente inscripta en el Registro de Arrendamiento Urbano y que de acuerdo al Art. 11 de la respectiva Ordenanza y acorde a lo dispuesto en el Art. 17 de la Ley de Inquilinato esta dependencia fija en USD$ ";

        canon += "55";
        parameter.put("canon", canon);
        // parameter.put("observaciones", p.getTerreno().getObservacion());

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/certificadoInquilinato.jasper"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameter, new JREmptyDataSource());

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        //response.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new Date().toString() + "-certificado-inquilinato" + ".pdf" + "\"");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void onTipoExencionChange() {

        Integer val = idTipoExencion;
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findByIdTipoExencion", val);

        razonesExencion = new ArrayList<SelectItem>();
        if (!list.isEmpty()) {
            razonExencionSeleccionada = list.get(0);

            idRazonExencion = razonExencionSeleccionada.getId();
            for (RazonExencion re : list) {
                razonesExencion.add(new SelectItem(re.getId(), re.getRazonExencion()));
            }
        }
    }

    public void onRazonExencionChange() {
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findById", idRazonExencion);
        if (list.isEmpty()) {
            razonExencionSeleccionada = new RazonExencion();
        } else {
            razonExencionSeleccionada = list.get(0);
            idRazonExencion = razonExencionSeleccionada.getId();
        }
    }

    public void fotoPredio(int idPredio) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Predio p = predioService.findByNamedQuery("Predio.findById", idPredio).get(0);

        String foto;
        String pathToPhoto = "";
        images = new ArrayList<String>();

//        if (!p.getArchivos().isEmpty()) {
//
//            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
//
//            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
//
//            for (Archivo img : p.getArchivos()) {
//
//                foto = img.getRuta();
//
//                try {
//                    if (existeArchivo(uploadDir + "/" + foto)) {
//                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
//                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);
//
//                        try {
//                            IOUtils.copy(inFile, outFile);
//                            inFile.close();
//                            outFile.close();
//                        } catch (IOException ex) {
//
//                        }
//                    }
//
//                    if (existeArchivo(tempDir + "/" + foto)) {
//                        pathToPhoto = urlBase + "/fotos/" + foto;
//                        if (!images.contains(pathToPhoto)) {
//                            images.add(pathToPhoto);
//                        }
//                    } else {
//                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
//                        if (!images.contains(pathToPhoto)) {
//                            images.add(pathToPhoto);
//                        }
//                    }
//
//                } catch (FileNotFoundException ex) {
//
//                }
//            }
//
//        }
    }

    public boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
    }

    public void excelExport() throws IOException {

        String[] titles = {"Zona", "Sector", "Manzana", "Solar", "PH", "Propietario", "Sector Inicial", "Manzana Inicial", "Solar Inicial"};

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "-predios filtrados" + "" + ".xlsx" + "\"");
        XSSFWorkbook wbw = new XSSFWorkbook();
        XSSFSheet sheet = wbw.createSheet("Listado de predios filtrados");

        Map<String, CellStyle> styles = createStyles(wbw);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Listado de Predios");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$X$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < 6; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        sheet.addMergedRegion(CellRangeAddress.valueOf("$P$2:$R$2"));
        headerCell = headerRow.createCell(15);
        headerCell.setCellValue(titles[6]);
        headerCell.setCellStyle(styles.get("header"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("$S$2:$U$2"));
        headerCell = headerRow.createCell(18);
        headerCell.setCellValue(titles[7]);
        headerCell.setCellStyle(styles.get("header"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("$V$2:$X$2"));
        headerCell = headerRow.createCell(21);
        headerCell.setCellValue(titles[8]);
        headerCell.setCellStyle(styles.get("header"));

        int rownum = 2;
        for (Predio predio : prediosFiltrados != null ? prediosFiltrados : predios) {
            Row row = sheet.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodZona());

            cell = row.createCell(1);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodSector());

            cell = row.createCell(2);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodManzana());

            cell = row.createCell(3);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodSolar());

            cell = row.createCell(4);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + predio.getPropiedadHorizontal());
            sheet.addMergedRegion(CellRangeAddress.valueOf("$F$" + (rownum - 1) + ":$O$" + (rownum - 1)));
            cell = row.createCell(5);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.PropietariosAsString());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$P$" + (rownum - 1) + ":$R$" + (rownum - 1)));
            cell = row.createCell(15);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + predio.getSector());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$S$" + (rownum - 1) + ":$U$" + (rownum - 1)));
            cell = row.createCell(18);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getManzana());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$V$" + (rownum - 1) + ":$X$" + (rownum - 1)));
            cell = row.createCell(21);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + predio.getSolar());

        }

        wbw.write(externalContext.getResponseOutputStream());
        context.responseComplete();
    }

    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(XSSFWorkbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short) 11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

    /**
     * @return the listaPredios
     */
    public LazyDataModelPredio getListaPredios() {
        return listaPredios;
    }

    /**
     * @param listaPredios the listaPredios to set
     */
    public void setListaPredios(LazyDataModelPredio listaPredios) {
        this.listaPredios = listaPredios;
    }
}
