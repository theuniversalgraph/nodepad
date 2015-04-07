// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeObserver.java

package enclosing.application.node;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import myutil.CounterInterface;
import myutil.filehandler;
import enclosing.application.node.server.SaveNodeFileToServer;
import enclosing.application.node.wiki.WikiLinkComponent;
import enclosing.awt.FlatDialogListener;
import enclosing.awt.YesNoCancelDialog;
import enclosing.model.Node;

// Referenced classes of package enclosing.application.node:
//            NodeInterface, NodeComponent, NewNodeContent, Line, 
//            NewNodeComponent

public class NodeObserver extends Panel implements FlatDialogListener
{

	String[] nodeFiles = null;
	private boolean faceless;
	boolean hardDelete;

	public String getNodefieldName(){
		final File file = new File(this.getFilename());
		final String abosulutePath = file.getAbsolutePath();
		final String withoutPath = abosulutePath.substring(abosulutePath.lastIndexOf("/")+1);
		String returened = withoutPath.replaceAll("\\.nd", "");
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
			for(int i = 0; i < nc.getNodeinterface().getChildren().size(); i++)
			{
				NodeComponent temp = (NodeComponent)node_components.get((String)nc.getNodeinterface().getChildren().elementAt(i));
				nc.getChildren().put(temp.getNodeinterface().getId(), temp);
				temp.getParents().put(nc.getNodeinterface().getId(), nc);
			}
		}
	}

	/**
	 * 
	 * @uml.property name="edit"
	 */
	 public TextField getEdit() {
		return edit;
	}


	/**
	 * 
	 * @uml.property name="selected"
	 */
	 public void setSelected(Hashtable hash) {
		 selected = hash;
	 }

	 /**
	  * 
	  * @uml.property name="selected"
	  */
	 public Hashtable getSelected() {
		 return selected;
	 }


	 public void addNC(NodeComponent nc)
	 {
		 node_components.put(nc.getNodeinterface().getId(), nc);
		 node_container.add(nc,0);
		 nc.repaint();
	 }
	 public void add(NodeComponent nc)
	 {
		 node_components.put(nc.getNodeinterface().getId(), nc);
		 node_container.add(nc);
	 }

	 public void returnAllFromEditing()
	 {
		 for(Enumeration en = getNode_components().elements(); en.hasMoreElements();)
		 {
			 NodeComponent temp = (NodeComponent)en.nextElement();
			 if(temp.getEditing())
				 temp.returnFromEditing();
		 }

	 }

	 public static Hashtable inputNodesViaIP(InetAddress add, int port)
	 {
		 Hashtable nodes = null;
		 try
		 {
			 nodes = inputNodeObject(new ObjectInputStream((new Socket(add, port)).getInputStream()));
			 return nodes;
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return null;
	 }

	 /**
	  * 
	  * @uml.property name="selected_line"
	  */
	  public void setSelected_line(Hashtable hash) {
		 selected_line = hash;
	  }

	  /**
	   * 
	   * @uml.property name="selected_line"
	   */
	  public Hashtable getSelected_line() {
		  return selected_line;
	  }


	  public void exportToStream(OutputStream out,Hashtable saved_nodes)
	  {
		  try
		  {
			  ObjectOutputStream objout = new ObjectOutputStream(out);
			  NodeComponent nc;

			  if(saved_nodes == null){
				  saved_nodes = getNodeHashFromNCHash();
			  }
			  String temp = "test";
			  objout.writeObject(saved_nodes);
			  objout.flush();
			  objout.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }

	Hashtable getNodeHashFromNCHash() {
		Hashtable saved_nodes;
		NodeComponent nc;
		saved_nodes = new Hashtable();
		  for(Enumeration en = node_components.elements(); en.hasMoreElements();){
			  nc = (NodeComponent)en.nextElement();
			  nc.updateNodeInterface();
			  saved_nodes.put(nc.getNodeinterface().getId(),nc.getNodeinterface());
		  }
		return saved_nodes;
	}

	  /**
	   * 
	   * @uml.property name="connecting_start"
	   */
	  public void setConnecting_start(Point p) {
		  connecting_start = p;
	  }

	  /**
	   * 
	   * @uml.property name="connecting_start"
	   */
	  public Point getConnecting_start() {
		  return connecting_start;
	  }


	  /**
	   * 
	   * @uml.property name="counter"
	   */
	  public void setCounter(CounterInterface counter) {
		  this.counter = counter;
	  }

	  /**
	   * 
	   * @uml.property name="counter"
	   */
	  public CounterInterface getCounter() {
		  return counter;
	  }

	  /**
	   * 
	   * @uml.property name="connecting"
	   */
	  public void setConnecting(boolean flag) {
		  connecting = flag;
	  }

	  /**
	   * 
	   * @uml.property name="connecting"
	   */
	  public boolean getConnecting() {
		  return connecting;
	  }



	  public void saveToFile()
	  {

		  if (this.getNodeFieldApplet().isNet()) {
			  SaveNodeFileToServer fileToServer = new SaveNodeFileToServer(this);
			  fileToServer.process(this.getNodeFieldApplet().getUser(),this.getNodeFieldApplet().getFilename(),this.getNodeFieldApplet().getServerName());
		  }else{
			  try
			  {
				  FileOutputStream fo = new FileOutputStream(this.filename);
				  exportToStream(fo,null);
				  fo.close();
				  //		        				new Rectangle(
				  //		        				-100,-100,
				  ////		        				(int)this.getNode_container().getAlignmentX(),
				  ////		        				(int)this.getNode_container().getAlignmentY(),
				  //		        				this.getNode_container().getBounds().width,
				  //		        				this.getNode_container().getBounds().height));
				  if(!faceless){
					  Robot robot = new Robot();
					  BufferedImage screenShot = 
							  robot.createScreenCapture(this.getNodeFieldApplet().getFrame().getBounds());

					  File file = new File(this.filename);
					  ImageIO.write(screenShot, "PNG", new File("./screenshots/" + file.getName() + ".png"));
				  }
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
			  }
		  }
		  for(Enumeration en = this.getNode_components().elements();en.hasMoreElements();){
			  ((NodeComponent)en.nextElement()).setDirty(false);
		  }
	  }

	  public void setAllNodesNormal()
	  {
		  NodeComponent temp;
		  for(Enumeration en = getNode_components().elements(); en.hasMoreElements(); temp.disselected())
		  {
			  temp = (NodeComponent)en.nextElement();
			  if(temp.getEditing())
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

	  public NodeObserver(Container node_container,  Hashtable nodes,java.awt.Frame frame)
	  {
		  mode = null;
		  mode_object = null;
		  selected = null;
		  selected_line = null;
		  node_components = new Hashtable();
		  edit = new TextField();
		  connecting_start = new Point();
		  connecting = false;
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

		  loadNodeFiles();
	  }
	  public String validNodeFile(String file){
		  if(!file.endsWith("nd")) return null;
		  
		  file = file.substring(0,file.length() - 3);
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

	  /**
	   * 
	   * @uml.property name="mode"
	   */
	  public String getMode() {
		  return mode;
	  }



	  public void paint(Graphics g)
	  {
		  g.setColor(Color.red);
		  g.fillRect(10, 10, 200, 200);
		  g.drawString(getMode(), 100, 100);
		  super.paint(g);
	  }

	  /**
	   * 
	   * @uml.property name="mode"
	   */
	  public void setMode(String str, Object obj) {
		  mode = str;
		  if (str.equals("connecting")) {
			  NodeComponent nc = (NodeComponent) obj;
			  setMode_object(obj);
			  mode = "connecting";
		  } else if (str.equals("delete")) {
			  if (selected != null) {
				  for (Enumeration en = selected.elements();
						  en.hasMoreElements();
						  ((NodeComponent) en.nextElement()).removed());
				  selected = null;
			  }
			  if (selected_line != null) {
				  for (Enumeration en = selected_line.elements();
						  en.hasMoreElements();
						  ((Line) en.nextElement()).removed());
				  selected_line = null;
				  hardDelete = false;
			  }
		  } else if (str.equals("save")) {
			  //			new HttpPostNodeField(this);
			  //			new XmlMultiplePostClientOfNodesOfANodefield(this);
			  saveToFile();
		  } else if (str.equals("normal")) {
			  setMode_object(null);
			  connecting = false;
		  } else if (str.equals("selectingZoneEnds")) {
			  for (Enumeration en = this.node_components.elements();
					  en.hasMoreElements();
					  ) {
				  NodeComponent nc = (NodeComponent) en.nextElement();
				  //        		((SelectingZone)this.getMode_object());

			  }
		  } else if (str.equals("WikiClicked")) {

			  openFromWikiWileName(
					  ((WikiLinkComponent) this.getMode_object())
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
			  this.setMode_object(obj);
		  }else if(str.equals("WikiAutoExpand")){
			  WikiLinkComponent wikiLinkComponent = 			((WikiLinkComponent)this.getMode_object());
			  AutoExpandOneStep autoExpandOneStep = new AutoExpandOneStep(wikiLinkComponent.getBranketContent(),(NodeComponent)wikiLinkComponent.getParent(),this.getNode_components());
		  }
		  getNode_container().repaint();
	  }

	  public void openFromWikiWileName(String wikiname,boolean net){
		  wikiname = wikiname.replaceAll("\\s", "_");
		  if(net){
		  }else{
			  File ndfile = new File(this.getFilename());
			  File dir = ndfile.getParentFile();

			  if(mac){
				  try {
					  System.err.println("aaaaa");
					  Process process = Runtime.getRuntime().exec("tv.sh " + wikiname );
				  }catch(Exception exception){
					  exception.printStackTrace();
				  }

				  //            	String filename = dir.getAbsolutePath() +"/"+wikiname+".nd";
				  //        		openFromFileWithNewWindow(filename,net);

			  }else{
				  String filename = "\"" +dir.getAbsolutePath() +"/"+wikiname+".nd\"";
				  openFromFileWithNewWindow(filename,net);

			  }
		  }
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
	  public void openFromFileWithNewWindow(String filename,boolean net){
		  if(!net){
			  try {
				  System.err.println(filename);
				  if(this.isMac()){
					  System.err.println("mac");
					  java.lang.Runtime.getRuntime().exec("./nodepad.sh  "+filename  + " -m");
				  }else{
					  System.err.println("win");

					  java.lang.Runtime.getRuntime().exec("./nodepad.exe "+filename);

				  }

			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }else{
			  try {
				  String url = this.getNodeFieldApplet().getURL()+"?f="+filename+"&u="+this.getNodeFieldApplet().getUser();
				  this.getNodeFieldApplet().getAppletContext().showDocument(new URL(url),"_brank");
			  } catch (Exception e) {
				  e.printStackTrace();
			  }

		  }
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
		  this.addNodeComponentHash(nchash,resize);

	  }
	  private String getWikiNameFromFile(String filename) {
		  String returned = filename.replaceAll(".nd", "");
		  returned = returned.substring(filename.lastIndexOf("/")+1, returned.length());
		  return returned;
	  }

	  public void openFromObject(Object obj){
		  this.selectAllNodes();
		  this.setMode("delete",null);
		  this.addFromObject(obj);
	  }

	  public void openFromFile(String filename){

		  this.filename = filename;
		  File file = new File(this.filename);
		  String theFilename = file.getName();
		  if(GetFacelessNodeField.isNodeSentence(theFilename) && !filename.contains("sentences/")){
			  System.err.println("it is a sentence ");
			  File newFile = new File(file.getParentFile().getAbsolutePath()+"/sentences/"+theFilename);
			  filename = newFile.getAbsolutePath();
			  this.filename = filename;
		  }
		  openFromObject(filehandler.objectInputer(filename));
		  System.err.println(this.filename);

	  }
	  public void addNodeComponentHash(Hashtable newhash){
		  this.addNodeComponentHash(newhash,true);
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

		  for(Enumeration en = newhash.elements();en.hasMoreElements();){
			  NodeComponent nc =(NodeComponent)en.nextElement(); 
			  nc.getNodeinterface().setId(Integer.toString(Integer.parseInt(nc.getNodeinterface().getId())+offset));

			  Vector childrenvec = nc.getNodeinterface().getChildren(); 
			  Vector newchildrenvec = new Vector();
			  for(int i = 0;i < childrenvec.size();i++){
				  newchildrenvec.add(Integer.toString(offset + Integer.parseInt((String)childrenvec.elementAt(i))));
			  }
			  nc.getNodeinterface().setChildren(newchildrenvec);
			  Vector parentvec = nc.getNodeinterface().getParents();
			  Vector newparents = new Vector();
			  for(int i = 0;i < parentvec.size();i++){
				  newparents.add(Integer.toString(offset + Integer.parseInt((String)parentvec.elementAt(i))));
			  }
			  nc.getNodeinterface().setParents(newparents);

			  this.addNC(nc);
			  Vector vec = nc.getNodeinterface().getChildren();
			  for(int i = 0;i < vec.size() ;i++){
				  nc.makeConnection((NodeComponent)newhash.get(Integer.toString(Integer.parseInt((String)vec.elementAt(i)) - offset)));
			  }
			  //			nc.setText(nc.getNodeinterface().getContent());
			  nc.repaint();
		  }
		  if(resize &&!this.faceless){
			  this.getNodeFieldApplet().resizeToProperSize();
		  }
		  this.getNodeFieldApplet().repaint();
		  this.repaint();
	  }

	  public boolean isFaceless() {
		  return faceless;
	  }

	  public void setFaceless(boolean faceless) {
		  this.faceless = faceless;
	  }

	  public void setNodeComponentHash(Hashtable newhash){
		  for(Enumeration en = this.node_components.elements();en.hasMoreElements();){
			  ((NodeComponent)en.nextElement()).removed();
		  }
		  this.addNodeComponentHash(newhash);
	  }

	  /**
	   * 
	   * @uml.property name="node_components"
	   */
	  public Hashtable getNode_components() {
		  return node_components;
	  }

	  /**
	   * 
	   * @uml.property name="node_container"
	   */
	  public void setNode_container(Container con) {
		  node_container = con;
	  }

	  /**
	   * 
	   * @uml.property name="node_container"
	   */
	  public Container getNode_container() {
		  return node_container;
	  }

	  /**
	   * 
	   * @uml.property name="mode_object"
	   */
	  public void setMode_object(Object obj) {
		  mode_object = obj;
	  }

	  /**
	   * 
	   * @uml.property name="mode_object"
	   */
	  public Object getMode_object() {
		  return mode_object;
	  }



	  public static Hashtable inputNodeObject(ObjectInputStream in)
	  {
		  Hashtable nodes = null;
		  try
		  {
			  nodes = (Hashtable)in.readObject();
			  in.close();
			  return nodes;
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return null;
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

	  public void exportToSocket(String hostname, int port)
	  {
		  try
		  {
			  Socket sock = new Socket(hostname, port);
			  exportToStream(sock.getOutputStream(),null);
			  sock.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }

	  public static Hashtable inputNodesFromFile(String filename)
	  {
		  Hashtable nodes = null;
		  try
		  {
			  nodes = inputNodeObject(new ObjectInputStream(new FileInputStream(filename)));
			  return nodes;
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return null;
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


	  /**
	   * 
	   * @uml.property name="selectingZone"
	   * @uml.associationEnd 
	   * @uml.property name="selectingZone" multiplicity="(0 1)"
	   */
	  private SelectingZone selectingZone;

	  /**
	   * 
	   * @uml.property name="mode"
	   */
	  private String mode;

	  /**
	   * 
	   * @uml.property name="mode_object"
	   */
	  private Object mode_object;

	  /**
	   * 
	   * @uml.property name="node_container"
	   */
	  private Container node_container;


	  /**
	   * 
	   * @uml.property name="selected"
	   * @uml.associationEnd 
	   * @uml.property name="selected" multiplicity="(0 1)" inverse="observer:enclosing.application.node.NodeComponent"
	   * qualifier="getId:java.lang.String this:enclosing.application.node.NodeComponent"
	   */
	  private Hashtable selected;

	  /**
	   * 
	   * @uml.property name="selected_line"
	   * @uml.associationEnd 
	   * @uml.property name="selected_line" multiplicity="(0 1)" qualifier="key:java.lang.Object
	   * value:enclosing.application.node.Line"
	   */
	  private Hashtable selected_line;

	  /**
	   * 
	   * @uml.property name="node_components"
	   * @uml.associationEnd 
	   * @uml.property name="node_components" multiplicity="(0 -1)" elementType="java.lang.String"
	   * qualifier="getId:java.lang.String temp:enclosing.application.node.NodeComponent"
	   */
	  private Hashtable node_components;

	  /**
	   * 
	   * @uml.property name="counter"
	   * @uml.associationEnd 
	   * @uml.property name="counter" multiplicity="(0 1)"
	   */
	  private CounterInterface counter;

	  /**
	   * 
	   * @uml.property name="edit"
	   */
	  private TextField edit;

	  /**
	   * 
	   * @uml.property name="connecting_start"
	   */
	  private Point connecting_start;

	  /**
	   * 
	   * @uml.property name="connecting"
	   */
	  private boolean connecting;

	  private java.awt.Frame frame;
	  String filename = "node.nd";

	  /**
	   * 
	   * @uml.property name="windowevent"
	   */
	  private WindowEvent windowevent = null;



	  private FontManager fontManager = null;



	  public boolean getDirty(){
		  for(Enumeration en = this.getNode_components().elements();en.hasMoreElements();){
			  NodeComponent nc = (NodeComponent)en.nextElement();
			  if(nc.getDirty()){ return true; }
		  }
		  return false;
	  }

	  /**
	   * 
	   * @uml.property name="selectingZone"
	   */
	  public SelectingZone getSelectingZone() {
		  return selectingZone;
	  }

	  /**
	   * 
	   * @uml.property name="selectingZone"
	   */
	  public void setSelectingZone(SelectingZone zone) {
		  selectingZone = zone;
	  }


	  /* (?�?� Javadoc)
	   * @see enclosing.awt.FlatDialogListener#dialogHaveEnd(java.lang.String)
	   */
	  public void  dialogHaveEnd(String command) {

		  if(command.equals("yes")){
			  this.saveToFile();
			  this.applicationEnds(this.windowevent);
		  }else if(command.equals("no")){
			  this.applicationEnds(this.windowevent);
		  }else if(command.equals("calcel")){
		  }
	  }

	  /**
	   * @return
	   * 
	   * @uml.property name="windowevent"
	   */
	  public WindowEvent getWindowevent() {
		  return windowevent;
	  }

	  /**
	   * @param event
	   * 
	   * @uml.property name="windowevent"
	   */
	  public void setWindowevent(WindowEvent event) {
		  windowevent = event;
	  }

	  /**
	   * 
	   * @uml.property name="nodeFieldApplet"
	   */
	  private NodeFieldApplet nodeFieldApplet = null;

	  /**
	   * @return
	   * 
	   * @uml.property name="nodeFieldApplet"
	   */
	  public NodeFieldApplet getNodeFieldApplet() {
		  return nodeFieldApplet;
	  }

	  /**
	   * @param applet
	   * 
	   * @uml.property name="nodeFieldApplet"
	   */
	  public void setNodeFieldApplet(NodeFieldApplet applet) {
		  nodeFieldApplet = applet;
	  }



	  /**
	   * @return Returns the fontManager.
	   */
	  public FontManager getFontManager() {
		  return fontManager;
	  }
	  /**
	   * @param fontManager The fontManager to set.
	   */
	  public void setFontManager(FontManager fontManager) {
		  this.fontManager = fontManager;
	  }
	  public String getFilename() {
		  return filename;
	  }
	  public void setFilename(String filename) {
		  this.filename = filename;
	  }


	  private boolean mac =false;



	  public boolean isMac() {
		  return mac;
	  }

	  public void setMac(boolean mac) {
		  this.mac = mac;
	  }
	  private int nodefieldid =0;



	  public int getNodefieldid() {
		  return nodefieldid;
	  }

	  public void setNodefieldid(int nodefieldid) {
		  this.nodefieldid = nodefieldid;
	  }



}
