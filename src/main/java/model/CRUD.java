package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.ResultSet;
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
    public static void insertUser(String cc, String name, String user,
                                  String pwd, String rol, Boolean estado) {
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO usuarios (cc, nombre, usuario, clave, rol, estado) " +
                    "VALUES('" +cc+ "','" +name+ "','" +user+ "','" +pwd+ "','" +rol+"','"+estado+"');";
            st.execute(query);
            st.close();
            connection.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Creación usuario");
            alert.setContentText("El usuario "+ user + " fue creado con exito!!");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Creación usuario");
            if(e.getMessage().subSequence(55,67).equals("usuarios_pke")) {
                alert.setContentText("El numero de identifiación " + cc + " ya existe.");
            } else {
                System.out.println(e.getMessage().subSequence(55,67).equals("usuarios_pke"));
                System.out.println(e.getMessage().subSequence(55,67));
                alert.setContentText("El usuario " + user + " ya existe.");
            } alert.showAndWait();
        }
    }

    /**
     * Actualiza un usuario de acuerdo
     * @param user User with date to update
     * @param cc Current user cc
     */
    public static void updateUser(User user, String cc) {
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "UPDATE usuarios SET cc = '" + user.getCc() + "',nombre = '" + user.getName() + "',usuario = '" + user.getUser() +
                    "',clave = '" + user.getPwd() + "',rol = '"+ user.getRol() + "',estado = '" + user.getStatus()
                    + "' WHERE cc = '" + cc + "';";
            st.executeUpdate(query);
            st.close();
            connection.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Modificación de usuario");
            alert.setContentText("El usuario con cédula"+ cc + " fue modificado");
            alert.showAndWait();
        } catch (Exception e ) {
            if(e.getMessage().subSequence(0,16).equals("ERROR: duplicate")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Modificación usuario");
                alert.setContentText("La cédula " + user.getCc() + " o el usuario "+ user.getUser() +" ya existe.");
                alert.showAndWait();
            } else {
                System.out.println(e.getMessage());
            }
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

    public static User selectUser(String cc) {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "SELECT u.nombre, u.usuario, u.rol, u.clave, u.estado FROM usuarios u where cc ='" + cc + "';";
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                String name = result.getString("nombre");
                String userName = result.getString("usuario");
                String rol = result.getString("rol");
                Boolean status = result.getBoolean("estado");
                String pwd = result.getString("clave");
                User user = new User( cc , name, userName, pwd, rol, status);
                userObservableList.add(user);
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userObservableList.get(0);
    }

}
