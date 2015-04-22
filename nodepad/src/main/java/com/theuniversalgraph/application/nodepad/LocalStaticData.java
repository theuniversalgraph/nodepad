package com.theuniversalgraph.application.nodepad;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LocalStaticData {
	public LocalStaticData(){
		try {
			SAXReader reader = new SAXReader();
			Document staticDataDocument = reader.read("staticData.xml");
			this.setStaticDataElement(staticDataDocument.getRootElement());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public String getValue(String param){
		return  this.staticDataElement.element(param).getText();
	}
	Document document = null;
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	Element staticDataElement;
	public Element getStaticDataElement() {
		return staticDataElement;
	}
	public void setStaticDataElement(Element staticDataElement) {
		this.staticDataElement = staticDataElement;
	}
	
}
