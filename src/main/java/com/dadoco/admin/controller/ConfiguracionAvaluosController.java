/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.cat.service.ZonaHomogeneaService;
import com.dadoco.common.service.ValorDiscretoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Named(value = "configuracionAvaluosView")
@SessionScoped
public class ConfiguracionAvaluosController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConfiguracionAvaluosController.class);

    @EJB
    VariableService variablesService;
    @EJB
    ValorDiscretoService valorDiscretoService;
    @EJB
    ZonaHomogeneaService zonaHomogeneaService;

    short anioEmision;
//    float bandaImpositiva;
    float salarioBasico;
    float tipo0, tipo1, tipo2, tipo3, tipo4, tipo5, tipo6, tipo7, tipo8, tipo9, tipo10, tipo11, tipo12, tipo13, tipo14, tipo15, tipo16, tipo17, tipo18, tipo19;
    float zona1, zona2, zona3, zona4, zona5, zona6, zona7, zona8, zona9, zona10, zona11, zona12, zona13, zona14, zona15;

    @PostConstruct
    public void init() {
        anioEmision = (short)(Util.ANIO_ACTUAL +(short)1);
        salarioBasico = 0;
//        bandaImpositiva = 0;

        tipo0 = tipo1 = tipo2 = tipo3 = tipo4 = tipo5 = tipo6 = tipo7 = tipo8 = tipo9 = tipo10 = tipo11 = tipo12 = tipo13 = tipo14 = tipo15 = tipo16 = tipo17 = tipo18 = tipo19
                = zona1 = zona2 = zona3 = zona4 = zona5 = zona6 = zona7 = zona8 = zona9 = zona10 = zona11 = zona12 = zona13 = zona14 = zona15 = 0;
    }

    public short getAnioEmision() {

        anioEmision = (short)(Util.ANIO_ACTUAL +(short)1);
        return anioEmision;
    }

    public void setAnioEmision(short anioEmision) {
        this.anioEmision = anioEmision;
    }

