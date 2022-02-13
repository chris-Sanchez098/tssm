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
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/views/user.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Creación usuarios");
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
