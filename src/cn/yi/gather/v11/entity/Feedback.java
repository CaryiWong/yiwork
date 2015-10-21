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
 * 意见反馈
 * @author Lee.J.Eric
 * @time 2014年6月11日下午5:44:19
 */
@Entity
@Table(name = "feedback")
public class Feedback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7786257999188464604L;
	@Id
	@Column(name = "id")
	private String id = System.currentTimeMillis()+RandomUtil.get32RandomUUID();//唯一码
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity = User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//发言者
	
	@Column(name = "content",length = 2000)
	private String content = "";//留言内容
	
	@Column(name = "createdate")
	private Date createdate = new Date();

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
