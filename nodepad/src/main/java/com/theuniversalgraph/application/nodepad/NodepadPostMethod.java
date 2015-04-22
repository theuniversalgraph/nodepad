package com.theuniversalgraph.application.nodepad;

import org.apache.commons.httpclient.methods.PostMethod;

public class NodepadPostMethod  extends PostMethod{
	public NodepadPostMethod(String uri){
        super(uri);
    }

    public String getRequestCharSet() {
        return "utf-8";
    }

	

}
