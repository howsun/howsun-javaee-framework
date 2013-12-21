
package org.howsun.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>功能描述：</h1>
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public abstract class Numbers {

	private static final int DEF_DIV_SCALE = 10;

	/**
	 *   判断是否为数字组成的字串
	 *   @param   validString   要判断的字符串
	 *   @return   boolen值，true或false
	 */
	public static boolean isNumber(String validString){
		if(!Strings.hasLengthAfterTrimWhiteSpace(validString)){
			return false;
		}
		byte[] tempbyte = validString.getBytes();
		for(int i = 0; i < validString.length(); i++){
			if( ( tempbyte[i] == 45 ) && ( i == 0 ) ){
				continue;
			}
			if((tempbyte[i] < 48) || (tempbyte[i] > 57)){
				return false;
			}
		}
		return true;
	}

	/**
	 *   判断是否为数字及最多一个小数点组成的实型数
	 *   @param   validString   要判断的字符串
	 *   @return   boolen值，true或false
	 */
	public static boolean isReal(String validString){
		byte[] tempbyte = validString.getBytes();
		int a = 0;   //0-9的个数
		int b = 0;   //小数点的个数
		int c = 0;    //其他字符
		int d = 0;   //符号
		for(int i=0;i<validString.length();i++){
			if((tempbyte[i] == 45)&&(i==0)){
				d++;
				continue;
			}
			if((tempbyte[i]>=48)&&(tempbyte[i]<=57)){
				a++;
				continue;
			}

			if(tempbyte[i] == 46){//这是小数点
				b++;
				continue;
			}
			c++;
		}
		if(c>0||b>1){
			return false;
		}
		return true;
	}


	/**
	 *   判断是否为数字组成的费款所属期
	 *   @param   validString   要判断的字符串
	 *   @return   boolen值，true或false
	 */
	public static boolean isFkssq(String  validString){
		if(!isNumber(validString)){
			//如果费款所属期内不全是数字则有误
			return   false;
		}
		if(validString.length() != 6){
			//如果费款所属期内不是六位数则有误
			return false;
		}
		int i_year = new Integer(validString.substring(0,4)).intValue();
		int i_month = new Integer(validString.substring(4,6)).intValue();

		if(i_year > 0 && i_year < 9999 && i_month > 0 && i_month < 13){
			return true;
		}
		return false;
	}

	/**
	 *   判断是否符合定长条件
	 *   @param   validString   要判断的字符串
	 *   @long1   最小长度
	 *   @long2   最大长度
	 *   @return   boolen值，true或false
	 */
	public static boolean isLong(String validString,int long1,int long2){
		if(validString.length()>long2||validString.length()<long1){   //字符串的长度有误
			return   false;
		}
		return   true;
	}


	/**
	 * 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}


	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}



	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * 定精度，以后的数字四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}



	/**
	 * 提供精确的小数位四舍五入处理。
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	/**
	 * 求出整数的位数，例如1000是四位数
	 * @param number
	 * @return
	 */
	public static int getIntegerDigit(Integer number){
		int digit = 1;
		while(number > 9){
			number = number/10;
			digit++;
		}
		return digit;
	}

	/**
	 * 填充数
	 * @param source
	 * @return
	 */
	public static List<Integer> fillNumber(List<Integer> source){
		List<Integer> s = new ArrayList<Integer>();
		Integer[] ss = new Integer[source.size()];
		ss = source.toArray(ss);
		int start = 0, end = 0;
		for (int i = 0; i < ss.length; i++) {
			s.add(start);
			end = ss[i];
			for (int j = start+1; j < end; j ++) {
				s.add(j);
			}
			start = ss[i];
		}
		return s;
	}

	/**
	 *
	 * @param src
	 * @param defaultValue
	 * @return
	 */
	public static Long parseLong(String src, Long defaultValue){
		try {
			return Long.parseLong(src);
		}
		catch (Exception e) {
			return defaultValue;
		}
	}

	public static Integer parseInt(String src, Integer defaultValue){
		try {
			return Integer.parseInt(src);
		}
		catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 求百分比，
	 * @param a
	 * @param b
	 * @return 返回带%号的字符串
	 */
	public static String scaleForDisplay(double a, double b){
		java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(0);// 小数点后保留几位
		return nf.format(a/b);
	}
	/**
	 * 求百分比，
	 * @param a
	 * @param b
	 * @return 返回参数相除后商的100倍值
	 */
	public static int scale(double a, double b){
		if(b == 0){
			return 0;
		}
		BigDecimal start = new BigDecimal(String.valueOf(a)),
		             end = new BigDecimal(String.valueOf(b)) ;
		return (int)(start.divide(end, 2, RoundingMode.HALF_UP).doubleValue() * 100);
	}

	public static boolean thanZero(Long longNumber){
		return longNumber != null && longNumber > 0;
	}

	public static boolean thanZero(Integer intNumber){
		return intNumber != null && intNumber > 0;
	}

	public static boolean thanZero(BigDecimal bigDecimalNumber){
		return bigDecimalNumber != null && bigDecimalNumber.signum() > 0;
	}

	public static boolean minOfBigDecimal(BigDecimal min, BigDecimal max){
		return min != null && max != null && min.min(max).equals(min);
	}

	public static boolean maxOfBigDecimal(BigDecimal max, BigDecimal min){
		return min != null && max != null && max.max(min).equals(max);
	}


	public static void main(String[] args) {
		System.out.println(scale(1, 0));
		System.out.println(scaleForDisplay(1, 0));
	}
}
