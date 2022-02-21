package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import tssm.App;
import java.net.URL;
import java.util.ResourceBundle;

public class OperatorController implements Initializable {
    @FXML
    public Button btnInit;
    @FXML
    public Button btnPhonePlans;
    @FXML
    public Button btnRegisClient;
    @FXML
    public Button btnLogOut;
    @FXML
    public Button btnConsultClient;
    @FXML
    private BorderPane pCenter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void clickInit(ActionEvent actionEvent) {
    }

    @FXML
    public void clickPhonePlans(ActionEvent actionEvent) {
    }

    @FXML
    public void clickRegisClient(ActionEvent actionEvent) {
        App.openStage("/views/registerClient", "Registrar cliente");
    }

    @FXML
    public void clickLogOut(ActionEvent actionEvent) {
    }

    @FXML
    public void clickConsultClient(ActionEvent actionEvent) {
        tableController viewTb = new tableController();
        pCenter.setCenter(viewTb);
    }
}
