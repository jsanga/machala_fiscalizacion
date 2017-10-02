/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
/**
 * @author dfcalderio
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int count(Map<String, Object> filters) {
        List<Predicate> fill = new LinkedList<>();

        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = builder.createQuery();
        Root<T> root = cq.from(this.entityClass);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                Expression<String> filterKeyExp = root.get(key).as(String.class);
                filterKeyExp = builder.upper(filterKeyExp);
                fill.add(builder.like(filterKeyExp, "%" + value.toString().toUpperCase() + "%"));
            } else {
                fill.add(builder.equal(root.get(key), value));
            }
        }
        if (fill.size() > 0) {
            cq.select(builder.count(root)).where(fill.toArray(new Predicate[]{}));
        } else {
            cq.select(builder.count(root));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    public List<T> load(int first, int pageSize, Map<String, Object> filters) {
        List<Predicate> fill = new LinkedList<>();
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(this.entityClass);
        Root<T> root = cq.from(this.entityClass);
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                Expression<String> filterKeyExp = root.get(key).as(String.class);
                filterKeyExp = builder.upper(filterKeyExp);
                fill.add(builder.like(filterKeyExp, "%" + value.toString().toUpperCase() + "%"));
            } else {
                fill.add(builder.equal(root.get(key), value));
            }
        }
        if (fill.size() > 0) {
            cq.select(root).where(fill.toArray(new Predicate[]{}));
        } else {
            cq.select(root);
        }
        TypedQuery<T> query = getEntityManager().createQuery(cq);

        List<T> listado = query.setFirstResult(first).setMaxResults(pageSize).getResultList();
        return listado;
    }

    /**
     * findByNamedQuery(java.lang.String, java.lang.Object[])
     */
    public List<T> findByNamedQuery(final String name, Object... params) {
        javax.persistence.Query query = getEntityManager().createNamedQuery(
                name);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        final List<T> result = (List<T>) query.getResultList();
        return result;
    }

    /**
     * #findByNamedQueryAndNamedParams(java.lang.String, java.util.Map)
     */
    public List<T> findByNamedQueryAndNamedParams(final String name,
            final Map<String, ? extends Object> params) {
        javax.persistence.Query query = getEntityManager().createNamedQuery(
                name);

        for (final Map.Entry<String, ? extends Object> param : params
                .entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        final List<T> result = (List<T>) query.getResultList();
        return result;
    }

    public List<T> findByExample(T exampleInstance) {

        TypedQuery<T> query = getQueryByExample(exampleInstance);
        if (query == null) {
            return null;
        } else {
            return query.getResultList();
        }
    }

    private TypedQuery<T> getQueryByExample(T exampleInstance) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> c = cb.createQuery(entityClass);
            Root<T> r = c.from(entityClass);

            Predicate p = cb.conjunction();
            if (exampleInstance != null) {
                Metamodel mm = getEntityManager().getMetamodel();
                EntityType<T> et = mm.entity(entityClass);
                Set<Attribute<? super T, ?>> attrs = et.getAttributes();

                for (Attribute<? super T, ?> a : attrs) {
                    String name = a.getName();
                    String javaName = a.getJavaMember().getName();
                    String getter = "get"
                            + javaName.substring(0, 1).toUpperCase()
                            + javaName.substring(1);
                    Method m;

                    m = entityClass.getMethod(getter, (Class<?>[]) null);

                    if (m.invoke(exampleInstance, (Object[]) null) != null) {
                        p = cb.and(p, cb.equal(r.get(name), m.invoke(
                                exampleInstance, (Object[]) null)));
                    }
                }
            }
            c.select(r).where(p);
            TypedQuery<T> query = getEntityManager().createQuery(c);
            return query;

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

}
