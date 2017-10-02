/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.admin.service.ZonificacionService;
import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.ParroquiaPK;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.SectorPK;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;

import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "sectorView")
@ViewScoped
public class SectorViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SectorViewController.class);

    @EJB
    private ConfigReader config;

    @EJB
    private ParroquiaService parroquiaService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;

    @EJB
    private ZonificacionService zonificacionService;

    private List<SelectItem> parroquias;
    private List<SelectItem> zonas;

    private List<Sector> sectores;
    private List<Sector> sectoresFiltrados;

    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;

    private Sector sectorSeleccionado;

    private boolean creando;
    private boolean editando;
    private String codigo;

    @PostConstruct
    public void init() {

        provinciaCod = Util.provincia_por_defecto;
        cantonCod = Util.canton_por_defecto;

        sectorSeleccionado = new Sector();

        parroquias = getParroquias();
        zonas = new ArrayList<SelectItem>();

        creando = false;
        editando = false;
        codigo = "";

    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ZonificacionService getZonificacionService() {
        return zonificacionService;
    }

    public void setZonificacionService(ZonificacionService zonificacionService) {
        this.zonificacionService = zonificacionService;
    }

    public ParroquiaService getParroquiaService() {
        return parroquiaService;
    }

    public void setParroquiaService(ParroquiaService parroquiaService) {
        this.parroquiaService = parroquiaService;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
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

    public List<SelectItem> getParroquias() {
        parroquias = new ArrayList<SelectItem>();
        List<Parroquia> ps = parroquiaService.findAll();
        for (Parroquia p : ps) {
            parroquias.add(new SelectItem(p.getParroquiaPK().getCodParroquia(), "" + p.getParroquiaPK().getCodParroquia() + "-" + p.getNombre()));
        }
        return parroquias;
    }

    public void setParroquias(List<SelectItem> parroquias) {
        this.parroquias = parroquias;
    }

    public List<SelectItem> getZonas() {
        if (parroquiaCod != null) {
            zonas = listaZonasPorParroquia(parroquiaCod);
        } else {
            zonas = new ArrayList<SelectItem>();
        }
        return zonas;
    }

    public void setZonas(List<SelectItem> zonas) {
        this.zonas = zonas;
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

    public List<Sector> getSectores() {
        sectores = sectorService.findAll();
        return sectores;
    }

    public void setSectores(List<Sector> sectores) {
        this.sectores = sectores;
    }

    public List<Sector> getSectoresFiltrados() {
        return sectoresFiltrados;
    }

    public void setSectoresFiltrados(List<Sector> sectoresFiltrados) {
        this.sectoresFiltrados = sectoresFiltrados;
    }

    public Sector getSectorSeleccionado() {
        return sectorSeleccionado;
    }

    public void setSectorSeleccionado(Sector sectorSeleccionado) {
        this.sectorSeleccionado = sectorSeleccionado;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

//</editor-fold>
    public void inicializarNuevo() {
        sectorSeleccionado = new Sector();
        sectorCod = null;
        parroquias = getParroquias();
        creando = true;
        editando = false;

    }

    public void cancelarCreacion() {
        init();
    }

    public void inicializarEdicion(String pkString) {

        String[] clave = pkString.split("-");

        if (clave.length == 5) {

            parroquiaCod = clave[2];
            zonaCod = clave[3];
            sectorCod = clave[4];

            SectorPK pk = new SectorPK(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod);

            sectorSeleccionado = sectorService.find(pk);

            editando = true;
            creando = false;
        } else {
            sectorSeleccionado = new Sector();
            editando = false;
            creando = false;
        }

    }

    public String crear() {
        try {

            SectorPK pk = new SectorPK();
            pk.setCodProvincia(provinciaCod);
            pk.setCodCanton(cantonCod);
            pk.setCodParroquia(parroquiaCod);
            pk.setCodZona(zonaCod);
            pk.setCodSector(sectorCod);

            sectorSeleccionado.setSectorPK(pk);

            RequestContext context = RequestContext.getCurrentInstance();
            Sector s = sectorService.find(pk);

            if (s != null) {
                context.update("datos-messages");
                JsfUtil.addErrorMessage("El sector ya se encuentra registrado.");
            } else {

                sectorService.create(sectorSeleccionado);
                //zonificacionService.crearManzana(manzanaSeleccionada, codZonaHomo, files);

                context.execute("PF('addDialog').hide();");
                // context.update("listado-form,messages,addDlg,table-list");
                JsfUtil.addInformationMessage("Sector creado", "Sector " + sectorSeleccionado.getNombre()+ " creada con éxito.");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }

        return "pretty:pretty:cat-crear-predio";
    }

    public void editar() {
        try {

            Subject subject = SecurityUtils.getSubject();
            String user = subject.getPrincipal().toString();

            SectorPK pk = new SectorPK();
            pk.setCodProvincia(provinciaCod);
            pk.setCodCanton(cantonCod);
            pk.setCodParroquia(parroquiaCod);
            pk.setCodZona(zonaCod);
            pk.setCodSector(sectorCod);

            sectorSeleccionado.setSectorPK(pk);
            RequestContext context = RequestContext.getCurrentInstance();

            sectorService.edit(sectorSeleccionado);

            //zonificacionService.editarManzana(manzanaSeleccionada, codZonaHomo, files);
            context.execute("PF('addDialog').hide();");
            //   context.update("listado-form,messages,addDlg,table-list");
            JsfUtil.addInformationMessage("Sector actualizado", "Sector " + sectorSeleccionado.toString() + " actualizado con éxito.");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void eliminar(String pkString) {

        String[] clave = pkString.split("-");

        if (clave.length == 5) {

            parroquiaCod = clave[2];
            zonaCod = clave[3];
            sectorCod = clave[4];
            try {
                SectorPK pk = new SectorPK(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod);
                sectorSeleccionado = sectorService.find(pk);
                sectorService.remove(sectorSeleccionado);

                RequestContext context = RequestContext.getCurrentInstance();

                context.execute("PF('addDialog').hide();");
                JsfUtil.addInformationMessage("Sector eliminado", "Sector " + sectorSeleccionado.getNombre()+ " eliminado con éxito.");

            } catch (Exception e) {
                JsfUtil.addErrorMessage(e.getMessage());
            }

        }

    }

    public List<SelectItem> listaZonasPorParroquia(String parroquia) {

        ParroquiaPK pk = new ParroquiaPK();
        pk.setCodProvincia(provinciaCod);
        pk.setCodCanton(cantonCod);
        pk.setCodParroquia(parroquia);

        Parroquia p = parroquiaService.find(pk);

        zonas = new ArrayList<SelectItem>();

        if (p != null) {
            List<Zona> zs = p.getZonaCollection();

            for (Zona z : zs) {
                zonas.add(new SelectItem(z.getZonaPK().getCodZona(), "" + z.getZonaPK().getCodZona() + "-" + z.getNombre()));
            }
        }

        return zonas;
    }

    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("form-form");

        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiP = (UIInput) components.findComponent("parroquia-select");
        String p = uiP.getLocalValue() == null ? ""
                : uiP.getLocalValue().toString();

        UIInput uiZ = (UIInput) components.findComponent("zona-select");
        String z = uiZ.getLocalValue() == null ? ""
                : uiZ.getLocalValue().toString();

        if (p.equals("")) {
            errores++;
            uiP.setValid(false);
        }
        if (z.equals("")) {
            errores++;
            uiZ.setValid(false);
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
}
