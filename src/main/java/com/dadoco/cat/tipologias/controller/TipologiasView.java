/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.tipologias.controller;

import com.dadoco.cat.tipologias.lazymodel.EquipoLazy;
import com.dadoco.cat.tipologias.lazymodel.MaterialLazy;
import com.dadoco.cat.tipologias.lazymodel.PersonalLazy;
import com.dadoco.cat.tipologias.model.Equipo;
import com.dadoco.cat.tipologias.model.Material;
import com.dadoco.cat.tipologias.model.Personal;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.util.JsfUti;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Joao Sanga
 */
@Named(value = "tipologiasView")
@ViewScoped
public class TipologiasView implements Serializable{
    
    @EJB
    protected ManagerService aclServices;
    
    private EquipoLazy equipos;
    private MaterialLazy materiales;
    private PersonalLazy personal;
    private Equipo equipo;
    private Personal personal_n;
    private Material material;

    @PostConstruct
    public void initView(){
        try{
            equipos = new EquipoLazy();
            materiales = new MaterialLazy();
            personal = new PersonalLazy();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void nuevoRegistro(int tipo){
        equipo = new Equipo();
        personal_n = new Personal();
        material = new Material();
        
        switch(tipo){
            case 1:
                
                JsfUti.executeJS("PF('idPersonal').show();");
                JsfUti.update("idFrmPersonal");
                break;
            case 2:
                JsfUti.executeJS("PF('idEquipo').show();");
                JsfUti.update("idFrmEquipo");
                break;
            case 3:
                JsfUti.executeJS("PF('idMaterial').show();");
                JsfUti.update("idFrmMaterial");
                break;
        }
    }

    public ManagerService getAclServices() {
        return aclServices;
    }

    public void setAclServices(ManagerService aclServices) {
        this.aclServices = aclServices;
    }

    public EquipoLazy getEquipos() {
        return equipos;
    }

    public void setEquipos(EquipoLazy equipos) {
        this.equipos = equipos;
    }

    public MaterialLazy getMateriales() {
        return materiales;
    }

    public void setMateriales(MaterialLazy materiales) {
        this.materiales = materiales;
    }

    public PersonalLazy getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalLazy personal) {
        this.personal = personal;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Personal getPersonal_n() {
        return personal_n;
    }

    public void setPersonal_n(Personal personal_n) {
        this.personal_n = personal_n;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    
}
