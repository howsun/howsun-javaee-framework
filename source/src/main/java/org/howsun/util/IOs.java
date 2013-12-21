/**
 * 
 */
package org.howsun.util;

import java.io.Closeable;

/**
 * @author howsun
 *
 */
public abstract class IOs {

	
	/**
	 * 
	 * @param closeable
	 */
	public static void close(Closeable... closeable){
		for (Closeable c : closeable) {
			if(c != null){
				try {c.close();} catch (Exception e) {e.printStackTrace();}
			}
		}
	}
	
	
	/**
	 * @param args{
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
