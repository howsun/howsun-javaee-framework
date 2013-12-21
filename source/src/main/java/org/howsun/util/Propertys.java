
package org.howsun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;

/**
 * Property工具类
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public abstract class Propertys {

	private static final Log log = LogFactory.getLog(Propertys.class);



	public static void loadPropertiesInMetaInf(Properties properties, String fileName){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		loadProperties(properties, cl.getResourceAsStream("META-INF/" + fileName));
	}

	public static Properties loadProperties(File file){
		Properties properties = new Properties();
		try {
			loadProperties(properties, new FileInputStream(file));
		}
		catch (Exception e) {
			log.info("读取Property文件出错：",e);
		}

		return properties;
	}

	public static Properties loadPropertiesInMetaInf(String fileName){
		Properties properties = new Properties();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		loadProperties(properties, cl.getResourceAsStream("META-INF/" + fileName));
		return properties;
	}

	public static void loadProperties(Properties properties, String path, String fileName){
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(new File(path, fileName));
			loadProperties(properties, fin);
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
		} finally{
			if(fin != null){
				try {
					fin.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	public static void loadProperties(Properties properties, InputStream in){
		try {
			properties.load(in);
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally{
			if(in != null){
				try {
					in.close();
				} catch (Exception e2) {}
			}
		}
	}

	public static Integer readPropertiesIntegerValue(Properties properties, String key, Integer defaultValue){
		String v = properties.getProperty(key);
		if(v != null){
			try {
				return Integer.parseInt(v);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return defaultValue;
	}

	public static String readPropertiesStringValue(Properties properties, String key, String defaultValue){
		return properties.getProperty(key, defaultValue);
	}

	public static Boolean readPropertiesBooleanValue(Properties properties, String key, Boolean defaultValue){
		String v = properties.getProperty(key);
		if(v != null){
			try {
				return Boolean.parseBoolean(v);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return defaultValue;
	}
}
