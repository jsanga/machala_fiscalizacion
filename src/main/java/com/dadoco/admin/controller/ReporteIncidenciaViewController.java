/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.admin.controller;

import com.dadoco.cat.controller.OpcionesBusquedaPredio;
import com.dadoco.cat.model.Incidencia;
import com.dadoco.cat.model.IncidenciaImagen;
import com.dadoco.cat.service.IncidenciasService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author root
 */
@Named(value = "reporteincidenciasView")
@ViewScoped
public class ReporteIncidenciaViewController implements Serializable{
    
    @EJB
    private ConfigReader config;
    @EJB
    private IncidenciasService incidenciaService;
     
    private List<Incidencia> listIncidencias;
    private List<Incidencia> incidenciasFiltradas;
    private String url;
    private List<String>inciFotos;
    private OpcionesBusquedaPredio opcionesBusqueda;
    private boolean visualizarDatos;
    
    @PostConstruct
    public void init(){
        inciFotos=new ArrayList<String>();
        listIncidencias=incidenciaService.listIncidencias();
        url = config.getDbConfiguration().getString("directorio_incidencias");
    }
    
    public void excelExport() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.responseReset();
        externalContext.setResponseContentType("application/vnd.ms-excel");
      
      String tempDir = context.getExternalContext().getRealPath("") + "/resources/";
      Date hoy=new Date();
      SimpleDateFormat formato=new SimpleDateFormat("dd-MM-yyyy");
      externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+"INFORME DE NOVEDADES "+formato.format(hoy)+".xlsx" +"\"");
      InputStream file = null;
      try {   
      
       file = new FileInputStream(new File(tempDir+"FORMATO DE INFORME DE NOVEDADES PROPUESTA Evelin Olaya.xlsx"));
     
            // High level representation of a workbook.
            // Representación del más alto nivel de la hoja excel.
            XSSFWorkbook hssfWorkbook = new XSSFWorkbook(file);
            // We chose the sheet is passed as parameter. 
            // Elegimos la hoja que se pasa por parámetro.
            XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            // We create the new sheet we are going to use.
            // Creamos la hoja nueva que vamos a utilizar.
//            XSSFSheet hssfSheetNew = hssfWorkbookNew.createSheet("Copy-Copia");
            // An object that allows us to read a row of the excel sheet, and <span class="IL_AD" id="IL_AD7">extract</span> from it the cell contents.
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            XSSFRow hssfRowNew;
            XSSFCreationHelper helper = hssfWorkbook. getCreationHelper ( ) ; 
            XSSFHyperlink file_link = helper.createHyperlink ( Hyperlink.LINK_FILE ) ; 
            // Initialize the object to read the value of the cell 
            // Inicializo el objeto que leerá el <span class="IL_AD" id="IL_AD4">valor</span> de la celda
            // I get the number of rows occupied on the sheet
            // Obtengo el número de filas ocupadas en la hoja
            int rows = 6;  
//            System.out.print("Cantidad de Filas: "+rows+"\n");
            for(Incidencia data: listIncidencias)
            {
            hssfRowNew=hssfSheet.createRow(rows);
            hssfRowNew.createCell(0).setCellValue(formato.format(data.getFechaIncidencia()));
//            hssfRowNew.createCell(1).setCellValue(data.getPredio().getSupervisor().getNombre());
//            hssfRowNew.createCell(2).setCellValue(data.getPredio().getValidador().getNombre());
//            hssfRowNew.createCell(3).setCellValue(data.getPredio().getTerreno().getBarrio());
            hssfRowNew.createCell(4).setCellValue(data.getPredio().getClaveAnterior());
            hssfRowNew.createCell(5).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodProvincia());
            hssfRowNew.createCell(6).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodCanton());
            hssfRowNew.createCell(7).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodParroquia());
            hssfRowNew.createCell(8).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodZona());
            hssfRowNew.createCell(9).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodSector());
            hssfRowNew.createCell(10).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodManzana());
            hssfRowNew.createCell(11).setCellValue(data.getPredio().getTerreno().getTerrenoPK().getCodSolar());
            hssfRowNew.createCell(12).setCellValue(data.getPredio().getClaveCatastral());
