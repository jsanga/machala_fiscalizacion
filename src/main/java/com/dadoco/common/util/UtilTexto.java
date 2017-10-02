/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author fernando
 */
public class UtilTexto {
    
    static SimpleDateFormat formatoUno=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static SimpleDateFormat formatoDos=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
    public static Date isDate(String fecha,String time){
        if(fecha == null){
            return null;
        }else{
            if(fecha.isEmpty()||fecha.contains("%")){
                return null;
            }
        }
        fecha=fecha.trim()+" "+time;
        try {
            return formatoUno.parse(fecha);
        } catch (ParseException ex) {
            try {
                return formatoDos.parse(fecha);
            } catch (ParseException ex1) {
               return null;
            }
        }
    }
    
}
