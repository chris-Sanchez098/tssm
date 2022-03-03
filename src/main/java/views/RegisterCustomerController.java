package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.CRUD;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCustomerController implements Initializable {
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
    public TextField txtGbData;
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
    public ComboBox<String> comboxSelectCustomer;
    @FXML
    public ComboBox<String> comboxSelectPlan;
    @FXML
    public Button btnCleanFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CBSelectCustomer();
        CBSelectPlan();
        setOnlyNum(txtIdentity);
    }

    @FXML
    public void clickSelectCustomer(ActionEvent actionEvent) {
    }

    @FXML
    public void clickSelectPlan(ActionEvent actionEvent) {
        String phonePlan = comboxSelectPlan.getSelectionModel().getSelectedItem();
        ObservableList<String> phonePlanList = null;
        switch (phonePlan){
            case "Seleccionar":
                cleanGUI();
                break;
            case "Plan 15 GB":
                phonePlanList = CRUD.getPhonePlan(2);
                viewPhonePlans(phonePlanList);
                break;
            case "Plan 25 GB":
                phonePlanList = CRUD.getPhonePlan(3);
                viewPhonePlans(phonePlanList);
                break;
            case "Plan 40 GB":
                phonePlanList = CRUD.getPhonePlan(4);
                viewPhonePlans(phonePlanList);
                break;
            case "Plan ilimitado":
                phonePlanList = CRUD.getPhonePlan(5);
                viewPhonePlans(phonePlanList);
                break;
        }
    }

    public void viewPhonePlans(ObservableList<String> list){
        txtPayment.setText(list.get(0));

        if(Integer.parseInt(list.get(1)) > 40) txtGbData.setText("Ilimitados");
        else txtGbData.setText(list.get(1));

        txtGbCloud.setText(list.get(2));
        txtGbShare.setText(list.get(3));

        if(Objects.equals(list.get(4), "t")) txtMinutesUnlimited.setText("Si");
        else txtMinutesUnlimited.setText("No");

        if(Objects.equals(list.get(5), "t")) txtMsmUnlimited.setText("Si");
        else txtMsmUnlimited.setText("No");

        if(Integer.parseInt(list.get(6)) > 1000) txtMinutes.setText("Ilimitados");
        else txtMinutes.setText(list.get(6));

        txtNetflix.setText(list.get(7));
        txtAreaMoreInfo.setText(list.get(8));
    }

    @FXML
    public void clickRegisterCustomer(ActionEvent actionEvent) {
        boolean insertCustomer = false;
        String name = txtName.getText();
        String cc = txtIdentity.getText().toLowerCase();
        String email = txtEmail.getText();
        String add = txtAddress.getText();
        String city = txtCity.getText();
        String dpto = txtDepto.getText();
        String typeCli = comboxSelectCustomer.getSelectionModel().getSelectedItem();
        String plane = comboxSelectPlan.getSelectionModel().getSelectedItem();
        String dateTime = dateTimeActual();
        String serv = txtService.getText();
        String phoneNum = txtPhoneNumber.getText();
        System.out.println(dateTime);
        ObservableList<String> list =
                FXCollections.observableArrayList(name, cc, email, add,
                        city, dpto, typeCli, plane, dateTime, serv, phoneNum);

        if(checkEmptyField(list) && !(typeCli == "Seleccionar") && !(plane == "Seleccionar")) {
            if(validateEmail(email)) {
                switch (plane) {
                    case "Plan 15 GB":
                        plane = "2";
                        break;
                    case "Plan 25 GB":
                        plane = "3";
                        break;
                    case "Plan 40 GB":
                        plane = "4";
                        break;
                    case "Plan ilimitado":
                        plane = "5";
                        break;
                }
                insertCustomer = CRUD.insertCustomer(name, cc, email, add, city,
                        dpto, typeCli, plane, dateTime, serv, phoneNum);
                cleanGUI();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Correo electrónico!");
                alert.setContentText("Por favor ingrese un correo válido!");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Registro de cliente!");
            alert.setContentText("Verifique que todos los campos esten llenos.");
            alert.showAndWait();
        }
        if(insertCustomer == true){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Registro de cliente");
            alert.setContentText("El cliente " + name + " fué registrado con éxito y su nùmero " +
                    "de celular es: " + phoneNum);
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
    public void CBSelectCustomer() {
        ObservableList<String> option =
                FXCollections.observableArrayList("Seleccionar", "Natural", "Corporativo");
        comboxSelectCustomer.setItems(option);
        comboxSelectCustomer.getSelectionModel().selectFirst();
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
        comboxSelectCustomer.getSelectionModel().selectFirst();
        comboxSelectPlan.getSelectionModel().selectFirst();
        txtService.setText("");
        txtPhoneNumber.setText("");
        txtPayment.setText("");
        txtMinutes.setText("");
        txtMinutesUnlimited.setText("");
        txtGbData.setText("");
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
        return !comboxSelectCustomer.getSelectionModel().getSelectedItem().isEmpty()
                && !comboxSelectPlan.getSelectionModel().getSelectedItem().isEmpty()
                && !txtName.getText().isEmpty() && !txtIdentity.getText().isEmpty()
                && !txtEmail.getText().isEmpty() && !txtAddress.getText().isEmpty()
                && !txtCity.getText().isEmpty() && !txtDepto.getText().isEmpty()
                && !txtService.getText().isEmpty() && txtPhoneNumber.getText().isEmpty();
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

    /**
     * get system date and time
     */
    public String dateTimeActual(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dateTime = dtf.format(LocalDateTime.now());
        return dateTime;
    }

    /**
     * check that the email is valid
     * @return boolean
     */
    public boolean validateEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        if (mather.find()) {
            return true;
        } else {
            return false;
        }
    }
}
