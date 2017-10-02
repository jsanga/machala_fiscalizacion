/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.rectificar;

import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.controller.OpcionesBusquedaPredio;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.SectorPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.ManzanaService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.TerrenoService;
import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FlowEvent;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "rectificarClaveView")
@ViewScoped
public class RectificarClaveCatastralController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RectificarClaveCatastralController.class);
    @EJB
    private ConfigReader config;
    @EJB
    private CatastroService catastroService;
    @EJB
    private TerrenoService terrenoService;
    @EJB
    private PredioService predioService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private TipoDocumentoService tipoDocumentoService;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    @EJB
    private ManzanaService manzanaService;

    private DatosAutorizacion datosAutorizacion;
    private List<SelectItem> tiposDocumentos;
    private Long idTipoDocumento;

    private OpcionesBusquedaPredio opcionesBusqueda;

    private Terreno terreno;
    private Predio predio;
    private Manzana manzana;
    private Sector sector;
    private Zona zona;

    private String nuevoCodigoSolar;
    private String nuevoCodigoPH;
    private String nuevoCodigoParroquia;
    private String oldCodigoSolar;
    private String oldCodigoPH;
    private String oldCodigoParroquia;

    @PostConstruct
    public void init() {

        Subject subject = SecurityUtils.getSubject();

        opcionesBusqueda = new OpcionesBusquedaPredio();
        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);
        opcionesBusqueda.setEjecutandoAccion(false);
        datosAutorizacion = new DatosAutorizacion();
        predio = new Predio();
        terreno = new Terreno();
        manzana = new Manzana();
        sector = new Sector();
        zona = new Zona();
    }

    public void buscarManzana() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();

        try {
            manzana = catastroService.obtenerManzana(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod);

            if (manzana == null) {
                JsfUtil.addErrorMessage(String.format("Manzana con clave catastral %d-%d-%d no encontrada.",
                        zonaCod, sectorCod, manzanaCod));
            } else {
                opcionesBusqueda.setEjecutandoAccion(true);

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public void buscarSector() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();

        try {
            sector = catastroService.obtenerSector(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod);

            if (manzana == null) {
                JsfUtil.addErrorMessage(String.format("Sector con clave catastral %d-%d no encontrada.",
                        zonaCod, sectorCod));

            } else {
                opcionesBusqueda.setEjecutandoAccion(true);

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public void buscarZona() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();

        try {
            sector = catastroService.obtenerSector(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod);

            if (manzana == null) {
                JsfUtil.addErrorMessage(String.format("Sector con clave catastral %d-%d no encontrado.",
                        zonaCod, sectorCod));

            } else {
                opcionesBusqueda.setEjecutandoAccion(true);

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
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
//                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d no encontrado",
//                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod));
            } else {

                oldCodigoSolar = solarCod;
               // oldCodigoPH = phCod;
                oldCodigoParroquia = parroquiaCod;
                opcionesBusqueda.setEjecutandoAccion(true);

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public void buscarPredioPH() {

        String provinciaCod = opcionesBusqueda.getProvinciaCod();
        String cantonCod = opcionesBusqueda.getCantonCod();
        String parroquiaCod = opcionesBusqueda.getParroquiaCod();
        String zonaCod = opcionesBusqueda.getZonaCod();
        String sectorCod = opcionesBusqueda.getSectorCod();
        String manzanaCod = opcionesBusqueda.getManzanaCod();
        String solarCod = opcionesBusqueda.getSolarCod();
       // short phCod = opcionesBusqueda.getPhCod();

        try {

//            if (phCod != 0) {
           // predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);

            if (predio == null) {
//                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %2d-%2d-%3d-%2d-%2d no encontrado",
//                        zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//                    init();
//                    return;
            } else {

               // oldCodigoPH = phCod;
                opcionesBusqueda.setEjecutandoAccion(true);

            }
//            } else {
//                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %2d-%2d-%3d-%2d-%2d no es una Propiedad Horizontal",
//                        zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//                init();
//                return;
//            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }

    public String ejecutarOperacion(boolean accion) {

        if (accion) {
            return cambiarClaveSolar();
        } else {
            return cambiarClavePH();
        }
    }

    public String cambiarClaveSolar() {

        Subject subject = SecurityUtils.getSubject();

        String usuar = subject.getPrincipal().toString();

        try {
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(usuar);

            TerrenoPK pk = new TerrenoPK(predio.getTerreno().getManzana().getManzanaPK(), nuevoCodigoSolar);

            Terreno nuevo = terrenoService.find(pk);

            if (nuevo == null) {

                if (nuevoCodigoSolar == oldCodigoSolar) {
                    JsfUtil.addInformationMessage("No hay cambios", "El codigo " + nuevoCodigoSolar + " es el mismo.");
                    init();
                } else {
                    catastroService.actualizarCodigoSolar(predio.getTerreno().getTerrenoPK(), nuevoCodigoSolar);
                    JsfUtil.addInformationMessage("Predio Actualizado", "Predio " + predio.getClaveCatastral() + "actualizado correctamente.");
                }
            } else {
                JsfUtil.addInformationMessage("Código existente", "El codigo " + predio.getTerreno().getManzana().getManzanaPK().toString() + "-" + nuevoCodigoSolar + " se ecuentra registrado.");
                init();
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        opcionesBusqueda.setEjecutandoAccion(false);

        return "pretty:cat-clave-catastral-solar";
    }

    public String cambiarClaveParroquia() {

        Subject subject = SecurityUtils.getSubject();

        String usuar = subject.getPrincipal().toString();

        try {
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(usuar);

            ZonaPK zonaPK = new ZonaPK(opcionesBusqueda.getProvinciaCod(), opcionesBusqueda.getCantonCod(), nuevoCodigoParroquia, opcionesBusqueda.getZonaCod());

            zona = zonaService.find(zonaPK);

            if (zona != null) {

                SectorPK sectorPK = new SectorPK(opcionesBusqueda.getProvinciaCod(), opcionesBusqueda.getCantonCod(), nuevoCodigoParroquia, opcionesBusqueda.getZonaCod(), opcionesBusqueda.getSectorCod());

                sector = sectorService.find(sectorPK);

                if (sector != null) {

                    ManzanaPK manzanaPK = new ManzanaPK(opcionesBusqueda.getProvinciaCod(), opcionesBusqueda.getCantonCod(), nuevoCodigoParroquia, opcionesBusqueda.getZonaCod(), opcionesBusqueda.getSectorCod(), opcionesBusqueda.getManzanaCod());

                    manzana = manzanaService.find(manzanaPK);

                    if (manzana != null) {
                        TerrenoPK pk = new TerrenoPK(predio.getTerreno().getTerrenoPK(), nuevoCodigoParroquia);

                        Terreno nuevo = terrenoService.find(pk);

                        if (nuevo == null) {

                            if (nuevoCodigoParroquia == oldCodigoParroquia) {
                                JsfUtil.addInformationMessage("No hay cambios", "El codigo de la parroquia " + nuevoCodigoParroquia + " es el mismo.");
                                init();
                            } else {
                                catastroService.actualizarCodigoParroquia(predio.getTerreno().getTerrenoPK(), nuevoCodigoParroquia);
                                JsfUtil.addInformationMessage("Predio Actualizado", "Predio " + predio.getClaveCatastral() + "actualizado correctamente.");
                            }
                        } else {
                            String clave = ""; //pk.getCodParroquia() + "-" + pk.getCodZona() + "-" + pk.getCodSector() + "-" + pk.getCodSolar() + predio.getPropiedadHorizontal();
                            JsfUtil.addInformationMessage("Código existente", "El codigo " + clave + " se ecuentra registrado.");
                        }

                    } else {
                        JsfUtil.addInformationMessage("Error", "La Manzana " + nuevoCodigoParroquia + "-" + manzanaPK.getCodZona() + "-" + manzanaPK.getCodSector() + "-" + manzanaPK.getCodManzana() + " no se ecuentra registrada.");
                    }

                } else {
                    JsfUtil.addInformationMessage("Error", "El Sector " + nuevoCodigoParroquia + "-" + sectorPK.getCodZona() + "-" + sectorPK.getCodSector() + " no se ecuentra registrado.");
                }

            } else {
                JsfUtil.addInformationMessage("Error", "La zona " + nuevoCodigoParroquia + "-" + zonaPK.getCodZona() + " no se ecuentra registrada.");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        opcionesBusqueda.setEjecutandoAccion(false);

        return "pretty:cat-clave-catastral-solar";
    }

    public String cambiarClavePH() {

        Subject subject = SecurityUtils.getSubject();

        String usuar = subject.getPrincipal().toString();

        try {
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(usuar);

            String parroquia = predio.getTerreno().getTerrenoPK().getCodParroquia();
            String zona = predio.getTerreno().getTerrenoPK().getCodZona();
            String sector = predio.getTerreno().getTerrenoPK().getCodSector();
            String manzana = predio.getTerreno().getTerrenoPK().getCodManzana();
            String solar = predio.getTerreno().getTerrenoPK().getCodSolar();

           // Predio p = catastroService.obtenerPredio((short) 24, (short) 3, parroquia, zona, sector, manzana, solar, nuevoCodigoPH);

//            if (p == null) {
//
//                if (nuevoCodigoPH == oldCodigoPH) {
//                    JsfUtil.addInformationMessage("No hay cambios", "El codigo PH " + nuevoCodigoPH + " es el mismo.");
//                    init();
//                } else {
//                    //predio.setPropiedadHorizontal(nuevoCodigoPH);
//                    catastroService.actualizarCodigoPH(predio);
//                    JsfUtil.addInformationMessage("Predio Actualizado", "Predio " + predio.getClaveCatastral() + "actualizado correctamente.");
//                }
//            } else {
//                JsfUtil.addInformationMessage("Código existente", "El codigo " + predio.getTerreno().getTerrenoPK().getClave() + "-" + nuevoCodigoPH + " se ecuentra registrado.");
//                init();
//            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());

        }

        opcionesBusqueda.setEjecutandoAccion(false);

        return "pretty:cat-clave-catastral-ph";
    }

    public String cancelar() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public Manzana getManzana() {
        return manzana;
    }

    public void setManzana(Manzana manzana) {
        this.manzana = manzana;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public TerrenoService getTerrenoService() {
        return terrenoService;
    }

    public void setTerrenoService(TerrenoService terrenoService) {
        this.terrenoService = terrenoService;
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

    public ManzanaService getManzanaService() {
        return manzanaService;
    }

    public void setManzanaService(ManzanaService manzanaService) {
        this.manzanaService = manzanaService;
    }

    public String getNuevoCodigoSolar() {
        return nuevoCodigoSolar;
    }

    public void setNuevoCodigoSolar(String nuevoCodigoSolar) {
        this.nuevoCodigoSolar = nuevoCodigoSolar;
    }

    public String getNuevoCodigoPH() {
        return nuevoCodigoPH;
    }

    public void setNuevoCodigoPH(String nuevoCodigoPH) {
        this.nuevoCodigoPH = nuevoCodigoPH;
    }

    public String getNuevoCodigoParroquia() {
        return nuevoCodigoParroquia;
    }

    public void setNuevoCodigoParroquia(String nuevoCodigoParroquia) {
        this.nuevoCodigoParroquia = nuevoCodigoParroquia;
    }

    public String getOldCodigoSolar() {
        return oldCodigoSolar;
    }

    public void setOldCodigoSolar(String oldCodigoSolar) {
        this.oldCodigoSolar = oldCodigoSolar;
    }

    public String getOldCodigoPH() {
        return oldCodigoPH;
    }

    public void setOldCodigoPH(String oldCodigoPH) {
        this.oldCodigoPH = oldCodigoPH;
    }

    public String getOldCodigoParroquia() {
        return oldCodigoParroquia;
    }

    public void setOldCodigoParroquia(String oldCodigoParroquia) {
        this.oldCodigoParroquia = oldCodigoParroquia;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
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
        tiposDocumentos = new ArrayList<SelectItem>();
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

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