//            hssfRowNew.createCell(13).setCellValue(data.getPredio().getTerreno().getAceras());
            String urls="";
             hssfRowNew.createCell(19);
            for(IncidenciaImagen imagen:data.getIncidenciaImagen())
            {
                
                urls+=url + "/"+imagen.getRuta()+"\n";
                file_link.setAddress(url + "/"+imagen.getRuta());
                file_link.setTooltip("Fotos");
            }
          
           
            hssfRowNew.createCell(19).setCellValue(urls);
            hssfRowNew.getCell(19).setHyperlink(file_link);
            hssfRowNew.createCell(20).setCellValue(data.getObservacionIncidencia());
             rows++;
            }
            hssfWorkbook.write(externalContext.getResponseOutputStream());
            System.out.println("Your excel file has been generated!(¡Se ha generado tu hoja excel!");
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
        } catch (IOException ex) {
            System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
        } finally {
            try {
                file.close();
                context.responseComplete();
            } catch (IOException ex) {
                System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
            }
        }
	// cerramos el libro excel
	
    }
    
    public void buscarPredio() {

        String palabraClave = opcionesBusqueda.getPalabraClave();

        try {

            if (palabraClave.equals("")) {
                listIncidencias = incidenciaService.findByFilter();
            } 

            incidenciasFiltradas.clear();
            incidenciasFiltradas = listIncidencias;
            visualizarDatos = false;

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }
    
    public ReporteIncidenciaViewController(){}

    public List<Incidencia> getListIncidencias() {
        return listIncidencias;
    }

    public void setListIncidencias(List<Incidencia> listIncidencias) {
        this.listIncidencias = listIncidencias;
    }
    
    public void listFotos(Incidencia incidencia)
    {
        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        inciFotos=new ArrayList<String>();
        String foto;
        String pathToPhoto = "";
        if(!incidencia.getIncidenciaImagen().isEmpty())
        { 
          String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/fotos";
            
          for(IncidenciaImagen img: incidencia.getIncidenciaImagen()){
            
              foto=img.getRuta();
              try {
                    if (existeArchivo(url + "/" + foto)) {
                        FileInputStream inFile = new FileInputStream(url + "/" + foto);
                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + foto);

                        try {
                            IOUtils.copy(inFile, outFile);
                            inFile.close();
                            outFile.close();
                        } catch (IOException ex) {

                        }
                    }

                    if (existeArchivo(tempDir + "/" + foto)) {
                        pathToPhoto = urlBase + "/fotos/" + foto;
                        if (!inciFotos.contains(pathToPhoto)) {
                            inciFotos.add(pathToPhoto);
                        }
                    } else {
                        pathToPhoto = urlBase + "/fotos/nofoto.jpg";
                        if (!inciFotos.contains(pathToPhoto)) {
                            inciFotos.add(pathToPhoto);
                        }
                    }

                } catch (FileNotFoundException ex) {

                }
              
             
          }
        }
       
    }

    public boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
    }
    
    public List<String> getInciFotos() {
        return inciFotos;
    }

    public void setInciFotos(List<String> inciFotos) {
        this.inciFotos = inciFotos;
    }

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public List<Incidencia> getIncidenciasFiltradas() {
        return incidenciasFiltradas;
    }

    public void setIncidenciasFiltradas(List<Incidencia> incidenciasFiltradas) {
        this.incidenciasFiltradas = incidenciasFiltradas;
    }

    public boolean isVisualizarDatos() {
        return visualizarDatos;
    }

    public void setVisualizarDatos(boolean visualizarDatos) {
        this.visualizarDatos = visualizarDatos;
    }
    
    
}
