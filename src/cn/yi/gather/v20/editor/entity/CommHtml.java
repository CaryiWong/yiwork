package cn.yi.gather.v20.editor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name ="comm_html")
public class CommHtml implements Serializable{
	private static final long serialVersionUID = 4639354777185310635L;
	
	@Id
	@Column(name="id")
	private  String id="html"+System.currentTimeMillis();
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="htmlcode",length=20000)
	private String htmlcode;//页面代码
	
	@Column(name="url")
	private String url;//URL
	
	@Column(name="createtime")
	private Date createtime=new Date();
	
	@Column(name="userid")
	private String userid;//创建者用户ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlcode() {
		return htmlcode;
	}

	public void setHtmlcode(String htmlcode) {
		this.htmlcode = htmlcode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public  CommHtml() {
		
	}
	
}
