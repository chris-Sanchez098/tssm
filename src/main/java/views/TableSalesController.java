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
import model.Sales;
import model.Validation;
import reports.PrintReport;

import java.net.URL;
import java.util.ResourceBundle;

public class TableSalesController implements Initializable {
    @FXML
    private TableView<Sales> salesTableView;
    @FXML
    private TableColumn<Sales, String> colCC;
    @FXML
    private TableColumn<Sales, String> colName;
    @FXML
    private TableColumn<Sales, String> colDate;
    @FXML
    private TableColumn<Sales, Integer> colCustomerType;
    @FXML
    private TableColumn<Sales, Integer> colLines;
    @FXML
    private TableColumn<Sales, Integer> colPhonePlan;
    @FXML
    private TextField searchTextF;
    @FXML
    private Button updateButton;
    private ObservableList<Sales> sales;


    @FXML
    public void updateEvent(ActionEvent event) {
        if(event.getSource() == updateButton) {
            sales = CRUD.getSales("");
            this.salesTableView.setItems(sales);
            updateButton.setText("Actualizar");
        } else {
            String cc = searchTextF.getText();
            sales = CRUD.getSales(cc);
            this.salesTableView.refresh();
            this.salesTableView.setItems(sales);
        }
    }

    /**
     * Initialize all columns of table
     */
    public void initTable() {
        Validation.setOnlyNum(searchTextF);
        this.colCC.setCellValueFactory(new PropertyValueFactory<Sales, String>("id"));
        this.colName.setCellValueFactory(new PropertyValueFactory<Sales, String>("name"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<Sales, String>("date"));
        this.colCustomerType.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("customerType"));
        this.colPhonePlan.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("phonePlan"));
        this.colLines.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("lines"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        sales = CRUD.getSales("");
        this.salesTableView.setItems(sales);

    }

    @FXML
    private void printEvent(ActionEvent event){
        String query = "SELECT * FROM usuarios";
        new PrintReport().showReport("prueba", query);
    }
}
