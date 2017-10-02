/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.cat.model.Predio;
import com.dadoco.common.util.BaseLazyDataModel;
import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 *
 * @author dadoco
 */
public class PrediosLazy extends BaseLazyDataModel<Predio> {
    
    private Boolean tipoPredio;
    private Criteria terreno;
    private Integer tipo;
    
    public PrediosLazy(Boolean tipoPredio) throws NamingException {
        super(Predio.class, "id", "desc");
        this.tipoPredio = tipoPredio;        
    }
    
    public PrediosLazy() throws NamingException {
        super(Predio.class, "id", "desc");   
        tipoPredio = null;
    }
    
    public PrediosLazy(Integer tipo) throws NamingException {
        super(Predio.class, "id", "desc");
        tipoPredio = null;
        this.tipo = tipo;
    }

    @Override
    public void criteriaFilterSetup(Criteria crit, Map<String, Object> filters) throws Exception {
        
        terreno = crit.createCriteria("terreno");
        
        if(this.tipoPredio != null){
            crit.add(Restrictions.eq("tipoPredio", this.tipoPredio));
            crit.add(Restrictions.eq("estado", (short)1));
        }else
            if(tipo != null){
                if(tipo == 4)
                    crit.add(Restrictions.eq("estado", (short)5));
            }else
                crit.add(Restrictions.eq("estado", (short)2));
                
        if (filters.containsKey("terreno.terrenoPK.codParroquia")) {
            terreno.add(Restrictions.ilike("terrenoPK.codParroquia", "%" + filters.get("terreno.terrenoPK.codParroquia").toString()+ "%"));
        }
        
        if (filters.containsKey("terreno.terrenoPK.codZona")) {
            terreno.add(Restrictions.ilike("terrenoPK.codZona", "%" + filters.get("terreno.terrenoPK.codZona").toString()+ "%"));
        }
        
        if (filters.containsKey("terreno.terrenoPK.codSector")) {
            terreno.add(Restrictions.ilike("terrenoPK.codSector", "%" + filters.get("terreno.terrenoPK.codSector").toString()+ "%"));
        }
        
        if (filters.containsKey("terreno.terrenoPK.codManzana")) {
            terreno.add(Restrictions.ilike("terrenoPK.codManzana", "%" + filters.get("terreno.terrenoPK.codManzana").toString()+ "%"));
        }
        
        if (filters.containsKey("terreno.terrenoPK.codSolar")) {
            terreno.add(Restrictions.ilike("terrenoPK.codSolar", "%" + filters.get("terreno.terrenoPK.codSolar").toString()+ "%"));
        }
        
        if(filters.containsKey("claveAnterior")){
            crit.add(Restrictions.eq("claveAnterior", filters.get("claveAnterior").toString()));
        }
        
        if(filters.containsKey("registroCatastral")){
            crit.add(Restrictions.eq("registroCatastral", filters.get("registroCatastral").toString()));
        }
        
        if(filters.containsKey("codBloque")){
            crit.add(Restrictions.ilike("codBloque", filters.get("codBloque").toString()));
        }
        
        if(filters.containsKey("codPhv")){
            crit.add(Restrictions.ilike("codPhv", filters.get("codPhv").toString()));
        }
        
        if(filters.containsKey("codPhh")){
            crit.add(Restrictions.ilike("codPhh", filters.get("codPhh").toString()));
        }
        
        if(filters.containsKey("fechaCreacion")){
            crit.add(Restrictions.between("fechaCreacion", (Date)filters.get("fechaCreacion"), new Date(((Date)filters.get("fechaCreacion")).getTime() + (1000 * 60 * 60 * 24))));
        }
        
        if(filters.containsKey("nombresPropietariosJS")){
            crit.createCriteria("propietarios", JoinType.INNER_JOIN).add(Restrictions.ilike("nombreCompleto", "%" +filters.get("nombresPropietariosJS").toString() + "%"));
        }
        
    }
    
}
