/*
 * Created on 2006/01/21
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.ncplugins;

import java.util.Enumeration;
import java.util.StringTokenizer;


import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BreakNodeIntoNodesWithCRLF {
    public BreakNodeIntoNodesWithCRLF(NodeComponent nc,NodeObserver observer){
        StringTokenizer st = new StringTokenizer(nc.getNodeInterface().getContent(),"\r\n");
        while(st.hasMoreElements()){
            int xRandom = (int)(Math.random() * 150)-75;
            int yRandom = (int)(Math.random() * 100)-50;
            NodeComponent temp = observer.makeNewNornalNodeAt(nc.getX()+xRandom,nc.getY()+yRandom);
            temp.setText(st.nextToken());
        }
    }
}
