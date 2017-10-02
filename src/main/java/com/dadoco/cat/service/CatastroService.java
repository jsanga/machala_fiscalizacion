/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.admin.service.ContribuyenteService;
//import com.dadoco.aud.model.HistoricoBloque;
//import com.dadoco.aud.model.HistoricoBloquePK;
import com.dadoco.audit.service.AuditService;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.cat.model.ArchivoEscritura;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BloqueImage;
import com.dadoco.cat.model.CatPredioRuralTerreno;
import com.dadoco.cat.model.ContratoArrendamiento;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.ContribuyentePredio;
import com.dadoco.cat.model.DatoInspeccion;
import com.dadoco.cat.model.DatosAutorizacion;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.FotosInspeccion;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.PredioImage;
import com.dadoco.cat.model.RazonExencion;
import com.dadoco.cat.model.Sector;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.TerrenoPK;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.cat.model.TipoEscritura;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.Zona;
import com.dadoco.common.jsf.UploadedDocument;
import com.dadoco.common.jsf.UploadedImage;
import com.dadoco.common.model.Archivo;
import com.dadoco.common.model.ArchivoAux;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.util.UploadFile;
import com.dadoco.config.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class CatastroService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CatastroService.class);

    @PersistenceContext(unitName = "dusatecorp_catastro")
    private EntityManager em;

    @EJB
    protected ManagerService aclServices;

    @EJB
    private ConfigReader config;
    
    @EJB
    private AuditService auditServices;

    @EJB
    private PredioService predioService;

    @EJB
    private ManzanaService manzaService;

    @EJB
    private TipoEscrituraService tipoEscrituraService;

    @EJB
    private TipoDocumentoService tipoDocumentoService;

    @EJB
    private ContribuyenteService contribuyenteService;

    @EJB
    private UsuarioService usuarioService;

    @EJB
    private ExencionService exencionService;

    @EJB
    private ContribuyentePredioService contribuyentePredioService;

    /*Eventos*/
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional
    public void crearPredio(boolean nuevoTerreno, Terreno t, Predio p, List<UploadFile> photos, Escritura escritura, List<UploadFile> files) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        p.setTerreno(t);
        p.setFechaCreacion(new Date());
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        List<ArchivoEscritura> documentos = new ArrayList<>();
        for (UploadFile f : files) {
            ArchivoEscritura archivo = new ArchivoEscritura();

            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setFechaCreacion(new Date());
            archivo.setEscritura(escritura);
            documentos.add(archivo);
        }
        escritura.setPredio(p);
        escritura.setAdjuntos(documentos);
        List<Escritura> escrituras = new ArrayList<>();
        escrituras.add(escritura);
        p.setEscrituras(escrituras);

        List<PredioImage> fotos = new ArrayList();

        for (UploadFile f : photos) {

            PredioImage foto = new PredioImage();
            foto.setAutor(user);
            foto.setRuta(f.getFileName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            foto.setPredio(p);
            fotos.add(foto);

        }
        p.setImages(fotos);
        if (!p.getBloques().isEmpty()) {
            for (int i = 0; i < p.getBloques().size(); i++) {
                p.getBloques().get(i).setNumeroBloque(i + 1);
                List<UploadFile> photosBloque = p.getBloques().get(i).getFotosBloque();
                List<BloqueImage> fotosBloque = new ArrayList();

                if (!photosBloque.isEmpty()) {
                    
                    for (UploadFile f : photosBloque) {

                        BloqueImage foto = new BloqueImage();
                        foto.setAutor(user);
                        foto.setRuta(f.getSavedFile().getName());
                        foto.setDescripcion(f.getDescription());
                        foto.setFechaCreacion(new Date());
                        foto.setBloque(p.getBloques().get(i));
                        fotosBloque.add(foto);
                    }
                    p.getBloques().get(i).setImages(fotosBloque);
                }
                for (int j = 0; j < p.getBloques().get(i).getPisos().size(); j++) {
                    p.getBloques().get(i).getPisos().get(j).setNumeroPiso(j + 1);
                }
            }
        }

        t.getPredios().add(p);

        if (nuevoTerreno) {
            em.persist(t);
        } else {
            em.merge(t);
        }


        /*for (Contribuyente c : p.getPropietarios()) {

            em.createNativeQuery("UPDATE public.cat_contribuyente_predio SET status=" + c.getTipoPropietario() + " \n"
                    + "WHERE id_contribuyente=" + c.getId() + " AND id_predio=" + p.getId() + "").executeUpdate();

            em.flush();
        }*/
        for (Contribuyente c : p.getPropietarios()) {
            if (c.getId() == null) {
                em.persist(c);
            } else {
                em.merge(c);
            }
        }
        auditServices.guardarUsuarioTransaccion(user, "CREACIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void crearPredioRustico(boolean nuevoTerreno, Terreno t, Predio p, List<UploadFile> photos, Escritura escritura, List<UploadFile> files, Map<Integer, List> terrenoClases) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        escritura.setPredio(p);
        p.setTerreno(t);
        p.setFechaCreacion(new Date());
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        List<ArchivoEscritura> documentos = new ArrayList<>();
        for (UploadFile f : files) {
            ArchivoEscritura archivo = new ArchivoEscritura();

            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setFechaCreacion(new Date());
            archivo.setEscritura(escritura);
            documentos.add(archivo);
        }
        
        for(int i = 1; i<7 ; i++){
            for(CatPredioRuralTerreno temp : (List<CatPredioRuralTerreno>)terrenoClases.get(i)){
                temp.setPredio(p);
                if(temp.getId() == null)
                    em.persist(temp);
                else
                    em.merge(temp);
            }
        }
        
        escritura.setPredio(p);
        escritura.setAdjuntos(documentos);
        List<Escritura> escrituras = new ArrayList<>();
        escrituras.add(escritura);
        p.setEscrituras(escrituras);

        List<PredioImage> fotos = new ArrayList();

        for (UploadFile f : photos) {

            PredioImage foto = new PredioImage();
            foto.setAutor(user);
            foto.setRuta(f.getFileName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            foto.setPredio(p);
            fotos.add(foto);

        }
        p.setImages(fotos);
        if (!p.getBloques().isEmpty()) {
            for (int i = 0; i < p.getBloques().size(); i++) {
                p.getBloques().get(i).setNumeroBloque(i + 1);
                List<UploadFile> photosBloque = p.getBloques().get(i).getFotosBloque();
                List<BloqueImage> fotosBloque = new ArrayList();

                if (!photosBloque.isEmpty()) {
                    for (UploadFile f : photosBloque) {

                        BloqueImage foto = new BloqueImage();
                        foto.setAutor(user);
                        foto.setRuta(f.getSavedFile().getName());
                        foto.setDescripcion(f.getDescription());
                        foto.setFechaCreacion(new Date());
                        foto.setBloque(p.getBloques().get(i));
                        fotosBloque.add(foto);
                    }
                    p.getBloques().get(i).setImages(fotosBloque);
                }
                for (int j = 0; j < p.getBloques().get(i).getPisos().size(); j++) {
                    p.getBloques().get(i).getPisos().get(j).setNumeroPiso(j + 1);
                }
            }
        }

        if (t.getPredios() == null) {
            t.setPredios(new ArrayList());
        }

        t.getPredios().add(p);

        if (nuevoTerreno) {
            em.persist(t);
        } else {
            em.merge(t);
        }

        /*for (Contribuyente c : p.getPropietarios()) {

            em.createNativeQuery("UPDATE public.cat_contribuyente_predio SET status=" + c.getTipoPropietario() + " \n"
                    + "WHERE id_contribuyente=" + c.getId() + " AND id_predio=" + p.getId() + "").executeUpdate();

            em.flush();
        }*/
        for (Contribuyente c : p.getPropietarios()) {
            if (c.getId() == null) {
                em.persist(c);
            } else {
                em.merge(c);
            }
        }
        auditServices.guardarUsuarioTransaccion(user, "CREACIÓN DE PREDIO RÚSTICO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }
    
    public void updatePredioRuralComplete(Predio p, List<Contribuyente> propietariosEliminados, Terreno t, Escritura e, List<UploadFile> docs, List<UploadFile> photosPredio, List<ArchivoAux> photosPredioEliminar, Map<Integer, List> terrenoClases){
        
        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        
        List<ArchivoEscritura> docsEscritura = new ArrayList<>();
        List<PredioImage> fotosP = new ArrayList();
        
        em.merge(t);
        
        for (UploadFile f : docs) {
            ArchivoEscritura archivo = new ArchivoEscritura();
            archivo.setAutor(p.getUsuarioModifica());
            archivo.setUsuarioModifica(p.getUsuarioModifica());
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setFechaCreacion(new Date());
            archivo.setEscritura(e);
            e.getAdjuntos().add(archivo);
            docsEscritura.add(archivo);
        }
        
        e.setAdjuntos(docsEscritura);
        if (e.getId() != null) {
            p.getEscrituras().remove(p.getEscrituras().size() - 1);
        } else {
            e.setPredio(p);
        }
        
        for(int i = 1; i<7 ; i++){
            for(CatPredioRuralTerreno temp : (List<CatPredioRuralTerreno>)terrenoClases.get(i)){
                temp.setPredio(p);
                if(temp.getId() == null)
                    em.persist(temp);
                else
                    em.merge(temp);
            }
        }
        
        em.persist(e);
        
        List<Bloque> bloques = p.getBloques() != null && !p.getBloques().isEmpty() ? p.getBloques() : new ArrayList();
        
        if (!bloques.isEmpty()) {
            for (int i = 0; i < bloques.size(); i++) {
                bloques.get(i).setNumeroBloque(i + 1);
                List<UploadFile> photosBloque = bloques.get(i).getFotosBloque();
                List<BloqueImage> fotosBloque = new ArrayList();

                if (!photosBloque.isEmpty()) {
                    
                    for (UploadFile f : photosBloque) {

                        BloqueImage foto = new BloqueImage();
                        foto.setAutor(p.getUsuarioModifica());
                        foto.setRuta(f.getSavedFile().getName());
                        foto.setDescripcion(f.getDescription());
                        foto.setFechaCreacion(new Date());
                        foto.setBloque(p.getBloques().get(i));
                        fotosBloque.add(foto);
                    }
                    p.getBloques().get(i).setImages(fotosBloque);
                }

                for (int j = 0; j < p.getBloques().get(i).getPisos().size(); j++) {
                    p.getBloques().get(i).getPisos().get(j).setNumeroPiso(j + 1);
                }
            }
        }
        
        // ELIMINA LAS FOTOS DEL PREDIO
        for (ArchivoAux a : photosPredioEliminar) {
            if (a.isEliminado()) {
                Query q = em.createNativeQuery("DELETE FROM cat_predio_image WHERE id = " + a.getIdArchivo());
                q.executeUpdate();
            }
        }
        
        // ELIMINA LAS FOTOS DE LOS BLOQUES
        for (Bloque b : p.getBloques()) {
            for (ArchivoAux a : b.getImagesPreview()) {

                if (a.isEliminado()) {
                    Query q = em.createNativeQuery("DELETE FROM cat_bloque_image WHERE id = " + a.getIdArchivo());
                    q.executeUpdate();
                }
            }
        }
        
        for (UploadFile f : photosPredio) {

            PredioImage foto = new PredioImage();
            foto.setAutor(p.getUsuarioModifica());
            foto.setRuta(f.getSavedFile().getName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            foto.setPredio(p);
            fotosP.add(foto);
        }
        p.setImages(fotosP);
        
        em.merge(p);
        
        for (Contribuyente c : p.getPropietarios()) {
            if(c.getId() != null){
                em.createNativeQuery("UPDATE public.cat_contribuyente_predio SET status=" + c.getTipoPropietario() + " \n"
                        + "WHERE id_contribuyente=" + c.getId() + " AND id_predio=" + p.getId() + "").executeUpdate();            
            }
        }
        if(propietariosEliminados!= null){
            for (Contribuyente c : propietariosEliminados) {
                if(c.getId() != null){
                    em.createNativeQuery("DELETE FROM public.cat_contribuyente_predio \n"
                            + "WHERE id_contribuyente=" + c.getId() + " AND id_predio=" + p.getId() + "").executeUpdate();            
                }
            }
        }
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void updatePredioComplete(Predio p, Terreno t, Escritura e, List<UploadFile> docs, List<Bloque> bloquesEliminar,
            List<Piso> pisosEliminar, List<UsoSuelo> usosEliminar, List<OtrosTipoConstruccion> otrosEliminar,
            List<UploadFile> photosPredio, List<ArchivoAux> photosPredioEliminar, List<UsoSuelo> usosPredioEliminar) {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        em.merge(t);
        p.setUsuarioModifica(user);

        List<ArchivoEscritura> docsEscritura = new ArrayList<>();

        for (UploadFile f : docs) {
            ArchivoEscritura archivo = new ArchivoEscritura();
            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setFechaCreacion(new Date());
            archivo.setEscritura(e);
            e.getAdjuntos().add(archivo);
            docsEscritura.add(archivo);
        }

        e.setAdjuntos(docsEscritura);
        if (e.getId() != null) {
            p.getEscrituras().remove(p.getEscrituras().size() - 1);
        } else {
            e.setPredio(p);
        }

        for (Bloque b : p.getBloques()) {
            for (ArchivoAux a : b.getImagesPreview()) {

                if (a.isEliminado()) {
                    Query q = em.createNativeQuery("DELETE FROM cat_bloque_image WHERE id = " + a.getIdArchivo());
                    q.executeUpdate();
                }
            }
        }

        if (!p.getBloques().isEmpty()) {
            for (int i = 0; i < p.getBloques().size(); i++) {
                p.getBloques().get(i).setNumeroBloque(i + 1);
                List<UploadFile> photosBloque = p.getBloques().get(i).getFotosBloque();
                List<BloqueImage> fotosBloque = new ArrayList();

                if (!photosBloque.isEmpty()) {
                    
                    for (UploadFile f : photosBloque) {

                        BloqueImage foto = new BloqueImage();
                        foto.setAutor(user);
                        foto.setRuta(f.getSavedFile().getName());
                        foto.setDescripcion(f.getDescription());
                        foto.setFechaCreacion(new Date());
                        foto.setBloque(p.getBloques().get(i));
                        fotosBloque.add(foto);
                    }
                    p.getBloques().get(i).setImages(fotosBloque);
                }

                for (int j = 0; j < p.getBloques().get(i).getPisos().size(); j++) {
                    p.getBloques().get(i).getPisos().get(j).setNumeroPiso(j + 1);
                }
            }
        }
        for (OtrosTipoConstruccion c : otrosEliminar) {
            OtrosTipoConstruccion x = em.find(OtrosTipoConstruccion.class, c.getId());
            em.remove(x);
        }
        for (UsoSuelo uso : usosEliminar) {
            UsoSuelo x = em.find(UsoSuelo.class, uso.getId());
            em.remove(x);
        }
        for (Piso pi : pisosEliminar) {
            Piso x = em.find(Piso.class, pi.getId());
            em.remove(x);
        }
        for (Bloque b : bloquesEliminar) {
            Bloque x = em.find(Bloque.class, b.getId());
            em.remove(x);
        }

        List<PredioImage> fotosP = new ArrayList();

        for (UploadFile f : photosPredio) {

            PredioImage foto = new PredioImage();
            foto.setAutor(user);
            foto.setRuta(f.getSavedFile().getName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            foto.setPredio(p);
            fotosP.add(foto);
        }
        p.setImages(fotosP);
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);
        em.merge(p);
        em.flush();

        for (ArchivoAux a : photosPredioEliminar) {
            if (a.isEliminado()) {
                Query q = em.createNativeQuery("DELETE FROM cat_predio_image WHERE id = " + a.getIdArchivo());
                q.executeUpdate();
            }
        }
        for (UsoSuelo u : usosPredioEliminar) {
            UsoSuelo x = em.find(UsoSuelo.class, u.getId());
            em.remove(x);
        }
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
        em.flush();
        for (Contribuyente c : p.getPropietarios()) {
            em.createNativeQuery("UPDATE public.cat_contribuyente_predio SET status=" + c.getTipoPropietario() + " \n"
                    + "WHERE id_contribuyente=" + c.getId() + " AND id_predio=" + p.getId() + "").executeUpdate();
            em.flush();
        }

    }

    @Transactional
    public void updateDatosLegales(Predio p, Escritura e, List<UploadFile> files) {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        p.setUsuarioModifica(user);

        List<ArchivoEscritura> docsEscritura = new ArrayList<>();

        for (UploadFile f : files) {
            ArchivoEscritura archivo = new ArchivoEscritura();
            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setFechaCreacion(new Date());
            archivo.setEscritura(e);
            e.getAdjuntos().add(archivo);
            docsEscritura.add(archivo);
        }

        e.setAdjuntos(docsEscritura);
        if (e.getId() != null) {
            p.getEscrituras().remove(p.getEscrituras().size() - 1);
        }

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - DATOS LEGALES - PREDIO: "+p.getClaveCatastral());
        em.flush();

        for (Contribuyente c : p.getPropietarios()) {

            em.createNativeQuery("UPDATE public.cat_contribuyente_predio SET status=" + c.getTipoPropietario() + " \n"
                    + "WHERE id_contribuyente=" + c.getId() + " AND id_predio=" + p.getId() + "").executeUpdate();

            em.flush();
        }
    }

    @Transactional
    public void updateDatosConstruccion(Predio p, List<Bloque> bloquesEliminar, List<Piso> pisosEliminar, List<UsoSuelo> usosEliminar, List<OtrosTipoConstruccion> otrosEliminar) {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        for (Bloque b : p.getBloques()) {
            for (ArchivoAux a : b.getImagesPreview()) {

                if (a.isEliminado()) {
                    Query q = em.createNativeQuery("DELETE FROM cat_bloque_image WHERE id = " + a.getIdArchivo());
                    q.executeUpdate();
                }
            }
        }

        if (!p.getBloques().isEmpty()) {
            for (int i = 0; i < p.getBloques().size(); i++) {
                p.getBloques().get(i).setNumeroBloque(i + 1);
                List<UploadFile> photosBloque = p.getBloques().get(i).getFotosBloque();
                List<BloqueImage> fotosBloque = new ArrayList();

                if (!photosBloque.isEmpty()) {
                    
                    for (UploadFile f : photosBloque) {

                        BloqueImage foto = new BloqueImage();
                        foto.setAutor(user);
                        foto.setRuta(f.getSavedFile().getName());
                        foto.setDescripcion(f.getDescription());
                        foto.setFechaCreacion(new Date());
                        foto.setBloque(p.getBloques().get(i));
                        fotosBloque.add(foto);
                    }
                    p.getBloques().get(i).setImages(fotosBloque);
                }

                for (int j = 0; j < p.getBloques().get(i).getPisos().size(); j++) {
                    p.getBloques().get(i).getPisos().get(j).setNumeroPiso(j + 1);
                }
            }
        }
        for (OtrosTipoConstruccion c : otrosEliminar) {
            OtrosTipoConstruccion x = em.find(OtrosTipoConstruccion.class, c.getId());
            em.remove(x);
        }
        for (UsoSuelo uso : usosEliminar) {
            UsoSuelo x = em.find(UsoSuelo.class, uso.getId());
            em.remove(x);
        }
        for (Piso pi : pisosEliminar) {
            Piso x = em.find(Piso.class, pi.getId());
            em.remove(x);
        }
        for (Bloque b : bloquesEliminar) {
            Bloque x = em.find(Bloque.class, b.getId());
            em.remove(x);
        }

        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - CONSTRUCCIÓN - PREDIO: "+p.getClaveCatastral());
        em.flush();

    }

    @Transactional
    public void updateDatosGenerales(Predio p, Terreno t, List<UploadFile> photos, List<ArchivoAux> eliminar) {

        em.merge(t);

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        List<PredioImage> fotos = new ArrayList();

        for (UploadFile f : photos) {

            PredioImage foto = new PredioImage();
            foto.setAutor(user);
            foto.setRuta(f.getSavedFile().getName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            foto.setPredio(p);
            fotos.add(foto);
        }
        p.setImages(fotos);
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        em.merge(p);

        for (ArchivoAux a : eliminar) {

            if (a.isEliminado()) {
                Query q = em.createNativeQuery("DELETE FROM cat_predio_image WHERE id = " + a.getIdArchivo());
                q.executeUpdate();
            }
        }
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - DATOS GENERALES - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void updateDatosTerreno(Predio p, Terreno t, List<UsoSuelo> usosEliminar) {

        em.merge(t);

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        em.merge(p);

        for (UsoSuelo u : usosEliminar) {
            UsoSuelo x = em.find(UsoSuelo.class, u.getId());
//            Query q = em.createNativeQuery("DELETE FROM cat_otros_tipos_construccion WHERE id = " + obra.getId());
//            q.executeUpdate();
            em.remove(x);
        }
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - DATOS TERRENO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void crearPredioMunicipal(Predio p) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        
        p.setFechaCreacion(new Date());
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        em.persist(p);
        auditServices.guardarUsuarioTransaccion(user, "CREACIÓN DE PREDIO MUNICIPAL - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void activar(Predio p, Terreno t) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        // t.setActivo((short) 0);
        p.setTerreno(t);
        // em.merge(t);

        p.setFechaCreacion(new Date());
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);
        //p.setEstado((short)0);

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTIVACIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void crearPredioMunicipal(Predio p, Long idTipoEscritura, Escritura e, List<UploadedDocument> files) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        Object[] params2 = new Object[1];
        params2[0] = idTipoEscritura;
        List<TipoEscritura> tiposEsc = tipoEscrituraService.findByNamedQuery("TipoEscritura.findById", params2);
        TipoEscritura te = new TipoEscritura();
        if (!tiposEsc.isEmpty()) {
            te = tiposEsc.get(0);
        }
        //  e.setTipo(te);
        e.setPredio(p);

        List<Archivo> docs = new ArrayList<Archivo>();
        for (UploadedDocument f : files) {
            Archivo archivo = new Archivo();

            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setEscritura(e);
            docs.add(archivo);
        }

        //e.setArchivoList(docs);
        List<Escritura> escrituras = new ArrayList<Escritura>();
        escrituras.add(e);
        // p.setEscrituras(escrituras);
        p.setFechaCreacion(new Date());
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

        String cla = p.getTerreno().getTerrenoPK().getCodParroquia() + "-" + p.getTerreno().getTerrenoPK().getCodZona() + "-" + p.getTerreno().getTerrenoPK().getCodSector() + "-" + p.getTerreno().getTerrenoPK().getCodManzana() + "-" + p.getTerreno().getTerrenoPK().getCodSolar() + "-" + p.getCodPhh();
        p.setClaveCatastral(cla);
        em.persist(p);
        auditServices.guardarUsuarioTransaccion(user, "CREACIÓN DE PREDIO URBANO - PREDIO: "+p.getClaveCatastral());
        em.flush();

    }

    @Transactional
    public void actualizarDatosConstruccion(Predio p, Long idTipoDocumento, DatosAutorizacion da) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        Object[] params = new Object[1];
        params[0] = idTipoDocumento;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setResponsable(user);
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);
        // p.getDatosAutorizacionList().add(da);
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user);

//        if (p.getSolar() == null) {
//            p.setSolar("Solar " + p.getTerreno().getTerrenoPK().getCodSolar());
//        }
        if (p.getManzana() == null) {
            p.setManzana("Manzana " + p.getTerreno().getTerrenoPK().getCodManzana());
        }

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - DATOS CONSTRUCCIÓN - PREDIO: "+p.getClaveCatastral());
        em.flush();

    }

    @Transactional
    public void actualizarDatosTerreno(Terreno t) throws Exception {

        em.merge(t);
        em.flush();
    }

    @Transactional
    public void actualizarDatosGenerales(Predio p) throws Exception {

        em.merge(p);
        em.flush();
    }

    @Transactional
    public void actualizarDatosTerreno(Terreno t, Predio p, Long idTipoDoc, Long idTipoEsc, DatosAutorizacion da, Escritura e, boolean conEscritura, List<UploadedImage> files, List<UploadedImage> files1) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        Archivo archivo = new Archivo();
        Archivo archivo1 = new Archivo();

        if (!files.isEmpty()) {

            UploadedImage ui = files.get(0);
            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setExtension(FilenameUtils.getExtension(ui.getFileName()));
            archivo.setRuta(ui.getSavedFile().getName());
            archivo.setDescripcion(ui.getDescription());
            em.persist(archivo);
            em.flush();

            //p.setPlano(archivo);
        }

        if (!files1.isEmpty()) {

            UploadedImage ui = files1.get(0);
            archivo1.setAutor(user);
            archivo1.setUsuarioModifica(user);
            archivo1.setExtension(FilenameUtils.getExtension(ui.getFileName()));
            archivo1.setRuta(ui.getSavedFile().getName());
            archivo1.setDescripcion(ui.getDescription());
            em.persist(archivo1);
            em.flush();

            // p.setFicha(archivo1);
        }
        em.merge(t);
        p.setTerreno(t);

        Object[] params1 = new Object[1];
        params1[0] = idTipoDoc;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);
        //p.getDatosAutorizacionList().add(da);
        
        if (conEscritura) {
            Object[] params2 = new Object[1];
            params2[0] = idTipoEsc;
            List<TipoEscritura> tiposEsc = tipoEscrituraService.findByNamedQuery("TipoEscritura.findById", params2);
            TipoEscritura te = new TipoEscritura();
            if (!tiposEsc.isEmpty()) {
                te = tiposEsc.get(0);
            }
            // e.setTipo(te);
            e.setPredio(p);
            p.getEscrituras().add(e);
        }
//        if (p.getSolar() == null) {
//            p.setSolar("Solar " + t.getTerrenoPK().getCodSolar());
//        }
//        if (p.getManzana() == null) {
//            p.setManzana("Manzana " + t.getTerrenoPK().getCodManzana());
//        }

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - DATOS TERRENO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void eliminarDatosConstruccin(Predio p, Long idTipoDoc, DatosAutorizacion da, List<Bloque> bloques, List<Piso> pisos) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        ObjectMapper mapper = new ObjectMapper();

        Object[] params1 = new Object[1];
        params1[0] = idTipoDoc;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);
        da.setResponsable(user);
        //p.getDatosAutorizacionList().add(da);

        String datosPredio = mapper.writeValueAsString(p);
        String datoAnterior = datosPredio + "@dusa@{blosqueList: [ ";
        String datoActual = datosPredio + "@dusa@{blosqueList: [ ";

        for (Piso pi : pisos) {
            Piso x = em.find(Piso.class, pi.getId());
            em.remove(x);

        }

        /* Para guarda el dato anterior*/
//        for (Bloque b : bloques) {
//            HistoricoBloquePK historicoBloquePK = new HistoricoBloquePK(b.getId(), new Date());
//            Bloque old = em.find(Bloque.class, b.getId());
//            datoAnterior += mapper.writeValueAsString(old);
//            HistoricoBloque hb = new HistoricoBloque(old);
//            hb.setUsuarioModifica(user);
//            hb.setHistoricoBloquePK(historicoBloquePK);
//            em.persist(hb);
//            em.remove(old);
//        }
        for (Bloque b : p.getBloques()) {
            String val = mapper.writeValueAsString(b);
            datoActual += val;
            datoAnterior += val;
        }
        datoAnterior += "]}";
        datoActual += "]}";

//        if (p.getSolar() == null) {
//            p.setSolar("Solar ");
//        }
        if (p.getManzana() == null) {
            p.setManzana("Manzana ");
        }

        InetAddress address = InetAddress.getLocalHost();

        //LogTransaccion logs = new LogTransaccion();
        //logs.setTerminal(address.getHostName());
        //logs.setIpTerminal(address.getHostAddress());

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - ELIMINAR DATOS CONSTRUCCIÓN - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void expropiarPredio(Predio p, Long idTipoDoc, DatosAutorizacion da) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        ObjectMapper mapper = new ObjectMapper();

        Object[] params1 = new Object[1];
        params1[0] = idTipoDoc;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);
        da.setResponsable(user);
        p.getDatosAutorizacion().add(da);

//        Object[] params = new Object[3];
//        params[0] = "R";
//        params[1] = config.getDbConfiguration().getString("ruc_municipio");
//        params[2] = (short) 1;
//
//        List<Contribuyente> busqueda = contribuyenteService.findByNamedQuery("Contribuyente.findByTipoIdentificacion", params);
//        List<Contribuyente> nuevosPropietarios = new ArrayList<>();
//        if (!busqueda.isEmpty()) {
//            //nuevosPropietarios.add(busqueda.get(0));
//            
//        }
//
//        p.setPropietarios(nuevosPropietarios);
        p.setDominio((short) 2);
        em.merge(p);
        
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - EXPROPIACIÓN DE PREDIO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void registrarInspeccion(Predio p, DatoInspeccion inspeccion, Long idTipoDoc, DatosAutorizacion da, Integer idUsuario, List<UploadedImage> files) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        ObjectMapper mapper = new ObjectMapper();

        Object[] params1 = new Object[1];
        params1[0] = idTipoDoc;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);
        da.setResponsable(user);
        p.getDatosAutorizacion().add(da);

        //Predio oldPredio = em.find(Predio.class, p.getId());
        List<DatoInspeccion> datoInspeccionList = p.getDatoInspecciones();
        //String datoAnterior = mapper.writeValueAsString(oldPredio) + "@dusa@{inpeccionList:[";

//        String datoActual = mapper.writeValueAsString(p) + "@dusa@{inspeccionList:[";
//
//        if (datoInspeccionList.isEmpty()) {
//            datoAnterior += "No tiene inspeccion registrada.";
//        } else {
//            for (DatoInspeccion dins : datoInspeccionList) {
//                datoAnterior += mapper.writeValueAsString(dins);
//                datoActual += mapper.writeValueAsString(dins);
//            }
//            datoAnterior += "]}";
//        }
        em.merge(p);

        Object[] params = new Object[1];
        params[0] = idUsuario;

        List<Usuario> users = usuarioService.findByNamedQuery("Usuario.findById", params);
        Usuario u = new Usuario();
        if (!users.isEmpty()) {
            u = users.get(0);
        }

        List<FotosInspeccion> fotosInspeccion = new ArrayList<FotosInspeccion>();
        for (UploadedImage f : files) {

            FotosInspeccion fotoInspeccion = new FotosInspeccion();

            fotoInspeccion.setAutor(u.getNombre());
            fotoInspeccion.setUsuarioModifica(user);
            fotoInspeccion.setRuta(f.getSavedFile().getName());
            fotoInspeccion.setDescripcion(f.getDescription());

            fotoInspeccion.setFechaModificacion(new Date());
            fotoInspeccion.setFechaCreacion(new Date());
            fotosInspeccion.add(fotoInspeccion);
            fotoInspeccion.setInspeccion(inspeccion);
        }

        inspeccion.setPredio(p);
        inspeccion.setUsuario(u);
        //inspeccion.set
        em.persist(inspeccion);

//        datoActual += mapper.writeValueAsString(inspeccion);
//        datoActual += "]}";

        /*Guardar datos de Auditoria*/
        
        auditServices.guardarUsuarioTransaccion(user, "PREDIO URBANO - REGISTRO DE INSPECCION - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void RegistrarExencion(Predio p, Long idTipoDoc, DatosAutorizacion da, Exencion exc, RazonExencion razon) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        Object[] params1 = new Object[1];
        params1[0] = idTipoDoc;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);
        da.setResponsable(user);
        p.getDatosAutorizacion().add(da);

        em.merge(p);

        /*Guardar datos de Auditoria*/
        
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - REGISTRO DE EXENCIÓN - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void eliminarPredio(Predio p, DatosAutorizacion da) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        ObjectMapper mapper = new ObjectMapper();

        da.setIdPredio(p);
        da.setResponsable(user);
        p.getDatosAutorizacion().add(da);

        Predio oldPredio = em.find(Predio.class, p.getId());
        // String datoAnterior = mapper.writeValueAsString(oldPredio) + "@dusa@{blosqueList: [ ";

        // p.setEstado((short) 1);
        //  String datoActual = mapper.writeValueAsString(p) + "@dusa@{blosqueList: [ ";
        Terreno t = em.find(Terreno.class, p.getTerreno().getTerrenoPK());
        String ph = "";
//        if (p.getSolar() == null) {
//            p.setSolar("Solar ");
//        }
        if (p.getManzana() == null) {
            p.setManzana("Manzana ");
        }

//        for (Bloque b : p.getBloques()) {
//            String val = mapper.writeValueAsString(b);
//            datoActual += val;
//            datoAnterior += val;
//        }
        // datoAnterior += "]}";
        // datoActual += "]}";
//
//        if (p.getCodPhh() != 0) {
//
//            HistoricoPredio hp = new HistoricoPredio(oldPredio);
//            HistoricoPredioPK hppk = new HistoricoPredioPK(p.getId(), new Date());
//            hp.setUsuarioModifica(user);
//            hp.setHistoricoPredioPK(hppk);
//            em.persist(hp);
//            em.merge(p);
//        } else {
//            String datosTerreno = mapper.writeValueAsString(t);
//            datoAnterior += "@dusa@" + datosTerreno;
//
//            HistoricoTerreno ht = new HistoricoTerreno(t);
//            HistoricoTerrenoPK htpk = new HistoricoTerrenoPK(new Date(), user);
//            ht.setHistoricoTerrenoPK(htpk);
//            em.persist(ht);
//
//            // t.setActivo((short) 1);
//            for (int i = 0; i < t.getPredios().size(); i++) {
//                t.getPredioList().get(i).setEstado((short) 1);
//                if (t.getPredioList().get(i) == null) {
//                    t.getPredioList().get(i).setSolar("Solar ");
//                }
//                if (t.getPredioList().get(i).getManzana() == null) {
//                    t.getPredioList().get(i).setManzana("Manzana ");
//                }
//                ph += t.getPredioList().get(i).getClaveCatastral() + " : ";
//
//                HistoricoPredio hp = new HistoricoPredio(t.getPredioList().get(i));
//                HistoricoPredioPK hppk = new HistoricoPredioPK(t.getPredioList().get(i).getId(), new Date());
//                hp.setHistoricoPredioPK(hppk);
//                hp.setUsuarioModifica(user);
//                em.persist(hp);
//            }
//            em.merge(t);
//            datoActual += "@dusa@" + mapper.writeValueAsString(t);
//        }
        em.remove(oldPredio);
        /*Guardar datos de Auditoria*/
        
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - ELIMINACIÓN DE PREDIO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void actualizarDatosGenerales(Predio p, Long idTipoEscritura, Long idTipoDocumento, Escritura e, DatosAutorizacion da, List<UploadedImage> files, List<UploadedImage> files1) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();
        
        Object[] params1 = new Object[1];
        params1[0] = idTipoDocumento;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);

        for (UploadedImage f : files1) {
            Archivo archivo = new Archivo();

            archivo.setAutor(user);
            archivo.setUsuarioModifica(user);
            archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());

            em.persist(archivo);
            em.flush();
            //da.setDocumento(archivo);

        }

