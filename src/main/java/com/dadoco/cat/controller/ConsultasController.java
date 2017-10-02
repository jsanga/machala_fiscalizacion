package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.admin.service.IntegranteService;
import com.dadoco.audit.controller.HistPredial;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.bpm.utils.QuerysFlow;
import com.dadoco.cat.model.ArchivoEscritura;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.BusquedaPredial;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.ImpuestoPredialPK;
import com.dadoco.cat.model.InfoBloque;
import com.dadoco.cat.model.InfoInfraestructura;
import com.dadoco.cat.model.Integrante;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaArchivo;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.ParroquiaPK;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.PrediosSinEdificacion;
import com.dadoco.cat.model.RazonExencion;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.SectorPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TipoEscritura;
import com.dadoco.cat.model.TipoExencion;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.BloqueService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.ContribuyentePredioService;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.EscrituraService;
import com.dadoco.cat.service.ImpuestoService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.RazonExencionService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.cat.service.TipoExencionService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedDocument;
import com.dadoco.common.lazyModel.LazyDataModelPredio;
import com.dadoco.common.lazyModel.LazyDataModelPredioBusqueda;
import com.dadoco.common.lazyModel.PrediosLazy;
import com.dadoco.common.model.Archivo;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.service.ArchivoService;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.service.ValorDiscretoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.ReportesSession;
import com.dadoco.common.util.Util;
import com.dadoco.common.util.ZipUtil;
import com.dadoco.config.ConfigEmail;
import com.dadoco.config.ConfigReader;
import com.dadoco.config.GlobalVars;
import com.dadoco.flow.model.HtMensajesUsuario;
import com.dadoco.flow.service.HtTramiteService;
import com.dadoco.registrador.model.CoactivaVsam;
import com.dadoco.registrador.model.DeudaVsam;
import com.dadoco.registrador.service.RegistroService;
import com.dadoco.ren.service.AvaluoService;
import com.dadoco.search.SearchService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "consultasView")
@ViewScoped
public class ConsultasController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConsultasController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private SearchService searchService;
    @EJB
    private BloqueService bloqueService;
    @EJB
    private ArchivoService archivoService;
    @EJB
    private PredioService predioService;
    @EJB
    private RegistroService vsamServices;

    @EJB
    private ParroquiaService parroquiaService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private ManzanaService manzanaService;

    @EJB
    private TipoExencionService tipoExencionService;
    @EJB
    private RazonExencionService razonExencionService;

    @EJB
    private AvaluoService avaluoService;

    @EJB
    private AvaluosService avaluosServices;

    @EJB
    private ContribuyenteService contribuyenteService;

    @EJB
    private ContribuyentePredioService contribuyentePredioService;

    @EJB
    private ImpuestoService impuestoService;

    @EJB
    private TipoEscrituraService tipoEscrituraService;

    @EJB
    private EscrituraService escrituraService;

    @EJB
    private DeudaService deudaService;
    @EJB
    private IntegranteService integranteService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private ValorDiscretoService discretoService;
    @EJB
    protected HtTramiteService htServices;
    @EJB
    private ManagerService aclServices;
