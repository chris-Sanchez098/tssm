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
import tssm.App;

import java.io.IOException;
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
     * @throws IOException
     */
    @FXML
    private void logInEvent(ActionEvent event){
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
