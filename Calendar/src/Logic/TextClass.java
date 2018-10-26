package Logic;

import XML.*;

import javax.xml.transform.TransformerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class TextClass{
    public static void main(String [ ] args) throws TransformerException {
        String dateIinput = "20181030";
        System.out.println("1.   " + dateIinput.substring(0,4));
        System.out.println("2.   " + dateIinput.substring(4,6));
        System.out.println("3.   " + dateIinput.substring(6,8));

    }


}