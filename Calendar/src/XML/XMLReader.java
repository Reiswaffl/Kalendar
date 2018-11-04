package XML;

import java.io.File;
import javax.swing.text.AbstractDocument;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import ReturnValues.Periods;

/**
 * @brief The XMLReader-class is used to get Nodeinformations form the XML-FIle.
 * its the only class, that has a direkt refernce to the File.
 * ---- In this case an Element is equals to an Node, because the Nodes, that are saved in the XML-File
 * are are ELEMENT_NODES, with means they can be casted into an Element, depending with method the Programmer wants use
 * @author Eric Brendel
 *
 */
public class XMLReader {
    NodeList users;
    Document doc;

    /**
     * @brief The constuctor reads in the File
     */
    public XMLReader() {

        //reads in file
        try {
            File inputFile = new File("resources/calendar.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //Console delete later and replace with GUI
            users = doc.getElementsByTagName("user");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();}

    }
    //Update-method to update the File (after changes were made)

    /**
     * @brief As a renault of changes the File needs to be read in again. This is the work of the Update()-function.
     */
    public void Update(){
    //reads in file
        try {
            doc = null;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            File inputFile = new File("resources/calendar.xml");
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //Console delete later and replace with GUI
            users = doc.getElementsByTagName("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief changes the currentUser - Element in the XML-FIle
     * @param user :New id-value for currentUser - Element
     */
    //Userinteraction
    public void SwitchUser(String user){
        NodeList currents = doc.getElementsByTagName("currentUser");
        Node currentUser = currents.item(0);
        //Changes attribute to the current user
        setAttribute((Element) currentUser,"id",user);
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("resources/calendar.xml"));
                transformer.transform(source, result);
            }catch (Exception e){}
    }

    /**
     * @brief returns id-Value of currentUser - Element
     * @return String: currentUser - id
     */
    public String getCurrentUser(){
        NodeList currents = doc.getElementsByTagName("currentUser");
        Node currentUser = currents.item(0);

        return ( (Element) currentUser).getAttribute("id");
    }

    /**
     * @brief returns all Users with a written in the register (got no child nodes)
     * @return NodeList: all Users in register
     */
    //Reading Information
    public NodeList getUsers(){
        NodeList Users = doc.getElementsByTagName("users");
        Node userList = Users.item(0);
        if(userList == null){
            return null;
        }
        return userList.getChildNodes();
    } //returns a NodeList with all possible Users

    /**
     * @brief returns all Users, with childNodes to save Appointments.
     * @param i useless, just to overload the getUsers()- function
     * @return NodeList: all Users
     */
    public NodeList getUsers(int i){
        NodeList Users = doc.getElementsByTagName("user");
        if(Users == null){
            return null;
        }
        return Users;
    }

    /**
     * @brief returns the single user with the id, that is given, if there is no user with that id, it will return null.
     * The use of that function is, that it is possible to get the childnodes
     * @param id id to search for
     * @return Node: user with that id
     */
    public Node getUser(String id){
        for(int i = 0; i < users.getLength(); i++){
            Node nuser = users.item(i);
            if(nuser.getNodeType() == Node.ELEMENT_NODE){
                Element euser = (Element) nuser;
                if(euser.getAttribute("id").equals((id))){
                    return nuser;
                }
            }
        }
        return null;
    }

    /**
     * @brief returns the single year with the id, that is given, if there is no year with that id, it will return null
     * @param id id to search for
     * @param years NodeList to search in
     * @return Node: year with that id
     */
    public Node getYearById(String  id, NodeList years ){
        for(int i = 0; i < years.getLength(); i++){
            Node nyear = years.item(i);
            if (nyear.getNodeType() == Node.ELEMENT_NODE){
                Element eyear = (Element) nyear;
                if(eyear.getAttribute("id").equals(id) ){
                    return nyear;
                }
            }
        }
        return null;
    }

    /**
     * @brief returns the single month with the id, that is given, if there is no month with that id, it will return null
     * @param id id to search for
     * @param months NodeList to search in
     * @return Node: month with that id
     */
    public Node getMonthById(String id , NodeList months) {
        for (int i = 0; i < months.getLength(); i++) {
            Node nmonth = months.item(i);
            if (nmonth.getNodeType() == Node.ELEMENT_NODE) {
                Element emonth = (Element) nmonth;
                if (emonth.getAttribute("id").equals(id)) {

                    return nmonth;
                }
            }
        }
        return null;
    }

    /**
     * @brief returns the single day with the date, that is given, if there is no day with that date, it will return null
     * @param date date to search for
     * @param days NodeList to search in
     * @return Node: day with that date
     */
    public Node getDayById(String date, NodeList days){
        for(int i = 0; i < days.getLength(); i++){
            Node nday = days.item(i);
            if(nday.getNodeType() == Node.ELEMENT_NODE){
                Element eday = (Element) nday;
                if(eday.getAttribute("date").equals(date)){
                    return nday;
                }
            }
        }
        return null;
    }

    /**
     * @brief returns the single period with the start, that is given, if there is no period with that start, it will return null
     * @param start start to search for
     * @param periods NodeList to search in
     * @return Node: period with that start
     */
    public Node getPeriodById(String start, NodeList periods){
        for(int i = 0; i < periods.getLength(); i++){
            Node nperiod = periods.item(i);
            if(nperiod.getNodeType() == Node.ELEMENT_NODE){
                Element eperiod = (Element) nperiod;
                if(eperiod.getAttribute("start").equals(start)){
                    return nperiod;
                }
            }
        }
        return null;
    }

    /**
     * @brief returns all permanent Appointments, depending on the user
     * @param user user to get the perm. Appointments from
     * @return NodeList: permanent Appointments from that user
     */
    public NodeList getPermAppointments(String user){
        NodeList permAppoitnments = doc.getElementsByTagName("permAppointments");
        NodeList users = permAppoitnments.item(0).getChildNodes();
        if(users == null){
            return null;
        }
        for(int i = 0; i  < users.getLength(); i++){
            Node nuser = users.item(i);
            if(nuser.getNodeType() == Node.ELEMENT_NODE){
                Element euser = (Element) nuser;
                if(euser.getAttribute("id").equals(user)){
                    return nuser.getChildNodes();
                }
            }
        }
        return null;
    }

    /**
     * @brief returns Node with the user that is asked.
     * @param user user so search for
     * @return Node: user, that is searched
     */
    public Node getPermUser(String user){
        NodeList permAppointments = doc.getElementsByTagName("permAppointments");
        NodeList users = permAppointments.item(0).getChildNodes();
        for(int i = 0; i < users.getLength(); i++){
            Node nuser = users.item(i);
            if(nuser.getNodeType() == Node.ELEMENT_NODE){
                Element euser = (Element) nuser;
                if(euser.getAttribute("id").equals(user)){
                    return nuser;
                }
            }
        }
        return null;
    }
    public Node getPermAppointment(String start,NodeList permAppointments){
        for(int i = 0; i< permAppointments.getLength();i++){
            Node nPermAppointment = permAppointments.item(i);
            if(nPermAppointment.getNodeType() == Node.ELEMENT_NODE){
                Element ePermAppointment = (Element) nPermAppointment;
                if(ePermAppointment.getAttribute("start").equals(start)){
                    return nPermAppointment;
                }
            }
        }
        return null;
    }
    //Writing Information

    /**
     * @brief creates new user-Element, returns his Element to check if it was created correctly
     * @param id id, the user is supposed to have
     * @return Element, that was created
     * @throws TransformerException
     */
    public Element CreateUser(String id) throws TransformerException {
        Element user = doc.createElement("user");
        user.setAttribute("id",id);
        NodeList rootList = doc.getElementsByTagName("calendar");
        Node root = rootList.item(0);
        root.appendChild(user);

        Element User = doc.createElement("User");
        User.setAttribute("id",id);
        NodeList userList = doc.getElementsByTagName("users");
        Node userSave = userList.item(0);
        userSave.appendChild(User);

        NodeList currents = doc.getElementsByTagName("currentUser");
        Node currentUser = currents.item(0);
        setAttribute((Element) currentUser,"id",id);

        Element permUser = doc.createElement("User");
        permUser.setAttribute("id",id);
        NodeList permAppointments = doc.getElementsByTagName("permAppointments");
        Node permAppointmentssave =  permAppointments.item(0);
        permAppointmentssave.appendChild(permUser);

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        }catch (Exception e){}

        return user;

    }

    /**
     * @brief creates new year-Element
     * @param parent parent to place the year-Element as ChildNode
     * @param id id, the year-Element is supposed to have
     * @return Element, that was created
     */
    public Element CreateYear(Node parent, String id){
        Element year = doc.createElement("years");
        year.setAttribute("id",id);
        parent.appendChild(year);
    try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("resources/calendar.xml"));
        transformer.transform(source, result);
    }catch (Exception e){}
    return year;
    }

    /**
     * @brief created new month-Element
     * @param parent parent to place the month-Element as ChildNode
     * @param id id, the month-Element is supposed to have.
     * @param maxdays ---from older version---
     * @return Element, that was created
     */
    public Element CreateMonth(Node parent, String id,String maxdays) {
        Element month = doc.createElement("month");
        if(id.length() == 1){id = "0"+id;}
        month.setAttribute("id", id);
        parent.appendChild(month);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
        }
        return month;
    }

    /**
     * @brief creates new day-Element
     * @param parent parent to place the day-Element as ChildNode
     * @param date date (id), the day-Element is supposed to have.
     * @param name ---from older version---
     * @param blocked
     * @return Element, that was created
     */
    public Element CreateDay(Node parent, String date, String name,String blocked){
        Element day = doc.createElement("day");
        day.setAttribute("date", date);
        day.setAttribute("blocked", blocked);
        parent.appendChild(day);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {}
        return day;
    }

    /**
     * @brief created new period-Element
     * @param parent parent, to place the period-Element as ChildNode
     * @param start start, the period-Element is supposed to have
     * @param end end, the period-Element is supposed to have
     * @param content content, the period-Element is supposed to have
     * @param isfamEvent says is the event is a family event or not
     * @return Element, that was created
     */
    public Element CreatePeriod(Node parent, String start, String end, String content,String isfamEvent){
        Element period = doc.createElement("period");
        period.setAttribute("start",start);
        period.setAttribute("end", end);
        period.setAttribute("isfamEvent",isfamEvent);
        period.setTextContent(content);
        parent.appendChild(period);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {}
        return period;
    }

    /**
     * @brief creates a permanent-Appointment
     * @param parent parent, to place the permAppointment-Element as ChildNode
     * @param start start, the permAppointment-Element is supposed to have
     * @param end end, the permAppointment-Element is supposed to have
     * @param content content, the permAppointment-Element is supposed to have
     * @param repetition repetition, the permAppointment-Element is supposed to have
     * @param weekday weekday, the permAppointment-Element is supposed to have
     * @return Element, that was created
     */
    public Element CreatePermanentAppointment(Node parent, String start, String end, String content, String repetition, String weekday){
        Element permAppointment = doc.createElement("permAppointment");
        permAppointment.setAttribute("start",start);
        permAppointment.setAttribute("end",end);
        permAppointment.setAttribute("repetition",repetition);
        permAppointment.setAttribute("weekday",weekday);
        permAppointment.setTextContent(content);
        parent.appendChild(permAppointment);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {}
        return null;
    }

    /**
     * @brief sets an attribute of an Element_Node to an other value
     * @param node Node, which attribute must changed
     * @param att name of the attribute
     * @param value value of the attribute
     */
    //Setting attributes
    public void setAttribute(Element node,String att, String value){
        node.setAttribute(att,value);
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        }catch (Exception e){}
    }

    /**
     * @brief removes a Node of the XML-File
     * @param parent parent of the Node, that is supposed to be deleted
     * @param child Node, that is supposed to be changed
     */
    //Remove Nodes
    public void remove(Node parent, Node child){
        System.out.println(parent +"-------" + child);
        parent.removeChild(child);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("resources/calendar.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {}
    }

    /**
     * @brief is used to check, if the program is already in use, or without any users
     * @return Node, that contains the described information
     */
    public Node getUnlocked(){
        return doc.getElementsByTagName("unlocked").item(0);
    }

}
