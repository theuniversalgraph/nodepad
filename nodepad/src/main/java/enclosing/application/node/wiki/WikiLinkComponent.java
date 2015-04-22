/*
 */
package enclosing.application.node.wiki;

import java.awt.*;
import java.awt.event.MouseEvent;

import com.theuniversalgraph.application.nodepad.FontManager;
import com.theuniversalgraph.application.nodepad.NodeObserver;
/**
 * @author Administrator
 *
 */
public class WikiLinkComponent extends Component{
	
	
	
	public WikiLinkComponent(String content,NodeObserver obsever){
		this.branketContent = content;
		this.observer = obsever;
		enableEvents(16L);
	}
	
	public void paint(Graphics g)
	{
		g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getWikiLinkBackColor());
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getWikiLinkLineColor());
		g.drawLine(0,this.getHeight()-1,this.getWidth(),this.getHeight()-1);
//		g.setFont(new Font("Dialog",Font.BOLD,10));

		
        if(this.isTransparent() ){
        	g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getWikiLinkTransparentForColor());
        }else{
    		g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getWikiLinkColor());
        }
//        g.setFont(new Font("Dialog", Font.BOLD, this.getObserver().getFontManager().getSize()));
		g.drawString(this.branketContent,0,12);

	}
	public void processMouseEvent(MouseEvent me)
	{
		switch(me.getID())
		{
		default:
			break;

		case 500: 
			if(me.getButton() == MouseEvent.BUTTON3){
				this.observer.getMode().setMode_object(this);
				this.observer.setMode("WikiAutoExpand",this);
				
			}else{
				if(me.getClickCount() ==2){
					if(me.isShiftDown()){
						this.observer.getMode().setMode_object(this);
						this.observer.setMode("WikiAutoExpand",this);
						
					}else{
						this.observer.getMode().setMode_object(this);
						this.observer.setMode("WikiClicked",this);
					}
				}
				
			}

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

	public Dimension getPreferredSize()
	{
		return getSize();
	}
	
	public Dimension getMinimumSize()
	{
		return getSize();
	}

	/**
	 * 
	 * @uml.property name="observer"
	 * @uml.associationEnd 
	 * @uml.property name="observer" multiplicity="(1 1)"
	 */
	private NodeObserver observer = null;
	private boolean transparent = false;
	
	String branketContent = "";

	/**
	 * @return
	 * 
	 * @uml.property name="branketContent"
	 */
	public String getBranketContent() {
		return branketContent;
	}

	/**
	 * @param string
	 * 
	 * @uml.property name="branketContent"
	 */
	public void setBranketContent(String string) {
		branketContent = string;
	}

	/**
	 * @return
	 * 
	 * @uml.property name="observer"
	 */
	public NodeObserver getObserver() {
		return observer;
	}

	/**
	 * @param observer
	 * 
	 * @uml.property name="observer"
	 */
	public void setObserver(NodeObserver observer) {
		this.observer = observer;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	
}
