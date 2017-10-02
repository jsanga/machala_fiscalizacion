/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.TipoEscritura;
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
public class EscrituraService extends AbstractFacade<Escritura> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    public EscrituraService() {
        super(Escritura.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEscritura obtenerTipoProtocolizada() {
        Object[] params = {"Protocolizada"};

        Query query = getEntityManager().createNamedQuery("TipoEscritura.findByTipo");

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<TipoEscritura> result = (List<TipoEscritura>) query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;

    }

    public TipoEscritura obtenerTipoCompraVenta() {
        Object[] params = {"Protocolizada"};

        Query query = getEntityManager().createNamedQuery("TipoEscritura.findByTipo");

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<TipoEscritura> result = (List<TipoEscritura>) query.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public Long obtenerNuevoNumeroCatastro() {

        org.slf4j.Logger log = LoggerFactory.getLogger(EscrituraService.class);

        Object singleResult = getEntityManager().createQuery("select max(e.nroCatastro) from Escritura e").getSingleResult();

        if (singleResult != null) {
            Long val = (Long) singleResult;
            val += (long) 1;
            return val;
        } else {
            Long val = (long) 1;
            return val;
        }

    }

}
