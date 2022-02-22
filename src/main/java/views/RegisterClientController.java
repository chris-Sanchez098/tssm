package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.CRUD;
import model.MD5;
import model.User;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegisterClientController implements Initializable {
    @FXML
    public TextField txtName;
    @FXML
    public TextField txtIdentity;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtAddress;
    @FXML
    public TextField txtCity;
    @FXML
    public TextField txtDepto;
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
    @FXML
    public Button btnCleanFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CBSelectClient();
        CBSelectPlan();
        setOnlyNum(txtIdentity);
    }

    @FXML
    public void clickSelectClient(ActionEvent actionEvent) {
    }

    @FXML
    public void clickSelectPlan(ActionEvent actionEvent) {
    }

    @FXML
    public void clickRegisterClient(ActionEvent actionEvent) {
        String name = txtName.getText();
        String cc = txtIdentity.getText().toLowerCase();
        String email = txtEmail.getText();
        String add = txtAddress.getText();
        String city = txtCity.getText();
        String dpto = txtDepto.getText();
        String typeCli = comboxSelectClient.getSelectionModel().getSelectedItem();
        String plane = comboxSelectPlan.getSelectionModel().getSelectedItem();
        String dateTime = dateTimeActual();
        String serv = txtService.getText();
        System.out.println(dateTime);
        ObservableList<String> list =
                FXCollections.observableArrayList(name, cc, email, add,
                        city, dpto, typeCli, plane, dateTime, serv);

        if(checkEmptyField(list) && !(typeCli == "Seleccionar") && !(plane == "Seleccionar")) {
            switch (plane) {
                case "Plan 15 GB":
                    plane = "1";
                    break;
                case "Plan 25 GB":
                    plane = "2";
                    break;
                case "Plan 40 GB":
                    plane = "3";
                    break;
                case "Plan ilimitado":
                    plane = "4";
                    break;
            }
            CRUD.insertCustomer(name, cc, email, add, city, dpto, typeCli, plane, dateTime, serv);
            cleanGUI();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Registro de cliente!");
            alert.setContentText("Verifique que todos los campos esten llenos.");
            alert.showAndWait();
        }
    }

    /**
     * Close the stage
     * @param actionEvent get the event
     */
    @FXML
    public void clickCancelRegister(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    /**
     * initializes the values in the comboBox comboxSelectClient
     */
    public void CBSelectClient() {
        ObservableList<String> option =
                FXCollections.observableArrayList("Seleccionar", "Natural", "Corporativo");
        comboxSelectClient.setItems(option);
        comboxSelectClient.getSelectionModel().selectFirst();
    }

    /**
     * initializes the values in the comboBox comboxSelectPlan
     */
    public void CBSelectPlan() {
        ObservableList<String> option =
                FXCollections.observableArrayList("Seleccionar", "Plan 15 GB",
                        "Plan 25 GB", "Plan 40 GB", "Plan ilimitado");
        comboxSelectPlan.setItems(option);
        comboxSelectPlan.getSelectionModel().selectFirst();
    }

    public void clickCleanFields(ActionEvent actionEvent) {
        cleanGUI();
    }

    /**
     * Reset GUI state
     */
    public void cleanGUI() {
        txtName.setText("");
        txtIdentity.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtDepto.setText("");
        comboxSelectClient.getSelectionModel().selectFirst();
        comboxSelectPlan.getSelectionModel().selectFirst();
        txtService.setText("");
        txtPhoneNumber.setText("");
        txtPayment.setText("");
        txtMinutes.setText("");
        txtMinutesUnlimited.setText("");
        txtData.setText("");
        txtGbCloud.setText("");
        txtGbShare.setText("");
        txtMsmUnlimited.setText("");
        txtNetflix.setText("");
        txtAreaMoreInfo.setText("");
    }

    /**
     * Restrict a textField to only accept numbers
     * @param textField to restrict
     */
    private void setOnlyNum(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Check that all textFiel is not empty
     * @return boolean
     */
    private boolean checkFill(){
        return !comboxSelectClient.getSelectionModel().getSelectedItem().isEmpty()
                && !comboxSelectPlan.getSelectionModel().getSelectedItem().isEmpty()
                && !txtName.getText().isEmpty() && !txtIdentity.getText().isEmpty()
                && !txtEmail.getText().isEmpty() && !txtAddress.getText().isEmpty()
                && !txtCity.getText().isEmpty() && !txtDepto.getText().isEmpty()
                && !txtService.getText().isEmpty();
    }

    /**
     * Check that all field are complete
     * @return boolean
     */
    public boolean checkEmptyField(ObservableList<String> lst) {
        for(String st: lst) {
            if (st.equals("")) {
                return false;
            }
        }
        return true;
    }

    public String dateTimeActual(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dateTime = dtf.format(LocalDateTime.now());
        return dateTime;
    }
}
