package com.common;

import java.sql.Timestamp;

public class Jr {
	private String method;
	private int cord;
	private Object data=new Object();
	private int pagenum=0;//当前页
	private int pagecount=0;//总页数
	private int pagesum=12;//每页条数
	private Object data2=new Object();
	private long total=0;
	private Long nowtime = System.currentTimeMillis(); 
	private Timestamp nowdatetime = new Timestamp(System.currentTimeMillis());//接口响应时间戳
	private String msg = "";//消息提示
	
	/**
	 * 记录总数
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月13日 上午11:05:15
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * 记录总数
	 * @param total
	 * Lee.J.Eric
	 * 2014 2014年4月13日 上午11:05:27
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * 数据
	 * @return
	 */
	public Object getData() {
		return data;
	}
	/**
	 * 数据
	 * @return
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public  Jr() {

	}

	public  Jr(String method,int cord,Object data) {
		this.cord=cord;
		this.method=method;
		this.data=data;
	}
	/**
	 * API 名称
	 * @return
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * API名称
	 * @return
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 状态码    0 OK
	 * @return
	 */
	public int getCord() {
		return cord;
	}

	/**
	 * 状态码 0 Ok
	 * @return
	 */
	public void setCord(int cord) {
		this.cord = cord;
	}

	/**
	 * 当前页
	 * @return
	 */
	public int getPagenum() {
		return pagenum;
	}

	/**
	 * 当前页
	 * @return
	 */
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}

	/**
	 * 总页数
	 * @return
	 */
	public int getPagecount() {
		return pagecount;
	}

	/**
	 * 总页数
	 * @return
	 */
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	/**
	 * 每页条数
	 * @return
	 */
	public int getPagesum() {
		return pagesum;
	}

	/**
	 * 每页条数
	 * @return
	 */
	public void setPagesum(int pagesum) {
		this.pagesum = pagesum;
	}
	public Object getData2() {
		return data2;
	}
	public void setData2(Object data2) {
		this.data2 = data2;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Timestamp getNowdatetime() {
		return nowdatetime;
	}
	public void setNowdatetime(Timestamp nowdatetime) {
		this.nowdatetime = nowdatetime;
	}
	public Long getNowtime() {
		return nowtime;
	}
	public void setNowtime(Long nowtime) {
		this.nowtime = nowtime;
	}
	
}
