package org.howsun.dao.page;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.howsun.util.Webs;

/**
 * @Description：数据分页
 *
 * @author 张纪豪
 * @Date 2007-9-16
 * @version v3.0
 */
public class Page implements Serializable {

	private static final long serialVersionUID = -625302977128629259L;

	/**URL传递的页码参数名**/
	public static final String PAGE_NUMBER_PARAMETER_NAME = "pageIndex";

	/**URL传递的页数参数名**/
	public static final String PAGE_TOTAL_PARAMETER_NAME  = "pageCount";

	/**作用域中的页对象**/
	public static final String SCOPE_NAME = Page.class.getName() + ".pageBean";

	/**当前页**/
	private int pageIndex;

	/**每页数据量**/
	private int pageSize = 10;

	/**总共数据量**/
	private int totalCount;

	/**页码跳转路径**/
	private String url;


	//////////////////////////////////////////////////////////

	/**
	 * @param pageIndex 当前页码
	 */
	public Page(int pageIndex){
		if(pageIndex < 1)
			pageIndex = 1;
		this.pageIndex = pageIndex;
	}

	/**
	 * @param pageIndex 当前页码
	 * @param pageSize  每页数量
	 */
	public Page(int pageIndex, int pageSize){
		this(pageIndex);
		this.pageSize = pageSize;
	}

	/**
	 * @param pageIndex 当前页码
	 * @param pageSize  每页数量
	 * @param url       页码上的地址
	 */
	public Page(int pageIndex, int pageSize, String url){
		this(pageIndex, pageSize);
		this.url = url;
	}

	/**
	 * 自动填充当前页码和总记录数
	 * @param pageIndex  页码
	 * @param pageSize 每页数量
	 * @param pageCount 总页数
	 * @param url      页码上的地址
	 */
	public Page(int pageIndex, int pageSize, int pageCount, String url){
		this(pageIndex, pageSize, url);
		this.totalCount = this.pageSize * pageCount;
	}

	/**
	 * 自动填充当前页码和总记录数
	 * @param request  HttpServletRequest对象
	 * @param pageSize 每页数量
	 * @param url      页码上的地址
	 */
	public Page(HttpServletRequest request, int pageSize, String url){
		//this.pageSize = pageSize;
		//this.pageIndex = Webs.getIntByRequestParameter(request, PAGE_NUMBER_PARAMETER_NAME, 1);
		//int  pageCount = Webs.getIntByRequestParameter(request, PAGE_TOTAL_PARAMETER_NAME,  0);
		//this.totalCount = this.pageSize * pageCount;
		//this.url = url;
		this(Webs.getIntByRequestParameter(request, PAGE_NUMBER_PARAMETER_NAME, 1),
				pageSize,
				Webs.getIntByRequestParameter(request, PAGE_TOTAL_PARAMETER_NAME,  0),
				url);
	}



	//////////////////////////////////////////////////////////

	/**
	 * 将Page对象置入HttpServletRequest域中
	 */
	public void setToScope(HttpServletRequest request){
		request.setAttribute(SCOPE_NAME, this);
	}

	//////////////////////////////////////////////////////////

	public void setPageIndex(int pageIndex) {
		if(pageIndex < 1)
			pageIndex = 1;
		this.pageIndex = pageIndex;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * 总记录
	 * @return int
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 每页数据大小
	 * @return  int
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 页中的第一条记录
	 * @return int
	 */
	public int getFirstIndex(){
		return (this.pageIndex - 1) * this.pageSize;
	}

	/**
	 * 页中的最后一条记录
	 * @return int
	 */
	public int getLastIndex(){
		int n = getFirstIndex() + this.pageSize;
		if (n > this.totalCount)
			n = this.totalCount;
		return n;
	}

	/**
	 * 总页数
	 * @return int
	 */
	public int getPageCount() {
		if (this.totalCount == 0)
			return 0;
		return this.totalCount / this.pageSize + ((this.totalCount % this.pageSize == 0) ? 0 : 1);
	}

	public boolean isEmpty() {
		return this.totalCount == 0;
	}

	public boolean getHasPrevious() {
		return this.pageIndex > 1;
	}

	public boolean getHasNext() {
		return this.pageIndex < getPageCount();
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	private Paginations printer = null;

	/**
	 * 输出分页代码
	 * @return
	 */
	public String print(){
		if(this.printer == null){
			this.printer = new Paginations(this);
		}
		return printer.print();
	}

	/**
	 * 可配置输出分页代码
	 * @param displayTotalPages   是否显示总页数
	 * @param displayTotalRecords 是否显示总记录
	 * @param imagePath           页码图片
	 * @param paginationStyleForCurrent 当前页CSS样式
	 * @param paginationStyleForLinked  链接页CSS样式
	 * @return
	 */
	public String print(boolean displayTotalPages, boolean displayTotalRecords, String paginationStyleForCurrent, String paginationStyleForLinked){
		if(this.printer == null){
			this.printer = new Paginations(this);
		}
		return printer.print(displayTotalPages, displayTotalRecords, paginationStyleForCurrent, paginationStyleForLinked);
	}
}