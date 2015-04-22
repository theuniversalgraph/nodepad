/*
 */
package com.theuniversalgraph.application.nodepad;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Administrator
 */
public class NodepadWindowAdapter extends WindowAdapter {

	/**
	 * 
	 * @uml.property name="observer"
	 * @uml.associationEnd 
	 * @uml.property name="observer" multiplicity="(1 1)"
	 */
	NodeObserver observer = null;

	public NodepadWindowAdapter(NodeObserver observer){
		this.observer= observer;
	}
	public void windowClosing(WindowEvent windowevent)	{
		this.observer.setWindowevent(windowevent);
		if(!this.observer.checkDirtyAndSave()){
			observer.applicationEnds(windowevent);
		}
	}
	

}
