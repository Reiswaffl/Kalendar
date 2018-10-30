package GUI;


import XML.Reader;
import XML.Writer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import Logic.Logic;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    @FXML private Text currentDayInfo;
    @FXML private Text day1Info;
    @FXML private Text day2Info;
    @FXML private Text day3Info;
    @FXML private Text day4Info;
    @FXML private Text day5Info;
    @FXML private Text day6Info;
    @FXML private Button addAppointment;
    @FXML private Button addPermanentAppointment;
    @FXML private Button getNextFreeDay;
    @FXML private Button timetable;
    @FXML private Button dayPreview;
    @FXML private Button freeTime;
    @FXML private Button SwitchUser;


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
        //Coloursetup
        addAppointment.setStyle("-fx-background-color: #0066C8;");
        addPermanentAppointment.setStyle("-fx-background-color: #0066C8;");
        getNextFreeDay.setStyle("-fx-background-color: #0066C8;");
        timetable.setStyle("-fx-background-color: #0066C8;");
        dayPreview.setStyle("-fx-background-color: #0066C8;");
        freeTime.setStyle("-fx-background-color: #0066C8;");
        SwitchUser.setStyle("-fx-background-color: #0066C8;");
        datetime.setTextFill(Color.rgb(0,102,200));
        currentDay.setTextFill(Color.rgb(0,102,200));
        day1.setTextFill(Color.rgb(0,102,200));
        day2.setTextFill(Color.rgb(0,102,200));
        day3.setTextFill(Color.rgb(0,102,200));
        day4.setTextFill(Color.rgb(0,102,200));
        day5.setTextFill(Color.rgb(0,102,200));
        day6.setTextFill(Color.rgb(0,102,200));

        addAppointment.setTextFill(Color.rgb(255,255,255));
        addPermanentAppointment.setTextFill(Color.rgb(255,255,255));
        getNextFreeDay.setTextFill(Color.rgb(255,255,255));
        timetable.setTextFill(Color.rgb(255,255,255));
        dayPreview.setTextFill(Color.rgb(255,255,255));
        freeTime.setTextFill(Color.rgb(255,255,255));
        SwitchUser.setTextFill(Color.rgb(255,255,255));
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        datetime.setText(Logic.getDateTime());
        currentUser.setText(Logic.getCurrentUser());
        try {
            currentDayInfo.setText(Logic.getDayInfo(0, "null", false));
            day1Info.setText(Logic.getDayInfo(1, "null", false));

            day2Info.setText(Logic.getDayInfo(2, "null", false));
            day3Info.setText(Logic.getDayInfo(3, "null", false));
            day4Info.setText(Logic.getDayInfo(4, "null", false));
            day5Info.setText(Logic.getDayInfo(5, "null", false));
            day6Info.setText(Logic.getDayInfo(6, "null", false));
        }catch (Exception e){}
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
        Logic.setup();
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
    public void AddTimeTable(javafx.event.ActionEvent event) throws IOException {
        Parent appointmentRequest = FXMLLoader.load(getClass().getResource("AddTimeTableScene.fxml"));
        Scene appointmentRequestScene = new Scene(appointmentRequest);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Stundenplan hizufügen");
        window.setScene(appointmentRequestScene);
        window.show();
    }
    @FXML
    public void deleteAppointment(javafx.event.ActionEvent event ) throws IOException {
        Parent appointmentRequest = FXMLLoader.load(getClass().getResource("DeleteAppointmentScene.fxml"));
        Scene appointmentRequestScene = new Scene(appointmentRequest);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Termin löschen");
        window.setScene(appointmentRequestScene);
        window.show();
    }
    @FXML
    public void AddPermAppointment(javafx.event.ActionEvent event) throws IOException{
        Parent permAppointment = FXMLLoader.load(getClass().getResource("AddPermanentAppointmentScene.fxml"));
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

    public void DayPrewiev(ActionEvent event) throws IOException {
        Parent permAppointment = FXMLLoader.load(getClass().getResource("DayReviewScene.fxml"));
        Scene appointmentRequestScene = new Scene(permAppointment);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Tagesvorschau");
        window.setScene(appointmentRequestScene);
        window.show();
    }

    public void FreeTime(ActionEvent event) throws IOException {
        Parent appointmentRequest = FXMLLoader.load(getClass().getResource("FreeTimeScene.fxml"));
        Scene appointmentRequestScene = new Scene(appointmentRequest);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setTitle("Freizeit definieren");
        window.setScene(appointmentRequestScene);
        window.show();
    }
}

