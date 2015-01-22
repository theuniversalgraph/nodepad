package enclosing.application.node.ncplugins;

import java.util.Enumeration;

import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;

public class CreateNormalNodeWithDate {
	public static void process(String date,String content,NodeObserver observer){
		
		for(Enumeration en = observer.getNode_components().elements();en.hasMoreElements();){
			NodeComponent nodeComponent = (NodeComponent)en.nextElement();
			if(nodeComponent.getNodeinterface().getContent().startsWith("#4444") && nodeComponent.getNodeinterface().getContent().contains(date)){
				int ranX = (int)(Math.random() * 100);
				int ranY = (int)(Math.random() * 100);
				
				NodeComponent nodeComponent2 = observer.makeNewNornalNodeAt(nodeComponent.getX() + ranX , nodeComponent.getY() + ranY);
				nodeComponent2.setText(content);
				observer.getNodeFieldApplet().repaint();
				
			}
		}
		
	}

}
