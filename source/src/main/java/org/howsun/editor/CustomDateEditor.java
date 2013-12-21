package org.howsun.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.howsun.util.Strings;

/**
 * @Description：CalendarEditor，日期转换器
 * @author 张纪豪
 * @Date 2010-7-12
 * @version v1.0
 */
public class CustomDateEditor extends PropertyEditorSupport {
	private SimpleDateFormat dateFormat;
	private boolean allowEmpty = true;
	private Class<?> objType;
	private String pattern = "yyyy-MM-dd";

	public CustomDateEditor(String pattern, Class<?> objType, Boolean allowEmpty) { 
		if(pattern != null) this.pattern = pattern;
		this.objType = objType;
		if(allowEmpty != null) this.allowEmpty = allowEmpty; 
		this.dateFormat = new SimpleDateFormat(this.pattern);
	}
	
	@Override
	public void setAsText(String text)	throws IllegalArgumentException	{
		if (Strings.hasLengthBytrim(text)) {
			try {
				if(objType.equals(Calendar.class)){
					Calendar date = Calendar.getInstance();
					date.setTime(this.dateFormat.parse(text));
					setValue(date);
				}
				if(objType.equals(java.util.Date.class)){
					setValue(this.dateFormat.parse(text));
				}
				if(objType.equals(java.sql.Date.class)){
					setValue(new java.sql.Date(this.dateFormat.parse(text).getTime()));
				}
			}catch (ParseException ex) {
				if(allowEmpty)
					setValue(null);
				else{
					throw new RuntimeException("Date does not allow empty.");
				}
			}
		}else if(!allowEmpty){
			throw new RuntimeException("Date does not allow empty.");
		}else{
			setValue(null);
		}
	}

	@Override
	public String getAsText()	{
		String result = "";
		if (getValue() != null) {
			if(objType.equals(Calendar.class)){
				Calendar value = (Calendar)getValue();
				result = this.dateFormat.format(value.getTime());
			}
			if(objType.equals(java.util.Date.class)){
				result = this.dateFormat.format((java.util.Date)getValue());
			}
			if(objType.equals(java.sql.Date.class)){
				result = this.dateFormat.format((java.sql.Date)getValue());
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		CustomDateEditor cde = new CustomDateEditor("yyyy/MM/dd",Calendar.class,true);
		cde.setAsText("2010/06/07");
		System.out.println(cde.getAsText());
	}
}
