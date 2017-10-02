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
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "deletePredioView")
@ViewScoped
public class EliminarPredioController implements Serializable {
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EliminarPredioController.class);
    
    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private TipoDocumentoService tipoDocumentoService;
    @EJB
    private PredioService predioService;
    @EJB
    private DeudaService deudaService;
    
    private DatosAutorizacion datosAutorizacion;
    private List<TipoDocumento> tiposDocumentos;
    private TipoDocumento tipoDocumentoSeleccionado;
    
    private OpcionesBusquedaPredio opcionesBusqueda;
    private Predio predio;
    private List<Contribuyente> propietarios;
    private Contribuyente propietarioSeleccionado;
    private boolean personeria;
    private List<ArchivoAux> fotosPreview;
    
    @PostConstruct
    public void init() {
        
        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);
        
        propietarioSeleccionado = new Contribuyente();
        propietarios = new ArrayList<Contribuyente>();
        personeria = true;
        
        tiposDocumentos = tipoDocumentoService.findAll();
        tipoDocumentoSeleccionado = tiposDocumentos.isEmpty() ? null : tiposDocumentos.get(0);
        datosAutorizacion = new DatosAutorizacion();
        datosAutorizacion.setResponsable(user);
        
    }
    
    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
    
    public void adicionarConstruccion() {
        
    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
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
    
    public List<TipoDocumento> getTiposDocumentos() {
        tiposDocumentos = tipoDocumentoService.findAll();
        return tiposDocumentos;
    }
    
    public void setTiposDocumentos(List<TipoDocumento> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }
    
    public DeudaService getDeudaService() {
        return deudaService;
    }
    
    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }
    
    public TipoDocumento getTipoDocumentoSeleccionado() {
        return tipoDocumentoSeleccionado;
    }
    
    public void setTipoDocumentoSeleccionado(TipoDocumento tipoDocumentoSeleccionado) {
        this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
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

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="buscarPredio">
    public void buscarPredio() {
        
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
        
        try {
            predio = catastroService.obtenerPredio(
                    provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
            
            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {

//                if (comprobarDeudas(predio)) {
//                    JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral: %d-%d-%d-%d-%d-%d, tiene deuda pendientes con el municipio.",
//                            parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//
//                    return;
//                }
                propietarios = predio.getPropietarios();
                if (!propietarios.isEmpty()) {
                    propietarioSeleccionado = propietarios.get(0);
                    if (propietarioSeleccionado.getTipo().equals("J")) {
                        personeria = false;
                    }
                } else {
                    propietarioSeleccionado = new Contribuyente();
                }
                opcionesBusqueda.setEjecutandoAccion(true);
                fotoPredio(predio.getId());
                
            }
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }
//</editor-fold>

    private boolean comprobarDeudas(Predio p) {
        
        Object[] params = new Object[2];
        params[0] = p.getClaveCatastral();
        params[1] = "NO PAGADO";
        List<Deuda> deudas = deudaService.findByNamedQuery("Deuda.findByClaveCatastralCheck", params);
        
        return !deudas.isEmpty();
    }
    
    public String cancelarModificacion() {
        return "pretty:cat-delete-datos-construccion";
    }
    
    public String EliminarPredio() {
        
        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
        // short phCod = opcionesBusqueda.getPhCod();
        try {
            datosAutorizacion.setIdTipoDocumento(tipoDocumentoSeleccionado);
            catastroService.eliminarPredio(predio, datosAutorizacion);
//            
//            Email email = new SimpleEmail();
//            email.setHostName(config.getDbConfiguration().getString("email_host_name"));
//            email.setSmtpPort(config.getDbConfiguration().getInt("email_port"));
//            email.setAuthenticator(new DefaultAuthenticator(config.getDbConfiguration().getString("email_user_host"), config.getDbConfiguration().getString("email_password_host")));
//            email.setSSLOnConnect(true);
//            email.setFrom(config.getDbConfiguration().getString("email_to_send"));
//            email.setSubject("Eliminacion de predio");
//            email.setMsg("El predio con clave: " + predio.getClaveCatastral() + "ha sido eliminado por el usuario: " + user + " ,con fecha: " + new Date().toString() + ".");
//            email.send();
            
            JsfUtil.addInformationMessage("Predio eliminado", "Predio eliminado con exito.");
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
//        JsfUtil.addSuccessMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d Eliminado exitosamente.",
//                parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod));
        return "pretty:cat-eliminar-predio";
    }
    
    public void onRowSelect(SelectEvent event) {
        propietarioSeleccionado = (Contribuyente) event.getObject();
        personeria = !propietarioSeleccionado.getTipo().equals("J");
        
    }
    
    public void onRowUnselect(UnselectEvent event) {
        
        propietarioSeleccionado = (Contribuyente) event.getObject();
        personeria = !propietarioSeleccionado.getTipo().equals("J");
        
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
            
            String uploadDir = config.getDbConfiguration().getString("directorio_imagenes");
            
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
