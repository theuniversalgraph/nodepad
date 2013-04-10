package enclosing.application.node;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;

import sun.nio.cs.ext.MacHebrew;

public class FollowAllTheUrlOfNodes {
	public FollowAllTheUrlOfNodes(Enumeration enumeration){
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			if(nodeComponent.getNodeinterface().getContent().contains("http://")){
				Pattern pattern = Pattern.compile("http://.*");
				Matcher matcher = pattern.matcher(nodeComponent.getNodeinterface().getContent());
				while (matcher.find()) {
					HttpBrowser browser = new HttpBrowser(matcher.group(0));
					
//					matcher.group(1);
				}
			}
//			HttpBrowser browser = new HttpBrowser()
		}
	}

}
