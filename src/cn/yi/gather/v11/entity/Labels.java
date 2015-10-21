package cn.yi.gather.v11.entity;

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
	private String ename;//英文名 
	
	@Column(name = "labeltype")
	private Integer labeltype;//0领域，1形式，2面向人群 3视频制作类型  4 寻人类型

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

	public Integer getLabeltype() {
		return labeltype;
	}

	public void setLabeltype(Integer labeltype) {
		this.labeltype = labeltype;
	}

}
