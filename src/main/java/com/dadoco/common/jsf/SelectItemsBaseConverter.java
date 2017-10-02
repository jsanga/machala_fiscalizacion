/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author dfcalderio
 */
public abstract class SelectItemsBaseConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {        
        return SelectItemsUtils.findValueByStringConversion(context, component, value, this);    
    }    
}
