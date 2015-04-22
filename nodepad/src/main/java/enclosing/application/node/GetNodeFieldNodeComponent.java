package enclosing.application.node;

import java.util.Enumeration;

import enclosing.util.NodeUtils;

public class GetNodeFieldNodeComponent {
	public static NodeComponent findExpandedNodeComponent(NodeFieldApplet applet, String nodeContent) {
		NodeComponent nodeComponent = (NodeComponent)applet.observer.getNode_components().get("1");
		if(nodeComponent == null){
			nodeComponent = applet.getObserver().makeNewFieldNode(nodeContent);
			return nodeComponent;
		}
		if(NodeUtils.removeTagString(nodeComponent.getNodeInterface().getContent()).equalsIgnoreCase(nodeContent)){
			return nodeComponent;
		}else{
			System.err.println("oh........");
		}
		return null;
			
//		Enumeration<NodeComponent> nodeComponentsEnumeration = applet.observer.getNode_components().elements();
//		while (nodeComponentsEnumeration.hasMoreElements()) {
//			NodeComponent nodeComponent = (NodeComponent) nodeComponentsEnumeration
//					.nextElement();
//			System.err.println(nodeComponent.getNodeInterface().getContent());
//			if(NodeUtils.removeTagString(nodeComponent.getNodeInterface().getContent()).equalsIgnoreCase(nodeContent)){
//				return nodeComponent;
//			}
//		}
//
//		return null;
	}

}
