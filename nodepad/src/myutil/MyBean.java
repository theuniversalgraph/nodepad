// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MyBean.java

package myutil;

import java.io.Serializable;

public abstract class MyBean
    implements Serializable
{

    public MyBean(String key)
    {
        this.key = key;
    }

    public MyBean()
    {
    }

    public abstract String toStringForInfo();

	/**
	 * 
	 * @uml.property name="key"
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * @uml.property name="key"
	 */
	public void setKey(String str) {
		key = str;
	}

    public abstract void setEnabled(boolean flag);

    public abstract boolean getEnabled();

    private String key;
}
