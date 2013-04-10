package enclosing.application.node;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class TextToNodes {
	public TextToNodes (NodeComponent nodeComponent,NodeObserver observer){
        
        String[] nodesource = new String[2];
        Vector vector = new Vector();
        int index = 0;
        String content = nodeComponent.getNodeinterface().getContent();
        while(index >= 0){
        	int tempinde = index;
//        	int causingindexd = content.indexOf("��",index);
        	int causedindex = content.indexOf("←",index);
        	if(causedindex >= 1 ){
//        		if(causedindex< causingindexd ){
//        			//if the caused is nearest.
        			vector.add(content.substring(index,causedindex));
        			index = causedindex+1;
//        		}else{
//        			vector.add(new String[]{content.substring(index,causedindex),"causing"});
//        			index = causingindexd+1;
//        		}
////        		index++;
        	}else{
        		index = -1;
        	}
        }
        String previousenode = null;
        NodeComponent previouseNodeComponent  = null;
        int y = nodeComponent.getY();
        int x = nodeComponent.getX();
        for (Iterator iter = vector.iterator(); iter.hasNext();) {
			String nodecontent = (String) iter.next();
			NodeComponent nc =observer.makeNewNornalNodeAt(x, y );
			nc.setText(nodecontent);
			y = y + nc.getPreferredSize().height + 30;
			x = x + 3;
			if(previousenode  !=null){
				previouseNodeComponent.makeConnection(nc);
			}
			previousenode = nodecontent;
			previouseNodeComponent = nc;
		}
	}
}
