/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.model;

import com.dadoco.pago.model.Pago;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dfcalderio
 */
@Entity
@Table(name = "deudas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deuda.findAll", query = "SELECT d FROM Deuda d"),
    @NamedQuery(name = "Deuda.findById", query = "SELECT d FROM Deuda d WHERE d.id = ?1"),
    @NamedQuery(name = "Deuda.findByDocumento", query = "SELECT d FROM Deuda d WHERE d.documento = :documento"),
    @NamedQuery(name = "Deuda.findByContribuyente", query = "SELECT d FROM Deuda d WHERE d.contribuyente = :contribuyente"),
    @NamedQuery(name = "Deuda.findByIdentificacion", query = "SELECT d FROM Deuda d WHERE d.identificacion = ?1"),
    @NamedQuery(name = "Deuda.findByIdentificacionSinPagar", query = "SELECT d FROM Deuda d WHERE d.identificacion = ?1 AND d.estado <>'PAGADO'"),
    @NamedQuery(name = "Deuda.findIdentificacion", query = "SELECT d FROM Deuda d WHERE d.identificacion LIKE ?1 AND d.estado<>'PAGADO' ORDER BY d.id ASC"),
    @NamedQuery(name = "Deuda.findBloqueados", query = "SELECT d FROM Deuda d WHERE d.identificacion LIKE ?1 AND d.bloqueado=TRUE ORDER BY d.id ASC"),
    @NamedQuery(name = "Deuda.findByFechaIngreso", query = "SELECT d FROM Deuda d WHERE d.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Deuda.findByConcepto", query = "SELECT d FROM Deuda d WHERE d.concepto = :concepto"),
    @NamedQuery(name = "Deuda.findByDocAnio", query = "SELECT d FROM Deuda d WHERE d.docAnio = :docAnio"),
    @NamedQuery(name = "Deuda.findByClaveCatastral", query = "SELECT d FROM Deuda d WHERE d.claveCatastral = ?1 AND d.estado <> 'ANULADO'"),
    @NamedQuery(name = "Deuda.findByClaveCatastralCheck", query = "SELECT d FROM Deuda d WHERE d.claveCatastral = ?1 AND d.estado = ?2"),
    @NamedQuery(name = "Deuda.findByClaveCatastralSinPagar", query = "SELECT d FROM Deuda d WHERE d.claveCatastral = ?1 AND d.estado <> 'PAGADO' AND d.estado <> 'ANULADO' "),
    @NamedQuery(name = "Deuda.findByClaveCatastralSinPagarAnio", query = "SELECT d FROM Deuda d WHERE d.claveCatastral = ?1 AND d.estado <> 'PAGADO' AND d.estado <> 'ANULADO' AND d.docAnio>= ?2"),
    @NamedQuery(name = "Deuda.findByEstado", query = "SELECT d FROM Deuda d WHERE d.estado = :estado"),
    @NamedQuery(name = "Deuda.findByObservacionbloqueo", query = "SELECT d FROM Deuda d WHERE d.observacionbloqueo = :observacionbloqueo"),
    @NamedQuery(name = "Deuda.findByFechaCobro", query = "SELECT d FROM Deuda d WHERE d.fechaCobro = :fechaCobro"),
    @NamedQuery(name = "Deuda.findBySubtotal", query = "SELECT d FROM Deuda d WHERE d.subtotal = :subtotal"),
    @NamedQuery(name = "Deuda.findByInteres", query = "SELECT d FROM Deuda d WHERE d.interes = :interes"),
    @NamedQuery(name = "Deuda.findByDescuento", query = "SELECT d FROM Deuda d WHERE d.descuento = :descuento"),
    @NamedQuery(name = "Deuda.findByVtc", query = "SELECT d FROM Deuda d WHERE d.vtc = :vtc"),
    @NamedQuery(name = "Deuda.findByTotalpago", query = "SELECT d FROM Deuda d WHERE d.totalpago = :totalpago"),
    @NamedQuery(name = "Deuda.findBySaldo", query = "SELECT d FROM Deuda d WHERE d.saldo = :saldo")})
