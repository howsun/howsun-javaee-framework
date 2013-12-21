package org.howsun.dao.hibernatedao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.howsun.dao.ExtendExecutant;
import org.howsun.dao.GenericDao;
import org.howsun.dao.OrderBean;
import org.howsun.dao.page.Page;
import org.howsun.log.Log;
import org.howsun.log.LogFactory;
import org.howsun.util.Asserts;
import org.howsun.util.Beans;
import org.howsun.util.Numbers;
import org.howsun.util.Strings;


/**
 *
* 功能描述：
*
*  PROPAGATION_REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。<br>
 * PROPAGATION_SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行。<br>
 * PROPAGATION_MANDATORY：支持当前事务，如果当前没有事务，就抛出异常。<br>
 * PROPAGATION_REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起。<br>
 * PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。<br>
 * PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。<br>
 * PROPAGATION_NESTED：支持当前事务，如果当前事务存在，则执行一个嵌套事务，如果当前没有事务，就新建一个事务。
 * 嵌套事务实现了隔离机制，例如B事务嵌套在A事务中，B失败不会影响A提交。而PROPAGATION_REQUIRED则全部回滚<br>
*
* @author howsun(howsun.zhang@google.com)
* @version 1.0.2
 */
@Named("hibernateGenericDao")
public class HibernateGenericDao implements GenericDao {

	protected Log log = LogFactory.getLog(HibernateGenericDao.class);

	public static final Map<Class<?>, Field> ID_FIELD = new HashMap<Class<?>, Field>();

	@Resource
	protected SessionFactory sessionFactory;

