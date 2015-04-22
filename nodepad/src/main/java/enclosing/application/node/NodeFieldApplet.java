// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeFieldApplet.java

package enclosing.application.node;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Enumeration;

import myutil.MainFrame;

import com.theuniversalgraph.model.NodeInterface;

import enclosing.application.node.fileplugins.OpenXlsWithTheSameNameAsNodeFile;
import enclosing.application.node.ncplugins.EnCauseNodesWithRelativeYPosision;
import enclosing.application.node.ncplugins.ProcessNodeCrudRequestByAccessingServer;
import enclosing.application.node.ncplugins.UniteNodesIntoOneNode;
import enclosing.application.node.server.OpenNodeFileFromServer;
import enclosing.application.node.skin.NodepadSkin;
import enclosing.application.node.skin.PrintingSkin;
import enclosing.application.node.suggestion.AutoExpandOneStepForNodeComponents;
import enclosing.faceless.AllNode;
import enclosing.faceless.MakeNodeFilesForExistingNodes;
import enclosing.model.TagHash;
import enclosing.webapi.client.NodesToTodoistItems;

public class NodeFieldApplet extends Applet implements KeyListener,ComponentListener
{
    Image imgOffscreen;
    Graphics gOffscreen;
    Dimension d;
    public void update(Graphics g){
        paint(g);
    }
    public void paintArea(Graphics g){
        int leftx = 1000;
        int rightx = 0;
        int topy = 1000;
        int buttony = 0;
        if(this.getObserver() !=null){
            for(Enumeration en = this.getObserver().getNode_components().elements();en.hasMoreElements();){
                NodeComponent component = (NodeComponent)en.nextElement();
                if(leftx > component.getX()){
                    leftx = component.getX();
                }
                if(rightx <component.getX()){
                    rightx = component.getX() + component.getWidth();
                }
                if(topy > component.getY()){
                    topy = component.getY();
                }
                if(buttony < component.getY()){
                    buttony = component.getY() + component.getHeight();
                }
            }
            g.setColor(new Color(128,128,128));
            g.drawRect(30,30,this.getWidth()/30,this.getHeight()/30);
            g.drawRect(30+(30+leftx)/30,30+(30+topy)/30,(rightx-leftx)/30,(buttony-topy)/30);
        }
    }
    
    public void paintMySelf(Graphics g){
    	Graphics2D g2 = (Graphics2D)g;

        g2.setPaint(new GradientPaint(
            0,0, this.getSkin().getGraduationTopColor(),0 , this.getHeight(), this.getSkin().getNodefield(), false));

//    	g.setColor(this.getSkin().getNodefield());
    	g2.fillRect(0,0,this.getWidth(),this.getHeight());
        if(observer != null && observer.getMode() != null){
        	g.setColor(Color.red);
        	g.setFont(new Font("Dialog", 0, 11));
            if(this.getObserver().isMac()){
                g.drawString(observer.getMode() + " @mac", 20, 20);
            }else{
                g.drawString(observer.getMode(), 20, 20);
            }
        }
    	if(this.isDisplayAux()){
    		this.paintAux(g);
    	}
    }
    
    public void paintAux(Graphics graphics){
    	graphics.setColor(this.getSkin().getAuxLineColor());
    		int span = 50;
    		int min = Integer.MAX_VALUE;
    		int max = 0;
    		if(this.getObserver() != null && this.getObserver().getNode_components() !=null){
        		for( Enumeration iter = this.getObserver().getNode_components().elements(); iter.hasMoreElements();) {
    				NodeComponent nodeComponent = (NodeComponent) iter.nextElement();
    				if(nodeComponent.getY() < min){
    					min = nodeComponent.getY();
    				}
    				if(nodeComponent.getY() > max){
    					max = nodeComponent.getY();
    				}
    			}
        		int i = 0;
//        		while(min + i < max){
//            		graphics.drawLine(0,min + i,this.getWidth(),min + i);
//        			i = i + span;
//        		}
//        		graphics.drawLine(0,min,100,min);
//        		graphics.drawLine(0,max,100,max);
//        		int gridspan = 10;
//        		int current = 0;
//        		for (int j = 0; (j * gridspan) < this.getWidth(); j++) {
//        			graphics.drawLine(j * gridspan,0,j * gridspan,this.getHeight());
//				}
//        		for (int j = 0; (j * gridspan) < this.getHeight(); j++) {
//        			graphics.drawLine(0 , j * gridspan ,this.getWidth(), j * gridspan   );
//				}

        		
        		
    		}
    }

