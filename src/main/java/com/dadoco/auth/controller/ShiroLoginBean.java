/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.controller;

import com.dadoco.common.jsf.JsfUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.view.ViewScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Dairon
 */
@Named
@ViewScoped
public class ShiroLoginBean implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ShiroLoginBean.class);
    
    private String username;
    private String password;
    private boolean rememberMe;

    public ShiroLoginBean() {

    }

    /**
     * Try and authenticate the user
     */
    public void doLogin() {

        UsernamePasswordToken token = null;
        try {
            Subject subject = SecurityUtils.getSubject();

            token = new UsernamePasswordToken(getUsername(), getPassword());
            
            token.setRememberMe(isRememberMe());

            subject.login(token);

            if (subject.hasRole("admin")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } catch (UnknownAccountException ex) {
            facesError("Unknown account");
            log.error(ex.getMessage(), ex);
        } catch (IncorrectCredentialsException ex) {
            facesError("Wrong password");
            log.error(ex.getMessage(), ex);
        } catch (LockedAccountException ex) {
            facesError("Locked account");
            log.error(ex.getMessage(), ex);
        } catch (AuthenticationException | IOException ex) {
            facesError("Unknown error: " + ex.getMessage());
            log.error(ex.getMessage(), ex);
        } finally {
            if (token != null) {
                token.clear();
            }
        }
    }

    /**
     * Adds a new SEVERITY_ERROR FacesMessage for the ui
     *
     * @param message Error Message
     */
    private void facesError(String message) {
        JsfUtil.addErrorMessage(message);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void logout() {
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }
}
