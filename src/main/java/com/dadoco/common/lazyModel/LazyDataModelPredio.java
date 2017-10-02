/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.cat.model.Predio;
import com.dadoco.cat.service.PredioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author fernando
 */
public class LazyDataModelPredio extends LazyDataModel<Predio> {

    private String busqueda;
    private PredioService service;
    private Map<String, Object> filtros;

    public LazyDataModelPredio(PredioService service) {
        super(service);
        this.service = service;
    }    

    @Override
    public List<Predio> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (filtros != null) {
            filters.putAll(filtros);
        }
        List<Predio> lista = this.service.busquedaPorCoincidencia(busqueda, first, pageSize, filters);
        this.setRowCount(this.service.countPorCoincidencia(busqueda, filters));
        log.info("LAZY MODEL PredioService Tamanio de lista: " + lista.size() + " Tamanio Total: " + this.getRowCount());
        return lista;
    }

    @Override
    public void addFilter(String key, Object object) {
        if (filtros == null) {
            filtros = new HashMap<>();
        }
        if (filtros.containsKey(key)) {
            filtros.replace(key, object);
        } else {
            filtros.put(key, object);
        }
    }

    public void removeAllFilter() {
        filtros = new HashMap<>();
    }

    /**
     * @return the busqueda
     */
    public String getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

}
