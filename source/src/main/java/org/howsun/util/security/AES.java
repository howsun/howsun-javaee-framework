/**
 * ========================================================
 * 北京五八信息技术有限公司技术中心开发一部
 * 日 期：2011-9-27 下午02:06:10
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun.util.security;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.howsun.util.Strings;

/**
 * 功能描述：
 * 使用示例：<br>
 * String key = AES.generateKey(128);<br>
 * System.out.println("key: " + key);<br>
 * key = "6e67656f25a8cd836edb4c60cd42da6e";<br>
 * String code = AES.encode("775", key);<br>
 * System.out.println("code: " + code);<br>
 * //code = "0bca33667da44e0a345615a629fc8591";<br>
 * String text = AES.decode(code, key);<br>
 * System.out.println("text: " + new String(text));<br>
 * <br>
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class AES{


	///////////////////////////////////////2011-09-27 by 王文通//////////////////////////////////////////
	/*
	 *
	 */
	/**
	 *
	 */
	public static String generateKey(int len) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(len);
			Key key = keyGen.generateKey();
			//System.out.println(key.getEncoded());
			return Strings.toHexString(key.getEncoded());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 张纪豪添加
	 * @param len
	 * @param password
	 * @return
	 */
	public static String generateKey(int len, String password) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(len);
			keyGen.init(128, new SecureRandom(password.getBytes()));
			Key key = keyGen.generateKey();
			return Strings.toHexString(key.getEncoded());
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] encode(byte[] source, String key) {
		try {
			byte[] ks = Strings.fromHexString(key);
			Key tKey = new SecretKeySpec(ks, "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, tKey);

			byte[] result = cipher.doFinal(source);

			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static String encode(String source, String key) {
		byte[] src = source.getBytes();
		byte[] code = encode(src,key);

		return Strings.toHexString(code);
	}

	public static byte[] decode(byte[] source, String key) {
		try {
			byte[] ks = Strings.fromHexString(key);
			Key tKey = new SecretKeySpec(ks, "AES");

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, tKey);

			byte[] result = cipher.doFinal(source);

			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static String decode(String source, String key) {
		byte[] src = Strings.fromHexString(source);
		byte[] rst = decode(src,key);
		return new String(rst);
	}


	///////////////////////////////////////2011-09-27 by net//////////////////////////////////////////
	/**
	 * 加密
	 *
	 * @param content 需要加密的内容
	 * @param password  加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**解密
	 * @param content  待解密内容, 注意，需要确保16进制转换过的编码
	 * @param password 解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}


	private static void sample1(){
		//String key = AES.generateKey(128);
		// System.out.println("key: " + key);
		String key = generateKey(128,"hoWs0un");
		String code = AES.encode("237EXPERT[张纪豪]17227422", key);
		System.out.println("code: " + code);
		String text = AES.decode(code, key);
		System.out.println("text: " + new String(text));
	}

	private static void sample2() throws UnsupportedEncodingException {

		String content = "2345236345632522223EXPERT[张纪豪华人共和华共在和国吴鄂尔多斯左键中人民银行天津市]4356473417227422683";
		String password = "b8K2m9H5o1W7n6";

		/*
		//加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = encrypt(content, password);
		System.out.println(new String(encryptResult,"UTF8"));
		//解密
		byte[] decryptResult = decrypt(encryptResult,password);
		System.out.println("解密后：" + new String(decryptResult));
		 */

		//加密
		System.out.println("加密前：" + content);
		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult); //16进制转码
		System.out.println("加密后：" + encryptResultStr);
		System.out.println("加密后长度：" + encryptResultStr.length());

		//解密
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr); //16进制转码
		byte[] decryptResult = decrypt(decryptFrom,password);
		System.out.println("解密后：" + new String(decryptResult));
	}

	public static void main(String[] args) throws Exception {
		sample1();//王文通代码
		System.out.println("\ntest1 done......................\n");
		sample2();//Iteye代码

		System.out.println("张纪豪华人共和华共在和国吴鄂尔多斯左键中人民银行天津市".getBytes("UTF-8").length);
		System.out.println("张纪豪".getBytes("UTF-8").length);
	}

}
