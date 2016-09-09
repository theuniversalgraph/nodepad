package enclosing.application.node.suggestion;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import com.theuniversalgraph.application.nodepad.NodeComponent;

import enclosing.application.node.wiki.WikiLinkComponent;

public class AutoExpandOneStepForNodeComponents {
	public AutoExpandOneStepForNodeComponents(Enumeration enumeration, Hashtable nodeComponents){
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			for (Iterator iter = nodeComponent.getWikilinks().iterator(); iter.hasNext();) {
				WikiLinkComponent	wikiLinkComponent= (WikiLinkComponent) iter.next();
				AutoExpandOneStep autoExpandOneStep 
				 = new AutoExpandOneStep(wikiLinkComponent.getBranketContent(),nodeComponent,nodeComponents,2);
			}
		}
		
	}
}
 