package enclosing.application.node;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import enclosing.application.node.predicate.NodePredicate;
import enclosing.model.Node;
import net.enclosing.list.List;

public class Project extends NodePredicate{
	static String[] PROJECTS = new String[]{"diyk","cf0411","astamuse_contract","better link to node"}; 
//	static String[] PROJECTS = new String[]{"diyk"}; 
	public static String[] getProjects(){
		return Project.PROJECTS;
	}
	public void saveToList(){
		File[] files = new GetNodeFiles().getFiles();
		java.util.List<Node> nodes = makeNodesFromFiles(files);
		List nodeList = new List("./");
		nodeList.writeList(nodes, Node.class);
		
	}

	private java.util.List<Node> makeNodesFromFiles(File[] files) {
		java.util.List<Node> nodes = new ArrayList<Node>();
		for (File file : files) {
			Node node = new Node("0", file.getName(), "project");
			nodes.add(node);
		}
		return nodes;
	}


	public static void main(String[] args) {
		Project project = new Project();
		project.saveToList();
	}

	public boolean apply(Node node) {
		if(isInProjectList(node)){
			return true;
		}
		return node.getContent().startsWith("@project");
	}
	private boolean isInProjectList(Node node) {
		for (int i = 0; i < PROJECTS.length; i++) {
			if(NodeUtils.saferStringOf(NodeUtils.removeTagString(node.getContent())).equals(
					NodeUtils.saferStringOf(NodeUtils.removeTagString(PROJECTS[i])))){
				return true;
			}
		}
		return false;
	}
}