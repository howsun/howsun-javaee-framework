/**
 *
 */
package org.howsun.util;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.howsun.util.security.Codings;

/**
 * 说明:<br>
 * Web层工具
 * @author 张纪豪
 * @version
 * Build Time Feb 23, 2009
 */
public class Servlets {


	/**
	 * 从Request对象中取出字符串
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getStringByRequestParameter(HttpServletRequest request, String name, String defaultValue){
		if(request == null) return defaultValue;
		String str = request.getParameter(name);
		return str == null ?  defaultValue : str.trim();
	}

	/**
	 * 从Request对象中取出布尔值
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanByRequestParameter(HttpServletRequest request, String name, boolean defaultValue){
		if(request == null || request.getParameter(name) == null)
			return defaultValue;
		try {
			return Boolean.parseBoolean(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 从Request对象中取出整数
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static Integer getIntByRequestParameter(HttpServletRequest request, String name, Integer defaultValue){
		if(request == null) return defaultValue;
		try {
			return Integer.parseInt(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 编码URL
	 * @param request
	 * @return
	 */
	public static String enUrlByRequest(HttpServletRequest request){
		StringBuffer url = new StringBuffer(request.getAttribute("javax.servlet.forward.servlet_path") == null ? request.getRequestURL() : (String)request.getAttribute("javax.servlet.forward.servlet_path"));
		String parm = param(request);
		if(Strings.hasLength(parm)){
			url.append("?").append(parm);
		}
		return new String(Codings.base64Encode(url.toString().getBytes()));
	}

	/**
	 * base64编码
	 * @param url
	 * @return
	 */
	public static String enStringToBase64(String url){
		return new String(Codings.base64Encode(url.getBytes()));
	}

	/**
	 * base64解码
	 * @param url
	 * @return
	 */
	public static String deBase64ToString(String url){
		return deUrl(url);
	}

	/**
	 * 还原URL地址
	 * @param url
	 * @return
	 */
	public static String deUrl(String url){//这个是还原方法
		if(url == null) return "";
		try {
			return new String(Codings.base64Decode(url));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取URL上的参数
	 * @param request
	 * @return
	 */
	public static String param(HttpServletRequest request){
		StringBuffer url = new StringBuffer("");
		Enumeration<?> param = request.getParameterNames();//得到所有参数名
		while(param.hasMoreElements()){
			String pname = param.nextElement().toString();
			url.append(pname).append("=").append(request.getParameter(pname)).append("&");
		}
		if(url.toString().endsWith("&")){
			url.deleteCharAt(url.length()-1);
		}
		return url.toString();
	}


	/**
	 * 检查验证码
	 * @param scope //作用域
	 * @param validateCode  //用户输入的验证码
	 * @return
	 */
	public static Boolean isValidateCode(Object scope, String validateCode){
		if(scope instanceof HttpSession){
			HttpSession session = (HttpSession) scope;
			String code = (String)session.getAttribute("validateCode");
			return code == null ? false : code.equals(validateCode);
		}

		if(scope instanceof HttpServletRequest){
			HttpServletRequest request = (HttpServletRequest) scope;
			return isValidateCode(request.getSession(),validateCode);
		}
		return false;
	}

	public static Cookie getCookie(HttpServletRequest request, String name){
		Cookie cooikes[] = request.getCookies();
		if(cooikes != null)
		for(Cookie cookie : cooikes){
			if(name.equals(cookie.getName())){
				return cookie;
			}
		}
		return null;
	}
	public static String getCookieValue(HttpServletRequest request, String name){
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	public static void setCookie(HttpServletResponse response, Cookie cookie){
		response.addCookie(cookie);
	}

	/**
	 *
	 * @param response
	 * @param name
	 * @param value
	 * @param domain
	 * @param expiry 有效期(秒)
	 * @param uri
	 */
	public static void setCookieValue(HttpServletResponse response, String name, String value, String domain, Integer expiry, String uri){
		Cookie cookie = new Cookie(name, value);
		if(domain != null){
			cookie.setDomain(domain);
		}
		if(expiry != null){
			cookie.setMaxAge(expiry);
		}
		if(uri != null){
			cookie.setPath(uri);
		}
		response.addCookie(cookie);
	}

	/**
	 * 防止站外连接
	 * @param request
	 * @return
	 */
	public static boolean validate(HttpServletRequest request){
		String referer = "";
		boolean referer_sign = true;  //true 站内提交，验证通过  //false  站外提交，验证失败
		Enumeration<?> headerValues =  request.getHeaders("Referer");
		while (headerValues.hasMoreElements())
			referer = (String)headerValues.nextElement();

		//判断是否存在请求页面
		if(referer == null || referer.length() < 1 ){
			return false;
		}

		//判断请求页面和getRequestURI是否相同
		String servername_str = request.getServerName();
		if(servername_str != null && servername_str.length() > 0){
			int index = 0;
			if(referer.startsWith("https://")){
				index = 8;
			}
			else if (referer.startsWith("https//")){
				index = 7;
			}
			if(referer.length() - index < servername_str.length())  //长度不够
				referer_sign = false;
			else{   //比较字符串（主机名称）是否相同
				String referer_str = referer.substring(index,index + servername_str.length());
				if(!servername_str.equalsIgnoreCase(referer_str))
					referer_sign = false;
			}
		}
		else
			referer_sign = false;
		return referer_sign;
	}

}
