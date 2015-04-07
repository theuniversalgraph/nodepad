package enclosing.webapi.client;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DownloadAllToDirectory {
	public DownloadAllToDirectory(){
		try {
			SAXReader reader = new SAXReader();
			Document staticDataDocument = reader.read("staticData.xml");
			Element staticDataElement = staticDataDocument.getRootElement();
			String tumblraccount = staticDataElement.element("tumblraccount").getText();
			String  tumblraccountpassword = staticDataElement.element("tumblraccountpassword").getText();
			String  dropboxpath = staticDataElement.element("dropboxpath").getText();
			try {
				boolean hasContents= true;
				SAXReader reader2 = new SAXReader();
				int start = 0;
				while(hasContents){
					HttpClient httpClient = new HttpClient();
					PostMethod method= new PostMethod("http://toukubo.com/api/read?type=regular&num=50&start=" + 50 * start);
					start++;
//					Part[] parts = { 
//							new StringPart("email",tumblraccount),	
//							new StringPart("generator","Nodepad"),		
//							new StringPart("password",tumblraccountpassword),
//							new StringPart("type","regular")
//					};
//					method.setRequestEntity( new MultipartRequestEntity(parts, method.getParams()) );
					httpClient.executeMethod(method);
//					String string = method.getResponseBodyAsString();
//					System.err.println(string);;
					
					Document responseDocument = reader2.read(method.getResponseBodyAsStream());
					Element postsElement = responseDocument.getRootElement().element("posts");
					int i = 0;
					for (Iterator iter = postsElement.elementIterator("post"); iter.hasNext();i++) {
						Element postElement = (Element) iter.next();
						String content = postElement.elementText("regular-title")  
						+ "\r\n"
						 + postElement.elementText("regular-body");
						File file = new File(dropboxpath + "/tumblrtexts/" + postElement.attributeValue("id") + ".txt");
						PrintWriter printWriter = new PrintWriter(file);
						printWriter.write(content);
						printWriter.close();
					}
					if(i == 0){
						hasContents = false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		public static void main(String[] args) {
			DownloadAllToDirectory  downloadAllToDirectory
			 = new DownloadAllToDirectory();
		}
}
