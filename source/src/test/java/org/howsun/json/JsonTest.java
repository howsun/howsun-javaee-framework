/**
 *
 */
package org.howsun.json;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author howsun
 *
 */
public class JsonTest {

	public static void main(String[] args) throws Exception{
		String source = "{\"oid\":\"7788\",\"displayName\":\"张三\",\"position\":\"工程师\",\"unitName\":\"中国科学院\"}";
		Json json = JacksonSupportJson.buildNormalBinder();
		ObjectMapper mapper = (ObjectMapper)json.getMapper();
		TypeReference type = new TypeReference<Map<String,String>>() {};

		Map<String,String> result = mapper.readValue(source, type);

		System.out.println(result.size());
	}
}
