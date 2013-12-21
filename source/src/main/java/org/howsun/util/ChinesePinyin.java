/**
 * ========================================================
 * 北京五八信息技术有限公司技术中心开发一部
 * 日 期：2011-7-26 下午04:47:40
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class ChinesePinyin {

	private static final Map<Integer,String> DICS = new HashMap<Integer,String>();

	static{init();}

	static void init(){
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/pinyin.dic");
			isr = new InputStreamReader(in);
			br = new BufferedReader(isr);

			String temp;
			while((temp = br.readLine()) != null ){
				String[] arr = temp.split("\\ \\(");
				int key = Integer.parseInt(arr[0], 16);
				String value = arr[1].replace(")", "").replaceAll("[0-9]*", "");
				int index = value.indexOf(',');
				if(index > -1){
					value = value.substring(0,index);
				}
				DICS.put(key,value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(br != null){
				try {br.close();} catch (Exception e2) {}
			}
			if(isr != null){
				try {isr.close();} catch (Exception e2) {}
			}
			if(in != null){
				try {in.close();} catch (Exception e2) {}
			}
		}
	}

	public static String getPinyin(String chinese){
		return getPinyin(chinese,"", false);
	}

	public static String getPinyin(String chinese, String separator){
		return getPinyin(chinese, separator, false);
	}

	public static String getPinyin(String chinese, String separator, boolean capitalized){
		char[] chs = chinese.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(char ch : chs){
			String pinyin = DICS.get((int)ch);
			if(pinyin == null){
				sb.append(ch).append(separator);
				continue;
			}
			if(capitalized){
				sb.append(pinyin.substring(0,1).toUpperCase()).append(pinyin.substring(1));
			}else{
				sb.append(pinyin);
			}
			sb.append(separator);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String chinese = "张%纪豪0";
		if(args.length > 0){
			chinese = args[0];
		}
		System.out.println(getPinyin(chinese," ", true));
	}
}
