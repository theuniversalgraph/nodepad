package com.theuniversalgraph.application.nodepad;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theuniversalgraph.model.Node;

import enclosing.application.node.server.SaveNodeFileToServer;
import enclosing.faceless.GetFacelessNodeField;
import enclosing.util.Configuration;

public class NodepadDAO {
	private NodeObserver nodeObserver = null;
	public NodepadDAO(NodeObserver nodeObserver){
		this.nodeObserver = nodeObserver;
	}

	  public  void saveToFile()
	  {

		  if (this.nodeObserver.getNodeFieldApplet().isNet()) {
			  SaveNodeFileToServer fileToServer = new SaveNodeFileToServer(this.nodeObserver);
//			  fileToServer.process(this.nodeObserver.getNodeFieldApplet().getUser(),this.nodeObserver.getNodeFieldApplet().getFilename(),this.nodeObserver.getNodeFieldApplet().getServerName());
		  }else{
			  try
			  {
				  FileOutputStream fo = new FileOutputStream(this.nodeObserver.filename);
				  exportToJson(fo, null);
				  fo.close();
				  //		        				new Rectangle(
				  //		        				-100,-100,
				  ////		        				(int)this.getNode_container().getAlignmentX(),
				  ////		        				(int)this.getNode_container().getAlignmentY(),
				  //		        				this.getNode_container().getBounds().width,
				  //		        				this.getNode_container().getBounds().height));
				  //TODO changed to dalse as it was throwing exceptions
				  if(false){
					  Robot robot = new Robot();
					  BufferedImage screenShot = 
							  robot.createScreenCapture(this.nodeObserver.getNodeFieldApplet().getFrame().getBounds());

					  File file = new File(this.nodeObserver.filename);
					  ImageIO.write(screenShot, "PNG", new File("./screenshots/" + file.getName() + ".png"));
				  }
			  }
			  catch(Exception e)
			  {
				  e.printStackTrace();
			  }
		  }
		  for(Enumeration en = this.nodeObserver.getNode_components().elements();en.hasMoreElements();){
			  ((NodeComponent)en.nextElement()).setDirty(false);
		  }
	  }

	public static void exportFileToJson(OutputStream out, Hashtable saved_nodes)
	{
	    try
	    {
	        Gson gson = new Gson();
	        String json = gson.toJson(saved_nodes);
	
	        OutputStreamWriter outputStreamWriterWriter =new OutputStreamWriter(out);
	        outputStreamWriterWriter.append(json);
	        outputStreamWriterWriter.flush();
	        outputStreamWriterWriter.close();
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
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

	// method to read json file
	  public static Hashtable inputNodesFromFile(String filename)
	  {
		  Hashtable nodes = new Hashtable();
	      Gson gson = new Gson();
	      try
		  {
	          FileInputStream fileInputStream = new FileInputStream(filename);
	          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			  Type type = new TypeToken<Map<String, Node>>() {}.getType();
			  Map<String, Node> fromJsonMap = gson.fromJson(bufferedReader, type);
			  for (Map.Entry<String, Node> entry : fromJsonMap.entrySet()) {
				  nodes.put(entry.getKey(), entry.getValue());
			  }
	          return nodes;
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return null;
	  }

	//method to read *.nd file
	public static Hashtable inputNodesFromFileOld(String filename)
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

	public void exportToJson(OutputStream out, Hashtable saved_nodes)
	  {
		  try
		  {
			  if(saved_nodes == null) {
				  saved_nodes = this.nodeObserver.getNodeHashFromNCHash();
			  }
	          Gson gson = new Gson();
	          String json = gson.toJson(saved_nodes);
	
	          OutputStreamWriter outputStreamWriterWriter =new OutputStreamWriter(out);
	          outputStreamWriterWriter.append(json);
	          outputStreamWriterWriter.flush();
	          outputStreamWriterWriter.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }

	public void exportToSocket(String hostname, int port)
	  {
		  try
		  {
			  Socket sock = new Socket(hostname, port);
			  exportToJson(sock.getOutputStream(), null);
			  sock.close();
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
	  }

	public void openFromFile(String filename){
	
		this.nodeObserver.filename = filename;
		  File file = new File(this.nodeObserver.filename);
		  String theFilename = file.getName();
		  if(GetFacelessNodeField.isNodeSentence(theFilename) && !filename.contains("sentences/")){
			  System.err.println("it is a sentence ");
			  File newFile = new File(file.getParentFile().getAbsolutePath()+"/sentences/"+theFilename);
			  filename = newFile.getAbsolutePath();
			  this.nodeObserver.filename = filename;
		  }
		  this.nodeObserver.openFromObject(NodepadDAO.inputNodesFromFile(filename));
		  System.err.println(this.nodeObserver.filename);
	
	  }

	public void openFromFileWithNewWindow(String filename,boolean net){
		  if(!net){
			  try {
				  System.err.println(filename);
				  if(this.nodeObserver.getMode().isMac()){
					  System.err.println("mac");
					  java.lang.Runtime.getRuntime().exec(Configuration.getInstance().getString("osxRunnable") +" "+filename  + " -m");
				  }else{
					  System.err.println("win");
	
					  java.lang.Runtime.getRuntime().exec("./nodepad.exe "+filename);
	
				  }
	
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }else{
			  try {
//				  String url = this.nodeObserver.getNodeFieldApplet().getURL()+"?f="+filename+"&u="+this.nodeObserver.getNodeFieldApplet().getUser();
//				  this.nodeObserver.getNodeFieldApplet().getAppletContext().showDocument(new URL(url),"_brank");
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
	
		  }
	  }


	public void openFromWikiWileName(String wikiname,boolean net){
		  wikiname = wikiname.replaceAll("\\s", "_");
		  if(net){
		  }else{
			  File ndfile = new File(this.nodeObserver.getFilename());
			  File dir = ndfile.getParentFile();
	
			  if(this.nodeObserver.getMode().isMac()){
				  try {
					  Process process = Runtime.getRuntime().exec("tv.sh " + wikiname );
				  }catch(Exception exception){
					  exception.printStackTrace();
				  }
			  }else{
				  String filename = "\"" +dir.getAbsolutePath() +"/"+wikiname+ SimpleStringConstants.FILE_POSTFIX;
				  openFromFileWithNewWindow(filename,net);
	
			  }
		  }
	  }

}
