package ReturnValues;

import java.util.ArrayList;

public class PermAppointments {
    ArrayList<String> start;
    ArrayList<String> end;
    ArrayList<String> content;
    ArrayList<String> info;
    ArrayList<String> repetition;
    public PermAppointments(){
        start = new ArrayList<>();
        end = new ArrayList<>();
        content = new ArrayList<>();
        info = new ArrayList<>();
        repetition = new ArrayList<>();
    }
    public void add(String start, String end, String content, String info, String repetition){
        this.start.add(start);
        this.end.add(end);
        this.content.add(content);
        this.info.add(info);
        this.repetition.add(repetition);
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
        return info;
    }
    public ArrayList getRepetition(){return repetition;}

}
