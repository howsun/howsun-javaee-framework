package org.howsun.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作的帮助函数
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-25
 * @version v0.1
 */
public abstract class Strings {

	//(http|ftp|https)://[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:/~+#-]*[\w@?^=%&amp;/~+#-])?
	//var urlPattern = new Regexp("(http|ftp|https)://[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:/~+#-]*[\w@?^=%&amp;/~+#-])?")
	//var urlPattern = /(http|ftp|https):\/\/[\w-]+(\.[\w-]+)+([\w.,@?^=%&amp;:\/~+#-]*[\w@?^=%&amp;\/~+#-])?/

	public static final String FOLDER_SEPARATOR = "/";

	public static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	public static final String TOP_PATH = "..";

	public static final String CURRENT_PATH = ".";

	public static final char EXTENSION_SEPARATOR = '.';

	//private static final char CHAR_SPACE = '\u0020';

	//private static final char CHAR_CHINESE_SPACE = '\u3000';

	/**
	 * 检查包含空白字符在内的字符系列长度
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 检查包含空白字符在内的字符系列长度
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not null and has length
	 * @see #hasLength(CharSequence)
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * 检查字符串过滤前后空白后的长度
	 * @param str
	 * @return boolean
	 */
	public static boolean hasLengthAfterTrimWhiteSpace(String str) {
		return str != null && str.trim().length() > 0;
	}
	public static boolean hasLengthBytrim(String str){
		return hasLengthAfterTrimWhiteSpace(str);
	}

