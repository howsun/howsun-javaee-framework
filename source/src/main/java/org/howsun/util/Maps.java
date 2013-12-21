/**
 * 版本修订记录
 * 创建：2012-12-6
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2012-12-6
 *
 */
public class Maps {

	private static final Log log = LogFactory.getLog(Maps.class);

	public static <E extends Enum<?>> Map<String, E> uniqueIndex(EnumSet<? extends E> cls, String function){
		Map<String, E> result = new HashMap<String, E>(cls.size(), cls.size());
		Iterator<? extends E> es = cls.iterator();
		while(es.hasNext()){
			E e = es.next();
			Method m = null;
			try {
				m = e.getClass().getDeclaredMethod(function);
			}
			catch (Exception e2) {}

			if(m == null){
				try {m = e.getClass().getDeclaredMethod("get" + String.valueOf(function.charAt(0)).toUpperCase() + function.substring(1));}catch (Exception e2) {}
			}
			if(m == null){
				log.info(String.format("Not found getter method of '%s' in Enum '%s'.", function, e.name()));
				continue;
			}
			try {
				Object o = m.invoke(e);
				if(o != null){
					result.put(o.toString(), e);
				}
			}
			catch (Exception exc) {
				log.info(String.format("The '%s' of key not found in Enum '%s'.", function, e.name()));
			}
		}
		return result;
	}

	/**
	 * @param h
	 * @return
	 * 实现对map按照value升序排序
	 */
	@SuppressWarnings("unchecked")
	public static Map.Entry[] getSortedHashtableByValue(Map h) {
		Set<Map.Entry> set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator<Map.Entry>() {
			public int compare(Map.Entry arg0, Map.Entry arg1) {
				Integer o1 = arg0.getValue().hashCode();
				Integer o2 = arg1.getValue().hashCode();
				return o1.compareTo(o2);
			}
		});
		return entries;
	}
}
