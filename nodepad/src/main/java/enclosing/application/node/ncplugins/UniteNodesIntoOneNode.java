/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.ncplugins;

import java.util.Enumeration;

import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UniteNodesIntoOneNode {
    public UniteNodesIntoOneNode(Enumeration en,NodeObserver nodeObserver){
        int leftx = 3000;
        int topy = 3000;
        int rightx = 0;
        int buttomy = 0;
        StringBuffer buffer = new StringBuffer();
        while(en.hasMoreElements()){
            NodeComponent temp = (NodeComponent)en.nextElement();
            buffer.append(temp.getNodeInterface().getContent()+"\r\n");
            if(leftx > temp.getX()){
                leftx = temp.getX();
            }
            if(topy > temp.getY()){
                topy = temp.getY();
            }
            if(rightx < temp.getX()){
                rightx = temp.getX();
            }
            if(buttomy < temp.getY()){
                buttomy = temp.getY();
            }
        }
        NodeComponent nc = nodeObserver.makeNewNornalNodeAt(leftx+(rightx-leftx)/2,topy+(buttomy-topy)/2);
        nc.setText(buffer.toString());
    }
}
