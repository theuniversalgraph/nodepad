/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.ncplugins;

import java.io.FileOutputStream;
import java.io.PrintWriter;


import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DumpContentToText {
    public DumpContentToText(NodeComponent component, NodeObserver nodeObserver,String xmltag,String filename){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("./dump",true));
            writer.write("<"+xmltag + ">");
            writer.write(component.getNodeinterface().getContent());
            writer.write("</"+xmltag + ">\r\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
