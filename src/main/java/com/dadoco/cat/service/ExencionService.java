package com.dadoco.cat.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.Predio;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class ExencionService extends AbstractFacade<Exencion> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public ExencionService() {
        super(Exencion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Exencion> obtenerExenciones(int idPredio, int anio) {

        return (List<Exencion>) em.createNamedQuery("Exencion.findByPredioFecha")
                .setParameter("idPredio", idPredio)
                .setParameter("anio", anio)
                .getResultList();
    }

    public void nuevaExencion(Predio p, Exencion e) {

        em.persist(e);

//        p.getExencionList().add(e);

        em.persist(p);
    }

    public List<Exencion> getExenciones() {

        List<Exencion> result;

        result = (List<Exencion>) em.createNamedQuery("Exencion.findAll")
                .getResultList();

        return result;
    }

    public List<Integer> getIDsPredios() {
        return (List<Integer>) em.createNamedQuery("Exencion.findAllIdsPredios").getResultList();
    }

}
