/*
 * �쐬��: 2004/07/04
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package enclosing.application.node.test;

import java.applet.Applet;
import java.net.URL;

/**
 * @author Administrator
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
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
