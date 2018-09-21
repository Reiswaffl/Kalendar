package GUI;

import Logic.Logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DayReview implements Initializable{
    Logic Logic = new Logic();
    @FXML Label time;
    @FXML Label information;
    @FXML TextField dayinput;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        information.setText(Logic.getDayInfo(0,"null",false));
        time.setText(Logic.getDayTimes(0,"null",false));
    }
    public void back(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();
    }

    public void select(ActionEvent event) {
        Logic.handleDate(dayinput.getText());
    }
}
