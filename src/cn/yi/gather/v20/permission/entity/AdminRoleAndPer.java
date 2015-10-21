package cn.yi.gather.v20.permission.entity;

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

import cn.yi.gather.v20.entity.User;

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "admin_role_permission")
public class AdminRoleAndPer {
	private static final long serialVersionUID = -359722997518580670L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5);
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminPermission.class,fetch=FetchType.EAGER)
	@JoinColumn(name="permitid")
	private AdminPermission adminpermission; 
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminRole.class,fetch=FetchType.EAGER)
	@JoinColumn(name="roleid")
	private AdminRole adminrole;

	@Column(name="status")
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	 

	public AdminPermission getAdminpermission() {
		return adminpermission;
	}

	public void setAdminpermission(AdminPermission adminpermission) {
		this.adminpermission = adminpermission;
	}

	public AdminRole getAdminrole() {
		return adminrole;
	}

	public void setAdminrole(AdminRole adminrole) {
		this.adminrole = adminrole;
	} 
	
}
