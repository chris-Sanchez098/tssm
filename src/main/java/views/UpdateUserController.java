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
import model.Validation;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {
    private User user;

    @FXML
    private Label currentName;

    @FXML
    private Label currentUser;

    @FXML
    private Label currentCC;

    @FXML
    private Label currentRol;

    @FXML
    private Label currentStatus;

    @FXML
    private TextField newUser;

    @FXML
    private TextField newName;

    @FXML
    private TextField newCC;

    @FXML
    private ComboBox<String> newRol;

    @FXML
    private ComboBox<String> newStatus;

    @FXML
    private PasswordField newPass;

    @FXML
    private PasswordField newPassCon;

    @FXML
    private Button updateButton;

    public void initAttributes(User user) {
        this.user = user;
        this.currentName.setText(user.getName());
        this.currentCC.setText(user.getCc());
        this.currentUser.setText(user.getUser());
        this.currentRol.setText(user.getRol());
        this.currentStatus.setText(user.statusToString());
    }

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
            String login = newUser.getText();
            User updateUser = buildUser(login);
            if(!pass.isEmpty() || !passCon.isEmpty()) {
                if (Objects.equals(pass, passCon)) {
                    if (User.checkPwd(pass) && !pass.equals(login)) {
                        updateUser.setPwd(pass);
                    } else {
                        emergent("La contraseña no cumple los mínimos: \n" +
                                "Mínimo de longitud 8, con al menos una mayúscula," +
                                " una minúscula, un número y un simbolo, ademas diferente del usuario de acceso.");
                        check = false;
                    }
                } else {
                    emergent("Los campos de la contraseña no coinciden.");
                    check = false;
                }
            }
            if(check){
                if(CRUD.updateUser(updateUser, user.getCc())){
                    user.getUpdate(updateUser);
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
            }
        }
    }

    /**
     * Get the string selected in ComboBox
     * @param event get the event
     */
    @FXML
    private void selectEvent(ActionEvent event){
        changeColorUpdateButton();
    }

    @FXML
    private void colorEvent(KeyEvent  event){
        changeColorUpdateButton();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newRolConfig();
        Validation.setOnlyNum(newCC);
    }

    /**
     * checks if at less there is a change
     * @return check
     */
    private boolean lessCheckFill(){
        return !newRol.getSelectionModel().getSelectedItem().isEmpty()
                || !newStatus.getSelectionModel().getSelectedItem().isEmpty()
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
        ObservableList<String> list1 = FXCollections.observableArrayList("","Habilitado", "Deshabilitado");
        newStatus.setItems(list1);
        newStatus.getSelectionModel().selectFirst();

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

    /**
     * build a user with new atributes
     * @param login login user of user
     * @return user
     */
    private User buildUser(String login){
        User newUser = new User(user.getCc(),user.getName(), user.getUser(),"", user.getRol(), user.getStatus());
        newUser.setPwdNoEncrypt(user.getPwd());
        newUser.setCc(newCC.getText());
        newUser.setRol(newRol.getSelectionModel().getSelectedItem());
        newUser.setStringStatus(newStatus.getSelectionModel().getSelectedItem());
        newUser.setUser(login);
        newUser.setName(newName.getText());
        return newUser;
    }
}
