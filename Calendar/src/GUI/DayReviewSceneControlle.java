package GUI;

import Logic.Logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DayReviewSceneControlle implements Initializable{
    Logic Logic = new Logic();
    @FXML
    Text time;
    @FXML Text information;
    @FXML TextField dayinput;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            information.setText(Logic.getDayInfo(0, "null", false));
            time.setText(Logic.getDayTimes(0, "null", false));
        }catch (Exception e){}
    }
    public void back(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();
    }

    public void select(ActionEvent event) {
        String date = Logic.handleDate(dayinput.getText());
        System.out.println(date);
        if(date == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");

            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText("Fehler bei der Eingabe, bitte versuchen sie es nocheinmal nach der beschriebene Schreibweise");
            alert.showAndWait();
        }else {
            try {
                information.setText(Logic.getDayInfo(0, date, true));
                time.setText(Logic.getDayTimes(0, date, true));
            }catch (Exception e){}
        }
    }
}
