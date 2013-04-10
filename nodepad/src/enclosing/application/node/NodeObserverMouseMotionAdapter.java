/*
 * �쐬��: 2004/05/01
 *
 * ���̐������ꂽ�R�����g�̑}����e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package enclosing.application.node;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Administrator
 *
 * ���̐������ꂽ�R�����g�̑}����e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
public class NodeObserverMouseMotionAdapter extends MouseMotionAdapter {

	/* (�� Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON2){
			
		
		}else{
			if(this.observer.getSelectingZone() != null){
				SelectingZone selectingZone = this.observer.getSelectingZone();
				if(arg0.getX() < selectingZone.getStartx()){
					selectingZone.setLocation(arg0.getX(),selectingZone.getY());
				}
				if(arg0.getY()< selectingZone.getStarty()){
					selectingZone.setLocation(selectingZone.getX(),arg0.getY());
				}
				selectingZone.setSize(Math.abs(arg0.getX()-selectingZone.getStartx()), Math.abs(arg0.getY()-selectingZone.getStarty()));
				this.observer.getSelectingZone().repaint();
				this.observer.repaint();
			}
		}
	}

	/* (�� Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
	}
	
	public NodeObserverMouseMotionAdapter(NodeObserver observer)
	{
		this.observer = observer;
	}

	public void setObsever(NodeObserver observer)
	{
		this.observer = observer;
	}

	/**
	 * 
	 * @uml.property name="observer"
	 * @uml.associationEnd 
	 * @uml.property name="observer" multiplicity="(1 1)"
	 */
	private NodeObserver observer;

	/**
	 * 
	 * @uml.property name="observer"
	 */
	public NodeObserver getObserver() {
		return observer;
	}

}
