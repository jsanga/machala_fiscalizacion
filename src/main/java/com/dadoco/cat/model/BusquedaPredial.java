/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Sistemas
 */
public class BusquedaPredial implements Serializable{
   
    private Date fIngreso;
    private Date fModificacion;
    private String propietario;
    private String cod_anterior;
    private String cod_actual;
    private String manzana;
    private double superficie;
    private double perimetro;
    private double area_construccion;
    private String uso_suelo;
    private String otros_construccion;
    private String niveles;
    private String estado;
    private String dominio;
    private String sitio;
    private String predio;
    private String geometria;
    //Campos Linderos
    private String lind_norte;
    private String lind_sur;
    private String lind_este;
    private String lind_oeste;
    private String lind_norte_long;
    private String lind_sur_long;
    private String lind_este_long;
    private String lind_oeste_long;
    private double ancho_via_pub;
    private double ancho_acera;
    //
    private String relevamiento;
    private String sup_relevamiento;
    private String c_calidad;
    private String digitalizacion;
    private String digitacion;
    private String sup_digitacion;
    private String usuario;
    private String fotoPredio;
    private String fotoBloque;
    
    public BusquedaPredial(){
        propietario="";
        cod_anterior="";
        cod_actual="";
        manzana="";
        uso_suelo="";
        otros_construccion="";
        niveles="";
        estado="";
        dominio="";
        sitio="";
        predio="";
        geometria="";
        lind_norte="";
        lind_sur="";
        lind_este="";
        lind_oeste="";
        lind_norte_long="";
        lind_sur_long="";
        lind_este_long="";
        lind_oeste_long="";
        relevamiento="";
        sup_relevamiento="";
        c_calidad="";
        digitalizacion="";
        digitacion="";
        sup_digitacion="";
        usuario="";
        fotoPredio="";
        fotoBloque="";
    }

    public Date getFIngreso() {
        return fIngreso;
    }

    public void setFIngreso(Date fIngreso) {
        this.fIngreso = fIngreso;
    }

    public Date getFModificacion() {
        return fModificacion;
    }

    public void setFModificacion(Date fModificacion) {
        this.fModificacion = fModificacion;
    }
    
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getCod_anterior() {
        return cod_anterior;
    }

    public void setCod_anterior(String cod_anterior) {
        this.cod_anterior = cod_anterior;
    }

    public String getCod_actual() {
        return cod_actual;
    }

    public void setCod_actual(String cod_actual) {
        this.cod_actual = cod_actual;
    }

    public String getManzana() {
        return manzana;
    }

    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getArea_construccion() {
        return area_construccion;
    }

    public void setArea_construccion(double area_construccion) {
        this.area_construccion = area_construccion;
    }

    public String getUso_suelo() {
        return uso_suelo;
    }

    public void setUso_suelo(String uso_suelo) {
        this.uso_suelo = uso_suelo;
    }

    public String getOtros_construccion() {
        return otros_construccion;
    }

    public void setOtros_construccion(String otros_construccion) {
        this.otros_construccion = otros_construccion;
    }

    public String getNiveles() {
        return niveles;
    }

    public void setNiveles(String niveles) {
        this.niveles = niveles;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }
    
    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public String getGeometria() {
        return geometria;
    }

    public void setGeometria(String geometria) {
        this.geometria = geometria;
    }
    
    public String getRelevamiento() {
        return relevamiento;
    }

    public void setRelevamiento(String relevamiento) {
        this.relevamiento = relevamiento;
    }

    public String getSup_relevamiento() {
        return sup_relevamiento;
    }

    public void setSup_relevamiento(String sup_relevamiento) {
        this.sup_relevamiento = sup_relevamiento;
    }

    public String getC_calidad() {
        return c_calidad;
    }

    public void setC_calidad(String c_calidad) {
        this.c_calidad = c_calidad;
    }

    public String getDigitalizacion() {
        return digitalizacion;
    }

    public void setDigitalizacion(String digitalizacion) {
        this.digitalizacion = digitalizacion;
    }

    public String getDigitacion() {
        return digitacion;
    }

    public void setDigitacion(String digitacion) {
        this.digitacion = digitacion;
    }

    public String getSup_digitacion() {
        return sup_digitacion;
    }

    public void setSup_digitacion(String sup_digitacion) {
        this.sup_digitacion = sup_digitacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFotoPredio() {
        return fotoPredio;
    }

    public void setFotoPredio(String fotoPredio) {
        this.fotoPredio = fotoPredio;
    }

    public String getFotoBloque() {
        return fotoBloque;
    }

    public void setFotoBloque(String fotoBloque) {
        this.fotoBloque = fotoBloque;
    }

    public double getPerimetro() {
        return perimetro;
    }

    public void setPerimetro(double perimetro) {
        this.perimetro = perimetro;
    }

    public String getLind_norte() {
        return lind_norte;
    }

    public void setLind_norte(String lind_norte) {
        this.lind_norte = lind_norte;
    }

    public String getLind_sur() {
        return lind_sur;
    }

    public void setLind_sur(String lind_sur) {
        this.lind_sur = lind_sur;
    }

    public String getLind_este() {
        return lind_este;
    }

    public void setLind_este(String lind_este) {
        this.lind_este = lind_este;
    }

    public String getLind_oeste() {
        return lind_oeste;
    }

    public void setLind_oeste(String lind_oeste) {
        this.lind_oeste = lind_oeste;
    }

    public String getLind_norte_long() {
        return lind_norte_long;
    }

    public void setLind_norte_long(String lind_norte_long) {
        this.lind_norte_long = lind_norte_long;
    }

    public String getLind_sur_long() {
        return lind_sur_long;
    }

    public void setLind_sur_long(String lind_sur_long) {
        this.lind_sur_long = lind_sur_long;
    }

    public String getLind_este_long() {
        return lind_este_long;
    }

    public void setLind_este_long(String lind_este_long) {
        this.lind_este_long = lind_este_long;
    }

    public String getLind_oeste_long() {
        return lind_oeste_long;
    }

    public void setLind_oeste_long(String lind_oeste_long) {
        this.lind_oeste_long = lind_oeste_long;
    }

    public double getAncho_via_pub() {
        return ancho_via_pub;
    }

    public void setAncho_via_pub(double ancho_via_pub) {
        this.ancho_via_pub = ancho_via_pub;
    }

    public double getAncho_acera() {
        return ancho_acera;
    }

    public void setAncho_acera(double ancho_acera) {
        this.ancho_acera = ancho_acera;
    }
    
}
