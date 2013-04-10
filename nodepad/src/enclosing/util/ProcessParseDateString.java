// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProcessParseDateString.java

package enclosing.util;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

public class ProcessParseDateString
{

    public ProcessParseDateString()
    {
    }

    public static Date process(String dateArg)
    {
        System.out.println("DEBUGGING MESSAGE : in ProcessParseDateString  the dateArg is ".concat(String.valueOf(String.valueOf(dateArg))));
        Calendar calendar = Calendar.getInstance();
        if(dateArg == null || dateArg.equals("") || dateArg.equals("0"))
            return calendar.getTime();
        if(!dateArg.startsWith("+"))
        {
            int year = 2000 + Integer.parseInt(dateArg.substring(0, 2));
            int month = Integer.parseInt(dateArg.substring(2, 4));
            int hour = Integer.parseInt(dateArg.substring(4, 6));
            int date = Integer.parseInt(dateArg.substring(6, 8));
            int minute = Integer.parseInt(dateArg.substring(8, 10));
            calendar.set(year, month, hour, date, minute);
        } else
        {
            System.out.println("comming here processparsedatestring");
            dateArg = dateArg.substring(1);
            if(dateArg.indexOf(".") > 0)
            {
                String tempStr = dateArg.substring(0, dateArg.indexOf("."));
                int hourInt = Integer.parseInt(tempStr);
                int minuteInt = Integer.parseInt(dateArg.substring(dateArg.indexOf(".") + 1));
                int temp = minuteInt * 60;
                temp /= 10;
                calendar.add(10, hourInt);
                calendar.add(12, temp);
            } else
            {
                calendar.add(10, Integer.parseInt(dateArg.substring(1)));
            }
        }
        System.out.println(calendar);
        return calendar.getTime();
    }
}
