/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_integrante")
@NamedQueries({
    @NamedQuery(name = "Integrante.findAll", query = "SELECT i FROM Integrante i where i.habilitado = true"),
    @NamedQuery(name = "Integrante.findById", query = "SELECT i FROM Integrante i WHERE i.id = :id"),
    @NamedQuery(name = "Integrante.findByNombre", query = "SELECT i FROM Integrante i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Integrante.findByApellidos", query = "SELECT i FROM Integrante i WHERE i.apellidos = :apellidos"),
    @NamedQuery(name = "Integrante.findByTelefono", query = "SELECT i FROM Integrante i WHERE i.telefono = :telefono"),
    @NamedQuery(name = "Integrante.findByEmail", query = "SELECT i FROM Integrante i WHERE i.email = :email"),
    @NamedQuery(name = "Integrante.findByHabilitado", query = "SELECT i FROM Integrante i WHERE i.habilitado = :habilitado"),
    @NamedQuery(name = "Integrante.findByTipo", query = "SELECT i FROM Integrante i WHERE i.tipo = ?1 AND i.habilitado = TRUE"),
    @NamedQuery(name = "Integrante.findByIdentificacion", query = "SELECT i FROM Integrante i WHERE i.identificacion = :identificacion"),
    @NamedQuery(name = "Integrante.findByFechaIngreso", query = "SELECT i FROM Integrante i WHERE i.fechaIngreso = :fechaIngreso")})
public class Integrante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 30)
    @Column(name = "telefono")
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "habilitado")
    private boolean habilitado;    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private short tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
        //Fsan
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "integrante")
    private List<Incidencia> incidencia;
	
   //
    @JsonIgnore
    @OneToMany(mappedBy = "padre")
    private List<Integrante> integranteList;
    @JsonIgnore
    @JoinColumn(name = "id_padre", referencedColumnName = "id")
    @ManyToOne
    private Integrante padre;
    @JsonIgnore
    @Transient
    private String nombreCompleto;

    public Integrante() {
        this.habilitado = true;
    }

    public Integrante(Integer id) {
        this.id = id;
    }

    public Integrante(Integer id, String nombre, String apellidos, boolean habilitado, short tipo, String identificacion, Date fechaIngreso) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.habilitado = habilitado;
        this.tipo = tipo;
        this.identificacion = identificacion;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        //nombre = nombre.toUpperCase();
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos.toUpperCase();
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

    public boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * 0 - relevamiento tecnico
     * 1 - supervisor relevamiento tecnico 
     * 2 - validador control de calidad
     * 3 - digitador
     * 4 - supervisor digitador
     * 5 - cartografo
     * @return tipo
     */
    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String valorTipo(){
        String valor = "";
        switch(this.tipo){
            case 0: { valor = "Relevamiento"; break;}
            case 1: { valor = "Digitador"; break;}
            case 2: { valor = "Validador"; break;}
            case 3: { valor = "Supervisor digitación"; break;}
            case 4: { valor = "Supervisor"; break;}
            case 5: { valor = "Cartografo"; break;}
            
        }
        
        return valor;
    }

    public List<Incidencia> getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(List<Incidencia> incidencia) {
        this.incidencia = incidencia;
    }

    public List<Integrante> getIntegranteList() {
        return integranteList;
    }

    public void setIntegranteList(List<Integrante> integranteList) {
        this.integranteList = integranteList;
    }

    public Integrante getPadre() {
        return padre;
    }

    public void setPadre(Integrante padre) {
        this.padre = padre;
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
        if (!(object instanceof Integrante)) {
            return false;
        }
        Integrante other = (Integrante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Integrante[ id=" + id + " ]";
    }
    
    /**
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        if(nombre!=null && apellidos!=null){
            nombreCompleto=nombre+" "+apellidos;
        }
        return nombreCompleto;
    }
    
}
