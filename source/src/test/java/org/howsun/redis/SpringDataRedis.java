/**
 * 版本修订记录
 * 创建：2013-1-7
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import java.util.ArrayList;
import java.util.List;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-7
 *
 */
public class SpringDataRedis {
	public static final String ip1 = "192.168.1.101";
	public static final String ip2 = "192.168.1.102";
	public static final int port = 6379;
	static Log log = LogFactory.getLog(SpringDataRedis.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JedisShardInfo jedisShardInfo1 = new JedisShardInfo(ip1);
		jedisShardInfo1.setPassword(JedisConstant.password);
		JedisShardInfo jedisShardInfo2 = new JedisShardInfo(ip2);
		jedisShardInfo2.setPassword(JedisConstant.password);

		List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
		jedisShardInfos.add(jedisShardInfo1);
		jedisShardInfos.add(jedisShardInfo2);

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxActive(JedisConstant.maxActive);
		poolConfig.setMaxIdle(JedisConstant.maxIdle);
		poolConfig.setMaxWait(JedisConstant.maxWait);
		poolConfig.setTestOnBorrow(JedisConstant.testOnBorrow);
		poolConfig.setTestOnReturn(JedisConstant.testOnReturn);


		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(poolConfig, jedisShardInfos);

		JedisConnectionFactory factory = new JedisConnectionFactory(jedisShardInfo1);
		StringRedisTemplate template = new StringRedisTemplate(factory);
		for (int i = 0; i < 2000; i++) {
			String key = "howsun_" + i;
			BoundValueOperations<String, String> v = template.boundValueOps(key);
			//jedis.set(key, UUID.randomUUID().toString());
			System.out.println(key + "\t" + v.get() + "\t" + factory.getHostName());
		}

	}

}
