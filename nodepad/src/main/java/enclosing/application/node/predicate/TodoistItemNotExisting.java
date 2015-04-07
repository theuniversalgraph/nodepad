package enclosing.application.node.predicate;

import java.util.Collection;

import net.enclosing.todoist.Item;
import net.enclosing.todoist.Todoist;

import com.google.common.base.Predicate;

import enclosing.model.Node;
import enclosing.util.NodeUtils;

public class TodoistItemNotExisting implements Predicate<Node> {
	Collection<Item> allItems = null;
	public  TodoistItemNotExisting(Collection<Item> alliItems){
		this.allItems = alliItems;
	}

	public boolean apply(Node input) {
		return Todoist.find(NodeUtils.removeTagString(input.getContent()).trim(),allItems).size()==0;
	}


}
