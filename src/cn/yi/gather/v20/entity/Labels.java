package cn.yi.gather.v20.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "labels")
public class Labels implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6953602835049579808L;
	
	@Id
	@Column(name = "id",unique=true)
	private Long id;//主键id
	
	@Column(name = "zname")
	private String zname;//中文名
	
	@Column(name = "ename")
	private String ename="";//英文名 
	
	@Column(name = "labeltype")
	private String labeltype = "";//job:职业，favourite:爱好，focus:关注
	
	@Column(name = "hot")
	private Integer hot=0;//标签热度
	
	@Column(name = "pid")
	private Long pid;//父级id
	
	@Column(name = "isdel")
	private Integer isdel=0;//默认0 使用中  1 删除

	public Labels() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZname() {
		return zname;
	}

	public void setZname(String zname) {
		this.zname = zname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	

	public String getLabeltype() {
		return labeltype;
	}

	public void setLabeltype(String labeltype) {
		this.labeltype = labeltype;
	}

	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
