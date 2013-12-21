/**
 * 版本修订记录
 * 创建：2013-4-15
 * 版本：
 *
 * 修订1：  说明：
 * 修订2：  说明：
 */
package org.howsun.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.howsun.json.JacksonSupportJson;
import org.howsun.log.Log;
import org.howsun.log.LogFactory;

/**
 * 描述：  
 * @author howsun
 * @version 3.0
 * Building Time 2013-4-15
 *
 */
public abstract class Responses {

	private static Log log = LogFactory.getLog(Responses.class);

	public static void write(HttpServletResponse response, Object content){
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().print(content);
		}
		catch (Exception e) {
			log.info("输出错误：", e);
		}
	}
	
	/**
	 * 写出的Ajax结果具有Json安全特性
	 * @param response
	 * @param status
	 * @param message
	 */
	public static void writeAjaxMessageBySafe(HttpServletResponse response, boolean status, String message){
		Map<String, Object> content = new HashMap<String, Object>(2,2);
		content.put("status", status);
		content.put("message", message);
		write(response, JacksonSupportJson.buildNormalBinder().toJson(content));
	}
	
	/**
	 * 约定的Ajax结果输出<br>
	 * 如果需要Json结果信息安全，请使用 {@link Responses#writeAjaxMessageBySafe()}
	 * @param response
	 * @param status
	 * @param message
	 */
	public static void writeAjaxMessage(HttpServletResponse response, boolean status, String message){
		write(response, String.format("{\"status\":%s,\"message\":\"%s\"}", status, message));
	}

	public static void writeJavaScript(HttpServletResponse response, String content){
		write(response, String.format("<script type=\"text/javascript\">%s</script>", content));
	}

	/**
	 * 与在浏览器点击后退按钮相同
	 * @param response
	 */
	public static void writeJavaScriptWindowBack(HttpServletResponse response){
		writeJavaScript(response, "history.back();");
	}

	/**
	 * Javascript提示信息
	 * @param response
	 */
	public static void writeJavaScriptAlert(HttpServletResponse response, String alertContent){
		writeJavaScript(response, "alert('" + alertContent + "');");
	}

	/**
	 * 通过JavaScript跳转
	 * @param response
	 * @param alertContent
	 */
	public static void writeJavaScriptRedirect(HttpServletResponse response, String url){
		writeJavaScript(response, "window.location.href='" + url + "';");
	}



	public static void download(HttpServletResponse response, String content, String filename){
		try {
			download(response, content.getBytes("UTF-8"), filename);
		}
		catch (Exception e) {
			log.info("下载文本数据失败：", e);
		}
	}
	public static void download(HttpServletResponse response, byte[] content, String filename){
		response.addHeader("pragma","NO-cache");
		response.addHeader("Cache-Control","no-cache");
		response.addDateHeader("Expries",0);
		response.setContentType("application/x-download");
		try {filename = new String(filename.getBytes("UTF-8"), "ISO8859_1");}catch (Exception e) {e.printStackTrace();}
		response.addHeader("Content-Disposition","attachment;filename=" + filename);
		OutputStream out = null;
		try{
			out = response.getOutputStream();
			out.write(content);
			out.flush();
		} catch (Exception e) {
			try {
				out.write(e.getMessage().getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally{
			if(out != null)
				try {out.close();} catch (Exception e2) {}
		}
	}


	public static void main(String[] args) {
		Map<String, Object> content = new HashMap<String, Object>(2,2);
		content.put("status", true);
		content.put("message", "<div class=\"paging\">sdfadsgasdf</div>");
		System.out.println(JacksonSupportJson.buildNormalBinder().toJson(content));
	}
}
