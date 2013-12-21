package org.howsun.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.howsun.util.IOs;

/**
 * <h1>功能描述：</h1>
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class Howsun {

	public final static String CONFIG_BASE ;
	
	public final static String LOG_FILE;
	
	public final static String NAME_SPACE;
	
	public final static Properties NAME_SPACE_PROPERTIES = new Properties();
	
	public final static String CONFIG_PREFIX = "/opt/hjf/";
	
	public final static String HOWSUN_CONFIG_FILE = "META-INF/howsun-config.properties";
	
	static{
		
		InputStream inputStream = null;
		try {
			inputStream = Howsun.class.getClassLoader().getResourceAsStream("META-INF/howsun-config.properties");
			if(inputStream != null){
				NAME_SPACE_PROPERTIES.load(inputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(inputStream != null){
				try {inputStream.close();} catch (Exception e2) {}
			}
		}
		
		NAME_SPACE  = NAME_SPACE_PROPERTIES.getProperty("namespace", "hjf_sample");
		CONFIG_BASE = new File("/", String.format("%s%s/",CONFIG_PREFIX, NAME_SPACE)).getAbsolutePath();
		LOG_FILE    = new File("/", String.format("%slogs/%s.log",CONFIG_PREFIX, NAME_SPACE)).getAbsolutePath();
		
		System.out.println("Howsun is working....");
	}
	
	public static String getConfigValue(String key, String defaultValue){
		return NAME_SPACE_PROPERTIES.getProperty(key, defaultValue);
	}
	
	public static Properties getHowsunConfig(Class<?> clazz){
		Properties properties = new Properties();
		InputStream in = null;
		try {
			URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
			String file = url.getFile();
			if(file.endsWith(".jar")){
				JarFile jarfile = new JarFile(url.getFile());
				JarEntry je = jarfile.getJarEntry(HOWSUN_CONFIG_FILE);
				in = jarfile.getInputStream(je);
			}
			else{
				in = new FileInputStream(new File(file,HOWSUN_CONFIG_FILE));
			}
		}
		catch (java.io.FileNotFoundException e) {
			in = clazz.getClassLoader().getResourceAsStream(HOWSUN_CONFIG_FILE);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(in != null){
				try {
					properties.load(in);
				} catch (Exception e2) {}
				IOs.close(in);
			}
		}
		return properties;
	}
}
