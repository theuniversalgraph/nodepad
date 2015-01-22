package enclosing.application.node;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
