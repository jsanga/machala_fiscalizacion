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
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import com.icl.generics.Versioner;
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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Dairon
 */
@Named(value = "updatePredioUrbanoTerrenoView")
@ViewScoped
public class ActualizarPredioUrbanoTerrenoController implements Serializable {

    @EJB
    private ConfigReader config;
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

    private List<SelectItem> valoresTipoLote;

    private Escritura escritura;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloEliminar;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;

    private Contribuyente propietarioSeleccionado;
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

        valoresTipoLote = new ArrayList<>();

        usoSeleccionado = new UsoSuelo();
        usosSuelo = new ArrayList<>();
        usosSueloEliminar = new ArrayList<>();
        valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
        usoSeleccionado.setValorDiscreto(valorDiscreto);
    }
//</editor-fold>

    public short getAnioActual() {
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        return anioActual;
    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public List<UsoSuelo> getUsosSueloEliminar() {
        return usosSueloEliminar;
    }

    public void setUsosSueloEliminar(List<UsoSuelo> usosSueloEliminar) {
        this.usosSueloEliminar = usosSueloEliminar;
    }

    public UsoSuelo getUsoSeleccionado() {
        return usoSeleccionado;
    }

    public void setUsoSeleccionado(UsoSuelo usoSeleccionado) {
        this.usoSeleccionado = usoSeleccionado;
    }

    public ValorDiscreto getValorDiscreto() {
        return valorDiscreto;
    }

    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
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

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
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

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public List<SelectItem> getValoresTipoLote() {
        this.valoresTipoLote = valoresVariables("cat_terreno", "tipo_lote");
        return this.valoresTipoLote;
    }

    public void setValoresTipoLote(List<SelectItem> valoresTipoLote) {
        this.valoresTipoLote = valoresTipoLote;
    }

    public List<SelectItem> valoresVariables(String tabla, String columna) {

        List<SelectItem> lista = new LinkedList<>();
        List<ValorDiscreto> valores = variablesService.obtenerValores(tabla, columna, anioActual);
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
        bloqueCod = "000";
        phvCod = "000";
        phhCod = "000";

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
                usosSuelo = predio.getUsosSuelo();

                if (!usosSuelo.isEmpty()) {
                    usoSeleccionado = usosSuelo.get(0);
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
            predio.setUsosSuelo(usosSuelo);

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

            catastroService.updateDatosTerreno(predio, terreno, usosSueloEliminar);
            audit.save(predioAnt.getClaveCatastral(), "Actualización de Terreno", pant, new Versioner().getJson(predio), user);
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

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public void initNuevoUso() {
        usoSeleccionado = new UsoSuelo();
        valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
        usoSeleccionado.setValorDiscreto(getValorDiscreto());
        usoSeleccionado.setCod((short) 12);

    }

    public void initEditUso() {

    }

    public void cancelarCrearUso() {
        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        }

    }

    public void eliminarUso() {

        for (int i = 0; i < usosSuelo.size(); i++) {
            if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                usosSuelo.remove(i);
                break;
            }
        }
        if (usoSeleccionado.getId() != null) {
            if (!usosSueloEliminar.contains(usoSeleccionado)) {
                usosSueloEliminar.add(usoSeleccionado);
            }
        }
        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        }

    }

    public void crearNuevoUso() {

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        boolean existe = false;

        for (int i = 0; i < usosSuelo.size(); i++) {
            if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            usoSeleccionado.setPredio(predio);
            usosSuelo.add(usoSeleccionado);
            initNuevoUso();
        }

        if (existe) {
            JsfUtil.addInformationMessage("Uso", "Uso del suelo se encuentra registrado al terreno.");
        } else {
            JsfUtil.addInformationMessage("Uso", "Uso del suelo registrado con exito");
        }

    }

    public void onRowSelectUso(SelectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();

    }

    public void onRowUnselectUso(UnselectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();
    }

    public List<SelectItem> valoresVariablesuUsos() {

        List<SelectItem> lista = new ArrayList<>();
        List<ValorDiscreto> valores = new ArrayList<>();
        for (int i = 33; i <= 52; i++) {
            valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
//            for (int j = 0; j < valores.size(); j++) {
//                for (UsoSuelo u : usosSuelo) {
//                    if (u.getCod() == valores.get(j).getValorDiscretoPK().getValor()) {
//                        valores.remove(j);
//                    }
//                }
//            }
            for (ValorDiscreto v : valores) {
                SelectItem item = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
                lista.add(item);
            }
        }

        return lista;

    }

    public String codigosVariablesuUsos() {

        String codigos = "";

        for (int i = 33; i <= 52; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariable(i, anioActual);
        }
        return codigos;

    }

    public void changeUso(short cod) {
        valorDiscreto = variablesService.obtenerValor(33, 52, cod, anioActual);
        //     usoSeleccionado.setCod((int) cod);
    }

    public void updateEstadoTerreno() {
        if (terreno.getEstado() != 1) {
            terreno.setEstadoEdificacion((short) 0);
            terreno.setPisosTerminados((short) 0);
        } else {
            if (terreno.getEstadoEdificacion() == 0) {
                terreno.setPisosTerminados((short) 1);
            } else {
                terreno.setPisosTerminados((short) 0);
            }
        }

    }
}
