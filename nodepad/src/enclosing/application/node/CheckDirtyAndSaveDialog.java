/*
 * �쐬��: 2004/05/30
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
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
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
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
