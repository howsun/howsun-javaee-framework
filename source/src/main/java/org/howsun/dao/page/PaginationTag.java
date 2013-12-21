package org.howsun.dao.page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <h1>参数说明：</h1>
 * 1)、displayTotalRecords参数用于控制是否显示总记录数<br>
 * 2)、displayTotalPages参数用于控制是否显示总页数<br>
 * 3)、currentPaginationStyle当前页码显示的样式，即没有连接的一页CSS风格<br>
 * 4)、linkPaginationStyle带链接页码的CSS风格样式<br>
 *
 * 1)、5页后当前页码总在中间<br>
 * 2)、页码上的URL地址支持多种方式，如：<br>
 * 		page.setUrl("http://www.zhangjihao.com/article/list_*.do");     “*”号会被替换成页码，支持REST风格<br>
 * 		page.setUrl("http://www.zhangjihao.com/article/list.do");       地址后面会自动加上页码参数<br>
 * 		page.setUrl("http://www.zhangjihao.com/article/list.do?cid=1"); 地址后面会自动加上页码参数，不影响多参数连接。<br>
 * 3)、页码显示样式可由外面css样式传入<br>
 * <br><br>
 * @author 张纪豪
 * @Date 2007-9-26
 * @version v3.0
 */
public class PaginationTag extends TagSupport {

	public static final long serialVersionUID = 1L;

	/**是否显示总记录数*/
	public Boolean displayTotalRecords;

	/**是否显示总页数*/
	public Boolean displayTotalPages;

	/**当前页码的显示样式**/
	public String currentPaginationStyle;

	/**带链接的页码的显示样式**/
	public String linkPaginationStyle;


	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

		Page page = (Page)request.getAttribute(Page.SCOPE_NAME);

		if(page != null){

			JspWriter out = pageContext.getOut();

			StringBuffer sb = new StringBuffer();

			try {
				sb.append(new Paginations(page).print(getDisplayTotalPages(), getDisplayTotalRecords(), getCurrentPaginationStyle(), getLinkPaginationStyle()));
			} catch (Exception e) {
				sb.append(e.getMessage());
			}finally{
				try {
					out.println(sb.toString());
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return TagSupport.SKIP_BODY;
	}


	/////////////////////////////////Setter&Getter/////////////////////////////////

	public boolean getDisplayTotalRecords() {
		if(this.displayTotalRecords == null){
			this.displayTotalRecords = Paginations.displayTotalRecords;
		}
		return this.displayTotalRecords;
	}

	public void setDisplayTotalRecords(boolean displayTotalRecords) {
		this.displayTotalRecords = displayTotalRecords;
	}

	public boolean getDisplayTotalPages() {
		if(this.displayTotalPages == null){
			this.displayTotalPages = Paginations.displayTotalPages;
		}
		return this.displayTotalPages;
	}

	public void setDisplayTotalPages(boolean displayTotalPages) {
		this.displayTotalPages = displayTotalPages;
	}

	public String getCurrentPaginationStyle() {
		if(this.currentPaginationStyle == null){
			this.currentPaginationStyle = Paginations.paginationStyleForCurrent;
		}
		return this.currentPaginationStyle;
	}

	public void setCurrentPaginationStyle(String currentPaginationStyle) {
		this.currentPaginationStyle = currentPaginationStyle;
	}

	public String getLinkPaginationStyle() {
		if(this.linkPaginationStyle == null){
			this.linkPaginationStyle = Paginations.paginationStyleForLinked;
		}
		return linkPaginationStyle;
	}

	public void setLinkPaginationStyle(String linkPaginationStyle) {
		this.linkPaginationStyle = linkPaginationStyle;
	}

}
