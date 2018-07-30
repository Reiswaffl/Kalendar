package ReturnValues;


import java.util.ArrayList;

public class ReturnValue {

    ArrayList<String> content;
    ArrayList<String> start;
    ArrayList<String> end;


    public ReturnValue(){
        content = new ArrayList<>();
        start = new ArrayList<>();
        end = new ArrayList<>();
    }

    public void AddContent(String content, String start, String end){
        this.content.add(content);
        this.start.add(start);
        this.end.add(end);
    }

    //returns
    public ArrayList<String> getContent() {
        return content;
    }
    public ArrayList<String> getStart(){return start;}
    public ArrayList<String> getEnd(){return end;}

}
