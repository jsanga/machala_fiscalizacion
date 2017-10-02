/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.ManzanaPK;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.common.service.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class ManzanaService extends AbstractFacade<Manzana> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ManzanaService() {
        super(Manzana.class);
    }
    

    public boolean existeManzana(ManzanaPK manzana) {
        Query q = getEntityManager().createNamedQuery("Manzana.existeManzanaPk");
        q.setParameter("manzanaPK", manzana);
        if (q.getResultList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Manzana getManzana(ManzanaPK manzana) {
        Query q = getEntityManager().createNamedQuery("Manzana.existeManzanaPk");
        q.setParameter("manzanaPK", manzana);
        List<Manzana> lista=q.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        }
        return null;
    }

    @Override
    public void remove(Manzana entity) {
        Query q = getEntityManager().createQuery("DELETE FROM Manzana m WHERE m.manzanaPK=:mzpk");
        q.setParameter("mzpk", entity.getManzanaPK());
        q.executeUpdate();
    }

    public Manzana getManzana(TerrenoPK pkTerreno) {
        Query q = getEntityManager().createNamedQuery("Manzana.findByCodes", Manzana.class);
        q.setParameter("1", pkTerreno.getCodProvincia());
        q.setParameter("2", pkTerreno.getCodCanton());
        q.setParameter("3", pkTerreno.getCodParroquia());
        q.setParameter("4", pkTerreno.getCodZona());
        q.setParameter("5", pkTerreno.getCodSector());
        q.setParameter("6", pkTerreno.getCodManzana());

        return (Manzana) q.getSingleResult();
    }

    @Transactional
    public void actualizarCodigo(ManzanaPK pk, short nuevoCodigo) throws Exception {

        Manzana m = em.find(Manzana.class, pk);

        if (m != null) {

            List<Terreno> terrenos = m.getTerrenoCollection();

            em.createNativeQuery("UPDATE manzana SET cod_manzana=" + nuevoCodigo + " \n"
                    + "WHERE cod_zona=" + pk.getCodZona() + " AND cod_sector=" + pk.getCodSector() + " AND cod_manzana=" + pk.getCodManzana() + "").executeUpdate();

            em.flush();

            for (Terreno t : terrenos) {
                em.createNativeQuery("UPDATE cat_predio \n"
                        + "SET clave_catastral=cod_zona||'-'||cod_sector||'-'||cod_manzana||'-'||cod_solar||'-'||propiedad_horizontal,\n"
                        + "clave_anterior=cod_zona||'-'||cod_sector||'-'||cod_manzana||'-'||cod_solar||'-'||propiedad_horizontal\n"
                        + "WHERE cod_zona=" + t.getTerrenoPK().getCodZona() + " AND cod_sector=" + t.getTerrenoPK().getCodSector() + " AND cod_manzana=" + nuevoCodigo + " AND cod_solar=" + t.getTerrenoPK().getCodSolar() + "").executeUpdate();

                em.flush();
            }
        }

    }

}
