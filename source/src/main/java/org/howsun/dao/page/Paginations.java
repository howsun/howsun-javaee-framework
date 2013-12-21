/**
 * 2013-06-05更新，四个图标分别配置
 */
package org.howsun.dao.page;

import java.text.MessageFormat;
import java.util.Properties;

import org.howsun.util.Propertys;
import org.howsun.util.Strings;

/**
 * 功能描述：往html页面输出<br>
 * 如果需要配置统一的风格，需要如下配置文件：<br>
 * 路径：classpath:META-INF/pagination.properties<br>
 * 内容：<br>
 * #是否显示总页数：<br>
 * DisplayTotalPages=false<br>
 * #是否显示总记录数：<br>
 * DisplayTotalRecords=false<br>
 * #首页链接图标，可以指定相对或绝对图标路径，也可以字符串，默认为“&nbsp;”，下同
 * IconTop=/images/navigation_top.gif
 * #上一页链接图标
 * IconPrevious=/images/navigation_previous.gif
 * #下一页链接图标
 * IconNext=/images/navigation_next.gif
 * #最后一页链接图标
 * IconBottom=/images/navigation_bottom.gif
 * #当前页码样式<br>
 * PaginationStyleForCurrent=<br>
 * #链接页码样式<br>
 * PaginationStyleForLinked=<br>
 * <br>
 * @author 张纪豪(howsun.zhang@google.com)
 * @version 1.0.0
 */
public class Paginations{


	///////////////////////////////////////////////////Static/////////////////////////////////////////////////

	/**是否显示总页数*/
	public static boolean displayTotalPages = false;

	/**是否显示总记录数*/
	public static boolean displayTotalRecords = false;

	/**是否要跳转页文本框*/
	public static boolean hasJumpPage = false;

	/**当前页码样式*/
	public static String paginationStyleForCurrent = "";

	/**链接页码样式*/
	public static String paginationStyleForLinked = "";

	/**首页图标**/
	public static String IconTop = "";

	/**上一页图标**/
	public static String IconPrevious = "";

	/**下一页图标**/
	public static String IconNext = "";

	/**尾页图标**/
	public static String IconBottom = "";

	static String JUMP_URL = "<input name=\"_jumppage_\" type=\"text\" id=\"_jumppage_\" size=\"3\" maxlength=\"3\"/><a href=\"javascript:void(0);\" onclick=\"javascript:var pageIndex=document.getElementById('_jumppage_');if(parseInt(pageIndex.value)){window.location.href='%s'.replace('{_jump_page_}',pageIndex.value);}else{alert('请输入正确的页码')}\">跳转</a>";
	static String JUMP_URL_POSI = "{_jump_page_}";

