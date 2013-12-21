/**
 *
 */
package org.howsun.util;

import org.junit.Test;

/**
 * @author howsun
 *
 */
public class RandomsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int j = 0; j < 10; j++) {
			int[] r = Randoms.subRandom(0, 1, 4);
			for (int i = 0; i < r.length; i++) {
				System.out.print(r[i] + "\t");
			}
			System.out.println("lenght:"+r.length);
		}
	}

	@Test
	public void testSubRandom_Int_Int(){
		for (int j = 0; j < 10; j++) {
			int[] r = Randoms.subRandom(0, 5, 4);
			for (int i = 0; i < r.length; i++) {
				System.out.print(r[i] + "\t");
			}
			System.out.println("lenght:"+r.length);
		}
	}

	@Test
	public void testRandom_Int_Int(){
		for (int j = 1; j < 50; j++) {
			System.out.println(Randoms.random(1, 5));
		}
	}


}
