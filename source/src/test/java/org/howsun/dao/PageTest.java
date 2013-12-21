/**
* ========================================================
* 北京五八信息技术有限公司技术中心开发一部
* 日 期：2011-9-28 下午04:04:41
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.dao;

import org.howsun.dao.page.Page;
import org.howsun.dao.page.Paginations;

/**
 * 功能描述：
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class PageTest{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Page page = new Page(3,50);
		page.setTotalCount(235342);
		System.out.println(page.print());

	}

}
