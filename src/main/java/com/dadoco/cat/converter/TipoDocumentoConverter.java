/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.cat.converter;

import com.dadoco.cat.service.TipoDocumentoService;
import com.dadoco.cat.model.TipoDocumento;
import com.dadoco.common.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Dairon
 */
@FacesConverter(value = "tipoDocumentoConverter", forClass = TipoDocumento.class)
public class TipoDocumentoConverter implements Converter {

    private TipoDocumentoService tipoDocumentoService;

    public TipoDocumentoConverter() {
        try {

            tipoDocumentoService = (TipoDocumentoService) new InitialContext().lookup(Util.moduleJNDI("TipoDocumentoService"));
        } catch (NamingException ex) {
            Logger.getLogger(BloqueConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        if (value == null) {

            throw new NullPointerException("Trying to getAsObject with a null value.");
        }
        Long id = null;
        try {

            id = Long.parseLong(value);

        } catch (NumberFormatException e) {

            throw new ConverterException("Trying to getAsObject with a wrong id format.");
        }

        try {
            TipoDocumento tipo = tipoDocumentoService.find(id);
            return tipo;
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
        }

    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((TipoDocumento) object).getId());
        } else {
            return null;
        }
    }

    public TipoDocumentoService getTipoDocumentoService() {
        return tipoDocumentoService;
    }

    public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

}
