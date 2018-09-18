package ReturnValues;

import java.util.ArrayList;

public class PermAppointments {
    ArrayList<String> start;
    ArrayList<String> end;
    ArrayList<String> content;
    ArrayList<String> weekday;
    public PermAppointments(){
        start = new ArrayList<>();
        end = new ArrayList<>();
        content = new ArrayList<>();
        weekday = new ArrayList<>();
    }
    public void add(String start, String end, String content, String weekday){
        this.start.add(start);
        this.end.add(end);
        this.content.add(content);
        this.weekday.add(weekday);
    }
    public ArrayList getStart(){
        return start;
    }
    public ArrayList getEnd(){
        return end;
    }
    public ArrayList getContent(){
        return content;
    }
    public ArrayList getWeekday(){
        return weekday;
    }

}
