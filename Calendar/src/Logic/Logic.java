package Logic;


import ReturnValues.PermAppointments;
import ReturnValues.ReturnValue;
import XML.Reader;
import XML.Writer;

import javax.xml.transform.TransformerException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static java.lang.String.*;

public final class Logic {
    public static Writer writer;
    public static Reader reader;
    public static int[] daysInMonth = new int[12];

    //getter to get information for the LogicWindow
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //HH:mm:ss
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String AddAppiontment(String date,String start,String end, String content, String driver, boolean learningTime, boolean famEvent) throws TransformerException {
        if(famEvent){
            //famEvent: highest priotrity
            ArrayList<String> users = reader.getUserList();
            int sthour = Integer.parseInt(start.substring(0,2));
            int stminutes = Integer.parseInt(start.substring(3,5));
            int enhour = Integer.parseInt(end.substring(0,2));
            int enminutes = Integer.parseInt(end.substring(3,5));

            for(int i = 0; i < users.size();i++){
                ArrayList<String> s = reader.getDayInformation(users.get(i),Integer.parseInt(date)).getStart();
                ArrayList<String> e = reader.getDayInformation(users.get(i),Integer.parseInt(date)).getEnd();
                for(int c = 0; c < s.size();c++){
                    int starthour = Integer.parseInt(s.get(c).substring(0, 2));
                    int startminutes = Integer.parseInt(s.get(c).substring(3, 5));
                    int endhour = Integer.parseInt(e.get(c).substring(0, 2));
                    int endminutes = Integer.parseInt(e.get(c).substring(3, 5));

                    if((sthour < starthour && enhour < starthour)||(sthour > endhour && enhour > endhour)) {
                    }else if(starthour == sthour){
                        if((stminutes < startminutes && enhour < startminutes)||(sthour > endminutes && enhour > endminutes)){

                        }else{
                            // Appointment in the time

                        }
                    }else{
                        // Appointment in the time

                    }
                }
                writer.AddPeriod(users.get(i),date,start,end,content);
                return null;
            }
        }
        if(driver != null && reader.isRegistered(driver) == false)
        {
            return "Die Begleitperson exestiert leider noch nicht. ";
        }
        String s = start.substring(0,start.length()-3);
        String e = end.substring(0,end.length()-3);
        if(Integer.parseInt(s) > Integer.parseInt(e)){
            return "Ende liegt vor Start";
        }
        ReturnValue periods = reader.getDayInformation(reader.getCurrentUser(),Integer.parseInt(date));
        ArrayList<String> sp = periods.getStart();
        ArrayList<String> ep = periods.getEnd();

        for(int i = 0; i < sp.size(); i++){
            String ps = sp.get(i).substring(0,sp.get(i).length()-3);
            String pe = ep.get(i).substring(0,ep.get(i).length()-3);
            if(Integer.parseInt(s) >= Integer.parseInt(ps) && Integer.parseInt(s) <= Integer.parseInt(pe))
            {
                return "Der Zeitraum ist leider schon belegt \n" + sp.get(i) + " - " + ep.get(i);
            }
        }
        ReturnValue driverperiods = reader.getDayInformation(driver,Integer.parseInt(date));
        ArrayList<String> spd = driverperiods.getStart();
        ArrayList<String> epd = driverperiods.getEnd();

        for(int i = 0; i < sp.size(); i++){
            String ps = spd.get(i).substring(0,spd.get(i).length()-3);
            String pe = epd.get(i).substring(0,epd.get(i).length()-3);
            if(Integer.parseInt(s) >= Integer.parseInt(ps) && Integer.parseInt(s) <= Integer.parseInt(pe)){
                return "Der Zeitraum ist leider schon belegt \n" + spd.get(i) + " -" + epd.get(i);
            }
        }

        writer.AddPeriod(reader.getCurrentUser(), date, start,end,content);
        writer.AddPeriod(driver,date,start,end,content);

        if(learningTime){
            for(int i = -5; i < 0; i++){
                // here comes the real shit biatch
            }
        }
        return null;

    }
    public static String AddPermanentAppointment(String start, String end, String content, String repetition, String input, String driver){
        PermAppointments permAppointments = reader.getPemAppointments(reader.getCurrentUser());
        String s = start.substring(0,start.length()-3);
        String e = end.substring(0,end.length()-3);

        if(Integer.parseInt(s) > Integer.parseInt(e)){
            return "Ende liegt vor Start";
        }
        if(permAppointments != null) {

            ArrayList<String> sp = permAppointments.getStart();
            ArrayList<String> ep = permAppointments.getEnd();
            ArrayList<String> wd = permAppointments.getWeekday();
            System.out.println(sp.size() + " \n " + sp.get(0));

            for (int i = 0; i < sp.size(); i++) {
                String ps = sp.get(i).substring(0, sp.get(i).length() - 3);
                String pe = ep.get(i).substring(0, ep.get(i).length() - 3);
                if (Integer.parseInt(s) > Integer.parseInt(ps) && Integer.parseInt(s) < Integer.parseInt(pe) && input.equals(wd.get(i))) {
                    return "Der Zeitraum ist leider schon belegt \n" + sp.get(i) + " - " + ep.get(i);
                }
            }
        }

        PermAppointments permAppointmentsDriver = reader.getPemAppointments(driver);
        if(permAppointmentsDriver != null) {
            ArrayList<String> spd = permAppointmentsDriver.getStart();
            ArrayList<String> epd = permAppointmentsDriver.getEnd();
            ArrayList<String> wdd = permAppointmentsDriver.getWeekday();

            for (int i = 0; i < spd.size(); i++) {
                String ps = spd.get(i).substring(0, spd.get(i).length() - 3);
                String pe = epd.get(i).substring(0, epd.get(i).length() - 3);
                if (Integer.parseInt(s) > Integer.parseInt(ps) && Integer.parseInt(s) < Integer.parseInt(pe) && input.equals(wdd.get(i))) {
                    return "Der Zeitraum ist leider schon belegt (bei Beleitperson) \n" + spd.get(i) + " -" + epd.get(i);
                }
            }
        }
        writer.AddPermAppointment(reader.getCurrentUser(),start,end,repetition,input,content);
        if(permAppointmentsDriver != null) {
            writer.AddPermAppointment(driver, start, end, repetition, input, content);
        }
        return null;
    }
    public static String deletePermanentAppointment(String start,String end, String weekday){

        return null;
    }
    public static String getDayInfo(int add, String dateInput, boolean isDate) throws ParseException {
        String date;
        ReturnValue returnValue = null;
        if(!isDate) {
            date = DayMonth(add);
        }else{
            date = dateInput;
        }
        if(reader.getDayInformation(reader.getCurrentUser(),Integer.parseInt(date))!=null) {
            returnValue = reader.getDayInformation(reader.getCurrentUser(), Integer.parseInt(date));
        }
        String ret = "-";
        if(returnValue != null) {
            ArrayList<String> a = returnValue.getContent();


            for (int i = 0; i < a.size(); i++) {
                if(ret.equals( "-")){
                    ret += a.get(i) + "\n";
                }else{
                    ret += "-" + a.get(i)+"\n";
                }

            }
        }
        PermAppointments permAppointments = reader.getPemAppointments(reader.getCurrentUser());
        if(!isDate){
            String[] s = DaysInOrder();
            if(permAppointments != null){
                for(int i = 0; i < permAppointments.getStart().size();i++){
                    if(checkDay(s[add],permAppointments.getWeekday().get(i).toString(),permAppointments.getRepetition().get(i).toString())) {
                        if (ret.equals("-")) {
                            ret += permAppointments.getContent().get(i).toString() + "\n";
                        } else {
                            ret += "-" + permAppointments.getContent().get(i).toString() + "\n";
                        }
                    }

                }
            }
        }else{
            dateInput = "20" + dateInput;
            String dateString = format("%d-%d-%d",Integer.parseInt(dateInput.substring(0,1)),Integer.parseInt(dateInput.substring(2,3)),Integer.parseInt(dateInput.substring(4,5)));
            Date dayNameDate = new SimpleDateFormat("yyyy-MM-d").parse(dateString);
            String dayofWeek = new SimpleDateFormat("EEEE", Locale.GERMAN).format(dayNameDate);
            if(permAppointments != null){
                for(int i = 0; i < permAppointments.getStart().size();i++){
                    if(checkDay(dayofWeek,permAppointments.getWeekday().get(i).toString(),permAppointments.getRepetition().get(i).toString())) {
                        if (ret.equals("-")) {
                            ret += permAppointments.getContent().get(i).toString() + "\n";
                        } else {
                            ret += "-" + permAppointments.getContent().get(i).toString() + "\n";
                        }
                    }

                }
            }
        }
        return ret;
    }
    public static String getDayTimes(int add,String dateInput,boolean isDate) throws ParseException {
        String date;
        ReturnValue returnValue = null;
        if(!isDate){
            date = DayMonth(add);
        }else{
            date = dateInput;
        }
        if(reader.getDayInformation(reader.getCurrentUser(),Integer.parseInt(date))!=null) {
            returnValue = reader.getDayInformation(reader.getCurrentUser(), Integer.parseInt(date));
        }
        String ret = "-";
        if(returnValue != null) {
            ArrayList<String> start = returnValue.getStart();
            ArrayList<String> end = returnValue.getEnd();

            for (int i = 0; i < start.size(); i++) {
                if(ret.equals( "-")){
                    ret += start.get(i) + " - " + end.get(i) + "\n";
                }else{
                    ret += "-" + start.get(i)+ " - " + end.get(i) +"\n";
                }

            }
        }
        PermAppointments permAppointments = reader.getPemAppointments(reader.getCurrentUser());
        if(!isDate){
            String[] s = DaysInOrder();
            if(permAppointments != null){
                for(int i = 0; i < permAppointments.getStart().size();i++){
                    if(checkDay(s[add],permAppointments.getWeekday().get(i).toString(),permAppointments.getRepetition().get(i).toString())) {
                        if (ret.equals("-")) {
                            ret += permAppointments.getStart().get(i).toString() + " - " + permAppointments.getEnd().get(i).toString() + "\n";
                        } else {
                            ret += "-" + permAppointments.getStart().get(i).toString() + " - " + permAppointments.getEnd().get(i).toString() + "\n";
                        }
                    }

                }
            }
        }else{
            dateInput = "20" + dateInput;
            String dateString = format("%d-%d-%d",Integer.parseInt(dateInput.substring(0,1)),Integer.parseInt(dateInput.substring(2,3)),Integer.parseInt(dateInput.substring(4,5)));
            Date dayNameDate = new SimpleDateFormat("yyyy-MM-d").parse(dateString);
            String dayofWeek = new SimpleDateFormat("EEEE", Locale.GERMAN).format(dayNameDate);
            if(permAppointments != null){
                for(int i = 0; i < permAppointments.getStart().size();i++){
                    if(checkDay(dayofWeek,permAppointments.getWeekday().get(i).toString(),permAppointments.getRepetition().get(i).toString())) {
                        if (ret.equals("-")) {
                            ret += permAppointments.getStart().get(i).toString() + " - " + permAppointments.getEnd().get(i).toString() + "\n";
                        } else {
                            ret += "-" + permAppointments.getStart().get(i).toString() + " - " + permAppointments.getEnd().get(i).toString() + "\n";
                        }
                    }

                }
            }
        }
        return ret;
    }
    public static String addLearningTime(String dateInput){
        //setup
        double hours = 0;
        return null;
    }
    public static String getNextFreeDay(String usr){
        Date date = new Date();
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
        return reader.getNextFreeDay(usr,month,day);
    }
    //Change user
    public static void SwitchUser(String user){
        writer.SwitchUser(user);
    }
    public static String  AddUser(String username) throws TransformerException {
        return writer.AddUser(username);
    }
    public static String getCurrentUser(){
        return writer.getCurrentUser();
    }
    public static String handleDate(String date){
        if(date.length() > 10) return null;

        date = date.replaceAll("[a-zA-Z.]","");
        String[] dateparts = date.split("(?<=\\G..)");
        if(dateparts.length == 4){
            dateparts[2] = dateparts[3];
            return dateparts[2] + dateparts[1] + dateparts[0];
        }else if(dateparts.length == 3){
            return dateparts[2] + dateparts[1] + dateparts[0];
        }

        return null;
    }

