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
import model.Payment;
import model.Validation;
import tssm.App;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

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
    public Button btnSearchCustomer;
    @FXML
    public TextField txtUserId;
    @FXML
    public TextField txtUserName;
    @FXML
    public TextField txtPaymentValue;
    @FXML
    public Button btnRegisterPayment;
    @FXML
    public TextField txtPaymentDate;
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
    @FXML
    private Button bUploadCSV;
    @FXML
    private Tab tapRegister;
    @FXML
    private Button bUploadBank;
    private File file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] descriptionPlans = new String[4];
        descriptionPlans = CRUD.getDescriptionPhonePlan();

        txtAreaPlan1.setText(descriptionPlans[0]);
        txtAreaPlan2.setText(descriptionPlans[1]);
        txtAreaPlan3.setText(descriptionPlans[2]);
        txtAreaPlan4.setText(descriptionPlans[3]);
        Validation.setOnlyNum(txtUserId);
    }

    @FXML
    public void clickInit(ActionEvent actionEvent) {
    }

    @FXML
    public void clickPhonePlans(ActionEvent actionEvent) {
    }

    @FXML
    public void clickRegisClient(ActionEvent actionEvent) {
        App.openStage("/views/registerCustomer", "Registrar cliente");
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

    @FXML
    public void clickUploadCSV(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Carga datos");
        ObservableList<String[]> data = readCSV();
        if(event.getSource() == bUploadCSV) {
            CRUD.insertConsume(data);
            alert.setContentText("Los datos fueron cargados");
        } else if(event.getSource() == bUploadBank
        ) {
            CRUD.insertPayBank(data);
            alert.setContentText("Los pagos fueron cargados!");
        } else {
            alert.setContentText("Los datos no fueron cargados");
        }
        alert.showAndWait();
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
            infoCustomerController ifoView = loader.getController();
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

    /**
     * Open file chooser
     */
    public void selectFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        stage.setTitle("Select csv file");
        file = fileChooser.showOpenDialog(stage);
    }

    /**
     * Read CSV file
     */
    public ObservableList<String[]> readCSV() {
        selectFile();
        ObservableList<String[]> info = FXCollections.observableArrayList();
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                info.add(nextLine);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        } return info;
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

    public void clickSearchCustomer(ActionEvent actionEvent) {
        cleanGUIPayment();
        double total = 0;
        String userId = txtUserId.getText().toLowerCase();
        Vector<Payment> dataPaymentList;

        if(!(txtUserId.getText().isEmpty())){
            dataPaymentList = CRUD.viewPayment(userId);
            if(!dataPaymentList.isEmpty()) {
                total = calculatePayment(dataPaymentList);
                txtUserName.setText(dataPaymentList.get(0).getName());
                txtPaymentValue.setText(String.valueOf(total));
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Usuario no encontrado");
                alert.setContentText("!El usuario ingresado no existe, intente de nuevo!");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Campo vacío!");
            alert.setContentText("Por favor ingrese el número de identificación del usuario");
            alert.showAndWait();
        }
    }

    public void cleanGUIPayment(){
        txtUserName.setText("");
        txtPaymentValue.setText("");
        txtPaymentDate.setText("");
    }

    public double calculatePayment(Vector<Payment> payment){
        double total = 0;
        for (Payment py: payment){
            total = Double.parseDouble(py.getBasicPay()) +
                    Double.parseDouble(py.getExtraPayMin()) +
                    Double.parseDouble(py.getExtraPayData()) +
                    Double.parseDouble(py.getTaxes());
        }
        return total;
    }

    public void clickRegisterPayment(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿está seguro de registrar el pago?");
        alert.showAndWait();
        if((alert.getResult().getButtonData().isDefaultButton())){
            String paymentId = txtUserId.getText();
            CRUD.registerPayment(paymentId);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setContentText("El pago fue registrado con exito.");
            alert1.showAndWait();
            cleanGUIPayment();
        }
        else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setHeaderText(null);
            alert2.setContentText("El pago fue cancelado.");
            alert2.showAndWait();
            cleanGUIPayment();
        }
    }
}