	static{
		try {
			Properties properties     	= Propertys.loadPropertiesInMetaInf("pagination.properties");
			displayTotalPages         	= Propertys.readPropertiesBooleanValue(properties, "DisplayTotalPages", false);
			displayTotalRecords       	= Propertys.readPropertiesBooleanValue(properties, "DisplayTotalRecords", false);
			paginationStyleForCurrent 	= properties.getProperty("PaginationStyleForCurrent", "pagenumbervisited");
			paginationStyleForLinked  	= properties.getProperty("PaginationStyleForLinked", "pagenumber");

			IconTop  					= properties.getProperty("IconTop", 		"首页");
			IconPrevious				= properties.getProperty("IconPrevious", 	"上一页");
			IconNext  					= properties.getProperty("IconNext", 		"下一页");
			IconBottom  				= properties.getProperty("IconBottom", 		"尾页");

			hasJumpPage  			  	= Propertys.readPropertiesBooleanValue(properties, "hasJumpPage", false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////

	private Page page;

	public Paginations(Page page){
		this.page = page;
	}

	/**
	 * 按默认配置打印页码
	 * @return
	 */
	public String print(){
		return print(displayTotalPages, displayTotalRecords, paginationStyleForCurrent, paginationStyleForLinked);
	}

	/**
	 * 打印页码
	 * @param displayTotalPages 是否显示总页数
	 * @param displayTotalRecords 是否显示总记录数
	 * @param imagePath 图标位置
	 * @param paginationStyleForCurrent 当前页码样式
	 * @param paginationStyleForLinked 链接页码样式
	 * @return
	 */
	public String print(boolean displayTotalPages, boolean displayTotalRecords, String paginationStyleForCurrent, String paginationStyleForLinked){
		if(page.getTotalCount() == 0){
			return "";
		}

		StringBuilder sb = new StringBuilder();

		//如果设定了要打印总页数，则在JSP页面中输出当前页和总页数
		if(displayTotalPages){
			sb.append("当前页/总页数："+ page.getPageIndex() + "/" + page.getPageCount() + "&nbsp;&nbsp;&nbsp;");
		}

		//sb.append("页码：");

		//如果当前页码大于1时，应该输出开始一页和上一页的连接
		if(page.getPageIndex() > 1){
			sb.append("<a title=\"开始一页\" href=\"")
			.append(conversionUrl(page.getUrl(), 1, page.getPageCount()))
			.append("\" class=\"")
			.append(paginationStyleForLinked)
			.append("\">")
			.append(getNavigationIcon(0))
			.append("</a>&nbsp;");
			sb.append("<a title=\"上一页\"  href=\"")
			.append(conversionUrl(page.getUrl(), page.getPageIndex() - 1, page.getPageCount()))
			.append("\" class=\"")
			.append(paginationStyleForLinked)
			.append("\">")
			.append(getNavigationIcon(1))
			.append("</a>&nbsp;&nbsp;");
		}

		//开始在总页数里循环
		for (int i = 1; i <= page.getPageCount(); i++){

			//假如有几十页、几百页、甚至n多，不能全部将页码输出，这里只取10页
			if(page.getPageIndex() < 5 && i < 10){
				if(i == page.getPageIndex()){
					//当前页不应该有连接
					sb.append("<span")
					.append(getStyle(paginationStyleForCurrent))
					.append(">")
					.append(i)
					.append("</span>&nbsp;");
				}else{
					sb.append("<a href=\"")
					.append(conversionUrl(page.getUrl(), i, page.getPageCount()))
					.append("\" class=\"")
					.append(paginationStyleForLinked)
					.append("\" title=\"第")
					.append(i)
					.append("页\">")
					.append(i)
					.append("</a>&nbsp;");
				}
			}else{

				if(i > page.getPageIndex() - 5 && i < page.getPageIndex() + 5){
					if(i == page.getPageIndex()){
						sb.append("<span")
						.append(getStyle(paginationStyleForCurrent))
						.append(">")
						.append(i)
						.append("</span>&nbsp;");
					}else{
						sb.append("<a href=\"")
						.append(conversionUrl(page.getUrl(), i, page.getPageCount()))
						.append("\" class=\"")
						.append(paginationStyleForLinked)
						.append("\" title=\"第")
						.append(i)
						.append("页\">")
						.append(i)
						.append("</a>&nbsp;");
					}
				}
			}
		}

		//只要没有进到最后一页，都应该输出下一面和最后一页连接
		if(page.getPageIndex() < page.getPageCount()){
			sb.append("&nbsp;<a title=\"下一页\" href=\"")
			.append(conversionUrl(page.getUrl(), page.getPageIndex() + 1, page.getPageCount()))
			.append("\" class=\"")
			.append(paginationStyleForLinked)
			.append("\">")
			.append(getNavigationIcon(2))
			.append("</a>&nbsp;");
			sb.append("<a title=\"最后一页\" href=\"")
			.append(conversionUrl(page.getUrl(), page.getPageCount(), page.getPageCount()))
			.append("\" class=\"")
			.append(paginationStyleForLinked)
			.append("\">")
			.append(getNavigationIcon(3))
			.append("</a>");
		}

		//是否开启跳转页功能且总页数大于10页
		if(hasJumpPage && page.getPageCount() > 10){
			sb.append("&nbsp;&nbsp;")
			.append(conversionUrl(page.getUrl(), -100, page.getPageCount()));
		}

		//标记中调用是否指定输出每页记录数和总记录数
		if(displayTotalRecords)
			sb.append("&nbsp;&nbsp;&nbsp;每页记录数/总记录数：")
			.append(page.getPageSize())
			.append("/")
			.append(page.getTotalCount());

		return sb.toString();
	}



	/**
	 * 转换URL，携带页数量，可以省去统计步骤
	 * @param url
	 * @param pageindex
	 * @param pageCount
	 * @return
	 */
	public static String conversionUrl(String url, int pageindex, int pageCount){
		if(url == null || url.length() < 1){
			return "index.jsp";
		}

		int index = url.indexOf("?");
		StringBuffer sb = new StringBuffer();

		/*
		 * 如果是REST风格
		 * e.g. http://www.howsun.org/article/list_*.html
		 */
		if(url.indexOf('*') > -1){

			if(pageindex == -100){
				url = url.replace("*", String.valueOf(JUMP_URL_POSI));
				sb.append(url)
				.append(index > -1 ? "&" : "?")
				.append(Page.PAGE_TOTAL_PARAMETER_NAME)
				.append("=")
				.append(pageCount);
				return String.format(JUMP_URL, sb.toString());
			}

			url = url.replace("*", String.valueOf(pageindex));
			return sb.append(url)
					.append(index > -1 ? "&" : "?")
					.append(Page.PAGE_TOTAL_PARAMETER_NAME)
					.append("=")
					.append(pageCount)
					.toString() ;
		}

		sb.append(url);
		if(index > -1){
			char c = sb.charAt(sb.length() - 1);
			if(c != '?' && c != '&'){
				sb.append("&");
			}
		}else{
			sb.append("?");
		}

		if(pageindex == -100){
			sb.append(Page.PAGE_TOTAL_PARAMETER_NAME)
			.append("=")
			.append(pageCount)
			.append("&")
			.append(Page.PAGE_NUMBER_PARAMETER_NAME)
			.append("=")
			.append(JUMP_URL_POSI);
			return String.format(JUMP_URL, sb.toString());
		}
		sb.append(Page.PAGE_NUMBER_PARAMETER_NAME)
		.append("=")
		.append(pageindex)
		.append("&")
		.append(Page.PAGE_TOTAL_PARAMETER_NAME)
		.append("=")
		.append(pageCount);

		return sb.toString();
	}



	public static String getNavigationIcon(int type){
		String icon = "<img src=\"%s\" border=\"0\" alt=\"%s\" title=\"%s\"/>";
		switch (type) {
		case 0:
			return (IconTop.endsWith(".gif") || IconTop.endsWith(".jpg") || IconTop.endsWith(".png"))
					? String.format(icon, IconTop, "最开始一页","最开始一页")
					: IconTop;
		case 1:
			return (IconPrevious.endsWith(".gif") || IconPrevious.endsWith(".jpg") || IconPrevious.endsWith(".png"))
					? String.format(icon, IconPrevious, "上一页","上一页")
					: IconPrevious;
		case 2:
			return (IconNext.endsWith(".gif") || IconNext.endsWith(".jpg") || IconNext.endsWith(".png"))
					? String.format(icon, IconNext, "下一页","下一页")
					: IconNext;
		case 3:
			return (IconBottom.endsWith(".gif") || IconBottom.endsWith(".jpg") || IconBottom.endsWith(".png"))
					? String.format(icon, IconBottom, "最后一页","最后一页")
					: IconBottom;
		}
		return "&nbsp;";
		//return hasIcons ? ("<img src=\"" + (imagePath + Paginations.ICONS[type][1]) + "\" border=\"0\"/>") : Paginations.ICONS[type][0];
	}


	/**
	 * 获取链接样式
	 * @param paginationStyleForCurrent
	 * @return
	 */
	public static String getStyle(String paginationStyleForCurrent){
		return Strings.hasLengthBytrim(paginationStyleForCurrent) ? String.format(" class=\"%s\"", paginationStyleForCurrent) : "";
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Page page = new Page(11,50,"/index?");
		page.setTotalCount(13453463);
		Paginations printer = new Paginations(page);
		System.out.println(printer.print());
		System.out.println(page.getPageCount()*50);
		System.out.println(MessageFormat.format("/index_{0}?pc={1}", 3,40));
	}

}
