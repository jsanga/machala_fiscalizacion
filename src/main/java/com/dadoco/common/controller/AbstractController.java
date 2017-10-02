/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.controller;

import com.dadoco.cat.controller.OpcionesBusquedaPredio;
import com.dadoco.common.customFilters.AbstractFacade;
import com.dadoco.common.customFilters.LazyDataModelAdvance;
import com.dadoco.common.jsf.JsfUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author fernando
 * @param <T>
 */
public abstract class AbstractController<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private OpcionesBusquedaPredio opcionBusqueda;
    private LazyDataModelAdvance<T> listado;
    private T entidadSeleccionada;
    private AbstractFacade<T> service;
    private Class<?> clase;

    protected String mensajeGuardar;
    protected String mensajeEditar;
    protected String mensajeAnular;

    private String usernameUser;
    /**
     * Atributo que se añadirá a el estado_predio
     */
    private String observacion;

    /**
     * Atributo que permitira habilitar y desabilitar botones en la vista
     */
    private boolean estadoOpciones;

    /**
     * 0 => Guardar y 1=> Editar
     */
    private int opcion = 0;

    /**
     *
     * @param clase
     */
    public AbstractController(Class<?> clase) {
        this.clase = clase;
        this.opcionBusqueda = new OpcionesBusquedaPredio();
        this.entidadSeleccionada = null;
        this.service = null;
        this.listado = new LazyDataModelAdvance<>();
        Subject subject = SecurityUtils.getSubject();
        this.usernameUser = subject.getPrincipal().toString();
    }

    public AbstractController() {
        this.listado = new LazyDataModelAdvance<>();
    }

    public abstract void buscar();

    public String cancelarBusqueda() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public void prepararCrear() {
        try {
            this.entidadSeleccionada = (T) this.clase.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage("No se ha podido instanciar : " + this.clase.getSimpleName());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage("Acceso Ilegal al Objecto : " + this.clase.getSimpleName());
        }
        this.opcion = 0;
    }

    public void prepararEditar() {
        this.opcion = 1;
    }

    public void prepararEliminar() {
        this.opcion = 2;
    }

    public void cancelar() {
        this.prepararCrear();
    }

    public void guardar() {
        switch (this.opcion) {
            case 0:
                try {
                    this.service.guardar(entidadSeleccionada);
                    JsfUtil.addInformationMessage("Guardar", mensajeGuardar);
                } catch (EJBException ex) {
                    JsfUtil.addErrorMessage("Error al tratar de Guardar: " + this.clase.getSimpleName() + ",ERROR: " + ex.getMessage());
                }
                break;
            case 1:
                try {
                    this.service.modificar(entidadSeleccionada);
                    JsfUtil.addInformationMessage("Guardar", mensajeEditar);
                } catch (EJBException ex) {
                    JsfUtil.addErrorMessage("Error al tratar de Modificar: " + this.clase.getSimpleName() + ",ERROR: " + ex.getMessage());
                }
                break;
            case 2:
                try {
                    this.service.eliminar(entidadSeleccionada);
                    JsfUtil.addInformationMessage("Eliminar", mensajeAnular);
                } catch (EJBException ex) {
                    JsfUtil.addErrorMessage("Error al tratar de Eliminar: " + this.clase.getSimpleName() + ",ERROR: " + ex.getMessage());
                }
                break;
            default:
                try {
                    this.service.guardar(entidadSeleccionada);
                    JsfUtil.addInformationMessage("Guardar", mensajeGuardar);
                } catch (EJBException ex) {
                    JsfUtil.addErrorMessage("Error al tratar de Guardar: " + this.clase.getSimpleName() + ",ERROR: " + ex.getMessage());
                }
                break;
        }
    }

//<editor-fold defaultstate="collapsed" desc="Encapsulamiento">
    public Class<?> getClase() {
        return clase;
    }

    public void setClase(Class<?> clase) {
        this.clase = clase;
    }

    public OpcionesBusquedaPredio getOpcionBusqueda() {
        return opcionBusqueda;
    }

    public void setOpcionBusqueda(OpcionesBusquedaPredio opcionBusqueda) {
        this.opcionBusqueda = opcionBusqueda;
    }

    public LazyDataModelAdvance<T> getListado() {
        listado.setFacade(this.service);
        return listado;
    }

    public T getEntidadSeleccionada() {
        return entidadSeleccionada;
    }

    public void setEntidadSeleccionada(T entidadSeleccionada) {
        this.entidadSeleccionada = entidadSeleccionada;
    }

    public void setService(AbstractFacade<T> service) {
        this.service = service;
    }
//</editor-fold>

    /**
     * @return the opcion
     */
    public int getOpcion() {
        return opcion;
    }

    /**
     * @param opcion the opcion to set
     */
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    /**
     * @return the estadoOpciones
     */
    public boolean isEstadoOpciones() {
        return estadoOpciones;
    }

    /**
     * @param estadoOpciones the estadoOpciones to set
     */
    public void setEstadoOpciones(boolean estadoOpciones) {
        this.estadoOpciones = estadoOpciones;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the usernameUser
     */
    public String getUsernameUser() {
        return usernameUser;
    }
}
