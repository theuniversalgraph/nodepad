package enclosing.application.node.suggestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import enclosing.application.node.Node;
import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;
import enclosing.faceless.GetFacelessNodeField;
import enclosing.util.NodeUtils;

public class AutoExpandOneStep {
	Node theNodeWithTheSameNameAsWikiString = null;
	public AutoExpandOneStep(String wikistring,NodeComponent  expandedNodeComponent,Hashtable existingNodeComponents){
        wikistring = wikistring.replaceAll("\\s", "_");
        wikistring = NodeUtils.removeTagString(wikistring);
        wikistring = NodeUtils.saferStringOf(wikistring);

        String filename = GetFacelessNodeField.getBasedirFile(wikistring).getAbsolutePath()+"/"+NodeUtils.saferStringOf(wikistring)+".json";
        Hashtable nodes   = (Hashtable) NodeObserver.inputNodesFromFile(filename);

        Enumeration enumeration = nodes.elements();
        Node theNodeWithTheSameNameAsWikiString = null;
        while (enumeration.hasMoreElements()) {
            Node node = (Node) enumeration.nextElement();
            final String safeNodeContent = NodeUtils.removeTagString(NodeUtils.saferStringOf(node.getContent()));
            if(safeNodeContent.equals(wikistring)){
                theNodeWithTheSameNameAsWikiString = node;
//				theNodeWithTheSameNameAsWikiStringExists = true;
                break;
            }
        }
        new AutoExpandOneStep(theNodeWithTheSameNameAsWikiString,expandedNodeComponent,existingNodeComponents,nodes);
	}
	public AutoExpandOneStep(Node theNodeWithTheSameNameAsWikiString,NodeComponent  expandedNodeComponent,Hashtable existingNodeComponents, Hashtable nodes){

        try
        {


            if(theNodeWithTheSameNameAsWikiString!=null){
            	Enumeration childs = theNodeWithTheSameNameAsWikiString.getChildren().elements();
            	while (childs.hasMoreElements()) {
					Node childNode = (Node) nodes.get(childs.nextElement());
					String childContent = childNode.getContent();
					childContent = NodeUtils.removeTagString(NodeUtils.saferStringOf(childContent));

					oneStepForChild(expandedNodeComponent,
							existingNodeComponents, childContent);
				}

            	Enumeration parents = theNodeWithTheSameNameAsWikiString.getParents().elements();
            	while (parents.hasMoreElements()) {
					Node parentNode = (Node) nodes.get(parents.nextElement());
					String parentContent = NodeUtils.removeTagString(NodeUtils.saferStringOf(parentNode.getContent()));
		        	Enumeration existingNodeComponentsEnumeration = existingNodeComponents.elements();

		        	oneStepForParent(expandedNodeComponent,
							parentContent, existingNodeComponentsEnumeration);
				}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



	}
	public static void oneStepForParent(NodeComponent expandedNodeComponent,
			 String parentContent,
			Enumeration existingNodeComponentsEnumeration) {
		boolean duplicated = false;

		//make connectiong as children if already existing
		while (existingNodeComponentsEnumeration.hasMoreElements()) {
			NodeComponent oneExistingNode = (NodeComponent) existingNodeComponentsEnumeration.nextElement();
			if(parentContent.equals(NodeUtils.saferStringOf(NodeUtils.removeTagString(oneExistingNode.getNodeinterface().getContent())))){
				duplicated = true;

				//make connection not a node.
				oneExistingNode.makeConnection(expandedNodeComponent);

				break;
			}
		}





		Enumeration parentsOfExpandedNode = expandedNodeComponent.getParents().elements();
		while (parentsOfExpandedNode.hasMoreElements()) {
			NodeComponent parentOfExpandedNodeComponent = (NodeComponent) parentsOfExpandedNode.nextElement();
			if(parentContent.equals(parentOfExpandedNodeComponent.getNodeinterface().getContent())){
				duplicated = true;
				break;
			}
		}
		if(!duplicated){
			NodeComponent createdParent = expandedNodeComponent.createNewParent();
			createdParent.setText(parentContent);
		}
	}
	public static void oneStepForChild(NodeComponent expandedNodeComponent,
			Hashtable existingNodeComponents, String childContent) {
		Enumeration childrenOfExpandedNode  = expandedNodeComponent.getChildren().elements();
		boolean duplicated = false;
		Enumeration existingNodeComponentsEnumeration = existingNodeComponents.elements();

		//make connectiong as children if already existing
		while (existingNodeComponentsEnumeration.hasMoreElements()) {
			NodeComponent oneExistingNode = (NodeComponent) existingNodeComponentsEnumeration.nextElement();

			final String oneExistingNodeStringWithoutTags = NodeUtils.saferStringOf(NodeUtils.removeTagString(oneExistingNode.getNodeinterface().getContent()));
			if(childContent.equals(oneExistingNodeStringWithoutTags)){
				duplicated = true;

				//make connection not a node.
				expandedNodeComponent.makeConnection(oneExistingNode);

				break;
			}
		}

		while (childrenOfExpandedNode.hasMoreElements()) {
			NodeComponent childOfExpandedNodeComponent = (NodeComponent) childrenOfExpandedNode.nextElement();
			if(childContent.equals(NodeUtils.removeTagString(childOfExpandedNodeComponent.getNodeinterface().getContent()))){
				duplicated = true;
				break;
			}
		}
		if(!duplicated){
		   	NodeComponent createdChild = expandedNodeComponent.createNewChild();
		   	createdChild.setText(childContent);
		}
	}


}
