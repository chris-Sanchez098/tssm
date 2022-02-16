package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import tssm.App;

public class AdministratorController  {

    @FXML
    private void launch(ActionEvent event) {
        App.openStage("/views/createUser","Creación usuarios");
    }

    @FXML
    private void updateStage(ActionEvent event){
        App.openStage("/views/updateUser","Modificación de usuarios");
    }

}
