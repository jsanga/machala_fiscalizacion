/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

/**
 *
 * @author dfcalderio
 */
public class OpcionesBusquedaPredio {

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

    private String zonaAnterior;
    private String sectorAnterior;
    private String manzanaAnterior;
    private String solarAnterior;
    private String palabraClave;
    private String claveAnterior;

    private boolean buscando;
    private boolean ejecutandoAccion;

    private boolean cambiandoCodigo;
    private short nuevoCodigo;

    public OpcionesBusquedaPredio() {
        buscando = false;
        ejecutandoAccion = false;
        provinciaCod="07";
        cantonCod="01";
        parroquiaCod = "00";
        zonaCod = "00";
        sectorCod = "00";
        manzanaCod = "000";
        solarCod = "000";
        bloqueCod = "000";
        phvCod = "000";
        phhCod = "000";
    }

    public String getZonaAnterior() {
        return zonaAnterior;
    }

    public void setZonaAnterior(String zonaAnterior) {
        this.zonaAnterior = zonaAnterior;
    }

    public String getSectorAnterior() {
        return sectorAnterior;
    }

    public void setSectorAnterior(String sectorAnterior) {
        this.sectorAnterior = sectorAnterior;
    }

    public String getManzanaAnterior() {
        return manzanaAnterior;
    }

    public void setManzanaAnterior(String manzanaAnterior) {
        this.manzanaAnterior = manzanaAnterior;
    }

    public String getSolarAnterior() {
        return solarAnterior;
    }

    public void setSolarAnterior(String solarAnterior) {
        this.solarAnterior = solarAnterior;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public boolean isBuscando() {
        return buscando;
    }

    public void setBuscando(boolean buscando) {
        this.buscando = buscando;
    }

    public boolean isEjecutandoAccion() {
        return ejecutandoAccion;
    }

    public void setEjecutandoAccion(boolean ejecutandoAccion) {
        this.ejecutandoAccion = ejecutandoAccion;
    }

    public boolean isCambiandoCodigo() {
        return cambiandoCodigo;
    }

    public void setCambiandoCodigo(boolean cambiandoCodigo) {
        this.cambiandoCodigo = cambiandoCodigo;
    }

    public short getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(short nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
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

    public String getClaveAnterior() {
        return claveAnterior;
    }

    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }
    
   /**
    * Metodo que permite determinar si se esta realizando una busqueda completa
    * @return 
    */
    public boolean getBusquedaCompleta(){
        if(parroquiaCod.equals("0") && zonaCod.equals("0") && sectorCod.equals("0") && manzanaCod.equals("0") && solarCod.equals("0") && phhCod.equals("0") && phvCod.equals("0")){
            return true;
        }
        return false;
    }

}
