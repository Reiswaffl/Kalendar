package GUI;

import Logic.Logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

enum Repetition{
    WEEKLY,
    DAILY,
    MONTHLY,
    YEARLY
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

public class AddPermanentAppointment implements Initializable {

    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextField content;
    @FXML private TextField begleiter;
    @FXML private MenuButton repetition;
    @FXML private MenuButton weekday;
    @FXML private TextField day;
    @FXML private TextField month;
    Repetition rep;
    Weekday weekd;
    Logic Logic = new Logic();



    @FXML
    public void handleWeekly(){
        repetition.setText("wöchentlich");
        rep = Repetition.WEEKLY;
    }
    @FXML
    public void handleDaily(){
        repetition.setText("täglich");
        rep = Repetition.DAILY;
    }
    @FXML
    public void handleMonthly(){
        repetition.setText("monatlich");
        rep = Repetition.MONTHLY;
    }
    @FXML
    public void handleYearly(){
        repetition.setText("jährlich");
        rep = Repetition.YEARLY;
    }
    @FXML
    public void handleMonday(){
        weekday.setText("Montag");
        weekd = Weekday.MONDAY;
    }
    @FXML
    public void handleTuesday(){
        weekday.setText("Dienstag");
        weekd = Weekday.TUESDAY;
    }
    @FXML
    public void handleWednesday(){
        weekday.setText("Mittwoch");
        weekd = Weekday.WEDNESDAY;
    }
    @FXML
    public void handleThursday(){
        weekday.setText("Donnerstag");
        weekd = Weekday.THURSDAY;
    }
    @FXML
    public void handleFriday(){
        weekday.setText("Freitag");
        weekd = Weekday.FRIDAY;
    }
    @FXML
    public void handleSaturday(){
        weekday.setText("Samstag");
        weekd = Weekday.SATURDAY;
    }
    @FXML
    public void handleSunday(){
        weekday.setText("Sonntag");
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
        String rp = "null";
        String input = "null";
        switch (rep){
            case DAILY:
                rp = "DAILY";
                input ="----";
                break;
            case WEEKLY:
                rp = "WEEKLY";
                input = week();
                break;
            case MONTHLY:
                rp = "MONTHLY";
                input = day.getText()   ;
                break;
            case YEARLY:
                rp = "YEARLY";
                String d = day.getText();
                String m = month.getText();
                if(d.length() == 1) d = "0" + d;
                if(m.length() == 1) d = "0" + m;
                input = d+m;
                break;

        }

        String ret = Logic.AddPermanentAppointment(start.getText(),end.getText(),content.getText(),rp,input,begleiter.getText());

        if(ret != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");

            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText(ret);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    private String week(){
        String wk = "null";
        switch (weekd){
            case MONDAY:
                wk = "MONDAY";
                break;
            case TUESDAY:
                wk = "TUESDAY";
                break;
            case WEDNESDAY:
                wk = "WEDNESDAY";
                break;
            case THURSDAY:
                wk ="THURSDAY";
                break;
            case FRIDAY:
                wk = "FRIDAY";
                break;
            case SATURDAY:
                wk = "SATURDAY";
                break;
            case SUNDAY:
                wk = "SUNDAY";
                break;
        }
        return wk;
    }

}
