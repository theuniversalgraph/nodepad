// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProcessDecodeHttpArgs.java

package enclosing.util;

import java.util.Hashtable;
import java.util.StringTokenizer;

public class ProcessDecodeHttpArgs
{

    public ProcessDecodeHttpArgs()
    {
    }

    public static Hashtable process(String str)
    {
        StringTokenizer st = new StringTokenizer(str, "&");
        Hashtable hash = new Hashtable();
        try
        {
            StringTokenizer st2;
            for(; st.hasMoreElements(); hash.put(st2.nextToken(), st2.nextToken()))
                st2 = new StringTokenizer(st.nextToken(), "=");

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Hashtable hashtable = null;
            return hashtable;
        }
        return hash;
    }
}