    public void paint(Graphics g)
    {
        if(this.imgOffscreen !=null){
            this.paintMySelf(this.gOffscreen);
            this.paintArea(this.gOffscreen);
            g.drawImage(this.imgOffscreen,0,0,this);

        }
        super.paint(g);
//        paintComponents(g);
    }
    public void startClosing(){
		this.observer.checkDirtyAndSave();
    }

    public Component add(Component com)
    {
        super.add(com);
        repaint();
        return com;
    }

    public NodeFieldApplet()
    {
    }

    public void init()
    {
    	
		d =getSize();
		imgOffscreen = createImage(d.width,d.height);
		gOffscreen = imgOffscreen.getGraphics();


		
		this.setFocusable(true);
		this.addKeyListener(this);
		this.addComponentListener(this);

        setSize(400, 400);
        setFont(new Font("Dialog", 0, 20));

		this.observer = new NodeObserver(this,null,this.frame);
		this.setLayout(null);

        observer.setCounter(new TestCounter());
        addMouseListener(new NodeObserverMouseAdapter(observer));
        addMouseMotionListener(new NodeObserverMouseMotionAdapter(observer));
        setBackground(Color.white);
        this.observer.setNodeFieldApplet(this);
		if(net){
			this.observer.openFromObject(OpenNodeFileFromServer.process(this.getUser(),this.getFilename(),this.getServerName()));
		}else{
		}        
    }
    
    public String getServerName(){
    	return this.getParameter("s");
    }
    public String getFilename(){
    	return this.getParameter("f");
    }
    public String getUser(){
    	return this.getParameter("u");
    }
    

    public String getURL(){
    	return this.getParameter("url");
    }

	/**
	 * 
	 * @uml.property name="frame"
	 */
	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	public Frame getFrame(){
		return this.frame;
	}

	/**
	 * 
	 * @uml.property name="observer"
	 * @uml.associationEnd 
	 * @uml.property name="observer" multiplicity="(0 1)"
	 */
	NodeObserver observer;

    private Point connecting_end;
    private boolean net = true;
	/**
	 * 
	 * @uml.property name="frame"
	 */
	private Frame frame = null;

     
	/**
	 * @return
	 */
	public boolean isNet() {
		return net;
	}

	/**
	 * @param b
	 */
	public void setNet(boolean b) {
		net = b;
	}

	/**
	 * @return
	 */
	public NodeObserver getObserver() {
		return observer;
	}

	/**
	 * @param observer
	 */
	public void setObserver(NodeObserver observer) {
		this.observer = observer;
	}




