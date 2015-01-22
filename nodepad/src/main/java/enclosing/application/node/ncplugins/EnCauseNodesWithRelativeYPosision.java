/*
 * Created on 2006/01/21
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
public class EnCauseNodesWithRelativeYPosision {
    public EnCauseNodesWithRelativeYPosision(Hashtable hash,NodeObserver nodeObserver){
        if(hash.size() == 2){
            //�Ƃ肠����2�̏ꍇ�̂݁B���̃A���S���Y���͂ǂ�����񂾁B
            Enumeration en = hash.elements();
            NodeComponent nc1 = (NodeComponent)en.nextElement();
            NodeComponent nc2 = (NodeComponent)en.nextElement();
            if(nc1.getY() > nc2.getY()){
                nc2.makeConnection(nc1);
            }else{
                nc1.makeConnection(nc2);
            }
        }
    }
}
