/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.controller;

import com.dadoco.admin.controller.ZonasViewController;
import com.dadoco.common.enumeracion.Opciones;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.AbstractFacade;
import com.dadoco.config.ConfigReader;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fernando
 * @param <T>
 */
public abstract class UtilController<T> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ZonasViewController.class);

    private Opciones opcion;
    private volatile AbstractFacade service;
    private Class<?> clase;
    private String username;
    private List<T> listadoEntidad;
    protected T entidadSeleccionada;

    protected String msgGuardar;
    protected String msgEditar;
    protected String msgEliminar;
    protected String msgEliminarLogico;

    @EJB
    protected ConfigReader configuracion;

    public UtilController() {

    }

    public UtilController(Class<?> clase) {
        this.clase = clase;
    }

    public void prepararGuardar() {
        this.opcion = Opciones.Guardar;
        this.entidadSeleccionada = null;
    }

    public void prepararEditar() {
        this.opcion = Opciones.Editar;
    }

    public void prepararEliminar() {
        this.opcion = Opciones.Eliminar;
    }

    public void realizarAccion() {
        this.setearMensajes();
        switch (this.opcion) {
            case Guardar:
                try {
                    guardar();
                    JsfUtil.addSuccessMessage(this.msgGuardar);
                    this.entidadSeleccionada = null;
                } catch (Exception ex) {
                    RequestContext.getCurrentInstance().addCallbackParam("notValid", true);
                    JsfUtil.addErrorMessage(ex, "No hemos podido guardar la información");
                }
                break;
            case Editar:
                try {
                    editar();
                    JsfUtil.addSuccessMessage(this.msgEditar);
                    this.entidadSeleccionada = null;
                } catch (Exception ex) {
                    RequestContext.getCurrentInstance().addCallbackParam("notValid", true);
                    JsfUtil.addErrorMessage(ex, "No hemos podido modificar la información");
                }
                break;
            case Eliminar:
                try {
                    eliminar();
                    JsfUtil.addSuccessMessage(this.msgEliminar);
                    this.entidadSeleccionada = null;
                } catch (Exception ex) {
                    RequestContext.getCurrentInstance().addCallbackParam("notValid", true);
                    JsfUtil.addErrorMessage(ex, "No hemos podido eliminar la información");
                }
                break;
            case EliminadoLogico:
                try {
                    eliminarLogico();
                    JsfUtil.addSuccessMessage(this.msgEliminarLogico);
                    this.entidadSeleccionada = null;
                } catch (Exception ex) {
                    RequestContext.getCurrentInstance().addCallbackParam("notValid", true);
                    JsfUtil.addErrorMessage(ex, "No hemos podido anular la información");
                }
                break;
        }

    }

    protected void guardar() throws Exception {
        this.service.create(this.entidadSeleccionada);
    }

    protected void editar() throws Exception {
        this.service.edit(this.entidadSeleccionada);
    }

    protected void eliminar() throws Exception {
        this.service.remove(this.entidadSeleccionada);
    }

    protected abstract void eliminarLogico();

    protected abstract void setearMensajes();

    /**
     * @return the opcion
     */
    public Opciones getOpcion() {
        return opcion;
    }

    /**
     * @param opcion the opcion to set
     */
    public void setOpcion(Opciones opcion) {
        this.opcion = opcion;
    }

    /**
     * @return the service
     */
    public AbstractFacade getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(AbstractFacade service) {
        this.service = service;
    }

    /**
     * @return the clase
     */
    public Class<?> getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(Class<?> clase) {
        this.clase = clase;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the configuracion
     */
    public ConfigReader getConfiguracion() {
        return configuracion;
    }

    /**
     * @param configuracion the configuracion to set
     */
    public void setConfiguracion(ConfigReader configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * @return the listadoEntidad
     */
    public List<T> getListadoEntidad() {
        listadoEntidad = this.service.findAll();
        return listadoEntidad;
    }

    /**
     * @param listadoEntidad the listadoEntidad to set
     */
    public void setListadoEntidad(List<T> listadoEntidad) {
        this.listadoEntidad = listadoEntidad;
    }

    /**
     * @return the entidadSeleccionada
     */
    public T getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    /**
     * @param entidadSeleccionada the entidadSeleccionada to set
     */
    public void setEntidadSeleccionada(T entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

}
