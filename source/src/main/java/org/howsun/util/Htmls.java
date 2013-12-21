/**
 *
 */
package org.howsun.util;

import java.util.regex.Pattern;

/**
 * 说明:<br>
 *
 * @author 张纪豪
 * @version
 * Build Time Mar 28, 2009
 */
public abstract class Htmls {



	private static final String TAG_SCRIPT_START = "<script";
	private static final String TAG_SCRIPT_END = "</script>";

	private static final String TAG_OBJECT_START = "<object";
	private static final String TAG_OBJECT_END = "</object>";

	private static final String TAG_IFRAME_START = "<iframe";
	private static final String TAG_IFRAME_END = "</iframe>";

	private static final String TAG_STYLE_START = "<style";
	private static final String TAG_STYLE_END = "</style>";

	/**
	 * 将过滤掉Html标签
	 */
	public static String cleanHtmlTag(String surText) {
		return Strings.toString(surText).replaceAll("<[.[^<]]*>", "").replace("\n", "<br/>");
	}

	/**
	 * 清除脚本标签，包括scrpit，object, iframe<br/>
	 * 特点:无论大小写都可以清除
	 * @param html
	 * @return
	 */
	public static String removeScriptTags(String html) {
        if(html==null)
            return "";
        boolean found;
        int count = 0;
        int start, end;
        StringBuilder sb = new StringBuilder(html);
        StringBuilder sb_lowercase = new StringBuilder(html.toLowerCase());
        do {
            found = false;
            // remove <script>...</script>
            start = sb_lowercase.lastIndexOf(TAG_SCRIPT_START);
            end = sb_lowercase.lastIndexOf(TAG_SCRIPT_END);
            if(start<end && start>=0) {
                found = true;
                count++;
                sb_lowercase.delete(start, end+9);
                sb.delete(start, end+9);
            }
            else { // remove <object>...</object>
                start = sb_lowercase.lastIndexOf(TAG_OBJECT_START);
                end = sb_lowercase.lastIndexOf(TAG_OBJECT_END);
                if(start<end && start>=0) {
                    found = true;
                    count++;
                    sb_lowercase.delete(start, end+9);
                    sb.delete(start, end+9);
                }
            }
            // remove <iframe>...</iframe>
            start = sb_lowercase.lastIndexOf(TAG_IFRAME_START);
            end = sb_lowercase.lastIndexOf(TAG_IFRAME_END);
            if(start<end && start>=0) {
                found = true;
                count++;
                sb_lowercase.delete(start, end+9);
                sb.delete(start, end+9);
            }

            start = sb_lowercase.lastIndexOf(TAG_STYLE_START);
            end = sb_lowercase.lastIndexOf(TAG_STYLE_END);
            if(start<end && start>=0) {
                found = true;
                count++;
                sb_lowercase.delete(start, end+8);
                sb.delete(start, end+8);
            }
        } while(found);
        if(count==0) // no tags found, just return the original String!
            return html;
        return sb.toString();
    }


	/**
	 * 消除Html标签，
	 * 特点：可以定制要失效的标签
	 * @param html
	 * @return
	 */
	public static String htmlEscape(String html, String[] tags) {
		if(html==null)
			return "";
		boolean found;
		int count = 0;
		int start;
		StringBuilder sb = new StringBuilder(html);
		StringBuilder sb_lowercase = new StringBuilder(html.toLowerCase());
		for(String tag : tags){
			tag.replaceAll(">", "");
			if(tag.indexOf('<')==-1){
				tag = "<" + tag;
			}
			do {
				found = false;
				start = sb_lowercase.lastIndexOf(tag);
				if(start > -1) {
					found = true;
					count++;
					sb.replace(start, start+1, "&lt;");
					sb_lowercase.replace(start, start+1, "&lt;");
					tag = "</"+tag.substring(1);
					start = sb_lowercase.lastIndexOf(tag);
					if(start > -1){
						sb.replace(start, start+2, "&lt;／");
						sb_lowercase.replace(start, start+2, "&lt;／");
					}
				}
			} while(found);
		}
		if(count==0) // no tags found, just return the original String!
			return html;
		return sb.toString();
	}

	/**
	 * 转义所有的Html标签
	 * @param text
	 * @return
	 */
	public static String htmlEscape(String text){
		if(text == null || "".equals(text))
            return text;
		return text.replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace(" ", "&nbsp;")
        .replace("\"", "&quot;")
        .replace("\'", "&apos;")
        .replace("\n", "<br/>");
	}

	public static String stringToHtml(String text){
		if(text == null || "".equals(text))
            return text;
		return text.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&nbsp;", " ")
				.replace("&quot;", "\"")
				.replace("&apos;", "\'")
				.replace("<br/>", "\n");
	}

	 /**
     * 去除html代码：该方法不能过滤含有大写字母或大小写混合的标签
     * @param inputString
     * @return
     */
    public static String html2Text(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;

        try {
        	//过滤script标签
        	//定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");

            //过滤style标签
            //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");

            //过滤html标签
            //定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
           // p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
           // m_html = p_html.matcher(htmlStr);
            //htmlStr = m_html.replaceAll("");

            //空格表达式
            String patternStr = "\\s+";
            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll("");

         textStr = htmlStr;

        }catch(Exception e) {
                    System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;//返回文本字符串
     }

	public static String clearWordFormat(String content) {
		//把<P></P>转换成</div></div>保留样式
		//content = content.replaceAll("(<P)([^>]*>.*?)(<\\/P>)", "<div$2</div>");
		//把<P></P>转换成</div></div>并删除样式
		content = content.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");
		//删除不需要的标签
		content = content.replaceAll("<[/]?(html|HTML|head|HEAD|link|LINK|title|TITLE|font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>", "");
		//删除不需要的属性
		content = content.replaceAll("<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>", "<$1$2>");
		//删除<STYLE TYPE="text/css"></STYLE>及之间的内容
		int styleBegin = content.indexOf("<STYLE");
		int styleEnd   = content.indexOf("</STYLE>") + 8;
		if(styleBegin > -1){
			String style   = content.substring(styleBegin, styleEnd);
			content = content.replace(style, "");
		}

		int styleLowerCaseBegin = content.indexOf("<style");
		int styleLowerCaseEnd   = content.indexOf("</style>") + 8;
		if(styleLowerCaseBegin > -1){
			String style = content.substring(styleLowerCaseBegin, styleLowerCaseEnd);
			content = content.replace(style, "");
		}
		return content;
	}

	 public static String cleanXSS(String value) {
		 if(value != null){
	         //You'll need to remove the spaces from the html entities below
			 value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
			 value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
			 value = value.replaceAll("'", "& #39;");
			 value = value.replaceAll("eval\\((.*)\\)", "");
			 value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			 value = value.replaceAll("script", "");
		 }
		 return value;
	 }
}
