/**
 *
 */
package org.howsun.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author howsun
 *
 */
public abstract class Enums {


	public static List<String> getElements(Set<? extends Enum<?>> es){
		List<String> result = new ArrayList<String>();
		for(Enum<?> e : es){
			result.add(e.name());
		}
		return result;
	}
}
