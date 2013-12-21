package org.howsun.util.jsptag;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;

/**
 * @author 张纪豪
 * @Date 2007-9-26
 * @version v3.0
 * @deprecated 测试没通过
 */
public class ToolInvokeTag extends TagSupport {

	public static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(ToolInvokeTag.class);

	private static final Map<String, Method> METHOD_LIBS = new HashMap<String, Method>();

	//public static final String CLASSNAME_REGEX = "([0-9a-zA-Z_\\.]*)\\.([0-9a-zA-Z_\\.]*)\\(([0-9a-zA-Z_\\.]*)\\)";

	/**
	 * e.g.:org.howsun.util.jsptag.ToolInvokeTag.getName(java.lang.String)
	 * e.g.:org.howsun.util.jsptag.ToolInvokeTag.getName()
	 * e.g.:org.howsun.util.jsptag.ToolInvokeTag.getName
	 * @param name
	 * @param args
	 * @return
	 * @throws JspTagException
	 */
	public static Object invoking(String name, Object args) throws JspTagException {
		Method method = METHOD_LIBS.get(name);
		if(method == null){
			method(name);
			method = METHOD_LIBS.get(name);
		}
		Object result = null;
		if(method != null){
			try {
				result = method.getParameterTypes().length == 0 ? method.invoke(null) : method.invoke(null, args);
			}
			catch (Exception e) {
				log.info(e.getMessage(),e);
			}
			if(method.getReturnType().equals(Void.TYPE)){
				result = "void";
			}
		}

		return result;
	}

	/**
	 * e.g.:org.howsun.util.jsptag.ToolInvokeTag.getName(java.lang.String)
	 * e.g.:org.howsun.util.jsptag.ToolInvokeTag.getName()
	 * e.g.:org.howsun.util.jsptag.ToolInvokeTag.getName
	 * @param name
	 * @return
	 * @throws JspTagException
	 */
	public static Method method(String name) throws JspTagException {
		Method method = METHOD_LIBS.get(name);
		if(method == null){
			try {
				String temp[] = name.replace(")", "").split("\\(");
				String className, methodName, parameters = null, classNameAndMethodName = temp[0];
				if(temp.length > 1){
					parameters = temp[1];
				}

				int split = classNameAndMethodName.lastIndexOf('.');
				if(split == -1){
					return null;
				}

				className  = classNameAndMethodName.substring(0, split);
				methodName = classNameAndMethodName.substring(split + 1, classNameAndMethodName.length());
				Class<?> clazz = Class.forName(className);

				Class<?> typeParameters[] = null;

				if(parameters != null){
					String p[] = parameters.split(",");
					typeParameters = new Class<?>[p.length];
					for (int i = 0; i < p.length; i++) {
						String t = p[i];
						if(t.startsWith("java.lang.")){
							t = t.substring(10);
						}
						if(!t.contains(".")){
							while(true){
								if("byte".equalsIgnoreCase(t)){ typeParameters[i] = byte.class;break;}
								if("int".equalsIgnoreCase(t)){ typeParameters[i] = int.class;break;}
								if("short".equalsIgnoreCase(t)){ typeParameters[i] = short.class;break;}
								if("long".equalsIgnoreCase(t)){ typeParameters[i] = long.class;break;}
								if("float".equalsIgnoreCase(t)){ typeParameters[i] = float.class;break;}
								if("double".equalsIgnoreCase(t)){ typeParameters[i] = double.class;break;}
								if("boolean".equalsIgnoreCase(t)){ typeParameters[i] = boolean.class;break;}
								if("char".equalsIgnoreCase(t)){ typeParameters[i] = char.class;break;}
								if("character".equalsIgnoreCase(t)){ typeParameters[i] = Character.class;break;}
								if("string".equalsIgnoreCase(t)){ typeParameters[i] = String.class;break;}
								if("integer".equalsIgnoreCase(t)){ typeParameters[i] = Integer.class;break;}
								break;
							}
						}else{
							typeParameters[i] = Class.forName(p[i]);
						}
					}
				}
				try {
					method = typeParameters == null ? clazz.getDeclaredMethod(methodName) :	 clazz.getDeclaredMethod(methodName,typeParameters) ;
				}
				catch (java.lang.NoSuchMethodException e) {
					if(typeParameters != null){
						for (int i = 0; i < typeParameters.length; i++) {
							while(true){
								if(typeParameters[i].equals(byte.class)){typeParameters[i] = Byte.class;break;}
								if(typeParameters[i].equals(int.class)){typeParameters[i] = Integer.class;break;}
								if(typeParameters[i].equals(short.class)){typeParameters[i] = Short.class;break;}
								if(typeParameters[i].equals(long.class)){typeParameters[i] = Long.class;break;}
								if(typeParameters[i].equals(float.class)){typeParameters[i] = Float.class;break;}
								if(typeParameters[i].equals(double.class)){typeParameters[i] = Double.class;break;}
								if(typeParameters[i].equals(boolean.class)){typeParameters[i] = Boolean.class;break;}
								if(typeParameters[i].equals(char.class)){typeParameters[i] = Character.class;break;}
								break;
							}
						}
					}
					method = typeParameters == null ? clazz.getDeclaredMethod(methodName) :	 clazz.getDeclaredMethod(methodName,typeParameters) ;
				}

				if(method != null){
					METHOD_LIBS.put(name, method);
				}
			}
			catch (Exception e) {
				log.info(e.getMessage(),e);
			}
		}

		return method;
	}

	public static void main(String[] args) throws Exception {

		//System.out.println(c.getDeclaredMethod("long2ip", long.class));
		//System.out.println(long.class.equals(Long.class));
		System.out.println(invoking("org.howsun.util.Ips.long2ip(java.lang.Long)", 2130706433L));
		System.out.println(invoking("org.howsun.util.Ips.ip2long(String)", "129.8.54.2"));
	}
}
