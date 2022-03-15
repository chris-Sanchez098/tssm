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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private TextField tfMobileNumber;

    @FXML
    private Button updateClient;

    @FXML
    private Button clearFields;

    @FXML
    private Button cancelUpdate;

    public void initAttributesCustomer(Customer customer) {
        this.customer = customer;
        this.tfCurrentName.setText(customer.getName());
        this.tfCurrentCC.setText(customer.getCc());
        this.tfCurrentEmail.setText(customer.getEmail());
        this.tfCurrentAddress.setText(customer.getAddress());
        this.tfCurrentCity.setText(customer.getCity());
        this.tfCurrentDepartment.setText(customer.getDepartment());
        this.tfCurrentTypeClient.setText(customer.getCustomerType());
        this.tfCurrentMobilePlan.setText(customer.detailsMobilePlan());
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
                FXCollections.observableArrayList("", "Natural", "Corporativo");
        comboBoxTypeClient.setItems(option);
        comboBoxTypeClient.getSelectionModel().selectFirst();
    }

    public void CbSelectPlan() {
        ObservableList<String> option =
                FXCollections.observableArrayList("", "Plan 15 GB",
                        "Plan 25 GB", "Plan 40 GB", "Plan ilimitado");
        comboBoxMobilPlan.setItems(option);
        comboBoxMobilPlan.getSelectionModel().selectFirst();
    }

    public int changePlan(String plan){
        if(plan == "Plan 15 GB"){
            return 2;
        }
        if(plan == "Plan 25 GB"){
            return 3;
        }
        if(plan == "Plan 40 GB"){
            return 4;
        }
        return 5;
    }


    public void clickUpdateClient(ActionEvent event) {
        if(lessCheckFillCustomer()){
            updateValues();
            if(updateEmail()){
                System.out.println("Cliente actualizado");
            }

        }
    }

    public void clickClearFields(ActionEvent actionEvent) {
        cleanGUI();
    }

    /**
     * checks if at less there is a change
     * @return check
     */
    private boolean lessCheckFillCustomer(){
        return !tfName.getText().isEmpty() || !tfCC.getText().isEmpty()
                || !tfEmail.getText().isEmpty() || !tfAddress.getText().isEmpty()
                || !tfCity.getText().isEmpty() || !tfDepartment.getText().isEmpty() || !comboBoxTypeClient.getSelectionModel().getSelectedItem().isEmpty()
                || !comboBoxMobilPlan.getSelectionModel().getSelectedItem().isEmpty() || !tfMobileNumber.getText().isEmpty();
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

    private void updateValues(){
        String name = tfName.getText();
        String cc = tfCC.getText();
        String city = tfCity.getText();
        String department = tfDepartment.getText();
        String typeClient = comboBoxTypeClient.getSelectionModel().getSelectedItem();
        String mobilePlan = comboBoxMobilPlan.getSelectionModel().getSelectedItem();
        String mobileNumber = tfMobileNumber.getText();

        if(!name.equals("")){
            customer.setName(name);
        }
        if(!cc.equals("")){
            customer.setCc(cc);
        }
        if(!city.equals("")){
            customer.setCity(city);
        }
        if(!department.equals("")){
            customer.setDepartment(department);
        }
        if(!typeClient.equals("")){
            customer.setCustomerType(typeClient);
        }
        if(!mobilePlan.equals("")){
            customer.setPhonePlanId(changePlan(mobilePlan));
        }
        if(!mobileNumber.equals("")){
            customer.setPhoneNumber(mobileNumber);
        }
    }

    public boolean updateEmail(){
        String email = tfEmail.getText();
        if(!email.equals("")){
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

            Matcher mather = pattern.matcher(email);
            if(mather.find()){
                customer.setEmail(tfEmail.getText());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error al actualizar cliente");
                alert.setContentText("El correo ingresado no es valido");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }


}
