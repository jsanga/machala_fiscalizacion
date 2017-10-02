/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.common.service.AbstractFacade;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import org.primefaces.model.SortOrder;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fernando
 */
public class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<T> {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(LazyDataModel.class);
    protected volatile AbstractFacade facade;
    private CriteriaBuilder builder;
    private Map<String, Object> filtros;

    public LazyDataModel(AbstractFacade facade) {
        this.facade = facade;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (filters == null) {
            filters = new HashMap<String, Object>();
        }
        
        if (filtros != null) {
            filters.putAll(this.filtros);
        }

        List<T> lista = facade.load(first, pageSize, filters);

        this.setRowCount(facade.count(filters));

        log.info("TMANIO DE LISTA: " + lista.size() + "  Tamanio total  " + this.getRowCount());
        return lista;
    }

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
     * @param facade the facade to set
     */
    public void setFacade(AbstractFacade facade) {
        this.facade = facade;
    }

    /**
     * @return the builder
     */
    public CriteriaBuilder getBuilder() {
        return builder;
    }

}
