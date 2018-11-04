package Logic;


import ReturnValues.PermAppointments;
import ReturnValues.ReturnValue;
import XML.Reader;
import XML.Writer;
import javafx.fxml.Initializable;


import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.*;

/**
 * @brief This class represents the Logic of the program. All proofs, that need to be made, are done here. This class delivers an interface between XML an GUI
 */
public final class Logic {
    public static Writer writer;
    public static Reader reader;
    public static int[] daysInMonth = new int[12];

    //getter to get information for the LogicWindow

    /**
     * @brief returns current date
     * @return String: date
     */
    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //HH:mm:ss
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * @brief checks, if an Appointment is possible, or if there is already on. If its possible it adds this one. If not it returns an Errormessage
     * @param date date of the appointment
     * @param start start of the appointment
     * @param end end of the appointment
     * @param content content of the appointment
     * @param driver possible driver (person that has that event aswell)
     * @param learningTime if the program should calculate learning-time or not
     * @param famEvent if the program is for the whole family or not
     * @return String: possible Errormessage
     * @throws TransformerException
     */
    public static String AddAppiontment(String date,String start,String end, String content, String driver, boolean learningTime, boolean famEvent) throws TransformerException {
        if (date == null) return "Eingabe nicht komplett";
        if (!reader.getUnlocked())
            return "Es muss erst ein Nutzer erstellt werden, bevor es möglich ist Termine einzutragen";
        if (famEvent) {
            //famEvent: highest priotrity
            ArrayList<String> users = reader.getUserList();
            int sthour = Integer.parseInt(start.substring(0, 2));
            int stminutes = Integer.parseInt(start.substring(3, 5));
            int enhour = Integer.parseInt(end.substring(0, 2));
            int enminutes = Integer.parseInt(end.substring(3, 5));
            for (int i = 0; i < users.size(); i++) {
                ArrayList<String> s = reader.getDayInformation(users.get(i), Integer.parseInt(date)).getStart();
                ArrayList<String> e = reader.getDayInformation(users.get(i), Integer.parseInt(date)).getEnd();
                for (int c = 0; c < s.size(); c++) {
                    int starthour = Integer.parseInt(s.get(c).substring(0, 2));
                    int startminutes = Integer.parseInt(s.get(c).substring(3, 5));
                    int endhour = Integer.parseInt(e.get(c).substring(0, 2));
                    int endminutes = Integer.parseInt(e.get(c).substring(3, 5));

                    if ((sthour < starthour && enhour < starthour) || (sthour > endhour && enhour > endhour)) {
                    } else if (starthour == sthour) {
                        if ((stminutes < startminutes && enhour < startminutes) || (sthour > endminutes && enhour > endminutes)) {

                        } else {
                            // Appointment in the time

                        }
                    } else {
                        // Appointment in the time
                    }
                }
                writer.AddPeriod(users.get(i), date, start, end, content, "true");
                return null;
            }
        }
        if (driver != null && reader.isRegistered(driver) == false) {
            System.out.println("Begleitperson");
            return "Die Begleitperson exestiert leider noch nicht. ";
        }
        String s = null;
        String e = null;
        String sm = null;
        String em = null;
        try {
            s = start.substring(0, start.length() - 3);
            e = end.substring(0, end.length() - 3);
            sm = start.substring(start.length()-2,start.length());
            em = end.substring(end.length()-2,end.length());
            System.out.println(sm + "       " + em);
            if (Integer.parseInt(s) > Integer.parseInt(e) || (Integer.parseInt(s) == Integer.parseInt(e)&& Integer.parseInt(em ) < Integer.parseInt(sm))) {
                return "Ende liegt vor Start";
            }
        } catch (NumberFormatException r) {
            return "Fehlerhafte Eingabe";
        }
        ReturnValue periods = reader.getDayInformation(reader.getCurrentUser(), Integer.parseInt(date));
        ArrayList<String> sp = periods.getStart();
        ArrayList<String> ep = periods.getEnd();

        for (int i = 0; i < sp.size(); i++) {
            String ps = sp.get(i).substring(0, sp.get(i).length() - 3);
            String pe = ep.get(i).substring(0, ep.get(i).length() - 3);
            String psm = sp.get(i).substring(start.length()-2,start.length());
            String pem = ep.get(i).substring(start.length()-2,start.length());
            System.out.println(sm + "       " + psm);
            System.out.println(sp.get(i));
            if(Integer.parseInt(e) < Integer.parseInt(ps)){continue;}
            else if(Integer.parseInt(s) > Integer.parseInt(pe)){continue;}
            else if(Integer.parseInt(e) == Integer.parseInt(ps) && Integer.parseInt(em) <= Integer.parseInt(psm)){continue;}
            else if(Integer.parseInt(s) == Integer.parseInt(pe) && Integer.parseInt(sm) >= Integer.parseInt(pem) ){continue;}
            else{
                return "Der Zeitraum ist leider schon belegt \n" + sp.get(i) + " - " + ep.get(i);
            }

        }
        if (driver != null){
            ReturnValue driverperiods = reader.getDayInformation(driver, Integer.parseInt(date));
        ArrayList<String> spd = driverperiods.getStart();
        ArrayList<String> epd = driverperiods.getEnd();

        for (int i = 0; i < sp.size(); i++) {
            String ps = spd.get(i).substring(0, spd.get(i).length() - 3);
            String pe = epd.get(i).substring(0, epd.get(i).length() - 3);
            String psm = spd.get(i).substring(start.length()-2,start.length());
            String pem = epd.get(i).substring(start.length()-2,start.length());
            if(Integer.parseInt(e) < Integer.parseInt(ps)){continue;}
            else if(Integer.parseInt(s) > Integer.parseInt(pe)){continue;}
            else if(Integer.parseInt(e) == Integer.parseInt(ps) && Integer.parseInt(em) <= Integer.parseInt(psm)){continue;}
            else if(Integer.parseInt(s) == Integer.parseInt(pe) && Integer.parseInt(sm) >= Integer.parseInt(pem) ){continue;}
            else{
                return "Der Zeitraum ist leider schon belegt \n" + spd.get(i) + " - " + epd.get(i);
            }

        }
        }
        writer.AddPeriod(reader.getCurrentUser(), date, start,end,content,"false");
        if(driver != null) {
            writer.AddPeriod(driver, date, start, end, content, "false");
        }

        if(learningTime){
            for(int i = -5; i < 0; i++){
                // here comes the real shit biatch
            }
        }
        return null;

    }

