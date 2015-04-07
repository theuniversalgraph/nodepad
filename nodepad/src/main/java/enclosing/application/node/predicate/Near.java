package enclosing.application.node.predicate;

import enclosing.model.Node;

public class Near extends NodePredicate {
	public boolean apply(Node input) {
		return hop < 10;
	}
}
