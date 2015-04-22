package enclosing.application.node.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.annotation.WebServlet;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.theuniversalgraph.model.Node;

import enclosing.faceless.AllNode;
import enclosing.faceless.MakeNodeFilesForExistingNodes;
import enclosing.model.Project;
import enclosing.util.NodeUtils;

@WebServlet("/SearchNotesServlet")
public class DescendantsOf {
	private static final int MAX_HOP = 7;
	AllNode allNode = AllNode.getInstance();
	Collection<Node> globalFiltered  = new Vector<Node>();;
	
	public DescendantsOf(String nodefield, NodePredicate predicate,Hashtable nodefieldHash, Collection processed2){
		nodefield=NodeUtils.saferStringOf(NodeUtils.removeTagString(nodefield));
		Node realNode = allNode.findNodeFieldNode(nodefield);

		new DescendantsOf(realNode,this.globalFiltered,predicate,processed2,1,nodefieldHash);
	}
	public DescendantsOf(Node node,Collection<Node> globalFiltered, NodePredicate predicate,Collection processed,int hop,Hashtable nodefieldHash){
		if(node==null){
			return;
		}
		predicate.setHop(hop);
		hop++;

		Project project = new Project();
		//		node=NodeUtils.removeTagString(NodeUtils.saferStringOf(node.getContent()));
		if(hop>MAX_HOP){
			return ;
		}
		if(!processed.contains(node.getContent()) && !processed.contains(NodeUtils.comparable(node.getContent()))){
			processed.add(NodeUtils.comparable(node.getContent()));
			Vector<String> children = node.getChildren();
			Vector<Node> nodeChildren = packIntoNodes(children, nodefieldHash);
//			Vector<String> children = AllNode.getAllChildren(node);
			Collection<Node> filtered = Collections2.filter(nodeChildren, predicate);
			globalFiltered.addAll(filtered);
			System.err.println(hop+" this is the hop number in the descendants");
			
			for (String child: children) {
				Node childNode = (Node)nodefieldHash.get(child);
				new DescendantsOf(childNode, globalFiltered, predicate,processed,hop,nodefieldHash);
				if(project.apply(childNode) && !processed.contains(NodeUtils.comparable(childNode.getContent()))){
					String childNodeFieldName = NodeUtils.comparable(childNode.getContent());
					Collection<Node> matched =  find(childNodeFieldName,new Terminal(),processed);
					globalFiltered.addAll(matched);
				}
			}
		}
	}

	private Vector<Node> packIntoNodes(Vector<String> children,Hashtable nodehash){
		Vector<Node> nodes = new Vector<Node>();
		for (String string : children) {
			nodes.add((Node)nodehash.get(string));
		}
		return nodes;
	}
	public static Collection<Node> find(String nodefield, NodePredicate predicate, Collection processed2){
		nodefield = NodeUtils.comparable(nodefield);
		AllNode allNode = AllNode.getInstance();
		Hashtable nodefieldHash = (Hashtable)allNode.allNodesHash.get(nodefield);	
		predicate.setNodehash(nodefieldHash);
		MakeNodeFilesForExistingNodes.makeNodeFieldsFromNodesOfANodeFields(allNode.getAllNodesHash(),nodefieldHash );
		DescendantsOf descendantsOf = new DescendantsOf(nodefield,predicate,nodefieldHash,processed2);
		Collection<Node> globalFiltered = descendantsOf.globalFiltered;
		//just removing the duplication. http://stackoverflow.com/questions/12242083/remove-duplicates-from-list-using-guava
		globalFiltered = ImmutableSet.copyOf(globalFiltered).asList();
		return globalFiltered;
	}
	
	public static Collection<Node> terminal(String node) {
		return find(node,new Terminal(),new ArrayList<Node>());
	}
	public static Collection<Node> related(String node) {
		return find(node,new Near(),new ArrayList<Node>());
	}
	public static String relatedInString(String startNode){
		Collection<Node> related =  related(startNode);
		StringBuilder builder = new StringBuilder();
		for (Node node : related) {
			builder.append(node.getContent());
			builder.append(" ");
		}
		return builder.toString();
	}
	public static void main(String[] args) {
		
		String startnode = "better_terminal";
		
		Collection<Node> terminals = terminal(startnode);
		for (Node node : terminals) {
			System.err.print(node.getContent());
			System.err.print(" ");
		}
		
		Collection<Node> related =  related(startnode);
		for (Node node : related) {
			System.err.print(node.getContent());
			System.err.print(" ");
		}
	}
}
