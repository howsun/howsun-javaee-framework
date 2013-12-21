/**JsonBinder.java*/
package org.howsun.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.howsun.util.Strings;



/**
 * Json：Jackson简单封装
 *
 * @author howsun(zjh@58.com)
 * @Date 2010-10-20
 * @version v0.1
 */
public class JacksonSupportJson implements Json {

	private static final org.howsun.log.Log log = org.howsun.log.LogFactory.getLog(JacksonSupportJson.class);

	private ObjectMapper objectMapper;

	public JacksonSupportJson(Inclusion inclusion) {
		objectMapper = new ObjectMapper();
		//设置输出包含的属性
		objectMapper.getSerializationConfig().setSerializationInclusion(inclusion);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		objectMapper.getDeserializationConfig().set(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		setDateFormat(null);
	}
	/**
	 * 创建输出全部属性到Json字符串的Binder.
	 * @return Json
	 */
	public static Json buildNormalBinder() {
		return new JacksonSupportJson(Inclusion.ALWAYS);
	}

	/**
	 * 创建只输出非空属性到Json字符串的Binder.
	 * @return Json
	 */
	public static Json buildNonNullBinder() {
		return new JacksonSupportJson(Inclusion.NON_NULL);
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Binder.
	 * @return Json
	 */
	public static Json buildNonDefaultBinder() {
		return new JacksonSupportJson(Inclusion.NON_DEFAULT);
	}

	/* (non-Javadoc)
	 * @see com.bj58.wf.json.Json#fromJson(java.lang.String, java.lang.Class)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		try {
			if (Strings.hasLengthAfterTrimWhiteSpace(jsonString))
				return objectMapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
		}
		return null;
	}


	@Override
	public <T> T fromJson(String jsonString, TypeReference<T> typeReference) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(jsonString, typeReference);
	}


	/* (non-Javadoc)
	 * @see com.bj58.wf.json.Json#toJson(java.lang.Object)
	 */
	public String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (IOException e) {
			log.warn("write to json string error:" + object, e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bj58.wf.json.Json#setDateFormat(java.lang.String)
	 */
	public void setDateFormat(String pattern) {
		if (!Strings.hasLengthAfterTrimWhiteSpace(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		DateFormat df = new SimpleDateFormat(pattern);
		objectMapper.getSerializationConfig().setDateFormat(df);
		objectMapper.getDeserializationConfig().setDateFormat(df);
	}

	/* (non-Javadoc)
	 * @see com.bj58.wf.json.Json#getMapper()
	 */
	public ObjectMapper getMapper() {
		return objectMapper;
	}
}
