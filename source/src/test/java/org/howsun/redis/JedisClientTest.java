/**
 * 版本修订记录
 * 创建：2013-1-6
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import java.util.Date;
import java.util.UUID;

import org.howsun.util.Randoms;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-6
 *
 */
public class JedisClientTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String key = "current";
		JedisShardInfo info = new JedisShardInfo("127.0.0.1");
		info.setPassword("111");

		Jedis jedis = new Jedis("127.0.0.1");
		jedis.auth("111");
		//jedis.set(key, new Date().toGMTString());
		//jedis.setex(key, seconds, value)
		//jedis.expire(key, 0);
		//Thread.sleep(1000);
		long start = System.currentTimeMillis();

		for (int i = 0; i < 1000; i++) {
			try {
				jedis.setex("howsun_"+i, 300, UUID.randomUUID().toString());
			}
			catch (Exception e) {
				System.out.println(i+",write error:"+e.getMessage());
				continue;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		for (int i = 0; i < 100; i++) {
			try {
				System.out.println(jedis.get("howsun_" + Randoms.nextInt(100000-1)));
			}
			catch (Exception e) {
				System.out.println(i+",reading error:"+e.getMessage());
				continue;
			}
		}
		System.out.println(System.currentTimeMillis() - end);
	}

}
