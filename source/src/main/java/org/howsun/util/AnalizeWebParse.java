/**
 * 
 */
package org.howsun.util;


import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;
import static java.lang.System.out;
/**
 * 说明:<br>
 * 从文本串中获取图片<br>
 * 调用示例：
 * StringReader sr = new StringReader(src);
 * AnalizeWebParse parse = new AnalizeWebParse();
 * List<String> results = parse.parse(sr);
 * 其中src就是需要检测的字符串，它可以Textarea控件传过来的，也可以是从文件中读取的，也可以从互联网上抓取的等等。
 * results就是在字符串中分析出的图片结果集合。
 * 
 * 
 * @author 张纪豪
 * @version 0.1
 * Build Time Apr 10, 2009
 */
public class AnalizeWebParse extends ParserCallback {

	//String regex = "^(http://.+)";//有的图片URL是绝对路径，如http://image.sohu.com/2009/10/10/6278481.jpg
	String regex = "^(.+)";

	List<String> imgs = new ArrayList<String>();

	boolean start = false;
	boolean finished = false;

	public void p(String s) {
		out.println(s);
	}

	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attribs, int pos) {

		if (finished == true) {
			return;
		}

		if (start == false) {
			if (tag == HTML.Tag.DIV) {
				String cla = (String) attribs.getAttribute(HTML.Attribute.CLASS);
				if (cla == null) {
					return;
				}

				if (cla.indexOf("body") != -1) {
					// Start
					start = true;
				}
			}
		}
	}

	public void handleEndTag(HTML.Tag tag, int pos) {
		if (tag == HTML.Tag.DIV && start == true && finished == false) {
			finished = true;
		}
	}

	public void handleText(char[] text, int pos) {

	}

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		if (t == HTML.Tag.IMG) {
			// get a src
			String src = (String) a.getAttribute(HTML.Attribute.SRC);
			if (src == null) {
				return;
			}

			if (Pattern.matches(regex, src)) {
				imgs.add(src);
			}
		}
	}

	//public String parse(BufferedReader file) throws Exception {
	public List<String> parse(Reader file) throws Exception {
		if (file == null) {
			return null;
		}

		ParserDelegator pd = new ParserDelegator();
		try {
			pd.parse(file, this, true);
		} catch (Exception e) {
			throw e;
		}

		return imgs;
	}
}

/*
 * 
 * 调用，并将结果保存到文件中
 * 
 * public class AnalizeIMG {

	public void p(String s) {
		System.out.println(s);
	}

	public void analizeFile(String infile, String outfile) throws Exception {
		File file = new File(infile);
		if (file == null || !file.exists()) {
			p("File " + infile + " not exits !");
		}

		if (!file.canRead()) {
			p("File " + infile + " can't read !");

		}

		FileReader frd = new FileReader(infile);
		BufferedReader bufferedReader = new BufferedReader(frd);
		try {
			AnalizeWebParse parse = new AnalizeWebParse();
			List<String> s = parse.parse(bufferedReader);
			System.out.println(s);
			createFile(outfile, s.toString());

		} catch (Exception ex) {
			throw ex;
		} finally {
			frd.close();
			bufferedReader.close();
		}
	}

	private void createFile(String filename, String content) {
		FileWriter f = null;
		try {
			f = new FileWriter(filename);
			if (f == null || content == null) {
				return;
			}

			f.write(content);
			f.flush();
			f.close();

		} catch (Exception e) {

		} finally {
			if (f != null) {
				try {
					f.close();
				} catch (Exception e) {

				}
			}
		}
	}

	public static void main(String arg[]) {
		AnalizeIMG ana = new AnalizeIMG();
		try {
			ana.analizeFile("E:\\1.txt", "E:\\out.lst");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
*/
