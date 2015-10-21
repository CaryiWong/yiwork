package cn.yi.gather.v11.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 活动回顾内容
 * @author Lee.J.Eric
 * @time 2014年6月13日下午3:12:05
 */
@Entity
@Table(name  = "reviewcontent")
public class Reviewcontent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3122303495457764263L;
	
	@Id
	@Column(name = "id")
	private String id;//与活动id值同
	
	@Column(name = "content",length=Integer.MAX_VALUE)
	private String content;//回顾页面的body的内容
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Reviewcontent(){
		
	}
	
	public Reviewcontent(String id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

}
