/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author fernando
 */
public class PaginaUtil {
    
    /**
     * Metodo para ejecutar un script
     * @param script 
     */
    public static void execute(String script){
        RequestContext.getCurrentInstance().execute(script);
    }
    /**
     * Metodo para actualizar componente en la vista
     * @param comp 
     */
    public static void update(String comp){
        RequestContext.getCurrentInstance().update(comp);
    }
    
    public static void update(String... comp){
        List<String> lista=new LinkedList<>();
        for(String c:comp){
            lista.add(c);
        }
        RequestContext.getCurrentInstance().update(lista);
    }
    
    /**
     * Metodo para Resetear Vista Actual
     * @return 
     */
    public static String resetPage(){
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }
    
}
