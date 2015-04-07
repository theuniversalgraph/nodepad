package enclosing.application.node.predicate;

import java.util.Hashtable;

import enclosing.faceless.AllNode;
import enclosing.model.Node;

public class Terminal extends NodePredicate{
	
	public boolean apply(Node node) {
//		Node realNode = allNode.findNodeFieldNode(node.getContent());
//		if(realNode==null){
//			return true;
//		}
		if(node.getContent().startsWith("@done")){
			return false;
		}
		boolean childrenAllDone = false;
		for (Object childNodeId : node.getChildren()) {
			if(((Node)this.nodehash.get(childNodeId)).getContent().startsWith("@done")){
				childrenAllDone = true;
			}else{
				childrenAllDone = false;
			}
		}
    	return (node.getParents().size()>=1 && node.getChildren().size()<1) || (node.getChildren().size()>0 && childrenAllDone);
	}

}
