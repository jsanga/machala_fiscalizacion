/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.BusquedaPredial;
import com.dadoco.cat.model.CatPredioRuralTerreno;
import com.dadoco.cat.model.Integrante;
import com.dadoco.cat.model.Predio;
import com.dadoco.common.lazyModel.InfoModel;
import com.dadoco.common.lazyModel.LazyDataModelPredioBusqueda;
import com.dadoco.common.service.AbstractFacade;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.common.util.UtilTexto;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class PredioService extends AbstractFacade<Predio> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PredioService.class);
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;
    @EJB
    private ManagerService aclServices;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PredioService() {
        super(Predio.class);
    }

    public void editarPredioTemporal(Predio p) {

        em.merge(p);
        em.flush();
    }

    public List<Predio> findByFilter() {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Predio> c = cb.createQuery(Predio.class);
        Root<Predio> predio = c.from(Predio.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

//        predicates.add(
//                cb.equal(predio.get("estado"), 0));
        c.select(predio);

        TypedQuery<Predio> query = getEntityManager().createQuery(c);

        return query.getResultList();
    }
    
    public Boolean existePredio(String parroquia, String zona, String sector, String manzana, String solar, String numBloque, String codPhv, String codPhh){
        try{
            Object[] params = {"07", "01", parroquia, zona, sector, manzana, solar, numBloque, codPhv, codPhh};
            List predios = ((List)findByNamedQuery("Predio.findByCodes", params));
            if(predios != null || !predios.isEmpty())
                return false;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public List<CatPredioRuralTerreno> getListaClaseTerrenoRural(int i, Predio p){
        List result = null;
                
        switch(i){
            case 1:
                result = aclServices.findListByParameters("SELECT r FROM CatPredioRuralTerreno r WHERE r.predio = :predio AND r.claseTierra is not null", new String[]{"predio"}, new Object[]{p});
                break;
            case 2:
                result = aclServices.findListByParameters("SELECT r FROM CatPredioRuralTerreno r WHERE r.predio = :predio AND r.cultivosAnuales is not null", new String[]{"predio"}, new Object[]{p});
                break;
            case 3:
                result = aclServices.findListByParameters("SELECT r FROM CatPredioRuralTerreno r WHERE r.predio = :predio AND r.cultivosPerennes is not null", new String[]{"predio"}, new Object[]{p});
                break;
            case 4:
                result = aclServices.findListByParameters("SELECT r FROM CatPredioRuralTerreno r WHERE r.predio = :predio AND r.plantacionesForestales is not null", new String[]{"predio"}, new Object[]{p});
                break;
            case 5:
                result = aclServices.findListByParameters("SELECT r FROM CatPredioRuralTerreno r WHERE r.predio = :predio AND r.semovientes is not null", new String[]{"predio"}, new Object[]{p});
                break;
            case 6:
                result = aclServices.findListByParameters("SELECT r FROM CatPredioRuralTerreno r WHERE r.predio = :predio AND r.avesDeCorral is not null", new String[]{"predio"}, new Object[]{p});
                break;
        }
        if(result != null){
            for(CatPredioRuralTerreno t : (List<CatPredioRuralTerreno>)result){
                t.setIdTemporal(Short.parseShort(""+t.getId()));
            }
        }
        return result == null ? new ArrayList() : result;
    }

    public List<Integer> getIDs(int start) {

        Query query = em.createNamedQuery("Predio.findAllIds");
        query.setParameter(1, start);

        return (List<Integer>) query.getResultList();
    }

    public Predio findByCodigoCatastro(String codigo) {
        Query q = getEntityManager().createNamedQuery("Predio.findByClaveCatastral");
        q.setParameter(1, codigo);
        List<Predio> lista = q.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }
    }
    
    public Predio findByCodigoCatastroSF(String codigo) {
        Query q = getEntityManager().createNamedQuery("Predio.findByClaveCatastralSF");
        q.setParameter(1, codigo);
        List<Predio> lista = q.getResultList();
        if (lista.size() > 0) {
            return lista.get(0);
        } else {
            return null;
        }
    }

    public List<Integer> getPHIDs() {
        return (List<Integer>) em.createNamedQuery("Predio.findAllIdsPH").getResultList();
    }

    public List<Predio> getPrediosPorPH(short provincia, short canton, short parroquia, short zona, short sector, short manzana, short solar, short ph) {

        Object[] params = {provincia, canton, parroquia, zona, sector, manzana, solar, ph};

        List<Predio> predios = findByNamedQuery("Predio.findAllByPH", params);
        return predios;
    }

    public List<String> coordenadas() {

        Query query = em.createNativeQuery("SELECT ST_X(ST_Centroid(ST_Transform(p.geom,4326))), ST_Y(ST_Centroid(ST_Transform(p.geom,4326))) from map.predios p where p.cod_catast = ?");
        query.setParameter(1, "2-11-39-1");

        List<String> result = query.getResultList();

        for (String s : result) {
            log.error("Valor del centro: " + s);
        }

        return result;
    }

    public boolean getEsFichaMadre(Predio predio) {

        return predio.getTipoPropiedad() == 2;
    }

    public int NumeroUltimoBloque(Predio predio) {

        if (predio.getBloques().isEmpty()) {
            return 0;
        }

        Object[] params = new Object[1];
        params[0] = predio.getId();

        Query query = em.createQuery("SELECT MAX(b.numeroBloque) FROM Bloque b JOIN b.predio p WHERE p.id = ?1");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        int max = (int) query.getSingleResult();

        return max;
    }

    public List<Predio> buscarPrediosLikeEdificio(String like) {

        Query q;
        q = em.createNamedQuery("Predio.findByLikeEdificio");
        q.setParameter("edificio", "%" + like.toUpperCase() + "%");

        List<Predio> result = (List<Predio>) q.getResultList();

        if (result.isEmpty()) {
            return new ArrayList<Predio>();
        }
        log.error("cantodad de elementos por edificio:" + result.size());
        return result;

    }

    public List<Predio> buscarPrediosLikeUbicacion(String like) {

        Query q;

        q = em.createNamedQuery("Predio.findByLikeUbicacion");
        q.setParameter("ubicacion", "%" + like.toUpperCase() + "%");

        List<Predio> result = (List<Predio>) q.getResultList();

        if (result.isEmpty()) {
            return new ArrayList<Predio>();
        }

        return result;

    }

    public List<Predio> buscarPrediosLikeCodigoInicial(String like) {

        Query q;
        q = em.createNamedQuery("Predio.findByLikeSector");
        q.setParameter("sector", "%" + like.toUpperCase() + "%");

        List<Predio> result1 = (List<Predio>) q.getResultList();

//        if (!result1.isEmpty()) {
//            return result1;
//        }
        q = em.createNamedQuery("Predio.findByLikeManzana");
        q.setParameter("manzana", "%" + like.toUpperCase() + "%");

        List<Predio> result2 = (List<Predio>) q.getResultList();

//        if (!result2.isEmpty()) {
//            return result2;
//        }
        q = em.createNamedQuery("Predio.findByLikeSolar");
        q.setParameter("solar", "%" + like.toUpperCase() + "%");

        List<Predio> result3 = (List<Predio>) q.getResultList();

//        if (!result3.isEmpty()) {
//            return result3;
//        }
        log.error("Lista por sector: " + result1.size());
        log.error("Lista por manzana: " + result2.size());
        log.error("Lista por solar: " + result3.size());
        log.error("Lista por Like: " + like.toUpperCase());

        if (result1.isEmpty() && result2.isEmpty() && result3.isEmpty()) {
            return new ArrayList<Predio>();
        } else if (result1.isEmpty() && !result2.isEmpty() && result3.isEmpty()) {
            return result2;
        } else if (!result1.isEmpty() && result2.isEmpty() && result3.isEmpty()) {
            return result1;
        } else if (result1.isEmpty() && result2.isEmpty() && !result3.isEmpty()) {
            return result3;
        } else if (result1.isEmpty() && !result2.isEmpty() && !result3.isEmpty()) {

            for (Predio p : result3) {
                if (!result2.contains(p)) {
                    result2.add(p);
                }
            }
            return result2;
        } else if (!result1.isEmpty() && !result2.isEmpty() && result3.isEmpty()) {

            for (Predio p : result1) {
                if (!result2.contains(p)) {
                    result2.add(p);
                }
            }
            return result1;
        } else if (!result1.isEmpty() && result2.isEmpty() && !result3.isEmpty()) {

            for (Predio p : result1) {
                if (!result3.contains(p)) {
                    result3.add(p);
                }
            }

            return result3;
        } else {
            for (Predio p : result1) {
                if (!result2.contains(p)) {
                    result2.add(p);
                }
            }
            for (Predio p : result3) {
                if (!result2.contains(p)) {
                    result2.add(p);
                }
            }
            return result2;
        }

        // return new ArrayList<Predio>();
    }

    public List<Predio> findByEdificio(String edificio, String piso, String dpto) {

        System.out.println("Edificio: " + edificio);
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Predio> c = cb.createQuery(Predio.class);
        EntityType<Predio> type = em.getMetamodel().entity(Predio.class);

        Root<Predio> predio = c.from(Predio.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        predicates.add(
                cb.equal(predio.get("estado"), 0));
//        predicates.add(
//                cb.equal(predio.get("terreno").get("tipoLote"), 2));

        if (edificio != null) {
            predicates.add(
                    cb.like(cb.upper(predio.get(type.getDeclaredSingularAttribute("edificio", String.class))), "%" + edificio.toUpperCase() + "%"));
        }
        if (piso != null) {
            predicates.add(
                    cb.like(cb.upper(predio.get(type.getDeclaredSingularAttribute("piso", String.class))), "%" + piso.toUpperCase() + "%"));
        }
        if (dpto != null) {
            predicates.add(
                    cb.like(cb.upper(predio.get(type.getDeclaredSingularAttribute("departamento", String.class))), "%" + dpto.toUpperCase() + "%"));
        }

        c.select(predio).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Predio> query = getEntityManager().createQuery(c);

        return query.getResultList();
    }

    //<editor-fold defaultstate="collapsed" desc="Editar masivamente la Manzana">
    public int editarManzanaMasivamente(boolean editManzana, String cod_parroquia, String cod_zona, String cod_sector, String cod_manzana, String nombre) {
        String sql = "";
        try {
            if (editManzana) {
                sql = "UPDATE public.cat_predio "
                        + "   SET manzana=? where cod_parroquia=? and cod_zona=? and cod_sector=? and cod_manzana=?;";
            } else {
                sql = "UPDATE public.cat_terreno "
                        + "   SET barrio=? where cod_parroquia=? and cod_zona=? and cod_sector=? and cod_manzana=?;";
            }
            Query query = em.createNativeQuery(sql)
                    .setParameter(1, nombre)
                    .setParameter(2, cod_parroquia)
                    .setParameter(3, cod_zona)
                    .setParameter(4, cod_sector)
                    .setParameter(5, cod_manzana);
            int result = query.executeUpdate();
            return result;
        } catch (Exception ex) {
            return 0;
        }
    }
//</editor-fold> //Fsan

    //<editor-fold defaultstate="collapsed" desc="Editar Clave Catastral">
    public int editarClaveCatastralP(String cod_parroquia, String cod_zona, String cod_sector, String cod_manzana, String cod_solar) {
        String claveCatastral = cod_parroquia + "-" + cod_zona + "-" + cod_sector + "-" + cod_manzana + "-" + cod_solar + "-0-0-0";
        int result = 0;
        try {

            Query q1 = em.createNativeQuery("UPDATE public.cat_predio "
                    + "   SET clave_catastral=?"
                    + " where cod_parroquia=? and cod_zona=? and cod_sector=? and cod_manzana=? and cod_solar=?;")
                    .setParameter(1, claveCatastral)
                    .setParameter(2, cod_parroquia)
                    .setParameter(3, cod_zona)
                    .setParameter(4, cod_sector)
                    .setParameter(5, cod_manzana)
                    .setParameter(6, cod_solar);

            result = q1.executeUpdate();

            return result;

        } catch (Exception ex) {
            return 0;
        }
    }

    public int editarClaveCatastralT(String cod_parroquia, String cod_zona, String cod_sector, String cod_manzana, String cod_solar, Predio p) {
        int result = 0;
        try {

            Query query2 = em.createNativeQuery("UPDATE public.cat_terreno "
                    + "   SET cod_parroquia=?,cod_zona=?,cod_sector=?,cod_manzana=?, cod_solar=?"
                    + " where cod_parroquia=? and cod_zona=? and cod_sector=? and cod_manzana=? and cod_solar=?;")
                    .setParameter(1, cod_parroquia)
                    .setParameter(2, cod_zona)
                    .setParameter(3, cod_sector)
                    .setParameter(4, cod_manzana)
                    .setParameter(5, cod_solar)
                    .setParameter(6, p.getTerreno().getTerrenoPK().getCodParroquia())
                    .setParameter(7, p.getTerreno().getTerrenoPK().getCodZona())
                    .setParameter(8, p.getTerreno().getTerrenoPK().getCodSector())
                    .setParameter(9, p.getTerreno().getTerrenoPK().getCodManzana())
                    .setParameter(10, p.getTerreno().getTerrenoPK().getCodSolar());
            result = query2.executeUpdate();

            return result;

        } catch (Exception ex) {
            return 0;
        }
    }
//</editor-fold> //Fsan
    
    //<editor-fold defaultstate="collapsed" desc="Obteniene el Ultimo codigo del Predio de una Manzana">
    public short ultimoIdPredioM(short parroquia, short zona, short sector, short manzana)
    {
     short codigo=0;
     try{
        Query q1=em.createNativeQuery("select MAX(p.cod_solar) from public.cat_predio p" +
"		inner join cat_terreno t on (p.cod_provincia, p.cod_canton, p.cod_parroquia, p.cod_zona, p.cod_sector, p.cod_manzana, p.cod_solar)" +
"		=(t.cod_provincia,t.cod_canton,t.cod_parroquia,t.cod_zona,t.cod_sector,t.cod_manzana,t.cod_solar) WHERE "
                + "p.cod_parroquia=?1 and p.cod_zona=?2 and p.cod_sector=?3 and p.cod_manzana=?4"
             )
                .setParameter("1", parroquia)
                .setParameter("2", zona)
                .setParameter("3", sector)
                .setParameter("4", manzana);
        codigo=(short)q1.getSingleResult();
       return codigo; 
    }catch(Exception ex)
    {
      return codigo; 
    }
        
    }
    //</editor-fold>

    public List<Predio> busquedaPorCoincidencia(String busqueda, int first, int pageSize) {
        StringBuilder sql1 = new StringBuilder();
        sql1.append("SELECT p.id FROM Predio p INNER JOIN p.propietarios d where p.claveCatastral LIKE :dato OR p.claveAnterior LIKE :dato OR (d IN (SELECT c FROM Contribuyente c WHERE c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato or c.identificacion like :dato)) OR p.nombreEdificio like :dato OR p.manzana LIKE :dato");

        StringBuilder sql2 = new StringBuilder();
        sql2.append("SELECT q.id FROM Predio q where q.claveCatastral LIKE :dato OR q.claveAnterior LIKE :dato OR q.nombreEdificio like :dato OR q.manzana LIKE :dato");

        StringBuilder sqlUnion = new StringBuilder();
        sqlUnion.append("SELECT DISTINCT x FROM Predio x WHERE x.id IN (").append(sql1).append(")");
        sqlUnion.append(" OR x.id IN (").append(sql2).append(") ORDER BY x.claveCatastral DESC");

        busqueda = "%" + busqueda.trim().toUpperCase() + "%";

        Query q = getEntityManager().createQuery(sqlUnion.toString());
        q.setParameter("dato", busqueda);

        return q.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int countPorCoincidencia(String busqueda) {
        StringBuilder sql1 = new StringBuilder();
        sql1.append("SELECT p.id FROM Predio p INNER JOIN p.propietarios d where p.claveCatastral LIKE :dato OR p.claveAnterior LIKE :dato OR (d IN (SELECT c FROM Contribuyente c WHERE c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato or c.identificacion like :dato)) OR p.nombreEdificio like :dato OR p.manzana LIKE :dato");

        StringBuilder sql2 = new StringBuilder();
        sql2.append("SELECT q.id FROM Predio q where q.claveCatastral LIKE :dato OR q.claveAnterior LIKE :dato OR q.nombreEdificio like :dato OR q.manzana LIKE :dato");

        StringBuilder sqlUnion = new StringBuilder();
        sqlUnion.append("SELECT Count(DISTINCT x ) FROM Predio x WHERE x.id IN (").append(sql1).append(")");
        sqlUnion.append(" OR x.id IN (").append(sql2).append(")");

        busqueda = "%" + busqueda.trim().toUpperCase() + "%";

        Query q = getEntityManager().createQuery(sqlUnion.toString());
        q.setParameter("dato", busqueda);

        return ((Long) q.getSingleResult()).intValue();

    }
    
    public boolean existePredioClaveAnterior(String claveAnterior){
        Query q;
        q = em.createNamedQuery("Predio.findByClaveAnterior");
        q.setParameter(1,  claveAnterior );
        
        if(q.getResultList() == null || q.getResultList().size() == 0)
            return false;
        return true;
    }

    public List<Predio> busquedaPorCoincidencia(String busqueda, int first, int pageSize, Map<String, Object> filtro) {
        log.info("INICIO DE CONSULTA busqueda: " + System.currentTimeMillis());
        Date fechaBusqueda = UtilTexto.isDate(busqueda, "00:00:00");
        Date fechaFinalBusqueda = UtilTexto.isDate(busqueda, "23:59:59");

        if (busqueda != null) {
            busqueda = "%" + busqueda.toUpperCase() + "%";
        } else {
            busqueda = "%%";
        }

        StringBuilder sql1 = new StringBuilder();
        sql1.append("SELECT DISTINCT p FROM Predio p ");
        if (fechaBusqueda == null) {
            sql1.append("LEFT JOIN p.propietarios c where ((p.claveCatastral LIKE :dato OR p.claveAnterior LIKE :dato OR p.nombreEdificio like :dato OR p.manzana LIKE :dato) OR (c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato OR c.identificacion like :dato))");
        } else {
            //sql1.append(" WHERE (p.fechaCreacion >= :finicio AND p.fechaCreacion <=:ffin) OR (p.fechaModificacion >= :finicio AND p.fechaModificacion <= :ffin)");
            sql1.append(" WHERE (p.fechaCreacion BETWEEN :finicio AND :ffin) OR (p.fechaModificacion BETWEEN :finicio AND :ffin)");
        }
        if (!filtro.isEmpty()) {
            sql1.append(" AND (");
            for (Map.Entry<String, Object> entry : filtro.entrySet()) {
                String key = entry.getKey();
                if (key.contains("terreno")) {
                    sql1.append(" p.").append(key).append(" =:").append(key.replace(".", ""));
                } else {
                    sql1.append(" p.").append(key).append(" LIKE :").append(key.replace(".", ""));
                }
                sql1.append(" AND");
            }
            sql1 = new StringBuilder(sql1.substring(0, sql1.length() - 3));
            sql1.append(")");
        }

        sql1.append(" ORDER BY p.claveCatastral DESC");
//        log.info("CONSULTA EJECUTADA:" + sqlConsulta.toString());
        Query q = getEntityManager().createQuery(sql1.toString());
        if (fechaBusqueda == null) {
            q.setParameter("dato", busqueda);
        } else {
            q.setParameter("finicio", fechaBusqueda);
            q.setParameter("ffin", fechaFinalBusqueda);
        }

        if (!filtro.isEmpty()) {
            for (Map.Entry<String, Object> entry : filtro.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.contains("terreno")) {
                    q.setParameter(key.replace(".", ""), value.toString());
                } else {
                    value = "%" + value.toString() + "%";
                    q.setParameter(key.replace(".", ""), value.toString());
                }
            }
        }

//        log.info("CONSULTA EJECUTADA:" + sqlConsulta.toString());
        List<Predio> lista = q.setFirstResult(first).setMaxResults(pageSize).getResultList();
        log.info("FIN DE CONSULTA busqueda: " + System.currentTimeMillis());
        return lista;
    }

    public int countPorCoincidencia(String busqueda, Map<String, Object> filtro) {
        log.info("INICIO DE CONSULTA busqueda COUNT: " + System.currentTimeMillis());
        Date fechaBusqueda = UtilTexto.isDate(busqueda, "00:00:00");
        Date fechaFinalBusqueda = UtilTexto.isDate(busqueda, "23:59:59");

        if (busqueda != null) {
            busqueda = "%" + busqueda.toUpperCase() + "%";
        } else {
            busqueda = "%%";
        }

        StringBuilder sql1 = new StringBuilder();
        sql1.append("SELECT Count(DISTINCT p) FROM Predio p ");
        if (fechaBusqueda == null) {
            sql1.append("LEFT JOIN p.propietarios c where ((p.claveCatastral LIKE :dato OR p.claveAnterior LIKE :dato OR p.nombreEdificio like :dato OR p.manzana LIKE :dato) OR (c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato OR c.identificacion like :dato))");
        } else {
            //sql1.append(" WHERE (p.fechaCreacion >= :finicio AND p.fechaCreacion <=:ffin) OR (p.fechaModificacion >= :finicio AND p.fechaModificacion <= :ffin)");
            sql1.append(" WHERE (p.fechaCreacion BETWEEN :finicio AND :ffin) OR (p.fechaModificacion BETWEEN :finicio AND :ffin)");
        }
        if (!filtro.isEmpty()) {
            sql1.append(" AND (");
            for (Map.Entry<String, Object> entry : filtro.entrySet()) {
                String key = entry.getKey();
                if (key.contains("terreno")) {
                    sql1.append(" p.").append(key).append(" =:").append(key.replace(".", ""));
                } else {
                    sql1.append(" p.").append(key).append(" LIKE :").append(key.replace(".", ""));
                }
                sql1.append(" AND");
            }
            sql1 = new StringBuilder(sql1.substring(0, sql1.length() - 3));
            sql1.append(")");
        }

//        log.info("CONSULTA COUNT EJECUTADA:" + sqlConsulta.toString());
        Query q = getEntityManager().createQuery(sql1.toString());
        if (fechaBusqueda == null) {
            q.setParameter("dato", busqueda);
        } else {
            q.setParameter("finicio", fechaBusqueda);
            q.setParameter("ffin", fechaFinalBusqueda);
        }
        if (!filtro.isEmpty()) {
            for (Map.Entry<String, Object> entry : filtro.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.contains("terreno")) {
                    q.setParameter(key.replace(".", ""), new java.lang.Short(value.toString()));
                } else {
                    value = "%" + value.toString() + "%";
                    q.setParameter(key.replace(".", ""), value.toString());
                }
            }
        }
//        log.info("CONSULTA COUNT EJECUTADA:" + sqlConsulta.toString());
        int contador = ((Long) q.getSingleResult()).intValue();
        log.info("FINAL DE CONSULTA busqueda COUNT: " + System.currentTimeMillis());
        return contador;
    }

    public InfoModel<Predio> busquedaLazy(String busqueda, int first, int pageSize, Map<String, Object> filtro) {

        Date fechaBusqueda = UtilTexto.isDate(busqueda, "00:00:00");
        Date fechaFinalBusqueda = UtilTexto.isDate(busqueda, "23:59:59");

        if (busqueda != null) {
            busqueda = "%" + busqueda.toUpperCase() + "%";
        } else {
            busqueda = "%%";
        }

        StringBuilder sqlConsulta = new StringBuilder();

        sqlConsulta.append("SELECT p FROM Predio p ");
        if (fechaBusqueda == null) {
            sqlConsulta.append("LEFT JOIN p.propietarios c where ((p.claveCatastral LIKE :dato OR p.claveAnterior LIKE :dato OR p.nombreEdificio like :dato OR p.manzana LIKE :dato) OR (c.apellidoMaterno LIKE :dato OR c.apellidoPaterno LIKE :dato OR c.nombre LIKE :dato OR c.identificacion like :dato))");
        } else {
            sqlConsulta.append(" WHERE ((p.fechaCreacion BETWEEN :finicio AND :ffin) OR (p.fechaModificacion BETWEEN :finicio AND :ffin)) ");
        }
        if (!filtro.isEmpty()) {
            sqlConsulta.append(" AND (");
            for (Map.Entry<String, Object> entry : filtro.entrySet()) {
                String key = entry.getKey();
                if (key.contains("terreno")) {
                    sqlConsulta.append(" p.").append(key).append(" =:").append(key.replace(".", ""));
                } else {
                    sqlConsulta.append(" p.").append(key).append(" LIKE :").append(key.replace(".", ""));
                }
                sqlConsulta.append(" AND");
            }
            sqlConsulta = new StringBuilder(sqlConsulta.substring(0, sqlConsulta.length() - 3));
            sqlConsulta.append(")");
        }
        StringBuilder sqlCount = new StringBuilder(sqlConsulta.toString().replace("SELECT p", "SELECT Count(p)"));

        sqlConsulta.append(" ORDER BY p.claveCatastral DESC");

        Query q = getEntityManager().createQuery(sqlConsulta.toString());
        Query qCount = getEntityManager().createQuery(sqlCount.toString());

        Map<String, Object> parametrros = new HashMap<>();

        if (fechaBusqueda == null) {
            q.setParameter("dato", busqueda);
            qCount.setParameter("dato", busqueda);
            parametrros.put("dato", busqueda);
        } else {
            q.setParameter("finicio", fechaBusqueda);
            q.setParameter("ffin", fechaFinalBusqueda);
            qCount.setParameter("finicio", fechaBusqueda);
            qCount.setParameter("ffin", fechaFinalBusqueda);
            parametrros.put("finicio", fechaBusqueda);
            parametrros.put("ffin", fechaFinalBusqueda);
        }

        if (!filtro.isEmpty()) {
            for (Map.Entry<String, Object> entry : filtro.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key.contains("terreno")) {
                    q.setParameter(key.replace(".", ""), new java.lang.Short(value.toString()));
                    qCount.setParameter(key.replace(".", ""), new java.lang.Short(value.toString()));
                    parametrros.put(key.replace(".", ""), new java.lang.Short(value.toString()));
                } else {
                    value = "%" + value.toString() + "%";
                    q.setParameter(key.replace(".", ""), value.toString());
                    qCount.setParameter(key.replace(".", ""), value.toString());
                    parametrros.put(key.replace(".", ""), value.toString());
                }
            }
        }

        InfoModel<Predio> info = new InfoModel<>();
        info.setListaDatos(q.setFirstResult(first).setMaxResults(pageSize).getResultList());
        info.setCount(((Long) qCount.getSingleResult()).intValue());
        info.setJpqlQuery(sqlConsulta.toString());
        info.setParametros(parametrros);

        return info;
    }

    public List<String> exportarExcel(InfoModel<BusquedaPredial> info, VariableService varser,String reportSource) throws JRException {
        StringBuilder consulta = new StringBuilder(info.getJpqlQuery());
        consulta.insert(consulta.indexOf("ORDER"), " AND p.digitador.id=:digitador ");
        log.info("SQL INICIO REPORTE: " + consulta + " TIEMPO: " + System.currentTimeMillis());
        List<String> listaRutas = new LinkedList<>();
        for (Integrante i : listaIntegrantes()) {
            try {
                Query q = getEntityManager().createQuery(consulta.toString());
                for (Map.Entry<String, Object> entry : info.getParametros().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    q.setParameter(key, value);
                }
                q.setParameter("digitador", i.getId());
                
                List<Predio> l = q.getResultList();
                
                if (l.size() > 0) {
                    log.info("EJECUCION DE SQL REPORTE: " + consulta + " TIEMPO: " + System.currentTimeMillis());
                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(l);
                    //JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, null, beanCollectionDataSource);
                    String fileName = JasperFillManager.fillReportToFile(reportSource, null, beanCollectionDataSource);
                    String reportDest = Util.directorio_reportes+File.separator+new Date().toString()+"predios_"+ i.getNombre() + ".xls";
                    
                    JRXlsxExporter exporter = new JRXlsxExporter();

                    exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                            fileName);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                            reportDest);
                    

                    exporter.exportReport();
                    listaRutas.add(reportDest);
                    //JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
                }
                //lista.add(jasperPrint);
            } catch (JRException ex) {
                ex.printStackTrace();
            }
        }
        return listaRutas;
    }

    public List<Integrante> listaIntegrantes() {
        Query q = getEntityManager().createNamedQuery("Integrante.findByTipo");
        q.setParameter(1, (short) 3);
        return q.getResultList();
    }

    
    public List<String> exportarExcel(InfoModel<BusquedaPredial> info, VariableService varser,String reportSource,LazyDataModelPredioBusqueda ldm) throws JRException {
        StringBuilder consulta = new StringBuilder(info.getJpqlQuery());
        consulta.insert(consulta.indexOf("ORDER"), " AND p.digitador.id=:digitador ");
        log.info("SQL INICIO REPORTE: " + consulta + " TIEMPO: " + System.currentTimeMillis());
        List<String> listaRutas = new LinkedList<>();
        for (Integrante i : listaIntegrantes()) {
            try {
                Query q = getEntityManager().createQuery(consulta.toString());
                for (Map.Entry<String, Object> entry : info.getParametros().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    q.setParameter(key, value);
                }
                q.setParameter("digitador", i.getId());
                
                List<Predio> l = q.getResultList();
                List<BusquedaPredial> lb=ldm.getBusquedaPredial(l);
                l=null;
                if (lb.size() > 0) {
                    log.info("EJECUCION DE SQL REPORTE: " + consulta + " TIEMPO: " + System.currentTimeMillis());
                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lb);
                    //JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, null, beanCollectionDataSource);
                    String fileName = JasperFillManager.fillReportToFile(reportSource, null, beanCollectionDataSource);
                    String reportDest = Util.directorio_reportes+File.separator+new Date().toString()+"predios_"+ i.getNombre() + ".xls";
                    
                    JRXlsxExporter exporter = new JRXlsxExporter();

                    exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                            fileName);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                            reportDest);
                    

                    exporter.exportReport();
                    listaRutas.add(reportDest);
                    //JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
                }
                //lista.add(jasperPrint);
            } catch (JRException ex) {
                ex.printStackTrace();
            }
        }
        return listaRutas;
    }
    
    
    public List<String> exportarExcelRustico() throws JRException {
        Connection conn = null;
        Session hibernateSession = this.em.unwrap(Session.class);
        conn = ((SessionImpl)hibernateSession).connection();
        List<String> listaRutas = new LinkedList<>();
        try{
            String fileName = JasperFillManager.fillReportToFile(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/cat/reportes/predios_rusticos_new.jasper"), null, conn);
            String reportDest = Util.directorio_reportes+File.separator+new Date().toString()+"predios_rusticos_NICOLE" + ".xls";

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                    fileName);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    reportDest);

            exporter.exportReport();
            listaRutas.add(reportDest);
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
        return listaRutas;
    }

      public List<String> exportarExcelOK(InfoModel<BusquedaPredial> info, VariableService varser,String reportSource,LazyDataModelPredioBusqueda ldm) throws JRException {
        StringBuilder consulta = new StringBuilder(info.getJpqlQuery());
        consulta.insert(consulta.indexOf("ORDER"), " AND p.digitador.id=:digitador ");
        log.info("SQL INICIO REPORTE: " + consulta + " TIEMPO: " + System.currentTimeMillis());
        List<String> listaRutas = new LinkedList<>();
        for (Integrante i : listaIntegrantes()) {
            try {
                Query q = getEntityManager().createQuery(consulta.toString());
                for (Map.Entry<String, Object> entry : info.getParametros().entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    q.setParameter(key, value);
                }
                q.setParameter("digitador", i.getId());
                
                List<Predio> l = q.getResultList();
                List<BusquedaPredial> lb=ldm.getBusquedaPredial(l);
               
                if (lb.size() > 0) {
                    log.info("EJECUCION DE SQL REPORTE: " + consulta + " TIEMPO: " + System.currentTimeMillis());
                    JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(l);
                    //JasperPrint jasperPrint = JasperFillManager.fillReport(reportSource, null, beanCollectionDataSource);
                    String fileName = JasperFillManager.fillReportToFile(reportSource, null, beanCollectionDataSource);
                    String reportDest = Util.directorio_reportes+File.separator+new Date().toString()+"predios_"+ i.getNombre() + ".xls";
                    
                    JRXlsxExporter exporter = new JRXlsxExporter();

                    exporter.setParameter(JRExporterParameter.INPUT_FILE_NAME,
                            fileName);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                            reportDest);
                    

                    exporter.exportReport();
                    listaRutas.add(reportDest);
                    //JasperExportManager.exportReportToPdfFile(jasperPrint, reportDest);
                }
                 l=null;
                //lista.add(jasperPrint);
            } catch (JRException ex) {
                ex.printStackTrace();
            }
        }
        return listaRutas;
    }


}
