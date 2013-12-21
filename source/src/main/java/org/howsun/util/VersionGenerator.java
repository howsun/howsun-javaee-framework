/**
* ========================================================
* 北京五八信息技术有限公司技术中心开发一部
* 日 期：2011-4-26 下午05:27:24
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述：
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class VersionGenerator {

	public static final Pattern PATTERN = Pattern.compile("(\\d{1,2})\\.(\\d{1,2})\\.(\\d{1,2})(.*$)");
	
	/**主版本**/
	public static final byte MAJOR_VERSION  = 0;
	
	/**次版本**/
	public static final byte MINOR_VERSION  = 1;
	
	/**增量版本**/
	public static final byte INCREMENTAL_VERSION  = 2;
	
	private byte[] versions = { 0, 0, 1 };
	
	public VersionGenerator(){
		
	}
	
	public VersionGenerator(byte[] versions){
		this.versions = versions;
	}
	
	public VersionGenerator(String version){
		parse(version);
	}
	
	/**
	 * 指定增量
	 * @param posit
	 */
	public void ascending(byte posit){
		versions[posit]++;
		for (int i = versions.length - 1; i >= 0; i--) {
			if(versions.length == posit){
				versions[i]++;
			}
			if(i > 0 && versions[i] >= 20){
				versions[i] -= 20; 
				versions[i-1]+=1;
			}
		}
	}
	
	/**
	 * 从增量版本递增升级
	 */
	public void ascending(){
		ascending(INCREMENTAL_VERSION);
	}
	
	private void parse(String version){
		Matcher m =  PATTERN.matcher(version);
		if(m.matches()){
			int size = m.groupCount();
			for (int i = 1; i <= size && i < 4; i++) {
				versions[i-1] = Byte.parseByte(m.group(i));
			}
		}
	}
	
	public byte[] getVersions() {
		return versions;
	}
	
	
	public String getVersion() {
		return this.toString();
	}

	@Override
	public String toString() {
		String version = String.valueOf(versions[0]);
		for (int i = 1; i < versions.length; i++) {
			version += "." + versions[i];
		}
		return version;
	}
	
	
	public static void main(String[] args) {
		VersionGenerator v = new VersionGenerator("0.3.12");
		v.ascending();
		System.out.println(v.toString());
	}
	
}
