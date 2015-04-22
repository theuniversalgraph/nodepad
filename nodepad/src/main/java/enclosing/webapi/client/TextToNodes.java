package enclosing.webapi.client;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.theuniversalgraph.application.nodepad.GetNodeFieldNodeComponent;
import com.theuniversalgraph.application.nodepad.NodeComponent;
import com.theuniversalgraph.application.nodepad.NodeFieldApplet;
import com.theuniversalgraph.application.nodepad.NodeObserver;

import enclosing.application.node.suggestion.AutoExpandOneStep;
import enclosing.faceless.GetFacelessNodeField;
import enclosing.util.NodeUtils;

public class TextToNodes {
	private static final String DATA_TEXT = "./data/text";
	public TextToNodes(){
		File dir = new File(DATA_TEXT);
		File[] textFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if(name.endsWith("txt")){
					return true;
				}
				return false;
			}
		});
		for (File file : textFiles) {
			String text = file.getName().replaceAll("\\.txt", "");
			try {
				text += FileUtils.readFileToString(file);
				new TextToNodes(text);
				file.delete();


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	public TextToNodes(String text) throws Exception{
		boolean directleft = true;
		if(text.contains("→")){
			directleft = false;
		}else if(!text.contains("←")){
			return;
		}
		String[] splitted = text.split("←");
		String parent = directleft?splitted[0].trim():splitted[1].trim();
		String child = directleft?splitted[1].trim():splitted[0].trim();

		if(directleft){
			link(parent,child,true);
		}else{
			link(child,parent,true);
		}

	}
	private void link(String target, String expanded, boolean expandsToChild) {
		try {
			NodeFieldApplet applet = GetFacelessNodeField.initApplet();
			
			applet.getObserver().getNodepadDao().openFromFile(GetFacelessNodeField.getBasedirFile(target).getAbsolutePath()+"/"+NodeUtils.saferStringOf(target)+".json");
			NodeComponent expandedNodeComponent = GetNodeFieldNodeComponent.findExpandedNodeComponent(applet,NodeUtils.removeTagString(NodeUtils.saferStringOf(target)));
			if(expandsToChild){
				AutoExpandOneStep.oneStepForChild(expandedNodeComponent, applet.getObserver().getNode_components(), expanded);
			}else{
				AutoExpandOneStep.oneStepForParent(expandedNodeComponent, expanded,applet.getObserver().getNode_components().elements());
			}

			applet.getObserver().getNodepadDao().saveToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TextToNodes (NodeComponent nodeComponent,NodeObserver observer){

		String[] nodesource = new String[2];
		Vector vector = new Vector();
		int index = 0;
		String content = nodeComponent.getNodeInterface().getContent();
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
	public static void main(String[] args) {
		TextToNodes textToNodes = new TextToNodes();
	}
}
