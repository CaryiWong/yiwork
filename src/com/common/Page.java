package com.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Page<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5750141425361705678L;
	
	// 公共变量
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	//分页参数
	protected Integer prePage = 0;//上一页页码
	protected Integer currentPage = 0;//当前页页码
	protected Integer nextPage = 0;//下一页页码
	protected Integer pageSize = 15;//页面大小 @kcm 修改了默认页大小 8.13
	protected String orderBy = null;
	protected String order = null;
	protected Integer currentCount;//当前页结果数
	protected Boolean autoCount = true;
	
	// 返回结果
	protected Integer totalCount = -1;//总结果数
	protected Integer totalPage = -1;//总页数
	protected List<T> result = Collections.emptyList();;//结果
	
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Page(Integer pageSize){
		this.pageSize = pageSize;
	}
	public Page(Integer prePage, Integer currentPage, Integer nextPage,
			Integer pageSize, String orderBy, String order,
			Integer currentCount, Integer totalCount, Integer totalPage,
			List<T> result) {
		super();
		this.prePage = prePage;
		this.currentPage = currentPage;
		this.nextPage = nextPage;
		this.pageSize = pageSize;
		this.orderBy = orderBy;
		this.order = order;
		this.currentCount = currentCount;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.result = result;
	}
	/**
	 * 获取上一页页码，从1开始，默认为0
	 * @return
	 */
	public Integer getPrePage() {
			return currentPage -1;
	}
	
	/**
	 * 设置上一页的页码,序号从1开始,低于1时自动调整为1.
	 * @param prePage
	 */
	public void setPrePage(final Integer prePage) {
		this.prePage = prePage;
		
		if (prePage < 1) {
			this.prePage = 0;
		}
	}
	
	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (currentPage - 1 >= 1);
	}
	
	/**
	 * 获取当前页页码，从1开始，默认为0
	 * @return
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	
	/**
	 * 设置当前页的页码,序号从1开始,低于1时自动调整为0.
	 * @param currentPage
	 */
	public void setCurrentPage(final Integer currentPage) {
		this.currentPage = currentPage;
		
		if(currentPage == null)//如果没填输入框，就把当前页码初始为1
			this.currentPage = 1;
		if (this.currentPage < 1) 
			this.currentPage = 0;
	}
	
	/**
	 * 获取下一页页码，从1开始，默认为1
	 * @return
	 */
	public Integer getNextPage() {
		return currentPage +1;
	}
	
	/**
	 * 设置下一页的页码,序号从1开始,低于1时自动调整为0.
	 * @param nextPage
	 */
	public void setNextPage(final Integer nextPage) {
		this.nextPage = nextPage;
		
		if (nextPage < 1) {
			this.nextPage = 0;
		}
	}
	
	/**
	 * 获取页面大小,默认为1.
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置每页的页面大小,低于1时自动调整为1.
	 * @param pageSize
	 */
	public void setPageSize(final Integer pageSize) {
		this.pageSize = pageSize;
		
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}
	
	public Page<T> pageSize(final Integer thePageSize) {
		setPageSize(thePageSize);
		return this;
	}
	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 * @return
	 */
	public String getOrderBy() {
		return orderBy;
	}
	
	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 * @param orderBy
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}
	
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}
	
	/**
	 * 获得排序方向.	
	 * @return
	 */
	public String getOrder() {
		return order;
	}
	
	/**
	 * 设置排序方式向.
	 * 
	 * @param order
	 *            可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(String order) {
		// 检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}

		this.order = StringUtils.lowerCase(order);
	}
	
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}
	
	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}
	
	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
	 */
	public Boolean isAutoCount() {
		return autoCount;
	}
	
	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数.
	 */
	public void setAutoCount(Boolean autoCount) {
		this.autoCount = autoCount;
	}
	
	public Page<T> autoCount(final Boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}
	
	public Integer getCurrentCount() {
		if(this.result !=null)
			return this.result.size();
		else
			return 0;
	}
	
	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}
	
	public void setCurrentCount(List<T> list) {
		if(list!=null)
			this.currentCount = list.size();
		else
			this.currentCount = 0;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(final Integer totalCount) {
		this.totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize +1;
		this.totalCount = totalCount;
		/*if(this.currentPage==0)
			this.currentPage = 1;
		else*/
		this.currentPage = getNextPage();
		this.prePage = this.currentPage - 1;
		this.nextPage = this.currentPage + 1;
	}
	
	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 * @return
	 */
	public Integer getTotalPage() {
		if (totalPage < 0)
			return -1;
		return totalPage;
	}
	
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	
	/**
	 * 是否还有下一页.
	 */
	public Boolean isHasNext() {
		return (currentPage + 1 <= getTotalPage());
	}
	public List<T> getResult() {
		return result;
	}
	
	public void setResult(List<T> result) {
		this.result = result;
		if(result !=null)
			this.currentCount = result.size();
		else
			this.currentCount = 0;
	}
}