    /**
     * @brief checks, if an permanent Appointment is possible, or if there is already on. If its possible it adds this one. If not it returns an Errormessage
     * @param start start of the appointment
     * @param end end of the appointment
     * @param content content of the appointment
     * @param repetition repetition of the appointment (DAILY, WEEKLY, MONTHLY or YEARLY)
     * @param input fixed point where the appointment is supposed to repeat. e.g MONDAY (at WEEKLY) or 03 (at MONTHLY)
     * @param driver possiblle driver (person that has that event aswell
     * @return
     */
    public static String AddPermanentAppointment(String start, String end, String content, String repetition, String input, String driver){
        if(!reader.getUnlocked()) return "Es muss erst ein Nutzer erstellt werden, bevor es möglich ist Termine einzutragen";
        PermAppointments permAppointments = reader.getPemAppointments(reader.getCurrentUser());
        String s = start.substring(0,start.length()-3);
        String e = end.substring(0,end.length()-3);
        String sm = start.substring(start.length()-2,start.length());
        String em = end.substring(end.length()-2,end.length());
        try{
        if(Integer.parseInt(s) > Integer.parseInt(e)){
            return "Ende liegt vor Start";
        }
        }catch (NumberFormatException exeption){
            return "Fehlerhafte Eingabe";}
        if(permAppointments != null) {

            ArrayList<String> sp = permAppointments.getStart();
            ArrayList<String> ep = permAppointments.getEnd();
            ArrayList<String> wd = permAppointments.getWeekday();
            for (int i = 0; i < sp.size(); i++) {
                String ps = sp.get(i).substring(0, sp.get(i).length() - 3);
                String pe = ep.get(i).substring(0, ep.get(i).length() - 3);
                String psm = sp.get(i).substring(start.length()-2,start.length());
                String pem = ep.get(i).substring(start.length()-2,start.length());
                if(Integer.parseInt(e) < Integer.parseInt(ps)&&wd.equals(input)){continue;}
                else if(Integer.parseInt(s) > Integer.parseInt(pe)&&wd.equals(input)){continue;}
                else if(Integer.parseInt(e) == Integer.parseInt(ps) && Integer.parseInt(em) <= Integer.parseInt(psm) ){continue;}
                else if(Integer.parseInt(s) == Integer.parseInt(pe) && Integer.parseInt(sm) >= Integer.parseInt(pem) ){continue;}
                else if(!wd.equals(input)){continue;}
                else{
                    return "Der Zeitraum ist leider schon belegt \n" + sp.get(i) + " - " + ep.get(i);
                }

            }
        }
        String sh = null;
        String eh = null;

        try {
            sh = start.substring(0, start.length() - 3);
            eh = end.substring(0, end.length() - 3);
            sm = start.substring(start.length()-2,start.length());
            em = end.substring(end.length()-2,end.length());
            System.out.println(sm + "       " + em);
            if (Integer.parseInt(sh) > Integer.parseInt(eh) || (Integer.parseInt(sh) == Integer.parseInt(eh)&& Integer.parseInt(em ) < Integer.parseInt(sm))) {
                return "Ende liegt vor Start";
            }
        } catch (NumberFormatException r) {
            return "Fehlerhafte Eingabe";
        }
        PermAppointments permAppointmentsDriver = reader.getPemAppointments(driver);
        if(permAppointmentsDriver != null) {
            ArrayList<String> spd = permAppointmentsDriver.getStart();
            ArrayList<String> epd = permAppointmentsDriver.getEnd();
            ArrayList<String> wdd = permAppointmentsDriver.getWeekday();

            for (int i = 0; i < spd.size(); i++) {
                String ps = spd.get(i).substring(0, spd.get(i).length() - 3);
                String pe = epd.get(i).substring(0, epd.get(i).length() - 3);
                String psm = spd.get(i).substring(start.length()-2,start.length());
                String pem = epd.get(i).substring(start.length()-2,start.length());
                System.out.println(spd.get(i));
                if(Integer.parseInt(e) < Integer.parseInt(ps)&&wdd.equals(input)){continue;}
                else if(Integer.parseInt(s) > Integer.parseInt(pe)&&wdd.equals(input)){continue;}
                else if(Integer.parseInt(e) == Integer.parseInt(ps) && Integer.parseInt(em) <= Integer.parseInt(psm)){continue;}
                else if(Integer.parseInt(s) == Integer.parseInt(pe) && Integer.parseInt(sm) >= Integer.parseInt(pem)){continue;}
                else if(!wdd.equals(input)) continue;
                else{
                    return "Der Zeitraum ist leider schon belegt \n" + spd.get(i) + " - " + epd.get(i);
                }
            }
        }
        writer.AddPermAppointment(reader.getCurrentUser(),start,end,repetition,input,content);
        if(permAppointmentsDriver != null) {
            writer.AddPermAppointment(driver, start, end, repetition, input, content);
        }
        return null;
    }

