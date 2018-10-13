package Logic;

import XML.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextClass{
    public static void main(String [ ] args){
        String day = getDate().substring(0,2);
        String month = getDate().substring(3,5);
        System.out.println(day + "----" + month);
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