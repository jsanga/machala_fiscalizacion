/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import com.dadoco.cat.model.ValorDiscretoPK;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Dairon
 */
public class Util {

    static Gson createGsonFromBuilder(ExclusionStrategy exs) {
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.setExclusionStrategies(exs);
        return gsonbuilder.serializeNulls().create();
    }

    public static String jndi = "java:module/";// "java:global/sistema-1.0/";

    public static String moduleJNDI(String nameService) {
        return jndi + nameService;
    }
    public static String NUMBER_FORMAT_1 = "###,###.00";
// Configuracion de parametros por defecto que debe de tener el sistema para el funcionamiento.
    public static Integer ANIO_ACTUAL = (Calendar.getInstance().get(Calendar.YEAR));
    public static short ANIO_PROXIMO = (short) (Calendar.getInstance().get(Calendar.YEAR) + 1);
    public static short codigo_uso_terreno_default = (short) 12;
    public static int id_variable_uso_terreno = (short) 33;

    public static String provincia_por_defecto = "07";
    public static String canton_por_defecto = "01";
    public static String parroquia_por_defecto = "01";
    public static String RUC_MUNICIPIO = "1792568269001";

    public static String directorio_imagenes = "/opt/sistema/fotos";  // 1
    //public static String directorio_imagenes = "C:\\Users\\Dairon Freddy\\fotos";  // 1
    public static String directorio_documentos = "/opt/sistema/documentos";  // 2
    public static String directorio_datos_autorizacion = "/opt/sistema/datos_autorizacion";  // 3
    public static String directorio_fichas = "/opt/sistema/fichas_prediales";  // 4
    public static String directorio_ficha_escaneada = "/opt/sistema/fichas_escaneadas";  // 5
    public static String directorio_manzaneros = "/opt/sistema/manzaneros";  // 6
    public static String directorio_planos_solar = "/opt/sistema/planos";  // 7
    public static String directorio_incidencias = "/opt/sistema/fotosIncidencia";  // 8
    public static String directorio_reportes = "/opt/sistema/reportes";  // 9
    public static String directorio_tramites = "/opt/sistema/tramites";  //10

// configuracion para el envio de correos
    public static String email_host_name;
    public static String email_port;
    public static String email_user_host;
    public static String email_password_host;
    public static String email_to_send;

    public static void copyFile(UploadedFile uploadedFile, String fileName, List<UploadFile> files, String serverForlder, short tipoDir) {
        String uploadDir = localDir(tipoDir);

        try {
            InputStream inputStr = null;

            inputStr = uploadedFile.getInputstream();

            fileName += UUID.randomUUID() + "." + FilenameUtils.getExtension(uploadedFile.getFileName());
            File destFile = new File(new File(uploadDir), fileName);

            FileUtils.copyInputStreamToFile(inputStr, destFile);

            UploadFile file = new UploadFile();
            file.setFileName(fileName);
            file.setSavedFile(destFile);
            file.setIndex(files.size() + 1);
            files.add(file);

        } catch (Exception e) {

        }

        processFile(files, uploadDir, serverForlder, tipoDir);
    }

