/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.RazonExencion;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TipoExencion;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.RazonExencionService;
import com.dadoco.cat.service.TipoExencionService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.model.Archivo;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "hdominioView")
@ViewScoped
public class HistoriaDominioController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConsultasController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;

    @EJB
    private PredioService predioService;

    @EJB
    private TipoExencionService tipoExencionService;
    @EJB
    private RazonExencionService razonExencionService;
    @EJB
    private AvaluosService avaluosService;

    @EJB
    private ContribuyenteService contribuyenteService;

    private Exencion exencion;
    private List<SelectItem> tiposExencion;
    private Integer idTipoExencion;
    private List<SelectItem> razonesExencion;
    private Integer idRazonExencion;

    private RazonExencion razonExencionSeleccionada;

    private List<Bloque> bloquesEliminar;
    private List<Piso> pisosEliminar;

    private OpcionesBusquedaPredio opcionesBusqueda;
    private Predio predioSeleccionado;
    private List<Predio> predios;
    private List<Predio> prediosFiltrados;
    private Terreno terreno;
    private List<Bloque> bloques;
    private Bloque bloqueSeleccionado;
    private int bloqueSeleccionadoIndex;
    private boolean creandoBloque;
    private boolean editandoBloque;

    private List<Piso> pisos;
    private Piso pisoSeleccionado;
    private int pisoSeleccionadoIndex;

    private boolean creandoPiso;
    private boolean editandoPiso;

    private boolean editandoDatos;

    private boolean visualizarListado;
    private boolean visualizarDatos;

    private String avaluoTerreno;
    private String avaluoConstruccion;
    private String avaluoComplementarias;
    private String avaluoPredial;

    private String areaTerreno;
    private String areaConstruccion;
    private String valorTerreno;

    private List<String> images;

    private String datoBusqueda;
    private String opcion;
    private String textoOpcion;

    private List<Contribuyente> listaContribuyente;

    @PostConstruct
    public void init() {

        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        predioSeleccionado = new Predio();
        predios = new ArrayList<Predio>();
        bloqueSeleccionado = new Bloque();

        bloqueSeleccionadoIndex = 0;
        pisoSeleccionadoIndex = 0;
        visualizarDatos = false;
        visualizarListado = true;
        exencion = new Exencion();
        images = new ArrayList<String>();
        listaContribuyente = new ArrayList<Contribuyente>();
        opcion = "CC";
        textoOpcion = "Clave catastral";

    }

    public void buscarOpcion() {

//        predios.clear();
//        prediosFiltrados.clear();
//        if (opcion.equalsIgnoreCase("P")) {
//            buscarPorContribuyente();
//        }
        if (opcion.equalsIgnoreCase("EU")) {
            buscarPorEdificioUbicacion();
        }
        if (opcion.equalsIgnoreCase("CI")) {
            buscarPorClaveInicial();
        }

    }

    public void onRadioButtonSelect() {

        if (opcion.equalsIgnoreCase("CC")) {
            textoOpcion = "Clave catastral";
        }
        if (opcion.equalsIgnoreCase("CI")) {
            textoOpcion = "Sector/Manzana/Solar";
        }
        if (opcion.equalsIgnoreCase("P")) {
            textoOpcion = "Propietario/Céduda/RUC/Pasaporte/Identificación";
        }
        if (opcion.equalsIgnoreCase("EU")) {
            textoOpcion = "Edificio/Urbanización/Ciudadela/Lotización";
        }

        log.error("Opcion: " + opcion);
    }

    public void buscarPorClaveInicial() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        datoBusqueda = ec.getRequestParameterMap().get("buscar-contribuyente-input");

        predios.clear();
        predios = predioService.buscarPrediosLikeCodigoInicial(datoBusqueda);
        prediosFiltrados = predios;

//        RequestContext context = RequestContext.getCurrentInstance();
//        context.update("datosConsultas,predio-list-form,datos-tab");
    }

    public void buscarPorEdificioUbicacion() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        datoBusqueda = ec.getRequestParameterMap().get("buscar-contribuyente-input");

        predios.clear();
        predios = predioService.buscarPrediosLikeEdificio(datoBusqueda);
        prediosFiltrados = predios;

//        RequestContext context = RequestContext.getCurrentInstance();
//        context.update("datosConsultas,predio-list-form,datos-tab");
    }

    public void buscarPredio() {

        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
        //short phCod = opcionesBusqueda.getPhCod();

        String zona = zonaCod;
        String sector = sectorCod;
        String manzana = manzanaCod;
        String solar = solarCod;
        //Short ph = phCod == 0 ? null : phCod;

        try {

            //predios = predioService.findByFilter(zona, sector, manzana, solar, ph);
            prediosFiltrados = predios;
            opcionesBusqueda.setEjecutandoAccion(true);
            visualizarDatos = true;
            visualizarDatos = false;

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

  
}
