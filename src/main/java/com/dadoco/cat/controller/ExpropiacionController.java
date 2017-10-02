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
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
@Named(value = "expropiacionView")
@ViewScoped
public class ExpropiacionController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExpropiacionController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private TipoDocumentoService tipoDocumentoService;

    private DatosAutorizacion datosAutorizacion;
    private List<SelectItem> tiposDocumentos;
    private Long idTipoDocumento;
    private OpcionesBusquedaPredio opcionesBusqueda;
    private Predio predio;
    private List<Contribuyente> propietarios;
    private Contribuyente propietarioSeleccionado;
    private boolean personeria;

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

        tiposDocumentos = new ArrayList<SelectItem>();
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

        try {predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado",
                        parroquiaCod,zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
                init();
            } else {

                if (predio.getDominio() != 2) {
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
                } else {
                    JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no se puede expropiar porque es Municipal.",
                            parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
                    opcionesBusqueda.setEjecutandoAccion(false);
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }
//</editor-fold>

    public String cancelarModificacion() {
        return "pretty:cat-expropiaciones";
    }

    public String expropiarPredio() {

        try {
            catastroService.expropiarPredio(predio, idTipoDocumento, datosAutorizacion);
            JsfUtil.addInformationMessage("Predio expropiado", "Predio con clave: " + predio.getClaveCatastral() + " ha sido expropiado.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

        return "pretty:cat-expropiaciones";
    }

    public void onRowSelect(SelectEvent event) {
        propietarioSeleccionado = (Contribuyente) event.getObject();

    }

    public void onRowUnselect(UnselectEvent event) {

    }

}
