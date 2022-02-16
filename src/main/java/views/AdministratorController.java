package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tssm.App;

public class AdministratorController  {

    @FXML
    private void launch(ActionEvent event) {
        App.newStage("/views/createUser","Creación usuarios");
    }

    @FXML
    private void updateStage(ActionEvent event){
        App.newStage("/views/updateUser","Modificación de usuarios");
    }

}
