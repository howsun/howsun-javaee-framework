/**
 *
 */
package org.howsun.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.howsun.dao.hibernatedao.HibernateGenericDao;
import org.howsun.dao.page.Page;

/**
 * 说明:<br>
 *
 * @author 张纪豪
 * @version
 * Build Time Mar 17, 2009
 */
public class Beans {

	/**
	 * 打印一个Pojo
	 * @param obj
	 * @return
	 */
	public static String printObject(Object obj){
		return printObject(obj, true);
	}

	/**
	 * 打印Pojo
	 * @param obj
	 * @param isToConsode 是否输出到控制台
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String printObject(Object obj, boolean isToConsode){
		StringBuffer sb = new StringBuffer("");
		try {
			Class<?> clazz = obj.getClass();

			Map<String,Field> fields = new HashMap<String,Field>();
			getDeclaredFields(fields, clazz);

			Map<String, Method> methods = new HashMap<String, Method>();
			getDeclaredMethods(methods, clazz);

			Iterator<?> it = fields.entrySet().iterator();

			while(it.hasNext()){
				Map.Entry<String, Field> entry = (Map.Entry<String, Field>) it.next();
				String fieldName = (String)entry.getKey();
				Method method = methods.get(Strings.getterName(fieldName));
				if(method == null)
					continue;
				try {
					Object value = method.invoke(obj);
					sb.append(fieldName);
					sb.append("==");
					sb.append(value == null ? "null" : value.toString());
					sb.append("\n");
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(isToConsode){
			System.out.println(sb.toString());
			return null;
		}
		return sb.toString();
	}


	public static int size(Object object){
		int result = -1;
		if(object != null && object instanceof Serializable){
			ByteArrayOutputStream baos = null;
			ObjectOutputStream    oos  = null;
			try {
				baos = new ByteArrayOutputStream();
				oos  = new ObjectOutputStream(baos);
				oos.writeObject(object);
				oos.flush();
				result = baos.toByteArray().length;
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if(oos != null){
					try {oos.close();} catch (Exception e2) {}
				}
				if(baos != null){
					try {baos.close();} catch (Exception e2) {}
				}
			}
		}
		return result;
	}

	/**
	 * 得到一个Pojo所有的方法
	 * @param methods
	 * @param clazz
	 */
	public static void getDeclaredMethods(Map<String, Method> methods, Class<?> clazz) {
		Method[] ms = clazz.getDeclaredMethods();
		for(Method m : ms){
			if(!methods.containsKey(m.getName())){
				methods.put(m.getName(), m);
			}
		}
		if(!clazz.equals(Object.class)){
			clazz = clazz.getSuperclass();
			getDeclaredMethods(methods,clazz);
		}
	}

