package enclosing.faceless;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import enclosing.application.node.predicate.Terminal;
import enclosing.model.Node;
import enclosing.util.NodeUtils;

public class Ancestors {
	AllNode allNode = AllNode.getInstance();
	Collection<String> processed = new ArrayList<String>();
	Collection<String> globalTerminals  = new Vector<String>();;

	public Ancestors(String nodeContent){
		Node node = allNode.findNodeFieldNode(nodeContent);
//		new Ancestors(nodeContent,new Terminal(),this.processed);

	}
	public Ancestors(String node,Predicate<String> predicate,Collection processed){
		node=NodeUtils.removeTagString(NodeUtils.saferStringOf(node));
		if(globalTerminals==null){
			globalTerminals = new Vector<String>();
		}
		if(processed==null){
			processed = new ArrayList<String>();
		}
		if(!processed.contains(node)){
			processed.add(node);

			Vector<String> children = AllNode.getAllChildren(node);
			Collection<String> terminals = Collections2.filter(children, predicate);
			globalTerminals.addAll(terminals);
			for (String node2 : children) {
//				new DescendantsOf(node2, globalTerminals, predicate,processed);
			}
		}
	}
}
