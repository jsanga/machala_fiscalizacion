/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.lazymodel;

import com.dadoco.cat.tipologias.model.Material;
import com.dadoco.cat.tipologias.model.Personal;
import com.dadoco.common.util.BaseLazyDataModel;
import java.util.Map;
import javax.naming.NamingException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Joao Sanga
 */
public class MaterialLazy extends BaseLazyDataModel<Material>{
    
    public MaterialLazy() throws NamingException{
        super(Material.class);
    }
    
    @Override
    public void criteriaFilterSetup(Criteria crit, Map<String, Object> filters) throws Exception {
                
        if (filters.containsKey("codigo")) {
            crit.add(Restrictions.eq("codigo", Integer.valueOf(filters.get("codigo").toString())));
        }
        
        if (filters.containsKey("descripcion")) {
            crit.add(Restrictions.eq("descripcion", filters.get("descripcion").toString()));
        }
    }
    
}
