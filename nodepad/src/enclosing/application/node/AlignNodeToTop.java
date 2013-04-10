package enclosing.application.node;

import java.util.Enumeration;
import java.util.Hashtable;

public class AlignNodeToTop {
	public AlignNodeToTop(Hashtable selected){
		int top = 10000;
		Enumeration enumeration = selected.elements();
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			if(nodeComponent.getY() <top){
				top = nodeComponent.getY();
			}
		}
		 enumeration = selected.elements();
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			nodeComponent.setLocation(nodeComponent.getX(),top);
		}
	}
}
