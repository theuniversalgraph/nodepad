package enclosing.application.node;

import java.io.File;
import java.io.FilenameFilter;

public class GetNodeFiles {

	public File[] getFiles() {
    	File dir = new File("./data");
    	File sentenceDir = new File("./data/sentences/");
    	File[] sentences = sentenceDir.listFiles(new NodeFilenameFilter());
		File[] files = dir.listFiles(new NodeFilenameFilter()) ;
		File[] allFiles = new File[files.length+sentences.length];
		System.arraycopy(files, 0, allFiles,0, files.length);
		System.arraycopy(sentences, 0, allFiles,files.length, sentences.length);
		
		return allFiles ;
	}
}
