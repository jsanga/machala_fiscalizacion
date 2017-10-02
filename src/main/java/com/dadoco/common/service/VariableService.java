/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.service;

import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.Variable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 *
 * @author dfcalderio
 */
@Singleton
public class VariableService extends AbstractFacade<Variable> {

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @Resource(lookup = "java:/jboss/datasources/MachalaDEV")
    private DataSource dataSource;

    @EJB
    private ManagerService aclServices;
    
    HashMap<String, Short[]> minMax;

    @PostConstruct
    private void init() {
        try {
            minMax = new HashMap<String, Short[]>();
            Statement st = dataSource.getConnection().createStatement();

            ResultSet rs = st.executeQuery("select v.tabla,v.columna,min(vd.valor) as min,max(vd.valor) as max from variable v inner join valor_discreto vd on v.id=vd.id_variable \n"
                    + "group by v.tabla,v.columna");

            while (rs.next()) {
                String _key = rs.getString("tabla") + "-" + rs.getString("columna");
                Short[] data = new Short[2];
                data[0] = rs.getShort("min");
                data[1] = rs.getShort("max");
                minMax.put(_key, data);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VariableService() {
        super(Variable.class);
    }

    public short getMinValue(String tabla, String columna) {
        String key = tabla + "-" + columna;
        Short[] values = minMax.get(key);
        return (values == null) ? 0 : values[0];
    }

    public short getMaxValue(String tabla, String columna) {
        String key = tabla + "-" + columna;
        Short[] values = minMax.get(key);
        return (values == null) ? 0 : values[1];
    }
    
    public String getTextoVariableDiscretaByIdVariableAndValor(Integer idVariable, Short valor, Short anio){
        try{
            return (String)aclServices.find("SELECT v.texto FROM ValorDiscreto v WHERE v.variable = :idVariable AND v.valorDiscretoPK.valor = :valor AND v.valorDiscretoPK.anio = :anio", new String[]{"idVariable","valor", "anio"}, new Object[]{(Variable)(em.find(Variable.class, idVariable)),valor,anio});            
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public List<ValorDiscreto> obtenerValores(String tabla, String columna, short anio) {

        List<ValorDiscreto> result = (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("anio", anio)
                .getResultList();
        
//        for (int i = 0; i < quitar.length; i++) {
//            if(valor)
//        }
        return result;
    }
    public List<ValorDiscreto> obtenerValor(String tabla, String columna, short anio) {
                
        List<ValorDiscreto> result = (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("anio", anio)
                .getResultList();
        
//        for (int i = 0; i < quitar.length; i++) {
//            if(valor)
//        }
        return result;
    }

    public List<ValorDiscreto> obtenerValoresPorCodVariable(int id, short anio) {

        return (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.id=:id AND v.valorDiscretoPK.anio =:anio")
                .setParameter("id", id)
                .setParameter("anio", anio)
                .getResultList();
    }
    
    public List<ValorDiscreto> obtenerValoresPorCodVariablePH(int id, short anio) {

        return (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.id=:id AND v.paraPH=true AND v.valorDiscretoPK.anio =:anio")
                .setParameter("id", id)
                .setParameter("anio", anio)
                .getResultList();
    }
    
    public List<ValorDiscreto> obtenerValores(Variable v, short anio) {
        return obtenerValores(v.getTabla(), v.getColumna(), anio);
    }

    public String obtenerCodigo(String tabla, String columna, short anio) {

        List<ValorDiscreto> lista = (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("anio", anio)
                .getResultList();
        
        String valores = "";
        
        if (!lista.isEmpty()) {
            if (lista.size() == 1) {
                valores += lista.get(0).getValorDiscretoPK().getValor();
            } else {
                for (int i = 0; i < lista.size() - 1; i++) {
                    valores += lista.get(i).getValorDiscretoPK().getValor() + "-";
                }
                valores += lista.get(lista.size() - 1).getValorDiscretoPK().getValor();
            }
        }
        return valores;
    }
    
    public String obtenerCodigoPorIdVariable(int id, short anio) {

        List<ValorDiscreto> lista = (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.id=:id AND v.valorDiscretoPK.anio =:anio")
                .setParameter("id", id)
                .setParameter("anio", anio)
                .getResultList();
        
        String valores = "";
        
        if (!lista.isEmpty()) {
            if (lista.size() == 1) {
                valores += lista.get(0).getValorDiscretoPK().getValor();
            } else {
                for (int i = 0; i < lista.size() - 1; i++) {
                    valores += lista.get(i).getValorDiscretoPK().getValor() + "-";
                }
                valores += lista.get(lista.size() - 1).getValorDiscretoPK().getValor();
            }
        }
        return valores;
    }
    
    public String obtenerCodigoPorIdVariablePH(int id, short anio) {

        List<ValorDiscreto> lista = (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.id=:id AND v.paraPH=true AND v.valorDiscretoPK.anio =:anio")
                .setParameter("id", id)
                .setParameter("anio", anio)
                .getResultList();
        
        String valores = "";
        
        if (!lista.isEmpty()) {
            if (lista.size() == 1) {
                valores += lista.get(0).getValorDiscretoPK().getValor();
            } else {
                for (int i = 0; i < lista.size() - 1; i++) {
                    valores += lista.get(i).getValorDiscretoPK().getValor() + "-";
                }
                valores += lista.get(lista.size() - 1).getValorDiscretoPK().getValor();
            }
        }
        return valores;
    }
    
    public String obtenerCodigoTrue(String tabla, String columna, boolean noTiene, short anio) {

        if(noTiene){
            tabla = "comun";
            columna = "comun";
        }
        
        List<ValorDiscreto> lista = (List<ValorDiscreto>) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("anio", anio)
                .getResultList();
        
        String valores = "";
        
        if (!lista.isEmpty()) {
            if (lista.size() == 1) {
                valores += lista.get(0).getValorDiscretoPK().getValor();
            } else {
                for (int i = 0; i < lista.size() - 1; i++) {
                    valores += lista.get(i).getValorDiscretoPK().getValor() + "-";
                }
                valores += lista.get(lista.size() - 1).getValorDiscretoPK().getValor();
            }
        }
        
        System.out.println("Cadena ed valores: " + valores);
        return valores;
    }

    public ValorDiscreto obtenerValores(String tabla, String columna, short valor, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getSingleResult();

        return result;
    }
    
    
    public Double obtenerCoefInfraestructuraBasica(Terreno t){
        
        Double coeficiente = 0.0;
        int cont = 0;
        
        if(t.getRedAguaPotable() != (short)0 && t.getRedAguaPotable() != (short)1){
            cont = cont + 1;
        }
        if(t.getRedAlcantarillado()!= (short)0 && t.getRedAlcantarillado() != (short)1){
            cont = cont + 1;
        }
        if(t.getRedEnergiaElectrica() != (short)0 && t.getRedEnergiaElectrica()!= (short)3){
            cont = cont + 1;
        }
        
        switch(cont){
            case 3:
                coeficiente = 1.00;
                break;
            
            case 2:
                coeficiente = 0.95;
                break;
                
            default:
                coeficiente = 0.8;
                break;
        }
        return coeficiente;
    }
    
    public Double obtenerCoefInfraestructuraComplementaria(Terreno t){
        
        Double coeficiente = 0.0;
        int cont = 0;
        
        if(t.getAceras()!= (short)0 && t.getRedAguaPotable() != (short)2){
            cont = cont + 1;
        }
        if(t.getBordillos()!= (short)0 && t.getBordillos() != (short)2){
            cont = cont + 1;
        }
        if(t.getRedTelefonica()!= (short)0 && t.getRedTelefonica()!= (short)3){
            cont = cont + 1;
        }
        if(t.getRecoleccionBasura()!= (short)0 && t.getRecoleccionBasura()!= (short)2){
            cont = cont + 1;
        }
        if(t.getAseoCalles()!= (short)0 && t.getAseoCalles()!= (short)2){
            cont = cont + 1;
        }
        
        switch(cont){
            case 5:
                coeficiente = 1.00;
                break;
            
            case 4:
                coeficiente = 0.95;
                break;
                
            case 3:
                coeficiente = 0.90;
                break;
            
            case 2:
                coeficiente = 0.80;
                break;
                
            default:
                coeficiente = 0.70;
                break;
        }
        return coeficiente;
    }
    
    public ValorDiscreto obtenerValor(int id_min, int id_max, short valor, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v  WHERE v.valorDiscretoPK.valor=:valor AND v.valorDiscretoPK.idVariable >= :id_min AND v.valorDiscretoPK.idVariable <= :id_max AND v.valorDiscretoPK.anio =:anio")
                .setParameter("valor", valor)
                .setParameter("id_min", id_min)
                .setParameter("id_max", id_max)
                .setParameter("anio", anio)
                .getSingleResult();

        return result;
    }

    public String obtenerValor(String tabla, String columna, short valor, short anio) {
                
        Query q1= em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d "
                + "WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio",ValorDiscreto.class)
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("valor", valor)
                .setParameter("anio", anio);
        
        ValorDiscreto result=(ValorDiscreto)q1.getSingleResult();
        return result.getTexto();
    }
    public String obtenerValor(int variable, short valor, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.id =:variable AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("variable", variable)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getSingleResult();

        return result != null ? result.getTexto() : "";
    }

    public ValorDiscreto obtenerValorbyVariableValor(int variable, short valor, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.id =:variable AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("variable", variable)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getSingleResult();

        return result;
    }
    public float obtenerCoeficiente(String tabla, String columna, short valor, short anio) {

        List<ValorDiscreto> valores = em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getResultList();

        return valores.isEmpty() ? -1 : valores.get(0).getCoeficiente();
    }

    public void setCoeficiente(String tabla, String columna, short valor, float coeficiente, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getSingleResult();

        result.setCoeficiente(coeficiente);

        em.merge(result);
        em.flush();
    }

    public float obtenerOperacion(String tabla, String columna, short valor, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getSingleResult();

        return result.getOperacion();
    }

    public void setOperacion(String tabla, String columna, short valor, float coeficiente, short anio) {

        ValorDiscreto result = (ValorDiscreto) em.createQuery("SELECT v FROM ValorDiscreto v INNER JOIN v.variable d WHERE d.tabla=:tabla AND d.columna=:columna AND v.valorDiscretoPK.valor =:valor AND v.valorDiscretoPK.anio =:anio")
                .setParameter("tabla", tabla)
                .setParameter("columna", columna)
                .setParameter("valor", valor)
                .setParameter("anio", anio)
                .getSingleResult();

        result.setOperacion(coeficiente);

        em.merge(result);
        em.flush();
    }

     public List<SelectItem> valoresVariables(String tabla, String columna, short anio) {

        List<SelectItem> lista = new LinkedList<>();
        List<ValorDiscreto> valores = obtenerValores(tabla, columna, anio);
        for (ValorDiscreto v : valores) {
            SelectItem i = new SelectItem(v.getValorDiscretoPK().getValor(), v.getTexto());
            lista.add(i);
        }
        return lista;

    }
}