/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.ZonaHomoValorService;
import com.dadoco.admin.service.ZonificacionService;
import com.dadoco.cat.model.ZonaHomoValor;
import com.dadoco.cat.model.ZonaHomoValorPK;
import com.dadoco.cat.model.ZonaHomogenea;
import com.dadoco.cat.service.ZonaHomogeneaService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "zonasHomoView")
@ViewScoped
public class ZonasHomoViewController implements Serializable {
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ZonasHomoViewController.class);
    
    @EJB
    private ZonaHomogeneaService homogeneaService;
    @EJB
    private ZonaHomoValorService homoValorService;
    @EJB
    private ZonificacionService zonificacionService;
    
    private List<ZonaHomoValor> zonaHomoValores;
    private List<ZonaHomoValor> zonaHomoValoresFiltrados;
    private ZonaHomoValor  zonaHomoValorSeleccionado;
    
    private boolean creando;
    private boolean editando;
    private short anioEmision;
    private short anioActual;
    
    @PostConstruct
    public void init() {
        
        anioActual = Util.ANIO_ACTUAL.shortValue();
        anioEmision = Util.ANIO_PROXIMO;
        
        zonaHomoValores = new ArrayList<>();
        zonaHomoValores = homoValorService.findByNamedQuery("ZonaHomoValor.findAll", anioEmision);
        
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    public ZonaHomoValor getZonaHomoValorSeleccionado() {
        return zonaHomoValorSeleccionado;
    }

    public void setZonaHomoValorSeleccionado(ZonaHomoValor zonaHomoValorSeleccionado) {
        this.zonaHomoValorSeleccionado = zonaHomoValorSeleccionado;
    }

    public ZonaHomoValorService getHomoValorService() {
        return homoValorService;
    }

    public void setHomoValorService(ZonaHomoValorService homoValorService) {
        this.homoValorService = homoValorService;
    }

    public List<ZonaHomoValor> getZonaHomoValores() {
        zonaHomoValores = homoValorService.findByNamedQuery("ZonaHomoValor.findAll", anioEmision);
        return zonaHomoValores;
    }

    public void setZonaHomoValores(List<ZonaHomoValor> zonaHomoValores) {
        this.zonaHomoValores = zonaHomoValores;
    }

    public List<ZonaHomoValor> getZonaHomoValoresFiltrados() {
        return zonaHomoValoresFiltrados;
    }

    public void setZonaHomoValoresFiltrados(List<ZonaHomoValor> zonaHomoValoresFiltrados) {
        this.zonaHomoValoresFiltrados = zonaHomoValoresFiltrados;
    }

    
    public short getAnioEmision() {
        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

    public short getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }
    
  
    
    public boolean isCreando() {
        return creando;
    }
    
    public void setCreando(boolean creando) {
        this.creando = creando;
    }
    
    public boolean isEditando() {
        return editando;
    }
    
    public void setEditando(boolean editando) {
        this.editando = editando;
    }
    
    
    public ZonificacionService getZonificacionService() {
        return zonificacionService;
    }
    
    public void setZonificacionService(ZonificacionService zonificacionService) {
        this.zonificacionService = zonificacionService;
    }
    
    public ZonaHomogeneaService getHomogeneaService() {
        return homogeneaService;
    }
    
    public void setHomogeneaService(ZonaHomogeneaService homogeneaService) {
        this.homogeneaService = homogeneaService;
    }
    
//</editor-fold>
    
    public void inicializarNuevo() {
        
    }
    
    public void cancelarCreacion() {
        init();
    }
    
    public void inicializarEdicion(ZonaHomoValorPK id) {
        
        zonaHomoValorSeleccionado= homoValorService.find(id);
        if (zonaHomoValorSeleccionado != null) {
           
            creando = false;
            editando = true;
        } else {
            creando = true;
            editando = false;
            zonaHomoValorSeleccionado = new ZonaHomoValor();
        }
        
    }
    
    public void crear() {
        try {
            
            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();
            log.error("Nex ID: " + homogeneaService.nextZonaHomoId());
            RequestContext context = RequestContext.getCurrentInstance();
           // zonaHomogeneaSeleccionada.setId(homogeneaService.nextZonaHomoId());
            
           
            
           // homogeneaService.create(zonaHomogeneaSeleccionada);
            
            context.execute("PF('addDialog').hide();");
//            context.update("listado-form,messages,addDlg,table-list");
          //  JsfUtil.addInformationMessage("Zona Homogenea Creada", "Zona Homogenea " + zonaHomogeneaSeleccionada.getNombreZona() + " creada con éxito.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }
    
    public void editar() {
        try {
            
            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();
            
            RequestContext context = RequestContext.getCurrentInstance();
            
           homoValorService.edit(zonaHomoValorSeleccionado);
            homogeneaService.edit(zonaHomoValorSeleccionado.getZonaHomogenea());

            //zonificacionService.editarManzana(manzanaSeleccionada, codZonaHomo, files);
            context.execute("PF('addDialog').hide();");
            //context.update("listado-form,messages,addDlg,table-list");
            JsfUtil.addInformationMessage("", "Zona Homogenea " + zonaHomoValorSeleccionado.getZonaHomogenea().getCodigoZona() + " modificada con éxito.");
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }
    
    public void eliminar(Integer id) {
        try {
            ZonaHomogenea zh = homogeneaService.find(id);
            homogeneaService.remove(zh);
            JsfUtil.addInformationMessage("Zona Homogenea", "Zona Homogenea " + zh.getNombreZona() + " eliminada con éxito.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
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
        
        UIInput uiP = (UIInput) components.findComponent("cod_zona");
        String p = uiP.getLocalValue() == null ? ""
                : uiP.getLocalValue().toString();
        
        UIInput uiZ = (UIInput) components.findComponent("nombre_zona");
        String z = uiZ.getLocalValue() == null ? ""
                : uiZ.getLocalValue().toString();
        
        UIInput uiS = (UIInput) components.findComponent("valor_zona");
        String s = uiS.getLocalValue() == null ? ""
                : uiS.getLocalValue().toString();
        
        UIInput uiValorE = (UIInput) components.findComponent("valor_esquinero");
        String valorE = uiValorE.getLocalValue() == null ? ""
                : uiValorE.getLocalValue().toString();
        
        UIInput uiValorM = (UIInput) components.findComponent("valor_esquinero");
        String valorM = uiValorM.getLocalValue() == null ? ""
                : uiValorM.getLocalValue().toString();
        
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
        if (valorE.equals("")) {
            errores++;
            uiValorE.setValid(false);
        }
        if (valorM.equals("")) {
            errores++;
            uiValorM.setValid(false);
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
