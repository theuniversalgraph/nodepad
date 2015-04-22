package enclosing.application.node.suggestion;

import java.util.Enumeration;
import java.util.Iterator;

import com.theuniversalgraph.application.nodepad.NodeComponent;
import com.theuniversalgraph.application.nodepad.NodeObserver;

import enclosing.application.node.wiki.WikiLinkComponent;

public class AutoExpandOneStepForAllWikiLinkComponent {
	public AutoExpandOneStepForAllWikiLinkComponent(NodeObserver nodeObserver){
		Enumeration enumeration = nodeObserver.getNode_components().elements();
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			for (Iterator iter = nodeComponent.getWikilinks().iterator(); iter.hasNext();) {
				WikiLinkComponent	wikiLinkComponent = (WikiLinkComponent) iter.next();
				AutoExpandOneStep autoExpandOneStep = new AutoExpandOneStep(wikiLinkComponent.getBranketContent(),nodeComponent,nodeObserver.getNode_components());
			}
		}
	}
}
