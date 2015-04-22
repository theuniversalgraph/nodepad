// Decompiled by Jad v1.5.7d. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeObserverMouseAdapter.java

package enclosing.application.node;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import enclosing.application.node.ncplugins.EnCauseNodesWithRelativeYPosision;
import enclosing.application.node.suggestion.AutoExpandOneStepForAllWikiLinkComponent;


// Referenced classes of package enclosing.application.node:
//            NodeObserver, NewNodeComponent

public class NodeObserverMouseAdapter extends MouseAdapter
{

	/**
	 * 
	 * @uml.property name="observer"
	 */
	public NodeObserver getObserver() {
		return observer;
	}


    public void mouseClicked(MouseEvent me)
    {

            
		if (me.getClickCount() == 2) {
			if(!observer.getMode().equals("selected") && !observer.getMode().equals("editing")){
				NodeComponent nc = this.observer.makeNewNornalNodeAt(me.getX(),me.getY());
				nc.goEditing();
			}
		}
            
    }
    public NodeObserverMouseAdapter(NodeObserver observer)
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

	/* (�� Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	

	/* (�� Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me) {
		this.observer.getNodeFieldApplet().requestFocus();

		for(Enumeration en = this.observer.getNode_components().elements();en.hasMoreElements();){
			((NodeComponent)en.nextElement()).returnFromEditing();
		}

		if(observer.getMode().equals("editing")){
			for(Enumeration en = observer.getNode_components().elements();en.hasMoreElements();){
				NodeComponent nc = (NodeComponent)en.nextElement();
				if(nc.isEditing()){
					nc.returnFromEditing();
				}
			}
		}
		if(!me.isControlDown()){
			this.observer.disselectAllNodes(this.observer.getNode_components());
		}

		if(me.getButton() == MouseEvent.BUTTON2 || me.getButton()== MouseEvent.BUTTON3){
			this.observer.setMode("panelmoving",me.getPoint());
		}else{
			SelectingZone selectingZone = new SelectingZone();
			this.observer.getNode_container().add(selectingZone);
			selectingZone.setStartx(me.getX());
			selectingZone.setStarty(me.getY());
			selectingZone.setLocation(me.getX(),me.getY());
			this.observer.getMode().setSelectingZone(selectingZone);
			selectingZone.validate();
			selectingZone.setVisible(true);
//			this.observer.repaint();
//			selectingZone.repaint();
			if(me.isAltDown()){
		        new EnCauseNodesWithRelativeYPosision(this.getObserver().getMode().getSelected(),this.getObserver());
			}
		}
		
		

	}

	/* (�� Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me) {
		//for panel move dragging the field 
		if(me.getButton() == MouseEvent.BUTTON2 || me.getButton()== MouseEvent.BUTTON3){
			Point startPoint = (Point)this.observer.getMode().getMode_object();
			for(Enumeration en = this.observer.getNode_components().elements();en.hasMoreElements();){
				NodeComponent nc = (NodeComponent)en.nextElement();
				nc.moveLocation(me.getX()-((int)startPoint.getX()),me.getY()-((int)startPoint.getY()));
			}
			
			//for indicaters
			//display indicaters with havingleft,right,up,down flag
			
			this.observer.getNodeFieldApplet().setHavingDown(false);
			this.observer.getNodeFieldApplet().setHavingLeft(false);
			this.observer.getNodeFieldApplet().setHavingRight(false);
			this.observer.getNodeFieldApplet().setHavingUP(false);
/*			
			for(Enumeration en = this.observer.getNode_components().elements();en.hasMoreElements();){
				NodeComponent nc = (NodeComponent)en.nextElement();
				if(this.observer.getNodeFieldApplet().getWidth()<nc.getX()){
					this.observer.getNodeFieldApplet().setHavingRight(true);
				}
				if(nc.getX()<0){
					this.observer.getNodeFieldApplet().setHavingLeft(true);
				}
				if(nc.getY()<0){
					this.observer.getNodeFieldApplet().setHavingUP(true);
					
				}
				if(this.observer.getNodeFieldApplet().getHeight()<nc.getY()){
					this.observer.getNodeFieldApplet().setHavingDown(true);
				}
			}
	*/
			this.getObserver().getNodeFieldApplet().repaint();

		}else{
			boolean selecting = true;
			this.observer.setMode("selectingZoneEnds",me);
			SelectingZone selectingZone = this.observer.getMode().getSelectingZone();
			try {
				this.observer.getNode_container().remove(selectingZone);
			} catch (NullPointerException e) {
				selecting = false;
			}

			if(selecting){
				if(this.observer.getMode().getSelected_line()!=null){
					for(Enumeration en1 = this.observer.getMode().getSelected_line().elements();en1.hasMoreElements();){
						((Line)en1.nextElement()).disselected();
					}
				}
				this.observer.getMode().setSelectingZone(null);
				for(Enumeration en = this.observer.getNode_components().elements();en.hasMoreElements();){
					NodeComponent nc = (NodeComponent)en.nextElement();
					if(selectingZone.contains(nc.getX()-selectingZone.getX() ,nc.getY()-selectingZone.getY())){
						if(!nc.isSelected())
							nc.selected();
						else{
							nc.disselected();
						
							
						}
					}
				}
			}
			if(me.isAltDown() && me.isControlDown() && me.isShiftDown()){
				AutoExpandOneStepForAllWikiLinkComponent autoExpandOneStepForAllWikiLinkComponent 
				     = new AutoExpandOneStepForAllWikiLinkComponent(this.getObserver());
			}

			if(me.isAltDown()){
		        new EnCauseNodesWithRelativeYPosision(this.getObserver().getMode().getSelected(),this.getObserver());
			}
			
		}
	}
}
