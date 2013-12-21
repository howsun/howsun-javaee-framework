/**
 * 版本修订记录
 * 创建：2012-12-6
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.dao;

import java.util.ArrayList;
import java.util.List;

import org.howsun.util.Collections;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2012-12-6
 *
 */
public class RQLConditionbind {

	protected StringBuilder rql = new StringBuilder();

	protected List<Object> params = new ArrayList<Object>(0);



	public RQLConditionbind() {
		super();
	}

	public RQLConditionbind(StringBuilder rql, List<Object> params) {
		super();
		this.rql = rql;
		this.params = params;
	}


	public void addRQL(String rql){
		this.rql.append(rql);
	}

	public void addParam(Object param){
		this.params.add(param);
	}

	public StringBuilder getRql() {
		return rql;
	}

	public List<Object> getParams() {
		return params;
	}

	public String getCondition(){
		return getRql() != null ? (getRql().length() > 0 ? getRql().toString() : null) : null;
	}
	public Object[] getParams_(){
		return Collections.notEmpty(getParams()) ? getParams().toArray() : null;
	}


}
