/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.customFilters;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.hql.internal.ast.ASTQueryTranslatorFactory;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.hql.spi.QueryTranslatorFactory;

/**
 * Clase Utilidad para la generación de consultas
 *
 * @author Fernando
 */
public class Consulta {

    private final static Logger log = Logger.getLogger(Consulta.class.getName());
    private StringBuilder sb;
    private List<Filtro> filtros;
    private int filas;

    /**
     * Método para inicializar unaconsulta
     *
     * @param clase
     */
    public Consulta(String clase) {
        sb = new StringBuilder();
        sb.append("SELECT x FROM ");
        sb.append(clase);
        sb.append(" x");
    }

    /**
     * Métodos para agregar Filtors
     *
     * @param fil
     */
    public void agregarFiltrado(List<Filtro> fil) {
        filtros = fil;
        int order = 0;
        if (filtros != null) {
            if (filtros.size() > 0) {
                sb.append(" WHERE ");
                for (Filtro f : filtros) {
                    if (f.getTipo().equals("ORDER")) {
                        order = 1;
                    } else {
                        sb.append(f.obtenerConsultaParcial());
                        sb.append(" AND ");
                    }
                }
                sb.delete(sb.length() - 4, sb.length());
                if (order == 1) {
                    sb.append(" ");
                    for (Filtro f : filtros) {
                        if (f.getTipo().equals("ORDER")) {
                            sb.append(f.obtenerConsultaParcial());
                        }
                    }

                }
            }
        }
    }

