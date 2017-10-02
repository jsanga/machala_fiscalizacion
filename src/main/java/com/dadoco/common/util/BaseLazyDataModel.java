/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import com.dadoco.admin.service.IntegranteService;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author dadoco
 */
public class BaseLazyDataModel<T extends Object> extends LazyDataModel<T> {

    private IntegranteService integranteService;

    private EntityManager manager;
    private Class<T> entity;
    private int rowCount = 0;
    private String defaultSorted = "id";
    private String defaultSortOrder = "ASC";
    private Criteria orderCrit;
    private String orderField;

    public BaseLazyDataModel() {
        try {
            integranteService = (IntegranteService) new InitialContext().lookup(Util.moduleJNDI("IntegranteService"));
            manager = integranteService.getEm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BaseLazyDataModel(Class<T> entity) throws NamingException {
        this.entity = entity;
        integranteService = (IntegranteService) new InitialContext().lookup(Util.moduleJNDI("IntegranteService"));
        manager = integranteService.getEm();
    }

    public BaseLazyDataModel(Class<T> entity, String defaultSorted) throws NamingException {
        this.entity = entity;
        this.defaultSorted = defaultSorted;
        integranteService = (IntegranteService) new InitialContext().lookup(Util.moduleJNDI("IntegranteService"));
        manager = integranteService.getEm();
    }

    public BaseLazyDataModel(Class<T> entity, String defaultSorted, String defaultSortOrder) throws NamingException {
        this.entity = entity;
        this.defaultSorted = defaultSorted;
        this.defaultSortOrder = defaultSortOrder;
        integranteService = (IntegranteService) new InitialContext().lookup(Util.moduleJNDI("IntegranteService"));
        manager = integranteService.getEm();
    }

    public void criteriaFilterSetup(Criteria crit, Map<String, Object> filters) throws Exception {
        //put your code here
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List result = null;
        Criteria cq, dcq;
        try {
            cq = (manager.unwrap(Session.class)).createCriteria(this.getEntity(), "entity");
            this.criteriaFilterSetup(cq, filters);
            cq.setProjection(Projections.projectionList().add(Projections.rowCount()));
            dcq = (manager.unwrap(Session.class)).createCriteria(this.getEntity(), "entity1");

            this.criteriaFilterSetup(dcq, filters);
            if (orderCrit != null) {
                this.criteriaSortSetup(orderCrit, orderField, sortOrder);
            } else {
                this.criteriaSortSetup(dcq, sortField, sortOrder);
            }
            this.criteriaPageSetup(dcq, first, pageSize);
            rowCount = 0;
            rowCount = ((Long) cq.uniqueResult()).intValue();
            this.setRowCount(rowCount);
            result = dcq.list();
            Hibernate.initialize(result);
        } catch (Exception ex) {
            Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public T getRowData(Object key) {
        T ob = null;
        try {
            ob = manager.find(entity, key);
        } catch (Exception e) {
            Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, e);
        }
        return ob;
    }

    @Override
    public T getRowData(String rowKey) {
        T ob = null;
        Object x = rowKey;
        try {
            ob = manager.find(entity, Long.parseLong(rowKey));
        } catch (IllegalArgumentException e) {
            ob = manager.find(entity, Integer.parseInt(rowKey));
            if (ob != null) {
                return ob;
            } else {
                Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (Exception e) {
            Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, e);
        }
        return ob;
    }

    @Override
    public void setRowIndex(int rowIndex) {
        try {
            if (rowIndex == -1 || getPageSize() == 0) {
                super.setRowIndex(-1);
            } else {
                super.setRowIndex(rowIndex % getPageSize());
            }
        } catch (Exception e) {
            Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void criteriaPageSetup(Criteria crit, int first, int pageSize) {
        try {
            crit.setFirstResult(first);
            crit.setMaxResults(pageSize);
        } catch (Exception e) {
            Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void criteriaSortSetup(Criteria crit, String field, SortOrder order) {
        try {
            if (field == null) {
                crit.addOrder((defaultSortOrder.equalsIgnoreCase("ASC")) ? Order.asc(defaultSorted) : Order.desc(defaultSorted));
            } else {
                if (order.equals(SortOrder.ASCENDING)) {
                    crit.addOrder(Order.asc(field));
                } else {
                    crit.addOrder(Order.desc(field));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(BaseLazyDataModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Class<T> getEntity() {
        return entity;
    }

    public void setEntity(Class<T> entity) {
        this.entity = entity;
    }

    public String getDefaultSorted() {
        return defaultSorted;
    }

    public void setDefaultSorted(String defaultSorted) {
        this.defaultSorted = defaultSorted;
    }

    public String getDefaultSortOrder() {
        return defaultSortOrder;
    }

    public void setDefaultSortOrder(String defaultSortOrder) {
        this.defaultSortOrder = defaultSortOrder;
    }

    public Criteria getOrderCrit() {
        return orderCrit;
    }

    public void setOrderCrit(Criteria orderCrit) {
        this.orderCrit = orderCrit;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

}
