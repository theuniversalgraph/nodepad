// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CounterInterface.java

package myutil;

import java.io.Serializable;


public interface CounterInterface extends Serializable
{

    public abstract int getNext(String s);

    public abstract int getCount(String s);

    public abstract void setCount(String s, int i);
}
