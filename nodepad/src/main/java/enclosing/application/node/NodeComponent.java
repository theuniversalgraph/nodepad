package enclosing.application.node;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myutil.filehandler;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.regexp.RE;

import com.theuniversalgraph.model.Node;
import com.theuniversalgraph.model.NodeInterface;

import enclosing.application.node.fileplugins.HttpBrowser;
import enclosing.application.node.ncplugins.BreakNodeIntoNodesWithCRLF;
import enclosing.application.node.ncplugins.DumpContentToText;
import enclosing.application.node.ncplugins.EnCauseNodesWithRelativeYPosision;
import enclosing.application.node.ncplugins.MuseigenText;
import enclosing.application.node.server.OpenFileFromServer;
import enclosing.application.node.suggestion.AutoExpandOneStep;
import enclosing.application.node.wiki.WikiLinkComponent;
import enclosing.model.TagHash;
import enclosing.webapi.client.AddToBackpack;
import enclosing.webapi.client.AddToGoogleCalendar;
import enclosing.webapi.client.AddToTumbler;
import enclosing.webapi.client.GoogleAnswer;
import enclosing.webapi.client.TextToNodes;

// Referenced classes of package enclosing.application.node:
//            NodeInterface, NodeObserver, Line, NodeOutlet, 
//            NodeInlet, NodeEditer

public class NodeComponent extends Container implements Serializable , KeyListener
{



	public void updateNodeInterface()
	{
		ni.setX(getLocation().x);
		ni.setY(getLocation().y);
		ni.setWidth(getSize().width);
		ni.setHeight(getSize().height);
		ni.setChildren(new Vector());
		ni.setParents(new Vector());
		for(Enumeration en = getChildren().keys(); en.hasMoreElements(); ni.getChildren().addElement((String)en.nextElement()));
		for(Enumeration en = getParents().keys(); en.hasMoreElements(); ni.getParents().addElement((String)en.nextElement()));
	}

	public void setObserver(NodeObserver observer)
	{
		this.observer = observer;
	}

	public NodeObserver getObserver()
	{
		return observer;
	}

	/**
	 * 
	 * @uml.property name="dirty"
	 */
	public void setDirty(boolean flag) {
		dirty = flag;
	}

	/**
	 * 
	 * @uml.property name="dirty"
	 */
	public boolean getDirty() {
		return dirty;
	}

	public void disselected()
	{
		setBackground(this.getNormalBackgroundColor());
		setForeground(this.getNormalForegroundColor());
		repaint();
		if(observer != null && observer.getSelected() != null)
		{ 
			observer.getSelected().remove(getNodeinterface().getId());
			if(observer.getSelected().size() == 0)
				observer.setSelected(null);
		}
	}

	public void setInvisibleChildren()
	{
		dirty = true;
		this.setVisible(false);
		for(Enumeration en = this.getChildren_line().elements();en.hasMoreElements();){
			Line line  = (Line)en.nextElement();
			line.setVisible(false);
		}
		for(Enumeration en = children.elements(); en.hasMoreElements(); ((NodeComponent)en.nextElement()).setInvisibleChildren());
	}

	public void goFolded()
	{
		dirty = true;
		setFolded(true);
		setInvisibleChildren();
		this.setVisible(true);
		observer.setMode("folded", this);
	}
	public void goUnfolded(){
		dirty = true;
		setFolded(false);
		setVisibleChildren();
		this.setVisible(true);
	}
	public void setVisibleChildren(){
		this.setVisible(true);
		for(Enumeration en = this.getChildren_line().elements();en.hasMoreElements();){
			Line line = (Line)en.nextElement();
			line.setVisible(true);
		}
		for(Enumeration en = this.getChildren().elements();en.hasMoreElements();){
			NodeComponent nc = (NodeComponent)en.nextElement();
			nc.setVisibleChildren();
		}
	}



	/**
	 * 
	 * @uml.property name="editing"
	 */
	public void setEditing(boolean flag) {
		editing = flag;
	}

	/**
	 * 
	 * @uml.property name="editing"
	 */
	public boolean getEditing() {
		return editing;
	}


	public void setNodeinterface(NodeInterface ni)
	{
		this.ni = ni;
	}

	public NodeInterface getNodeinterface()
	{
		return ni;
	}

	/**
	 * 
	 * @uml.property name="children"
	 */
	public void setChildren(Hashtable hash) {
		children = hash;
	}

	/**
	 * 
	 * @uml.property name="children"
	 */
	public Hashtable getChildren() {
		return children;
	}


	public void update(Graphics g)
	{
		paint(g);
	}

	public void selected()
	{
		setBackground(this.getSelectedBackgroundColor());
		setForeground(this.getSelectedForegroundColor());
		repaint();
		if(observer != null)
		{
			if(observer.getSelected() == null)
				observer.setSelected(new Hashtable());
			observer.getSelected().put(getNodeinterface().getId(), this);
			//            this.observer.getNodeFieldApplet().repaint();
		}
	}

	public void goVisible()
	{
		dirty = true;
		setVisible(true);
		for(Enumeration en = children.elements(); en.hasMoreElements();)
		{
			NodeComponent temp = (NodeComponent)en.nextElement();
			if(temp.getFolded())
			{
				temp.setVisible(true);
			} else
			{
				temp.setVisible(true);
				temp.goVisible();
			}
		}

	}
	public void moveLocationOfChildren(int x,int y ,Hashtable hashtable){
		dirty = true;
		if(!this.getFolded() && hashtable.get(this.getNodeinterface().getId()) == null){
			this.moveLocation(x,y);
			hashtable.put(this.getNodeinterface().getId(),this);
		}
		for(Enumeration en = children.elements(); en.hasMoreElements(); ((NodeComponent)en.nextElement()).moveLocationOfChildren(x,y,hashtable));
	}

