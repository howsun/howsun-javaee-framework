/**
* ========================================================
* 北京五八信息技术有限公司技术中心开发一部
* 日 期：2011-7-19 下午04:52:05
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.dao;


/**
 * 功能描述：扩展数据库执行器
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public interface ExtendExecutant {
	/**
	 * 执行器
	 * @param executant 必须为各ORM的数据库操作工具
	 */
	void executing(Object executant);
}
