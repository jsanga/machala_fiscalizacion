/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.controller;

import com.dadoco.auth.service.SecurityService;
import com.dadoco.auth.model.Departamento;
import com.dadoco.auth.model.Rol;
import com.dadoco.auth.model.Usuario;
import com.dadoco.auth.service.DepartamentoService;
import com.dadoco.auth.service.RolService;
import com.dadoco.auth.service.UsuarioService;
import com.dadoco.common.jsf.ComponentVisitor;
import com.dadoco.common.jsf.JsfUtil;
import com.dadoco.common.service.ManagerService;
import com.dadoco.common.util.JsfUti;
import com.dadoco.config.ConfigEmail;
import com.dadoco.flow.model.HtMensajesUsuario;
import com.dadoco.flow.service.HtTramiteService;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.subject.Subject;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dairon
 */
@Named(value = "userAdminView")
@ViewScoped
public class UsuarioAdminViewController implements Serializable {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UsuarioAdminViewController.class);

    @EJB
    private SecurityService secService;

    @EJB
    private RolService rolService;
    
    @EJB
    private HtTramiteService msgServices;

    @EJB
    private UsuarioService usuarioService;
    
    @EJB
    private ManagerService aclServices;

    @EJB
    private DepartamentoService departamentoService;

    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados;
    private Usuario usuarioSeleccionado;

    private boolean creandoUsuario;
    private boolean editandoUsuario;

    private List<Rol> sourceRoles;
    private List<Rol> targetRoles;
    private List<Rol> eliminarRoles;

    private DualListModel<Rol> dualListModelRoles;
    private List<Rol> roles;

    private String currentPassword;
    private String currentUser;
    private String currentIdentificacion;
    private String pass="", passAnterior="";

    @PostConstruct
    public void init() {
        try{
            usuarios = usuarioService.findByNamedQuery("Usuario.findAll", true);
            roles = secService.listaRoles();
            targetRoles = new ArrayList<Rol>();
            eliminarRoles = new ArrayList<Rol>();
            
            Subject subject = SecurityUtils.getSubject();     
            if(subject == null){
                JsfUti.redirectFaces("/admin/usuario-edit");
                return;
            }
                
            if(subject != null && subject.isAuthenticated()){
                String s = subject.getPrincipal().toString();
                usuarioSeleccionado = (Usuario)aclServices.find("SELECT r FROM Usuario r WHERE r.username = :username", new String[]{"username"}, new Object[]{s});
            }
            
                        
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentIdentificacion() {
        return currentIdentificacion;
    }

    public void setCurrentIdentificacion(String currentIdentificacion) {
        this.currentIdentificacion = currentIdentificacion;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public List<Rol> getEliminarRoles() {
        return eliminarRoles;
    }

    public void setEliminarRoles(List<Rol> eliminarRoles) {
        this.eliminarRoles = eliminarRoles;
    }

    public RolService getRolService() {
        return rolService;
    }

    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }

    public List<Rol> getSourceRoles() {
        sourceRoles = rolService.findAll();
        return sourceRoles;
    }

    public void setSourceRoles(List<Rol> sourceRoles) {
        this.sourceRoles = sourceRoles;
    }

    public List<Rol> getTargetRoles() {
        return targetRoles;
    }

    public void setTargetRoles(List<Rol> targetRoles) {
        this.targetRoles = targetRoles;
    }

    public DualListModel<Rol> getDualListModelRoles() {

        dualListModelRoles = new DualListModel<Rol>(sourceRoles, targetRoles);

        return dualListModelRoles;
    }

    public void setDualListModelRoles(DualListModel<Rol> dualListModelRoles) {
        this.dualListModelRoles = dualListModelRoles;
    }

    public SecurityService getSecService() {
        return secService;
    }

    public void setSecService(SecurityService secService) {
        this.secService = secService;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<Usuario> getUsuarios() {
        usuarios = usuarioService.findByNamedQuery("Usuario.findAll", true);
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Usuario> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public boolean isCreandoUsuario() {
        return creandoUsuario;
    }

    public void setCreandoUsuario(boolean creandoUsuario) {
        this.creandoUsuario = creandoUsuario;
    }

    public boolean isEditandoUsuario() {
        return editandoUsuario;
    }

    public void setEditandoUsuario(boolean editandoUsuario) {
        this.editandoUsuario = editandoUsuario;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public DepartamentoService getDepartamentoService() {
        return departamentoService;
    }

    public void setDepartamentoService(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

//</editor-fold>
    public List<String> getSexos() {
        String[] valores = {"M", "F"};
        return Arrays.asList(valores);
    }

    public void inicializarNuevoUsuario() {
        SecureRandom random = new SecureRandom();
        String codigo = new BigInteger(130, random).toString(32);
        
        usuarioSeleccionado = new Usuario();
        creandoUsuario = true;
        editandoUsuario = false;
        
        usuarioSeleccionado.setPassword(codigo.substring(0, 6).toUpperCase());
        
        sourceRoles = rolService.findAll();
        targetRoles = new ArrayList<>();

        JsfUti.update("user-form");
        
        dualListModelRoles = new DualListModel<>(sourceRoles, targetRoles);
    }

    public void cancelarCreacionUsuario() {
        if (creandoUsuario) {
            usuarioSeleccionado = null;
        }
        editandoUsuario = false;
        creandoUsuario = false;
    }

    public void inicializarEdicion(Integer id) {

        usuarioSeleccionado = usuarioService.find(id);
        currentPassword = usuarioSeleccionado.getPassword();
        currentIdentificacion = usuarioSeleccionado.getIdentificacion();
        currentUser = usuarioSeleccionado.getUsername();
        sourceRoles = new ArrayList<Rol>();
        targetRoles = new ArrayList<Rol>();

        List<Rol> listSource = rolService.findAll();
        List<Rol> listTarget = usuarioSeleccionado.getRoles();

        for (Rol r : listSource) {
            if (listTarget.contains(r)) {
                targetRoles.add(r);
            } else {
                sourceRoles.add(r);
            }
        }
        eliminarRoles = targetRoles;

        dualListModelRoles = new DualListModel<Rol>(sourceRoles, targetRoles);
        editandoUsuario = true;
        creandoUsuario = false;

    }

    public void crearUsuario() {
        try {
            
            Object[] params = new Object[2];
            params[0] = usuarioSeleccionado.getUsername();
            params[1] = true;
            List<Usuario> users = usuarioService.findByNamedQuery("Usuario.findByUsername", params);

            if (users.isEmpty()) {

                params[0] = usuarioSeleccionado.getIdentificacion();
                users = usuarioService.findByNamedQuery("Usuario.findByIdentificacion", params);
                if (users.isEmpty()) {
                    if (usuarioSeleccionado.getTipoIdentificacion().equals("C")) {
                        if (!validarCedula(usuarioSeleccionado.getIdentificacion())) {
                            JsfUtil.addWarningMessage("Advertencia", "Cédula " + usuarioSeleccionado.getIdentificacion() + " no válida, formato incorrecto.");
                            return;
                        }
                    }
                    if (usuarioSeleccionado.getEmail() != null && !"".equals(usuarioSeleccionado.getEmail())) {
                        if (!validarEmail(usuarioSeleccionado.getEmail())) {
                            JsfUtil.addWarningMessage("Advertencia", "Email " + usuarioSeleccionado.getEmail() + " no válido, formato incorrecto.");
                            return;
                        }
                    }

                    List<Rol> rolesSelected = dualListModelRoles.getTarget();
                    usuarioSeleccionado.setRoles(rolesSelected);

                    DefaultPasswordService passwordService = new DefaultPasswordService();
                    
                    ConfigEmail configs = aclServices.getEm().find(ConfigEmail.class, 1L); 
                    HtMensajesUsuario msj = this.aclServices.getEm().find(HtMensajesUsuario.class, 5L);
                    msgServices.sendBasicEmail(usuarioSeleccionado.getEmail(), "Contrase&ntilde;a temporal", 
                            msj.getHeaderMail()+"Su clave temporal es: "+usuarioSeleccionado.getPassword()+". C&aacute;mbiela lo m&aacute;s pronto posible.<br/><br/>Su usuario es: "+usuarioSeleccionado.getUsername()+"<br/>"+msj.getFooterMail(), configs, null);
                    usuarioSeleccionado.setContrasenia(usuarioSeleccionado.getPassword());
                    usuarioSeleccionado.setPassword(passwordService.encryptPassword(usuarioSeleccionado.getPassword()));
                    usuarioSeleccionado.setSalt(passwordService.hashPassword(usuarioSeleccionado.getPassword()).getSalt().toString());
                    usuarioService.create(usuarioSeleccionado);
                    JsfUtil.addInformationMessage("Usuario creado", "Usuario " + usuarioSeleccionado.getUsername() + " creado con éxito.");
                    inicializarNuevoUsuario();
                } else {
                    JsfUtil.addWarningMessage("Advertencia", "Existe usuario con Nro identificación " + usuarioSeleccionado.getIdentificacion() + ".");
                }

            } else {
                JsfUtil.addWarningMessage("Advertencia", "Usuario " + usuarioSeleccionado.getUsername() + " ya se ecuentra registrado.");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }
    
    @Transactional
    public void editarUsuarioNew(){
        try{
            DefaultPasswordService passwordService = new DefaultPasswordService();
            
            if(passAnterior.equals(usuarioSeleccionado.getContrasenia())){
                usuarioSeleccionado.setContrasenia(pass);
                usuarioSeleccionado.setPassword(passwordService.encryptPassword(pass));
                usuarioSeleccionado.setSalt(passwordService.hashPassword(pass).getSalt().toString());
                aclServices.getEm().merge(usuarioSeleccionado);
                JsfUtil.addInformationMessage("Usuario editado", "Usuario " + usuarioSeleccionado.getUsername() + " editado con éxito.");
            }else{
                JsfUtil.addInformationMessage("Mensaje", "La contraseña anterior ingresada es inválida.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editUsuario() {
        try {
            boolean ok = true;
            List<Rol> listTarget = dualListModelRoles.getTarget();
            usuarioSeleccionado.setRoles(listTarget);

            Object[] params = new Object[2];
            params[0] = usuarioSeleccionado.getUsername();
            params[1] = true;
            List<Usuario> users = new ArrayList<>();

            if (!usuarioSeleccionado.getUsername().equals(currentUser)) {
                users = usuarioService.findByNamedQuery("Usuario.findByUsername", params);
                if (!users.isEmpty()) {
                    JsfUtil.addWarningMessage("Advertencia", "Usuario " + usuarioSeleccionado.getUsername() + " ya se ecuentra registrado.");
                    ok = false;
                }
            }
            if (!usuarioSeleccionado.getIdentificacion().equals(currentIdentificacion)) {
                params[0] = usuarioSeleccionado.getIdentificacion();
                users = usuarioService.findByNamedQuery("Usuario.findByIdentificacion", params);
                if (!users.isEmpty()) {
                    JsfUtil.addWarningMessage("Advertencia", "Existe usuario con Nro identificación " + usuarioSeleccionado.getIdentificacion() + ".");
                    ok = false;
                } else if (usuarioSeleccionado.getTipoIdentificacion().equals("C")) {
                    if (!validarCedula(usuarioSeleccionado.getIdentificacion())) {
                        JsfUtil.addWarningMessage("Advertencia", "Cédula " + usuarioSeleccionado.getIdentificacion() + " no válida, formato incorrecto.");
                        ok = false;
                    }
                }
            }
            usuarioSeleccionado.setContrasenia(usuarioSeleccionado.getPassword());
            DefaultPasswordService passwordService = new DefaultPasswordService();
            if (usuarioSeleccionado.getPassword() != null && !"".equals(usuarioSeleccionado.getPassword())) {
                usuarioSeleccionado.setPassword(passwordService.encryptPassword(usuarioSeleccionado.getPassword()));
                usuarioSeleccionado.setSalt(passwordService.hashPassword(usuarioSeleccionado.getPassword()).getSalt().toString());
            } else {
                usuarioSeleccionado.setPassword(currentPassword);
            }

            if (ok) {
                secService.editUsuario(usuarioSeleccionado, eliminarRoles);
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('usuarioDialog').hide();");
                context.update("user-list-form,messages");

                JsfUtil.addInformationMessage("Usuario editado", "Usuario " + usuarioSeleccionado.getUsername() + " editado con éxito.");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public void deleteUsuario(Integer id) {
        try {
            Usuario u = usuarioService.find(id);

            if (u != null) {
                log.error("Usuario: " + u.getUsername());
                u.setHabilitado(false);
                usuarioService.edit(u);
                usuarioSeleccionado = new Usuario();
                JsfUtil.addInformationMessage("Usuario eliminado", "Usuario " + u.getUsername() + " eliminado con éxito.");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getMessage());
        }
    }

    public List<Departamento> getDepartamentoList() {
        return secService.listaDepartamentos();
    }

    public void validarFormulario(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();
        UIForm form = (UIForm) components.findComponent("user-form");

        //Map<String, Object> validValues = new HashMap<String, Object>();
        Set<VisitHint> hints = EnumSet.of(VisitHint.SKIP_UNRENDERED);
        ComponentVisitor visitor = new ComponentVisitor();

        form.visitTree(VisitContext.createVisitContext(fc, null, hints), visitor);

        int errores = 0;

        UIInput uiTipoIdentificacion = (UIInput) components.findComponent("usuario_tipo_identificacion");
        String tipoIdentificacion = uiTipoIdentificacion.getLocalValue() == null ? ""
                : uiTipoIdentificacion.getLocalValue().toString();

        UIInput uiIdentificacion = (UIInput) components.findComponent("usuario_identificacion");
        String identificacion = uiIdentificacion.getLocalValue() == null ? ""
                : uiIdentificacion.getLocalValue().toString();

        if ("C".equals(tipoIdentificacion) && Usuario.validarCedula(identificacion) == false) {
            errores++;
            uiIdentificacion.setValid(false);
        }

        errores += visitor.getInvalidFields();

        if (errores != 0) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setDetail("Existen errores en el formulario");
            fc.addMessage(null, msg);
        }

        fc.renderResponse();

        /*FacesContext fc = FacesContext.getCurrentInstance();
         RequestContext context = RequestContext.getCurrentInstance();

         UIComponent components = event.getComponent();
         // get password
         UIInput uiInputUserName = (UIInput) components.findComponent("usuario_username");
         String userName = uiInputUserName.getLocalValue() == null ? ""
         : uiInputUserName.getLocalValue().toString();
         String userNameId = uiInputUserName.getClientId();

         if (true) {

         FacesMessage msg = new FacesMessage("Password must match confirm password");
         msg.setSeverity(FacesMessage.SEVERITY_ERROR);
         fc.addMessage(userNameId, msg);
         fc.renderResponse();

         //context.execute("PF('usuarioDialog').hide();");
         }*/
    }

    public String cancelarModificacion() {

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public void CancelarAction() {

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('usuarioDialog').hide();");
    }

    public static boolean validarCedula(String cedula) {

        Pattern pattern = Pattern.compile("^[0-9]{10}+$");
        Matcher matcher = pattern.matcher(cedula);
        if (!matcher.matches()) {
            return false;
        }
        int suma = 0;
        int a[] = new int[cedula.length() / 2];
        int b[] = new int[(cedula.length() / 2)];
        int c = 0;
        int d = 1;
        for (int i = 0; i < cedula.length() / 2; i++) {
            a[i] = Integer.parseInt(String.valueOf(cedula.charAt(c)));
            c = c + 2;
            if (i < (cedula.length() / 2) - 1) {
                b[i] = Integer.parseInt(String.valueOf(cedula.charAt(d)));
                d = d + 2;
            }
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = a[i] * 2;
            if (a[i] > 9) {
                a[i] = a[i] - 9;
            }
            suma = suma + a[i] + b[i];
        }
        int aux = suma / 10;
        int dec = (aux + 1) * 10;
        if ((dec - suma) == Integer.parseInt(String.valueOf(cedula.charAt(cedula.length() - 1)))) {
            return true;
        } else {
            return suma % 10 == 0 && cedula.charAt(cedula.length() - 1) == '0';
        }

    }

    public boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {

                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
        }

        return cedulaCorrecta;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPassAnterior() {
        return passAnterior;
    }

    public void setPassAnterior(String passAnterior) {
        this.passAnterior = passAnterior;
    }
    
}
