package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import model.ChartsQuerys;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartsController implements Initializable {

    @FXML
    private BarChart userChart;

    @FXML
    private BarChart plansChart;

    @FXML
    private PieChart payChart;

    @FXML
    private LineChart customerChart;

    @FXML
    private CategoryAxis xAxisUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildBarChartUser();
        buildBarChartPlan();
        buildPieChartPay();
        buildLineChartCustomer();
    }


    /**
     *
     * @param data observableList with the data
     * @return observableList with the strings Y axis
     */
    private ObservableList<String> getYaxis(ObservableList<XYChart.Data<String, Number>> data){
        ObservableList<String> list = FXCollections.observableArrayList();
        int i = 0;
        while (i < data.size()){
            list.add(data.get(i).getXValue());
            i++;
        }
        return list;
    }

    /**
     * build BarChart from attribute rol in user
     */
    private void buildBarChartUser(){
        XYChart.Series<String, Number> rolesOn = ChartsQuerys.getRoles(true);
        XYChart.Series<String, Number> rolesOff = ChartsQuerys.getRoles(false);
        rolesOn.setName("Habilitados");
        rolesOff.setName("Inhabilitados");
        xAxisUser.setCategories(getYaxis(rolesOn.getData()));
        userChart.getData().addAll(rolesOn,rolesOff);
    }
    /**
     * build BarChart from attribute plan in customer
     */
    private void buildBarChartPlan(){
        XYChart.Series<Number, String> plans = ChartsQuerys.getPlans();
        plans.setName("Plan de telefonía movíl");
        plansChart.getData().add(plans);
    }


    /**
     * build BarChart from attribute payAccepted in pay
     */
    private void buildPieChartPay(){
        payChart.getData().addAll(
          new PieChart.Data("Pagados", 54),
          new PieChart.Data("Sin pagar", 46)
        );
    }

    /**
     * build BarChart from attribute date in customerCreate
     */
    private void buildLineChartCustomer(){
        XYChart.Series<String, Number> customers = new XYChart.Series<>(
                FXCollections.observableArrayList(
                        new XYChart.Data<>("Enero", 49),
                        new XYChart.Data<>("Febrero", 51),
                        new XYChart.Data<>("Marzo", 63),
                        new XYChart.Data<>("Abril", 54)
                )
        );
        customers.setName("Ventas por mes");
        customerChart.getData().add(customers);
    }

    /**
     * update the charts
     * @param event the event
     */
    @FXML
    private void updateEvent(ActionEvent event){
        removeSeries();
        buildBarChartUser();
        buildBarChartPlan();
        buildPieChartPay();
        buildLineChartCustomer();
    }

    /**
     * clean charts from old data
     */
    private void removeSeries(){
        userChart.getData().remove(0,2);
        plansChart.getData().remove(0);
        payChart.getData().remove(0,2);
        customerChart.getData().remove(0);
    }
}