	public void keyPressed(KeyEvent ke) {
		System.out.println(ke.getKeyCode());
		if(ke.isShiftDown()){
		}
		
	    if(ke.isAltDown()){
			//////////////taghoist
			if(TagHash.getInstance().getTag(ke.getKeyCode()) !=null ){
				if(this.isTaggedHoist() && TagHash.getInstance().getTag(ke.getKeyCode()).equals(this.getHoisttag())){
					for (Enumeration en= this.getObserver().getNode_components().elements();en.hasMoreElements();) {
						NodeComponent nc = (NodeComponent)en.nextElement();
						if(!nc.getNodeinterface().getContent().startsWith( this.getHoisttag())){
							nc.setHoist(false);
							nc.repaint();
						}
					}
					this.setHoisttag("");
					this.setTaggedHoist(false);
					
				}else{
					this.setHoisttag(TagHash.getInstance().getTag(ke.getKeyCode()));
					this.setTaggedHoist(true);
					for (Enumeration en= this.getObserver().getNode_components().elements();en.hasMoreElements();) {
						NodeComponent nc = (NodeComponent)en.nextElement();
						if(!nc.getNodeinterface().getContent().startsWith(this.getHoisttag())){
							nc.setHoist(true);
							nc.repaint();
						}
					}	
				}
			}				

	    	
//	        if(ke.getKeyCode() == KeyEvent.VK_P){
//	            new OpenRelatedMPP(this.getObserver());
//	        }
	    }

	    if(ke.isControlDown()){
	    	///////////////////////////////////////    	 	ctrl + shift + ?
		    if(ke.isShiftDown()){ 
		    	switch (ke.getKeyCode()) {
				case KeyEvent.VK_ENTER:
			        new UniteNodesIntoOneNode(this.getObserver().getSelected().elements(),this.getObserver());
					break;
				case KeyEvent.VK_X:
		            new OpenXlsWithTheSameNameAsNodeFile(this.getObserver());
					break;
				case 107:
					this.getSkin().morecontrast();
					this.repaint();
					break;
				case 109:
					this.getSkin().lesscontrast();
					this.repaint();
					break;
				case KeyEvent.VK_LEFT:
					new AlignNodeToLeft(this.getObserver().getSelected());
					break;
				case KeyEvent.VK_UP:
					new AlignNodeToTop(this.getObserver().getSelected());
					break;
				default:
					break;
				}
		    	

				if(ke.isAltDown()){
					/////////////////////////////////////////////// ctrl + alt
					if(ke.getKeyCode() == KeyEvent.VK_A){
						AutoExpandOneStepForNodeComponents autoExpandOneStepForNodeComponents
						 =new AutoExpandOneStepForNodeComponents(this.getObserver().getSelected().elements(),this.getObserver().getNode_components());
					}else if(ke.getKeyCode() == KeyEvent.VK_S){
						if(!this.isSeekingNext()){
							this.setSeekingNext(true);
							Enumeration enumeration = this.observer.getNode_components().elements();
							while (enumeration.hasMoreElements()) {
								NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
								if(nodeComponent.getNodeinterface().getContent().startsWith("#current")){
									this.setCurrentBarExists(true);
									this.setYOfCurrentBar(nodeComponent.getY());
									break;
								}
							}
						}else{
							this.setSeekingNext(false);
							this.setCurrentBarExists(false);
						}
					}
				}

				
				
				
				
				///////////////////////////////////////    	 	ctrl + alt + ?
		    }else if(ke.isAltDown()){
		    	if(ke.getKeyCode() == KeyEvent.VK_L){
		    	FollowAllTheUrlOfNodes followAllTheUrlOfNodes =new FollowAllTheUrlOfNodes(this.getObserver().getSelected().elements());
		    		
		    	}
		    	
		    
				///////////////////////////////////////    	 	ctrl + ?
		    }else  {
			    if(ke.getKeyCode() == KeyEvent.VK_I){
			    	MakeNodeFilesForExistingNodes.makeNodeFieldsFromNodesOfANodeFields(AllNode.getInstance().getAllNodesHash(), this.getObserver().getNodeHashFromNCHash());
//			    	if(this.isIdshow()){
//			    		this.setIdshow(false);
//			    		this.setSkin(new MoniterSkin());
//			    	}else{
//				    	this.setIdshow(true);
//				    	this.setSkin(new PrintingSkin());
//			    	}
//			    	this.repaint();
			    }
			    if(ke.getKeyCode() == KeyEvent.VK_U){
			    	ProcessNodeCrudRequestByAccessingServer.process(this.observer);
			    	this.repaint();
			    }

				if(ke.getKeyCode()==KeyEvent.VK_T){
					System.err.println(this.getFilename());
					System.err.println(this.getObserver().getNodefieldName());

					new NodesToTodoistItems(this.getObserver().getNodefieldName());
				}
				if(ke.getKeyCode()==83){
					this.observer.setMode("save",this);
				}
				if(ke.getKeyCode()== 67){
					new ClipBoardManager(this.observer).copy();
				}
				if(ke.getKeyCode()==86){
					new ClipBoardManager(this.observer).paste();
				}
				if(ke.getKeyCode()==88){
					new ClipBoardManager(this.observer).cut();
				}
				if(ke.getKeyCode()==65){
					this.observer.selectAllNodes();
	 			}
				if(ke.getKeyCode()==87){
					if(!this.observer.checkDirtyAndSave()){
						

						
						observer.applicationEnds(null);
					}				
				}
				if(ke.getKeyCode()==107 || ke.getKeyCode()==187){
					new FieldZoomManager(this.getObserver()).zoomin();
					this.observer.getFontManager().setFontSizeBigger();
				}
				if(ke.getKeyCode()==109 || ke.getKeyCode()==189){
					new FieldZoomManager(this.getObserver()).zoomout();
					this.observer.getFontManager().setFontSizeSmaller();
				}
				if(ke.getKeyCode() == ke.VK_N){
					
					// how to put dousureba in the head of nodes without a tag==not starts witttth
					
					Enumeration enumeration = this.observer.getNode_components().elements();
					while (enumeration.hasMoreElements()) {
						NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
						if(!nodeComponent.getNodeinterface().getContent().startsWith("@")
								&& !nodeComponent.getNodeinterface().getContent().startsWith("どうすれば")
								&& !nodeComponent.getNodeinterface().getContent().contains("考")
								&& !nodeComponent.getNodeinterface().getContent().contains("どうすれば")
						){
							nodeComponent.getNodeinterface().setContent("どうすれば"+ nodeComponent.getNodeinterface().getContent());
							nodeComponent.setText(nodeComponent.getNodeinterface().getContent());
						}
					}

				    
				}
				if(ke.getKeyCode() == KeyEvent.VK_L){
				    new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
				}
		    }
		}else{
		    if(ke.getKeyCode()==107){
		        new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
		    }
		}
	    
		
		if(ke.getKeyCode()==127){//delete
			if(ke.isShiftDown()){
				this.getObserver().hardDelete = true;
			}
			this.getObserver().setMode("delete",null);
		}
		
		
		if(!ke.isAltDown() && !ke.isActionKey() && !ke.isAltGraphDown() && !ke.isControlDown() && !ke.isMetaDown() && !ke.isShiftDown()){
			if(TagHash.getInstance().getTag(ke.getKeyCode()) !=null ){
				Enumeration enumeration =this.getObserver().getSelected().elements(); 
				while (enumeration.hasMoreElements()) {
					NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
					NodeInterface nodeInterface =nodeComponent.getNodeinterface();
					if(nodeInterface.getContent().startsWith(TagHash.getInstance().getTag(ke.getKeyCode()))){
						nodeInterface.setContent(nodeInterface.getContent().substring(TagHash.getInstance().getTag(ke.getKeyCode()).length() + 1));
					}else{
						nodeInterface.setContent(TagHash.getInstance().getTag(ke.getKeyCode()) + " " + nodeInterface.getContent());
					}
					nodeComponent.setText(nodeInterface.getContent());
				}
			}
		}
	}

