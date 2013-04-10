// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OpenFileToAppletServer.java

package enclosing.application.node.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.Vector;

// Referenced classes of package enclosing.application.node.server:
//            OpenFileToAppletServerThread

public class OpenFileToAppletServer
{

    public OpenFileToAppletServer()
    {
        openFileToAppletServerThread = new Vector();
    }

    public static void main(String args[])
    {
        OpenFileToAppletServer enclosingNodeServer = new OpenFileToAppletServer();
        enclosingNodeServer.start();
    }

    public void start()
    {
        try
        {
            System.err.println("starting server :: port=20001\r\n\r\n\r\n");
            serverSocket = new ServerSocket(20001);
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
                System.err.println("server statrt accepting... \r\n            port number is 20001\r\n\r\n\r\n");
                java.net.Socket sock = serverSocket.accept();
                OpenFileToAppletServerThread tempThread = new OpenFileToAppletServerThread();
                tempThread.setSock(sock);
                Thread th = new Thread(tempThread);
                addOpenFileToAppletServerThread(tempThread);
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

    public Vector getOpenFileToAppletServerThread()
    {
        return openFileToAppletServerThread;
    }

    public void addOpenFileToAppletServerThread(OpenFileToAppletServerThread openFileToAppletServerThread)
    {
        if(!this.openFileToAppletServerThread.contains(openFileToAppletServerThread))
        {
            this.openFileToAppletServerThread.addElement(openFileToAppletServerThread);
            openFileToAppletServerThread.setOpenFileToAppletServer(this);
        }
    }

    public void removeOpenFileToAppletServerThread(OpenFileToAppletServerThread openFileToAppletServerThread)
    {
        boolean removed = this.openFileToAppletServerThread.removeElement(openFileToAppletServerThread);
        if(removed)
            openFileToAppletServerThread.setOpenFileToAppletServer(null);
    }

    public ServerSocket serverSocket;
    public Vector openFileToAppletServerThread;
}
