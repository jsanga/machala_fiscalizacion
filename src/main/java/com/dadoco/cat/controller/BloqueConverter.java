/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dfcalderio
 */
@FacesConverter("converter.BloqueSelectConverter")
public class BloqueConverter implements Converter {

 

    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        
      return null;
    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
        System.out.println("Two" + value);
        return value.toString();
    }

}
