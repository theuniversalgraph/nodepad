// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeInlet.java

package enclosing.application.node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

// Referenced classes of package enclosing.application.node:
//            NodeComponent, NodeObserver

public class NodeInlet extends Component
{

    public NodeInlet(NodeComponent nc)
    {
        this.nc = null;
        this.nc = nc;
        enableEvents(16L);
        setLocation(0, 0);
        setSize(20, 5);
    }

    public void paint(Graphics g)
    {
        g.setColor(this.getNc().getObserver().getNodeFieldApplet().getSkin().getInletnormal());
        if(this.getNc().getNodeInterface().getContent().startsWith("#w")){
        	g.setColor(this.getNc().getObserver().getNodeFieldApplet().getSkin().getInlettransparent());
        }
        if(big){
            g.fillRect(0, 0, 20, 5);
        }        else{
//            g.fillRect(0, 0, 10, 3);
        }
    }

    public void processMouseEvent(MouseEvent me)
    {
        if(nc.getObserver() != null && nc.getObserver().getMode().equals("connecting"))
            switch(me.getID())
            {
            case 504: 
                setBig(true);
                repaint();
                break;

            case 500: 
                endConnection();
                repaint();
                break;

            case 505: 
                setBig(false);
                repaint();
                break;
            }
    }

	/**
	 * 
	 * @uml.property name="big"
	 */
	public void setBig(boolean flag) {
		big = flag;
	}

	/**
	 * 
	 * @uml.property name="big"
	 */
	public boolean getBig() {
		return big;
	}


    public boolean contains(int x, int y)
    {
        return x > -4 && y > -10 && x < getSize().width && y < getSize().height+15;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void makeLines(Hashtable hashtable)
    {
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


    public void endConnection()
    {
        ((NodeComponent)nc.getObserver().getMode().getMode_object()).makeConnection(nc);
        setBig(false);
        nc.getObserver().setMode("normal", null);
    }

    private boolean big;

	/**
	 * 
	 * @uml.property name="nc"
	 * @uml.associationEnd 
	 * @uml.property name="nc" multiplicity="(1 1)" inverse="inlet:enclosing.application.node.NodeComponent"
	 */
	private NodeComponent nc;

}
