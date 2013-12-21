/**
 * 版本修订记录
 * 创建：2012-12-17
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2012-12-17
 *
 */
public abstract class GeneralSeeker implements Seeker {

	private static final long serialVersionUID = 1L;

	static{
		ORDER_TYPE.put(false, "倒排序");
		ORDER_TYPE.put(true, "正排序");
	}


	///////////////////////Order by////////////////
    protected String orderField;

    protected boolean isAsc = false;

    private OrderBean orderBean;

    protected boolean conditioned = false;



    ///////////////////////////////////////////////////Building Query////////////////////////////////////////////////////////
    /* (non-Javadoc)
     * @see org.howsun.dao.Seeker#buildQuery()
     */
    @Override
    public Query buildQuery(){
    	Criteria criteria = buildCriteria();
    	return criteria == null ? new Query() : Query.query(criteria);
    }

	@Override
	public abstract Criteria buildCriteria();

	@Override
	public abstract RQLConditionbind buildRQL() ;

	///////////////////////////////////////////////////Building Order////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see org.howsun.dao.Seeker#addOrderBean(java.lang.String, boolean, boolean)
	 */
	@Override
	public OrderBean addOrderBean(String addField, boolean isAsc, boolean isReplace){
		if(orderField == null && addField == null){
			return null;
		}
		if(isReplace){
			getOrderBean().clear();
			getOrderBean().add(addField, isAsc);
		}
		else if(orderField != null){
			getOrderBean().add(orderField, isAsc).add(addField, isAsc);
		}
		return getOrderBean();
	}

	/* (non-Javadoc)
	 * @see org.howsun.dao.Seeker#getOrderBean()
	 */
	@Override
	public OrderBean getOrderBean() {
		if(this.orderBean == null && this.orderField != null){
			this.orderBean = new OrderBean(orderField, isAsc);
		}
		return orderBean;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	public boolean hasConditioned(){
		return this.conditioned;
	}

	public void setConditioned(boolean conditioned) {
		this.conditioned = conditioned;
	}

	public static void removeStartCharSequence(StringBuilder rql, String str){
		if(rql.indexOf(str) == 0){
			rql.delete(0, str.length());
		}
	}
	public static void removeEndCharSequence(StringBuilder rql, String str){
		int ol = rql.length();
		if(ol > 0){
			int l = str.length();
			if(rql.indexOf(str) == (ol - l)){
				rql.delete(ol - l, ol - 1);
			}
		}
	}

}
