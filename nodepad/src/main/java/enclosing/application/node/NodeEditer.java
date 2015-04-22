// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeEditer.java

package enclosing.application.node;

import enclosing.application.node.wiki.LengthComparator;

import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// Referenced classes of package enclosing.application.node:
//            NodeComponent, NodeInterface

public class NodeEditer extends TextArea implements KeyListener{
	
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
    	
        

        
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 27){
        	this.setAfterspace(false);
            this.nc.returnFromEditing();
            this.nc.requestFocusInWindow();
        }else if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isAltDown()){
        	this.setAfterspace(false);
            this.nc.returnFromEditing();
            NodeComponent nodeComponent = this.nc.createNewChild();
            nodeComponent.requestFocusInWindow();
            nodeComponent.goEditing();
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
        	this.setAfterspace(true);
        }else if(this.isAfterspace()){
        	
        }else if(e.getKeyCode() == KeyEvent.VK_TAB){
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT && e.isAltDown()){
        	this.nc.returnFromEditing();
        	NodeComponent nodeComponent = this.nc.createNewFriendsRightSide();
        	if(nodeComponent!=null){
        		nodeComponent.requestFocusInWindow();
        		nodeComponent.goEditing();
        	}
        }else if(e.getKeyCode()  == KeyEvent.VK_UP&& e.isAltDown()){
        	this.nc.returnFromEditing();
        	NodeComponent nodeComponent = this.nc.createNewParent();
        	if(nodeComponent!=null){
        		nodeComponent.requestFocusInWindow();
        		nodeComponent.goEditing();
        	}
        }else{
        	this.setCurrentword(this.getCurrentword() + String.valueOf(e.getKeyChar()));
        }
        if(e.isControlDown() && e.getKeyCode() == e.VK_SPACE){
//        	int currentcaretposition = this.getCaretPosition();
//        	int wordstartindex  =this.getText().lastIndexOf(" ",this.getCaretPosition() - 2);
//        	int misticalnumber = 0;
//	      	if(wordstartindex >=0){
//	    		this.setCurrentword(this.getText().substring(this.getText().lastIndexOf(" ",this.getCaretPosition() -2),this.getCaretPosition() -2));
//	    		misticalnumber = 1;
//	    	}else{
//	    		this.setCurrentword(this.getText().substring(0,this.getCaretPosition()));
//	    	}
//	      	currentword = currentword.trim();
//	    	AutoComplete autoComplete =  new AutoComplete(this.suggested,this.getCurrentword());
//	    	if(autoComplete.getSuggestion()!=null){
//		    	String newtext = this.getText();
//		    	newtext = newtext.substring(0,newtext.length() - this.getCurrentword().length() - 1 - misticalnumber);
//		    	newtext = newtext +autoComplete.getSuggestion(); 
//	
//		    	super.setText(newtext);
//		    	this.setCaretPosition(currentcaretposition + misticalnumber);
//	    	}
      }
        

    }
    public void keyTyped(KeyEvent e) {
    	
    }
    public NodeEditer(NodeComponent nc)    {
		super(nc.getNodeInterface().getContent(),0,0,SCROLLBARS_NONE);
		int x = nc.getLocation().x;
		int y = nc.getLocation().y;
      this.nc = null;
      this.nc = nc;
		setLocation(x,y);
		this.setText(nc.getNodeInterface().getContent());

		setBackground(Color.white);
		setForeground(Color.black);
		enableEvents(KeyEvent.INPUT_METHOD_EVENT_MASK);
		enableEvents(KeyEvent.KEY_EVENT_MASK);
		addKeyListener(this);
		enableEvents(TextEvent.TEXT_EVENT_MASK);
		setFont(new Font("Dialog", 0, this.nc.getObserver().getFontManager().getSize()));
		
		
		File ndfile  = new File(this.nc.getObserver().getFilename());
		File dir = ndfile.getParentFile();
		if(dir !=null){
			this.suggested = dir.list(new FilenameFilter(){
	    		public boolean accept(File dir,String filename){
	    			return filename.endsWith(SimpleStringConstants.FILE_POSTFIX);
	    		}
	    	});
		}else{
			this.suggested = new File("./data").list(new FilenameFilter(){ 
	    		public boolean accept(File dir,String filename){
	    			return filename.endsWith(SimpleStringConstants.FILE_POSTFIX);
	    		}
	    	});
		}
		if(suggested ==null){
			suggested =  new String[]{""};
			return;
		}
    	List l = Arrays.asList(suggested);
    	Collections.sort(l, new LengthComparator());
    	suggested = (String[])l.toArray();
    }
    public void setText(String text){
		StringTokenizer st = new StringTokenizer(text, "\n\r");
		String[] lines = new String[st.countTokens()];
		int i = 0;
		int maxLength = 0;
		while (st.hasMoreTokens()) {
			lines[i] = st.nextToken();
			if(maxLength< nc.getFm().stringWidth(lines[i])){
				maxLength = nc.getFm().stringWidth(lines[i]);
			}
			i++;
		}
		if(nc.getNodeInterface().getContent().length() != 0)
			setSize(maxLength+20, 14*(i-1)+28);
		else
			setSize(80, 20);
    }

	/**
	 * 
	 * @uml.property name="nc"
	 * @uml.associationEnd 
	 * @uml.property name="nc" multiplicity="(1 1)" inverse="edit:enclosing.application.node.NodeComponent"
	 */
	private NodeComponent nc;

	/* (�� Javadoc)
	 * @see java.awt.TextComponent#processTextEvent(java.awt.event.TextEvent)
	 */
	protected void processTextEvent(TextEvent te) {
		super.processTextEvent(te);
//		te.
////		this.setText(this.getText());
	}

	private String currentword = "";



	public String getCurrentword() {
		return currentword;
	}

	public void setCurrentword(String currentword) {
		this.currentword = currentword;
	}
	private boolean afterspace = false;

	public boolean isAfterspace() {
		return afterspace;
	}
	public void setAfterspace(boolean afterspace) {
		this.afterspace = afterspace;
	}

	
	private String[] suggested = null;

	public String[] getSuggested() {
		return suggested;
	}
	public void setSuggested(String[] suggested) {
		this.suggested = suggested;
	}
	
//	protected void processKeyEvent(KeyEvent e) {
//
//	}
	protected void processInputMethodEvent(InputMethodEvent e) {
//		e.getCaret().
//		super.processInputMethodEvent(e);
//		e.gettTextext().
		
	}
	
	
}
