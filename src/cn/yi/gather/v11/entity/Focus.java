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
	
	@Transient
	private Long createdate;//关注时间
	
	@Column(name="createdate1")
	private Date createdate1=new Date();

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

	public Long getCreatedate() {
		if(createdate1!=null){
			return createdate1.getTime();
		}
		return 0l;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate1() {
		return createdate1;
	}

	public void setCreatedate1(Date createdate1) {
		this.createdate1 = createdate1;
	}
	
}
