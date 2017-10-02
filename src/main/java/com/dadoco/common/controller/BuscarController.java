/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.controller;

import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.SectorService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Dairon
 */
@Named(value = "buscarView")
@ApplicationScoped
public class BuscarController implements Serializable {

    @EJB
    private ConfigReader config;
    @EJB
    private ZonaService zonaService;
    @EJB
    private SectorService sectorService;
    
    private List<Sector> sectores;
    private List<Zona> zonas;
    
    private String provinciaCod;
    private String cantonCod;
    private String parroquia;
    private String zonaCod;
    private String sectorCod;

    @PostConstruct
    public void init(){
    
        provinciaCod = Util.provincia_por_defecto;
        cantonCod = Util.canton_por_defecto;
        parroquia = Util.parroquia_por_defecto;
        sectores = new ArrayList<Sector>();
        zonas = zonaService.findAll();
        
        if(!zonas.isEmpty())
            sectores = zonas.get(0).getSectorCollection();
    }
    
    
    
    public List<Sector> listaSectores(String zona){
        
        System.out.println("Valor de la zona: " + zona);
        ZonaPK pk = new ZonaPK();
        pk.setCodProvincia(provinciaCod);
        pk.setCodCanton(cantonCod);
        pk.setCodParroquia(parroquia);
        pk.setCodZona(zona);
        
        Zona z = zonaService.find(pk);
        
        sectores = new ArrayList<Sector>();
        
        if(z != null)
            sectores = z.getSectorCollection();
        
        return sectores;
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }
    

    public List<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(List<Sector> sectores) {
        this.sectores = sectores;
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

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
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

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    
}
