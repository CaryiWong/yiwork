package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 行业分类
 * @author Lee.J.Eric
 * @time 2014年5月28日下午2:46:43
 */
@Entity
@Table(name = "classsort")
public class Classsort implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -922294889057723438L;
	
	@Id
	@Column(name = "id",unique=true)
	private Long id;//主键id
	
	@Column(name = "zname")
	private String zname;//中文名
	
	@Column(name = "ename")
	private String ename;//英文名 
	
	@Column(name = "pid")
	private Long pid;//父级id
	
	@Transient
	private List<Classsort> subClasssorts = new ArrayList<Classsort>();//其子类

	public Classsort() {
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public List<Classsort> getSubClasssorts() {
		return subClasssorts;
	}

	public void setSubClasssorts(List<Classsort> subClasssorts) {
		this.subClasssorts = subClasssorts;
	}
	

}
