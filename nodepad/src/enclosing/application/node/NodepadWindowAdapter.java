/*
 * �쐬��: 2004/05/30
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package enclosing.application.node;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Administrator
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
public class NodepadWindowAdapter extends WindowAdapter {

	/**
	 * 
	 * @uml.property name="observer"
	 * @uml.associationEnd 
	 * @uml.property name="observer" multiplicity="(1 1)"
	 */
	NodeObserver observer = null;

	public NodepadWindowAdapter(NodeObserver observer){
		this.observer= observer;
	}
	public void windowClosing(WindowEvent windowevent)	{
		this.observer.setWindowevent(windowevent);
		if(!this.observer.checkDirtyAndSave()){
			observer.applicationEnds(windowevent);
		}
	}
	

}