    public static String convertirStringDate(String date) {
        String s[] = date.split("-");
        String mes = "";

        switch (s[1]) {
            case "01":
                mes = "Enero";
                break;
            case "02":
                mes = "Febrero";
                break;
            case "03":
                mes = "Marzo";
                break;
            case "04":
                mes = "Abril";
                break;
            case "05":
                mes = "Mayo";
                break;
            case "06":
                mes = "Junio";
                break;
            case "07":
                mes = "Julio";
                break;
            case "08":
                mes = "Agosto";
                break;
            case "09":
                mes = "Septiembre";
                break;
            case "10":
                mes = "Octubre";
                break;
            case "11":
                mes = "Noviembre";
                break;
            case "12":
                mes = "Diciembre";
                break;
        }

        String fecha = s[2] + " de " + mes + " de " + s[0];

        return fecha;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static void processFile(List<UploadFile> files, String uploadRealDir, String serverForlder, short tipoDir) {

        String urlBase = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme() + "://"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerName() + ":"
                + FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()
                + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

        String file;
        String pathToFile = "";

        if (!files.isEmpty()) {

            String uploadDir = uploadRealDir;

            String tempDir = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/" + serverForlder;

            for (UploadFile f : files) {

                file = f.getFileName();

                try {
                    if (existeArchivo(uploadDir + "/" + file)) {
                        FileInputStream inFile = new FileInputStream(uploadDir + "/" + file);
                        FileOutputStream outFile = new FileOutputStream(tempDir + "/" + file);

                        try {
                            IOUtils.copy(inFile, outFile);
                            inFile.close();
                            outFile.close();
                        } catch (IOException ex) {

                        }
                    }

                    if (existeArchivo(tempDir + "/" + file)) {
                        pathToFile = urlBase + "/" + serverForlder + "/" + file;
                        f.setPathToFile(pathToFile);
                    } else if (tipoDir == 1) {

                        pathToFile = urlBase + "/" + serverForlder + "/nofoto.jpg";
                        f.setPathToFile(pathToFile);
                    }

                } catch (FileNotFoundException ex) {

                }
            }

        }
    }

    public static boolean existeArchivo(String nombreArchivo) {
        if (!nombreArchivo.equals("")) {

            File archivo = new File(nombreArchivo);

            if (archivo.exists()) {
                return true;
            }
        }

        return false;
    }

    public static void deleteFile(int index, List<UploadFile> files) {
        files.remove(index);
        for (int i = 0; i < files.size(); i++) {
            files.get(i).setIndex(i + 1);
        }
    }

    public static String localDir(short tipoDir) {
        String uploadDir;
        switch (tipoDir) {
            case 1:
                uploadDir = directorio_imagenes;
                break;
            case 2:
                uploadDir = directorio_documentos;
                break;
            case 3:
                uploadDir = directorio_datos_autorizacion;
                break;
            case 4:
                uploadDir = directorio_fichas;
                break;
            case 5:
                uploadDir = directorio_ficha_escaneada;
                break;
            case 6:
                uploadDir = directorio_manzaneros;
                break;
            case 7:
                uploadDir = directorio_planos_solar;
                break;
            case 10:
                uploadDir = directorio_tramites;
                break;
            default:
                uploadDir = directorio_documentos;
                break;
        }

        return uploadDir;
    }

    public static ValorDiscretoPK discretoPKbyString(String value) {
        String[] clave = value.split("-");
        ValorDiscretoPK pk = new ValorDiscretoPK();
        pk.setIdVariable(Integer.parseInt(clave[0]));
        pk.setValor(Short.parseShort(clave[1]));
        pk.setAnio(Short.parseShort(clave[2]));

        return pk;
    }

    public static short obtenerAnio(Date date) {

        if (null == date) {

            return 0;

        } else {

            String formato = "YYYY";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Short.parseShort(dateFormat.format(date));

        }

    }

    public static short obtenerMes(Date date) {

        if (null == date) {

            return 0;

        } else {

            String formato = "MM";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Short.parseShort(dateFormat.format(date));

        }

    }

    public static short obtenerDia(Date date) {

        if (null == date) {

            return 0;

        } else {

            String formato = "dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Short.parseShort(dateFormat.format(date));

        }

    }

    public static String numberToString(double number) {

        String result = "";

        Map<Integer, String> millar = new HashMap<>();
        millar.put(1, "MIL");
        millar.put(2, "DOS MIL");
        millar.put(3, "TRES MIL");
        millar.put(4, "CUATRO MIL");
        millar.put(5, "CINCO MIL");
        millar.put(6, "SEIS MIL");
        millar.put(7, "SIETE MIL");
        millar.put(8, "OCHO MIL");
        millar.put(9, "NUEVE MIL");

        Map<Integer, String> centenas = new HashMap<>();
        millar.put(1, "CIEN");
        millar.put(2, "DOSCIENTOS");
        millar.put(3, "TRESCIENTOS");
        millar.put(4, "CUATROCIENTOS");
        millar.put(5, "QUINIENTOS");
        millar.put(6, "SEISCIENTOS");
        millar.put(7, "SETECIENTOS");
        millar.put(8, "OCHOCIENTOS");
        millar.put(9, "NOVECIENTOS");

        Map<Integer, String> decenas = new HashMap<>();
        millar.put(1, "DIEZ");
        millar.put(2, "VEINTE");
        millar.put(3, "TREINTA");
        millar.put(4, "CUARENTA");
        millar.put(5, "CINCUENTA");
        millar.put(6, "SESENTA");
        millar.put(7, "SETENTA");
        millar.put(8, "OCHENTA");
        millar.put(9, "NOVENTA");

        Map<Integer, String> unidades = new HashMap<>();
        millar.put(1, "DIEZ");
        millar.put(2, "VEINTE");
        millar.put(3, "TREINTA");
        millar.put(4, "CUARENTA");
        millar.put(5, "CINCUENTA");
        millar.put(6, "SESENTA");
        millar.put(7, "SETENTA");
        millar.put(8, "OCHENTA");
        millar.put(9, "NOVENTA");

        return result;
    }

    /**
     * @author Ing.CarlosLoorVargas
     *
     */
    public static String setFile2B64(File f) {
        try {
            InputStream st = new FileInputStream(f);
            byte[] bts = new byte[(int) f.length()];
            int min = 0;
            int iterations = 0;
            while (min < bts.length && (iterations = st.read(bts, min, bts.length - min)) >= 0) {
                min += iterations;
            }
            if (min < bts.length) {
                return "";
            }
            st.close();
            byte[] resb = Base64.getEncoder().encode(bts);
            if (resb != null) {
                return new String(resb);
            }
        } catch (Exception e) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Object getValueFromExpretion(String exp) {
        try {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            return engine.eval(exp);
        } catch (Exception e) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
