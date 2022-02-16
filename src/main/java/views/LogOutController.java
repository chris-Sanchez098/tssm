package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tssm.App;

public class LogOutController {

    Stage stage;

    @FXML
    private BorderPane scenePane;
    /**
     * Log out
     * @param event get the event
     */
    public void logOutEvent(ActionEvent event){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Cerrar Sesión");
        exitAlert.setHeaderText("Vas a cerrar sesión.");
        exitAlert.setContentText("¿Realmente quieres cerrar sesión?");
        if(exitAlert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
            App.setStage("/views/logIn");
        }
    }

}

