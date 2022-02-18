package views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
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

    @FXML
    private VBox vBox;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    /**
     * validate data then set a new stage
     * @param event get the event
     */
    @FXML
    private void logInEvent(ActionEvent event){
        /**
        String login = userField.getText();
        String passWord = MD5.encrypt(passwordField.getText());
        User user = CRUD.selectLogin(login);
        String loginUser = user.getUser();
        if(login.equals(loginUser) && passWord.equals(user.getPwd()) && !loginUser.isEmpty()){
            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
            App.setStage("/views/administrator");
        }
        */
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
        App.setStage("/views/administrator");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeFocusField(userField, vBox);
    }

    /**
     * remove focus for textfield
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
}
