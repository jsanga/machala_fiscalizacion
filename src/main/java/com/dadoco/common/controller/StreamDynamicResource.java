/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.controller;

import java.io.ByteArrayInputStream;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dfcalderio
 */
@Named(value = "sdr")
@RequestScoped
public class StreamDynamicResource {

    /**
     * Creates a new instance of StreamDynamicResource
     */
    public StreamDynamicResource() {
        
    }
    
    public StreamedContent streamContentFromUploadedFile(UploadedFile uf){
        return new DefaultStreamedContent(new ByteArrayInputStream(uf.getContents()));
    }
    
}
