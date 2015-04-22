/*
 * Created on 2004/08/29
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node;

import java.awt.Font;
import java.util.Enumeration;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FontManager {
	
	NodeObserver nodeObserver = null;
	private int size = 14;
	private int margin = 16;
	public FontManager(NodeObserver nodeObserver){
		this.nodeObserver = nodeObserver;
	}
	public void setFontSize(int size){
		this.size = size;
		this.refresh();
	}
	public void refresh(){
		for(Enumeration en = this.nodeObserver.getNode_components().elements();en.hasMoreElements();){
			NodeComponent component =(NodeComponent)en.nextElement(); 
			component.setFont(new Font("Dialog", Font.PLAIN, this.size));
			component.setFm(component.getFontMetrics(component.getFont()));
			component.setText(component.getNodeInterface().getContent());
		}
//		this.nodeObserver.repaint();
		this.nodeObserver.getNodeFieldApplet().repaint();

	}
	
	
	public int getMargin() {
		return margin;
	}
	public void setMargin(int margin) {
		this.margin = margin;
	}
	public void setFontSizeBigger(){
		this.size++;
		this.margin++;
		this.refresh();
	}
	public void setFontSizeSmaller(){
		this.size--;
		this.margin--;
		this.refresh();
	}
	

	/**
	 * @return Returns the size.
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size The size to set.
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
