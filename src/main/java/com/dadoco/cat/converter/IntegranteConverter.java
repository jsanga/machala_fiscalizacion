/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.converter;


import com.dadoco.admin.service.IntegranteService;
import com.dadoco.cat.model.Integrante;
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
@FacesConverter(value = "integranteConverter", forClass = Integrante.class)
public class IntegranteConverter implements Converter {
    
    private IntegranteService integranteService ;

    public IntegranteConverter() {
        try {
            
            integranteService = (IntegranteService) new InitialContext().lookup(Util.moduleJNDI("IntegranteService"));
        } catch (NamingException ex) {
            Logger.getLogger(IntegranteConverter.class.getName()).log(Level.SEVERE, null, ex);
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
            Integrante f = integranteService.find(id);
            return f;
        } catch (NumberFormatException e) {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Integrante) object).getId());
        } else {
            return null;
        }
    }

    public IntegranteService getIntegranteService() {
        return integranteService;
    }

    public void setIntegranteService(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    

}
