package ReturnValues;

import java.util.ArrayList;

public class PermAppointments {
    ArrayList<String> start;
    ArrayList<String> end;
    ArrayList<String> content;
    public PermAppointments(){
        start = new ArrayList<>();
        end = new ArrayList<>();
        content = new ArrayList<>();
    }
    public void add(String start, String end, String content){
        this.start.add(start);
        this.end.add(end);
        this.content.add(content);
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

}
