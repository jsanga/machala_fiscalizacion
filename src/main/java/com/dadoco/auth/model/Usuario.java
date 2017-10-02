/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dairon
 */
@Entity
@Table(name = "auth_usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u  WHERE u.habilitado = ?1 ORDER BY u.id"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = ?1"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByTipoIdentificacion", query = "SELECT u FROM Usuario u WHERE u.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Usuario.findByTipoIdentificacionIdentificacion", query = "SELECT u FROM Usuario u WHERE u.tipoIdentificacion = ?1 AND u.identificacion = ?2 AND u.habilitado = ?3"),
    @NamedQuery(name = "Usuario.findByIdentificacion", query = "SELECT u FROM Usuario u WHERE u.identificacion = ?1 AND u.habilitado = ?2"),
    @NamedQuery(name = "Usuario.findByTipo", query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findBySexo", query = "SELECT u FROM Usuario u WHERE u.sexo = :sexo"),
    @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = ?1 and u.habilitado = ?2"),
    @NamedQuery(name = "Usuario.findByHabilitado", query = "SELECT u FROM Usuario u WHERE u.habilitado = :habilitado")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(max = 100, message = "solo 100 máximo")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_identificacion", nullable = false, length = 1)
    private String tipoIdentificacion;

    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, length = 20)
    private String identificacion;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false, length = 1)
    private String tipo;

    @Size(max = 100)
    @Column(length = 100)
    private String apellidos;


    @Size(min = 1, max = 1)
    @Column(length = 1)
    private String sexo;

    @Size(max = 30)
    @Column(length = 30)
    private String telefono;

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(length = 50)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String password;
    
    @Size(min = 1, max = 255)
    @Column(length = 255)
    private String salt;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String username;

    @Basic(optional = false)
    @Column(name = "contrasenia")
    private String contrasenia;
    
    private Boolean habilitado;

    @JsonIgnore
    @JoinColumn(name = "id_departamento", referencedColumnName = "id")
    @ManyToOne
    private Departamento departamento;

    @ManyToMany
    @JoinTable(name = "auth_usuario_rol",
            joinColumns = {
                @JoinColumn(name = "id_usuario", referencedColumnName="id")},
            inverseJoinColumns = {
                @JoinColumn(name = "id_rol",referencedColumnName="id")})
    private List<Rol> roles;
    
    

    public Usuario() {
       this.tipo="N";
       this.habilitado = true;
    }

    public Usuario(Integer id) {
        this.id = id;
        
    }

    public Usuario(Integer id, String nombre, String tipoIdentificacion, String identificacion, String tipo, String password, String username) {
        this.id = id;
        this.nombre = nombre;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.tipo = tipo;
        this.password = password;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    @XmlTransient
    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
    public void addRol(Rol r){
        if(!this.roles.contains(r))
            this.roles.add(r);
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.auth.model.Usuario[ id=" + id + " ]";
    }

    public static boolean validarCedula(String x) {

        Pattern pattern = Pattern.compile("^[0-9]{10}+$");
        Matcher matcher = pattern.matcher(x);
        if (!matcher.matches()) {
            return false;

        }

        int suma = 0;

        int a[] = new int[x.length() / 2];
        int b[] = new int[(x.length() / 2)];
        int c = 0;
        int d = 1;
        for (int i = 0; i < x.length() / 2; i++) {
            a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
            c = c + 2;
            if (i < (x.length() / 2) - 1) {
                b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
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
        if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1)))) {
            return true;
        } else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
            return true;
        } else {
            return false;
        }

    }

//    private void generatePassword() {
//        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
//        Object salt = rng.nextBytes();
//        this.salt = salt.toString();
//
//        // Now hash the plain-text password with the random salt and multiple
//        // iterations and then Base64-encode the value (requires less space than Hex):
//        String hashedPasswordBase64 = new Sha256Hash(this.password, salt, 1024).toBase64();
//
//        this.setPassword(hashedPasswordBase64);
//
//    }

}
