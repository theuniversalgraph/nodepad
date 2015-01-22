package enclosing.application.node;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class AlignNodeToLeft {
	Hashtable selected = new Hashtable();
	public AlignNodeToLeft(Hashtable selected){
		this.selected = selected;
		int left = 10000;
		Enumeration enumeration = selected.elements();
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			if(nodeComponent.getX() < left){
				left = nodeComponent.getX();
			}
		}
		 enumeration = selected.elements();
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			nodeComponent.setLocation(left,nodeComponent.getY());
		}
	}
}
