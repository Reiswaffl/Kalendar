package ReturnValues;

import java.util.ArrayList;
import org.w3c.dom.Node;

public class Periods {
    ArrayList<Node> periods;

    public Periods(){
        periods = new ArrayList<>();
    }
    public void addPeriod(Node n){
        periods.add(n);
    }
    public ArrayList<Node> getPeriods(){
        return periods;
    }
}
