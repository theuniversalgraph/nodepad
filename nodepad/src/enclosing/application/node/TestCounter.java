// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestCounter.java

package enclosing.application.node;

import myutil.CounterInterface;

public class TestCounter
    implements CounterInterface
{

    public TestCounter()
    {
        nodecount = 0;
        connectioncount = 0;
        schecount  =0;
    }

    public int getNext(String countername)
    {
        if(countername.equals("enclosing.application.node.Node"))
        {
            nodecount++;
            return nodecount;
        } else if(countername.equals("enclosing.scheduler.Schedule")){
        	schecount++;
        	return schecount;
        }else{
            return 0;
        }
    }

    public int getCount(String countername)
    {
        if(countername.equals("enclosing.application.node.Node"))
            return nodecount;
        else if(countername.equals("enclosing.scheduler.Schedule")){
        	return this.schecount;
        }
        
            return 0;
    }

    public void setCount(String countername, int i)
    {
        if(countername.equals("enclosing.application.node.Node"))
            nodecount = i;
        else if(countername.equals("enclosing.scheduler.Schedule"))
        		schecount = i;
    }
    

    int nodecount;
    int connectioncount;
    int schecount;
    
}
