package GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Logic.Logic;

import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SwitchUserSceneController implements Initializable {
    @FXML private TextField select;
    @FXML private Label selectedUser;
    @FXML private Button selectButton;
    @FXML private Button addButton;
    @FXML private Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        selectedUser.setText(Logic.getCurrentUser());
        selectedUser.setTextFill(Color.rgb(0,102,200));
        selectButton.setStyle("-fx-background-color: #0066C8;");
        selectButton.setTextFill(Color.rgb(255,255,255));
        addButton.setStyle("-fx-background-color: #0066C8;");
        addButton.setTextFill(Color.rgb(255,255,255));
        back.setStyle("-fx-background-color: #0066C8;");
        back.setTextFill(Color.rgb(255,255,255));
    }
    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();
    }

    @FXML
    public void handleSelect(){
        String ret = Logic.SwitchUser(select.getText());
        if(ret != null){
            //Fehler
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");
            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText(ret);
            alert.showAndWait();
        }else{
            selectedUser.setText(select.getText());
        }
        select.clear();
    }
    @FXML
    public void handleAdd() throws TransformerException {
        selectedUser.setText(select.getText());
        String exception = Logic.AddUser(select.getText());
        if(exception.equals("Name bereits vorhanden!")){
            //Fehler
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");
            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText("Name bereits vorhanden!");
            alert.showAndWait();
        }else{
            selectedUser.setText(select.getText());
        }
        select.clear();
        //add to XML-File
    }

}
