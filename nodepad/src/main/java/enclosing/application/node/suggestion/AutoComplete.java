package enclosing.application.node.suggestion;

import java.util.Iterator;
import java.util.Vector;

public class AutoComplete {
	public AutoComplete(String[] suggested,String suggestee){
		boolean solo = false;
		Vector vector = new Vector();
		for (int i = 0; i < suggested.length; i++) {
			if(suggested[i].startsWith(suggestee)){
				if(solo){
					vector.add(suggested[i]);
					solo = false;
					suggestion = null;
				}else{
					solo = true;
					suggestion = suggested[i];
				}
			}
		}
		if(suggestion == null && vector.size() !=0){
			String workingsuggestion = suggestee;
			int currentlength = suggestee.length();
			while(suggestion == null){
				currentlength++;
				workingsuggestion = (String)vector.elementAt(0);
				workingsuggestion = workingsuggestion.substring(0,currentlength);
				for (Iterator iter = vector.iterator(); iter.hasNext();) {
					String suggestedstr = (String) iter.next();
					if(!suggestedstr.substring(0,currentlength).equals(workingsuggestion)){
						suggestion = workingsuggestion.substring(0,workingsuggestion.length()- 1);
						break;
					}
				}
			}
		}
	}
	String suggestion = null;
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	
	

}
