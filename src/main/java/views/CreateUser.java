package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateUser {
    @FXML
    private Label lName;
    @FXML
    private Label lUser;
    @FXML
    private Label lCC;
    @FXML
    private Label lRol;
    @FXML
    private Label lPwd;
    @FXML
    private Label lPwdConfirm;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfCC;
    @FXML
    private TextField tfPwd;
    @FXML
    private TextField tfPwdConfirm;
    @FXML
    private ComboBox<String> cbRol;
    @FXML
    private Button bCreate;
    @FXML
    private Button bOther;

}
