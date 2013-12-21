/**
* ========================================================
* 北京五八信息技术有限公司技术中心开发一部
* 日 期：2011-3-14 上午10:16:22
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * <h1>功能描述：</h1>
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public abstract class Dates {

	/**
	 * @deprecated 未考虑线程安全
	 */
	public final static SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @deprecated 未考虑线程安全
	 */
	public final static SimpleDateFormat DATE_FORMATTER      = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @deprecated 未考虑线程安全
	 */
	public final static SimpleDateFormat TIME_FORMATTER      = new SimpleDateFormat("HH:mm:ss");


	/***
	 *  线程安全的时间格式化工具
	 */
	private static final ThreadLocal<SimpleDateFormat> TIMESTAMP_FORMATER = new ThreadLocal<SimpleDateFormat>();

	/**
	 * @param date
	 * @return 返回yyyy-MM-dd HH:mm:ss SSS格式的时间
	 */
	public static String getDateFormated_yyyyMMddHHmmssSSS(Date date){
		return getDateFormated("yyyy-MM-dd HH:mm:ss SSS", date);
	}

	/**
	 * @param date
	 * @return 返回yyyy-MM-dd HH:mm:ss格式的时间
	 */
	public static String getDateFormated_yyyyMMddHHmmss(Date date){
		return getDateFormated("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * @param date
	 * @return 返回yyyy-MM-dd格式的时间
	 */
	public static String getDateFormated_yyyyMMdd(Date date){
		return getDateFormated("yyyy-MM-dd", date);
	}

	/**
	 * @param date
	 * @return 返回HH:mm:ss格式的时间
	 */
	public static String getDateFormated_HHmmss(Date date){
		return getDateFormated("HH:mm:ss", date);
	}

	/**
	 * 通用日期格式化方法，考虑到线程安全问题
	 * @param pattren
	 * @param date
	 * @return
	 */
	public static String getDateFormated(String pattern, Date date){
		SimpleDateFormat formater = TIMESTAMP_FORMATER.get();
		if(formater == null){
			formater = new SimpleDateFormat(pattern);
			TIMESTAMP_FORMATER.set(formater);
		}
		else if(!pattern.equals(formater.toPattern())){
			formater.applyPattern(pattern);
		}
		return formater.format(date);
	}

	/**
	 * 把字节数组中时分秒转换成以秒为单位的整数
	 *
	 * @param times
	 * @return
	 */
	public static int HHmmssBytes2second(byte[] times){
		int length = times.length - 1;
		int ret = 0;
		for (int i = 0; i <= length ; i++) {
			ret += ((times[i] < 0) ? 60 + times[i] : times[i]) * Math.pow(60, length - i);
		}
		return ret;
	}

	/**
	 * 时:分:秒 格式的字符串转换成整数秒
	 * @param time
	 * @return
	 */
	public static int HHmmssString2secoend(String onlyTime){
		Date date = null;
		try {
			date = TIME_FORMATTER.parse(onlyTime);
			return getSecoendInIntraday(date);
		} catch (Exception e) {e.printStackTrace();}
		return -1;
	}

	/**
	 * 将秒数转换成数组
	 * @param time
	 * @return
	 */
	public static byte[] secoends2HHmmssBytes(int secoends){
		byte[] ts = new byte[3];
		try {
			ts[0] = (byte)(secoends / 3600);
			ts[1] = (byte)(secoends % 3600 / 60);
			ts[2] = (byte)(secoends % 3600 % 60);
		} catch (Exception e) {e.printStackTrace();}
		return ts;
	}

	/**
	 * 获取今天当前的秒数
	 * @return
	 */
	public static int getSecoendInIntraday(){
		return getSecoendInIntraday(Calendar.getInstance(Locale.CHINA));
	}


	/**
	 * 获取某天至现在的秒数
	 * @param calendar
	 * @return
	 */
	public static int getSecoendInIntraday(Calendar calendar){
		if(calendar == null)
			calendar = Calendar.getInstance();
		return (int)( ( calendar.getTimeInMillis() - getSecoendOfBegin(calendar, Calendar.DATE) ) / 1000 );
	}

	/**
	 * 获取某年的秒数
	 * @param calendar
	 * @return
	 */
	public static int getSecoendInThisYear(Calendar calendar){
		if(calendar == null)
			calendar = Calendar.getInstance();
		return (int)( ( calendar.getTimeInMillis() - getSecoendOfBegin(calendar, Calendar.YEAR) ) / 1000 );
	}


	/**
	 * 获取当天当前的秒数
	 * @param date
	 * @return
	 */
	public static int getSecoendInIntraday(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getSecoendInIntraday(cal);
	}

	/**
	 * 获取某年的秒数
	 * @param calendar
	 * @return
	 */
	public static int getSecoendInThisYear(){
		Calendar calendar = Calendar.getInstance();
		return (int)( ( calendar.getTimeInMillis() - getSecoendOfBegin(calendar, Calendar.YEAR) ) / 1000 );
	}

	/**
	 * 获取某时间的开始毫秒数
	 *
	 * @param calendar 时间
	 * @param calendarField 类型，只支持年开始的秒数，月开始的秒数，日开始的秒数，小时开始的秒数，分钟开始的秒数
	 * @return 毫秒数
	 */
	public static long getSecoendOfBegin(Calendar calendar, int calendarField){

		Calendar cal = calendar == null ? Calendar.getInstance() : (Calendar)calendar.clone();
		cal.set(Calendar.AM_PM, 0);

		switch (calendarField) {
			case Calendar.YEAR:
				cal.set(cal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
			break;
			case Calendar.MONTH:
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
				break;
			case Calendar.DATE:
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
				break;
			case Calendar.HOUR:
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),  cal.get(Calendar.HOUR), 0, 0);
				break;
			case Calendar.MINUTE:
				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),  cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), 0);
				break;
		default:
			throw new RuntimeException("不支持的时间模式。");
		}

		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	/**
	 * 获取参数1指定时间类型的开始毫秒数
	 * 例如今天最开始的秒数为getSecoendOfBegin(Calendar.DATE)
	 * @param calendarField
	 * @return 毫秒数
	 */
	public static long getSecoendOfBegin(int calendarField){
		return getSecoendOfBegin(null, calendarField);
	}

	/**
	 * 获取今天字符串格式的日期
	 * @param formatter
	 * @return
	 */
	public static String getDateString(SimpleDateFormat formatter){
		return formatter.format(new Date());
	}

	public static String getDate(Calendar time, String format){
		if(time == null) return "";
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(new SimpleDateFormat(format).format(time.getTime()));
		} catch (Exception e) {
			sb.append(new SimpleDateFormat("yyyy-MM-dd").format(time.getTime()));
		}

		return sb.toString();
	}

	public static String getDate(Calendar time){
		return getDate(time,"yyyy-MM-dd");
	}


	@Deprecated
	public static void setCalendar(Calendar time, int field, int amount){
		if(time == null)
			time = Calendar.getInstance(Locale.CHINA);
		time.add(field, amount);
		//return time;
	}

	public static Calendar cloneCalendar(Calendar time, int field, int amount){
		Calendar newTime = time== null ? Calendar.getInstance(Locale.CHINA) : (Calendar)time.clone();
		newTime.add(field, amount);
		return newTime;
	}


	/**
	 * 今天以后的日期离今天还剩的天数
	 * @param oldTime
	 * @return
	 */
	public static long amountLeaveAfter(Calendar oldTime){
		Calendar newTime = Calendar.getInstance();
		return ( oldTime.getTimeInMillis() - newTime.getTimeInMillis() ) / 1000 / 60 / 60 / 24;

	}

	/**
	 * 今天以前的日期离今天还剩的天数
	 * @param oldTime
	 * @return
	 */
	public static long amountLeaveBefore(Calendar oldTime){
		Calendar newTime = Calendar.getInstance();
		return (newTime.getTimeInMillis()-oldTime.getTimeInMillis())/1000/60/60/24;

	}

	/**
	 *
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getDateByOffset(Date date, int amount, TimeUnit timeUnit){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (timeUnit) {
		case DAYS:
			calendar.add(Calendar.DATE, amount);
			break;
		case HOURS:
			calendar.add(Calendar.HOUR, amount);
			break;
		case MINUTES:
			calendar.add(Calendar.MINUTE, amount);
			break;
		case SECONDS:
			calendar.add(Calendar.SECOND, amount);
			break;
		case MILLISECONDS:
			calendar.add(Calendar.MILLISECOND, amount);
			break;

		default:
			break;
		}
		return calendar.getTime();
	}

	/**
	 * 求真实年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static int getAge(Date birthDay){
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }

	public static boolean isToday(Calendar date){
		Calendar today = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);
		today.setFirstDayOfWeek(Calendar.MONDAY);
		return     date.get(Calendar.YEAR)  == today.get(Calendar.YEAR)
				&& date.get(Calendar.MONTH) == today.get(Calendar.MONTH)
				&& date.get(Calendar.DATE)  == today.get(Calendar.DATE);
	}
	public static boolean isThisWeek(Calendar date){
		Calendar today = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);
		today.setFirstDayOfWeek(Calendar.MONDAY);
		return     date.get(Calendar.YEAR)         == today.get(Calendar.YEAR)
				&& date.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR);
	}
	public static boolean isThisMonth(Calendar date){
		Calendar today = Calendar.getInstance();
		date.setFirstDayOfWeek(Calendar.MONDAY);
		today.setFirstDayOfWeek(Calendar.MONDAY);
		return     date.get(Calendar.YEAR)  == today.get(Calendar.YEAR)
				&& date.get(Calendar.MONTH) == today.get(Calendar.MONTH);
	}
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse("1976-11-21");
		System.out.println(getAge(d));

	}

}
