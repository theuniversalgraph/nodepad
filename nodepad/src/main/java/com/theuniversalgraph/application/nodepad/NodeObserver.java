// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeObserver.java

package com.theuniversalgraph.application.nodepad;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import lombok.Data;
import myutil.CounterInterface;

import com.theuniversalgraph.model.Node;
import com.theuniversalgraph.model.NodeInterface;

import enclosing.application.node.server.SaveNodeFileToServer;
import enclosing.application.node.suggestion.AutoExpandOneStep;
import enclosing.application.node.wiki.LengthComparator;
import enclosing.application.node.wiki.WikiLinkComponent;
import enclosing.awt.FlatDialogListener;
import enclosing.awt.YesNoCancelDialog;
import enclosing.util.NodeUtils;

@Data
public class NodeObserver extends Panel implements FlatDialogListener
{

	public String getNodefieldName(){
		final File file = new File(this.getFilename());
		final String abosulutePath = file.getAbsolutePath();
		final String withoutPath = abosulutePath.substring(abosulutePath.lastIndexOf("/")+1);
		String returened = withoutPath.replaceAll(SimpleStringConstants.FILE_POSTFIX, "");
		return returened;
	}


	public void makeComponents(Hashtable nodes)
	{
		NodeInterface node;
		for(Enumeration en = nodes.elements(); en.hasMoreElements(); node_components.put(node.getId(), new NodeComponent(this, node)))
			node = (NodeInterface)en.nextElement();

		for(Enumeration en = node_components.elements(); en.hasMoreElements();)
		{
			NodeComponent nc = (NodeComponent)en.nextElement();
			for(int i = 0; i < nc.getNodeInterface().getChildren().size(); i++)
			{
				NodeComponent temp = (NodeComponent)node_components.get((String)nc.getNodeInterface().getChildren().elementAt(i));
				nc.getChildren().put(temp.getNodeInterface().getId(), temp);
				temp.getParents().put(nc.getNodeInterface().getId(), nc);
			}
		}
	}

	public void addNC(NodeComponent nc)
	{
		node_components.put(nc.getNodeInterface().getId(), nc);
		node_container.add(nc,0);
		nc.repaint();
	}
	public void add(NodeComponent nc)
	{
		node_components.put(nc.getNodeInterface().getId(), nc);
		node_container.add(nc);
	}

	public void returnAllFromEditing()
	{
		for(Enumeration en = getNode_components().elements(); en.hasMoreElements();)
		{
			NodeComponent temp = (NodeComponent)en.nextElement();
			if(temp.isEditing())
				temp.returnFromEditing();
		}

	}

