package com.mycompany.anime.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ImportarAnime {

    
    public static void main(String[] args) {
        
        try {
            Connection conexionBD = DriverManager.getConnection(
                    "jdbc:derby:BDAnime",
                    "APP", "");
            Statement sentenciaSQL = conexionBD.createStatement();
            
            String nombreFichero = "anime.csv";
            BufferedReader br = new BufferedReader(new FileReader(nombreFichero));
            String texto = br.readLine();
            texto = br.readLine();
            while(texto != null) {
                String[] lineaAni = texto.split(",");
                // Eliminar comillas de los datos
                String codigoAni = lineaAni[1].replace("\"", "");
                String nombreAni = lineaAni[2].replace("\"", "");
                // Realiza una consulta para ver si el estudio ya existe
                String select = "SELECT CODIGO FROM ANIME WHERE CODIGO='"+codigoAni+"'";
                ResultSet resultados = sentenciaSQL.executeQuery(select);
                // Si no se encuentran
                if(!resultados.next()) {
                    // Insertar nuevo estudio
                    String insert = "INSERT INTO ANIME(CODIGO, NOMBRE) "
                            + "VALUES ('"+codigoAni+"', '" + nombreAni + "')";
                    System.out.println(insert);
                    sentenciaSQL.executeUpdate(insert);
                }
                // Lee la siguiente linea del archivo
                texto = br.readLine();
            }
            // Cierra la conexión con la base de datos y cierra el archivo
            conexionBD.close();
            br.close();
        } catch (SQLException e) {
            System.out.println("Se ha procido un error al conectar con la base de datos");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error de lectura del fichero");
            e.printStackTrace();
        }
    }
    
}
