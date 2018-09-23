package Logic;


import Logic.TextClass;
import XML.Writer;
import XML.Reader;

import javax.xml.transform.TransformerException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TextClass{
    public static void main(String[] args) throws TransformerException, ParseException {
    String dateInput = "20181015";
    String dateString = String.format("%d-%d-%d",Integer.parseInt(dateInput.substring(0,1)),Integer.parseInt(dateInput.substring(2,3)),Integer.parseInt(dateInput.substring(4,5)));
    Date date = new SimpleDateFormat("yyyy-MM-d").parse(dateString);
    String dayofWeek = new SimpleDateFormat("EEEE", Locale.GERMAN).format(date);
    System.out.println(dayofWeek);
    }
}