public class Deuda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "doc_anio")
    private short docAnio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "clave_catastral")
    private String claveCatastral;
    @Size(max = 2147483647)
    @Column(name = "contribuyente")
    private String contribuyente;
    @Size(max = 2147483647)
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Size(max = 2147483647)
    @Column(name = "concepto")
    private String concepto;
    @Size(max = 2147483647)
    @Column(name = "estado")
    private String estado;
    @Size(max = 2147483647)
    @Column(name = "observacionbloqueo")
    private String observacionbloqueo;
    @Column(name = "fecha_cobro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCobro;
    @Column(name = "subtotal")
    private float subtotal;
    @Column(name = "interes")
    private float interes;
    @Column(name = "interes_calculado")
    private float interesCalculado;
    @Column(name = "descuento")
    private float descuento;
    @Column(name = "descuento_calculado")
    private float descuentoCalculado;
    @Column(name = "vtc")
    private float vtc;
    @Column(name = "totalpago")
    private float totalpago;
    @Column(name = "saldo")
    private float saldo;
    @Column(name = "pago_efectivo")
    private float pagoEfectivo;
    @Column(name = "pago_cheque")
    private float pagoCheque;
    @Column(name = "pago_deposito")
    private float pagoDeposito;
    @Column(name = "pago_nota_credito")
    private float pagoNotaCredito;

    @JoinTable(name = "pago_deuda_pago", joinColumns = {
        @JoinColumn(name = "id_deuda", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_pago", referencedColumnName = "id")})
    @ManyToMany
    private List<Pago> pagos;

    @JoinColumn(name = "id_pago", referencedColumnName = "id")
    @ManyToOne
    private Pago idPago;

    @JoinColumns({
        @JoinColumn(name = "id_predio", referencedColumnName = "id_predio", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "anio", referencedColumnName = "anio", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "status", referencedColumnName = "estado", nullable = false, insertable = false, updatable = false)})
    @OneToOne(fetch = FetchType.LAZY)
    private ImpuestoPredial impuestoPredial;
    
    private boolean bloqueado;

    public Deuda() {
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public float getPagoEfectivo() {
        return pagoEfectivo;
    }

    public void setPagoEfectivo(float pagoEfectivo) {
        this.pagoEfectivo = pagoEfectivo;
    }

    public float getPagoCheque() {
        return pagoCheque;
    }

    public void setPagoCheque(float pagoCheque) {
        this.pagoCheque = pagoCheque;
    }

    public float getPagoDeposito() {
        return pagoDeposito;
    }

    public void setPagoDeposito(float pagoDeposito) {
        this.pagoDeposito = pagoDeposito;
    }

    public float getPagoNotaCredito() {
        return pagoNotaCredito;
    }

    public void setPagoNotaCredito(float pagoNotaCredito) {
        this.pagoNotaCredito = pagoNotaCredito;
    }

    public String getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(String contribuyente) {
        this.contribuyente = contribuyente;
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

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacionbloqueo() {
        return observacionbloqueo;
    }

    public void setObservacionbloqueo(String observacionbloqueo) {
        this.observacionbloqueo = observacionbloqueo;
    }

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getInteres() {
        return interes;
    }

    public void setInteres(float interes) {
        this.interes = interes;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getVtc() {
        return vtc;
    }

    public void setVtc(float vtc) {
        this.vtc = vtc;
    }

    public float getTotalpago() {
        return totalpago;
    }

    public void setTotalpago(float totalpago) {
        this.totalpago = totalpago;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getInteresCalculado() {
        return interesCalculado;
    }

    public void setInteresCalculado(float interesCalculado) {
        this.interesCalculado = interesCalculado;
    }

    public float getDescuentoCalculado() {
        return descuentoCalculado;
    }

    public void setDescuentoCalculado(float descuentoCalculado) {
        this.descuentoCalculado = descuentoCalculado;
    }

    public ImpuestoPredial getImpuestoPredial() {
        return impuestoPredial;
    }

    public void setImpuestoPredial(ImpuestoPredial impuestoPredial) {
        this.impuestoPredial = impuestoPredial;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Deuda other = (Deuda) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "com.dusatecorp.cat.deudas.Deuda[ id=" + id + " ]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlTransient
    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public short getDocAnio() {
        return docAnio;
    }

    public void setDocAnio(short docAnio) {
        this.docAnio = docAnio;
    }

    public String getClaveCatastral() {
        return claveCatastral;
    }

    public void setClaveCatastral(String claveCatastral) {
        this.claveCatastral = claveCatastral;
    }

    public Pago getIdPago() {
        return idPago;
    }

    public void setIdPago(Pago idPago) {
        this.idPago = idPago;
    }

}
