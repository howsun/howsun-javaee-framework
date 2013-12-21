package org.howsun.dao.jpadao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.howsun.dao.ExtendExecutant;
import org.howsun.dao.GenericDao;
import org.howsun.dao.OrderBean;
import org.howsun.dao.page.Page;
import org.howsun.log.Log;
import org.howsun.log.LogFactory;
import org.howsun.util.Asserts;
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
@Named("jpaGenericDao")
public class JpaGenericDao implements GenericDao {

	protected Log log = LogFactory.getLog(JpaGenericDao.class);

	@PersistenceContext
	protected EntityManager entityManager;


	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public <T> int delete(Class<T> entityClass, Serializable... entityids) {
		int i = 0;
		for(Serializable id : entityids){
			try {
				entityManager.remove(entityManager.find(entityClass, id));
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
		entityManager.remove(object);
		return 1;
	}

	@Override
	public <T> int delete(Class<T> entityClass, String condition, Object[] params){
		if(Strings.hasLength(condition)){
			Query query = entityManager.createQuery("DELETE " + entityClass.getName() + " WHERE " + condition);
			this.setParameter(query, params);
			return query.executeUpdate();
		}
		return 0;
	}

	@Override
	public <T> T find(Class<T> entityClass, Serializable entityid) {
		return (T)entityManager.find(entityClass, entityid);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T findBySQL(String sql, Object[] params) {
		T t = null;
		Query query = entityManager.createNativeQuery(sql);
		this.setParameter(query, params);
		try {
			t = (T)query.getSingleResult();
		}
		catch (javax.persistence.NoResultException e) {
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T findByXQL(Class<T> entityClass, String condition, Object[] params) {
		T t = null;
		StringBuffer sql = new StringBuffer("from " + entityClass.getName() );
		if(condition != null && condition.trim().length() > 0)
			sql.append(" where ").append(condition);
		Query query = entityManager.createQuery(sql.toString());
		this.setParameter(query, params);
		try {
			t = (T)query.getSingleResult();
		}
		catch (javax.persistence.NoResultException e) {
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return t;
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
		Query query = null;
		if(page != null && page.getTotalCount() == 0){
			query = entityManager.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		//设置排序

		//设置排序
		if(order != null)
			sql.append(order.toSQL("o"));

		query = entityManager.createQuery(sql.toString());
		this.setParameter(query, params);

		//分页
		if(page != null){
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());
		}

		return query.getResultList();
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

	@Override
	public <T> List<T> finds(Class<T> entityClass) {
		CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<?> findsBySQL(String sql, Page page, Object[] params) {
		String sqlCopy = sql.toLowerCase();
		Asserts.isTrue(sqlCopy.contains("select") && sqlCopy.contains("from"), "不合格的SQL语句");

		if(page != null && page.getTotalCount() == 0){
			int start = sqlCopy.indexOf("select") + 6;
			int end = sqlCopy.indexOf("from");
			StringBuffer s = new StringBuffer();
			s.append(sql.substring(0, start)).append(" count(*) ").append(sql.substring(end, sql.length()));
			Query query = entityManager.createNativeQuery(s.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}

		Query query = entityManager.createNativeQuery(sql);
		setParameter(query, params);

		return query.getResultList();
	}

	@Override
	public List<?> findsBySQL(String sql, Object[] params) {
		return this.findsBySQL(sql, null, params);
	}

	@Override
	public List<?> findsByXQL(String xql, Page page, Object[] params) {

		Query query = entityManager.createQuery(xql);

		if(page != null && page.getTotalCount() == 0){
			String countSql = "select count(*) " + xql.substring(xql.indexOf("from"));
			query = entityManager.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}

		// 分页
		if (page !=null)
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());

		this.setParameter(query, params);

		return query.getResultList();
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	public <T> long getCount(Class<T> entityClass) {
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityClass.getName() +" o");
		Query query = entityManager.createQuery(countSql.toString());
		return getCount(query);
	}

	@Override
	public <T> long getCount(Class<T> entityClass, String condition, Object[] params) {
		String entityName = entityClass.getName();
		StringBuffer countSql = new StringBuffer("select count(" + getCountField(entityClass) + ") from " + entityName +" o");

		//设置查询条件
		if(condition != null && condition.trim().length() > 0){
			countSql.append(" where ").append(condition);
		}

		Query query = entityManager.createQuery(countSql.toString());
		this.setParameter(query, params);
		return getCount(query);
	}


	@Override
	public void merge(Object object) {
		entityManager.merge(object);
	}

	@Override
	public void save(Object object) {
		entityManager.persist(object);
	}

	@Override
	public void update(Object object) {
		entityManager.merge(object);
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
		String idField = getIdField(entityName);
		return updateByBatch(entityName, fs.toString(), idField, vs.toArray());
	}

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


		Query query = entityManager.createQuery(xQL.toString());
		setParameter(query, values);

		return query.executeUpdate();
	}


	@Override
	public Long nextId(Class<?> entityClass) {
		Query query = entityManager.createQuery("SELECT MAX(id) FROM " + entityClass.getName());
		Integer maxId = (Integer)query.getSingleResult();
		return maxId == null ? 1L : ++maxId;
	}

	@Override
	public <T> void increaseFieldValue(Class<T> entityName, String field, Integer defaultValue, Serializable id) {
		String idFieldName = getCountField(entityName);
		Asserts.notNull(idFieldName, "未找到主键字段");
		if(!Numbers.thanZero(defaultValue)){
			defaultValue = 1;
		}
		Query query = entityManager.createQuery(String.format("UPDATE %s SET %s=%s+" + defaultValue + " WHERE %s=?:id",
						entityName.getName(),
						field,
						field,
						idFieldName));
		query.setParameter("id", id);
		query.executeUpdate();

	}

	@Override
	public void execute(ExtendExecutant extendExecutant){
		extendExecutant.executing(entityManager);
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
				query.setParameter(i+1, params[i]);
			}
		}
	}


	private <T> String getIdField(Class<T> entityName){
		String idField = null;
		try {
			idField = entityManager.getEntityManagerFactory().getMetamodel().entity(entityName).getId(Id.class).getName();
		}
		catch (Exception e) {
			try {
				idField = entityManager.getEntityManagerFactory().getMetamodel().entity(entityName).getId(EmbeddedId.class).getName();
			}
			catch (Exception e2) {
				idField = "id";
			}
		}
		return idField == null ? "id" : idField;
	}
	/**
	 * 得到统计字段
	 * @param <T>
	 * @param entityClass
	 * @return String
	 */
	private <T> String getCountField(Class<T> entityClass){
		String idField = null;
		try {
			idField = entityManager.getEntityManagerFactory().getMetamodel().entity(entityClass).getId(Id.class).getName();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return idField == null ? "*" : "o." + idField;
		/*
		try {
			PropertyDescriptor[] ps = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : ps){
				Method getter = propertydesc.getReadMethod();
				if(getter != null && getter.isAnnotationPresent(EmbeddedId.class)){
					PropertyDescriptor[] idClassps  = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					return "o." + propertydesc.getName() + "." + (idClassps[0].getName().equals("class") ? idClassps[1].getName() : idClassps[0].getName());
				}

				//需要修改，查找到id字段
				String idField = entityManager.getEntityManagerFactory().getMetamodel().entity(entityClass).getId(Id.class).getName();
				if(idField == null){
					idField = "*";
				}
				return "o." + idField;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "o";
		*/
	}

	//主方法
	@SuppressWarnings("unchecked")
	private <T> List<T> getScrollData(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order) {
		StringBuffer sql = new StringBuffer("select o from " + entityClass.getName() +" o");
		StringBuffer countSql = new StringBuffer("select count("+getCountField(entityClass)+") from " + entityClass.getName() +" o");


		//设置查询条件
		if(Strings.hasLengthBytrim(condition)){
			sql.append(" where ").append(condition);
			countSql.append(" where ").append(condition);
		}

		Query query = null;

		if(page != null && page.getTotalCount() == 0){
			log.debug(countSql.toString());
			query = entityManager.createQuery(countSql.toString());
			this.setParameter(query, params);
			this.setCount(query, page);
		}
		//设置排序
		if(order != null)
			sql.append(order.toSQL("o"));//排序
		query = entityManager.createQuery(sql.toString());
		this.setParameter(query, params);

		//分页
		if(page != null){
			query.setFirstResult(page.getFirstIndex()).setMaxResults(page.getPageSize());
		}

		log.debug(sql.toString());

		return query.getResultList();
	}

	private void setCount(Query query, Page page){
		int count = getCount(query);
		page.setTotalCount(count);
	}

	private int getCount(Query query){
		Object object  = query.getSingleResult();
		if(object instanceof BigInteger){
			BigInteger bi = (BigInteger)object;
			return bi.intValue();
		}
		if(object instanceof Long){
			long i = (Long)object;
			return (int)i;
		}
		if(object instanceof Integer){
			Integer i = (Integer)object;
			return i;
		}
		return 0;
	}

	@Override
	public void finalize() throws Throwable {
		if(this.entityManager != null){
			entityManager.close();
		}
		super.finalize();
	}



	////////////////////////////////////////////////////////////////////

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public static void main(String[] args) {

	}


}
