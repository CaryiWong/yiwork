package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City implements Serializable{
	private static final long serialVersionUID = 4441570656386488069L;
	
	@Id
	@Column(name = "id")
	private String id="c"+System.currentTimeMillis();
	
	@Column(name = "name")
	private String name="";
	
	@Column(name = "createTime")
	private Date createTime=new Date();
	
	@Column(name = "userid")
	private String userid="";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
