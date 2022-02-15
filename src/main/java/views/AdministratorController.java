package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tssm.App;

public class AdministratorController  {
    @FXML
    private Button bLaunchCreate;

    @FXML
    private void launch(ActionEvent event) {
        App.setStage("/views/createUser","Creación usuarios");
    }

    @FXML
    private void updateStage(ActionEvent Event){
        App.setStage("/views/updateUser","Modificación de usuarios");
    }

}
