/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.TipoEscritura;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.ParroquiaPK;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.SectorPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.TipoEscrituraService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedDocument;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dairon
 */
@Named(value = "unificarPredioView")
@ViewScoped
public class UnificarPredioController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UnificarPredioController.class);

    @EJB
    private ConfigReader config;
    @EJB
    private ManzanaService manzanaService;
    @EJB
    private VariableService variablesService;
    @EJB
    private ContribuyenteService contribuyenteService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private TipoEscrituraService tipoEscrituraService;
    @EJB
    private PredioService predioService;
    @EJB
    private DeudaService deudaService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private ParroquiaService parroquiaService;
    
    @Inject
    private CrearPredioGenerico crearPredioView;

    private OpcionesBusquedaPredio opcionesBusqueda;

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

    private Manzana manzana;

    private Predio predio;
    private Terreno terreno;
    private Predio predioOnClic;

    private List<SelectItem> tiposEscrituras;
    private Long idTipoEscritura;
    private Escritura escritura;

    private List<Predio> prediosAUnificar;
    private List<Contribuyente> propietarios;
    private Contribuyente propietario;
    private Contribuyente propietarioSeleccionado;
    private List<Contribuyente> propietariosEliminar;
    private Contribuyente propietarioEliminarSeleccionado;

    private List<Contribuyente> contribuyentes;
    private Contribuyente contribuyenteSeleccionado;
    private Contribuyente nuevoContribuyente;

    private String s_tipoIdentificacion;
    private String s_numeroIdentificacion;
    private String s_nombreContribuyente;

    private boolean terrenoExistente;

    private String tipoPredio;

    private double coord_X;
    private double coord_Y;
    String claveGeoreferenciada;
    String claveOnclic;

    private String areaTotal;

    private List<Predio> prediosEnManzana;

    private List<Predio> prediosSeleccionados;

    private List<UploadedFile> documentos;
    private UploadedDocument[] datosDocumento;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloTerreno;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;
    private short anioActual;
    private Bloque bloqueSeleccionado;
    private int bloqueSeleccionadoIndex;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {
        
        //crearPredioView.setPredio(null);
        //predio = crearPredioView.getPredio();
        predioOnClic = new Predio();
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        opcionesBusqueda = new OpcionesBusquedaPredio();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        terrenoExistente = false;

        propietarios = new ArrayList<>();
        contribuyentes = new ArrayList<>();
        tiposEscrituras = new ArrayList<>();

        tipoPredio = "M";

        escritura = new Escritura();

        prediosEnManzana = new ArrayList<Predio>();
        prediosSeleccionados = new ArrayList<Predio>();
        propietarioSeleccionado = new Contribuyente();
        propietariosEliminar = new ArrayList<Contribuyente>();
        nuevoContribuyente = new Contribuyente();

        documentos = new ArrayList<UploadedFile>();
        datosDocumento = new UploadedDocument[30];

        usoSeleccionado = new UsoSuelo();
        usosSuelo = new ArrayList<>();
        usosSueloTerreno = new ArrayList<>();
        valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        prediosAUnificar = new ArrayList();
    }
    
    public void ajustarOpcionesBusqueda() {
        opcionesBusqueda.setProvinciaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getProvinciaCod()), 2));
        opcionesBusqueda.setCantonCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getCantonCod()), 2));
        opcionesBusqueda.setParroquiaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getParroquiaCod()), 2));
        opcionesBusqueda.setZonaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getZonaCod()), 2));
        opcionesBusqueda.setSectorCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSectorCod()), 2));
        opcionesBusqueda.setManzanaCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getManzanaCod()), 3));
        opcionesBusqueda.setSolarCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getSolarCod()), 3));
        opcionesBusqueda.setBloqueCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getBloqueCod()), 3));
        opcionesBusqueda.setPhhCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getPhhCod()), 3));
        opcionesBusqueda.setPhvCod(generadorCeroALaIzquierda(Long.parseLong(opcionesBusqueda.getPhvCod()), 3));
    }
    
    public String generadorCeroALaIzquierda(Long n, Integer max) {
        int cont = 0;
        Long num = n;
        String salida = "";
        while (num > 0) {
            num = num / 10;
            cont++;
        }
        for (int i = 0; i < max - cont; i++) {
            salida = salida + "0";
        }
        if(n > 0)
            salida = salida + n;
        return salida;
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setters">
    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public DeudaService getDeudaService() {
        return deudaService;
    }

    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    public List<UploadedFile> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<UploadedFile> documentos) {
        this.documentos = documentos;
    }

    public UploadedDocument[] getDatosDocumento() {
        return datosDocumento;
    }

    public void setDatosDocumento(UploadedDocument[] datosDocumento) {
        this.datosDocumento = datosDocumento;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

    public String getAreaTotal() {

        double area = 0;

//        if (!prediosSeleccionados.isEmpty()) {
//            area += predio.getTerreno().getAreaLevantamiento().doubleValue();
//            for (Predio p : prediosSeleccionados) {
//                area += p.getTerreno().getAreaLevantamiento().doubleValue();
//            }
//        }
        areaTotal = String.format("%.2f", area);

        return areaTotal;
    }

    public void setAreaTotal(String areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Predio getPredioOnClic() {
        return predioOnClic;
    }

    public void setPredioOnClic(Predio predioOnClic) {
        this.predioOnClic = predioOnClic;
    }

    public String getClaveOnclic() {
        return claveOnclic;
    }

    public void setClaveOnclic(String claveOnclic) {
        this.claveOnclic = claveOnclic;
    }

    public String getClaveGeoreferenciada() {
        return claveGeoreferenciada;
    }

    public void setClaveGeoreferenciada(String claveGeoreferenciada) {
        this.claveGeoreferenciada = claveGeoreferenciada;
    }

    public TipoEscrituraService getTipoEscrituraService() {
        return tipoEscrituraService;
    }

    public void setTipoEscrituraService(TipoEscrituraService tipoEscrituraService) {
        this.tipoEscrituraService = tipoEscrituraService;
    }

    public double getCoord_X() {
        return coord_X;
    }

    public void setCoord_X(double coord_X) {
        this.coord_X = coord_X;
    }

    public double getCoord_Y() {
        return coord_Y;
    }

    public void setCoord_Y(double coord_Y) {
        this.coord_Y = coord_Y;
    }

    public List<SelectItem> getTiposEscrituras() {
        List<TipoEscritura> escr = tipoEscrituraService.findAll();
        for (TipoEscritura te : escr) {
            tiposEscrituras.add(new SelectItem(te.getId(), te.getTipo()));
        }
        return tiposEscrituras;
    }

    public void setTiposEscrituras(List<SelectItem> tiposEscrituras) {
        this.tiposEscrituras = tiposEscrituras;
    }

    public Long getIdTipoEscritura() {
        return idTipoEscritura;
    }

    public void setIdTipoEscritura(Long idTipoEscritura) {
        this.idTipoEscritura = idTipoEscritura;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public List<Contribuyente> getPropietariosEliminar() {
        return propietariosEliminar;
    }

    public void setPropietariosEliminar(List<Contribuyente> propietariosEliminar) {
        this.propietariosEliminar = propietariosEliminar;
    }

    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public Contribuyente getNuevoContribuyente() {
        return nuevoContribuyente;
    }

    public void setNuevoContribuyente(Contribuyente nuevoContribuyente) {
        this.nuevoContribuyente = nuevoContribuyente;
    }

    public Contribuyente getPropietarioEliminarSeleccionado() {
        return propietarioEliminarSeleccionado;
    }

    public void setPropietarioEliminarSeleccionado(Contribuyente propietarioEliminarSeleccionado) {
        this.propietarioEliminarSeleccionado = propietarioEliminarSeleccionado;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public Contribuyente getContribuyenteSeleccionado() {
        return contribuyenteSeleccionado;
    }

    public void setContribuyenteSeleccionado(Contribuyente contribuyenteSeleccionado) {
        this.contribuyenteSeleccionado = contribuyenteSeleccionado;
    }

    public List<Contribuyente> getContribuyentes() {
        return contribuyentes;
    }

    public void setContribuyentes(List<Contribuyente> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }

    public String getS_tipoIdentificacion() {
        return s_tipoIdentificacion;
    }

    public void setS_tipoIdentificacion(String s_tipoIdentificacion) {
        this.s_tipoIdentificacion = s_tipoIdentificacion;
    }

    public String getS_numeroIdentificacion() {
        return s_numeroIdentificacion;
    }

    public void setS_numeroIdentificacion(String s_numeroIdentificacion) {
        this.s_numeroIdentificacion = s_numeroIdentificacion;
    }

    public String getS_nombreContribuyente() {
        return s_nombreContribuyente;
    }

    public void setS_nombreContribuyente(String s_nombreContribuyente) {
        this.s_nombreContribuyente = s_nombreContribuyente;
    }

    public List<Predio> getPrediosSeleccionados() {
        return prediosSeleccionados;
    }

    public void setPrediosSeleccionados(List<Predio> prediosSeleccionados) {
        this.prediosSeleccionados = prediosSeleccionados;
    }

    public List<Predio> getPrediosEnManzana() {
        return prediosEnManzana;
    }

    public void setPrediosEnManzana(List<Predio> prediosEnManzana) {
        this.prediosEnManzana = prediosEnManzana;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public VariableService getVariablesService() {
        return variablesService;
    }

    public void setVariablesService(VariableService variablesService) {
        this.variablesService = variablesService;
    }

    public ContribuyenteService getContribuyenteService() {
        return contribuyenteService;
    }

    public void setContribuyenteService(ContribuyenteService contribuyenteService) {
        this.contribuyenteService = contribuyenteService;
    }

    public CatastroService getCatastroService() {
        return catastroService;
    }

    public void setCatastroService(CatastroService catastroService) {
        this.catastroService = catastroService;
    }

    public ZonaService getZonaService() {
        return zonaService;
    }

    public void setZonaService(ZonaService zonaService) {
        this.zonaService = zonaService;
    }

    public SectorService getSectorService() {
        return sectorService;
    }

    public void setSectorService(SectorService sectorService) {
        this.sectorService = sectorService;
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

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Contribuyente getPropietario() {
        return propietario;
    }

    public void setPropietario(Contribuyente propietario) {
        this.propietario = propietario;
    }

    public boolean isTerrenoExistente() {
        return terrenoExistente;
    }

    public void setTerrenoExistente(boolean terrenoExistente) {
        this.terrenoExistente = terrenoExistente;
    }

    public String getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(String tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

//</editor-fold>
    public void buscarPredio() {
        
        ajustarOpcionesBusqueda();
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
                JsfUti.messageInfo(null, "Info", "Predio no encontrado.");
            } else {
                
                if(!prediosAUnificar.contains(predio)){
                    prediosAUnificar.add(predio);
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public void initNuevoUso() {
        usoSeleccionado = new UsoSuelo();
        valorDiscreto = variablesService.obtenerValor(33, 52, (short) 12, anioActual);
        usoSeleccionado.setValorDiscreto(getValorDiscreto());
        usoSeleccionado.setCod((short) 12);

    }

    public void crearNuevoUso(boolean bloque) {

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        boolean existe = false;

        if (bloque) {
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                // usoSeleccionado.setBloque(bloqueSeleccionado);
                usosSuelo.add(usoSeleccionado);
                bloqueSeleccionado.setUsosSuelo(usosSuelo);
                initNuevoUso();
            }

            if (existe) {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo se encuentra registrado al bloque.");
            } else {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito");
            }
        } else {
            for (int i = 0; i < usosSueloTerreno.size(); i++) {
                if (Objects.equals(usosSueloTerreno.get(i).getCod(), usoSeleccionado.getCod())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                usoSeleccionado.setPredio(predio);
                usosSueloTerreno.add(usoSeleccionado);
                initNuevoUso();
            }

            if (existe) {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo se ya encuentra registrado al terreno.");
            } else {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito.");
            }
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

    public void changeUso(short cod) {
        valorDiscreto = variablesService.obtenerValor(33, 52, cod, anioActual);
        //     usoSeleccionado.setCod((int) cod);
    }

    public String codigosVariablesuUsos() {

        String codigos = "";

        for (int i = 33; i <= 52; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariable(i, anioActual);
        }
        return codigos;

    }

    public void cancelarCrearUso() {
        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        }

    }

    public void eliminarUso(boolean bloque) {

        if (bloque) {
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    usosSuelo.remove(i);
                    break;
                }
            }
            if (!usosSuelo.isEmpty()) {
                usoSeleccionado = usosSuelo.get(0);
            }
        } else {
            for (int i = 0; i < usosSueloTerreno.size(); i++) {
                if (Objects.equals(usosSueloTerreno.get(i).getCod(), usoSeleccionado.getCod())) {
                    usosSueloTerreno.remove(i);
                    break;
                }
            }
            if (!usosSueloTerreno.isEmpty()) {
                usoSeleccionado = usosSueloTerreno.get(0);
            }
        }

    }

    public String unificarPredios() {

        try {
            Subject user = SecurityUtils.getSubject();
            Usuario usuario = getUsuarioService().serchUser(user.getPrincipal().toString());
            String claves = "( ";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest htservlet = (HttpServletRequest) fc.getExternalContext().getRequest();
            InetAddress add = InetAddress.getByName(htservlet.getRemoteAddr());
            String sNew = predio.getClaveCatastral();

            for (Predio p : prediosSeleccionados) {
                claves += p.getClaveCatastral() + ", ";                
            }

            if (catastroService.unificarPredios(prediosAUnificar, predio)) {
                JsfUtil.addInformationMessage("Predio unificados", claves);
                JsfUtil.addInformationMessage("Predio Creado", sNew);
            }else{
                JsfUtil.addErrorMessage("Problema al generar la transacción. Inténtelo nuevamente");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        return "pretty:cat-unificar-predio";
    }
    
    public void buscarTerreno() {

        try {
            ajustarOpcionesBusqueda();
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

            ParroquiaPK parroquiaPK = new ParroquiaPK();
            parroquiaPK.setCodProvincia(provinciaCod);
            parroquiaPK.setCodCanton(cantonCod);
            parroquiaPK.setCodParroquia(parroquiaCod);

            Parroquia parroquia = parroquiaService.find(parroquiaPK);

            if (parroquia != null) {
                ZonaPK zonaPK = new ZonaPK();
                zonaPK.setCodProvincia(provinciaCod);
                zonaPK.setCodCanton(cantonCod);
                zonaPK.setCodParroquia(parroquia.getParroquiaPK().getCodParroquia());
                zonaPK.setCodZona(zonaCod);

                Zona zona = zonaService.find(zonaPK);

                if (zona != null) {
                    SectorPK sectorPK = new SectorPK();
                    sectorPK.setCodProvincia(provinciaCod);
                    sectorPK.setCodCanton(cantonCod);
                    sectorPK.setCodParroquia(parroquiaCod);
                    sectorPK.setCodZona(zona.getZonaPK().getCodZona());
                    sectorPK.setCodSector(sectorCod);

                    Sector sector = sectorService.find(sectorPK);

                    if (sector != null) {
                        ManzanaPK manzanaPK = new ManzanaPK();
                        manzanaPK.setCodProvincia(provinciaCod);
                        manzanaPK.setCodCanton(cantonCod);
                        manzanaPK.setCodParroquia(parroquiaCod);
                        manzanaPK.setCodZona(zonaCod);
                        manzanaPK.setCodSector(sectorCod);
                        manzanaPK.setCodManzana(manzanaCod);

                        manzana = manzanaService.find(manzanaPK);

                        if (manzana != null) {

                            if (solarCod.equals("0")) {

                                JsfUtil.addErrorMessage("El codigo del solar no puede ser 0");
                                opcionesBusqueda.setEjecutandoAccion(false);
                                return;
                            }

                            TerrenoPK terrenoPK = new TerrenoPK(manzanaPK, solarCod);
                            terreno = catastroService.obtenerTerreno(terrenoPK);
                            Predio fichaResumen;
                            if (terreno != null) {
                                fichaResumen = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, "000", "000", "000");
                                if (fichaResumen != null) {
                                    if (Integer.parseInt(bloqueCod) + Integer.parseInt(phhCod) + Integer.parseInt(phvCod) == 0) {
                                        JsfUtil.addInformationMessage(null, "Predio " + fichaResumen.getClaveCatastral() + " ya se encuentra registrado.");
                                        return;
                                    } else if (fichaResumen.getTipoPropiedad() != 2) {
                                        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-0-0-0";
                                        JsfUtil.addInformationMessage(null, "Predio con clave: " + clave + " no esta registrado como propiedad horizontal.");
                                        opcionesBusqueda.setEjecutandoAccion(false);

                                    } else if (Integer.parseInt(phvCod) == 0 || Integer.parseInt(phhCod) == 0 || Integer.parseInt(bloqueCod) == 0) {
                                        String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                        JsfUtil.addInformationMessage(null, "Predio " + clave + " ninguno de los codigos BLOQUE, PHV y PHH no pueden tener valor valor 0.");
                                    } else {
                                        predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
                                        if (predio != null) {
                                            JsfUtil.addInformationMessage(null, "Predio " + predio.getClaveCatastral() + " ya se encuentra registrado.");

                                        }
                                    }
                                }
                            } else if (Integer.parseInt(bloqueCod) != 0 || Integer.parseInt(phhCod) != 0 || Integer.parseInt(phvCod) != 0) {
                                JsfUtil.addErrorMessage("Debe registrar la ficha madre con clave: " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-0-0-0 para poder registrar las PH.");
                                opcionesBusqueda.setEjecutandoAccion(false);

                            } else {

                                predio = new Predio();
                                predio.setCodBloque("000");
                                predio.setCodPhh("000");
                                predio.setCodPhv("000");
                                String clave = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod;
                                predio.setClaveCatastral(clave);
                                predio.setBloques(new ArrayList<Bloque>());
                                terreno = new Terreno();
                                terreno.setTerrenoPK(terrenoPK);
                                terreno.setManzana(manzana);
                                predio.setTerreno(terreno);
                                //terreno.setPredios(new ArrayList<Predio>());
                                opcionesBusqueda.setEjecutandoAccion(true);
                                crearPredioView.setPredio(predio);
                            }

                        } else {
                            JsfUtil.addErrorMessage("Manzana con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + " no se encuentra registrada en el sistema.");
                            opcionesBusqueda.setEjecutandoAccion(false);
                        }
                    } else {
                        JsfUtil.addErrorMessage("Sector con clave " + parroquiaCod + "-" + zonaCod + "-" + sectorCod + " no se encuentra registrado en el sistema.");
                        opcionesBusqueda.setEjecutandoAccion(false);
                    }

                } else {
                    JsfUtil.addErrorMessage("Zona con clave " + parroquiaCod + "-" + zonaCod + " no se encuentra registrada en el sistema.");
                    opcionesBusqueda.setEjecutandoAccion(false);
                }

            } else {
                JsfUtil.addErrorMessage("Parroquia con clave " + parroquiaCod + " no se encuentra registrada en el sistema.");
                opcionesBusqueda.setEjecutandoAccion(false);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    private boolean comprobarDeudas(Predio p) {

        Object[] params = new Object[2];
        params[0] = p.getClaveCatastral();
        params[1] = "NO PAGADO";
        List<Deuda> deudas = deudaService.findByNamedQuery("Deuda.findByClaveCatastralCheck", params);

        return !deudas.isEmpty();
    }

    public String cancelarUnificarPredio() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public String onFlowProcess(FlowEvent event) {
        opcionesBusqueda = new OpcionesBusquedaPredio();
        if(event.getNewStep().equals("step-confirmacion-fracionar")){
            JsfUti.update("step-confirmacion-fracionar");
        }
        if(event.getNewStep().equals("step-unificar") && prediosAUnificar.isEmpty()){
            JsfUti.messageInfo(null, "Info", "Debe ingresar predios antes de continuar");
            return "";
        }
        return event.getNewStep();
    }

    public void nuevoPredio() {
        opcionesBusqueda.setBuscando(true);
    }

    public void onRowSelect(SelectEvent event) {

        //addContribuyenteListadoPropietarios();
        /*
         if (comprobarDeudas(select)) {
         JsfUtil.addInformationMessage("Información", "Predio con clave catastral: " + select.getClaveCatastral() + ", tiene deuda pendientes con el municipio");
         prediosSeleccionados = new ArrayList<Predio>();
         }
         */
    }

    public void onRowUnselect(UnselectEvent event) {

        Predio p = (Predio) event.getObject();

        // deleteContribuyenteListadoPropietarios(p);
        // JsfUtil.addInformationMessage("Predio Unselec", "Predio creado:" + ((Predio) event.getObject()).getId());
    }

    public void onRowSelectDelete(SelectEvent event) {

    }

    public void onRowUnselectDelete(UnselectEvent event) {

    }

    public void inicializarBusquedaContribuyente() {
        contribuyenteSeleccionado = new Contribuyente();
        contribuyentes = new ArrayList<Contribuyente>();
        s_tipoIdentificacion = "";
        s_nombreContribuyente = "";
        s_numeroIdentificacion = "";

    }

    public void inicializarContribuyente() {
        nuevoContribuyente = new Contribuyente();

    }

    public void adicionarPropietario() {
        if (contribuyenteSeleccionado != null) {
            boolean found = false;
            for (Contribuyente cont : propietarios) {
                if (contribuyenteSeleccionado.equals(cont)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                propietarios.add(contribuyenteSeleccionado);
            }
        }
    }

    public void buscarContribuyente() {

        try {
            contribuyentes = new ArrayList<Contribuyente>();

            Object[] params = new Object[3];
            params[0] = s_tipoIdentificacion;
            params[1] = s_numeroIdentificacion;
            params[2] = (short) 1;

            contribuyentes = contribuyenteService.findByNamedQuery("Contribuyente.findByTipoIdentificacion", params);

            if (contribuyentes == null || contribuyentes.isEmpty()) {
                JsfUtil.addWarningMessage(null, "Contribuyente no encontrado");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    private void addContribuyenteListadoPropietarios() {

        List<Contribuyente> props;
        propietarios = new ArrayList<Contribuyente>();

        for (Contribuyente p : predio.getPropietarios()) {
            propietarios.add(p);
        }

        for (Predio p : prediosSeleccionados) {

            props = p.getPropietarios();

            for (Contribuyente c : props) {
                if (!propietarios.contains(c)) {
                    propietarios.add(c);
                }
            }
        }
    }

    public void eliminarPropietario() {

        for (Contribuyente c : propietariosEliminar) {
            if (propietarios.contains(c)) {
                propietarios.remove(c);
            }
        }
    }

    public void RegistrarNuevoContribuyente() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("alert('alert inside bean');");
        try {
            contribuyenteSeleccionado = contribuyenteService.create(nuevoContribuyente);
            log.error("Datos: " + contribuyenteSeleccionado.getId());
            JsfUtil.addInformationMessage("Contribuyente creado", "con exito.");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
        }
    }

    public int cantidadTotalBloques() {
        int cantidad = 0;
        if (predio.getBloques() != null && !predio.getBloques().isEmpty()) {
            cantidad += predio.getBloques().size();
        }
        for (Predio p : prediosSeleccionados) {
            if (p.getBloques() != null && !p.getBloques().isEmpty()) {
                cantidad += p.getBloques().size();
            }
        }
        return cantidad;
    }

    public String areatotalBloques(boolean a) {
        float area = 0;
        for (Bloque b : predio.getBloques()) {
            if (b.getAreaTotal() >= 0) {
                area += b.getAreaTotal();
            }
        }
        for (Predio p : prediosSeleccionados) {
            for (Bloque b : p.getBloques()) {
                if (b.getAreaTotal() >= 0) {
                    area += b.getAreaTotal();
                }
            }
        }

//        if (a) {
//            area += predio.SumaAreaTotalTodosBloque();
//        }
        return String.format("%.2f", area);
    }

    public String areatotalTerreno(boolean a) {
        float area = 0;

        for (Predio p : prediosSeleccionados) {
            if (p.getTerreno() != null && p.getTerreno().getAreaLevantamiento() >= 0) {
                area += p.getTerreno().getAreaLevantamiento();
            }
        }
        if (a) {
            if (predio.getTerreno() != null && predio.getTerreno().getAreaLevantamiento() >= 0) {
                area += predio.getTerreno().getAreaLevantamiento();
            }
        }
        return String.format("%.2f", area);
    }

    public void doSomething() {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        claveOnclic = ec.getRequestParameterMap().get("form:input");
        claveOnclic += "-0";

        List<Predio> lista = predioService.findByNamedQuery("Predio.findByClaveCatastral", claveOnclic);

        if (!lista.isEmpty()) {
            predioOnClic = lista.get(0);
        } else {
            predioOnClic = new Predio();
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('UsersWidget').show();");
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        documentos.add(uploadedFile);
        UploadedDocument ui = new UploadedDocument(documentos.size(), FilenameUtils.getName(uploadedFile.getFileName()), "");

        datosDocumento[documentos.size() - 1] = ui;
    }

    public void eliminarDocumento(int index) {
        if (index >= 0 && index < documentos.size()) {
            for (int i = index; i < documentos.size() - 1; i++) {
                datosDocumento[i] = datosDocumento[i + 1];
            }
            datosDocumento[documentos.size() - 1] = null;
            documentos.remove(index);
        }
    }

    private List<UploadedDocument> guardarDocumentos() {

        List<UploadedDocument> copiados = new ArrayList<UploadedDocument>();
        for (int i = 0; i < documentos.size(); i++) {
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);
            int millis = now.get(Calendar.MILLISECOND);
            UploadedFile uploadedFile = documentos.get(i);
            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_documentos");
                String nuevoNombre = String.format("%04d-%02d-%02d-%02d-%02d-%02d-%03d", year, month, day, hour, minute, second, millis);
                nuevoNombre += "-" + FilenameUtils.getName(uploadedFile.getFileName());

                File destFile = new File(new File(uploadDir), nuevoNombre);

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedDocument doc = datosDocumento[i];
                doc.setSavedFile(destFile);

                copiados.add(doc);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return copiados;
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

    public short getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public List<UsoSuelo> getUsosSueloTerreno() {
        return usosSueloTerreno;
    }

    public void setUsosSueloTerreno(List<UsoSuelo> usosSueloTerreno) {
        this.usosSueloTerreno = usosSueloTerreno;
    }

    public Bloque getBloqueSeleccionado() {
        return bloqueSeleccionado;
    }

    public void setBloqueSeleccionado(Bloque bloqueSeleccionado) {
        this.bloqueSeleccionado = bloqueSeleccionado;
    }

    public int getBloqueSeleccionadoIndex() {
        return bloqueSeleccionadoIndex;
    }

    public void setBloqueSeleccionadoIndex(int bloqueSeleccionadoIndex) {
        this.bloqueSeleccionadoIndex = bloqueSeleccionadoIndex;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public List<Predio> getPrediosAUnificar() {
        return prediosAUnificar;
    }

    public void setPrediosAUnificar(List<Predio> prediosAUnificar) {
        this.prediosAUnificar = prediosAUnificar;
    }

    public CrearPredioGenerico getCrearPredioView() {
        return crearPredioView;
    }

    public void setCrearPredioView(CrearPredioGenerico crearPredioView) {
        this.crearPredioView = crearPredioView;
    }

}
