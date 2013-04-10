// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ToolComponent.java

package enclosing.application.node.tool;

import enclosing.application.node.NodeObserver;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ListComponent extends Component
{

    public void set(int x, int y)
    {
        setLocation(x, y);
        setSize(20, 20);
        repaint();
    }

    public ListComponent(NodeObserver observer, String command)
    {
        this.observer = null;
        this.observer = observer;
        setLocation(10, 10);
        setSize(20, 20);
        this.command = command;
        enableEvents(16L);
        repaint();
    }

    public void paint(Graphics g)
    {
        g.drawRect(0, 0, 19, 18);
        g.setFont(new Font("Dialog", 0, 8));
        g.drawString(command, 0, 10);
        super.paint(g);
    }

    public void processMouseEvent(MouseEvent me)
    {
        switch(me.getID())
        {
        default:
            break;

        case 500: 
            if(observer != null)
                observer.setMode(command, null);
            break;
        }
    }

    public boolean contains(int x, int y)
    {
        return x > 0 && y > 0 && x < 20 && y < 20;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

	/**
	 * 
	 * @uml.property name="command"
	 */
	public void setCommand(String str) {
		command = str;
	}

	/**
	 * 
	 * @uml.property name="command"
	 */
	public String getCommand() {
		return command;
	}


    public Dimension getMinimumSize()
    {
        return new Dimension(20, 20);
    }

    public Dimension getPreferredSize()
    {
        return getSize();
    }

	/**
	 * 
	 * @uml.property name="observer"
	 * @uml.associationEnd 
	 * @uml.property name="observer" multiplicity="(1 1)"
	 */
	private NodeObserver observer;

    private String command;
}
