/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;

public class ZipUtil {

    public static InputStream crearZip(List<String> listaRutas, String nombreArchivo,String rutaTemporal) throws FileNotFoundException, IOException {
        String zipPath = rutaTemporal+File.separator+ nombreArchivo;
        File dir = new File(zipPath);
        if (!dir.exists()) {
            dir.createNewFile();
        }
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(dir));
        for (String ruta : listaRutas) {
            File a = new File(ruta);
            ZipEntry entrada = new ZipEntry(a.getName());
            FileInputStream in = new FileInputStream(a);
            out.putNextEntry(entrada);
            byte[] b = new byte[(int) a.length()];
            in.read(b);
            out.write(b);
            in.close();
            out.closeEntry();
        }
        out.close();
        return new FileInputStream(dir);
    }
//    
//    public static InputStream crearZip(List<String> listaRutas,ViewDocumento[] docs, String nombreArchivo) throws FileNotFoundException, IOException {
//        String serverPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/temporal/Doc.txt").replace(File.separator+"build", "");
//        String zipPath = serverPath.replace("Doc.txt", "") + nombreArchivo;
//        File dir = new File(zipPath);
//        if (!dir.exists()) {
//            dir.createNewFile();
//        }
//        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(dir));
//        for (String ruta : listaRutas) {
//            File a = new File(serverPath.replace(File.separator+"temporal"+File.separator+"Doc.txt", "") + ruta);
//            ZipEntry entrada = new ZipEntry(a.getName());
//            FileInputStream in = new FileInputStream(a);
//            out.putNextEntry(entrada);
//            byte[] b = new byte[(int) a.length()];
//            in.read(b);
//            out.write(b);
//            in.close();
//            out.closeEntry();
//        }
//        out.close();
//        return new FileInputStream(dir);
//    }

    public static File comprimirZIP(String Directorio, String nombre, List<File> listado) throws IOException {
        File dir = new File(Directorio + File.separatorChar + "tmp");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File archivoZip = new File(Directorio + File.separatorChar + "tmp" + nombre);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(archivoZip));
        for (File a : listado) {
            ZipEntry entrada = new ZipEntry(a.getName());
            FileInputStream in = new FileInputStream(a);
            out.putNextEntry(entrada);
            byte[] b = new byte[(int) a.length()];
            in.read(b);
            out.write(b);
            in.close();
            out.closeEntry();
        }
        out.close();
        return archivoZip;
    }
}
