/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.model;

import java.util.Objects;

/**
 *
 * @author dairon
 */
public class ArchivoAux {

    private String ruta;
    private Integer idArchivo;
    private Integer idPredio;
    private boolean eliminado;

    public ArchivoAux() {
        this.eliminado = false;
    }

    public ArchivoAux(String ruta, Integer idArchivo, boolean eliminado) {
        this.ruta = ruta;
        this.idArchivo = idArchivo;
        this.eliminado = eliminado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }
    

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ArchivoAux) {
            if (Objects.equals(((ArchivoAux) obj).getIdArchivo(), this.idArchivo)) {
                return true;
            }
        }
        return false;
    }

}
