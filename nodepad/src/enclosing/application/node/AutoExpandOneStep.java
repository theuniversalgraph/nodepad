package enclosing.application.node;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public class AutoExpandOneStep {
	public AutoExpandOneStep(String wikistring,NodeComponent  expandedNodeComponent,Hashtable existingNodeComponents){

        try
        {
        	
        	File ndfile = new File(expandedNodeComponent.getObserver().getFilename());
        	File dir = ndfile.getParentFile();
        	String filename = null;
        	if(expandedNodeComponent.getObserver().isMac()){
        		filename =  dir.getAbsolutePath() + "/"+ wikistring +".nd";
        	}else{
        		filename = dir.getAbsolutePath() + "/" + wikistring +".nd";
        	}

            ObjectInputStream in =new ObjectInputStream(new FileInputStream(filename)) ;
            Hashtable nodes   = null;
            nodes = (Hashtable)in.readObject();
            in.close();
            
            
            Enumeration enumeration = nodes.elements();
            Node theNodeWithTheSameNameAsWikiString = null;
            while (enumeration.hasMoreElements()) {
				Node node = (Node) enumeration.nextElement();
				if(node.getContent().equals(wikistring)){
					theNodeWithTheSameNameAsWikiString = node;
//					theNodeWithTheSameNameAsWikiStringExists = true;
					break;
				}
			}
            
            
            if(theNodeWithTheSameNameAsWikiString!=null){
            	Enumeration childs = theNodeWithTheSameNameAsWikiString.getChildren().elements();
            	while (childs.hasMoreElements()) {
					Node childNode = (Node) nodes.get(childs.nextElement());
					Enumeration childrenOfExpandedNode  = expandedNodeComponent.getChildren().elements();
					boolean duplicated = false;
		        	Enumeration existingNodeComponentsEnumeration = existingNodeComponents.elements();

		        	//make connectiong as children if already existing
					while (existingNodeComponentsEnumeration.hasMoreElements()) {
						NodeComponent oneExistingNode = (NodeComponent) existingNodeComponentsEnumeration.nextElement();
						if(childNode.getContent().equals(oneExistingNode.getNodeinterface().getContent())){
							duplicated = true;
							
							//make connection not a node.
							expandedNodeComponent.makeConnection(oneExistingNode);
							
							break;
						}
					}

					while (childrenOfExpandedNode.hasMoreElements()) {
						NodeComponent childOfExpandedNodeComponent = (NodeComponent) childrenOfExpandedNode.nextElement();
						if(childNode.getContent().equals(childOfExpandedNodeComponent.getNodeinterface().getContent())){
							duplicated = true;
							break;
						}
					}
					if(!duplicated){
			           	NodeComponent createdChild = expandedNodeComponent.createNewChild();
			           	createdChild.setText(childNode.getContent());
					}
				}
            	
            	Enumeration parents = theNodeWithTheSameNameAsWikiString.getParents().elements();
            	while (parents.hasMoreElements()) {
					Node parentNode = (Node) nodes.get(parents.nextElement());
					boolean duplicated = false;
		        	Enumeration existingNodeComponentsEnumeration = existingNodeComponents.elements();

		        	//make connectiong as children if already existing
					while (existingNodeComponentsEnumeration.hasMoreElements()) {
						NodeComponent oneExistingNode = (NodeComponent) existingNodeComponentsEnumeration.nextElement();
						if(parentNode.getContent().equals(oneExistingNode.getNodeinterface().getContent())){
							duplicated = true;
							
							//make connection not a node.
							oneExistingNode.makeConnection(expandedNodeComponent);
							
							break;
						}
					}
					
					
					
					
					
					Enumeration parentsOfExpandedNode = expandedNodeComponent.getParents().elements();
					while (parentsOfExpandedNode.hasMoreElements()) {
						NodeComponent parentOfExpandedNodeComponent = (NodeComponent) parentsOfExpandedNode.nextElement();
						if(parentNode.getContent().equals(parentOfExpandedNodeComponent.getNodeinterface().getContent())){
							duplicated = true;
							break;
						}
					}
					if(!duplicated){
						NodeComponent createdParent = expandedNodeComponent.createNewParent();
						createdParent.setText(parentNode.getContent());
					}
				}
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



	}
}
