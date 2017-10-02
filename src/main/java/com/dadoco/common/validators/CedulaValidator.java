/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

/**
 *
 * @author Dairon
 */
@FacesValidator("dusa.CedulaValidator")
public class CedulaValidator implements Validator, ClientValidator {

    private Pattern pattern;
    private static final String CEDULA_PATTERN = "^[0-9]{10}+$";

    public CedulaValidator() {
        pattern = Pattern.compile(CEDULA_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        if (!validarCedula(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error",
                    value + " no es una cedula valida;"));
        }

    }

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public String getValidatorId() {
        return "dusa.CedulaValidator";
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    
    private boolean validarCedula(String cedula) {

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
        } else if (suma % 10 == 0 && cedula.charAt(cedula.length() - 1) == '0') {
            return true;
        } else {
            return false;
        }

    }
}
