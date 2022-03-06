package views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CRUD;
import model.Customer;
import model.Validation;
import reports.PrintReport;

import java.net.URL;
import java.util.ResourceBundle;

public class TableCustomerController implements Initializable {
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> colCC;
    @FXML
    private TableColumn<Customer, String> colName;
    @FXML
    private TableColumn<Customer, String> colEmail;
    @FXML
    private TableColumn<Customer, Integer> colCustomerType;
    @FXML
    private TableColumn<Customer, Integer> colPhonePlan;
    @FXML
    private TextField searchTextF;
    @FXML
    private Button updateButton;
    private ObservableList<Customer> customers;


    @FXML
    public void updateEvent(ActionEvent event) {
        if(event.getSource() == updateButton) {
            customers = CRUD.getCustomer("");
            this.customerTableView.setItems(customers);
            updateButton.setText("Actualizar");
        } else {
            String cc = searchTextF.getText();
            customers = CRUD.getCustomer(cc);
            this.customerTableView.refresh();
            this.customerTableView.setItems(customers);
        }
    }

    /**
     * Initialize all columns of table
     */
    public void initTable() {
        Validation.setOnlyNum(searchTextF);
        this.colCC.setCellValueFactory(new PropertyValueFactory<Customer, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        this.colCustomerType.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerTypeId"));
        this.colPhonePlan.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phonePlanId"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        customers = CRUD.getCustomer("");
        this.customerTableView.setItems(customers);

    }

    @FXML
    private void printEvent(ActionEvent event){
        String query = "SELECT * FROM usuarios";
        new PrintReport().showReport("prueba", query);
    }
}
