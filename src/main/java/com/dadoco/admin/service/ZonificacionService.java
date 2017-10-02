/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.service;

import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaArchivo;
import com.dadoco.cat.model.ZonaHomogenea;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.jsf.UploadedImage;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class ZonificacionService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ZonificacionService.class);

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @EJB
    PredioService predioService;

    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional
    public void crearManzana(Manzana m, Integer zh, List<UploadedImage> files) {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        Manzana manzana = em.find(Manzana.class, m.getManzanaPK());
        if (manzana == null) {
            manzana = m;
        }

        //ZonaHomogenea zonaH = em.find(ZonaHomogenea.class, zh);
        //manzana.setZonaHomogenea(zonaH);

        ManzanaArchivo archivo = new ManzanaArchivo();

        if (!files.isEmpty()) {

            UploadedImage ui = files.get(0);
            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setFechaCreacion(new Date());
            archivo.setRuta(ui.getSavedFile().getName());
            archivo.setDescripcion(ui.getDescription());

            em.persist(archivo);
            em.flush();

            manzana.setManzanero(archivo);
        }
        em.persist(manzana);
        em.flush();

    }

    @Transactional
    public void editarManzana(Manzana m, Integer zh, List<UploadedImage> files) {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        Manzana manzana = em.find(Manzana.class, m.getManzanaPK());
        if (manzana == null) {
            manzana = m;
            log.error("null la manzana");
        } else {
            log.error("Manzana encontrada");
            manzana.setArea(m.getArea());
            manzana.setCalleEste(m.getCalleEste());
            manzana.setCalleNorte(m.getCalleNorte());
            manzana.setCalleSur(m.getCalleSur());
            manzana.setCalleOeste(m.getCalleOeste());
            manzana.setObservacion(m.getObservacion());
        }

        ZonaHomogenea zonaH = em.find(ZonaHomogenea.class, zh);
        manzana.setZonaHomogenea(zonaH);

        ManzanaArchivo archivo = new ManzanaArchivo();

        if (!files.isEmpty()) {

            UploadedImage ui = files.get(0);
            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setRuta(ui.getSavedFile().getName());
            archivo.setDescripcion(ui.getDescription());

            em.persist(archivo);
            em.flush();

            manzana.setManzanero(archivo);
        }
        em.merge(manzana);
        em.flush();

    }
}
