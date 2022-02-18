package views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CRUD;
import model.User;
import tssm.App;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {

    @FXML
    private TableView<User> tbUsers;
    @FXML
    private TableColumn<User, String> colCC;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colUser;
    @FXML
    private TableColumn<User, String> colPwd;
    @FXML
    private TableColumn<User, String> colRol;
    @FXML
    private TableColumn<User, Boolean> colStatus;
    @FXML
    private Button bUpdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
            private String cc;
    private String name;
    private String user;
    private String pwd;
    private String rol;
    private Boolean status;
         */
        this.colCC.setCellValueFactory(new PropertyValueFactory<User, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        this.colUser.setCellValueFactory(new PropertyValueFactory<User, String>("user"));
        this.colPwd.setCellValueFactory(new PropertyValueFactory<User, String>("pwd"));
        this.colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        /*
        for (User sr: items) {
            System.out.println(sr.getName());
            System.out.println(sr.getUser());
            System.out.println(sr.getCc());
            System.out.println(sr.getPwd());
            System.out.println(sr.getRol());
            System.out.println(sr.getStatus());
        }
        */

    }

    @FXML
    private void listUsers(ActionEvent event) {
        ObservableList<User> items = CRUD.getUsers();
        this.tbUsers.setItems(items);
    }

    @FXML
    private void launch(ActionEvent event) {
        App.openStage("/views/createUser","Creación usuarios");
    }

    @FXML
    private void updateStage(ActionEvent event){
        App.openStage("/views/updateUser","Modificación de usuarios");
    }
}
