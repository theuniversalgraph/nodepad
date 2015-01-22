// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Line.java

package enclosing.application.node;

import java.awt.*;

// Referenced classes of package enclosing.application.node:
//            NodeComponent, NodeInterface, NodeObserver, RecycleBox

public class SelectingZone extends Component
{
    public SelectingZone(){
		setBackground(Color.white);
		setForeground(Color.black);
		setSize(getPreferredSize());
		repaint();
    }
    public void paint(Graphics g)
    {
        g.setColor(getForeground());
        g.drawRect(0, 0, getSize().width-1, getSize().height-1);
        super.paint(g);
    }



    public boolean contains(int x, int y)
    {
		return x > 0 && x < getSize().width && y > 0 && y < getSize().height;
		    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(30, 30);
    }


    public Dimension getPreferredSize()
    {
        return getSize();
    }

    private boolean reverse;

	/**
	 * 
	 * @uml.property name="parent_node"
	 * @uml.associationEnd 
	 * @uml.property name="parent_node" multiplicity="(0 1)"
	 */
	private NodeComponent parent_node;

	/**
	 * 
	 * @uml.property name="child"
	 * @uml.associationEnd 
	 * @uml.property name="child" multiplicity="(0 1)"
	 */
	private NodeComponent child;

    private int startx;
    private int starty;

	/**
	 * @return
	 * 
	 * @uml.property name="startx"
	 */
	public int getStartx() {
		return startx;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="starty"
	 */
	public int getStarty() {
		return starty;
	}

	/**
	 * @param i
	 * 
	 * @uml.property name="startx"
	 */
	public void setStartx(int i) {
		startx = i;
	}

	/**
	 * @param i
	 * 
	 * @uml.property name="starty"
	 */
	public void setStarty(int i) {
		starty = i;
	}

}
