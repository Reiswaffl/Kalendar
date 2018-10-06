package Logic;



import ReturnValues.ReturnValue;

import XML.Reader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TextClass{
    int[] dIM = new int[12];
    dIM[0] =31;
    dIM[1] = 28;
    dIM[2] = 31;
    dIM[3] = 30;
    dIM[4] = 31;
    dIM[5] = 30;
    dIM[6] = 31;
    dIM[7] = 31;
    dIM[8] = 30;
    dIM[9] = 31;
    dIM[10] = 30;
    dIM[11] = 31;
    public static void main(String[] args) throws ParseException {
        Reader reader = new Reader();
        ReturnValue[] periods = new ReturnValue[7];
        for(int i = 0; i < 7; i++){
            periods[i] = reader.getDayInformation(reader.getCurrentUser(), Integer.parseInt(DayMonth(i)));
        }
        for(int i = 0; i < periods.length;i++){
            ArrayList<String> s = periods[i].getStart();
            ArrayList<String> e = periods[i].getEnd();
            ArrayList<String> c = periods[i].getContent();

            for(int b = 0; b < s.size(); i++){
                System.out.println(s.get(b) + " - " + e.get(b) + " - " + c.get(b));
            }
        }
    }

    private static String DayMonth(int add) {
        System.out.println("ADD:" + add);
        String date = TransformToString();
        int totaldate = Integer.parseInt(date);
        int month = totaldate % 10000;
        month /= 100;
        int day = totaldate % 100;

        if (dIM[month - 1] < day + add) {
            System.out.println("HI" + dIM[month-1]);
            // day is in next month
            month += 1;
            day += add;
            day -= dIM[month - 1];
            day += 1;
        } else if (day + add < 1){
            // day os in lst month
            month -= 1;
            day = dIM[month-1] + (day - add);
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

