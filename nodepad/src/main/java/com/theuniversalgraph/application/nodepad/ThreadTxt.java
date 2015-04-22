package com.theuniversalgraph.application.nodepad;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ThreadTxt {
	public ThreadTxt(NodeComponent node){
		this.setNode(node);
	}
	NodeComponent node = null;
	public NodeComponent getNode() {
		return node;
	}
	public void setNode(NodeComponent node) {
		this.node = node;
	}
	public String getText(){
		StringBuilder 			builder = new StringBuilder();
		try {

			String string = node.getNodeInterface().getContent();
			SAXReader reader = new SAXReader();
			Document document = reader.read( new LocalStaticData().getValue("storytellerurl") + "/DisplayXmlOfJ2eeStory.do");
			Element j2eeStorysElemnt =  document.getRootElement();
			int count = 0;
			String projectname = "";
			
			for (Iterator iter = j2eeStorysElemnt.elementIterator("j2eestory"); iter.hasNext();) {
				Element j2eeStoryElement = (Element) iter.next();
				String name = j2eeStoryElement.elementText("name");
				String id = j2eeStoryElement.elementText("id");
				Pattern pattern = Pattern.compile(name);
				Matcher matcher = pattern.matcher(string);
					count++;
					while(matcher.find()){
					if(count>2){
						projectname = name;
						string = projectname + "(project: http://storytellermachine.net/storyteller/J2eeStoryDetail.do?id="+id+")"; 
						break;
					}
				}
			}
			
			builder.append(string);
			if(node.getParents().size()>0){
				Enumeration enumeration = node.getParents().elements();
				while (enumeration.hasMoreElements()) {
					NodeComponent parentNodeComponent = (NodeComponent) enumeration.nextElement();
					builder.append("->");
					ThreadTxt threadTxt = new ThreadTxt(parentNodeComponent);
					builder.append(threadTxt.getText());
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		Stack stack = new Stack();
		StringTokenizer stringTokenizer = new StringTokenizer(builder.toString(),"->");
		while (stringTokenizer.hasMoreElements()) {
			String string = (String) stringTokenizer.nextToken();;
			stack.push(string);
		}
		StringBuilder builder2 = new StringBuilder();
		while (!stack.empty()) {
			builder2.append(stack.pop() + " <- ");
		}
		return builder2.toString();
	}
}
