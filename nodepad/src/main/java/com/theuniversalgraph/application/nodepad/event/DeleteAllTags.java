package com.theuniversalgraph.application.nodepad.event;

import java.util.Collections;
import java.util.Hashtable;

import com.theuniversalgraph.application.nodepad.NodeComponent;

import enclosing.util.NodeUtils;

public class DeleteAllTags {

	public DeleteAllTags(Hashtable<String, NodeComponent> node_components) {
		Collections.list(node_components.elements()).forEach(NodeUtils::removeTagStringAndSave);
	}
	

}
