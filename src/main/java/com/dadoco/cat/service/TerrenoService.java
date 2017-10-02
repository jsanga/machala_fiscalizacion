/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class TerrenoService extends AbstractFacade<Terreno> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerrenoService() {
        super(Terreno.class);
    }

    public Terreno encontrarTerreno(TerrenoPK pk) {

        return em.find(Terreno.class, pk);

    }

}
