package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 会员关注
 * @author Lee.J.Eric
 * @time 2014年6月3日下午3:34:27
 */
@Entity
@Table(name = "userfocus")
public class UserFocus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3036951166372353741L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@OneToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="who_id")
	private User who;//被加星的会员ID
	
	@OneToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="me_id")
	private User me;//自己的会员ID
	
	@Column(name="createdate")
	private Date createdate = new Date();//创建时间

	public UserFocus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public User getWho() {
		return who;
	}

	public void setWho(User who) {
		this.who = who;
	}

	public User getMe() {
		return me;
	}

	public void setMe(User me) {
		this.me = me;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	
}
