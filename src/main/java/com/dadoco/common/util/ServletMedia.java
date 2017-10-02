/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import com.dadoco.admin.service.IntegranteService;
import com.dadoco.flow.model.HtArchivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JoaoIsrael
 */
@WebServlet(name = "ServletMedia", urlPatterns = {"/ServletMedia"})
public class ServletMedia extends HttpServlet {
        
    private IntegranteService integranteService;
    
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
        
        OutputStream outStream;
        HtArchivo file;
        String idArchivo = request.getParameter("objectId");
        
        if(idArchivo == null)
            return;
        
        
        try {
            integranteService = (IntegranteService) new InitialContext().lookup(Util.moduleJNDI("IntegranteService"));
            file = integranteService.getEm().find(HtArchivo.class, Long.parseLong(idArchivo));
            
            File f = new File(file.getRutaArchivo()+"/"+file.getNombreArchivo());
            InputStream is = new FileInputStream(f);

            response.setContentType(file.getContentType());
            response.setHeader("Content-Disposition","inline; filename="+file.getNombreArchivo());       
            outStream = response.getOutputStream();
            
            byte[] buffer = new byte[4096];
            int length;
            while ((length = is.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }
            
            outStream.flush();
            outStream.close();
                        
            //processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletMedia.class.getName()).log(Level.SEVERE, null, ex);
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
