package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        mainWindow();
    }
    public void mainWindow(){
        try {
            FXMLLoader loader = new FXMLLoader(GUI.class.getResource("MainWindow.fxml"));
            AnchorPane pane = loader.load();

            primaryStage.setMinHeight(100);
            primaryStage.setMinWidth(100);
            primaryStage.setTitle("Menü");

            GUIController guiCon = loader.getController();
            guiCon.setGui(this);
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