	/**
	 * Check whether the given CharSequence has actual text.
	 * More specifically, returns <code>true</code> if the string not <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * <p><pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 * its length is greater than 0, and it does not contain whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String has actual text.
	 * More specifically, returns <code>true</code> if the string not <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * @param str the String to check (may be <code>null</code>)
	 * @return <code>true</code> if the String is not <code>null</code>, its length is
	 * greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	public static int length(String str){
		return hasLength(str) ? str.length() : 0;
	}
	/**
	 * 根据匹配串是提取占位符中的值
	 *
	 * @param url
	 *            原字符串
	 * @param ref
	 *            匹配串
	 * @param placeholder
	 *            占位符
	 * @return 占位符中的值
	 *
	 * 示例: String ref = "list_*.html"; String url = "xxx/list_2578.html?a=1"; System.out.println(getParamValueInPlaceholder(url, ref, "\\*"));
	 */
	public static String getParamValueInPlaceholder(String url, String ref, String placeholder) {
		String result = null;
		try {
			String[] refs = ref.split(placeholder);
			String reg = "^.*" + refs[0] + "(.+?)";
			if (refs.length > 1)
				reg = "^.*" + refs[0] + "(.+?)" + refs[1] + ".*";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(url);
			if (m.matches()) {
				result = m.group(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getPointBefore(String str) {
		try {
			return str.split("\\.")[0];
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 截串
	 *
	 * @param source
	 * @param length
	 * @param fill
	 * @return
	 */
	public static String getStringByLength(String source, int length, String fill) {
		if (source == null)
			return "";
		if (source.length() < length + 1)
			return source;
		return source.substring(0, length) + fill;
	}

	/**
	 * getter方法名
	 * @param fieldName
	 * @return
	 */
	public static String getterName(String fieldName) {
		if (fieldName != null && !"".equals(fieldName)) {
			fieldName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return fieldName;
	}

	/**
	 * setter方法名
	 * @param fieldName
	 * @return
	 */
	public static String setterName(String fieldName) {
		if (fieldName != null && !"".equals(fieldName)) {
			fieldName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
		return fieldName;
	}

	/**
	 * 通过getter&setter方法名取得字段名
	 * @param getterMethodName
	 * @return
	 */
	public static String fieldName(String getterMethodName) {
		if(hasLengthAfterTrimWhiteSpace(getterMethodName) && getterMethodName.startsWith("get")){
			return getterMethodName.substring(3, 4).toLowerCase() + getterMethodName.substring(4);
		}
		return getterMethodName;
	}

	/**
	 * 转换到Josn格式
	 * @param s
	 * @return
	 */
	public static String string2Json(String s) {
	    StringBuilder sb = new StringBuilder(s.length() + 20);
	    sb.append('\"');
	    for (int i=0; i<s.length(); i++) {
	        char c = s.charAt(i);
	        switch (c) {
	        case ',':
	            sb.append("，");
	            break;
	        case '\"':
	            sb.append("\\\"");
	            break;
	        case '\\':
	            sb.append("\\\\");
	            break;
	        case '/':
	            sb.append("\\/");
	            break;
	        case '\b':
	            sb.append("\\b");
	            break;
	        case '\f':
	            sb.append("\\f");
	            break;
	        case '\n':
	            sb.append("\\n");
	            break;
	        case '\r':
	            sb.append("\\r");
	            break;
	        case '\t':
	            sb.append("\\t");
	            break;
	        default:
	            sb.append(c);
	        }
	    }
	    sb.append('\"');
	    return sb.toString();
	 }



	/**
	 * 把null转化为空字符串
	 */
	public static String toString(String str) {
		if (str == null)
			return "";
		if (str.equals("null"))
			return "";
		if (str.length() == 0)
			return "";
		return str.trim();
	}

	/**
	 * 复制字符串
	 *
	 * @param cs 字符串
	 * @param num 数量
	 * @return 新字符串
	 */
	public static String dup(CharSequence cs, int num) {
		if (isEmpty(cs) || num <= 0)
			return "";
		StringBuilder sb = new StringBuilder(cs.length() * num);
		for (int i = 0; i < num; i++)
			sb.append(cs);
		return sb.toString();
	}

	/**
	 * 复制字符
	 *
	 * @param c
	 *            字符
	 * @param num
	 *            数量
	 * @return 新字符串
	 */
	public static String dup(char c, int num) {
		if (c == 0 || num < 1)
			return "";
		StringBuilder sb = new StringBuilder(num);
		for (int i = 0; i < num; i++)
			sb.append(c);
		return sb.toString();
	}

	/**
	 * 将字符串首字母大写
	 * @param s 字符串
	 * @return 首字母大写后的新字符串
	 */
	public static String capitalize(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char char0 = s.charAt(0);
		if (Character.isUpperCase(char0))
			return s.toString();
		return new StringBuilder(len).append(Character.toUpperCase(char0))
										.append(s.subSequence(1, len))
										.toString();
	}

	/**
	 * 将字符串首字母小写
	 * @param s 字符串
	 * @return 首字母小写后的新字符串
	 */
	public static String lowerFirst(CharSequence s) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == 0)
			return "";
		char c = s.charAt(0);
		if (Character.isLowerCase(c))
			return s.toString();
		return new StringBuilder(len).append(Character.toLowerCase(c))
										.append(s.subSequence(1, len))
										.toString();
	}

	/**
	 * 检查两个字符串的忽略大小写后是否相等.
	 *
	 * @param s1 字符串A
	 * @param s2 字符串B
	 * @return true 如果两个字符串忽略大小写后相等,且两个字符串均不为null
	 */
	public static boolean equalsIgnoreCase(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
	}

	/**
	 * 检查两个字符串是否相等.
	 *
	 * @param s1 字符串A
	 * @param s2 字符串B
	 * @return true 如果两个字符串相等,且两个字符串均不为null
	 */
	public static boolean equals(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equals(s2);
	}

	/**
	 * 判断字符串是否以特殊字符开头
	 *
	 * @param s 字符串
	 * @param c 特殊字符
	 * @return 是否以特殊字符开头
	 */
	public static boolean startsWithChar(String s, char c) {
		return null != s ? (s.length() == 0 ? false : s.charAt(0) == c) : false;
	}

	/**
	 * 判断字符串是否以特殊字符结尾
	 *
	 * @param s 字符串
	 * @param c 特殊字符
	 * @return 是否以特殊字符结尾
	 */
	public static boolean endsWithChar(String s, char c) {
		return null != s ? (s.length() == 0 ? false : s.charAt(s.length() - 1) == c) : false;
	}

	/**
	 * @param cs 字符串
	 * @return 是不是为空字符串
	 */
	public static boolean isEmpty(CharSequence cs) {
		return null == cs || cs.length() == 0;
	}

	/**
	 * @param cs 字符串
	 * @return 是不是为空白字符串
	 */
	public static boolean isBlank(CharSequence cs) {
		if (null == cs)
			return true;
		int length = cs.length();
		for (int i = 0; i < length; i++) {
			if (!(Character.isWhitespace(cs.charAt(i))))
				return false;
		}
		return true;
	}

	/**
	 * 去掉字符串前后空白
	 *
	 * @param cs 字符串
	 * @return 新字符串
	 */
	public static String trim(CharSequence cs) {
		if (null == cs)
			return null;
		if (cs instanceof String)
			return ((String) cs).trim();
		int length = cs.length();
		if (length == 0)
			return cs.toString();
		int l = 0;
		int last = length - 1;
		int r = last;
		for (; l < length; l++) {
			if (!Character.isWhitespace(cs.charAt(l)))
				break;
		}
		for (; r > l; r--) {
			if (!Character.isWhitespace(cs.charAt(r)))
				break;
		}
		if (l > r)
			return "";
		else if (l == 0 && r == last)
			return cs.toString();
		return cs.subSequence(l, r + 1).toString();
	}

	/**
	 * 将字符串按半角逗号，拆分成数组，空元素将被忽略
	 *
	 * @param s
	 *            字符串
	 * @return 字符串数组
	 */
	public static String[] splitIgnoreBlank(String s) {
		return Strings.splitIgnoreBlank(s, ",");
	}

	/**
	 * 根据一个正则式，将字符串拆分成数组，空元素将被忽略
	 * @param s 字符串
	 * @param regex 正则式
	 * @return 字符串数组
	 */
	public static String[] splitIgnoreBlank(String s, String regex) {
		if (null == s)
			return null;
		String[] ss = s.split(regex);
		List<String> list = new LinkedList<String>();
		for (String st : ss) {
			if (isBlank(st))
				continue;
			list.add(trim(st));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 将一个整数转换成最小长度为某一固定数值的十进制形式字符串
	 *
	 * @param d 整数
	 * @param width 宽度
	 * @return 新字符串
	 */
	public static String fillDigit(int d, int width) {
		return alignRight(String.valueOf(d), width, '0');
	}

	/**
	 * 将一个整数转换成最小长度为某一固定数值的十六进制形式字符串
	 *
	 * @param d
	 *            整数
	 * @param width
	 *            宽度
	 * @return 新字符串
	 */
	public static String fillHex(int d, int width) {
		return alignRight(Integer.toHexString(d), width, '0');
	}

	/**
	 * 将一个整数转换成最小长度为某一固定数值的二进制形式字符串
	 *
	 * @param d 整数
	 * @param width 宽度
	 * @return 新字符串
	 */
	public static String fillBinary(int d, int width) {
		return alignRight(Integer.toBinaryString(d), width, '0');
	}

	/**
	 * 将一个整数转换成固定长度的十进制形式字符串
	 *
	 * @param d 整数
	 * @param width 宽度
	 * @return 新字符串
	 */
	public static String toDigit(int d, int width) {
		return Strings.cutRight(String.valueOf(d), width, '0');
	}

	/**
	 * 将一个整数转换成固定长度的十六进制形式字符串
	 *
	 * @param d 整数
	 * @param width 宽度
	 * @return 新字符串
	 */
	public static String toHex(int d, int width) {
		return Strings.cutRight(Integer.toHexString(d), width, '0');
	}

	/**
	 * 将一个整数转换成固定长度的二进制形式字符串
	 *
	 * @param d 整数
	 * @param width 宽度
	 * @return 新字符串
	 */
	public static String toBinary(int d, int width) {
		return Strings.cutRight(Integer.toBinaryString(d), width, '0');
	}

	/**
	 * 保证字符串为一固定长度。超过长度，切除，否则补字符。
	 *
	 * @param s 字符串
	 * @param width 长度
	 * @param c 补字符
	 * @return 修饰后的字符串
	 */
	public static String cutRight(String s, int width, char c) {
		if (null == s)
			return null;
		int len = s.length();
		if (len == width)
			return s;
		if (len < width)
			return Strings.dup(c, width - len) + s;
		return s.substring(len - width, len);
	}

	/**
	 * 在字符串左侧填充一定数量的特殊字符
	 *
	 * @param cs 字符串
	 * @param width 字符数量
	 * @param c 字符
	 * @return 新字符串
	 */
	public static String alignRight(CharSequence cs, int width, char c) {
		if (null == cs)
			return null;
		int len = cs.length();
		if (len >= width)
			return cs.toString();
		return new StringBuilder().append(dup(c, width - len)).append(cs).toString();
	}

	/**
	 * 在字符串右侧填充一定数量的特殊字符
	 *
	 * @param cs 字符串
	 * @param width 字符数量
	 * @param c 字符
	 * @return 新字符串
	 */
	public static String alignLeft(CharSequence cs, int width, char c) {
		if (null == cs)
			return null;
		int length = cs.length();
		if (length >= width)
			return cs.toString();
		return new StringBuilder().append(cs).append(dup(c, width - length)).toString();
	}

	/**
	 * @param cs
	 *            字符串
	 * @param lc
	 *            左字符
	 * @param rc
	 *            右字符
	 * @return 字符串是被左字符和右字符包裹 -- 忽略空白
	 */
	public static boolean isQuoteByIgnoreBlank(CharSequence cs, char lc, char rc) {
		if (null == cs)
			return false;
		int len = cs.length();
		if (len < 2)
			return false;
		int l = 0;
		int last = len - 1;
		int r = last;
		for (; l < len; l++) {
			if (!Character.isWhitespace(cs.charAt(l)))
				break;
		}
		if (cs.charAt(l) != lc)
			return false;
		for (; r > l; r--) {
			if (!Character.isWhitespace(cs.charAt(r)))
				break;
		}
		return l < r && cs.charAt(r) == rc;
	}

	/**
	 * @param cs
	 *            字符串
	 * @param lc
	 *            左字符
	 * @param rc
	 *            右字符
	 * @return 字符串是被左字符和右字符包裹
	 */
	public static boolean isQuoteBy(CharSequence cs, char lc, char rc) {
		if (null == cs)
			return false;
		int length = cs.length();
		return length > 1 && cs.charAt(0) == lc && cs.charAt(length - 1) == rc;
	}

	/**
	 * 获得一个字符串集合中，最长串的长度
	 *
	 * @param coll 字符串集合
	 * @return 最大长度
	 */
	public static int maxLength(Collection<? extends CharSequence> coll) {
		int re = 0;
		if (null != coll)
			for (CharSequence s : coll)
				if (null != s)
					re = Math.max(re, s.length());
		return re;
	}

	/**
	 * 获得一个字符串数组中，最长串的长度
	 *
	 * @param array
	 *            字符串数组
	 * @return 最大长度
	 */
	public static <T extends CharSequence> int maxLength(T[] array) {
		int re = 0;
		if (null != array)
			for (CharSequence s : array)
				if (null != s)
					re = Math.max(re, s.length());
		return re;
	}

	/**
	 * 对obj进行toString()操作,如果为null返回""
	 *
	 * @param obj
	 * @return obj.toString()
	 */
	public static String sNull(Object obj) {
		return sNull(obj, "");
	}

	/**
	 * 对obj进行toString()操作,如果为null返回def中定义的值
	 *
	 * @param obj
	 * @param defaultValue
	 *            如果obj==null返回的内容
	 * @return obj的toString()操作
	 */
	public static String sNull(Object obj, String defaultValue) {
		return obj != null ? obj.toString() : defaultValue;
	}

	/**
	 * 对obj进行toString()操作,如果为空串返回""
	 *
	 * @param obj
	 * @return obj.toString()
	 */
	public static String sBlank(Object obj) {
		return sBlank(obj, "");
	}

	/**
	 * 对obj进行toString()操作,如果为空串返回def中定义的值
	 *
	 * @param obj
	 * @param def
	 *            如果obj==null返回的内容
	 * @return obj的toString()操作
	 */
	public static String sBlank(Object obj, String def) {
		if (null == obj)
			return def;
		String s = obj.toString();
		return Strings.isBlank(s) ? def : s;
	}

	/**
	 * 截去第一个字符
	 * <p>
	 * 比如:
	 * <ul>
	 * <li>removeFirst("12345") => 2345
	 * <li>removeFirst("A") => ""
	 * </ul>
	 *
	 * @param str
	 *            字符串
	 * @return 新字符串
	 */
	public static String removeFirst(CharSequence str) {
		if (str == null)
			return null;
		if (str.length() > 1)
			return str.subSequence(1, str.length()).toString();
		return "";
	}

	/**
	 * 如果str中第一个字符和 c一致,则删除,否则返回 str
	 * <p>
	 * 比如:
	 * <ul>
	 * <li>removeFirst("12345",1) => "2345"
	 * <li>removeFirst("ABC",'B') => "ABC"
	 * <li>removeFirst("A",'B') => "A"
	 * <li>removeFirst("A",'A') => ""
	 * </ul>
	 *
	 * @param str
	 *            字符串
	 * @param c
	 *            第一个个要被截取的字符
	 * @return 新字符串
	 */
	public static String removeFirst(String str, char c) {
		return (Strings.isEmpty(str) || c != str.charAt(0)) ? str : str.substring(1);
	}

	/**
	 * 判断一个字符串数组是否包括某一字符串
	 *
	 * @param ss
	 *            字符串数组
	 * @param s
	 *            字符串
	 * @return 是否包含
	 */
	public static boolean isin(String[] ss, String s) {
		if (null == ss || ss.length == 0 || Strings.isBlank(s))
			return false;
		for (String w : ss)
			if (s.equals(w))
				return true;
		return false;
	}

	private static Pattern email_Pattern = Pattern.compile("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

	/**
	 * 检查一个字符串是否为合法的电子邮件地址
	 *
	 * @param input
	 *            需要检查的字符串
	 * @return true 如果是有效的邮箱地址
	 */
	public static synchronized final boolean isEmail(CharSequence input) {
		return email_Pattern.matcher(input).matches();
	}

	/**
	 * 将一个字符串某一个字符后面的字母变成大写，比如
	 *
	 * <pre>
	 *  upperWord("hello-world", '-') => "helloWorld"
	 * </pre>
	 *
	 * @param s
	 *            字符串
	 * @param c
	 *            字符
	 *
	 * @return 转换后字符串
	 */
	public static String upperWord(CharSequence s, char c) {
		StringBuilder sb = new StringBuilder();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			char ch = s.charAt(i);
			if (ch == c) {
				do {
					i++;
					if (i >= len)
						return sb.toString();
					ch = s.charAt(i);
				} while (ch == c);
				sb.append(Character.toUpperCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static int indexOf(String referer, String string) {
		if(hasLengthBytrim(referer)){
			return referer.indexOf(string);
		}
		return -1;
	}

	/**
     * 根据某种编码方式将字节数组转换成字符串
     * @param b 字节数组
     * @param offset 要转换的起始位置
     * @param len 要转换的长度
     * @param encoding 编码方式
     * @return 如果encoding不支持，返回一个缺省编码的字符串
     */
    public static String getString(byte[] b, int offset, int len, String encoding) {
        try {
            return new String(b, offset, len, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(b, offset, len);
        }
    }

    public static String toHexString(byte[] buf) {
		return toHexString(buf, null, Integer.MAX_VALUE);
	}
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	public static String toHexString(byte[] buf, String sep, int lineLen) {
		if (buf == null)
			return null;
		if (lineLen <= 0)
			lineLen = Integer.MAX_VALUE;
		StringBuffer res = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			int b = buf[i];
			res.append(HEX_DIGITS[(b >> 4) & 0xf]);
			res.append(HEX_DIGITS[b & 0xf]);
			if (i > 0 && (i % lineLen) == 0)
				res.append('\n');
			else if (sep != null && i < lineLen - 1)
				res.append(sep);
		}
		return res.toString();
	}

	private static final int charToNibble(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		} else if (c >= 'a' && c <= 'f') {
			return 0xa + (c - 'a');
		} else if (c >= 'A' && c <= 'F') {
			return 0xA + (c - 'A');
		} else {
			return -1;
		}
	}
	public static byte[] fromHexString(String text) {
		text = text.trim();
		if (text.length() % 2 != 0)
			text = "0" + text;
		int resLen = text.length() / 2;
		int loNibble, hiNibble;
		byte[] res = new byte[resLen];
		for (int i = 0; i < resLen; i++) {
			int j = i << 1;
			hiNibble = charToNibble(text.charAt(j));
			loNibble = charToNibble(text.charAt(j + 1));
			if (loNibble == -1 || hiNibble == -1)
				return null;
			res[i] = (byte) (hiNibble << 4 | loNibble);
		}
		return res;
	}

	/**
	 * URL编码
	 * @param url
	 * @param enc
	 * @return
	 */
	public static String urlencoding(String url, String enc){
		if(url != null){
			try {
				return URLEncoder.encode(Htmls.cleanHtmlTag(url), enc);
			}
			catch (Exception e) {e.printStackTrace();}
		}
		return null;
	}

	/**
	 * URL编码
	 * @param url
	 * @return
	 */
	public static String urlencoding(String url){
		return urlencoding(url, "UTF-8");
	}

	/**
	 * URL解码
	 * @param url
	 * @param enc
	 * @return
	 */
	public static String urldecoding(String url, String enc){
		if(url != null){
			try {
				return URLDecoder.decode(url, enc);
			}
			catch (Exception e) {e.printStackTrace();}
		}
		return null;
	}

	/**
	 * URL解码
	 * @param url
	 * @return
	 */
	public static String urldecoding(String url){
		return urldecoding(url, "UTF-8");
	}

	public static void main(String[] args) {
		System.out.println(urldecoding("SiGeC%2FSi%E5%BC%82%E8%B4%A8%E7%BB%93"));
	}
}
