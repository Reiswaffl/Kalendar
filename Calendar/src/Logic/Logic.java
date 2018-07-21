package Logic;

import ReturnValues.*;
import XML.Reader;
import XML.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import ReturnValues.ReturnValue;
import java.util.ArrayList;

import org.omg.PortableInterceptor.INACTIVE;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerException;

public final class Logic {
    public static Writer writer;
    public static Reader reader;
    public static int[] daysInMonth = new int[12];

    //Getter to get information for the LogicWindow
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //HH:mm:ss
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String AddAppiontment(String date,String start,String end, String content) throws TransformerException {
        String s = start.substring(0,start.length()-3);
        String e = end.substring(0,end.length()-3);
        if(Integer.parseInt(s) > Integer.parseInt(e)){
            return "Ende liegt vor Start";
        }
        ReturnValue periods = reader.GetDayInformation(reader.GetCurrentUser(),Integer.parseInt(date));
        ArrayList<String> sp = periods.GetStart();
        ArrayList<String> ep = periods.GetEnd();

        for(int i = 0; i < sp.size(); i++){
            String ps = sp.get(i).substring(0,sp.get(i).length()-3);
            String pe = ep.get(i).substring(0,ep.get(i).length()-3);
            if(Integer.parseInt(s) >= Integer.parseInt(ps) && Integer.parseInt(s) <= Integer.parseInt(pe))
            {
                return "Der Zeitraum ist leider schon belegt \n" + sp.get(i) + " - " + ep.get(i);
            }
        }
        writer.AddPeriod(reader.GetCurrentUser(), date, start,end,content);
        return null;

    }
    public static String GetDayInfo(int add){
        String date = DayMonth(add);
        ReturnValue returnValue = null;

        if(reader.GetDayInformation(reader.GetCurrentUser(),Integer.parseInt(date))!=null) {
            returnValue = reader.GetDayInformation(reader.GetCurrentUser(), Integer.parseInt(date));
        }
        String ret = "-";
        if(returnValue != null) {
            ArrayList<String> a = returnValue.GetContent();


            for (int i = 0; i < a.size(); i++) {
                if(ret.equals( "-")){
                    ret += a.get(i) + "\n";
                }else{
                    ret += "-" + a.get(i)+"\n";
                }

            }
        }

        return ret;
    }
    public static String GetNextFreeDay(String usr){
        return reader.GetNextFreeDay(usr);
    }
    //Change user
    public static void SwitchUser(String user){
        writer.SwitchUser(user);
    }
    public static String  AddUser(String username) throws TransformerException {
        return writer.AddUser(username);
    }
    public static String GetCurrentUser(){
        return writer.GetCurrentUser();
    }


    public static String[] DaysInOrder(){
        String[] daynames = new String[7];
        Date date = new Date();
        DateFormat format2=new SimpleDateFormat("EEEE");
        String currentDay=format2.format(date);

        int startid = GetID(currentDay);
        int id = 0;
        for(int i = startid; i < 7; i++){
            daynames[id] = GetDayName(i);
            id++;
        }
        for(int i = 0; i < startid; i++){
            daynames[id] = GetDayName(i);
            id++;
        }

        return daynames;
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
    private static String DayMonth(int add){
        String date = TransformToString();
        int totaldate = Integer.parseInt(date);
        int month = totaldate % 10000;
        month /= 100;
        int day = totaldate % 100;

        if(daysInMonth[month-1] < day + add ){
            // day is in next month
            month += 1;
            day += add;
            day -= daysInMonth[month-1];
            day += 1;
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
        return date;
    }
    private static int GetID(String dayname){
        switch (dayname){
            case "Montag":
                return 0;
            case "Dienstag":
                return 1;
            case "Mittwoch":
                return 2;
            case "Donnerstag":
                return 3;
            case "Freitag":
                return 4;
            case "Samstag":
                return 5;
            case "Sonntag":
                return 6;
            default:
                return 10;
        }
    }
    private static String GetDayName(int id){
        switch (id){
            case 0:
                return "Montag";
            case 1:
                return "Dienstag";
            case 2:
                return "Mittwoch";
            case 3:
                return "Donnerstag";
            case 4:
                return "Freitag";
            case 5:
                return "Samstag";
            case 6:
                return "Sonntag";
            default:
                return null;
        }
    }


    public static void Update(){
        writer.Update();
    }   
}
