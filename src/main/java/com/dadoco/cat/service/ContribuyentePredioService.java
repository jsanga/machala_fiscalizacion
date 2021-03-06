/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.ContribuyentePredio;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class ContribuyentePredioService extends AbstractFacade<ContribuyentePredio> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public ContribuyentePredioService() {
        super(ContribuyentePredio.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
