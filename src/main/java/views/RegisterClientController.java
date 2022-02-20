package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterClientController implements Initializable {
    @FXML
    public TextField txtNameOp;
    @FXML
    public TextField txtIdentity;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtAddress;
    @FXML
    public TextField txtDate;
    @FXML
    public TextField txtTime;
    @FXML
    public TextField txtService;
    @FXML
    public TextField txtPhoneNumber;
    @FXML
    public TextField txtPayment;
    @FXML
    public TextField txtMinutes;
    @FXML
    public TextField txtMinutesUnlimited;
    @FXML
    public TextField txtData;
    @FXML
    public TextField txtGbCloud;
    @FXML
    public TextField txtGbShare;
    @FXML
    public TextField txtMsmUnlimited;
    @FXML
    public TextField txtNetflix;
    @FXML
    public TextArea txtAreaMoreInfo;
    @FXML
    public Button btnRegisterClient;
    @FXML
    public Button btnCancelRegister;
    @FXML
    public ComboBox<String> comboxSelectClient;
    @FXML
    public ComboBox<String> comboxSelectPlan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CBSelectClient();
        CBSelectPlan();
    }

    @FXML
    public void clickSelectClient(ActionEvent actionEvent) {

    }

    @FXML
    public void clickSelectPlan(ActionEvent actionEvent) {
    }

    @FXML
    public void clickRegisterClient(ActionEvent actionEvent) {
    }

    @FXML
    public void clickCancelRegister(ActionEvent actionEvent) {
    }

    public void CBSelectClient() {
        ObservableList<String> option =
                FXCollections.observableArrayList("", "Natural", "Corporativo");
        comboxSelectClient.setItems(option);
        comboxSelectClient.getSelectionModel().selectFirst();
    }

    public void CBSelectPlan() {
        ObservableList<String> option =
                FXCollections.observableArrayList("", "Plan 15 GB", "Plan 25 GB", "Plan 40 GB", "Plan ilimitado");
        comboxSelectPlan.setItems(option);
        comboxSelectPlan.getSelectionModel().selectFirst();
    }

    /**
     * Reset GUI state
     */
    public void cleanGUI() {
        txtNameOp.setText("");
        txtIdentity.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        comboxSelectClient.getSelectionModel().selectFirst();
        comboxSelectPlan.getSelectionModel().selectFirst();

    }
}
