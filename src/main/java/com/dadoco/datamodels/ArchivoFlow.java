/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.datamodels;

import java.io.InputStream;

/**
 *
 * @author dadoco
 */
public class ArchivoFlow {
    
    private String id;
    private String nombreArchivo;
    private String contentType;
    private String carpeta;
    private InputStream stream;

    public ArchivoFlow(String nombreArchivo, String contentType, InputStream stream, String carpeta, Long id){
        this.nombreArchivo = nombreArchivo;
        this.contentType = contentType;
        this.carpeta = carpeta;
        this.stream = stream;
        this.id = id+"";
    }
    
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
