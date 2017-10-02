/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.jsf;

import java.io.File;
import java.io.Serializable;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dfcalderio
 */
public class UploadedImage implements Serializable{
    private int index;
    private String fileName;
    private String data;
    private String description;
    private String pathToFile;
    private UploadedFile uploadedFile;
    private File savedFile;

    public UploadedImage() {
    }

     public UploadedImage(int index, String fileName, String description, UploadedFile uploadedFile) {
        this.index = index;
        this.fileName = fileName;
        this.description = description;
        this.uploadedFile = uploadedFile;
    }
        
    public UploadedImage(int index, String fileName, String data, String description) {
        this.index = index;
        this.fileName = fileName;
        this.data = data;
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
    
    
}