    /**
     * Método para crear una consulta
     *
     * @param em
     * @param first
     * @param count
     * @return
     */
    public Query crearConsulta(EntityManager em, int first, int count) {
        Query q = em.createQuery(sb.toString());
        int orderby = sb.toString().indexOf("ORDER");
        String countQuery = "";
        if (orderby == -1) {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)");
        } else {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)").substring(0, orderby + 5);
        }
        Query q1 = em.createQuery(countQuery);
        for (Filtro f : filtros) {
            if (f.getTipo().equals("BETWEENFECHA")) {
                if (f.getKey1().trim().length() != 0 && f.getKey2().trim().length() != 0) {
                    q.setParameter(f.parametroKey1(), f.getValue1());
                    q.setParameter(f.parametroKey2(), f.getValue2());
                    q1.setParameter(f.parametroKey1(), f.getValue1());
                    q1.setParameter(f.parametroKey2(), f.getValue2());
                }
            } else if (!f.getTipo().equals("ORDER")) {
                if (f.getKey1().trim().length() != 0) {
                    if (f.getTipo().equals("LIKE")) {
                        f.setValue1("%" + f.getValue1().toString().toUpperCase() + "%");
                    }
                    if (f.getTipo().equals("SUB")) {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                    } else {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                    }
                }
                if (!f.getTipo().equals("SUB")) {
                    if (f.getKey2().trim().length() != 0) {
                        if (f.getTipo().equals("LIKE")) {
                            f.setValue2(f.getValue2().toString().toUpperCase() + "%");
                        }
                        q.setParameter(f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey2(), f.getValue2());
                    }
                }
            }
        }
        filas = Integer.parseInt(q1.getSingleResult().toString());
        q.setMaxResults(count);
        q.setFirstResult(first);
        return q;
    }

    public Object[] generarLazyQuery(EntityManager em, int first, int count) {
        Query q = em.createQuery(sb.toString());
        int orderby = sb.toString().replace("SELECT x", "SELECT Count(x)").indexOf("ORDER");
        String countQuery = "";
        if (orderby == -1) {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)");
        } else {
            countQuery = sb.toString().replace("SELECT x", "SELECT Count(x)").substring(0, orderby - 1);
        }
        Query q1 = em.createQuery(countQuery);
        for (Filtro f : filtros) {
            if (f.getTipo().equals("BETWEENFECHA")) {
                if (f.getKey1().trim().length() != 0 && f.getKey2().trim().length() != 0) {
                    q.setParameter(f.parametroKey1(), f.getValue1());
                    q.setParameter(f.parametroKey2(), f.getValue2());
                    q1.setParameter(f.parametroKey1(), f.getValue1());
                    q1.setParameter(f.parametroKey2(), f.getValue2());
                }
            } else if (!f.getTipo().equals("ORDER")) {
                if (f.getKey1().trim().length() != 0) {
                    if (f.getTipo().equals("LIKE")) {
                        f.setValue1("%" + f.getValue1().toString().toUpperCase() + "%");
                    }
                    if (f.getTipo().equals("SUB")) {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                    } else {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q1.setParameter(f.parametroKey1(), f.getValue1());
                    }
                }
                if (!f.getTipo().equals("SUB")) {
                    if (f.getKey2().trim().length() != 0) {
                        if (f.getTipo().equals("LIKE")) {
                            f.setValue2(f.getValue2().toString().toUpperCase() + "%");
                        }
                        q.setParameter(f.parametroKey2(), f.getValue2());
                        q1.setParameter(f.parametroKey2(), f.getValue2());
                    }
                }
            }
        }

        Session sesion = em.unwrap(Session.class);
        org.hibernate.Query qh = q.unwrap(org.hibernate.Query.class);
        final QueryTranslatorFactory translatorFactory = new ASTQueryTranslatorFactory();
        final SessionFactoryImplementor factory
                = (SessionFactoryImplementor) sesion.getSessionFactory();
        final QueryTranslator translator = translatorFactory.
                createQueryTranslator(
                        qh.getQueryString(),
                        qh.getQueryString(),
                        Collections.EMPTY_MAP, factory, null
                );

        translator.compile(Collections.EMPTY_MAP, false);

        q.setMaxResults(count);
        q.setFirstResult(first);

        for (Object col : translator.getParameterTranslations().getNamedParameterNames().toArray()) {
            log.info("PARAMETRO: " + col);
            log.info("ORDINAL: " + translator.getParameterTranslations().getNamedParameterSqlLocations(col.toString())[0]);
        }

        return new Object[]{q.getResultList(), Integer.parseInt(q1.getSingleResult().toString()), this.getSQLString(translator)};
    }

    public String getSQLString(QueryTranslator qt) {
        String sql = qt.getSQLString();
        TreeMap<Integer, Object> tokenMap = new TreeMap<Integer, Object>();
        for (Iterator iter = qt.getParameterTranslations().getNamedParameterNames().iterator(); iter.hasNext();) {
            String nombreParam = (String) iter.next();
            int[] ordinalParam = qt.getParameterTranslations().getNamedParameterSqlLocations(nombreParam);
            for (int orden : ordinalParam) {
                int indiceParamValue = existeFiltroIndex(nombreParam);
                if (indiceParamValue != -1) {
                    Object value = this.filtros.get(indiceParamValue).getValue1();
                    tokenMap.put(orden, value);
                }
            }
        }

        for (Object value : tokenMap.values()) {
            if (value instanceof String) {
                sql = sql.replaceFirst("\\?", "'" + value + "' ");
            }
            if (value instanceof Short || value instanceof Integer || value instanceof Long) {
                sql = sql.replaceFirst("\\?", "" + value + " ");
            }
            if(value instanceof Date){
                SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                sql = sql.replaceFirst("\\?", "'" + df.format(value) + "' ");
            }
        }
        return sql;
    }

    private int existeFiltroIndex(String key) {
        int cont = 0;
        for (Filtro f : this.filtros) {
            String datakey[]=f.getKey1().split("\\.");
            if (datakey[datakey.length-1].equals(key)) {
                return cont;
            }
            cont++;
        }
        return -1;
    }

    /**
     * Método para crear una consulta
     *
     * @param em
     * @return
     */
    public Query crearConsulta(EntityManager em) {
        Query q = em.createQuery(sb.toString());
        for (Filtro f : filtros) {
            if (!f.getTipo().equals("ORDER")) {
                if (f.getKey1().trim().length() != 0) {
                    if (f.getTipo().equals("LIKE")) {
                        f.setValue1(f.getValue1().toString().toUpperCase() + "%");
                    }
                    if (f.getTipo().equals("SUB")) {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                        q.setParameter(f.parametroKey2() + f.parametroKey2(), f.getValue2());
                    } else {
                        q.setParameter(f.parametroKey1(), f.getValue1());
                    }
                }
                if (!f.getTipo().equals("SUB")) {
                    if (f.getKey2().trim().length() != 0) {
                        if (f.getTipo().equals("LIKE")) {
                            f.setValue2(f.getValue2().toString().toUpperCase() + "%");
                        }
                        q.setParameter(f.parametroKey2(), f.getValue2());
                    }
                }
            }
        }

        return q;
    }

    /**
     * @return the filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }
}
