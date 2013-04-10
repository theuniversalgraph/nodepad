// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OpenNodeFileFromServer.java

package enclosing.application.node.server;

import java.io.*;
import java.net.Socket;

public class OpenNodeFileFromServer
{

    public OpenNodeFileFromServer()
    {
    }

    public static Object process(String user, String filename, String serverhost)
    {
        String host;
        Socket sock;
        InputStream in;
        OutputStream out;
        host = serverhost;
        sock = null;
        in = null;
        out = null;
        BufferedWriter writer = null;
        Object obj1;
        try
        {
            sock = new Socket(host, 20002);
            out = sock.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "EUC-JP"));
            writer.write(user + "\r\n");
            writer.write(filename + "\r\n");
            writer.flush();
            in = sock.getInputStream();
            ObjectInputStream reader = new ObjectInputStream(in);
            Object obj = reader.readObject();
            reader.close();
            return obj;
        }catch(Exception e){
        	e.printStackTrace();
        }finally
        {
            try
            {
                out.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
            try
            {
                sock.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        try
        {
            in.close();
            out.close();
            sock.close();
            Object obj2 = null;
            return obj2;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }
}
