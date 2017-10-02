package com.dadoco.common.customFilters;

import com.dadoco.common.util.UtilReflexion;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.model.SortOrder;

/**
 * Clase que nos permite realizar el CRUD sobre entidades JPA
 *
 * @author Fernando
 * @param <T>
 */
public abstract class AbstractFacade<T> implements Serializable {

    private Class<T> entityClass;
    private int countFiltro;
    private boolean autoCommit;
    private boolean entityWithState;
    private FacesContext contexto;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.entityWithState = true;
    }

    /**
     * Método para obetner ruta completa de un archivo en el servidor
     *
     * @param rutaArchivo
     * @return
     */
    public String obtenerArchivos(String rutaArchivo) {
        return this.getContexto().getExternalContext().getRealPath(rutaArchivo);
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Método para guardar una entidad JPA
     *
     * @param entity
     * @throws EJBException
     */
    public T guardar(T entity) throws EJBException {
        getEntityManager().persist(entity);
        getEntityManager().flush();

        return entity;
    }

    public Connection getConection() throws SQLException {
        Conexion con = new Conexion();
        return con.conexion();
    }

    /**
     * Método para modificar una entidad JPA
     *
     * @param entity
     * @throws EJBException
     */
    public void modificar(T entity) throws EJBException {

        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    /**
     * Método para eliminar JPA
     *
     * @param entity
     * @throws EJBException
     */
    public void eliminar(T entity) throws EJBException {
        getEntityManager().remove(entity);
        getEntityManager().flush();
    }

    /**
     * Método para buscar una entidad JPA por su primary key
     *
     * @param id
     * @return
     * @throws EJBException
     */
    public T buscarPorCodigo(Object id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Método para listar todos lso datos de una entidad JPA
     *
     * @return
     * @throws EJBException
     */
    public List<T> listarTodos() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Método para utilizar una datatable de primefaces con lazy loading
     *
     * @param first
     * @param count
     * @param sortField
     * @param sortOrder
     * @param filters
     * @param filtros
     * @return
     * @throws Exception
     */
    public List<T> load(int first, int count, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<Filtro> filtros) {
        List<T> datos = new LinkedList<T>();
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        System.out.println("TIEMPO 1: " + System.currentTimeMillis());
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue() instanceof String) {
                    filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                } else {
                    filtrados.add(new Filtro("=", filter.getKey(), filter.getValue()));
                }
            }
        }
        Consulta cq = new Consulta(getEntityClass().getSimpleName());
        cq.agregarFiltrado(filtrados);
        System.out.println("TIEMPO 2: " + System.currentTimeMillis());
        datos = cq.crearConsulta(getEntityManager(), first, count).getResultList();
        System.out.println("TIEMPO 3: " + System.currentTimeMillis());
        countFiltro = cq.getFilas();
        System.out.println("TIEMPO 4: " + System.currentTimeMillis());
        return datos;
    }

    public Object[] loadNuevo(int first, int count, String sortField, SortOrder sortOrder, Map<String, Object> filters, List<Filtro> filtros) {
        List<T> datos = new LinkedList<T>();
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        System.out.println("TIEMPO 1: " + System.currentTimeMillis());
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue().toString().trim().length() != 0) {
                    try {
                        Class typeFilter = UtilReflexion.getTipoCampo(entityClass, filter.getKey());
                        if (typeFilter.equals(String.class)) {
                            filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                        } else {
                            filtrados.add(new Filtro("=", filter.getKey(), UtilReflexion.getObjectCast(typeFilter, filter.getValue())));
                        }
                    } catch (NoSuchFieldException ex) {
                        Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        Consulta cq = new Consulta(getEntityClass().getSimpleName());
        cq.agregarFiltrado(filtrados);
        System.out.println("TIEMPO 2: " + System.currentTimeMillis());
        // datos = cq.crearConsulta(getEntityManager(), first, count).getResultList();
        Object[] info = cq.generarLazyQuery(this.getEntityManager(), first, count);
        System.out.println("TIEMPO 3: " + System.currentTimeMillis());
        System.out.println("TIEMPO 4: " + System.currentTimeMillis());
        //return new Object[]{cq.getFilas(), datos};
        return info;
    }

    /**
     * Método para selecionar todos los registros que exsiten de una carga
     * liviana
     *
     * @param filters
     * @param filtros
     * @return
     * @throws Exception
     */
    public List<T> loadFull(Map<String, Object> filters, List<Filtro> filtros) {
        List<T> datos = new LinkedList<T>();
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                if (filter.getValue().toString().trim().length() != 0) {
                    try {
                        Class typeFilter = UtilReflexion.getTipoCampo(entityClass, filter.getKey());
                        if (typeFilter.equals(String.class)) {
                            filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                        } else {
                            filtrados.add(new Filtro("=", filter.getKey(), UtilReflexion.getObjectCast(typeFilter, filter.getValue())));
                        }
                    } catch (NoSuchFieldException ex) {
                        Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        Consulta cq = new Consulta(getEntityClass().getSimpleName());
        cq.agregarFiltrado(filtrados);
        datos = cq.crearConsulta(getEntityManager()).getResultList();
        countFiltro = cq.getFilas();
        return datos;
    }

    /**
     * Método para obtener lso filtros desde el datatable primefaces
     *
     * @param filters
     * @param filtros
     * @return
     */
    public List<Filtro> load(Map<String, Object> filters, List<Filtro> filtros) {
        List<Filtro> filtrados = new LinkedList<Filtro>();
        filtrados.addAll(filtros);
        if (filters != null) {
            Set<Map.Entry<String, Object>> entries = filters.entrySet();
            for (Map.Entry<String, Object> filter : entries) {
                try {
                    Class typeFilter = UtilReflexion.getTipoCampo(entityClass, filter.getKey());
                    if (typeFilter.equals(String.class)) {
                        filtrados.add(new Filtro("LIKE", filter.getKey(), filter.getValue()));
                    } else {
                        filtrados.add(new Filtro("=", filter.getKey(), UtilReflexion.getObjectCast(typeFilter, filter.getValue())));
                    }
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return filtrados;
    }

    /**
     * @return the count
     */
    public int getCountFiltro() {
        return countFiltro;
    }

    /**
     * @return the autoCommit
     */
    public boolean getAutoCommit() {
        return autoCommit;
    }

    /**
     * @param autoCommit the autoCommit to set
     */
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    /**
     * @return the entityWithState
     */
    public boolean getEntityWithState() {
        return entityWithState;
    }

    /**
     * @param entityWithState the entityWithState to set
     */
    public void setEntityWithState(boolean entityWithState) {
        this.entityWithState = entityWithState;
    }

    /**
     * @return the entityClass
     */
    public Class<T> getEntityClass() {
        return entityClass;
    }

    /**
     * @return the contexto
     */
    public FacesContext getContexto() {
        return contexto;
    }

    /**
     * @param contexto the contexto to set
     */
    public void setContexto(FacesContext contexto) {
        this.contexto = contexto;
    }

    /**
     * findByNamedQuery(java.lang.String, java.lang.Object[])
     *
     * @param name
     * @param params
     * @return
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

    public T create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

}
