package com.dadoco.cat.service;

import com.dadoco.cat.model.Deuda;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class DeudaService extends AbstractFacade<Deuda> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public DeudaService() {
        super(Deuda.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void calculaDeudaActual(Deuda deuda) {

        String queryStr = "SELECT actualizar_deuda(" + deuda.getId() + ")";

        int dummy;

        dummy = (short) em.createNativeQuery(queryStr).getSingleResult();
    }

    public void calculaDeudasContribuyente(String identificacion) {

        String queryStr = "SELECT actualizar_deudas_contribuyente('" + identificacion + "')";

        int dummy;

        dummy = (short) em.createNativeQuery(queryStr).getSingleResult();
    }

}
