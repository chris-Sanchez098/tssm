package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.CRUD;
import model.Consumption;
import model.Validation;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsumptionController implements Initializable {
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfMinutes;
    @FXML
    private TextField tfMsg;
    @FXML
    private TextField tfCloud;
    @FXML
    private TextField tfShare;
    @FXML
    private TextField tfData;
    @FXML
    private Button bSearch;
    @FXML
    private Button bConsult;
    @FXML
    private ComboBox<String> cbPhones;

    @FXML
    public void clickSearch(ActionEvent event) {
        String id = tfId.getText().toLowerCase().trim();
        if(event.getSource() == bSearch) {
            if(!id.isEmpty()) {
                ObservableList<String> data = CRUD.getPhoneNumber(id);
                cbPhones.setItems(data);
                cbPhones.getSelectionModel().selectFirst();
            }
        } else if (event.getSource() == bConsult) {
            String phone = cbPhones.getSelectionModel().getSelectedItem().toString();
            System.out.println(phone);
            if(!phone.isEmpty()) {
                Consumption consumption = CRUD.getData(id, phone);
                tfMinutes.setText(consumption.getMinutes() + "");
                tfMsg.setText(consumption.getMsg() + "");
                tfCloud.setText(consumption.getGbCloud() + " GB");
                tfShare.setText(consumption.getGbShare() + " GB");
                tfData.setText(consumption.getGbData() + " GB");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Consulta consumos");
            alert.setContentText("Verifique que el documento del cliente sea valido!");
            alert.showAndWait();
        }
    }

    @FXML
    public void cleanGUI(Event event) {
        tfId.setText("");
        tfMinutes.setText("");
        tfMsg.setText("");
        tfCloud.setText("");
        tfShare.setText("");
        tfData.setText("");
        cbPhones.setItems(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Validation.setOnlyNum(tfId);
    }
}
