package model;

import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.Statement;

public class CRUD extends ConexionDB {
    /**
     * Inserta un usuario a la base de datos
     * @param cc cedula
     * @param name nombre
     * @param user usuario
     * @param pwd contraseña
     * @param rol cargo
     * @param estado estado
     */
    public static void insertUser(int cc, String name, String user,
                                  String pwd, String rol, Boolean estado) {
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO usuarios (cc, nombre, usuario, clave, rol, estado) " +
                    "VALUES('" +cc+ "','" +name+ "','" +user+ "','" +pwd+ "','" +rol+"','"+estado+"');";
            st.execute(query);
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Actualiza un usuario de acuerdo
     * @param info Información necesaria
     */
    public void updateUser(String info) {
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "";
            st.executeUpdate(query);
            st.close();
            connection.close();

        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Desactiva un usuario
     * @param cc cedula
     */
    public void disableUser(int cc) {
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "DELETE FROM usuarios where cc='"+cc+"';";
            st.executeUpdate(query);
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
