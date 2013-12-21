
package org.howsun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.howsun.core.Howsun;


/**
 * 功能描述：
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public abstract class Resources {

	private static String currentPath;

	private static final Map<String, File> PATHS = new HashMap<String, File>();

	//private static final Log log = LogFactory.getLog(Resources.class);


	/**
	 * 在当前类路径和工作区下搜索指定文件
	 * @param fileName
	 * @return
	 */
	public static File seekFile(String fileName){
		File result = PATHS.get(fileName);

		if(result == null){

			while(true){
				
				//1、在name-space下搜索
				if(Howsun.CONFIG_BASE != null){
					File name_space_path = new File(Howsun.CONFIG_BASE);
					if(name_space_path.exists()){
						result = getFile(name_space_path, fileName);
						if(result != null){
							break;
						}
					}
				}
				
				//2、在类路径下获取
				ClassLoader classLoader = Resources.class.getClassLoader();
				URL url = classLoader.getResource("META-INF");
				if(url != null){
					File file = new File(url.getPath());
					if(file.exists()){
						result = getFile(file, fileName);
						if(result != null){
							break;
						}
					}
				}

				url = classLoader.getResource("");
				if(url != null){
					File file = new File(url.getPath());
					if(file.exists()){
						result = getFile(file, fileName);
						if(result != null){
							break;
						}
					}
				}

				//3、在工程目录下搜索
				result = getFile(new File(System.getProperty("user.dir")), fileName);
				if(result != null && result.exists()){
					break;
				}
				
				//4在当前类路径下搜索
				result = getFile(new File(getCurrentPath()), fileName);
				break;
			}

			if(result != null){
				PATHS.put(fileName, result);
			}
			
		}

		return result;
	}


	/**
	 * 在类路径、工作区、config目录下获取
	 * @param filename
	 * @return
	 */
	public static InputStream seekFileAndGenerateInputStream(String fileName){
		InputStream inputStream = null;
		while(true){

			//类路径
			ClassLoader cl = Resources.class.getClassLoader();
			URL url = cl.getResource(fileName);
			if(url != null){
				inputStream = cl.getResourceAsStream(fileName);
				if(inputStream != null)
					break;
			}

			url = cl.getResource("META-INF/" + fileName);
			if(url != null){
				inputStream = cl.getResourceAsStream(url.getFile());
				if(inputStream != null)
					break;
			}

			//当前工作区
			File file = seekFile(fileName);
			if(file != null && file.exists()){
				try {
					inputStream = new FileInputStream(file);
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		}
		return inputStream;
	}

	public static File getFile(File root, String fileName){
		if(root.isDirectory()){
			File[] files = root.listFiles();
			if(files != null)
				for(File f : files){
					File found = getFile(f, fileName);
					if(found != null){
						return found;
					}
				}
		}
		return fileName.equalsIgnoreCase(root.getName()) ? root : null;
	}



	/**
	 * 获取包所在的目录
	 * @return
	 */
	public static String getCurrentPath() {

		if(currentPath == null) {
			try {
				Class<?> caller = getCaller();
				if(caller == null) {
					caller = Resources.class;
				}
				currentPath = caller.getProtectionDomain().getCodeSource().getLocation().getPath();
			} catch (Exception ex) {
				//log.info("获取当前工作路径出错：系统将采用${user.dir}目录", ex);
				currentPath = System.getProperty("user.dir");
				return currentPath;
			}

			/*
			if(log.isDebugEnabled()){
				log.debug(String.format("当前工作路径为：%s", currentPath));
			}
			 */
			File file = new File(currentPath);

			if(file.exists()){
				currentPath = file.isDirectory() ? file.getPath() : file.getParentFile().getPath();
			}
			else{
				currentPath = currentPath.replaceFirst("file:/", "");
				currentPath = currentPath.replaceAll("!/", "");
				currentPath = currentPath.replaceAll("\\\\", "/");
				currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));

				if(currentPath.startsWith("/")){
					String osName = System.getProperty("os.name").toLowerCase();
					if(osName.indexOf("window") >=0){
						currentPath = currentPath.substring(1);
					}
				}
			}
		}

		return currentPath;
	}


	public static Class<?> getCaller() {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		if(stack.length < 3) {
			return Resources.class;
		}
		String className = stack[2].getClassName();
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println(String.format("%s类未找到", className));
			//log.info(String.format("%s类未找到", className), e);
		}
		return null;
	}


	public static String getCurrentPath(Class<?> cls) {
		String path = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.replaceFirst("file:/", "");
		path = path.replaceAll("!/", "");
		path = path.substring(0, path.lastIndexOf("/"));
		if(path.substring(0,1).equalsIgnoreCase("/")){
			String osName = System.getProperty("os.name").toLowerCase();
			if(osName.indexOf("window") >=0){
				path = path.substring(1);
			}
		}
		return path;
	}

	public static void main(String[] args) throws Exception {

	}
}
