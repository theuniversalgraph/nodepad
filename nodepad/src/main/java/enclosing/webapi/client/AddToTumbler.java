package enclosing.webapi.client;

import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import enclosing.model.NodeInterface;

public class AddToTumbler {
	public AddToTumbler(NodeInterface node){

		try {
			SAXReader reader = new SAXReader();
				Document staticDataDocument = reader.read("staticData.xml");
				Element staticDataElement = staticDataDocument.getRootElement();
				String tumblraccount = staticDataElement.element("tumblraccount").getText();
				String  tumblraccountpassword = staticDataElement.element("tumblraccountpassword").getText();
				
				try {
					HttpClient httpClient = new HttpClient();

					PostMethod method= new PostMethod("http://toukubo.com/api/write");
					String body = node.getContent();
					body += "<br /><p align=\"right\">xxxxxxxxxxxxxxxxxxxx<br />Generator->nodepad<br />xxxxxxxxxxxxxxxxxxxx</p>";

					Part[] parts = { new StringPart("body", body, "utf-8"),
							new StringPart("email",tumblraccount),		
							new StringPart("generator","Nodepad"),		
					new StringPart("password",tumblraccountpassword),
					new StringPart("type","regular")
					};
					
					
					method.setRequestEntity( new MultipartRequestEntity(parts, method.getParams()) );
					
					httpClient.executeMethod(method);
					System.err.println(method.getResponseBodyAsString());
					

				} catch (Exception e) {
					e.printStackTrace();
				}

		} catch (Exception e) {
			e.printStackTrace();
		}	 




			

			
			

		
		
	}
}
