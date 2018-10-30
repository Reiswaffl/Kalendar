package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Logic.Logic;

import javax.xml.transform.TransformerException;

public class FreeTimeSceneController implements Initializable{
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextField input;
    @FXML private TextField dayinput;
    @FXML private MenuButton repetition;
    @FXML private Label normal;
    @FXML private Label date;
    @FXML private Label required;
    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label head;
    @FXML private Label repetitionhead;
    @FXML private Label perm;
    @FXML private Label inputhead;
    @FXML private Button select;
    @FXML private Button back;

    Repetition rep;
    Logic Logic = new Logic();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        repetition.setText("wöchentlich");
        rep = Repetition.WEEKLY;
        repetition.setStyle("-fx-background-color: #0066C8;");
        repetition.setTextFill(Color.rgb(255,255,255));
        select.setStyle("-fx-background-color: #0066C8;");
        select.setTextFill(Color.rgb(255,255,255));
        back.setStyle("-fx-background-color: #0066C8;");
        back.setTextFill(Color.rgb(255,255,255));
        normal.setTextFill(Color.rgb(0,102,200));
        date.setTextFill(Color.rgb(0,102,200));
        required.setTextFill(Color.rgb(0,102,200));
        startLabel.setTextFill(Color.rgb(0,102,200));
        endLabel.setTextFill(Color.rgb(0,102,200));
        head.setTextFill(Color.rgb(0,102,200));
        repetitionhead.setTextFill(Color.rgb(0,102,200));
        perm.setTextFill(Color.rgb(0,102,200));
        inputhead.setTextFill(Color.rgb(0,102,200));


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
    public void handleYearly() {
        repetition.setText("jährlich");
        rep = Repetition.YEARLY;
    }
    public void handleWeekly(){
        repetition.setText("wöchentlich");
        rep = Repetition.WEEKLY;
    }

    public void back(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();
    }

    public void confirm(ActionEvent event) throws TransformerException {
        String rp = null;
        switch (rep){
            case DAILY:
                rp = "DAILY";
                break;
            case WEEKLY:
                rp = "WEEKLY";
                break;
            case MONTHLY:
                rp = "MONTHLY";
                break;
            case YEARLY:
                rp = "YEARLY";
                break;


        }
        String sdate = dayinput.getText().replace(" ","");
        String sinput = input.getText().replace(" ","");
        if(sdate.length() == 0) sdate = null;
        if(sinput.length() == 0) sinput = null;
        if(week().equals("Fehler, Tag nicht Vorhanden")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");

            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText(week());
            alert.showAndWait();
        }else {
            String ret = Logic.addFreeTime(sdate, sinput, start.getText(), end.getText(), rp, week());
            if(ret != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fehler beim Eintragen");
                //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
                alert.setContentText(ret);
                alert.showAndWait();
            }
        }

    }


    private String week(){
        if(input.getText().equals("Montag")) return "MONDAY";
        if(input.getText().equals("Dienstag")) return "TUESDAY";
        if(input.getText().equals("Mittwoch")) return "WEDNESDAY";
        if(input.getText().equals("Donnerstag")) return "THUERSDAY";
        if(input.getText().equals("Freitag")) return "FRIDAY";
        if(input.getText().equals("Samstag")) return "SATURDAY";
        if(input.getText().equals("Sonntag")) return "SUNDAY";
        return "Fehler, Tag nicht Vorhanden";
    }
}
