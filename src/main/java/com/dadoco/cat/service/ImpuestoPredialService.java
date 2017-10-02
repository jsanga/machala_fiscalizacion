/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.service;

import com.dadoco.cat.model.Contribuyente;
import com.dadoco.cat.model.Deuda;
import com.dadoco.cat.model.Exencion;
import com.dadoco.cat.model.ImpuestoPredial;
import com.dadoco.cat.model.ImpuestoPredialPK;
import com.dadoco.cat.model.Predio;
import com.dadoco.common.service.VariableService;
import com.dadoco.ren.service.AvaluoService;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfcalderio
 */
@Stateless
public class ImpuestoPredialService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger("");

    @EJB
    private ExencionService exencionesService;
    @EJB
    private RecargoService recargosService;
    @EJB
    private VariableService variableService;
    @EJB
    private AvaluosService avaluosService;
    @EJB
    private AvaluoService avaluoService;
    @EJB
    private ImpuestoService impuestoService;

    @PersistenceContext(unitName = "dusatecorp_catastro")

    private EntityManager em;

    private List<ImpuestoPredial> impuestos;

    /*Eventos*/
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<ImpuestoPredial> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoPredial> impuestos) {
        this.impuestos = impuestos;
    }

    @Transactional
    public ImpuestoPredial emitirImpuestoPredial(Predio predio, short anio, double aterreno, double aconstruccion,
            double acomplementarias, double apredial, float bandaImpositiva) throws Exception {

        double exenciones = avaluoService.calcularExenciones(predio, anio, aterreno, aconstruccion, acomplementarias);
        exenciones = (float) (Math.round(exenciones * 100.0) / 100.0);
        log.error("exen: " + exenciones);
        double recargos = 0;// calcularRecargos(predio, anio, aterreno, aconstruccion, acomplementarias);
//        log.error("recarg: " + recargos);
        recargos = (float) (Math.round(recargos * 100.0) / 100.0);

        float salarioBasico = variableService.obtenerCoeficiente("datos_configuracion", "salario_basico", (short) anio, (short) anio);
        float valor_tope = salarioBasico * 500;

        double baseImponible;

        if (exenciones == 0) {
            baseImponible = apredial;
            log.error("baseimp exe = 0: " + baseImponible);
        } else if (apredial <= valor_tope) {
            baseImponible = apredial - exenciones;
            log.error("baseimp apredial <= valor tope : " + baseImponible);
        } else {
            baseImponible = valor_tope - exenciones;
            log.error("baseimp apredial > valor tope: " + baseImponible);
        }
        double impuestoPredial = apredial * bandaImpositiva / 1000;

        if (impuestoPredial < predio.getTerreno().getManzana().getZonaHomogenea().valorActual(anio).getContribucionMinima()) {
            impuestoPredial = predio.getTerreno().getManzana().getZonaHomogenea().valorActual(anio).getContribucionMinima();
        }

        float coef_cem = variableService.obtenerCoeficiente("datos_configuracion", "cem", (short) anio, (short) anio);
        double cem = apredial * coef_cem / 1000;
//        log.error("coef cem: " + coef_cem);
        float coef_rpj = variableService.obtenerCoeficiente("datos_configuracion", "recargo_personas_juridicas", (short) anio, (short) anio);
        double rpj = 0;
        if (predio.contieneContribuyenteJuridico()) {
            rpj = apredial * coef_rpj / 1000;
            rpj = (Math.round(rpj * 100.0) / 100.0);
        }

        float valorTasa = variableService.obtenerCoeficiente("datos_configuracion", "servicios_administrativos", (short) anio, (short) anio);

        float coef_bomberos = variableService.obtenerCoeficiente("datos_configuracion", "bombero", (short) anio, (short) anio);

        double impuestoBombero = apredial * coef_bomberos / 1000;

        float impuestoSaludSeguridad = predio.getTerreno().getManzana().getZonaHomogenea().valorActual(anio).getSaludSeguridad();

//        log.error("coef rpj: " + coef_rpj);
        float coef_basura = 0;//variableService.obtenerCoeficiente("datos_configuracion", "recoleccion_basura", (short) anio, (short) anio);
        double basura = 0;//apredial * coef_basura / 1000;
        basura = (Math.round(basura * 100.0) / 100.0);
//        log.error("coef bas: " + coef_basura);
        float coef_noedif = variableService.obtenerCoeficiente("datos_configuracion", "solar_no_edificado", (short) anio, (short) anio);
        double no_edif = aconstruccion == 0 ? apredial * coef_noedif / 1000 : 0;
        no_edif = (Math.round(no_edif * 100.0) / 100.0);

        long consec = (long) variableService.obtenerCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) anio, (short) anio) + 1;
