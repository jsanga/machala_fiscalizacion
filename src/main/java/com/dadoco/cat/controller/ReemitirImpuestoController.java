/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.audit.service.AuditService;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.ImpuestoPredialPK;
import com.dadoco.cat.model.InfoBloque;
import com.dadoco.cat.model.InfoInfraestructura;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Reemision;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.DatosAutorizacionService;
import com.dadoco.cat.service.DeudaService;
import com.dadoco.cat.service.ImpuestoPredialService;
import com.dadoco.cat.service.ImpuestoService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.util.Base64;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "reemitirImpuestoView")
@ViewScoped
public class ReemitirImpuestoController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ReemitirImpuestoController.class);
    @EJB
    private ConfigReader config;
    @EJB
    private CatastroService catastroService;
    @EJB
    private PredioService predioService;
    @EJB
    private TipoDocumentoService tipoDocumentoService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private DatosAutorizacionService autorizacionService;
    @EJB
    private DeudaService deudaService;
    @EJB
    private VariableService variableService;
    @EJB
    private ImpuestoService impuestoService;
    @EJB
    private ImpuestoPredialService impuestoPredialService;

    @EJB
    private AvaluosService avaluosService;
    @EJB
    private AuditService auditServices;


    private OpcionesBusquedaPredio opcionesBusqueda;

    private DatosAutorizacion datosAutorizacion;

    private List<SelectItem> tiposDocumentos;

    private Long idTipoDocumento;
    private SelectItem documentoSelect;

    private String user;
    private Predio predio;

    private List<UploadedFile> docs;
    private UploadedImage[] datosDocs;

    private List<Deuda> listaDeudas;

    private List<Integer> deudasMarcadas;

    private List<Reemision> listaConfirmacion;

    private short anioEmision = 0;
    private Subject subject;

    private ImpuestoPredial impuestoPredial;
    private ImpuestoPredial impuestoPredialAnterior;

    @PostConstruct
    public void init() {

        subject = SecurityUtils.getSubject();

        opcionesBusqueda = new OpcionesBusquedaPredio();

        datosAutorizacion = new DatosAutorizacion();
        datosAutorizacion.setResponsable(subject.getPrincipal().toString());

        idTipoDocumento = new Long(1);

        tiposDocumentos = new ArrayList<>();

        predio = new Predio();
        documentoSelect = new SelectItem();

        deudasMarcadas = new ArrayList<>();
        listaConfirmacion = new ArrayList<>();

        datosDocs = new UploadedImage[2];
        docs = new ArrayList<>();
        anioEmision = Util.ANIO_ACTUAL.shortValue();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);
    }

    public void buscarPredio() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
       // short phCod = opcionesBusqueda.getPhCod();

        try {
           // predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);

            if (predio == null) {
//                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %2d-%2d-%3d-%2d-%2d no encontrado",
//                        zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//                init();
            } else {

                ImpuestoPredialPK pk = new ImpuestoPredialPK(predio.getId(), anioEmision);
                impuestoPredialAnterior = impuestoService.find(pk);
                impuestoPredial = avaluosService.reCalcularImpuestoPredial(predio, anioEmision);

                opcionesBusqueda.setEjecutandoAccion(true);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public void marcarDesmarcar(int idDeuda) {

        if (deudasMarcadas.contains(idDeuda)) {
            deudasMarcadas.remove(deudasMarcadas.indexOf(idDeuda));
        } else {
            deudasMarcadas.add(idDeuda);
        }
    }

    public String reemitirDeudasSeleccionadas() {

        try {

            avaluosService.actualizarImpuestoPredial(predio, anioEmision);

            int totalPredios = impuestoService.totalPredios();
            int totalPreemitidos = impuestoService.totalPreEmitidosPorAnnio(anioEmision);
            int totalEmitidos = impuestoService.totalEmitidosPorAnnio(anioEmision);
            double recaudacionTotal = Math.round(impuestoService.recaudacionTotal(anioEmision) * 100) / 100;
            double recaudacionEmitida = Math.round(impuestoService.recaudacionEmitida(anioEmision) * 100) / 100;

            impuestoService.actualizarDatosEmision(anioEmision, totalPredios, totalPreemitidos, totalEmitidos, recaudacionTotal, recaudacionEmitida);

        

            JsfUtil.addInformationMessage("Reevaluación de Deudas", "Deudas reevaluadas correctamente.");

            actualizarResumenEmision(anioEmision);
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

//        if (event.getNewStep().equals("step-lista-deudas")) {
//            deudasMarcadas.clear();
//            listaConfirmacion.clear();
//        }
//
//        if (event.getOldStep().equals("step-lista-deudas") && event.getNewStep().equals("step-confirmacion")) {
//
//            if (!deudasMarcadas.isEmpty()) {
//                generaDocumentos();
//            } else {
//                return event.getOldStep();
//            }
//        }
        return event.getNewStep();
    }

    @Transactional
    public void generaReEmision(String clave) {

        Predio p = predioService.findByNamedQuery("Predio.findByClaveCatastral", clave).get(0);

        avaluosService.actualizarImpuestoPredial(p, anioEmision);

        int totalPredios = impuestoService.totalPredios();
        int totalPreemitidos = impuestoService.totalPreEmitidosPorAnnio(anioEmision);
        int totalEmitidos = impuestoService.totalEmitidosPorAnnio(anioEmision);
        double recaudacionTotal = Math.round(impuestoService.recaudacionTotal(anioEmision) * 100) / 100;
        double recaudacionEmitida = Math.round(impuestoService.recaudacionEmitida(anioEmision) * 100) / 100;

        impuestoService.actualizarDatosEmision(anioEmision, totalPredios, totalPreemitidos, totalEmitidos, recaudacionTotal, recaudacionEmitida);
        auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "GENERACIÓN DE EMISIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
    }

    public void generaDocumentos() {

        Reemision reemision;

        long consec = (long) variableService.obtenerCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) 0, anioEmision) + 1;

        String documento;
        float nuevoValorTitulo = 0;

        Object[] params = new Object[3];

        List<ImpuestoPredial> impuestos;
        ImpuestoPredial impuesto;

        for (Deuda d : listaDeudas) {
            if (deudasMarcadas.contains((int) d.getId())) {

                documento = String.format("%d%06d01M", d.getDocAnio(), consec);

                consec++;

//                params[0] = d.getClaveCatastral();
//                params[1] = d.getDocAnio();
//                params[2] = (short) 0;
//                impuestos = impuestoService.findByNamedQuery("ImpuestoPredial.findByClaveAnio", params);
//
//                if (!impuestos.isEmpty()) {
//                    impuesto = impuestos.get(0);
                // nuevoValorTitulo = avaluosService.actualizarImpuestoPredialReemision(predio, anioEmision);
                log.error("Nuevo valor del titulo en Generar Documento: " + nuevoValorTitulo);
//                }

                reemision = new Reemision((int) d.getId(), d.getDocumento(), d.getDocAnio(), d.getConcepto(), d.getSubtotal(), d.getSaldo(), d.getEstado(), d.isBloqueado(), documento, nuevoValorTitulo);

                listaConfirmacion.add(reemision);
            }
        }

    }

    @Transactional
    public void generaNuevaDeuda() {

        String documento;
        long consec;

        Deuda nuevaDeuda;
        List<ImpuestoPredial> impuestos;
        ImpuestoPredial impuesto;
        ImpuestoPredial impuestoNew = new ImpuestoPredial();

        float nuevoValorTitulo = 0;

        Object[] params = new Object[3];

        for (Deuda d : listaDeudas) {
            if (deudasMarcadas.contains((int) d.getId())) {

                consec = (long) variableService.obtenerCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) 0, anioEmision) + 1;
                documento = String.format("%d%06d01M", d.getDocAnio(), consec);
                variableService.setCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) 0, (float) consec, anioEmision);

