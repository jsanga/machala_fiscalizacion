/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Sector;
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
public class SectorService extends AbstractFacade<Sector> {
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SectorService() {
        super(Sector.class);
    }

    @Override
    public void remove(Sector entity) {
        Query q=getEntityManager().createQuery("DELETE FROM Sector s WHERE s.sectorPK=:spk");
        q.setParameter("spk", entity.getSectorPK());
        q.executeUpdate();
    }
    
    
    
}
