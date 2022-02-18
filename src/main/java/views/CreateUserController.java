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
import model.MD5;
import model.User;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateUserController implements Initializable {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfCC;
    @FXML
    private PasswordField pfPwd;
    @FXML
    private PasswordField pfPwdConfirm;
    @FXML
    private ComboBox<String> cbRol;
    @FXML
    private Button bCreate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCB();
        tfCC.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) {
                tfCC.setText(newValue.replaceAll("[^\\d]",""));
            }
        });
    }

    /**
     * Close the stage
     * @param event get the event
     */
    @FXML
    private void cancelEvent(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void colorEvent(KeyEvent event){
        changeColorUpdateButton();
    }

    @FXML
    private void selectEvent(ActionEvent event){
        changeColorUpdateButton();
    }

    /**
     * inicializa los valores en el ComboBox cbRol
     */
    public void initCB() {
        ObservableList<String> option =
                FXCollections.observableArrayList("","Administrador", "Gerente", "Operador");
        cbRol.setItems(option);
        cbRol.getSelectionModel().selectFirst();
    }

    /**
     * Deja la GUI es su estado inicial
     */
    public void cleanGUI() {
        tfName.setText("");
        tfUser.setText("");
        tfCC.setText("");
        pfPwd.setText("");
        pfPwdConfirm.setText("");
        cbRol.getSelectionModel().selectFirst();
    }

    /**
     * Verifica que todos los campos necesarios se encuentren llenos
     * @return boolean
     */
    public boolean checkEmptyField(ObservableList<String> lst) {
        for(String st: lst) {
            if (st.equals("")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Muestra un mensaje de error si la contraseña no cumple con la politica,
     * no coincide y es igual al user.
     * @param pwdCheck boolean
     * @param equal boolean
     * @param pwdUser boolean
     */
    public void errorMsg(boolean pwdCheck, boolean equal, boolean pwdUser) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Creación usuario");

        if (!pwdCheck) {
            alert.setContentText("La contraseña no cumple la politica:\n"
                    +"Longitud minima 8 caracteres, debe contener minusculas, mayusculas, numero y simbolos.");
        } else if (!equal) {
            alert.setContentText("Las contraseñas no coinciden.");
        } else {
            alert.setContentText("Usuario y contraseña no deben ser iguales");
        }
        alert.showAndWait();
    }

    @FXML
    private void clean(ActionEvent event) {
        cleanGUI();
        changeColorUpdateButton();
    }

    @FXML
    private void create(ActionEvent event) {
        String cc = tfCC.getText();
        String name = tfName.getText().toLowerCase();
        String rol = cbRol.getSelectionModel().getSelectedItem();
        String pwd = pfPwd.getText();
        String pwdC = pfPwdConfirm.getText();
        String user = tfUser.getText().toLowerCase();
        ObservableList<String> list =
                FXCollections.observableArrayList(cc,name,pwd,pwdC,user);
        if(checkEmptyField(list) && !(rol == null)) {
            boolean check = User.checkPwd(pwd);
            boolean equal = pwd.equals(pwdC);
            boolean userPwd = !pwd.equals(user);
            if(check && equal && userPwd) {
                String encryptPwd = MD5.encrypt(pwd);
                CRUD.insertUser(cc, name, user, encryptPwd, rol, true);
                cleanGUI();
            } else {
                errorMsg(check,equal,userPwd);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Creación usuario");
            alert.setContentText("Verifique que todos los campos esten llenos.");
            alert.showAndWait();
        }
    }

    /**
     * change bottom's color grey to do action, grey to not possible action
     */
    private void changeColorUpdateButton(){
        if(checkFill()){
            bCreate.setStyle("-fx-background-color: lightgreen; ");
        } else{
            bCreate.setStyle("-fx-background-color: silver; ");
        }
    }

    private boolean checkFill(){
        return !cbRol.getSelectionModel().getSelectedItem().isEmpty()
                && !tfName.getText().isEmpty() && !tfCC.getText().isEmpty()
                && !tfUser.getText().isEmpty() && !pfPwd.getText().isEmpty()
                && !pfPwdConfirm.getText().isEmpty();
    }
}