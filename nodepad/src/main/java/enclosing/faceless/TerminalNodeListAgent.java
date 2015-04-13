/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.faceless;

import java.awt.Frame;
import java.util.Collection;
import java.util.Vector;

import myutil.MainFrame;
import myutil.filehandler;
import core.model.Node;
import enclosing.application.node.NodeFieldApplet;
import enclosing.application.node.NodepadWindowAdapter;
import enclosing.application.node.predicate.Terminal;


public class TerminalNodeListAgent {
	public static void main(String[] args) {
		TerminalNodeListAgent agent = new TerminalNodeListAgent();
//		agent.outputTerminalsNodeFile(new AllNode().getAllTheNodes());
//		agent.outputTerminalsTextFile(new AllNode().getAllTheNodes());
	}
	private void outputTerminalsTextFile(Vector<Node> vec){
//		Collection<Node> terminals = com.google.common.collect.Collections2.filter(vec, new Terminal());
//		filehandler.outputer("terminals.txt",terminals);
	}
	
	private void outputTerminalsNodeFile(Vector<Node> vec){

		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		Frame frame = new MainFrame(applet, 500, 500);
		while(applet.getObserver()==null){
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		applet.setFrame(frame);
		frame.addWindowListener(new NodepadWindowAdapter(applet.getObserver()));
		applet.getObserver().openFromFile("terminals.json");

////		Collection<Node> terminals = com.google.common.collect.Collections2.filter(vec, new Terminal());
//
//		while (terminals.iterator().hasNext()) {
//			Node tempnode = (Node) terminals.iterator().next();
//			Node node = new Node(Integer.toString(applet.observer.getCounter().getNext(Node.class.getName())),tempnode.getContent(),Node.class.getName());
//			node.setX(((int)(Math.random()*800)));
//			node.setY(((int)(Math.random()*600)));
//			NodeComponent nc = new NodeComponent(applet.observer,(NodeInterface)node);
//			nc.setLocation(((int)(Math.random()*800)),((int)(Math.random()*600)));
//			applet.observer.addNC(nc);
//			
//		}

	}

}
