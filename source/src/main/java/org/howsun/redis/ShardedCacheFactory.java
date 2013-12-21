/**
 * 版本修订记录
 * 创建：2013-1-8
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.pool.BasePoolableObjectFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;


/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-8
 *
 */
public class ShardedCacheFactory extends redis.clients.util.Pool<ShardedJedis> {

	//private static org.howsun.log.Log log = org.howsun.log.LogFactory.getLog(CacheFactory.class);

	public ShardedCacheFactory(final CacheFactoryConfig connectionConfig) {
		super(connectionConfig, new ShardedJedisFactory(connectionConfig.getJedisShardInfos(), Hashing.MURMUR_HASH, null));
	}

	private static class ShardedJedisFactory extends BasePoolableObjectFactory {
		private List<JedisShardInfo> shards;
		private Hashing algo;
		private Pattern keyTagPattern;

		public ShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo,
				Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
			this.keyTagPattern = keyTagPattern;
		}

		public Object makeObject() throws Exception {
			ShardedJedis jedis = new ShardedJedis(shards, algo, keyTagPattern);
			return jedis;
		}

		public void destroyObject(final Object obj) throws Exception {
			if ((obj != null) && (obj instanceof ShardedJedis)) {
				ShardedJedis shardedJedis = (ShardedJedis) obj;
				for (Jedis jedis : shardedJedis.getAllShards()) {
					try {
						try {
							jedis.quit();
						} catch (Exception e) {

						}
						jedis.disconnect();
					} catch (Exception e) {

					}
				}
			}
		}

		public boolean validateObject(final Object obj) {
			try {
				ShardedJedis jedis = (ShardedJedis) obj;
				for (Jedis shard : jedis.getAllShards()) {
					if (!shard.ping().equals("PONG")) {
						return false;
					}
				}
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}
}
