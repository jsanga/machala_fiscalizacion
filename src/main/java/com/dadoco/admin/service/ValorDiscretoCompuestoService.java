/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;

import com.dadoco.common.service.*;
import com.dadoco.ren.model.ValorDiscretoCompuesto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class ValorDiscretoCompuestoService extends AbstractFacade<ValorDiscretoCompuesto> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValorDiscretoCompuestoService() {
        super(ValorDiscretoCompuesto.class);
    }

}
