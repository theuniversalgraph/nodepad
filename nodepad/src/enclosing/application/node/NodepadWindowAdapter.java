/*
 * 作成日: 2004/05/30
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package enclosing.application.node;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Administrator
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
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
