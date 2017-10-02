/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.DatoInspeccion;
import com.dadoco.cat.model.InfoBloque;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
 * @author Dairon
 */
@Named(value = "registroInpView")
@ViewScoped
public class RegistroInspeccionController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RegistroInspeccionController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private TipoDocumentoService tipoDocumentoService;
    @EJB
    private AvaluosService avaluosService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private PredioService predioService;

    private DatosAutorizacion datosAutorizacion;
    private List<SelectItem> tiposDocumentos;
    private Long idTipoDocumento;
    private DatoInspeccion datoInspeccion;

    private OpcionesBusquedaPredio opcionesBusqueda;
    private Predio predio;
    private List<Contribuyente> propietarios;
    private Contribuyente propietarioSeleccionado;
    private boolean personeria;

    private List<UploadedFile> fotos;
    private UploadedImage[] datosFotos;

    private Usuario inspector;
    private List<SelectItem> usuarios;
    private Integer idUsuario;

    private String avaluoTerreno;
    private String avaluoConstruccion;
    private String avaluoComercial;

    private double coord_X;
    private double coord_Y;
    String claveGeoreferenciada;
    String claveOnclic;
    private short anioActual;
    
    private Predio predioOnClic;

    private StreamedContent content;

    @PostConstruct
    public void init() {
        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        propietarioSeleccionado = new Contribuyente();
        propietarios = new ArrayList<Contribuyente>();
        personeria = true;

        tiposDocumentos = new ArrayList<SelectItem>();
        datosAutorizacion = new DatosAutorizacion();
        datosAutorizacion.setResponsable(user);
        datoInspeccion = new DatoInspeccion();
        datoInspeccion.setSecuencial(catastroService.NumeroProximaInspeccion());

        usuarios = new ArrayList<SelectItem>();
        fotos = new ArrayList<UploadedFile>();
        datosFotos = new UploadedImage[30];
        predioOnClic = new Predio();
    }

    public String onFlowProcess(FlowEvent event) {

        return event.getNewStep();
    }

    public void adicionarConstruccion() {

    }

    public void doSomething() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        claveOnclic = ec.getRequestParameterMap().get("form:input");
        claveOnclic += "-0";

        log.error("Clave Onclic: " + claveOnclic);

        List<Predio> lista = predioService.findByNamedQuery("Predio.findByClaveCatastral", claveOnclic);

        if (!lista.isEmpty()) {
            predioOnClic = lista.get(0);
        } else {
            predioOnClic = new Predio();
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('UsersWidget').show();");
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

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
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

    public String getClaveOnclic() {
        return claveOnclic;
    }

    public void setClaveOnclic(String claveOnclic) {
        this.claveOnclic = claveOnclic;
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

    public String getAvaluoComercial() {
        return avaluoComercial;
    }

    public void setAvaluoComercial(String avaluoComercial) {
        this.avaluoComercial = avaluoComercial;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Usuario getInspector() {
        return inspector;
    }

    public void setInspector(Usuario inspector) {
        this.inspector = inspector;
    }

    public List<SelectItem> getUsuarios() {
        List<Usuario> lista = usuarioService.findAll();
        for (Usuario u : lista) {
            usuarios.add(new SelectItem(u.getId(), u.getNombre()));
        }
        return usuarios;
    }

    public void setUsuarios(List<SelectItem> usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public AvaluosService getAvaluosService() {
        return avaluosService;
    }

    public void setAvaluosService(AvaluosService avaluosService) {
        this.avaluosService = avaluosService;
    }

    public DatoInspeccion getDatoInspeccion() {
        return datoInspeccion;
    }

    public void setDatoInspeccion(DatoInspeccion datoInspeccion) {
        this.datoInspeccion = datoInspeccion;
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Contribuyente> propietarios) {
        this.propietarios = propietarios;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public boolean isPersoneria() {
        return personeria;
    }

    public void setPersoneria(boolean personeria) {
        this.personeria = personeria;
    }

    public TipoDocumentoService getTipoDocumentoService() {
        return tipoDocumentoService;
    }

    public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public DatosAutorizacion getDatosAutorizacion() {
        return datosAutorizacion;
    }

    public void setDatosAutorizacion(DatosAutorizacion datosAutorizacion) {
        this.datosAutorizacion = datosAutorizacion;
    }

    public List<SelectItem> getTiposDocumentos() {
        List<TipoDocumento> docs = tipoDocumentoService.findAll();

        for (TipoDocumento td : docs) {
            tiposDocumentos.add(new SelectItem(td.getId(), td.getNombre()));
        }
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
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

    public Predio getPredio() {
        return predio;
    }

    public VariableService getVariablesService() {
        return variablesService;
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="buscarPredio">
    public void buscarPredio() {
        ajustarOpcionesBusqueda();
        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
        String bloqueCod = opcionesBusqueda.getBloqueCod();
        String phvCod = opcionesBusqueda.getPhvCod();
        String phhCod = opcionesBusqueda.getPhhCod();

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat anotherDFormat = (DecimalFormat) numberFormat;
        anotherDFormat.applyPattern("#.00");
        anotherDFormat.setGroupingUsed(true);
        anotherDFormat.setGroupingSize(3);

       predio = catastroService.obtenerPredio(
                provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
        if (predio == null) {
            JsfUtil.addErrorMessage("Predio no encontrado");
        } else {

            propietarios = predio.getPropietarios();
            if (propietarios.isEmpty()) {
                propietarios = new ArrayList<>();
            }

            //double aT = avaluosService.avaluoTerreno(predio);
            avaluoTerreno = "";//anotherDFormat.format(aT);
            // double aC = avaluosService.avaluoConstruccion(predio);

            avaluoConstruccion = "";// anotherDFormat.format(aC);
            avaluoComercial = "";//anotherDFormat.format(aC + aT);

            opcionesBusqueda.setEjecutandoAccion(true);

            Object[] coord = catastroService.coordenadas(predio.getClaveCatastral());
            if (coord != null) {
                coord_X = Double.parseDouble(coord[0].toString());
                coord_Y = Double.parseDouble(coord[1].toString());
                claveGeoreferenciada = coord[2].toString();
            }
        }
    }
//</editor-fold>

    public String cancelarModificacion() {
        return "pretty:cat-registro-inspeccion";
    }

    public String registrarInspeccion() {

        try {

            List<UploadedImage> files = guardarFotos();
//            datoInspeccion.setLinderoInspeccion(linderoInspeccion);

            catastroService.registrarInspeccion(predio, datoInspeccion, idTipoDocumento, datosAutorizacion, idUsuario, files);
            JsfUtil.addInformationMessage("Inspección registrada", "Predio con clave: " + predio.getClaveCatastral() + " se ha registrado la inspección " + datoInspeccion.getSecuencial() + ".");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

        return "pretty:cat-registro-inspeccion";
    }

    public void onRowSelect(SelectEvent event) {
        propietarioSeleccionado = (Contribuyente) event.getObject();
        personeria = !propietarioSeleccionado.getTipo().equals("J");

    }

    public void onRowUnselect(UnselectEvent event) {

        propietarioSeleccionado = (Contribuyente) event.getObject();
        personeria = !propietarioSeleccionado.getTipo().equals("J");

    }

    private List<UploadedImage> guardarFotos() {
        List<UploadedImage> copiados = new ArrayList<UploadedImage>();
        for (int i = 0; i < fotos.size(); i++) {
            UploadedFile uploadedFile = fotos.get(i);
            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");

                File destFile = new File(new File(uploadDir), UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));

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

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

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

    public List<InfoBloque> creaListaBloques() {

        List<InfoBloque> lista = new ArrayList<>();

//        if (predio.getBloqueList().isEmpty()) {
//            return lista;
//        }
        InfoBloque datosBloque;

//        for (Bloque b : predio.getBloqueList()) {
//
//            datosBloque = new InfoBloque(
//                    b.getNumeroBloque(),
//                    b.getListaPisos().size(),
//                    b.getAreaTotal().floatValue(),
//                    variablesService.obtenerValor("cat_bloque", "tipo_construccion", b.getTipoConstruccion()),
//                    variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", b.getTipoConstruccion()),
//                    b.getPorcientoConstruccion().floatValue(),
//                    b.getPorcientoConservacion().floatValue(),
//                    avaluosService.avaluoBloque(b)
//            );
//
//            lista.add(datosBloque);
//        }
        return lista;
    }

    public void onPrerender() {

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/jasper/ejemplo.jasper"));

        InputStream inputStream = null;

        Map<String, Object> parameter = new HashMap<String, Object>();

//        parameters.put("ALGUN_PARAMETRO", ID_PARAMETRO);
        try {

            ByteArrayOutputStream Teste = new ByteArrayOutputStream();

            //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream(path.trim()));
            JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), parameter, new JREmptyDataSource());
            // JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

            exporter.exportReport();
            inputStream = new ByteArrayInputStream(Teste.toByteArray());

        } catch (JRException ex) {
        }

        content = new DefaultStreamedContent(inputStream, "application/pdf");

    }

    public void onPrerendeAAAr(ComponentSystemEvent event) {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            for (int i = 0; i < 50; i++) {
                document.add(new Paragraph("All work and no play makes Jack a dull boy"));
            }

            document.close();
            content = new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), "application/pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public short getAnioActual() {
        anioActual = (short)(Util.ANIO_ACTUAL.shortValue());
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }
}
