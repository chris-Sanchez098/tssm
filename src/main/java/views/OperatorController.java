package views;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CRUD;
import model.Customer;
import model.Validation;
import tssm.App;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;

public class OperatorController implements Initializable {
    @FXML
    public TextArea txtAreaPlan1;
    @FXML
    public TextArea txtAreaPlan2;
    @FXML
    public TextArea txtAreaPlan3;
    @FXML
    public TextArea txtAreaPlan4;
    @FXML
    private Button bUpdateTb;
    @FXML
    private Button ButtonUpdateCustomer;
    @FXML
    private TableView<Customer> tbCustomers;
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
    private TextField tfSearch;
    @FXML
    private Button bUploadCSV;
    @FXML
    private File file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] descriptionPlans = new String[4];
        descriptionPlans = CRUD.getDescriptionPhonePlan();

        txtAreaPlan1.setText(descriptionPlans[0]);
        txtAreaPlan2.setText(descriptionPlans[1]);
        txtAreaPlan3.setText(descriptionPlans[2]);
        txtAreaPlan4.setText(descriptionPlans[3]);
    }

    @FXML
    public void clickConsultClient(Event event) {
        initTable();
    }

    @FXML
    public void updateTb(ActionEvent event) {
        ObservableList<Customer> customers;
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
     * Initialize all columns of table
     */
    public void initTable() {
        Validation.setOnlyNum(tfSearch);
        this.colCC.setCellValueFactory(new PropertyValueFactory<Customer, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        this.colCustomerType.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerTypeId"));
        this.colPhonePlan.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phonePlanId"));
    }

    /**
     * Load infoCustomer view
     * @param customer to show information
     */
    public void loadInfoView(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/infoCustomer.fxml"));
            Parent root = loader.load();
            InfoCustomerController ifoView = loader.getController();
            ifoView.initAttributes(customer);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Información detallada");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
            this.tbCustomers.refresh();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadViewConsume() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/consumption.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Consulta consumos");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void showInfo(MouseEvent mouseEvent) {
        Customer customer = this.tbCustomers.getSelectionModel().getSelectedItem();
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2 && customer != null) { loadInfoView(customer); }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Se debe selecionar un cliente");
            alert.showAndWait();
        }
    }

    @FXML
    public void clickUpdateCustomer(ActionEvent event) {
        Customer customer = this.tbCustomers.getSelectionModel().getSelectedItem();
        if(event.getSource() == ButtonUpdateCustomer && customer != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateCustomer.fxml"));
                Parent root = loader.load();
                UpdateCustomerController updateView = loader.getController();
                updateView.initAttributesCustomer(customer);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modificación cliente");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.showAndWait();
                this.tbCustomers.refresh();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un cliente");
            alert.showAndWait();
        }
    }
}
