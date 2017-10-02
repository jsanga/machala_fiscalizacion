/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;


import com.dadoco.cat.model.EfectoTipoEscritura;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class EfectoTipoEscrituraService extends AbstractFacade<EfectoTipoEscritura> {
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EfectoTipoEscrituraService() {
        super(EfectoTipoEscritura.class);
    }
    
}
