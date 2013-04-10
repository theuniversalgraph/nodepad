/*
 * Created on 2003/12/30
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package myutil;
// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainFrame.java


import java.applet.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageProducer;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import sun.applet.AppletAudioClip;

public class MainFrame extends Frame
	implements Runnable, AppletStub, AppletContext
{

	public MainFrame(Applet applet1, String as[], int i, int j)
	{
		args = null;
		barebones = true;
		label = null;
		build(applet1, as, i, j);
	}

	public MainFrame(Applet applet1, String as[])
	{
		args = null;
		barebones = true;
		label = null;
		build(applet1, as, -1, -1);
	}

	public MainFrame(Applet applet1, int i, int j)
	{
		args = null;
		barebones = true;
		label = null;
		build(applet1, null, i, j);
	}

	private void build(Applet applet1, String as[], int i, int j)
	{
		instances++;
		applet = applet1;
		args = as;
		applet1.setStub(this);
		name = applet1.getClass().getName();
//		setTitle(name);
		Properties properties = System.getProperties();
		properties.put("browser", "Acme.MainFrame");
		properties.put("browser.version", "11jul96");
		properties.put("browser.vendor", "Acme Laboratories");
		properties.put("browser.vendor.url", "http://www.acme.com/");
		if(as != null)
			parseArgs(as, properties);
		String s = getParameter("width");
		if(s != null)
			i = Integer.parseInt(s);
		String s1 = getParameter("height");
		if(s1 != null)
			j = Integer.parseInt(s1);
		if(i == -1 || j == -1)
		{
			System.err.println("Width and height must be specified.");
			return;
		}
		String s2 = getParameter("barebones");
		if(s2 != null && s2.equals("true"))
			barebones = true;
		setLayout(new BorderLayout());
		/*
		ScrollPane scrollPane = new ScrollPane();
		this.add(scrollPane);
		scrollPane.add("Center", applet1);*/
		add("Center", applet1);
		pack();
		validate();
		appletSize = applet1.getSize();
		applet1.setSize(i, j);
		setVisible(true);
		SecurityManager securitymanager = System.getSecurityManager();
		boolean flag = true;
		if(securitymanager != null)
			try
			{
				securitymanager.checkExit(0);
			}
			catch(SecurityException securityexception)
			{
				flag = false;
			}
		boolean flag1 = flag;

		(new Thread(this)).start();
	}

	private static void parseArgs(String as[], Properties properties)
	{
		for(int i = 0; i < as.length; i++)
		{
			String s = as[i];
			int j = s.indexOf(61);
			if(j == -1)
				properties.put("parameter." + s.toLowerCase(), "");
			else
				properties.put("parameter." + s.substring(0, j).toLowerCase(), s.substring(j + 1));
		}

	}

	public void run()
	{
		showStatus(name + " initializing...");
		applet.init();
		validate();
		showStatus(name + " starting...");
		applet.start();
		validate();
		showStatus(name + " running...");
	}

	public boolean isActive()
	{
		return true;
	}

	public URL getDocumentBase()
	{
		String s = System.getProperty("user.dir");
		String s1 = s.replace(File.separatorChar, '/');
		try
		{
			return new URL("file:" + s1 + "/");
		}
		catch(MalformedURLException malformedurlexception)
		{
			return null;
		}
	}

	public URL getCodeBase()
	{
		String s = System.getProperty("java.class.path");
		for(StringTokenizer stringtokenizer = new StringTokenizer(s, ":"); stringtokenizer.hasMoreElements();)
		{
			String s1 = (String)stringtokenizer.nextElement();
			String s2 = s1 + File.separatorChar + name + ".class";
			File file = new File(s2);
			if(file.exists())
			{
				String s3 = s1.replace(File.separatorChar, '/');
				try
				{
					return new URL("file:" + s3 + "/");
				}
				catch(MalformedURLException malformedurlexception)
				{
					return null;
				}
			}
		}

		return null;
	}

	public String getParameter(String s)
	{
		return System.getProperty("parameter." + s.toLowerCase());
	}

	public void appletResize(int i, int j)
	{
		Dimension dimension = getSize();
		dimension.width += i - appletSize.width;
		dimension.height += j - appletSize.height;
		setSize(dimension);
		appletSize = applet.getSize();
	}

	public AppletContext getAppletContext()
	{
		return this;
	}

	public AudioClip getAudioClip(URL url)
	{
		return new AppletAudioClip(url);
	}

	public Image getImage(URL url)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		try
		{
			ImageProducer imageproducer = (ImageProducer)url.getContent();
			return toolkit.createImage(imageproducer);
		}
		catch(IOException ioexception)
		{
			return null;
		}
	}

	public Applet getApplet(String s)
	{
		if(s.equals(name))
			return applet;
		else
			return null;
	}

	public Enumeration getApplets()
	{
		Vector vector = new Vector();
		vector.addElement(applet);
		return vector.elements();
	}

	public void showDocument(URL url)
	{
	}

	public void showDocument(URL url, String s)
	{
	}

	public void showStatus(String s)
	{
		if(label != null)
			label.setText(s);
	}

	public void setStream(String s, InputStream inputstream)
	{
		throw new RuntimeException("Not Implemented");
	}

	public InputStream getStream(String s)
	{
		throw new RuntimeException("Not Implemented");
	}

	public Iterator getStreamKeys()
	{
		throw new RuntimeException("Not Implemented");
	}

	private String args[];
	private static int instances = 0;
	private String name;
	private boolean barebones;
	private Applet applet;
	private Label label;
	private Dimension appletSize;
	private static final String PARAM_PROP_PREFIX = "parameter.";




}
