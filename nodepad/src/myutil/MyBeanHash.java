// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyBeanHash.java

package myutil;

import java.io.Serializable;
import java.util.*;

// Referenced classes of package myutil:
//            MyBean, filehandler, MyBeanDetecter

public class MyBeanHash extends Hashtable
    implements Serializable
{

    public void remove(MyBean bean)
    {
        remove(bean.getKey());
    }

    public synchronized void setToFile(String filename)
    {
        filehandler.objectOutputer(filename, this);
    }

    public void setToFile()
    {
        setToFile(filename);
    }

    public void put(MyBean mybean)
    {
        super.put(mybean.getKey(), mybean);
    }

    public MyBean get(String key)
    {
        return (MyBean)super.get(key);
    }

    public MyBeanHash()
    {
    }

	/**
	 * 
	 * @uml.property name="filename"
	 */
	public void setFilename(String str) {
		filename = str;
	}

    public MyBean[] detect(Vector detectersvec)
    {
        MyBeanDetecter detecters[] = new MyBeanDetecter[detectersvec.size()];
        detectersvec.copyInto(detecters);
        Vector beansvec = new Vector();
        for(Enumeration en = elements(); en.hasMoreElements();)
        {
            MyBean tempbean = (MyBean)en.nextElement();
            boolean flag = true;
            for(int i = 0; i < detecters.length; i++)
                if(!detecters[i].detect(tempbean))
                    flag = false;

            if(flag)
                beansvec.addElement(tempbean);
        }

        MyBean beans[] = new MyBean[beansvec.size()];
        beansvec.copyInto(beans);
        return beans;
    }

    public static void main(String args[])
    {
        if(args.length < 2)
            System.out.println("usage: -make filename (make a new MyBeanHashObject that contains nothing");
        else
        if(args[0].equals("-make"))
        {
            MyBeanHash m = new MyBeanHash();
            m.setFilename(args[1]);
            m.setToFile(args[1]);
        }
    }

    public MyBean detectOne(Vector detectersvec)
    {
        MyBeanDetecter detecters[] = new MyBeanDetecter[detectersvec.size()];
        detectersvec.copyInto(detecters);
        for(Enumeration en = elements(); en.hasMoreElements();)
        {
            MyBean tempbean = (MyBean)en.nextElement();
            boolean flag = true;
            for(int i = 0; i < detecters.length; i++)
                if(!detecters[i].detect(tempbean))
                    flag = false;

            if(flag)
                return tempbean;
        }

        return null;
    }

    public MyBean[] getMyBeanArr()
    {
        MyBean mybeans[] = new MyBean[size()];
        int i = 0;
        for(Enumeration en = elements(); en.hasMoreElements();)
        {
            mybeans[i] = (MyBean)en.nextElement();
            i++;
        }

        return mybeans;
    }

    public MyBean[] getEnabledMyBeanArr()
    {
        int i = 0;
        Vector vec = new Vector();
        for(Enumeration en = elements(); en.hasMoreElements();)
        {
            MyBean temp = (MyBean)en.nextElement();
            if(temp.getEnabled())
                vec.addElement(temp);
        }

        MyBean mybeans[] = new MyBean[vec.size()];
        vec.copyInto(mybeans);
        return mybeans;
    }

    public void remove(String key)
    {
        super.remove(key);
    }

    public String getFileName()
    {
        return filename;
    }

    private String filename;
}
