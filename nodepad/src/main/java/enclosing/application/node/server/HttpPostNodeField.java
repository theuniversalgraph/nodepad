package enclosing.application.node.server;

import java.io.StringReader;

import myutil.filehandler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import enclosing.application.node.NodeObserver;
import enclosing.application.node.NodepadPostMethod;

public class HttpPostNodeField {
	public HttpPostNodeField(NodeObserver observer){
		try {
			SAXReader reader = new SAXReader();
			Document staticDataDocument = reader.read("staticData.xml");
			Element staticDataElement = staticDataDocument.getRootElement();
			Element serevruriElement = staticDataElement.element("serveruri");
			String serevruri = serevruriElement.getText();
			
			String url = serevruri + "/PostNodeFieldVP.do";
			HttpClient client = new HttpClient();
			NodepadPostMethod method = new NodepadPostMethod(url);
			if(observer.getNodefieldid() != 0){
				method.addParameter(new NameValuePair("id",String.valueOf(observer.getNodefieldid())));
			}
			method.addParameter(new NameValuePair("name",getNameOfNodeFieldFromFilename(observer.getFilename())));
			method.addParameter(new NameValuePair("ajax","true"));
			method.addParameter(new NameValuePair("averagenodevalue",""));
			client.executeMethod(method);
			
			SAXReader reader2 = new SAXReader();
			String responcebody = method.getResponseBodyAsString();
			StringReader stringReader = new StringReader(responcebody);
			Document document2 = reader2.read(stringReader);
			Element responseElement = document2.getRootElement();
			String  id = responseElement.elementText("id");
			observer.setNodefieldid(Integer.parseInt(id));
			
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
