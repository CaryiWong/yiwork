package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 重置密码记录表
 * @author Lee.J.Eric
 * @time 2014年5月30日下午5:14:19
 */
@Entity
@Table(name = "resetpassword")
public class Resetpassword implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7127470617483390424L;

	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis() + RandomUtil.get32RandomUUID();//唯一码
	
	@Column(name = "user")
	private String user;//用户ID
	
	@Column(name = "createdate")
	private Date createdate = new Date();
	
	@Column(name = "flag")
	private Integer flag = 0;//链接使用标记，链接只能使用1次

	public Resetpassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
}
