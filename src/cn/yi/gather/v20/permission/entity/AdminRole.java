package cn.yi.gather.v20.permission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_role")
public class AdminRole {
	
	@Id
	@Column(name="roleid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer roleid=null;
	
	@Column(name="rolename")
	private String rolename;  // rolename
	
	@Column(name="description")
	private String description;  // rolename
	
	@Column(name="rolelevel")
	private Integer rolelevel;  
	
	@Column(name="parentid")
	private Integer parentid;  
	
	@Column(name="createtime")
	private Date createtime=new Date();

	@Column(name="isdel")
	private Integer isdel=0;  //0未删除 1删除

		
	
	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRolelevel() {
		return rolelevel;
	}

	public void setRolelevel(Integer rolelevel) {
		this.rolelevel = rolelevel;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}  
	

}
