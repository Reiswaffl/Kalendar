package GUI;


import XML.Reader;
import XML.Writer;
import javafx.fxml.FXML;

import java.io.IOException;

import Logic.Logic;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GUIController{

    public GUI gui;

    //Views
    @FXML private Label datetime;
    @FXML private Label currentUser;
    @FXML private Label currentDay;
    @FXML private Label day1;
    @FXML private Label day2;
    @FXML private Label day3;
    @FXML private Label day4;
    @FXML private Label day5;
    @FXML private Label day6;
    @FXML private Label currentDayInfo;
    @FXML private Label day1Info;
    @FXML private Label day2Info;
    @FXML private Label day3Info;
    @FXML private Label day4Info;
    @FXML private Label day5Info;
    @FXML private Label day6Info;


    // Methods
    public void setGui(GUI gui){
        Logic.Update();
        Logic.daysInMonth[0] = 31;
        Logic.daysInMonth[1] = 28;
        Logic.daysInMonth[2] = 31;
        Logic.daysInMonth[3] = 30;
        Logic.daysInMonth[4] = 31;
        Logic.daysInMonth[5] = 30;
        Logic.daysInMonth[6] = 31;
        Logic.daysInMonth[7] = 31;
        Logic.daysInMonth[8] = 30;
        Logic.daysInMonth[9] = 31;
        Logic.daysInMonth[10] = 30;
        Logic.daysInMonth[11] = 31;
        initialize();
        this.gui = gui;
    }
    @FXML
    public void initialize(){
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        datetime.setText(Logic.getDateTime());
        currentUser.setText(Logic.getCurrentUser());
        currentDayInfo.setText(Logic.getDayInfo(0));
        day1Info.setText(Logic.getDayInfo(1));
        day2Info.setText(Logic.getDayInfo(2));
        day3Info.setText(Logic.getDayInfo(3));
        day4Info.setText(Logic.getDayInfo(4));
        day5Info.setText(Logic.getDayInfo(5));
        day6Info.setText(Logic.getDayInfo(6));
        Logic.DaysInOrder();
        String[] daynames = Logic.DaysInOrder();
        currentDay.setText(daynames[0]);
        day1.setText(daynames[1]);
        day2.setText(daynames[2]);
        day3.setText(daynames[3]);
        day4.setText(daynames[4]);
        day5.setText(daynames[5]);
        day6.setText(daynames[6]);
        currentUser.setText(Logic.getCurrentUser());
    }

    @FXML
    public void AddAppointment(javafx.event.ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("AddAppointmentScene.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Termin hinzufügen");
        window.setScene(addAppiontmentScene);
        window.show();
    }
    @FXML
    public void AppointmentRequest(javafx.event.ActionEvent event) throws IOException {
        Parent appointmentRequest = FXMLLoader.load(getClass().getResource("AppointmentRequest.fxml"));
        Scene appointmentRequestScene = new Scene(appointmentRequest);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Terminanfrage");
        window.setScene(appointmentRequestScene);
        window.show();
    }
    @FXML
    public void getNextFreeDay(javafx.event.ActionEvent event ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nächster freier Tag");
        alert.setHeaderText(null);
        alert.setContentText("Funktioniert noch nicht ");
        alert.showAndWait();
    }
    @FXML
    public void AddPermAppointment(javafx.event.ActionEvent event) throws IOException{
        Parent permAppointment = FXMLLoader.load(getClass().getResource("AddPermanentAppointment.fxml"));
        Scene appointmentRequestScene = new Scene(permAppointment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Permanenttermin");
        window.setScene(appointmentRequestScene);
        window.show();
    }

    @FXML void SwitchUser(javafx.event.ActionEvent event) throws IOException {
        Parent addAppiontment = FXMLLoader.load(getClass().getResource("SwitchUserScene.fxml"));
        Scene addAppiontmentScene = new Scene(addAppiontment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppiontmentScene);
        window.setTitle("Nutzer wechseln");
        window.show();
    }
}