	public void moveLocation(int x, int y)
	{
		dirty = true;
		setLocation(getLocation().x + x, getLocation().y + y);
		NodeComponent nc;
		for(Enumeration en = getParents().elements(); en.hasMoreElements(); ((Line)nc.getChildren_line().get(this)).setLocationAndSize(nc, this))
			nc = (NodeComponent)en.nextElement();

		for(Enumeration en = getChildren_line().keys(); en.hasMoreElements(); ((Line)getChildren_line().get(nc)).setLocationAndSize(this, nc))
			nc = (NodeComponent)en.nextElement();
		if(this.getFolded()){
			this.moveLocationOfChildren(x,y,new Hashtable());
		}
		if(this.getObserver().getNodeFieldApplet().isSeekingNext() && this.getNodeinterface().getContent().startsWith("#current")){
			this.getObserver().getNodeFieldApplet().setYOfCurrentBar(this.getY());
			this.getObserver().getNodeFieldApplet().repaint();
		}
		repaint();
	}

	public void setInlet(NodeInlet i)
	{
		inlet = i;
	}

	public NodeInlet getInlet()
	{
		return inlet;
	}

	public void removed()
	{
		new  DumpContentToText(this,this.getObserver(),"removed","./removed.txt");
		if(this.getParents().size()>0 ){
			Enumeration en1 = this.getParents().elements();
			NodeComponent topParentNC = (NodeComponent)en1.nextElement();
			if(!this.getObserver().getSelected().contains(topParentNC)){
				this.getObserver().disselectAllNodes(this.getObserver().getNode_components());
				topParentNC.requestFocusInWindow();
			}
		}

		if(observer != null)
			observer.getNode_components().remove(getNodeinterface().getId());

		getParent().remove(this);

		for(Enumeration en = getChildren_line().elements(); en.hasMoreElements(); ((Line)en.nextElement()).removed());
		for(Enumeration en = getParents().elements(); en.hasMoreElements(); ((Line)((NodeComponent)en.nextElement()).getChildren_line().get(this)).removed());


		this.getObserver().getNodeFieldApplet().repaint();


	}

	public void goEditing()
	{
		disselected();
		editing = true;
		if(observer != null)
			observer.setMode("editing", this);
		processEditing();
	}

	public NodeComponent(NodeObserver observer, NodeInterface ni)
	{
		//for regexp
		if(this.bracketName ==null){
			try {
				if(observer.getNodeFieldApplet().isNet()){
					Vector vec = OpenFileFromServer.process(observer.getNodeFieldApplet().getUser(),"bracketname.txt",observer.getNodeFieldApplet().getServerName());
					this.bracketName = (String)vec.elementAt(0);

				}else{
					BufferedReader reader = new BufferedReader(new FileReader(new File("bracketname.txt")));
					this.bracketName = reader.readLine(); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.err.println(this.bracketName);
		this.re = new RE(this.bracketName);        

		this.ni = null;
		this.observer = null;
		outlet = null;
		inlet = null;
		children_line = new Hashtable();
		children = new Hashtable();
		parents = new Hashtable();
		fm = null;
		editing = false;
		folded = false;
		edit = null;
		pressed = false;
		p = null;
		enableEvents(16L);
		enableEvents(8L);
		this.observer = observer;
		setFont(new Font("Dialog", Font.PLAIN, this.getObserver().getFontManager().getSize()));
		fm = getToolkit().getFontMetrics(getFont());
		this.ni = ni;
		setLocation(ni.getX(), ni.getY());
		outlet = new NodeOutlet(this);
		inlet = new NodeInlet(this);
		add(outlet);
		add(inlet);
		setEditing(false);
		setBackground(this.getNormalBackgroundColor());
		setForeground(this.getNormalForegroundColor());
		setSize(getPreferredSize());
		this.setText(ni.getContent());

		this.setFocusable(true);
		this.addKeyListener(this);

		repaint();
	}

	public void setOutlet(NodeOutlet o)
	{
		outlet = o;
	}

	public NodeOutlet getOutlet()
	{
		return outlet;
	}

	public void paint(Graphics g)
	{
		this.outlet.setLocation(0,this.getPreferredSize().height-10);
		g.setFont(getFont());
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(new GradientPaint(
				0,0, new Color(255,255,255),0 , this.getHeight(),  new Color(236,236,236), false));
		g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNcnormalback());
		//        g.setColor(getBackground());
		if(this.getObserver().getNodeFieldApplet().isTaggedHoist() && !this.isHoist()){

		}else{
			if(this.getObserver().getNodeFieldApplet().isSeekingNext()){
				if(this.getObserver().getNodeFieldApplet().isCurrentBarExists()&& 
						this.getObserver().getNodeFieldApplet().getYOfCurrentBar() - 100 < this.getY()
						&& this.getObserver().getNodeFieldApplet().getYOfCurrentBar() + 100 > this.getY() ) {
					g.setColor(Color.PINK);
				}else{
					g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNctransparentback());
				}
			}else{
				if(this.isTransparent() || this.isHoist()){
					g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNctransparentback());
				}else if(this.isSelected()){
					g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNcselectedback());
					//                	g2.setPaint(new GradientPaint(
					//                            0,0, new Color(249,252,255),0 , this.getHeight(),  new Color(232,243,255), false));
					//                	g.setColor(new Color(234,245,255));
				}
			}
		}
		if(this.isSpecial()){
			g.setFont(new Font("Dialog",0,17));
		}
		// xxxxxxxxxxxxxxxxxxxxxxxx colored node
		if(isColored()){
			Pattern pattern = Pattern.compile("#[0-9a-f]{6}.*");
			Matcher matcher =pattern.matcher(this.contentBufBuf[0]);
			if(matcher.matches()){
				Color color = this.getColorFromCssColor(this.contentBuf[0].substring(0,7));
				g.setColor(color);

				String colorExpression = this.contentBuf[0];
				int rvalue = getColorFromHex(colorExpression.substring(1,3)); 
				//        				16 *  Integer.parseInt()  + Integer.parseInt(this.contentBufBuf[0].substring(2,3));
				int gvalue = getColorFromHex(colorExpression.substring(3,5));
				int bvalue = getColorFromHex(colorExpression.substring(5,7));

				g.setColor(new Color(rvalue,gvalue,bvalue));
				System.err.println("-----------" + rvalue + " -- " + gvalue + " -- -- " + bvalue); 
			}
		}
		if(this.isSpecial()){
			g.fillRoundRect(0, 0, getPreferredSize().width - 4, getPreferredSize().height -4  ,25,25	);

		}else{
			g.fillRoundRect(0, 0, getPreferredSize().width - 4, getPreferredSize().height - 8  ,15,15	);

		}

