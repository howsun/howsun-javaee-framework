/**
* ========================================================
* 日 期：2011-11-4 上午10:42:36
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.dao;

import java.util.GregorianCalendar;

import org.howsun.util.Asserts;
///package com.bj58.spat.umc.dal;

/**
 * 功能描述：
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class IDGenerator{

	static int SERVER_ID = 1;
	static int DB_COUNT = 9;
	public final static long ID_BEGIN_TIME = new GregorianCalendar(2013, 0, 1, 0, 0, 0).getTimeInMillis();


	/**
	 * 获取主键ID
	 * @return
	 * @throws Exception
	 */
	public static synchronized long getUniqueID(){

		Asserts.isTrue(SERVER_ID > 0, "Server id error, please check config file!");

/*		if(Constant.SERVER_ID <= 0) {
			throw new Exception("server id error, please check config file!");
		}
*/
		long destId = System.currentTimeMillis() - ID_BEGIN_TIME;

		destId = (destId << 8) | SERVER_ID;

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return destId;
	}

	/**
	 * 生成唯一ID,该ID的dbIndex与sourceID一至
	 * 注：最大支持库      ：512个
	 *     最大支持时间：4240-01-01
	 * @param sourceID，如主站web、wap、xx客户端
	 * @return
	 * @throws Exception
	 */
	public static synchronized long getUniqueID(long sourceId){

		/*if(Constant.SERVER_ID <= 0) {
			throw new Exception("server id error, please check config file!");
		}*/

		Asserts.isTrue(SERVER_ID > 0, "Server id error, please check config file!");

		int sourceIndex = getDBIndex(sourceId);
		long destId = System.currentTimeMillis() - ID_BEGIN_TIME;

		destId = (destId << 9) | sourceIndex;
		destId = (destId << 8) | SERVER_ID;

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return destId;
	}

	/**
	 * 获取ID所对应该的数据库编号
	 * @param ID
	 * @return 数据库
	 */
	public static int getDBIndex(long id) {
		return (int)((id >> 8) & (DB_COUNT - 1));
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(getDBIndex(77279543777368L));
		for(int i = 0; i <10; i++) {
			System.out.println(getUniqueID());
		}
	}
}
