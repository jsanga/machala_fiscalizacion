/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import java.util.List;
import java.util.Map;

/**
 *
 * @author fernando
 * @param <T>
 */
public class InfoModel<T> {
    
    private String jpqlQuery;
    private Map<String,Object> parametros;
    private List<T> listaDatos;
    private int count;

    public InfoModel() {
    }

    public InfoModel(String jpqlQuery, Map<String, Object> parametros) {
        this.jpqlQuery = jpqlQuery;
        this.parametros = parametros;
    }

    public InfoModel(String jpqlQuery, Map<String, Object> parametros, List<T> listaDatos) {
        this.jpqlQuery = jpqlQuery;
        this.parametros = parametros;
        this.listaDatos = listaDatos;
    }

    public InfoModel(String jpqlQuery, Map<String, Object> parametros, List<T> listaDatos, int count) {
        this.jpqlQuery = jpqlQuery;
        this.parametros = parametros;
        this.listaDatos = listaDatos;
        this.count = count;
    }
    
    public String getJpqlQuery() {
//        if(jpqlQuery!=null){
//            if(parametros!=null){
//                for (Map.Entry<String, Object> entry : parametros.entrySet()) {
//                    String key = entry.getKey();
//                    jpqlQuery=jpqlQuery.replace(":"+key, "$P{"+key+"}");
//                }
//            }
//        }
        return jpqlQuery;
    }

    public void setJpqlQuery(String jpqlQuery) {
        this.jpqlQuery = jpqlQuery;
    }

    public Map<String,Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String,Object> parametros) {
        this.parametros = parametros;
    }

    /**
     * @return the listaDatos
     */
    public List<T> getListaDatos() {
        return listaDatos;
    }

    /**
     * @param listaDatos the listaDatos to set
     */
    public void setListaDatos(List<T> listaDatos) {
        this.listaDatos = listaDatos;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    
    
    
}
