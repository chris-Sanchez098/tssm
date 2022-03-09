package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
            result.close();
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
            result.close();
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    /**
     * Get the Customer from the database
     * @param CC user to search
     * @return ObservableList<Customer>
     */
    public static ObservableList<Customer> getCustomer(String CC) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String query;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            if(CC.isEmpty()) {
                query = "SELECT * " +
                        "FROM customer cst NATURAL JOIN customer_type cst_ty NATURAL JOIN address ads " +
                        "NATURAL JOIN phone_plan pp NATURAL JOIN phone_number pn \n" +
                        "limit 20";
            } else {
                query = "SELECT * " +
                        "FROM customer cst NATURAL JOIN customer_type cst_ty NATURAL JOIN address ads " +
                        "NATURAL JOIN phone_plan pp NATURAL JOIN phone_number pn \n" +
                        "where cc like '" + CC + '%' +"';";
            }
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                String cc = result.getString("cc");
                String name = result.getString("name");
                String email = result.getString("email");
                int addressId = result.getInt("address_id");
                String address = result.getString("st_address");
                String city = result.getString("city");
                String department = result.getString("departament");
                int customerTypeId = result.getInt("cust_type_id");
                String customerType = result.getString("cust_type");
                int phone_plan_id = result.getInt("phone_plan_Id");
                String phoneNumber = result.getString("number_id" +"");
                double price = result.getDouble("price");
                String gbCloud = result.getString("gb_cloud");
                String gbShare = result.getString("gb_share");;
                boolean minutesUnLimited = result.getBoolean("unlimited_min");
                boolean msgUnLimited = result.getBoolean("unlimited_sms");;
                int minutes = result.getInt("minutes");
                int netflix = result.getInt("netflix");
                String details = result.getString("description");
                Customer customer = new Customer(cc,name,email,addressId,address,city,department,customerTypeId,
                        customerType,phone_plan_id,phoneNumber,price,gbCloud,gbShare,minutesUnLimited,msgUnLimited,
                        minutes,netflix,details);
                if(!customerList.contains(customer)) {
                    customerList.add(customer);
                }
            }
            result.close();
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
     * @param typeCust to insert
     * @param fk_plane to insert
     * @param dateTime to insert
     */
    public static boolean insertCustomer(String name, String cc, String email, String add,
                                    String city, String dpto, String typeCust, String fk_plane,
                                    String dateTime, String serv, String phoneNum) {
        try {
            String fk_add = insertAddress(add, city, dpto);
            String fk_TypeCust = selectTypeCustomer(typeCust);
            String fk_customer = insertClient(cc, name, email, fk_add, fk_TypeCust, fk_plane);
            insertPhoneNumber(phoneNum, cc);
            String fk_Period = insertPeriod(dateTime);
            insertPayment(fk_customer, fk_Period);
            return true;
        }catch (Exception e){
            System.out.println("Error en InsertCustomer: " + e);
            return false;
        }
    }

    /**
     * Insert a Customer into database
     * @param phoneNum to insert
     * @param cc to insert
     */
    public static void insertPhoneNumber(String phoneNum, String cc){
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO phone_number (number_id, customer_id) " +
                    "VALUES('" +phoneNum+"', '" +cc+ "')";
            st.execute(query);
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error en insertPhoneNumber: " + e.getMessage());
        }
    }

    /**
     * insert a Address into database
     * @param add to insert
     * @param city to insert
     * @param dpto to insert
     * @return fk_add
     */
    public static String insertAddress(String add, String city, String dpto) {
        String fk_add = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO address (st_address, city, departament) " +
                    "VALUES('" +add+ "','" +city+ "', '" +dpto+ "');";
            st.execute(query);

            query = "SELECT * FROM address ORDER BY address_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fk_add = result.getString("address_id");
            }
            result.close();
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertAddress: " + e.getMessage());
        }
        return fk_add;
    }

    /**
     * Select a type_cliente from database
     * @param custType to select
     * @return fk_CustType
     */
    public static String selectTypeCustomer(String custType){
        String fk_CustType = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "SELECT * FROM customer_type WHERE cust_type = '"+custType+"' ";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fk_CustType = result.getString("cust_type_id");
            }
            result.close();
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error en selectTypeClient: "+ e.getMessage());
        }
        return fk_CustType;
    }

    /**
     * Insert a Customer into database
     * @param cc to insert
     * @param name to insert
     * @param email to insert
     * @param fk_add to insert
     * @param fk_custType to insert
     * @param fk_plane to insert
     * @return fk_cc
     */
    public static String insertClient(String cc, String name, String email,
                                    String fk_add, String fk_custType, String fk_plane){
        String fk_cc = null;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO customer (cc, name, email, address_id, cust_type_id, phone_plan_id) " +
                            "VALUES('" +cc+"', '" +name+ "','" +email+ "', '" +fk_add+ "', " +
                            "'" +fk_custType+ "', '" +fk_plane+"')";
            st.execute(query);
            st.close();
            connection.close();
            fk_cc = cc;

        } catch (Exception e) {
            System.out.println("Error en insertClient: " + e.getMessage());
        }
        return fk_cc;
    }

    /**
     * Insert a Customer into database
     * @param dateTimeInit to insert
     * @return fk_period
     */
    public static String insertPeriod(String dateTimeInit){
        String fk_period = null;
        String dateTimeEnd = "Activo";
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO period (start_p, end_p) " +
                    "VALUES('"+dateTimeInit+"', '" +dateTimeInit+"')";
            st.execute(query);

            query = "SELECT * FROM period ORDER BY period_id DESC LIMIT 1";
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                fk_period = result.getString("period_id");
            }
            result.close();
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertPeriod: " + e.getMessage());
        }
        return fk_period;
    }

    /**
     * Insert a Customer into database
     * @param fk_cust to insert
     * @param fk_period to insert
     */
    public static void insertPayment(String fk_cust, String fk_period){
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();

            String query = "SELECT * FROM customer cs JOIN " +
                    "phone_plan pp ON cs.phone_plan_id = pp.phone_plan_id " +
                    "ORDER BY cc DESC LIMIT 1";

            double basicPay = 0.0;
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                basicPay = result.getDouble("price");
            }
            double tax = basicPay * 0.19;
            int extra = 0;
            query = "INSERT INTO payment (basic_pay, extra_pay_min, extra_pay_data, taxes, " +
                    "customer_id, period_id) " +
                    "VALUES('" +basicPay+ "', '" +extra+ "', '" +extra+ "', '"+tax+"', " +
                    "'"+fk_cust+"', '" +fk_period+"')";
            result.close();
            st.execute(query);
            st.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error en insertPayment: " + e.getMessage());
        }
    }

    /**
     * get information about a phone plan
     * @param planType to insert
     */
    public static ObservableList<String> getPhonePlan(int planType) {
        String price = null;
        String gb_data = null;
        String gb_cloud= null;
        String gb_share = null;
        String unlim_min = null;
        String unlim_sms = null;
        String minutes = null;
        String netflix = null;
        String descrip = null;
        ObservableList<String> phonePlanlist =
                FXCollections.observableArrayList(price, gb_data, gb_cloud, gb_share,
                        unlim_min, unlim_sms, minutes, netflix, descrip);

        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "SELECT * FROM phone_plan WHERE phone_plan_id = '"+planType+"' ";
            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                price = String.valueOf(result.getDouble("price"));
                gb_data = result.getString("gb_data");
                gb_cloud = result.getString("gb_cloud");
                gb_share = result.getString("gb_share");
                unlim_min = result.getString("unlimited_min");
                unlim_sms = result.getString("unlimited_sms");
                minutes = result.getString("minutes");
                netflix = result.getString("netflix");
                descrip = result.getString("description");
            }
            phonePlanlist = FXCollections.observableArrayList(price, gb_data, gb_cloud,
                    gb_share, unlim_min, unlim_sms, minutes, netflix, descrip);
            result.close();
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error en getPhonePlan: " + e.getMessage());
        }
        return phonePlanlist;
    }

    /**
     * get the description of a phone plan
     */
    public static String[] getDescriptionPhonePlan() {
        String[] descriptionPlans = new String[4];

        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "SELECT description FROM phone_plan";
            ResultSet result = st.executeQuery(query);

            int i = 0;
            while (result.next()) {
                descriptionPlans[i] = result.getString("description");
                i++;
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error en getDescriptionPhonePlan: " + e.getMessage());
        }
        return descriptionPlans;
    }

    /**
     * Insert consume into database
     * @param info ObservableList<String []>
     */
    public static void insertConsume(ObservableList<String[]> info)  {
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            info.forEach( (list) -> {
                String date_time = list[0];
                int minutes = Validation.parseInteger(list[1]);
                int msg =  Validation.parseInteger(list[2]);
                double gb_cloud = Validation.parseDouble(list[3]);
                double gb_Share = Validation.parseDouble(list[4]);
                double gb_data = Validation.parseDouble(list[5]);
                String phone_number_id = list[6];
                int period_id =  Validation.parseInteger(list[7]);
                String query = "INSERT INTO register_cust (date_time, minutes, msg, gb_cloud, gb_share, gb_data, " +
                        "phone_number_id, period_id)" + "VALUES ('"+date_time+"','"+minutes+"','"+msg+"','"+gb_cloud+"'," +
                        "'"+gb_Share+"','"+gb_data+"','"+phone_number_id+"','"+period_id+"')";
                try {
                    st.execute(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            st.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Vector<Payment> viewPayment(String userId) {
        Vector<Payment> dataPaymentList = new Vector<>();
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "SELECT name, customer_id, basic_pay, extra_pay_min, extra_pay_data, taxes  " +
                    "FROM payment py JOIN customer cs " +
                    "ON py.customer_id = cs.cc WHERE cs.cc = '"+userId+"'";

            ResultSet result = st.executeQuery(query);

            while (result.next()) {
                String name = result.getString("name");
                String customerId = result.getString("customer_id");
                String basicPay = result.getString("basic_pay");
                String extraPayMin = result.getString("extra_pay_min");
                String extraPayData = result.getString("extra_pay_data");
                String taxes = result.getString("taxes");
                Payment dataPayment = new Payment(name, customerId, basicPay, extraPayMin, extraPayData, taxes);
                dataPaymentList.add(dataPayment);
            }
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error en registerPayment: " + e.getMessage());
        }
        return dataPaymentList;
    }

    public static boolean registerPayment(String userId){
        /*no es userId, es payment_id*/
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            String query = "INSERT INTO pay (payment_processed, payment_id)" +
                    "VALUES ('"+true+"', '"+userId+"')";

            ResultSet result = st.executeQuery(query);
            st.close();
            connection.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en registerPayment: " + e.getMessage());
            return false;
        }
    }

    public static ObservableList<Sales> getSales(String id){
        ObservableList<Sales> salesList = FXCollections.observableArrayList();
        String query;
        try {
            if (id.isEmpty()) {
                query = "SELECT name, cc, phone_plan_id, date_create, cust_type, COUNT(number_id) AS \"lines\"\n" +
                        "FROM customer NATURAL JOIN customer_type \n" +
                        "NATURAL JOIN phone_number NATURAL JOIn date_customer\n" +
                        "group by cc, date_create, cust_type\n" +
                        "limit 20;";
            }
            else{
                query = "SELECT name, cc, phone_plan_id, date_create, cust_type, COUNT(number_id) AS \"lines\"\n" +
                        "FROM customer NATURAL JOIN customer_type \n" +
                        "NATURAL JOIN phone_number NATURAL JOIN date_customer\n" +
                        "WHERE cc = '" + id  +"';\n" +
                        "GROUP by cc, date_create, cust_type";
            }
            Connection connection = connect();
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                String name = result.getString(1);
                String cc = result.getString(2);
                String date = result.getString(4);
                String customerType = result.getString(5);
                int phonePlan = result.getInt(3);
                int lines = result.getInt(6);
                Sales sales = new Sales(date, cc, name, customerType, phonePlan, lines);
                salesList.add(sales);
            }
            result.close();
            st.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesList;
    }

}
