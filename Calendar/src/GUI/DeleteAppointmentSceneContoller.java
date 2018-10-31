package GUI;

import Logic.Logic;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DeleteAppointmentSceneContoller implements Initializable{

    @FXML private TextField dayinput;
    @FXML private TextField start;
    @FXML private  TextField input;
    @FXML private MenuButton repetition;
    @FXML private Label head;
    @FXML private Label normal;
    @FXML private Label required;
    @FXML private Label dayinputhead;
    @FXML private Label inputhead;
    @FXML private Label perm;
    @FXML private Label starthead;
    @FXML private Label repetitionhead;
    @FXML private Button back;
    @FXML private Button confirm;
    Repetition rep;

    public void initialize(URL location, ResourceBundle resources) {
        repetition.setText("wöchentlich");
        rep = Repetition.WEEKLY;
        inputhead.setTextFill(Color.rgb(0,102,200));
        head.setTextFill(Color.rgb(0,102,200));
        normal.setTextFill(Color.rgb(0,102,200));
        dayinputhead.setTextFill(Color.rgb(0,102,200));
        perm.setTextFill(Color.rgb(0,102,200));
        repetitionhead.setTextFill(Color.rgb(0,102,200));
        required.setTextFill(Color.rgb(0,102,200));
        starthead.setTextFill(Color.rgb(0,102,200));
        back.setTextFill(Color.rgb(255,255,255));
        back.setStyle("-fx-background-color: #0066C8;");
        confirm.setStyle("-fx-background-color: #0066C8;");
        confirm.setTextFill(Color.rgb(255,255,255));
        repetition.setStyle("-fx-background-color: #0066C8;");
        repetition.setTextFill(Color.rgb(255,255,255));
    }

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
    public void back(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();
    }
    @FXML
    public void confirm(ActionEvent event) throws IOException{
        //do some cool stuff
       String sdate = dayinput.getText().replace(" ","");
       String sinput = input.getText().replaceAll(" ","");
       if(sdate.length() == 0) sdate = null;
       if(sinput.length() == 0) sinput = null;
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
        if(week().equals("Fehler, Tag nicht Vorhanden")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");

            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText(week());
            alert.showAndWait();
        }else{
            String ret = Logic.removeAppointment(sdate,sinput,start.getText(),rp,week());
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
