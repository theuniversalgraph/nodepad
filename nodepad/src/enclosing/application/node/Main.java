/*
 * �쐬��: 2004/05/04
 *
 * ���̐������ꂽ�R�����g�̑}����e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package enclosing.application.node;
import myutil.MainFrame;
import java.awt.*;

import org.apache.commons.lang.StringUtils;

/**
 * @author Administrator
 *
 */
public class Main {
	public static void main(String[] args) {
		NodeFieldApplet applet = new NodeFieldApplet();
		applet.setNet(false);
		System.err.println(" -----------------starting nodepad -------------------");

		Frame frame = new MainFrame(applet, 500, 500);
		while(applet.observer==null){
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		applet.setFrame(frame);
		frame.addWindowListener(new NodepadWindowAdapter(applet.observer));
	
		if(args.length > 0){
			for (int i = 0; i < args.length; i++) {
				String string = args[i];
				string = string.trim();
				System.err.println(string + " is " + i + " th arg of " + args.length);
				if(StringUtils.isNotBlank(string)){
					if(string.equals("-m")){
						applet.observer.setMac(true);
						
					}else{
						applet.observer.setMac(true);
						
						System.err.println("trying to go...applet");
						applet.observer.openFromFile(string);
						frame.setTitle(string);
					}
				}
			}
		}else{
			applet.observer.setMac(true);
			
		    frame.setTitle("top.nd");
			applet.observer.openFromFile("./data/top.nd");
			System.err.println("nothing maybe");
		}
	} // end of main (method of HelloJava3D)
	
}