	public void keyReleased(KeyEvent ke) {

	}
	public void keyTyped(KeyEvent ke){
//		if(ke.isAltDown()){
//	        new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
//		}
	}	
	
	public void resizeToProperSize(){
		int width = 300;
		int height = 300;
		
		for (Enumeration en = this.observer.getNode_components().elements(); en.hasMoreElements();) {
			NodeComponent element = (NodeComponent) en.nextElement();
			element.updateNodeInterface();
			if(element.getNodeinterface().getX()+element.getPreferredSize().width > width){
				width = (element.getNodeinterface().getX()+element.getPreferredSize().width);
			}
			if(element.getNodeinterface().getY()+element.getPreferredSize().height > height){
				height = (element.getNodeinterface().getY()+ element.getPreferredSize().height);
			}
		}
		if(this.net){
		}else{
			this.resize(new Dimension(width + 20,height+50));
			this.getParent().setSize(this.getSize());
			((MainFrame)this.getParent()).appletResize(width + 30,height + 50);
			this.getParent().repaint();
			this.updateImageBuffer();
		}
		
		

		this.repaint();
	}
	private void updateImageBuffer(){
		d =getSize();
		imgOffscreen = createImage(d.width,d.height);
		gOffscreen = imgOffscreen.getGraphics();
	}
	
	
	
