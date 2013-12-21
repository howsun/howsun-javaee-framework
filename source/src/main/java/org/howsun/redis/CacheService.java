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

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-1-20
 *
 */
//@org.howsun.dcs.ContractService
public interface CacheService {

	public String get(String key);

	public <T> T  get(String key, Class<T> clazz);

	public Object getObject(String key);



	public void set(String key, String value);

	public void set(String key, String value, int offset);

	public String set(String key, Object value);

	public String set(String key, Object value, int seconds);


	public boolean exists(String key);


	public Long expire(String key, int timeout);

	public Long expireAt(String key, Date date);

	public long remove(String key);

}
