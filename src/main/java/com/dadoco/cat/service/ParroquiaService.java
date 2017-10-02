/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Parroquia;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Dairon
 */
@Stateless
public class ParroquiaService extends AbstractFacade<Parroquia> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParroquiaService() {
        super(Parroquia.class);
    }

    public Parroquia findParroquia(Parroquia p) {
        Query q = getEntityManager().createNamedQuery("Parroquia.find");
        if (p.getNombre() != null) {
            q.setParameter("nombre", p.getNombre().toUpperCase());
        } else {
            q.setParameter("nombre", p.getNombre());
        }
        q.setParameter("parroquiaPK", p.getParroquiaPK());
        List<Parroquia> resultado = q.getResultList();
        if (resultado.size() > 0) {
            return resultado.get(0);
        } else {
            return null;
        }
    }
}
