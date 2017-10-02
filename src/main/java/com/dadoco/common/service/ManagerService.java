/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.eclipse.persistence.internal.oxm.schema.model.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author dadoco
 */
@Stateless
public class ManagerService {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public <T extends Object> List<T> findAll(Class<T> entity) {
        List result = null;
        try {
            Session sess = em.unwrap(Session.class);
            Criteria cq = sess.createCriteria(entity);
            result = (List) cq.list();
            Hibernate.initialize(result);
        } catch (Exception e) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    public <T extends Object> List<T> findAllByOrder(Class<T> entity, String parameter, Boolean isAsc) {
        List result = null;
        try {
            Session sess = em.unwrap(Session.class);
            Criteria cq = sess.createCriteria(entity);
            if(isAsc)
                cq.addOrder(Order.asc(parameter));
            else
                cq.addOrder(Order.desc(parameter));
            result = (List) cq.list();
            Hibernate.initialize(result);
        } catch (Exception e) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public Object find(String query, String[] par, Object[] val) {
        Object ob = null;
        try {
            Session sess = em.unwrap(Session.class);
            Query q = sess.createQuery(query).setMaxResults(1);
            for (int i = 0; i < par.length; i++) {
                q.setParameter(par[i], val[i]);
            }
            ob = (Object) q.uniqueResult();
            Hibernate.initialize(ob);
            //HiberUtil.unproxy(ob);
        } catch (Exception e) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return ob;
    }

    public EntityManager getEm() {
        return em;
    }

    public <T extends Object> List<T> findListByParameters(String query, String[] par, Object[] val) {
        List result = null;
        try {
            Session sess = em.unwrap(Session.class);
            Query q = sess.createQuery(query);
            for (int i = 0; i < par.length; i++) {
                q.setParameter(par[i], val[i]);
            }
            result = (List) q.list();
            Hibernate.initialize(result);
            //HiberUtil.unproxy(ob);
        } catch (Exception e) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public <T extends Object> List<T> findListByParameters(String query, String[] par, Object[] val, Integer maxResults) {
        List result = null;
        try {
            Session sess = em.unwrap(Session.class);
            Query q = sess.createQuery(query);
            q.setMaxResults(maxResults);
            for (int i = 0; i < par.length; i++) {
                q.setParameter(par[i], val[i]);
            }
            result = (List) q.list();
            Hibernate.initialize(result);
            //HiberUtil.unproxy(ob);
        } catch (Exception e) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }

    public Object getNative(String query, String[] par, Object[] val) {
        try {
            Session sess = em.unwrap(Session.class);
            Query q = sess.createSQLQuery(query);
            for (int i = 0; i < par.length; i++) {
                q.setParameter(par[i], val[i]);
            }
            return q.list();
        } catch (Exception e) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
