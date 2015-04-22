package enclosing.application.net;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.theuniversalgraph.application.nodepad.NodeComponent;

public class HttpPost {
	public HttpPost(String hostname,String uri,NodeComponent nodeComponent){
		try {
			String url = hostname + uri;
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);
			method.addParameter(new NameValuePair("content",nodeComponent.getNodeInterface().getContent()));
			client.executeMethod(method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
