package enclosing.faceless;

import java.io.File;
import java.util.Vector;

import com.theuniversalgraph.application.nodepad.NodeFieldApplet;
import com.theuniversalgraph.application.nodepad.NodeObserver;
import com.theuniversalgraph.application.nodepad.TestCounter;

import enclosing.util.NodeUtils;

public class GetFacelessNodeField {
	public static NodeFieldApplet initApplet() {
		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		applet.setObserver(new NodeObserver(applet,null,null));
		applet.getObserver().setCounter(new TestCounter());
		applet.getObserver().setNodeFieldApplet(applet);
		applet.getObserver().getMode().setFaceless(true);;
		
		if(applet.getObserver()==null){
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return applet;
	}

	public static File getBasedirFile(String foundNodeContent) {
		String basedir = "./data/";
		if(isNodeSentence(foundNodeContent)){
			basedir += "sentences/";
		}
		File basedirFile = new File(basedir);
		return basedirFile;
	}
	public static boolean isNodeSentence(String content) {
		if(getWords(content).length<3)
			return false;
		if(content.length()>20){
			return true;
		}
		return true;
	}


	// there is a case we use underbar...
	public static String[] getWords(String content) {
		Vector<String> vector = new Vector<String>();
		final String saferString = NodeUtils.saferStringOf(content);
		String[] words = saferString.split("_");
		System.err.println(words.length + " ----------- is the length of the words!");
		return words;
	}
}
