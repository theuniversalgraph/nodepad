package enclosing.application.node;

import java.util.Enumeration;

public class NodeUtils {
	public static String saferStringOf(String content) {
		String returned = removeTagString(content); 
		returned = returned.replaceAll("\\s", "_");
		returned = returned.replaceAll("\\[", "");
		returned = returned.replaceAll("\\]", "");
		return returned;
	}
	public static String removeTagString(String content) {
		TagHash tagHash = TagHash.getInstance();
		Enumeration<String> tags = tagHash.getTags();
		while(tags.hasMoreElements()){
			String tag = tags.nextElement();
			content = content.replaceAll(tag + "\\s", "");
			content = content.replaceAll(tag, "");
		}
		return content;
	}
	public static String comparable(String nodefield) {
		return saferStringOf(removeTagString(nodefield));
	}

}
