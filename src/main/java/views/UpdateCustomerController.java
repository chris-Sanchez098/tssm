package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.CRUD;
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
    private Button updateClient;

    /**
     * Init the attributes
     * @param customer customer from DB
     */
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
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CbSelectCustomer();
        CbSelectPlan();
        setOnlyNumber(tfCC);
    }

    @FXML
    public void clickSelectClient(ActionEvent event) {
        changeColorUpdateButton();
    }

    @FXML
    public void clickSelectPlan(ActionEvent event) {
        changeColorUpdateButton();
    }

    @FXML
    public void clickCancelUpdate(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    /**
     * Set the type of customer in comboBox
     */
    public void CbSelectCustomer() {
        ObservableList<String> option =
                FXCollections.observableArrayList("", "Natural", "Corporativo");
        comboBoxTypeClient.setItems(option);
        comboBoxTypeClient.getSelectionModel().selectFirst();
    }

    /**
     * Set plan in comboBox
     */
    public void CbSelectPlan() {
        ObservableList<String> option =
                FXCollections.observableArrayList("", "Plan 15 GB",
                        "Plan 25 GB", "Plan 40 GB", "Plan ilimitado");
        comboBoxMobilPlan.setItems(option);
        comboBoxMobilPlan.getSelectionModel().selectFirst();
    }

    /**
     * Change the plan string to planId
     * @param plan a plan
     * @return int
     */
    public int changePlan(String plan){
        if(plan.equals("Plan 15 GB")){
            return 2;
        }
        if(plan.equals("Plan 25 GB")){
            return 3;
        }
        if(plan.equals("Plan 45 GB")){
            return 4;
        }
        return 5;
    }

    /**
     * Update the customer
     * @param event get the event
     */
    @FXML
    public void clickUpdateClient(ActionEvent event) {
        if(lessCheckFillCustomer()){
            updateValues();
            if(updateEmail()){
                if(CRUD.updateCustomer(customer,tfCurrentCC.getText())){
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
            }
        }
    }

    @FXML
    private void colorEvent(KeyEvent event){
        changeColorUpdateButton();
    }

    /**
     * change bottom's color grey to do action, grey to not possible action
     */
    private void changeColorUpdateButton(){
        if(lessCheckFillCustomer()){
            updateClient.setStyle("-fx-background-color: lightgreen; ");
        } else{
            updateClient.setStyle("-fx-background-color: silver; ");
        }
    }

    /**
     * checks if at less there is a change
     * @return check
     */
    private boolean lessCheckFillCustomer(){
        return !tfName.getText().isEmpty() || !tfCC.getText().isEmpty()
                || !tfEmail.getText().isEmpty() || !tfAddress.getText().isEmpty()
                || !tfCity.getText().isEmpty() || !tfDepartment.getText().isEmpty() || !comboBoxTypeClient.getSelectionModel().getSelectedItem().isEmpty()
                || !comboBoxMobilPlan.getSelectionModel().getSelectedItem().isEmpty();
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

    /**
     * Update the values
     */
    private void updateValues(){
        String name = tfName.getText();
        String cc = tfCC.getText();
        String city = tfCity.getText();
        String department = tfDepartment.getText();
        String typeClient = comboBoxTypeClient.getSelectionModel().getSelectedItem();
        String mobilePlan = comboBoxMobilPlan.getSelectionModel().getSelectedItem();

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
    }

    /**
     * Check the email and then update the email
     * @return boolean
     */
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
    }
}
