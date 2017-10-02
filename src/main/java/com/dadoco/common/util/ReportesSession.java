/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author JoaoIsrael
 */
@Named
@ViewScoped
public class ReportesSession implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Inject
    private ServletSession servletSession;

    private JRBeanCollectionDataSource dataSource;
    private Map parametros;
    private HashMap<String, Object> params = new HashMap<>();
    private Connection conn;

    @PostConstruct
    public void init() {

    }

    public void descargarImagenArregloBytes(byte[] bytes) throws IOException {
        HttpServletResponse response;
        OutputStream outputStream = null;
        try {
            if (bytes == null) {
                return;
            }

            response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();

            response.setContentType("image/JPEG");
            response.setCharacterEncoding("UTF-8");
            //response.setHeader("Content-disposition", "attachment; filename="+servletSession.getNombreReporte()+".pdf");
            response.setHeader("Content-disposition", "attachment; filename=inspeccion" + servletSession.getNombreReporte() + ".jpeg");
            response.setContentLength(bytes.length);

            outputStream = response.getOutputStream();

            outputStream.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }

    }

    public void descargarPDFarregloBytes(byte[] bytes) throws IOException {
        HttpServletResponse response;
        OutputStream outputStream = null;

        try {
            byte[] byteStream = bytes;

            if (byteStream == null) {
                return;
            }

            response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.reset();

            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + servletSession.getNombreDocumento() + ".pdf");
            response.setContentLength(byteStream.length);

            outputStream = response.getOutputStream();

            outputStream.write(byteStream, 0, byteStream.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    public void generarReporte() throws SQLException, JRException, IOException {

        parametros = servletSession.getParametros();
        JasperPrint jasperPrint = null;
        HttpServletResponse response;
        OutputStream outputStream = null;
        byte[] byteStream = null;

        String ruta = JsfUti.getRealPath("//reportes//");
        if (servletSession.getNombreSubCarpeta() != null) {
            ruta = ruta.concat("//" + servletSession.getNombreSubCarpeta());
        }
        ruta = ruta.concat(servletSession.getNombreReporte() + ".jasper");
        try {
            System.out.println("Ruta: " + ruta);
            if (servletSession.getTieneDatasource()) {
                Session sess = servletSession.getEm().unwrap(Session.class);
                this.setConn(((SessionImpl) sess).connection());
                byteStream = JasperRunManager.runReportToPdf(ruta, parametros, this.getConn());
            } else {
                dataSource = new JRBeanCollectionDataSource(new ArrayList());
                //jasperPrint = JasperFillManager.fillReport(ruta, parametros, dataSource);
                byteStream = JasperRunManager.runReportToPdf(ruta, parametros, dataSource);
                dataSource = null;
            }

            params.put("archivoByteArray", byteStream);
            params.put("tipoArchivoByteArray", "application/pdf");
            params.put("nombreArchivoByteArray", servletSession.getNombreReporte());

            servletSession.borrarDatos();

            servletSession.setReportePDF(byteStream);

        } catch (JRException e) {
            Logger.getLogger(ReportesSession.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
        }

    }

    public void exportToDirectory(String directory, Map params, Connection cx) {
        try {
            JasperPrint jasperPrint;
            try {
                String ruta;
                if (servletSession.getNombreSubCarpeta() == null) {
                    ruta = servletSession.getDireccionReportes() + "//" + servletSession.getNombreReporte() + ".jasper";
                } else {
                    ruta = servletSession.getDireccionReportes() + "//" + servletSession.getNombreSubCarpeta() + "//" + servletSession.getNombreReporte() + ".jasper";
                }
                jasperPrint = JasperFillManager.fillReport(ruta, params, cx);
                if (directory != null) {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, directory + "//" + servletSession.getNombreDocumento());
                }
                servletSession.borrarDatos();
            } catch (JRException e) {
                Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (HibernateException e) {
            Logger.getLogger(ReportesSession.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Connection getConnection() {
        Session hibernateSession = servletSession.getEm().unwrap(Session.class);
        conn = ((SessionImpl) hibernateSession).connection();
        return conn;
    }

    public void cerrarConexion() {
        try {
            if (this.getConn() != null) {
                this.getConn().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServletSession getServletSession() {
        return servletSession;
    }

    public void setServletSession(ServletSession servletSession) {
        this.servletSession = servletSession;
    }

    public JRBeanCollectionDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JRBeanCollectionDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map getParametros() {
        return parametros;
    }

    public void setParametros(Map parametros) {
        this.parametros = parametros;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
