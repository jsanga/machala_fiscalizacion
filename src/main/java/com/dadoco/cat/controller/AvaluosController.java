/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.audit.service.AuditService;
import com.dadoco.cat.model.Avaluo;
import com.dadoco.models.cat.AvaluoTerrenoModel;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.service.AvaluosService;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.lazyModel.PrediosLazy;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.annotation.PostConstruct;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Joao Sanga
 */
@Named(value = "avaluosView")
@ViewScoped
public class AvaluosController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AvaluosController.class);

    @EJB
    private PredioService predioService;
    @EJB
    private AvaluosService avaluosService;
    @EJB
    private ManagerService aclServices;
    @EJB
    private AuditService auditServices;

    private PrediosLazy prediosLazy;
    private Integer tipoPredio, tipoCalculoMasivo;
    private Predio predioSeleccionado;
    private AvaluoTerrenoModel avaluo;
    private List<AvaluoTerrenoModel> avaluosMasivos;
    private List<Predio> prediosList;
    private Integer iteracion;
    private Double prediosProcesados, prediosProcesados2;
    private Boolean terminado;
    private Integer renderTab;
    private Double tiempoEscatimado;
    private List<Sector> sectoresList;
    private Subject subject;

    @PostConstruct
    public void init() {
        try {
            subject = SecurityUtils.getSubject();
        
            prediosProcesados = 0.0;
            prediosList = null;
            predioSeleccionado = null;
            tipoCalculoMasivo = 1;
            tipoPredio = 1;
            terminado = false;
            changeList();
            renderTab = 1;
            sectoresList = aclServices.findAll(Sector.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método de actualización de sectores
    public void recalculo(){
        try{
            avaluosService.recalcularFactoresDeSectores();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void generarAvaluos(Predio p) {
        try {
            this.predioSeleccionado = p;
            avaluo = avaluosService.avaluoTerrenoNew(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generarAvaluoMasivamente() {
        switch (tipoCalculoMasivo) {
            case 1:
                this.generarAvaluosTerrenoMasivamente();
                break;
            case 2:
                break;
            case 3:
                this.generarAvaluosTerrenoMasivamente();
                break;
        }
    }

    public void generarAvaluosTerrenoMasivamente() {
        try {
            avaluosMasivos = new ArrayList();
            Integer cont;
            Date d = new Date(), d2;
            long starttime = d.getTime();
            
            this.iteracion = 1;
            Predio p;
            List<Integer> ids;
            ids = aclServices.findListByParameters("SELECT p.id FROM Predio p WHERE p.tipoPredio = true AND p.estado = 1", new String[]{}, new Object[]{});

            cont = ids.size();
            for (Integer i : ids) {
                p = aclServices.getEm().find(Predio.class, i);
                avaluo = avaluosService.avaluoTerrenoNew(p);
                avaluosMasivos.add(avaluo);
                prediosProcesados = iteracion*100/cont < 1 ? 0 : (double)Math.round(iteracion*100)/cont;
                iteracion++;
            }
            
            d2 = new Date();
            long endtime = d2.getTime();

            this.tiempoEscatimado = (double)Math.round(((Long)((endtime - starttime) / 60000)).doubleValue()*100)/cont;
            terminado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Transactional
    public void guardarDatos(){
        try{
            Avaluo av;
            List<Avaluo> temp;
            Predio p = null;
            Integer cont;
            Subject user = SecurityUtils.getSubject();

            cont = avaluosMasivos.size();
            this.iteracion = 1;
            
            for(AvaluoTerrenoModel t : avaluosMasivos){
                
                p = aclServices.getEm().find(Predio.class, t.getIdPredio());
                        
                av = new Avaluo();
                av.setAvaluoConstruccion(t.getAvaluoTerreno());
                av.setAvaluoTerreno(0.0);
                av.setAvaluoTotal(0.0);
                av.setEstado((short)1);
                av.setFactorTerrenoSuperficie(t.getSuperficie());
                av.setFactorTerrenoZonaHomogenea(t.getValorZonaHomogenea());
                av.setFactorTerrenoTotal(t.getFactorTotal());
                av.setFechaIngreso(new Date());
                av.setPredio(p);
                av.setAnio(Util.ANIO_ACTUAL.shortValue());
                av.setUsuarioIngreso(user.getPrincipal().toString());
                
                temp = aclServices.findListByParameters("SELECT av FROM Avaluo av WHERE av.predio = :predio AND av.anio = :anio AND av.estado = 1", new String[]{"predio","anio"}, new Object[]{p, Util.ANIO_ACTUAL.shortValue()});
                
                for(Avaluo a : temp){
                    a.setEstado((short)0);
                    aclServices.getEm().merge(a);
                }                
                prediosProcesados2 = iteracion*100/cont < 1 ? 0 : (double)Math.round(iteracion*100)/cont;
                aclServices.getEm().persist(av);
                auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - GENERACIÓN DE AVALÚOS - PREDIO: "+p.getClaveCatastral());
            }
            
            aclServices.getEm().flush();
            JsfUti.messageInfo(null, "Info", "Datos guardados correctamente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void changeList() {
        try {
            if (tipoPredio == 1) {
                prediosLazy = new PrediosLazy(true);
            }
            if (tipoPredio == 2) {
                prediosLazy = new PrediosLazy(false);
            }
            if (tipoPredio == 3) {
                prediosLazy = new PrediosLazy();
            }
            this.predioSeleccionado = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avaluoPredio() {

        List<Predio> predios = predioService.findAll();

        Date d = new Date();
        long starttime = d.getTime();
        log.error("INIT: " + starttime);

        float acum = 0;
        long cont = 0;

        for (Predio p : predios) {
//            acum += avaluosService.avaluoPredio(p);
            cont++;
            log.error("Predio: " + cont);
        }

        Date d2 = new Date();
        long endtime = d2.getTime();
        
        //log.error("DIFF: " + ((endtime - starttime) / 1000) + " secs " + ((endtime - starttime) % 1000) + " milisecs");
    }

    public PrediosLazy getPrediosLazy() {
        return prediosLazy;
    }

    public void setPrediosLazy(PrediosLazy prediosLazy) {
        this.prediosLazy = prediosLazy;
    }

    public Integer getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(Integer tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    public Predio getPredioSeleccionado() {
        return predioSeleccionado;
    }

    public void setPredioSeleccionado(Predio predioSeleccionado) {
        this.predioSeleccionado = predioSeleccionado;
    }

    public AvaluoTerrenoModel getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(AvaluoTerrenoModel avaluo) {
        this.avaluo = avaluo;
    }

    public Integer getTipoCalculoMasivo() {
        return tipoCalculoMasivo;
    }

    public void setTipoCalculoMasivo(Integer tipoCalculoMasivo) {
        this.tipoCalculoMasivo = tipoCalculoMasivo;
    }

    public List<Predio> getPrediosList() {
        return prediosList;
    }

    public void setPrediosList(List<Predio> prediosList) {
        this.prediosList = prediosList;
    }

    public Double getPrediosProcesados() {
        return prediosProcesados;
    }

    public void setPrediosProcesados(Double prediosProcesados) {
        this.prediosProcesados = prediosProcesados;
    }

    public Boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(Boolean terminado) {
        this.terminado = terminado;
    }

    public Integer getIteracion() {
        return iteracion;
    }

    public void setIteracion(Integer iteracion) {
        this.iteracion = iteracion;
    }

    public Integer getRenderTab() {
        return renderTab;
    }

    public void setRenderTab(Integer renderTab) {
        this.renderTab = renderTab;
    }

    public Double getTiempoEscatimado() {
        return tiempoEscatimado;
    }

    public void setTiempoEscatimado(Double tiempoEscatimado) {
        this.tiempoEscatimado = tiempoEscatimado;
    }

    public Double getPrediosProcesados2() {
        return prediosProcesados2;
    }

    public void setPrediosProcesados2(Double prediosProcesados2) {
        this.prediosProcesados2 = prediosProcesados2;
    }

    public List<Sector> getSectoresList() {
        return sectoresList;
    }

    public void setSectoresList(List<Sector> sectoresList) {
        this.sectoresList = sectoresList;
    }

}
