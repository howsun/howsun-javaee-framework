/**
 * ========================================================
 * 北京五八信息技术有限公司技术中心开发一部
 * 日 期：2011-8-19 下午05:43:45
 * 作 者：张纪豪
 * 版 本：1.0.0
 * ========================================================
 * 修订日期                        修订人                     描述
 *
 */

package org.howsun.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述：Html代码压缩器
 * 
 * @author howsun(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class HtmlCompressor {
	private static String tempPreBlock           = "%%%HTMLCOMPRESS~PRE&&&";
	private static String tempTextAreaBlock      = "%%%HTMLCOMPRESS~TEXTAREA&&&";
	private static String tempScriptBlock        = "%%%HTMLCOMPRESS~SCRIPT&&&";
	private static String tempStyleBlock         = "%%%HTMLCOMPRESS~STYLE&&&";
	private static String tempJspBlock           = "%%%HTMLCOMPRESS~JSP&&&";

	private static Pattern commentPattern        = Pattern.compile("<!--\\s*[^\\[].*?-->", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	private static Pattern itsPattern            = Pattern.compile(">\\s+?<", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	private static Pattern prePattern            = Pattern.compile("<pre[^>]*?>.*?</pre>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); 
	private static Pattern taPattern             = Pattern.compile("<textarea[^>]*?>.*?</textarea>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	private static Pattern jspPattern            = Pattern.compile("<%([^-@][\\w\\W]*?)%>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	// <script></script>
	private static Pattern scriptPattern         = Pattern.compile("(?:<script\\s*>|<script type=['\"]text/javascript['\"]\\s*>)(.*?)</script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	private static Pattern stylePattern          = Pattern.compile("<style[^>()]*?>(.+)</style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

	// 单行注释，
	private static Pattern signleCommentPattern  = Pattern.compile("//.*");
	// 字符串匹配
	private static Pattern stringPattern         = Pattern.compile("(\"[^\"\\n]*?\"|'[^'\\n]*?')");
	// trim去空格和换行符 
	private static Pattern trimPattern           = Pattern.compile("\\n\\s*",Pattern.MULTILINE);
	private static Pattern trimPattern2          = Pattern.compile("\\s*\\r",Pattern.MULTILINE);
	// 多行注释
	private static Pattern multiCommentPattern   = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

	private static String tempSingleCommentBlock = "%%%HTMLCOMPRESS~SINGLECOMMENT&&&";  // //占位符
	private static String tempMulitCommentBlock1 = "%%%HTMLCOMPRESS~MULITCOMMENT1&&&";  // /*占位符
	private static String tempMulitCommentBlock2 = "%%%HTMLCOMPRESS~MULITCOMMENT2&&&";  // */占位符


	/**
	 * 压缩
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public static String compress(String html) throws Exception {
		if(html == null || html.length() == 0) {
			return html;
		}

		List<String> preBlocks = new ArrayList<String>();
		List<String> taBlocks = new ArrayList<String>();
		List<String> scriptBlocks = new ArrayList<String>();
		List<String> styleBlocks = new ArrayList<String>();
		List<String> jspBlocks = new ArrayList<String>();

		String result = html;

		//preserve inline java code
		Matcher jspMatcher = jspPattern.matcher(result);
		while(jspMatcher.find()) {
			jspBlocks.add(jspMatcher.group(0));
		}
		result = jspMatcher.replaceAll(tempJspBlock);

		//preserve PRE tags
		Matcher preMatcher = prePattern.matcher(result);
		while(preMatcher.find()) {
			preBlocks.add(preMatcher.group(0));
		}
		result = preMatcher.replaceAll(tempPreBlock);

		//preserve TEXTAREA tags
		Matcher taMatcher = taPattern.matcher(result);
		while(taMatcher.find()) {
			taBlocks.add(taMatcher.group(0));
		}
		result = taMatcher.replaceAll(tempTextAreaBlock);

		//preserve SCRIPT tags
		Matcher scriptMatcher = scriptPattern.matcher(result);
		while(scriptMatcher.find()) {
			scriptBlocks.add(scriptMatcher.group(0));
		}
		result = scriptMatcher.replaceAll(tempScriptBlock);

		// don't process inline css 
		Matcher styleMatcher = stylePattern.matcher(result);
		while(styleMatcher.find()) {
			styleBlocks.add(styleMatcher.group(0));
		}
		result = styleMatcher.replaceAll(tempStyleBlock);

		//process pure html
		result = processHtml(result);

		//process preserved blocks
		result = processPreBlocks(result, preBlocks);
		result = processTextareaBlocks(result, taBlocks);
		result = processScriptBlocks(result, scriptBlocks);
		result = processStyleBlocks(result, styleBlocks);
		result = processJspBlocks(result, jspBlocks);

		preBlocks = taBlocks = scriptBlocks = styleBlocks = jspBlocks = null;

		return result.trim();
	}

	private static String processHtml(String html) {
		String result = html;

		//remove comments
		//        if(removeComments) {
		result = commentPattern.matcher(result).replaceAll("");
		//        }

		//remove inter-tag spaces
		//        if(removeIntertagSpaces) {
		result = itsPattern.matcher(result).replaceAll("><");
		//        }

		//remove multi whitespace characters
		//        if(removeMultiSpaces) {
		result = result.replaceAll("\\s{2,}"," ");
		//        }

		return result;
	}

	private static String processJspBlocks(String html, List<String> blocks){
		String result = html;
		for(int i = 0; i < blocks.size(); i++) {
			blocks.set(i, compressJsp(blocks.get(i)));
		}
		//put preserved blocks back
		while(result.contains(tempJspBlock)) {
			result = result.replaceFirst(tempJspBlock, Matcher.quoteReplacement(blocks.remove(0)));
		}

		return result;
	}
	private static String processPreBlocks(String html, List<String> blocks) throws Exception {
		String result = html;

		//put preserved blocks back
		while(result.contains(tempPreBlock)) {
			result = result.replaceFirst(tempPreBlock, Matcher.quoteReplacement(blocks.remove(0)));
		}

		return result;
	}

	private static String processTextareaBlocks(String html, List<String> blocks) throws Exception {
		String result = html;

		//put preserved blocks back
		while(result.contains(tempTextAreaBlock)) {
			result = result.replaceFirst(tempTextAreaBlock, Matcher.quoteReplacement(blocks.remove(0)));
		}

		return result;
	}

	private static String processScriptBlocks(String html, List<String> blocks) throws Exception {
		String result = html;

		//        if(compressJavaScript) {
		for(int i = 0; i < blocks.size(); i++) {
			blocks.set(i, compressJavaScript(blocks.get(i)));
		}
		//        }

		//put preserved blocks back
		while(result.contains(tempScriptBlock)) {
			result = result.replaceFirst(tempScriptBlock, Matcher.quoteReplacement(blocks.remove(0)));
		}

		return result;
	}

	private static String processStyleBlocks(String html, List<String> blocks) throws Exception {
		String result = html;

		//        if(compressCss) {
		for(int i = 0; i < blocks.size(); i++) {
			blocks.set(i, compressCssStyles(blocks.get(i)));
		}
		//        }

		//put preserved blocks back
		while(result.contains(tempStyleBlock)) {
			result = result.replaceFirst(tempStyleBlock, Matcher.quoteReplacement(blocks.remove(0)));
		}

		return result;
	}

	private static String compressJsp(String source)  {
		//check if block is not empty
		Matcher jspMatcher = jspPattern.matcher(source);
		if(jspMatcher.find()) {
			String result = compressJspJs(jspMatcher.group(1));
			return (new StringBuilder(source.substring(0, jspMatcher.start(1))).append(result).append(source.substring(jspMatcher.end(1)))).toString();
		} else {
			return source;
		}
	}    
	private static String compressJavaScript(String source)  {
		//check if block is not empty
		Matcher scriptMatcher = scriptPattern.matcher(source);
		if(scriptMatcher.find()) {
			String result = compressJspJs(scriptMatcher.group(1));
			return (new StringBuilder(source.substring(0, scriptMatcher.start(1))).append(result).append(source.substring(scriptMatcher.end(1)))).toString();
		} else {
			return source;
		}
	}

	private static String compressCssStyles(String source)  {
		//check if block is not empty
		Matcher styleMatcher = stylePattern.matcher(source);
		if(styleMatcher.find()) {
			// 去掉注释，换行
			String result= multiCommentPattern.matcher(styleMatcher.group(1)).replaceAll("");
			result = trimPattern.matcher(result).replaceAll("");
			result = trimPattern2.matcher(result).replaceAll("");
			return (new StringBuilder(source.substring(0, styleMatcher.start(1))).append(result).append(source.substring(styleMatcher.end(1)))).toString();
		} else {
			return source;
		}
	}

	private static String compressJspJs(String source){
		String result = source;
		// 因注释符合有可能出现在字符串中，所以要先把字符串中的特殊符好去掉
		Matcher stringMatcher = stringPattern.matcher(result);
		while(stringMatcher.find()){
			String tmpStr = stringMatcher.group(0);

			if(tmpStr.indexOf("//") != -1 || tmpStr.indexOf("/*") != -1 || tmpStr.indexOf("*/") != -1){
				String blockStr = tmpStr.replaceAll("//", tempSingleCommentBlock).replaceAll("/\\*", tempMulitCommentBlock1)
				.replaceAll("\\*/", tempMulitCommentBlock2);
				result = result.replace(tmpStr, blockStr);
			}
		}
		// 去掉注释
		result = signleCommentPattern.matcher(result).replaceAll("");
		result = multiCommentPattern.matcher(result).replaceAll("");
		result = trimPattern2.matcher(result).replaceAll("");
		result = trimPattern.matcher(result).replaceAll(" ");
		// 恢复替换掉的字符串
		result = result.replaceAll(tempSingleCommentBlock, "//").replaceAll(tempMulitCommentBlock1, "/*")
		.replaceAll(tempMulitCommentBlock2, "*/");

		return result;
	}

	public static void main(String[] args) throws Exception {
		InputStream pStream = new FileInputStream("D:\\58Development\\InfoList\\Template\\car\\ershouche\\ershouche_img_1310696026102007_0.0.1.html");
		String html = Streams.asString(pStream, "utf-8");
		html = HtmlCompressor.compress(html);
		System.out.println(html);
		pStream.close();
	}
}
