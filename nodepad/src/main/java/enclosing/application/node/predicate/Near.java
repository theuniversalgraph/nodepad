package enclosing.application.node.predicate;

import com.theuniversalgraph.model.Node;

public class Near extends NodePredicate {
	public boolean apply(Node input) {
		return hop < 10;
	}
}