	@Override
	public void clear() {
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public <T> int delete(Class<T> entityClass, Serializable... entityids) {
		int i = 0;
		Session session = sessionFactory.getCurrentSession();
		for(Serializable id : entityids){
			try {
				session.delete(session.load(entityClass, id));
				i++;
			} catch (Exception e) {
				log.info(e.getMessage(), e);
				continue;
			}
		}
		return i;
	}

	@Override
	public int delete(Object object) {
		sessionFactory.getCurrentSession().delete(object);
		return 1;
	}

	@Override
	public <T> int delete(Class<T> entityClass, String condition, Object[] params){
		if(Strings.hasLength(condition)){
			Query query = sessionFactory.getCurrentSession().createQuery("DELETE " + entityClass.getName() + " WHERE " + condition);
			this.setParameter(query, params);
			return query.executeUpdate();
		}
		return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T find(Class<T> entityClass, Serializable entityid) {
		return (T)sessionFactory.getCurrentSession().get(entityClass, entityid);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T findBySQL(String sql, Object[] params) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
		this.setParameter(query, params);
		return (T)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T findByXQL(Class<T> entityClass, String condition, Object[] params) {
		StringBuffer sql = new StringBuffer("from " + entityClass.getName() );
		if(condition != null && condition.trim().length() > 0)
			sql.append(" where ").append(condition);
		Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
		this.setParameter(query, params);
		return (T)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> finds(Class<T> entityClass, String fields, Page page,
			String condition, Object[] params, OrderBean order) {
		String entityName = entityClass.getName();
		StringBuffer sql = new StringBuffer("select new " + entityName + "(" + fields + ") from " + entityName +" o");
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityName +" o");

		//设置查询条件
		if(condition != null && condition.trim().length() > 0){
			sql.append(" where ").append(condition);
			countSql.append(" where ").append(condition);
		}
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		if(page != null && page.getTotalCount() == 0){
			query = session.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		//设置排序

		//设置排序
		if(order != null)
			sql.append(order.toSQL("o"));

		query = session.createQuery(sql.toString());
		this.setParameter(query, params);

		//分页
		if(page != null){
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());
		}

		return query.list();
	}


	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order) {
		return getScrollData(entityClass, page, condition, params, order);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params) {
		return getScrollData(entityClass, page, condition, params, null);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, OrderBean order) {
		return getScrollData(entityClass, page, null, null, order);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page) {
		return getScrollData(entityClass, page, null, null, null);
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, OrderBean order) {
		return getScrollData(entityClass, null, null, null, order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> finds(Class<T> entityClass) {
		return sessionFactory.getCurrentSession().createCriteria(entityClass).list();
	}

	@Override
	public List<?> findsBySQL(String sql, Page page, Object[] params) {
		Session session = sessionFactory.getCurrentSession();
		String sqlCopy = sql.toLowerCase();
		Asserts.isTrue(sqlCopy.contains("select") && sqlCopy.contains("from"), "不合格的SQL语句");

		if(page != null && page.getTotalCount() == 0){
			int start = sqlCopy.indexOf("select") + 6;
			int end = sqlCopy.indexOf("from");
			StringBuffer s = new StringBuffer();
			s.append(sql.substring(0, start)).append(" count(*) ").append(sql.substring(end, sql.length()));
			SQLQuery query = session.createSQLQuery(s.toString());
			setParameter(query, params);
			this.setCount(query, page);
		}

		SQLQuery query = session.createSQLQuery(sql);
		setParameter(query, params);

		return query.list();
	}

	@Override
	public List<?> findsBySQL(String sql, Object[] params) {
		return this.findsBySQL(sql, null, params);
	}

	@Override
	public List<?> findsByXQL(String xql, Page page, Object[] params) {

		Query query = sessionFactory.getCurrentSession().createQuery(xql);

		// 分页
		if (page !=null)
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());

		this.setParameter(query, params);

		List<?> s = query.list();

		if(page != null && page.getTotalCount() == 0){
			String countSql = "select count(*) " + xql.substring(xql.indexOf("from"));
			query = sessionFactory.getCurrentSession().createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		return s;
	}

	@Override
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public <T> long getCount(Class<T> entityClass) {
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityClass.getName() +" o");
		Query query = sessionFactory.getCurrentSession().createQuery(countSql.toString());
		return getCountOfLong(query);
	}

	@Override
	public <T> long getCount(Class<T> entityClass, String condition, Object[] params) {
		String entityName = entityClass.getName();
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityName +" o");

		//设置查询条件
		if(condition != null && condition.trim().length() > 0){
			countSql.append(" where ").append(condition);
		}

		Query query = sessionFactory.getCurrentSession().createQuery(countSql.toString());
		this.setParameter(query, params);

		return getCountOfLong(query);
	}


	@Override
	public void merge(Object object) {
		sessionFactory.getCurrentSession().merge(object);
	}

	@Override
	public void save(Object object) {
		sessionFactory.getCurrentSession().save(object);
	}

	@Override
	public void update(Object object) {
		sessionFactory.getCurrentSession().update(object);
	}

	/**
	 * update()
	 * e.g: update Entity set f1=?, f2=? where id=?
	 *
	 * @param <T>
	 * @param entityName
	 * @param fields
	 * @param values
	 * @param id
	 */
	public <T> int update(Class<T> entityName, String[] fields, Object[] values, Serializable id){

		boolean valid = fields == null || fields.length == 0 || id == null;
		Asserts.isFalse(valid, "无法按要求数据更新实体");
		StringBuffer fs = new StringBuffer();
		for (String field : fields) {
			fs.append(field).append(",");
		}
		List<Object> vs = new ArrayList<Object>(Arrays.asList(values));
		vs.add(id);

		return updateByBatch(entityName, fs.toString(), sessionFactory.getClassMetadata(entityName).getIdentifierPropertyName(), vs.toArray());
	}

	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#updateByBatch(java.lang.Class, java.lang.String, java.lang.String, java.lang.Object[])
	 * 有bug:
	 * condition="id in (1,2,3,4)"
	 * WHERE id in (1=? AND 2=? AND 3=? AND 4)=?]
	 */
	@Override
	public <T> int updateByBatch(Class<T> entityName, String fields, String condition, Object[] values){

		Asserts.isTrue(Strings.hasLength(fields), "无法按要求数据更新实体");

		String fs[] = fields.split(",");

		StringBuffer xQL = new StringBuffer("UPDATE ")
		.append(entityName.getName())
		.append(" SET");
		for(String field : fs){
			xQL.append(" ").append(field).append(field.indexOf('=') > -1 ? "," : "=?,");
		}
		if(xQL.toString().endsWith(",")){
			xQL.deleteCharAt(xQL.length() - 1);
		}
		if(Strings.hasLength(condition)){
			xQL.append(" WHERE");
			String conditions[] = condition.split(",");
			if(conditions.length > 1){
				for(String c : conditions){
					xQL.append(" ").append(c).append(c.indexOf('=') > -1 ? "" : "=?").append(" AND");
				}
			}else if(conditions[0].indexOf('=') == -1){
				xQL.append(" ").append(conditions[0]).append("=?");
			}else{
				xQL.append(" ").append(conditions[0]);
			}
			if(xQL.toString().endsWith(" AND")){
				xQL.delete(xQL.length() - 4, xQL.length());
			}
		}

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery(xQL.toString());
		setParameter(query, values);

		return query.executeUpdate();
	}


	@Override
	public Long nextId(Class<?> entityClass) {
		Query query = sessionFactory.getCurrentSession().createQuery("SELECT MAX(id) FROM " + entityClass.getName());
		return getCountOfLong(query) + 1;
	}

	@Override
	public <T> void increaseFieldValue(Class<T> entityName, String field, Integer defaultValue, Serializable id) {
		if(id == null){
			return;
		}
		if(!Numbers.thanZero(defaultValue)){
			defaultValue = 1;
		}
		String idFieldName = sessionFactory.getClassMetadata(entityName).getIdentifierPropertyName();
		Asserts.notNull(idFieldName, "未找到主键字段");
		Query query = sessionFactory.getCurrentSession().createQuery(String.format("UPDATE %s SET %s=%s+" + defaultValue + " WHERE %s=?",
						entityName.getName(),
						field,
						field,
						idFieldName));
		query.setParameter(0, id);
		query.executeUpdate();
	}

	@Override
	public void execute(ExtendExecutant extendExecutant){
		extendExecutant.executing(sessionFactory.getCurrentSession());
	}

	////////////////////////////////////////////////////////private method////////////////////////////////////////////////

	/**
	 * 为查询对象设定参数，注意：如果是JPA，则索引位要加一
	 * @param query
	 * @param params
	 */
	protected void setParameter(Query query, Object[] params){
		if(params != null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	/**
	 * 得到统计字段
	 * @param <T>
	 * @param entityClass
	 * @return String

	private <T> String getCountField(Class<T> entityClass){
		try {
			PropertyDescriptor[] ps = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : ps){
				Method getter = propertydesc.getReadMethod();
				if(getter != null && getter.isAnnotationPresent(EmbeddedId.class)){
					PropertyDescriptor[] idClassps  = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					return "o." + propertydesc.getName() + "." + (idClassps[0].getName().equals("class") ? idClassps[1].getName() : idClassps[0].getName());
				}
				//需要修改，查找到id字段
				return "o." + sessionFactory.getClassMetadata(entityClass).getIdentifierPropertyName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "o";
	}
	*/

	private <T> String getCountField(Class<T> entityClass){
		Field idField = getIdField(entityClass);
		if(idField.isAnnotationPresent(EmbeddedId.class)){
			return "*";
		}
		return "o." + idField.getName();
		//return "o";
	}

	private <T> Field getIdField(Class<T> entityClass){
		Field idField = ID_FIELD.get(entityClass);
		if(idField != null){
			return idField;
		}

		List<Field> fields = new ArrayList<Field>();
		Beans.getDeclaredFields(fields, entityClass);
		for(Field f : fields){
			if(f.getAnnotation(Id.class) != null || f.getAnnotation(EmbeddedId.class) != null){
				idField = f;
			}
		}

		ID_FIELD.put(entityClass, idField);
		Asserts.notNull(idField, String.format("%s实体中没有ID字段", entityClass));

		return idField;
	}

	//主方法
	@SuppressWarnings("unchecked")
	private <T> List<T> getScrollData(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order) {
		StringBuffer sql = new StringBuffer("select o from " + entityClass.getName() +" o");

		//设置查询条件
		if(Strings.hasLengthBytrim(condition)){
			sql.append(" where ").append(condition);
		}

		Session session = sessionFactory.getCurrentSession();
		Query query = null;

		if(page != null && page.getTotalCount() == 0){
			StringBuffer countSql = new StringBuffer("select count("+getCountField(entityClass)+") from " + entityClass.getName() +" o");
			//设置查询条件
			if(Strings.hasLengthBytrim(condition)){
				countSql.append(" where ").append(condition);
			}

			if(log.isDebugEnabled()){
				log.debug(countSql.toString());
			}

			query = session.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		//设置排序
		if(order != null)
			sql.append(order.toSQL("o"));//排序
		query = session.createQuery(sql.toString());
		this.setParameter(query, params);

		//分页
		if(page != null){
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());
		}

		log.debug(sql.toString());

		return query.list();
	}

	private void setCount(Query query, Page page){
		int count = getCount(query);
		page.setTotalCount(count);
	}

	private int getCount(Query query){
		return (int)getCountOfLong(query);
	}

	private long getCountOfLong(Query query){
		Object object  = query.uniqueResult();
		if(object != null){
			if(object instanceof BigInteger){
				BigInteger bi = (BigInteger)object;
				return bi.longValue();
			}
			if(object instanceof Long){
				long i = (Long)object;
				return i;
			}
			if(object instanceof Integer){
				Integer i = (Integer)object;
				return i;
			}
		}
		return 0;
	}
	@Override
	public void finalize() throws Throwable {
		if(this.sessionFactory.getCurrentSession() != null){
			sessionFactory.getCurrentSession().close();
		}
		super.finalize();
	}



	////////////////////////////////////////////////////////////////////

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static void main(String[] args) {
		String sql = "SELECT * FROM cms_article Where articlegroup=1 order id desc";
		String sqlCopy = sql.toLowerCase();
		Asserts.isTrue(sqlCopy.contains("select") && sqlCopy.contains("from"), "不合格的SQL语句");

		int start = sqlCopy.indexOf("select") + 6;
		int end = sqlCopy.indexOf("from");
		StringBuffer s = new StringBuffer();
		s.append(sql.substring(0, start)).append(" count(*) ").append(sql.substring(end, sql.length()));

		System.out.println(s.toString());
	}
}
