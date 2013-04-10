package enclosing.application.node;

import java.util.Enumeration;
import java.util.Iterator;

import enclosing.application.node.wiki.WikiLinkComponent;

public class AutoExpandOneStepForNodeComponents {
	public AutoExpandOneStepForNodeComponents(Enumeration enumeration){
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			for (Iterator iter = nodeComponent.getWikilinks().iterator(); iter.hasNext();) {
				WikiLinkComponent	wikiLinkComponent= (WikiLinkComponent) iter.next();
				AutoExpandOneStep autoExpandOneStep 
				 = new AutoExpandOneStep(wikiLinkComponent.getBranketContent(),nodeComponent);
			}
		}
		
	}
}
 