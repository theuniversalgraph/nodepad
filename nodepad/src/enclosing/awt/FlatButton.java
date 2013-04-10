/*
 * 作成日: 2004/05/30
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
package enclosing.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * @author Administrator
 *
 * この生成されたコメントの挿入されるテンプレートを変更するため
 * ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class FlatButton extends FlatComponent{
	private String command = null;

	/**
	 * 
	 * @uml.property name="parentFC"
	 * @uml.associationEnd 
	 * @uml.property name="parentFC" multiplicity="(1 1)"
	 */
	private FlatContainer parentFC = null;

	public FlatButton(String command,FlatContainer parent){
		this.command = command;
		this.parentFC = parent;
		enableEvents(16L);

		this.setSize(50,30);
		setFont(new Font("Dialog", 0, 14));		
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,this.getPreferredSize().width,(int)this.getPreferredSize().height);
		g.setColor(Color.BLACK);
		g.drawRect(0,0,this.getPreferredSize().width-3,(int)this.getPreferredSize().height-3);
		g.drawString(this.command,3,20);
	}

	public void processMouseEvent(MouseEvent me)	{

			switch(me.getID())
			{
				default:
				break;

				case 500:
					this.parentFC.callBackCommand(this);
					break; 

			}
	}

	public boolean contains(int x, int y)
	{
		return x > 0 && y > 0 && x < getSize().width && y < getSize().height;
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public Dimension getMinimumSize()
	{
		return new Dimension(this.getSize().width,this.getSize().height);
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(this.getSize().width,this.getSize().height);
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

	/**
	 * @return
	 * 
	 * @uml.property name="parentFC"
	 */
	public FlatContainer getParentFC() {
		return parentFC;
	}

	/**
	 * @param container
	 * 
	 * @uml.property name="parentFC"
	 */
	public void setParentFC(FlatContainer container) {
		parentFC = container;
	}

}
