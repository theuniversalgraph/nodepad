/*
 */
package enclosing.awt;

import java.awt.Container;

/**
 * @author Administrator
 *
 */
public class YesNoCancelDialog extends FlatDialog {
	public YesNoCancelDialog(Container parent){
		super("foo,bar",new String[]{"yes","no","cancel"},parent);

	}
}
