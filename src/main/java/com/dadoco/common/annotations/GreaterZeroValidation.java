/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author dfcalderio
 */
public class GreaterZeroValidation implements ConstraintValidator<GreaterZero, Object>{

    @Override
    public void initialize(GreaterZero a) {
       
    }

    @Override
    public boolean isValid(Object t, ConstraintValidatorContext cvc) {
        if(t == null)
            return true;
        
         return Float.valueOf(t.toString()) > 0;
    }

//    @Override
//    public void initialize(GreaterZero constraintAnnotation) {
//        
//    }
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        
//        Float valor = Float.valueOf(value);
//        
//        System.out.println("valor desde el validador: " + value);
//        return valor > 0;
//    }


}
