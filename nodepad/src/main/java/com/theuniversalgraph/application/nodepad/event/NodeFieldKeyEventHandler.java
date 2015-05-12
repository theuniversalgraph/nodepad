package com.theuniversalgraph.application.nodepad.event;

import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Enumeration;

import com.theuniversalgraph.application.nodepad.AlignNodeToLeft;
import com.theuniversalgraph.application.nodepad.AlignNodeToTop;
import com.theuniversalgraph.application.nodepad.ClipBoardManager;
import com.theuniversalgraph.application.nodepad.FieldZoomManager;
import com.theuniversalgraph.application.nodepad.FollowAllTheUrlOfNodes;
import com.theuniversalgraph.application.nodepad.NodeComponent;
import com.theuniversalgraph.application.nodepad.NodeFieldApplet;

import enclosing.application.node.fileplugins.OpenXlsWithTheSameNameAsNodeFile;
import enclosing.application.node.ncplugins.EnCauseNodesWithRelativeYPosision;
import enclosing.application.node.ncplugins.ProcessNodeCrudRequestByAccessingServer;
import enclosing.application.node.ncplugins.UniteNodesIntoOneNode;
import enclosing.application.node.suggestion.AutoExpandOneStepForNodeComponents;
import enclosing.faceless.AllNode;
import enclosing.faceless.MakeNodeFilesForExistingNodes;
import enclosing.model.TagHash;
import enclosing.util.NodeUtils;
import enclosing.webapi.client.NodesToTodoistItems;

public class NodeFieldKeyEventHandler {
	NodeFieldApplet nodeFieldApplet = null;
	public NodeFieldKeyEventHandler(NodeFieldApplet nodeFieldApplet) {
		this.nodeFieldApplet = nodeFieldApplet;
	}
	
	public void process(KeyEvent ke){
	    if(ke.isAltDown())
			alt(ke);
	    if(ke.isControlDown()){
		    if(ke.isShiftDown()){ 
		    	ctrlShift(ke);
				if(ke.isAltDown()){
					ctrlAltShift(ke);
				}
		    }else if(ke.isAltDown()){
		    	ctrlAlt(ke);
		    }else  {
			    ctrl(ke);
		    }
		}
		if(!ke.isAltDown() && !ke.isActionKey() && !ke.isAltGraphDown() && !ke.isControlDown() && !ke.isMetaDown() && !ke.isShiftDown()){
		    noModifiers(ke);
		}
	}

	private void noModifiers(KeyEvent ke) {
		if(ke.getKeyCode()==107)
		    new EnCauseNodesWithRelativeYPosision(this.nodeFieldApplet.getObserver().getMode().getSelected(),this.nodeFieldApplet.getObserver());
		if(ke.getKeyCode()==127){//delete
			if(ke.isShiftDown()){
				this.nodeFieldApplet.getObserver().getMode().setHardDelete(true); 
			}
			this.nodeFieldApplet.getObserver().setMode("delete",null);
		}
		this.nodeFieldApplet.tagging(ke);
	}

	private void ctrl(KeyEvent ke) {
		if(ke.getKeyCode()==KeyEvent.VK_DELETE)
			Collections.list(this.nodeFieldApplet.getObserver().getNode_components().elements()).forEach(NodeUtils::removeTagStringAndSave);

		if(ke.getKeyCode() == KeyEvent.VK_I){
			MakeNodeFilesForExistingNodes.makeNodeFieldsFromNodesOfANodeFields(AllNode.getInstance().getAllNodesHash(), this.nodeFieldApplet.getObserver().getNodeHashFromNCHash());
//			    	if(this.nodeFieldApplet.isIdshow()){
//			    		this.nodeFieldApplet.setIdshow(false);
//			    		this.nodeFieldApplet.setSkin(new MoniterSkin());
//			    	}else{
//				    	this.nodeFieldApplet.setIdshow(true);
//				    	this.nodeFieldApplet.setSkin(new PrintingSkin());
//			    	}
//			    	this.nodeFieldApplet.repaint();
		}
		if(ke.getKeyCode() == KeyEvent.VK_U){
			ProcessNodeCrudRequestByAccessingServer.process(this.nodeFieldApplet.getObserver());
			this.nodeFieldApplet.repaint();
		}

		if(ke.getKeyCode()==KeyEvent.VK_T){
			System.err.println(this.nodeFieldApplet.getObserver().getFilename());
			System.err.println(this.nodeFieldApplet.getObserver().getNodefieldName());
			new NodesToTodoistItems(this.nodeFieldApplet.getObserver().getNodefieldName());
		}
		if(ke.getKeyCode()==83){
			this.nodeFieldApplet.getObserver().setMode("save",this);
		}
		if(ke.getKeyCode()== 67)
			new ClipBoardManager(this.nodeFieldApplet.getObserver()).copy();
		if(ke.getKeyCode()==86)
			new ClipBoardManager(this.nodeFieldApplet.getObserver()).paste();
		if(ke.getKeyCode()==88)
			new ClipBoardManager(this.nodeFieldApplet.getObserver()).cut();
		if(ke.getKeyCode()==65)
			this.nodeFieldApplet.getObserver().selectAllNodes();
		if(ke.getKeyCode() == KeyEvent.VK_L)
		    new EnCauseNodesWithRelativeYPosision(this.nodeFieldApplet.getObserver().getMode().getSelected(),this.nodeFieldApplet.getObserver());

		if(ke.getKeyCode()==87){
			if(!this.nodeFieldApplet.getObserver().checkDirtyAndSave()){
				this.nodeFieldApplet.getObserver().applicationEnds(null);
			}				
		}
		if(ke.getKeyCode()==107 || ke.getKeyCode()==187){
			new FieldZoomManager(this.nodeFieldApplet.getObserver()).zoomin();
			this.nodeFieldApplet.getObserver().getFontManager().setFontSizeBigger();
		}
		if(ke.getKeyCode()==109 || ke.getKeyCode()==189){
			new FieldZoomManager(this.nodeFieldApplet.getObserver()).zoomout();
			this.nodeFieldApplet.getObserver().getFontManager().setFontSizeSmaller();
		}

		if(ke.getKeyCode() == ke.VK_N){
			Enumeration enumeration = this.nodeFieldApplet.getObserver().getNode_components().elements();
			while (enumeration.hasMoreElements()) {
				NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
				if(!nodeComponent.getNodeInterface().getContent().startsWith("@")
						&& !nodeComponent.getNodeInterface().getContent().startsWith("どうすれば")
						&& !nodeComponent.getNodeInterface().getContent().contains("考")
						&& !nodeComponent.getNodeInterface().getContent().contains("どうすれば")
				){
					nodeComponent.getNodeInterface().setContent("どうすれば"+ nodeComponent.getNodeInterface().getContent());
					nodeComponent.setText(nodeComponent.getNodeInterface().getContent());
				}
			}
		}
		
	}

