package Logic;


import Logic.TextClass;
import XML.Writer;
import XML.Reader;

import javax.xml.transform.TransformerException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TextClass{
    public static void main(String[] args) throws TransformerException {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        System.out.println(format.format(date));
    }
}

