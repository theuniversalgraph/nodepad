/*
 */
package enclosing.application.node.wiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import org.apache.regexp.RE;

/**
 * @author Administrator
 */
public class TestBrancketName {

	/**
	 * 
	 * @uml.property name="re"
	 * @uml.associationEnd 
	 * @uml.property name="re" multiplicity="(1 1)"
	 */
	org.apache.regexp.RE re = null;

	public void process(){
		Object[] temps = null;
		Vector tempsvec = new Vector();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("temp.txt")));
			String line = reader.readLine();
			while(line != null){
				tempsvec.add(line);
				line = reader.readLine(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		temps =new String[tempsvec.size()];
		tempsvec.copyInto(temps);
 
		for(int i  = 0;i < temps.length;i++){
			String tempstr = (String)temps[i];
			this.matchAndNext(tempstr);
		}
	}
	public TestBrancketName(){
		String str =null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("bracketname.txt")));
			str = reader.readLine(); 
		} catch (Exception e) {
		}
		System.out.println(str); 
		this.re = new RE(str);
	}
	public static void main(String[] strings) {
		new TestBrancketName().process();
	}
	public void matchAndNext(String temp){
		if(this.re.match(temp)){
			System.out.println(re.getParen(0));
			this.matchAndNext(temp.substring(re.getParenEnd(0)));
		}
	}

}
