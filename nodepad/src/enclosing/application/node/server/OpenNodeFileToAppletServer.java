// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OpenNodeFileToAppletServer.java

package enclosing.application.node.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.Vector;

// Referenced classes of package enclosing.application.node.server:
//            OpenNodeFileToAppletServerThread

public class OpenNodeFileToAppletServer
{

    public OpenNodeFileToAppletServer()
    {
        OpenNodeFileToAppletServerThread = new Vector();
    }

    public static void main(String args[])
    {
        OpenNodeFileToAppletServer enclosingNodeServer = new OpenNodeFileToAppletServer();
        enclosingNodeServer.start();
    }

    public void start()
    {
        try
        {
            System.err.println("starting server :: port=20002\r\n\r\n\r\n");
            serverSocket = new ServerSocket(20002);
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
                System.err.println("server statrt accepting... \r\n            port number is 20002\r\n\r\n\r\n");
                java.net.Socket sock = serverSocket.accept();
                OpenNodeFileToAppletServerThread tempThread = new OpenNodeFileToAppletServerThread();
                tempThread.setSock(sock);
                Thread th = new Thread(tempThread);
                addOpenNodeFileToAppletServerThread(tempThread);
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

    public Vector getOpenNodeFileToAppletServerThread()
    {
        return OpenNodeFileToAppletServerThread;
    }

    public void addOpenNodeFileToAppletServerThread(OpenNodeFileToAppletServerThread OpenNodeFileToAppletServerThread)
    {
        if(!this.OpenNodeFileToAppletServerThread.contains(OpenNodeFileToAppletServerThread))
        {
            this.OpenNodeFileToAppletServerThread.addElement(OpenNodeFileToAppletServerThread);
            OpenNodeFileToAppletServerThread.setOpenNodeFileToAppletServer(this);
        }
    }

    public void removeOpenNodeFileToAppletServerThread(OpenNodeFileToAppletServerThread OpenNodeFileToAppletServerThread)
    {
        boolean removed = this.OpenNodeFileToAppletServerThread.removeElement(OpenNodeFileToAppletServerThread);
        if(removed)
            OpenNodeFileToAppletServerThread.setOpenNodeFileToAppletServer(null);
    }

    public ServerSocket serverSocket;
    public Vector OpenNodeFileToAppletServerThread;
}
