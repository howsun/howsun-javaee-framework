package org.howsun.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

/**
 * JSON接口
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public interface Json {

	/**
	 * 如果JSON字符串为Null或"null"字符串,返回Null.<br>
	 * 如果JSON字符串为"[]",返回空集合.
	 * <p>
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:<br>
	 * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
	 *
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public abstract <T> T fromJson(String jsonString, Class<T> clazz);

	public abstract <T> T fromJson(String jsonString, TypeReference<T> type) throws JsonParseException, JsonMappingException, IOException;

	/**
	 * 如果对象为Null,返回"null".<br>
	 * 如果集合为空集合,返回"[]".
	 * @param object
	 * @return
	 */
	public abstract String toJson(Object object);

	/**
	 * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数.
	 * @param pattern
	 */
	public abstract void setDateFormat(String pattern);

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 * @return ObjectMapper
	 */
	public abstract Object getMapper();

}