	/**
	 * 得到所有但不包括final修饰的字段
	 * @param fields
	 * @param clazz
	 */
	public static void getDeclaredFields(Map<String,Field> fields, Class<?> clazz){
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs){
			if(f.getModifiers() != (Modifier.PRIVATE + Modifier.STATIC + Modifier.FINAL)){
				if(!fields.containsKey(f.getName())){
					fields.put(f.getName(), f);
				}
			}
		}
		if(!clazz.equals(Object.class)){
			clazz = clazz.getSuperclass();
			getDeclaredFields(fields, clazz);
		}
	}




	/**
	 * 得到对象字段值并输出一个String对象中
	 * @param sb
	 * @param obj
	 * @param parent
	 */
	public static void getObjectFieldsValue(StringBuffer sb, Object obj, Class<?> parent){
		try {
			Class<?> clazz = parent == null ? obj.getClass() : parent;
			Method[] Methods = clazz.getDeclaredMethods();
			for(Method m : Methods){
				if(m.getName().startsWith("get")){
					sb.append(Strings.fieldName(m.getName()) + "=");
					Object o = m.invoke(obj);
					sb.append(o == null ? "null" : o.toString());
					sb.append("\n");
				}
			}
			Class<?> superClass = clazz.getSuperclass();//递归调用父类方法
			if(!superClass.equals(Object.class)){
				getObjectFieldsValue(sb,obj,superClass);
			}
		} catch (Exception e) {
			sb.append("打印失败："+e.getMessage());
		}
	}

	public static void getDeclaredFields(List<Field> fields, Class<?> clazz) {
		Field[] fs = clazz.getDeclaredFields();
		for (Field f : fs) {
			if ((f.getModifiers() == 26) ||  (fields.contains(f))) {
				continue;
			}
			fields.add(f);
		}

		if (!clazz.equals(Object.class)) {
			clazz = clazz.getSuperclass();
			getDeclaredFields(fields, clazz);
		}
	}

	public static Field getField(Class<?> clazz, Class<?> annotationClass) {
		List<Field> fields = new ArrayList<Field>();
		getDeclaredFields(fields, clazz);
		for (Field f : fields) {
			Annotation[] ans = f.getAnnotations();
			for (Annotation a : ans) {
				if (a.annotationType().equals(annotationClass))
					return f;
			}
		}
		return null;
	}


	/**
	 * 根据字段，将源对象字段中的值赋予目标对象字段的值
	 * @param fields
	 * @param source
	 * @param target
	 */
	public static void cloneBean(String[] fields, Object source, Object target) {
		if(fields==null || fields.length==0){
			target = source;
		}
		Method s_m,t_m;
		Map<String, Method> s_methods = new HashMap<String, Method>();
		getDeclaredMethods(s_methods,source.getClass());

		Map<String, Method> t_methods = new HashMap<String, Method>();
		getDeclaredMethods(t_methods,target.getClass());
		try {
			for(String field : fields){
				//source's getter
				s_m = s_methods.get(Strings.getterName(field));
				Object value = s_m.invoke(source);

				//setter
				t_m = t_methods.get(Strings.setterName(field));
				t_m.invoke(target, value);//这时target对象的值会变吗？
			}
		} catch (Exception e) {
			target = null;
		}
	}

	/**
	 * 数组相加
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	public static String[] addArrayOfString(String[] first, String[] second){
		String[] dst = new String[first.length + second.length];
		System.arraycopy(first,0,dst,0,first.length);
		System.arraycopy(second, 0, dst, first.length, second.length);
		return dst;
	}
	/**
	 * 数组相加：注意，返回类型不能强转
	 * @param first
	 * @param second
	 * @return
	 */
	public static Object[] addArray(Object[] first, Object[] second){
		Object[] dst = new String[first.length + second.length];
		System.arraycopy(first,0,dst,0,first.length);
		System.arraycopy(second, 0, dst, first.length, second.length);
		return dst;
	}

	/**
	 * 扫描包下所有类，暂不支持子包
	 * @param packg
	 * @return
	 * @throws IOException
	 */
	public static List<Class<?>> scanPackage(Package packageName) throws IOException {
		String path = packageName.getName().replace('.', '/') + "/";
		return scanPackage(path);
	}

	/**
	 * 扫描包下所有类，暂不支持子包
	 * @param packg
	 * @return
	 * @throws IOException
	 */
	public static List<Class<?>> scanPackage(String packagePath) throws IOException {
		List<Class<?>> cls = new ArrayList<Class<?>>(0);
		if(!Strings.hasLength(packagePath)){
			return cls;
		}
		packagePath = packagePath.replace('.', '/') + "/";
		ClassLoader cl = Beans.class.getClassLoader();
		Enumeration<URL> urls = cl.getResources(packagePath);
		while(urls.hasMoreElements()){
			URL url = urls.nextElement();
			if("file".equalsIgnoreCase(url.getProtocol())){
				File file = new File(url.getPath());
				if(file.exists() && file.isDirectory()){
					File[] classFiles = file.listFiles();
					if(classFiles != null && classFiles.length > 0){
						for(File classFile : classFiles){
							String className = (packagePath.replace('/', '.') + classFile.getName().replace(".class", "")).replace("..", ".");
							try {
								cls.add(cl.loadClass(className));
							} catch (ClassNotFoundException e) {
								System.out.println("警告：没有发现类->"+className);;
							}
						}
					}
				}
			}
			else if("jar".equalsIgnoreCase(url.getProtocol())){
				JarFile jar = ((JarURLConnection)url.openConnection()).getJarFile();
				Enumeration<JarEntry> jarEntities = jar.entries();
				while(jarEntities.hasMoreElements()){
					JarEntry je = jarEntities.nextElement();
					String className = je.getName();
					if(className.startsWith(packagePath) && className.endsWith(".class")){
						className = je.getName().replace(".class", "").replace('/', '.');
						try {
							cls.add(cl.loadClass(className));
						} catch (ClassNotFoundException e) {
							System.out.println("警告：jar中没有发现类->"+className);;
						}
					}
				}
			}
		}
		return cls;
	}

	public static void main(String[] args) {
		HibernateGenericDao hgd = new HibernateGenericDao();
		System.out.println(hgd instanceof Serializable);
		Page page = new Page(1,2);
		System.out.println(page instanceof Serializable);
	}
}
