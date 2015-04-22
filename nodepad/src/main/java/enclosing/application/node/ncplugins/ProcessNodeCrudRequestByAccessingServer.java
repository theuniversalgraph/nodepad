package enclosing.application.node.ncplugins;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import enclosing.application.node.NodeComponent;
import enclosing.application.node.NodeObserver;

public class ProcessNodeCrudRequestByAccessingServer {
	public static  void  process(NodeObserver observer){
	    try {
            URL url = new URL("http://unudev.org/en/NodeCreates.do");
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line = reader.readLine();
            Vector buffer = new Vector();
            while(line != null){
                buffer.add(line);
                line = reader.readLine();
            }
            reader.close();
            StringTokenizer stringTokenizer = new StringTokenizer(buffer.toString(),"<br>");
            
            while(stringTokenizer.hasMoreTokens()){
            	String createCommand = stringTokenizer.nextToken();
            	System.err.println(createCommand + " is the command");
            	int startindex = createCommand.indexOf("@");
            	if(startindex>= 0){
            		String date = createCommand.substring(startindex + 1,startindex + 9);
            		String content=createCommand.substring(startindex + 10);
            		System.err.println(date + " date ");
            		System.err.println(content + " ceontent");
            		CreateNormalNodeWithDate.process(date,content,observer);
            	}
            }
            
            url = new URL("http://unudev.org/en/NodeDeletes.do");
             connection = url.openConnection();
             reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
             line = reader.readLine();
             buffer = new Vector();
            while(line != null){
                buffer.add(line);
                line = reader.readLine();
            }
            reader.close();
             stringTokenizer = new StringTokenizer(buffer.toString(),",");
            observer.disselectAllNodes(observer.getNode_components());
            		 
            while(stringTokenizer.hasMoreTokens()){
                String idstr = stringTokenizer.nextToken();
                for(Enumeration en = observer.getNode_components().elements();en.hasMoreElements();){
                	NodeComponent nodeComponent = (NodeComponent)en.nextElement();
                	if(nodeComponent.getNodeInterface().getId() .equals( idstr)){
                		nodeComponent.selected();
                		break;
                	}
                }
            }
            observer.setMode("delete",null);
            
            //access and trancate request db on the server
            
            url = new URL("http://unudev.org/en/SetAllNodeCrudRequestDone.do");
            connection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            line = reader.readLine();
            buffer = new Vector();
            while(line != null){
            	buffer.add(line);
               line = reader.readLine();
            }
           reader.close();

            System.err.println("success");
            
    } catch (Exception e) {
        e.printStackTrace();
    }
	}
	
}