	/**
	 * @return Returns the havingDown.
	 */
	public boolean isHavingDown() {
		return havingDown;
	}
	/**
	 * @param havingDown The havingDown to set.
	 */
	public void setHavingDown(boolean havingDown) {
		this.havingDown = havingDown;
	}
	/**
	 * @return Returns the havingLeft.
	 */
	public boolean isHavingLeft() {
		return havingLeft;
	}
	/**
	 * @param havingLeft The havingLeft to set.
	 */
	public void setHavingLeft(boolean havingLeft) {
		this.havingLeft = havingLeft;
	}
	/**
	 * @return Returns the havingRight.
	 */
	public boolean isHavingRight() {
		return havingRight;
	}
	/**
	 * @param havingRight The havingRight to set.
	 */
	public void setHavingRight(boolean havingRight) {
		this.havingRight = havingRight;
	}
	/**
	 * @return Returns the havingUP.
	 */
	public boolean isHavingUP() {
		return havingUP;
	}
	/**
	 * @param havingUP The havingUP to set.
	 */
	public void setHavingUP(boolean havingUP) {
		this.havingUP = havingUP;
	}
	 boolean havingLeft,havingRight,havingUP,havingDown = false;
	 
	 
	 
	 
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
     */
    public void componentHidden(ComponentEvent e) {

    }
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
     */
    public void componentMoved(ComponentEvent e) {

    }
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
     */
    public void componentResized(ComponentEvent e) {
        this.setSize(e.getComponent().getSize());
        this.updateImageBuffer();
    }
    /* (non-Javadoc)
     * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
     */
    public void componentShown(ComponentEvent e) {

    }
    
    
	private boolean displayAux = true;


	public boolean isDisplayAux() {
		return displayAux;
	}

	public void setDisplayAux(boolean displayAux) {
		this.displayAux = displayAux;
	}
	
	private boolean idshow;
	public boolean isIdshow() {
		return idshow;
	}
	public void setIdshow(boolean idshow) {
		this.idshow = idshow;
	}
	
	
	private NodepadSkin skin = new PrintingSkin();
	public NodepadSkin getSkin() {
		return skin;
	}
	public void setSkin(NodepadSkin skin) {
		this.skin = skin;
	}
	
	
	
	private boolean taggedHoist = false;
	public boolean isTaggedHoist() {
		return taggedHoist;
	}
	public void setTaggedHoist(boolean taggedHoist) {
		this.taggedHoist = taggedHoist;
	}
	
	  
	  private String hoisttag = "";
	public String getHoisttag() {
		return hoisttag;
	}
	public void setHoisttag(String hoisttag) {
		this.hoisttag = hoisttag;
	}
	  
	private boolean seekingNext = false;
	public boolean isSeekingNext() {
		return seekingNext;
	}
	public void setSeekingNext(boolean seekingNext) {
		this.seekingNext = seekingNext;
	}
	private boolean currentBarExists;
	private int yOfCurrentBar = 0;
	public boolean isCurrentBarExists() {
		return currentBarExists;
	}
	public void setCurrentBarExists(boolean currentBarExists) {
		this.currentBarExists = currentBarExists;
	}
	public int getYOfCurrentBar() {
		return yOfCurrentBar;
	}
	public void setYOfCurrentBar(int ofCurrentBar) {
		yOfCurrentBar = ofCurrentBar;
	}
	
	
	  
	
	
	
	
}
