/*
 * 作成日: 2004/07/04
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package enclosing.application.node.test;

import java.applet.Applet;
import java.net.URL;

/**
 * @author Administrator
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class TestNewWindow extends Applet {
	public void init(){
		try {
			this.getAppletContext().showDocument(new URL("http://www.sfc.keio.ac.jp"),"_brank");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
