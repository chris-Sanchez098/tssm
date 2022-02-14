package views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.CRUD;
import model.User;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {
    User user;
    String rol;

    @FXML
    private Label currentName;

    @FXML
    private Label currentUser;

    @FXML
    private Label currentCC;

    @FXML
    private Label currentRol;

    @FXML
    private TextField newUser;

    @FXML
    private TextField newName;

    @FXML
    private TextField newCC;

    @FXML
    private ComboBox newRol;

    @FXML
    private TextField newPass;

    @FXML
    private TextField newPassCon;

    @FXML
    public void updatedateEvent(ActionEvent event){
        if(!newCC.getText().isEmpty()){
            user.setCc(newCC.getText());
            System.out.println("CC aceptada");
        }
        user.setRol(rol);
        user.setUser(newUser.getText());
        user.setName(newName.getText());

        if(!newPass.getText().isEmpty() && !newPassCon.getText().isEmpty()){
            if(Objects.equals(newPass.getText(), newPassCon.getText())){
                user.setPwd(newPass.getText());
                if(User.checkPwd(newPass.getText())){
                    CRUD.updateUser(user,1004675446);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Creación usuario");
                    alert.setContentText("El usuario "+ user.getName() + " fue modificado");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Modificar usuario");
                    alert.setContentText("El usuario "+ currentName.getText() + " no fue modificado, la contraseña no cumple los requerimientos");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Modificar usuario");
                alert.setContentText("El usuario "+ currentName.getText() + " no fue modificado, la contraseña no coincide");
                alert.showAndWait();
            }
        }


    }


    @FXML
    private void selectEvent(ActionEvent event){
        rol = newRol.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("","Administrador", "Gerente", "Operador");
        newRol.setItems(list);
        rol = "";
        user = CRUD.selectUser("1193075514").get(0);
        currentName.setText(user.getName());
        currentUser.setText(user.getUser());
        currentCC.setText(String.valueOf(user.getCc()));
        currentRol.setText(user.getRol());
        newCC.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    newCC.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }
}
