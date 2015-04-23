package enclosing.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Configuration {
	public String getWindowsRunnable(){
		return getString("windowsRunnable");
	}

	Document staticDataDocument;
	Element staticDataElement;
	public String getString(String param){
		return staticDataElement.element(param).getText();
	}
	private static Configuration configuration = null;
	public static Configuration getInstance(){
		return configuration!=null?configuration:new Configuration();
	}
	private Configuration(){
		try {
			SAXReader reader = new SAXReader();
			this.staticDataDocument = reader.read("staticData.xml");
			staticDataElement = staticDataDocument.getRootElement();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private reflectioning(){
//		ClassLoader classLoader = new URLClassLoader(new URL[]{file.toURL()}, getClass().getClassLoader());
//
//		for (int i = 0; i < classl.getMethods().length; i++) {
//			System.err.println("1." + classl.getMethods()[i].getName() + "is challenged....");
//			if(classl.getMethods()[i].getParameterTypes().length == 1 && 
//					(classl.getMethods()[i].getParameterTypes()[0].getName().equals("java.lang.String") 
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().equals("java.util.Date")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().equals("java.lang.Boolean")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().equals("java.lang.Integer")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().contains("int")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().equals("java.lang.Float")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().contains("float")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().contains("double")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().contains("Double")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().contains("boolean")
//							|| classl.getMethods()[i].getParameterTypes()[0].getName().equals("java.util.Collection")
//							|| hashtable.get(classl.getMethods()[i].getParameterTypes()[0].getName())!=null
//					) 
//					&& classl.getMethods()[i].getName().startsWith("set")){
//	}
}
