/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.ren.model.Emision;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class EmisionService extends AbstractFacade<Emision> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public EmisionService() {
        super(Emision.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
