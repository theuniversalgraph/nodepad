package enclosing.application.node.skin;

import java.awt.Color;

public interface NodepadSkin {
	public Color getInletnormal();
	public Color getInletselected();
	public Color getInlettransparent();
	public Color getNcnormalback() ;
	public Color getNcnormalfore();
	public Color getNcselectedback() ;
	public Color getNcselectedfore() ;
	public Color getNcshadow();
	public Color getNctransparentback() ;
	public Color getNctransparentfore() ;
	public Color getNctransparentshadow() ;
	public Color getNodefield() ;
	public Color getOutletnormal() ;
	public Color getOutletselected();
	public Color getOutlettransparent() ;
	public Color getAuxLineColor() ;
	public Color getWikiLinkColor();
	public Color getWikiLinkBackColor();
	public Color getWikiLinkLineColor();
	public void morecontrast();	
	public void lesscontrast();
	public Color getLineColor();
	public Color getWikiLinkTransparentForColor();
	public Color getGraduationTopColor();
}
