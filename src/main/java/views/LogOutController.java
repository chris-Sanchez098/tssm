package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LogOutController {

    Stage stage;

    @FXML
    private Button logOutButton;

    @FXML
    private BorderPane scenePane;

    public void logoutEvent(ActionEvent event){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Cerrar Sesión");
        exitAlert.setHeaderText("Vas a cerrar sesión");
        exitAlert.setContentText("¿Realmente quieres salir?");

        if(exitAlert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) scenePane.getScene().getWindow();
             stage.close();
        }
    }

}

