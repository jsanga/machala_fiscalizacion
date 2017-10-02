package com.dadoco.admin.controller;

import com.dadoco.cat.model.Parroquia;
import com.dadoco.cat.model.Zona;
import com.dadoco.cat.model.ZonaPK;
import com.dadoco.cat.service.ParroquiaService;
import com.dadoco.cat.service.ZonaService;
import com.dadoco.common.controller.UtilController;
import com.dadoco.common.util.Util;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "zonaView")
@ViewScoped
public class ZonasViewController extends UtilController<Zona> implements Serializable {

    @EJB
    private ZonaService zonaService;
    @EJB
    private ParroquiaService parroquiaService;

    private List<SelectItem> parroquias;

    public ZonasViewController() {
        super(Zona.class);
    }

    @PostConstruct
    public void inicializar() {
        this.setService(zonaService);
    }

    @Override
    protected void eliminarLogico() {
        throw new UnsupportedOperationException("Opcion no Soprtada");
    }

    @Override
    protected void guardar() throws Exception{
        if(this.zonaService.find(this.entidadSeleccionada.getZonaPK())==null){
              super.guardar();
        }else{
            throw new Exception("La zona "+this.entidadSeleccionada.getZonaPK().toString()+" ya Existe. Imposible guardar.");
        }
      
    }

    @Override
    protected void setearMensajes() {
        this.msgGuardar = "Se ha guardado correctamente la Zona : " + this.entidadSeleccionada.getNombre().toUpperCase();
        this.msgEditar = "Se ha editado correctamente la Zona : " + this.entidadSeleccionada.getNombre().toUpperCase();
        this.msgEliminar = "Se ha eliminado correctamente la Zona : " + this.entidadSeleccionada.getNombre().toUpperCase();
    }

    public List<SelectItem> getParroquias() {
        parroquias = new ArrayList<>();
        List<Parroquia> ps = parroquiaService.findAll();
        for (Parroquia p : ps) {
            parroquias.add(new SelectItem(p.getParroquiaPK().getCodParroquia(), "" + p.getParroquiaPK().getCodParroquia() + "-" + p.getNombre()));
        }
        return parroquias;
    }

    @Override
    public Zona getEntidadSeleccionada() {
        if (this.entidadSeleccionada == null) {
            this.entidadSeleccionada = new Zona(new ZonaPK());
            this.entidadSeleccionada.setAvaluoMinimo(BigDecimal.ZERO);
            this.entidadSeleccionada.getZonaPK().setCodProvincia(Util.provincia_por_defecto);
            this.entidadSeleccionada.getZonaPK().setCodCanton(Util.canton_por_defecto);
        }
        return this.entidadSeleccionada;
    }

}
