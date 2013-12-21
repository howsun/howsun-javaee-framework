/**
 * 版本修订记录
 * 创建：2013-1-20
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.redis;

import java.util.Date;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;
import org.howsun.test.RunTimeConsuming;
import org.howsun.util.Objects;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import redis.clients.jedis.ShardedJedis;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-20
 *
 */
//@org.howsun.dcs.ContractService("cacheService")
public class JedisCacheService implements CacheService {

	private Log log = LogFactory.getLog(JedisCacheService.class);

	protected ShardedCacheFactory cacheFactory;

	protected JdkSerializationRedisSerializer serializer;

	protected boolean report = false;


	public void setCallback(CacheObjectTransfCallbacker callbacker){
		callbacker.setCallback(this);
	}

	//-------------------------------------read----------------------------------
	@Override
	public String get(String key) {

		long start = System.currentTimeMillis();

		String result = null;
		ShardedJedis jedis = null;
		try {
			jedis  = cacheFactory.getResource();
			result = jedis.get(key);
		}
		catch (Exception e) {
			log.error("获取缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}

		if(report){
			log.info(String.format("The [%s]'s cache %s, total time of %s milliseconds by it.", key, result == null ? "did not got":"is got", System.currentTimeMillis() - start));
		}

		return result;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final String key, Class<T> clazz) {
		Object obj = getObject(key);
		if(obj != null && Objects.isTheClass(obj, clazz)){
			return (T)obj;
		}
		return null;
	}


	@Override
	public Object getObject(String key){

		long start = System.currentTimeMillis();

		Object result = null;
		ShardedJedis jedis = null;
		try {
			jedis = cacheFactory.getResource();
			byte[] value = jedis.get(key.getBytes("UTF-8"));
			result = serializer.deserialize(value);
		}
		catch (Exception e) {
			log.error("获取缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}

		if(report){
			log.info(String.format("The [%s]'s cache %s, total time of %s milliseconds by it.", key, result == null ? "did not got":"is got", System.currentTimeMillis() - start));
		}

		return result;
	}


	@Override
	public boolean exists(String key) {
		if(key == null){
			return false;
		}
		ShardedJedis jedis = null;
		try {
			jedis = cacheFactory.getResource();
			return jedis.exists(key);
		}
		catch (Exception e) {
			log.error("操作缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}
		return false;
	}

	//-------------------------------------write----------------------------------


	public void set(String key, String value) {
		set(key, value, -1);
	}

	public void set(String key, String value, int offset) {
		setStringValue(key, value, offset);
	}

	public String set(String key, Object value){
		return set(key, value, -1);
	}

	public String set(String key, Object value, int seconds){
		return setObject(key, value, seconds);
	}

	/**
	 * 设置过期时间
	 */
	public Long expire(String key, int seconds){
		ShardedJedis jedis = null;
		try {
			jedis = cacheFactory.getResource();
			return jedis.expire(key, seconds);
		}
		catch (Exception e) {
			log.error("操作缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}
		return null;
	}


	/**指定在未来日期过期*/
	public Long expireAt(String key, Date date){
		ShardedJedis jedis = null;
		try {
			jedis = cacheFactory.getResource();
			final long rawTimeout = date.getTime() / 1000;
			return jedis.expireAt(key, rawTimeout);
		}
		catch (Exception e) {
			log.error("获取缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}
		return null;
	}


	@Override
	public long remove(String key) {
		ShardedJedis jedis = null;
		try {
			jedis = cacheFactory.getResource();
			return jedis.del(key);
		}
		catch (Exception e) {
			log.error("删除缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}
		return -1;
	}


	public void destroy(){
		if(this.cacheFactory != null){
			try {
				cacheFactory.destroy();
			}
			catch (Exception e) {
				log.info("关闭错误：",e);
			}
		}
	}

	//-----------------------------------Private method------------------------------

	private String setObject(String key, Object value, int seconds) {
		ShardedJedis jedis = null;

		if(value instanceof String){
			return setStringValue(key, value.toString(), seconds);
		}

		try {
			jedis = cacheFactory.getResource();

			byte[] keyBytes = key.getBytes("UTF-8");
			byte result[] = serializer.serialize(value);
			if(seconds > -1){
				return jedis.setex(keyBytes, seconds, result);
			}else{
				return jedis.set(keyBytes, result);
			}
		}
		catch (Exception e) {
			log.error("获取缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}
		return null;
	}

	private String setStringValue(String key, String value, int seconds) {
		ShardedJedis jedis = null;
		try {
			jedis = cacheFactory.getResource();
			if(seconds > -1){
				return jedis.setex(key, seconds, value);
			}else{
				return jedis.set(key, value);
			}
		}
		catch (Exception e) {
			log.error("获取缓存失败：", e);
		}
		finally{
			if(jedis != null){
				try {cacheFactory.returnResource(jedis);}
				catch (Exception e2) {
					log.error("不能释放缓存操纵对象：",e2);
				}
			}
		}
		return null;
	}

	public void setCacheFactory(ShardedCacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

	public ShardedCacheFactory getCacheFactory() {
		return cacheFactory;
	}

	public JdkSerializationRedisSerializer getSerializer() {
		return serializer;
	}

	public void setSerializer(JdkSerializationRedisSerializer serializer) {
		this.serializer = serializer;
	}
	public boolean isReport() {
		return report;
	}
	public void setReport(boolean report) {
		this.report = report;
	}


	public static void main(String[] args) {
		RunTimeConsuming run = new RunTimeConsuming() {

			@Override
			public void run() {

			}
		};
		System.out.println(run.consume());
	}
}
