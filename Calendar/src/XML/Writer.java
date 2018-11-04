package XML;

//Imports
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerException;

/**
 * @brief This class is used to cast String-information into Node-information. It gets Strings from the Logic class,
 * casts them and gives them to the XMLReader
 */


public class Writer {
    XMLReader xmlReader;
    int[] daysInMonth;
    //setup

    /**
     * @brief setup
     */
    public Writer(){
        xmlReader = new XMLReader();
        daysInMonth = new int[12];
        daysInMonth[0] = 31;
        daysInMonth[1] = 28;
        daysInMonth[2] = 31;
        daysInMonth[3] = 30;
        daysInMonth[4] = 31;
        daysInMonth[5] = 30;
        daysInMonth[6] = 31;
        daysInMonth[7] = 31;
        daysInMonth[8] = 30;
        daysInMonth[9] = 31;
        daysInMonth[10] = 30;
        daysInMonth[11] = 31;
    }

    /**
     * @brief forward to xmlReader
     * @param user user that is supposed to be the new currentUser
     */
    //Change Users
    public void SwitchUser(String user){
        xmlReader.SwitchUser(user);
    }

    /**
     * forward to xmlReader
     * @return String, currentUser
     */
    public String getCurrentUser(){
        return xmlReader.getCurrentUser();
    }

    /**
     * @brief checks, if user already exist, before forewarding to the xmlReader
     * @param name id, the new User-Element is supposed to have
     * @return can return Errormassage
     * @throws TransformerException
     */
    public String AddUser(String name) throws TransformerException {
        NodeList userList = xmlReader.getUsers();
        if (userList == null){
            xmlReader.CreateUser(name);
            return "--";
        }
        for(int i = 0; i < userList.getLength(); i++){
            Node user = userList.item(i);
            if(user.getNodeType() == Node.ELEMENT_NODE){
                Element euser = (Element) user;
                if(name.equals(euser.getAttribute("id"))){
                    return "Name bereits vorhanden!";
                }
            }
        }
        xmlReader.CreateUser(name);
        return null;
    }

    /**
     * @brief need of this method descried in XMLReader
     */
    public void Update(){
        xmlReader.Update();
    }

    /**
     * @brief To make an efficient program data-structure is important, therefore the AddPeriod-method checks,
     * if the ParentNodes for the year-/month-/day- and period-Elements already exists. If so, the program won't create a new one, otherwise it will.
     * @param user user, the appointment is from
     * @param date date, the appointment is supposed to have
     * @param start start, the appointment is supposed to have
     * @param end end, the appointment is supposed to have
     * @param content content, the appointment is supposed to have
     * @param isfamEvent says, if the appointment is a family Event
     * @throws TransformerException
     */
    public void AddPeriod(String user, String date, String start,String end, String content, String isfamEvent) throws TransformerException {
        Node usr = xmlReader.getUser(user);
        NodeList years = null;
        int iyear = Integer.parseInt(date);
        iyear /= 10000;
        int month = Integer.parseInt(date);
        month %= 10000;
        month /= 100;
        if(usr != null){ // usr allready exists
            System.out.println("User not Created");
            iyear = Integer.parseInt(date);
            iyear /= 10000;
            years = usr.getChildNodes();
            Node year = null;
            if(years != null) {
                year = xmlReader.getYearById(Integer.toString(iyear), years);
            }
            month = Integer.parseInt(date);
            month %= 10000;
            month /= 100;
            String smonth = Integer.toString(month);
            if(smonth.length() == 1){
                smonth = "0" + smonth;
            }
            System.out.println(smonth);
            if(year != null){ // year allready exists
                NodeList months = year.getChildNodes();
                Node nmonth = null;
                if(months != null) {
                    nmonth = xmlReader.getMonthById(smonth, months);
                }
                if(nmonth != null){ // month allready exists
                    NodeList days = nmonth.getChildNodes();
                    Node day = null;
                    if(days != null) {
                        day = xmlReader.getDayById(date,days);
                    }
                    if(day != null){
                        xmlReader.CreatePeriod(day,start,end,content,isfamEvent);
                    }else{
                        day = xmlReader.CreateDay(nmonth,date,"--","false");
                        xmlReader.CreatePeriod(day,start,end,content,isfamEvent);
                    }

                }else{
                    nmonth = xmlReader.CreateMonth(year,Integer.toString(month),Integer.toString(daysInMonth[month-1]));
                    Node nday = xmlReader.CreateDay(nmonth,date,"--","false");
                    xmlReader.CreatePeriod(nday,start,end,content,isfamEvent);
                }
            }else{
                Node nyear = xmlReader.CreateYear(usr,Integer.toString(iyear));
                Node nmonth = xmlReader.CreateMonth(nyear,Integer.toString(month),Integer.toString(daysInMonth[month-1]));
                Node nday = xmlReader.CreateDay(nmonth,date,"--","false");
                xmlReader.CreatePeriod(nday,start,end,content,isfamEvent);

            }
        }else{
            System.out.println("User Created");
            usr = xmlReader.CreateUser(user);
            Node nyear = xmlReader.CreateYear(usr,Integer.toString(iyear));
            Node nmonth = xmlReader.CreateMonth(nyear,Integer.toString(month),Integer.toString(daysInMonth[month-1]));
            Node nday = xmlReader.CreateDay(nmonth,date,"--","false");
            xmlReader.CreatePeriod(nday,start,end,content,isfamEvent);
        }
        xmlReader.Update();
    }

