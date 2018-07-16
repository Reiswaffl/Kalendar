package Logic;


import Logic.TextClass;
import XML.Writer;

import javax.xml.transform.TransformerException;

public class TextClass{
    public static void Logic(String[] args) throws TransformerException {
        String a = "16:30";
        String b;
        b = a.substring(0,a.length()-3);
        System.out.println(b);

    }
}