//        p.getDatosAutorizacionList().add(da);
        
        // Object[] params2 = new Object[1];
        //params2[0] = idTipoEscritura;
        //   List<TipoEscritura> tiposEsc = tipoEscrituraService.findByNamedQuery("TipoEscritura.findById", idTipoEscritura);
        //TipoEscritura te = em.getReference(TipoEscritura.class, idTipoEscritura);
        TipoEscritura te = em.find(TipoEscritura.class, idTipoEscritura);

//        e.setTipo(te);
        e.setPredio(p);
        p.getEscrituras().add(e);

        em.merge(p);
        em.flush();

//        Predio predio = em.find(Predio.class, p.getId());
//        for (UploadedImage f : files) {
//            Archivo archivo = new Archivo();
//
//            archivo.setAutor(user.getPrincipal().toString());
//            archivo.setUsuarioModifica(user.getPrincipal().toString());
//            archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
//            archivo.setRuta(f.getSavedFile().getName());
//            archivo.setDescripcion(f.getDescription());
//
//            em.persist(archivo);
//            em.flush();
//
//            //PredioArchivo pa = new PredioArchivo(new PredioArchivoPK());
//           // pa.setArchivo(archivo);
//            pa.setPredio(predio);
//            em.persist(pa);
//        }
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - DATOS GENERALES - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void eliminarFotos(Predio p, Long idTipoDocumento, DatosAutorizacion da, List<ArchivoAux> images, List<UploadedImage> files) throws Exception {

        Subject user = SecurityUtils.getSubject();
        Object[] params1 = new Object[1];
        params1[0] = idTipoDocumento;

        List<TipoDocumento> tiposDocs = tipoDocumentoService.findByNamedQuery("TipoDocumento.findById", params1);
        TipoDocumento td = new TipoDocumento();
        if (!tiposDocs.isEmpty()) {
            td = tiposDocs.get(0);
        }
        da.setIdTipoDocumento(td);
        da.setIdPredio(p);

        for (UploadedImage f : files) {
            Archivo archivo = new Archivo();

            archivo.setAutor(user.getPrincipal().toString());
            archivo.setUsuarioModifica(user.getPrincipal().toString());
            archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());

            em.persist(archivo);
            em.flush();
            //da.setDocumento(archivo);

        }

