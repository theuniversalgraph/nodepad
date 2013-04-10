/*
 * Created on 2006/01/27
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.ncplugins;

import java.util.Enumeration;
import java.util.Hashtable;

import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;

/**	
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OpenAllUrlsOfNodes {
     public OpenAllUrlsOfNodes(Hashtable nodehash,NodeObserver nodeObserver){
         for(Enumeration en = nodehash.elements();en.hasMoreElements();){
             NodeComponent component = (NodeComponent)en.nextElement();
         }
     }
     

}
