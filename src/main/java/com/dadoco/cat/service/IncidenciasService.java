/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Incidencia;
import com.dadoco.cat.model.IncidenciaImagen;
import com.dadoco.cat.model.Predio;
import com.dadoco.common.jsf.UploadedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
@Stateless
public class IncidenciasService {
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IncidenciasService.class);
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;
    
    @Transactional
    public void crearIncidencia(boolean nuevaIncidencia,Incidencia incidencia, List<UploadedImage>photos)
    {
         Subject subject = SecurityUtils.getSubject();
         String user = subject.getPrincipal().toString();
        
        if(nuevaIncidencia)
        {
        em.persist(incidencia);
        }else
        {
        em.merge(incidencia);
        }
        em.flush();
        
        for (UploadedImage f : photos) {

            IncidenciaImagen foto = new IncidenciaImagen();
            foto.setAutor(user);
            foto.setRuta(f.getSavedFile().getName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            foto.setIncidencia(incidencia);
            em.persist(foto);
        }
    }
    
    @Transactional
    public List<Incidencia> listIncidencias()
    {
        List<Incidencia> incidencias;
        Query q1=em.createQuery("SELECT inc From Incidencia inc ORDER BY inc.predio.claveCatastral ASC", Incidencia.class);
        incidencias=q1.getResultList();
        return incidencias;
    }
    
      public List<Incidencia> findByFilter() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Incidencia> c = cb.createQuery(Incidencia.class);
        Root<Incidencia> incidencia = c.from(Incidencia.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

//        predicates.add(
//                cb.equal(predio.get("estado"), 0));

        c.select(incidencia);

        TypedQuery<Incidencia> query = em.createQuery(c);

        return query.getResultList();
    }
}
