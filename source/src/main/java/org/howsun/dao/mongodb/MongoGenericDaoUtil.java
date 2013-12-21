/**
 *
 */
package org.howsun.dao.mongodb;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.howsun.dao.OrderBean;
import org.howsun.dao.page.Page;
import org.howsun.util.Collections;
import org.howsun.util.Strings;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author howsun
 *
 */
public abstract class MongoGenericDaoUtil {


	/**
	 * 仿SQL的Like查询
	 *Criteria.where(field).regex(String.format(REGEX_LIKE, name));
	 */
	//public static final java.lang.String REGEX_LIKE = "/%s/";
	public static final java.lang.String REGEX_LIKE = "^.*%s.*$";

	/**
	 * 绑定分页
	 * @param entityName
	 * @param mongoOperations
	 * @param query
	 * @param page
	 */
	public static void bindPaging(Class<?> entityName, MongoOperations mongoOperations, Query query, Page page){
		if(page == null){
			return;
		}
		if(page.getTotalCount() == 0){
			page.setTotalCount((int)mongoOperations.count(query, entityName));
		}
		query.skip(page.getFirstIndex()).limit(page.getPageSize());
	}


	/**
	 * 绑定排序
	 * @param query
	 * @param orderBean
	 */
	public static void bindOrders(Query query, OrderBean orderBean){
		if(orderBean == null){
			return;
		}
		LinkedHashMap<String, Boolean> ov = orderBean.getOrderValue();
		for(Map.Entry<String, Boolean> o : ov.entrySet()){
			query.with(new org.springframework.data.domain.Sort(o.getValue() ? org.springframework.data.domain.Sort.Direction.ASC : org.springframework.data.domain.Sort.Direction.DESC, o.getKey()));
			//query.sort().on(o.getKey(), o.getValue() ? Order.ASCENDING : Order.DESCENDING);
		}
	}


	/**
	 * 定制字段
	 * @param query
	 * @param fields 指定的字段，多个须以逗号隔开，支持*号
	 */
	public static void bindFields(Query query, String fields){
		if(Strings.hasLengthBytrim(fields) && !"*".equals(fields)){
			String[] fs = fields.split(",");
			for(String f : fs){
				query.fields().include(f);
			}
		}
	}
	
	public static void bindFields(Query query, Set<String> fields){
		if(Collections.notEmpty(fields)){
			for(String f : fields){
				query.fields().include(f);
			}
		}
	}

	public static void bindUpdateFieldValue(Update update, Map<String, Object> values){
		if(values == null || values.size() == 0){
			return;
		}
		for(Map.Entry<String, Object> value : values.entrySet()){
			update.set(value.getKey(), value.getValue());
		}
	}
	
	/**
	 * 根据主键和值构建查询对象
	 * @param id
	 * @param value
	 * @return
	 */
	public static Query parseQuery(String id, Object value){
		Criteria critera = Criteria.where(id).is(value);
		Query query = Query.query(critera);
		return query;
	}
}
