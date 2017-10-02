/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.controller;

import com.dadoco.auth.service.SecurityService;
import com.dadoco.auth.model.Permiso;
import com.dadoco.auth.model.Rol;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "rolAdminView")
@ViewScoped
public class RolAdminViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RolAdminViewController.class);

    @EJB
    private RolService rolService;

    @EJB
    private PermisoService permisoService;

    @EJB
    private SecurityService securityService;

    private List<Rol> roles;
    private List<Rol> rolesFiltrados;
    private Rol rolSeleccionado;

    private boolean creandoRol;
    private boolean editandoRol;

    private List<Permiso> sourcePermisos;
    private List<Permiso> targetPermisos;
    private List<Permiso> eliminarPermisos;

    private DualListModel<Permiso> permisos;

    @PostConstruct
    public void init() {
        roles = rolService.findAll();
        targetPermisos = new ArrayList<Permiso>();
        eliminarPermisos = new ArrayList<>();
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

    public List<Permiso> getSourcePermisos() {
        sourcePermisos = permisoService.findAll();
        return sourcePermisos;
    }

    public void setSourcePermisos(List<Permiso> sourcePermisos) {
        this.sourcePermisos = sourcePermisos;
    }

    public List<Permiso> getTargetPermisos() {
        return targetPermisos;
    }

    public void setTargetPermisos(List<Permiso> targetPermisos) {
        this.targetPermisos = targetPermisos;
    }

    public DualListModel<Permiso> getPermisos() {
        permisos = new DualListModel<Permiso>(sourcePermisos, targetPermisos);
        return permisos;
    }

    public void setPermisos(DualListModel<Permiso> permisos) {
        this.permisos = permisos;
    }

    public List<Permiso> getEliminarPermisos() {
        return eliminarPermisos;
    }

    public void setEliminarPermisos(List<Permiso> eliminarPermisos) {
        this.eliminarPermisos = eliminarPermisos;
    }

    public List<Rol> getRoles() {
        roles = rolService.findAll();
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Rol> getRolesFiltrados() {
        return rolesFiltrados;
    }

    public void setRolesFiltrados(List<Rol> rolesFiltrados) {
        this.rolesFiltrados = rolesFiltrados;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    public boolean isCreandoRol() {
        return creandoRol;
    }

    public void setCreandoRol(boolean creandoRol) {
        this.creandoRol = creandoRol;
    }

    public boolean isEditandoRol() {
        return editandoRol;
    }

    public void setEditandoRol(boolean editandoRol) {
        this.editandoRol = editandoRol;
    }

//</editor-fold>
    public void inicializarNuevoRol() {
        rolSeleccionado = new Rol();
        creandoRol = true;
        editandoRol = false;

        targetPermisos = new ArrayList<Permiso>();
        sourcePermisos = permisoService.findAll();

        permisos = new DualListModel<Permiso>(sourcePermisos, targetPermisos);
    }

    public void cancelarCreacionRol() {
        if (creandoRol) {
            rolSeleccionado = null;
        }
        editandoRol = false;
        creandoRol = false;
    }

    public void inicializarEdicion(int id) {

        rolSeleccionado = rolService.find(id);

        sourcePermisos = new ArrayList<Permiso>();
        targetPermisos = new ArrayList<Permiso>();

        List<Permiso> listSource = permisoService.findAll();
        List<Permiso> listTarget = rolSeleccionado.getPermisos();

        for (Permiso p : listSource) {
            if (listTarget.contains(p)) {
                targetPermisos.add(p);
            } else {
                sourcePermisos.add(p);
            }
        }
        eliminarPermisos = targetPermisos;
        permisos = new DualListModel<Permiso>(sourcePermisos, targetPermisos);
        editandoRol = true;
        creandoRol = false;

    }

    public void crearRol() {
        try {

            List<Permiso> permisosSelected = permisos.getTarget();
            List<Permiso> permisosList = new ArrayList<Permiso>();
            for (Permiso p : permisosSelected) {

                Permiso x = permisoService.find(p.getId());
                x.getRoles().add(rolSeleccionado);
                permisosList.add(x);
            }

            rolSeleccionado.setPermisos(permisosList);

            rolService.create(rolSeleccionado);

//            RequestContext context = RequestContext.getCurrentInstance();
//            context.execute("PF('rolDialog').hide();");
            JsfUtil.addInformationMessage("ROL creado", "Rol " + rolSeleccionado.getRol() + " creado con éxito.");
            inicializarNuevoRol();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void editRol() {
        try {

            List<Permiso> listTarget = permisos.getTarget();

            rolSeleccionado.setPermisos(listTarget);
            securityService.editRol(rolSeleccionado, eliminarPermisos);

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('rolDialog').hide();");
            context.update("messages");
            JsfUtil.addInformationMessage("ROL actualizado", "Rol " + rolSeleccionado.getRol() + " actualizado con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void validarFormulario(ComponentSystemEvent event) {

    }

    public String cancelarModificacion() {
        return "pretty:cat-mod-datos-construccion";
    }

    public void deleteRol(Integer id) {

        try {
            Rol r = rolService.find(id);

            if (r != null) {
                rolService.remove(r);
                rolSeleccionado = new Rol();
                JsfUtil.addInformationMessage("Rol eliminado", "Rol " + r.getRol() + " eliminado con éxito.");
            }

//            RequestContext context = RequestContext.getCurrentInstance();
//            context.update("rol-list-form, messages");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void onTransfer(TransferEvent event) {

    }
}
