package cn.yi.gather.v11.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tools.utils.RandomUtil;

/**
 * 广播接收表
 * @author Lee.J.Eric
 * @time 2014年6月11日上午11:47:56
 */
@Entity
@Table(name = "broadcast")
public class Broadcast implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7453070276108811578L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Sysnetwork.class,fetch=FetchType.EAGER)
	@JoinColumn(name="sysnetwork_id")
	private Sysnetwork sysnetwork;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name = "createdate1")
	private Date createdate1=new Date();
	
	@Column(name = "isdel")
	private Integer isdel=0;
	
	@Column(name = "isread")
	private Integer isread=0;

	public Broadcast() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Sysnetwork getSysnetwork() {
		return sysnetwork;
	}

	public void setSysnetwork(Sysnetwork sysnetwork) {
		this.sysnetwork = sysnetwork;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedate1() {
		return createdate1;
	}

	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	
}
