/*
 * 作成日: 2004/05/30
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package enclosing.application.node;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Administrator
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class CheckDirtyAndSaveDialog extends WindowAdapter {


	private void start() {
			Dialog alert = new Dialog(new Frame() , "Kitty on your lap");
			alert.add(new Label("Kitty on your lap"));

			alert.setSize(300 , 150);
			alert.setVisible(true);

			alert.addWindowListener(this);
	}
	public void windowClosing(WindowEvent e) {
			System.exit(0);
	}

}
