/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.converter;


import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.ValorDiscretoPK;
import com.dadoco.common.service.ValorDiscretoService;
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
@FacesConverter(value = "valorDiscretoConverter", forClass = ValorDiscreto.class)
public class ValorDiscretoConverter implements Converter {
    
    private ValorDiscretoService valorDiscretoService;

    public ValorDiscretoConverter() {
        try {
            
            valorDiscretoService = (ValorDiscretoService) new InitialContext().lookup(Util.moduleJNDI("ValorDiscretoService"));
        } catch (NamingException ex) {
            Logger.getLogger(ValorDiscretoConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value == null) {

            return null;
        }
        ValorDiscretoPK id = null;
        try {

            
            id = Util.discretoPKbyString(value);

        } catch (Exception e) {

            return null;
        }

        try {
            ValorDiscreto v = valorDiscretoService.find(id);
            return v;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return ((ValorDiscreto) object).getValorDiscretoPK().toString();
        } else {
            return null;
        }
    }

    public ValorDiscretoService getValorDiscretoService() {
        return valorDiscretoService;
    }

    public void setValorDiscretoService(ValorDiscretoService valorDiscretoService) {
        this.valorDiscretoService = valorDiscretoService;
    }
    

}
