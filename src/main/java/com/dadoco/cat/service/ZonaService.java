/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Zona;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dairon
 */
@Stateless
public class ZonaService extends AbstractFacade<Zona> {
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZonaService() {
        super(Zona.class);
    }

    @Override
    public void remove(Zona entity) {
        Query q=getEntityManager().createQuery("DELETE FROM Zona z WHERE z.zonaPK=:zpk");
        q.setParameter("zpk", entity.getZonaPK());
        q.executeUpdate();
    }
    
}
