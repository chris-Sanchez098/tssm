package tssm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/views/logIn"));
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * alert to close the window
     * @param stage stage to close
     */
    private static void exit(Stage stage){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Salir");
        exitAlert.setHeaderText("Vas a salir de la aplicación");
        exitAlert.setContentText("¿Realmente quieres salir?");

        if(exitAlert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    /**
     * open a emergent window
     * @param addressFxml where it is fxml
     * @param title for the window
     */
    public static void openStage(String addressFxml, String title){
        try{
            Scene sceneEmergent;
            sceneEmergent = new Scene(loadFXML(addressFxml));
            Stage stageEmergent = new Stage();
            stageEmergent.initModality(Modality.APPLICATION_MODAL);
            stageEmergent.setScene(sceneEmergent);
            stageEmergent.setResizable(false);
            stageEmergent.setTitle(title);
            stageEmergent.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * close the window then open a new
     * @param addressFxml where it is fxml
     */
    public static void setStage(String addressFxml){
        try{
            Scene newScene;
            newScene = new Scene(loadFXML(addressFxml));
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
            if(!addressFxml.equals("/views/logIn")){
                newStage.setOnCloseRequest(event -> {
                    event.consume();
                    exit(newStage);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}