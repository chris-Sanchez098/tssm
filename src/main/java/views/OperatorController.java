package views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CRUD;
import model.Customer;
import model.User;
import tssm.App;
import java.net.URL;
import java.util.ResourceBundle;

public class OperatorController implements Initializable {
    @FXML
    private Button bUpdateTb;
    @FXML
    private TableView<Customer> tbCustomers;
    @FXML
    private TableColumn<Customer, Integer> colId;
    @FXML
    private TableColumn<Customer, String> colCC;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private TableColumn<Customer, Integer> colAddressId;
    @FXML
    private TableColumn<Customer, Integer> colCustomerType;
    @FXML
    private TableColumn<Customer, Integer> colPhonePlan;
    @FXML
    public Button btnInit;
    @FXML
    public Button btnPhonePlans;
    @FXML
    public Button btnRegisClient;
    @FXML
    public Button btnLogOut;
    @FXML
    private TextField tfSearch;
    private ObservableList<Customer> customers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void clickInit(ActionEvent actionEvent) {
    }

    @FXML
    public void clickPhonePlans(ActionEvent actionEvent) {
    }

    @FXML
    public void clickRegisClient(ActionEvent actionEvent) {
        App.openStage("/views/registerClient", "Registrar cliente");
    }

    @FXML
    public void clickLogOut(ActionEvent actionEvent) {
    }

    @FXML
    public void clickConsultClient(Event event) {
        initTable();
    }

    @FXML
    public void updateTb(ActionEvent event) {
        if(event.getSource() == bUpdateTb) {
            customers = CRUD.getCustomer("");
            this.tbCustomers.setItems(customers);
            bUpdateTb.setText("Actualizar");
        } else {
            String cc = tfSearch.getText();
            customers = CRUD.getCustomer(cc);
            this.tbCustomers.refresh();
            this.tbCustomers.setItems(customers);
        }
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

    private void initTable() {
        setOnlyNum(tfSearch);
        this.colId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_id"));
        this.colCC.setCellValueFactory(new PropertyValueFactory<Customer, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        this.colCustomerType.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerTypeId"));
        this.colPhonePlan.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phonePlanId"));
    }
}
