/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node;

import java.awt.Frame;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import myutil.MainFrame;
import myutil.filehandler;

/**
 * @author Administrator
 *	open all the files in the dir.
 *get the node.
 * check if the node isTerminal();
 * //if so,put a copy to a terminals.nd
 * //and write the put the content of teh node terminal.txt
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TerminalNodeListAgent {
	public static void main(String[] args) {
		TerminalNodeListAgent agent = new TerminalNodeListAgent();
		agent.outputTerminalsNodeFile(agent.getAllTheNodes());
		agent.outputTerminalsTextFile(agent.getAllTheNodes());
	}
	private Vector getAllTheNodes(){
		Vector vec = new Vector();
		//list all the files of the ./data/ dir
		File datadir = new File("data");
		File[] nodefiles = datadir.listFiles();
		
		//if the file is the nodefile,get the content.
		for(int i= 0;i < nodefiles.length;i++){
			if(nodefiles[i].getName().endsWith(".nd")){
				Object obj = filehandler.objectInputer(nodefiles[i].getAbsolutePath());
				Hashtable hash = (Hashtable)obj;	
				if(hash == null){
					hash = new Hashtable();
				}
				for(Enumeration en = hash.elements();en.hasMoreElements();){
					vec.add((Node)en.nextElement());
				}
			}
		}
		return vec;
	}
	private void outputTerminalsTextFile(Vector vec){
		Vector textfileoutput = new Vector();
		for(Enumeration en = vec.elements();en.hasMoreElements();){
			Node node = (Node)en.nextElement();
			if(this.isTerminal(node)){
				textfileoutput.add(node.getContent());
			}
		}
		filehandler.outputer("terminals.txt",textfileoutput);
	}
	private void outputTerminalsNodeFile(Vector vec){

		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		Frame frame = new MainFrame(applet, 500, 500);
		while(applet.observer==null){
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		applet.setFrame(frame);
		frame.addWindowListener(new NodepadWindowAdapter(applet.observer));
		applet.observer.openFromFile("terminals.nd");

		
		for(Enumeration en = vec.elements();en.hasMoreElements();){
			Node tempnode = (Node)en.nextElement();
			if(this.isTerminal(tempnode)){
				Node node = new Node(Integer.toString(applet.observer.getCounter().getNext(Node.class.getName())),tempnode.getContent(),Node.class.getName());
				node.setX(((int)(Math.random()*800)));
				node.setY(((int)(Math.random()*600)));
				NodeComponent nc = new NodeComponent(applet.observer,(NodeInterface)node);
				nc.setLocation(((int)(Math.random()*800)),((int)(Math.random()*600)));
				applet.observer.addNC(nc);
			}
		}
	}
	
    
    public boolean isSolo(Node node){
    	boolean flag = false;
    	if(node.getParents().size()<1 && node.getChildren().size()<1){
    		flag = true;
    	}
    	return flag;
    }
    public boolean isTerminal(Node node){
    	boolean flag = false;
    	if(node.getParents().size()>=1 && node.getChildren().size()<1){
    		flag = true;
    	}
    	return flag;
    }
}
