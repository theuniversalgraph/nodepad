// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeOutlet.java

package com.theuniversalgraph.application.nodepad;

import java.awt.*;
import java.awt.event.MouseEvent;

// Referenced classes of package enclosing.application.node:
//            NodeComponent, NodeObserver

public class NodeOutlet extends Component
{

    public NodeOutlet(NodeComponent nc)
    {
        entered = false;
        this.nc = null;
        this.nc = nc;
        enableEvents(16L);
        setLocation(0, 13);
        setSize(20, 10);
    }

    public void paint(Graphics g)
    {
        g.setColor(this.getNc().getObserver().getNodeFieldApplet().getSkin().getOutletnormal());
        if((this.getNc().getChildren() == null || this.getNc().getChildren().size() ==0) && !this.getNc().getNodeInterface().getContent().startsWith("#w") && !this.getNc().getNodeInterface().getContent().startsWith("#cycle")){
        	g.setColor(new Color(250,70,70,140));
        }else if(this.getNc().getNodeInterface().getContent().startsWith("#w")){
        	g.setColor(this.getNc().getObserver().getNodeFieldApplet().getSkin().getOutlettransparent());
        }
        if(entered){
            g.fillRect(0, 0, 35, 35);
            System.err.println("entering....");
        }        else{
//            g.fillRect(0, 0, 10, 3);
        }
    }

    public void processMouseEvent(MouseEvent me)
    {
        switch(me.getID())
        {
        case 504: 
            entered = true;
            repaint();
            break;

        case 505: 
            entered = false;
            repaint();
            break;

        case 500: 
            startConnecting();
            break;
        }
    }

    public boolean contains(int x, int y)
    {
        return x > -8 && y > -4 && x < getSize().width+8 && y < getSize().height+10;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

	/**
	 * 
	 * @uml.property name="entered"
	 */
	public boolean getEntered() {
		return entered;
	}

	/**
	 * 
	 * @uml.property name="entered"
	 */
	public void setEntered(boolean flag) {
		entered = flag;
	}


    public Dimension getMinimumSize()
    {
        return new Dimension(20, 10);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(20, 10);
    }

	/**
	 * 
	 * @uml.property name="nc"
	 */
	public void setNc(NodeComponent nc) {
		this.nc = nc;
	}

	/**
	 * 
	 * @uml.property name="nc"
	 */
	public NodeComponent getNc() {
		return nc;
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


    public void startConnecting()
    {
        connecting = true;
        if(nc.getObserver() != null)
            nc.getObserver().setMode("connecting", nc);
    }

    private boolean entered;
    private boolean connecting;

	/**
	 * 
	 * @uml.property name="nc"
	 * @uml.associationEnd 
	 * @uml.property name="nc" multiplicity="(1 1)" inverse="outlet:enclosing.application.node.NodeComponent"
	 */
	private NodeComponent nc;

}
