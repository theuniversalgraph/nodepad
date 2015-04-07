package enclosing.faceless;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.map.HashedMap;

import enclosing.application.node.GetNodeFieldNodeComponent;
import enclosing.application.node.Node;
import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeFieldApplet;
import enclosing.application.node.suggestion.AutoExpandOneStep;
import enclosing.util.NodeUtils;
import myutil.filehandler;

public class MakeNodeFilesForExistingNodes {
	private static final String TIME_STAMP_FILE = "nodefield_process_map.txt";
	private Map<String,Long> nodefield_processedTimeMap = null;;
	public MakeNodeFilesForExistingNodes(){
		loadTimeStamp();
		AllNode allNode = AllNode.getInstance();
		Hashtable<String, Hashtable> allNodesHash = allNode.getAllNodesHash();
		int i= 0;
		Enumeration<String> nodeFieldsEnumeration = allNodesHash.keys();
		while (nodeFieldsEnumeration.hasMoreElements()) {
			String nodefield = (String) nodeFieldsEnumeration.nextElement();
			if(isDirty(nodefield)){
				Hashtable nodefieldHash = allNodesHash.get(nodefield);
				makeNodeFieldsFromNodesOfANodeFields(allNodesHash,
						nodefieldHash);
				markTimeStamp(nodefield);

			}
			
			i++;
			if(i%100==0){
				System.err.println("-*********************** --yeah 100");
			}
//			if(i>1){
//				break;
//			}
		} 

	}

	public static void makeNodeFieldsFromNodesOfANodeFields(
			Hashtable<String, Hashtable> allNodesHash, Hashtable nodefieldHash) {
		if(allNodesHash==null){
			AllNode allNode = AllNode.getInstance();
			allNodesHash = allNode.getAllNodesHash();
		}
		System.err.println("iiiiiiiiiiiii");
		for(Enumeration en = nodefieldHash.elements();en.hasMoreElements();){
			Node foundNode = (Node)en.nextElement();
			System.err.println(foundNode.getContent());
			if(foundNode.content.contains("のstatusをみたい")){
				System.err.println("aaaaaaaoiuhygfdfgh");
			}
			if(!foundNode.getContent().contains("*")){
				
				if(allNodesHash.get(NodeUtils.saferStringOf(foundNode.getContent()))==null){
					makeNodeFile(foundNode, nodefieldHash); 
				}else{
					createLines(foundNode, null,nodefieldHash);
				}
				
			}
		}
		System.err.println("aaaaaaaaaaaa");
	}

	private boolean isDirty(String nodefield) {
		if(!this.nodefield_processedTimeMap.containsKey(nodefield)){
			return true;
		}
		if(this.nodefield_processedTimeMap.get(nodefield).longValue() < getFileTimeStamp(nodefield)){
			return true;
		}
		return false;
	}

	private long getFileTimeStamp(String nodefield) {
		File file = new File("./data/"+nodefield+".nd");
		return file.lastModified();
	}

	private void loadTimeStamp() {
		this.nodefield_processedTimeMap = (Map<String, Long>)filehandler.objectInputer(TIME_STAMP_FILE);
		if(this.nodefield_processedTimeMap==null){
			this.nodefield_processedTimeMap = new HashMap<String, Long>();
		}
	}

	private void markTimeStamp(String nodefield) {
		this.nodefield_processedTimeMap.put(nodefield, Long.valueOf(System.currentTimeMillis()));
		persistTimeStamp();
	}

	private void persistTimeStamp() {
		filehandler.objectOutputer(TIME_STAMP_FILE,this.nodefield_processedTimeMap);
		// TODO Auto-generated method stub
		
	}

	

	static void createLines(Node foundNode, NodeFieldApplet applet, Hashtable nodes) {
		try {
			File basedirFile = GetFacelessNodeField.getBasedirFile(foundNode.getContent());
			if(applet==null){
				applet = GetFacelessNodeField.initApplet();
				applet.getObserver().openFromFile(basedirFile.getAbsolutePath()+"/"+NodeUtils.saferStringOf(foundNode.getContent())+".nd");
			}

			NodeComponent expandedNodeComponent = GetNodeFieldNodeComponent.findExpandedNodeComponent(applet,NodeUtils.removeTagString(NodeUtils.saferStringOf(foundNode.getContent())));
			AutoExpandOneStep autoExpandOneStep = new AutoExpandOneStep(foundNode, expandedNodeComponent, applet.getObserver().getNode_components(),nodes);
			applet.getObserver().saveToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	static void makeNodeFile(Node foundNode, Hashtable nodes) {
		
		NodeFieldApplet applet = GetFacelessNodeField.initApplet();
		
		try {
			applet.getObserver().openFromFile(GetFacelessNodeField.getBasedirFile(foundNode.getContent()).getAbsolutePath()+"/"+NodeUtils.saferStringOf(foundNode.getContent())+".nd");
			createLines(foundNode,applet,nodes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}




	public static void main(String[] args) {
		MakeNodeFilesForExistingNodes makeNodeFilesForExistingNodes = new MakeNodeFilesForExistingNodes();
		
	}
}