//        log.error("consec: " + consec);

        variableService.setCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) anio, (float) consec, (short) anio);

        String documento = String.format("%d%06d01M", anio, consec);
        String descripcion = "";

//        log.error("doc: " + documento);
        ImpuestoPredialPK impuestoPK = new ImpuestoPredialPK(predio.getId(), anio);
        impuestoPK.setEstado((short) 0);
        ImpuestoPredial impuesto = new ImpuestoPredial();
        impuesto.setImpuestoPredialPK(impuestoPK);

        ImpuestoPredialPK impuestoPKa = new ImpuestoPredialPK(predio.getId(), (short) (anio - 1));
        impuestoPKa.setEstado((short) 0);
        ImpuestoPredial impuestoAnterior = em.find(ImpuestoPredial.class, impuestoPKa);

        if (impuestoAnterior != null) {
            impuesto.setAnterior(impuestoAnterior);
        }

        double total = impuestoPredial + cem + basura + rpj + no_edif + valorTasa;
        log.error("Total actual: " + total);
        boolean recalcular = true;

        double aTerreno = predio.getTerreno().getAreaLevantamiento();

        impuesto.setDocumento(documento);
        impuesto.setAreaTerreno((Math.round(aTerreno * 100.0) / 100.0));
        impuesto.setAreaConstruccion((Math.round(predio.areaTotalEdificacion() * 100.0) / 100.0));
        impuesto.setAvaluoTerreno(aterreno);
        impuesto.setAvaluoEdificacion(aconstruccion);
        impuesto.setAvaluoComplementarias(acomplementarias);
        impuesto.setAvaluoPredial(apredial);
        impuesto.setExenciones(exenciones);
        impuesto.setRecargos(recargos);
        impuesto.setBaseImponible(baseImponible);
        impuesto.setBandaImpositiva(bandaImpositiva);
        impuesto.setImpuestoPredial(impuestoPredial);
        impuesto.setClaveCatastral(predio.getClaveCatastral());
        impuesto.setCem(cem);
        impuesto.setRecargoPersonasJuridicas(rpj);
        impuesto.setSolarNoEdificado(no_edif);
        impuesto.setRecoleccionBasura(basura);
        impuesto.setTasa(valorTasa);
        impuesto.setBombero(impuestoBombero);
        impuesto.setSaludSeguridad(impuestoSaludSeguridad);
        impuesto.setExcluido(false);
        impuesto.setBloqueado(false);
        impuesto.setEmitido(false);
        impuesto.setTotal(impuestoPredial + cem + rpj + no_edif + valorTasa + impuestoBombero + impuestoSaludSeguridad);
        impuesto.setDescripcion(descripcion);

        em.persist(impuesto);
        em.flush();

        if (impuestoAnterior != null) {
            em.createNativeQuery("UPDATE cat_impuesto_predial \n"
                    + "SET anio_anterior = " + (anio - 1)
                    + " WHERE id_predio=" + predio.getId() + " AND anio=" + anio + " AND estado=0" + "").executeUpdate();

            em.flush();
        }
        return impuesto;
    }

    @Transactional
    public ImpuestoPredial emitirImpuestoPredialPH(Predio predio, short anio, double apredial, float bandaImpositiva) throws Exception {

        double exenciones = avaluoService.calcularExencionesPH(predio, anio, apredial);
        exenciones = (float) (Math.round(exenciones * 100.0) / 100.0);
        log.error("exen: " + exenciones);
        double recargos = 0;// calcularRecargos(predio, anio, aterreno, aconstruccion, acomplementarias);
//        log.error("recarg: " + recargos);
        recargos = (float) (Math.round(recargos * 100.0) / 100.0);

        float salarioBasico = variableService.obtenerCoeficiente("datos_configuracion", "salario_basico", anio, anio);
        float valor_tope = salarioBasico * 500;

        double baseImponible;

        if (exenciones == 0) {
            baseImponible = apredial;
            log.error("baseimp exe = 0: " + baseImponible);
        } else if (apredial <= valor_tope) {
            baseImponible = apredial - exenciones;
            log.error("baseimp apredial <= valor tope : " + baseImponible);
        } else {
            baseImponible = valor_tope - exenciones;
            log.error("baseimp apredial > valor tope: " + baseImponible);
        }
        double impuestoPredial = apredial * bandaImpositiva / 1000;

        if (impuestoPredial < predio.getTerreno().getManzana().getZonaHomogenea().valorActual(anio).getContribucionMinima()) {
            impuestoPredial = predio.getTerreno().getManzana().getZonaHomogenea().valorActual(anio).getContribucionMinima();
        }

        float valorTasa = variableService.obtenerCoeficiente("datos_configuracion", "servicios_administrativos", (short) anio, (short) anio);

        float coef_bomberos = variableService.obtenerCoeficiente("datos_configuracion", "bombero", (short) anio, (short) anio);
        float impuestoSaludSeguridad = predio.getTerreno().getManzana().getZonaHomogenea().valorActual(anio).getSaludSeguridad();

        double impuestoBombero = apredial * coef_bomberos / 1000;

        float coef_cem = variableService.obtenerCoeficiente("datos_configuracion", "cem", anio, anio);
        double cem = apredial * coef_cem / 1000;
//        log.error("coef cem: " + coef_cem);
        float coef_rpj = variableService.obtenerCoeficiente("datos_configuracion", "recargo_personas_juridicas", anio, anio);
        double rpj = 0;
        if (predio.contieneContribuyenteJuridico()) {
            rpj = apredial * coef_rpj / 1000;
            rpj = (Math.round(rpj * 100.0) / 100.0);
        }

//        log.error("coef rpj: " + coef_rpj);
        float coef_basura = variableService.obtenerCoeficiente("datos_configuracion", "recoleccion_basura", anio, anio);
        double basura = apredial * coef_basura / 1000;
        basura = (Math.round(basura * 100.0) / 100.0);

        double no_edif = 0;
        no_edif = (Math.round(no_edif * 100.0) / 100.0);

        long consec = (long) variableService.obtenerCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) 0, anio) + 1;
