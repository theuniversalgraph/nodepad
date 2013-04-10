// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   textparser.java

package myutil;


public class textparser
{

    public static boolean parse(String object, String text)
    {
        return text.indexOf(object) > 0;
    }

    public static boolean parse(String object, char text[])
    {
        return text.toString().indexOf(object) > 0;
    }

    public static boolean parse(String object, StringBuffer text)
    {
        return text.toString().indexOf(object) > 0;
    }

    public static boolean parse(char object[], String text)
    {
        return text.indexOf(object.toString()) > 0;
    }

    public static boolean parse(char object[], char text[])
    {
        return text.toString().indexOf(object.toString()) > 0;
    }

    public static boolean parse(char object[], StringBuffer text)
    {
        return text.toString().indexOf(object.toString()) > 0;
    }

    public static boolean parse(StringBuffer object, String text)
    {
        return text.indexOf(object.toString()) > 0;
    }

    public static boolean parse(StringBuffer object, char text[])
    {
        return text.toString().indexOf(object.toString()) > 0;
    }

    public static boolean parse(StringBuffer object, StringBuffer text)
    {
        return text.toString().indexOf(object.toString()) > 0;
    }

    public textparser()
    {
    }
}
