package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;

/**
 * push记录表
 * @author Lee.J.Eric
 * @time 2014年6月11日下午4:32:16
 */
@Entity
@Table(name = "pushlog")
public class Pushlog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1664148337031047725L;
	
	@Id
	@Column(name ="id")
	private String id = System.currentTimeMillis() + RandomUtil.get32RandomUUID();
	
	@Column(name = "title")
	private String title = "";//消息
	
	@Column(name = "type")
	private Integer type = 0 ;//消息类型，0活动，1资源，2系统
	
	@Column(name = "tag",length=1000)
	private String tag = "";//消息标签
	
	@Column(name = "createtime")
	private Date createtime = new Date();

	public Pushlog() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
