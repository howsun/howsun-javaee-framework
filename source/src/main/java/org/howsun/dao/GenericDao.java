package org.howsun.dao;

import java.io.Serializable;
import java.util.List;

import org.howsun.dao.page.Page;


/**
 * @Description：GenericDao，通用数据访问组件
 *
 * @author 张纪豪
 * @Date 2007-9-18
 * @version v2.0
 */
public interface GenericDao {
	/**
	 * 保存
	 * @param object
	 */
	public void save(Object object);

	/**
	 * 更新
	 * @param object
	 */
	public void update(Object object);

	/**
	 * 按需字段更新<br>
	 * e.g: update Entity set f1=?, f2=? where id=?
	 *
	 * @param <T>
	 * @param entityName
	 * @param fields 字段名，多个字段用逗号隔开，字段中不需要=？
	 * @param values 字段的值
	 * @param id
	 */
	public <T> int update(Class<T> entityName, String[] fields, Object[] fieldValues, Serializable id);

	/**
	 * 批量更新
	 * e.g:
	 * "a=1 and b=false"
	 * "a=1,b=false"
	 * "a,b"
	 * "a=1,b=false"
	 * @param <T>
	 * @param entityName 实体名
	 * @param fields 字段名，多个字段用逗号隔开，字段中不需要=？
	 * @param condition  要更新的字段和条件 。e.g: "a,b"、"a=? and b=false"、"a=1,b=false"
	 * @param values 先提供字段的值，再提供条件的值
	 * @return
	 */
	public <T> int updateByBatch(Class<T> entityName, String fields, String condition, Object[] values);

	/**
	 * 更新
	 * @param object
	 */
	public void merge(Object object);

	/**
	 * 删除即时对象
	 * @param object
	 * @return
	 */
	public int delete(Object object);
	/**
	 * 删除，支持批量删除
	 * @param <T>
	 * @param entityClass
	 * @param entityids
	 */
	public <T> int delete(Class<T> entityClass, Serializable ... entityids);

	/**
	 * 根据条件删除
	 * @param <T>
	 * @param entityClass
	 * @param condition
	 * @param params
	 * @return
	 */
	public <T> int delete(Class<T> entityClass, String condition, Object[] params);
	/**
	 * 单个对象查找
	 * @param <T>
	 * @param entityClass
	 * @param entityid
	 * @return t
	 */
	public <T> T find(Class<T> entityClass, Serializable entityid);

	/**
	 * ORM查询语言定制查询
	 * @param <T>
	 * @param entityClass
	 * @param condition
	 * @param params
	 * @return
	 */
	public <T> T findByXQL(Class<T> entityClass, String condition, Object[] params);

	/**
	 * 用原生SQL语言查询
	 * @param <T>
	 * @param entityClass
	 * @param whereql
	 * @param params
	 * @return
	 */
	public <T> T findBySQL(String sql, Object[] params);


	/**
	 * 可定制字段查询，对象中需要与可定制字段对应的构造方法
	 * 注意：当有外连接查询时，条件中的字段一定要加别名，例如o.userid=?
	 * @param <T>
	 * @param entityClass
	 * @param fields
	 * @param page
	 * @param whereql
	 * @param params
	 * @param order
	 * @return
	 */
	public <T> List<T> finds(
			Class<T> entityClass,
			String fields,
			Page page,
			String condition,
			Object[] params,
			OrderBean order);


	/**
	 * 清除会话，对象变成游离态
	 */
	public void clear();

	/**
	 * 提交
	 */
	public void flush();
	/**
	 * 多结果查询一：支持分页、条件参数、排序
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @param whereql
	 * @param params
	 * @param order
	 * @return t
	 */
	<T> List<T> finds(
			Class<T> entityClass,
			Page page,
			String condition,
			Object[] params,
			OrderBean order);

	/**
	 * 多结果查询二：支持分页、条件参数
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @param whereql
	 * @param params
	 * @return t
	 */
	<T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params);


	/**
	 * 多结果查询三：支持分页、排序
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @param order
	 * @return t
	 */
	<T> List<T> finds(Class<T> entityClass, Page page, OrderBean order);

	/**
	 * 多结果查询四：支持分页
	 * @param <T>
	 * @param entityClass
	 * @param first
	 * @param maxResult
	 * @return t
	 */
	<T> List<T> finds(Class<T> entityClass, Page page);


	/**
	 * 多结果查询五：排序不分页
	 * @param <T>
	 * @param entityClass
	 * @return t
	 */
	<T> List<T> finds(Class<T> entityClass,OrderBean order);

	/**
	 * 多结果查询六：
	 * @param <T>
	 * @param entityClass
	 * @return t
	 */
	<T> List<T> finds(Class<T> entityClass);


	/**
	 * 多结果查询七：
	 * 利用自定义的SQL语句查询，带分页和条件
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	List<?> findsByXQL(String xql, Page page, Object[] params);

	/**
	 * 多结果查询七：
	 * 利用自定义的SQL语句查询，带分页和条件
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	List<?> findsBySQL(String sql, Page page, Object[] params);

	/**
	 * 多结果查询八：
	 * 利用自定义的SQL语句查询，带条件
	 * @param <T>
	 * @param entityClass
	 * @return
	 * @throws Exception
	 */
	List<?> findsBySQL(String sql, Object[] params);

	/**
	 * 统计
	 * @param <T>
	 * @param entityClass
	 * @return t
	 * @throws Exception
	 */
	<T> long getCount(Class<T> entityClass);

	/**
	 * 统计
	 * @param <T>
	 * @param entityClass
	 * @param condition
	 * @param params
	 * @return
	 */
	<T> long getCount(Class<T> entityClass, String condition, Object[] params);

	/**自增长主键**/
	Long nextId(Class<?> entityClass);

	/**
	 * 递增某字段值，例如递增点击数
	 * @param entityName
	 * @param fields
	 * @param fieldValues
	 * @param id
	 */
	public <T> void increaseFieldValue(Class<T> entityName, String field, Integer defaultValue, Serializable id);

	/**
	 * 扩展执行器
	 * @param extendExecutant
	 */
	void execute(ExtendExecutant extendExecutant);
}