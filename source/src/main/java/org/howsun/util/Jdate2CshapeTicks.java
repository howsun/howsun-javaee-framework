/**
 * ========================================================
 * 北京五八信息技术有限公司技术中心开发一部
 * 日 期：2011-4-2 下午01:02:55
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 功能描述：C#的ticks值与Java的Date对象互相转换
 * 如果算出的结果有误，则是时区timeZone不对，可以调用setTimeZone()方法设置时区
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class Jdate2CshapeTicks {

	private static final long TICKS_AT_EPOCH        = 621355968000000000L;
	
	private static final long TICKS_PER_MILLISECOND = 10000; 
	
	private static final SimpleDateFormat sdf       = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private static TimeZone timeZone = TimeZone.getDefault();

	/**
	 * 将C#的ticks值转换成Java的Date对象
	 * @param ticks
	 * @return
	 */
	public static Date fromDnetTicksToJdate(long ticks){
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis((ticks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND);
		calendar.setTimeInMillis(calendar.getTimeInMillis() - calendar.getTimeZone().getRawOffset());
		return calendar.getTime();
	}

	/**
	 * 将日期的字符串值转换成C#的ticks值
	 * @param time
	 * @return
	 */
	public static long getCShapeTicks(String time){
		long result = -1;
		Date date = null;
		try {
			date = sdf.parse(time);
			Calendar calendar = Calendar.getInstance(timeZone);
			calendar.setTime(date);
			return (calendar.getTimeInMillis() + calendar.getTimeZone().getRawOffset()) * TICKS_PER_MILLISECOND + TICKS_AT_EPOCH;
		} catch (Exception e) {

		}
		return result;
	}

	/**
	 * 将Java日期对象转换成C#的ticks值
	 * @param jDate
	 * @return
	 */
	public static long getCShapeTicks(Date jDate){
		long result = -1;
		try {
			Calendar calendar = Calendar.getInstance(timeZone);
			calendar.setTime(jDate);
			return (calendar.getTimeInMillis() + calendar.getTimeZone().getRawOffset()) * TICKS_PER_MILLISECOND + TICKS_AT_EPOCH;
		} catch (Exception e) {

		}
		return result;
	}


	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		String[] cc = {
				"634264332702330000|2010-11-27 05:41:10.233",
				"634264332702070000|2010-11-27 05:41:10.207",
				"634264332701800000|2010-11-27 05:41:10.180",
				"634264332701530000|2010-11-27 05:41:10.153",
				"634264332701270000|2010-11-27 05:41:10.127"};
		
		for (int i = 0; i < cc.length; i++) {
			String[] rr = cc[i].split("\\|");
			long ticks = Long.parseLong(rr[0]);
			long tf = getCShapeTicks(rr[1]);
			System.out.print(ticks == tf);
			System.out.print("\t");
			System.out.print(sdf.format(fromDnetTicksToJdate(ticks)).equals(rr[1]));
			System.out.print("\t");
			long newTicks = getCShapeTicks(sdf.parse(rr[1]));
			System.out.println(ticks==newTicks);
		}
	}

	public static void setTimeZone(TimeZone timeZone) {
		Jdate2CshapeTicks.timeZone = timeZone;
	}
}
