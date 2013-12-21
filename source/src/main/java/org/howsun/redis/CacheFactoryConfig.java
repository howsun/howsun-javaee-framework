/**
 * 版本修订记录
 * 创建：2013-1-8
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-8
 *
 */
//@javax.inject.Named(value = "connectionFactoryConfig")
public class CacheFactoryConfig extends JedisPoolConfig {

	private List<ShardedServer> servers = new ArrayList<ShardedServer>(2);

	public List<JedisShardInfo> getJedisShardInfos() {
		return this.servers.size() > 0 ? new ArrayList<JedisShardInfo>(this.servers) : null;
	}

	public List<ShardedServer> getServers() {
		return servers;
	}

	public void setServers(List<ShardedServer> servers) {
		this.servers = servers;
	}

}
