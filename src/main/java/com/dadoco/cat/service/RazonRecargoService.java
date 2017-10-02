package com.dadoco.cat.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.RazonRecargo;
import com.dadoco.cat.model.Recargo;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dairon
 */
@Stateless
public class RazonRecargoService extends AbstractFacade<RazonRecargo> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public RazonRecargoService() {
        super(RazonRecargo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
