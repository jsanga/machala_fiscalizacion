/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_recargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recargo.findAll", query = "SELECT c FROM Recargo c"),
    @NamedQuery(name = "Recargo.findById", query = "SELECT c FROM Recargo c WHERE c.id = :id"),
    @NamedQuery(name = "Recargo.findByValor", query = "SELECT c FROM Recargo c WHERE c.valor = :valor"),
    @NamedQuery(name = "Recargo.findByAnioRegistro", query = "SELECT c FROM Recargo c WHERE c.anioRegistro = :anioRegistro"),
    @NamedQuery(name = "Recargo.findByVigencia", query = "SELECT c FROM Recargo c WHERE c.vigencia = :vigencia"),
    @NamedQuery(name = "Recargo.findByFechaIngreso", query = "SELECT c FROM Recargo c WHERE c.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Recargo.findByUsuarioIngreso", query = "SELECT c FROM Recargo c WHERE c.usuarioIngreso = :usuarioIngreso"),
    @NamedQuery(name = "Recargo.findByFechaModificacion", query = "SELECT c FROM Recargo c WHERE c.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "Recargo.findByUsuarioModifica", query = "SELECT c FROM Recargo c WHERE c.usuarioModifica = :usuarioModifica"),
    @NamedQuery(name = "Recargo.findByFechaEliminacion", query = "SELECT c FROM Recargo c WHERE c.fechaEliminacion = :fechaEliminacion"),
    @NamedQuery(name = "Recargo.findByUsuarioElimina", query = "SELECT c FROM Recargo c WHERE c.usuarioElimina = :usuarioElimina"),

    @NamedQuery(name = "Recargo.findByPredioFecha", query = "SELECT c FROM Recargo c WHERE (c.predio.id = :idPredio) AND ((c.anioRegistro <= :anio) AND (:anio <= (c.anioRegistro+c.vigencia)))"),

    @NamedQuery(name = "Recargo.findByNotas", query = "SELECT c FROM Recargo c WHERE c.notas = :notas")})
public class Recargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private float valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anio_registro")
    private int anioRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vigencia")
    private int vigencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "usuario_ingreso")
    private String usuarioIngreso;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Size(max = 150)
    @Column(name = "usuario_modifica")
    private String usuarioModifica;
    @Column(name = "fecha_eliminacion")
    @Temporal(TemporalType.DATE)
    private Date fechaEliminacion;
    @Size(max = 150)
    @Column(name = "usuario_elimina")
    private String usuarioElimina;
    @Size(max = 255)
    @Column(name = "notas")
    private String notas;
    @JoinColumn(name = "id_razon_recargo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RazonRecargo razonRecargo;
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;

    public Recargo() {
    }

    public Recargo(Predio predio, RazonRecargo razon, float valor, int anioRegistro, int vigencia, Date fechaIngreso, String usuarioIngreso, String notas) {
        this.predio = predio;
        this.razonRecargo = razon;
        this.valor = valor;
        this.anioRegistro = anioRegistro;
        this.vigencia = vigencia;
        this.fechaIngreso = fechaIngreso;
        this.usuarioIngreso = usuarioIngreso;
        this.notas = notas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getAnioRegistro() {
        return anioRegistro;
    }

    public void setAnioRegistro(int anioRegistro) {
        this.anioRegistro = anioRegistro;
    }

    public int getVigencia() {
        return vigencia;
    }

    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(String usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public Date getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(Date fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public String getUsuarioElimina() {
        return usuarioElimina;
    }

    public void setUsuarioElimina(String usuarioElimina) {
        this.usuarioElimina = usuarioElimina;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public RazonRecargo getRazonRecargo() {
        return razonRecargo;
    }

    public void setRazonRecargo(RazonRecargo razonRecargo) {
        this.razonRecargo = razonRecargo;
    }

    public boolean estaActivoEn(int anio) {
        return (this.anioRegistro <= anio && anio <= (this.anioRegistro + this.vigencia));
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
        if (!(object instanceof Recargo)) {
            return false;
        }
        Recargo other = (Recargo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Recargo[ id=" + id + " ]";
    }

}
