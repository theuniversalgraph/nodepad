// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   testapplet.java

package enclosing.application.node;

import java.applet.Applet;
import java.awt.*;

import enclosing.model.Node;

// Referenced classes of package enclosing.application.node:
//            NodeComponent, Node

public class testapplet extends Applet
{

    public void paint(Graphics g)
    {
        super.paint(g);
    }

    public testapplet()
    {
        nc = null;
    }

    public void init()
    {
        setBackground(Color.white);
        setForeground(Color.black);
        setSize(500, 500);
        nc = new NodeComponent(null, new Node("1", "test", "node"));
        add(nc);
    }

	/**
	 * 
	 * @uml.property name="nc"
	 * @uml.associationEnd 
	 * @uml.property name="nc" multiplicity="(0 1)"
	 */
	NodeComponent nc;

}
