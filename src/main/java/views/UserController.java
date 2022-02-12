package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.CRUD;
import model.MD5;
import static java.lang.Boolean.TRUE;

public class UserController {

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfCC;
    @FXML
    private PasswordField pfPwd;
    @FXML
    private PasswordField pfPwdConfirm;
    @FXML
    private ComboBox<String> cbRol;
    @FXML
    private Button bCreate;

    @FXML
    private void create(ActionEvent event) {
        MD5 md5 = new MD5();
        int cc = Integer.parseInt(tfCC.getText());
        String name = tfName.getText();
        String rol = "Administrador";
        String pwd = pfPwd.getText();
        String pwdC = pfPwdConfirm.getText();
        String user = tfUser.getText();

        if (pwd.equals(pwdC)) {
            String encryptPwd = md5.encrypt(pwd);
            CRUD.insertUser(cc,name,user,encryptPwd,rol,TRUE);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Creación usuario");
            alert.setContentText("El usuario "+ user + " fue creado");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Creación usuario");
            alert.setContentText("El usuario "+ user + " no se fue creado");
            alert.showAndWait();
        }
    }
}
