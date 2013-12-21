/**
 *
 */
package org.howsun.util;

/**
 * @author howsun
 *
 */
public abstract class Regexs {

	/**邮箱正则**/
	/*
	 * 有Bug，超过40个字符会发生死循环
	 * public static final String REGEX_EMAIL  = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	 */
	public static final String REGEX_EMAIL  = "^[\\w\\.]+@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

	/**手机正则**/
	public static final String REGEX_MOBILE = "^(13[0-9]|15[0,8,9,1,7]|188|187|186)\\d{8}$";


}
