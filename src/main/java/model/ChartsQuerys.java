package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChartsQuerys extends ConexionDB{

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

    private static XYChart.Series<Number, String> getDataNS(String query){
        ObservableList<XYChart.Data<Number, String>> data = FXCollections.observableArrayList();
        XYChart.Series<Number, String> series;
        try {
            Connection connection = connect();
            Statement st = connection.createStatement();
            ResultSet result = st.executeQuery(query);
            while (result.next()){
                Integer columString = result.getInt(1);
                Integer columInt = result.getInt(2);
                data.add(new XYChart.Data<>(columInt, columString.toString()));
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

    public static XYChart.Series<String, Number> getRoles(Boolean status){
        String query = "SELECT rol, COUNT(rol), estado " +
                "FROM usuarios " +
                "GROUP BY " +
                "rol,estado " +
                "HAVING estado = "+ status.toString() + " ;";
        return getDataSN(query);
    }

    public static XYChart.Series<Number, String> getPlans(){
        String query = "SELECT phone_plan_id, COUNT(phone_plan_id) " +
                "FROM customer NATURAL JOIN phone_plan " +
                "GROUP BY " +
                "phone_plan_id;";
        return getDataNS(query);
    }


}
