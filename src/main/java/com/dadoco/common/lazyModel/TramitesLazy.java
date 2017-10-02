/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.common.util.BaseLazyDataModel;
import com.dadoco.flow.model.HtTramite;
import java.util.Map;
import javax.naming.NamingException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author dadoco
 */
public class TramitesLazy extends BaseLazyDataModel<HtTramite> {
    
    public TramitesLazy() throws NamingException {
        super(HtTramite.class);
    }

    @Override
    public void criteriaFilterSetup(Criteria crit, Map<String, Object> filters) throws Exception {
        
        Criteria solicitante = crit.createCriteria("solicitante");
        
        if (filters.containsKey("id")) {
            crit.add(Restrictions.eq("id", Long.valueOf(filters.get("id").toString())));
        }
        
        if (filters.containsKey("nombreCompleto")) {
            solicitante.add(Restrictions.ilike("nombreCompleto", "%" + filters.get("nombreCompleto").toString().replace(' ', '%')+ "%"));
        }
        if (filters.containsKey("estado")) {
            crit.add(Restrictions.eq("estado.id", Long.parseLong(filters.get("estado").toString())));
        }
        if (filters.containsKey("tipoTramite")) {
            crit.add(Restrictions.eq("tipoTramite.id", Long.parseLong(filters.get("tipoTramite").toString())));
        }
    }
    
}
