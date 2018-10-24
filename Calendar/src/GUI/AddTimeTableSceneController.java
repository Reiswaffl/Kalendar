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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTimeTableSceneController implements Initializable {
    @FXML
    private Label weekday;
    @FXML
    private TextField s1;
    @FXML
    private TextField s2;
    @FXML
    private TextField s3;
    @FXML
    private TextField s4;
    @FXML
    private TextField s5;
    @FXML
    private TextField s6;
    @FXML
    private TextField s7;
    @FXML
    private TextField s8;
    @FXML
    private TextField s9;
    @FXML
    private TextField s10;
    @FXML
    private TextField s11;
    Logic Logic = new Logic();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML public void back(ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.show();
    }
    @FXML public void next(ActionEvent event ) throws IOException{
        String input = "null";
        if(weekday.getText().equals("MONTAG")) {
            weekday.setText("DIENSTAG");
            input = "MONDAY"; }
        else if(weekday.getText().equals("DIENSTAG")) {
            weekday.setText("MITTWOCH");
            input ="TUESDAY";
        }
        else if(weekday.getText().equals("MITTWOCH")) {
            weekday.setText("DONNERSTAG");
            input = "WEDNESDAY";
        }
        else if(weekday.getText().equals("DONNERSTAG")) {
            weekday.setText("FREITAG");
            input = "THURSDAY";
        }
        else if(weekday.getText().equals("FREITAG")){
            weekday.setText("---");
            input = "FRIDAY";
        }

        if(!s1.getText().equals("")) Logic.AddPermanentAppointment("08:00","08:45","Schule:"+s1.getText(),"WEEKLY",input,null);
        if(!s2.getText().equals("")) Logic.AddPermanentAppointment("08:45","09:30","Schule:"+s2.getText(),"WEEKLY",input,null);
        if(!s3.getText().equals("")) Logic.AddPermanentAppointment("09:45","10:30","Schule:"+s3.getText(),"WEEKLY",input,null);
        if(!s4.getText().equals("")) Logic.AddPermanentAppointment("10:30","11:15","Schule:"+s4.getText(),"WEEKLY",input,null);
        if(!s5.getText().equals("")) Logic.AddPermanentAppointment("11:35","12:20","Schule:"+s5.getText(),"WEEKLY",input,null);
        if(!s6.getText().equals("")) Logic.AddPermanentAppointment("12:20","13:05","Schule:"+s6.getText(),"WEEKLY",input,null);
        if(!s7.getText().equals("")) Logic.AddPermanentAppointment("13:15","14:00","Schule:"+s7.getText(),"WEEKLY",input,null);
        if(!s8.getText().equals("")) Logic.AddPermanentAppointment("14:00","14:45","Schule:"+s8.getText(),"WEEKLY",input,null);
        if(!s9.getText().equals("")) Logic.AddPermanentAppointment("14:45","15:30","Schule:"+s9.getText(),"WEEKLY",input,null);
        if(!s10.getText().equals("")) Logic.AddPermanentAppointment("15:30","16:15","Schule:"+s10.getText(),"WEEKLY",input,null);
        if(!s11.getText().equals("")) Logic.AddPermanentAppointment("16:15","17:00","Schule:"+s11.getText(),"WEEKLY",input,null);

        s1.clear();
        s2.clear();
        s3.clear();
        s4.clear();
        s5.clear();
        s6.clear();
        s7.clear();
        s8.clear();
        s9.clear();
        s10.clear();
        s11.clear();
        if(input.equals("FRIDAY")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");
            alert.setContentText("Stundenplan eingetragen");
            alert.showAndWait();

            Parent addAppiontment = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene addAppiontmentScene = new Scene(addAppiontment);
            Stage window = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            window.setScene(addAppiontmentScene);
            window.show();
        }
    }
    @FXML public void delete(ActionEvent event) throws  IOException{
        String input = "null";
        if(weekday.getText().equals("MONTAG")) {
            input = "MONDAY"; }
        else if(weekday.getText().equals("DIENSTAG")) {
            input ="TUESDAY";
        }
        else if(weekday.getText().equals("MITTWOCH")) {
            input = "WEDNESDAY";
        }
        else if(weekday.getText().equals("DONNERSTAG")) {
            input = "THURSDAY";
        }
        else if(weekday.getText().equals("FREITAG")){
            input = "FRIDAY";
        }
        Logic.deletePermanentAppointment("08:00",input,"WEEKLY");
        Logic.deletePermanentAppointment("08:45",input,"WEEKLY");
        Logic.deletePermanentAppointment("09:45",input,"WEEKLY");
        Logic.deletePermanentAppointment("10:30",input,"WEEKLY");
        Logic.deletePermanentAppointment("11:35",input,"WEEKLY");
        Logic.deletePermanentAppointment("12:20",input,"WEEKLY");
        Logic.deletePermanentAppointment("13:05",input,"WEEKLY");
        Logic.deletePermanentAppointment("14:00",input,"WEEKLY");
        Logic.deletePermanentAppointment("14:45",input,"WEEKLY");
        Logic.deletePermanentAppointment("15:30",input,"WEEKLY");
        Logic.deletePermanentAppointment("16:15",input,"WEEKLY");
    }
}
