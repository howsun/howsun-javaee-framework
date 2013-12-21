package org.howsun.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DatesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = sdf1;
		SimpleDateFormat sdf3 = (SimpleDateFormat)sdf1.clone();
		System.out.println(sdf1);
		System.out.println(sdf2);
		System.out.println(sdf3);
		System.out.println(sdf1.toLocalizedPattern());
		System.out.println(sdf1.toPattern());

	}


	@Test
	public void testHHmmssBytes2second(){
		byte times[] = new byte[]{(byte)1,(byte)59,(byte)59};
		int result = Dates.HHmmssBytes2second(times);
		Assert.assertTrue(result == 3600 * 2 - 1);//等于两小时差一秒
	}

	@Test
	public void testHHmmssString2secoend(){
		Assert.assertTrue(Dates.HHmmssString2secoend("1:59:59") == 3600 * 2 - 1);//等于两小时差一秒

	}

	@Test
	public void testGetSecoendInIntraday(){
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		cal.set(Calendar.AM_PM, 0);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int result = Dates.getSecoendInIntraday();
		Assert.assertTrue(result == ((now - cal.getTimeInMillis())/1000) );
	}

	/**
	 * @see Dates.getSecoendInIntraday(Calendar calendar)
	 */
	@Test
	public void testGetSecoendInIntradayByCalendarParam(){
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = (Calendar)cal.clone();

		long now = cal.getTimeInMillis();
		cal.set(Calendar.AM_PM, 0);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		int result = Dates.getSecoendInIntraday(cal1);
		Assert.assertTrue(result == ((now - cal.getTimeInMillis())/1000) );
	}

	@Test
	public void testIsToday() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse("2013-02-1 23:59:59");
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		System.out.println(Dates.isToday(date));
		System.out.println(Dates.isThisWeek(date));
		System.out.println(Dates.isThisMonth(date));
	}
}
