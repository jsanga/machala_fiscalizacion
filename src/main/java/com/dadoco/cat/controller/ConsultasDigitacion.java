/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.ArchivoEscritura;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.ImpuestoPredialPK;
import com.dadoco.cat.model.InfoBloque;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.service.BloqueService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.ImpuestoService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.lazyModel.PrediosLazy;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.ServletSession;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.dadoco.ren.service.AvaluoService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.data.FilterEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author JoaoIsrael
 */
@Named(value = "consultasDigitacionView")
@ViewScoped
public class ConsultasDigitacion implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConsultasDigitacion.class);
    
    @Inject
    private ServletSession servletSession;
    
    @EJB
    private PredioService predioService;
    
    @EJB
    private ImpuestoService impuestoService;
    
    @EJB
    private AvaluoService avaluoService;
    
    @EJB
    private BloqueService bloqueService;
    
    @EJB
    private CatastroService catastroService;
    
    @EJB
    private ConfigReader config;
    
    private String entrada;
    private Date fecha;
    private String username;
    private Subject subject;
    private PrediosLazy listaPredios;
    
    private List<Contribuyente> propietariosPredioSeleccionado;
    private List<Contribuyente> arrendatariosPredioSeleccionado;
    private Predio predioSeleccionado;
    private ImpuestoPredial impuestoPredial;
    private ImpuestoPredial impuestoPredialAnterior;
    private String avaluoTerreno;
    private String avaluoConstruccion;
    private String avaluoComplementarias;
    private String avaluoPredial;
    private String areaTerreno;
    private String areaConstruccion;
    private String valorTerreno;
    private List<OtrosTipoConstruccion> obrasComplementarias;
    private List<InfoBloque> infoBloques;
    private boolean visualizarDatos, conEdificaciones;
    private List<Bloque> bloques;
    private Bloque bloqueSeleccionado;
    private int bloqueSeleccionadoIndex;
    private List<UsoSuelo> usosBloque;
    private List<Piso> pisos;
    private Piso pisoSeleccionado;
    private int pisoSeleccionadoIndex;
    private List<String> images;
    private Escritura escritura;
    private List<String> docs;
    private double coord_X;
    private double coord_Y;
    private String claveGeoreferenciada;
    private List<ArchivoAux> imagesBloque;
    private Map<String, Object> filtros;
    
    @PostConstruct
    public void initView(){
        try{
            listaPredios = new PrediosLazy(true);
            subject = SecurityUtils.getSubject();
            username = subject.getPrincipal().toString();
            fecha = new Date();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean ajustarOpcionesBusqueda(){
        
        for(String s : entrada.split("-")){
            if(!Util.isInteger(s))                
                return false;            
        }        
        return true;
    }
    
    public void filterListener(FilterEvent filterEvent) {
        filtros = filterEvent.getFilters();
    }
    
    public void MostrarDatosPredioSeleccionado(Integer id) {

        // short anioEmision = (short) variablesService.obtenerCoeficiente("datos_configuracion", "anio_emision", (short) 0, anioEmision);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        predioSeleccionado = predioService.find(id);

        short anioAnterior = (short) (Util.ANIO_ACTUAL.shortValue() - 1);
        ImpuestoPredialPK pk = new ImpuestoPredialPK(predioSeleccionado.getId(), Util.ANIO_ACTUAL.shortValue());

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
            conEdificaciones = false;
            bloqueSeleccionado = new Bloque();
            pisoSeleccionado = new Piso();
            bloques = new ArrayList<>();

        }
        fotoPredio(predioSeleccionado.getId());

        List<Escritura> escrituras = predioSeleccionado.getEscrituras();

        if (!escrituras.isEmpty()) {
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

//        listaDeudas = deudaService.findByNamedQuery("Deuda.findByClaveCatastral", predioSeleccionado.getClaveCatastral());

//        RequestContext context = RequestContext.getCurrentInstance();
//        context.update("datosConsultas,predio-list-form,datos-tab");
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

        List<InfoBloque> lista = avaluoService.infoBloques(predioSeleccionado, (short)(Util.ANIO_ACTUAL.shortValue()));
        return lista;
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

        if (!p.getImages().isEmpty()) {

            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";

            for (PredioImage img : p.getImages()) {

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

                } catch (FileNotFoundException ex) {

                }
            }

        }

    }
    
    public void consultaPredioLazy(){
        servletSession.instanciarParametros();
        if (filtros.containsKey("terreno.terrenoPK.codParroquia"))
            servletSession.agregarParametro("COD_PARROQUIA", filtros.get("terreno.terrenoPK.codParroquia"));
        if (filtros.containsKey("terreno.terrenoPK.codZona"))
            servletSession.agregarParametro("COD_ZONA", filtros.get("terreno.terrenoPK.codZona"));
        if (filtros.containsKey("terreno.terrenoPK.codSector"))
            servletSession.agregarParametro("COD_SECTOR", filtros.get("terreno.terrenoPK.codSector"));
        if (filtros.containsKey("terreno.terrenoPK.codManzana"))
            servletSession.agregarParametro("COD_MANZANA", filtros.get("terreno.terrenoPK.codManzana"));
        if (filtros.containsKey("terreno.terrenoPK.codSolar"))
            servletSession.agregarParametro("COD_SOLAR", filtros.get("terreno.terrenoPK.codSolar"));
        if (filtros.containsKey("claveAnterior"))
            servletSession.agregarParametro("CLAVE_ANTERIOR", filtros.get("claveAnterior"));
        
        servletSession.setNombreReporte("digitacion//reporte_predial");
        servletSession.agregarParametro("SUBREPORT_DIR", JsfUti.getRealPath("//reportes//digitacion").concat("//"));
        servletSession.setTieneDatasource(Boolean.TRUE);

        JsfUti.redirectFacesNewTab("/DocumentoExcel");
    }
    
    public void consultaPrediosPorPalabrasClaves() throws SQLException, IOException{
        if(ajustarOpcionesBusqueda()){
            servletSession.instanciarParametros();
            servletSession.agregarParametro("BUSQUEDA", entrada);
            servletSession.setNombreReporte("digitacion//reporte_predial");
            servletSession.agregarParametro("SUBREPORT_DIR", JsfUti.getRealPath("//reportes//digitacion").concat("//"));
            servletSession.setTieneDatasource(Boolean.TRUE);

            JsfUti.redirectFacesNewTab("/DocumentoExcel");
        }else
            JsfUti.messageInfo(null, "Info", "Error al ingresar la información");
    }
    
    public void consultaDigitacion(){
        try{
            servletSession.instanciarParametros();
            servletSession.agregarParametro("FECHA_INICIO", fecha);
            servletSession.agregarParametro("FECHA_FIN", fecha);
            servletSession.agregarParametro("USUARIO", username);
            servletSession.agregarParametro("ciRuc", "");
            servletSession.setNombreReporte("digitacion//reporte_digitadoras");
            servletSession.agregarParametro("SUBREPORT_DIR", JsfUti.getRealPath("//reportes//digitacion").concat("//"));
            
            //servletSession.setNombreSubCarpeta("");
            servletSession.setTieneDatasource(Boolean.TRUE);
            JsfUti.redirectFacesNewTab("/DocumentoExcel");    
            /*this.reportes.generarReporte();
            servletSession.setNombreDocumento("prueba");        
            this.reportes.descargarPDFarregloBytes(servletSession.getReportePDF());*/
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public PrediosLazy getListaPredios() {
        return listaPredios;
    }

    public void setListaPredios(PrediosLazy listaPredios) {
        this.listaPredios = listaPredios;
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

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public ServletSession getServletSession() {
        return servletSession;
    }

    public void setServletSession(ServletSession servletSession) {
        this.servletSession = servletSession;
    }

    public BloqueService getBloqueService() {
        return bloqueService;
    }

    public void setBloqueService(BloqueService bloqueService) {
        this.bloqueService = bloqueService;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImpuestoPredial getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(ImpuestoPredial impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }

    public ImpuestoPredial getImpuestoPredialAnterior() {
        return impuestoPredialAnterior;
    }

    public void setImpuestoPredialAnterior(ImpuestoPredial impuestoPredialAnterior) {
        this.impuestoPredialAnterior = impuestoPredialAnterior;
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

    public List<OtrosTipoConstruccion> getObrasComplementarias() {
        return obrasComplementarias;
    }

    public void setObrasComplementarias(List<OtrosTipoConstruccion> obrasComplementarias) {
        this.obrasComplementarias = obrasComplementarias;
    }

    public List<InfoBloque> getInfoBloques() {
        return infoBloques;
    }

    public void setInfoBloques(List<InfoBloque> infoBloques) {
        this.infoBloques = infoBloques;
    }

    public boolean isVisualizarDatos() {
        return visualizarDatos;
    }

    public void setVisualizarDatos(boolean visualizarDatos) {
        this.visualizarDatos = visualizarDatos;
    }

    public boolean isConEdificaciones() {
        return conEdificaciones;
    }

    public void setConEdificaciones(boolean conEdificaciones) {
        this.conEdificaciones = conEdificaciones;
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

    public List<UsoSuelo> getUsosBloque() {
        return usosBloque;
    }

    public void setUsosBloque(List<UsoSuelo> usosBloque) {
        this.usosBloque = usosBloque;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public List<String> getDocs() {
        return docs;
    }

    public void setDocs(List<String> docs) {
        this.docs = docs;
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

    public List<ArchivoAux> getImagesBloque() {
        return imagesBloque;
    }

    public void setImagesBloque(List<ArchivoAux> imagesBloque) {
        this.imagesBloque = imagesBloque;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public ImpuestoService getImpuestoService() {
        return impuestoService;
    }

    public void setImpuestoService(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public AvaluoService getAvaluoService() {
        return avaluoService;
    }

    public void setAvaluoService(AvaluoService avaluoService) {
        this.avaluoService = avaluoService;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }
    
}
