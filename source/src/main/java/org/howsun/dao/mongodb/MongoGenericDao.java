/**
 *
 */
package org.howsun.dao.mongodb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;

import org.howsun.core.exception.DaoException;
import org.howsun.dao.ExtendExecutant;
import org.howsun.dao.GenericDao;
import org.howsun.dao.OrderBean;
import org.howsun.dao.page.Page;
import org.howsun.util.Sets;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import com.mongodb.CommandResult;
import com.mongodb.WriteResult;

/**
 * @author howsun
 *
 */
@Named("mongoGenericDao")
public class MongoGenericDao implements GenericDao {

	@Resource
	protected MongoOperations operations;

	@Override
	public void save(Object object) {
		operations.save(object);
	}

	@Override
	public void update(Object object) {
		/*Query query = Query.query(Criteria.where(""));
		operations.updateFirst(query, update, entityClass)
*/
	}

	@Override
	public <T> int update(Class<T> entityClass, String[] fields, Object[] fieldValues, Serializable id) {
		WriteResult result = operations.updateFirst(new Query(where("_id").is(id)), setFieldValue(fields, fieldValues), entityClass);
		CommandResult commandResult = result.getLastError();
		if(commandResult.ok()){
			return 1;
		}
		throw new DaoException(commandResult.getErrorMessage());
	}
	public static Update setFieldValue( String[] fields, Object[] fieldValues){
		Update update = new Update();
		for (int i = 0; i < fieldValues.length; i++) {
			update.set(fields[i], fieldValues[i]);
		}
		return update;
	}

	@Override
	public <T> int updateByBatch(Class<T> entityName, String fields, String condition, Object[] values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void merge(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public int delete(Object object) {
		operations.remove(object);
		return 1;
	}

	@Override
	public <T> int delete(Class<T> entityClass, Serializable... entityids) {
		operations.remove(Query.query(Criteria.where("_id").in(Arrays.asList(entityids))), entityClass);
		return entityids.length;
	}

	@Override
	public <T> int delete(Class<T> entityClass, String condition, Object[] params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T find(Class<T> entityClass, Serializable entityid) {
		return operations.findById(entityid, entityClass);
	}

	@Override
	@Deprecated
	public <T> T findByXQL(Class<T> entityClass, String condition, Object[] params) {
		throw new DaoException("此方法不适用");
	}

	@Override
	@Deprecated
	public <T> T findBySQL(String sql, Object[] params) {
		throw new DaoException("此方法不适用");
	}

	/*
	 * (non-Javadoc)
	 * @see org.howsun.dao.GenericDao#finds(java.lang.Class, java.lang.String, org.howsun.dao.page.Page, java.lang.String, java.lang.Object[], org.howsun.dao.OrderBean)
	 * condition:
	 * a=? and b=? and c=?
	 * a=?,b=?,c=?
	 * a,b,c
	 *
	 */
	@Override
	public <T> List<T> finds(Class<T> entityClass, String fields, Page page, String condition, Object[] params, OrderBean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void clear() {
		throw new DaoException("此方法不适用");
	}

	@Override
	@Deprecated
	public void flush() {
		throw new DaoException("此方法不适用");
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params, OrderBean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, String condition, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page, OrderBean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, Page page) {

		return null;
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass, OrderBean order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> finds(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<?> findsByXQL(String xql, Page page, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<?> findsBySQL(String sql, Page page, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<?> findsBySQL(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> long getCount(Class<T> entityClass) {
		return operations.count(null, entityClass);
	}

	@Override
	public <T> long getCount(Class<T> entityClass, String condition, Object[] params) {
		return 0;
	}

	@Override
	public Long nextId(Class<?> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> void increaseFieldValue(Class<T> entityName, String field, Integer defaultValue, Serializable id) {
		Query query = Query.query(Criteria.where("_id").is(id));
		Update update = new Update();
		update.inc(field, defaultValue);
		operations.updateFirst(query, update, entityName);
	}

	@Override
	public void execute(ExtendExecutant extendExecutant) {
		extendExecutant.executing(operations);
	}

	/**
	 * e.g.:
	 * Query query = parseQuery("{'tracks.name' : 'Wheels'}");
	 * Query query = parseQuery("{'_id' : { '$oid' : '%s' }}", bigWhiskey.getId());
	 * Query query = parseQuery("{'tracks.name' : 'Wheels'}");
	 * Query query = parseQuery("{'tracks.name' : { '$regex' : '.*it.*' , '$options' : '' }}");
	 * Query build = new Query(where("_id").is(bigWhiskey.getId()));
	 *
	 * @param query
	 * @param arguments
	 * @return
	 */
	public static Query parseQuery(String query, Object... arguments) {
		return new BasicQuery(String.format(query, arguments));
	}


	public MongoOperations getOperations() {
		return operations;
	}

	public void setOperations(MongoOperations operations) {
		this.operations = operations;
	}


}
