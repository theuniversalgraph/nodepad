// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OpenFileFromServer.java

package enclosing.application.node.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

public class OpenFileFromServer
{

    public OpenFileFromServer()
    {
    }

    public static Vector process(String user, String filename, String serverhost)
    {
        String host;
        Socket sock;
        InputStream in;
        OutputStream out;
        Vector vector;
        host = serverhost;
        sock = null;
        in = null;
        out = null;
        BufferedWriter writer = null;
        vector = new Vector();
        try
        {
            sock = new Socket(host, 20001);
            out = sock.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "EUC-JP"));
            writer.write(user + "\r\n");
            writer.write(filename + "\r\n");
            writer.flush();
            in = sock.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "EUC-JP"));
            for(String buf = reader.readLine(); buf != null; buf = reader.readLine())
                vector.add(buf);

            reader.close();
            return  vector;
        }catch(Exception ee){
        	ee.printStackTrace();
        }finally{
        try
        {
            in.close();
            out.close();
            sock.close();
            Vector vector2 = null;
            return vector2;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
        }
    }
}
