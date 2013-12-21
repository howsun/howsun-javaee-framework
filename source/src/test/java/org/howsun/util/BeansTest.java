/**
 * 版本修订记录
 * 创建：2013-2-16
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util;

import java.util.List;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-2-16
 *
 */
public class BeansTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<Class<?>> cls = Beans.scanPackage(Strings.class.getPackage());
		System.out.println(cls);

	}

}
