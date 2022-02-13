package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.CRUD;
import model.MD5;
import model.User;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private Label lError;
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
    private Button bClean;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCB();
    }

    /**
     * inicializa los valores en el ComboBox cbRol
     */
    public void initCB() {
        ObservableList<String> option =
                FXCollections.observableArrayList("Administrador", "Gerente", "Operador");
        cbRol.setItems(option);
    }

    /**
     * Deja la GUI es su estado inicial
     */
    public void cleanGUI() {
        tfName.setText("");
        tfUser.setText("");
        tfCC.setText("");
        pfPwd.setText("");
        pfPwdConfirm.setText("");
        lError.setText("");
    }

    /**
     * Verifica que todos los campos necesarios se encuentren llenos
     * @return boolean
     */
    public boolean checkEmptyField() {
        try {
            Integer.parseInt(tfCC.getText());
            tfName.getText();
            cbRol.getSelectionModel().getSelectedItem();
            pfPwd.getText();
            pfPwdConfirm.getText();
            tfUser.getText();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Muestra un mensaje de error si la contraseña no cumple con la politica,
     * no coincide y es igual al user.
     * @param pwdCheck boolean
     * @param equal boolean
     * @param pwdUser boolean
     */
    public void erroMsg(boolean pwdCheck, boolean equal, boolean pwdUser) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Creación usuario");

        if (!pwdCheck) {
            alert.setContentText("La contraseña no cumple la politica:\n"
                    +"Logitud minima 8 caracteres, debe contener minusculas, mayusculas, numero y simbolos.");
            alert.showAndWait();
        } else if (!equal) {
            alert.setContentText("Las contraseñas no coinciden.");
            alert.showAndWait();
        } else {
            alert.setContentText("Usuario y contraseña no deben ser iguales");
            alert.showAndWait();
        }
    }

    @FXML
    private void cleanError(MouseEvent event) {
        lError.setText("");
    }

    @FXML
    private void clean(ActionEvent event) {
        cleanGUI();
    }

    @FXML
    private void create(ActionEvent event) {
        if(checkEmptyField()) {
            int cc = Integer.parseInt(tfCC.getText());
            String name = tfName.getText().toLowerCase();
            String rol = cbRol.getSelectionModel().getSelectedItem();
            String pwd = pfPwd.getText();
            String pwdC = pfPwdConfirm.getText();
            String user = tfUser.getText().toLowerCase();

            boolean check = User.checkPwd(pwd);
            boolean equal = pwd.equals(pwdC);
            boolean userPwd = !pwd.equals(user);
            if(check && equal && userPwd) {
                String encryptPwd = MD5.encrypt(pwd);
                CRUD.insertUser(cc, name, user, encryptPwd, rol, true);
            } else {
                erroMsg(check,equal,userPwd);
            }
        } else {
            lError.setText("Verifique que los campos esten llenos");
        }
    }
}
