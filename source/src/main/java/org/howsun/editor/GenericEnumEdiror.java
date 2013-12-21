/**EnumEdiror.java*/
package org.howsun.editor;

import java.beans.PropertyEditorSupport;

import org.howsun.util.Strings;

/**
 * @Description：EnumEdiror，通用枚举类型编辑器，可以将前端传来的字符串转换成所需的Java枚举对象
 * @author 张纪豪
 * @Date 2010-7-5
 * @version v1.0
 */
@SuppressWarnings("unchecked")
public class GenericEnumEdiror<T extends Enum<T>> extends PropertyEditorSupport{

	private Class<T> clazz;
	public GenericEnumEdiror(Class<T> clazz){
		this.clazz = clazz;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		T target = null;
		if(Strings.hasLengthBytrim(text)){
			target = (T) Enum.valueOf(clazz, text.toUpperCase());
		}
		setValue(target);
	}

	@Override
	public String getAsText() {
		Enum<T> e = (T) super.getValue();
		return e.name();
	}

}
