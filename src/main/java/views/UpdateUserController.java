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
import model.User;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {
    User user;
    String rol;

    @FXML
    private Label currentName;

    @FXML
    private Label currentUser;

    @FXML
    private Label currentCC;

    @FXML
    private Label currentRol;

    @FXML
    private TextField newUser;

    @FXML
    private TextField newName;

    @FXML
    private TextField newCC;

    @FXML
    private ComboBox<String> newRol;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField newPassCon;

    @FXML
    private Button updateButton;

    /**
     * Close the stage
     * @param event get the event
     */
    @FXML
    private void cancelEvent(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    /**
     * Update the user
     * @param event get the event
     */
    @FXML
    private void updateEvent(ActionEvent event){
        if(lessCheckFill()){
            boolean check = true;
            String pass = newPass.getText();
            String passCon = newPassCon.getText();
            user.setCc(newCC.getText());
            user.setRol(rol);
            user.setUser(newUser.getText());
            user.setName(newName.getText());

            if(!newPass.getText().isEmpty() && !newPassCon.getText().isEmpty()) {
                if (Objects.equals(pass, passCon)) {
                    if (User.checkPwd(pass) && !Objects.equals(pass, newUser.getText())) {
                        user.setPwd(newPass.getText());
                    } else {
                        emergent("La contraseña no cumple los mínimos: \n " +
                                "Mínimo de longitud 8, con al menos una mayúscula," +
                                " una minúscula, un número y un simbolo, ademas diferente del usuario de acceso.");
                        check = false;
                    }
                } else {
                    emergent("La contraseña no coincide.");
                    check = false;
                }
            }
            if(check){
                CRUD.updateUser(user, currentCC.getText());
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        }
    }

    /**
     * Get the string selected in ComboBox
     * @param event get the event
     */
    @FXML
    private void selectEvent(ActionEvent event){
        rol = newRol.getSelectionModel().getSelectedItem();
        changeColorUpdateButton();
    }

    @FXML
    private void colorEvent(KeyEvent event){
        changeColorUpdateButton();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newRolConfig();
        currentUser();
        setOnlyNum(newCC);
    }

    /**
     * checks if at less there is a change
     * @return check
     */
    private boolean lessCheckFill(){
        return !newRol.getSelectionModel().getSelectedItem().isEmpty()
                || !newName.getText().isEmpty() || !newCC.getText().isEmpty()
                || !newUser.getText().isEmpty() || !newPass.getText().isEmpty()
                || !newPassCon.getText().isEmpty();
    }

    /**
     * Alert
     * @param reason why the alert show up
     */
    private void emergent(String reason){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Modificación de usuario fallida");
        alert.setContentText("El usuario "+ user.getName() + " no fue modificado, " + reason );
        alert.showAndWait();
    }

    /**
     * set config to the ComboBox
     */
    private void newRolConfig(){
        ObservableList<String> list = FXCollections.observableArrayList("","Administrador", "Gerente", "Operador");
        newRol.setItems(list);
        newRol.getSelectionModel().selectFirst();
    }

    /**
     * Set initial values to labels
     */
    private void currentUser(){
        user = CRUD.selectUser("1193075514");
        currentName.setText(user.getName());
        currentUser.setText(user.getUser());
        currentCC.setText(user.getCc());
        currentRol.setText(user.getRol());
    }

    /**
     * Set only numbers to input
     * @param textField textField to set the property
     */
    private void setOnlyNum(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * change bottom's color grey to do action, grey to not possible action
     */
    private void changeColorUpdateButton(){
        if(lessCheckFill()){
            updateButton.setStyle("-fx-background-color: lightgreen; ");
        } else{
            updateButton.setStyle("-fx-background-color: silver; ");
        }
    }
}
