package org.howsun.log;

import org.junit.Test;

public class LogTest {

	static Log log = LogFactory.getLog(LogTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		Logger root = LogManager.getRootLogger();
		System.out.println("isDisabled=" + LogManager.getLoggerRepository().isDisabled(Level.ALL_INT));
		Enumeration ls = LogManager.getCurrentLoggers();
		//Logger loger = LoggerFactory.getLogger("ROOT");
//		/Logger.getDefaultHierarchy()
		while(ls.hasMoreElements()){
			
			Logger l = (Logger)ls.nextElement();
			Enumeration as = l.getAllAppenders();
			System.out.println("hasmore:"+as.hasMoreElements());
			while(as.hasMoreElements()){
				System.out.println("appender:"+as.nextElement());
			}
			System.out.println(l.getAppender("org.apache.log4j.ConsoleAppender"));
			System.out.println("=="+l);
		}
		
		log.info("----------------");
		Enumeration s = LogManager.getLoggerRepository().getCurrentCategories();
		while(s.hasMoreElements()){
			Logger l = (Logger)s.nextElement();
			System.out.println("=="+l);
		}
		*/
	}

	@Test
	public void test(){log.info("log...");}
}
