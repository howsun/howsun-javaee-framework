/**Log4jConfigurator.java*/
package org.howsun.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.howsun.core.Howsun;

/**
 * Log4j配置
 *
 * @author howsun (zjh@58.com)
 * @version v0.1
 */
public class Log4jConfigurator {

	/**日志记录目录
	public static final File LOG_RECORD_FOLDER = new File("/","/opt/hjf/logs");
	**/
	
	Log4jConfigurator(){}
	
	/**
	 * 自动搜索配置文件
	 
	public static void initConfigurator(){
		File log4jconfig = Resources.seekFile("log4j.properties");
		if(log4jconfig == null){
			log4jconfig = Resources.seekFile("log.properties");
		}
		if(log4jconfig != null){
			initConfigurator(log4jconfig.getAbsolutePath());
		}else{
			System.out.println("警告：没有发现日志配置文件。");
		}
	}
	*/
	
	/**
	 * 初始化Log4j配置
	 * @param configFile
	 */
	public static void initConfigurator(String configFile){
		
		File file = new File(configFile);
		if(!file.exists()){
			copyDefaultLog4jPropertiyFile(file);
			file = new File(configFile);
		}
		Properties log4j_properties = new Properties();
		Reader reader = null;
		try {
			reader = new FileReader(file);
			log4j_properties.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		//String logRecordFile = configFile.substring(0, configFile.lastIndexOf('/')) + LOG_RECORD_FOLDER + "/logrecord.log";
		log4j_properties.setProperty("log4j.appender.file.File", Howsun.LOG_FILE);
		PropertyConfigurator.configure(log4j_properties);
	}
	
	/**
	 * 如果配置文件夹中没有配置文件夹，将从模板中拷贝配置文件
	 * @param propertyFile
	 */
	private static void copyDefaultLog4jPropertiyFile(File propertyFile){
		InputStream in = null;
		OutputStream out = null;
		try {
			if(!propertyFile.getParentFile().exists()){
				propertyFile.getParentFile().mkdirs();
			}
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/template/log4j.template");
			out = new FileOutputStream(propertyFile);
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(out != null){
				try {
					out.flush();
					out.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(in != null){
				try {
					in.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Log log = LogFactory.getLog("howsun");
		log.info("----");
	}
}
