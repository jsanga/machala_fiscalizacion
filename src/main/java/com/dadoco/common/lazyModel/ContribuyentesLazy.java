/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.common.util.BaseLazyDataModel;
import java.util.Map;
import javax.naming.NamingException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author dadoco
 */
public class ContribuyentesLazy extends BaseLazyDataModel<Contribuyente> {
    
    public ContribuyentesLazy() throws NamingException {
        super(Contribuyente.class);
    }

    @Override
    public void criteriaFilterSetup(Criteria crit, Map<String, Object> filters) throws Exception {

        if (filters.containsKey("nombreCompleto")) {
            crit.add(Restrictions.ilike("nombreCompleto", "%" + filters.get("nombreCompleto").toString().replace(' ', '%')+ "%"));
        }
    }
    
}
