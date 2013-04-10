// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Counters.java

package myutil;

import java.util.Enumeration;
import java.util.Hashtable;

// Referenced classes of package myutil:
//            filehandler

public class Counters
{

    public static synchronized void makeNewCounters()
    {
        filehandler.objectOutputer("counters.obj", new Hashtable());
    }

    public Counters()
    {
    }

    public static synchronized int getNext(String countername)
    {
        Hashtable counters = (Hashtable)filehandler.objectInputer("counters.obj");
        int i = Integer.parseInt((String)counters.get(countername));
        counters.put(countername, Integer.toString(i + 1));
        filehandler.objectOutputer("counters.obj", counters);
        return i;
    }

    public static synchronized Hashtable getHash()
    {
        return (Hashtable)filehandler.objectInputer("counters.obj");
    }

    public static synchronized void main(String args[])
    {
        if(args.length < 1)
            printErr();
        else
        if(args[0].equals("-set"))
        {
            if(args.length < 3)
            {
                printErr();
            } else
            {
                System.out.println("setting the counter of " + args[1] + "to " + args[2]);
                setCount(args[1], Integer.parseInt(args[2]));
            }
        } else
        if(args[0].equals("-get"))
            System.out.println(getCount(args[1]));
        else
        if(args[0].equals("-getall"))
        {
            Hashtable counters = getHash();
            String name;
            for(Enumeration enu = counters.keys(); enu.hasMoreElements(); System.out.println(name + ":" + counters.get(name)))
                name = (String)enu.nextElement();

        } else
        if(args[0].equals("-make"))
        {
            makeNewCounters();
            System.out.println("making new counter(counters.obj) done");
        } else
        {
            printErr();
        }
    }

    public static void printErr()
    {
        System.out.println("arguments are not enough!\r\nusage: java Counters [command] countername count");
        System.out.println("command set usage : java Counters -set countername count");
        System.out.println("command get usage : java Counters -get countername");
    }

    public static synchronized int getCount(String countername)
    {
        Hashtable counters = (Hashtable)filehandler.objectInputer("counters.obj");
        return Integer.parseInt((String)counters.get(countername));
    }

    public static synchronized void setCount(String countername, int i)
    {
        Hashtable counters = (Hashtable)filehandler.objectInputer("counters.obj");
        counters.put(countername, Integer.toString(i));
        filehandler.objectOutputer("counters.obj", counters);
    }
}
