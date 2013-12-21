package org.howsun.log;

import java.io.File;

import org.howsun.core.Howsun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 * Log warp：
 *
 * @author howsun(howsun.zhang@google.com)
 * @Date 2010-10-20
 * @version v0.2
 */
public final class LogFactory implements Log {

	private LogFactory(){};
	
	private Logger logger;
	
	public static final String LOG_CONFIG_FILE = "log4j.properties";
	
	static{
		try {
			Class.forName("org.howsun.core.Howsun");
			Log4jConfigurator.initConfigurator(new File(Howsun.CONFIG_BASE, LOG_CONFIG_FILE).getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Log getLog(Class<?> clazz){
		return getLog(clazz.getName());
	}
	
	public static Log getLog(String name){
		LogFactory log = new LogFactory();
		log.logger = LoggerFactory.getLogger(name);
		return log;
	}
	
	public static Log make() {
		Throwable t = new Throwable();
		StackTraceElement directCaller = t.getStackTrace()[1];
		return getLog( directCaller.getClassName() );
	}
	
	 /* (non-Javadoc)
	 * @see org.howsun.log.Log#getLoggerName()
	 */
	public String getLoggerName(){
		 return logger.getName();
	 }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isTraceEnabled()
	 */
	  public boolean isTraceEnabled(){
		  return logger.isTraceEnabled();
	  }
	    

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(java.lang.String)
	 */
	  public void trace(String msg){
		  logger.trace(msg);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(java.lang.String, java.lang.Object)
	 */
	  public void trace(String format, Object arg){
		  logger.trace(format, arg);
	  }


	   
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void trace(String format, Object arg1, Object arg2){
		  logger.trace(format, arg1, arg2);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(java.lang.String, java.lang.Object[])
	 */
	  public void trace(String format, Object[] argArray){
		  logger.trace(format, argArray);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(java.lang.String, java.lang.Throwable)
	 */ 
	  public void trace(String msg, Throwable t){
		  logger.trace(msg, t);
	  }
	 
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isTraceEnabled(org.slf4j.Marker)
	 */
	  public boolean isTraceEnabled(Marker marker){
		 return logger.isTraceEnabled(marker);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(org.slf4j.Marker, java.lang.String)
	 */
	  public void trace(Marker marker, String msg){
		  logger.trace(marker, msg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	  public void trace(Marker marker, String format, Object arg){
		  logger.trace(marker, format, arg);
	  }
	 
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void trace(Marker marker, String format, Object arg1, Object arg2){
		  logger.trace(marker, format, arg1, arg2);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	  public void trace(Marker marker, String format, Object[] argArray){
		  logger.trace(marker, format, argArray);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#trace(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */ 
	  public void trace(Marker marker, String msg, Throwable t){
		  logger.trace(marker, msg, t);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isDebugEnabled()
	 */
	  public boolean isDebugEnabled(){
		  return logger.isDebugEnabled();
	  }
	  
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(java.lang.String)
	 */
	  public void debug(String msg){
		  logger.debug(msg);
	  }
	  
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(java.lang.String, java.lang.Object)
	 */
	  public void debug(String format, Object arg){
		  logger.debug(format, arg);
	  }


	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void debug(String format, Object arg1, Object arg2){
		  logger.debug(format, arg1, arg2);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(java.lang.String, java.lang.Object[])
	 */
	  public void debug(String format, Object[] argArray){
		  logger.debug(format, argArray);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(java.lang.String, java.lang.Throwable)
	 */ 
	  public void debug(String msg, Throwable t){
		  logger.debug(msg, t);
	  }
	 
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isDebugEnabled(org.slf4j.Marker)
	 */
	  public boolean isDebugEnabled(Marker marker){
		  return logger.isDebugEnabled(marker);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(org.slf4j.Marker, java.lang.String)
	 */
	  public void debug(Marker marker, String msg){
		  logger.debug(marker, msg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	  public void debug(Marker marker, String format, Object arg){
		  logger.debug(marker, format, arg);
	  }
	 
	 
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void debug(Marker marker, String format, Object arg1, Object arg2){
		  logger.debug(marker, format, arg1, arg2);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	  public void debug(Marker marker, String format, Object[] argArray){
		  logger.debug(marker, format, argArray);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#debug(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */ 
	  public void debug(Marker marker, String msg, Throwable t){
		  logger.debug(marker, msg, t);
	  }
	  
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isInfoEnabled()
	 */
	  public boolean isInfoEnabled(){
		  return logger.isInfoEnabled();
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(java.lang.String)
	 */
	  public void info(String msg){
		  logger.info(msg);
	  }
	  

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(java.lang.String, java.lang.Object)
	 */
	  public void info(String format, Object arg){
		  logger.info(format, arg);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void info(String format, Object arg1, Object arg2){
		  logger.info(format, arg1, arg2);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(java.lang.String, java.lang.Object[])
	 */
	  public void info(String format, Object[] argArray){
		  logger.info(format, argArray);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(java.lang.String, java.lang.Throwable)
	 */
	  public void info(String msg, Throwable t){
		  logger.info(msg, t);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isInfoEnabled(org.slf4j.Marker)
	 */
	  public boolean isInfoEnabled(Marker marker){
		  return logger.isInfoEnabled(marker);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(org.slf4j.Marker, java.lang.String)
	 */
	  public void info(Marker marker, String msg){
		  logger.info(marker, msg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	  public void info(Marker marker, String format, Object arg){
		  logger.info(marker, format, arg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void info(Marker marker, String format, Object arg1, Object arg2){
		  logger.info(format, arg1, arg2);
	  }
	  
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	  public void info(Marker marker, String format, Object[] argArray){
		  logger.info(marker, format, argArray);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#info(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */ 
	  public void info(Marker marker, String msg, Throwable t){
		  logger.info(marker, msg, t);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isWarnEnabled()
	 */
	  public boolean isWarnEnabled(){
		  return logger.isWarnEnabled();
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(java.lang.String)
	 */
	  public void warn(String msg){
		  logger.warn(msg);
	  }

	 /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(java.lang.String, java.lang.Object)
	 */
	  public void warn(String format, Object arg){
		  logger.warn(format, arg);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(java.lang.String, java.lang.Object[])
	 */
	  public void warn(String format, Object[] argArray){
		  logger.warn(format, argArray);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void warn(String format, Object arg1, Object arg2){
		  logger.warn(format, arg1, arg2);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(java.lang.String, java.lang.Throwable)
	 */
	  public void warn(String msg, Throwable t){
		  logger.warn(msg, t);
	  }
	  

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isWarnEnabled(org.slf4j.Marker)
	 */
	  public boolean isWarnEnabled(Marker marker){
		  return logger.isWarnEnabled(marker);
	  }
	 
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(org.slf4j.Marker, java.lang.String)
	 */
	  public void warn(Marker marker, String msg){
		  logger.warn(marker, msg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	  public void warn(Marker marker, String format, Object arg){
		  logger.warn(marker, format, arg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void warn(Marker marker, String format, Object arg1, Object arg2){
		  logger.warn(marker, format, arg1, arg2);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	  public void warn(Marker marker, String format, Object[] argArray){
		  logger.warn(marker, format, argArray);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#warn(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */ 
	  public void warn(Marker marker, String msg, Throwable t){
		  logger.warn(marker, msg, t);
	  }
	  

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isErrorEnabled()
	 */
	  public boolean isErrorEnabled(){
		  return logger.isErrorEnabled();
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(java.lang.String)
	 */
	  public void error(String msg){
		  logger.error(msg);
	  }
	  
	 /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(java.lang.String, java.lang.Object)
	 */
	  public void error(String format, Object arg){
		  logger.error(format, arg);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void error(String format, Object arg1, Object arg2){
		  logger.error(format, arg1, arg2);
	  }

	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(java.lang.String, java.lang.Object[])
	 */
	  public void error(String format, Object[] argArray){
		  logger.error(format, argArray);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(java.lang.String, java.lang.Throwable)
	 */
	  public void error(String msg, Throwable t){
		  logger.error(msg, t);
	  }


	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#isErrorEnabled(org.slf4j.Marker)
	 */
	  public boolean isErrorEnabled(Marker marker){
		  return logger.isErrorEnabled(marker);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(org.slf4j.Marker, java.lang.String)
	 */
	  public void error(Marker marker, String msg){
		  logger.error(marker, msg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(org.slf4j.Marker, java.lang.String, java.lang.Object)
	 */
	  public void error(Marker marker, String format, Object arg){
		  logger.error(marker, format, arg);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(org.slf4j.Marker, java.lang.String, java.lang.Object, java.lang.Object)
	 */
	  public void error(Marker marker, String format, Object arg1, Object arg2){
		  logger.error(marker, format, arg1, arg2);
	  }
	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(org.slf4j.Marker, java.lang.String, java.lang.Object[])
	 */
	  public void error(Marker marker, String format, Object[] argArray){
		  logger.error(marker, format, argArray);
	  }

	  
	  /* (non-Javadoc)
	 * @see org.howsun.log.Log#error(org.slf4j.Marker, java.lang.String, java.lang.Throwable)
	 */ 
	  public void error(Marker marker, String msg, Throwable t){
		  logger.error(marker, msg, t);
	  }
}
