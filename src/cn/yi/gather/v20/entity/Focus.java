package cn.yi.gather.v20.entity;

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
import javax.persistence.Transient;

import com.tools.utils.RandomUtil;
@Entity
@Table(name="focus")
public class Focus implements Serializable{

	private static final long serialVersionUID = 8764071715107456273L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Demands.class,fetch=FetchType.EAGER)
	@JoinColumn(name="demands_id")
	private Demands demands;//关注主体需求
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//关注用户
	
	@Column(name="createdate")
	private Date createdate=new Date();

	public Focus() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Demands getDemands() {
		return demands;
	}

	public void setDemands(Demands demands) {
		this.demands = demands;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
