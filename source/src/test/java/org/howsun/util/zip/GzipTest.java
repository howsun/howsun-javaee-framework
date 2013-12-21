/**
 * 版本修订记录
 * 创建：2013-6-20
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util.zip;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.howsun.util.Asserts;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-6-20
 * 
 */
public class GzipTest {

	String password = "howsun";
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		GzipTest test = new GzipTest();
		test.testHowsunZip();
		test.testHowsunUnZip();
		System.out.println("ok");
	}

	
	/**
	 * 压缩并加密
	 * @throws IOException
	 */
	public void testHowsunZip() throws IOException{
		FileInputStream fileInputStream = new FileInputStream("D:/train/18.JavaEE高级主题.pptx");
		
		byte[] contents = GZips.compress(fileInputStream);
		
		contents = Encrypts.encrypt(contents, password);
		
		OutputStream out =  new FileOutputStream("D:/train/howsun.zip");
		out.write(contents);
		out.flush();
		out.close();
	}
	
	
	/**
	 * 解密并解压
	 * @throws IOException
	 */
	public void testHowsunUnZip() throws IOException{
		FileInputStream inputStream = new FileInputStream("D:/train/howsun.zip");
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int len = 0;
		byte buf[] = new byte[8192];
		while ((len = inputStream.read(buf)) != -1) {
			byteArrayOutputStream.write(buf, 0, len);
		}
		
		byte[] contents = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		contents = Encrypts.decrypt(contents, password);
		contents = GZips.decompress(contents);
		Asserts.notNull(contents,"解密失败");
		
		OutputStream out =  new FileOutputStream("D:/train/howsun.pptx");
		out.write(contents);
		out.flush();
		out.close();
		inputStream.close();
	}
	
	/**
	 * 加密压缩Cookie示例
	 * @throws Exception
	 */
	public void testToCookie() throws Exception{
		/*
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("test", "test");
		map.put("test2", 123);
		map.put("test3", null);

		String datatemp = new String(GZips.compress(Objects.objectToByteArray(map)),Objects.CHARSET_ISO88591);
		String str = Encrypts.encrypt(datatemp, "123");
		System.out.println("加密后:"+str);
		String str2 = Encrypts.decrypt(str, "123");
		System.out.println("解密后"+Objects.byteArrayToObject(GZipUtils.decompress(str2.getBytes(SerializeUtil.CHARSET_ISO88591))));
		 */

	}
	
}