	public static Hashtable inputNodesViaIP(InetAddress add, int port)
	{
		Hashtable nodes = null;
		try
		{
			nodes = NodepadDAO.inputNodeObject(new ObjectInputStream((new Socket(add, port)).getInputStream()));
			return nodes;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	Hashtable getNodeHashFromNCHash() {
		Hashtable saved_nodes;
		NodeComponent nc;
		saved_nodes = new Hashtable();
		for(Enumeration en = node_components.elements(); en.hasMoreElements();){
			nc = (NodeComponent)en.nextElement();
			nc.updateNodeInterface();
			saved_nodes.put(nc.getNodeInterface().getId(),nc.getNodeInterface());
		}
		return saved_nodes;
	}




	public void setAllNodesNormal()
	{
		NodeComponent temp;
		for(Enumeration en = getNode_components().elements(); en.hasMoreElements(); temp.disselected())
		{
			temp = (NodeComponent)en.nextElement();
			if(temp.isEditing())
				temp.returnFromEditing();
		}

	}

	public void setTools()
	{
		/*        ToolComponent save_com = new ToolComponent(this, "save");
        save_com.setLocation(20,30);
        getNode_container().add(save_com);
        ToolComponent delete_com = new ToolComponent(this, "delete");
        delete_com.setLocation(50,30);
        getNode_container().add(delete_com);
		ToolComponent list_com = new ToolComponent(this, "list");
		list_com.setLocation(30,30);
		getNode_container().add(list_com);
		 */
	}

	public void openFromObject(Hashtable obj){
		this.selectAllNodes();
		this.setMode("delete",null);
		this.addFromObject(obj);
	}


	public NodeObserver(Container node_container,  Hashtable nodes,java.awt.Frame frame)
	{
		this.mode = new Mode();
		this.mode.setMode(null);
		//		mode_object = null;
		//		selected = null;
		//		selected_line = null;
		node_components = new Hashtable();
		edit = new TextField();
		this.mode.setConnecting_start(new Point());
		this.mode.setConnecting(false);
		setLocation(0, 0);
		this.node_container = node_container;

		if(nodes != null)
			makeComponents(nodes);
		else
			makeComponents(new Hashtable());
		setTools();
		setMode("normal", null);
		this.frame = frame;

		this.fontManager = new FontManager(this);
		this.nodepadDao = new NodepadDAO(this);

		loadNodeFiles();
	}
	public String validNodeFile(String file){
		if(!file.endsWith("json")) return null;

		file = file.substring(0,file.length() - 5);
		file = file.replaceAll("\\[", "").replaceAll("\\]", "");
		file = NodeUtils.removeTagString(NodeUtils.saferStringOf(file));

		if(file.length()==0) return null;

		if(file.length()==1){
			//someone write file name filter for this.....
			if(file.matches("[a-z1-9]")) return null;
			if(file.equals("_")) return null;
			if(file.equals("-")) return null;
		}

		if(file.startsWith("@")) return null;
		return file;

	}
	private void loadNodeFiles() {
		File ndfile = new File(this.getFilename());
		File dir = ndfile.getParentFile();
		String[] files =null;
		if(dir!=null){
			files = dir.list();
		}else{
			files = new File("./data").list();
		}
		for (String string : files) {
		}

		List l = Arrays.asList(files);
		Collections.sort(l, new LengthComparator());

		List<String> newList = new ArrayList<String>();
		for (Object fileObject : l) {
			String file = (String)fileObject;
			file = validNodeFile(file);

			if(file!=null){
				newList.add((String)file);
			}
		}

		//		  files = (String[])newList.toArray();
		this.nodeFiles = new String[newList.size()];
		newList.toArray(this.nodeFiles);

	}

	public NodeObserver(Container node_container,  Hashtable nodes,java.awt.Frame frame,int fontsize){
		this(node_container,nodes,frame);
		this.fontManager.setFontSize(fontsize);
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(10, 10, 200, 200);
		g.drawString(this.mode.getMode(), 100, 100);
		super.paint(g);
	}

	/**
	 * 
	 * @uml.property name="mode"
	 */
	public void setMode(String str, Object obj) {
		this.mode.setMode(str);
		if (this.mode.getMode().equals("connecting")) {
			NodeComponent nc = (NodeComponent) obj;
			this.mode.setMode_object(obj);
			this.mode.setMode("connecting");
		} else if (this.mode.getMode().equals("delete")) {
			if (this.mode.getSelected() != null) {
				for (Enumeration en = this.mode.getSelected().elements();
						en.hasMoreElements();
						((NodeComponent) en.nextElement()).removed());
				this.mode.setSelected(null);
			}
			if (this.mode.getSelected_line() != null) {
				for (Enumeration en = this.mode.getSelected_line().elements();
						en.hasMoreElements();
						((Line) en.nextElement()).removed());
				this.mode.setSelected_line(null);
				this.mode.setHardDelete(false);
			}
		} else if (str.equals("save")) {
			//			new HttpPostNodeField(this);
			//			new XmlMultiplePostClientOfNodesOfANodefield(this);
			this.nodepadDao.saveToFile();
		} else if (str.equals("normal")) {
			this.mode.setMode_object(null);
			this.mode.setConnecting(false);
		} else if (str.equals("selectingZoneEnds")) {
			for (Enumeration en = this.node_components.elements();
					en.hasMoreElements();
					) {
				NodeComponent nc = (NodeComponent) en.nextElement();
				//        		((SelectingZone)this.getMode_object());

			}
		} else if (str.equals("WikiClicked")) {

			this.nodepadDao.openFromWikiWileName(
					((WikiLinkComponent) this.mode.getMode_object())
					.getBranketContent(),this.getNodeFieldApplet().isNet());
		} else if (str.equals("list")) {
			Hashtable hash = new Hashtable();
			NodeComponent nc =
					new NodeComponent(
							this,
							(NodeInterface) new Node(Integer
									.toString(
											this.getCounter().getNext(Node.class.getName())),
											"&wikilist",
											Node.class.getName()));
			hash.put("0", nc);
			addNodeComponentHash(hash);

			nc.setLocation(
					((int) nc.getParent().getSize().getWidth() / 2) - 20,
					((int) nc.getParent().getSize().getHeight() / 2) - 80);
		}else if(str.equals("panelmoving")){
			this.mode.setMode_object(obj);
		}else if(str.equals("WikiAutoExpand")){
			WikiLinkComponent wikiLinkComponent = 			((WikiLinkComponent)this.mode.getMode_object());
			AutoExpandOneStep autoExpandOneStep = new AutoExpandOneStep(wikiLinkComponent.getBranketContent(),(NodeComponent)wikiLinkComponent.getParent(),this.getNode_components());
		}
		getNode_container().repaint();
	}

	/**
	 * 
	 * @return if yes or can, return true, if cancel,return false
	 */
	public boolean checkDirtyAndSave(){
		if(this.getDirty()){
			YesNoCancelDialog yesNoCancelDialog = new YesNoCancelDialog(this.getNode_container());
			yesNoCancelDialog.addFlatDialogListener(this);
			this.getNode_container().add(yesNoCancelDialog);

			yesNoCancelDialog.setVisible(true);
			this.getNode_container().repaint();
			return true;
		}
		return false;
	}
	public static void newWindow(){
		try {
			java.lang.Runtime.getRuntime().exec("./nodepad.exe");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void applicationEnds(WindowEvent windowevent){
		if(this.nodeFieldApplet.isNet()){

		}else{
			//			  
			//				AllNode allNode = new AllNode();
			//				Hashtable<String, Hashtable> allNodesHash = allNode.getAllNodesHash();
			//
			//			  MakeNodeFilesForExistingNodes.makeNodeFieldsFromNodesOfANodeFields(allNodesHash, this.getNodeHashFromNCHash());




			Window window = (Window)this.nodeFieldApplet.getFrame();
			window.hide();
			try	{
				window.dispose();
			}
			catch(IllegalStateException illegalstateexception) { }
			System.exit(0);
		}

	}
	public void addFromObject(Object obj){
		this.addFromObject(obj,0,0,true);

	}
	public void addFromObject(Object obj,int x, int y){
		this.addFromObject(obj,x,y,true);
	}

	public void addFromObject(Object obj,int x, int y,boolean resize){
		Hashtable hash = (Hashtable)obj;		
		if(hash == null){
			hash = new Hashtable();
		}
		Hashtable nchash = new Hashtable();

		if(hash.size()==0){
			String nodefieldname = getWikiNameFromFile(this.filename); 
			Node node = new Node("1",nodefieldname,Node.class.getName());
			node.setX(((int)(Math.random()*100)));
			node.setY(((int)(Math.random()*100)));
			hash.put("1",node);

		}
		//create NodeComponents for the nodes
		for(Enumeration en = hash.elements();en.hasMoreElements();){
			Node node = (Node)en.nextElement();
			//			node.setX(200);
			//			node.setY(100);
			//put in the nc hash of this
			nchash.put(node.getId(),new NodeComponent(this,node));
		}
		this.addNodeComponentHash(nchash, resize);

	}
	private String getWikiNameFromFile(String filename) {
		String returned = filename.replaceAll(SimpleStringConstants.FILE_POSTFIX, "");
		returned = returned.substring(filename.lastIndexOf("/")+1, returned.length());
		return returned;
	}

	public void addNodeComponentHash(Hashtable newhash){
		this.addNodeComponentHash(newhash, true);
	}

	public void addNodeComponentHash(Hashtable newhash,boolean resize){
		int lowest = Integer.MAX_VALUE;
		int highest  = 0;
		for(Enumeration en = newhash.keys();en.hasMoreElements();){
			int temp =  Integer.parseInt((String)en.nextElement());
			if(lowest > temp){
				lowest = temp;
			}
			if(highest <temp){
				highest = temp;
			}
		}
		highest++;/// space
		int offset = 0;
		if(this.getCounter().getCount(Node.class.getName()) >= lowest){
			offset = this.getCounter().getCount((Node.class.getName())) + 1;

		}
		if(this.getCounter().getCount(Node.class.getName()) <= highest + offset){
			this.getCounter().setCount(Node.class.getName(),highest + offset + 1);
		}
		System.err.println(highest + " : highest");
		System.err.println(lowest + " : lowest");

		System.err.println(Node.class.getName() + " ---- is node class name. ----------");
		System.err.println(this.getCounter().getCount(Node.class.getName()) + " --------------- debugging -----------");

		for(Enumeration en = newhash.elements();en.hasMoreElements();){
			NodeComponent nc =(NodeComponent)en.nextElement();
			nc.getNodeInterface().setId(Integer.toString(Integer.parseInt(nc.getNodeInterface().getId())+offset));

			Vector childrenvec = nc.getNodeInterface().getChildren(); 
			Vector newchildrenvec = new Vector();
			for(int i = 0;i < childrenvec.size();i++){
				newchildrenvec.add(Integer.toString(offset + Integer.parseInt((String)childrenvec.elementAt(i))));
			}
			nc.getNodeInterface().setChildren(newchildrenvec);
			Vector parentvec = nc.getNodeInterface().getParents();
			Vector newparents = new Vector();
			for(int i = 0;i < parentvec.size();i++){
				newparents.add(Integer.toString(offset + Integer.parseInt((String)parentvec.elementAt(i))));
			}
			nc.getNodeInterface().setParents(newparents);

			this.addNC(nc);
			Vector vec = nc.getNodeInterface().getChildren();
			for(int i = 0;i < vec.size() ;i++){
				nc.makeConnection((NodeComponent)newhash.get(Integer.toString(Integer.parseInt((String)vec.elementAt(i)) - offset)));
			}
			//			nc.setText(nc.getNodeInterface().getContent());
			nc.repaint();
		}
		if(resize &&!this.mode.isFaceless()){
			this.getNodeFieldApplet().resizeToProperSize();
		}
		this.getNodeFieldApplet().repaint();
		this.repaint();
	}


	public void setNodeComponentHash(Hashtable newhash){
		for(Enumeration en = this.node_components.elements();en.hasMoreElements();){
			((NodeComponent)en.nextElement()).removed();
		}
		this.addNodeComponentHash(newhash);
	}



	public void disselectAllNodes(Hashtable hash)
	{
		NodeComponent temp;
		for(Enumeration en = hash.elements(); en.hasMoreElements(); temp.disselected())
			temp = (NodeComponent)en.nextElement();

		setMode("normal", null);
	}
	public void selectAllNodes(){
		Hashtable hash = this.getNode_components();
		NodeComponent temp;
		for(Enumeration en = hash.elements(); en.hasMoreElements(); temp.selected())
			temp = (NodeComponent)en.nextElement();

		setMode("normal", null);
	}

	public NodeComponent makeNewNornalNodeAt(int x,int y){
		try {
			Node node = new Node(Integer.toString(this.getCounter().getNext(Node.class.getName())),"",Node.class.getName());
			node.setX(x);
			node.setY(y);
			NodeComponent nc = new NodeComponent(this,(NodeInterface)node);
			nc.setLocation(x,y);
			this.addNC(nc);
			return nc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public NodeComponent makeNewFieldNode(String nodefieldName){
		try {
			Node node = new Node("1","",Node.class.getName());
			node.setContent(nodefieldName);
			node.setX(0);
			node.setY(0);
			NodeComponent nc = new NodeComponent(this,(NodeInterface)node);
			nc.setLocation(0,0);
			this.addNC(nc);
			return nc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	private Mode mode;

	String[] nodeFiles = null;
	private NodepadDAO nodepadDao;
	private NodeFieldApplet nodeFieldApplet;
	private Container node_container;
	private Hashtable node_components;
	private CounterInterface counter;
	private TextField edit;
	private java.awt.Frame frame;
	String filename = SimpleStringConstants.FILE_NAME_NODE + SimpleStringConstants.FILE_POSTFIX;
	private int nodefieldid =0;

	private WindowEvent windowevent = null;
	private FontManager fontManager = null;

	public boolean getDirty(){
		for(Enumeration en = this.getNode_components().elements();en.hasMoreElements();){
			NodeComponent nc = (NodeComponent)en.nextElement();
			if(nc.isDirty()){ return true; }
		}
		return false;
	}

	public void  dialogHaveEnd(String command) {

		if(command.equals("yes")){
			this.nodepadDao.saveToFile();
			this.applicationEnds(this.windowevent);
		}else if(command.equals("no")){
			this.applicationEnds(this.windowevent);
		}else if(command.equals("calcel")){
		}
	}

}
