// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   filehandler.java

package myutil;

import java.io.*;
import java.util.*;

public class filehandler
{

    public static void outputer(String filename, String write[])
    {
        try
        {
            PrintWriter DO = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            for(int i = 0; i < write.length; i++)
                DO.println(write[i]);

            DO.println("end");
            DO.close();
        }
        catch(IOException e)
        {
            System.out.println("IOError " + e.getMessage());
        }
    }

    public static void outputer(String filename, String written)
    {
        try
        {
            if(written != null)
            {
                File file = new File(filename);
                PrintWriter DO = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
                DO.println(written);
                DO.close();
            }
        }
        catch(IOException e)
        {
            System.out.println("IOwritingfileError ");
            e.printStackTrace();
        }
    }

    public static void outputer(String filename, Vector written)
    {
        String writtenstr[] = new String[written.size()];
        written.copyInto(writtenstr);
        outputer(filename, writtenstr);
    }

    public static void outputer(String filename, Collection written)
    {
        String writtenstr[] = new String[written.size()];
        written.toArray(writtenstr);
        outputer(filename, writtenstr);
    }

    public static Object[] objectsInputer(String filename)
    {
        Object returned_objects[] = null;
        ObjectInputStream in = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(filename));
            Vector temp = new Vector();
            for(Object tempof = in.readObject(); tempof != null; tempof = in.readObject())
                temp.addElement(tempof);

            returned_objects = new Object[temp.size()];
            temp.copyInto(returned_objects);
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return returned_objects;
    }

    public static String[] inputer7(String filename)
    {
        Vector tostring = new Vector();
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(filename));
            for(String line = DI.readLine(); !"end".equals(line); line = DI.readLine())
                tostring.addElement(line);

            DI.close();
        }
        catch(IOException e)
        {
            System.out.println("IOinError " + e.getMessage());
            e.printStackTrace();
        }
        String returned[] = new String[tostring.size()];
        tostring.copyInto(returned);
        return returned;
    }

    public static String inputer3(String filename)
    {
        StringBuffer buf = new StringBuffer();
        try
        {
            DataInputStream DI = new DataInputStream(new FileInputStream(filename));
            for(String line = DI.readLine(); line != null; line = DI.readLine())
                buf.append(line);

            DI.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        String returned = buf.toString();
        return returned;
    }

    public filehandler()
    {
    }

    public static void copy2(String original, String duplicated)
    {
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(original));
            PrintWriter DO = new PrintWriter(new BufferedWriter(new FileWriter(duplicated)));
            do
                DO.println(DI.readLine());
            while(true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }
    }

    public static Object objectInputer(String filename)
    {
    	System.err.println(filename + " ---------- is filename");
        Object returned_object = null;
        ObjectInputStream in = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(filename));
            returned_object = in.readObject();
            in.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return returned_object;
    }

    public static void objectsOutputer(String filename, Object object[])
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            for(int i = 0; i < object.length; i++)
                out.writeObject(object[i]);

            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void objectConcater(String filename, Object object)
    {
        Object objects[] = objectsInputer(filename);
        Object objects2[] = new Object[objects.length];
        for(int i = 0; i < objects.length; i++)
            objects2[i] = objects[i];

        objects2[objects.length - 1] = object;
        objectsOutputer(filename, objects2);
    }

    public static String inputer4(String filename)
    {
        StringBuffer buf = new StringBuffer();
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(filename));
            for(String line = DI.readLine(); line != null; line = DI.readLine())
                buf.append(line);

            DI.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static Hashtable inputer8(String filename)
    {
        Hashtable returned = new Hashtable();
        String temp[] = inputer6(filename);
        for(int i = 0; i < temp.length; i++)
        {
            StringTokenizer st = new StringTokenizer(temp[i]);
            String temptemp[] = new String[st.countTokens()];
            for(int j = 0; j < temptemp.length; j++)
                temptemp[j] = st.nextToken();

            if(temptemp.length > 2)
            {
                for(int j = 2; j < temptemp.length; j++)
                    temptemp[1] = temptemp[1] + temptemp[j];

            }
            returned.put(temptemp[0], temptemp[1]);
        }

        return returned;
    }

    public static Hashtable inputer10(String filename)
    {
        Hashtable returned = new Hashtable();
        String temp[] = inputer6(filename);
        for(int i = 0; i < temp.length; i++)
        {
            StringTokenizer st = new StringTokenizer(temp[i]);
            String temptemp[] = new String[st.countTokens()];
            for(int j = 0; j < temptemp.length; j++)
                temptemp[j] = st.nextToken();

            returned.put(temptemp[0], temptemp);
        }

        return returned;
    }

    public static String[] inputer6(String filename)
    {
        Vector tostring = new Vector();
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(filename));
            for(String line = DI.readLine(); line != null; line = DI.readLine())
                tostring.addElement(line);

            DI.close();
        }
        catch(IOException e)
        {
            System.out.println("IOinError " + e.getMessage());
        }
        String returned[] = new String[tostring.size()];
        tostring.copyInto(returned);
        return returned;
    }

    public static void copy(String original, String duplicated)
    {
        outputer(duplicated, inputer4(original));
    }

    public static String[] inputer2(String filename)
    {
        Vector tostring = new Vector();
        String returned[] = null;
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(filename));
            for(String line = DI.readLine(); !"end".equals(line); line = DI.readLine())
                tostring.addElement(line);

            DI.close();
            returned = new String[tostring.size()];
            tostring.copyInto(returned);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return returned;
    }

    public static Vector inputer(String filename)
    {
        Vector tostring = new Vector();
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(filename));
            for(String line = DI.readLine(); !"end".equals(line); line = DI.readLine())
                tostring.addElement(line);

            DI.close();
        }
        catch(IOException e)
        {
            System.out.println("IOinError " + e.getMessage());
        }
        return tostring;
    }

    public static void objectOutputer(String filename, Object object)
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(object);
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void concater(String filename, String addedstr)
    {
        Vector temp = inputer(filename);
        temp.addElement(addedstr);
        String written[] = new String[temp.size()];
        temp.copyInto(written);
        outputer(filename, written);
    }

    public static Vector inputer9(String filename)
    {
        Vector returned = new Vector();
        String temp[] = inputer6(filename);
        for(int i = 0; i < temp.length; i++)
        {
            StringTokenizer st = new StringTokenizer(temp[i]);
            String temptemp[] = new String[st.countTokens()];
            for(int j = 0; j < temptemp.length; j++)
                temptemp[j] = st.nextToken();

            returned.addElement(temptemp);
        }

        return returned;
    }

    public static String inputer5(String filename)
    {
        StringBuffer buf = new StringBuffer();
        try
        {
            BufferedReader DI = new BufferedReader(new FileReader(filename));
            for(String line = DI.readLine(); line != null; line = DI.readLine())
                buf.append(line + "\r\n");

            DI.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
