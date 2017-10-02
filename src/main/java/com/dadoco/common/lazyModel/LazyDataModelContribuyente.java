/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author fernando
 */
public class LazyDataModelContribuyente extends LazyDataModel<Contribuyente> {

    private String busqueda;
    private ContribuyenteService service;

    public LazyDataModelContribuyente(AbstractFacade facade) {
        super(facade);
    }

//    public LazyDataModelContribuyente(ContribuyenteService service) {
//        super(service);
//        this.service = service;
//    }

    @Override
    public Contribuyente getRowData(String rowKey) {
        if (rowKey != null) {
            Integer codigo = Integer.parseInt(rowKey);
            return service.find(codigo);
        } else {
            return null;
        }
    }

    @Override
    public List<Contribuyente> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (busqueda != null) {
            List<Contribuyente> lista = this.service.busquedaPorCoincidencia(busqueda, first, pageSize);
            this.setRowCount(this.service.countPorCoincidencia(busqueda));
            log.info("LAZY MODEL CONTRIBUYENTE Tamanio de lista: " + lista.size() + " Tamanio Total: " + this.getRowCount());
            return lista;
        } else {
            return super.load(first, pageSize, sortField, sortOrder, filters);
        }
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
