/*
 * �쐬��: 2004/05/30
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package enclosing.awt;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * @author Administrator
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
public abstract class FlatContainer extends Container{
	public abstract void callBackCommand(FlatButton fb);
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0,0,this.getPreferredSize().width-3,(int)this.getPreferredSize().height-3);
		g.setColor(Color.BLACK);
		g.drawRect(0,0,this.getPreferredSize().width-3,(int)this.getPreferredSize().height-3);
		super.paint(g);
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
		return new Dimension(this.getPreferredSize().width, this.getPreferredSize().height);
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(this.getSize().width, this.getSize().height);
	}

	public void addFlatButton(FlatButton fb){
		this.add(fb);

	}
}
