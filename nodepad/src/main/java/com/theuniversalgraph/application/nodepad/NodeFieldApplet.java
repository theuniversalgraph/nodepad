// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeFieldApplet.java

package com.theuniversalgraph.application.nodepad;

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
import java.util.Collections;
import java.util.Enumeration;

import lombok.Data;
import lombok.EqualsAndHashCode;
import myutil.MainFrame;

import com.theuniversalgraph.application.nodepad.event.NodeFieldKeyEventHandler;

import enclosing.application.node.skin.NodepadSkin;
import enclosing.application.node.skin.PrintingSkin;
import enclosing.model.TagHash;

@Data
@EqualsAndHashCode(callSuper=false)
public class NodeFieldApplet extends Applet implements KeyListener,ComponentListener
{
	public int hashCode(){
		return 2;
	}
    Image imgOffscreen;
    Graphics gOffscreen;
    Dimension d;

    NodeObserver observer;
    private Point connecting_end;
    private boolean net = true;
	private Frame frame = null;
	private NodeFieldKeyEventHandler nodeFieldKeyEventHandler;
	private NodepadSkin skin = new PrintingSkin();
	
	private boolean displayAux = true;
	private boolean idshow;
	private boolean taggedHoist = false;
	private String hoisttag = "";
	private boolean seekingNext = false;
	private boolean currentBarExists;
	private int yOfCurrentBar = 0;
	 boolean havingLeft,havingRight,havingUP,havingDown = false;

	
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
            if(this.getObserver().getMode().isMac()){
                g.drawString(observer.getMode().getMode() + " @mac", 20, 20);
            }else{
                g.drawString(observer.getMode().getMode(), 20, 20);
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


    public void init()
    {
    	this.nodeFieldKeyEventHandler = new NodeFieldKeyEventHandler(this);
    	
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
    }
    

	public void keyPressed(KeyEvent ke) {
		this.nodeFieldKeyEventHandler.process(ke);
	}
	
	public void tagging(KeyEvent ke) {
		if(TagHash.getInstance().getTag(ke.getKeyCode()) ==null )
			return;
		Collections.list(this.getObserver().getMode().getSelected().elements()).forEach(n -> n.tagging(ke));
	}

	public void keyReleased(KeyEvent ke) {
	}
	public void keyTyped(KeyEvent ke){
	}	

	
	public void resizeToProperSize(){
		int width = 300;
		int height = 300;
		
		for (Enumeration en = this.observer.getNode_components().elements(); en.hasMoreElements();) {
			NodeComponent element = (NodeComponent) en.nextElement();
			element.updateNodeInterface();
			if(element.getNodeInterface().getX()+element.getPreferredSize().width > width){
				width = (element.getNodeInterface().getX()+element.getPreferredSize().width);
			}
			if(element.getNodeInterface().getY()+element.getPreferredSize().height > height){
				height = (element.getNodeInterface().getY()+ element.getPreferredSize().height);
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
	 
    public void componentHidden(ComponentEvent e) {

    }
    public void componentMoved(ComponentEvent e) {

    }
    public void componentResized(ComponentEvent e) {
        this.setSize(e.getComponent().getSize());
        this.updateImageBuffer();
    }
    public void componentShown(ComponentEvent e) {
    }
	
}
