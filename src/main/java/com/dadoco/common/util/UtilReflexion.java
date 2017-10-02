/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.lang.reflect.Field;

/**
 *
 * @author fernando
 */
public class UtilReflexion {

    public static Class<?> getTipoCampo(Class clase, String campo) throws NoSuchFieldException {
        String[] infoCampo = campo.split("\\.");
        Field field = null;
        Class claseAuxiliar=clase;
        for (String c : infoCampo) {
            field=claseAuxiliar.getDeclaredField(c);
            claseAuxiliar=field.getType();
        }
        return field.getType();
    }
    
    public static Object getObjectCast(Class tipo,Object obj){
        if(tipo.equals(Short.class) || tipo.equals(short.class)){
            return Short.parseShort(obj.toString());
        }
        return obj;
    }
}
