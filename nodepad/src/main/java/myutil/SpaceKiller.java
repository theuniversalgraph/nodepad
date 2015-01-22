// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpaceKiller.java

package myutil;

import java.util.StringTokenizer;

public class SpaceKiller
{

    public static String kill(String str)
    {
        String returned = new String("");
        for(StringTokenizer st = new StringTokenizer(str); st.hasMoreTokens();)
            returned = returned + st.nextToken();

        return returned;
    }

    public SpaceKiller()
    {
    }
}
