/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.converter;


import com.dadoco.cat.model.ZonaHomogenea;
import com.dadoco.cat.service.ZonaHomogeneaService;
import com.dadoco.common.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author dfcalderio
 */
@FacesConverter(value = "zonaHomoConverter", forClass = ZonaHomogenea.class)
public class ZonaHomogeneaConverter implements Converter {
    
    private ZonaHomogeneaService homogeneaService;

    public ZonaHomogeneaConverter() {
        try {
            
            homogeneaService = (ZonaHomogeneaService) new InitialContext().lookup(Util.moduleJNDI("ZonaHomogeneaService"));
        } catch (NamingException ex) {
            Logger.getLogger(ZonaHomogeneaConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value == null) {

            return null;
        }
        Integer id = null;
        try {

            
            id = Integer.parseInt(value);

        } catch (NumberFormatException e) {

            return null;
        }

        try {
            ZonaHomogenea v = homogeneaService.find(id);
            return v;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return ((ZonaHomogenea) object).getId().toString();
        } else {
            return null;
        }
    }

    public ZonaHomogeneaService getHomogeneaService() {
        return homogeneaService;
    }

    public void setHomogeneaService(ZonaHomogeneaService homogeneaService) {
        this.homogeneaService = homogeneaService;
    }


}
