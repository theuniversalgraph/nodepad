package enclosing.application.node;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.Hashtable;

public class SimpleStringConstants {

    final public static String FILE_POSTFIX = ".json";
    final public static String FILE_NAME_NODE = "node";
    final public static String FILE_NAME_CLIPBOARD = "./data/clipboard";

    final public static String FILE_ND = "data/top.nd";
    final public static String FILE_JSON = "data/top.json";

    /*
    For convert file *.nd to file *.json :
        create file *.json
        Set:
            FILE_ND = reference to file *.nd
            FILE_JSON = reference to file *.json
        Run main method
     */
    public static void main(String[] args) {

        Hashtable hashtable = NodeObserver.inputNodesFromFileOld(FILE_ND);
        try {
            FileOutputStream fo = new FileOutputStream(FILE_JSON);
            NodeObserver nodeObserver = new NodeObserver(new Container(), new Hashtable(), null);
            nodeObserver.exportToJson(fo, hashtable);
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
