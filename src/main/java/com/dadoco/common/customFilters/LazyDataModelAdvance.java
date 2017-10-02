/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.customFilters;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyDataModelAdvance<T> extends LazyDataModel<T> {

    private final static Logger log = Logger.getLogger(LazyDataModelAdvance.class.getName());
    private volatile AbstractFacade<T> facade;
    private List<Filtro> filtros = new LinkedList<>();
    private List<Filtro> filtrosTotales;
    private int tamanioLista = 0;
    private Map<String, Object> filters;
    private Class clasePK;
    private Object[] infoLazyDataModel;

    public LazyDataModelAdvance(AbstractFacade<T> facade) {
        super();
        this.facade = facade;
        //clasePK = Long.class;
    }

    public LazyDataModelAdvance() {
        super();
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<T> list = null;
        this.filters = filters;
        try {
            //list = getFacade().load(first, pageSize, sortField, sortOrder, filters, getFiltros());
            infoLazyDataModel = getFacade().loadNuevo(first, pageSize, sortField, sortOrder, filters, getFiltros());
            list=(List<T>) infoLazyDataModel[0];
        } catch (Exception ex) {
            Logger.getLogger(LazyDataModelAdvance.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        this.filtrosTotales = getFacade().load(filters, getFiltros());
        log.log(Level.INFO, "list.size():{0}", String.valueOf(list.size()));
        if (list == null) {
            setRowCount(0);
            tamanioLista = 0;
        } else {
            setRowCount((int)infoLazyDataModel[1]);
            tamanioLista = getRowCount();
        }
        //log.log(Level.INFO, infoLazyDataModel[2]+": ESA ES");
        return list;
    }

    @Override
    public T getRowData(String rowKey) {
//        try{
        if (clasePK.equals(Long.class)) {
            return facade.buscarPorCodigo(Long.parseLong(rowKey));//To change body of generated methods, choose Tools | Templates.
        } else {
            if (clasePK.equals(Integer.class)) {
                return facade.buscarPorCodigo(Integer.parseInt(rowKey));//To change body of generated methods, choose Tools | Templates.
            }
        }
//        }catch(NullPointerException ex){
//            return null;
//        }
        return null;
    }

    @Override
    public Object getRowKey(T object) {
        return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
    }

    public List<T> loadTotalList() throws Exception {
        List<T> list = getFacade().loadFull(filters, getFiltros());
        return list;
    }

    public void filtroEqual(String key, Object value) {
        if (!existeFiltro(key)) {
            getFiltros().add(new Filtro("=", key, value));
        }
    }

    public void filtroMayorEqual(String key, Object value) {
        if (!existeFiltro(key)) {
            getFiltros().add(new Filtro(">=", key, value));
        }
    }

    public void filtroNotEqual(String key, Object value) {
        if (!existeFiltro(key)) {
            getFiltros().add(new Filtro("!=", key, value));
        }
    }

    public void filtroSub(String key1, Object value1, String key2, Object value2) {
        getFiltros().add(new Filtro("SUB", key1, value1, key2, value2));
    }

    public void filtroLike(String key, Object value, String tipo) {
        getFiltros().add(new Filtro("LIKE", key, value, tipo));
    }

    public void filtroBetween(String key1, Object value1, String key2, Object value2) {
        getFiltros().add(new Filtro("BETWEEN", key1, value1, key2, value2));
    }

    public void filtroBetweenFecha(String key1, Date value1, String key2, Date value2) {
        if (!existeFiltro(key1)) {
            getFiltros().add(new Filtro("BETWEENFECHA", key1, value1, key2, value2));
        }
    }

    public void filtroOrderBy(String key1, String modo) {
        getFiltros().add(new Filtro("ORDER", key1, modo));
    }

    public void removeFiltro(String key) {
        int index = existeFiltroIndex(key);
        if (index != -1) {
            this.getFiltros().remove(index);
        }
    }

    private int existeFiltroIndex(String key) {
        int cont = 0;
        for (Filtro f : getFiltros()) {
            if (f.getKey1().equals(key)) {
                return cont;
            }
            cont++;
        }
        return -1;
    }

    private boolean existeFiltro(String key) {
        for (Filtro f : getFiltros()) {
            if (f.getKey1().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the facade
     */
    public AbstractFacade<T> getFacade() {
        return facade;
    }

    /**
     * @param facade the facade to set
     */
    public void setFacade(AbstractFacade<T> facade) {
        this.facade = facade;
    }

    /**
     * @return the filtros
     */
    public List<Filtro> getFiltros() {
        if (filtros == null) {
            filtros = new LinkedList<>();
        }
        return filtros;
    }

    /**
     * @return the tamanioLista
     */
    public int getTamanioLista() {
        return tamanioLista;
    }

    /**
     * @return the filtrosTotales
     */
    public List<Filtro> getFiltrosTotales() {
        return filtrosTotales;
    }

    /**
     * @param filtrosTotales the filtrosTotales to set
     */
    public void setFiltrosTotales(List<Filtro> filtrosTotales) {
        this.filtrosTotales = filtrosTotales;
    }

    /**
     * @param filtros the filtros to set
     */
    public void setFiltros(List<Filtro> filtros) {
        this.filtros = filtros;
    }

    /**
     * @return the clasePK
     */
    public Class getClasePK() {
        return clasePK;
    }

    /**
     * @param clasePK the clasePK to set
     */
    public void setClasePK(Class clasePK) {
        this.clasePK = clasePK;
    }

    /**
     * @return the infoLazyDataModel
     */
    public Object[] getInfoLazyDataModel() {
        return infoLazyDataModel;
    }

    /**
     * @param infoLazyDataModel the infoLazyDataModel to set
     */
    public void setInfoLazyDataModel(Object[] infoLazyDataModel) {
        this.infoLazyDataModel = infoLazyDataModel;
    }
}
