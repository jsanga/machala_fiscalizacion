/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.ImpuestoPredialPK;
import com.dadoco.cat.model.Predio;
import com.dadoco.common.lazyModel.InfoModel;
import com.dadoco.common.customFilters.AbstractFacade;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Stateless
public class ImpuestoService extends AbstractFacade<ImpuestoPredial> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger("");

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImpuestoService() {
        super(ImpuestoPredial.class);
    }

    public void setExcluido(short anio, int idPredio) {

        Query q = em.createNativeQuery("UPDATE cat_impuesto_predial SET excluido=NOT excluido WHERE anio=" + anio + " AND id_predio=" + idPredio);
        q.executeUpdate();

        em.flush();
    }

    public void eliminarEmision(short anio) {

        Query q = em.createNativeQuery("DELETE FROM cat_impuesto_predial WHERE anio=" + anio);
        q.executeUpdate();

        em.flush();
    }

    public List<Integer> getIdsParaEmitir(short anio) {

        Query q = em.createNativeQuery("SELECT id_predio FROM cat_impuesto_predial WHERE emitido=FALSE AND excluido=FALSE AND anio=" + anio);

        List<Integer> result = (List<Integer>) q.getResultList();

        return result;
    }
    
    public List<ImpuestoPredialPK> getIdsParaConfirmarEmision(short anio) {

        //Query q = em.createNativeQuery("SELECT id_predio FROM cat_impuesto_predial WHERE emitido=FALSE AND excluido=FALSE AND anio=" + anio);
         Query q = em.createQuery("SELECT i.impuestoPredialPK FROM ImpuestoPredial i  WHERE i.excluido=FALSE AND i.impuestoPredialPK.anio=" +anio+" ORDER BY i.claveCatastral");
        List<ImpuestoPredialPK> result = (List<ImpuestoPredialPK>) q.getResultList();

        return result;
    }
    

    public void confirmaEmision(short anio) {
        Query q = em.createQuery("UPDATE ImpuestoPredial i SET i.emitido = TRUE WHERE i.excluido=FALSE AND i.impuestoPredialPK.anio=" + anio);
        q.executeUpdate();
    }

    public double recaudacionTotal(short anio) {

        Query query = em.createNativeQuery("SELECT sum(total) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    }

    public double recaudacionEmitida(short anio) {

        Query query = em.createNativeQuery("SELECT sum(total) FROM cat_impuesto_predial WHERE emitido=true AND anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    }

    public double recaudacionSinEmitir(short anio) {

        Query query = em.createNativeQuery("SELECT sum(total) FROM cat_impuesto_predial WHERE emitido=false AND anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    }
    
    public double totalAvaluoTerreno(short anio) {

        Query query = em.createNativeQuery("SELECT sum(avaluo_terreno) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    }
    
     public double totalAvaluoConstruccion(short anio) {

        Query query = em.createNativeQuery("SELECT sum(avaluo_edificacion) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    }
     
    public double totalAvaluoComplementarias(short anio) {

        Query query = em.createNativeQuery("SELECT sum(avaluo_complementarias) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalImpuestoPredial(short anio) {

        Query query = em.createNativeQuery("SELECT sum(impuesto_predial) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalRPJ(short anio) {

        Query query = em.createNativeQuery("SELECT sum(recargo_personas_juridicas) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalCEM(short anio) {

        Query query = em.createNativeQuery("SELECT sum(cem) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalNoEdificado(short anio) {

        Query query = em.createNativeQuery("SELECT sum(solar_no_edificado) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalServiciosAdministrativos(short anio) {

        Query query = em.createNativeQuery("SELECT sum(valor_tasa) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalBombero(short anio) {

        Query query = em.createNativeQuery("SELECT sum(bombero) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalSaludSeguridad(short anio) {

        Query query = em.createNativeQuery("SELECT sum(salud_seguridad) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalVetustez(short anio) {

        Query query = em.createNativeQuery("SELECT sum(vetustez) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 
    
    public double totalNoEdifZonaPromocion(short anio) {

        Query query = em.createNativeQuery("SELECT sum(no_edif_zona_promo) FROM cat_impuesto_predial WHERE anio=" + anio);

        List<Object> result = query.getResultList();

        if (!result.isEmpty() && result.get(0) != null) {
            return (float) result.get(0);
        } else {
            return 0;
        }
    } 

    public int totalPredios() {

        Query query = em.createNativeQuery("SELECT count(id) FROM cat_predio");

        BigInteger resp = (BigInteger) query.getSingleResult();

        return resp.intValue();
    }

    public int totalPreEmitidosPorAnnio(short anio) {

        Query query = em.createNativeQuery("SELECT count(id_predio) FROM cat_impuesto_predial WHERE anio=" + anio);

        BigInteger resp = (BigInteger) query.getSingleResult();

        return resp.intValue();
    }

    public int totalEmitidosPorAnnio(short anio) {

        Query query = em.createNativeQuery("SELECT count(id_predio) FROM cat_impuesto_predial WHERE emitido=true AND anio=" + anio);

        BigInteger resp = (BigInteger) query.getSingleResult();

        return resp.intValue();
    }

    public ImpuestoPredial getImpuestoPredial(Predio p, short anio) {

        Object[] params = {p.getId(), anio};

        Query query = em.createNamedQuery("ImpuestoPredial.findByPredioAnio");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        ImpuestoPredial result = (ImpuestoPredial) query.getSingleResult();
        if (result != null) {
            return result;
        }
        return null;
    }

    public boolean preemisionCompleta(short anio) {

        Query query = em.createNativeQuery("SELECT count(p.id) AS sinpreemitir FROM cat_predio p LEFT JOIN cat_impuesto_predial ip ON p.id=ip.id_predio WHERE anio=" + anio + " AND ip.id_predio IS NULL");

        BigInteger resp = (BigInteger) query.getSingleResult();

        return (resp.intValue() == 0);
    }

    public List<Integer> getPrediosSinPreemitir(short anio) {

        Query query = em.createNativeQuery("SELECT p.id FROM cat_predio p LEFT JOIN cat_impuesto_predial ip ON p.id=ip.id_predio AND anio=" + anio + " WHERE ip.id_predio IS NULL");

        List<Integer> resp = (List<Integer>) query.getResultList();

        return resp;
    }

    public List<Object> getImpuestosPreemitidos(short anio) {

        Query query = em.createNativeQuery(
                "SELECT p.clave_catastral, ip1.anio AS anioActual, ip1.avaluo_predial AS avaluoActual, ip1.impuesto_predial AS impuestoActual, ip1.total AS totalActual, "
                + "ip2.anio AS anioAnterior, ip2.avaluo_predial AS avaluoAnterior, ip2.impuesto_predial AS impuestoAnterior, ip2.total AS totalAnterior, "
                + "ip1.excluido AS excluido, ip1.emitido AS emitido, ip1.id_predio AS id_predio "
                + "FROM cat_impuesto_predial ip1 "
                + "LEFT JOIN cat_impuesto_predial ip2 ON ip1.id_predio=ip2.id_predio AND ip1.anio=ip2.anio+1 "
                + "INNER JOIN cat_predio p ON p.id=ip1.id_predio "
                + "WHERE ip1.anio=" + anio + " AND ip1.emitido=false "
                + "ORDER BY p.clave_catastral");

        List<Object> resp = (List<Object>) query.getResultList();

        return resp;
    }

    public List<Object> getImpuestosEmitidos(short anio) {

        Query query = em.createNativeQuery(
                "SELECT p.clave_catastral, ip1.anio AS anioActual, ip1.avaluo_predial AS avaluoActual, ip1.impuesto_predial AS impuestoActual, ip1.total AS totalActual, "
                + "ip2.anio AS anioAnterior, ip2.avaluo_predial AS avaluoAnterior, ip2.impuesto_predial AS impuestoAnterior, ip2.total AS totalAnterior "
                + "FROM cat_impuesto_predial ip1 "
                + "LEFT JOIN cat_impuesto_predial ip2 ON ip1.id_predio=ip2.id_predio AND ip1.anio=ip2.anio+1 "
                + "INNER JOIN cat_predio p ON p.id=ip1.id_predio "
                + "WHERE ip1.anio=" + anio + " AND ip1.emitido=true "
                + "ORDER BY p.clave_catastral");

        List<Object> resp = (List<Object>) query.getResultList();

        return resp;
    }

    public InfoModel<Object> getImpuestosEmitidos(short anio, int first, int pageSize, Map<String, Object> filters) {
        StringBuilder sqlSelect = new StringBuilder();
        sqlSelect.append("SELECT p.clave_catastral, ip1.anio AS anioActual, ip1.avaluo_predial AS avaluoActual, ip1.impuesto_predial AS impuestoActual, ip1.total AS totalActual,");
        sqlSelect.append("ip2.anio AS anioAnterior, ip2.avaluo_predial AS avaluoAnterior, ip2.impuesto_predial AS impuestoAnterior, ip2.total AS totalAnterior ");
        sqlSelect.append("FROM cat_impuesto_predial ip1 ");
        sqlSelect.append("LEFT JOIN cat_impuesto_predial ip2 ON ip1.id_predio=ip2.id_predio AND ip1.anio=ip2.anio+1 ");
        sqlSelect.append("INNER JOIN cat_predio p ON p.id=ip1.id_predio ");
        sqlSelect.append("WHERE ip1.anio=").append(anio).append(" AND ip1.emitido=true ");
        sqlSelect.append("ORDER BY p.clave_catastral");

        StringBuilder sqlCount = new StringBuilder(sqlSelect);
        sqlCount.delete(0, sqlSelect.indexOf("FROM"));
        sqlCount.insert(0, "Select Count(p.clave_catastral) ");

        Query query = em.createNativeQuery(sqlSelect.toString());

        List<Object> resp = (List<Object>) query.getResultList();

        return null;
    }

    public Object[] getResumenEmision(short anio) {

        Query query = em.createNativeQuery("SELECT * FROM cat_emisiones WHERE anio=" + anio);

        List<Object[]> resp = query.getResultList();

        if (!resp.isEmpty()) {
            return resp.get(0);
        }

        return null;
    }

    public void actualizarDatosEmision(int anio, int total, int preemitidos, int emitidos, double recaudacionTotal, double recaudacionEmitida) {

        Object result = null;

        try {
            Query query = em.createNativeQuery("SELECT e.anio from cat_emisiones e where e.anio = ?");
            query.setParameter(1, anio);

            result = query.getSingleResult();
        } catch (Exception e) {

        }

        Object[] resumen = obtenerResumenEmision((short) anio);

        double avaluoTerreno = new Double(resumen[0].toString());
        double avaluoConst = new Double(resumen[1].toString());
        double avaluoCompl = new Double(resumen[2].toString());
        double avaluoComercial = new Double(resumen[3].toString());
        double impuestoPredial = new Double(resumen[4].toString());
        double basura = new Double(resumen[5].toString());
        double rpj = new Double(resumen[6].toString());
        double cem = new Double(resumen[7].toString());
        double noEdificado = new Double(resumen[8].toString());

        int sin_emitir = preemitidos - emitidos;
        double recaudacionSinEmitir = recaudacionTotal - recaudacionEmitida;

        if (result != null) {

            em.createNativeQuery("UPDATE cat_emisiones SET\n"
                    + " total_predios=" + total + ", preemitidos=" + preemitidos + ", emitidos=" + emitidos + ", sin_emitir=" + sin_emitir + ", \n"
                    + " recaudacion_total=" + recaudacionTotal + ", recaudacion_emitida=" + recaudacionEmitida + ", recaudacion_sin_emitir=" + recaudacionSinEmitir + ",\n"
                    + " avaluo_terreno=" + avaluoTerreno + ", avaluo_construccion=" + avaluoConst + ", avaluo_complementarias=" + avaluoCompl + ",\n"
                    + " avaluo_comercial=" + avaluoComercial + ", impuesto_predial=" + impuestoPredial + ", recoleccion_basura=" + basura + ",\n"
                    + " rpj=" + rpj + ", cem=" + cem + ", recargo_noedif=" + noEdificado + "\n"
                    + " WHERE anio=" + anio).executeUpdate();
        } else {

            em.createNativeQuery("INSERT INTO cat_emisiones(\n"
                    + "         anio, total_predios, preemitidos, emitidos, sin_emitir, recaudacion_total, \n"
                    + "         recaudacion_emitida, recaudacion_sin_emitir, avaluo_terreno, avaluo_construccion, avaluo_complementarias, avaluo_comercial, impuesto_predial, recoleccion_basura, rpj, cem, recargo_noedif)\n"
                    + "         VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17)"
            ).setParameter(1, anio)
                    .setParameter(2, total)
                    .setParameter(3, preemitidos)
                    .setParameter(4, emitidos)
                    .setParameter(5, sin_emitir)
                    .setParameter(6, recaudacionTotal)
                    .setParameter(7, recaudacionEmitida)
                    .setParameter(8, recaudacionSinEmitir)
                    .setParameter(9, avaluoTerreno)
                    .setParameter(10, avaluoConst)
                    .setParameter(11, avaluoCompl)
                    .setParameter(12, avaluoComercial)
                    .setParameter(13, impuestoPredial)
                    .setParameter(14, basura)
                    .setParameter(15, rpj)
                    .setParameter(16, cem)
                    .setParameter(17, noEdificado)
                    .executeUpdate();

        }
        em.flush();
    }

    public List<ImpuestoPredial> obtenerImpuesto(short anio, int offset) {
        String sql = "select cat_impuesto_predial.* from cat_impuesto_predial where emitido=true and anio =" + anio + " order by clave_catastral limit 10000 offset " + offset + "";

        Query query = em.createNativeQuery(sql, ImpuestoPredial.class);

        List<ImpuestoPredial> result = (List<ImpuestoPredial>) query.getResultList();

        return result;
    }

    public Object[] obtenerResumenEmision(short anio) {
        String sql = "SELECT \n"
                + "sum(avaluo_terreno) as avaluo_terreno,sum(avaluo_edificacion) as avaluo_edificacion,sum(avaluo_complementarias) as avaluo_complementarias,\n"
                + "sum(avaluo_predial) as avaluo_predial, sum(impuesto_predial) as impuesto_predial,sum(recoleccion_basura) as recoleccion_basura,\n"
                + "sum(recargo_personas_juridicas) as recargo_personas_juridicas,sum(cem) as cem,sum(solar_no_edificado) as solar_no_edificado,\n"
                + "sum(total) as total\n"
                + "FROM cat_impuesto_predial\n"
                + "where anio=" + anio + "";

        Query query = em.createNativeQuery(sql);

        Object[] result = (Object[]) query.getSingleResult();

        return result;
    }

    public List<ImpuestoPredial> findByFilter(String parroquia, String zona, String sector, String manzana, boolean emitido, short anio) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ImpuestoPredial> c = cb.createQuery(ImpuestoPredial.class);
        EntityType<ImpuestoPredial> type = em.getMetamodel().entity(ImpuestoPredial.class);

        Root<ImpuestoPredial> impuesto = c.from(ImpuestoPredial.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(
                cb.equal(impuesto.get("emitido"), emitido));
        predicates.add(
                cb.equal(impuesto.get("impuestoPredialPK").get("anio"), anio));

        if (parroquia != null) {
            predicates.add(
                    cb.equal(impuesto.get("predio").get("terreno").get("terrenoPK").get("codParroquia"), parroquia));
        }

        if (zona != null) {
            predicates.add(
                    cb.equal(impuesto.get("predio").get("terreno").get("terrenoPK").get("codZona"), zona));
        }

        if (sector != null) {
            predicates.add(
                    cb.equal(impuesto.get("predio").get("terreno").get("terrenoPK").get("codSector"), sector));
        }
        if (manzana != null) {
            predicates.add(
                    cb.equal(impuesto.get("predio").get("terreno").get("terrenoPK").get("codManzana"), manzana));
        }

        c.select(impuesto).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<ImpuestoPredial> query = getEntityManager().createQuery(c);

        return query.getResultList();
    }

}
