/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;

import com.dadoco.cat.model.ZonaAumentoReduccion;
import com.dadoco.common.service.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class ZonaAumentoReduccionService extends AbstractFacade<ZonaAumentoReduccion> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZonaAumentoReduccionService() {
        super(ZonaAumentoReduccion.class);
    }

}
