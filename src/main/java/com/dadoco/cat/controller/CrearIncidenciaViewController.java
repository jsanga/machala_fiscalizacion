/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.IntegranteService;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Incidencia;
import com.dadoco.cat.model.Integrante;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.cat.service.IncidenciasService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import com.dadoco.config.ConfigReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.util.Base64;
import org.slf4j.LoggerFactory;

/**
 *
 * @author root
 */
@Named(value="crearIncidenciaView")
@ViewScoped
public class CrearIncidenciaViewController implements Serializable{
   private static final org.slf4j.Logger log = LoggerFactory.getLogger(CrearIncidenciaViewController.class); 
   
   @EJB
   private ConfigReader config;
   @EJB
   private CatastroService catastroService;
   @EJB
   private IncidenciasService inciedenciaService;
   @EJB
   private IntegranteService integranteService;
   @EJB
   private UsuarioService usuarioService;
   @EJB
   private VariableService variableService;
   
   private OpcionesBusquedaPredio opcionesBusqueda;
   
    private String provinciaCod;
    private String cantonCod;
    private String parroquiaCod;
    private String zonaCod;
    private String sectorCod;
    private String manzanaCod;
    private String solarCod;
    private String bloqueCod;
    private String phvCod;
    private String phhCod;
    
    private Predio predio;
    
    private Terreno terreno;
    
    private short secuenciaPredio;
    private List<Contribuyente> propietarios;
    private List<Predio> prediosEnManzana;
    private double coord_X;
    private double coord_Y;
    String claveGeoreferenciada;
    
    private Incidencia incidencia;
    
    private List<UploadedFile> fotos;
    private UploadedImage[] infoFotos;
    
    private List<Integrante> integrantes;
    private Integrante integranteSeleccionado;
   
