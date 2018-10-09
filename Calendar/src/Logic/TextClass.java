package Logic;



import ReturnValues.ReturnValue;

import XML.Reader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TextClass{
    private static int[] daysInMonth = new int[12];
    public static void main(String[] args) throws ParseException {
        String time = "08:30";
            int hour = Integer.parseInt(time.substring(0,2));
            int minutes = Integer.parseInt(time.substring(3,5));
        System.out.println(hour+"----"+minutes);
    }

    private static String DayMonth(int add) {
        System.out.println("ADD:" + add);
        String date = TransformToString();
        int totaldate = Integer.parseInt(date);
        int month = totaldate % 10000;
        month /= 100;
        int day = totaldate % 100;

        if (daysInMonth[month - 1] < day + add) {
            System.out.println("HI" + daysInMonth[month-1]);
            // day is in next month
            month += 1;
            day += add;
            day -= daysInMonth[month - 1];
            day += 1;
        } else if (day + add < 1){
            // day os in lst month
            month -= 1;
            day = daysInMonth[month-1] + (day - add);
        }else{
            day = day +add;
        }
        String smonth = Integer.toString(month);
        String sday = Integer.toString(day);
        if(smonth.length() == 1){
            smonth = "0" + smonth;
        }
        if(sday.length() == 1){
            sday = "0" +sday;
        }
        int year = totaldate / 10000;
        String syear = Integer.toString(year);
        date = syear + smonth + sday;
        System.out.println(date);
        return date;
    }

    private static String TransformToString(){
        Date date = new Date();

        DateFormat dayFormat = new SimpleDateFormat("dd");
        String day = dayFormat.format(date);

        DateFormat monthFormat = new SimpleDateFormat("MM");
        String month = monthFormat.format(date);

        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(date);
        int iyear = Integer.parseInt(year);
        iyear %= 100;
        year = Integer.toString(iyear);

        if (year.length() == 1) {
            year = "0" + year;
        }
        String totaldate = year + month + day;
        return totaldate;
    }
}

