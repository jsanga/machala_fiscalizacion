/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;

import com.dadoco.common.service.AbstractFacade;
import com.dadoco.admin.model.ContribucionEspecial;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class ContribucionEspecialService extends AbstractFacade<ContribucionEspecial> {
   
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public ContribucionEspecialService() {
        super(ContribucionEspecial.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