   public void buscarPredio() {
        try {
            incidencia = new Incidencia();
            provinciaCod = opcionesBusqueda.getProvinciaCod();
            cantonCod = opcionesBusqueda.getCantonCod();
            parroquiaCod = opcionesBusqueda.getParroquiaCod();
            zonaCod = opcionesBusqueda.getZonaCod();
            sectorCod = opcionesBusqueda.getSectorCod();
            manzanaCod = opcionesBusqueda.getManzanaCod();
            solarCod = opcionesBusqueda.getSolarCod();
            bloqueCod = opcionesBusqueda.getBloqueCod();
            phvCod = opcionesBusqueda.getPhvCod();
            phhCod = opcionesBusqueda.getPhhCod();
           

            //predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);
             predio = catastroService.obtenerPredio(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod);

            if (predio == null) {
                JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no encontrado.",
                        parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
            } else {

//                if (predio.getTipoPropiedad() == 1) {
//                    JsfUtil.addErrorMessage(String.format("Predio con clave catastral %d-%d-%d-%d-%d-%d-%d-%d no se encuentra.",
//                            parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, bloqueCod, phvCod, phhCod));
//                    init();
//                }
                incidencia.setPredio(predio);
                terreno = predio.getTerreno();

//                if (comprobarDeudas(predio)) {
//                    JsfUtil.addInformationMessage("Información", String.format("Predio con clave catastral: %2d-%2d-%3d-%2d-%2d, tiene deuda pendientes con el municipio",
//                            zonaCod, sectorCod, manzanaCod, solarCod, phCod));
//
//                    return;
//                }
                secuenciaPredio = catastroService.NumeroProximoTerreno(predio.getTerreno().getTerrenoPK());
                if (secuenciaPredio != 1) {
                    secuenciaPredio++;
                }
                opcionesBusqueda.setEjecutandoAccion(true);
                opcionesBusqueda.setBuscando(false);
                propietarios = predio.getPropietarios();
                // prediosEnManzana = catastroService.obtenerPrediosDeUnaManzana(provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, phCod);

                if (prediosEnManzana.contains(predio)) {
                    prediosEnManzana.remove(predio);
                }

                Object[] coord = catastroService.coordenadas(predio.getClaveCatastral());
                if (coord != null) {
                    coord_X = Double.parseDouble(coord[0].toString());
                    coord_Y = Double.parseDouble(coord[1].toString());
                    claveGeoreferenciada = coord[2].toString();

                }

//                fotoPredio(predio.getId());

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
    }
   
   public String cancelarCreacionIncidencia() {
    String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
   }
   
   @PostConstruct
   public void init(){
        incidencia=new Incidencia();
        provinciaCod = "0";
        cantonCod = "0";
        parroquiaCod = "0";
        zonaCod = "0";
        sectorCod = "0";
        manzanaCod = "0";
        solarCod = "0";

        predio = new Predio();
        opcionesBusqueda = new OpcionesBusquedaPredio();

        opcionesBusqueda.setProvinciaCod(Util.provincia_por_defecto);
        opcionesBusqueda.setCantonCod(Util.canton_por_defecto);

        opcionesBusqueda.setEjecutandoAccion(false);

        propietarios = new ArrayList<>();
        prediosEnManzana = new ArrayList<>();

         infoFotos = new UploadedImage[20];
         fotos = new ArrayList<>();
         List<Integrante> integranteTarget=new ArrayList<Integrante>();
         integrantes = integranteService.findByNamedQuery("Integrante.findByTipo", (short) 2);
          for(Integrante integ: integrantes){
            String nomCompleto=integ.getApellidos()+" "+integ.getNombre();
            if(!nomCompleto.equals("Sin Dato")){
              integranteTarget.add(integ);
            }
        }
        integrantes=integranteTarget;
//          integranteTarget.clear();
   }

    
   public void handlePhotoUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        //get uploaded file from the event
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        fotos.add(uploadedFile);
        UploadedImage ui = new UploadedImage(fotos.size(), FilenameUtils.getName(uploadedFile.getFileName()),
                getBase64Data(uploadedFile), "");

        // ui.setContent(new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContents())));
        infoFotos[fotos.size() - 1] = ui;
    }
     
   public void eliminarFoto(int index) {
        if (index >= 0 && index < fotos.size()) {
            for (int i = index; i < fotos.size() - 1; i++) {
                infoFotos[i] = infoFotos[i + 1];
            }
            infoFotos[fotos.size() - 1] = null;
            fotos.remove(index);

        }
    }
    
   public StreamedContent getImage() {
        DefaultStreamedContent content = new DefaultStreamedContent();

        FacesContext context = FacesContext.getCurrentInstance();

        // if (context.getCurrentPhaseId() != PhaseId.RENDER_RESPONSE) {
        try {
            content = new DefaultStreamedContent(fotos.get(0).getInputstream(), "image/png");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        //}
        return content;
    }

   public String getImageName() {
        try {
            InputStream is = fotos.get(0).getInputstream();
            byte[] data = IOUtils.toByteArray(is);
            return "data:image/png;base64," + Base64.encodeToString(data, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
     
   private String getBase64Data(UploadedFile file) {
        try {

            byte[] data = IOUtils.toByteArray(file.getInputstream());

            return "data:image/" + FilenameUtils.getExtension(file.getFileName()) + ";base64," + Base64.encodeToString(data, false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
    
   //<editor-fold defaultstate="collapsed" desc="guardarFotos">
    private List<UploadedImage> guardarFotos() {
        List<UploadedImage> copiados = new ArrayList<UploadedImage>();
        for (int i = 0; i < fotos.size(); i++) {
            UploadedFile uploadedFile = fotos.get(i);
            try {
                InputStream inputStr = null;

                inputStr = uploadedFile.getInputstream();

                String uploadDir = config.getDbConfiguration().getString("directorio_incidencias");

                String fileName = parroquiaCod + "-" + zonaCod + "-" + sectorCod + "-" + manzanaCod + "-" + solarCod + "-" + bloqueCod + "-" + phvCod + "-" + phhCod + "_";
                fileName += UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
                File destFile = new File(new File(uploadDir), fileName);

                FileUtils.copyInputStreamToFile(inputStr, destFile);

                UploadedImage image = infoFotos[i];
                image.setSavedFile(destFile);

                copiados.add(image);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return copiados;
    }
//</editor-fold>
      /*
public String incidenciaPredio() throws EmailException{
    List<UploadedImage> photos = guardarFotos();
    incidencia.setIntegrante(integranteSeleccionado);
    Subject subject = SecurityUtils.getSubject();
    String user = subject.getPrincipal().toString();
    
    try
    {  
    int idFlujo= Integer.parseInt(variableService.obtenerCodigo("wf_flujo", "id", (short)Util.ANIO_ACTUAL.shortValue()));
    flujoIncidencia= flujoService.flujoFind(idFlujo);
    
    }catch(Exception ex){
          JsfUtil.addErrorMessage("Error Variable wf_flujo_id no existe"+ex);
    }
    try
    {    
        Usuario usuarioLogin=usuarioService.serchUser(user);
        inciedenciaService.crearIncidencia(true, incidencia,photos);
        WfTramiteIncidencia tramite=new WfTramiteIncidencia();
        tramite.setIncidencia(incidencia);
        tramite.setObservacion("");
        tramite.setUsuario(usuarioLogin);
        tramite.setWfActividad(flujoIncidencia.getWfActividad().get(0));
        tramite.setWfFlujo(flujoIncidencia);
        tramite.setEstado("AC");
        flujoService.crearTramite(true, tramite);
        SimpleDateFormat formatoF=new SimpleDateFormat("dd/MM/yyyy");
        String fechaIncidencia=formatoF.format(incidencia.getFechaIncidencia());
        String link="http://localhost:8080/sistema-1.0";
        //<editor-fold defaultstate="collapsed" desc="enviar email">
//    Email email = new SimpleEmail();
//    email.setHostName("smtp.ipage.com");
//    email.setSmtpPort(465);
//    email.setAuthenticator(new DefaultAuthenticator("servicioalcliente@dusatecorp.ec", "Dusatecorp11"));
//    email.setSSLOnConnect(true);
//    email.setFrom("servicioalcliente@dusatecorp.ec");
//    email.setSubject("Inconsitencia Generada "+fechaIncidencia);
//    email.setMsg("Porfavor revisar la incosistencia en el siguiente link: "+link);
//    String emailIntegrante=tramite.getWfActividad().getIntegranteList().get(0).getEmail();
//    email.addTo(emailIntegrante);
//    email.send();

//</editor-fold>
           JsfUtil.addInformationMessage("Incidencia creada", "Incidencia creada");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
            for (UploadedImage f : photos) {
                f.getSavedFile().delete();
            }
        }
     return "pretty:cat-incidencias";
    }*/
       
   public CrearIncidenciaViewController(){}

    public OpcionesBusquedaPredio getOpcionesBusqueda() {
        return opcionesBusqueda;
    }

    public void setOpcionesBusqueda(OpcionesBusquedaPredio opcionesBusqueda) {
        this.opcionesBusqueda = opcionesBusqueda;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    public List<UploadedFile> getFotos() {
        return fotos;
    }

    public void setFotos(List<UploadedFile> fotos) {
        this.fotos = fotos;
    }

    public UploadedImage[] getInfoFotos() {
        return infoFotos;
    }

    public void setInfoFotos(UploadedImage[] infoFotos) {
        this.infoFotos = infoFotos;
    }

    public List<Integrante> getIntegrantes() {
    
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }

    public Integrante getIntegranteSeleccionado() {
        return integranteSeleccionado;
    }

    public void setIntegranteSeleccionado(Integrante integranteSeleccionado) {
        this.integranteSeleccionado = integranteSeleccionado;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public VariableService getVariableService() {
        return variableService;
    }

    public void setVariableService(VariableService variableService) {
        this.variableService = variableService;
    }

}
