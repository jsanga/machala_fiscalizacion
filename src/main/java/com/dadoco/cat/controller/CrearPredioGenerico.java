/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.controller;

import com.dadoco.admin.service.ContribuyenteService;
import com.dadoco.audit.service.AuditService;
import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Escritura;
import com.dadoco.cat.model.Manzana;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Piso;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.Terreno;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.service.CatastroService;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.common.util.UploadFile;
import com.dadoco.common.util.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Joao Sanga
 */
@Named(value = "crearPredioView")
@ViewScoped
public class CrearPredioGenerico implements Serializable {
    
    @EJB
    private ManagerService aclServices;
    @EJB
    private ContribuyenteService contribuyenteService;
    @EJB
    private VariableService variablesService;
    @EJB
    private CatastroService catastroService;
    @EJB
    private AuditService auditServices;
    
    private Predio predio;
    private Terreno terreno;
    private Escritura escritura;
    private Contribuyente propietarioSeleccionado;
    private List<Contribuyente> propietarios;
    private List<SelectItem> tiposPropietarios;
    private List<OtrosTipoConstruccion> obrasComplementarias;
    private List<Bloque> bloques;
    private List<Piso> pisos;
    private Piso pisoSeleccionado;
    private List<UsoSuelo> usosSuelo;
    private List<ValorDiscreto> valorDiscretos;
    private List<UploadFile> fotosPredio, fotosBloque;
    private Subject subject;
    
    private Bloque bloqueSeleccionado;
    private UsoSuelo usoSeleccionado;
    private List<UsoSuelo> usosSueloTerreno;
    private ValorDiscreto valorDiscreto, valorDiscretoObra;
    private OtrosTipoConstruccion obraSeleccionada;
    private short tipoConstruccion, defaultCode;
    private String materialLabel, columna, onkeypressevent;
    private Integer idObra, pisoSeleccionadoIndex, bloqueSeleccionadoIndex;
    private Boolean creandoBloque, creandoPiso;
    
    @PostConstruct
    public void init(){
        subject = SecurityUtils.getSubject();
        
        tiposPropietarios = new ArrayList<>();
        tiposPropietarios.add(new SelectItem(0, "PROPIETARIO"));
        tiposPropietarios.add(new SelectItem(1, "POSESIONARIO"));
        tiposPropietarios.add(new SelectItem(2, "ARRENDATARIO"));   
        creandoBloque = false;
        creandoPiso = false;
        defaultCode = Util.codigo_uso_terreno_default;
        valorDiscretos = getValorDiscretos();
        usosSueloTerreno = new ArrayList();
        idObra = 0;
    }
    
    public void updateEstadoTerreno() {
        if (terreno.getEstado() != 1) {
            terreno.setEstadoEdificacion((short) 0);
            terreno.setPisosTerminados((short) 0);
        } else {
            if (terreno.getEstadoEdificacion() == 0) {
                terreno.setPisosTerminados((short) 1);
            } else {
                terreno.setPisosTerminados((short) 0);
            }
        }
    }
    
