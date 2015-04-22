package enclosing.application.node.ncplugins;

import java.util.Enumeration;

import com.theuniversalgraph.application.nodepad.NodeComponent;
import com.theuniversalgraph.application.nodepad.NodeObserver;

public class CreateNormalNodeWithDate {
	public static void process(String date,String content,NodeObserver observer){
		
		for(Enumeration en = observer.getNode_components().elements();en.hasMoreElements();){
			NodeComponent nodeComponent = (NodeComponent)en.nextElement();
			if(nodeComponent.getNodeInterface().getContent().startsWith("#4444") && nodeComponent.getNodeInterface().getContent().contains(date)){
				int ranX = (int)(Math.random() * 100);
				int ranY = (int)(Math.random() * 100);
				
				NodeComponent nodeComponent2 = observer.makeNewNornalNodeAt(nodeComponent.getX() + ranX , nodeComponent.getY() + ranY);
				nodeComponent2.setText(content);
				observer.getNodeFieldApplet().repaint();
				
			}
		}
		
	}

}
