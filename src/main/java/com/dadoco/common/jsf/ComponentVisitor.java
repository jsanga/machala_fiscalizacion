/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.jsf;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;

/**
 *
 * @author dairon
 */
public class ComponentVisitor implements VisitCallback {

    private int invalidFields;
    private int validFields;

    public ComponentVisitor() {
        this.invalidFields = 0;
        this.validFields = 0;

    }

    @Override
    public VisitResult visit(VisitContext context, UIComponent component) {
        if (component instanceof UIInput) {
            UIInput input = (UIInput) component;
            if (input.isValid()) {
                //validValues.put(input.getClientId(context.getFacesContext()), input.getValue());
                validFields++;
            } else {
                invalidFields++;
            }
        }
        return VisitResult.ACCEPT;

    }

    public int getInvalidFields() {
        return invalidFields;
    }

    public int getValidFields() {
        return validFields;
    }
    
    

}