    /**
     * @brief deletes an permanent Appointment, that already exist. If it is School (learning) it has to be proven, that it is an school-appointment to delete if (see AddTimeTableScene)
     * @param start start of the appointment
     * @param weekday fixed point, where the Appointment repeats (see AppPermAppointment).
     * @param repetition repetition of the Appointment
     * @param learning if it is school or not
     * @return String: possible to return Errormessage
     */
    public static String deletePermanentAppointment(String start, String weekday,String repetition, boolean learning){
        if(!reader.getUnlocked()) return "Es muss erst ein Nutzer erstellt werden, bevor das löschen von Terminen möglich ist";
        if(learning){
            PermAppointments perm = reader.getPemAppointments(reader.getCurrentUser());
            ArrayList<String> apps = perm.getContent();
            for(int i = 0; i < apps.size(); i++){
                if(apps.get(i).contains("Schule:")) writer.removePermAppointment(reader.getCurrentUser(),start,weekday, repetition);
            }
        }else {
            writer.removePermAppointment(reader.getCurrentUser(), start, weekday, repetition);
        }
        return null;
    }

    /**
     * @brief deletes an Appointment, that already exists
     * @param start start of the Appointment
     * @param date date of the Appointment
     * @return String: possible Errormessage
     */
    public static String deleteNode(String start,String date){
        if(!reader.getUnlocked()) return "Es muss erst ein Nutzer erstellt werden, bevor das löschen von Terminen möglich ist";
        boolean done = writer.removeNode(reader.getCurrentUser(),date,start);
        if(done) return null;
        return "Leider ist ein Fehler beim Löschen aufgetreten. Der Termin ist nicht vorhanden";
    }