//        log.error("consec: " + consec);

        variableService.setCoeficiente("datos_configuracion", "consecutivo_impuestos", (short) 0, (float) consec, anio);

        String documento = String.format("%d%06d01M", anio, consec);
        String descripcion = "";

//        log.error("doc: " + documento);
        ImpuestoPredialPK impuestoPK = new ImpuestoPredialPK(predio.getId(), anio);
        impuestoPK.setEstado((short) 0);
        ImpuestoPredial impuesto = new ImpuestoPredial();
        impuesto.setImpuestoPredialPK(impuestoPK);

        ImpuestoPredialPK impuestoPKa = new ImpuestoPredialPK(predio.getId(), (short) (anio - 1));
        impuestoPKa.setEstado((short) 0);

        double total = impuestoPredial + cem + basura + rpj + no_edif + valorTasa;
        log.error("Total actual: " + total);
        boolean recalcular = true;

        double aTerreno = predio.getTerreno().getAreaLevantamiento();

        impuesto.setDocumento(documento);
        impuesto.setAreaTerreno((Math.round(aTerreno * 100.0) / 100.0));
        impuesto.setAreaConstruccion((Math.round(predio.areaTotalEdificacion() * 100.0) / 100.0));
        impuesto.setAvaluoTerreno(0);
        impuesto.setAvaluoEdificacion(0);
        impuesto.setAvaluoComplementarias(0);
        impuesto.setAvaluoPredial(apredial);
        impuesto.setExenciones(exenciones);
        impuesto.setRecargos(recargos);
        impuesto.setBaseImponible(baseImponible);
        impuesto.setBandaImpositiva(bandaImpositiva);
        impuesto.setImpuestoPredial(impuestoPredial);
        impuesto.setClaveCatastral(predio.getClaveCatastral());
        impuesto.setCem(cem);
        impuesto.setRecargoPersonasJuridicas(rpj);
        impuesto.setSolarNoEdificado(no_edif);
        impuesto.setRecoleccionBasura(basura);
        impuesto.setBombero(impuestoBombero);
        impuesto.setSaludSeguridad(impuestoSaludSeguridad);
        impuesto.setExcluido(false);
        impuesto.setBloqueado(false);
        impuesto.setEmitido(false);
        impuesto.setTotal(impuestoPredial + cem + rpj + no_edif + valorTasa + impuestoBombero + impuestoSaludSeguridad);
        impuesto.setDescripcion(descripcion);
        impuesto.setTasa(valorTasa);

        em.persist(impuesto);
        em.flush();

        return impuesto;
    }

    @Transactional
    public Deuda crearDeuda(ImpuestoPredial ip) throws Exception {

        short anioEmision = ip.getImpuestoPredialPK().getAnio();

        log.error("Impuesto predia PK: " + ip.getImpuestoPredialPK().toString());

        String contribuyentes = "";
        String identificaciones = "";

        List<Contribuyente> propietarios = ip.getPredio().getPropietarios();
        if (!propietarios.isEmpty() && propietarios != null) {
            if (propietarios.size() == 1) {
                contribuyentes += propietarios.get(0).getNombreCompleto();
                identificaciones += propietarios.get(0).getIdentificacion();
            } else {
                for (int i = 0; i < propietarios.size() - 1; i++) {
                    if (i == 0) {
                        contribuyentes += propietarios.get(0).getNombreCompleto();
                        identificaciones += propietarios.get(0).getIdentificacion();
                    } else {
                        contribuyentes += "," + propietarios.get(0).getNombreCompleto();
                        identificaciones += "," + propietarios.get(0).getIdentificacion();
                    }
                }

                contribuyentes += propietarios.get(propietarios.size() - 1).getNombreCompleto();
                identificaciones += propietarios.get(propietarios.size() - 1).getIdentificacion();
            }
        }
        Deuda deuda = new Deuda();

        deuda.setDocumento(ip.getDocumento());
        deuda.setDocAnio(ip.getImpuestoPredialPK().getAnio());
        deuda.setClaveCatastral(ip.getClaveCatastral());
        deuda.setConcepto("IMPUESTO PREDIAL URBANO " + ip.getImpuestoPredialPK().getAnio());
        deuda.setContribuyente(contribuyentes);
        deuda.setIdentificacion(identificaciones);
        deuda.setEstado("NO PAGADO");
        deuda.setFechaIngreso(new Date());
        deuda.setSubtotal((float) ip.getTotal());
        deuda.setVtc((float)ip.getTasa());
        deuda.setSaldo((float) ip.getTotal());

        deuda.setBloqueado(deudaRequiereBloqueo(ip.getPredio()));
        //deuda.setImpuestoPredial(ip);

        em.persist(deuda);
        em.flush();

        em.createNativeQuery("UPDATE deudas \n"
                + "SET id_predio = " + ip.getPredio().getId() + ", anio = " + anioEmision + ", status = " + ip.getImpuestoPredialPK().getEstado()
                + " WHERE documento='" + ip.getDocumento() + "' ").executeUpdate();

        em.flush();

        return deuda;

    }

    public boolean deudaRequiereBloqueo(Predio p) {

        List<Exencion> exenciones = p.getExenciones();

        for (Exencion e : exenciones) {
            if (e.getRazonExencion().getId() == 11) {
                return true;
            }
        }
        return false;
    }
}
