/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.service;

import com.dadoco.common.model.Archivo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class ArchivoService extends AbstractFacade<Archivo> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoService() {
        super(Archivo.class);
    }

    public List<Integer> archivosPorEscritura(Long idEscritura) {

        Query query = em.createNativeQuery("SELECT adj_archivo.id FROM adj_archivo WHERE id_escritura=" + idEscritura + "");

        return (List<Integer>) query.getResultList();
    }

}
