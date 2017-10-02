/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.lazyModel;

import com.dadoco.cat.model.Bloque;
import com.dadoco.cat.model.BusquedaPredial;
import com.dadoco.cat.model.OtrosTipoConstruccion;
import com.dadoco.cat.model.Predio;
import com.dadoco.cat.model.UsoSuelo;
import com.dadoco.cat.model.ValorDiscreto;
import com.dadoco.cat.model.ValorDiscretoPK;
import com.dadoco.cat.service.PredioService;
import com.dadoco.common.service.ValorDiscretoService;
import com.dadoco.common.service.VariableService;
import com.dadoco.common.util.Util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author fernando
 */
public class LazyDataModelPredioBusqueda extends LazyDataModel<BusquedaPredial> {

    private String busqueda;
    private PredioService service;
    private VariableService variablesService;
    private ValorDiscretoService discretoService;
    private InfoModel<BusquedaPredial> informacionLazyModel;

    public LazyDataModelPredioBusqueda(VariableService variablesService) {
        super(null);
        this.variablesService = variablesService;
    }

    public LazyDataModelPredioBusqueda(PredioService service, VariableService variablesService) {
        super(service);
        this.service = service;
        this.variablesService = variablesService;
    }
    
    public LazyDataModelPredioBusqueda(PredioService service, VariableService variablesService, ValorDiscretoService discretoService) {
        super(service);
        this.service = service;
        this.variablesService = variablesService;
        this.discretoService = discretoService;
    }

