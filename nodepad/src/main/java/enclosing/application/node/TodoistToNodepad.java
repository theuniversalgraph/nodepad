package enclosing.application.node;

import java.util.Collection;

import net.enclosing.todoist.Item;
import net.enclosing.todoist.Note;
import net.enclosing.todoist.Todoist;

public class TodoistToNodepad {

	public TodoistToNodepad(){
		Collection<Item> items = Todoist.getItems();
		for (Item item : items) {
			Collection<Note> notes = item.getNotes();
			for (Note note : notes) {
				if(!note.getContent().startsWith("#")){
					if(note.getContent().contains("←") || note.getContent().contains("→")){
						String linkText = item.getContent()+ note.getContent(); 
						try {
							new TextToNodes(linkText);
							note.setContent("#"+note.getContent());
							note.updateNote();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
 
		
	}
	
	public static void main(String[] args) {
		new TodoistToNodepad();
	}
}
