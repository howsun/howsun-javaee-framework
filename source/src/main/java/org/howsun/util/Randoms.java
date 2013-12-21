package org.howsun.util;

import java.util.Random;

import org.howsun.core.exception.ExceptionUtils;


/**
 * 随机数封装
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-25
 * @version v0.1
 */
public abstract class Randoms {

	private static final char[] src = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	public static final Random random = new Random();

	/**
	 * 随机整数
	 * @return
	 */
	public static int nextInt() {
		return (random.nextInt() & 0x7fffffff) | 0x1000100;
	}

	/**
	 * 0-max之间的随机整数
	 * @param max
	 * @return
	 */
	public static int nextInt(int max) {
		return random.nextInt(max);
	}

	/**
	 * min-max之间的随机整数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	public static int[] subRandom(int min, int max, int subLength){
		int size = max - min + 1;
		Asserts.isTrue(size > 0);

		int[] randoms = new int[size];

		for(int i = 0; i < size; i++){
			randoms[i] = min + i;
		}

		if(size <= subLength){
			return randoms;
		}

		int temp1, temp2, len = randoms.length;

		int subRandoms[] = new int[subLength];

		for(int i = 0; i < subLength; i++)  {
			temp1 = Math.abs(nextInt()) % len;
			subRandoms[i] = randoms[temp1];
			temp2 = randoms[temp1];
			randoms[temp1] = randoms[len-1];
			randoms[len-1] = temp2;
			len--;
		}
		return subRandoms;
	}

	/**
	 * 1-9,a-Z之间的随机字符
	 * @return char
	 */
	public static char nextCharacter() {
		return src[Math.abs(random.nextInt(src.length))];
	}

	/**
	 * max长度的随机字符数
	 * @param max
	 * @return String
	 */
	public static String nextString(int max){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < max; i++) {
			result.append(nextCharacter());
		}
		return result.toString();
	}

	/**
	 * 长度min-max之间的字符
	 * @param min
	 * @param max
	 * @return String
	 */
	public static String nextString(int min, int max) {
		StringBuffer result = new StringBuffer(nextString(min));
		for (int i = 0; i < nextInt(max-min); i++) {
			result.append(nextCharacter());
		}
		return result.toString();
	}

	public static void main(String[] args) {
		try {
			if(args.length == 0){
				throw ExceptionUtils.makeThrow("Not is empty '%s'", "param of 'args'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(nextString(5));
		}
	}

}
