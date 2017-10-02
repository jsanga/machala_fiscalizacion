/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.audit.service.AuditService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.service.BloqueService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
import com.icl.generics.Versioner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.commons.io.IOUtils;
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
 * @author dfcalderio
 */
@Named(value = "updateConstruccionView")
@ViewScoped
public class ActualizarPredioUrbanoConstruccionController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ActualizarPredioUrbanoConstruccionController.class);

    @EJB
    private VariableService variablesService;

    @EJB
    private CatastroService catastroService;

    @EJB
    private BloqueService bloqueService;
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
    private String phhCod;
    private String phvCod;

    private Predio predio, predioAnt;
    private String pant;
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

    private OpcionesBusquedaPredio opcionesBusqueda;

    private int secuenciaBloque;
    private int secuenciaPiso;

    private List<Bloque> bloquesEliminar;
    private List<Piso> pisosEliminar;
    private List<UploadFile> fotos;
    private List<ArchivoAux> fotosPreview;

    private List<UsoSuelo> usosSuelo;
    private List<UsoSuelo> usosSueloEliminar;
    private UsoSuelo usoSeleccionado;
    private ValorDiscreto valorDiscreto;
    private List<ValorDiscreto> valorDiscretos;

    private List<OtrosTipoConstruccion> obrasComplementarias;
    private List<OtrosTipoConstruccion> obrasComplementariasEliminar;
    private OtrosTipoConstruccion obraSeleccionada;
    private int idObra;

    private short tipoObraSeleccionada;
    private short tipoConstruccion;
    private String columna;
    private String materialLabel;
    private String unidadLabel;
    private String onkeypressevent;
    private ValorDiscreto valorDiscretoObra;
    private short anioActual;
    private short codeUsoDefault;

    @PostConstruct
    //<editor-fold defaultstate="collapsed" desc="init">
    public void init() {

        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        predio = new Predio();

        bloques = new ArrayList<>();

        pisos = new ArrayList<>();
        bloquesEliminar = new ArrayList<>();
        pisosEliminar = new ArrayList<>();
        creandoBloque = false;
        editandoBloque = false;
        fotos = new ArrayList<>();
        usoSeleccionado = new UsoSuelo();
        usosSuelo = new ArrayList<>();

        valorDiscreto = valorDiscreto = variablesService.obtenerValorbyVariableValor(33, (short) 12, anioActual);
        valorDiscretos = getValorDiscretos();
        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        usosSueloEliminar = new ArrayList<>();

        codeUsoDefault = (short) 12;

        obrasComplementarias = new ArrayList<>();
        obrasComplementariasEliminar = new ArrayList<>();
        obraSeleccionada = new OtrosTipoConstruccion();
        idObra = catastroService.numeroProximaObra();
        tipoConstruccion = 1;
        tipoObraSeleccionada = 1;
        materialLabel = "Tipo material:";
        columna = "muros";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    public List<ValorDiscreto> getValorDiscretos() {

        valorDiscretos = new ArrayList<>();

        for (int i = 33; i < 54; i++) {
            List<ValorDiscreto> valores = variablesService.obtenerValoresPorCodVariable(i, anioActual);
            if (valores != null) {
                valorDiscretos.addAll(valores);
            }
        }

        return valorDiscretos;
//        valorDiscretos = new ArrayList<>();
//        List<ValorDiscreto> valores = new ArrayList<>();
//        switch (predio.getTerreno().getUsoSuelo()) {
//
//            case 19: {
//                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//            case 20: {
//                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(36, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//            case 21: {
//                valores = variablesService.obtenerValoresPorCodVariable(33, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(37, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(39, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(51, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                valores = variablesService.obtenerValoresPorCodVariable(36, anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//            default: {
//                valores = variablesService.obtenerValoresPorCodVariable(valorById(predio.getTerreno().getUsoSuelo()), anioActual);
//                if (valores != null) {
//                    valorDiscretos.addAll(valores);
//                }
//                break;
//            }
//        }

//        return valorDiscretos;
    }

    public void setValorDiscretos(List<ValorDiscreto> valorDiscretos) {
        this.valorDiscretos = valorDiscretos;
    }

    public short getCodeUsoDefault() {
        return codeUsoDefault;
    }

    public void setCodeUsoDefault(short codeUsoDefault) {
        this.codeUsoDefault = codeUsoDefault;
    }

    public List<UploadFile> getFotos() {
        return fotos;
    }

    public void setFotos(List<UploadFile> fotos) {
        this.fotos = fotos;
    }

    public List<OtrosTipoConstruccion> getObrasComplementarias() {
        return obrasComplementarias;
    }

    public void setObrasComplementarias(List<OtrosTipoConstruccion> obrasComplementarias) {
        this.obrasComplementarias = obrasComplementarias;
    }

    public List<OtrosTipoConstruccion> getObrasComplementariasEliminar() {
        return obrasComplementariasEliminar;
    }

    public void setObrasComplementariasEliminar(List<OtrosTipoConstruccion> obrasComplementariasEliminar) {
        this.obrasComplementariasEliminar = obrasComplementariasEliminar;
    }

    public OtrosTipoConstruccion getObraSeleccionada() {
        return obraSeleccionada;
    }

    public void setObraSeleccionada(OtrosTipoConstruccion obraSeleccionada) {
        this.obraSeleccionada = obraSeleccionada;
    }

    public int getIdObra() {
        return idObra;
    }

    public void setIdObra(int idObra) {
        this.idObra = idObra;
    }

    public short getTipoObraSeleccionada() {
        return tipoObraSeleccionada;
    }

    public void setTipoObraSeleccionada(short tipoObraSeleccionada) {
        this.tipoObraSeleccionada = tipoObraSeleccionada;
    }

    public short getTipoConstruccion() {
        return tipoConstruccion;
    }

    public void setTipoConstruccion(short tipoConstruccion) {
        this.tipoConstruccion = tipoConstruccion;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getMaterialLabel() {
        return materialLabel;
    }

    public void setMaterialLabel(String materialLabel) {
        this.materialLabel = materialLabel;
    }

    public String getUnidadLabel() {
        return unidadLabel;
    }

    public void setUnidadLabel(String unidadLabel) {
        this.unidadLabel = unidadLabel;
    }

    public String getOnkeypressevent() {
        return onkeypressevent;
    }

    public void setOnkeypressevent(String onkeypressevent) {
        this.onkeypressevent = onkeypressevent;
    }

    public ValorDiscreto getValorDiscretoObra() {
        return valorDiscretoObra;
    }

    public void setValorDiscretoObra(ValorDiscreto valorDiscretoObra) {
        this.valorDiscretoObra = valorDiscretoObra;
    }

    public List<UsoSuelo> getUsosSueloEliminar() {
        return usosSueloEliminar;
    }

    public void setUsosSueloEliminar(List<UsoSuelo> usosSueloEliminar) {
        this.usosSueloEliminar = usosSueloEliminar;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
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

    public List<Piso> getPisos() {
        return pisos;
    }

    public void setPisos(List<Piso> pisos) {
        this.pisos = pisos;
    }

    public Piso getPisoSeleccionado() {
        return pisoSeleccionado;
    }

    public void setPisoSeleccionado(Piso pisoSeleccionado) {
        this.pisoSeleccionado = pisoSeleccionado;
    }

    public int getPisoSeleccionadoIndex() {
        return pisoSeleccionadoIndex;
    }

    public void setPisoSeleccionadoIndex(int pisoSeleccionadoIndex) {
        this.pisoSeleccionadoIndex = pisoSeleccionadoIndex;
    }

    public boolean isCreandoPiso() {
        return creandoPiso;
    }

    public void setCreandoPiso(boolean creandoPiso) {
        this.creandoPiso = creandoPiso;
    }

    public boolean isEditandoPiso() {
        return editandoPiso;
    }

    public void setEditandoPiso(boolean editandoPiso) {
        this.editandoPiso = editandoPiso;
    }

    public int getSecuenciaPiso() {
        return secuenciaPiso;
    }

    public void setSecuenciaPiso(int secuenciaPiso) {
        this.secuenciaPiso = secuenciaPiso;
    }

    public List<Piso> getPisosEliminar() {
        return pisosEliminar;
    }

    public void setPisosEliminar(List<Piso> pisosEliminar) {
        this.pisosEliminar = pisosEliminar;
    }

    public BloqueService getBloqueService() {
        return bloqueService;
    }

    public void setBloqueService(BloqueService bloqueService) {
        this.bloqueService = bloqueService;
    }

    public List<ArchivoAux> getFotosPreview() {
        return fotosPreview;
    }

    public void setFotosPreview(List<ArchivoAux> fotosPreview) {
        this.fotosPreview = fotosPreview;
    }

    public int getSecuenciaBloque() {
        return secuenciaBloque;
    }

    public void setSecuenciaBloque(int secuenciaBloque) {
        this.secuenciaBloque = secuenciaBloque;
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

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public Predio getPredio() {
        return predio;
    }

    public List<Bloque> getBloques() {
        return bloques;
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

    public VariableService getVariablesService() {
        return variablesService;
    }

    public boolean isEditandoBloque() {
        return editandoBloque;
    }

    public void setEditandoBloque(boolean editandoBloque) {
        this.editandoBloque = editandoBloque;
    }

    public boolean isCreandoBloque() {
        return creandoBloque;
    }

    public void setCreandoBloque(boolean creandoBloque) {
        this.creandoBloque = creandoBloque;
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

    public List<Bloque> getBloquesEliminar() {
        return bloquesEliminar;
    }

    public void setBloquesEliminar(List<Bloque> bloquesEliminar) {
        this.bloquesEliminar = bloquesEliminar;
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

    public String getPhhCod() {
        return phhCod;
    }

    public void setPhhCod(String phhCod) {
        this.phhCod = phhCod;
    }

    public String getPhvCod() {
        return phvCod;
    }

    public void setPhvCod(String phvCod) {
        this.phvCod = phvCod;
    }

//</editor-fold>
    public void buscar() {

        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
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
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {
                predioAnt = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);
                pant = new Versioner().getJson(predioAnt);
                if (!predio.getPropietarios().isEmpty()) {
                    for (int i = 0; i < predio.getPropietarios().size(); i++) {
                        predio.getPropietarios().get(i).obtenerEstatus(predio.getId());
                    }
                }
                obrasComplementarias = predio.getOtrosConstruccion();
                if (!obrasComplementarias.isEmpty()) {
                    obraSeleccionada = obrasComplementarias.get(0);
                }
                secuenciaBloque = 1;
                bloques = predio.getBloques();
                if (!bloques.isEmpty()) {
                    bloqueSeleccionadoIndex = 0;
                    bloqueSeleccionado = bloques.get(0);
                    fotoBloque(bloqueSeleccionado.getId());
                    usosSuelo = bloqueSeleccionado.getUsosSuelo();
                    if (!usosSuelo.isEmpty()) {
                        usoSeleccionado = usosSuelo.get(0);
                    }
                    secuenciaBloque = catastroService.nextBloque(predio.getId());

                    pisos = bloqueSeleccionado.getPisos();
                    if (!pisos.isEmpty()) {
                        pisoSeleccionadoIndex = 0;
                        pisoSeleccionado = pisos.get(0);
                        secuenciaPiso = catastroService.nextPiso(bloqueSeleccionado.getId());
                    }
                }

//                int id = valorById(predio.getTerreno().getUsoSuelo());
//                codeUsoDefault = valorDefaultById(id);
//
//                valorDiscreto = variablesService.obtenerValorbyVariableValor(id, codeUsoDefault, anioActual);
                opcionesBusqueda.setEjecutandoAccion(true);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    public String cancelar() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String actualizar() {

        Subject subject = SecurityUtils.getSubject();

        String usuar = subject.getPrincipal().toString();
        try {
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(usuar);
            predio.setOtrosConstruccion(obrasComplementarias);

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

            catastroService.updateDatosConstruccion(predio, bloquesEliminar, pisosEliminar, usosSueloEliminar, obrasComplementariasEliminar);
            audit.save(predioAnt.getClaveCatastral(), "Actualización de Construcciones", pant, new Versioner().getJson(predio), usuar);
            JsfUtil.addInformationMessage(null, "Predio " + predio.getClaveCatastral() + " actualizado correctamente.");
            init();

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        opcionesBusqueda.setEjecutandoAccion(false);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";

    }

    public String onFlowProcess(FlowEvent event) {

        return event.getNewStep();

    }

    private int numeroBloque() {

        if (bloques.isEmpty()) {
            secuenciaBloque = 1;
        } else {
            secuenciaBloque = bloques.get(bloques.size() - 1).getNumeroBloque();
            secuenciaBloque++;
        }

        return secuenciaBloque;
    }

    //<editor-fold defaultstate="collapsed" desc="inicializarNuevoBloque">
    public void inicializarNuevoBloque() {
        bloqueSeleccionado = new Bloque();
        bloqueSeleccionado.setNumeroBloque(numeroBloque());
        pisos = new ArrayList<>();
        fotosPreview = new ArrayList<>();
        usosSuelo.clear();
        creandoBloque = true;
        editandoBloque = false;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionBloque">
    public void inicializarActualizacionBloque() {
        editandoBloque = true;
        creandoBloque = true;
        fotos.clear();
//        if (bloqueSeleccionado.isEditado()) {
//            fotosBloquePreview = bloqueSeleccionado.getImagesPreview();
//        }
        if (bloqueSeleccionado.getId() != null) {
//            if (bloqueSeleccionado.isEditado()) {
//                fotosBloquePreview = bloqueSeleccionado.getImagesPreview();
//            } else {
            fotoBloque(bloqueSeleccionado.getId());
//            }
        }
        pisos = bloqueSeleccionado.getPisos();
        if (!pisos.isEmpty()) {
            pisoSeleccionadoIndex = 0;
            pisoSeleccionado = pisos.get(pisoSeleccionadoIndex);
        }
        usosSuelo = bloqueSeleccionado.getUsosSuelo();
        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        } else {
            usoSeleccionado = new UsoSuelo();
        }

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="crearBloque">
    public void crearBloque() {

        if (pisos.isEmpty()) {
            secuenciaBloque--;
            creandoBloque = false;
            creandoPiso = false;
            editandoBloque = false;
            editandoBloque = false;
            JsfUtil.addErrorMessage("Debe registrarle pisos al bloque.");
        } else if (bloqueSeleccionado != null) {

            if (bloqueSeleccionado.getNumeroNiveles() != pisos.size()) {
                JsfUtil.addErrorMessage("Faltan " + (bloqueSeleccionado.getNumeroNiveles() - pisos.size()) + " pisos por registrar al bloque " + bloqueSeleccionado.getNumeroBloque() + " para que coincida con el nro de niveles.");
            } else {

                bloqueSeleccionado.setPredio(predio);
                bloqueSeleccionado.setFotosBloque(fotos);
                //bloqueSeleccionado.setInfoFotosBloque(datosFotos);
                bloqueSeleccionado.setImagesPreview(fotosPreview);
                for (int i = 0; i < usosSuelo.size(); i++) {
                    usosSuelo.get(i).setBloque(bloqueSeleccionado);
                }
                bloqueSeleccionado.setUsosSuelo(usosSuelo);
                boolean estaBloque = false;
                String creado = " creado ";
                for (int i = 0; i < bloques.size(); i++) {
                    if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {
                        bloques.remove(i);
                        bloques.add(i, bloqueSeleccionado);
                        estaBloque = true;
                        creado = " editado ";
                        bloqueSeleccionadoIndex = i;
                        break;
                    }
                }

                if (!estaBloque) {
                    bloques.add(bloqueSeleccionado);
                    bloqueSeleccionadoIndex = bloques.size() - 1;
                }

                predio.setBloques(bloques);

                JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                creandoBloque = false;
                editandoBloque = false;
                editandoPiso = false;
                creandoPiso = false;
                secuenciaPiso = 1;
            }

        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancelarCreacionBloque">
    public void cancelarCreacionBloque() {
        creandoBloque = false;
        editandoBloque = false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actualizarBloque">
    public void actualizarBloque() {
        if (bloqueSeleccionado != null) {

            JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + " actualizado");
            creandoBloque = false;
            editandoBloque = false;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarBloque">
    public void eliminarBloque() {
        if (bloqueSeleccionado != null) {
            for (int i = 0; i < bloques.size(); i++) {
                if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {
                    bloques.remove(i);
                    break;
                }
            }
            if (bloqueSeleccionado.getId() != null) {
                bloquesEliminar.add(bloqueSeleccionado);
            }

            if (!bloques.isEmpty()) {
                bloqueSeleccionado = bloques.get(0);
                bloqueSeleccionadoIndex = 0;
                pisos = bloqueSeleccionado.getPisos();
                if (!pisos.isEmpty()) {
                    pisoSeleccionado = pisos.get(0);
                    pisoSeleccionadoIndex = 0;
                }
            }

            for (Bloque b : bloquesEliminar) {
                for (Piso p : b.getPisos()) {
                    if (p.getId() != null) {
                        if (!pisosEliminar.contains(p)) {
                            pisosEliminar.add(p);
                        }
                    }
                }
                for (UsoSuelo uso : b.getUsosSuelo()) {
                    if (uso.getId() != null) {
                        if (!usosSueloEliminar.contains(uso)) {
                            usosSueloEliminar.add(uso);
                        }
                    }
                }
            }
        }

        creandoBloque = false;
        editandoBloque = false;

    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarBloque">
    public void cambiarBloque() {
        bloqueSeleccionado = bloques.get(bloqueSeleccionadoIndex);
        pisos = bloqueSeleccionado.getPisos();
        pisoSeleccionadoIndex = -1;
        if (!pisos.isEmpty()) {
            pisoSeleccionado = pisos.get(0);
            pisoSeleccionadoIndex = 0;
        }
        editandoBloque = false;
        creandoBloque = false;
        creandoPiso = false;
        editandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();

        if (!bloqueSeleccionado.getUsosSuelo().isEmpty()) {
            usosSuelo = bloqueSeleccionado.getUsosSuelo();
            usoSeleccionado = usosSuelo.get(0);
        } else {
            usosSuelo = new ArrayList<>();
            usoSeleccionado = new UsoSuelo();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarNuevoPiso">
    public void inicializarNuevoPiso() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (pisos.size() == bloqueSeleccionado.getNumeroNiveles()) {
            JsfUtil.addErrorMessage("La cantidad de pisos registra coincide con la cantidad de niveles del bloque.");
        } else {

            context.execute("PF('datosPisoDialog').show();");

            pisoSeleccionado = new Piso();
            if (pisos.isEmpty()) {
                pisoSeleccionado.setNumeroPiso(pisos.size() + 1);
            } else {
                pisoSeleccionado.setNumeroPiso(pisos.get(pisos.size() - 1).getNumeroPiso() + 1);
            }
            creandoPiso = true;
            editandoPiso = false;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="guardarPiso">
    public void guardarPiso() {
        try {
            if (pisoSeleccionado != null) {
                if (pisoSeleccionado.getArea() != 0) {
                    pisoSeleccionado.setBloque(bloqueSeleccionado);
                    boolean estaPiso = false;
                    String creado = " creado ";
                    for (int i = 0; i < pisos.size(); i++) {
                        if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {
                            pisos.remove(i);
                            pisos.add(i, pisoSeleccionado);
                            estaPiso = true;
                            creado = " editado ";
                            pisoSeleccionadoIndex = i;
                            break;
                        }
                    }
                    if (!estaPiso) {
                        pisos.add(pisoSeleccionado);
                        pisoSeleccionadoIndex = pisos.size() - 1;
                    }

                    creandoPiso = false;
                    editandoPiso = false;
                    bloqueSeleccionado.setPisos(pisos);
                    double area_total = 0;
                    for (Piso p : pisos) {
                        area_total += p.getArea();
                    }
                    bloqueSeleccionado.setAreaTotal(area_total);
                    JsfUtil.addSuccessMessage("Piso " + pisoSeleccionado.getNumeroPiso() + " del bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                } else {

                    JsfUtil.addErrorMessage("Área del piso no puede ser 0.");
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicializarActualizacionPiso">
    public void inicializarActualizacionPiso() {
        editandoPiso = true;
        creandoPiso = false;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actualizarPiso">
    public void actualizarPiso() {
        editandoPiso = false;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancelarCreacionPiso">
    public void cancelarCreacionPiso() {
        if (creandoPiso) {
            pisoSeleccionado = null;
        }
        editandoPiso = false;
        creandoPiso = false;
        secuenciaPiso--;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cambiarPiso">
    public void cambiarPiso() {
        pisoSeleccionado = pisos.get(pisoSeleccionadoIndex);
        obraSeleccionada = new OtrosTipoConstruccion();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eliminarPiso">
    public void eliminarPiso() {
        if (pisoSeleccionado != null) {
            for (int i = 0; i < pisos.size(); i++) {
                if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {

                    if (pisoSeleccionado.getId() != null) {
                        if (!pisosEliminar.contains(pisoSeleccionado)) {
                            pisosEliminar.add(pisoSeleccionado);
                        }
                    }
                    pisos.remove(i);
                    break;
                }
            }

            JsfUtil.addSuccessMessage("Piso " + pisoSeleccionado.getNumeroPiso() + " del bloque " + bloqueSeleccionado.getNumeroBloque() + " eliminado con èxito.");
            if (!pisos.isEmpty()) {
                pisoSeleccionado = pisos.get(0);
                pisoSeleccionadoIndex = 0;
            }

            double area_total = 0;
            for (Piso p : pisos) {
                area_total += p.getArea();
            }
            bloqueSeleccionado.setAreaTotal(area_total);
        }
        editandoPiso = false;
        creandoPiso = false;
    }
//</editor-fold>

    public void onRowPisoSelect(SelectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
        // FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowPisoUnselect(UnselectEvent event) {
        //FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
        // FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_FB_";
        Util.copyFile(uploadedFile, fileName, fotos, "fotos", (short) 1);
    }

    public void eliminarFoto(int index) {
        Util.deleteFile(index, fotos);
    }

    private void fotoBloque(int idBloque) {
        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        Bloque b = bloqueService.findByNamedQuery("Bloque.findByID", idBloque).get(0);

        String foto;
        String pathToPhoto = "";
        fotosPreview = new ArrayList<>();

        if (!b.getImages().isEmpty()) {

            String uploadDir = Util.directorio_imagenes;

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";

            for (BloqueImage img : b.getImages()) {

                foto = img.getRuta();
                ArchivoAux aux = new ArchivoAux();
                aux.setIdArchivo(img.getId());

                try {
                    if (existeArchivo(uploadDir + "/" + foto)) {
                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + foto);
                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);

                        try {
                            IOUtils.copy(inFile, outFile);
                            inFile.close();
                            outFile.close();
                        } catch (IOException ex) {

                        }
                    }

                    if (existeArchivo(tempDir + "/" + foto)) {
                        pathToPhoto = urlBase + "/fotos/" + foto;
                        aux.setRuta(pathToPhoto);
                        if (!fotosPreview.contains(aux)) {
                            fotosPreview.add(aux);
                        }
                    } else {
                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
                        aux.setRuta(pathToPhoto);
                        if (!fotosPreview.contains(aux)) {
                            fotosPreview.add(aux);
                        }
                    }

                } catch (FileNotFoundException ex) {

                }
            }

        }

    }

    public void eliminarFotoActual(Integer id) {

        for (ArchivoAux au : fotosPreview) {
            if (Objects.equals(au.getIdArchivo(), id)) {
                fotosPreview.remove(au);
            }
        }

    }

    private boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
    }

    public void selectFotoEliminar(Integer id) {

    }

    public void initNuevoUso() {
//        usoSeleccionado = new UsoSuelo();
//        int id = valorById(predio.getTerreno().getUsoSuelo());
//        codeUsoDefault = valorDefaultById(id);

        valorDiscreto = variablesService.obtenerValorbyVariableValor(33, (short) 12, anioActual);

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());

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
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());

        boolean existe = false;

        for (int i = 0; i < usosSuelo.size(); i++) {
            if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            usoSeleccionado.setBloque(bloqueSeleccionado);
            usosSuelo.add(usoSeleccionado);
        }

        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        }

        if (existe) {
            JsfUtil.addInformationMessage("Uso", "Uso del suelo se encuentra registrado al bloque.");
        } else {
            JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito");
        }

        initNuevoUso();
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

        for (int i = 33; i < 54; i++) {
            codigos += "-" + variablesService.obtenerCodigoPorIdVariable(i, anioActual);
        }
//        switch (predio.getTerreno().getUsoSuelo()) {
//
//            case 1: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                break;
//            }
//            case 2: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(34, anioActual);
//                break;
//            }
//            case 3: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(35, anioActual);
//                break;
//            }
//            case 4: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 5: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(38, anioActual);
//                break;
//            }
//            case 6: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                break;
//            }
//            case 7: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(40, anioActual);
//                break;
//            }
//            case 8: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(41, anioActual);
//                break;
//            }
//            case 9: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(42, anioActual);
//                break;
//            }
//            case 10: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(45, anioActual);
//                break;
//            }
//            case 11: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(46, anioActual);
//                break;
//            }
//            case 12: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(47, anioActual);
//                break;
//            }
//            case 13: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(48, anioActual);
//                break;
//            }
//            case 14: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(49, anioActual);
//                break;
//            }
//            case 15: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                break;
//            }
//            case 16: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(52, anioActual);
//                break;
//            }
//            case 17: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(36, anioActual);
//                break;
//            }
//            case 18: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(53, anioActual);
//                break;
//            }
//            case 19: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 20: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            case 21: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            default: {
//                // codigos += variablesService.obtenerCodigoPorIdVariable(terreno.getUsoSuelo(), anioActual);
//                break;
//            }
//        }
        //codigos += variablesService.obtenerCodigoPorIdVariable(i,anioActual);
        // }

        return codigos;

    }

    public String codigosVariablesuUsosPH() {

        String codigos = "";

        for (int i = 33; i < 54; i++) {
            codigos += "-" + variablesService.obtenerCodigoPorIdVariable(i, anioActual);
        }

//        switch (predio.getTerreno().getUsoSuelo()) {
//
//            case 1: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                break;
//            }
//            case 2: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(34, anioActual);
//                break;
//            }
//            case 3: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(35, anioActual);
//                break;
//            }
//            case 4: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 5: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(38, anioActual);
//                break;
//            }
//            case 6: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                break;
//            }
//            case 7: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(40, anioActual);
//                break;
//            }
//            case 8: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(41, anioActual);
//                break;
//            }
//            case 9: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(42, anioActual);
//                break;
//            }
//            case 10: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(45, anioActual);
//                break;
//            }
//            case 11: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(46, anioActual);
//                break;
//            }
//            case 12: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(47, anioActual);
//                break;
//            }
//            case 13: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(48, anioActual);
//                break;
//            }
//            case 14: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(49, anioActual);
//                break;
//            }
//            case 15: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                break;
//            }
//            case 16: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(52, anioActual);
//                break;
//            }
//            case 17: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(36, anioActual);
//                break;
//            }
//            case 18: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(53, anioActual);
//                break;
//            }
//            case 19: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                break;
//            }
//            case 20: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            case 21: {
//                codigos += variablesService.obtenerCodigoPorIdVariable(33, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(37, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(39, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(51, anioActual);
//                codigos += "-"+ variablesService.obtenerCodigoPorIdVariable(57, anioActual);
//                break;
//            }
//            default: {
//                // codigos += variablesService.obtenerCodigoPorIdVariable(terreno.getUsoSuelo(), anioActual);
//                break;
//            }
//        }
//        for (int i = 33; i <= 52; i++) {
//            codigos += variablesService.obtenerCodigoPorIdVariablePH(i,anioActual);
//        }
        return codigos;

    }

    public void changeUso() {

//        ValorDiscreto aux = variablesService.obtenerValorbyVariableValor(valorDiscreto.getValorDiscretoPK().getIdVariable(), usoSeleccionado.getCod(), anioActual);
//        if (aux != null) {
//            valorDiscreto = aux;
//        } else {
//
//            int id = valorById(predio.getTerreno().getUsoSuelo());
//            short value = valorDefaultById(id);
//            valorDiscreto = variablesService.obtenerValorbyVariableValor(id, value, anioActual);
//            codeUsoDefault = value;
//        }
//        
        try {
            valorDiscreto = variablesService.obtenerValor(33, 53, usoSeleccionado.getCod(), anioActual);

        } catch (Exception e) {
            valorDiscreto = variablesService.obtenerValorbyVariableValor(33, (short) 12, anioActual);
        }

        //valorDiscreto = variablesService.obtenerValor(33, 52, cod, anioActual);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        usoSeleccionado.setValorDiscreto(valorDiscreto);
    }

    public void changeUsoSelect() {

        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        usoSeleccionado.setValorDiscreto(valorDiscreto);

    }

    public void onRowSelectObra(SelectEvent event) {
        obraSeleccionada = (OtrosTipoConstruccion) event.getObject();
    }

    public void onRowUnselectObra(UnselectEvent event) {
        obraSeleccionada = (OtrosTipoConstruccion) event.getObject();
    }

    public void initNuevaObra() {
        obraSeleccionada = new OtrosTipoConstruccion();
        obraSeleccionada.setNumero(idObra++);
        tipoConstruccion = (short) 1;
        obraSeleccionada.setTipoConstruccion((short) 1);
        materialLabel = "Tipo material:";
        columna = "muros";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, anioActual);
    }

    public void initEditObra() {
        if (obraSeleccionada.getVariable() == 100) {
            this.tipoConstruccion = (short) 3;
        }
        if (obraSeleccionada.getVariable() == 101) {
            this.tipoConstruccion = (short) 4;
        }
        if (obraSeleccionada.getVariable() == 102) {
            this.tipoConstruccion = (short) 1;
        }
        if (obraSeleccionada.getVariable() == 103) {
            this.tipoConstruccion = (short) 2;
        }
        short valorActualTipo = tipoConstruccion;
        short valorActualTipoConstruccion = obraSeleccionada.getTipoConstruccion();
        changeTipoConstruccion();
        obraSeleccionada.setTipoConstruccion(valorActualTipoConstruccion);
        tipoConstruccion = valorActualTipo;

    }

    public void cancelarCrearObra() {
        if (!obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        }

    }

    public void eliminarObra() {

        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                obrasComplementarias.remove(i);

                break;
            }
        }

        if (obraSeleccionada.getId() != null) {
            if (!obrasComplementariasEliminar.contains(obraSeleccionada)) {
                obrasComplementariasEliminar.add(obraSeleccionada);
            }
        }

        if (!obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        } else {
            initNuevaObra();
        }

    }

    public void crearNuevaObra() {

        RequestContext context = RequestContext.getCurrentInstance();
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, obraSeleccionada.getTipoConstruccion(), anioActual);
        obraSeleccionada.setVariable(valorDiscretoObra.getVariable().getId());
        obraSeleccionada.setUnidad(obraSeleccionada.getArea().shortValue());
        boolean existe = false;
        String accion = "editado";
        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                obrasComplementarias.remove(i);
                obrasComplementarias.add(i, obraSeleccionada);
                existe = true;
                break;
            }
        }
        if (!existe) {
            obraSeleccionada.setPredio(predio);
            obrasComplementarias.add(obraSeleccionada);
            accion = "creado";

        }
        initNuevaObra();
        JsfUtil.addInformationMessage(null, "Otro tipo de construcción " + accion + " con exito.");

        if (existe) {

            context.execute("PF('obrasDialog').hide();");

        }
    }

    public void changeTipoConstruccion() {
        switch (tipoConstruccion) {
            case 1:
            case 2:
                materialLabel = "Tipo material:";
                if (tipoConstruccion == 1) {
                    columna = "muros";
                } else {
                    columna = "cerramiento";
                }
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            case 3:
                materialLabel = "Obra complementaria:";
                columna = "obras_complementarias";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
            default:
                materialLabel = "Instalación especial:";
                columna = "instalaciones_especiales";
                onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
                break;
        }

        obraSeleccionada.setTipoConstruccion((short) 1);
        tipoObraSeleccionada = 1;
    }

    public void changeTipoObra() {
        if (tipoConstruccion == 3 && obraSeleccionada.getTipoConstruccion() == 8) {
            onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')(event, 'area_estructura:cat_bloque-estructura-input')";
        } else {
            onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        }
    }

    public String nombreVariable(int id) {

        return variablesService.find(id).getNombre();
    }

    public String nombreValorDiscreto(int id, short tipo, short estructura, short cubierta) {
        String valor = variablesService.obtenerValor(id, tipo, anioActual);
        if (id == 100 && tipo == 8) {
            valor += " (ESTRUCTURA: " + variablesService.obtenerValor("cat_bloque", "estructura", estructura, anioActual)
                    + " CUBIERTA: " + variablesService.obtenerValor("cat_bloque", "cubierta", cubierta, anioActual) + ")";

        }
        return valor;
    }

    public short getAnioActual() {
        anioActual = (short) (Util.ANIO_ACTUAL.shortValue());
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    private int valorById(int id) {
        switch (id) {
            default: {
                return 33;
            }
            case 2: {
                return 34;
            }
            case 3: {
                return 35;
            }
            case 4: {
                return 37;
            }
            case 5: {
                return 38;
            }
            case 6: {
                return 39;
            }
            case 7: {
                return 40;
            }
            case 8: {
                return 41;
            }
            case 9: {
                return 42;
            }
            case 10: {
                return 45;
            }
            case 11: {
                return 46;
            }
            case 12: {
                return 47;
            }
            case 13: {
                return 48;
            }
            case 14: {
                return 49;
            }
            case 15: {
                return 51;
            }
            case 16: {
                return 52;
            }
            case 17: {
                return 36;
            }
            case 18: {
                return 53;
            }

        }
    }

    private short valorDefaultById(int id) {
        switch (id) {
            default: {
                return 12;
            }
            case 34: {
                return 10;
            }
            case 35: {
                return 47;
            }
            case 37: {
                return 95;
            }
            case 38: {
                return 6;
            }
            case 39: {
                return 34;
            }
            case 40: {
                return 4;
            }
            case 41: {
                return 25;
            }
            case 42: {
                return 27;
            }
            case 45: {
                return 97;
            }
            case 46: {
                return 29;
            }
            case 47: {
                return 30;
            }
            case 48: {
                return 21;
            }
            case 49: {
                return 1;
            }
            case 51: {
                return 49;
            }
            case 52: {
                return 11;
            }
            case 36: {
                return 101;
            }
            case 53: {
                return 106;
            }

        }
    }

}
