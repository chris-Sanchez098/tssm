package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChartsQuery extends ConexionDB{
    /**
     * get series data when first is String and second is Int
     * @param query to db
     * @return data to a chart
     */
    private static XYChart.Series<String, Number> getDataSN(String query){
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                String columString = result.getString(1);
                int columInt = result.getInt(2);
                data.add(new XYChart.Data<>(columString, columInt));
            }
            result.close();
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        series = new XYChart.Series<>(data);
        return series;
    }
    /**
     * get series data when first is Int and second is String
     * @param query to db
     * @return data to a barChart
     */
    private static XYChart.Series<Number, String> getDataNS(String query){
        ObservableList<XYChart.Data<Number, String>> data = FXCollections.observableArrayList();
        XYChart.Series<Number, String> series;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                int columString = result.getInt(1);
                int columInt = result.getInt(2);
                data.add(new XYChart.Data<>(columInt, Integer.toString(columString)));
            }
            result.close();
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        series = new XYChart.Series<>(data);
        return series;
    }

    /**
     * get roles from usuarios
     * @param status is enabled in system
     * @return data to a chart
     */
    public static XYChart.Series<String, Number> getRoles(Boolean status){
        String query = "SELECT rol, COUNT(rol), estado " +
                "FROM usuarios " +
                "GROUP BY " +
                "rol,estado " +
                "HAVING estado = "+ status.toString() + " ;";
        return getDataSN(query);
    }

    /**
     * get plans from db
     * @return data to a chart
     */
    public static XYChart.Series<Number, String> getPlans(){
        String query = "SELECT phone_plan_id, COUNT(phone_plan_id) " +
                "FROM customer NATURAL JOIN phone_plan " +
                "GROUP BY " +
                "phone_plan_id;";
        return getDataNS(query);
    }
    /**
     * get sales from db
     * @return data to a chart
     */
    public static XYChart.Series<String, Number> getSales(){
        String query = "WITH table_dates as (SELECT TO_CHAR(date_create, 'YYYY-MM') as dates, COUNT(date_create) AS \"number of dates\"\n" +
                "FROM date_customer\n" +
                "GROUP BY dates\n" +
                "order by dates DESC\n" +
                "LIMIT 12)\n" +
                "SELECT * FROM table_dates\n" +
                "ORDER BY dates ASC;";
        return getDataSN(query);
    }

    /**
     * getPercent of pays from db
     * @return data to a pieChart
     */
    public static ObservableList<PieChart.Data> getPercent(){
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String query = "SELECT rol, ROUND(count(*) * 100 / u.total::decimal) percent\n" +
                "FROM usuarios\n" +
                "CROSS JOIN (SELECT count(*) AS total FROM usuarios) AS u\n" +
                "group by rol, u.total;";
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                String columString = result.getString(1);
                int columInt = result.getInt(2);
                data.add(new PieChart.Data(columString, columInt));
            }
            result.close();
            st.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

}
