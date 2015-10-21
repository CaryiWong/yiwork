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

/*@Entity
@Table(name = "admin_user_role")*/
public class AdminUserAndRole {
	/*private static final long serialVersionUID = -359723997518580670L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5);
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminUser.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private AdminUser adminuser; 
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = AdminRole.class,fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private AdminRole adminrole;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AdminUser getAdminuser() {
		return adminuser;
	}

	public void setAdminuser(AdminUser adminuser) {
		this.adminuser = adminuser;
	}

	public AdminRole getAdminrole() {
		return adminrole;
	}

	public void setAdminrole(AdminRole adminrole) {
		this.adminrole = adminrole;
	} */
	
}
