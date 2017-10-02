/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.customFilters.AbstractFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class ContribuyenteService extends AbstractFacade<Contribuyente> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ContribuyenteService.class);
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @EJB
    PredioService predioService;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContribuyenteService() {
        super(Contribuyente.class);
    }

    public List<Contribuyente> buscarParecidos(String nombre) {

        String[] palabras = nombre.split(" ");

        Query q;

        switch (palabras.length) {
            case 2:
                q = em.createNamedQuery("Contribuyente.findLike2");
                q.setParameter("code1", "%" + palabras[0].toUpperCase() + "%");
                q.setParameter("code2", "%" + palabras[1].toUpperCase() + "%");
                break;
            case 3:
                q = em.createNamedQuery("Contribuyente.findLike3");
                q.setParameter("code1", "%" + palabras[0].toUpperCase() + "%");
                q.setParameter("code2", "%" + palabras[1].toUpperCase() + "%");
                q.setParameter("code3", "%" + palabras[2].toUpperCase() + "%");
                break;
            case 4:
                q = em.createNamedQuery("Contribuyente.findLike4");
                q.setParameter("code1", "%" + palabras[0].toUpperCase() + "%");
                q.setParameter("code2", "%" + palabras[1].toUpperCase() + "%");
                q.setParameter("code3", "%" + palabras[2].toUpperCase() + "%");
                q.setParameter("code4", "%" + palabras[3].toUpperCase() + "%");
                break;
            default:
                q = em.createNamedQuery("Contribuyente.findLike");
                q.setParameter("code", "%" + nombre.toUpperCase() + "%");
                break;
        }

        return (List<Contribuyente>) q.getResultList();
    }

    public List<Contribuyente> buscarPorIdentificacion(String identificacion) {

        Query q = em.createNamedQuery("Contribuyente.findIdentificacion");
        q.setParameter("code", identificacion.toUpperCase() + "%");

        return (List<Contribuyente>) q.getResultList();
    }

    public List<Contribuyente> buscarPorClaveCatastral(String clave) {

        List<Predio> predios = predioService.findByNamedQuery("Predio.findClaveCatastral", clave.toUpperCase() + "%");

        List<Contribuyente> contribuyentes = new ArrayList<>();

        for (Predio p : predios) {
            contribuyentes.addAll(p.getPropietarios());
        }

        return contribuyentes;
    }

    public void insertarContribuyente(String nombre, String identificacion) {

        String queryStr = "INSERT INTO contribuyente (nombre,identificacion) "
                + "SELECT '" + nombre + "','" + identificacion + "' "
                + "WHERE NOT EXISTS (SELECT 1 FROM contribuyente WHERE nombre='" + nombre + "' AND identificacion='" + identificacion + "')";

        em.createNativeQuery(queryStr).getSingleResult();
    }

    public List<Contribuyente> findAllActivo() {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Contribuyente> c = cb.createQuery(Contribuyente.class);
        Root<Contribuyente> contribuyente = c.from(Contribuyente.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(
                cb.equal(contribuyente.get("estado"), 1));

        c.select(contribuyente);

        TypedQuery<Contribuyente> query = getEntityManager().createQuery(c);

        return query.getResultList();
    }

    public List<Contribuyente> buscarListaContribuyentes(String listado) {

        if (listado.equals("")) {
            return new ArrayList<>();
        }

        Query query = getEntityManager().createQuery("SELECT c FROM Contribuyente c WHERE c.id IN (" + listado + ")");

        return query.getResultList();
    }

    public List<Integer> busquedaGeneral(String clave) {

        List<Integer> resp = null;
        try {

            Query query = em.createNativeQuery("with consulta as (\n"
                    + "select (c.identificacion||' '||c.apellido_paterno||' '||c.apellido_materno||' '||c.nombre) as texto, c.id from  contribuyente c\n"
                    + " where c.estado = 1 )\n"
                    + "SELECT id FROM consulta \n"
                    + "WHERE texto LIKE '%" + clave.toUpperCase() + "%'");

            resp = query.getResultList();

            log.error("with consulta as (\n"
                    + "select (c.identificacion||' '||c.apellido_paterno||' '||c.apellido_materno||' '||c.nombre) as texto, c.id from  contribuyente c\n"
                    + " where c.estado = 1 )\n"
                    + "SELECT id FROM consulta \n"
                    + "WHERE texto LIKE '%" + clave.toUpperCase() + "%'");

        } catch (Exception e) {

        }

        return resp;
    }

    public List<Contribuyente> busquedaPorCoincidencia(String busqueda, int first, int pageSize) {
        busqueda="%"+busqueda.toUpperCase()+"%";
        Query q = getEntityManager().createNamedQuery("Contribuyente.findByMatch");
        q.setParameter("dato", busqueda);
        return q.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int countPorCoincidencia(String busqueda) {
        busqueda="%"+busqueda.toUpperCase()+"%";
        Query q = getEntityManager().createNamedQuery("Contribuyente.countByMatch");
        q.setParameter("dato", busqueda);
        return ((Long) q.getSingleResult()).intValue();
    }
}
