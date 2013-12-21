/**
 * 版本修订记录
 * 创建：2013-1-8
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import redis.clients.jedis.JedisShardInfo;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-8
 *
 */
public class ShardedServer extends JedisShardInfo {

	public ShardedServer(String host, int port, int timeout, String name) {
		super(host, port, timeout, name);
	}

	public ShardedServer(String host, int port, String name) {
		super(host,port,name);
	}

	public int getTimeout() {
		return super.getTimeout();
	}

	public void setTimeout(int timeout) {
		super.setTimeout(timeout);
	}

	public String getPassword() {
		return super.getPassword();
	}

	public void setPassword(String password) {
		super.setPassword(password);
	}

}