//                params[0] = d.getClaveCatastral();
//                params[1] = d.getDocAnio();
//                params[2] = (short) 0;
//                impuestos = impuestoService.findByNamedQuery("ImpuestoPredial.findByClaveAnio", params);
//
//                if (!impuestos.isEmpty()) {
//                    impuesto = impuestos.get(0);
                //nuevoValorTitulo = avaluosService.actualizarImpuestoPredialReemision(predio, anioEmision);
                log.error("Nuevo valor del titulo en Generar Nueva Deuda: " + nuevoValorTitulo);
//                }

                nuevaDeuda = new Deuda();

                nuevaDeuda.setDocumento(documento);
                nuevaDeuda.setDocAnio(d.getDocAnio());
                nuevaDeuda.setClaveCatastral(d.getClaveCatastral());
                nuevaDeuda.setConcepto(d.getConcepto());
                nuevaDeuda.setContribuyente(d.getContribuyente());
                nuevaDeuda.setIdentificacion(d.getIdentificacion());
                nuevaDeuda.setEstado(d.getEstado());
                nuevaDeuda.setFechaIngreso(new Date());

                nuevaDeuda.setSubtotal(nuevoValorTitulo);
                nuevaDeuda.setVtc(d.getVtc());
                nuevaDeuda.setSaldo(nuevoValorTitulo + d.getVtc());

                nuevaDeuda.setBloqueado(d.isBloqueado());

                deudaService.create(nuevaDeuda);

                d.setEstado("ANULADO");
                deudaService.edit(d);
            }
        }
        //auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "NUEVA DEUDA DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
    }

    public void actualizarResumenEmision(short anio) {

        int totalPredios = impuestoService.totalPredios();
        int totalPreemitidos = impuestoService.totalPreEmitidosPorAnnio(anio);
        int totalEmitidos = impuestoService.totalEmitidosPorAnnio(anio);
        double recaudacionTotal = impuestoService.recaudacionTotal(anio);
        double recaudacionEmitida = impuestoService.recaudacionEmitida(anio);

        impuestoService.actualizarDatosEmision(anio, totalPredios, totalPreemitidos, totalEmitidos, recaudacionTotal, recaudacionEmitida);
    }

    public void handleFileUploadDocs(FileUploadEvent event) throws FileNotFoundException, IOException {

        //get uploaded file from the event
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        docs.add(uploadedFile);
        UploadedImage ui = new UploadedImage(docs.size(), FilenameUtils.getName(uploadedFile.getFileName()),
                "", "");

        datosDocs[docs.size() - 1] = ui;
    }

    public void eliminarDoc(int index) {
        if (index >= 0 && index < docs.size()) {
            for (int i = index; i < docs.size() - 1; i++) {
                datosDocs[i] = datosDocs[i + 1];
            }
            datosDocs[docs.size() - 1] = null;
            docs.remove(index);
        }
    }

    private String getBase64Data(UploadedFile file) {
        try {

            byte[] data = IOUtils.toByteArray(file.getInputstream());

            return "data:image/" + FilenameUtils.getExtension(file.getFileName()) + ";base64," + Base64.encodeToString(data, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }

    private List<UploadedImage> guardarDocumentoEscaneado() {
        List<UploadedImage> copiados = new ArrayList<>();
        for (int i = 0; i < docs.size(); i++) {
            UploadedFile uploadedFile = docs.get(i);
            try {
                InputStream inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_documentos");

                File destFile = new File(new File(uploadDir), UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName()));

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedImage image = datosDocs[i];
                image.setSavedFile(destFile);

                copiados.add(image);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return copiados;
    }

    public List<InfoBloque> creaListaBloques() {

        List<InfoBloque> lista = new ArrayList<>();

//        if (predio.getBloqueList().isEmpty()) {
//            return lista;
//        }

        InfoBloque datosBloque;

//        for (Bloque b : predio.getBloqueList()) {
//
//            datosBloque = new InfoBloque(
//                    b.getNumeroBloque(),
//                    b.getListaPisos().size(),
//                    b.getAreaTotal().floatValue(),
//                    variableService.obtenerValor("cat_bloque", "tipo_construccion", b.getTipoConstruccion()),
//                    variableService.obtenerOperacion("cat_bloque", "tipo_construccion", b.getTipoConstruccion()),
//                    b.getPorcientoConstruccion().floatValue(),
//                    b.getPorcientoConservacion().floatValue(),
//                    avaluosService.avaluoBloque(b)
//            );
//            lista.add(datosBloque);
//        }

        return lista;
    }

    public List<InfoInfraestructura> creaListaInfraestructura() {

        List<InfoInfraestructura> lista = new ArrayList<>();

        InfoInfraestructura datosInfra;
        ValorDiscreto valorDiscreto;
        float acum = 1;

//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_vias", predio.getTerreno().getInfVias());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Vías", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_agua_potable", predio.getTerreno().getInfAguaPotable());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Agua potable", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_alcantarillado_sanitario", predio.getTerreno().getInfAlcantarilladoSanitario());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Alcantarillado sanitario", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_alcantarillado_pluvial", predio.getTerreno().getInfAlcantarilladoPluvial());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Alcantarillado pluvial", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_energia_electrica", predio.getTerreno().getInfEnergiaElectrica());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Energía eléctrica", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_red_telefonica", predio.getTerreno().getInfRedTelefonica());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Red telefónica", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_transporte_publico", predio.getTerreno().getInfTransportePublico());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Transporte público", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_acera_bordillos", predio.getTerreno().getInfAceraBordillos());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Aceras y bordillos", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "localizacion", predio.getTerreno().getLocalizacion());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Localización", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "topografia", predio.getTerreno().getTopografia());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Topografía", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_recoleccion_basura", predio.getTerreno().getInfRecoleccionBasura());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Recolección de basura", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);
//
//        valorDiscreto = variableService.obtenerValores("cat_terreno", "inf_aseo_calles", predio.getTerreno().getInfAseoCalles());
//        acum *= valorDiscreto.getCoeficiente();
//        datosInfra = new InfoInfraestructura("Aseo de calles", valorDiscreto.getTexto(), valorDiscreto.getCoeficiente());
//        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Coeficiente de ajuste", " ", acum);
        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Valor de Zona Homogénea " + predio.getTerreno().getManzana().getZonaHomogenea().getCodigoZona(), "USD", predio.getTerreno().getManzana().getZonaHomogenea().getValor());
        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Valor Esquinero", "USD", predio.getTerreno().getManzana().getZonaHomogenea().getValorEsquinero());
        lista.add(datosInfra);

        datosInfra = new InfoInfraestructura("Valor Medianero", "USD", predio.getTerreno().getManzana().getZonaHomogenea().getValorMedianero());
        lista.add(datosInfra);

        return lista;
    }

    public boolean esFichaMadre() {
        return predioService.getEsFichaMadre(predio);
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public ImpuestoService getImpuestoService() {
        return impuestoService;
    }

    public void setImpuestoService(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    public ImpuestoPredialService getImpuestoPredialService() {
        return impuestoPredialService;
    }

    public void setImpuestoPredialService(ImpuestoPredialService impuestoPredialService) {
        this.impuestoPredialService = impuestoPredialService;
    }

    public AvaluosService getAvaluosService() {
        return avaluosService;
    }

    public void setAvaluosService(AvaluosService avaluosService) {
        this.avaluosService = avaluosService;
    }

  

    public ImpuestoPredial getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(ImpuestoPredial impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }

    public ImpuestoPredial getImpuestoPredialAnterior() {
        return impuestoPredialAnterior;
    }

    public void setImpuestoPredialAnterior(ImpuestoPredial impuestoPredialAnterior) {
        this.impuestoPredialAnterior = impuestoPredialAnterior;
    }

    public short getAnioEmision() {

        anioEmision = Util.ANIO_ACTUAL.shortValue();

        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

    public VariableService getVariableService() {
        return variableService;
    }

    public void setVariableService(VariableService variableService) {
        this.variableService = variableService;
    }

    public List<Reemision> getListaConfirmacion() {
        return listaConfirmacion;
    }

    public void setListaConfirmacion(List<Reemision> listaConfirmacion) {
        this.listaConfirmacion = listaConfirmacion;
    }

    public DeudaService getDeudaService() {
        return deudaService;
    }

    public void setDeudaService(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    public List<Integer> getDeudasMarcadas() {
        return deudasMarcadas;
    }

    public void setDeudasMarcadas(List<Integer> deudasMarcadas) {
        this.deudasMarcadas = deudasMarcadas;
    }

    public List<Deuda> getListaDeudas() {
        return listaDeudas;
    }

    public void setListaDeudas(List<Deuda> listaDeudas) {
        this.listaDeudas = listaDeudas;
    }

    public List<UploadedFile> getDocs() {
        return docs;
    }

    public void setDocs(List<UploadedFile> docs) {
        this.docs = docs;
    }

    public UploadedImage[] getDatosDocs() {
        return datosDocs;
    }

    public void setDatosDocs(UploadedImage[] datosDocs) {
        this.datosDocs = datosDocs;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public DatosAutorizacionService getAutorizacionService() {
        return autorizacionService;
    }

    public void setAutorizacionService(DatosAutorizacionService autorizacionService) {
        this.autorizacionService = autorizacionService;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public TipoDocumentoService getTipoDocumentoService() {
        return tipoDocumentoService;
    }

    public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
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

    public SelectItem getDocumentoSelect() {
        return documentoSelect;
    }

    public void setDocumentoSelect(SelectItem documentoSelect) {
        this.documentoSelect = documentoSelect;
    }

    public DatosAutorizacion getDatosAutorizacion() {
        return datosAutorizacion;
    }

    public void setDatosAutorizacion(DatosAutorizacion datosAutorizacion) {
        this.datosAutorizacion = datosAutorizacion;
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

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public PredioService getPredioService() {
        return predioService;
    }

    public void setPredioService(PredioService predioService) {
        this.predioService = predioService;
    }

//</editor-fold>
}
