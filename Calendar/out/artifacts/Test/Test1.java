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
public class Test1 {
    @Test
    public void addAppointmentTestNI() throws TransformerException {
        //no appointments created yet (XML-File unlocked, one User exist, but got no Appointments
        Logic Logic = new Logic();
        Logic.reader = new Reader();
        Logic.writer = new Writer();
        assertEquals(null,Logic.AddAppiontment("181118","12:30","13:30","Test1",null,false,false));
        assertEquals(null,Logic.AddAppiontment("181120","13:31","14:30","Test2",null,false,false));
        assertEquals(null,Logic.AddAppiontment("181120","14:32","15:30","Test3",null,false,false));
        assertEquals(null,Logic.AddAppiontment("181120","15:33","16:30","Test4",null,false,false));
        assertEquals(null,Logic.AddAppiontment("181120","16:34","17:30","Test5",null,false,false));
        assertEquals(null,Logic.AddAppiontment("181120","17:35","18:30","Test6",null,false,false));
        assertEquals(null,Logic.AddAppiontment("181120","18:36","19:30","Test7",null,false,false));
    }
    @Test
    public void addpermAppointmentTestNI(){
        // no permanent appontments created yet (XML-File is unlocked, one User exist, but got no permanent Appointments
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        assertEquals(null,Logic.AddPermanentAppointment("12:30","13.30","permTest1","WEEKLY","MONDAY",null));
        assertEquals(null,Logic.AddPermanentAppointment("13:30","14.30","permTest2","WEEKLY","MONDAY",null));
        assertEquals(null,Logic.AddPermanentAppointment("14:30","15.30","permTest3","WEEKLY","MONDAY",null));
        assertEquals(null,Logic.AddPermanentAppointment("15:30","16.30","permTest3","WEEKLY","MONDAY",null));
        assertEquals(null,Logic.AddPermanentAppointment("16:30","17.30","permTest4","WEEKLY","MONDAY",null));
        assertEquals(null,Logic.AddPermanentAppointment("17:30","18.30","permTest5","WEEKLY","MONDAY",null));
        assertEquals(null,Logic.AddPermanentAppointment("18:30","19.30","permTest6","WEEKLY","MONDAY",null));
    }
    @Test
    public void addUserNI() throws TransformerException {
        //only one User exists
        Logic Logic = new Logic();
        Logic.reader = new Reader();
        Logic.writer = new Writer();
        assertEquals(null, Logic.AddUser("User1"));
        assertEquals(null, Logic.AddUser("User2"));
        assertEquals(null, Logic.AddUser("User3"));
        assertEquals(null, Logic.AddUser("User4"));
        assertEquals(null, Logic.AddUser("User5"));
        assertEquals(null, Logic.AddUser("User6"));
        assertEquals(null, Logic.AddUser("User7"));
    }
    @Test
    public void deleteAppointmentNI(){
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        Logic.SwitchUser("User");
        assertEquals(null,Logic.deleteNode("12:30","181118"));
        assertEquals(null,Logic.deleteNode("13:30","181118"));
        assertEquals(null,Logic.deleteNode("14:30","181118"));
        assertEquals(null,Logic.deleteNode("15:30","181118"));
        assertEquals(null,Logic.deleteNode("16:30","181118"));
        assertEquals(null,Logic.deleteNode("17:30","181118"));
        assertEquals(null,Logic.deleteNode("18:30","181118"));

    }
    @Test
    public void deletePermAppointmentNI(){
        Logic Logic = new Logic();
        Logic.writer = new Writer();
        Logic.reader = new Reader();
        Logic.SwitchUser("User");
        assertEquals(null,Logic.deletePermanentAppointment("12:30","MONDAY","WEEKLY",false));
        assertEquals(null,Logic.deletePermanentAppointment("13:30","MONDAY","WEEKLY",false));
        assertEquals(null,Logic.deletePermanentAppointment("14:30","MONDAY","WEEKLY",false));
        assertEquals(null,Logic.deletePermanentAppointment("15:30","MONDAY","WEEKLY",false));
        assertEquals(null,Logic.deletePermanentAppointment("16:30","MONDAY","WEEKLY",false));
        assertEquals(null,Logic.deletePermanentAppointment("17:30","MONDAY","WEEKLY",false));
        assertEquals(null,Logic.deletePermanentAppointment("18:30","MONDAY","WEEKLY",false));
    }
}