    public void inicializarNuevoPiso() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (pisos.size() == bloqueSeleccionado.getNumeroNiveles()) {
            JsfUtil.addErrorMessage("La cantidad de pisos registra coincide con la cantidad de niveles del bloque.");
        } else {

            context.execute("PF('datosPisoDialog').show();");

            pisoSeleccionado = new Piso();
            pisoSeleccionado.setNumeroPiso(pisos == null || pisos.isEmpty() ? 1 : pisos.get(pisos.size() - 1).getNumeroPiso() + 1);
        }
        obraSeleccionada = new OtrosTipoConstruccion();
    }
    
    public void guardarDatos(){
        String claveCatastral = "";
        predio.setEstado(new Short("1"));
        predio.setPropietarios(propietarios);
        predio.setOtrosConstruccion(obrasComplementarias);
        predio.setUsosSuelo(usosSueloTerreno);
        predio.setTipoPredio(Boolean.TRUE);
        predio.setCodBloque("000");
        predio.setCodPhh("000");
        predio.setCodPhv("000");
        
        //if(predio.getEstadoLegalPredio() == 1)
        //    predio.setEstado((short)-1);
        
        try {
            claveCatastral = terreno.getTerrenoPK().getCodParroquia() + "-" + terreno.getTerrenoPK().getCodZona() + "-" + terreno.getTerrenoPK().getCodSector() + "-" + terreno.getTerrenoPK().getCodManzana() + "-" + terreno.getTerrenoPK().getCodSolar() + "-" + predio.getCodBloque() + "-" + predio.getCodPhv() + "-" + predio.getCodPhh();
            predio.setClaveCatastral(claveCatastral);
            catastroService.crearPredio(true, terreno, predio, new ArrayList<UploadFile>(), escritura, new ArrayList<UploadFile>());
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest htservlet = (HttpServletRequest) fc.getExternalContext().getRequest();
            InetAddress add = InetAddress.getByName(htservlet.getRemoteAddr());            

            JsfUtil.addInformationMessage("Predio ", predio.getClaveCatastral() + " regsitrado con exito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public String codigosVariablesuUsos() {

        String codigos = "";

        for (int i = 33; i < 54; i++) {
            codigos += variablesService.obtenerCodigoPorIdVariable(i, Util.ANIO_ACTUAL.shortValue());
            codigos += "-";
        }

        return codigos;
    }
    
    public void crearNuevaObra() {

        RequestContext context = RequestContext.getCurrentInstance();

        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, obraSeleccionada.getTipoConstruccion(), Util.ANIO_ACTUAL.shortValue());
        obraSeleccionada.setVariable(valorDiscretoObra.getVariable().getId());
        obraSeleccionada.setUnidad(obraSeleccionada.getArea().shortValue());
        boolean existe = false;
        String accion = "editado";
        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                obrasComplementarias.remove(i);
                obrasComplementarias.add(i, obraSeleccionada);
                existe = true;
                break;
            }
        }
        if (!existe) {
            obraSeleccionada.setPredio(predio);
            obrasComplementarias.add(obraSeleccionada);
            accion = "creado";
        }
        initNuevaObra();
        JsfUtil.addInformationMessage(null, "Otro tipo de construcción " + accion + " con exito.");

        if (existe) {

            context.execute("PF('obrasDialog').hide();");

        }
    }
    
    public void crearNuevoUso(boolean bloque) {

        if (valorDiscreto != null) {
            usoSeleccionado.setValorDiscreto(valorDiscreto);
            usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        } else {
            valorDiscreto = variablesService.obtenerValorbyVariableValor(33, Util.codigo_uso_terreno_default, Util.ANIO_ACTUAL.shortValue());
            usoSeleccionado.setValorDiscreto(valorDiscreto);
            usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        }

        boolean existe = false;

        if (bloque) {
            if(usosSuelo == null)
                usosSuelo = new ArrayList();
                
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                usoSeleccionado.setBloque(bloqueSeleccionado);
                usosSuelo.add(usoSeleccionado);
                bloqueSeleccionado.setUsosSuelo(usosSuelo);
            }

            if (existe) {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo se encuentra registrado al bloque.");
            } else {
                JsfUtil.addInformationMessage("Uso", "Uso del suelo adicionado con exito");
            }
        }
        initNuevoUso();
    }
    
    public void inicializarActualizacionBloque() {        
        creandoBloque = false;
        //fotosBloque = bloqueSeleccionado.getFotosBloque();
        this.creandoBloque = true;
        pisos = bloqueSeleccionado.getPisos();
        obraSeleccionada = new OtrosTipoConstruccion();
    }
    
    public String obtenerValor(String tabla, String columna, short valueColumna){
        return variablesService.obtenerValor(tabla, columna, valueColumna, Util.ANIO_ACTUAL.shortValue());
    }
    
    public void crearBloque() {

        if (pisos.isEmpty()) {
            creandoBloque = false;
            creandoPiso = false;
            JsfUtil.addErrorMessage("Debe registrarle pisos al bloque.");
        } else if (bloqueSeleccionado != null) {

            if (bloqueSeleccionado.getNumeroNiveles() != pisos.size()) {
                JsfUtil.addErrorMessage("Faltan " + (bloqueSeleccionado.getNumeroNiveles() - pisos.size()) + " pisos por registrar al bloque " + bloqueSeleccionado.getNumeroBloque() + " para que coincida con el nro de niveles.");
            } else {
                for (int i = 0; i < pisos.size(); i++) {
                    pisos.get(i).setNumeroPiso(i + 1);
                }

                bloqueSeleccionado.setPisos(pisos);
                bloqueSeleccionado.setPredio(predio);
                //bloqueSeleccionado.setFotosBloque(fotosBloque);
                if(usosSuelo == null)
                    usosSuelo = new ArrayList();
                for (int i = 0; i < usosSuelo.size(); i++) {
                    usosSuelo.get(i).setBloque(bloqueSeleccionado);
                }
                bloqueSeleccionado.setUsosSuelo(usosSuelo);
                boolean estaBloque = false;
                String creado = " creado ";
                if(bloques == null)
                    bloques = new ArrayList();
                for (int i = 0; i < bloques.size(); i++) {
                    if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {
                        bloques.remove(i);
                        bloques.add(i, bloqueSeleccionado);
                        estaBloque = true;
                        creado = " editado ";
                        bloqueSeleccionadoIndex = i;
                        break;
                    }
                }

                if (!estaBloque) {
                    bloques.add(bloqueSeleccionado);
                    bloqueSeleccionadoIndex = bloques.size() - 1;
                }

                predio.setBloques(bloques);

                JsfUtil.addSuccessMessage("Bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                creandoBloque = false;
                creandoPiso = false;
            }

        }
        obraSeleccionada = new OtrosTipoConstruccion();
        usosSuelo = new ArrayList<UsoSuelo>();
        usoSeleccionado = new UsoSuelo();
        //fotosBloque = new ArrayList<UploadFile>();
    }
    
    public void cancelarCrearUso() {
        if (!usosSuelo.isEmpty()) {
            usoSeleccionado = usosSuelo.get(0);
        }

    }
    
    public void onRowSelectUso(SelectEvent event) {
        usoSeleccionado = (UsoSuelo) event.getObject();
    }
    
    public void guardarPiso() {
        try {
            if (pisoSeleccionado != null) {
                if (pisoSeleccionado.getArea() != 0) {
                    pisoSeleccionado.setBloque(bloqueSeleccionado);
                    boolean estaPiso = false;
                    String creado = " creado ";
                    for (int i = 0; i < pisos.size(); i++) {
                        if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {
                            pisos.remove(i);
                            pisos.add(i, pisoSeleccionado);
                            estaPiso = true;
                            creado = " editado ";
                            pisoSeleccionadoIndex = i;
                            break;
                        }
                    }
                    if (!estaPiso) {
                        pisos.add(pisoSeleccionado);
                        pisoSeleccionadoIndex = pisos.size() - 1;
                    }

                    creandoPiso = false;
                    bloqueSeleccionado.setPisos(pisos);
                    double area_total = 0;
                    for (Piso p : pisos) {
                        area_total += p.getArea();
                    }
                    bloqueSeleccionado.setAreaTotal(area_total);
                    JsfUtil.addSuccessMessage("Piso " + pisoSeleccionado.getNumeroPiso() + " del bloque " + bloqueSeleccionado.getNumeroBloque() + creado + "con èxito.");
                } else {

                    JsfUtil.addErrorMessage("Área del piso no puede ser 0.");
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }
        obraSeleccionada = new OtrosTipoConstruccion();
    }
    
    public void eliminarUso(boolean bloque) {

        if (bloque) {
            for (int i = 0; i < usosSuelo.size(); i++) {
                if (Objects.equals(usosSuelo.get(i).getCod(), usoSeleccionado.getCod())) {
                    usosSuelo.remove(i);
                    break;
                }
            }
            if (!usosSuelo.isEmpty()) {
                usoSeleccionado = usosSuelo.get(0);
            }
        } else {
            for (int i = 0; i < usosSueloTerreno.size(); i++) {
                if (Objects.equals(usosSueloTerreno.get(i).getCod(), usoSeleccionado.getCod())) {
                    usosSueloTerreno.remove(i);
                    break;
                }
            }
            if (!usosSueloTerreno.isEmpty()) {
                usoSeleccionado = usosSueloTerreno.get(0);
            }
        }

    }
    
    public void eliminarPiso() {
        if (pisoSeleccionado != null) {
            for (int i = 0; i < pisos.size(); i++) {
                if (pisos.get(i).getNumeroPiso() == pisoSeleccionado.getNumeroPiso()) {
                    pisos.remove(i);
                    break;
                }
            }

            JsfUtil.addSuccessMessage("Piso " + pisoSeleccionado.getNumeroPiso() + " del bloque " + bloqueSeleccionado.getNumeroBloque() + " eliminado con èxito.");
            if (!pisos.isEmpty()) {
                pisoSeleccionado = pisos.get(0);
                pisoSeleccionadoIndex = 0;
            }

            double area_total = 0;
            for (Piso p : pisos) {
                area_total += p.getArea();
            }
            bloqueSeleccionado.setAreaTotal(area_total);
        }
        creandoPiso = false;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
    
    public void initNuevoUso() {
        usoSeleccionado = new UsoSuelo();

        valorDiscreto = variablesService.obtenerValorbyVariableValor(33, Util.codigo_uso_terreno_default, Util.ANIO_ACTUAL.shortValue());

        usoSeleccionado.setValorDiscreto(valorDiscreto);
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());

    }
    
    public List<ValorDiscreto> getValorDiscretos() {
        valorDiscretos = new ArrayList<>();

        for (int i = 33; i < 54; i++) {
            List<ValorDiscreto> valores = variablesService.obtenerValoresPorCodVariable(i, Util.ANIO_ACTUAL.shortValue());
            if (valores != null) {
                valorDiscretos.addAll(valores);
            }
        }

        return valorDiscretos;
    }

    public void setValorDiscretos(List<ValorDiscreto> valorDiscretos) {
        this.valorDiscretos = valorDiscretos;
    }
    
    public void inicializarNuevoBloque() {
        bloqueSeleccionado = new Bloque();
        bloqueSeleccionado.setPisos(new ArrayList<Piso>());

        bloqueSeleccionado.setNumeroBloque(bloques != null ? bloques.size()+1 : 1);
        pisoSeleccionado = new Piso();
        pisos = new ArrayList<>();
        pisoSeleccionadoIndex = 0;
        creandoBloque = true;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
    
    public void eliminarBloque() {
        if(bloqueSeleccionado.getNumeroBloque() != bloques.size()){
            JsfUti.messageInfo(null, "Info", "Solo se puede eliminar el último bloque ingresado.");
            return;
        }
        if (bloqueSeleccionado != null) {
            for (int i = 0; i < bloques.size(); i++) {
                if (bloques.get(i).getNumeroBloque() == bloqueSeleccionado.getNumeroBloque()) {
                    bloques.remove(i);
                    break;
                }
            }

            if (!bloques.isEmpty()) {
                bloqueSeleccionado = bloques.get(0);
                bloqueSeleccionadoIndex = 0;
            }
        }
        creandoBloque = false;
        obraSeleccionada = new OtrosTipoConstruccion();
    }
    
    public void initNuevaObra() {
        obraSeleccionada = new OtrosTipoConstruccion();
        obraSeleccionada.setNumero(idObra++);
        tipoConstruccion = (short) 1;
        obraSeleccionada.setTipoConstruccion((short) 1);
        materialLabel = "Tipo material:";
        columna = "muros";
        onkeypressevent = "validateFloatValueAndFocusNext(event, 'crear-obra-btn')";
        valorDiscretoObra = variablesService.obtenerValores("coef_avaluos", columna, (short) 1, Util.ANIO_ACTUAL.shortValue());
    }
    
    public void changeUsoSelect() {
        usoSeleccionado.setCod(valorDiscreto.getValorDiscretoPK().getValor());
        usoSeleccionado.setValorDiscreto(valorDiscreto);
    }
    
    public void cambiarBloque() {
        bloqueSeleccionado = bloques.get(bloqueSeleccionadoIndex);
        pisos = bloqueSeleccionado.getPisos();
        pisoSeleccionadoIndex = -1;
        if (!pisos.isEmpty()) {
            pisoSeleccionado = pisos.get(0);
            pisoSeleccionadoIndex = 0;
        }
        obraSeleccionada = new OtrosTipoConstruccion();

        if (!bloqueSeleccionado.getUsosSuelo().isEmpty()) {
            usosSuelo = bloqueSeleccionado.getUsosSuelo();
            usoSeleccionado = usosSuelo.get(0);
        } else {
            usosSuelo = new ArrayList<>();
            usoSeleccionado = new UsoSuelo();
        }
    }
    
    public void eliminarObra() {
        for (int i = 0; i < obrasComplementarias.size(); i++) {
            if (obrasComplementarias.get(i).getNumero() == obraSeleccionada.getNumero()) {
                obrasComplementarias.remove(i);
                break;
            }
        }

        if (!obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        } else {
            initNuevaObra();
        }
    }
    
    @Transactional
    public void onCellEditPropietario(CellEditEvent event) {
        
        try{
            String s[] = event.getColumn().getColumnKey().split(":");
            Contribuyente c = null;

            if(s[s.length-1].equals( "nombres")){
                c = propietarios.get(event.getRowIndex());
                c.setNombreCompleto((String)event.getNewValue());
            }
            if(s[s.length-1].equals("cedula")){
                c = propietarios.get(event.getRowIndex());
                c.setIdentificacion((String)event.getNewValue());
            }
            if(s[s.length-1].equals( "celular")){
                c = propietarios.get(event.getRowIndex());
                c.setCelular((String)event.getNewValue());
            }
            if(s[s.length-1].equals("telefono")){
                c = propietarios.get(event.getRowIndex());
                c.setTelefono((String)event.getNewValue());
            }
            if(c != null){
                aclServices.getEm().merge(c);
                aclServices.getEm().flush();
                JsfUti.messageInfo(null, "Info", "Contribuyente editado correctamente.");
            }
            auditServices.guardarUsuarioTransaccion(subject.getPrincipal().toString(), "ACTUALIZACIÓN DE PREDIO URBANO - EDICIÓN DE PROPIETARIO - PREDIO: "+this.predio.getClaveCatastral());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void crearContribuyente() {

        try {

            propietarioSeleccionado.setFechaIngreso(new Date());
            propietarioSeleccionado.setNombre(propietarioSeleccionado.getNombre().toUpperCase());
            propietarioSeleccionado.setApellidoPaterno(propietarioSeleccionado.getApellidoPaterno().toUpperCase());
            propietarioSeleccionado.setApellidoMaterno(propietarioSeleccionado.getApellidoMaterno().toUpperCase());
            String nombreCompleto = propietarioSeleccionado.getApellidoPaterno() + " " + propietarioSeleccionado.getApellidoMaterno() + " " + propietarioSeleccionado.getNombre();
            propietarioSeleccionado.setNombreCompleto(nombreCompleto);
            propietarioSeleccionado = contribuyenteService.guardar(propietarioSeleccionado);
            propietarios.add(propietarioSeleccionado);

        } catch (EJBException e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        propietarioSeleccionado = (Contribuyente) event.getObject();
    }
    
    public void nuevoContribuyente(){
        propietarioSeleccionado = new Contribuyente();
    }
    
    public void eliminarPropietario() {

        if (propietarios.contains(propietarioSeleccionado)) {
            propietarios.remove(propietarioSeleccionado);
        }
        propietarioSeleccionado = null;
    }

    public void cancelarCrearObra() {
        if (obrasComplementarias != null && !obrasComplementarias.isEmpty()) {
            obraSeleccionada = obrasComplementarias.get(0);
        }
    }
    
    @Asynchronous
    public void handlePhotoUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_FP_";
        if(fotosPredio == null)
            fotosPredio = new ArrayList();
        Util.copyFile(uploadedFile, fileName, fotosPredio, "fotos", (short) 1);
    }
    
    @Asynchronous
    public void handlePhotoBloqueUpload(FileUploadEvent event) throws FileNotFoundException, IOException {

        UploadedFile uploadedFile = (UploadedFile) event.getFile();
        String fileName = predio.getClaveCatastral() + "_FB" + bloqueSeleccionado.getNumeroBloque() + "_";
        if(fotosBloque == null)
            fotosBloque = new ArrayList();
        Util.copyFile(uploadedFile, fileName, fotosBloque, "fotos", (short) 1);
        bloqueSeleccionado.setFotosBloque(fotosBloque);
    }
    
    public void eliminarFotoPredio(int index) {
        Util.deleteFile(index, fotosPredio);
    }
    
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
        if(this.predio != null){
            this.terreno = this.predio.getTerreno();
            this.propietarios = predio.getPropietarios();
            this.escritura = new Escritura();
            this.escritura.setPredio(this.predio);
            this.predio.setEscrituras(new ArrayList());
            this.predio.getEscrituras().add(this.escritura);
            this.propietarios = new ArrayList();
        }else{
            this.predio = new Predio();
            this.terreno = new Terreno();
            this.predio.setTerreno(this.terreno);
            this.propietarios = new ArrayList();
            this.escritura = new Escritura();
            this.predio.setEscrituras(new ArrayList());
            this.predio.getEscrituras().add(this.escritura);
        }
    }

    public Terreno getTerreno() {
        return terreno;
    }

    public void setTerreno(Terreno terreno) {
        this.terreno = terreno;
    }

    public Contribuyente getPropietarioSeleccionado() {
        return propietarioSeleccionado;
    }

    public void setPropietarioSeleccionado(Contribuyente propietarioSeleccionado) {
        this.propietarioSeleccionado = propietarioSeleccionado;
    }

    public List<Contribuyente> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Contribuyente> propietarios) {
        this.propietarios = propietarios;
    }

    public List<SelectItem> getTiposPropietarios() {
        return tiposPropietarios;
    }

    public void setTiposPropietarios(List<SelectItem> tiposPropietarios) {
        this.tiposPropietarios = tiposPropietarios;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }

    public UsoSuelo getUsoSeleccionado() {
        return usoSeleccionado;
    }

    public void setUsoSeleccionado(UsoSuelo usoSeleccionado) {
        this.usoSeleccionado = usoSeleccionado;
    }

    public ValorDiscreto getValorDiscreto() {
        return valorDiscreto;
    }

    public void setValorDiscreto(ValorDiscreto valorDiscreto) {
        this.valorDiscreto = valorDiscreto;
    }

    public ValorDiscreto getValorDiscretoObra() {
        return valorDiscretoObra;
    }

    public void setValorDiscretoObra(ValorDiscreto valorDiscretoObra) {
        this.valorDiscretoObra = valorDiscretoObra;
    }

    public OtrosTipoConstruccion getObraSeleccionada() {
        return obraSeleccionada;
    }

    public void setObraSeleccionada(OtrosTipoConstruccion obraSeleccionada) {
        this.obraSeleccionada = obraSeleccionada;
    }

    public short getTipoConstruccion() {
        return tipoConstruccion;
    }

    public void setTipoConstruccion(short tipoConstruccion) {
        this.tipoConstruccion = tipoConstruccion;
    }

    public String getMaterialLabel() {
        return materialLabel;
    }

    public void setMaterialLabel(String materialLabel) {
        this.materialLabel = materialLabel;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getOnkeypressevent() {
        return onkeypressevent;
    }

    public void setOnkeypressevent(String onkeypressevent) {
        this.onkeypressevent = onkeypressevent;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public List<OtrosTipoConstruccion> getObrasComplementarias() {
        return obrasComplementarias;
    }

    public void setObrasComplementarias(List<OtrosTipoConstruccion> obrasComplementarias) {
        this.obrasComplementarias = obrasComplementarias;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public List<Piso> getPisos() {
        return pisos;
    }

    public void setPisos(List<Piso> pisos) {
        this.pisos = pisos;
    }

    public Piso getPisoSeleccionado() {
        return pisoSeleccionado;
    }

    public void setPisoSeleccionado(Piso pisoSeleccionado) {
        this.pisoSeleccionado = pisoSeleccionado;
    }

    public List<UsoSuelo> getUsosSuelo() {
        return usosSuelo;
    }

    public void setUsosSuelo(List<UsoSuelo> usosSuelo) {
        this.usosSuelo = usosSuelo;
    }

    public Bloque getBloqueSeleccionado() {
        return bloqueSeleccionado;
    }

    public void setBloqueSeleccionado(Bloque bloqueSeleccionado) {
        this.bloqueSeleccionado = bloqueSeleccionado;
    }

    public Integer getPisoSeleccionadoIndex() {
        return pisoSeleccionadoIndex;
    }

    public void setPisoSeleccionadoIndex(Integer pisoSeleccionadoIndex) {
        this.pisoSeleccionadoIndex = pisoSeleccionadoIndex;
    }

    public Integer getBloqueSeleccionadoIndex() {
        return bloqueSeleccionadoIndex;
    }

    public void setBloqueSeleccionadoIndex(Integer bloqueSeleccionadoIndex) {
        this.bloqueSeleccionadoIndex = bloqueSeleccionadoIndex;
    }

    public Boolean getCreandoBloque() {
        return creandoBloque;
    }

    public void setCreandoBloque(Boolean creandoBloque) {
        this.creandoBloque = creandoBloque;
    }

    public Boolean getCreandoPiso() {
        return creandoPiso;
    }

    public void setCreandoPiso(Boolean creandoPiso) {
        this.creandoPiso = creandoPiso;
    }

    public short getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(short defaultCode) {
        this.defaultCode = defaultCode;
    }

    public List<UploadFile> getFotosPredio() {
        return fotosPredio;
    }

    public void setFotosPredio(List<UploadFile> fotosPredio) {
        this.fotosPredio = fotosPredio;
    }

    public List<UsoSuelo> getUsosSueloTerreno() {
        return usosSueloTerreno;
    }

    public void setUsosSueloTerreno(List<UsoSuelo> usosSueloTerreno) {
        this.usosSueloTerreno = usosSueloTerreno;
    }

    public List<UploadFile> getFotosBloque() {
        return fotosBloque;
    }

    public void setFotosBloque(List<UploadFile> fotosBloque) {
        this.fotosBloque = fotosBloque;
    }
    
}
