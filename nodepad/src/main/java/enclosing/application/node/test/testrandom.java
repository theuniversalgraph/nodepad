/*
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package enclosing.application.node.test;

import java.util.Random;

/**
 * @author Administrator
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class testrandom {

	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		for(int i =0;i < 10;i++)
		System.err.println(random.nextInt());
		System.err.println("aiueo");
		for(int i = 0;i < 10;i++){
			System.err.println(((int)(Math.random()*800)));
			
		}
	}
}
