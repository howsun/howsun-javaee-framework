/**
 * 版本修订记录
 * 创建：2013-1-7
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-7
 *
 */
public class JedisShartClient {
	private final static JedisPoolConfig config;
	public static final String ip1 = "192.168.1.101";
	public static final String ip2 = "192.168.1.102";
	public static final int port = 6379;
	static{
		config = new JedisPoolConfig();
		config.setMaxActive(1024);
		config.setMaxIdle(200);
		config.setMaxWait(2000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo(ip1, port);
		jedisShardInfo1.setPassword(JedisConstant.password);
		list.add(jedisShardInfo1);
		JedisShardInfo jedisShardInfo2 = new JedisShardInfo(ip2, port);
		jedisShardInfo2.setPassword(JedisConstant.password);
		list.add(jedisShardInfo2);
		ShardedJedisPool pool = new ShardedJedisPool(config, list);
		for (int i = 0; i < 2000; i++) {
			ShardedJedis jedis = pool.getResource();
			String key = "howsun_" + i;
			//jedis.set(key, UUID.randomUUID().toString());
			System.out.println(key + "\t" + jedis.get(key) + "\t" + jedis.toString());
			pool.returnResource(jedis);
		}
	}

}
