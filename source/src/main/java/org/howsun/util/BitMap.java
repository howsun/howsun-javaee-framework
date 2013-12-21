
package org.howsun.util;

/**
 * 功能描述：
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class BitMap{
	private static final byte MAX = 127;  

	public static void main(String[] args) throws InterruptedException {  
		int m = 1578015112 ;  
		BitMap hm = new BitMap(12500000) ;  
		for(int i = 0; i < 12500; i++) {
			hm.add(i) ;  
		}
		System.out.println(hm.contains(Integer.MAX_VALUE));  
	}  

	public BitMap() {  
		bytes = new byte[12500000];  
	}  

	public BitMap(int size) {  
		bytes = new byte[size];  
	}  

	private byte[] bytes = null;  

	public void add(int i) {  
		int r = i / 8;  
		int c = i % 8;  
		bytes[r] = (byte) (bytes[r] | (1 << c));  
	}  

	public boolean contains(int i) {  
		int r = i / 8;  
		int c = i % 8;  
		if (((byte) ((bytes[r] >>> c)) & 1) == 1) {  
			return true;  
		}  
		return false;  
	}  

	public void remove(int i) {  
		int r = i / 8;  
		int c = i % 8;  
		bytes[r] = (byte) (bytes[r] & (((1 << (c + 1)) - 1) ^ MAX));  
	}  
	
}
