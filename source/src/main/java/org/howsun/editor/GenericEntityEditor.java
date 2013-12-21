/**GenericEntityEditor.java*/
package org.howsun.editor;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import javax.persistence.Id;

import org.howsun.util.Beans;
import org.howsun.util.Numbers;
import org.howsun.util.Strings;

/**
 * @Description：GenericEntityEditor，通用实体编辑器，可以将前端传的字符串转换成所需的Java对象
 * @author 张纪豪
 * @Date 2010-7-5
 * @version v1.0
 */
public class GenericEntityEditor<T> extends PropertyEditorSupport {
	private Class<T> clazz;

	public GenericEntityEditor(Class<T> clazz){
		this.clazz = clazz;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!Strings.hasLengthBytrim(text)) {
			setValue(null);
			return;
		}
		try {
			Field idField = Beans.getField(this.clazz, Id.class);
			if (idField == null) {
				setValue(null);
				return;
			}
			Class<?> idClass = idField.getType();
			if (Integer.class.equals(idClass)) {
				Constructor<T> c = this.clazz.getConstructor(new Class[] { Integer.class });
				setValue(Numbers.isNumber(text) ? c.newInstance(new Object[] { Integer.valueOf(Integer.parseInt(text)) }) : null );
			}
			else if(Long.class.equals(idClass)){
				Constructor<T> c = this.clazz.getConstructor(new Class[] { Long.class });
				setValue(Numbers.isNumber(text) ? c.newInstance(new Object[] { Long.valueOf(Long.parseLong(text)) }) : null );
			}
			else {
				Constructor<T> c = this.clazz.getConstructor(new Class[] { String.class });
				setValue(c.newInstance(new Object[] { text }));
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException("实体转换器错误: " + ex.getMessage(), ex);
		}
	}
	
	@Override
	 public String getAsText() {
	    if (getValue() == null || getValue().toString() == null) {
	      return "";
	    }
	    return getValue().toString();
	  }
}
