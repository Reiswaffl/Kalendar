package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Logic.Logic;

import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;

public class SwitchUserSceneController{
    @FXML private TextField select;
    @FXML private TextField add;
    @FXML private Label selectedUser;


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
        selectedUser.setText(select.getText());
        Logic.SwitchUser(select.getText());
        select.clear();
    }
    @FXML
    public void handleAdd() throws TransformerException {
        selectedUser.setText(add.getText());
        String exception = Logic.AddUser(add.getText());
        if(exception.equals("Name bereits vorhanden!")){
            //Fehler
        }else{
            selectedUser.setText(add.getText());
        }
        add.clear();
        //add to XML-File
    }

}
