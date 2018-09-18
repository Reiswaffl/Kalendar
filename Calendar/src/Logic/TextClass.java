package Logic;


import Logic.TextClass;
import XML.Writer;
import XML.Reader;

import javax.xml.transform.TransformerException;

public class TextClass{
    public static void main(String[] args) throws TransformerException {
        String a = "16:30";
        String b;
        b = a.substring(0,a.length()-3);
        Reader reader = new Reader();
        System.out.println(reader.getNextFreeDay("Eric",9,17));

    }
}

