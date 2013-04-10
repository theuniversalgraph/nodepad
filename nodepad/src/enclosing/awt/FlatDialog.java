/*
 * 作成日: 2004/05/30
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package enclosing.awt;


import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * @author Administrator
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public abstract class FlatDialog extends FlatContainer {
	String command = null;
	String mongon = null;

	/**
	 * 
	 * @uml.property name="listeners"
	 * @uml.associationEnd 
	 * @uml.property name="listeners" multiplicity="(0 -1)" elementType="enclosing.awt.FlatDialogListener"
	 */
	Vector listeners = new Vector();

	public void addFlatDialogListener(FlatDialogListener listener){
		this.listeners.add(listener);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.drawString(mongon,30,30);
	}

	public FlatDialog(String mongon,String[] commands ,Container parent){
		this.mongon = mongon;
	
		for(int i = 0;i < commands.length;i++){
			FlatButton button = new FlatButton(commands[i],this);
			button.setLocation(i*50+10,50);
			button.setVisible(true);
			this.addFlatButton(button);
		}
		this.setLocation(0,0);
		this.setSize(parent.getWidth(),parent.getHeight());
		//		this.setLocation((parent.getWidth()/2)-50,(parent.getHeight()/2)-50);
		//		this.setSize(i*50+20,100);
		Component[] coms =parent.getComponents();
		for(int i = 0;i < coms.length;i++){
			coms[i].setVisible(false);
		}
		enableEvents(16L);

	}



	public void processMouseEvent(MouseEvent me)	{

			switch(me.getID())
			{
			case 504: 

				repaint();
				break;

			case 500: 

				repaint();
				break;

			case 505: 

				repaint();
				break;
			}
	}

	/* (非 Javadoc)
	 * @see enclosing.awt.FlatContainer#callBackCommand(enclosing.awt.FlatButton)
	 */
	public void callBackCommand(FlatButton fb) {
		Component[] coms =this.getParent().getComponents();
		for(int i = 0;i < coms.length;i++){
			coms[i].setVisible(true);
		}		
		this.command = fb.getCommand();
		this.getParent().remove(this);
		for(int i = 0;i < this.listeners.size();i++){
			((FlatDialogListener)this.listeners.elementAt(i)).dialogHaveEnd(this.command);
		}
	}

	/**
	 * @return
	 * 
	 * @uml.property name="command"
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="command"
	 */
	public void setCommand(String string) {
		command = string;
	}

}
