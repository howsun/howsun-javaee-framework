/**
 * ========================================================
 * 北京五八信息技术有限公司技术中心开发一部
 * 日 期：2011-3-14 下午04:24:51
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;

/**
 * <h1>功能描述：</h1>
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.1
 */
public class Version {

	private static final Log log = LogFactory.getLog(Version.getVersion());

	private static String version ;

	/**
	 * @param args
	 */
	public static void touch() {}

	public static String getVersion() {
		Package pkg = Version.class.getPackage();
		return (pkg != null ? pkg.getImplementationVersion() : null);
	}

}
