/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.ZonaHomogenea;
import com.dadoco.cat.report.GenerarFichaController;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Stateless
public class ZonaHomogeneaService extends AbstractFacade<ZonaHomogenea> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenerarFichaController.class);

    public ZonaHomogeneaService() {
        super(ZonaHomogenea.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<ZonaHomogenea> zonasHomogeneasListByAnio(short anio){                                                       
        
        return (List<ZonaHomogenea>) em.createNamedQuery("ZonaHomogenea.findByAnio")
                .setParameter("anio", anio)
                .getResultList();
    }

    public int nextZonaHomoId() {

        Query query = getEntityManager().createQuery("select max(z.id) from ZonaHomogenea z");

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }
}
