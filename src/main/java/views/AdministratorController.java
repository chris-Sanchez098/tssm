package views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.CRUD;
import model.User;
import tssm.App;
import java.net.URL;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {
    @FXML
    private TextField tfSearch;
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
    private Button bUpdateUser;
    @FXML
    private Button bUpdateTb;
    private ObservableList<User> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setOnlyNum(tfSearch);
        this.colCC.setCellValueFactory(new PropertyValueFactory<User, String>("cc"));
        this.colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        this.colUser.setCellValueFactory(new PropertyValueFactory<User, String>("user"));
        this.colPwd.setCellValueFactory(new PropertyValueFactory<User, String>("pwd"));
        this.colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    private void listUsers(ActionEvent event) {
        if(event.getSource() == bUpdateTb) {
            items = CRUD.getUsers("");
            this.tbUsers.setItems(items);
            bUpdateTb.setText("Actualizar");
        } else {
            String cc = tfSearch.getText();
            ObservableList<User> items = CRUD.getUsers(cc);
            this.tbUsers.refresh();
            this.tbUsers.setItems(items);
        }
    }

    @FXML
    public void selectModify(ActionEvent event) {
        User user = this.tbUsers.getSelectionModel().getSelectedItem();
        if(event.getSource() == bUpdateUser && user != null) {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateUser.fxml"));
                Parent root = loader.load();
                UpdateUserController updateView = loader.getController();
                updateView.initAttributes(items, user);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modificación usuario");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                this.tbUsers.refresh();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un usuario");
            alert.showAndWait();
        }
    }

    @FXML
    private void launch(ActionEvent event) {
        App.openStage("/views/createUser","Creación usuarios");
    }

    /**
     * Restrict a textField to only accept numbers
     * @param textField to restrict
     */
    private void setOnlyNum(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
