package enclosing.application.node.predicate;

import java.util.Hashtable;

import com.google.common.base.Predicate;
import com.theuniversalgraph.model.Node;

public abstract class NodePredicate implements Predicate<Node>{
	int hop = 0;
	Hashtable nodehash = null;
	public int getHop(){
		return hop;
	}
	public void setHop(int hop2) {
		this.hop = hop2;

	}
	public void setNodehash(Hashtable nodefieldHash) {
		this.nodehash = nodefieldHash;
		
	}
}
