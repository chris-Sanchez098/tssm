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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CRUD;
import model.Pay;
import model.Validation;
import reports.PrintReport;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    public Button btnSearchCustomer;
    @FXML
    private Button bUploadBank;
    @FXML
    private Button billBut;
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
    @FXML
    private Button bUploadConsume;
    private Pay pay;
    private String cc;
    private String dateFinal;
    private String dateInit;

    @FXML
    public void clickUploadCSV(ActionEvent event) {
        ObservableList<String[]> data = readCSV();
        if(event.getSource() == bUploadBank) {
            CRUD.insertPayBank(data);
        } else if (event.getSource() == bUploadConsume) {
            CRUD.insertConsume(data);
        }
    }

    /**
     * Read CSV file
     */
    public ObservableList<String[]> readCSV() {
        File file = selectFile();
        ObservableList<String[]> info = FXCollections.observableArrayList();
        if (file != null) {
            try {
                CSVReader reader = new CSVReader(new FileReader(file));
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    info.add(nextLine);
                }
            } catch (CsvValidationException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error datos");
                alert.setContentText("Error al leer el archivo de datos, debe ser formato CSV");
                alert.showAndWait();
            } return info;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Carga de datos");
            alert.setContentText("Se debe seleccionar un archivo");
            alert.showAndWait();
        } return info;
    }

    /**
     * Open file chooser
     */
    public File selectFile() {
        File file;
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        stage.setTitle("Select csv file");
        file = fileChooser.showOpenDialog(stage);
        return file;
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
                checkPastPayment(year, month);
                txtPaymentValue.setText(Double.toString(pay.getTotal()));
                dateCutFinalField.setText(dateFinal + "-15");
                dateCutInitField.setText(dateInit + "-16");
                btnRegisterPayment.setStyle("-fx-background-color: lightgreen;");
                btnSearchCustomer.setStyle("-fx-background-color: silver;");
                billBut.setStyle("-fx-background-color: lightgreen;");
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

    /**
     * clean the GUI
     */
    public void cleanGUIPayment(){
        txtUserName.setText("");
        txtPaymentValue.setText("");
        dateCutInitField.setText("");
        dateCutFinalField.setText("");

    }

    public void clickRegisterPayment(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Estado de pago");
        alert.setHeaderText(null);
        if(!txtUserName.getText().isEmpty()){
            if(CRUD.registerPayment(dateFinal+ "-15", cc)){
                alert.setContentText("El pago fue registrado con exito.");
                cleanGUIPayment();
            }
            else{
                alert.setContentText("El pago no fue regristrado: ya existia en sistema");
                cleanGUIPayment();
            }
        } else{
            alert.setContentText("El pago no fue regristrado: no hay datos suficientes");
        }
        alert.showAndWait();
        btnRegisterPayment.setStyle("-fx-background-color: silver; ");
        billBut.setStyle("-fx-background-color: silver;");
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

    private void checkPastPayment(int year, int month){
        Calendar date = Calendar.getInstance();
        int currentYear = date.get(Calendar.YEAR);
        int currentMonth = date.get(Calendar.MONTH) + 1;
        int currentDay = date.get(Calendar.DAY_OF_MONTH);
        Double lateMax = pay.getPrice() * 0.3;
        if(year < currentYear){
            if(month == 12 && currentMonth == 1){
                if(currentDay > 30){
                    pay.setLate(lateMax);
                }
                pay.setLate(pay.getPrice() * (currentDay / 10));
            }
            else{
                pay.setLate(lateMax);
            }
        }
        else{
            if(month == currentMonth){
                pay.setLate(0.0);
            }
            else if(month + 1 == currentMonth){
                if(currentDay > 30){
                    pay.setLate(lateMax);
                }
                pay.setLate(pay.getPrice() * (currentDay / 10));
            }

            else{
                pay.setLate(lateMax);
            }
        }
    }

    @FXML
    private void changeColorEvent(KeyEvent event){
        if(!txtCustomerId.getText().isEmpty()){
            btnSearchCustomer.setStyle("-fx-background-color:  lightgreen;");
        }else{
            btnSearchCustomer.setStyle("-fx-background-color: silver;");
        }
    }

    @FXML
    private void billEvent(ActionEvent event){
        if(!txtUserName.getText().isEmpty()){
            new PrintReport().showBill(pay);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComboBox();
        Validation.setOnlyNum(txtCustomerId);
    }
}
