/**
* ========================================================
* 北京五八信息技术有限公司技术中心开发一部
* 日 期：2010-12-30 上午11:06:00
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.dao;

import java.util.LinkedHashMap;

/**
 * <h1>功能描述：</h1>
 *
 * @author howsun(zjh@58.com)
 * @version 1.0.0
 */
public class OrderBean {

	private LinkedHashMap<String, Boolean> orderby = new LinkedHashMap<String, Boolean>();

	public OrderBean(){}

	public OrderBean(String field , boolean isAsc){
		orderby.put(field, isAsc);
	}

	public OrderBean add(String field , boolean isAsc){
		this.orderby.put(field, isAsc);
		return this;
	}

	public static OrderBean order(String field , boolean isAsc){
		OrderBean o = new OrderBean(field, isAsc);
		return o;
	}

	public void remove(String field){
		this.orderby.remove(field);
	}

	public String toSQL(String alias){
		alias = alias == null ? "" :
			(alias.endsWith(".") ? alias : alias + ".");
		StringBuffer result = new StringBuffer("");
		if(orderby != null && orderby.size() > 0){
			result.append(" order by ");
			for(String key : orderby.keySet()){
				result.append(alias).append(key).append(" ").append(orderby.get(key) ? "asc" : "desc").append(",");
			}
			result.deleteCharAt(result.length()-1);
		}
		return result.toString();
	}

	public void clear(){
		this.orderby.clear();
	}

	public int fieldsTotal(){
		return this.orderby.size();
	}


	public LinkedHashMap<String, Boolean> getOrderValue() {
		return orderby;
	}

	public static void main(String[] args) {
		System.out.println("a1".toUpperCase());
		for (int i = 0; i < 200; i++) {
			System.out.print((char)i);
		}
	}
}
