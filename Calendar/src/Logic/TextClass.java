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
    public static void main(String[] args) throws ParseException {
        Logic Logic = new Logic();
        String string = Logic.getDayTimes(0,"181005",false);
        System.out.println(string);
    }
}

