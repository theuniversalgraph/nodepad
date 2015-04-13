package enclosing.application.node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Hashtable;

public class SimpleStringConstants {

    final public static String FILE_POSTFIX = ".json";
    final public static String FILE_NAME_NODE = "node";
    final public static String FILE_NAME_CLIPBOARD = "./data/clipboard";

    final public static String FILE_ND = "data/top.nd";
    final public static String FILE_JSON = "data/top.json";

    final public static String FILE_ND_POSTFIX = ".nd";
    final public static String FILE_JSON_POSTFIX = ".json";

    /*
    For convert file *.nd to file *.json :
        create file *.json
        Set:
            FILE_ND = reference to file *.nd
            FILE_JSON = reference to file *.json
        Run main method
     */
    public static void main(String[] args) {

        final String path = args[0];

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(FILE_ND_POSTFIX);
                }
            });

            for (File ndFile : files) {
                Hashtable hashtable = NodeObserver.inputNodesFromFileOld(ndFile.getAbsolutePath());
                try {
                    String nameJsonPostfix = ndFile.getName().replaceAll(FILE_ND_POSTFIX, FILE_JSON_POSTFIX);

                    File file_json = new File(path + nameJsonPostfix);
                    if (!file_json.exists()) {
                        file_json.createNewFile();
                    }

                    FileOutputStream fo = new FileOutputStream(file_json, false);
                    NodeObserver.exportFileToJson(fo, hashtable);
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.out.println(file.getAbsolutePath() + "Folder not exists");
        }
    }
}
