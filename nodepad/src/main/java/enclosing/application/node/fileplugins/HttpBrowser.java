/*
 * Created on 2006/01/16
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.fileplugins;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpBrowser {
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public HttpBrowser(String urlstring){
        try {
			SAXReader reader = new SAXReader();
			Document staticDataDocument = reader.read("staticData.xml");
			Element staticDataElement = staticDataDocument.getRootElement();
			String browserpath = staticDataElement.element("browserpath").getText();

//        	URL url = new URL(urlstring);
//        	try {
//        		// Lookup the javax.jnlp.BasicService object
//        		BasicService bs = (BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");
//        		// Invoke the showDocument method
//        		bs.showDocument(url);
//        	} catch(UnavailableServiceException ue) {
//        		// Service is not supported
//        		ue.printStackTrace();
//        	}
            Runtime.getRuntime().exec("\"" + browserpath+ "\" " + urlstring);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