    public static String[] DaysInOrder(){
        String[] daynames = new String[7];
        Date date = new Date();
        DateFormat format2=new SimpleDateFormat("EEEE");
        String currentDay=format2.format(date);

        int startid = getID(currentDay);
        int id = 0;
        for(int i = startid; i < 7; i++){
            daynames[id] = getDayName(i);
            id++;
        }
        for(int i = 0; i < startid; i++){
            daynames[id] = getDayName(i);
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
    private static String DayMonth(int add) {
        String date = TransformToString();
        int totaldate = Integer.parseInt(date);
        int month = totaldate % 10000;
        month /= 100;
        int day = totaldate % 100;

        if (daysInMonth[month - 1] < day + add) {
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
        return date;
    }
    private static int getID(String dayname){
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
    private static String getDayName(int id){
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
    private static boolean checkDay(String compare,String input,String repetition){
        if(repetition.equals("DAILY"))return true;
        if(repetition.equals("WEEKLY")) {
            if (compare.equals("Montag") && input.equals("MONDAY")) return true;
            if (compare.equals("Dienstag") && input.equals("TUESDAY")) return true;
            if (compare.equals("Mittwoch") && input.equals("WEDNESDAY")) return true;
            if (compare.equals("Donnerstag") && input.equals("THURSDAY")) return true;
            if (compare.equals("Freitag") && input.equals("FRIDAY")) return true;
            if (compare.equals("Samstag") && input.equals("SATURDAY")) return true;
            if (compare.equals("Sonntag") && input.equals("SUNDAY")) return true;
        }else if(repetition.equals("MONTHLY")){
            String day = getDate().substring(0,2);
            if(input.equals(day)) return true;
        }else if(repetition.equals("YEARLY")){
            String day = getDate().substring(0,2);
            String month = getDate().substring(3,5);
            if(input.equals(day+month))return true;
        }
        return false;
    }

    public static void setup(){
        Date date = new Date();
        DateFormat monthFormat = new SimpleDateFormat("MM");
        String month = monthFormat.format(date);
        int imonth = Integer.parseInt(month) -1;
        month = Integer.toString(imonth);
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(date);
        year = year.substring(2,4);
        if(month.length() == 1){
            month = "0" + month;
        }
        writer.setup(year,month);
    }
    public static void Update(){
        writer.Update();
    }

}