//    @EJB
//    private ServletSession sess;
    @Inject
    private ReportesSession reporte;
    @Inject
    private HistPredial audit;

    private ImpuestoPredial impuestoPredial;
    private ImpuestoPredial impuestoPredialAnterior;

    private Exencion exencion;
    private List<SelectItem> tiposExencion;
    private Integer idTipoExencion;
    private List<SelectItem> razonesExencion;
    private Integer idRazonExencion;

    private RazonExencion razonExencionSeleccionada;

    private List<Bloque> bloquesEliminar;
    private List<Piso> pisosEliminar;
    private List<BusquedaPredial> prediosF = new ArrayList<>();

    private List<PrediosSinEdificacion> prediosSinE;
    private Long totalPredios = Long.parseLong("0");
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
    private boolean exportar; // True para urbanos
    private Integer tipoPredio; // 1 urbano - 2 rural - 3 invasiones
    private HashMap<Integer, List> map;
    private List<UsoSuelo> usosBloque;
    private List<UsoSuelo> usosTerreno;
    private String email, msg;

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

    private List<OtrosTipoConstruccion> obrasComplementarias;

    private short tipoObraSeleccionada;
    private short tipoConstruccion;
    private String columna;
    private String materialLabel;
    private String unidadLabel;
    private String onkeypressevent;
    private ValorDiscreto valorDiscretoObra;

    private List<String> images;
    private List<ArchivoAux> imagesBloque;
    private List<String> docs;
    private String planoManzanero;
    private String planoPredio;
    private String fichaPredial;
    private String docUltimoDatoAutorizacion;

    private String datoBusqueda;
    private String piso;
    private String dpto;
    private String opcion;
    private String textoOpcion;
    private boolean porEdificio;
    private List<CoactivaVsam> coactivasList;
    private List<DeudaVsam> deudasList;
    private DeudaVsam deudaSelec;

    private List<Contribuyente> listaContribuyente;

    private List<Contribuyente> propietariosPredioSeleccionado;
    private List<Contribuyente> arrendatariosPredioSeleccionado;

    private double coord_X;
    private double coord_Y;
    private String claveGeoreferenciada;

    private List<InfoBloque> infoBloques;

    private Escritura escritura;

    private List<SelectItem> tiposEscrituras;
    private Long idTipoEscritura;

    private List<UploadedFile> documentos;
    private UploadedDocument[] datosDocumento;

    private List<Deuda> listaDeudas;

    private short anioEmision;

    private List<Integrante> relevamientos;
    private List<Integrante> validadores;
    private List<Integrante> supervisores;
    private List<Integrante> supervisoresDigitadores;
    private List<Integrante> cartografos;
    private List<Integrante> digitadores;

    private boolean conEdificaciones;

    private Date desde;
    private Date hasta;

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;

    private Boolean mostrarEstado;

    private List<SelectItem> parroquias;
    private List<SelectItem> zonas;
    private List<SelectItem> sectores;
    private List<SelectItem> manzanas;

    private String nombreManzana;
    private String nombreSitio;
    private String solarCod;
    private boolean manzanaEdit;
    private boolean claveCatastralEdit;
    private boolean sitioEdit;
    private BusquedaPredial predioFSelect;
    private Predio predioEdit;

    private LazyDataModelPredio listaPredios;
    private LazyDataModelPredioBusqueda listaPrediosBusqueda;
    private StreamedContent descarga;
    private PrediosLazy prediosLazy;
    private String uploadDirectory, baseUrl, tempDirectory;
    private List<String> imgsPredio;

    @PostConstruct
    public void init() {
        try {
            //this.listaPredios = new LazyDataModelPredio(predioService);
            this.listaPrediosBusqueda = new LazyDataModelPredioBusqueda(predioService, variablesService, discretoService);
            listaPredios = new LazyDataModelPredio(predioService);
            prediosSinE = new ArrayList<>();
            provinciaCod = Util.provincia_por_defecto;
            cantonCod = Util.canton_por_defecto;
            parroquias = getParroquias();
            opcionesBusqueda = new OpcionesBusquedaPredio();
            opcionesBusqueda.setProvinciaCod(provinciaCod);
            opcionesBusqueda.setCantonCod(cantonCod);
            exportar = false;
            tipoPredio = 1;
            opcionesBusqueda.setEjecutandoAccion(false);

            predioSeleccionado = new Predio();
            predios = new ArrayList<>();
            bloqueSeleccionado = new Bloque();

            bloqueSeleccionadoIndex = 0;
            pisoSeleccionadoIndex = 0;
            visualizarDatos = false;
            visualizarListado = true;
            exencion = new Exencion();
            images = new ArrayList<>();

            docs = new ArrayList<>();
            infoBloques = new ArrayList<>();
            listaContribuyente = new ArrayList<>();
            opcion = "CC";
            textoOpcion = "Clave catastral";
            //prediosFiltrados = new ArrayList<Predio>();
            propietariosPredioSeleccionado = new ArrayList<>();
            arrendatariosPredioSeleccionado = new ArrayList<>();
            porEdificio = false;
            conEdificaciones = false;

            imagesBloque = new ArrayList<>();
            documentos = new ArrayList<>();
            datosDocumento = new UploadedDocument[30];

            anioEmision = Util.ANIO_ACTUAL.shortValue();

            usosBloque = new ArrayList<>();
            usosTerreno = new ArrayList<>();

            SimpleDateFormat fechaFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            mostrarEstado = false;
            try {
                Date fechaCorrecta = fechaFormat.parse("26-07-2016 00:00:00");
                totalPredios = searchService.totalPredios(fechaCorrecta);
            } catch (ParseException pex) {
                System.out.print("Error " + pex);
            }
            changeList();
            imgsPredio = new ArrayList();
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key") != null) {
                this.MostrarDatosPredioSeleccionado(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List getListClases(Integer i) {
        return map.get(i);
    }

    public void changeList() {
        try {
            if (tipoPredio == 1) {
                prediosLazy = new PrediosLazy(true);
            }
            if (tipoPredio == 2) {
                prediosLazy = new PrediosLazy(false);
            }
            if (tipoPredio == 3) {
                prediosLazy = new PrediosLazy();
            }
            if (tipoPredio == 4) {
                prediosLazy = new PrediosLazy(4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTextoValorDiscreto(Integer idVariable, Short valor) {
        return variablesService.getTextoVariableDiscretaByIdVariableAndValor(idVariable, valor, Util.ANIO_ACTUAL.shortValue());
    }

    public List<UsoSuelo> getUsosBloque() {
        return usosBloque;
    }

    public void setUsosBloque(List<UsoSuelo> usosBloque) {
        this.usosBloque = usosBloque;
    }

    public List<UsoSuelo> getUsosTerreno() {
        return usosTerreno;
    }

    public void setUsosTerreno(List<UsoSuelo> usosTerreno) {
        this.usosTerreno = usosTerreno;
    }

    public AvaluoService getAvaluoService() {
        return avaluoService;
    }

    public void setAvaluoService(AvaluoService avaluoService) {
        this.avaluoService = avaluoService;
    }

    public List<OtrosTipoConstruccion> getObrasComplementarias() {
        return obrasComplementarias;
    }

    public void setObrasComplementarias(List<OtrosTipoConstruccion> obrasComplementarias) {
        this.obrasComplementarias = obrasComplementarias;
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

    public BloqueService getBloqueService() {
        return bloqueService;
    }

    public void setBloqueService(BloqueService bloqueService) {
        this.bloqueService = bloqueService;
    }

    public boolean isConEdificaciones() {
        return conEdificaciones;
    }

    public void setConEdificaciones(boolean conEdificaciones) {
        this.conEdificaciones = conEdificaciones;
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public List<Integrante> getDigitadores() {
        digitadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 3);
        return digitadores;
    }

    public void setDigitadores(List<Integrante> digitadores) {
        this.digitadores = digitadores;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    public List<Integrante> getRelevamientos() {
        relevamientos = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 0);
        return relevamientos;
    }

    public void setRelevamientos(List<Integrante> relevamientos) {
        this.relevamientos = relevamientos;
    }

    public List<Integrante> getValidadores() {
        validadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 2);
        return validadores;
    }

    public void setValidadores(List<Integrante> validadores) {
        this.validadores = validadores;
    }

    public List<ArchivoAux> getImagesBloque() {
        return imagesBloque;
    }

    public void setImagesBloque(List<ArchivoAux> imagesBloque) {
        this.imagesBloque = imagesBloque;
    }

    public List<Integrante> getSupervisores() {
        supervisores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 1);
        return supervisores;
    }

    public void setSupervisores(List<Integrante> supervisores) {
        this.supervisores = supervisores;
    }

    public List<Integrante> getSupervisoresDigitadores() {
        supervisoresDigitadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 4);
        return supervisoresDigitadores;
    }

    public void setSupervisoresDigitadores(List<Integrante> supervisoresDigitadores) {
        this.supervisoresDigitadores = supervisoresDigitadores;
    }

    public List<Integrante> getCartografos() {
        cartografos = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 5);
        return cartografos;
    }

    public void setCartografos(List<Integrante> cartografos) {
        this.cartografos = cartografos;
    }

    public short getAnioEmision() {

        anioEmision = Util.ANIO_ACTUAL.shortValue();
        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

    public String getDocUltimoDatoAutorizacion() {
        return docUltimoDatoAutorizacion;
    }

    public void setDocUltimoDatoAutorizacion(String docUltimoDatoAutorizacion) {
        this.docUltimoDatoAutorizacion = docUltimoDatoAutorizacion;
    }

    public boolean esFichaMadre(Predio predio) {
        return predioService.getEsFichaMadre(predio);
    }

    public List<String> propietariosAsCombo(Integer id) {

        Predio p = predioService.find(id);
        String[] valores = {" "};

        if (!p.getPropietarios().isEmpty()) {
            List<Contribuyente> prop = p.getPropietarios();
            valores = new String[prop.size()];

            for (int i = 0; i < prop.size(); i++) {
                valores[i] = prop.get(i).getIdentificacion() + " " + prop.get(i).getNombreCompleto();
            }
        } else {
            valores[0] = "SIN PROPIETARIOS";
        }

        return Arrays.asList(valores);
    }

    public void buscarPredio() {
        prediosF = new ArrayList<BusquedaPredial>();
        predios = new ArrayList<Predio>();
        String palabraClave = opcionesBusqueda.getPalabraClave();

        this.listaPredios.setBusqueda(palabraClave);
        this.listaPrediosBusqueda.setBusqueda(palabraClave);
        exportar = true;
    }

    public void buscarPredioByParameter() {
        List<PrediosSinEdificacion> auxPrediosSinE = new ArrayList<>();
        List<PrediosSinEdificacion> auxPrediosSinE2 = new ArrayList<>();
        prediosSinE = new ArrayList<>();
        predios = new ArrayList<>();
        PrediosSinEdificacion predioSE;
        try {

            List<ValorDiscreto> valoresD = variablesService.obtenerValores("cat_predio", "dominio", anioEmision);
            List<ValorDiscreto> valoresE = variablesService.obtenerValores("cat_terreno", "estado", anioEmision);
            predios = searchService.buscarByParametros(desde, hasta);

            exportar = true;
            if (!predios.isEmpty()) {

                for (Predio p : predios) {
                    predioSE = new PrediosSinEdificacion();
                    predioSE.setProvincia("" + p.getTerreno().getTerrenoPK().getCodProvincia());
                    predioSE.setCanton("" + p.getTerreno().getTerrenoPK().getCodCanton());
                    predioSE.setParroquia("" + p.getTerreno().getTerrenoPK().getCodParroquia());
                    predioSE.setZona("" + p.getTerreno().getTerrenoPK().getCodZona());
                    predioSE.setSector("" + p.getTerreno().getTerrenoPK().getCodSector());
                    predioSE.setManzana("" + p.getTerreno().getTerrenoPK().getCodManzana());
                    predioSE.setSolar("" + p.getTerreno().getTerrenoPK().getCodSolar());
                    predioSE.setClaveCatastral(p.getClaveCatastral());
                    predioSE.setClaveAnterior(p.getClaveAnterior());
                    String dominio = "";
                    for (ValorDiscreto val : valoresD) {
                        if (val.getValorDiscretoPK().getValor() == p.getDominio()) {
                            dominio = val.getTexto();
                        }
                    }
                    predioSE.setDominio(dominio);
                    String estado = "";
                    for (ValorDiscreto val2 : valoresE) {
                        if (val2.getValorDiscretoPK().getValor() == p.getTerreno().getEstado()) {
                            estado = val2.getTexto();
                        }
                    }
                    predioSE.setEstado((p.getTerreno() != null) ? estado : "");
                    auxPrediosSinE.add(predioSE);
                }
                int cont = 0;
                while (cont < 2) {
                    for (PrediosSinEdificacion pSE : auxPrediosSinE) {
                        predioSE = new PrediosSinEdificacion();
                        predioSE = pSE;
                        if (pSE.getProvincia().length() == 1) {
                            predioSE.setProvincia("0" + pSE.getProvincia());
                        }
                        if (pSE.getCanton().length() == 1) {
                            predioSE.setCanton("0" + pSE.getCanton());
                        }
                        if (pSE.getParroquia().length() == 1) {
                            predioSE.setParroquia("0" + pSE.getParroquia());
                        }
                        if (pSE.getZona().length() == 1) {
                            predioSE.setZona("0" + pSE.getZona());
                        }
                        if (pSE.getSector().length() == 1) {
                            predioSE.setSector("0" + pSE.getSector());
                        }
                        if (pSE.getManzana().length() <= 2) {
                            predioSE.setManzana("0" + pSE.getManzana());
                        }
                        if (pSE.getSolar().length() <= 2) {
                            predioSE.setSolar("0" + pSE.getSolar());
                        }
                        predioSE.setClaveCatastral(pSE.getProvincia().concat(pSE.getCanton()
                                .concat(pSE.getParroquia().concat(pSE.getSector().concat(pSE.getManzana().concat(pSE.getSolar()))))));
                        if (cont < 1) {
                            auxPrediosSinE2.add(predioSE);
                        } else {
                            prediosSinE.add(predioSE);
                        }
                    }
                    if (cont < 1) {
                        auxPrediosSinE = auxPrediosSinE2;
                    }
                    cont++;
                }

            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
            exportar = false;
        }

    }

    private String cadenaClaves(List<String> claves) {
        String result = "";
        if (!claves.isEmpty()) {
            int len = claves.size();
            for (int i = 0; i < len; i++) {
                result += (result != "") ? "," : "";
                result += "'" + claves.get(i) + "'";
            }

        }

        return result;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public void adicionarConstruccion() {

    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public ArchivoService getArchivoService() {
        return archivoService;
    }

    public void setArchivoService(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    public DeudaService getDeudaService() {
        return deudaService;
    }

    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    public List<Deuda> getListaDeudas() {
        return listaDeudas;
    }

    public void setListaDeudas(List<Deuda> listaDeudas) {
        this.listaDeudas = listaDeudas;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
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

    public ImpuestoPredial getImpuestoPredialAnterior() {
        return impuestoPredialAnterior;
    }

    public void setImpuestoPredialAnterior(ImpuestoPredial impuestoPredialAnterior) {
        this.impuestoPredialAnterior = impuestoPredialAnterior;
    }

    public EscrituraService getEscrituraService() {
        return escrituraService;
    }

    public void setEscrituraService(EscrituraService escrituraService) {
        this.escrituraService = escrituraService;
    }

    public List<String> getDocs() {
        return docs;
    }

    public void setDocs(List<String> docs) {
        this.docs = docs;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public String getFichaPredial() {
        return fichaPredial;
    }

    public void setFichaPredial(String fichaPredial) {
        this.fichaPredial = fichaPredial;
    }

    public TipoEscrituraService getTipoEscrituraService() {
        return tipoEscrituraService;
    }

    public void setTipoEscrituraService(TipoEscrituraService tipoEscrituraService) {
        this.tipoEscrituraService = tipoEscrituraService;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public List<SelectItem> getTiposEscrituras() {
        List<TipoEscritura> escr = tipoEscrituraService.findAll();
        tiposEscrituras = new ArrayList<SelectItem>();
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

    public List<InfoBloque> getInfoBloques() {
        return infoBloques;
    }

    public void setInfoBloques(List<InfoBloque> infoBloques) {
        this.infoBloques = infoBloques;
    }

    public List<String> getDominios() {
        List<ValorDiscreto> valoresD = variablesService.obtenerValores("cat_predio", "dominio", anioEmision);
        String[] valores = new String[valoresD.size()];
        for (int i = 0; i < valoresD.size(); i++) {
            valores[i] = valoresD.get(i).getTexto();
        }
        return Arrays.asList(valores);
    }

    public boolean isPorEdificio() {
        return porEdificio;
    }

    public void setPorEdificio(boolean porEdificio) {
        this.porEdificio = porEdificio;
    }

    public String getPlanoManzanero() {
        return planoManzanero;
    }

    public void setPlanoManzanero(String planoManzanero) {
        this.planoManzanero = planoManzanero;
    }

    public String getPlanoPredio() {
        return planoPredio;
    }

    public void setPlanoPredio(String planoPredio) {
        this.planoPredio = planoPredio;
    }

    public ImpuestoService getImpuestoService() {
        return impuestoService;
    }

    public void setImpuestoService(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public ImpuestoPredial getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(ImpuestoPredial impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
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
        razonesExencion = new ArrayList<SelectItem>();
        if (val != null) {
            List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findByIdTipoExencion", val);

            if (!list.isEmpty()) {
                razonExencionSeleccionada = list.get(0);
                idRazonExencion = razonExencionSeleccionada.getId();
                for (RazonExencion re : list) {
                    razonesExencion.add(new SelectItem(re.getId(), re.getRazonExencion()));
                }
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
        pisos = bloqueSeleccionado.getPisos();
        if (!pisos.isEmpty()) {
            pisoSeleccionadoIndex = 0;
            pisoSeleccionado = pisos.get(0);
        }
//        pisos = bloqueSeleccionado.getListaPisos();
        editandoBloque = true;
        fotoBloque(bloqueSeleccionado.getId());
        usosBloque = bloqueSeleccionado.getUsosSuelo();
    }
//</editor-fold>

    public void cambiarPiso() {
        pisoSeleccionado = pisos.get(pisoSeleccionadoIndex);
    }

    public String cancelarModificacion() {
        visualizarDatos = false;
        visualizarListado = true;
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public void prepararNotificacion(CoactivaVsam cv) {
        try {
            msg = "El predio: " + cv.getClaveCatastral() + " se encuentra en un proceso de coactiva del año " + Util.convertirStringDate(cv.getFecha_coactiva().substring(0, 10)) + " a "
                    + "nombre de " + cv.getNombresContribuyente() + ", por lo que se recomienda acercarse a cancelar el valor de $" + cv.getValor()
                    + " inmediatamente.";
            JsfUti.update("frmNotificacion");
            JsfUti.executeJS("PF('dlgNotificacion').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje() {
        if (this.email != null && this.msg != null) {
            try {
                ConfigEmail configs = aclServices.getEm().find(ConfigEmail.class, 1L);
                HtMensajesUsuario msj;
                msj = aclServices.getEm().find(HtMensajesUsuario.class, 6L);
                htServices.sendBasicEmail(this.email, msj.getDescripcionMensaje(), msj.getHeaderMail() + this.msg + msj.getFooterMail(), configs, null);
                JsfUti.messageInfo(null, "Mensaje", "Notificación enviada con éxito");
            } catch (Exception e) {
                e.printStackTrace();
                JsfUti.messageError(null, "Mensaje", "Hubo un error al enviar la notificación.");
            }
        }
    }

    public void consultarEstado(String claveAnterior) {
        List<Object[]> coactivas = null;
        List<Object[]> deudas = null;
        if (claveAnterior != null && !claveAnterior.equals("")) {
            visualizarDatos = false;
            coactivas = vsamServices.getCoactivaPorPredio(claveAnterior);
            deudas = vsamServices.getDeudasPorPredio(claveAnterior);
            if (coactivas != null && !coactivas.isEmpty()) {
                JsfUti.executeJS("PF('dlgAvisoCoactiva').show();");
            }
            mostrarEstado = true;
            this.llenarData(coactivas, deudas);
        } else {
            mostrarEstado = false;
            JsfUti.messageInfo(null, "Mensaje", "El predio no tiene clave catastral anterior.");
        }
    }

    public void llenarData(List<Object[]> coactivas, List<Object[]> deudas) {

        this.coactivasList = new ArrayList();
        for (Object[] ob : coactivas) {
            this.coactivasList.add(new CoactivaVsam(ob));
        }

        this.deudasList = new ArrayList();
        for (Object[] ob : deudas) {
            this.deudasList.add(new DeudaVsam(ob[0] + "", ob[1] + "", ob[2] + "", ob[3] + "", ob[4] + "", ob[5] + "", ob[6] + "", ob[7] + "",
                    ob[8] + "", ob[9] + "", ob[10] + "", ob[11] + "", ob[12] + "", ob[13] + "", ob[14] + "", ob[15] + "", ob[16] + "", ob[17] + "", ob[18] + "",
                    ob[19] + "", ob[20] + "", ob[21] + "", ob[22] + "", ob[23] + "", ob[24] + "", ob[25] + ""));
        }
    }

    public void mostrarRubros(DeudaVsam deuda) {
        this.deudaSelec = deuda;
        JsfUti.update("frmRubros");
        JsfUti.executeJS("PF('dlgRubros').show()");
    }

    public void MostrarDatosPredioSeleccionado(Object id) {

        // short anioEmision = (short) variablesService.obtenerCoeficiente("datos_configuracion", "anio_emision", (short) 0, anioEmision);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        if (id instanceof Integer) {
            predioSeleccionado = predioService.find(Integer.parseInt(id.toString()));
        } else {
            predioSeleccionado = predioService.findByCodigoCatastroSF(id.toString());
        }
        mostrarEstado = false;
        fotoPredio(predioSeleccionado.getId());
        short anioAnterior = (short) (anioEmision - 1);
        ImpuestoPredialPK pk = new ImpuestoPredialPK(predioSeleccionado.getId(), anioEmision);

        ImpuestoPredialPK pka = new ImpuestoPredialPK(predioSeleccionado.getId(), anioAnterior);

        impuestoPredial = impuestoService.find(pk);
        impuestoPredialAnterior = impuestoService.find(pka);

        if (numberFormat instanceof DecimalFormat) {
            DecimalFormat anotherDFormat = (DecimalFormat) numberFormat;
            anotherDFormat.applyPattern("#.00");
            anotherDFormat.setGroupingUsed(true);
            anotherDFormat.setGroupingSize(3);

            if (impuestoPredial != null) {

                avaluoTerreno = "" + numberFormat.format(impuestoPredial.getAvaluoTerreno());
                areaTerreno = "" + numberFormat.format(impuestoPredial.getAreaTerreno());

                avaluoConstruccion = "" + numberFormat.format(impuestoPredial.getAvaluoEdificacion());

                areaConstruccion = "" + numberFormat.format(impuestoPredial.getAreaConstruccion());
                valorTerreno = "" + numberFormat.format(impuestoPredial.getAvaluoTerreno() / impuestoPredial.getAreaTerreno());

                avaluoComplementarias = "" + numberFormat.format(impuestoPredial.getAvaluoComplementarias());

                avaluoPredial = "" + numberFormat.format(impuestoPredial.getImpuestoPredial());
            } else {
                impuestoPredial = new ImpuestoPredial();
            }
        }

        if (impuestoPredialAnterior == null) {
            impuestoPredialAnterior = new ImpuestoPredial();
        }

        if (!predioSeleccionado.getPropietarios().isEmpty()) {
            for (int i = 0; i < predioSeleccionado.getPropietarios().size(); i++) {
                predioSeleccionado.getPropietarios().get(i).obtenerEstatus(predioSeleccionado.getId());
            }
        }
        obrasComplementarias = predioSeleccionado.getOtrosConstruccion();

        infoBloques = creaListaBloques();

        visualizarDatos = true;

        bloques = predioSeleccionado.getBloques();
        if (!bloques.isEmpty()) {
            conEdificaciones = true;
            bloqueSeleccionadoIndex = 0;
            bloqueSeleccionado = bloques.get(0);
            usosBloque = bloqueSeleccionado.getUsosSuelo();
            fotoBloque(bloqueSeleccionado.getId());
            pisos = bloqueSeleccionado.getPisos();
            if (!pisos.isEmpty()) {
                pisoSeleccionado = pisos.get(0);
                pisoSeleccionadoIndex = 0;
            }

        } else {
            imagesBloque = new ArrayList<>();
            conEdificaciones = false;
            bloqueSeleccionado = new Bloque();
            pisoSeleccionado = new Piso();
            bloques = new ArrayList<>();

        }

        List<Escritura> escrituras = predioSeleccionado.getEscrituras();

        if (escrituras == null || !escrituras.isEmpty()) {
            escritura = escrituras.get(escrituras.size() - 1);
            documentosPredio(escritura);
        } else {
            escritura = new Escritura();
        }

//
//        if (!escrituras.isEmpty()) {
//            escritura = escrituras.get(escrituras.size() - 1);
////           
////            idTipoEscritura = escritura.getTipo().getId();
//            documentosPredio(escritura);
//        } else {
//            escritura = new Escritura();
//        }
//        if (predioSeleccionado.getPropiedadHorizontal() != 0) {
//            Predio padre = catastroService.obtenerPredio(predioSeleccionado.getTerreno(), (short) 0);
//            if (padre != null) {
//                fotoPredio(padre.getId());
//                predioSeleccionado.setEdificio(padre.getEdificio());
//                predioSeleccionado.setBarrio(padre.getBarrio());
//                predioSeleccionado.setDireccion(padre.getDireccion());
//                predioSeleccionado.setTipoUbicacion(padre.getTipoUbicacion());
//                predioSeleccionado.setManzana(padre.getManzana());
//                predioSeleccionado.setSolar(padre.getSolar());
//                predioSeleccionado.setSector(padre.getSector());
//            } else {
//                fotoPredio(predioSeleccionado.getId());
//            }
//        } else {
//            fotoPredio(predioSeleccionado.getId());
//        }
//        fotoPlanoManzanero(predioSeleccionado.getTerreno().getManzana().getManzanaPK());
//        fotoFicha(predioSeleccionado);
//        fotoPlanoSolar(predioSeleccionado);
//        dotosAutorizacionPredio(predioSeleccionado);
        if (predioSeleccionado.getContratoArrendamiento() != null) {

//            for (ContribuyentePredio cp : predioSeleccionado.getContribuyentePredioList()) {
//                if (cp.getStatus() == 0) {
//                    propietariosPredioSeleccionado.add(cp.getContribuyente());
//                }
//                if (cp.getStatus() == 1) {
//                    arrendatariosPredioSeleccionado.add(cp.getContribuyente());
//                }
//            }
        } else {
            propietariosPredioSeleccionado = predioSeleccionado.getPropietarios();
        }

        Object[] coord = catastroService.coordenadas(predioSeleccionado.getClaveCatastral());
        if (coord != null) {
            coord_X = Double.parseDouble(coord[0].toString());
            coord_Y = Double.parseDouble(coord[1].toString());
            claveGeoreferenciada = coord[2].toString();
        }
        map = new HashMap<>();
        if (!predioSeleccionado.getTipoPredio()) {
            map.put(1, predioService.getListaClaseTerrenoRural(1, predioSeleccionado));
            map.put(2, predioService.getListaClaseTerrenoRural(2, predioSeleccionado));
            map.put(3, predioService.getListaClaseTerrenoRural(3, predioSeleccionado));
            map.put(4, predioService.getListaClaseTerrenoRural(4, predioSeleccionado));
            map.put(5, predioService.getListaClaseTerrenoRural(5, predioSeleccionado));
            map.put(6, predioService.getListaClaseTerrenoRural(6, predioSeleccionado));
        }
        //escritura = predioSeleccionado.getEscrituras() == null || predioSeleccionado.getEscrituras().isEmpty() ? null : predioSeleccionado.getEscrituras().get(0);
        terreno = predioSeleccionado.getTerreno();

//        listaDeudas = deudaService.findByNamedQuery("Deuda.findByClaveCatastral", predioSeleccionado.getClaveCatastral());
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.update("datosConsultas,predio-list-form,datos-tab");
    }

    public void onTipoExencionChange() {

        Integer val = idTipoExencion;
        List<RazonExencion> list = razonExencionService.findByNamedQuery("RazonExencion.findByIdTipoExencion", val);

        razonesExencion = new ArrayList<>();
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
        images = new ArrayList<>();
        System.out.println("Cantidad de imagenes del predio: " + p.getImages().size());

        if (!p.getImages().isEmpty()) {

            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
            baseUrl = urlBase;
            uploadDirectory = uploadDir;
            tempDirectory = tempDir;

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

                }
            }

        }
//         fotoFicha(p);
//         fotoPlanoSolar(p);
    }

    private void fotoBloque(int idBloque) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Bloque b = bloqueService.findByNamedQuery("Bloque.findByID", idBloque).get(0);

        String foto;
        String pathToPhoto = "";
        imagesBloque = new ArrayList<>();

        if (!b.getImages().isEmpty()) {

            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");

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
                        if (!imagesBloque.contains(aux)) {
                            imagesBloque.add(aux);
                        }
                    } else {
                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
                        aux.setRuta(pathToPhoto);
                        if (!imagesBloque.contains(aux)) {
                            imagesBloque.add(aux);
                        }
                    }
                    System.out.println("pathFoto " + pathToPhoto + "  " + urlBase);
                } catch (FileNotFoundException ex) {

                }
            }
        }

    }

    public void documentosPredio(Escritura esc) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        String doc;
        String pathToDoc = "";
        docs = new ArrayList<String>();

        //esc = escrituraService.find(esc.getId());
        List<ArchivoEscritura> documents = escritura.getAdjuntos();

        if (documents != null) {

            String uploadDir = config.getDbConfiguration().getString("directorio_documentos");

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/documentos";

            for (ArchivoEscritura img : documents) {

                doc = img.getRuta();

                try {
                    if (existeArchivo(uploadDir + "/" + doc)) {
                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + doc);
                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + doc);

                        try {
                            IOUtils.copy(inFile, outFile);
                            inFile.close();
                            outFile.close();
                        } catch (IOException ex) {

                        }
                    }

                    if (existeArchivo(tempDir + "/" + doc)) {
                        pathToDoc = urlBase + "/documentos/" + doc;
                        if (!docs.contains(pathToDoc)) {
                            docs.add(pathToDoc);
                        }
                    }

                } catch (FileNotFoundException ex) {

                }
            }

        }

    }

    public void dotosAutorizacionPredio(Predio p) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        String doc = "";
        String pathToDoc = "";

        //esc = escrituraService.find(esc.getId());
        List<DatosAutorizacion> autorizacionDatos = null;//p.getDatosAutorizacionList();

        if (!autorizacionDatos.isEmpty()) {

            String uploadDir = config.getDbConfiguration().getString("directorio_documentos");

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/documentos";

            DatosAutorizacion da = autorizacionDatos.get(autorizacionDatos.size() - 1);

//            if (da.getDocumento() != null) {
//
//                doc = da.getDocumento().getRuta();
//
//                try {
//                    if (existeArchivo(uploadDir + "/" + doc)) {
//                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + doc);
//                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + doc);
//
//                        try {
//                            IOUtils.copy(inFile, outFile);
//                            inFile.close();
//                            outFile.close();
//                        } catch (IOException ex) {
//                        }
//                    }
//
//                    if (existeArchivo(tempDir + "/" + doc)) {
//                        docUltimoDatoAutorizacion = urlBase + "/documentos/" + doc;
//                    }
//
//                } catch (FileNotFoundException ex) {
//
//                }
//            }
        }

    }

    public void fotoPlanoManzanero(ManzanaPK pk) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Manzana m = manzanaService.find(pk);

        String foto;

        if (m.getManzanero() != null) {

            String uploadDir = config.getDbConfiguration().getString("directorio_manzaneros");

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";

            ManzanaArchivo img = m.getManzanero();

            foto = img.getRuta();

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
                    planoManzanero = urlBase + "/fotos/" + foto;
                } else {
                    planoManzanero = urlBase + "/fotos/nofoto.jpg";
                }

            } catch (FileNotFoundException ex) {

            }

        } else {
            planoManzanero = urlBase + "/fotos/nofoto.jpg";
        }
    }

    public void fotoPlanoSolar(Predio p) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        String foto;

//        if (p.getPlano() != null) {
//
//            String uploadDir = config.getDbConfiguration().getString("directorio_planos_solar");
//
//            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
//
//            Archivo img = p.getPlano();
//
//            foto = img.getRuta();
//
//            try {
//                if (existeArchivo(uploadDir + "/" + foto)) {
//                    FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
//                    FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);
//
//                    try {
//                        IOUtils.copy(inFile, outFile);
//                        inFile.close();
//                        outFile.close();
//                    } catch (IOException ex) {
//
//                    }
//                }
//
//                if (existeArchivo(tempDir + "/" + foto)) {
//                    planoPredio = urlBase + "/fotos/" + foto;
//                } else {
//                    planoPredio = urlBase + "/fotos/nofoto.jpg";
//                }
//
//            } catch (FileNotFoundException ex) {
//
//            }
//
//        } else {
//            planoPredio = urlBase + "/fotos/nofoto.jpg";
//        }
    }

    public void fotoFicha(Predio p) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        String foto;

//        if (p.getFicha() != null) {
//
//            String uploadDir = config.getDbConfiguration().getString("directorio_ficha_escaneada");
//
//            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
//
//            Archivo img = p.getFicha();
//
//            foto = img.getRuta();
//
//            try {
//                if (existeArchivo(uploadDir + "/" + foto)) {
//                    FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
//                    FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);
//
//                    try {
//                        IOUtils.copy(inFile, outFile);
//                        inFile.close();
//                        outFile.close();
//                    } catch (IOException ex) {
//
//                    }
//                }
//
//                if (existeArchivo(tempDir + "/" + foto)) {
//                    fichaPredial = urlBase + "/fotos/" + foto;
//                } else {
//                    fichaPredial = urlBase + "/fotos/nofoto.jpg";
//                }
//
//            } catch (FileNotFoundException ex) {
//
//            }
//
//        } else {
//            fichaPredial = urlBase + "/fotos/nofoto.jpg";
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

    public List<InfoBloque> creaListaBloques() {

        List<InfoBloque> lista = avaluoService.infoBloques(predioSeleccionado, (short) (anioEmision));
        return lista;
    }

    public void excelExport() throws IOException {

        String[] titles = {"Parroquia", "Zona", "Sector", "Manzana", "Solar", "PH", "Propietario", "Sector Inicial", "Manzana Inicial", "Solar Inicial"};

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
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$Y$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < 6; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        sheet.addMergedRegion(CellRangeAddress.valueOf("$G$2:$P$2"));
        headerCell = headerRow.createCell(6);
        headerCell.setCellValue(titles[6]);
        headerCell.setCellStyle(styles.get("header"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("$Q$2:$S$2"));
        headerCell = headerRow.createCell(16);
        headerCell.setCellValue(titles[7]);
        headerCell.setCellStyle(styles.get("header"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("$T$2:$V$2"));
        headerCell = headerRow.createCell(19);
        headerCell.setCellValue(titles[8]);
        headerCell.setCellStyle(styles.get("header"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("$W$2:$Y$2"));
        headerCell = headerRow.createCell(22);
        headerCell.setCellValue(titles[9]);
        headerCell.setCellStyle(styles.get("header"));

        int rownum = 2;
        for (Predio predio : prediosFiltrados != null ? prediosFiltrados : predios) {
            Row row = sheet.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodParroquia());

            cell = row.createCell(1);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodZona());

            cell = row.createCell(2);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodSector());

            cell = row.createCell(3);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodManzana());

            cell = row.createCell(4);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getTerreno().getTerrenoPK().getCodSolar());

            cell = row.createCell(5);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + predio.getPropiedadHorizontal());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$G$" + (rownum) + ":$P$" + (rownum)));
            cell = row.createCell(6);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.PropietariosAsString());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$Q$" + (rownum) + ":$S$" + (rownum)));
            cell = row.createCell(16);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + predio.getSector());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$T$" + (rownum) + ":$V$" + (rownum)));
            cell = row.createCell(19);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue("" + predio.getManzana());

            sheet.addMergedRegion(CellRangeAddress.valueOf("$W$" + (rownum) + ":$Y$" + (rownum)));
            cell = row.createCell(22);
            cell.setCellStyle(styles.get("cell"));
//            cell.setCellValue("" + predio.getSolar());

        }

        wbw.write(externalContext.getResponseOutputStream());
        context.responseComplete();
    }

