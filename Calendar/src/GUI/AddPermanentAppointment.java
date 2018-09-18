package GUI;

import com.sun.xml.internal.ws.api.FeatureConstructor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

enum Repetition{
    WEEKLY,
    DAILY,
    MONTHLY
}
enum Weekday{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

public class AddPermanentAppointment {

    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextField begleiter;
    @FXML private MenuButton repetition;
    @FXML private MenuButton weekday;
    Repetition rep;
    Weekday weekd;

    @FXML
    public void handleWeekly(){
        rep = Repetition.WEEKLY;
    }
    @FXML
    public void handleDaily(){
        rep = Repetition.DAILY;
    }
    @FXML
    public void handleMonthly(){
        rep = Repetition.MONTHLY;
    }
    @FXML
    public void handleMonday(){
        weekd = Weekday.MONDAY;
    }
    @FXML
    public void handleTuesday(){
        weekd = Weekday.TUESDAY;
    }
    @FXML
    public void handleWednesday(){
        weekd = Weekday.WEDNESDAY;
    }
    @FXML
    public void handleThursday(){
        weekd = Weekday.THURSDAY;
    }
    @FXML
    public void handleFriday(){
        weekd = Weekday.FRIDAY;
    }
    @FXML
    public void handleSaturday(){
        weekd = Weekday.SATURDAY;
    }
    @FXML
    public void handleSunday(){
        weekd = Weekday.SUNDAY;
    }
    @FXML
    public void back(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();

    }
    @FXML
    public void add(){

    }
}
