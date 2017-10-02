/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.jsf;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Dairon
 */
public class UploadedDocument implements Serializable{
    private int index;
    private String fileName;
    private String data;
    private String description;
    
    private File savedFile;

    public UploadedDocument() {
    }

        
    public UploadedDocument(int index, String fileName, String description) {
        this.index = index;
        this.fileName = fileName;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public File getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(File savedFile) {
        this.savedFile = savedFile;
    }
    
    
    

    public void setData(String data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
