/**
 * 日 期：2012-3-27 下午2:50:37
 * 作 者：张纪豪
 * 版 本：3.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */
package org.howsun.dao;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述：
 * 
 * @author 张纪豪(zhangjihao@sohu.com)
 * @version 3.0
 */
public class IDGeneratorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long curr = System.currentTimeMillis();
		int size = 100000;
		Set<Long> ids = new HashSet<Long>(size);
		try {
			for (int i = 0; i < size; i++) {
				ids.add(IDGenerator.getUniqueID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(String.format("创建ID总数量：%s，耗时：%s(s).", ids.size(), (System.currentTimeMillis() - curr)/1000));

	}

}
