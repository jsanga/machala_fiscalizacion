package com.dadoco.cat.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Recargo;
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
public class RecargoService extends AbstractFacade<Recargo> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public RecargoService() {
        super(Recargo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Recargo> obtenerRecargos(int idPredio, int anio) {

        return (List<Recargo>) em.createNamedQuery("Recargo.findByPredioFecha")
                .setParameter("idPredio", idPredio)
                .setParameter("anio", anio)
                .getResultList();
    }

    public void nuevoRecargo(Recargo r) {

        em.persist(r);

    }

}
