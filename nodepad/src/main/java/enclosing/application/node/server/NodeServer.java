// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeServer.java

package enclosing.application.node.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import enclosing.application.node.NodeObserver;

// Referenced classes of package enclosing.application.node:
//            NodeObserver

public class NodeServer extends Thread
{

    public void servDestributing(int port, String filename)
    {
        try
        {
            sock = new ServerSocket(port);
            do
            {
                Socket incomingsock = sock.accept();
                sock = new ServerSocket(port);
                java.util.Hashtable hash = NodeObserver.inputNodesFromFile(filename);
                ObjectOutputStream out = new ObjectOutputStream(incomingsock.getOutputStream());
                out.writeObject(hash);
                out.close();
                incomingsock.close();
            } while(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public NodeServer()
    {
        sock = null;
    }

    public static void main(String args[])
    {
        if(args.length > 3)
        {
            System.out.println("too much arguements!");
            System.out.println("usage: type(recieve/distribute) port filename");
        } else
        if(args.length < 1)
        {
            System.out.println("please specify which type this server is");
            System.out.println("usage: type(recieve/distribute) port filename");
        } else
        if(args[0].equals("recieve"))
        {
            NodeServer node = new NodeServer();
            node.serveRecieving(Integer.parseInt(args[1]), args[2]);
        } else
        if(args[0].equals("distribute"))
        {
            NodeServer node = new NodeServer();
            node.servDestributing(Integer.parseInt(args[1]), args[2]);
        }
    }

    public void serveRecieving(int port, String filename)
    {
        try
        {
            sock = new ServerSocket(port);
            do
            {
                Socket incomingsock = sock.accept();
                ObjectInputStream in = null;
                Vector vec = new Vector();
                in = new ObjectInputStream(incomingsock.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("main.node"));
                out.writeObject(in.readObject());
                out.close();
                in.close();
                incomingsock.close();
            } while(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private ServerSocket sock;
}
