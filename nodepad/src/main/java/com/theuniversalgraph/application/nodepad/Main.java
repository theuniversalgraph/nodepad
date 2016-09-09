package com.theuniversalgraph.application.nodepad;
import myutil.MainFrame;
import org.apache.commons.lang.StringUtils;

import java.awt.*;

public class Main {
	public static void main(String[] args) {
		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		String file = "top.json";
		System.err.println(" ----------------- starting nodepad -------------------");

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
			applet.observer.getMode().setMac(true);
		}
		if(args.length > 0){
			for (int i = 0; i < args.length; i++) {
				file = args[i];
				file = file.trim();
				System.err.println(file + " is " + i + " th arg of " + args.length);
				if(StringUtils.isNotBlank(file)){
					applet.observer.getNodepadDao().openFromFile(file);
					frame.setTitle(file);
				}
			}
		}else{
			frame.setTitle(file);
			applet.observer.getNodepadDao().openFromFile("./data/build_menu.json");
		}
	} 
}

