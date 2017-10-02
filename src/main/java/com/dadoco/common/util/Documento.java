/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

/**
 *
 * @author dadoco
 */
@WebServlet(name = "Documento", urlPatterns = {"/Documento"})
public class Documento extends HttpServlet {
    
    @Inject
    private ServletSession servletSession;

    private Map parametros;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{

        JasperPrint jasperPrint;
        OutputStream outStream;   
        
        Connection conn = null;
        /*if (servletSession.estaVacio()) {
            PrintWriter salida = response.getWriter();
            MsgFormatoNotificacion msg = EjbsCaller.getTransactionManager().find(MsgFormatoNotificacion.class, new Long(1));
            salida.println(msg.getHeader());
            salida.println("<center><p>No hay datos que mostrar.</p></center>");
            salida.println(msg.getFooter());
            salida.close();
            return;
        }*/
        parametros = servletSession.getParametros();
        response.setContentType("application/pdf");

        /*if (servletSession.tieneParametro("ciRuc")) {
            response.addHeader("Content-disposition", "filename=" + servletSession.getNombreReporte() + servletSession.retornarValor("ciRuc") + ".pdf");
        } else {
            response.addHeader("Content-disposition", "filename=" + servletSession.getNombreReporte() + ".pdf");
        }*/

        try {
            Session hibernateSession = servletSession.getEm().unwrap(Session.class);
            conn = ((SessionImpl)hibernateSession).connection();
            
            request.setCharacterEncoding("UTF-8");
            String ruta;
            
            if (servletSession.getNombreSubCarpeta() == null) {
                ruta = servletSession.getDireccionReportes() +"//" + servletSession.getNombreReporte() + ".jasper";
            } else {
                ruta = servletSession.getDireccionReportes() +"//" +  servletSession.getNombreSubCarpeta() + "//" + servletSession.getNombreReporte() + ".jasper";
            }

            if (servletSession.getTieneDatasource()) {
                
                jasperPrint = JasperFillManager.fillReport(ruta, parametros, conn);
                if (servletSession.getEncuadernacion() != null && servletSession.getEncuadernacion()) {
                    List pages = jasperPrint.getPages();
                    JRPrintPage page;
                    List<JRPrintElement> elements;
                    for (int i = 1; i < pages.size() + 1; i++) {
                        page = (JRPrintPage) pages.get(i - 1);
                        elements = page.getElements();
                        if (i % 2 != 0) {//IMPAR
                            for (JRPrintElement temp : elements) {
                                temp.setX(temp.getX() + 30);
                            }
                        } else {//PAR
                            for (JRPrintElement temp : elements) {
                                temp.setX(temp.getX() - 30);
                            }
                        }
                    }
                }
            } else {
                JRDataSource dataSource = new JRBeanCollectionDataSource(new ArrayList());
                if (servletSession.getDataSource() != null) {
                    dataSource = new JRBeanCollectionDataSource(servletSession.getDataSource());
                }
                jasperPrint = JasperFillManager.fillReport(ruta, parametros, dataSource);

            }
            if (servletSession.getAgregarReporte()!=null && servletSession.getAgregarReporte()) {
                for (Map reporte : servletSession.getReportes()) {
                    if (reporte.containsKey("nombreSubCarpeta")) {
                        ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("//reportes//" + reporte.get("nombreSubCarpeta") + "//" + reporte.get("nombreReporte") + ".jasper");
                    } else {
                        ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("//reportes//" + servletSession.getNombreSubCarpeta() + "//" + reporte.get("nombreReporte") + ".jasper");
                    }
                    JasperPrint jasperPrint2 = JasperFillManager.fillReport(ruta, reporte, conn);
                    if (jasperPrint2.getPages() != null && jasperPrint2.getPages().size() > 0) {
                        jasperPrint.addPage(jasperPrint2.getPages().get(0));
                    }
                }
            }
            outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
            outStream.flush();
            outStream.close();
            servletSession.borrarDatos();

        } catch (JRException | IOException e) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Documento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
