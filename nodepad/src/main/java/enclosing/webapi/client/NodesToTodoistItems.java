package enclosing.webapi.client;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import net.enclosing.todoist.Item;
import net.enclosing.todoist.Project;
import net.enclosing.todoist.Todoist;

import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import enclosing.application.node.predicate.DescendantsOf;
import enclosing.application.node.predicate.TodoistItemNotExisting;
import enclosing.model.Node;

public class NodesToTodoistItems {
	String NODE_WEB_BASEURL = "http://zxc.cz/e2h/p.html?query=";
	public NodesToTodoistItems(){
		String[] projects = enclosing.model.Project.getProjects();
		for (String project : projects) {
			new NodesToTodoistItems(project);
		}
	}
	public NodesToTodoistItems(String projectNode){

		Collection<Node> terminals = DescendantsOf.terminal(projectNode);
		Collection<Item> allItems = Todoist.allItems();
		Collection<Node> newlyCreated = Collections2.filter(terminals, new TodoistItemNotExisting(allItems));
		for (Node node : newlyCreated) {
			Item item = new Item();
			item.setContent(node.getContent());
//			if(item.getContent().startsWith("@")){
//				item.setContent("#test "+item.getContent());
//			}
			if(item.getContent().startsWith("@")){
				List<Project> projects = Todoist.getProjects();
				for (Project project : projects) {
					if(item.getContent().startsWith(project.getName())){
						item.setProject_id(project.getId());
					}
				}
			}
			try {
				String addedString = item.add();
				Type itemType = new TypeToken<Item>(){}.getType();
				Gson gson = new Gson();
				Item returnedItem = gson.fromJson(addedString, itemType);
				
				item.setId(returnedItem.getId());
				String projectNodeFieldUrl = NODE_WEB_BASEURL + projectNode;
				item.addNote(projectNode);
				item.addNote(projectNodeFieldUrl);
				
				String projectNodeFieldUrlForContext = DescendantsOf.relatedInString(projectNode);
				if(projectNodeFieldUrlForContext.length()>49){
					projectNodeFieldUrlForContext = projectNodeFieldUrlForContext.substring(0, 50);
				}

				item.addNote(NODE_WEB_BASEURL+projectNodeFieldUrlForContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.err.println("closed");
	}
	public static void main(String[] args) {
		new  NodesToTodoistItems();
	}
}