//    //<editor-fold defaultstate="collapsed" desc="PredialExcelExport">
//    public void predialExcelExport() throws IOException {
//        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        if (!predios.isEmpty()) {
//            String[] titles = {"F. Ingreso", "F. Actualización", "Propietario", "Código Anterior", "Código Actual", "Manzana", "Superficie",
//                "Perimetro",
//                "A.Construcción", "Uso de Suelo", "A.Otros Tip. Construcción", "#Niveles", "Estado", "Dominio", "Foto Predio", "Foto Bloque",
//                "Sitio", "#Predio", "Geometría",
//                "Lidero Norte", "Longitud", "Lidero Sur", "Longitud.", "Lidero Este", "Longitud", "Lidero Oeste", "Longitud.",
//                "A. Vía Pública",
//                "A. de Acera",
//                "Relevamiento", "Sup. Relev.", "C. Calidad", "Digitalización", "Digitación", "Sup. Digitación"};
//            Date hoy = new Date();
//            FacesContext context = FacesContext.getCurrentInstance();
//            ExternalContext externalContext = context.getExternalContext();
//            externalContext.responseReset();
//            externalContext.setResponseContentType("application/vnd.ms-excel");
//            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + ((opcionesBusqueda.getPalabraClave().equals(""))
//                    ? fechaFormat.format(hoy) : opcionesBusqueda.getPalabraClave()) + "-predios filtrados" + "" + ".xlsx" + "\"");
//
//            XSSFWorkbook wbw = new XSSFWorkbook();
//            Map<String, CellStyle> styles = createStyles(wbw);
//
//            List<Usuario> listDigitadores = new ArrayList<Usuario>();
//            listDigitadores = usuarioService.userByRol(57);
//            for (Usuario digitador : listDigitadores) {
//                int rownum = 1;
//                //header row  
//                XSSFSheet sheet = wbw.createSheet(digitador.getUsername());
//
//                PrintSetup printSetup = sheet.getPrintSetup();
//                printSetup.setLandscape(true);
//                sheet.setFitToPage(true);
//                sheet.setHorizontallyCenter(true);
//
//                //title row
//                Row titleRow = sheet.createRow(0);
//                titleRow.setHeightInPoints(45);
//                Cell titleCell = titleRow.createCell(0);
//                titleCell.setCellValue("Listado de Predios");
//                titleCell.setCellStyle(styles.get("title"));
//                sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$AI$1"));
//
//                Row headerRow = sheet.createRow(rownum++);
//                headerRow.setHeightInPoints(40);
//                Cell headerCell;
//                Cell headerCell2;
//                headerCell2 = headerRow.createCell(0);
//                headerCell2.setCellValue("Digitador: ");
//                sheet.autoSizeColumn(0);
//                headerCell2.setCellStyle(styles.get("header"));
//                headerCell2 = headerRow.createCell(1);
//                headerCell2.setCellValue(digitador.getNombre() + " " + digitador.getApellidos());
//                sheet.autoSizeColumn(1);
//                headerCell2 = headerRow.createCell(3);
//                headerCell2.setCellValue("Manzana: ");
//                sheet.autoSizeColumn(3);
//                headerCell2.setCellStyle(styles.get("header"));
//                headerCell2 = headerRow.createCell(4);
//                sheet.autoSizeColumn(4);
//
//                Row headerRow2 = sheet.createRow(rownum++);
//                for (int i = 0; i < titles.length; i++) {
//                    headerCell = headerRow2.createCell(i);
//                    headerCell.setCellValue(titles[i]);
//                    sheet.autoSizeColumn(i);
//                    headerCell.setCellStyle(styles.get("header"));
//
//                }
//                int totalPredios2 = 0;
//                for (BusquedaPredial predio : prediosF) {
//                    if (predio.getUsuario().toLowerCase().equals(digitador.getUsername().toLowerCase())) {
//                        headerCell2.setCellValue(predio.getManzana());
//
//                        for (int i = 0; i < titles.length; i++) {
//                            sheet.autoSizeColumn(i);
//                        }
//                        totalPredios2++;
//                        Row row = sheet.createRow(rownum++);
//                        Cell cell = row.createCell(0);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(fechaFormat.format(predio.getFIngreso()));
//
//                        cell = row.createCell(1);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(fechaFormat.format(predio.getFModificacion()));
//
//                        cell = row.createCell(2);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getPropietario());
//
//                        cell = row.createCell(3);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getCod_anterior());
//
//                        cell = row.createCell(4);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getCod_actual());
//
//                        cell = row.createCell(5);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getManzana());
//
//                        cell = row.createCell(6);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue("" + predio.getSuperficie());
//
//                        cell = row.createCell(7);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue("" + predio.getPerimetro());
//
//                        cell = row.createCell(8);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue("" + predio.getArea_construccion());
//
//                        cell = row.createCell(9);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getUso_suelo());
//
//                        cell = row.createCell(10);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getOtros_construccion());
//
//                        cell = row.createCell(11);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getNiveles());
//
//                        cell = row.createCell(12);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getEstado());
//
//                        cell = row.createCell(13);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getDominio());
//
//                        cell = row.createCell(14);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getFotoPredio());
//
//                        cell = row.createCell(15);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getFotoBloque());
//
//                        cell = row.createCell(16);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getSitio());
//
//                        cell = row.createCell(17);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getPredio());
//
//                        cell = row.createCell(18);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getGeometria());
//
//                        cell = row.createCell(19);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_norte());
//
//                        cell = row.createCell(20);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_norte_long());
//
//                        cell = row.createCell(21);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_sur());
//
//                        cell = row.createCell(22);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_sur_long());
//
//                        cell = row.createCell(23);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_este());
//
//                        cell = row.createCell(24);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_este_long());
//
//                        cell = row.createCell(25);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_oeste());
//
//                        cell = row.createCell(26);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getLind_oeste_long());
//
//                        cell = row.createCell(27);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getAncho_via_pub());
//
//                        cell = row.createCell(28);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getAncho_acera());
//
//                        cell = row.createCell(29);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getRelevamiento());
//
//                        cell = row.createCell(30);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getSup_relevamiento());
//
//                        cell = row.createCell(31);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getC_calidad());
//
//                        cell = row.createCell(32);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getDigitalizacion());
//
//                        cell = row.createCell(33);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getDigitacion());
//
//                        cell = row.createCell(34);
//                        cell.setCellStyle(styles.get("cell"));
//                        cell.setCellValue(predio.getSup_digitacion());
//                    }
//                }
//                headerRow = sheet.createRow(rownum++);
//                headerRow.setHeightInPoints(40);
//                headerCell = headerRow.createCell(0);
//                headerCell.setCellValue("Total: ");
//                sheet.autoSizeColumn(0);
//                headerCell.setCellStyle(styles.get("header"));
//                headerCell = headerRow.createCell(1);
//                headerCell.setCellValue(totalPredios2);
//                sheet.autoSizeColumn(1);
//            }
//            wbw.write(externalContext.getResponseOutputStream());
//            context.responseComplete();
//        } else {
//            JsfUtil.addWarningMessage("Advertencia", "No existe Información para exportar");
//        }
//    }
////</editor-fold>
//    
    public void predialExcelExport() throws IOException {
        try {
            //String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/revisar/digitacion.jasper");
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/rpt_predios_consulta_busqueda.jasper");

            List<String> ruta = this.predioService.exportarExcel(listaPrediosBusqueda.getInformacionLazyModel(), variablesService, reportPath, listaPrediosBusqueda);
            this.descarga = new DefaultStreamedContent(ZipUtil.crearZip(ruta, new Date().toString() + "reporte.zip", Util.directorio_reportes), "", "reporte.zip");
        } catch (JRException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reporteEXCEL(JasperPrint jasper) throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }

    //<editor-fold defaultstate="collapsed" desc="Predios Export by Parametros">
    public void exportPredialParameter() throws IOException {

        String[] titles = {"P", "C", "PA", "Z", "S", "MZ", "SL", "C. Actual", "C. Anterior", "Dominio", "Estado", "A. Construcción"};

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
        externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "-predios filtrados" + "" + ".xlsx" + "\"");
        XSSFWorkbook wbw = new XSSFWorkbook();
        XSSFSheet sheet = wbw.createSheet("Predios sin edificación");

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
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            sheet.autoSizeColumn(i);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (PrediosSinEdificacion predio : prediosSinE) {
            Row row = sheet.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getProvincia());

            cell = row.createCell(1);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getCanton());

            cell = row.createCell(2);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getParroquia());

            cell = row.createCell(3);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getZona());

            cell = row.createCell(4);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getSector());

            cell = row.createCell(5);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getManzana());

            cell = row.createCell(6);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getSolar());

            cell = row.createCell(7);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getClaveCatastral());

            cell = row.createCell(8);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getClaveAnterior());

            cell = row.createCell(9);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getDominio());

            cell = row.createCell(10);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getEstado());

            cell = row.createCell(11);
            cell.setCellStyle(styles.get("cell"));
            cell.setCellValue(predio.getAreaTotal());
        }

        headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(40);
        headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Total: ");
        sheet.autoSizeColumn(0);
        headerCell.setCellStyle(styles.get("header"));
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue(prediosSinE.size());
        sheet.autoSizeColumn(1);

        wbw.write(externalContext.getResponseOutputStream());
        context.responseComplete();

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Edit Manzana de Predios">
    public void editManzana() {
        int result = predioService.editarManzanaMasivamente(true, parroquiaCod, zonaCod, sectorCod, manzanaCod, nombreManzana);
        String palabraClave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod;
        if (result == 0) {
            JsfUtil.addErrorMessage("No se pudo editar la manzana: " + palabraClave);
        } else {
            JsfUtil.addInformationMessage("Actualización", "La Actualización a los " + result + " predios de la manzana: " + palabraClave + " se realizo con exito...!");
            String datoB = palabraClave + "-";
            opcionesBusqueda.setPalabraClave(datoB);
            buscarPredio();
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Edit Sitio por Manzana">
    public void editSitio() {
        int result = predioService.editarManzanaMasivamente(false, parroquiaCod, zonaCod, sectorCod, manzanaCod, nombreSitio);
        String palabraClave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod;
        if (result == 0) {
            JsfUtil.addErrorMessage("No se pudo editar la manzana: " + palabraClave);
        } else {
            JsfUtil.addInformationMessage("Actualización", "La Actualización a los " + result + " predios de la manzana: " + palabraClave + " se realizo con exito...!");
            String datoB = palabraClave + "-";
            opcionesBusqueda.setPalabraClave(datoB);
            buscarPredio();
        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Edit Clave Catastral">
    public void editClaveCatastral() {
        String palabraClave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-0-0-0";
        int result2 = predioService.editarClaveCatastralT(parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, predioEdit);
        int result = 0;
        if (result2 != 0) {
            result = predioService.editarClaveCatastralP(parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod);
        }

        if (result == 0 || result2 == 0) {
            JsfUtil.addErrorMessage("No se pudo editar la clave Catastral: " + palabraClave);
        } else {
            JsfUtil.addInformationMessage("Actualización", "El predio se actualizon con la nueva clave catastral: " + palabraClave + "..!");
            opcionesBusqueda.setPalabraClave(palabraClave);
            buscarPredio();
        }

    }
//</editor-fold>

    public String estadoConservacion(List<Bloque> construccion) {
        List<String> estados = new ArrayList<String>();
        String estado = "";
        for (Bloque b1 : construccion) {
            estados.add(variablesService.obtenerValor("cat_bloque", "estado_conservacion", b1.getEstadoConservacion(), anioEmision));
        }

        HashSet<String> hashSet = new HashSet<String>(estados);
        estados.clear();
        estados.addAll(hashSet);

        for (String conservacion : estados) {
            estado += conservacion + "\n";
        }

        return estado;
    }

    public List<UsoSuelo> usosSueloSelect(List<Bloque> construccion) {
        List<UsoSuelo> usosSelect = new ArrayList<UsoSuelo>();
        for (Bloque b1 : construccion) {
            for (UsoSuelo suelo : b1.getUsosSuelo()) {
                usosSelect.add(suelo);
            }
        }
        HashSet<UsoSuelo> hashSet = new HashSet<UsoSuelo>(usosSelect);
        usosSelect.clear();
        usosSelect.addAll(hashSet);

        return usosSelect;
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

    public void onRowSelect(SelectEvent event) {
        exencion = (Exencion) event.getObject();

    }

    public void onRowUnselect(UnselectEvent event) {

    }

    public List<InfoInfraestructura> creaListaInfraestructura() {

        List<InfoInfraestructura> lista = new ArrayList<>();

        InfoInfraestructura datosInfra;
        ValorDiscreto valorDiscreto;
        float FA, prom1, prom2, prom3;

        datosInfra = new InfoInfraestructura("Factores geométricos", "", -1, true);
        lista.add(datosInfra);
        float areaTerr = (float) predioSeleccionado.getTerreno().getAreaLevantamiento();
        float coef_variacion = avaluoService.coeficienteAreaTerreno(areaTerr, (short) (anioEmision));
        datosInfra = new InfoInfraestructura("Área terreno: " + (Math.round(areaTerr * 100.0) / 100.0) + "", avaluoService.rangoAreaTerreno(areaTerr, (short) (anioEmision)), coef_variacion);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", predioSeleccionado.getTipoPredio() ? "geometria" : "geometria_rural", predioSeleccionado.getTerreno().getGeometria(), anioEmision);
        float coef_geometria = valorDiscreto.getCoeficiente();
        datosInfra = new InfoInfraestructura("Geometría", valorDiscreto.getTexto(), coef_geometria);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", "ubicacion", predioSeleccionado.getTerreno().getUbicacion(), anioEmision);
        float coef_ubicacion = valorDiscreto.getCoeficiente();
        datosInfra = new InfoInfraestructura("Ubicación", valorDiscreto.getTexto(), coef_ubicacion);
        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Factores topográficos", "", -1, true);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", predioSeleccionado.getTipoPredio() ? "topografia" : "topografia_rural", predioSeleccionado.getTerreno().getTopografia(), anioEmision);
        float coef_topografia = valorDiscreto.getCoeficiente();
        datosInfra = new InfoInfraestructura("Topografía", valorDiscreto.getTexto(), coef_topografia);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", "caracteristicas_suelo", predioSeleccionado.getTerreno().getCaracteristicasSuelo(), anioEmision);
        float coef_suelo = valorDiscreto.getCoeficiente();
        datosInfra = new InfoInfraestructura("Suelo", valorDiscreto.getTexto(), coef_suelo);
        lista.add(datosInfra);

        short cantServBasicos = 0;
        if (predioSeleccionado.getTerreno().getRedEnergiaElectrica() != 3) {
            cantServBasicos++;
        }
        if (predioSeleccionado.getTerreno().getRedAguaPotable() != 1) {
            cantServBasicos++;
        }
        if (predioSeleccionado.getTerreno().getRedAlcantarillado() != 1) {
            cantServBasicos++;
        }
        float coef_serv_basico = variablesService.obtenerCoeficiente("coef_avaluos", "infraestructura_basica", cantServBasicos, anioEmision);

        datosInfra = new InfoInfraestructura("Infraestructura básica", "", coef_serv_basico, true);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", predioSeleccionado.getTipoPredio() ? "red_energia_electrica" : "red_energia_electrica_rural", predioSeleccionado.getTerreno().getRedEnergiaElectrica(), anioEmision);
        datosInfra = new InfoInfraestructura("Energía eléctrica", valorDiscreto.getTexto(), predioSeleccionado.getTerreno().getRedEnergiaElectrica() != 3 ? 1 : 0);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", predioSeleccionado.getTipoPredio() ? "red_agua_potable" : "red_agua_potable_rural", predioSeleccionado.getTerreno().getRedAguaPotable(), anioEmision);
        datosInfra = new InfoInfraestructura("Agua potable", valorDiscreto.getTexto(), predioSeleccionado.getTerreno().getRedAguaPotable() != 1 ? 1 : 0);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", predioSeleccionado.getTipoPredio() ? "red_alcantarillado" : "red_alcantarillado_rural", predioSeleccionado.getTerreno().getRedAlcantarillado(), anioEmision);
        datosInfra = new InfoInfraestructura("Alcantarillado", valorDiscreto.getTexto(), predioSeleccionado.getTerreno().getRedAlcantarillado() != 1 ? 1 : 0);
        lista.add(datosInfra);

        short cantServCompl = 0;
        if (predioSeleccionado.getTerreno().getAceras() == 1) {
            cantServCompl++;
        }
        if (predioSeleccionado.getTerreno().getBordillos() == 1) {
            cantServCompl++;
        }
        if (predioSeleccionado.getTerreno().getRedTelefonica() != 3) {
            cantServCompl++;
        }
        float coef_serv_compl = variablesService.obtenerCoeficiente("coef_avaluos", "infraestructura_complementaria", cantServCompl, anioEmision);

        datosInfra = new InfoInfraestructura("Infraestructura complementaria", "", coef_serv_compl, true);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("comun", "tiene", predioSeleccionado.getTerreno().getAceras(), anioEmision);
        datosInfra = new InfoInfraestructura("Aceras", valorDiscreto.getTexto(), predioSeleccionado.getTerreno().getAceras() == 1 ? 1 : 0);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("comun", "tiene", predioSeleccionado.getTerreno().getBordillos(), anioEmision);
        datosInfra = new InfoInfraestructura("Bordillos", valorDiscreto.getTexto(), predioSeleccionado.getTerreno().getBordillos() == 1 ? 1 : 0);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", "red_telefonica", predioSeleccionado.getTerreno().getRedTelefonica(), anioEmision);
        datosInfra = new InfoInfraestructura("Red telefónica", valorDiscreto.getTexto(), predioSeleccionado.getTerreno().getRedTelefonica() != 3 ? 1 : 0);
        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Infraestructura víal", "", -1, true);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", "tipo_capa_rodadura", predioSeleccionado.getTerreno().getTipoCapaRodadura(), anioEmision);
        float coef_capa_rodadura = valorDiscreto.getCoeficiente();
        datosInfra = new InfoInfraestructura("Tipo capa rodadura", valorDiscreto.getTexto(), coef_capa_rodadura);
        lista.add(datosInfra);
        valorDiscreto = variablesService.obtenerValores("cat_terreno", predioSeleccionado.getTipoPredio() ? "estado_conservacion" : "estado_conservacion_rural", predioSeleccionado.getTerreno().getEstadoConservacion(), anioEmision);
        float coef_conserv = valorDiscreto.getCoeficiente();
        datosInfra = new InfoInfraestructura("Estado conservación", valorDiscreto.getTexto(), coef_conserv);
        lista.add(datosInfra);

        FA = coef_variacion * coef_geometria * coef_ubicacion * coef_topografia * coef_suelo * coef_serv_basico * coef_serv_compl * coef_capa_rodadura * coef_conserv;

        datosInfra = new InfoInfraestructura("", "Factor acumulado", FA);
        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Zona valor", predioSeleccionado.getTerreno().getManzana().getZonaHomogenea().getCodigoZona(), predioSeleccionado.getTerreno().getManzana().getZonaHomogenea().valorActual(Util.ANIO_ACTUAL.shortValue()).getValor(), true);
        lista.add(datosInfra);
        if (predioSeleccionado.getTerreno().getManzana().getZonaAumentoReduccion() != null) {
            datosInfra = new InfoInfraestructura("Factor aumento/reduccion", predioSeleccionado.getTerreno().getManzana().getZonaAumentoReduccion().getCodigo(), predioSeleccionado.getTerreno().getManzana().getZonaAumentoReduccion().valorFactor(Util.ANIO_ACTUAL.shortValue()).getFactor(), true);
            lista.add(datosInfra);
        }
        if (predioSeleccionado.getTerreno().getManzana().getZonaHomogenea() != null) {
            float avaluoTerr = areaTerr * predioSeleccionado.getTerreno().getManzana().getZonaHomogenea().getValor() * FA;
            datosInfra = new InfoInfraestructura("Avalúo", "Terreno", avaluoTerr);
            lista.add(datosInfra);
        }

        return lista;
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
                String nuevoNombre = String.format("%04d-%02d-%02d-%02d:%02d:%02d:%03d", year, month, day, hour, minute, second, millis);
                nuevoNombre += "-" + FilenameUtils.getName(uploadedFile.getFileName());

                File destFile = new File(new File(uploadDir), nuevoNombre);

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedDocument doc = datosDocumento[i];
                doc.setSavedFile(destFile);

                copiados.add(doc);
            } catch (IOException e) {
            }
        }

        return copiados;
    }

    public String getUsuario(String username) {
        Usuario user = usuarioService.serchUser(username);
        return user.getNombre() + user.getApellidos();
    }

    public void ImprimirCertificado() throws JRException, IOException {

        // Predio p = predioService.find(id);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat anotherDFormat = (DecimalFormat) numberFormat;
        anotherDFormat.applyPattern("#.00");
        anotherDFormat.setGroupingUsed(true);
        anotherDFormat.setGroupingSize(3);

        Predio p = predioService.findByNamedQuery("Predio.findById", predioSeleccionado.getId()).get(0);
        ImpuestoPredial impuesto = new ImpuestoPredial();
        //short anioEmision = (short) variablesService.obtenerCoeficiente("datos_configuracion", "anio_emision", (short) 0, anioEmision);
        ImpuestoPredialPK ipk = new ImpuestoPredialPK(predioSeleccionado.getId(), anioEmision);

        impuesto = impuestoService.find(ipk);

        Map<String, Object> parameter = new HashMap<String, Object>();

        long numeroCertificado = (long) variablesService.obtenerCoeficiente("datos_configuracion", "consecutivo_certificado_avaluo", (short) 0, anioEmision) + 1;
        variablesService.setCoeficiente("datos_configuracion", "consecutivo_certificado_avaluo", (short) 0, (float) numeroCertificado, anioEmision);

        parameter.put("numeroCertificado", String.format("%06d", numeroCertificado));
        parameter.put("clave", p.getClaveCatastral());
        String parroquia = p.getTerreno().getTerrenoPK().getCodParroquia();
        parameter.put("parroquia", !parroquia.equals("99") ? parroquia : "");
        parameter.put("zona", "" + p.getTerreno().getTerrenoPK().getCodZona());
        parameter.put("sector", "" + p.getTerreno().getTerrenoPK().getCodSector());
        parameter.put("manzana", "" + p.getTerreno().getTerrenoPK().getCodManzana());
//        parameter.put("solar", "" + p.getTerreno().getTerrenoPK().getCodSolar());
//        parameter.put("ph", "" + p.getPropiedadHorizontal());
//
//        parameter.put("sectorA", p.getSector());
//        parameter.put("manzanaA", p.getManzana());
//        parameter.put("solarA", p.getSolar());
//        parameter.put("calle", p.getCalle());

        String prop1 = "", ced1 = "";
        String prop2 = "", ced2 = "";
        String prop3 = "", ced3 = "";

        List<Contribuyente> props = p.getPropietarios();

        if (!props.isEmpty()) {
            for (int i = 0; i < props.size(); i++) {
                if (i == 0) {
                    prop1 = props.get(i).getApellidoPaterno() + " " + props.get(i).getApellidoMaterno() + " " + props.get(i).getNombre();
                    ced1 = props.get(i).getIdentificacion();
                }
                if (i == 1) {
                    prop2 = props.get(i).getApellidoPaterno() + " " + props.get(i).getApellidoMaterno() + " " + props.get(i).getNombre();
                    ced2 = props.get(i).getIdentificacion();
                }
                if (i == 2) {
                    prop3 = props.get(i).getApellidoPaterno() + " " + props.get(i).getApellidoMaterno() + " " + props.get(i).getNombre();
                    ced3 = props.get(i).getIdentificacion();
                }
            }
        }

        parameter.put("propietario1", prop1);
        parameter.put("cedula1", ced1);
        parameter.put("propietario2", prop2);
        parameter.put("cedula2", ced2);
        parameter.put("propietario3", prop3);
        parameter.put("cedula3", ced3);

        List<Escritura> listEscrituras = p.getEscrituras();

        Escritura escritura = listEscrituras.isEmpty() ? new Escritura() : listEscrituras.get(listEscrituras.size() - 1);

        parameter.put("notaria", escritura.getNotaria());

        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

//        parameter.put("fechaEscritura", escritura.getFechaNotaria() != null ? dt.format(escritura.getFechaNotaria()) : "");
//        parameter.put("fechaInscripcion", escritura.getFechaInscripcion() != null ? dt.format(escritura.getFechaInscripcion()) : "");
//        parameter.put("repertorio", escritura.getRepertorioRegistrador());
//        parameter.put("folio", escritura.getFolioRegistrador());
//        parameter.put("cuantia", escritura.getCuantia());
//
//        parameter.put("edificio", p.getEdificio());
//        parameter.put("piso", p.getPropiedadHorizontal() > 0 ? "" + p.getPiso() : "");
//        parameter.put("dpto", p.getPropiedadHorizontal() > 0 ? "" + p.getDepartamento() : "");
//        parameter.put("piso", p.getPropiedadHorizontal() > 0 ? "" + p.getPiso() : "");
//        parameter.put("cohera1", p.getPropiedadHorizontal() > 0 ? "" + p.getCochera() : "");
//        parameter.put("bodega1", p.getPropiedadHorizontal() > 0 ? "" + p.getBodega() : "");
//        parameter.put("aliDpto", p.getPropiedadHorizontal() > 0 ? "" + p.getAlicuotaPh().floatValue() : "");
//
//        parameter.put("tipoSolar", p.getTerreno().getTipoLote() == 0 ? "TERRENO VACIO" : (p.getTerreno().getTipoLote() == 1 ? "TERRENO CONST." : "PROP. HORIZ."));
//        parameter.put("usoSuelo", p.getBloqueList().isEmpty() ? "" : "");
//        parameter.put("estatus", p.getTipoPropiedad() == 0 ? "PROPIO" : "MUNICIPAL");
//
//        parameter.put("cNorte", p.getTerreno().getLindero().getLinNorteRefSis());
//        parameter.put("cSur", p.getTerreno().getLindero().getLinSurRefSis());
//        parameter.put("cEste", p.getTerreno().getLindero().getLinEsteRefSis());
//        parameter.put("cOeste", p.getTerreno().getLindero().getLinOesteRefSis());
//        parameter.put("cArea", "" + anotherDFormat.format(p.getTerreno().getAreaLevantamiento()));
//        parameter.put("eNorte", p.getTerreno().getLindero().getLinNorteRefEsc());
//        parameter.put("eSur", p.getTerreno().getLindero().getLinSurRefEsc());
//        parameter.put("eEste", p.getTerreno().getLindero().getLinEsteRefEsc());
//        parameter.put("eOeste", p.getTerreno().getLindero().getLinOesteRefEsc());
//        parameter.put("eArea", "" + anotherDFormat.format(p.getTerreno().getAreaEscritura().floatValue()));
//
//        parameter.put("cNorteL", p.getTerreno().getLindero().getLinNorteLongitudSis());
//        parameter.put("cSurL", p.getTerreno().getLindero().getLinSurLongitudSis());
//        parameter.put("cEsteL", p.getTerreno().getLindero().getLinEsteLongitudSis());
//        parameter.put("cOesteL", p.getTerreno().getLindero().getLinOesteLongitudSis());
//
//        parameter.put("eNorteL", p.getTerreno().getLindero().getLinNorteLongitudEsc());
//        parameter.put("eSurL", p.getTerreno().getLindero().getLinSurLongitudEsc());
//        parameter.put("eEsteL", p.getTerreno().getLindero().getLinEsteLongitudEsc());
//        parameter.put("eOesteL", p.getTerreno().getLindero().getLinOesteLongitudEsc());
        String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
        String uploadDirManzanero = config.getDbConfiguration().getString("directorio_manzaneros");
        String pathManzanero = "blank.png";
        ManzanaArchivo manzanero = p.getTerreno().getManzana().getManzanero();

        if (manzanero != null) {
            String mm = manzanero.getRuta();

            String ruta = uploadDirManzanero + "/" + manzanero.getRuta().substring(0, mm.length() - 4) + ".jpg";

            if (existeArchivo(ruta)) {
                pathManzanero = ruta;
            }
        } else {
        }
//        parameter.put("path_croquis", pathManzanero);
//        List<Archivo> fotos = p.getArchivos();
//
//        String path2 = "blank.png";
//
//        if (!fotos.isEmpty()) {
//            for (int j = 0; j < fotos.size(); j++) {
//                if (j == 0) {
//                    String ruta = uploadDir + "/" + fotos.get(j).getRuta();
//                    if (existeArchivo(ruta)) {
//                        path2 = ruta;
//                    }
//                }
//
//            }
//        }

//        parameter.put("path_foto1", pathManzanero);
//        parameter.put("path_foto2", path2);
//
//        parameter.put("avaluoTerreno", "" + anotherDFormat.format(impuesto.getAvaluoTerreno()));
//        parameter.put("avaluoConstruccion", "" + anotherDFormat.format(impuesto.getAvaluoEdificacion()));
//        parameter.put("avaluoComplementarias", "" + anotherDFormat.format(impuesto.getAvaluoComplementarias()));
//        parameter.put("avaluoComercial", "" + anotherDFormat.format(impuesto.getAvaluoPredial()));
//        parameter.put("valorMetroTerreno", "" + anotherDFormat.format(impuesto.getAvaluoTerreno() / impuesto.getAreaTerreno()));
//        parameter.put("valorMetroConstruccion", "" + anotherDFormat.format(impuesto.getAvaluoEdificacion() / impuesto.getAreaConstruccion()));
//
//        parameter.put("vigenciaAvaluo", "" + anioEmision + "-" + (anioEmision + 1));
//        parameter.put("observaciones", p.getTerreno().getObservacion());
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/certificadoAvaluo.jasper"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameter, new JREmptyDataSource());

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        //response.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + String.format("%06d", numeroCertificado) + "-certificado-avaluo" + ".pdf" + "\"");
        try (ServletOutputStream stream = response.getOutputStream()) {
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            stream.flush();
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void actulizarVistaPrevia() {

        docs.clear();

        List<UploadedDocument> files = guardarDocumentos();
        if (!files.isEmpty()) {
            long id = 1;
            List<TipoEscritura> tiposEsc = tipoEscrituraService.findByNamedQuery("TipoEscritura.findById", id);
            TipoEscritura te = new TipoEscritura();
            if (!tiposEsc.isEmpty()) {
                te = tiposEsc.get(0);
            }

            Escritura e = new Escritura();

//            e.setTipo(te);
            e.setPredio(predioSeleccionado);

            List<Archivo> docs1 = new ArrayList<Archivo>();
            for (UploadedDocument f : files) {
                Archivo archivo = new Archivo();

                archivo.setAutor("Prueba");
                archivo.setUsuarioModifica("Prueba");
                archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
                archivo.setRuta(f.getSavedFile().getName());
                archivo.setDescripcion(f.getDescription());
                archivo.setEscritura(e);
                docs1.add(archivo);
            }

//            e.setArchivoList(docs1);
            predioSeleccionado.getEscrituras().add(e);

            predioService.editarPredioTemporal(predioSeleccionado);

            Predio prueba = predioService.find(predioSeleccionado.getId());

            List<Escritura> es = prueba.getEscrituras();

            Escritura aux = es.get(es.size() - 1);

            documentosPredio(aux);
        }
        documentos = new ArrayList<UploadedFile>();
        datosDocumento = new UploadedDocument[30];
    }

    public List<SelectItem> getParroquias() {
        parroquias = new ArrayList<SelectItem>();
        List<Parroquia> ps = parroquiaService.findAll();
        for (Parroquia p : ps) {
            parroquias.add(new SelectItem(p.getParroquiaPK().getCodParroquia(), "" + p.getParroquiaPK().getCodParroquia() + "-" + p.getNombre()));
        }
        return parroquias;
    }

    public List<SelectItem> listaZonasPorParroquia(String parroquia) {

        ParroquiaPK pk = new ParroquiaPK();
        pk.setCodProvincia(provinciaCod);
        pk.setCodCanton(cantonCod);
        pk.setCodParroquia(parroquia);

        Parroquia p = parroquiaService.find(pk);

        zonas = new ArrayList<SelectItem>();
        sectores = new ArrayList<SelectItem>();

        if (p != null) {
            List<Zona> zs = p.getZonaCollection();

            for (Zona z : zs) {
                zonas.add(new SelectItem(z.getZonaPK().getCodZona(), "" + z.getZonaPK().getCodZona() + "-" + z.getNombre()));
            }
        }

        return zonas;
    }

    public List<SelectItem> listaSectoresPorZona(String zona) {

        ZonaPK pk = new ZonaPK();
        pk.setCodProvincia(provinciaCod);
        pk.setCodCanton(cantonCod);
        pk.setCodParroquia(parroquiaCod);
        pk.setCodZona(zona);

        Zona z = zonaService.find(pk);

        sectores = new ArrayList<SelectItem>();

        if (z != null) {
            List<Sector> ss = z.getSectorCollection();
            for (Sector s : ss) {
                sectores.add(new SelectItem(s.getSectorPK().getCodSector(), "" + s.getSectorPK().getCodSector() + "-" + s.getNombre()));
            }
        }

        return sectores;
    }

    public List<SelectItem> listaManzanasPorSector(String sector) {

        SectorPK pk = new SectorPK();
        pk.setCodProvincia(provinciaCod);
        pk.setCodCanton(cantonCod);
        pk.setCodParroquia(parroquiaCod);
        pk.setCodZona(zonaCod);
        pk.setCodSector(sector);

        Sector s = sectorService.find(pk);

        manzanas = new ArrayList<SelectItem>();

        if (s != null) {
            List<Manzana> mm = (List<Manzana>) s.getManzanaCollection();
            for (Manzana m : mm) {
                manzanas.add(new SelectItem(m.getManzanaPK().getCodManzana(), ""
                        + m.getManzanaPK().getCodManzana() + "-Manzana-" + m.getManzanaPK().getCodManzana()));
            }
        }

        return manzanas;
    }

    public void inicializarEdicionManzanaPredial() {
        manzanaCod = null;
        parroquiaCod = null;
        zonaCod = null;
        sectorCod = null;
        parroquias = getParroquias();
        nombreManzana = "";
        manzanaEdit = true;
        sitioEdit = false;
        claveCatastralEdit = false;
    }

    public void cancelarEdicionManzanaPredial() {

        if (claveCatastralEdit) {
            parroquias = getParroquias();
            manzanaEdit = false;
            sitioEdit = false;
            claveCatastralEdit = true;
        } else if (manzanaEdit) {
            inicializarEdicionManzanaPredial();
        } else {
            inicializarEdicionSitioPredial();
        }

    }

    public void inicializarEdicionSitioPredial() {
        manzanaCod = null;
        parroquiaCod = null;
        zonaCod = null;
        sectorCod = null;
        parroquias = getParroquias();
        nombreSitio = "";
        manzanaEdit = false;
        sitioEdit = true;
        claveCatastralEdit = false;
    }

    public void inicializarEdicionClaveCatastral(BusquedaPredial predioSelect) {
        if (predioSelect != null) {
            Predio p = this.predioService.findByCodigoCatastro(predioSelect.getCod_actual());
            parroquiaCod = p.getTerreno().getTerrenoPK().getCodParroquia();
            manzanaCod = p.getTerreno().getTerrenoPK().getCodManzana();
            zonaCod = p.getTerreno().getTerrenoPK().getCodZona();
            sectorCod = p.getTerreno().getTerrenoPK().getCodSector();
            solarCod = p.getTerreno().getTerrenoPK().getCodSolar();
            predioEdit = p;
        }
        parroquias = getParroquias();
        manzanaEdit = false;
        sitioEdit = false;
        claveCatastralEdit = true;
    }

    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("form-form");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiP = (UIInput) components.findComponent("parroquia-select");
        String p = uiP.getLocalValue() == null ? ""
                : uiP.getLocalValue().toString();

        UIInput uiZ = (UIInput) components.findComponent("zona-select");
        String z = uiZ.getLocalValue() == null ? ""
                : uiZ.getLocalValue().toString();

        UIInput uiS = (UIInput) components.findComponent("sector-select");
        String s = uiS.getLocalValue() == null ? ""
                : uiS.getLocalValue().toString();

        UIInput uiM = (UIInput) components.findComponent("manzana-select");
        String m = uiS.getLocalValue() == null ? ""
                : uiS.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiP.setValid(false);
        }
        if (z.equals("")) {
            errores++;
            uiZ.setValid(false);
        }
        if (s.equals("")) {
            errores++;
            uiS.setValid(false);
        }
        if (m.equals("")) {
            errores++;
            uiM.setValid(false);
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

    public void abrir() {
        audit.setClave(predioSeleccionado.getClaveCatastral());
        audit.showDialog();
    }

    public void imprimirFichaPredial(Predio px) {
        try {
            if (px != null) {
                this.setPredioSeleccionado(px);
            }
            if (this.getPredioSeleccionado() != null) {
                reporte.getServletSession().initView();
                reporte.getServletSession().instanciarParametros();
                Map params = reporte.getServletSession().getParametros();
                params.put("idPredio", this.getPredioSeleccionado().getId().longValue());
                params.put("SUBREPORT_DIR", JsfUti.getRealPath("//reportes//catastro").concat("//"));
                params.put("logo", JsfUti.getRealPath("//resources//images//gad-machala.png"));
                params.put("ruta", GlobalVars.contextURL + "/ImageViewer?type=pred&cod=");
                params.put("url", GlobalVars.geoServerUrl);
                String clave = predioSeleccionado.getTerreno().getTerrenoPK().getCodProvincia() + predioSeleccionado.getTerreno().getTerrenoPK().getCodCanton()
                        + predioSeleccionado.getTerreno().getTerrenoPK().getCodParroquia() + predioSeleccionado.getTerreno().getTerrenoPK().getCodZona()
                        + predioSeleccionado.getTerreno().getTerrenoPK().getCodSector() + predioSeleccionado.getTerreno().getTerrenoPK().getCodManzana();
                params.put("manzanero", clave);
                params.put("code", clave + predioSeleccionado.getTerreno().getTerrenoPK().getCodSolar());

                reporte.getServletSession().setNombreSubCarpeta("catastro");
                reporte.getServletSession().setNombreDocumento(predioSeleccionado.getClaveCatastral() + ".pdf");
                reporte.getServletSession().setNombreReporte("fichaPredial");
                reporte.getServletSession().setTieneDatasource(Boolean.TRUE);
                reporte.getServletSession().setParametros(params);
                JsfUti.redirectFacesNewTab("/Documento");
            }
        } catch (Exception e) {
            log.error(e.getCause().getMessage());
        }
    }

    public void generarFichas() {
        List<Predio> preds = aclServices.findListByParameters(QuerysFlow.getPrediosTipo, new String[]{}, new Object[]{});
        reporte.getServletSession().initView();
        Connection cx = reporte.getConnection();
        for (Predio pred : preds) {
            Map params = new HashMap();
            params.put("idPredio", pred.getId().longValue());
            params.put("SUBREPORT_DIR", JsfUti.getRealPath("//reportes//catastro").concat("//"));
            params.put("logo", JsfUti.getRealPath("//resources//images//gad-machala.png"));
            params.put("ruta", GlobalVars.contextURL + "/ImageViewer?type=pred&cod=");
            params.put("url", GlobalVars.geoServerUrl);
            String clave = pred.getTerreno().getTerrenoPK().getCodProvincia() + pred.getTerreno().getTerrenoPK().getCodCanton()
                    + pred.getTerreno().getTerrenoPK().getCodParroquia() + pred.getTerreno().getTerrenoPK().getCodZona()
                    + pred.getTerreno().getTerrenoPK().getCodSector() + pred.getTerreno().getTerrenoPK().getCodManzana();
            params.put("manzanero", clave);
            params.put("code", clave + pred.getTerreno().getTerrenoPK().getCodSolar());
            reporte.getServletSession().setNombreSubCarpeta("catastro");
            reporte.getServletSession().setNombreDocumento(pred.getClaveCatastral() + ".pdf");
            reporte.getServletSession().setNombreReporte("fichaPredial");
            reporte.getServletSession().setTieneDatasource(Boolean.TRUE);
            reporte.exportToDirectory(GlobalVars.directorio_cat_docs, params, cx);
        }
        reporte.cerrarConexion();
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String nombreVariable(int id) {
        return variablesService.find(id).getNombre();
    }

    public String nombreValorDiscreto(int id, short tipo, short estructura, short cubierta) {
        String valor = variablesService.obtenerValor(id, tipo, anioEmision);
        if (id == 100 && tipo == 8) {
            valor += " (ESTRUCTURA: " + variablesService.obtenerValor("cat_bloque", "estructura", estructura, anioEmision)
                    + " CUBIERTA: " + variablesService.obtenerValor("cat_bloque", "cubierta", cubierta, anioEmision) + ")";

        }
        return valor;
    }

    public boolean isExportar() {
        return exportar;
    }

    public void setExportar(boolean exportar) {
        this.exportar = exportar;
    }

    public List<BusquedaPredial> getPrediosF() {
        return prediosF;
    }

    public void setPrediosF(List<BusquedaPredial> prediosF) {
        this.prediosF = prediosF;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public List<PrediosSinEdificacion> getPrediosSinE() {
        return prediosSinE;
    }

    public void setPrediosSinE(List<PrediosSinEdificacion> prediosSinE) {
        this.prediosSinE = prediosSinE;
    }

    public Long getTotalPredios() {
        return totalPredios;
    }

    public void setTotalPredios(Long totalPredios) {
        this.totalPredios = totalPredios;
    }

    public ValorDiscretoService getDiscretoService() {
        return discretoService;
    }

    public void setDiscretoService(ValorDiscretoService discretoService) {
        this.discretoService = discretoService;
    }

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
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

    public Predio getPredioEdit() {
        return predioEdit;
    }

    public void setPredioEdit(Predio predioEdit) {
        this.predioEdit = predioEdit;
    }

    public ParroquiaService getParroquiaService() {
        return parroquiaService;
    }

    public void setParroquiaService(ParroquiaService parroquiaService) {
        this.parroquiaService = parroquiaService;
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

    public void setParroquias(List<SelectItem> parroquias) {
        this.parroquias = parroquias;
    }

    public List<SelectItem> getZonas() {
        if (parroquiaCod != null) {
            zonas = listaZonasPorParroquia(parroquiaCod);
        } else {
            zonas = new ArrayList<SelectItem>();
        }
        return zonas;
    }

    public void setZonas(List<SelectItem> zonas) {
        this.zonas = zonas;
    }

    public List<SelectItem> getSectores() {
        if (zonaCod != null) {
            sectores = listaSectoresPorZona(zonaCod);
        } else {
            sectores = new ArrayList<SelectItem>();
        }
        return sectores;
    }

    public void setSectores(List<SelectItem> sectores) {

        this.sectores = sectores;
    }

    public List<SelectItem> getManzanas() {
        if (sectorCod != null) {
            manzanas = listaManzanasPorSector(sectorCod);
        } else {
            manzanas = new ArrayList<SelectItem>();
        }
        return manzanas;
    }

    public void setManzanas(List<SelectItem> manzanas) {
        this.manzanas = manzanas;
    }

    public String getNombreManzana() {
        return nombreManzana;
    }

    public void setNombreManzana(String nombreManzana) {
        this.nombreManzana = nombreManzana;
    }

    public String getNombreSitio() {
        return nombreSitio;
    }

    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }

    public boolean isManzanaEdit() {
        return manzanaEdit;
    }

    public void setManzanaEdit(boolean manzanaEdit) {
        this.manzanaEdit = manzanaEdit;
    }

    public boolean isClaveCatastralEdit() {
        return claveCatastralEdit;
    }

    public void setClaveCatastralEdit(boolean claveCatastralEdit) {
        this.claveCatastralEdit = claveCatastralEdit;
    }

    public boolean isSitioEdit() {
        return sitioEdit;
    }

    public void setSitioEdit(boolean sitioEdit) {
        this.sitioEdit = sitioEdit;
    }

    public BusquedaPredial getPredioFSelect() {
        return predioFSelect;
    }

    public void setPredioFSelect(BusquedaPredial predioFSelect) {
        this.predioFSelect = predioFSelect;
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

    /**
     * @return the listaPrediosBusqueda
     */
    public LazyDataModelPredioBusqueda getListaPrediosBusqueda() {
        return listaPrediosBusqueda;
    }

    /**
     * @param listaPrediosBusqueda the listaPrediosBusqueda to set
     */
    public void setListaPrediosBusqueda(LazyDataModelPredioBusqueda listaPrediosBusqueda) {
        this.listaPrediosBusqueda = listaPrediosBusqueda;
    }

    /**
     * @return the descarga
     */
    public StreamedContent getDescarga() {
        return descarga;
    }

    /**
     * @param descarga the descarga to set
     */
    public void setDescarga(StreamedContent descarga) {
        this.descarga = descarga;
    }

    public PrediosLazy getPrediosLazy() {
        return prediosLazy;
    }

    public void setPrediosLazy(PrediosLazy prediosLazy) {
        this.prediosLazy = prediosLazy;
    }

    public Integer getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(Integer tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    public List<CoactivaVsam> getCoactivasList() {
        return coactivasList;
    }

    public void setCoactivasList(List<CoactivaVsam> coactivasList) {
        this.coactivasList = coactivasList;
    }

    public List<DeudaVsam> getDeudasList() {
        return deudasList;
    }

    public void setDeudasList(List<DeudaVsam> deudasList) {
        this.deudasList = deudasList;
    }

    public DeudaVsam getDeudaSelec() {
        return deudaSelec;
    }

    public void setDeudaSelec(DeudaVsam deudaSelec) {
        this.deudaSelec = deudaSelec;
    }

    public Boolean getMostrarEstado() {
        return mostrarEstado;
    }

    public void setMostrarEstado(Boolean mostrarEstado) {
        this.mostrarEstado = mostrarEstado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
