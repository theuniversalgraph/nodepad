package com.theuniversalgraph.application.nodepad;

import java.awt.Point;
import java.util.Hashtable;

import lombok.Data;

@Data
public class Mode {
	private Hashtable selected;
	boolean hardDelete;
	private Hashtable selected_line;
	private boolean connecting;
	private Object mode_object;
	private String mode;
	private SelectingZone selectingZone;
	private Point connecting_start;
	private boolean mac =false;
	private boolean faceless;

}
