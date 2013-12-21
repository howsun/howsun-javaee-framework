/**
 * 版本修订记录
 * 创建：2013-1-7
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-7
 *
 */
public class JedisConstant {

	public static final String ip1 = "192.168.1.101";
	public static final String ip2 = "192.168.1.102";
	public static final String password = "123456";
	public static final int port = 6379;

	public static final int maxActive = 1024;
	public static final int maxIdle = 200;
	public static final int maxWait = 2000;
	public static final boolean testOnBorrow = true;
	public static final boolean testOnReturn = true;
}
