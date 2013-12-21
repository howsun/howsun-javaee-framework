/**
 *
 */
package org.howsun.dao.hibernatedao;

import org.hibernate.Session;
import org.howsun.dao.ExtendExecutant;
import org.howsun.util.Asserts;

/**
 *  功能描述：Hibernate扩展执行器
 * @author howsun
 *
 */
public abstract class HibernateExtendExecutant implements ExtendExecutant {

	/* (non-Javadoc)
	 * @see org.howsun.dao.ExtendExecutant#executing(java.lang.Object)
	 */
	@Override
	public void executing(Object executant){
		Asserts.isTrue(executant instanceof Session, "Type of argument is not org.hibernate.Session");
		Session session = (Session)executant;
		hibernateCallback(session);
	}

	protected abstract void hibernateCallback(Session session);

}
