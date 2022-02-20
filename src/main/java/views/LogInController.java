package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CRUD;
import model.MD5;
import model.User;
import tssm.App;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    private int counter = 0;

    @FXML
    private VBox vBox;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label userMessage;

    @FXML
    private Label passMessage;

    /**
     * clean label userMessage
     */
    @FXML
    private void messageEventU(){
        userMessage.setText("");
    }

    /**
     * clean label passMessage
     */
    @FXML
    private void messageEventP(){
        passMessage.setText("");
    }
    /**
     * validate data then set a new stage
     * @param event get the event
     */
    @FXML
    private void logInEvent(ActionEvent event){
        String login = userField.getText();
        String passWord = passwordField.getText();
        String passWordEn = MD5.encrypt(passWord);
        User user = CRUD.selectLogin(login);
        String loginUser = user.getUser();
        if(checkFields(loginUser.isEmpty(), passWord.isEmpty())){
            if(user.getStatus()){
                if(login.equals(loginUser) && passWordEn.equals(user.getPwd())){
                    Stage stage = (Stage) vBox.getScene().getWindow();
                    stage.close();
                    App.setStage("/views/"+user.rolFxml());
                }
                else{
                    passMessage.setText("Contraseña incorrecta");
                    blockUser(loginUser);
                }
            }
            else{
                userMessage.setText("Usuario actualmente inactivado");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeFocusField(userField, vBox);
    }

    /**
     * remove focus from a textField
     * @param textField TextField to remove focus
     * @param aVBox vBox where it belongs textField
     */
    private void changeFocusField(TextField textField, VBox aVBox){
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        textField.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                aVBox.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
    }

    /**
     *
     * @param fieldUser is userField empty?
     * @param fieldPass is passField empty?
     * @return true if both are not empty
     */
    private boolean checkFields(boolean fieldUser  ,boolean fieldPass){
        if(fieldUser){
            userMessage.setText("Usuario no registrado");
        }
        if(fieldPass){
            passMessage.setText("Contraseña vacía");
        }
        return !fieldPass && !fieldUser;
    }

    /**
     * block a user (status = false)
     * @param user to block
     */
    private void blockUser(String user){
        counter += 1;
        if(counter == 5){
            counter = 0;
            CRUD.setFalseStatus(user);
            userMessage.setText("El usuario"+ user +"fue bloqueado por acceso irregular");
        }
    }
}
