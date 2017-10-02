/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class TipoEscrituraEJB {

    @Resource  SessionContext ctx;
    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    

}
