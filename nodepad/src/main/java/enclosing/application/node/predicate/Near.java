package enclosing.application.node.predicate;

import core.model.Node;

public class Near extends NodePredicate {
	public boolean apply(Node input) {
		return hop < 10;
	}
}
