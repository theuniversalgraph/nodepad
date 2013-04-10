package enclosing.application.node;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AddToBackpack {
	public AddToBackpack(String nodefieldname,NodeComponent nodeComponent){

		try {
			SAXReader reader = new SAXReader();
				Document staticDataDocument = reader.read("staticData.xml");
				Element staticDataElement = staticDataDocument.getRootElement();
				String backpacktoken = staticDataElement.element("backpacktoken").getText();
				
				try {
					StringBuilder bodyBuilder = new StringBuilder();
					ThreadTxt  threadTxt = new ThreadTxt(nodeComponent);
					bodyBuilder.append(threadTxt.getText());
					HttpClient httpClient = new HttpClient();
					StringBuilder builder = new StringBuilder();
					builder.append("<request>");
					builder.append("<token>");
					builder.append(backpacktoken);
					builder.append("</token>");
					builder.append("<item>");
					builder.append("<content>");
					builder.append(bodyBuilder.toString());
					builder.append("</content>");
					builder.append("</item>");
					builder.append("</request>");

					PostMethod method= new PostMethod("http://toukubo.backpackit.com/ws/page/1348304/lists/1436601/items/list");
					method.addRequestHeader(new Header("X-POST_DATA_FORMAT", "xml")); 
					
					String body = builder.toString();
//					body += "<br /><p align=\"right\">xxxxxxxxxxxxxxxxxxxx<br />Generator->nodepad<br />xxxxxxxxxxxxxxxxxxxx</p>";

					Part[] parts = { new StringPart("body", body, "utf-8")};
					
					method.setRequestEntity( new MultipartRequestEntity(parts, method.getParams()) );
					
					httpClient.executeMethod(method);
					System.err.println(method.getResponseBodyAsString());
					
					method= new PostMethod("http://toukubo.backpackit.com/ws/page/1348304/lists/1436601/items/add");
					method.addRequestHeader(new Header("X-POST_DATA_FORMAT", "xml")); 
					method.addRequestHeader(new Header("X-POST_DATA_FORMAT", "xml")); 
					
					body = builder.toString();

					Part[] parts2 = { new StringPart("body", body, "utf-8")};
					
					method.setRequestEntity( new MultipartRequestEntity(parts2, method.getParams()) );
					
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
