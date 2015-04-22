package enclosing.application.node.server;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.theuniversalgraph.application.nodepad.NodeComponent;
import com.theuniversalgraph.application.nodepad.NodeObserver;
import com.theuniversalgraph.application.nodepad.NodepadPostMethod;
import com.theuniversalgraph.model.NodeInterface;



public class XmlMultiplePostClientOfNodesOfANodefield {
	public XmlMultiplePostClientOfNodesOfANodefield(NodeObserver observer){
		try {
			SAXReader reader  = new SAXReader();
			Document staticDataDocument = reader.read("staticData.xml");
			Element staticDataElement = staticDataDocument.getRootElement();
			Element serveruriElement =staticDataElement.element("serveruri"); 
			String serveruri = serveruriElement.getText();
			

			Document document =DocumentHelper.createDocument();
			Element nodefieldElement = document.addElement("nodefield");
			Enumeration enumeration = observer.getNode_components().elements();
			
			while (enumeration.hasMoreElements()) {
				NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
				NodeInterface node = nodeComponent.getNodeInterface();
				
				Element nodeElement =  nodefieldElement.addElement("node");
				Element idElement =  nodeElement.addElement("id");
				idElement.addText(node.getId());
				Element contentElement =  nodeElement.addElement("content");
				contentElement.addText(node.getContent());
				Element xElement =  nodeElement.addElement("x");
				xElement.addText(String.valueOf(node.getX()));
				Element yElement =  nodeElement.addElement("y");
				yElement.addText(String.valueOf(node.getY()));
//				
//				Element childrenElement = nodeElement.addElement("children");
//				
//				for (Iterator iter = node.getChildren().iterator(); iter.hasNext();) {
//					String childstring= (String) iter.next();
//					Element childElement = childrenElement.addElement("childlink");
//					childElement.addText(childstring);
//				}
//				Element parentsElement = nodeElement.addElement("parents");
//				for (Iterator iter = node.getParents().iterator(); iter.hasNext();) {
//					String parentString= (String) iter.next();
//					Element parentElement = parentsElement.addElement("parentlink");
//					parentElement.addText(parentString);
//				}
				
			}
			
			 enumeration = observer.getNode_components().elements();
			while (enumeration.hasMoreElements()) {
				NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
				NodeInterface node = nodeComponent.getNodeInterface();
				for (Iterator iter = node.getChildren().iterator(); iter.hasNext();) {
					String childString = (String) iter.next();
					Element linkElement = nodefieldElement.addElement("link");
					Element aschildElement = linkElement.addElement("aschild");
					aschildElement.addText(childString);
					Element asparentElement = linkElement.addElement("asparent");
					asparentElement.addText(node.getId().toString());
				}
			}
			
	        OutputFormat format = OutputFormat.createPrettyPrint();
	        StringWriter writer = new StringWriter();
			XMLWriter xmlWriter  = new XMLWriter(writer,format);
			xmlWriter.write(document);
			xmlWriter.close();
			
//			saving to the server
			HttpClient client = new HttpClient();
			NodepadPostMethod method  = new NodepadPostMethod(serveruri + "/XmlMultiplePostVPOfNodesOfANodefield.do");
//			method.getParams().setContentCharset("utf-8");
		    method.setRequestHeader(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");

			method.addParameter(new NameValuePair("xmlconfent",writer.toString()));
//			method.addParameter(new NameValuePair("name",getNameOfNodeFieldFromFilename(observer.getFilename())));
			method.addParameter(new NameValuePair("id",String.valueOf(observer.getNodefieldid())));

			client.executeMethod(method);

			
			
			
//        	and saves to the local ndx file
        	if(observer.getFilename().endsWith(".json")){
        		File ndxfile = new File(observer.getFilename() + "x");
        		XMLWriter xmlWriter2 = new XMLWriter(new FileWriter(ndxfile.getAbsoluteFile()),format);
        		xmlWriter2.write(document);
        		xmlWriter2.close();
        	}else{
        		File ndxfile = new File(observer.getFilename() );
        		XMLWriter xmlWriter2 = new XMLWriter(new FileWriter(ndxfile.getAbsoluteFile()),format);
        		xmlWriter2.write(document);
        		xmlWriter2.close();
        	}
//        	File dir = ndfile.getParentFile();

		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getNameOfNodeFieldFromFilename(String filename){
		if(filename.contains("/")){
			return filename.substring(filename.lastIndexOf("/") + 1,filename.lastIndexOf("."));
		}else if(filename.contains("\\")){
			return filename.substring(filename.lastIndexOf("\\") + 1,filename.lastIndexOf("."));
		}else{
			return filename.substring(0,filename.lastIndexOf("."));
		}
	}

	
	
}
