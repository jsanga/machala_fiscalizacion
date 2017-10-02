/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.rectificar;

import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.controller.OpcionesBusquedaPredio;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.cat.model.TipoEscritura;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.DatosAutorizacionService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.util.Base64;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "rectificarDatosGeneralesView")
@ViewScoped
public class RectificarDatosGeneralesController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RectificarDatosGeneralesController.class);
    @EJB
    private ConfigReader config;
    @EJB
    private CatastroService catastroService;
    @EJB
    private PredioService predioService;

    @EJB
    private UsuarioService usuarioService;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private String user;
    private Predio predio;
    private List<UploadedFile> fotos;
    private UploadedImage[] datosFotos;

    @PostConstruct
    public void init() {

        Subject subject = SecurityUtils.getSubject();

        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);

        predio = new Predio();

        fotos = new ArrayList<UploadedFile>();
        datosFotos = new UploadedImage[30];
    }

    public void buscarPredio() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
       // short phCod = opcionesBusqueda.getPhCod();

        try {
            //predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);

            if (predio == null) {
//                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %2d-%2d-%3d-%2d-%2d no encontrado",
//                        zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//                init();
                return;
            } else {

                opcionesBusqueda.setEjecutandoAccion(true);

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public String actualizarDatosGenerales() {

        Subject subject = SecurityUtils.getSubject();

        String usuar = subject.getPrincipal().toString();
        try {
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(usuar);

            //catastroService.actualizarDatosGenerales(predio, idTipoEscritura, idTipoDocumento, escritura, datosAutorizacion);
            JsfUtil.addInformationMessage("Predio Actualizado", "Predio " + predio.getClaveCatastral() + "actualizado correctamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        opcionesBusqueda.setEjecutandoAccion(false);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
        //return "pretty:cat-mod-datos-generales";
    }

    public String cancelar() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
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
}