    /**
     * @brief forwards to XMLReader
     * @param user --
     * @param start --
     * @param end --
     * @param repetition --
     * @param weekday --
     * @param content --
     */
    public void AddPermAppointment(String user,String start, String end, String repetition,String weekday,String content){
        xmlReader.CreatePermanentAppointment(xmlReader.getPermUser(user),start, end,content,repetition,weekday);

    }

    /**
     * @brief prepares to delete a Node, by searching and deleting it
     * @param user user, the appointment is from
     * @param start start of the appointment
     * @param date date of the appointment
     * @return
     */
    public boolean removeNode(String user,String date,String start){
        System.out.println(date +"+"+start);
        Node nuser = xmlReader.getUser(user);
        String year = date.substring(0,2);
        String month = date.substring(2, 4);
        System.out.println(year +month);
        Node nyear = null;
        if(nuser != null) nyear = xmlReader.getYearById(year,nuser.getChildNodes());
        Node nmonth = null;
        if(nyear != null) nmonth = xmlReader.getMonthById(month,nyear.getChildNodes());
        Node nday = null;
        if(nmonth != null) nday = xmlReader.getDayById(date,nmonth.getChildNodes());
        Node period = null;
        if(nday != null) period = xmlReader.getPeriodById(start,nday.getChildNodes());
        if(period != null){ xmlReader.remove(nday,period); return true;}

        return false;
    }

    /**
     * @brief prepares to delete a permanent Appointment- Node, by searching and deleting it
     * @param user user, the appointment is from
     * @param start start of the appointment
     * @param weekday weekday, or day (when repetition is monthly) of the appointment. e.g. MONDAY, of 26
     * @param repetition repetition of the appointment (DAILY, WEEKLY, MONTHLY or YEARLY)
     */
    public void removePermAppointment(String user, String start, String weekday, String repetition){
       NodeList permAppointments = xmlReader.getPermAppointments(user);
        Node parent = xmlReader.getPermUser(user);
       for(int i = 0; i < permAppointments.getLength();i++){
           Node nPermAppointment = permAppointments.item(i);
           if(nPermAppointment.getNodeType() == Node.ELEMENT_NODE){
               Element ePermAppointment = (Element) nPermAppointment;
               if(ePermAppointment.getAttribute("start").equals(start) && ePermAppointment.getAttribute("weekday").equals(weekday) && ePermAppointment.getAttribute("repetition").equals(repetition)){
                   xmlReader.remove(parent,nPermAppointment);
               }
           }
       }
    }
    public void SetDayBusy(String user, String date, String content){}
    public void SetDayBusy(String user, String date){}

    /**
     * @brief removes all Nodes from the last month to hold the size of the XML-File small
     * @param year current year
     * @param month cureent month
     */
    public void setup(String year, String month){
        System.out.println(year + "----" + month);
        NodeList users = xmlReader.getUsers(12);
        for(int i = 0; i < users.getLength();i++){
            Node nuser = users.item(i);
            Node nyear = xmlReader.getYearById(year,nuser.getChildNodes());
            Node nmonth = null;
            if(nyear != null){
                nmonth = xmlReader.getMonthById(month,nyear.getChildNodes());
            }
            if(nmonth != null) {
                xmlReader.remove(nyear, nmonth);
            }
        }

    }

    /**
     * @brief sets the current state to unlocked
     */
    public void setUnlocked(){
        Node unlocked = xmlReader.getUnlocked();
        xmlReader.setAttribute((Element)unlocked,"state","true");
    }

}
