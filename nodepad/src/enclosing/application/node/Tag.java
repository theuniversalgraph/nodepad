package enclosing.application.node;

public class Tag {
	int keyCode;
	String tag;
	public Tag(int keyCode,String tag){
		this.keyCode = keyCode;
		this.tag = tag;
	}
		

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
	

}
