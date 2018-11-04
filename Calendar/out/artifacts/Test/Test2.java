package Test;
import Logic.Logic;
import static org.junit.jupiter.api.Assertions.assertEquals;

import XML.Reader;
import XML.Writer;
import org.junit.jupiter.api.Test;


import javax.xml.transform.TransformerException;

/**
 * NI = no interference -> all inputs should work
 * I = interference -> all inputs shouldn't work
 */
public class Test2 {
    @Test
    public void switchUserI(){
        //Users with a name equals the method-input should't exist
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User15"));
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User16"));
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User17"));
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User18"));
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User19"));
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User20"));
        assertEquals("Nutzername noch nicht eingetragen",Logic.SwitchUser("User21"));
    }
    @Test
    public void addUserI() throws TransformerException {
        //Users with a name equals the method-input should exist
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User1"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User2"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User3"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User4"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User5"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User6"));
        assertEquals("Name bereits vorhanden!",Logic.AddUser("User7"));
    }
    @Test
    public void addAppointmentEndBeforeStartI() throws TransformerException {
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","12:30","11:20","Test1",null,false,false));
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","11:30","11:20","Test1",null,false,false));
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","13:30","11:20","Test1",null,false,false));
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","14:30","11:20","Test1",null,false,false));
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","18:30","11:20","Test1",null,false,false));
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","12:30","11:20","Test1",null,false,false));
        assertEquals("Ende liegt vor Start",Logic.AddAppiontment("181118","15:30","11:20","Test1",null,false,false));
    }
    @Test
    public void addAppointmentDriverDoesntExistI() throws TransformerException {
        //driver should't exist yet; appointments with this time line shouldn't exist
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        assertEquals("Die Begleitperson exestiert leider noch nicht. ",Logic.AddAppiontment("181118","20:30","21:30","TestDriverDoesntExist","doesntExist",false,false));
        assertEquals("Die Begleitperson exestiert leider noch nicht. ",Logic.AddAppiontment("181118","20:30","21:30","TestDriverDoesntExist","usr",false,false));
        assertEquals("Die Begleitperson exestiert leider noch nicht. ",Logic.AddAppiontment("181118","20:30","21:30","TestDriverDoesntExist","r",false,false));
        assertEquals("Die Begleitperson exestiert leider noch nicht. ",Logic.AddAppiontment("181118","20:30","21:30","TestDriverDoesntExist","a",false,false));
    }

    @Test
    public void addPermanentAppointmentStartBeforeEndI(){
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        assertEquals("Ende liegt vor Start",Logic.AddPermanentAppointment("12:30","00:10","StartBeforeEnd","WEELLY","MONDAY",null));
        //assertEquals("Ende liegt vor Start",Logic.AddPermanentAppointment("14:30","14:31","StartBeforeEnd","WEELLY","MONDAY",null));
        assertEquals("Ende liegt vor Start",Logic.AddPermanentAppointment("00:30","00:10","StartBeforeEnd","WEELLY","MONDAY",null));
        assertEquals("Ende liegt vor Start",Logic.AddPermanentAppointment("20:30","00:10","StartBeforeEnd","WEELLY","MONDAY",null));

    }
    @Test void addAppointmentAppointmentExistI() throws TransformerException {
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        //assertEquals(null,Logic.AddAppiontment("181118","12:30","13:30","Test1",null,false,false));
        //assertEquals("Der Zeitraum ist leider schon belegt \n 12:30 - 13:30",Logic.AddAppiontment("181118","12:30","12:40","Test1",null,false,false));
    }
}