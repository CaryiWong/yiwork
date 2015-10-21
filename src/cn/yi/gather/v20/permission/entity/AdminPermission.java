package cn.yi.gather.v20.permission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_permission")
public class AdminPermission {
	
	@Id
	@Column(name="permitid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer permitid=null;
	
	@Column(name="permitname")
	private String permitname="";  // 权限名称
	
	
	@Column(name="parent")
	private String parent;  // 父ID

	@Column(name="isleaf")
	private String isleaf;   //0显示 1显示
	
	@Column(name="url")
	private String url;  
	 
	public Integer getPermitid() {
		return permitid;
	}

	public void setPermitid(Integer permitid) {
		this.permitid = permitid;
	}

	public String getPermitname() {
		return permitname;
	}

	public void setPermitname(String permitname) {
		this.permitname = permitname;
	}
 
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
