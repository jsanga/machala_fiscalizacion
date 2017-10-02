/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dadoco.common.customFilters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase para conectarnos por medio de JDB a bases de datos externas de las
 * cuales hacemos pequeñas consultas
 * @author Fernando
 */
public class Conexion {

    private Connection conn;

  
    /**
     * Conexion a la base de datos
     */
    public Connection conexion() {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/machala_dev";
            String user = "postgres";
            String password = "postgres";
            conexion = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error1 en la Conexión con la BD " + ex.getMessage());
            conexion = null;
        } catch (SQLException ex) {
            System.err.println("Error1 en la Conexión con la BD " + ex.getMessage());
            conexion = null;
        } catch (Exception ex) {
            System.err.println("OtroError  en la Conexión con la BD " + ex.getMessage());
            conexion = null;
        } finally {
            conn = conexion;
        }
        return conexion;
    }
  

    /**
     * Método para ejecutar una sentencia SQL
     * @param consulta
     * @param parametros
     * @throws SQLException 
     */
    public void ejecutarSQL(String consulta, Map<String, Object> parametros) throws SQLException {
        PreparedStatement ps = null;
        ps = conn.prepareStatement(consulta);
        int cont = 1;
        if (parametros != null) {
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                Object object = entry.getValue();
                ps.setObject(cont, object);
                cont++;
            }
        }
        ps.executeUpdate();
    }

    /**
     * Método para ejecutar una sentencia SQL
     * @param consulta
     * @param parametros
     * @throws SQLException 
     */
    public void ejecutarSQL(String consulta, Object... parametros) throws SQLException {
        PreparedStatement ps = null;
        ps = conn.prepareStatement(consulta);
        if (parametros != null) {
            for (int cont = 0; cont < parametros.length; cont++) {
                ps.setObject((cont + 1), parametros[cont]);
            }
        }
        ps.executeUpdate();
    }

    /**
     * Método para ejecutar una sentencia SELECT SQL
     * @param consulta
     * @param parametros
     * @return
     * @throws SQLException 
     */
    public ResultSet ejecutarSQLSelect(String consulta,Object... parametros) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(consulta);
         if (parametros != null) {
            for (int cont = 0; cont < parametros.length; cont++) {
                ps.setObject((cont + 1), parametros[cont]);
            }
         }
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Método para ejecutar una sentencia SELECT SQL
     * @param consulta
     * @param parametros
     * @return
     * @throws SQLException 
     */
    public ResultSet ejecutarSQLSelect(String consulta, Map<String, Object> parametros) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(consulta);
        int cont = 1;
        if (parametros != null) {
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                Object object = entry.getValue();
                ps.setObject(cont, object);
                cont++;
            }
        }
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    /**
     * Método para cerrar la conexión con la base de datos
     */
    public void cerrarConexion() {
        try {
            if (!this.conn.isClosed()) {
                this.conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
