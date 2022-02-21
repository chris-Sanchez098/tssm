package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CRUD extends ConexionDB {
    /**
     * Insert a user into database
     * @param cc to insert
     * @param name to insert
     * @param user to insert
     * @param pwd to insert
     * @param rol to insert
     * @param estado to insert
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
            alertDuplicate(cc, user, e);
        }
    }

    /**
     * Update a user
     * @param user User with date to update
     * @param cc Current user cc
     */
    public static boolean updateUser(User user, String cc) {
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
            alert.setContentText("El usuario con cédula "+ cc + " fue modificado");
            alert.showAndWait();
            return true;
        } catch (Exception e ) {
            alertDuplicate(user.getCc(), user.getUser(), e);
        }
        return false;
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

    /**
     * search a user then get status, rol and password
     * @param user login user to search
     * @return user
     */
    public static User selectLogin(String user){

        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "SELECT u.rol, u.clave, u.estado FROM usuarios u where u.usuario ='" + user + "';";
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                String rol = result.getString("rol");
                String pwd = result.getString("clave");
                Boolean status = result.getBoolean("estado");
                User anUser = new User("", "", user, "", rol, status);
                anUser.setPwdNoEncrypt(pwd);
                userObservableList.add(anUser);
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(userObservableList.isEmpty()){
            userObservableList.add(new User("","", "", "", "", false));
        }
        return userObservableList.get(0);
    }

    /**
     * show an alert about duplicate
     * @param cc to show
     * @param user to show
     * @param e exception
     */
    private static void alertDuplicate(String cc, String user, Exception e){
        System.out.println(e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Modificación de usuario");
        if(e.getMessage().subSequence(55,67).equals("usuarios_pke")) {
            alert.setContentText("El numero de identifiación " + cc + " ya existe.");
        } else {
            alert.setContentText("El usuario " + user + " ya existe.");
        }
        alert.showAndWait();
    }

    /**
     * Get the users from the database
     * @param CC user to search
     * @return ObservableList<User>
     */
    public static ObservableList<User> getUsers(String CC) {
        ObservableList<User> userObservableList = FXCollections.observableArrayList();
        String query;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            if(CC.isEmpty()) {
                query = "SELECT u.cc, u.nombre, u.usuario, u.rol, u.clave, u.estado FROM usuarios u " +
                        "limit 22;";
            } else {
                query = "SELECT u.cc, u.nombre, u.usuario, u.rol, u.clave, u.estado FROM usuarios u " +
                        "where cc like '" + CC+ '%'+ "';";
            }
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                String cc = result.getString("cc");
                String name = result.getString("nombre");
                String userName = result.getString("usuario");
                String rol = result.getString("rol");
                Boolean status = result.getBoolean("estado");
                String pwd = result.getString("clave");
                User user = new User( cc , name, userName, "", rol, status);
                user.setPwdNoEncrypt(pwd);
                if(!userObservableList.contains(user)) {
                    userObservableList.add(user);
                }
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userObservableList;
    }

    /**
     * set status to false
     * @param user to set
     */
    public static void setFalseStatus(String user){
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "UPDATE usuarios SET estado = false WHERE usuario ='" + user + "';";
            st.executeUpdate(query);
            st.close();
            connection.close();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a Customer into database
     * @param name to insert
     * @param cc to insert
     * @param email to insert
     * @param add to insert
     * @param city to insert
     * @param dpto to insert
     * @param typeCli to insert
     * @param plane to insert
     * @param date to insert
     * @param time to insert
     */
    public static void insertCustomer(String name, String cc, String email, String add,
                                    String city, String dpto, String typeCli, String plane,
                                    String date, String time, String serv) {

        insertAddress(add, city, dpto);
        insertTypeClient(typeCli);
        //insertClient(name, email);
        //insertPhoneLines();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Registro de cliente");
        alert.setContentText("El cliente" + name + "fué registrado con exito");
        alert.showAndWait();
    }

    /**
     * insert a Address into database
     * @param add to insert
     * @param city to insert
     * @param dpto to insert
     */
    public static void insertAddress(String add, String city, String dpto){
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO street_address (st_address, city, departament) " +
                    "VALUES('" +add+ "','" +city+ "', '" +dpto+"');";
            st.execute(query);
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    /**
     * Insert a type_cliente into database
     * @param typeCli to insert
     */
    public static void insertTypeClient(String typeCli){
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO customer_type (cust_type) " +
                    "VALUES('" +typeCli+ "');";
            st.execute(query);
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    public static void insertClient(String name, String email){

    }

    public static void insertPhoneLines(){

    }
}