    /**
     * @brief There a two ways to get the information of a day: by direct dayinput, or by saying how much das the day is away from today. The program will search for information depending on the input.
     * @param add How much days the searched day is away from now
     * @param dateInput date to search for
     * @param isDate search direct date or not
     * @return String: possible Errormessage
     * @throws ParseException
     */
    public static String getDayInfo(int add, String dateInput, boolean isDate) throws ParseException {
        System.out.println("Date:" + dateInput);
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

            if(dateInput.length() == 6) dateInput = "20" + dateInput;
            String dateString = format("%d-%d-%d",Integer.parseInt(dateInput.substring(0,4)),Integer.parseInt(dateInput.substring(4,6)),Integer.parseInt(dateInput.substring(6,8)));
            System.out.println("DateString" + dateString);
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
            System.out.println("HI2");
        }
        return ret;
    }

    /**
     * @brief same as getDayInfo, just with the start and end, not with the content of the Appointments
     * @param add --
     * @param dateInput --
     * @param isDate --
     * @return String: possible Errormessage
     * @throws ParseException
     */
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
            if(dateInput.length() == 6) dateInput = "20" + dateInput;
            String dateString = format("%d-%d-%d",Integer.parseInt(dateInput.substring(0,4)),Integer.parseInt(dateInput.substring(4,6)),Integer.parseInt(dateInput.substring(6,8)));
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

    /**
     * @brief calculares the learning-times
     * @param dateInput date of the test or whatever you want to learn for
     * @param subject Name of the subject the test is written in
     * @return String: possible Errormessage
     * @throws TransformerException
     */
    public static String addLearningTime(String dateInput,String subject ) throws TransformerException {
        if(!reader.getUnlocked()) return "Es muss erst ein Nuter erstellt werden, bevor das Ermitteln der Lernzeit möglich ist";
        //setup
        double hours = 0;
       PermAppointments permAppointments = reader.getPemAppointments(reader.getCurrentUser());
        //for the 7 days before the class
        int hour = 8;
        int minutes = 0;
        for(int i = -7; i < 0; i++) {


            ReturnValue appointments = reader.getDayInformation(reader.getCurrentUser(),Integer.parseInt(DayMonth(i)));
            ArrayList<String> appStart = appointments.getStart();
            ArrayList<String> appEnd = appointments.getEnd();
            ArrayList<String> permEnd = null;
            ArrayList<String> permStart = null;
            if(permAppointments != null){
                permStart = permAppointments.getStart();
                permEnd = permAppointments.getEnd();
            }
            boolean free = true;
            for(int c = 0; c < appStart.size(); c++){
                int shour = Integer.parseInt(appStart.get(c).toString().substring(0,2));
                int ehour = Integer.parseInt(appEnd.get(c).toString().substring(0,2));
                int sminutes = Integer.parseInt(appStart.get(c).toString().substring(3,5));
                int eminutes = Integer.parseInt(appEnd.get(c).toString().substring(3,5));

                if(hour <= shour && (hour +1) > ehour) free = false;
                if(hour < shour && hour < ehour) free = false;
                if(hour > shour && hour > ehour) free = false;
                if((hour+1) == shour && minutes > sminutes) free = false;
                if(hour == ehour && minutes < eminutes ) free = false;
                if(hour == shour && minutes == sminutes) free = false;
            }
            if(permAppointments != null) {
                for (int c = 0; c < permStart.size(); i++) {
                    int shour = Integer.parseInt(permStart.get(c).toString().substring(0, 2));
                    int ehour = Integer.parseInt(permEnd.get(c).toString().substring(0, 2));
                    int sminutes = Integer.parseInt(permStart.get(c).toString().substring(3, 5));
                    int eminutes = Integer.parseInt(permEnd.get(c).toString().substring(3, 5));

                    if (hour <= shour && (hour + 1) > ehour) free = false;
                    if (hour < shour && hour < ehour) free = false;
                    if (hour > shour && hour > ehour) free = false;
                    if ((hour + 1) == shour && minutes > sminutes) free = false;
                    if (hour == ehour && minutes < eminutes) free = false;
                    if (hour == shour && minutes == sminutes) free = false;
                }
            }
            if(free) {
                String starth = Integer.toString(hour);
                if (starth.length() == 1) starth = "0" + starth;
                String startm = Integer.toString(minutes);
                if (startm.length() == 1) startm = "0" + startm;

                String endh = Integer.toString(hour + 1);
                if (endh.length() == 1) endh = "0" + endh;
                writer.AddPeriod(reader.getCurrentUser(), DayMonth(i,dateInput), starth + ":" + startm, endh + ":" + startm, "Lernen:" + subject,"false");
                hours += 1;
                System.out.println("Eingetragen");
            }else if(hour <= 19 && minutes <= 30){
                if(minutes == 0) minutes = 30; i--;
                if(minutes == 30) minutes = 0; hour++;i--;

            }
        }
        return "Es wurden " + hours + " Stunden Lernzeit engetragen";
    }

    /**
     * @brief decies id a normal or a permanent Appointment is supposed to be deleted
     * @param date date of Appointment (only for normal Appointments)
     * @param input
     * @param start start of the Appointment (required)
     * @param repetition repetition of the Appointment (only for permanent Appointments)
     * @param weekday  fixed point, where the Appointment repeats (see AppPermAppointment).(only for permanent Appointments)
     * @return
     */
    public static String removeAppointment(String date,String input , String start,String repetition, String weekday){
        if(!reader.getUnlocked()) return "Es muss erst ein Nutzer erstellt werden, bevor das löschen von Terminen möglich ist";
        if(date != null && input != null) return "Fehlerhafte Eingabe";
        if((date == null && input == null)|| start == null) return "Fehlerhaft Eingabe";
        if(date != null){
            System.out.println(date);
            date = date.replace(".","");
            String day = date.substring(0,2);
            String month = date.substring(2, 4);
            String year = date.substring(4, 6);
            date = year + month + day;
            System.out.println(date + "+" + start);
            return deleteNode(date,start);

        }else if (input != null){
            return deletePermanentAppointment(start, weekday,repetition,false);
        }
        return "Nicht vollständige Eingabe";
    }

    /**
     * @brief Adds Appointment with special writer "Freizeit"
     * @param date date of Appointment (only for normal Appointments)
     * @param input
     * @param start start of the Appointment (required)
     * @param end end of the Appointment (required)
     * @param repetition repetition of the Appointment (only for permanent Appointments)
     * @param weekday fixed point, where the Appointment repeats (see AppPermAppointment).(only for permanent Appointments)
     * @return
     * @throws TransformerException
     */
    public static String addFreeTime(String date, String input, String start, String end, String repetition, String weekday) throws TransformerException {
        if(!reader.getUnlocked()) return "Es muss erst ein Nutzer erstellt werden, bevor das hinzufügen von Freizeit möglich ist";
        if(date != null && input != null) return "Es ist nicht möglich einen permanenten und normalen Termin gleichzeitig einzutragen";
        if(date == null && input == null) return "Fehlerhafte Eingabe";
        if(date != null){
            date = date.replace(".","");
            String day = date.substring(0,2);
            String month = date.substring(2, 4);
            String year = date.substring(4, 6);
            date = year + month + day;
            return AddAppiontment(date,start,end,"Freizeit",null,false,false);
        }else if(input != null){
            AddPermanentAppointment(start,end,"Freizeit",repetition,input,null);
        }
        return "Fehler bei der Eingabe";
    }

    //Change user

    /**
     * @brief checks the possibility to switch the user
     * @param user user, that the program is supposed to switch to
     * @return String: poss ible Errormessage
     */
    public static String SwitchUser(String user){
        if(!reader.getUnlocked()){
            return "Es muss zuerst ein Nutzer erzeugt werden";
        }else {
            ArrayList<String> s = reader.getUserList();
            for (int i = 0; i < s.size(); i++) {
                if (user.equals(s.get(i))) {
                    writer.SwitchUser(user);
                    return null;
                }
            }
        }
        return "Nutzername noch nicht eingetragen";
    }

    /**
     * @brief Adds a user
     * @param username name of the new user
     * @return String: possible Errormessage
     * @throws TransformerException
     */
    public static String  AddUser(String username) throws TransformerException {
        if(!reader.getUnlocked()){
            writer.setUnlocked();
        }
        return writer.AddUser(username);
    }

    /**
     * @brief forwards writer
     * @return String: current User
     */
    public static String getCurrentUser(){
        return writer.getCurrentUser();
    }

    /**
     * @brief filters dateinput
     * @param date dateinput
     * @return date or null, if no filtering is possible
     */
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
    public static boolean getUnlocked(){return reader.getUnlocked();}

    /**
     * @brief sorts day beginning withe current day. e.g. "Samstag" - "Sonntag" - "Montag" and so on (only for Mainwindow)
     * @return String[] days in the rigth order
     */
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

    /**
     * @brief transforms the date in a String, the program can work with
     * @return String: date
     */
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

    /**
     * @brief if there is an add with would run out of the month. e.g day: 30.10.yyyy add = 5. it will cast into the next month --> 05.11.yyy
     * @param add days away from current day
     * @return String: new date
     */
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
            // day ss in last month
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

    /**
     * @brief same as upper, but with dateinput to start from
     * @param add --
     * @param date --
     * @return
     */
    private static String DayMonth(int add,String date) {
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

    /**
     * @brief for mainwindow
     * @param dayname
     * @return
     */
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

    /**
     * @brief for mainwindow
     * @param id
     * @return
     */
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

    /**
     * @brief returns date, but with "." only to show in GUI
     * @return date
     */
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

    /**
     * @brief check if the permanent Appoints need to be shown
     * @param compare comparevalue (only for WEEKLY)
     * @param input point of the repetition. e.g. MONDAY or "03"
     * @param repetition repetition of permanent Appointment
     * @return boolean: true, when Appointment need to be shown. false when not
     */
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

    /**
     * @brief searches current month and year to make it possible to delete the last month (see XMLReader)
     */
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

    /**
     * @brief forwards writer
     */
    public static void Update(){
        writer.Update();
    }

}
 