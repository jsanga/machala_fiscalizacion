/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.converter;

import com.dadoco.auth.model.Permiso;
import com.dadoco.auth.service.PermisoService;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author dfcalderio
 */
@FacesConverter("permisoConverter")
public class PermisoConverter implements Converter {

    @EJB
    PermisoService permisoService;

    public PermisoConverter() {
    }

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Object ret = null;
        if (arg1 instanceof PickList) {
            Object dualList = ((PickList) arg1).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Iterator iterator = dl.getSource().iterator(); iterator.hasNext();) {
                Object o = iterator.next();
                String id = (new StringBuilder()).append(((Permiso) o).getId()).toString();
                if (arg2.equals(id)) {
                    ret = o;
                    break;
                }
            }
            if (ret == null) {  
                for (Iterator iterator1 = dl.getTarget().iterator(); iterator1.hasNext();) {
                    Object o = iterator1.next();
                    String id = (new StringBuilder()).append(((Permiso) o).getId()).toString();
                    if (arg2.equals(id)) {
                        ret = o;
                        break;
                    }
                }
            }
        }
        return ret;
    }


    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        return ((Permiso) value).getId().toString();
    }

    public PermisoService getPermisoService() {
        return permisoService;
    }

    public void setPermisoService(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

}
