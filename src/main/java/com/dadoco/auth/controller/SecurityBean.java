/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author dfcalderio
 */
@Named(value = "securityBean")
@SessionScoped
public class SecurityBean implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SecurityBean.class);

    private Subject subject;

    public SecurityBean() {

    }

    @PostConstruct
    public void init() {
        try {
            this.subject = SecurityUtils.getSubject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public boolean hasPermision(String permision) {
        if (subject == null) {
            return false;
        }
        return subject.isPermitted(permision);
    }

    public boolean hasAllPermision(String permisions) {
        if (subject == null) {
            return false;
        }
        String[] perms = permisions.split(",");
        for (String p : perms) {
            if (!subject.isPermitted(p)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyPermision(String permisions) {
        if (subject == null) {
            return false;
        }
        String[] perms = permisions.split(",");
        for (String p : perms) {
            if (subject.isPermitted(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRol(String rol) {
        if (subject == null) {
            return false;
        }
        return subject.hasRole(rol);
    }

    public boolean hasAllRol(String roles) {
        if (subject == null) {
            return false;
        }
        String[] rols = roles.split(",");

        for (String r : rols) {
            if (!subject.hasRole(r)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyRol(String roles) {
        if (subject == null) {
            return false;
        }
        String[] rols = roles.split(",");

        for (String r : rols) {
            if (subject.hasRole(r)) {
                return true;
            }
        }
        return false;
    }

}
