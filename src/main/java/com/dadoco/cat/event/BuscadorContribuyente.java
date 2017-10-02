/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.event;

import com.dadoco.cat.model.Contribuyente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dfcalderio
 */
public class BuscadorContribuyente {

    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombreContribuyente;
    
    private List<Contribuyente> listaContribuyentes = null;
    
    public BuscadorContribuyente(){
        listaContribuyentes = new ArrayList<Contribuyente>();
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombreContribuyente() {
        return nombreContribuyente;
    }

    public void setNombreContribuyente(String nombreContribuyente) {
        this.nombreContribuyente = nombreContribuyente;
    }
    
    
}
