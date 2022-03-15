package views;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CRUD;
import model.Pay;
import model.Validation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    public Button btnSearchCustomer;
    @FXML
    private Button bUploadBank;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPaymentValue;
    @FXML
    private Button btnRegisterPayment;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private ComboBox<String> yearCombo;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private TextField dateCutInitField;
    @FXML
    private TextField dateCutFinalField;
    private File file;
    private Pay pay;
    private String cc;
    private String dateFinal;
    private String dateInit;



    @FXML
    public void clickUploadCSV(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Carga datos");
        ObservableList<String[]> data = readCSV();
        if(event.getSource() == bUploadBank
        ) {
            CRUD.insertPayBank(data);
            alert.setContentText("Los pagos fueron cargados!");
        } else {
            alert.setContentText("Los datos no fueron cargados");
        }
        alert.showAndWait();
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

    /**
     * Open file chooser
     */
    public void selectFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        stage.setTitle("Select csv file");
        file = fileChooser.showOpenDialog(stage);
    }

    public void clickSearchCustomer(ActionEvent actionEvent) {
        cc = txtCustomerId.getText().toLowerCase();
        cleanGUIPayment();
        if(!cc.isEmpty()){
            int year = getYear();
            int month = getMonth();
            dateFinal = year + "-" + month;
            dateInit = pastDate(year, month);
            ObservableList<Pay> payList = CRUD.getPayment(cc,dateInit ,dateFinal);
            if(!payList.isEmpty()) {
                pay = payList.get(0);
                txtUserName.setText(pay.getName());
                txtPaymentValue.setText(Double.toString(pay.getTotal()));
                dateCutFinalField.setText(dateFinal + "-15");
                dateCutInitField.setText(dateInit + "-16");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Usuario no encontrado");
                alert.setContentText("!El Cliente o la fecha ingresada no existe, intente de nuevo!");
                alert.showAndWait();
            }
        }
    }

    public void cleanGUIPayment(){
        txtUserName.setText("");
        txtPaymentValue.setText("");
        dateCutInitField.setText("");
        dateCutFinalField.setText("");
    }

    public void clickRegisterPayment(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿está seguro de registrar el pago?");
        alert.showAndWait();
        if((alert.getResult().getButtonData().isDefaultButton())){
            String paymentId = txtCustomerId.getText();
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

    private void setComboBox(){
        ObservableList<String> list = FXCollections.observableArrayList("Actual","2021", "2022", "2023", "2024", "2025");
        yearCombo.setItems(list);
        yearCombo.getSelectionModel().selectFirst();
        ObservableList<String> list1 = FXCollections.observableArrayList("Actual","Enero", "Febrero", "Marzo", "Abril", "Mayo",
                "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        monthCombo.setItems(list1);
        monthCombo.getSelectionModel().selectFirst();
    }

    private int getYear(){
        String year = yearCombo.getSelectionModel().getSelectedItem();
        if(year.equals("Actual")){
            year = Integer.toString(YearMonth.now().getYear());
        }
        return Integer.parseInt(year);
    }

    private int getMonth(){
        String month = monthCombo.getSelectionModel().getSelectedItem();
        return switch (month) {
            case "Enero" -> 1;
            case "Febrero" -> 2;
            case "Marzo" -> 3;
            case "Abril" -> 4;
            case "Mayo" -> 5;
            case "Junio" -> 6;
            case "Julio" -> 7;
            case "Agosto" -> 8;
            case "Septiembre" -> 9;
            case "Octubre" -> 10;
            case "Noviembre" -> 11;
            case "Diciembre" -> 12;
            default -> YearMonth.now().getMonth().getValue();
        };
    }

    private String pastDate(int year, int month){
        if(month == 1){
            return (year - 1) + "-12";
        }
            return (getYear()) + "-" + (getMonth() - 1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComboBox();
        Validation.setOnlyNum(txtCustomerId);
        System.out.println(getYear());
        System.out.println(getMonth());
    }
}
