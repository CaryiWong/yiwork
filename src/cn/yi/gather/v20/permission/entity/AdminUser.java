package cn.yi.gather.v20.permission.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.WorkSpaceInfo;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "admin_user")
public class AdminUser {
	
	private static final long serialVersionUID = -359723997518580670L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);
	
	 
	@Column(name="username")
	private String username;  // username
	
	@Column(name="userpassword")
	private String userpassword;  // userpassword
	
	@Column(name="usersex")
	private Integer usersex=0;  //0男 1女
	
	@Column(name="connectphone")
	private String connectphone;  // userpassword
	
	@Column(name="description")
	private String description;  
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminRole.class,fetch=FetchType.EAGER)
	@JoinColumn(name="roleid")
	private AdminRole adminrole; 
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = WorkSpaceInfo.class,fetch=FetchType.EAGER)
	@JoinColumn(name="spaceid")
	private WorkSpaceInfo workspaceinfo;	
	
	public WorkSpaceInfo getWorkspaceinfo() {
		return workspaceinfo;
	}

	public void setWorkspaceinfo(WorkSpaceInfo workspaceinfo) {
		this.workspaceinfo = workspaceinfo;
	}

	@Column(name="isdel")
	private Integer isdel=0;    //0未删除 1删除
	
	@Column(name="usertype")
	private Integer usertype=2;    //0 系统管理员  1 管理员  2 分地点管理员
	
	@Column(name="createtime")
	private Date createtime=new Date();  //
	
	@Column(name="lastlogintime")
	private Date lastlogintime=new Date();  //
	
	@Column(name="lastloginip")
	private String lastloginip;  //

	
	
	public AdminRole getAdminrole() {
		return adminrole;
	}

	public void setAdminrole(AdminRole adminrole) {
		this.adminrole = adminrole;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public Integer getUsersex() {
		return usersex;
	}

	public void setUsersex(Integer usersex) {
		this.usersex = usersex;
	}

	public String getConnectphone() {
		return connectphone;
	}

	public void setConnectphone(String connectphone) {
		this.connectphone = connectphone;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