//    public float getBandaImpositiva() {
//
//        bandaImpositiva = (float) variablesService.obtenerCoeficiente("datos_configuracion", "banda_impositiva", anioEmision);
//
//        if (bandaImpositiva == -1) {
//            bandaImpositiva = 0;
//
//            ValorDiscreto banda = new ValorDiscreto(69, anioEmision);
//            banda.setTexto("Banda Impositiva");
//            valorDiscretoService.create(banda);
//        }
//        return bandaImpositiva;
//    }
//
//    public void setBandaImpositiva(float bandaImpositiva) {
//        this.bandaImpositiva = bandaImpositiva;
//    }
//    public float getTipo0() {
//        tipo0 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 0);
//
//        return tipo0;
//    }
//
//    public void setTipo0(float tipo) {
//        this.tipo0 = tipo;
//    }
//
//    public float getTipo1() {
//        tipo1 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 1);
//
//        return tipo1;
//    }
//
//    public void setTipo1(float tipo) {
//        this.tipo1 = tipo;
//    }
//
//    public float getTipo2() {
//        tipo2 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 2);
//
//        return tipo2;
//    }
//
//    public void setTipo2(float tipo) {
//        this.tipo2 = tipo;
//    }
//
//    public float getTipo3() {
//        tipo3 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 3);
//
//        return tipo3;
//    }
//
//    public void setTipo3(float tipo3) {
//        this.tipo3 = tipo3;
//    }
//
//    public float getTipo4() {
//        tipo4 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 4);
//
//        return tipo4;
//    }
//
//    public void setTipo4(float tipo4) {
//        this.tipo4 = tipo4;
//    }
//
//    public float getTipo5() {
//        tipo5 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 5);
//
//        return tipo5;
//    }
//
//    public void setTipo5(float tipo5) {
//        this.tipo5 = tipo5;
//    }
//
//    public float getTipo6() {
//        tipo6 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 6);
//
//        return tipo6;
//    }
//
//    public void setTipo6(float tipo6) {
//        this.tipo6 = tipo6;
//    }
//
//    public float getTipo7() {
//        tipo7 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 7);
//
//        return tipo7;
//    }
//
//    public void setTipo7(float tipo7) {
//        this.tipo7 = tipo7;
//    }
//
//    public float getTipo8() {
//        tipo8 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 8);
//
//        return tipo8;
//    }
//
//    public void setTipo8(float tipo8) {
//        this.tipo8 = tipo8;
//    }
//
//    public float getTipo9() {
//        tipo9 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 9);
//
//        return tipo9;
//    }
//
//    public void setTipo9(float tipo9) {
//        this.tipo9 = tipo9;
//    }
//
//    public float getTipo10() {
//        tipo10 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 10);
//
//        return tipo10;
//    }
//
//    public void setTipo10(float tipo10) {
//        this.tipo10 = tipo10;
//    }
//
//    public float getTipo11() {
//        tipo11 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 11);
//
//        return tipo11;
//    }
//
//    public void setTipo11(float tipo11) {
//        this.tipo11 = tipo11;
//    }
//
//    public float getTipo12() {
//        tipo12 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 12);
//
//        return tipo12;
//    }
//
//    public void setTipo12(float tipo12) {
//        this.tipo12 = tipo12;
//    }
//
//    public float getTipo13() {
//        tipo13 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 13);
//
//        return tipo13;
//    }
//
//    public void setTipo13(float tipo13) {
//        this.tipo13 = tipo13;
//    }
//
//    public float getTipo14() {
//        tipo14 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 14);
//
//        return tipo14;
//    }
//
//    public void setTipo14(float tipo14) {
//        this.tipo14 = tipo14;
//    }
//
//    public float getTipo15() {
//        tipo15 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 15);
//        return tipo15;
//    }
//
//    public void setTipo15(float tipo15) {
//        this.tipo15 = tipo15;
//    }
//
//    public float getTipo16() {
//        tipo16 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 16);
//        return tipo16;
//    }
//
//    public void setTipo16(float tipo16) {
//        this.tipo16 = tipo16;
//    }
//
//    public float getTipo17() {
//        tipo17 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 17);
//        return tipo17;
//    }
//
//    public void setTipo17(float tipo17) {
//        this.tipo17 = tipo17;
//    }
//
//    public float getTipo18() {
//        tipo18 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 18);
//        return tipo18;
//    }
//
//    public void setTipo18(float tipo18) {
//        this.tipo18 = tipo18;
//    }
//
//    public float getTipo19() {
//        tipo19 = variablesService.obtenerOperacion("cat_bloque", "tipo_construccion", (short) 19);
//        return tipo19;
//    }
//
//    public void setTipo19(float tipo19) {
//        this.tipo19 = tipo19;
//    }
//
//    public float getZona1() {
//        zona1 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 1).get(0).getValor();
//
//        return zona1;
//    }
//
//    public ValorDiscretoService getValorDiscretoService() {
//        return valorDiscretoService;
//    }
//
//    public void setValorDiscretoService(ValorDiscretoService valorDiscretoService) {
//        this.valorDiscretoService = valorDiscretoService;
//    }
//
//    public void setZona1(float zona1) {
//        this.zona1 = zona1;
//    }
//
//    public float getZona2() {
//        zona2 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 2).get(0).getValor();
//
//        return zona2;
//    }
//
//    public void setZona2(float zona2) {
//        this.zona2 = zona2;
//    }
//
//    public float getZona3() {
//        zona3 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 3).get(0).getValor();
//
//        return zona3;
//    }
//
//    public void setZona3(float zona3) {
//        this.zona3 = zona3;
//    }
//
//    public float getZona4() {
//        zona4 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 4).get(0).getValor();
//
//        return zona4;
//    }
//
//    public void setZona4(float zona4) {
//        this.zona4 = zona4;
//    }
//
//    public float getZona5() {
//        zona5 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 5).get(0).getValor();
//
//        return zona5;
//    }
//
//    public void setZona5(float zona5) {
//        this.zona5 = zona5;
//    }
//
//    public float getZona6() {
//        zona6 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 6).get(0).getValor();
//
//        return zona6;
//    }
//
//    public void setZona6(float zona6) {
//        this.zona6 = zona6;
//    }
//
//    public float getZona7() {
//        zona7 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 7).get(0).getValor();
//
//        return zona7;
//    }
//
//    public void setZona7(float zona7) {
//        this.zona7 = zona7;
//    }
//
//    public float getZona8() {
//        zona8 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 8).get(0).getValor();
//
//        return zona8;
//    }
//
//    public void setZona8(float zona8) {
//        this.zona8 = zona8;
//    }
//
//    public float getZona9() {
//        zona9 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 9).get(0).getValor();
//
//        return zona9;
//    }
//
//    public void setZona9(float zona9) {
//        this.zona9 = zona9;
//    }
//
//    public float getZona10() {
//        zona10 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 10).get(0).getValor();
//
//        return zona10;
//    }
//
//    public void setZona10(float zona10) {
//        this.zona10 = zona10;
//    }
//
//    public float getZona11() {
//        zona11 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 11).get(0).getValor();
//
//        return zona11;
//    }
//
//    public void setZona11(float zona11) {
//        this.zona11 = zona11;
//    }
//
//    public float getZona12() {
//        zona12 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 12).get(0).getValor();
//
//        return zona12;
//    }
//
//    public void setZona12(float zona12) {
//        this.zona12 = zona12;
//    }
//
//    public float getZona13() {
//        zona13 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 13).get(0).getValor();
//
//        return zona13;
//    }
//
//    public void setZona13(float zona13) {
//        this.zona13 = zona13;
//    }
//
//    public float getZona14() {
//        zona14 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 14).get(0).getValor();
//
//        return zona14;
//    }
//
//    public void setZona14(float zona14) {
//        this.zona14 = zona14;
//    }
//
//    public float getZona15() {
//        zona15 = zonaHomogeneaService.findByNamedQuery("findByIdParam", (int) 999).get(0).getValor();
//
//        return zona15;
//    }
//
//    public void setZona15(float zona15) {
//        this.zona15 = zona15;
//    }
//
//    public VariableService getVariablesService() {
//        return variablesService;
//    }
//
//    public void setVariablesService(VariableService variablesService) {
//        this.variablesService = variablesService;
//    }
//
//    public ZonaHomogeneaService getZonaHomogeneaService() {
//        return zonaHomogeneaService;
//    }
//
//    public void setZonaHomogeneaService(ZonaHomogeneaService zonaHomogeneaService) {
//        this.zonaHomogeneaService = zonaHomogeneaService;
//    }
//
//    public float getSalarioBasico() {
//        salarioBasico = (short) variablesService.obtenerCoeficiente("datos_configuracion", "salario_basico", anioEmision);
//        return salarioBasico;
//    }
//
//    public void setSalarioBasico(float salarioBasico) {
//        this.salarioBasico = salarioBasico;
//    }
//
//    public void guardarCambios() {
//
//        log.error("Guardando cambios.");
//
//        variablesService.setCoeficiente("datos_configuracion", "anio_emision", (short) 0, anioEmision);
//        variablesService.setCoeficiente("datos_configuracion", "salario_basico", anioEmision, salarioBasico);
////        variablesService.setCoeficiente("datos_configuracion", "banda_impositiva", anioEmision, bandaImpositiva);
//
//        log.error("anio y banda OK.");
//
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 0, tipo0);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 1, tipo1);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 2, tipo2);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 3, tipo3);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 4, tipo4);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 5, tipo5);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 6, tipo6);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 7, tipo7);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 8, tipo8);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 9, tipo9);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 10, tipo10);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 11, tipo11);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 12, tipo12);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 13, tipo13);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 14, tipo14);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 15, tipo15);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 16, tipo16);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 17, tipo17);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 18, tipo18);
//        variablesService.setOperacion("cat_bloque", "tipo_construccion", (short) 19, tipo19);
//
//    }

}
