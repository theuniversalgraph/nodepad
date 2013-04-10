/*
 * Created on 2006/01/27
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.fileplugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import enclosing.application.node.NodeObserver;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OpenXlsWithTheSameNameAsNodeFile {
    public OpenXlsWithTheSameNameAsNodeFile(NodeObserver nodeObserver){
        try {
            System.err.println(nodeObserver.getFilename());
            System.err.println(File.pathSeparatorChar + "is the path separator?");
            File xlsfile =  new File("xls"+nodeObserver.getFilename().substring(nodeObserver.getFilename().lastIndexOf("/"),nodeObserver.getFilename().length()-3) +".xls");
            String excelpath = "";
            try {
                BufferedReader reader = new BufferedReader(new FileReader("excel.txt"));
                excelpath = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println(excelpath  + " " + xlsfile.getAbsolutePath());

            Runtime.getRuntime().exec(excelpath  + xlsfile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
