/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.controller.OpcionesBusquedaPredio;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.common.customFilters.LazyDataModelAdvance;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.lazyModel.LazyDataModel;
import com.dadoco.common.lazyModel.LazyDataModelContribuyente;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "contribuyenteAdminView")
@ViewScoped
public class ContribuyenteAdminViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ContribuyenteAdminViewController.class);

    @EJB
    private ContribuyenteService contribuyenteService;
    private List<Contribuyente> contribuyentes;
    private List<Contribuyente> contribuyentesFiltrados;
    private Contribuyente contribuyenteSeleccionado;

    private boolean creando;
    private boolean editando;
    private boolean activarRepresentanteLegal;
    private boolean activarNroDiscapacitado;
    private boolean activarNroArtesano;

    private boolean artesano;
    private boolean sinFinesLucros;
    private boolean discapacitado;
    private boolean llevaContabilidad;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private LazyDataModelAdvance<Contribuyente> lazyData;
    
    /**
     *
     */
    private JasperPrint jasper;

    @PostConstruct
    public void init() {
        setLazyData(new LazyDataModelAdvance<Contribuyente>(contribuyenteService));
        opcionesBusqueda = new OpcionesBusquedaPredio();
    }

    public void buscar() {
        String palabraClave = opcionesBusqueda.getPalabraClave();
        log.error("Clace: " + palabraClave);
    }
    
     /**
     * Este métod permite inicializar la exportación de un reporteque luego será
     * exportado a aun formato PDF o EXCEL
     */
    public void prepExportar() {
        try {
            Map parametros = new HashMap();
            String sql= this.getLazyData().getInfoLazyDataModel()[2].toString();
            int index=sql.indexOf("where");
            if(index!=-1){
                 parametros.put("sqlWhere",sql.subSequence(index, sql.length()));
            }else{
                 parametros.put("sqlWhere","");
            }
           
            this.inicializarReporte(parametros, "/admin/reportes/reporte_contribuyente.jasper",this.contribuyenteService.getConection());
        } catch (JRException ex) {
            Logger.getLogger(ContribuyenteAdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContribuyenteAdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void inicializarReporte(Map parametros, String ruta,Connection con) throws JRException {
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(ruta);
        jasper = JasperFillManager.fillReport(reportPath, parametros,con);
    }
    
     /**
     * Método para exportar reporte a PDF
     *
     * @throws JRException
     * @throws IOException
     */
    public void reportePDF() throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasper, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    /**
     * Método para exportar reporte a EXCEL
     *
     * @throws JRException
     * @throws IOException
     */
    public void reporteEXCEL() throws JRException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasper);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
        FacesContext.getCurrentInstance().responseComplete();
    }



    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public boolean isArtesano() {
        return artesano;
    }

    public void setArtesano(boolean artesano) {
        this.artesano = artesano;
    }

    public boolean isSinFinesLucros() {
        return sinFinesLucros;
    }

    public void setSinFinesLucros(boolean sinFinesLucros) {
        this.sinFinesLucros = sinFinesLucros;
    }

    public boolean isDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    public boolean isLlevaContabilidad() {
        return llevaContabilidad;
    }

    public void setLlevaContabilidad(boolean llevaContabilidad) {
        this.llevaContabilidad = llevaContabilidad;
    }

    public ContribuyenteService getContribuyenteService() {
        return contribuyenteService;
    }

    public void setContribuyenteService(ContribuyenteService contribuyenteService) {
        this.contribuyenteService = contribuyenteService;
    }

    public List<Contribuyente> getContribuyentes() {
//        contribuyentes = new ArrayList<Contribuyente>();
//        short val = 1;
//        Object[] params = {val};
//        contribuyentes = contribuyenteService.findByNamedQuery("Contribuyente.findAll", params);
        return contribuyentes;
    }

    public void setContribuyentes(List<Contribuyente> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }

    public List<Contribuyente> getContribuyentesFiltrados() {
        return contribuyentesFiltrados;
    }

    public void setContribuyentesFiltrados(List<Contribuyente> contribuyentesFiltrados) {
        this.contribuyentesFiltrados = contribuyentesFiltrados;
    }

    public Contribuyente getContribuyenteSeleccionado() {
        return contribuyenteSeleccionado;
    }

    public void setContribuyenteSeleccionado(Contribuyente contribuyenteSeleccionado) {
        this.contribuyenteSeleccionado = contribuyenteSeleccionado;
    }

    public boolean isCreando() {
        return creando;
    }

    public void setCreando(boolean creando) {
        this.creando = creando;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public boolean isActivarRepresentanteLegal() {
        return activarRepresentanteLegal;
    }

    public void setActivarRepresentanteLegal(boolean activarRepresentanteLegal) {
        this.activarRepresentanteLegal = activarRepresentanteLegal;
    }

    public boolean isActivarNroDiscapacitado() {
        return activarNroDiscapacitado;
    }

    public void setActivarNroDiscapacitado(boolean activarNroDiscapacitado) {
        this.activarNroDiscapacitado = activarNroDiscapacitado;
    }

    public boolean isActivarNroArtesano() {
        return activarNroArtesano;
    }

//</editor-fold>
    public void setActivarNroArtesano(boolean activarNroArtesano) {
        this.activarNroArtesano = activarNroArtesano;
    }

    public List<String> getTiposIdentificacion() {
        String[] valores = {"C", "R", "P"};
        return Arrays.asList(valores);
    }

    public void inicializarNuevo() {
        contribuyenteSeleccionado = new Contribuyente();
        creando = true;
        editando = false;
    }

    public void cancelarCreacionUsuario() {
        if (creando) {
            contribuyenteSeleccionado = null;
        }
        editando = false;
        creando = false;
    }

    public void inicializarEdicion(Integer id) {

        contribuyenteSeleccionado = contribuyenteService.find(id);

        log.error("Id del C seleccionado: " + contribuyenteSeleccionado.getId());

        editando = true;
        creando = false;

    }

    public void crearContribuyente() {

        try {

            if (artesano) {
                contribuyenteSeleccionado.setArtesano("S");
            }
            if (discapacitado) {
                contribuyenteSeleccionado.setDiscapacitado("S");
            }
            if (sinFinesLucros) {
                contribuyenteSeleccionado.setSinFinesLucro("S");
            }
            if (llevaContabilidad) {
                contribuyenteSeleccionado.setLlevaContabilidad("S");
            }

            contribuyenteSeleccionado.setFechaIngreso(new Date());
            contribuyenteSeleccionado.setNombre(contribuyenteSeleccionado.getNombre().toUpperCase());
            contribuyenteSeleccionado.setApellidoPaterno(contribuyenteSeleccionado.getApellidoPaterno().toUpperCase());
            contribuyenteSeleccionado.setApellidoMaterno(contribuyenteSeleccionado.getApellidoMaterno().toUpperCase());
            String nombreCompleto = contribuyenteSeleccionado.getApellidoPaterno() + " " + contribuyenteSeleccionado.getApellidoMaterno() + " " + contribuyenteSeleccionado.getNombre();
            contribuyenteSeleccionado.setNombreCompleto(nombreCompleto);
            contribuyenteService.guardar(contribuyenteSeleccionado);

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('CreateContribuyenteDialog').hide();");
            context.update("contribuyente-list-form,messages");
            JsfUtil.addInformationMessage("Contribuyente creado", "Contribuyente " + contribuyenteSeleccionado.getNombre() + " creado con éxito.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void editContribuyente() {
        try {

            contribuyenteSeleccionado.setNombre(contribuyenteSeleccionado.getNombre().toUpperCase());
            contribuyenteSeleccionado.setApellidoPaterno(contribuyenteSeleccionado.getApellidoPaterno().toUpperCase());
            contribuyenteSeleccionado.setApellidoMaterno(contribuyenteSeleccionado.getApellidoMaterno().toUpperCase());

            String nombreCompleto = contribuyenteSeleccionado.getApellidoPaterno() + " " + contribuyenteSeleccionado.getApellidoMaterno() + " " + contribuyenteSeleccionado.getNombre();
            contribuyenteSeleccionado.setNombreCompleto(nombreCompleto);
            contribuyenteService.edit(contribuyenteSeleccionado);
//            int i = 0;
//            for (Contribuyente c : contribuyentesFiltrados) {
//                if (Objects.equals(c.getId(), contribuyenteSeleccionado.getId())) {
//                    contribuyentesFiltrados.remove(c);
//                    contribuyentesFiltrados.add(i, contribuyenteSeleccionado);
//                }
//                i++;
//            }
            // contribuyentes = contribuyentesFiltrados;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('CreateContribuyenteDialog').hide();");
            context.update("contribuyente-list-form,messages");
            JsfUtil.addInformationMessage("Contribuyente Actualizado", "Contribuyente " + contribuyenteSeleccionado.getNombre() + " actualizado con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void deleteContribuyente(Integer id) {
        try {
            Contribuyente c = contribuyenteService.find(id);

            if (c != null) {
                c.setEstado((short) 0);
                contribuyenteService.edit(c);
                contribuyenteSeleccionado = new Contribuyente();
                JsfUtil.addInformationMessage("Contribuyente eliminado", "Contribyyente " + c.getNombre() + " " + c.getApellidoPaterno() + " eliminado con éxito.");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("contribuyente-list-form,messages");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void validarFormulario(ComponentSystemEvent event) {

        log.error("Entro a validar");

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("adicionarContribuyenteForm");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiNumero = (UIInput) components.findComponent("numero_identificacion");
        String p = uiNumero.getLocalValue() == null ? ""
                : uiNumero.getLocalValue().toString();

        UIInput uiNombre = (UIInput) components.findComponent("nombre_contribuyente");
        String z = uiNombre.getLocalValue() == null ? ""
                : uiNombre.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiNumero.setValid(false);
        }
        if (z.equals("")) {
            errores++;
            uiNombre.setValid(false);
        }

        errores += visitor.getInvalidFields();

        if (errores != 0) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("Existen errores en el formulario");
            fc.addMessage(null, msg);
        }

        fc.renderResponse();

    }

    public String cancelarModificacion() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public void CancelarAction() {

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('usuarioDialog').hide();");
    }

    public void selectDiscapacitado() {

        activarNroDiscapacitado = discapacitado;
    }

    public void selectArtesano() {

        activarNroArtesano = artesano;
    }

    /**
     * @return the lazyData
     */
    public LazyDataModelAdvance<Contribuyente> getLazyData() {
        if(lazyData==null){
            lazyData=new LazyDataModelAdvance<>(contribuyenteService);
        }
        return lazyData;
    }

    /**
     * @param lazyData the lazyData to set
     */
    public void setLazyData(LazyDataModelAdvance<Contribuyente> lazyData) {
        this.lazyData = lazyData;
    }

    
}
