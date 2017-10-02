/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.ZonificacionService;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaArchivo;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.ParroquiaPK;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaHomogenea;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.ManzanaArchivoService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.ZonaHomogeneaService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.util.Base64;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "manzanaView")
@ViewScoped
public class ManzanaViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ManzanaViewController.class);

    @EJB
    private ConfigReader config;

    @EJB
    private ManzanaArchivoService manzanaArchivoService;
    @EJB
    private ParroquiaService parroquiaService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private ManzanaService manzanaService;
    @EJB
    private ZonaHomogeneaService homogeneaService;
    @EJB
    private ZonificacionService zonificacionService;

    private List<SelectItem> parroquias;
    private List<SelectItem> zonas;
    private List<SelectItem> sectores;

    private List<Manzana> manzanas;
    private List<Manzana> manzanasFiltradas;

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;

    private Integer codZonaHomo;
    private List<ZonaHomogenea> zonasHomogeneas;
    private ZonaHomogenea zonaHomogeneaSeleccionada;

    private Manzana manzanaSeleccionada;

    private boolean creandoManzana;
    private boolean editandoManzana;
    private String codigoManzana;

    private List<UploadedFile> fotos;
    private UploadedImage[] datosFotos;

    private String codigoOriginal;

    @PostConstruct
    public void init() {

        provinciaCod = Util.provincia_por_defecto;
        cantonCod = Util.canton_por_defecto;

        manzanaSeleccionada = new Manzana();
        manzanas = manzanaService.findAll();
        parroquias = getParroquias();
        zonas = new ArrayList<>();
        sectores = new ArrayList<>();
        zonasHomogeneas = homogeneaService.findByNamedQuery("ZonaHomogenea.findAll");
       
        creandoManzana = false;
        editandoManzana = false;
        codigoManzana = "";
        fotos = new ArrayList<>();
        datosFotos = new UploadedImage[2];

    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public String getCodigoOriginal() {
        return codigoOriginal;
    }

    public void setCodigoOriginal(String codigoOriginal) {
        this.codigoOriginal = codigoOriginal;
    }

    public ZonificacionService getZonificacionService() {
        return zonificacionService;
    }

    public void setZonificacionService(ZonificacionService zonificacionService) {
        this.zonificacionService = zonificacionService;
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

    public Integer getCodZonaHomo() {
        return codZonaHomo;
    }

    public void setCodZonaHomo(Integer codZonaHomo) {
        this.codZonaHomo = codZonaHomo;
    }

    public ManzanaArchivoService getManzanaArchivoService() {
        return manzanaArchivoService;
    }

    public void setManzanaArchivoService(ManzanaArchivoService manzanaArchivoService) {
        this.manzanaArchivoService = manzanaArchivoService;
    }

    public List<ZonaHomogenea> getZonasHomogeneas() {
        return zonasHomogeneas;
    }

    public void setZonasHomogeneas(List<ZonaHomogenea> zonasHomogeneas) {
        this.zonasHomogeneas = zonasHomogeneas;
    }

    public ZonaHomogenea getZonaHomogeneaSeleccionada() {
        return zonaHomogeneaSeleccionada;
    }

    public void setZonaHomogeneaSeleccionada(ZonaHomogenea zonaHomogeneaSeleccionada) {
        this.zonaHomogeneaSeleccionada = zonaHomogeneaSeleccionada;
    }

   

    public ZonaHomogeneaService getHomogeneaService() {
        return homogeneaService;
    }

    public void setHomogeneaService(ZonaHomogeneaService homogeneaService) {
        this.homogeneaService = homogeneaService;
    }

    public ParroquiaService getParroquiaService() {
        return parroquiaService;
    }

    public void setParroquiaService(ParroquiaService parroquiaService) {
        this.parroquiaService = parroquiaService;
    }

    public String getCodigoManzana() {
        return codigoManzana;
    }

    public void setCodigoManzana(String codigoManzana) {
        this.codigoManzana = codigoManzana;
    }

    public List<Manzana> getManzanasFiltradas() {
        return manzanasFiltradas;
    }

    public void setManzanasFiltradas(List<Manzana> manzanasFiltradas) {
        this.manzanasFiltradas = manzanasFiltradas;
    }

    public Manzana getManzanaSeleccionada() {
        return manzanaSeleccionada;
    }

    public void setManzanaSeleccionada(Manzana manzanaSeleccionada) {
        this.manzanaSeleccionada = manzanaSeleccionada;
    }

    public boolean isCreandoManzana() {
        return creandoManzana;
    }

    public void setCreandoManzana(boolean creandoManzana) {
        this.creandoManzana = creandoManzana;
    }

    public boolean isEditandoManzana() {
        return editandoManzana;
    }

    public void setEditandoManzana(boolean editandoManzana) {
        this.editandoManzana = editandoManzana;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
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

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public List<SelectItem> getParroquias() {
        parroquias = new ArrayList<SelectItem>();
        List<Parroquia> ps = parroquiaService.findAll();
        for (Parroquia p : ps) {
            parroquias.add(new SelectItem(p.getParroquiaPK().getCodParroquia(), "" + p.getParroquiaPK().getCodParroquia() + "-" + p.getNombre()));
        }
        return parroquias;
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

    public List<Manzana> getManzanas() {
        manzanas = manzanaService.findAll();
        return manzanas;
    }

    public void setManzanas(List<Manzana> manzanas) {
        this.manzanas = manzanas;
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

//</editor-fold>
    public void inicializarNuevo() {
        manzanaSeleccionada = new Manzana();
        manzanaCod = null;
        parroquias = getParroquias();
        creandoManzana = true;
        editandoManzana = false;

    }

    public void cancelarCreacion() {
        init();
    }

    public void inicializarEdicion(String pkString) {

        log.error("Clave: " + pkString);

        String[] clave = pkString.split("-");

        if (clave.length == 6) {

            log.error("len: " + clave.length);
            parroquiaCod = clave[2];

            zonaCod = clave[3];
            sectores = listaSectoresPorZona(zonaCod);
            sectorCod = clave[4];
            manzanaCod = clave[5];

            ManzanaPK pk = new ManzanaPK(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod);

            manzanaSeleccionada = manzanaService.find(pk);
            //zonaHomogeneaSeleccionada = manzanaSeleccionada.getZonaHomogenea();
            codigoOriginal = manzanaCod;
            
            log.error("ID de la zona desde el inicializar: " + manzanaSeleccionada.getZonaHomogenea().getId());

            //manzanaSeleccionada.setZonaHomogenea(zonaHomogeneaSeleccionada);
            //codZonaHomo = manzanaSeleccionada.getZonaHomogenea().getId();

            editandoManzana = true;
            creandoManzana = false;
        } else {
            manzanaSeleccionada = new Manzana();
            editandoManzana = false;
            creandoManzana = false;
        }

    }

    public String crear() {
        try {
            
            if(manzanaCod.length() < 3){
                JsfUti.messageError(null, "Info", "El código de la manzana debe ser de tres dígitos.");
                return "";
            }
            
            ManzanaPK pk = new ManzanaPK();
            pk.setCodProvincia(provinciaCod);
            pk.setCodCanton(cantonCod);
            pk.setCodParroquia(parroquiaCod);
            pk.setCodZona(zonaCod);
            pk.setCodSector(sectorCod);
            pk.setCodManzana(manzanaCod);

            manzanaSeleccionada.setManzanaPK(pk);

            RequestContext context = RequestContext.getCurrentInstance();
            Manzana mz = manzanaService.find(pk);

            if (mz != null) {
                context.update("datos-messages");
                JsfUtil.addErrorMessage("La manzana ya se encuentra registrada.");
            } else {
                List<UploadedImage> files = guardarFotos();
                //manzanaSeleccionada.setZonaHomogenea(zonaHomogeneaSeleccionada);
                zonificacionService.crearManzana(manzanaSeleccionada, codZonaHomo, files);
                context.execute("PF('addDialog').hide();");
                JsfUtil.addInformationMessage("Manzana creada", "Manzana " + manzanaSeleccionada.toString() + " creada con éxito.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
        return "pretty:pretty:cat-crear-predio";
    }

    public void editar() {
        try {
            if(manzanaCod.length() < 3){
                JsfUti.messageError(null, "Info", "El código de la manzana debe ser de tres dígitos.");
                return;
            }
            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();

            ManzanaPK pk = new ManzanaPK();
            pk.setCodProvincia(provinciaCod);
            pk.setCodCanton(cantonCod);
            pk.setCodParroquia(parroquiaCod);
            pk.setCodZona(zonaCod);
            pk.setCodSector(sectorCod);
            pk.setCodManzana(manzanaCod);

            manzanaSeleccionada.setManzanaPK(pk);
            //ZonaHomogenea zonaH = homogeneaService.find(codZonaHomo);
            //manzanaSeleccionada.setZonaHomogenea(zonaHomogeneaSeleccionada);
            
            RequestContext context = RequestContext.getCurrentInstance();

            ManzanaArchivo archivo = new ManzanaArchivo();
            List<UploadedImage> files = guardarFotos();

            if (files.isEmpty()) {
                manzanaService.edit(manzanaSeleccionada);
                //zonaHomogeneaSeleccionada.getManzanas().add(manzanaSeleccionada);
                //homogeneaService.edit(zonaHomogeneaSeleccionada);
            } else {
                UploadedImage ui = files.get(0);
                archivo.setAutor(user);
                archivo.setUsuarioModifica(user);
                archivo.setRuta(ui.getSavedFile().getName());
                archivo.setDescripcion(ui.getDescription());
                manzanaArchivoService.create(archivo);
                manzanaSeleccionada.setManzanero(archivo);
                manzanaService.edit(manzanaSeleccionada);
                //zonaHomogeneaSeleccionada.getManzanas().add(manzanaSeleccionada);
                //homogeneaService.edit(zonaHomogeneaSeleccionada);
            }
            context.execute("PF('addDialog').hide();");
            JsfUtil.addInformationMessage("Manzana actualizada", "Manzana " + manzanaSeleccionada.toString() + " actualizada con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void eliminar(String pkString) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            String[] m=pkString.split("\\-");
            ManzanaPK pk=new ManzanaPK();
            manzanaSeleccionada=manzanaService.getManzana(new ManzanaPK(m[0], m[1],m[2], m[3], m[4], m[5]));
            manzanaService.remove(manzanaSeleccionada);
            context.execute("PF('addDialog').hide();");
            JsfUtil.addInformationMessage("Manzana Eliminada", "Manzana " + manzanaSeleccionada.toString() + " actualizada con éxito.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public List<SelectItem> listaZonasPorParroquia(String parroquia) {

        ParroquiaPK pk = new ParroquiaPK();
        pk.setCodProvincia(provinciaCod);
        pk.setCodCanton(cantonCod);
        pk.setCodParroquia(parroquia);

        Parroquia p = parroquiaService.find(pk);

        zonas = new ArrayList<>();
        sectores = new ArrayList<>();

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

        sectores = new ArrayList<>();

        if (z != null) {
            List<Sector> ss = z.getSectorCollection();
            for (Sector s : ss) {
                sectores.add(new SelectItem(s.getSectorPK().getCodSector(), "" + s.getSectorPK().getCodSector() + "-" + s.getNombre()));
            }
        }

        return sectores;
    }

    private List<UploadedImage> guardarFotos() {
        List<UploadedImage> copiados = new ArrayList<UploadedImage>();
        for (int i = 0; i < fotos.size(); i++) {
            UploadedFile uploadedFile = fotos.get(i);
            try {

                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_manzaneros");
                String nameFile = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "_mz_" + UUID.randomUUID();
                File destFile = new File(new File(uploadDir), nameFile + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedImage image = datosFotos[i];
                image.setSavedFile(destFile);
//
//                Rectangle pageSize = new Rectangle(2780, 2525);
//                Document pdfDocument = new Document(pageSize);
//
//                FileOutputStream fileOutputStream = new FileOutputStream(uploadDir + "/" + nameFile + ".pdf");
//                PdfWriter writer = null;
//                writer = PdfWriter.getInstance(pdfDocument, fileOutputStream);
//                writer.open();
//                pdfDocument.open();
//                pdfDocument.add(com.lowagie.text.Image.getInstance(""));

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

    private String getBase64DataFromFile(byte[] data, String tipo) {

        return "data:image/" + tipo + ";base64," + Base64.encodeToString(data, false);
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

        UIInput uiZH = (UIInput) components.findComponent("zona-homo-select");
        String zh = uiS.getLocalValue() == null ? ""
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
        if (zh.equals("")) {
            errores++;
            uiZH.setValid(false);
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

//    public void fotoPredio(int idPredio) {
//
//        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
//                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
//
//        String foto;
//        String pathToPhoto = "";
//
//         String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
//
//            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
//
//
//            if (existeArchivo(uploadDir + "/" + foto)) {
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
//
//        images = new ArrayList<String>();
//
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
//
//    }
    public boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
    }
}
