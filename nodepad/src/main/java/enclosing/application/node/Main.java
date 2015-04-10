package enclosing.application.node;
import myutil.MainFrame;
import org.apache.commons.lang.StringUtils;

import java.awt.*;

/**
 * @author Administrator
 *
 */
public class Main {
	public static void main(String[] args) {
		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		System.err.println(" -----------------starting nodepad -------------------");

		Frame frame = new MainFrame(applet, 500, 500);
		while(applet.observer==null){
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		applet.setFrame(frame);
		frame.addWindowListener(new NodepadWindowAdapter(applet.observer));
		if(PlatformUtils.isMac()){
			applet.observer.setMac(true);
		}

		if(args.length > 0){
			for (int i = 0; i < args.length; i++) {
				String string = args[i];
				string = string.trim();
				System.err.println(string + " is " + i + " th arg of " + args.length);
				if(StringUtils.isNotBlank(string)){
						
						System.err.println("trying to go...applet");
//						string = "store_terminals_to_todoist.nd";
						applet.observer.openFromFile(string);
						frame.setTitle(string);
				}
			}
		}else{
			
		    frame.setTitle("top.json");
			applet.observer.openFromFile("./data/top.json");
			System.err.println("nothing maybe");
		}
	} // end of main (method of HelloJava3D)
	
}
