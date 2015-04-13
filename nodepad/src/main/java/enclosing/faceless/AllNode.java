package enclosing.faceless;

import core.model.Node;
import enclosing.application.node.NodeComponent;
import enclosing.util.NodeUtils;
import myutil.filehandler;

import java.io.File;
import java.util.*;

public class AllNode {
	List<NodeComponent> nodeComponents = new ArrayList<NodeComponent>();
	Vector<Node> vec = new Vector<Node>();
	public static Hashtable<String, Hashtable> allNodesHash = new Hashtable<String, Hashtable>();
	public static AllNode allNode = null;
	public static AllNode getInstance(){
		if(AllNode.allNode == null){
			AllNode.allNode = new AllNode(); 
		}
		return AllNode.allNode;
	}
	private AllNode(){
		File[] nodeFiles = new GetNodeFiles().getFiles();

		//if the file is the nodefile,get the content.
		for(int i= 0;i < nodeFiles.length;i++){
			Object obj = filehandler.objectInputer(nodeFiles[i].getAbsolutePath());
			Hashtable nodefieldHash = (Hashtable)obj;	
			if(nodefieldHash == null){
				nodefieldHash = new Hashtable();
			}
			allNodesHash.put(nodeFiles[i].getName().replaceAll("\\.json", ""), nodefieldHash);
			for(Enumeration en = nodefieldHash.elements();en.hasMoreElements();){
				vec.add((Node)en.nextElement());
			}
		}

	}
	public static Vector<String> getAllChildren(String node) {
		node=NodeUtils.saferStringOf(NodeUtils.removeTagString(node));
		Node realNode = allNode.findNodeFieldNode(node);
		if(realNode==null){
			return new Vector<String>();
		}
		Vector<String> childrenInString = new Vector<String>();
		Hashtable nodefieldHash = (Hashtable)allNode.allNodesHash.get(node);	
		for (Object childId : realNode.getChildren()) {
			String childIdString = (String)childId;
			String childContent = ((Node)nodefieldHash.get(childId)).getContent();
			childrenInString.add(childContent);
			
			
			
		}
		
		return childrenInString;
	
	}
	public List<String> getChildren(String targetNodeString){
		List<String> children = new ArrayList<String>();

		Enumeration<String> nodeFieldsEnumeration = allNodesHash.keys();
		while (nodeFieldsEnumeration.hasMoreElements()) {
			String nodefield = (String) nodeFieldsEnumeration.nextElement();
			Hashtable nodefieldHash = allNodesHash.get(nodefield);
			for(Enumeration en = nodefieldHash.elements();en.hasMoreElements();){
				Node foundNode = (Node)en.nextElement(); 
				if(foundNode.getContent().equalsIgnoreCase(targetNodeString)){
					for (Object childId : foundNode.getChildren()) {
						children.add(((Node)nodefieldHash.get(childId)).getContent());
					}
				}
			}			
		}
		return children;
		
	}
	public List<String> getParents(String targetNodeString){
		List<String> parents = new ArrayList<String>();

		Enumeration<String> nodeFieldsEnumeration = allNodesHash.keys();
		while (nodeFieldsEnumeration.hasMoreElements()) {
			String nodefield = (String) nodeFieldsEnumeration.nextElement();
			Hashtable nodefieldHash = allNodesHash.get(nodefield);
			for(Enumeration en = nodefieldHash.elements();en.hasMoreElements();){
				Node foundNode = (Node)en.nextElement(); 
				if(foundNode.getContent().equalsIgnoreCase(targetNodeString)){
					for (Object parentId : foundNode.getParents()) {
						parents.add(((Node)nodefieldHash.get(parentId)).getContent());
					}
				}
			}			
		}
		return parents;
		
	}
	public Node findNodeFieldNode(String nodefield){
		Hashtable nodefieldHash = (Hashtable<String, Hashtable>)allNodesHash.get(nodefield);
		if(nodefieldHash==null){
			nodefieldHash = nodefieldHash = (Hashtable<String, Hashtable>)allNodesHash.get(NodeUtils.saferStringOf(NodeUtils.removeTagString(nodefield)));
			if(nodefieldHash==null){
				return null;
			}
		}
		Enumeration<Node> nodes = nodefieldHash.elements();
		while (nodes.hasMoreElements()) {
			Node node = (Node) nodes.nextElement();
			if(NodeUtils.saferStringOf(NodeUtils.removeTagString(node.getContent())).equalsIgnoreCase(NodeUtils.saferStringOf(NodeUtils.removeTagString(nodefield)))){
				return node;
			}
		}
		return null;
	}
	public Vector<Node> getAllTheNodes(){
		return this.vec;
	}
	public void dump(){
		Vector textfileoutput = new Vector();
		for(Enumeration en = this.vec.elements();en.hasMoreElements();){
			Node node = (Node)en.nextElement();
			textfileoutput.add(node.getId()+":"+node.getContent());
		}
		filehandler.outputer("allnodes.txt",textfileoutput);
	}
	public static void main(String[] args) {
		AllNode allNode = new AllNode();
		allNode.dump();
	}
	public Hashtable<String, Hashtable> getAllNodesHash() {
		return allNodesHash;
	}
	
}
