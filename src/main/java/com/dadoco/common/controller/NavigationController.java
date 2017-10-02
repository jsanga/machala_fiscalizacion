/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.controller;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;

import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author dfcalderio
 */
@Named(value = "navigationController")
@ApplicationScoped
public class NavigationController implements Serializable {

    private MenuModel sideMenuModel;
    private String activeModule = "desktop";
    private String selectedModule = "";

    public void showSideBarMenu(ActionEvent event) {

        selectedModule = (String) event.getComponent().getAttributes().get("module_name");

        /*FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Prueba",  null);
         FacesContext.getCurrentInstance().addMessage(null, message);*/
    }

    public MenuModel getSideMenuModel() {
        if (!activeModule.equals(selectedModule)) {
            if ("maintenance".equals(selectedModule)) {
                buildMaintenanceModuleSideBarLinks();
            } else if ("contributor".equals(selectedModule)) {
                buildContributorSideBarLinks();
            } else {
                buildDefaultSideBarLinks();
            }
        }

        activeModule = selectedModule;
        return sideMenuModel;
    }

    private void buildDefaultSideBarLinks() {
        sideMenuModel = new DefaultMenuModel();
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Generales");

        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("fa fa-user");
        firstSubmenu.addElement(item);

        sideMenuModel.addElement(firstSubmenu);
    }

    private void buildMaintenanceModuleSideBarLinks() {
        sideMenuModel = new DefaultMenuModel();
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Mantenimiento");

        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);

        sideMenuModel.addElement(firstSubmenu);
    }

    private void buildContributorSideBarLinks() {
        sideMenuModel = new DefaultMenuModel();
        DefaultSubMenu contribuyenteSubmenu = new DefaultSubMenu("Contribuyentes");

        DefaultMenuItem item = new DefaultMenuItem("Nuevo contribuyente");
        item.setUrl("/faces/contribuyente/adicionar.xhtml");
        item.setIcon("fa fa-user");
        contribuyenteSubmenu.addElement(item);
        
        item = new DefaultMenuItem("Buscar contribuyente");
        item.setUrl("/faces/contribuyente/buscar.xhtml");
        item.setIcon("fa fa-search");
        contribuyenteSubmenu.addElement(item);

        sideMenuModel.addElement(contribuyenteSubmenu);
    }

}