		g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNcnormalfore());
		if(this.getObserver().getNodeFieldApplet().isTaggedHoist() && !this.isHoist()){
		}else{
			if(this.getObserver().getNodeFieldApplet().isSeekingNext()){
				if(this.getObserver().getNodeFieldApplet().isCurrentBarExists()&& 
						this.getObserver().getNodeFieldApplet().getYOfCurrentBar() - 100 < this.getY()
						&& this.getObserver().getNodeFieldApplet().getYOfCurrentBar() + 100 > this.getY() ) {
					g.setColor(Color.RED);
				}else{
					g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNctransparentfore());
				}
			}else{
				if (this.isHoist() || this.isTransparent()) {
					g.setColor(this.getObserver().getNodeFieldApplet().getSkin()
							.getNctransparentfore());
				} else {
					g.setColor(this.getObserver().getNodeFieldApplet().getSkin()
							.getNcnormalfore());
				}

			}

		}
		//        if(!this.getNodeinterface().getContent().equals("") &&this.contentBufBuf[0]!=null && (this.contentBufBuf[0].startsWith("@"))  ){
		//            if(!this.getNodeinterface().getContent().equals("") &&this.contentBufBuf[0]!=null && (this.contentBufBuf[0].startsWith("@wait") || this.contentBufBuf[0].startsWith("@done") || this.contentBufBuf[0].startsWith("@cycle"))  ){
		//        	g.setColor(this.getObserver().getNodeFieldApplet().getSkin().getNctransparentfore());
		//        }
		if(this.contentBufBuf != null){
			for(int i = 0;i < this.contentBufBuf.length;i++){
				String contextBufBufString = contentBufBuf[i].replaceAll("\\*", "");
				if(isColored())
					contextBufBufString = contextBufBufString.replaceAll("\\#[0-9a-f]{6}", "");
				System.err.println(contextBufBufString);
				this.paintLineWithBracketName(contextBufBufString,g, 11, i*this.getFm().getHeight()+this.getObserver().getFontManager().getMargin());
			}
		}
		if(this.isFocusOwner()){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 5, 5);
			//            g.setFont(new Font("Dialog", 0, 5));
			//            g.drawString("",2,4);
		}
		if(this.getFolded() == true){
			g.setColor(Color.RED);
			g.setFont(new Font("Dialog",0,20));
			g.drawString(" ! ",0,this.getHeight()-5);
		}

		if(observer.getNodeFieldApplet().isIdshow() == true){
			g.setColor(new Color(70,70,70,200));
			g.drawString(this.getNodeinterface().getId(),this.getPreferredSize().width - 20,getPreferredSize().height-4 );
		}
		//       gg.drawImage(offImage,0,0,this);
		//        this.paintComponents(g);
		super.paint(g);
	}

	private boolean isColored() {
		return StringUtils.isNotBlank(this.getNodeinterface().getContent()) && this.contentBufBuf[0]!=null && this.contentBufBuf[0].startsWith("#");
	}
	private int getColorFromHex(String colorExpression) {

		int iHex = Integer.decode( "0x" + colorExpression );

		return iHex;
	}

	private boolean isSpecial() {
		return this.contentBufBuf!=null && this.contentBufBuf[0]!=null && this.contentBufBuf[0].startsWith("*");
	}

	public void handlePlugin(String command){
		if(command.equals("wikilist")){
			if(!this.observer.getNodeFieldApplet().isNet()){
				File dateDir = new File("./data");

				this.contentBufBuf = this.decodeFilename(dateDir.list());
			}else{
				this.contentBufBuf = new String[]{"&wikilist"}; 
			}
		}
		if(command.equals("museigenbox")){
			MuseigenText museigenText = new MuseigenText();
			this.contentBufBuf = museigenText.getContentArr();
		}
		if(command.equals("museigentext")){

		}
		if(command.equals("museigenimage")){

		}
		if(command.equals("google")){
			/** @todo */
		}

		if(command.startsWith("grep")){
			System.out.println("mark 1----------------------------------");
			System.err.println("mark 1 this file is modidied");
			final String keyword = this.contentBuf[0].substring(6).replaceAll("\\[", "").replaceAll("\\]", "");
			System.err.println(keyword);
			File ndfile = new File(this.getObserver().getFilename());
			System.err.println(ndfile.getAbsolutePath());

			File dateDir =  ndfile.getParentFile();
			String filenames[] = dateDir.list();
			System.err.println(filenames);

			Arrays.sort(filenames);
			Vector vector = new Vector();
			StringBuilder builder = new StringBuilder();
			for(int i = 0;i < filenames.length;i++){
				String filename = filenames[i];
				if(filename.endsWith(SimpleStringConstants.FILE_POSTFIX)){
					try{
						Object obj = filehandler.objectInputer(dateDir.getAbsolutePath() + "/" + filename);
						Hashtable hash = (Hashtable)obj;		
						if(hash == null){
							hash = new Hashtable();
						}
						for(Enumeration en = hash.elements();en.hasMoreElements();){
							Node node = (Node)en.nextElement();
							if(node.getContent().indexOf(keyword) >=0){
								filename = filename.substring(0,filename.length()-3);
								if(node.getContent().length()>50){
									builder.append("[[" + filename + "]]\r\n");
									vector.add("[[" + filename + "]]");
									builder.append(node.getContent().substring(0,50)  + "\r\n");

									vector.add(node.getContent().substring(0,50) );
								}else{
									builder.append("[[" + filename + "]]\r\n");
									builder.append(node.getContent() + "\r\n" );

									vector.add("[[" + filename + "]]");
									vector.add(node.getContent() );
								}
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(vector.size() == 0){
				this.contentBufBuf = new String[]{"no result found"};
				this.getNodeinterface().setContent("no result found");
			}else{
				this.contentBufBuf = new String[vector.size()];
				int i = 0;
				for (Iterator iter = vector.iterator(); iter.hasNext();) {
					this.contentBufBuf[i] = (String) iter.next();
					i++;
				}
				this.getNodeinterface().setContent(builder.toString());

			}

			//        	this.contentBufBuf = (String[]) vector.toArray();
		}
	}


	public void showGoogleResultONBrowser(){
		GoogleAnswer answer = new GoogleAnswer(this.getNodeinterface().getContent());
		answer.openAllWithBrowser();
	}
	public NodeComponent[] createGoogleAnswerChildrenOfThisNode(){
		GoogleAnswer answer = new GoogleAnswer(this.getNodeinterface().getContent());
		NodeComponent[] components = new NodeComponent[answer.getAnswers().length];
		if(answer.getAnswers().length>0){
			for(int i= 0;i < answer.getAnswers().length;i++){
				components[i] = this.createNewChild();
				components[i].setText(answer.getAnswers()[i]);
			}
		}
		return components;
	}
	public String[] decodeFilename(String[] filenames){
		Arrays.sort(filenames);
		for(int i = 0;i < filenames.length;i++){
			String filename = filenames[i];
			if(filename.endsWith(SimpleStringConstants.FILE_POSTFIX)){
				filenames[i] = "[["+filename.substring(0,filename.length()-3)+"]]";
			}
		}
		return filenames;
	}
	public void createWikiLinkComponents(){
		for (Enumeration en  = this.wikilinks.elements(); en.hasMoreElements();) {
			this.removeWikiLinkComponent((WikiLinkComponent)en.nextElement());
		}

		for(int i = 0;i < this.contentBufBuf.length;i++){
			this.createWikiLinkComponent(this.contentBufBuf[i], 10, i*this.fm.getHeight() + 4);
		}

	}
	public void  createWikiLinkComponent(String str,int x, int y){
		this.re = new RE(this.bracketName);
		if(this.re.match(str)){
			int wikix =x+this.fm.stringWidth(str.substring(0,re.getParenStart(0))) + 1;//plus 1 because too much of left 
			int wikiy = y -2; 
			int wikiw = +this.fm.stringWidth(re.getParen(1));
			int wikih = 14;
			WikiLinkComponent wlc = new WikiLinkComponent(re.getParen(1),this.observer);
			wlc.setLocation(wikix,wikiy);
			wlc.setSize(wikiw,wikih);
			if(this.isTransparent()){
				wlc.setTransparent(true);
			}else{
				wlc.setTransparent(false);
			}
			this.addWikiLinkComponent(wlc);
			this.repaint();
			wlc.repaint();
			int end0 = re.getParenEnd(0);
			this.createWikiLinkComponent(str.substring(end0),x+this.fm.stringWidth(str.substring(0,end0)) - 5,y);
		}
	}
	public void paintLineWithBracketName(String str,Graphics g,int x,int y){
		if(str !=null){
			if(this.re.match(str)){
				g.drawString(str.substring(0,re.getParenStart(0)),x ,y);
				//			g.drawLine(wikix,wikiy,wikix+wikiw,wikiy+wikih);
				//			g.drawString(re.getParen(1),x+this.fm.stringWidth(str.substring(0,re.getParenStart(0))),y);
				this.paintLineWithBracketName(str.substring(re.getParenEnd(0)),g,x+this.fm.stringWidth(str.substring(0,this.re.getParenEnd(0))) - 10 ,y);
			}else{
				g.drawString(str,x,y);
			}
		}
	}


	//craete museigen child!
	private void createMuseigenChild(){
		NodeComponent nc = this.createNewChild();
		nc.setText("&museigenbox");
	}
	public NodeComponent createNewParent(){
		int xRandom = ((int)(Math.random() * 250));
		int yRandom = (int)(Math.random() * 30);
		int yoffset = 20;
		int xoffset = 55;

		int xToBeAdded =     	this.getNodeinterface().getParents().size()==0?0:xRandom-xoffset;
		NodeComponent nc = this.getObserver().makeNewNornalNodeAt(this.getX()+xToBeAdded,this.getY() - yoffset - yRandom);
		nc.makeConnection(this);
		this.getObserver().setMode("normal",null);
		nc.requestFocusInWindow();
		return nc;
	}
	public NodeComponent createNewChild(){
		Enumeration enumeration = this.getChildren().elements();
		int xRandom  = 0;
		int yRandom  = 0;
		int addedx = 0;
		boolean hassamexchild=false;
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			if(nodeComponent.getX() ==this.getX()){
				hassamexchild = true;
				break;
			}
		}
		if(hassamexchild){
			xRandom = (int)(Math.random() * 150);
			addedx = xRandom-75;
			yRandom = (int)(Math.random() * 30);
		}
		NodeComponent nc = this.getObserver().makeNewNornalNodeAt(this.getX() + addedx,this.getY() + this.getHeight() + 30 - yRandom);
		enumeration = this.getChildren().elements();
		int span = 50;
		boolean nodesnear = false;
		while (enumeration.hasMoreElements()) {
			NodeComponent nodeComponent = (NodeComponent) enumeration.nextElement();
			if( ( nodeComponent.getX() - nc.getX() ) < span
					|| ( nc.getX() -  nodeComponent.getX() ) < span
					|| ( nc.getY() - nodeComponent.getY() ) < span
					|| ( nodeComponent.getY() - nc.getY() ) < span){
				nodesnear = true;
				break;
			}
		}
		if(nodesnear){
			nc.moveLocation(-100 + xRandom * 2, -10 + yRandom * 2);
		}
		this.makeConnection(nc);
		this.getObserver().setMode("normal",null);
		nc.requestFocusInWindow();
		return nc;
	}
	public NodeComponent createNewFriendsRightSide(){
		if(this.getParents().size()==1){
			NodeComponent parentNC = (NodeComponent)this.getParents().elements().nextElement();
			int xRandom = RandomUtils.nextInt() % 50;
			int yRandom = RandomUtils.nextInt() % 30;
			NodeComponent nc = this.getObserver().makeNewNornalNodeAt(this.getX() + 50 + xRandom,this.getY() + yRandom - 15);
			parentNC.makeConnection(nc);
			this.moveLocation( -20,0);
			this.getObserver().setMode("normal",null);
			nc.requestFocusInWindow();
			return nc;
		}
		return null;
	}
	public NodeComponent createNewFriendsLeftSide(){
		if(this.getParents().size()==1){
			NodeComponent parentNC = (NodeComponent)this.getParents().elements().nextElement();
			int xRandom = RandomUtils.nextInt() % 150;
			int yRandom = RandomUtils.nextInt() % 30;
			NodeComponent nc = this.getObserver().makeNewNornalNodeAt(this.getX() - 50 -  xRandom,this.getY() + yRandom - 15);
			parentNC.makeConnection(nc);
			this.moveLocation(15,0);
			this.getObserver().setMode("normal",null);
			nc.requestFocusInWindow();
			return nc;
		}
		return null;
	}
	private void goToParentNode(){
		if(this.isFocusOwner() && this.getParents().size() > 0){
			NodeComponent parentNC = (NodeComponent)this.getParents().elements().nextElement();
			parentNC.observer.disselectAllNodes(this.getObserver().getNode_components());
			parentNC.selected();
			parentNC.requestFocusInWindow();
			//            this.observer.getNodeFieldApplet().repaint();
		}
	}
	private void goToChildNode(){
		if(this.isFocusOwner() && this.getChildren().size() >0){
			NodeComponent childNC = (NodeComponent)this.getChildren().elements().nextElement();
			this.getObserver().disselectAllNodes(this.getObserver().getNode_components());
			childNC.selected();
			childNC.requestFocusInWindow();
			//            this.observer.getNodeFieldApplet().repaint();
		}
	}
	private void goToFriendLeftSide(){
		int distance = Integer.MAX_VALUE;
		NodeComponent nearlest = null;
		if(this.isFocusOwner()){
			if(this.getParents().size() > 0){
				if(this.getParents().size() ==1){
					NodeComponent nc = (NodeComponent)this.getParents().elements().nextElement();
					for(Enumeration en = nc.getChildren().elements();en.hasMoreElements();){
						NodeComponent temp = (NodeComponent)en.nextElement();
						if(this.getX() - temp.getX() > 0 && Math.abs((this.getX() - temp.getX())) < distance){
							distance  = this.getX() -  temp.getX();
							nearlest = temp;
						}
					}
				}
			}
			else if(getChildren().size() >0){
				if(this.getChildren().size()==1){
					NodeComponent nc = (NodeComponent)this.getChildren().elements().nextElement();
					for(Enumeration en = nc.getParents().elements();en.hasMoreElements();){
						NodeComponent temp = (NodeComponent)en.nextElement();
						if(this.getX()-temp.getX() > 0 && (this.getX() - temp.getX()< distance)){
							distance = this.getX() - temp.getX();
							nearlest = temp;
						}
					}
				}

			}
			if(nearlest!=null){
				nearlest.observer.disselectAllNodes(this.observer.getNode_components());
				nearlest.selected();
				nearlest.requestFocusInWindow();
				//        		this.observer.getNodeFieldApplet().repaint();
			}
		}
	}
	private void goToFriendRightSide(){
		int distance = Integer.MAX_VALUE;
		NodeComponent nearlest = null;
		if(this.isFocusOwner()){
			if(this.getParents().size() > 0){
				if(this.getParents().size() ==1){
					NodeComponent nc = (NodeComponent)this.getParents().elements().nextElement();
					for(Enumeration en = nc.getChildren().elements();en.hasMoreElements();){
						NodeComponent temp = (NodeComponent)en.nextElement();
						if(temp.getX() - this.getX() > 0 &&Math.abs((temp.getX() - this.getX())) < distance){
							distance  = temp.getX() -  this.getX();
							nearlest = temp;
						}
					}
				}
			}
			else if(getChildren().size() >0){
				if(this.getChildren().size()==1){
					NodeComponent nc = (NodeComponent)this.getChildren().elements().nextElement();
					for(Enumeration en = nc.getParents().elements();en.hasMoreElements();){
						NodeComponent temp = (NodeComponent)en.nextElement();
						if(temp.getX()-this.getX() > 0 && (temp.getX() - this.getX()> distance)){
							distance = temp.getX() - this.getX();
							nearlest = temp;
						}
					}
				}

			}
			if(nearlest!=null){
				nearlest.observer.disselectAllNodes(this.observer.getNode_components());
				nearlest.selected();
				nearlest.requestFocusInWindow();
				//        		this.observer.getNodeFieldApplet().repaint();
			}
		}
	}

	public String replateNodefileStringToLink(String string){
		if(string.length()==0){
			return "";
		}

		for (int i = 0; i < this.observer.nodeFiles.length; i++) {

			String file =  this.observer.nodeFiles[i];
			string = autolink(string, file);
			if(file.contains("_")){
				file = file.replaceAll("_", " ");
				string = autolink(string, file);
			}
		}
		return string;
	}

		private String autolink(String string, String file) {
			int index = 0;
			while(index >=0){
				if(string.length() >= file.length()){
					index =  string.indexOf(file,index);
					if(index >= 0 ){
						int indexoflastbraketstart = string.lastIndexOf("[[",index);
						int indexoflastbraketend = string.lastIndexOf("]]",index);
						int indexofnextbraketstart = string.indexOf("[[",index);
						int indexofnextbraketend = string.indexOf("]]",index);
						if( indexoflastbraketstart <=  indexoflastbraketend){

							String before = string.substring(0,index);
							String replaced = string.substring(index ,file.length() + index );
							String after = string.substring(index + file.length());
							//    						if(!after.startsWith("]]") && !before.endsWith("[[")){
							//        						if( ! before.endsWith("[[")  && !after.startsWith("]]")){
							StringBuffer buffer =new StringBuffer();
							buffer.append(before);
							try {
								buffer.append(replaced.replaceAll(file,"[["+ file + "]]"));
							} catch (Exception e) {
								buffer.append(replaced);
								e.printStackTrace();
							}
							buffer.append(after);
							string = buffer.toString();
							index = index + file.length() + 4;
						}else{
							index++;
						}
					}else{
						index  = 0;
						break;
					}
				}else{
					break;
				}
			}
			return string;
		}

		public void setText(String text){
			System.err.println(text);
			if(text.endsWith("\r\n")){
				text = text.substring(text.lastIndexOf("\r\n"));
				System.err.println("both");
			}

			if(text.endsWith("\n")){
				int lastIndex = text.lastIndexOf("\n");
				text = text.substring(0,lastIndex);
				System.err.println(text);
			}
			if(text.startsWith("\n")){
				text = text.substring(1);
				System.err.println(text);
			}



			getNodeinterface().setContent(text);
			text = this.replateNodefileStringToLink(text);
			StringTokenizer st = new StringTokenizer(text, "\n");
			if(text != null && !(text.equals(""))){
				this.contentBuf= new String[st.countTokens()];
				int i = 0;
				this.saidai_length = 0;
				while (st.hasMoreTokens()) {
					this.contentBuf[i] = st.nextToken();

					i++;
				}

				if(this.contentBuf[0].startsWith("&")){
					this.handlePlugin(this.contentBuf[0].substring(1));
				}else{
					this.contentBufBuf = this.contentBuf;
				}
				for(int ii = 0;ii < this.contentBufBuf.length;ii++){
					if(this.saidai_length< fm.stringWidth(this.contentBufBuf[ii])){
						saidai_length = fm.stringWidth(this.contentBufBuf[ii]);
					}		
				}
				if(this.contentBufBuf[0]!=null && (this.contentBufBuf[0].startsWith("@"))){
					//		    	if(this.contentBufBuf[0]!=null && (this.contentBufBuf[0].startsWith("@wait") ||  this.contentBufBuf[0].startsWith("@done") || this.contentBufBuf[0].startsWith("@cycle"))){
					this.setTransparent(true);
				}else{
					this.setTransparent(false);
				}
				this.createWikiLinkComponents();
			}else{
				this.contentBuf = null;
				this.contentBufBuf = null;
			}
			this.repaint();
		}

		public final boolean isSelected()
		{
			return getForeground().equals(this.getSelectedForegroundColor());
		}

		public void processMouseEvent(MouseEvent me)
		{

			if(me.getButton() == MouseEvent.BUTTON2){

			}else{
				switch(me.getID())
				{
				case 500: 
					requestFocus();
					processMouseClicked(me);
					break;

				case 501:
					requestFocus(); 
					for(Enumeration en = this.observer.getNode_components().elements();en.hasMoreElements();){
						((NodeComponent)en.nextElement()).returnFromEditing();
					}
					pressed = true;
					p = me.getPoint();
					break;

				case 502:
					requestFocus(); 
					pressed = false;
					if(this.isSelected()){
						for(Enumeration en = this.observer.getSelected().elements();en.hasMoreElements();){
							((NodeComponent)en.nextElement()).moveLocation(me.getX() - p.x, me.getY() - p.y);
						}
					}else{
						moveLocation(me.getX() - p.x, me.getY() - p.y);
					}
					break;
				}

			}
		}

		public boolean contains(int x, int y)
		{
			return x > 0 && x < getSize().width && y > -4 && y < getSize().height;
		}

		/**
		 * 
		 * @uml.property name="folded"
		 */
		public void setFolded(boolean flag) {
			folded = flag;
		}

		/**
		 * 
		 * @uml.property name="folded"
		 */
		public boolean getFolded() {
			return folded;
		}


		public void processMouseClicked(MouseEvent me)
		{

			if(me.getClickCount() ==2){
				this.goEditing();
				return;
			} 
			if(me.isControlDown()){

				if(isSelected()){
					if(me.isAltDown()){
						new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
					}	
					disselected();

				}else{
					selected();
					if(me.isAltDown()){
						new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
					}
				}
			}        else        if(me.isAltDown()){
				processMouseAltAndClicked(me);
			}        else{
				processMouseNormallyClicked(me);
			}
		}

		public void processEditing()
		{
			dirty = true;
			setVisible(false);
			edit = new NodeEditer(this);
			getParent().add(edit);
			getParent().repaint();
			edit.requestFocusInWindow();
		}

		public void returnFromEditing()
		{
			if(this.editing){
				setVisible(true);
				setEditing(false);
				this.setText(edit.getText());
				if(observer != null)
					observer.setMode("return_from_editing", this);
				getParent().remove(edit);
				edit = null;
				invalidate();
				repaint();
				this.dirty=true;
			}
		}

		public void processMouseAltAndClicked(MouseEvent me)
		{
			if(this.getFolded() == false){
				goFolded();
			}else{
				goUnfolded();
			}
		}

		public void setParents(Hashtable hash)
		{
			parents = hash;
		} 

		public Hashtable getParents()
		{
			return parents;
		}

		public Dimension getMinimumSize()
		{
			return new Dimension(80, 15);
		}

		public Dimension getPreferredSize()
		{
			if(this.contentBufBuf == null || this.ni.getContent().equals("")){
				this.setSize(new Dimension(80, 20));
				return this.getSize();
			}else{
				if(this.getObserver().getNodeFieldApplet().isIdshow()){
					this.setSize(new Dimension(this.saidai_length +35, this.contentBufBuf.length * this.getFm().getHeight()+15));
				}else{
					this.setSize(new Dimension(this.saidai_length + 28, this.contentBufBuf.length * this.getFm().getHeight() + 18));
				}
				return this.getSize();
			}

		}

		/**
		 * 
		 * @uml.property name="children_line"
		 */
		public void setChildren_line(Hashtable hash) {
			children_line = hash;
		}

		/**
		 * 
		 * @uml.property name="children_line"
		 */
		public Hashtable getChildren_line() {
			return children_line;
		}


		public void processMouseNormallyClicked(MouseEvent me)
		{
			if(isSelected()){
				this.observer.disselectAllNodes(this.observer.getNode_components());
			}
			else{
				this.observer.disselectAllNodes(this.observer.getNode_components());
				selected();
			}


		}

		public void makeConnection(NodeComponent theother)
		{
			if(theother.getNodeinterface().getId().equals(getNodeinterface().getId()) || getChildren().get(theother.getNodeinterface().getId()) != null)
			{
				observer.setMode("normal", null);
			} else
			{
				dirty = true;
				children.put(theother.getNodeinterface().getId(), theother);
				theother.getParents().put(getNodeinterface().getId(), this);
				Line line = new Line(this, theother);
				getParent().add(line);
				children_line.put((NodeComponent)children.get(theother.getNodeinterface().getId()), line);
			}
		}

		/**
		 * 
		 * @uml.property name="ni"
		 * @uml.associationEnd 
		 * @uml.property name="ni" multiplicity="(1 1)"
		 */
		private NodeInterface ni;

		/**
		 * 
		 * @uml.property name="observer"
		 * @uml.associationEnd 
		 * @uml.property name="observer" multiplicity="(0 1)" inverse="selected:enclosing.application.node.NodeObserver"
		 */
		private NodeObserver observer;

		/**
		 * 
		 * @uml.property name="outlet"
		 * @uml.associationEnd 
		 * @uml.property name="outlet" multiplicity="(0 1)" inverse="nc:enclosing.application.node.NodeOutlet"
		 */
		private NodeOutlet outlet;

		/**
		 * 
		 * @uml.property name="inlet"
		 * @uml.associationEnd 
		 * @uml.property name="inlet" multiplicity="(0 1)" inverse="nc:enclosing.application.node.NodeInlet"
		 */
		private NodeInlet inlet;

		/**
		 * 
		 * @uml.property name="children_line"
		 * @uml.associationEnd 
		 * @uml.property name="children_line" multiplicity="(0 1)" inverse="child:enclosing.application.node.Line"
		 * qualifier="this:enclosing.application.node.NodeComponent line:enclosing.application.node.Line"
		 */
		private Hashtable children_line;

		/**
		 * 
		 * @uml.property name="children"
		 * @uml.associationEnd 
		 * @uml.property name="children" multiplicity="(0 1)" inverse="children:enclosing.application.node.NodeComponent"
		 * qualifier="getId:java.lang.String temp:enclosing.application.node.NodeComponent"
		 */
		private Hashtable children;

		/**
		 * 
		 * @uml.property name="parents"
		 * @uml.associationEnd 
		 * @uml.property name="parents" multiplicity="(0 -1)" ordering="ordered" elementType=
		 * "java.lang.String" qualifier="getId:enclosing.application.node.NodeComponent nc:enclosing.application.node.Line"
		 */
		private Hashtable parents;

		/**
		 * 
		 * @uml.property name="fm"
		 */
		private FontMetrics fm;

		/**
		 * 
		 * @uml.property name="editing"
		 */
		private boolean editing;

		/**
		 * 
		 * @uml.property name="folded"
		 */
		private boolean folded;


		/**
		 * 
		 * @uml.property name="edit"
		 * @uml.associationEnd 
		 * @uml.property name="edit" multiplicity="(1 1)" inverse="nc:enclosing.application.node.NodeEditer"
		 */
		private NodeEditer edit;

		/**
		 * 
		 * @uml.property name="pressed"
		 */
		private boolean pressed;

		/**
		 * 
		 * @uml.property name="p"
		 */
		private Point p;

		//	//////////////////normal setting
		//	
		//	private Color selectedBackgroundColor = 	new Color(0,0,0,80);
		//	private Color selectedForegroundColor= new Color(255,255,255,150);
		//	
		//	private Color normalBackgroundColor =new Color(255,255,255,200); 	
		//	private Color normalForegroundColor = 		new Color(0,0,0,250);


		private Color selectedBackgroundColor = 	new Color(255,255,255,80);
		private Color selectedForegroundColor= new Color(0,0,0,150);

		private Color normalBackgroundColor =new Color(0,0,0,0); 	
		private Color normalForegroundColor = 		new Color(255,255,255,250);



		/**
		 * 
		 * @uml.property name="dirty"
		 */
		private boolean dirty;

		private int saidai_length = 0;
		private String[] contentBuf = null;

		/**
		 * 
		 * @uml.property name="re"
		 * @uml.associationEnd 
		 * @uml.property name="re" multiplicity="(0 1)"
		 */
		private org.apache.regexp.RE re = null;

		private String[] contentBufBuf = null;
		private static String bracketName = null;

		/**
		 * 
		 * @uml.property name="wikilinks"
		 * @uml.associationEnd 
		 * @uml.property name="wikilinks" multiplicity="(0 -1)" elementType="enclosing.application.node.wiki.WikiLinkComponent"
		 */
		private Vector wikilinks = new Vector();


		public void addWikiLinkComponent(WikiLinkComponent wlc){
			this.add(wlc);
			this.wikilinks.add(wlc);
		}
		public void removeWikiLinkComponent(WikiLinkComponent wlc){
			this.remove(wlc);
			this.wikilinks.remove(wlc);
		}

		/**
		 * @return
		 * 
		 * @uml.property name="fm"
		 */
		public FontMetrics getFm() {
			return fm;
		}

		/**
		 * @return
		 */
		public Vector getWikilinks() {
			return wikilinks;
		}

		/**
		 * @param vector
		 */
		public void setWikilinks(Vector vector) {
			wikilinks = vector;
		}

		/**
		 * @return
		 * 
		 * @uml.property name="pressed"
		 */
		public boolean isPressed() {
			return pressed;
		}

		/**
		 * @param b
		 * 
		 * @uml.property name="pressed"
		 */
		public void setPressed(boolean b) {
			pressed = b;
		}

		/**
		 * @return
		 * 
		 * @uml.property name="p"
		 */
		public Point getP() {
			return p;
		}

		/**
		 * @param point
		 * 
		 * @uml.property name="p"
		 */
		public void setP(Point point) {
			p = point;
		}

		/* (�� Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent ke) {
		}

		/* (�� Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent ke) {
			if(ke.getKeyCode()==127){//delete
				if(ke.isControlDown()){
					AddToGoogleCalendar addToGoogleCalendar 
					= new AddToGoogleCalendar(this.getNodeinterface());
					AddToTumbler addToTumbler = new AddToTumbler(this.getNodeinterface());
				}
				this.getObserver().setMode("delete",null);
			}
			if(ke.getKeyCode()==155){//insert
				this.createNewChild();
			}

			if(ke.isShiftDown()){//only shift is down
				if(ke.getKeyCode() == 38){
					this.moveLocation(0,-this.movespan);
					return;
				}
				if(ke.getKeyCode() == 40){
					this.moveLocation(0, this.movespan);
					return;
				}
				if(ke.getKeyCode() == 37){
					this.moveLocation( - this.movespan,0);
					return;
				}
				if(ke.getKeyCode() == 39){
					this.moveLocation(this.movespan,0);
					return;
				}
				if(ke.getKeyCode()==10) {
					for (Iterator iterator = this.getWikilinks().iterator(); iterator
							.hasNext();) {
						WikiLinkComponent wikiLinkComponent = (WikiLinkComponent) iterator.next();
						new AutoExpandOneStep(wikiLinkComponent.getBranketContent(), this,this.getObserver().getNode_components());
						//					this.observer.setMode_object(wikiLinkComponent);
						//					this.observer.setMode("WikiAutoExpand",this);
					}
				}
			}
			if(ke.isControlDown()){//ctrl is 
				if(ke.isShiftDown()){//ctrl + shift


				}else if(ke.isAltDown()){//ctrl + alt
					if(ke.getKeyCode() == KeyEvent.VK_C){
						// c for Conversion
						System.err.println("ctrl+alt+c");
						TextToNodes textToNodes = new TextToNodes(this,this.getObserver());
					}
					if(ke.getKeyCode() == KeyEvent.VK_T){
						AddToTumbler addToTumbler = new AddToTumbler(this.getNodeinterface());
					}
					if(ke.getKeyCode()==KeyEvent.VK_B){
						AddToBackpack addToBackpack = new AddToBackpack(this.getObserver().getFilename(),this);
					}


				}else{//only ctrl
					if(ke.getKeyCode()==38){//up
						this.createNewParent();
					}
					if(ke.getKeyCode()==40){//down
						this.createNewChild();
					}
					if(ke.getKeyCode()==37){//left
						this.createNewFriendsLeftSide();
					}
					if(ke.getKeyCode()==39){//right
						this.createNewFriendsRightSide();
					}
					if(ke.getKeyCode()==83){
						this.observer.setMode("save",this.getObserver().getNodeFieldApplet());
					}
					if(ke.getKeyCode()==KeyEvent.VK_M){
						this.createMuseigenChild();
					}
					if(ke.getKeyCode() == KeyEvent.VK_G){
						this.showGoogleResultONBrowser();
						//				    this.createGoogleAnswerChildrenOfThisNode();
					}
					if(ke.getKeyCode() == KeyEvent.VK_ENTER){
						new BreakNodeIntoNodesWithCRLF(this,this.getObserver());
					}

				}
			}else{//not ctrl
				if(ke.getKeyCode()==107){
					new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
				}
			}

			if(!ke.isAltDown() && !ke.isControlDown() && !ke.isMetaDown() && !ke.isShiftDown()){
				//change forcus to other nodes.
				if(ke.getKeyCode()==38){//up
					this.goToParentNode();
				}
				if(ke.getKeyCode()==40){//down
					this.goToChildNode();
				}
				if(ke.getKeyCode()==37){//left
					this.goToFriendLeftSide();
				}
				if(ke.getKeyCode()==39){//right
					this.goToFriendRightSide();
				}
			}


			if(ke.getKeyCode()==113){//f2
				this.goEditing();
			}
			if(ke.getKeyCode()==10 && !ke.isShiftDown()){//enter!
				this.goEditing();
			} 


			if(ke.isAltDown()){//only alt
				//	        new EnCauseNodesWithRelativeYPosision(this.getObserver().getSelected(),this.getObserver());
			}

			if(ke.getKeyCode()==KeyEvent.VK_K){
				String wikiname = this.getNodeinterface().getContent();

				//			this.getNodeinterface().setContent("[[" + this.getNodeinterface().getContent() + "]]");
				//			this.setText(this.getNodeinterface().getContent());
				this.getObserver().openFromWikiWileName(wikiname,false);
			}else if(ke.getKeyCode() == KeyEvent.VK_P){
				HttpBrowser browser = new HttpBrowser("http://localhost:8090/en/CashEventsFromNDaysBack.do?n=-20");
			}


			if(TagHash.getInstance().getTag(ke.getKeyCode()) !=null ){
				if(this.getNodeinterface().getContent().startsWith(TagHash.getInstance().getTag(ke.getKeyCode()))){
					this.getNodeinterface().setContent(this.getNodeinterface().getContent().substring(TagHash.getInstance().getTag(ke.getKeyCode()).length() + 1));
				}else{
					this.getNodeinterface().setContent(TagHash.getInstance().getTag(ke.getKeyCode()) + " " + this.getNodeinterface().getContent());
				}
				this.setText(this.getNodeinterface().getContent());
			}


		}

		/* (�� Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		public void keyTyped(KeyEvent ke){

		}	






		public Color getNormalBackgroundColor() {
			return normalBackgroundColor;
		}

		public void setNormalBackgroundColor(Color normalBackgroundColor) {
			this.normalBackgroundColor = normalBackgroundColor;
		}

		public Color getNormalForegroundColor() {
			return normalForegroundColor;
		}

		public void setNormalForegroundColor(Color normalForegroundColor) {
			this.normalForegroundColor = normalForegroundColor;
		}

		public Color getSelectedBackgroundColor() {
			return selectedBackgroundColor;
		}

		public void setSelectedBackgroundColor(Color selectedBackgroundColor) {
			this.selectedBackgroundColor = selectedBackgroundColor;
		}

		public Color getSelectedForegroundColor() {
			return selectedForegroundColor;
		}

		public void setSelectedForegroundColor(Color selectedForegroundColor) {
			this.selectedForegroundColor = selectedForegroundColor;
		}

		public void setFm(FontMetrics fm) {
			this.fm = fm;
		}

		private boolean transparent = false;


		private int movespan = 30;


		public int getMovespan() {
			return movespan;
		}

		public void setMovespan(int movespan) {
			this.movespan = movespan;
		}

		public boolean isTransparent() {
			return transparent;
		}

		public void setTransparent(boolean transparent) {
			this.transparent = transparent;
		}
		private boolean hoist = false;


		public boolean isHoist() {
			return hoist;
		}

		public void setHoist(boolean hoist) {
			this.hoist = hoist;
		}
		private boolean terminal;


		public boolean isTerminal() {
			return terminal;
		}

		public void setTerminal(boolean terminal) {
			this.terminal = terminal;
		}

		private Color getColorFromCssColor(String csscolorstring ){
			String rstring = csscolorstring.substring(1,3);
			String gstring = csscolorstring.substring(3,5);
			String bstring = csscolorstring.substring(5);
			int r = Integer.parseInt(rstring,16);
			int g = Integer.parseInt(gstring,16);
			int b = Integer.parseInt(bstring,16);
			return new Color(r,g,b);
		}


	}

