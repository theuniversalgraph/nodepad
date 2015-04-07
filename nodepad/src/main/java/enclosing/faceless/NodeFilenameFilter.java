package enclosing.faceless;

import java.io.File;
import java.io.FilenameFilter;

public class NodeFilenameFilter implements FilenameFilter{

	public boolean accept(File dir, String name){
		if(name.matches(".*\\.nd$")){
			return true;
			}
		return false;
	}
}