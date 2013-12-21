/**
 * 版本修订记录
 * 创建：2013-8-3
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述：
 * @author howsun
 * @version 3.0
 * Building Time 2013-8-3
 * 
 */
public abstract class Times {

	public static String getDisplayTime(Date date){

		long millisecond = System.currentTimeMillis() - date.getTime();   

		//现在时间与参数时间之间的秒数，简称"差秒"
		long second = millisecond / 1000;

		//5分钟内都为刚刚
		if (second < 5 * 60) {   
			return "刚刚";
		}
		
		//1小时内为"x分钟前"
		if (second < 1 * 3600) {   
			long minute = second / 60;   
			return minute + "分钟前";   
		}
		
		
		//如果"差秒"在1小时以上，3小时以内
		if(second <= 3 * 3600){
			long hour = (second / 60) / 60;
			return hour + "小时前";
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("H:m");
		
		//今天最开始的秒数
		long todayFirstSecond = Dates.getSecoendOfBegin(Calendar.DATE) / 1000;
		
		//如果参数秒数大于今天最开始的秒数，则是今天的时间 
		long dateSecond = date.getTime() / 1000;
		if (dateSecond >= todayFirstSecond) { 
			return "今天" + sdf.format(date);
		}
		
		
		// 如果参数秒数小于今天最开始的秒数，则是今天以前的时间，如昨天 
		long second1 = todayFirstSecond - dateSecond;
		if (second1 <= 86400) {
			return "昨天" + sdf.format(date);
		}
		
		if (second1 <= 86400 * 2) {
			return "前天" + sdf.format(date);
		}
		
		if (second1 <= 86400 * 3) {
			long day = ((second / 60) / 60) / 24;   
			return day + "天前";   
		}
		
		long yearFirstSecond = Dates.getSecoendOfBegin(Calendar.YEAR) / 1000;
		long second2 = yearFirstSecond - dateSecond;
		if (second2 < 0) {
			sdf.applyPattern("M-d H:m");
			return sdf.format(date);
		} 
		
		sdf.applyPattern("yyyy-M-d H:m");
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		date.setTime(date.getTime()- 1 * 60 * 1000L);
		System.out.println(getDisplayTime(date));
	}
}
