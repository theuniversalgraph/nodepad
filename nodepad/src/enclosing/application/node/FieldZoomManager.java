package enclosing.application.node;

import java.util.Enumeration;

public class FieldZoomManager {
	private NodeObserver observer;
	public FieldZoomManager(NodeObserver nodeObserver) {
		this.observer = nodeObserver;
	}
	public void zoomin(int percentage){
		this.zoom(percentage);
	}
	public void zoomin(){
		this.zoomin(10);
	}
	public void zoomout(int percentage){
		this.zoom(-percentage);
	}
	public void zoomout(){
		this.zoomout(10);
	}
	private void zoom(int percentage){
		for(Enumeration en = this.observer.getNode_components().elements();en.hasMoreElements();){
			NodeComponent nc = (NodeComponent)en.nextElement();
			int relativex = (this.observer.getNodeFieldApplet().getSize().width/2)-nc.getX();
			int relativey = (this.observer.getNodeFieldApplet().getSize().height/2)-nc.getY();
			nc.moveLocation((-relativex)*10000*percentage/1000000,(-relativey)*10000*percentage/1000000);
		}
	}
}
