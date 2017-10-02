/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.controller;

import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author dfcalderio
 */
@Named(value = "variableAdmin")
@ApplicationScoped
public class VariableAdmin implements Serializable {

    @EJB
    private VariableService variablesService;

    private List<SelectItem> valores = null;

    private short anioActual = Util.ANIO_ACTUAL.shortValue();

    public short getAnioActual() {
        anioActual = Util.ANIO_ACTUAL.shortValue();
        return anioActual;
    }

    public void setAnioActual(short anioActual) {
        this.anioActual = anioActual;
    }

    public List<SelectItem> valoresVariables(String tabla, String columna, short anio) {

        List<SelectItem> lista = new ArrayList<SelectItem>();
        List<ValorDiscreto> valores = variablesService.obtenerValores(tabla, columna, anio);
        if(valores == null || valores.isEmpty())
            valores = variablesService.obtenerValores(tabla, columna, Short.parseShort("0"));
        for (ValorDiscreto v : valores) {
            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
            lista.add(i);
        }
        return lista;

    }

    public List<SelectItem> valoresVariablesCombo(String tabla1, String columna1, String tabla2, String columna2, short valor, short anio) {

        List<SelectItem> lista = new ArrayList<>();
        List<ValorDiscreto> valores = variablesService.obtenerValores("comun", "comun", anio);
        switch (valor) {
            case 1: {
                valores = variablesService.obtenerValores(tabla1, columna1, anio);
                break;
            }
            case 2: {
                valores = variablesService.obtenerValores(tabla2, columna2, anio);
                break;
            }

        }

        for (ValorDiscreto v : valores) {
            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
            lista.add(i);
            System.out.println("Comobo : " + i.toString());
        }
        return lista;

    }

    public String codigosVariable(String tabla, String columna, short anio) {

        return variablesService.obtenerCodigo(tabla, columna, anio);

    }
    
    public String obtenerValor(Short s1, Short s2){
        return variablesService.obtenerValor(Integer.parseInt(""+s1), s2, Util.ANIO_ACTUAL.shortValue());
    }

    public String codigosVariableDobleCombo(String tabla1, String columna1, String tabla2, String columna2, short valor, short anio) {

        System.out.println("valor desde el servicio: " + valor);
        switch (valor) {
            case 1:
                return variablesService.obtenerCodigoTrue(tabla1, columna1, false, anio);
            case 2:
                return variablesService.obtenerCodigoTrue(tabla2, columna2, false, anio);
            default:
                return variablesService.obtenerCodigoTrue(tabla2, columna2, true, anio);
        }

    }

    public short getMinValue(String tabla, String columna) {
        return variablesService.getMinValue(tabla, columna);
    }

    public short getMaxValue(String tabla, String columna) {
        return variablesService.getMaxValue(tabla, columna);
    }

    public String getValorVariable(String tabla, String columna, short valor, short anio) {
        return variablesService.obtenerValor(tabla, columna, valor, anio);
    }

    public String getValorVariable(String tabla, String columna, short valor) {

        return variablesService.obtenerValor(tabla, columna, valor, Util.ANIO_ACTUAL.shortValue());
    }

    public void updateComponente() {

    }

    public String obtenerValor(String tabla, String columna, short valor, short anio) {

        return variablesService.obtenerValor(tabla, columna, valor, anio);
    }
}
