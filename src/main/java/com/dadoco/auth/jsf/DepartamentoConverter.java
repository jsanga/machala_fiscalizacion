/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.jsf;

import com.dadoco.auth.model.Departamento;
import com.dadoco.common.jsf.SelectItemsBaseConverter;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author dfcalderio
 */
@ManagedBean(name="departamentoConverter")
public class DepartamentoConverter extends SelectItemsBaseConverter {
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ""+((Departamento) value).getId();
    }
    
    
}
