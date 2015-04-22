/*
 * Created on 2004/08/28
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node;

import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import myutil.filehandler;

import com.theuniversalgraph.model.NodeInterface;

import enclosing.application.node.server.SaveNodeFileToServer;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClipBoardManager {
	NodeObserver observer = null;
	public ClipBoardManager(NodeObserver observer){
		this.observer = observer;
	}
	private String filename = SimpleStringConstants.FILE_NAME_CLIPBOARD+ SimpleStringConstants.FILE_POSTFIX;
	public Hashtable copy(){
	    if(this.observer == null || this.observer.getSelected() == null || this.observer.getSelected().size()<1){
	        return null;
	    }
		Hashtable hash = new Hashtable();
		Hashtable nchash = this.observer.getSelected();
		int leftx = Integer.MAX_VALUE;
		int topy = Integer.MAX_VALUE;
		for(Enumeration en = nchash.elements();en.hasMoreElements();){
		    NodeComponent nc = (NodeComponent)en.nextElement();
		    if(leftx > nc.getX()){
		        leftx = nc.getX();
		    }
		    if(topy > nc.getY()){
		        topy = nc.getY();
		    }
		}
        for(Enumeration en = nchash.elements(); en.hasMoreElements();){
            NodeComponent nc = (NodeComponent)en.nextElement();
            nc.updateNodeInterface();
            System.err.println(nc.getNodeinterface().getId() + "is the  " + nc.getNodeinterface().getContent());
            NodeInterface ni = nc.getNodeinterface();
            ni.setX(ni.getX()-leftx+10);
            ni.setY(ni.getY()-topy + 30);
            hash.put(ni.getId(),ni);
        }
		if (this.observer.getNodeFieldApplet().isNet()) {
			SaveNodeFileToServer fileToServer = new SaveNodeFileToServer(this.observer);
			fileToServer.process(this.observer.getNodeFieldApplet().getUser(),this.observer.getNodeFieldApplet().getFilename(),this.observer.getNodeFieldApplet().getServerName());
		}else{
			try
			{
				FileOutputStream fo = new FileOutputStream(this.filename);
				this.observer.exportToJson(fo, hash);
				fo.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return hash;
    }
		
		
	public void paste(){
		this.observer.addFromObject(filehandler.objectInputer(this.filename),this.observer.getNodeFieldApplet().getWidth() / 2,this.observer.getNodeFieldApplet().getHeight() / 2,false);
	}
	public void cut(){
		Hashtable hash = this.copy();
		this.observer.setMode("delete",null);
	}
}
