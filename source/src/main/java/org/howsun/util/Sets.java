/**
 * 版本修订记录
 * 创建：2012-11-21
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2012-11-21
 *
 */
public abstract class Sets {

	/**
	 * 将枚举元素包转换到Set集合中
	 * @param enums
	 * @return
	 */
	public static <E extends Enum<?>> Set<String> converElements(E...enums){
		Set<String> elements = new HashSet<String>(enums.length);
		for(Enum<?> e : enums){
			elements.add(e.name());
		}
		return elements;
	}

	/**
	 * 将指定枚举类名的全部元素转换到Set集合中
	 * @param e
	 * @return
	 */
	public static <E extends Enum<?>> Set<String> converElements(Class<E> e){
		Set<String> elements = new HashSet<String>();
		if(e.isEnum()){
			for(E _e : e.getEnumConstants()){
				elements.add(_e.name());
			}
		}
		return elements;
	}

	/**
	 * 将枚集合中的枚举元素的名称转换到Set集合中
	 * @param enums
	 * @return
	 */
	public static <E extends Enum<?>> Set<String> converElements(Set<E> enums){
		Set<String> elements = new HashSet<String>(enums.size());
		for(Enum<?> e : enums){
			elements.add(e.name());
		}
		return elements;
	}

	/**
	 * 将迭代器的枚举元素名称转换到Set集合中
	 * @param elements
	 * @return
	 */
	public static <E extends Enum<E>> Set<String> converElementss(Iterator<? extends E> elements){
		Set<String> es = new HashSet<String>();
		while(elements.hasNext()){
			es.add(elements.next().name());
		}
		return es;
	}
}
