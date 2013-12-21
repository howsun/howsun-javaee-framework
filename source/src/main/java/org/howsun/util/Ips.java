/**
* ========================================================
* 北京五八信息技术有限公司技术中心开发一部
* 日 期：2011-5-10 下午01:59:00
* 作 者：张纪豪
* 版 本：1.0.0
* ========================================================
* 修订日期                        修订人                     描述
*
*/

package org.howsun.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.howsun.log.Log;
import org.howsun.log.LogFactory;

/**
 * 功能描述：IPv4工具
 *
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public abstract class Ips {

	private static final Log logger = LogFactory.getLog(Ips.class);

	/**
     * 从ip的字符串形式得到字节数组形式
     * @param ip 字符串形式的ip
     * @return 字节数组形式的ip
     */
    public static byte[] getIpByteArrayFromString(String ip) {
        byte[] ret = new byte[4];
        StringTokenizer st = new StringTokenizer(ip, ".");
        try {
            ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
            ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xFF);
        } catch (Exception e) {
          logger.warn("从ip的字符串形式得到字节数组形式报错", e);
        }
        return ret;
    }


    /**
     * @param ip ip的字节数组形式
     * @return 字符串形式的ip
     */
    public static String getIpStringFromBytes(byte[] ip) {
    	return new StringBuilder()
	    	.append(ip[0] & 0xFF).append('.')
	    	.append(ip[1] & 0xFF).append('.')
	    	.append(ip[2] & 0xFF).append('.')
	    	.append(ip[3] & 0xFF)
	    	.toString();
    }


    /**
     * 整数转换成IP地址
     * @param value
     * @return
     */
    public static String long2ip(long value){
		StringBuffer ip = new StringBuffer();
		ip.append(String.valueOf((value >>> 24)))             .append("."); //直接右移24位
		ip.append(String.valueOf((value & 0x00FFFFFF) >>> 16)).append("."); //将高8位置0，然后右移16位
		ip.append(String.valueOf((value & 0x0000FFFF) >>> 8)) .append("."); //将高16位置0，然后右移8位
		ip.append(String.valueOf((value & 0x000000FF)));                    //将高24位置0
		return ip.toString();
	}

    /**
     * ip地址转换成整数
     * @param ip
     * @return
     */
	public static long ip2long(String ip){
		long result = 0;
		if(Strings.hasLength(ip)){
			String section[] = ip.split("\\.");
			if(section.length > 2){
				for (int i = 0; i < section.length; i++) {
					result += Long.parseLong(section[i]) << ((section.length - i - 1) * 8);
				}
			}
		}
		return result;
	}

	public static Set<String> IP_HEADERS = new HashSet<String>(Arrays.asList("x-forwarded-for", "x-real-ip", "proxy-client-ip", "wl-proxy-client-ip"));

	/**
	 * 获取IP地址<br>
	 * 首先判断是不是测试IP地址，再判断是不是代理，最后才正常获取
	 *
	 * 2013-03-12：增加Nginx反向代理识别，不过Nginx服务端需要加上配置，代码如下：
	 *  proxy_set_header Host $host;
     *  proxy_set_header X-real-ip $remote_addr;
     *  proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	 *
	 * @param request
	 * @return
	 */
	public static long getIp(HttpServletRequest request) {

		String ip = request.getParameter("ip");

		if(!Strings.hasLength(ip)){
			ip = request.getRemoteAddr();
		}

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equals(ip)) {//Nginx代理后IP为本机地址
			Enumeration<?> headerNames = request.getHeaderNames();
			while(headerNames.hasMoreElements()){
				String headerName = String.valueOf(headerNames.nextElement());
				if(IP_HEADERS.contains(headerName.toLowerCase())){
					ip = request.getHeader(headerName);
					if(Strings.hasLengthBytrim(ip) && !"unknown".equalsIgnoreCase(ip)) {
						break;
					}
				}
			}
			if(ip != null && ip.indexOf(",") > 0){
				ip = ip.split(",")[0];
			}
		}

		return ip2long(ip);
	}

	public static void main(String[] args) {
		byte b[] = {(byte)127,(byte)0,(byte)0,(byte)1};
		System.out.println(ip2long("127.0.0.1"));
		System.out.println(long2ip(2130706433L));
		System.out.println(getIpStringFromBytes(b));
		System.out.println(ip2long(getIpStringFromBytes(b)));
	}
}
