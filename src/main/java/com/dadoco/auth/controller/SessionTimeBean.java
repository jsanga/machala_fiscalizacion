/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.controller;

import com.dadoco.config.ConfigReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author yadul
 */
@Named(value = "sessionTimeBean")
@ViewScoped
public class SessionTimeBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(SessionTimeBean.class.getName());

    @EJB
    private ConfigReader config;

    private int timeActive;

    private int timeLimit;

    private int checkInterval;

    public SessionTimeBean() {
    }

    @PostConstruct
    public void init() {
        this.timeLimit = Integer.valueOf(config.getDbConfiguration().getString("tiempo_de_vida_de_sesion"));
        this.checkInterval = Integer.valueOf(config.getDbConfiguration().getString("intervalo_de_chequeo_sesion_activa"));
    }

    public int getTimeActive() {
        return timeActive;
    }

    public void setTimeActive(int timeActive) {
        this.timeActive = timeActive;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public ConfigReader getConfig() {
        return config;
    }

    public void setConfig(ConfigReader config) {
        this.config = config;
    }

    public int getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
    }

    public void checkAlive() throws IOException {

        LOG.info("timeActive: " + timeActive + "  timeLimit: " + timeLimit);

        if (timeActive < timeLimit) {
            timeActive += checkInterval;
        } else {

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/logout");
        }
    }
}
