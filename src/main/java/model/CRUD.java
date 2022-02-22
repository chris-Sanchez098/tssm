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
        ObservableList<User> userList = FXCollections.observableArrayList();
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
                if(!userList.contains(user)) {
                    userList.add(user);
                }
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    /**
     * Get the Customer from the database
     * @param id user to search
     * @return ObservableList<Customer>
     */
    public static ObservableList<Customer> getCustomer(String id) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String query;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            if(id.isEmpty()) {
                query = "SELECT c.customer_id, cc, name, email, address_id, cust_type_id, phone_plan_id FROM customer c " +
                        "limit 22;";
            } else {
                query = "SELECT c.customer_id, cc, name, email, address_id, cust_type_id, phone_plan_id FROM customer c " +
                        "where cc like '" + id + '%'+ "';";
            }
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                int customer_id = result.getInt("customer_id");
                String cc = result.getString("cc");
                String name = result.getString("name");
                String email = result.getString("email");
                int address_id = result.getInt("address_id");
                int cust_type_id = result.getInt("cust_type_id");
                int phone_plan_id = result.getInt("phone_plan_id");
                Customer customer = new Customer(customer_id,cc,name,email,address_id,cust_type_id,phone_plan_id);
                if(!customerList.contains(customer)) {
                    customerList.add(customer);
                }
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customerList;
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
     * @param fkPlane to insert
     * @param dateTime to insert
     *
     */
    public static void insertCustomer(String name, String cc, String email, String add,
                                    String city, String dpto, String typeCli, String fkPlane,
                                    String dateTime, String serv) {

        String fkAdd = insertAddress(add, city, dpto);
        String fkTypeCli = insertTypeClient(typeCli);
        String fkClient = insertClient(cc, name, email, fkAdd, fkTypeCli, fkPlane);
        String fkPhoneNum = insertPhoneNumber(fkClient);
        String fkPeriod = insertPeriod(dateTime);
        insertPayment(fkClient, fkPeriod);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Registro de cliente");
        alert.setContentText("El cliente " + name + " fué registrado con exito y su nùmero " +
                "de celular es " + fkPhoneNum);
        alert.showAndWait();
    }

    /**
     * insert a Address into database
     * @param add to insert
     * @param city to insert
     * @param dpto to insert
     */
    public static String insertAddress(String add, String city, String dpto) {
        String fkAdd = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO address (st_address, city, departament) " +
                    "VALUES('" +add+ "','" +city+ "', '" +dpto+ "');";
            st.execute(query);

            query = "SELECT * FROM address ORDER BY address_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fkAdd = result.getString("address_id");
            }
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertAddress: " + e.getMessage());
        }
        return fkAdd;
    }

    /**
     * Insert a type_cliente into database
     * @param typeCli to insert
     */
    public static String insertTypeClient(String typeCli){
        String fkTypeCli = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO customer_type (cust_type) " +
                    "VALUES('" +typeCli+ "');";
            st.execute(query);

            query = "SELECT * FROM customer_type ORDER BY cust_type_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fkTypeCli = result.getString("cust_type_id");
            }

            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertTypeClient: "+ e.getMessage());
        }
        return fkTypeCli;
    }

    public static String insertClient(String cc, String name, String email,
                                    String fkadd, String fkTypeCli, String fkPlane){
        String fkClient = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO customer (cc, name, email, address_id, cust_type_id, phone_plan_id) " +
                            "VALUES('" +cc+"', '" +name+ "','" +email+ "', '" +fkadd+ "', " +
                            "'" +fkTypeCli+ "', '" +fkPlane+"')";
            st.execute(query);

            query = "SELECT * FROM customer ORDER BY customer_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fkClient = result.getString("customer_id");
            }
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertClient: " + e.getMessage());
        }

        return fkClient;
    }

    public static String insertPhoneNumber(String fkClient){
        String fkPhoneNum = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO phone_number (customer_id) " +
                    "VALUES('" +fkClient+"')";
            st.execute(query);

            query = "SELECT * FROM phone_number ORDER BY number_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fkPhoneNum = result.getString("number_id");
            }
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertPhoneNumber: " + e.getMessage());
        }
        return fkPhoneNum;
    }

    public static String insertPeriod(String dateTimeInit){
        String fkPeriod = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO period (start_p, end_p) " +
                    "VALUES('"+dateTimeInit+"', '" +dateTimeInit+"')";
            st.execute(query);

            query = "SELECT * FROM period ORDER BY period_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fkPeriod = result.getString("period_id");
            }
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertPeriod: " + e.getMessage());
        }
        return fkPeriod;
    }

    public static void insertPayment(String fkClient, String fkPeriod){
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();

            String query = "SELECT * FROM customer cs JOIN " +
                    "phone_plan pp ON cs.phone_plan_id = pp.phone_plan_id " +
                    "ORDER BY customer_id DESC LIMIT 1";

            String basicPay = null;
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                basicPay = result.getString("price");
            }

            StringBuilder price = new StringBuilder(basicPay);
            price = price.deleteCharAt(0);
            price = price.deleteCharAt(2);

            String pc = price.toString();
            double dbl = Float.parseFloat(pc) * 0.19;
            String tax = String.valueOf(dbl);
            int extra = 0;
            query = "INSERT INTO payment (basic_pay, extra_pay_min, extra_pay_data, taxes, " +
                    "customer_id, period_id) " +
                    "VALUES('" +price+ "', '" +extra+ "', '" +extra+ "', '"+tax+"', " +
                    "'"+fkClient+"', '" +fkPeriod+"')";
            st.execute(query);

            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertPayment: " + e.getMessage());
        }
    }
}