//        p.getDatosAutorizacionList().add(da);
        em.merge(p);
        em.flush();

        for (ArchivoAux a : images) {

            if (a.isEliminado()) {
                Query q = em.createNativeQuery("DELETE FROM cat_predio_archivo WHERE id_archivo = " + a.getIdArchivo() + " AND id_predio = " + a.getIdPredio());
                q.executeUpdate();
            }
        }
        auditServices.guardarUsuarioTransaccion(user.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - ELIMINACIÓN DE FOTOS - PREDIO: "+p.getClaveCatastral());
        em.flush();

    }
    
    public Boolean unificarPredios(List<Predio> prediosList, Predio nuevoPredio){
        try {
            Subject user = SecurityUtils.getSubject();
            Terreno t;
            String s="";
            
            for(Predio p : prediosList){
                p.setEstado((short)4);
                p.setFechaModificacion(new Date());
                p.setUsuarioModifica(user.getPrincipal().toString());
                em.merge(p);
                s = s + p.getClaveCatastral() + " ";
            }
            
            t = nuevoPredio.getTerreno();
            t.setPredios(new ArrayList<Predio>());
            
            nuevoPredio.setFechaCreacion(new Date());
            nuevoPredio.setFechaModificacion(new Date());
            nuevoPredio.setUsuarioModifica(user.getPrincipal().toString());

            /*documentos = new ArrayList<>();
            for (UploadedDocument f : files) {
                ArchivoEscritura archivo = new ArchivoEscritura();

                archivo.setAutor(user.getPrincipal().toString());
                archivo.setUsuarioModifica(user.getPrincipal().toString());
                archivo.setRuta(f.getSavedFile().getName());
                archivo.setDescripcion(f.getDescription());
                archivo.setFechaCreacion(new Date());
                documentos.add(archivo);
            }*/
            
            nuevoPredio.setFechaCreacion(new Date());
            nuevoPredio.setFechaModificacion(new Date());
            nuevoPredio.setUsuarioModifica(user.getPrincipal().toString());
            //predio.setEscrituras(null);            
            nuevoPredio.setImages(null);
            //predio.setPropietarios(null);
           
            t.getPredios().add(nuevoPredio);
            em.persist(t);
            
            if(nuevoPredio.getPhotos() != null){
                nuevoPredio.setImages(new ArrayList());
                for (UploadFile f : nuevoPredio.getPhotos()) {
                    PredioImage foto = new PredioImage();
                    foto.setAutor(user.getPrincipal().toString());
                    foto.setRuta(f.getFileName());
                    foto.setDescripcion(f.getDescription());
                    foto.setFechaCreacion(new Date());
                    foto.setPredio(nuevoPredio);
                    em.persist(foto);
                }
            }
            auditServices.guardarUsuarioTransaccion(user.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - UNIFICACIÓN DE PREDIOS - PREDIOS: "+s+" - PREDIO NUEVO: "+nuevoPredio.getClaveCatastral());
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Transactional
    public Terreno actulizarPrediosUnificado(Predio p, Terreno terren, List<Predio> predios, Escritura e, List<UploadedDocument> files) throws Exception {
        Terreno terrenoUnificado = null;
        try {
            Subject user = SecurityUtils.getSubject();
            
            Predio predioUni = new Predio(p);
            predioUni.setEstado(new Short("1"));
            predioUni.setTipoPredio(p.getTipoPredio());
            predioUni.setFechaCreacion(new Date());
            predioUni.setFechaModificacion(new Date());
            predioUni.setUsuarioModifica(user.getPrincipal().toString());
            predioUni.setImages(new LinkedList<PredioImage>());
            predioUni.setBloques(new LinkedList<Bloque>());
            predioUni.setContribuyentePredioList(new LinkedList<ContribuyentePredio>());
            terrenoUnificado = new Terreno(terren);
            log.info("Terreno PK: " + terrenoUnificado.getTerrenoPK());
            String claves = "( ";
            Manzana manzana = manzaService.getManzana(terren.getTerrenoPK());
            int numeroP = manzana.getSecuencia();
            log.info("numero ultimo de Predio: " + numeroP);
            numeroP++;
            log.info("Siguiente: " + numeroP);
            log.info(p.getClaveCatastral());
            TerrenoPK tuPK = new TerrenoPK(manzana.getManzanaPK(), "" + numeroP);
            claves += p.getClaveCatastral() + " , ";
            predioUni.setClaveCatastral(tuPK.getClave());
            int numero = predioService.NumeroUltimoBloque(p);
            for (Predio predio : predios) {
                predio.setEstado(new Short("0"));
                for (Bloque b : predio.getBloques()) {
                    Bloque bloque = new Bloque(b);
                    bloque.setNumeroBloque(++numero);
                    bloque.setPredio(predioUni);
                    predioUni.getBloques().add(bloque);
                }
                for (PredioImage pi : predio.getImages()) {
                    PredioImage imgPredio = new PredioImage(pi);
                    imgPredio.setPredio(predioUni);
                    predioUni.getImages().add(imgPredio);
                }
                em.merge(predio);
                claves += predio.getClaveCatastral() + " , ";
                Terreno te = em.find(Terreno.class, predio.getTerreno().getTerrenoPK());
                te.setEstado(new Short("0"));
                em.merge(te);
            }
            claves += " )";
            e.setPredio(p);
            e.setPredio(predioUni);
            List<Archivo> doc = new ArrayList<>();
            for (UploadedDocument f : files) {
                Archivo archivo = new Archivo();
                archivo.setAutor(user.getPrincipal().toString());
                archivo.setUsuarioModifica(user.getPrincipal().toString());
                archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
                archivo.setRuta(f.getSavedFile().getName());
                archivo.setDescripcion(f.getDescription());
                archivo.setEscritura(e);
                doc.add(archivo);
            }
            terrenoUnificado.setTerrenoPK(tuPK);
            log.info("Terreno PK: " + terrenoUnificado.getTerrenoPK());
            predioUni.setTerreno(terrenoUnificado);
            List<Predio> addPredio = new ArrayList<>();
            addPredio.add(predioUni);
            terrenoUnificado.setPredios(addPredio);
            //em.flush();
            terrenoUnificado = em.merge(terrenoUnificado);
            log.info("Predio Registrado: ");
            
            
            Terreno t = em.find(Terreno.class, p.getTerreno().getTerrenoPK());
            t.setEstado(new Short("0"));
            em.merge(t);
            auditServices.guardarUsuarioTransaccion(user.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - PREDIOS UNIFICADOS - PREDIO: "+p.getClaveCatastral());
            em.flush();
        } catch (NumberFormatException ex) {
            log.error(ex.getMessage());
        }
        return terrenoUnificado;
    }

    @Transactional
    public void catastrarEscritura(Predio p, Escritura e, List<UploadedDocument> files) {
        List<Exencion> exenciones = p.getExenciones();

        for (Exencion ex : exenciones) {
            Exencion eliminar = em.find(Exencion.class, ex);
            em.remove(eliminar);
        }

        Subject subject = SecurityUtils.getSubject();
        String user = subject.getPrincipal().toString();

        p.setUsuarioModifica(user);

        List<ArchivoEscritura> docsEscritura = new ArrayList<>();

        if (files != null) {
            for (UploadedDocument f : files) {
                ArchivoEscritura archivo = new ArchivoEscritura();
                archivo.setAutor(user);
                archivo.setUsuarioModifica(user);
                archivo.setRuta(f.getSavedFile().getName());
                archivo.setDescripcion(f.getDescription());
                archivo.setFechaCreacion(new Date());
                archivo.setEscritura(e);
                e.getAdjuntos().add(archivo);
                docsEscritura.add(archivo);
            }
        }

        e.setAdjuntos(docsEscritura);
        e.setPredio(p);
        p.getEscrituras().add(e);

        em.merge(p);
        auditServices.guardarUsuarioTransaccion(user, "ACTUALIZACIÓN DE PREDIO URBANO - CATASTRAR ESCRITURA - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void registrarContratoArrendamiento(Predio p, List<Contribuyente> propietarios, ContratoArrendamiento contrato) throws Exception {

        Subject user = SecurityUtils.getSubject();

        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user.getPrincipal().toString());
        contrato.setHabilitado((short) 1);
        em.persist(contrato);
        //em.flush();

        p.setContratoArrendamiento(contrato);
//        Object[] params2 = new Object[1];
//        params2[0] = idEscritura;
//        List<TipoEscritura> tiposEsc = tipoEscrituraService.findByNamedQuery("TipoEscritura.findById", params2);
//        TipoEscritura te = new TipoEscritura();
//        if (!tiposEsc.isEmpty()) {
//            te = tiposEsc.get(0);
//        }
//        e.setTipo(te);
//        e.setPredio(p);
//        p.getEscrituras().add(e);
        // p.setPropietarios(propietarios);
        em.merge(p);

//        /*Guardar datos de Auditoria*/
//        Transaccion trans = new Transaccion();
//        TipoTransaccion tt = em.find(TipoTransaccion.class, (short) 14);
//        trans.setFechaInicio(new Date());
//        trans.setUsuario(user.getPrincipal().toString());
//        trans.setTipoTrans(tt);
//        trans.setReferencia(tt.getDescripcion());
//        trans.setObservacion("Se realizo contrato de arrendamiento al predio: " + p.getClaveCatastral());
//        trans.setFechaFin(new Date());
//        //trans.setTipoDocSustento(idEscritura.intValue());
//        em.persist(trans);
//
//        InetAddress address = InetAddress.getLocalHost();
//
//        LogTransaccion logs = new LogTransaccion();
//        logs.setFecha(new Date());
//        logs.setUsuario(user.getPrincipal().toString());
//        logs.setProceso(tt.getDescripcion());
//        logs.setObjeto("ca_predio, contribuyente");
//        logs.setEvento("Se realizo contrato de arrendamiento al predio: " + p.getClaveCatastral());
//        logs.setDatoAnterior("");
//        logs.setDatoActual("");
//        logs.setTerminal(address.getHostName());
//        logs.setIpTerminal(address.getHostAddress());
//        logs.setAccion(tt.getId());
//        em.persist(logs);
        auditServices.guardarUsuarioTransaccion(user.getPrincipal().toString(), "REGISTRO DE CONTRATO DE ARRENDAMIENTO - PREDIO: "+p.getClaveCatastral());
        em.flush();
    }

    @Transactional
    public void fraccionarPredio(Predio p, List<Predio> predios, List<UploadedDocument> files, List<UploadedImage> photos, List<ArchivoAux> eliminar) {

        Subject user = SecurityUtils.getSubject();
        Terreno t;
        String s="";
        p.setEstado((short)3);
        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user.getPrincipal().toString());
        em.merge(p);
        
       /* List<ArchivoEscritura> documentos = new ArrayList<>();
        for (UploadedDocument f : files) {
            ArchivoEscritura archivo = new ArchivoEscritura();

            archivo.setAutor(user.getPrincipal().toString());
            archivo.setUsuarioModifica(user.getPrincipal().toString());
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setFechaCreacion(new Date());
            documentos.add(archivo);
        }

        List<PredioImage> fotos = new ArrayList();

        for (UploadedImage f : photos) {

            PredioImage foto = new PredioImage();
            foto.setAutor(user.getPrincipal().toString());
            foto.setRuta(f.getSavedFile().getName());
            foto.setDescripcion(f.getDescription());
            foto.setFechaCreacion(new Date());
            fotos.add(foto);
        }*/
       
        
       
        for (Predio predio : predios) {
            s = s + predio.getClaveCatastral()+" ";
            t = predio.getTerreno();
            t.setPredios(new ArrayList<Predio>());
            
            predio.setFechaCreacion(new Date());
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(user.getPrincipal().toString());

            /*documentos = new ArrayList<>();
            for (UploadedDocument f : files) {
                ArchivoEscritura archivo = new ArchivoEscritura();

                archivo.setAutor(user.getPrincipal().toString());
                archivo.setUsuarioModifica(user.getPrincipal().toString());
                archivo.setRuta(f.getSavedFile().getName());
                archivo.setDescripcion(f.getDescription());
                archivo.setFechaCreacion(new Date());
                documentos.add(archivo);
            }*/
            
            predio.setFechaCreacion(new Date());
            predio.setFechaModificacion(new Date());
            predio.setUsuarioModifica(user.getPrincipal().toString());
            //predio.setEscrituras(null);            
            predio.setImages(null);
            //predio.setPropietarios(null);
           
            t.getPredios().add(predio);
            em.persist(t);
            
            if(predio.getPhotos() != null){
                predio.setImages(new ArrayList());
                for (UploadFile f : predio.getPhotos()) {
                    PredioImage foto = new PredioImage();
                    foto.setAutor(user.getPrincipal().toString());
                    foto.setRuta(f.getFileName());
                    foto.setDescripcion(f.getDescription());
                    foto.setFechaCreacion(new Date());
                    foto.setPredio(predio);
                    em.persist(foto);
                }
            }            
        }
        //this.borrarPredio(p);
        auditServices.guardarUsuarioTransaccion(user.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - FRACCIONAR PREDIO - PREDIO: "+p.getClaveCatastral()+" - PREDIOS NUEVOS: "+s);
        em.flush();
    }

    public void AdicionarPHs(Predio p, List<Predio> predios, Escritura e, Long idTipoEscritura, List<UploadedDocument> files) {

        Subject user = SecurityUtils.getSubject();

        Predio pp = em.find(Predio.class, p.getId());

        pp.setFechaModificacion(new Date());
        pp.setUsuarioModifica(user.getPrincipal().toString());
//        pp.setEdificio(p.getEdificio());
        em.merge(pp);

        Object[] params = new Object[1];
        params[0] = idTipoEscritura;
        List<TipoEscritura> tiposEsc = tipoEscrituraService.findByNamedQuery("TipoEscritura.findById", params);
        TipoEscritura te = new TipoEscritura();
        if (!tiposEsc.isEmpty()) {
            te = tiposEsc.get(0);
        }
//        e.setTipo(te);

        List<Archivo> docs = new ArrayList<Archivo>();
        for (UploadedDocument f : files) {
            Archivo archivo = new Archivo();

            archivo.setAutor(user.getPrincipal().toString());
            archivo.setUsuarioModifica(user.getPrincipal().toString());
            archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
            archivo.setRuta(f.getSavedFile().getName());
            archivo.setDescripcion(f.getDescription());
            archivo.setEscritura(e);
            docs.add(archivo);
        }

//        e.setArchivoList(docs);
        List<Bloque> bs = new ArrayList<Bloque>();

        TerrenoPK pk = p.getTerreno().getTerrenoPK();
        Terreno terreno = em.find(Terreno.class, pk);

        if (terreno == null) {

//            terreno = new Terreno(p.getTerreno());
//            terreno.setTerrenoPK(pk);
        }

//        terreno.setLindero(terrenoLindero);
        for (Predio predio : predios) {

            predio.setTerreno(terreno);
            List<Contribuyente> conts = new ArrayList<Contribuyente>();
            predio.setPropietarios(new ArrayList<Contribuyente>());
            for (Contribuyente c : pp.getPropietarios()) {
                Contribuyente ct = em.find(Contribuyente.class, c.getId());
                if (ct != null) {
                    predio.getPropietarios().add(ct);
                }
                // c.getPredioList().add(predio);

            }
            //predio.setPropietarios(pp.getPropietarios());
//
//            Bloque torre = p.BloquePorNumeroBloque(predio.getNroTorre());
//
//            Bloque nuevo = new Bloque(torre);
//            Piso nuevoPiso = new Piso();
//            List<Piso> pisos = new ArrayList<Piso>();
//            nuevoPiso.setArea(new BigDecimal(predio.getAreaBloquePH()));
//            nuevoPiso.setNumeroPiso(1);
//            nuevoPiso.setBloque(nuevo);
//            pisos.add(nuevoPiso);
//            nuevo.setListaPisos(pisos);
//            nuevo.setNumeroBloque(1);
//            nuevo.setAreaTotal(new BigDecimal(predio.getAreaBloquePH()));
//            nuevo.setPredio(predio);

//            bs = new ArrayList<Bloque>();
//            bs.add(nuevo);
//
//            predio.setBloqueList(bs);
//            List<Escritura> le = new ArrayList<Escritura>();
            docs = new ArrayList<Archivo>();
            for (UploadedDocument f : files) {
                Archivo archivo = new Archivo();

                archivo.setAutor(user.getPrincipal().toString());
                archivo.setUsuarioModifica(user.getPrincipal().toString());
                archivo.setExtension(FilenameUtils.getExtension(f.getFileName()));
                archivo.setRuta(f.getSavedFile().getName());
                archivo.setDescripcion(f.getDescription());
                archivo.setEscritura(e);
                docs.add(archivo);
            }

//            e.setArchivoList(docs);
//            e.setPredio(predio);
//            le.add(e);
//            predio.setDireccion(p.getDireccion());
            predio.setManzana(p.getManzana());
//            predio.setSolar(p.getSolar());
//            predio.setEscrituras(le);
//            predio.setFechaCreacion(new Date());
//            predio.setFechaModificacion(new Date());
//            predio.setUsuarioModifica(user.getPrincipal().toString());
//            predio.setBarrio(p.getBarrio());
//            predio.setSector(p.getSector());
//            predio.setEdificio(p.getEdificio());

            em.persist(predio);
            em.flush();
            bs.clear();
        }
        auditServices.guardarUsuarioTransaccion(user.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - ADICIÓN DE PH");
        em.flush();
    }

    public Predio obtenerPredio(String provincia, String canton, String parroquia, String zona, String sector, String manzana, String solar, String bloque, String phv, String phh) {

        Object[] params = {parroquia, zona, sector, manzana, solar, bloque, phv, phh};

        Predio p = (Predio) aclServices.find("SELECT r FROM Predio r JOIN r.terreno t WHERE t.terrenoPK.codParroquia = :parroquia"
                + " AND t.terrenoPK.codZona = :zona AND t.terrenoPK.codSector = :sector AND t.terrenoPK.codManzana = :manzana "
                + "AND t.terrenoPK.codSolar = :solar AND r.codBloque = :bloque AND r.codPhv = :phv AND r.codPhh = :phh AND"
                + " r.tipoPredio = true", new String[]{"parroquia", "zona", "sector", "manzana", "solar", "bloque", "phv", "phh"},
                params);

        return p;

    }

    public Predio obtenerPredioRustico(String parroquia, String zona, String sector, String manzana, String solar) {
        Object[] params = {parroquia, zona, sector, manzana, solar};

        Predio p = (Predio) aclServices.find("SELECT r FROM Predio r JOIN r.terreno t WHERE t.terrenoPK.codParroquia = :parroquia"
                + " AND t.terrenoPK.codZona = :zona AND t.terrenoPK.codSector = :sector AND t.terrenoPK.codManzana = :manzana "
                + "AND t.terrenoPK.codSolar = :solar AND r.tipoPredio = false", new String[]{"parroquia", "zona", "sector", "manzana", "solar"},
                params);

        return p;
    }

    public Predio obtenerPredioEliminado(short provincia, short canton, short parroquia, short zona, short sector, short manzana, short solar, short ph) {

        short val = 1;
        Object[] params = {provincia, canton, parroquia, zona, sector, manzana, solar, ph, val};

        List<Predio> predios = predioService.findByNamedQuery("Predio.findByCodes", params);
        if (predios != null && !predios.isEmpty()) {
            return predios.get(0);
        }
        return null;

    }

    public Predio obtenerPredio(Terreno t, short ph) {

        String provinciaCod = t.getTerrenoPK().getCodProvincia();
        String cantonCod = t.getTerrenoPK().getCodCanton();
        String parroquiaCod = t.getTerrenoPK().getCodParroquia();
        String zonaCod = t.getTerrenoPK().getCodZona();
        String sectorCod = t.getTerrenoPK().getCodSector();
        String manzanaCod = t.getTerrenoPK().getCodManzana();
        String solarCod = t.getTerrenoPK().getCodSolar();
        short estado = 0;

        Object[] params = {provinciaCod, cantonCod, parroquiaCod, zonaCod, sectorCod, manzanaCod, solarCod, ph, estado};

        List<Predio> predios = predioService.findByNamedQuery("Predio.findByCodes", params);
        if (predios != null && !predios.isEmpty()) {
            return predios.get(0);
        }
        return null;

    }

    public List<Predio> obtenerPrediosDeUnaManzana(String query, String provincia, String canton, String parroquia, String zona, String sector, String manzana, String solar, String bloque, String phv, String phh) {

        if (Integer.parseInt(bloque) + Integer.parseInt(phv) + Integer.parseInt(phh) == 0) {
            String cq = "Predio.findAllByManzanaTerreno";
            if (query != null) {
                cq = query;
            }
            Object[] params = {provincia, canton, parroquia, zona, sector, manzana, bloque, phv, phh};
            List<Predio> predios = predioService.findByNamedQuery(cq, params);
            if (predios != null && !predios.isEmpty()) {
                return predios;
            }
        } else {
            Object[] params = {provincia, canton, parroquia, zona, sector, manzana, solar};
            List<Predio> predios = predioService.findByNamedQuery("Predio.findAllByManzanaPH", params);
            if (predios != null && !predios.isEmpty()) {
                return predios;
            }
        }

        return new ArrayList<>();

    }

    public Terreno obtenerTerreno(String provincia, String canton, String parroquia, String zona, String sector, String manzana, String solar) {

        Object[] params = {provincia, canton, parroquia, zona, sector, manzana, solar};

        Query query = em.createNamedQuery("Terreno.findByCodes");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        List<Terreno> result = (List<Terreno>) query.getResultList();

        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public Terreno obtenerTerrenoEliminado(String provincia, String canton, String parroquia, String zona, String sector, String manzana, String solar) {

        short activo = 1;
        Object[] params = {provincia, canton, parroquia, zona, sector, manzana, solar, activo};

        Query query = em.createNamedQuery("Terreno.findByCodes");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        List<Terreno> result = (List<Terreno>) query.getResultList();

        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public Manzana obtenerManzana(String provincia, String canton, String parroquia, String zona, String sector, String manzana) {

        Object[] params = {provincia, canton, parroquia, zona, sector, manzana};

        Query query = em.createNamedQuery("Manzana.findByCodes");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<Manzana> result = (List<Manzana>) query.getResultList();

        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;

    }

    public Sector obtenerSector(String provincia, String canton, String parroquia, String zona, String sector) {

        Object[] params = {provincia, canton, parroquia, zona, sector};

        Query query = em.createNamedQuery("Sector.findByCodes");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<Sector> result = (List<Sector>) query.getResultList();

        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;

    }

    public Zona obtenerZona(short provincia, short canton, short parroquia, short zona) {

        Object[] params = {provincia, canton, parroquia, zona};

        Query query = em.createNamedQuery("Zona.findByCodes");
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<Zona> result = (List<Zona>) query.getResultList();

        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;

    }

    public Terreno obtenerTerreno(TerrenoPK pk) {

        return obtenerTerreno(pk.getCodProvincia(), pk.getCodCanton(), pk.getCodParroquia(),
                pk.getCodZona(), pk.getCodSector(), pk.getCodManzana(), pk.getCodSolar());
    }

    public Terreno obtenerTerrenoEliminado(TerrenoPK pk) {

        return obtenerTerrenoEliminado(pk.getCodProvincia(), pk.getCodCanton(), pk.getCodParroquia(),
                pk.getCodZona(), pk.getCodSector(), pk.getCodManzana(), pk.getCodSolar());
    }

    public void actualizarPredio(Predio p, List<File> files) {

        Subject user = SecurityUtils.getSubject();

        p.setFechaModificacion(new Date());
        p.setUsuarioModifica(user.getPrincipal().toString());

        em.persist(p);
        em.flush();
        /*
         for (File f : files) {
         Archivo archivo = new Archivo();

         archivo.setAutor(user.getPrincipal().toString());
         archivo.setUsuarioModifica(user.getPrincipal().toString());
         archivo.setExtension(FilenameUtils.getExtension(f.getName()));
         archivo.setRuta(f.getName());
         // fotos.add(archivo);

         em.persist(archivo);
         em.flush();

         PredioArchivo pa = new PredioArchivo(new PredioArchivoPK());
         pa.setArchivo(archivo);
         pa.setPredio(p);
         em.persist(pa);


         }
         */
        // em.flush();

    }

    public short NumeroProximoTerreno(TerrenoPK pk) {
        Query query = getEntityManager().createQuery("select max(t.terrenoPK.codSolar) from Terreno t where (t.terrenoPK.codProvincia = :prov) and (t.terrenoPK.codCanton = :cant) and (t.terrenoPK.codParroquia = :parr) and (t.terrenoPK.codZona = :zona) and (t.terrenoPK.codSector = :sector) and (t.terrenoPK.codManzana = :mz)");
        query.setParameter("prov", pk.getCodProvincia());
        query.setParameter("cant", pk.getCodCanton());
        query.setParameter("parr", pk.getCodParroquia());
        query.setParameter("zona", pk.getCodZona());
        query.setParameter("sector", pk.getCodSector());
        query.setParameter("mz", pk.getCodManzana());
        Object singleResult = query.getSingleResult();
        if (singleResult != null) {
            return Short.parseShort(singleResult.toString());
        }

        getEntityManager().flush();
        return 1;
    }

    public short NumeroProximoPH(TerrenoPK pk) {
        Query query = getEntityManager().createQuery("select max(p.propiedadHorizontal) from Predio p where (p.terreno.terrenoPK.codProvincia = :prov) and (p.terreno.terrenoPK.codCanton = :cant) and (p.terreno.terrenoPK.codParroquia = :parr) and (p.terreno.terrenoPK.codZona = :zona) and (p.terreno.terrenoPK.codSector = :sector) and (p.terreno.terrenoPK.codManzana = :mz) and (p.terreno.terrenoPK.codSolar = :solar) ");
        query.setParameter("prov", pk.getCodProvincia());
        query.setParameter("cant", pk.getCodCanton());
        query.setParameter("parr", pk.getCodParroquia());
        query.setParameter("zona", pk.getCodZona());
        query.setParameter("sector", pk.getCodSector());
        query.setParameter("mz", pk.getCodManzana());
        query.setParameter("solar", pk.getCodSolar());
        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            return (short) singleResult;
        }

        getEntityManager().flush();
        return 1;
    }

    public int NumeroProximaInspeccion() {

        //SELECT ST_X(ST_Centroid(ST_Transform(p.geom,4326))), ST_Y(ST_Centroid(ST_Transform(p.geom,4326))) from map.predios p;
        //Query query = getEntityManager().createQuery("select max(d.secuencial) from DatoInspeccion d");
        Query query = getEntityManager().createQuery("select max(d.secuencial) from DatoInspeccion d");

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }

    public int NumeroProximoBloque(Integer idPredio) {

        Query query = getEntityManager().createQuery("select max(b.numeroBloque) from Bloque b where b.predio.id = :idPredio");
        query.setParameter("idPredio", idPredio);

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }

    public int NumeroProximoPiso(Integer idBloque) {

        Query query = getEntityManager().createQuery("select max(p.numeroPiso) from Piso p where p.bloque.id = :idBloque");
        query.setParameter("idBloque", idBloque);

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }

    public int CoordenadasCentroPoligonoPredio() {

        return 1;
    }

    public Object[] coordenadas(String clave) {
        
        return null;
        
        /*
        String[] a = clave.split("-");
        String claveOK = a[0];
        claveOK += "-" + a[1];
        claveOK += "-" + a[2];
        claveOK += "-" + a[3];
        claveOK += "-" + a[4];

        Object[] resp = null;
        try {
            
            Query query = em.createNativeQuery("SELECT ST_X(ST_Centroid(ST_Transform(p.geom,4326))), ST_Y(ST_Centroid(ST_Transform(p.geom,4326))) from map.predios p where p.cod_catast = ?");
            query.setParameter(1, claveOK);

            Object result = query.getSingleResult();

            if (result == null) {

                resp = new Object[3];
                resp[1] = "0";
                resp[0] = "0";
                resp[2] = claveOK;

            } else {
                resp = new Object[3];
                Object[] ok = (Object[]) result;
                resp[0] = ok[0].toString();
                resp[1] = ok[1].toString();
                resp[2] = claveOK;
            }
        } catch (Exception e) {

        }

        return resp;
        */
    }

    public List<Predio> buscarListaPredios(String listado) {

        if (listado.equals("")) {
            return new ArrayList<Predio>();
        }

        Query query = getEntityManager().createQuery("SELECT p FROM Predio p WHERE p.claveCatastral IN (" + listado + ")");

        return query.getResultList();
    }

    public List<String> busquedaGeneral(String clave) {

        List<String> resp = null;
        try {

            Query query = em.createNativeQuery(" with consulta as (\n"
                    + "select concat(p.clave_catastral,' ',p.sector,' ',p.manzana,' ',p.solar,' ',c.identificacion,' ',p.edificio,' ',c.apellido_paterno,' ',c.apellido_materno,' ',c.nombre,' ') as texto,p.clave_catastral from cat_predio p\n"
                    + "left JOIN cat_contribuyente_predio cp  on cp.id_predio=p.id\n"
                    + "left JOIN contribuyente c on cp.id_contribuyente=c.id\n"
                    + ")\n"
                    + "SELECT clave_catastral,texto FROM consulta\n"
                    + "WHERE consulta.texto LIKE '%" + clave.toUpperCase() + "%'");

            resp = query.getResultList();

        } catch (Exception e) {

        }

        return resp;
    }

    public Object areaPredioDibujado(String clave) {

        Object result = null;
        try {

            Query query = em.createNativeQuery("SELECT p.shape_area from map.predios p where p.cod_catast = ?");
            query.setParameter(1, clave);

            result = query.getSingleResult();

        } catch (Exception e) {

        }

        return result;

    }

    @Transactional
    public void actualizarCodigoSolar(TerrenoPK pk, String nuevoCodigo) throws Exception {

        em.createNativeQuery("UPDATE cat_terreno SET cod_solar=" + nuevoCodigo + " \n"
                + "WHERE cod_parroquia=" + pk.getCodParroquia() + " AND cod_zona=" + pk.getCodZona() + " AND cod_sector=" + pk.getCodSector() + " AND cod_manzana=" + pk.getCodManzana() + " AND cod_solar=" + pk.getCodSolar() + "").executeUpdate();

        em.flush();

        em.createNativeQuery("UPDATE cat_predio \n"
                + "SET clave_catastral=cod_parroquia||'-'||cod_zona||'-'||cod_sector||'-'||cod_manzana||'-'||cod_solar||'-'||propiedad_horizontal \n"
                + "WHERE cod_parroquia=" + pk.getCodParroquia() + " AND cod_zona=" + pk.getCodZona() + " AND cod_sector=" + pk.getCodSector() + " AND cod_manzana=" + pk.getCodManzana() + " AND cod_solar=" + nuevoCodigo + "").executeUpdate();

        em.flush();
        /* SISTEMAS
        em.createNativeQuery("UPDATE map.predios SET predio=" + nuevoCodigo + " \n"
                + "WHERE zona=" + pk.getCodZona() + " AND sector=" + pk.getCodSector() + " AND manzana=" + pk.getCodManzana() + " AND predio=" + pk.getCodSolar() + "").executeUpdate();

        em.flush();

        em.createNativeQuery("UPDATE map.predios \n"
                + "SET cod_catast=" + pk.getCodParroquia() + "||'-'||zona||'-'||sector||'-'||manzana||'-'||predio \n"
                + "WHERE zona=" + pk.getCodZona() + " AND sector=" + pk.getCodSector() + " AND manzana=" + pk.getCodManzana() + " AND predio=" + nuevoCodigo + "").executeUpdate();
        */
        em.flush();
    }

    @Transactional
    public void actualizarCodigoParroquia(TerrenoPK pk, String nuevoCodigo) throws Exception {

        em.createNativeQuery("UPDATE cat_terreno SET cod_parroquia=" + nuevoCodigo + " \n"
                + "WHERE cod_parroquia=" + pk.getCodParroquia() + " AND cod_zona=" + pk.getCodZona() + " AND cod_sector=" + pk.getCodSector() + " AND cod_manzana=" + pk.getCodManzana() + " AND cod_solar=" + pk.getCodSolar() + "").executeUpdate();

        em.flush();

        em.createNativeQuery("UPDATE cat_predio \n"
                + "SET clave_catastral=cod_parroquia||'-'||cod_zona||'-'||cod_sector||'-'||cod_manzana||'-'||cod_solar||'-'||propiedad_horizontal \n"
                + "WHERE cod_parroquia=" + nuevoCodigo + " AND cod_zona=" + pk.getCodZona() + " AND cod_sector=" + pk.getCodSector() + " AND cod_manzana=" + pk.getCodManzana() + " AND cod_solar=" + pk.getCodSolar() + "").executeUpdate();

        em.flush();

//        em.createNativeQuery("UPDATE map.predios SET predio=" + nuevoCodigo + " \n"
//                + "WHERE zona=" + pk.getCodZona() + " AND sector=" + pk.getCodSector() + " AND manzana=" + pk.getCodManzana() + " AND predio=" + pk.getCodSolar() + "").executeUpdate();
//
//        em.flush();
//
//        em.createNativeQuery("UPDATE map.predios \n"
//                + "SET cod_catast=" + pk.getCodParroquia() + "||'-'||zona||'-'||sector||'-'||manzana||'-'||predio \n"
//                + "WHERE zona=" + pk.getCodZona() + " AND sector=" + pk.getCodSector() + " AND manzana=" + pk.getCodManzana() + " AND predio=" + nuevoCodigo + "").executeUpdate();
//
//        em.flush();
    }

    @Transactional
    public void actualizarCodigoPH(Predio p) throws Exception {

//        em.merge(p);
//        em.flush();
//        TerrenoPK pk = p.getTerreno().getTerrenoPK();
//        em.createNativeQuery("UPDATE cat_predio \n"
//                + "SET clave_catastral=cod_parroquia||'-'||cod_zona||'-'||cod_sector||'-'||cod_manzana||'-'||cod_solar||'-'||propiedad_horizontal \n"
//                + "WHERE cod_parroquia=" + pk.getCodParroquia() + " AND cod_zona=" + pk.getCodZona() + " AND cod_sector=" + pk.getCodSector() + " AND cod_manzana=" + pk.getCodManzana() + " AND cod_solar=" + p.getPropiedadHorizontal() + "").executeUpdate();
//        em.flush();
    }

    public int cantidadTotalPredios() {

        Query query = getEntityManager().createNativeQuery("select count(p.id) from cat_predio p");

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result;
        }

        getEntityManager().flush();
        return 0;
    }

    public int nextBloque(Integer idPredio) {

        Query query = getEntityManager().createQuery("select max(b.numeroBloque) from Bloque b where b.predio.id = :idPredio");
        query.setParameter("idPredio", idPredio);

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }

    public int nextPiso(Integer idBloque) {

        Query query = getEntityManager().createQuery("select max(p.numeroPiso) from Piso p where p.bloque.id = :idBloque");
        query.setParameter("idBloque", idBloque);

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }

    public int numeroProximaObra() {

        Query query = getEntityManager().createQuery("select max(o.id) from OtrosTipoConstruccion o");

        Object singleResult = query.getSingleResult();

        if (singleResult != null) {
            int result = (int) singleResult;
            return result + 1;
        }

        getEntityManager().flush();
        return 1;
    }

    private void borrarPredio(Predio p) {
        for (Bloque b : p.getBloques()) {
            for (BloqueImage bi : b.getImages()) {
                em.remove(em.contains(bi) ? bi : em.merge(bi));
            }
            for (UsoSuelo us : b.getUsosSuelo()) {
                em.remove(em.contains(us) ? us : em.merge(us));
            }
            em.remove(em.contains(b) ? b : em.merge(b));
        }
        for (PredioImage pi : p.getImages()) {
            em.remove(em.contains(pi) ? pi : em.merge(pi));
        }
        for (UsoSuelo us : p.getUsosSuelo()) {
            em.remove(em.contains(us) ? us : em.merge(us));
        }
        for (OtrosTipoConstruccion oc : p.getOtrosConstruccion()) {
            em.remove(em.contains(oc) ? oc : em.merge(oc));
        }
        em.remove(em.contains(p.getTerreno()) ? p.getTerreno() : em.merge(p.getTerreno()));
        em.flush();
    }

}
