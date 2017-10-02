/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;

import com.dadoco.cat.model.Integrante;
import com.dadoco.common.service.AbstractFacade;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class IntegranteService extends AbstractFacade<Integrante> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IntegranteService.class);
    
    @EJB
    private VariableService variableService;
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Transactional
    public List<Integrante> integrantesByTipo(int tipo)
    {
        Query query=em.createQuery("SELECT inte from Integrante inte where inte.tipo=:tipo and "
                + "inte.habilitado=:estado Order By inte.nombre ASC", Integrante.class)
                .setParameter("tipo", tipo)
                .setParameter("estado", true);
        
        return query.getResultList();
    }
    
    public Integrante getIntegranteByIdentificacion(String identificacion){
        Query q=getEntityManager().createNamedQuery("Integrante.findByIdentificacion");
        q.setParameter("identificacion", identificacion);
        List<Integrante> lista=q.getResultList();
        if(lista.size()>0){
            return lista.get(0);
        }else{
            return null;
        }
    }

    public IntegranteService() {
        super(Integrante.class);
    }

    public VariableService getVariableService() {
        return variableService;
    }

    public void setVariableService(VariableService variableService) {
        this.variableService = variableService;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<SelectItem> rol(){
        short anio = (short)(Util.ANIO_ACTUAL.shortValue());
        return variableService.valoresVariables("cat_integrante", "rol", anio);
    }
    
}
