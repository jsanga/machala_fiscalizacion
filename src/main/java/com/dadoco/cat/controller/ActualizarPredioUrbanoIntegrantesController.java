/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.IntegranteService;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.Integrante;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.icl.generics.Versioner;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Dairon
 */
@Named(value = "updatePredioUrbanoIntegrantesView")
@ViewScoped
public class ActualizarPredioUrbanoIntegrantesController implements Serializable {

    @EJB
    private IntegranteService integranteService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private CatastroService catastroService;
    @EJB
    PredioService predioService;
    @EJB
    private VariableService variablesService;

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

    private Terreno terreno;

    private Predio predio, predioAnt;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private List<Integrante> relevamientos;
    private Integrante relevamientoSeleccionado;
    private List<Integrante> validadores;
    private Integrante validadorSeleccionado;
    private List<Integrante> supervisores;
    private Integrante supervisorSeleccionado;
    private List<Integrante> supervisoresDigitadores;
    private Integrante supervisorDigtadorSeleccionado;
    private List<Integrante> cartografos;
    private Integrante cartografoSeleccionado;
    private List<Integrante> digitadores;
    private Integrante digitadorSeleccionado;
    private short anioActual;
    private String pant;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        Subject user = SecurityUtils.getSubject();
        //Fsan
        Usuario usuario = getUsuarioService().serchUser(user.getPrincipal().toString());
        //
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        predio = new Predio();

        terreno = new Terreno();
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public short getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public List<Integrante> getDigitadores() {
        digitadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 3);
        return digitadores;
    }

    public void setDigitadores(List<Integrante> digitadores) {
        this.digitadores = digitadores;
    }

    public Integrante getDigitadorSeleccionado() {
        return digitadorSeleccionado;
    }

    public void setDigitadorSeleccionado(Integrante digitadorSeleccionado) {
        this.digitadorSeleccionado = digitadorSeleccionado;
    }

    public List<Integrante> getRelevamientos() {
        relevamientos = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 0);
        return relevamientos;
    }

    public void setRelevamientos(List<Integrante> relevamientos) {
        this.relevamientos = relevamientos;
    }

    public Integrante getRelevamientoSeleccionado() {
        return relevamientoSeleccionado;
    }

    public void setRelevamientoSeleccionado(Integrante relevamientoSeleccionado) {
        this.relevamientoSeleccionado = relevamientoSeleccionado;
    }

    public List<Integrante> getValidadores() {
        validadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 2);
        return validadores;
    }

    public void setValidadores(List<Integrante> validadores) {
        this.validadores = validadores;
    }

    public Integrante getValidadorSeleccionado() {
        return validadorSeleccionado;
    }

    public void setValidadorSeleccionado(Integrante validadorSeleccionado) {
        this.validadorSeleccionado = validadorSeleccionado;
    }

    public List<Integrante> getSupervisores() {
        supervisores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 1);
        return supervisores;
    }

    public void setSupervisores(List<Integrante> supervisores) {
        this.supervisores = supervisores;
    }

    public Integrante getSupervisorSeleccionado() {
        return supervisorSeleccionado;
    }

    public void setSupervisorSeleccionado(Integrante supervisorSeleccionado) {
        this.supervisorSeleccionado = supervisorSeleccionado;
    }

    public List<Integrante> getSupervisoresDigitadores() {
        supervisoresDigitadores = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 4);
        return supervisoresDigitadores;
    }

    public void setSupervisoresDigitadores(List<Integrante> supervisoresDigitadores) {
        this.supervisoresDigitadores = supervisoresDigitadores;
    }

    public Integrante getSupervisorDigtadorSeleccionado() {
        return supervisorDigtadorSeleccionado;
    }

    public void setSupervisorDigtadorSeleccionado(Integrante supervisorDigtadorSeleccionado) {
        this.supervisorDigtadorSeleccionado = supervisorDigtadorSeleccionado;
    }

    public List<Integrante> getCartografos() {
        cartografos = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 5);
        return cartografos;
    }

    public void setCartografos(List<Integrante> cartografos) {
        this.cartografos = cartografos;
    }

    public Integrante getCartografoSeleccionado() {
        return cartografoSeleccionado;
    }

    public void setCartografoSeleccionado(Integrante cartografoSeleccionado) {
        this.cartografoSeleccionado = cartografoSeleccionado;
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
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

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
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
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %s-%s-%s-%s-%s-%s-%s-%s no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {
                predioAnt = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
                pant = new Versioner().getJson(predioAnt);
                opcionesBusqueda.setEjecutandoAccion(true);
                terreno = predio.getTerreno();
                digitadorSeleccionado = predio.getDigitador();
                relevamientoSeleccionado = predio.getRelevamiento();
                supervisorDigtadorSeleccionado = predio.getSupervisorDigitacion();
                supervisorSeleccionado = predio.getSupervisor();
                validadorSeleccionado = predio.getValidador();
                cartografoSeleccionado = predio.getCartografo();
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
            predio.setRelevamiento(relevamientoSeleccionado);
            predio.setSupervisor(supervisorSeleccionado);
            predio.setSupervisorDigitacion(supervisorDigtadorSeleccionado);
            predio.setCartografo(cartografoSeleccionado);
            predio.setValidador(validadorSeleccionado);
            predio.setDigitador(digitadorSeleccionado);

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

            predioService.edit(predio);
            
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

}