	private void ctrlAlt(KeyEvent ke) {
		if(ke.getKeyCode() == KeyEvent.VK_L){
			FollowAllTheUrlOfNodes followAllTheUrlOfNodes =new FollowAllTheUrlOfNodes(this.nodeFieldApplet.getObserver().getMode().getSelected().elements());
		}
	}

	private void ctrlAltShift(KeyEvent ke) {
		if(ke.getKeyCode() == KeyEvent.VK_A){
			AutoExpandOneStepForNodeComponents autoExpandOneStepForNodeComponents
			 =new AutoExpandOneStepForNodeComponents(this.nodeFieldApplet.getObserver().getMode().getSelected().elements(),this.nodeFieldApplet.getObserver().getNode_components());
		}else if(ke.getKeyCode() == KeyEvent.VK_S){
			if(!this.nodeFieldApplet.isSeekingNext()){
				this.nodeFieldApplet.setSeekingNext(true);
				Enumeration enumeration = this.nodeFieldApplet.getObserver().getNode_components().elements();
				while (enumeration.hasMoreElements()) {
					NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
					if(nodeComponent.getNodeInterface().getContent().startsWith("#current")){
						this.nodeFieldApplet.setCurrentBarExists(true);
						this.nodeFieldApplet.setYOfCurrentBar(nodeComponent.getY());
						break;
					}
				}
			}else{
				this.nodeFieldApplet.setSeekingNext(false);
				this.nodeFieldApplet.setCurrentBarExists(false);
			}
		}
	}

	private void ctrlShift(KeyEvent ke) {
		switch (ke.getKeyCode()) {
		case KeyEvent.VK_ENTER:
		    new UniteNodesIntoOneNode(this.nodeFieldApplet.getObserver().getMode().getSelected().elements(),this.nodeFieldApplet.getObserver());
			break;
		case KeyEvent.VK_X:
		    new OpenXlsWithTheSameNameAsNodeFile(this.nodeFieldApplet.getObserver());
			break;
		case 107:
			this.nodeFieldApplet.getSkin().morecontrast();
			this.nodeFieldApplet.repaint();
			break;
		case 109:
			this.nodeFieldApplet.getSkin().lesscontrast();
			this.nodeFieldApplet.repaint();
			break;
		case KeyEvent.VK_LEFT:
			new AlignNodeToLeft(this.nodeFieldApplet.getObserver().getMode().getSelected());
			break;
		case KeyEvent.VK_UP:
			new AlignNodeToTop(this.nodeFieldApplet.getObserver().getMode().getSelected());
			break;
		default:
			break;
		}
	}

	private void alt(KeyEvent ke) {
		if(TagHash.getInstance().getTag(ke.getKeyCode()) !=null ){
			if(this.nodeFieldApplet.isTaggedHoist() && TagHash.getInstance().getTag(ke.getKeyCode()).equals(this.nodeFieldApplet.getHoisttag())){
				for (Enumeration en= this.nodeFieldApplet.getObserver().getNode_components().elements();en.hasMoreElements();) {
					NodeComponent nc = (NodeComponent)en.nextElement();
					if(!nc.getNodeInterface().getContent().startsWith( this.nodeFieldApplet.getHoisttag())){
						nc.setHoist(false);
						nc.repaint();
					}
				}
				this.nodeFieldApplet.setHoisttag("");
				this.nodeFieldApplet.setTaggedHoist(false);
				
			}else{
				this.nodeFieldApplet.setHoisttag(TagHash.getInstance().getTag(ke.getKeyCode()));
				this.nodeFieldApplet.setTaggedHoist(true);
				for (Enumeration en= this.nodeFieldApplet.getObserver().getNode_components().elements();en.hasMoreElements();) {
					NodeComponent nc = (NodeComponent)en.nextElement();
					if(!nc.getNodeInterface().getContent().startsWith(this.nodeFieldApplet.getHoisttag())){
						nc.setHoist(true);
						nc.repaint();
					}
				}	
			}
		}				
		
//	        if(ke.getKeyCode() == KeyEvent.VK_P){
//	            new OpenRelatedMPP(this.getObserver());
//	        }
	}

}
