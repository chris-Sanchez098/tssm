package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;


public class UpdateCustomerController implements Initializable{
    private Customer customer;

    @FXML
    private TextField tfCurrentName;

    @FXML
    private TextField tfCurrentCC;

    @FXML
    private TextField tfCurrentEmail;

    @FXML
    private TextField tfCurrentAddress;

    @FXML
    private TextField tfCurrentCity;

    @FXML
    private TextField tfCurrentDepartment;

    @FXML
    private TextField tfCurrentTypeClient;

    @FXML
    private TextField tfCurrentMobilePlan;

    @FXML
    private TextField tfCurrentService;

    @FXML
    private TextField tfCurrentMobilNumber;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfCC;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfCity;

    @FXML
    private TextField tfDepartment;

    @FXML
    private ComboBox<String> comboBoxTypeClient;

    @FXML
    private ComboBox<String> comboBoxMobilPlan;

    @FXML
    private TextField tfService;

    @FXML
    private TextField tfMobileNumber;

    @FXML
    private Button updateClient;

    @FXML
    private Button clearFields;

    @FXML
    private Button cancelUpdate;

    public void initAttributesClient(Customer customer) {
        this.customer = customer;
        this.tfCurrentName.setText(customer.getName());
        this.tfCurrentCC.setText(customer.getCc());
        this.tfCurrentEmail.setText(customer.getEmail());
        this.tfCurrentAddress.setText(customer.getAddress());
        this.tfCurrentCity.setText(customer.getCity());
        this.tfCurrentDepartment.setText(customer.getDepartment());
        this.tfCurrentTypeClient.setText(customer.getCustomerType());
    //    this.tfCurrentMobilePlan.setText(customer.detailsMobilePlan());
        this.tfCurrentMobilNumber.setText(customer.getPhoneNumber());
    }


    @FXML
    private  void updateEvent(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CbSelectClient();
        CbSelectPlan();
        setOnlyNumber(tfCC);
    }

    public void clickSelectClient(ActionEvent event) {
    }

    public void clickSelectPlan(ActionEvent event) {
    }

    @FXML
    public void clickCancelUpdate(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void CbSelectClient() {
        ObservableList<String> option =
                FXCollections.observableArrayList("Seleccionar", "Natural", "Corporativo");
        comboBoxTypeClient.setItems(option);
        comboBoxTypeClient.getSelectionModel().selectFirst();
    }

    public void CbSelectPlan() {
        ObservableList<String> option =
                FXCollections.observableArrayList("Seleccionar", "Plan 15 GB",
                        "Plan 25 GB", "Plan 40 GB", "Plan ilimitado");
        comboBoxMobilPlan.setItems(option);
        comboBoxMobilPlan.getSelectionModel().selectFirst();
    }


    public void clickUpdateClient(ActionEvent event) {

    }

    public void clickClearFields(ActionEvent actionEvent) {
        cleanGUI();
    }


    /**
     * Reset GUI state
     */
    public void cleanGUI() {
        tfName.setText("");
        tfCC.setText("");
        tfEmail.setText("");
        tfAddress.setText("");
        tfCity.setText("");
        tfDepartment.setText("");
        comboBoxTypeClient.getSelectionModel().selectFirst();
        comboBoxMobilPlan.getSelectionModel().selectFirst();
        tfService.setText("");
        tfMobileNumber.setText("");
    }

    /**
     * Restrict a textField to only accept numbers
     * @param textField to restrict
     */
    private void setOnlyNumber(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }



}
