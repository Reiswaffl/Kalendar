package Logic;

import XML.*;

import javax.xml.transform.TransformerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextClass{
    public static void main(String [ ] args) throws TransformerException {
    Logic Logic = new Logic();
    Logic.reader = new Reader();
    Logic.writer = new Writer();
    Logic.addLearningTime("181027","Informatik");
    }

    private static String getDate(){
        Date date = new Date();
        DateFormat dayFormat = new SimpleDateFormat("dd");
        String day = dayFormat.format(date);

        DateFormat monthFormat = new SimpleDateFormat("MM");
        String month = monthFormat.format(date);

        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(date);
        return day +"." + month + "." + year;
    }
}