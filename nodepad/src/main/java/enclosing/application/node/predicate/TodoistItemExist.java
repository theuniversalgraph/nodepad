package enclosing.application.node.predicate;

import java.util.Collection;

import net.enclosing.todoist.Item;
import net.enclosing.todoist.Todoist;

import com.google.common.base.Predicate;

public class TodoistItemExist implements Predicate<String>{
	Collection<Item> allItems = null;
	public TodoistItemExist(Collection<Item> alliItems){
		this.allItems = alliItems;
	}

	public boolean apply(String input) {
		return Todoist.find(input,allItems).size()>0;
	}

}
