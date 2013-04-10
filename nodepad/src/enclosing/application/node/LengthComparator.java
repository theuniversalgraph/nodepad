package enclosing.application.node;

import java.util.Comparator;

public class LengthComparator implements Comparator{
	
		public int compare(Object o1, Object o2) {
		    String s1 = (String) o1;
		    String s2 = (String) o2;
		    return s2.length() - s1.length();
		  }

	}


