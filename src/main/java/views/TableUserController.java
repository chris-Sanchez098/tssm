package views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CRUD;
import model.User;
import model.Validation;

import java.net.URL;
import java.util.ResourceBundle;

public class TableUserController implements Initializable {
    @FXML
    private TextField searchTextF;
    @FXML
    private TableView<User> userTableView;
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
    private Button updateButton;
    private ObservableList<User> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Validation.setOnlyNum(searchTextF);
        this.colCC.setCellValueFactory(new PropertyValueFactory<User, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        this.colUser.setCellValueFactory(new PropertyValueFactory<User, String>("user"));
        this.colPwd.setCellValueFactory(new PropertyValueFactory<User, String>("pwd"));
        this.colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        items = CRUD.getUsers("");
        this.userTableView.setItems(items);
    }

    @FXML
    private void updateEvent(ActionEvent event) {
        if(event.getSource() == updateButton) {
            items = CRUD.getUsers("");
            this.userTableView.setItems(items);
        } else {
            String cc = searchTextF.getText();
            ObservableList<User> items = CRUD.getUsers(cc);
            this.userTableView.refresh();
            this.userTableView.setItems(items);
        }
    }
}