    @Override
    public List<BusquedaPredial> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        if (this.service != null) {
            InfoModel<Predio> infoPredio = this.service.busquedaLazy(busqueda, first, pageSize, filters);
            List<BusquedaPredial> l = getBusquedaPredial(infoPredio.getListaDatos());
            this.informacionLazyModel = new InfoModel<>(infoPredio.getJpqlQuery(), infoPredio.getParametros());
            this.setRowCount(infoPredio.getCount());
            return l;
        } else {
            return null;
        }
    }

    public List<BusquedaPredial> getBusquedaPredial(List<Predio> lista) {
        log.info("SQL INICIO REPORTE: " + lista.size() + " TIEMPO: " + System.currentTimeMillis());
        List<BusquedaPredial> prediosF = new LinkedList<>();
        for (Predio bpredio : lista) {
            log.info("INICIANDO CONVERSION: " + bpredio.getClaveCatastral() + " TIEMPO: " + System.currentTimeMillis());
            BusquedaPredial addPredio = new BusquedaPredial();
            addPredio.setFIngreso(bpredio.getFechaCreacion());
            addPredio.setFModificacion(bpredio.getFechaModificacion());
            addPredio.setPropietario(bpredio.PropietariosAsString());
            addPredio.setCod_anterior(bpredio.getClaveAnterior());
            addPredio.setCod_actual(bpredio.getClaveCatastral());
            addPredio.setManzana(bpredio.getManzana());
            addPredio.setSuperficie(bpredio.getTerreno().getAreaLevantamiento());
            addPredio.setPerimetro(bpredio.getTerreno().getPerimetroLevantamiento());
            addPredio.setArea_construccion(bpredio.areaTotalEdificacion());
            log.info("Inicio de Uso de Suelos TIEMPO: " + System.currentTimeMillis());
            List<UsoSuelo> usos = new ArrayList<UsoSuelo>();
            String suelos = "";
        
            if (bpredio.getBloques()!=null) {
             if (!bpredio.getBloques().isEmpty()) {   
                usos = usosSueloSelect(bpredio.getBloques());
                addPredio.setEstado(estadoConservacion(bpredio.getBloques()));
                int niveles = 0;
                for (Bloque bloq : bpredio.getBloques()) {
                    niveles += bloq.getNumeroNiveles();
                }
                addPredio.setNiveles("" + niveles);
             }   
            }

            for (UsoSuelo uso2 : usos) {
                suelos += uso2.getValorDiscreto().getVariable().getNombre() + "-" + uso2.getValorDiscreto().getTexto() + "\n";
            }
            
            if(bpredio.getUsosSuelo()!=null)
            {
               for (UsoSuelo usoS : bpredio.getUsosSuelo()) {
                suelos += usoS.getValorDiscreto().getVariable().getNombre() + " " + usoS.getValorDiscreto().getTexto() + "\n";
               } 
            }
            log.info("Predio: "+addPredio.getCod_actual()+" Uso: " +suelos);
            addPredio.setUso_suelo(suelos);
            addPredio.setEstado(estadoConservacion(bpredio.getBloques()));
            addPredio.setNiveles(bpredio.getNiveles());
            log.info("Inicio de Otros de Construccion TIEMPO: " + System.currentTimeMillis());
            StringBuilder otrosTiposC = new StringBuilder();
            if (!bpredio.getOtrosConstruccion().isEmpty()) {
                for (OtrosTipoConstruccion otrosC : bpredio.getOtrosConstruccion()) {
                    ValorDiscretoPK pk = new ValorDiscretoPK();
                    pk.setAnio(Util.ANIO_ACTUAL.shortValue());
                    pk.setIdVariable(otrosC.getVariable());
                    pk.setValor(otrosC.getTipoConstruccion());
                    ValorDiscreto vd = discretoService.find(pk);
                    if(vd != null){
                        otrosTiposC.append(vd.getVariable().getNombre()).append(" - ").append(vd.getTexto());
                        otrosTiposC.append("\n");
                    }
//                    otrosTiposC.append(nombreVariable(otrosC.getVariable()));
//                    otrosTiposC.append("\n");
                }
            }
            addPredio.setOtros_construccion(otrosTiposC.toString());

            log.info("Inicio de Dominio TIEMPO: " + System.currentTimeMillis());
            String dominio = "";
            List<ValorDiscreto> valoresD = variablesService.obtenerValores("cat_predio", "dominio", (short) Util.ANIO_ACTUAL.shortValue());
            for (ValorDiscreto val : valoresD) {
                if (val.getValorDiscretoPK().getValor() == bpredio.getDominio()) {
                    dominio = val.getTexto();
                }
            }
            addPredio.setDominio(dominio);
            addPredio.setSitio((bpredio.getTerreno().getBarrio() != null) ? bpredio.getTerreno().getBarrio() : "");
            addPredio.setPredio((bpredio.getNroPredio() != null) ? bpredio.getNroPredio() : "");
            String geometria = "";
            log.info("Inicio de Geometria TIEMPO: " + System.currentTimeMillis());
            geometria = variablesService.obtenerValor("cat_terreno", "geometria", bpredio.getTerreno().getGeometria(), (short) Util.ANIO_ACTUAL.shortValue());
            log.info("Fin de Geometria TIEMPO: " + System.currentTimeMillis());
            addPredio.setGeometria(geometria);
            addPredio.setLind_norte(bpredio.getTerreno().getLinNorteRef());
            addPredio.setLind_sur(bpredio.getTerreno().getLinSurRef());
            addPredio.setLind_este(bpredio.getTerreno().getLinEsteRef());
            addPredio.setLind_oeste(bpredio.getTerreno().getLinOesteRef());
            addPredio.setLind_norte_long(bpredio.getTerreno().getLinNorteLongitud());
            addPredio.setLind_sur_long(bpredio.getTerreno().getLinSurLongitud());
            addPredio.setLind_este_long(bpredio.getTerreno().getLinEsteLongitud());
            addPredio.setLind_oeste_long(bpredio.getTerreno().getLinOesteLongitud());
            addPredio.setAncho_via_pub((bpredio.getTerreno().getAnchoViaPuplica() != null) ? bpredio.getTerreno().getAnchoViaPuplica() : 0);
            addPredio.setAncho_acera((bpredio.getTerreno().getAnchoAcera() != null) ? bpredio.getTerreno().getAnchoAcera() : 0);

            addPredio.setRelevamiento((bpredio.getRelevamiento() != null)
                    ? bpredio.getRelevamiento().getNombre() + " " + bpredio.getRelevamiento().getApellidos() : "");

            addPredio.setSup_relevamiento((bpredio.getSupervisor() != null)
                    ? bpredio.getSupervisor().getNombre() + " " + bpredio.getSupervisor().getApellidos() : "");

            addPredio.setC_calidad((bpredio.getValidador() != null)
                    ? bpredio.getValidador().getNombre() + " " + bpredio.getValidador().getApellidos() : "");

            addPredio.setDigitalizacion((bpredio.getCartografo() != null)
                    ? bpredio.getCartografo().getNombre() + " " + bpredio.getCartografo().getApellidos() : "");

            addPredio.setDigitacion((bpredio.getDigitador() != null)
                    ? bpredio.getDigitador().getNombre() + " " + bpredio.getDigitador().getApellidos() : bpredio.getUsuarioModifica());

            addPredio.setSup_digitacion((bpredio.getSupervisorDigitacion() != null)
                    ? bpredio.getSupervisorDigitacion().getNombre() + " " + bpredio.getSupervisorDigitacion().getApellidos() : "");

            addPredio.setUsuario(bpredio.getUsuarioModifica());

             if(bpredio.getImages().isEmpty())
             {
               addPredio.setFotoPredio("No tiene");
             }else
             {
               addPredio.setFotoPredio("Tiene");
             }
                    
                    if(!bpredio.getBloques().isEmpty())
                    { 
                      for(Bloque bloque:bpredio.getBloques())
                      {
                        
                       if(bloque.getImages().isEmpty())
                       {
                        addPredio.setFotoBloque("No tiene");
                       }else
                       {
                        addPredio.setFotoBloque("Tiene");
                        break;
                       }
                      }
                    }
                    else{
                       addPredio.setFotoBloque("No existen bloques construidos");
                    }
                    
            log.info("FIN DE PREDIO TIEMPO: " + System.currentTimeMillis());
            prediosF.add(addPredio);
        }

        return prediosF;
    }

    public String estadoConservacion(List<Bloque> construccion) {
        StringBuilder estado = new StringBuilder();
        for (Bloque b1 : construccion) {
            estado.append(variablesService.obtenerValor("cat_bloque", "estado_conservacion", b1.getEstadoConservacion(), (short) Util.ANIO_ACTUAL.shortValue()));
            estado.append("\n");
        }
        return estado.toString();
    }

    public List<UsoSuelo> usosSueloSelect(List<Bloque> construccion) {
        List<UsoSuelo> usosSelect = new ArrayList<UsoSuelo>();
        for (Bloque b1 : construccion) {
            for (UsoSuelo suelo : b1.getUsosSuelo()) {
                usosSelect.add(suelo);
            }
        }
//        HashSet<UsoSuelo> hashSet = new HashSet<UsoSuelo>(usosSelect);
//        usosSelect.clear();
//        usosSelect.addAll(hashSet);
        return usosSelect;
    }

    public String nombreVariable(int id) {

        return variablesService.find(id).getNombre();
    }

    /**
     * @return the busqueda
     */
    public String getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * @return the informacionLazyModel
     */
    public InfoModel<BusquedaPredial> getInformacionLazyModel() {
        return informacionLazyModel;
    }

    /**
     * @param informacionLazyModel the informacionLazyModel to set
     */
    public void setInformacionLazyModel(InfoModel<BusquedaPredial> informacionLazyModel) {
        this.informacionLazyModel = informacionLazyModel;
    }

}
