package XML;

//Imports
import ReturnValues.*;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.swing.text.AbstractDocument;


public class Reader {
    XMLReader xmlReader;

    public Reader(){
        xmlReader = new XMLReader();
    }
    public ReturnValue getDayInformation(String user, int date) {
        xmlReader.Update();
        ReturnValue returnValue = new ReturnValue();
        int y = date / 10000;
        String idy = FormToString(y);
        NodeList years = null;
        if(xmlReader.getUser(user) != null){
            years = xmlReader.getUser(user).getChildNodes();
        }
        Node year = null;
        if(years != null){
            year = xmlReader.getYearById(idy, years);
        }

        int m = date % 10000;
        m /= 100;
        String idm = FormToString(m);

        Node month = null;
        if (year != null) {
            month = xmlReader.getMonthById(idm, year.getChildNodes());

        } else {
            return returnValue;
        }

        Node day = null;
        if (month != null) {
            day = xmlReader.getDayById(Integer.toString(date), month.getChildNodes());
        } else {
            return returnValue;
        }
        if (day != null) {
            NodeList periods = null;
            if (day.getChildNodes() != null) {
                periods = day.getChildNodes();
            }
            String lastcontent = "";
            if (periods != null) {
                for (int i = 0; i < periods.getLength(); i++) {
                    Node nperiod = periods.item(i);
                    if (nperiod.getNodeType() == Node.ELEMENT_NODE) {
                        Element eperiod = (Element) nperiod;
                        if(lastcontent.equals(eperiod.getTextContent())){

                        }else {
                            returnValue.AddContent(eperiod.getTextContent(), eperiod.getAttribute("start"), eperiod.getAttribute("end"));
                        }
                        lastcontent = eperiod.getTextContent();
                    }
                }
            }
        }
        return  returnValue;
    }
    public PermAppointments getPemAppointments(String user){
        Node usr = xmlReader.getPermUser(user);
        PermAppointments permAppointments = new PermAppointments();
        if(usr != null){
        for(int i = 0; i< usr.getChildNodes().getLength();i++){
            Node nPermAppointment = usr.getChildNodes().item(i);
            if(nPermAppointment.getNodeType() == Node.ELEMENT_NODE){
                Element ePermAppointment = (Element) nPermAppointment;
                permAppointments.add(ePermAppointment.getAttribute("start" ),ePermAppointment.getAttribute("end"),ePermAppointment.getTextContent(),
                        ePermAppointment.getAttribute("weekday"),ePermAppointment.getAttribute("repetition"));
            }
        }
        return permAppointments;
        }
        return null;
    }


    public String getNextFreeDay(String usr,int month, int day ){
        xmlReader.Update();
        Node user = xmlReader.getUser(usr);
        Node year =null;
        if(user != null){
            year = xmlReader.getYearById("18",user.getChildNodes()); //change later to aktuall year (with datetime)
        }
        NodeList months;
        if(year != null){

            months = year.getChildNodes();
            for(int m = 0; m < months.getLength(); m++){
                Node nmonth = months.item(m);
                NodeList days = nmonth.getChildNodes();
                int lastday = 0;
                if(m >= month) {
                    for (int d = 0; d < days.getLength(); d++) {
                        Node nday = days.item(d);
                        if (nday.getNodeType() == Node.ELEMENT_NODE) {
                            Element eday = (Element) nday;
                            int currentday = Integer.parseInt(eday.getAttribute("date"));
                            currentday = currentday % 100;
                            if (currentday - lastday > 1 && d >= day) {
                                //Free day found
                                Element em = (Element) nmonth;
                                Element ey = (Element) year;
                                return (Integer.toString(lastday + 1) + " " + em.getAttribute("name") + " " + "20" + ey.getAttribute("id"));
                            }
                        }

                    }
                }
            }
        }
        return "Gönn dir mal ne Pause!";
    }
    public String getNextFreePeriod(String usr, String mintime, String maxtime, String hours){
        //Not possible, because searches intervall is bigger than the possible one
        xmlReader.Update();
        if(Integer.parseInt(maxtime) - Integer.parseInt(mintime) < Integer.parseInt(hours)){
            return "Leider nicht möglich, der Zeitraum und die Anzahl der gewünschten Stunden ist zu groß!";
        }
        Node user = xmlReader.getUser(usr);
        Node year = xmlReader.getYearById("18",user.getChildNodes());
        NodeList months = year.getChildNodes();
        for(int m = 0; m < months.getLength(); m++){
            Node nmonth = months.item(m);
            NodeList days = nmonth.getChildNodes();
            int lasttime = Integer.parseInt(mintime);
            for(int d = 0; d < days.getLength(); d++){
                Node nday = days.item(d);
                NodeList periods = nday.getChildNodes();

                for(int p = 0; p < periods.getLength(); p++){
                    Node nperiod = periods.item(p);
                    if(nperiod.getNodeType() == Node.ELEMENT_NODE){
                        Element eperiod = (Element) nperiod;
                        int currenttime = Integer.parseInt(eperiod.getAttribute("time"));
                        if(InTime(lasttime,currenttime,Integer.parseInt(mintime),Integer.parseInt(maxtime))) {
                            if (currenttime - lasttime >= Integer.parseInt(hours)) {
                                return Integer.toString(lasttime) + "/" + Integer.toString(currenttime);
                            }
                        }
                    }
                }
            }
        }

        return "Leider keinen passenden Zeitraum gefunden!";
    }
    public ArrayList<Integer> getMonthInformation(){
        return null;
    }

    public String getCurrentUser(){
        xmlReader.Update();
        return xmlReader.getCurrentUser();
    }
    public ArrayList<String> getUserList(){
        NodeList userlist = xmlReader.getUsers();
        ArrayList<String> users = new ArrayList<>();
        for(int i =  0;i < userlist.getLength();i++){
            Node nuser = userlist.item(i);
            if(nuser.getNodeType() == Node.ELEMENT_NODE){
                Element euser = (Element) nuser;
                users.add(euser.getAttribute("id"));
            }
        }
        return users;
    }
    public boolean isRegistered(String id){
        NodeList users = xmlReader.getUsers();

        for(int i = 0; i < users.getLength(); i++){
            Node nuser = users.item(i);
            System.out.println(i);
            if(nuser.getNodeType() == Node.ELEMENT_NODE){
                Element euser = (Element) nuser;
                System.out.println("--------- " + i);
                System.out.println(euser.getAttribute("id"));
                if(id.equals(euser.getAttribute("id"))){
                    return true;
                }
            }
        }
        return false;
    }
    //casts days and months in from int to String
    private String FormToString(int input){

        if(Integer.toString(input).length() > 1){
            return Integer.toString(input);
        }else{
            return "0"+Integer.toString(input);
        }
    }
    //checks if the time for the next free period is possible
    private boolean InTime(int lasttime, int currenttime, int mintime, int maxtime){
        if(lasttime > currenttime){
            if(lasttime < mintime){
                return false;
            }
            if(lasttime > maxtime){
                return false;
            }
        }
        return true;
    }
     public boolean getUnlocked(){
        return xmlReader.getUnlocked().equals("true");
     }
}
