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

import com.tools.utils.RandomUtil;

@Entity
@Table(name = "invitecomment")
public class Invitecomment implements Serializable{

	private static final long serialVersionUID = -1276468397623968519L;
	
	@Id
	@Column(name="id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//说话的人
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = Invitations.class,fetch=FetchType.EAGER)
	@JoinColumn(name="invitations_id")
	private Invitations invitations;//对应的邀请函
	
	@Column(name="createtime")
	private Date createtime=new Date();//说话时间
	
	@Column(name="texts")
	private String texts="";//内容
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="acceptuser_id")
	private User acceptuser;//听话的人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Invitations getInvitations() {
		return invitations;
	}

	public void setInvitations(Invitations invitations) {
		this.invitations = invitations;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public User getAcceptuser() {
		return acceptuser;
	}

	public void setAcceptuser(User acceptuser) {
		this.acceptuser = acceptuser;
	}

	public Invitecomment() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
