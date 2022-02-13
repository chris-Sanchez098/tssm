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

    public void cleanGUI() {
        tfName.setText("");
        tfUser.setText("");
        tfCC.setText("");
        pfPwd.setText("");
        pfPwdConfirm.setText("");
    }

    /**
     * Verifica que todos los campos necesarios esten llenos
     * @return boolean
     */
    public boolean validad() {
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
        if(validad()) {
            int cc = Integer.parseInt(tfCC.getText());
            String name = tfName.getText();
            String rol = cbRol.getSelectionModel().getSelectedItem();
            String pwd = pfPwd.getText();
            String pwdC = pfPwdConfirm.getText();
            String user = tfUser.getText();

            if(User.checkPwd(pwd) && pwd.equals(pwdC) && !pwd.equals(user)) {
                String encryptPwd = MD5.encrypt(pwd);
                CRUD.insertUser(cc,name,user,encryptPwd,rol,true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Creación usuario");
                alert.setContentText("El usuario "+ user + " no se fue creado");
                alert.showAndWait();
            }
        } else {
            lError.setText("Verifique que los campos esten llenos");
        }
    }
}
