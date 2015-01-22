// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnclosingNodeServer.java

package enclosing.application.node.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

// Referenced classes of package enclosing.application.node.server:
//            EnclosingNodeServerThread

public class EnclosingNodeServer
{

    public EnclosingNodeServer()
    {
        enclosingNodeServerThread = new Vector();
    }

    public static void main(String args[])
    {
        EnclosingNodeServer enclosingNodeServer = new EnclosingNodeServer();
        enclosingNodeServer.start();
    }

    public void start()
    {
        try
        {
            System.err.println("starting server :: port=20000\r\n\r\n\r\n");
            serverSocket = new ServerSocket(20000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                serverSocket.close();
            }
            catch(IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        try
        {
            do
            {
                System.err.println("server statrt accepting... \r\n            port number is 20050\r\n\r\n\r\n");
                java.net.Socket sock = serverSocket.accept();
                EnclosingNodeServerThread tempThread = new EnclosingNodeServerThread();
                tempThread.setSock(sock);
                Thread th = new Thread(tempThread);
                addEnclosingNodeServerThread(tempThread);
                th.start();
            } while(true);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public Vector getEnclosingNodeServerThreads()
    {
        return enclosingNodeServerThread;
    }

    public void addEnclosingNodeServerThread(EnclosingNodeServerThread enclosingNodeServerThread)
    {
        if(!this.enclosingNodeServerThread.contains(enclosingNodeServerThread))
        {
            this.enclosingNodeServerThread.addElement(enclosingNodeServerThread);
            enclosingNodeServerThread.setEnclosingNodeServer(this);
        }
    }

    public void removeEnclosingNodeServerThread(EnclosingNodeServerThread enclosingNodeServerThread)
    {
        boolean removed = this.enclosingNodeServerThread.removeElement(enclosingNodeServerThread);
        if(removed)
            enclosingNodeServerThread.setEnclosingNodeServer(null);
    }

    public ServerSocket serverSocket;
    public Vector enclosingNodeServerThread;
}
