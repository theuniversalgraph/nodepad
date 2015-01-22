package enclosing.application.node;

import java.io.File;
import java.util.Vector;

public class GetFacelessNodeField {
	public static NodeFieldApplet initApplet() {
		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		applet.observer = new NodeObserver(applet,null,null);
		applet.observer.setCounter(new TestCounter());
		applet.observer.setNodeFieldApplet(applet);
		applet.observer.setFaceless(true);;
		
		if(applet.observer==null){
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
