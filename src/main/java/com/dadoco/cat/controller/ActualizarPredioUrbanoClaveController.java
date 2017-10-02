/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.audit.service.AuditService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
import com.icl.generics.Versioner;
import com.icl.generics.models.Historic;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "updateClaveView")
@ViewScoped
public class ActualizarPredioUrbanoClaveController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActualizarPredioUrbanoClaveController.class);
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private PredioService predioService;    
    @EJB
    private AuditService audit;

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
    private String pant;
    private Terreno terreno;

    private Predio predio, predioAnt;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private List<UploadFile> fotos;
    private List<ArchivoAux> fotosPreview;

    private List<Contribuyente> propietarios;

    private short anioActual;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);

        predio = new Predio();
        terreno = new Terreno();
        fotos = new ArrayList<>();
        propietarios = new ArrayList<>();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public short getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
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

    public List<ArchivoAux> getFotosPreview() {
        return fotosPreview;
    }

    public void setFotosPreview(List<ArchivoAux> fotosPreview) {
        this.fotosPreview = fotosPreview;
    }

    public List<SelectItem> valoresVariables(String tabla, String columna) {

        short anio = (short) (Util.ANIO_ACTUAL.shortValue());
        List<SelectItem> lista = new LinkedList<>();
        List<ValorDiscreto> valores = variablesService.obtenerValores(tabla, columna, anio);
        for (ValorDiscreto v : valores) {
            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
            lista.add(i);
        }
        return lista;

    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

//</editor-fold>
    public void buscar() {

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

        try {
            predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {
                predioAnt = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
                pant = new Versioner().getJson(predioAnt);
                opcionesBusqueda.setEjecutandoAccion(true);
                terreno = predio.getTerreno();
                fotoPredio(predio.getId());
                propietarios = predio.getPropietarios();
                if (!predio.getPropietarios().isEmpty()) {
                    for (int i = 0; i < predio.getPropietarios().size(); i++) {
                        predio.getPropietarios().get(i).obtenerEstatus(predio.getId());
                    }
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
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(user);

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest htservlet = (HttpServletRequest) fc.getExternalContext().getRequest();
            InetAddress add = InetAddress.getByName(htservlet.getRemoteAddr());
            
            Predio predioSelect = catastroService.obtenerPredio(predio.getTerreno().getTerrenoPK().getCodProvincia(),
                    predio.getTerreno().getTerrenoPK().getCodCanton(),
                    predio.getTerreno().getTerrenoPK().getCodParroquia(),
                    predio.getTerreno().getTerrenoPK().getCodZona(),
                    predio.getTerreno().getTerrenoPK().getCodSector(),
                    predio.getTerreno().getTerrenoPK().getCodManzana(),
                    predio.getTerreno().getTerrenoPK().getCodSolar(),
                    predio.getCodBloque(),
                    predio.getCodPhv(),
                    predio.getCodPhh());

            //registroHistorico(predioSelect, predioSelect.getTerreno(), auditoria, false);

            catastroService.updateDatosGenerales(predio, terreno, fotos, fotosPreview);
            audit.save(predioAnt.getClaveCatastral(), "Actualizacion Clave Catastral y Locallizacion", pant, new Versioner().getJson(predio), user);
            //registroHistorico(predio, terreno, auditoria, true);

            JsfUtil.addInformationMessage(null, "Predio " + predio.getClaveCatastral() + " actualizado correctamente.");
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

        return event.getNewStep();

    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_FP_";
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

}
