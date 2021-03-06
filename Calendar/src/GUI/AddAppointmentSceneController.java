package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import Logic.Logic;

import javax.xml.transform.TransformerException;

public class AddAppointmentSceneController implements Initializable {
    @FXML private TextField jahr;
    @FXML private TextField tag;
    @FXML private TextField start;
    @FXML private TextField end;
    @FXML private TextField content;
    @FXML private TextField begleiter;
    @FXML private MenuButton mb;
    @FXML private CheckBox learningTime;
    @FXML private CheckBox famEvent;
    @FXML private AnchorPane anchor;
    @FXML private Button back;
    @FXML private Button add;
    @FXML private Label head;
    @FXML private Label monthhead;
    @FXML private Label dayhead;
    @FXML private Label starthead;
    @FXML private Label endhead;
    @FXML private Label learninghead;
    @FXML private Label famhead;
    @FXML private Label driverhead;
    @FXML private Label contenthead;
    @FXML private Label yearhead;
    String month;
    String monthnumber;
    Logic Logic = new Logic();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        head.setTextFill(Color.rgb(0,102,200));
        starthead.setTextFill(Color.rgb(0,102,200));
        endhead.setTextFill(Color.rgb(0,102,200));
        monthhead.setTextFill(Color.rgb(0,102,200));
        famhead.setTextFill(Color.rgb(0,102,200));
        dayhead.setTextFill(Color.rgb(0,102,200));
        learninghead.setTextFill(Color.rgb(0,102,200));
        driverhead.setTextFill(Color.rgb(0,102,200));
        contenthead.setTextFill(Color.rgb(0,102,200));
        yearhead.setTextFill(Color.rgb(0,102,200));
        add.setStyle("-fx-background-color: #0066C8;");
        add.setTextFill(Color.rgb(255,255,255));
        back.setStyle("-fx-background-color: #0066C8;");
        back.setTextFill(Color.rgb(255,255,255));
        mb.setText("Januar");
        month = "Januar";
        monthnumber = "01";
    }
    @FXML
    public void add() throws TransformerException {
        if( (learningTime.isSelected() && famEvent.isSelected()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");
            alert.setContentText("Es kann immer nur eine Checkbox angeclickt werden");
            alert.showAndWait();
        }
        String year = jahr.getText();
        year = year.replace(" ","");
        if(year.length() == 0) year = null;
        String day = tag.getText();
        day = day.replace(" ","");
        if(day.length() == 0) day = null;
        String driver = begleiter.getText();
            System.out.println(year + "" + month + "" + day);

        String date = null;
        if(year != null && day != null){
        if(year.length() == 4){
            try {
                int i = Integer.parseInt(year);
                i %= 100;
                year = Integer.toString(i);
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fehler beim Eintragen");
                //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
                alert.setContentText("Fehlerhafte Eingabe");
                alert.showAndWait();
            }

        }
        if(day.length() == 1){
            day = "0" + day;
        }
        date = year + monthnumber + day;}

        System.out.println(driver.length());
        String s = null;
        if(driver.length() == 0){
             s = Logic.AddAppiontment(date,start.getText(),end.getText(),content.getText(), null, learningTime.isSelected(),famEvent.isSelected());
        }else {
             s = Logic.AddAppiontment(date, start.getText(), end.getText(), content.getText(), driver, learningTime.isSelected(), famEvent.isSelected());
        }
        if(s != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");
            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText(s);
            alert.showAndWait();
        }

        if(s == null && learningTime.isSelected()){
            String l = Logic.addLearningTime(date,content.getText());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fehler beim Eintragen");
                //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
                alert.setContentText(l);
                alert.showAndWait();

        }

        jahr.clear();
        month = "--";
        mb.setText("Januar");
        month = "Januar";
        monthnumber = "01";
        tag.clear();
        content.clear();
        begleiter.clear();
        start.clear();
        end.clear();

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
    public void handleJanuar(){
        month = "Januar";
        monthnumber = "01";
        mb.setText(month);
    }
    @FXML
    public void handleFebruar(){
        month = "Februar";
        monthnumber = "02";
        mb.setText(month);
    }
    @FXML
    public void handleMaerz(){
        month = "Maerz";
        monthnumber = "03";
        mb.setText(month);
    }
    @FXML
    public void handleApril(){
        month = "April";
        monthnumber = "04";
        mb.setText(month);
    }
    @FXML
    public void handleMai(){
        month = "Mai";
        monthnumber = "05";
        mb.setText(month);
    }
    @FXML
    public void handleJuni(){month = "Juni";
        monthnumber = "06";
        mb.setText(month);
        }
    @FXML
    public void handleJuli(){month = "Juli";
        monthnumber = "07";
        mb.setText(month);
        }
    @FXML
    public void handleAugust(){month = "August";
        monthnumber = "08";
        mb.setText(month);
    }
    @FXML
    public void handleSeptember(){
        month = "September";
        monthnumber = "09";
        mb.setText(month);
    }
    @FXML
    public void handleOktober(){month = "Oktober";
        monthnumber = "10";
        mb.setText(month);

    }
    @FXML
    public void handleNovember(){month = "November";monthnumber = "11";mb.setText(month);
    }
    @FXML
    public void handleDezember(){month = "Dezember";monthnumber = "12";mb.setText(month);
    }


}
