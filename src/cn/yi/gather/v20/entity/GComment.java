package cn.yi.gather.v20.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
 * 小活动评论表
 * @author Lee.J.Eric
 *
 */
@Entity
@Table(name = "gcomment")
public class GComment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4222860818440181700L;
	
	@Id
	@Column(name = "id")
	private String id=System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20);//唯一码;
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;//评论人
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="receive_id")
	private User receiver;//接受者
	
	@ManyToOne(cascade=CascadeType.REFRESH,targetEntity=Gathering.class,fetch=FetchType.EAGER)
	@JoinColumn(name="gathering_id")
	private Gathering gathering;//被评论的主体
	
	@Column(name="texts",length=5000)
	private String texts="";//评论内容
	
	@Column(name="createdate")
	private Date createdate=new Date();//评论时间
	
	@Column(name="isdel")
	private Integer isdel=0;

	public GComment() {
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

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Gathering getGathering() {
		return gathering;
	}

	public void setGathering(Gathering gathering) {
		this.gathering = gathering;
	}

	public String getTexts() {
		try {
			texts = URLDecoder.decode(texts, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texts;
	}

	public void setTexts(String texts) {
		try {
			this.texts = URLEncoder.encode(texts, "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
