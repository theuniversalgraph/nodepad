package enclosing.application.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.util.StringTokenizer;

public class FindNodeEditer  extends TextArea implements KeyListener
{
	
	private NodeObserver  observer; 

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        System.err.println(e.getKeyCode() +" typed");
        
        if(e.getKeyCode() == 27){
//        	FindNode findNode = new FindNode();
//        	findNode.findNode();
//            this.nc.returnFromEditing();
//            this.nc.requestFocusInWindow();
        }

    }
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) { 
    }
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
    }
    public FindNodeEditer(NodeObserver  observer)    {
    	this.observer = observer;
		setLocation(this.observer.getNodeFieldApplet().getWidth()/2 - 20,this.observer.getNodeFieldApplet().getHeight()/2);
		this.setSize(200,15);
		setBackground(Color.black);
		setForeground(Color.white);
//		enableEvents(KeyEvent.INPUT_METHOD_EVENT_MASK);
		enableEvents(KeyEvent.KEY_EVENT_MASK);
		addKeyListener(this);
		enableEvents(TextEvent.TEXT_EVENT_MASK);
    }

    public void setText(String text){
		StringTokenizer st = new StringTokenizer(text, "\n\r");
		String[] lines = new String[st.countTokens()];
		int i = 0;
		int saidai_length = 0;
		while (st.hasMoreTokens()) {
			lines[i] = st.nextToken();
//			if(saidai_length< nc.getFm().stringWidth(lines[i])){
//				saidai_length = nc.getFm().stringWidth(lines[i]);
//			}
//			i++;
		}
//		
//		if(nc.getNodeInterface().getContent().length() != 0)
//			setSize(saidai_length+20, 14*(i-1)+28);
//		else
//			setSize(80, 20);
    }
	protected void processTextEvent(TextEvent te) {
		super.processTextEvent(te);
		this.setText(this.getText());
	}
	public NodeObserver getObserver() {
		return observer;
	}
	public void setObserver(NodeObserver observer) {
		this.observer = observer;
	}

	
}
