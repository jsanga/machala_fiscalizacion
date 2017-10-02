/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "cat_exencion")
@NamedQueries({
    @NamedQuery(name = "Exencion.findAll", query = "SELECT c FROM Exencion c"),
    @NamedQuery(name = "Exencion.findById", query = "SELECT c FROM Exencion c WHERE c.id = :id"),
    @NamedQuery(name = "Exencion.findByPorcentaje", query = "SELECT c FROM Exencion c WHERE c.porcentaje = :porcentaje"),
    @NamedQuery(name = "Exencion.findByValor", query = "SELECT c FROM Exencion c WHERE c.valor = :valor"),
    @NamedQuery(name = "Exencion.findByDesde", query = "SELECT c FROM Exencion c WHERE c.desde = :desde"),
    @NamedQuery(name = "Exencion.findByHasta", query = "SELECT c FROM Exencion c WHERE c.hasta = :hasta"),
    @NamedQuery(name = "Exencion.findByFechaIngreso", query = "SELECT c FROM Exencion c WHERE c.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Exencion.findByUsuarioIngreso", query = "SELECT c FROM Exencion c WHERE c.usuarioIngreso = :usuarioIngreso"),
    @NamedQuery(name = "Exencion.findByFechaModificacion", query = "SELECT c FROM Exencion c WHERE c.fechaModificacion = :fechaModificacion"),

    @NamedQuery(name = "Exencion.findByPredioFecha", query = "SELECT c FROM Exencion c WHERE c.predio.id = :idPredio AND c.desde <= :anio AND :anio <= c.hasta"),

    @NamedQuery(name = "Exencion.findByUsuarioModifica", query = "SELECT c FROM Exencion c WHERE c.usuarioModifica = :usuarioModifica"),
    @NamedQuery(name = "Exencion.findByFechaEliminacion", query = "SELECT c FROM Exencion c WHERE c.fechaEliminacion = :fechaEliminacion"),
    @NamedQuery(name = "Exencion.findByUsuarioElimina", query = "SELECT c FROM Exencion c WHERE c.usuarioElimina = :usuarioElimina"),
    @NamedQuery(name = "Exencion.findByNotas", query = "SELECT c FROM Exencion c WHERE c.notas = :notas"),
    @NamedQuery(name = "Exencion.findAllIdsPredios", query = "SELECT c.predio.id FROM Exencion c WHERE c.id>25680")
})
public class Exencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "porcentaje")
    private float porcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private float valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "desde")
    private int desde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hasta")
    private int hasta;
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

    @Column(name = "permite_porcentaje")
    private boolean permitePorcentaje;

    @JsonIgnore
    @JoinColumn(name = "id_razon_exencion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RazonExencion razonExencion;
    @JsonIgnore
    @JoinColumn(name = "id_predio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Predio predio;

    public Exencion() {
    }

    public Exencion(Integer id) {
        this.id = id;
    }

    public Exencion(Integer id, float porcentaje, float valor, int desde, int hasta, Date fechaIngreso, String usuarioIngreso) {
        this.id = id;
        this.porcentaje = porcentaje;
        this.valor = valor;
        this.desde = desde;
        this.hasta = hasta;
        this.fechaIngreso = fechaIngreso;
        this.usuarioIngreso = usuarioIngreso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    @JsonIgnore
    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getDesde() {
        return desde;
    }

    public void setDesde(int desde) {
        this.desde = desde;
    }

    public int getHasta() {
        return hasta;
    }

    public void setHasta(int hasta) {
        this.hasta = hasta;
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

    public RazonExencion getRazonExencion() {
        return razonExencion;
    }

    public void setRazonExencion(RazonExencion razonExencion) {
        this.razonExencion = razonExencion;
    }

    public boolean estaActivaEn(int anio) {
        return (this.desde <= anio && anio <= this.hasta);
    }

    public boolean isPermitePorcentaje() {
        return permitePorcentaje;
    }

    public void setPermitePorcentaje(boolean permitePorcentaje) {
        this.permitePorcentaje = permitePorcentaje;
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
        if (!(object instanceof Exencion)) {
            return false;
        }
        Exencion other = (Exencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.model.Exencion[ id=" + id + " ]";
    }

}
