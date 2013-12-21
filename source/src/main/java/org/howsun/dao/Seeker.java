package org.howsun.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public interface Seeker extends Serializable {

	/**排序字段**/
	public static final Map<String, String> ORDER_FIELDS = new LinkedHashMap<String, String>();

	/**排序方式**/
	public static final Map<Boolean, String> ORDER_TYPE = new LinkedHashMap<Boolean, String>(2,1);

	/**
	 * 获取排序字段
	 * @return
	 */
	public String getOrderField();

	/**覆盖排序字段**/
	public void setOrderField(String orderField);

	/**排序方式**/
	public boolean isAsc();

	/**重新设置排序方式**/
	public void setAsc(boolean isAsc);

	/**是否有条件**/
	public boolean hasConditioned();

	///////////////////////////////////////////////////Building MongoDB Querrier////////////////////////////////////////////////////////
	/**
	 * 创建查询器
	 * @return
	 */
	public Query buildQuery();

	/**
	 * 创建条件
	 * @return
	 */
	public Criteria buildCriteria();

	///////////////////////////////////////////////////Building RDBMS Condition////////////////////////////////////////////////////////
	/**
	 * 创建针对于RDBMS的HowsunDao查寻条件
	 * @return
	 */
	public RQLConditionbind buildRQL();

	///////////////////////////////////////////////////Building Order////////////////////////////////////////////////////////
	/**
	 * 添加排序字段
	 * @param addField
	 * @param isAsc
	 * @param isReplace 是否保留已有的排序字段
	 * @return
	 */
	public OrderBean addOrderBean(String addField, boolean isAsc, boolean isReplace);

	/**
	 * 获取排序对象
	 * @return
	 */
	public OrderBean getOrderBean();


}