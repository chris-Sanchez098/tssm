package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class tableController extends AnchorPane implements Initializable {
    @FXML
    private TextField tfSearch;
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

    public tableController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/tableConsult.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            System.out.println("Error");
            e.getCause();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOnlyNum(tfSearch);
        this.colId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_id"));
        this.colCC.setCellValueFactory(new PropertyValueFactory<Customer, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        this.colAddressId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("addressId"));
        this.colCustomerType.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerTypeId"));
        this.colPhonePlan.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phonePlanId"));
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
}
