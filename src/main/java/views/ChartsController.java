package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import model.ChartsQuery;

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
    private LineChart salesChart;

    @FXML
    private CategoryAxis xAxisUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildBarChartUser();
        buildBarChartPlan();
        buildPieChartPay();
        buildLineChartSales();
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
        XYChart.Series<String, Number> rolesOn = ChartsQuery.getRoles(true);
        XYChart.Series<String, Number> rolesOff = ChartsQuery.getRoles(false);
        rolesOn.setName("Habilitados");
        rolesOff.setName("Inhabilitados");
        xAxisUser.setCategories(getYaxis(rolesOn.getData()));
        userChart.getData().addAll(rolesOn,rolesOff);
    }
    /**
     * build BarChart from attribute plan in customer
     */
    private void buildBarChartPlan(){
        XYChart.Series<Number, String> plans = ChartsQuery.getPlans();
        plans.setName("Plan de telefonía movíl");
        plansChart.getData().add(plans);
    }


    /**
     * build BarChart from attribute payAccepted in pay
     */
    private void buildPieChartPay(){
        ObservableList<PieChart.Data> list = ChartsQuery.getPercent();
        payChart.getData().addAll(list);
    }

    /**
     * build BarChart from attribute date in customerCreate
     */
    private void buildLineChartSales(){
        XYChart.Series<String, Number> sales = ChartsQuery.getSales();
        sales.setName("Ventas últimos 12 meses");
        salesChart.getData().add(sales);
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
        buildLineChartSales();
    }

    /**
     * clean charts from old data
     */
    private void removeSeries(){
        userChart.getData().remove(0,2);
        plansChart.getData().remove(0);
        payChart.getData().remove(0,3);
        salesChart.getData().remove(0);
    }
}

