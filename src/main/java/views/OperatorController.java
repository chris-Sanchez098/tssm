package views;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CRUD;
import model.Customer;
import tssm.App;
import java.net.URL;
import java.util.ResourceBundle;

public class OperatorController implements Initializable {
    @FXML
    private Button bUpdateTb;
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

    /**
     * Initialize all columns of table
     */
    public void initTable() {
        setOnlyNum(tfSearch);
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
            infoCustomerController ifoView = loader.getController();
            ifoView.initAttributes(customers, customer);
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
}
