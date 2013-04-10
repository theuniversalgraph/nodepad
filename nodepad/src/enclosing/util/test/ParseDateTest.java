// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParseDateTest.java

package enclosing.util.test;

import enclosing.util.ProcessParseDateString;
import java.io.PrintStream;
import java.util.Date;

public class ParseDateTest
{

    public ParseDateTest()
    {
    }

    public static void process()
    {
        String temp = "+50.5";
        Date date = ProcessParseDateString.process(temp);
        System.out.println(date);
    }

    public static void main(String args[])
    {
        process();
    }
}
