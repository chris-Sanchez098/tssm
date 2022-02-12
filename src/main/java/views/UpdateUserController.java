package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

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
    private ComboBox newRol;

    public void updatedateEvent(ActionEvent event){
        System.out.println("Funciona");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(newCC.getText() == ""){
            currentCC.setText("hola");
        }

    }
}
