/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.io.File;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dfcalderio
 */
public class UploadFile implements Serializable{
    private int index;
    private String fileName;
    private String description;
    private String pathToFile;
    private UploadedFile uploadedFile;
    private File savedFile;

    public UploadFile() {
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


    public File getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(File savedFile) {
        this.savedFile = savedFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    
    
}
