package enclosing.application.node.predicate;

import com.google.common.base.Predicate;

import enclosing.model.Node;

public class Solo implements Predicate<Node>{

	public boolean apply(Node node) {
		return node.getParents().size()<1 && node.getChildren().size()<1?true:false;
	}

}
