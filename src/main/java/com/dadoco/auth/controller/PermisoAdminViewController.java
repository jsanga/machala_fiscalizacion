/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.controller;

import com.dadoco.auth.service.SecurityService;
import com.dadoco.auth.model.Permiso;
import com.dadoco.auth.service.PermisoService;
import com.dadoco.auth.service.RolService;
import com.dadoco.common.jsf.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "permisoAdminView")
@ViewScoped
public class PermisoAdminViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PermisoAdminViewController.class);

    @EJB
    private RolService rolService;

    @EJB
    private PermisoService permisoService;

    @EJB
    private SecurityService securityService;

    private List<Permiso> permisos;
    private List<Permiso> permisosFiltrados;
    private Permiso permisoSeleccionado;

    private boolean creandoPermiso;
    private boolean editandoPermiso;

    @PostConstruct
    public void init() {
        permisos = new ArrayList<Permiso>();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public RolService getRolService() {
        return rolService;
    }

    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }

    public PermisoService getPermisoService() {
        return permisoService;
    }

    public void setPermisoService(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public List<Permiso> getPermisos() {
        permisos = permisoService.findAll();
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public List<Permiso> getPermisosFiltrados() {
        return permisosFiltrados;
    }

    public void setPermisosFiltrados(List<Permiso> permisosFiltrados) {
        this.permisosFiltrados = permisosFiltrados;
    }

    public Permiso getPermisoSeleccionado() {
        return permisoSeleccionado;
    }

    public void setPermisoSeleccionado(Permiso permisoSeleccionado) {
        this.permisoSeleccionado = permisoSeleccionado;
    }

    public boolean isCreandoPermiso() {
        return creandoPermiso;
    }

    public void setCreandoPermiso(boolean creandoPermiso) {
        this.creandoPermiso = creandoPermiso;
    }

    public boolean isEditandoPermiso() {
        return editandoPermiso;
    }

    public void setEditandoPermiso(boolean editandoPermiso) {
        this.editandoPermiso = editandoPermiso;
    }

//</editor-fold>
    public void inicializarNuevoPermiso() {
        permisoSeleccionado = new Permiso();
        creandoPermiso = true;
        editandoPermiso = false;
    }

    public void cancelarCreacionPermiso() {
        if (creandoPermiso) {
            permisoSeleccionado = null;
        }
        editandoPermiso = false;
        creandoPermiso = false;
    }

//<editor-fold defaultstate="collapsed" desc="inicializarEdicion(int id)">
    public void inicializarEdicion(int id) {
        Subject subject = SecurityUtils.getSubject();
        //subject.checkPermissions("ANA");
        String user = subject.getPrincipal().toString();

        permisoSeleccionado = null;
        editandoPermiso = false;
        for (Permiso p : permisos) {
            if (p.getId().equals(id)) {
                permisoSeleccionado = p;
                editandoPermiso = true;
                break;
            }
        }
        editandoPermiso = true;
        creandoPermiso = false;
    }
//</editor-fold>

    public void crearPermiso() {
        try {
            permisoService.create(permisoSeleccionado);

//            RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('permisoDialog').hide();");
//            context.update("permiso-list-table,messages");
            JsfUtil.addInformationMessage("Permiso creado", "Permiso " + permisoSeleccionado.getPermiso() +" creado con éxito.");
            inicializarNuevoPermiso();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void validarFormulario(ComponentSystemEvent event) {

    }

    public String cancelarModificacion() {
        return "pretty:cat-mod-datos-construccion";
    }

    public void deletepermiso(Integer id) {

        try {
            Permiso p = permisoService.find(id);

            if (p != null) {
                permisoService.remove(p);
                permisoSeleccionado = new Permiso();
                JsfUtil.addInformationMessage("Permiso eliminado", "Permiso " + p.getPermiso() +" eliminado con éxito.");
            }
            
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("permiso-list-table, messages");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }
    
    public void editarPermiso(){
        try {
            permisoService.edit(permisoSeleccionado);

//            RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('permisoDialog').hide();");
//            context.update("permiso-list-table,messages");
            JsfUtil.addInformationMessage("Permiso actualizado", "Permiso " + permisoSeleccionado.getPermiso() +" actualizado con éxito.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }
}
