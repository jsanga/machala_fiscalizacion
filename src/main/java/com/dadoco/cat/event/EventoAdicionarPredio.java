/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.event;


import com.dadoco.cat.model.Predio;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
public class EventoAdicionarPredio {
    
    
    private Predio predio;
    private String username;

    public EventoAdicionarPredio() {
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
}
