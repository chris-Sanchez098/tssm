package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import model.ChartsQuerys;

import java.net.URL;
import java.util.ResourceBundle;

public class ChartsController implements Initializable {

    @FXML
    private BarChart userChart;

    @FXML
    private BarChart plansChart;

    @FXML
    private CategoryAxis xAxisUser;

    @FXML
    private CategoryAxis xAxisPlan;

    private XYChart.Series<String, Number> rolesOn;
    private XYChart.Series<String, Number> rolesOff;
    private XYChart.Series<String, Number> plans;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBarChartUser();
        createBarChartPlan();
    }


    /**
     *
     * @param data observable list with the data
     * @return observableList with the Y axis
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
     * build BarChart
     */
    private void createBarChartUser(){
        rolesOn = ChartsQuerys.getRoles(true);
        rolesOff = ChartsQuerys.getRoles(false);
        rolesOn.setName("Habilitados");
        rolesOff.setName("Inhabilitados");
        xAxisUser.setCategories(getYaxis(rolesOn.getData()));
        userChart.getData().addAll(rolesOn,rolesOff);

    }

    private void createBarChartPlan(){
        plans = ChartsQuerys.getPlans();
        plansChart.getData().addAll(plans);

    }

    @FXML
    private void updateEvent(ActionEvent event){
        userChart.getData().removeAll(rolesOn,rolesOff);
        createBarChartUser();
    }
}
