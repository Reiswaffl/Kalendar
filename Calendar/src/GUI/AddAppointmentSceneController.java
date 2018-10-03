package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    String month;
    String monthnumber;
    Logic Logic = new Logic();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void add() throws TransformerException {
        String year = jahr.getText();
        String day = tag.getText();
        String driver = begleiter.getText();
        System.out.println(year + "" + month +"" +day);
        if(year.length() == 4){
            int i = Integer.parseInt(year);
            i %= 100;
            year = Integer.toString(i);
        }
        if(day.length() == 1){
            day = "0" + day;
        }
        String date = year + monthnumber + day;
        String s = Logic.AddAppiontment(date,start.getText(),end.getText(),content.getText(), driver, learningTime.isSelected());
        if(s != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler beim Eintragen");
            //alert.setHeaderText("Es ist leider ein Fehler aufgetreten");
            alert.setContentText(s);
            alert.showAndWait();
        }

        jahr.clear();
        month = "--";
        mb.setText("Monat");
        tag.clear();
        begleiter.clear();


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
