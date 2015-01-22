/*
 *
 */
package enclosing.application.node.test;

import java.applet.Applet;
import java.net.URL;

/**
 * @author Administrator
 *
 */
public class TestNewWindow extends Applet {
	public void init(){
		try {
			this.getAppletContext().showDocument(new URL("http://www.sfc.keio.ac.jp"),"_brank");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
