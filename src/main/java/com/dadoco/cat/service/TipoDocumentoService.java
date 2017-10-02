/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.common.service.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class TipoDocumentoService extends AbstractFacade<TipoDocumento> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public TipoDocumentoService() {
        super(TipoDocumento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
