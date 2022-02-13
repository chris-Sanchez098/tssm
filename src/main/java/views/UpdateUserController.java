package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        if(!newPass.getText().isEmpty() && !newPassCon.getText().isEmpty()){
            if(Objects.equals(newPass.getText(), newPassCon.getText())){
                user.setPwd(newPass.getText());
                System.out.println("Clave aceptada" + user.getPwd());
            }
        }
        if(!newCC.getText().isEmpty()){
            user.setCc(Integer.parseInt(newCC.getText()));
            System.out.println("CC aceptada");
        }
        user.setRol(rol);
        user.setUser(newUser.getText());
        user.setName(newName.getText());

        CRUD.updateUser(user,1004675446);
    }

    @FXML
    public void selectEvent(ActionEvent event){
        rol = newRol.getSelectionModel().getSelectedItem().toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("","Administrador", "Gerente", "Operador");
        newRol.setItems(list);
        rol = "";
        user = CRUD.selectUser(1193075514).get(0);
        currentName.setText(user.getName());
        currentUser.setText(user.getUser());
        currentCC.setText(String.valueOf(user.getCc()));
        currentRol.setText(user.getRol());

    }
}
