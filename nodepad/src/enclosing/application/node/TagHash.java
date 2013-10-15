package enclosing.application.node;

import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class TagHash {
	private static final TagHash tagHash =new TagHash();
	private Hashtable hash1 = new Hashtable();
	private Hashtable hash2 = new Hashtable();
	private TagHash(){
		
		hash1.put(new Integer(KeyEvent.VK_A),"@Air");
		hash2.put("@Air",new Integer(KeyEvent.VK_A));

		hash1.put(new Integer(KeyEvent.VK_C),"@cycle");
		hash2.put("@cycle",new Integer(KeyEvent.VK_C));
		
		hash1.put(new Integer(KeyEvent.VK_D),"@done");
		hash2.put("@done",new Integer(KeyEvent.VK_D));

		
		hash1.put(new Integer(KeyEvent.VK_E),"@eclipse");
		hash2.put("@Eclipse",new Integer(KeyEvent.VK_E));
		
//		hash1.put(new Integer(KeyEvent.VK_K),"@keyword");
//		hash2.put("@keyword",new Integer(KeyEvent.VK_K));
		
		hash1.put(new Integer(KeyEvent.VK_I),"@iPhone");
		hash2.put("@iPhone",new Integer(KeyEvent.VK_I));
		
		hash1.put(new Integer(KeyEvent.VK_G),"@google");
		hash2.put("@google",new Integer(KeyEvent.VK_G));
		
		hash1.put(new Integer(KeyEvent.VK_H),"@home");
		hash2.put("@home",new Integer(KeyEvent.VK_H));

		hash1.put(new Integer(KeyEvent.VK_M),"@mece");
		hash2.put("@mece",new Integer(KeyEvent.VK_M));

		hash1.put(new Integer(KeyEvent.VK_N),"@Noding");
		hash2.put("@Noding",new Integer(KeyEvent.VK_N));

		hash1.put(new Integer(KeyEvent.VK_O),"@Onthego");
		hash2.put("@Onthego",new Integer(KeyEvent.VK_O));

		hash1.put(new Integer(KeyEvent.VK_P),"@purchase");
		hash2.put("@purchase",new Integer(KeyEvent.VK_P));

		hash1.put(new Integer(KeyEvent.VK_S),"@secretary");
		hash2.put("@secretary",new Integer(KeyEvent.VK_S));
		
		hash1.put(new Integer(KeyEvent.VK_T),"@time");
		hash2.put("@time",new Integer(KeyEvent.VK_T));

		hash1.put(new Integer(KeyEvent.VK_W),"@wait");
		hash2.put("@wait",new Integer(KeyEvent.VK_W));
	}
	public int getKeyFromTag(String tag){
		return ((Integer)hash2.get(tag)).intValue();
	}
	public String getTag(int keyCode){
		return (String)hash1.get(new Integer(keyCode));
	}
	public static TagHash getInstance(){
		return tagHash;
	}

}
