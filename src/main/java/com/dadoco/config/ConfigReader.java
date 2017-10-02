/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.config;

import com.dadoco.common.service.ManagerService;
import com.dadoco.external.queries.ExtQueries;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Singleton
public class ConfigReader {

    private Configuration dbConfiguration;

    PropertiesConfiguration propertiesConfiguration;

    @EJB
    private ManagerService manager;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConfigReader.class);

    @PostConstruct
    protected void init() {
        try {
            dbConfiguration = new PropertiesConfiguration(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/parametros.properties"));
            GlobalVars.directorio_imagenes = dbConfiguration.getString("directorio_imagenes");
            GlobalVars.directorio_incidencias = dbConfiguration.getString("directorio_incidencias");

            GlobalVars.directorio_manzaneros = dbConfiguration.getString("directorio_manzaneros");
            GlobalVars.directorio_planos_solar = dbConfiguration.getString("directorio_planos_solar");
            GlobalVars.directorio_documentos = dbConfiguration.getString("directorio_documentos");

            GlobalVars.directorio_fichas = dbConfiguration.getString("directorio_fichas");
            GlobalVars.directorio_ficha_escaneada = dbConfiguration.getString("directorio_ficha_escaneada");
            GlobalVars.directorio_datos_autorizacion = dbConfiguration.getString("directorio_datos_autorizacion");
            GlobalVars.directorio_reportes = dbConfiguration.getString("directorio_reportes");
            GlobalVars.directorio_cat_docs = dbConfiguration.getString("directorio_cat_docs");
            loadGeoUrl();
            GlobalVars.contextURL = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                    + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                    + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                    + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void loadGeoUrl() {
        List v = (List) manager.getNative(ExtQueries.getGeoUrl, new String[]{"prefijo"}, new Object[]{"GEO-SERVER"});
        if (v != null) {
            GlobalVars.geoServerUrl = v.get(0).toString();
        }
    }

    public Configuration getDbConfiguration() {
        return dbConfiguration;
    }

    public void setDbConfiguration(Configuration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }

    public PropertiesConfiguration getPropertiesConfiguration() {
        return propertiesConfiguration;
    }

    public void setPropertiesConfiguration(PropertiesConfiguration propertiesConfiguration) {
        this.propertiesConfiguration = propertiesConfiguration;
    }

}
